
package se.tube42.marm.data;

import java.util.*;


public class EntryList
{    
    private HashMap<String, Entry> list;
    
    public EntryList()
    {
        this.list = new HashMap<String, Entry>();
    }
    
    public Entry get(String base)
    {
        Entry ret = list.get(base);
        
        if(ret == null) {
            ret = new Entry(base);
            list.put(base, ret);
        }
        
        return ret;
    }    
    
    public Collection<Entry> entries()
    {
        return list.values();
    }
}
