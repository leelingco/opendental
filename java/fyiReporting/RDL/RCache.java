//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:33 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;

public class RCache   
{
    Hashtable _RunCache = new Hashtable();
    public RCache() throws Exception {
        _RunCache = new Hashtable();
    }

    public void add(ReportLink rl, String name, Object o) throws Exception {
        _RunCache.Add(getKey(rl,name), o);
    }

    public void addReplace(ReportLink rl, String name, Object o) throws Exception {
        String key = getKey(rl,name);
        _RunCache.Remove(key);
        _RunCache.Add(key, o);
    }

    public Object get(ReportLink rl, String name) throws Exception {
        return _RunCache[getKey(rl,name)];
    }

    public void remove(ReportLink rl, String name) throws Exception {
        _RunCache.Remove(getKey(rl,name));
    }

    public void add(ReportDefn rd, String name, Object o) throws Exception {
        _RunCache.Add(getKey(rd,name), o);
    }

    public void addReplace(ReportDefn rd, String name, Object o) throws Exception {
        String key = getKey(rd,name);
        _RunCache.Remove(key);
        _RunCache.Add(key, o);
    }

    public Object get(ReportDefn rd, String name) throws Exception {
        return _RunCache[getKey(rd,name)];
    }

    public void remove(ReportDefn rd, String name) throws Exception {
        _RunCache.Remove(getKey(rd,name));
    }

    public void add(String key, Object o) throws Exception {
        _RunCache.Add(key, o);
    }

    public void addReplace(String key, Object o) throws Exception {
        _RunCache.Remove(key);
        _RunCache.Add(key, o);
    }

    public Object get(String key) throws Exception {
        return _RunCache[key];
    }

    public void remove(String key) throws Exception {
        _RunCache.Remove(key);
    }

    public Object get(int i, String name) throws Exception {
        return _RunCache[getKey(i,name)];
    }

    public void remove(int i, String name) throws Exception {
        _RunCache.Remove(getKey(i,name));
    }

    String getKey(ReportLink rl, String name) throws Exception {
        return getKey(rl.ObjectNumber,name);
    }

    String getKey(ReportDefn rd, String name) throws Exception {
        if (rd.getSubreport() == null)
            return getKey(0,name);
        else
            return getKey(rd.getSubreport().ObjectNumber,name); 
    }

    // top level report use 0
    // Use the subreports object number
    String getKey(int onum, String name) throws Exception {
        return name + onum.ToString();
    }

}


