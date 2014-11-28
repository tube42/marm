

package se.tube42.marm.logic;

import java.io.*;
import se.tube42.marm.data.*;

public class ExecHelper
{
    
    /* see if the command is also a variale */
    private static String [] lookup_cmd(String [] args)
    {
        String cmd = Variables.get(args[0]);
        if(cmd == null)
            return args;
        
        String [] args0 = cmd.split(" ");        
        int idx = 0;
        int n1 = args0.length;
        int n2 = args.length;
        String [] new_args = new String[n1 + n2 -1];
        
        
        for(int i = 0; i < n1; i++)
            new_args[idx++] = args0[i];
        
        for(int i = 1; i < n2; i++)
            new_args[idx++] = args[i];
        
        return new_args;        
    }
    
    public static void run(String... args_)
          throws IOException
    {
        String [] args = lookup_cmd(args_);
        Process p = Runtime.getRuntime().exec(args);
        
        try {
            p.waitFor();
            
            if(p.exitValue() != 0) {
                throw new IOException("Exit code " + p.exitValue());
            }
            
        } catch(IOException e) {
            throw e;        
        } catch(Exception e) {
            throw new IOException(e.toString());
        }
    }    
    
}
