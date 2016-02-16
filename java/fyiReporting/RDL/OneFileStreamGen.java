//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:31 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import fyiReporting.RDL.IStreamGen;

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
* file.  The first file uses that name.  If subsequant files are needed
* then a number suffix is generated sequentially.	 e.g. afile.html, afile2.gif,
* afile3.jpeg, ...
*/
public class OneFileStreamGen  extends IDisposable implements IStreamGen
{
    String _Directory = new String();
    StreamWriter _SW = new StreamWriter();
    Stream _io = new Stream();
    String _FileName = new String();
    List<String> _FileList = new List<String>();
    int _nextFileNumber = 1;
    boolean _Overwrite = new boolean();
    public OneFileStreamGen(String filename, boolean bOverwrite) throws Exception {
        _Overwrite = bOverwrite;
        String ext = Path.GetExtension(filename).Substring(1);
        // extension (without the '.')
        _Directory = Path.GetDirectoryName(filename);
        _FileName = Path.GetFileNameWithoutExtension(filename);
        _FileList = new List<String>();
        String relativeName = new String();
        RefSupport<String> refVar___0 = new RefSupport<String>();
        _io = getIOStream(refVar___0,ext);
        relativeName = refVar___0.getValue();
    }

    public List<String> getFileList() throws Exception {
        return _FileList;
    }

    public String getFileName() throws Exception {
        return _FileName;
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
        // Obtain a new file name
        // directory
        // "\"
        // filename
        // suffix: first file doesn't need number suffix
        String filename = String.Format("{0}{1}{2}{3}.{4}", _Directory, Path.DirectorySeparatorChar, _FileName, (this._nextFileNumber > 1 ? _nextFileNumber.ToString() : ""), extension);
        // extension
        _nextFileNumber++;
        // increment to next file
        FileInfo fi = new FileInfo(filename);
        if (fi.Exists)
        {
            if (_Overwrite)
                fi.Delete();
            else
                throw new Exception(String.Format("File {0} already exists.", filename)); 
        }
         
        relativeName.setValue(Path.GetFileName(filename));
        io = fi.Create();
        _FileList.Add(filename);
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


