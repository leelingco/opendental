//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:32 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import fyiReporting.RDL.IStreamGen;
import fyiReporting.RDL.StreamGen;

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
* An implementation of IStreamGen.  Constructor is passed the name of a
* directory to place work files and the primary name of resultant file.
*/
public class StreamGen  extends IDisposable implements IStreamGen
{
    String _Directory = new String();
    String _RelativeDirectory = new String();
    StreamWriter _SW = new StreamWriter();
    Stream _io = new Stream();
    String _FileName = new String();
    Random _rand = new Random();
    List<String> _FileList = new List<String>();
    public StreamGen(String directory, String relativeDirectory, String ext) throws Exception {
        _Directory = directory;
        _RelativeDirectory = relativeDirectory;
        if (_Directory[_Directory.Length - 1] == Path.DirectorySeparatorChar || _Directory[_Directory.Length - 1] == Path.AltDirectorySeparatorChar)
            _Directory = _Directory.Substring(0, _Directory.Length - 1);
         
        // ensure we have a separator before and after the relative directory name
        if (_RelativeDirectory == null)
            _RelativeDirectory = Path.DirectorySeparatorChar.ToString();
         
        if (!(_RelativeDirectory[0] == Path.DirectorySeparatorChar || _RelativeDirectory[0] == Path.AltDirectorySeparatorChar))
            _RelativeDirectory = Path.DirectorySeparatorChar + _RelativeDirectory;
         
        if (!(_RelativeDirectory[_RelativeDirectory.Length - 1] == Path.DirectorySeparatorChar || _RelativeDirectory[_RelativeDirectory.Length - 1] == Path.AltDirectorySeparatorChar))
            _RelativeDirectory = _RelativeDirectory + Path.DirectorySeparatorChar;
         
        _FileList = new List<String>();
        String relativeName = new String();
        RefSupport<String> refVar___0 = new RefSupport<String>();
        _io = getIOStream(refVar___0,ext);
        relativeName = refVar___0.getValue();
        _FileName = _Directory + relativeName;
        _rand = null;
    }

    public List<String> getFileList() throws Exception {
        return _FileList;
    }

    public String getFileName() throws Exception {
        return _FileName;
    }

    public String getRelativeDirectory() throws Exception {
        return _RelativeDirectory;
    }

    public void closeMainStream() throws Exception {
        if (_SW != null)
        {
            _SW.Close();
            _SW = null;
        }
         
        if (_io != null)
        {
            _io.Close();
            _io = null;
        }
         
        return ;
    }

    public Stream getStream() throws Exception {
        return this._io;
    }

    public TextWriter getTextWriter() throws Exception {
        if (_SW == null)
            _SW = new StreamWriter(_io);
         
        return _SW;
    }

    // create a new file in the directory specified and return
    //   a Stream caller can then write to.   relativeName is filled in with
    //   name we generate (sans the directory).
    public Stream getIOStream(RefSupport<String> relativeName, String extension) throws Exception {
        Stream io = null;
        synchronized (StreamGen.class)
        {
            {
                // single thread lock while we're creating a file
                //  this assumes no other routine creates files in this directory
                // Obtain a new file name
                if (_rand == null)
                    _rand = new Random();
                 
                int rnd = _rand.Next();
                String filename = _Directory + _RelativeDirectory + "f" + rnd.ToString() + "." + extension;
                FileInfo fi = new FileInfo(filename);
                while (fi.Exists)
                {
                    rnd = _rand.Next();
                    filename = _Directory + _RelativeDirectory + "f" + rnd.ToString() + "." + extension;
                    fi = new FileInfo(filename);
                }
                relativeName.setValue(_RelativeDirectory + "f" + rnd.ToString() + "." + extension);
                io = fi.Create();
                _FileList.Add(filename);
            }
        }
        return io;
    }

    public void dispose() throws Exception {
        if (_SW != null)
        {
            _SW.Flush();
            _SW.Close();
            _SW = null;
        }
         
        if (_io != null)
        {
            _io.Flush();
            _io.Close();
            _io = null;
        }
         
    }

}


