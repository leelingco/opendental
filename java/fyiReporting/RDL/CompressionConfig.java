//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:33 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.XmlUtil;

public class CompressionConfig   
{
    int _UseCompression = -1;
    Assembly _Assembly = null;
    String _CodeModule = new String();
    String _ClassName = new String();
    String _Finish = new String();
    MethodInfo _FinishMethodInfo = new MethodInfo();
    //	if there is a finish method
    String _ErrorMsg = new String();
    // error encountered loading compression
    public CompressionConfig(String cm, String cn, String fn) throws Exception {
        _CodeModule = cm;
        _ClassName = cn;
        _Finish = fn;
        if (cm == null || cn == null || fn == null)
            _UseCompression = 2;
         
    }

    public boolean getCanCompress() throws Exception {
        if (_UseCompression >= 1)
            return true;
         
        // we've already successfully inited
        if (_UseCompression == 0)
            return false;
         
        // we've tried to init and failed
        init();
        return _UseCompression == 1;
    }

    // initialize compression
    // and return the status
    public void callStreamFinish(Stream strm) throws Exception {
        if (_FinishMethodInfo == null)
            return ;
         
        Object returnVal = _FinishMethodInfo.Invoke(strm, null);
        return ;
    }

    public Stream getStream(Stream str) throws Exception {
        if (_UseCompression == 2)
        {
            // use the built-in compression .NET 2 provides
            System.IO.Compression.DeflateStream cs = new System.IO.Compression.DeflateStream(str, System.IO.Compression.CompressionMode.Compress);
            return cs;
        }
         
        if (_UseCompression == 0)
            return null;
         
        if (_UseCompression == -1)
        {
            // make sure we're init'ed
            init();
            if (_UseCompression != 1)
                return null;
             
        }
         
        try
        {
            Object[] args = new Object[]{ str };
            Stream so = _Assembly.CreateInstance(_ClassName, false, BindingFlags.CreateInstance, null, args, null, null) instanceof Stream ? (Stream)_Assembly.CreateInstance(_ClassName, false, BindingFlags.CreateInstance, null, args, null, null) : (Stream)null;
            return so;
        }
        catch (Exception __dummyCatchVar4)
        {
            return null;
        }
    
    }

    public String getErrorMsg() throws Exception {
        return _ErrorMsg;
    }

    void init() throws Exception {
        synchronized (this)
        {
            {
                if (_UseCompression != -1)
                    return ;
                 
                _UseCompression = 0;
                try
                {
                    // assume failure
                    // Load the assembly
                    _Assembly = XmlUtil.assemblyLoadFrom(_CodeModule);
                    // Load up a test stream to make sure it will work
                    Object[] args = new Object[]{ new MemoryStream() };
                    Stream so = _Assembly.CreateInstance(_ClassName, false, BindingFlags.CreateInstance, null, args, null, null) instanceof Stream ? (Stream)_Assembly.CreateInstance(_ClassName, false, BindingFlags.CreateInstance, null, args, null, null) : (Stream)null;
                    if (so != null)
                    {
                        // we've successfully inited
                        so.Close();
                        _UseCompression = 1;
                    }
                    else
                        _Assembly = null; 
                    if (_Finish != null)
                    {
                        Type theClassType = so.GetType();
                        this._FinishMethodInfo = theClassType.GetMethod(_Finish);
                    }
                     
                }
                catch (Exception e)
                {
                    _ErrorMsg = e.InnerException == null ? e.Message : e.InnerException.Message;
                }
            
            }
        }
    }

}


