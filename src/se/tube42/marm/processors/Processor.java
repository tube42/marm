
package se.tube42.marm.processors;

import java.io.*;

public interface Processor
{
    public void process(
              String type, int zoom, 
              File infile, File outfile)
          throws IOException;
          
}
