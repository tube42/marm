
package se.tube42.marm.data;

import java.util.*;

public class Config
{
    // size mask
    private static int mask = 0;
    public static boolean isSizeEnabled(int n)
    {
        if(mask == 0) mask = 7;
        return (mask & (1 << n)) != 0;
    }

    public static void enableSize(int n)
    {
        mask |= 1 << n;
    }

    // variables
    private static HashMap<String,String> map = new HashMap<String,String>();

    public static void setVariable(String name, String value)
    {
        map.put(name, value);
    }

    public static String getVariable(String name)
    {
        return map.get(name);
    }

    public static String getVariable(String name, String def_)
    {
        String ret = getVariable(name);
        return ret != null ? ret : def_;
    }


    // Verbose
    private static boolean verbose = false;

    public static void setVerbose(boolean v)
    {
	verbose = v;
    }
    public static boolean isVerbose()
    {
	return verbose;
    }
}
