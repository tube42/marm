
package se.tube42.marm.data;

import java.util.*;

public final class Variables
{        
    private static HashMap<String,String> map;
    
    static {        
        map = new HashMap<String,String>();
    }
    
    
    public static void set(String name, String value)
    {
        map.put(name, value);
    }
    
    public static String get(String name)
    {
        return map.get(name);
    }
    
    public static String get(String name, String def_)
    {
        String ret = get(name);
        return ret != null ? ret : def_;
    }        
}
