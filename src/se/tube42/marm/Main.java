
package se.tube42.marm;

import se.tube42.marm.data.*;
import se.tube42.marm.logic.*;
import se.tube42.marm.processors.*;

public class Main 
{
    private static void do_help()
    {
        System.err.println("Usage: \n" +
                  "\t marm resize <dir-in> <dir-out>\n" +
                  "\t marm pack <dir-in> <dir-out> <atlas name>\n"
                  );
        System.exit(3);
    }
    
    private static void do_resize(String indir, String outdir)
    {
        Handler bh = new Handler(outdir);
        bh.setProcessor("svg", new SVGProcessor());
        bh.setProcessor("png", new PNGProcessor());
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
    
    public static void main(String args[])
    {
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
