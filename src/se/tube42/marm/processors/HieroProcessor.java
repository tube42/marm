
package se.tube42.marm.processors;

import java.io.*;
import se.tube42.marm.data.*;
import se.tube42.marm.logic.*;

public class HieroProcessor implements Processor
{
    
    public void process(
              String type, int zoom, 
              File infile, File outfile)
          throws IOException
    {
        final boolean keep = type.indexOf('k') != -1;        
        final float ratio = keep ? 1f : (float)Math.pow(2, zoom);
                
        outfile = FileHelper.replaceExtension(outfile, "fnt");
        final String hiero = infile.getPath();
        final String fnt = outfile.getPath();
        
        
        ExecHelper.run("hiero",
                  "--batch",
                  "--input", hiero,
                  "--output", fnt,
                  "--scale", ""+ratio
                  );            
    }
}
