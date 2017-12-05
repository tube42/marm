

package se.tube42.marm.logic;

import java.io.*;
import se.tube42.marm.data.*;

public class ExecHelper
{

    // read entire inputstream as a string:
    private static String read_all(InputStream i)
        throws IOException
    {
        BufferedInputStream bis = new BufferedInputStream(i);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        for(;;) {
            int n = bis.read();
            if(n == -1)
                return buf.toString();
            buf.write((byte) n);
        }
    }

    /* see if the command is also a variale */
    private static String [] lookup_cmd(String [] args)
    {
	// get command replacement
        String cmd = Config.getVariable(args[0]);
        if(cmd == null)
	    cmd = args[0];

        String [] args0 = cmd.split(" ");
        int n1 = args0.length;

	// get the rest
        int n2 = args.length - 1;

	// put them together
        String [] new_args = new String[n1 + n2];
        int idx = 0;

        for(int i = 0; i < n1; i++)
            new_args[idx++] = args0[i];

        for(int i = 0; i < n2; i++)
            new_args[idx++] = args[1 + i];

        return new_args;
    }

    public static String run(String... args_)
          throws IOException
    {
        String [] args = lookup_cmd(args_);

	if(Config.isVerbose())
	    System.out.println("Executing " + String.join(" ", args));

        Process p = Runtime.getRuntime().exec(args);
        try {
            InputStream i = p.getInputStream();
            p.waitFor();

            if(p.exitValue() != 0) {
                throw new IOException("Exit code " + p.exitValue());
            }

            String ret = read_all(i);
            i.close();
            return ret;

        } catch(IOException e) {
            throw e;
        } catch(Exception e) {
            throw new IOException(e.toString());
        }
    }

}
