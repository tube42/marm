
package se.tube42.marm.data;


public class Entry
{    
    public String []in;
    public String base;
    
    public Entry(String base)
    {
        this.in = new String[Const.COUNT];
        this.base = base;
    }    
}
