

package se.tube42.marm.logic;

import java.io.*;
import se.tube42.marm.data.*;

public class ExecHelper
{
    
    public static void run(String... args)
          throws IOException
    {
        /*
        for(String s : args) System.out.print(s + " ");
           System.out.println();
        */
        Process p = Runtime.getRuntime().exec(args);
        
        try {
            p.waitFor();
            
            if(p.exitValue() != 0) {
                throw new IOException("Exit code " + p.exitValue());
            }

        } catch(Exception e) {
            throw new IOException(e.toString());
        }
    }    
    
}
