

package se.tube42.marm.logic;

import java.io.*;
import java.util.*;

import se.tube42.marm.data.*;
import se.tube42.marm.processors.*;


public class Handler
extends DirWalker
{
    private String outdir;
    private File [] dirs;
    private HashMap<String, EntryList> lists;
    private HashMap<String, Processor> processors;
    
    private EntryList def_list;
    private Processor def_proc;
    
    public Handler(String outdir)
    {
        this.outdir = outdir;
        this.dirs = new File[Const.COUNT];
        this.lists = new HashMap<String, EntryList>();
        this.processors = new HashMap<String, Processor>();
        
        this.def_list = new EntryList();
        this.def_proc = null;
        this.lists.put(null, def_list);
        
        for(int i = 0; i < dirs.length; i++) {
            String dirname = "" + (1 << i);
            dirs[i] = new File(outdir, dirname);
            dirs[i].mkdirs();
        }
    }
    
    public void setProcessor(String ext, Processor proc)
    {
        ext = ext.toLowerCase();
        processors.put(ext, proc);
    }
    
    public void setDefaultProcessor(Processor proc)
    {
        def_proc = proc;
    }
    
    
    public void processFile(
              String ext, 
              String type, int zoom, 
              File infile, File outfile)
    {
        try {
            if(ext != null) {
                Processor proc = processors.get(ext);
                proc.process(type, zoom, infile, outfile);
            } else if(def_proc != null) {
                def_proc.process(type, zoom, infile, outfile);            
            } else {
                System.err.println("WARNING: don't know how to handler " + infile);
            }
        } catch(Exception e) {
            System.err.println("ERROR: " + e);
            System.exit(20);
        }
    }

    public EntryList getListForExtension(String ext)
    {
        ext = ext.toLowerCase();
        
        Processor proc = processors.get(ext);
        if(proc != null) {
            return findListFor(ext, true);
        } else {
            return def_list;
        }
    }
    
    // ----------------------------------------------------
            
    public boolean accessDir(File dir, int level)
    {
        final String name = dir.getName();
        return name.charAt(0) != '.';
    }
    
    public final void accessFile(File f)
    {
        final String filename = f.getPath();        
        final String ext = FileHelper.getExtension(filename).toLowerCase();
        final EntryList el = getListForExtension(ext);
        if(el != null) {
            
            final String base = FileHelper.getBase(filename);
            final String type = FileHelper.getType(filename);
            final Entry e = el.get(base);
            
            // just some error checking in case we have 1,2,3,4,... instead of 1,2,4,8,...
            if(type.indexOf('3') != -1 || type.indexOf('5') != -1) {
                System.err.println("BAD SCALE IN TYPE: " + type);
                System.exit(20);
            }

            // add file to entry
            int count = 0;
            for(int i = 0; i < Const.COUNT; i++) {
                char c = (char) ('0' + (1 << i));
                
                if( type.indexOf(c) != -1) { 
                    e.in[i] = filename;
                    count++;
                }
            }
            
            if(count == 0)
                e.in[0] = filename;
        }        
    }
    
    
    // -------------------------------------------------------------
        
    protected final EntryList findListFor(String ext, boolean create)
    {
        EntryList ret = lists.get(ext);
        if(ret == null && create) {
            ret = new EntryList();
            lists.put(ext, ret);
        }
        return ret;
    }
            
    public void generate()
    {
        for(final String ext : lists.keySet()) {
            final EntryList el = lists.get(ext);            
            // System.out.println("HANDLING EXT " + ext);
            
            for(Entry e : el.entries()) {
                // System.out.println("HANDLING " + e.base);
            
                for(int i = 0; i < Const.COUNT; i++) {
                    final int j = get_best_match(e, i);
                    final String filename = e.in[j];                    
                    final File fin = new File(filename);
                    final File fout = new File(dirs[i], FileHelper.getBase(filename));                    
                    processFile(ext, FileHelper.getType(filename), i - j, fin, fout);
                }
            }                   
        }        
    }
    
    // ------------------------------------------------
        
    private int get_best_match(Entry e, int level)
    {
        // smaller
        for(int i = level; i >= 0; i--) {
            if(e.in[i] != null)
                return i;
        }
        
        // larger?
        for(int i = level; i < Const.COUNT; i++) {
            if(e.in[i] != null)
                return i;
        }
        
        System.err.println("ERROR: no match for " + e.base + " at level " + level);
        System.exit(20);
        return -1;
    }
}
