//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:31 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import fyiReporting.RDL.IStreamGen;
import fyiReporting.RDL.MemoryStreamGen;

/* ====================================================================
    Copyright (C) 2004-2006  fyiReporting Software, LLC
    This file is part of the fyiReporting RDL project.
	
    This library is free software; you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation; either version 2.1 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
    You should have received a copy of the GNU Lesser General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
    For additional information, email info@fyireporting.com or visit
    the website www.fyiReporting.com.
*/
/**
* An implementation of IStreamGen.  Used for single file with memory stream.
* XML and PDF are the only types that will work with this implementation.
*/
public class MemoryStreamGen  extends IDisposable implements IStreamGen
{
    static public long Counter = new long();
    // counter used for unique expression count
    MemoryStream _io = new MemoryStream();
    StreamWriter _sw = null;
    List<MemoryStream> _MemoryList = new List<MemoryStream>();
    // array of MemoryStreams - 1st is main stream; others are generated
    //   for charts, images, ...
    List<String> _MemoryNames = new List<String>();
    // names associated with the MemoryStreams
    public String Prefix = new String();
    // used as prefix to names generated
    public String Suffix = new String();
    // used as a suffix to names generated
    public String Extension = "";
    // extension name for first file
    public MemoryStreamGen() throws Exception {
        this(null, null, null);
    }

    public MemoryStreamGen(String prefix, String suffix, String extension) throws Exception {
        Prefix = prefix;
        Suffix = suffix;
        Extension = extension;
        _io = new MemoryStream();
        _MemoryList = new List<MemoryStream>();
        _MemoryList.Add(_io);
        _MemoryNames = new List<String>();
        // create the first name
        RefSupport refVar___0 = new RefSupport(MemoryStreamGen.Counter);
        String unique = Interlocked.Increment(refVar___0).ToString();
        MemoryStreamGen.Counter = refVar___0.getValue();
        String name = new String();
        if (Prefix == null)
            name = "a" + Extension + "&unique=" + unique;
        else
            name = Prefix + Extension + "&unique=" + unique; 
        _MemoryNames.Add(name);
    }

    public String getText() throws Exception {
        _sw.Flush();
        StreamReader sr = null;
        String t = null;
        try
        {
            _io.Position = 0;
            sr = new StreamReader(_io);
            t = sr.ReadToEnd();
        }
        finally
        {
            sr.Close();
        }
        return t;
    }

    public IList getMemoryList() throws Exception {
        return _MemoryList;
    }

    public IList getMemoryNames() throws Exception {
        return _MemoryNames;
    }

    public void closeMainStream() throws Exception {
        return ;
    }

    //	_io.Close();   // TODO  --- need to make this more robust; need multiple streams as well
    public Stream getStream() throws Exception {
        return this._io;
    }

    public TextWriter getTextWriter() throws Exception {
        if (_sw == null)
            _sw = new StreamWriter(_io);
         
        return _sw;
    }

    /**
    * Createa a new stream and return a Stream caller can then write to.
    * 
    *  @param relativeName Filled in with a name
    *  @param extension ????
    *  @return
    */
    public Stream getIOStream(RefSupport<String> relativeName, String extension) throws Exception {
        MemoryStream ms = new MemoryStream();
        _MemoryList.Add(ms);
        RefSupport refVar___1 = new RefSupport(MemoryStreamGen.Counter);
        String unique = Interlocked.Increment(refVar___1).ToString();
        MemoryStreamGen.Counter = refVar___1.getValue();
        if (Prefix == null)
            relativeName.setValue("a" + extension + "&unique=" + unique);
        else
            relativeName.setValue(Prefix + extension + "&unique=" + unique); 
        if (Suffix != null)
            relativeName.setValue(relativeName.getValue() + Suffix);
         
        _MemoryNames.Add(relativeName.getValue());
        return ms;
    }

    public void dispose() throws Exception {
        if (_sw != null)
        {
            _sw.Close();
            _sw = null;
        }
         
        if (_io != null)
        {
            _io.Close();
            _io = null;
        }
         
    }

}


