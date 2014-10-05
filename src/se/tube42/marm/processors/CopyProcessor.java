
package se.tube42.marm.processors;

import java.io.*;
import se.tube42.marm.data.*;
import se.tube42.marm.logic.*;

public class CopyProcessor implements Processor
{
    
    public void process(
              String type, int zoom, 
              File infile, File outfile)
          throws IOException
    {
        FileHelper.copy(infile, outfile);
    }
}
