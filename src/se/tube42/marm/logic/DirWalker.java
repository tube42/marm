

package se.tube42.marm.logic;

import java.io.*;
import java.util.*;

import se.tube42.marm.data.*;


public abstract class DirWalker
{
    public DirWalker()
    {
    }
    
    
    public abstract void accessFile(File file);
    public abstract boolean accessDir(File file, int level);
    
    
    // -----------------------------------------------------------    
    
    public void scan(String indir)
    {
        File d0 = new File(indir);
        if(!d0.isDirectory()) {
            System.err.println("Could not open directory " + indir);
            System.exit(20);
        }
        
        scan_dir(d0, 0);                
    }
    
    // -----------------------------------------------------------
    
    private void scan_dir(File dir, int level)
    {
        try {            
            for (final File f : dir.listFiles()) {
                final String filename = f.getPath();
                
                if(f.isDirectory()) {
                    if(accessDir(f, level +1))
                        scan_dir(f, level + 1);
                }  else if(f.isFile()) {
                    accessFile(f);
                }
            }
        } catch(Exception exx) {
            System.err.println("ERROR: " +exx);
            System.exit(20);
        }
    }           
}
