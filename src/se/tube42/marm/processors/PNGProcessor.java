
package se.tube42.marm.processors;

import java.io.*;
import se.tube42.marm.data.*;
import se.tube42.marm.logic.*;

public class PNGProcessor implements Processor
{
    
    public void process(
              String type, int zoom, 
              File infile, File outfile)
          throws IOException
    {
        final boolean plain = type.indexOf('p') != -1;
        final boolean keep = type.indexOf('k') != -1;        
        
        
        if(keep) {
            FileHelper.copy(infile, outfile);
        } else {
            final float ratio = keep ? 1f : (float)Math.pow(2, zoom);        
            final String pngin = infile.getPath();
            final String pngout = outfile.getPath();
            
            
            ExecHelper.run(
                      "convert",
                      pngin,    
                      "-transparent", "#fffffe",
                      "-filter", plain ? "Point" : "Gaussian",
                      "-resize", "" + (100 * ratio) + "%",
                      "-define", "png:bit-depth=8",
                      "png32:" + pngout
                      );            
            
        }
    }
}
