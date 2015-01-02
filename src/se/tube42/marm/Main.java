
package se.tube42.marm;

import java.util.*;

import se.tube42.marm.data.*;
import se.tube42.marm.logic.*;
import se.tube42.marm.processors.*;


public class Main 
{
    private static void do_help()
    {
        System.err.println("Usage: \n" +                 
                  "\t marm [OPTIONS] resize <dir-in> <dir-out>\n" +
                  "\t marm [OPTIONS] pack <dir-in> <dir-out> <atlas name>\n" +
                  "Valid options are:\n" +
                  "\t name=value     define a variable\n"
                  );
        System.exit(3);
    }
    
    
    private static void do_resize(String indir, String outdir)
    {
        Handler bh = new Handler(outdir);
        bh.setProcessor("svg", new SVGProcessor());
        bh.setProcessor("png", new PNGProcessor());
        bh.setProcessor("hiero", new HieroProcessor());
        bh.setDefaultProcessor(new CopyProcessor());
        
        bh.scan(indir);
        
        bh.generate();        
    }
    
    private static void do_pack(String indir, String outdir, String name)
    {
        try {
            ExecHelper.run(
                      "texturepacker",
                      indir,
                      outdir,
                      name
                      );      
        } catch(Exception e) {
            System.err.println("ERROR: " + e);
            System.exit(20);
        }
    }
    
    // ------------------------------------------------------------------------------
    
    /*
     * parse the options and remove them from argument list
     */
    public static String [] parse_options(String [] args )
    {        
        List<String> l = new ArrayList<String>();
        
        for(String s : args) {
            if(s.charAt(0) == '-') {
                if(s.equals("-1")) {
                    Config.enableSize(0);
                } else if(s.equals("-2")) {
                    Config.enableSize(1);
                } else if(s.equals("-4")) {
                    Config.enableSize(2);                
                } else if(s.equals("-8")) {
                    Config.enableSize(3);
                } else {
                    do_help();
                }
                    
            } else {
                int n = s.indexOf("=");
                if(n == -1) {
                    l.add(s);
                } else {
                    String s1 = s.substring(0, n);
                    String s2 = s.substring(n+1);
                    Config.setVariable(s1, s2);
                }
            }
        }
        
        // convert List to String []
        String [] ret = new String[ l.size()];
        for(int i = 0; i < ret.length; i++)
            ret[i] = l.get(i);
        return ret;
    }
    
    // ------------------------------------------------------------------------------
    
    public static void main(String args[])
    {              
        args = parse_options(args);
        
        if(args.length == 0) 
            do_help();        
        
        if(args[0].equals("resize") && args.length == 3) {
            do_resize(args[1], args[2]);
        } else if(args[0].equals("pack") && args.length == 4) {
            do_pack(args[1], args[2], args[3]);
            
        } else {
            do_help();
        }
    }
}
