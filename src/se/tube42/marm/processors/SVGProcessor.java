
package se.tube42.marm.processors;

import java.io.*;
import se.tube42.marm.data.*;
import se.tube42.marm.logic.*;

public class SVGProcessor implements Processor
{
    
    public void process(
              String type, int zoom, 
              File infile, File outfile)
          throws IOException
    {
        final boolean plain = type.indexOf('p') != -1;
        final boolean keep = type.indexOf('k') != -1;        
        final float ratio = keep ? 1f : (float)Math.pow(2, zoom);
        
        outfile = FileHelper.replaceExtension(outfile, "png");
        final String svg = infile.getPath();
        final String png = outfile.getPath();
        
        
        if(plain) {
            ExecHelper.run("inkscape",
                      "--without-gui",
                      "--file="+ svg,
                      "--export-dpi=" + (90 * ratio),
                      "--export-background-opacity=0",
                      "--export-png="+png
                      );                
        } else {
            ExecHelper.run("inkscape",
                      "--without-gui",
                      "--file="+svg,
                      "--export-dpi=1440",
                      "--export-background-opacity=0",
                      "--export-png="+png 
                      );
            
            ExecHelper.run("convert",
                      png,
                      "-transparent", 
                      "#fffffe",
                      "-resize",
                      "" + (6.25 * ratio) + "%",
                      "-define",
                      "png:bit-depth=8",
                      "png32:" + png
                      );            
            
        }        
    }
}
