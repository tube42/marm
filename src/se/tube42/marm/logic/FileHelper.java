

package se.tube42.marm.logic;

import java.io.*;
import java.nio.*;
import java.nio.file.*;

// import se.tube42.marm.data.*;

public class FileHelper 
{
    public static String getBase(String filename)
    {
        final String ext = getExtension(filename);
        int n;
        
        n = filename.lastIndexOf('.');
        if(n != -1)
            filename = filename.substring(0, n);
        
        n = filename.lastIndexOf('/');
        if(n != -1) 
            filename = filename.substring(n+1);
        
        n = filename.lastIndexOf('_');
        if(n != -1)
            filename = filename.substring(0, n);
        
                
        if(ext.length() > 0) 
            filename = filename + "." + ext;
        
        return filename;
    }
    
    public static String getType(String filename)
    {
        final int start = filename.lastIndexOf('_');
        if(start != -1) {
            filename = filename.substring(start+1);
            final int end = filename.indexOf('.');
            if(end != -1)
                filename = filename.substring(0, end);
            
            return filename;
        }
        
        return "";
    }
    
    public static String getExtension(String filename)
    {
        int n = filename.lastIndexOf('.');
        if(n == -1 || n == filename.length()-1)
            return "";
        
        
        return filename.substring(n+1);
    }
    
    public static File getTempFile(String ext)
    {
        String tmpdir = System.getProperty("java.io.tmpdir");
        if(tmpdir == null) tmpdir = "/tmp";
        try {
            return File.createTempFile("_", "." + ext, new File(tmpdir));
        } catch(Exception e) {
            System.err.println("ERROR: " + e);
            System.exit(20);
            return null;
        }
    }
    
    public static File replaceExtension(File f, String newext)
    {
        String filename = f.getPath();
        int n = filename.lastIndexOf('.');
        if(n != -1)
            filename = filename.substring(0, n);
        
        return new File(filename + "." + newext);
    }
    
    
    public static void copy(File from, File to)
          throws IOException                    
    {
        Files.copy( 
                  from.toPath(), 
                  to.toPath(),
                  StandardCopyOption.REPLACE_EXISTING,
                  StandardCopyOption.COPY_ATTRIBUTES,
                  LinkOption.NOFOLLOW_LINKS );       
    }
    
}
