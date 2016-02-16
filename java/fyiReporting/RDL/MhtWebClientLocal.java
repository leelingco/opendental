//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:31 PM
//

package fyiReporting.RDL;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;

// This file was contributed to the RDL Project under the MIT License.  It was
// modified as part of merging into the RDL Project.
/*
The MIT License
Copyright (c) 2006 Christian Cunlif and Lionel Cuir of Autofee
Permission is hereby granted, free of charge, to any person obtaining a copy of 
this software and associated documentation files (the "Software"), to deal in 
the Software without restriction, including without limitation the rights to use, 
copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the 
Software, and to permit persons to whom the Software is furnished to do so, subject 
to the following conditions:
The above copyright notice and this permission notice shall be included in all 
copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR 
PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE 
LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, 
TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE 
USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
public class MhtWebClientLocal   
{
    static final String _AcceptedEncodings = "gzip,deflate";
    static final String _DefaultHttpUserAgent = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)";
    String _ContentLocation = new String();
    Encoding _DefaultEncoding = new Encoding();
    String _DetectedContentType = new String();
    Encoding _DetectedEncoding = new Encoding();
    Encoding _ForcedEncoding = new Encoding();
    byte[] _ResponseBytes = new byte[]();
    public enum HttpContentEncoding
    {
        None,
        Gzip,
        Compress,
        Deflate,
        Unknown
    }
    /**
    * Returns the actual location of the downloaded content, which can
    * be different than the requested URL, eg, http://web.com/IsThisAfolderOrNot
    */
    public String getContentLocation() throws Exception {
        return _ContentLocation;
    }

    /**
    * if the correct string encoding type cannot be detected, or detection is disabled
    * this is the default string encoding that will be used.
    */
    public Encoding getDefaultEncoding() throws Exception {
        return _DefaultEncoding;
    }

    public void setDefaultEncoding(Encoding value) throws Exception {
        _DefaultEncoding = value;
    }

    /**
    * this is the string encoding that was autodetected from the HTML content
    */
    public Encoding getDetectedEncoding() throws Exception {
        return _DetectedEncoding;
    }

    /**
    * Bypass detection of content encoding and force a specific encoding
    */
    public Encoding getForcedEncoding() throws Exception {
        return _ForcedEncoding;
    }

    public void setForcedEncoding(Encoding value) throws Exception {
        _ForcedEncoding = value;
    }

    /**
    * Returns the raw bytestream representing the last HTTP response data
    */
    public byte[] getResponseBytes() throws Exception {
        return _ResponseBytes;
    }

    /**
    * this is the string encoding that was autodetected from the HTML content
    */
    public String getResponseContentType() throws Exception {
        return _DetectedContentType;
    }

    /**
    * Returns true if the last HTTP response was in a non-text format
    */
    public boolean getResponseIsBinary() throws Exception {
        // if we truly have no content-type, we're kinda hosed, but
        // let's assume the response is text data to be on the safe side
        if (StringSupport.equals(_DetectedContentType, ""))
            return false;
         
        return (_DetectedContentType.IndexOf("text") == -1);
    }

    /**
    * Returns a string representation, with encoding, of the last HTTP response data
    */
    public String getResponseString() throws Exception {
        if (this.getResponseIsBinary())
            return "(" + _ResponseBytes.Length.ToString() + " bytes of binary data)";
         
        if (_ForcedEncoding == null)
            return _DetectedEncoding.GetString(_ResponseBytes);
         
        return _ForcedEncoding.GetString(_ResponseBytes);
    }

    public MhtWebClientLocal() throws Exception {
        // sets default values
        this.clear();
    }

    /**
    * clears any downloaded content and resets all properties to default values
    */
    public void clear() throws Exception {
        clearDownload();
        _DefaultEncoding = Encoding.GetEncoding("Windows-1252");
        _ForcedEncoding = null;
    }

    /**
    * clears any downloaded content
    */
    public void clearDownload() throws Exception {
        _ResponseBytes = null;
        _DetectedEncoding = null;
        _DetectedContentType = "";
        _ContentLocation = "";
    }

    /**
    * download URL contents to an array of bytes, using HTTP compression if possible
    */
    public byte[] downloadBytes(String url) throws Exception {
        getUrlData(url);
        return _ResponseBytes;
    }

    /**
    * returns a collection of bytes from a Url
    * 
    *  @param url The URL to retrieve
    */
    public void getUrlData(String url) throws Exception {
        Uri uri = new Uri(url);
        if (!uri.IsFile)
            throw new UriFormatException("url is not a local file");
         
        FileWebRequest request = WebRequest.Create(url) instanceof FileWebRequest ? (FileWebRequest)WebRequest.Create(url) : (FileWebRequest)null;
        if (request == null)
        {
            this.clear();
            return ;
        }
         
        request.Method = "GET";
        // download the target URL
        FileWebResponse response = (FileWebResponse)request.GetResponse();
        // convert response stream to byte array
        Stream stream = response.GetResponseStream();
        try
        {
            {
                ExtendedBinaryReader extReader = new ExtendedBinaryReader(stream);
                _ResponseBytes = extReader.readToEnd();
            }
        }
        finally
        {
            if (stream != null)
                Disposable.mkDisposable(stream).dispose();
             
        }
        // For local operations, we consider the data are never compressed. Else, the "Content-Encoding" field
        // in the headers would be "gzip" or "deflate". This could be handled quite easily with SharpZipLib for instance.
        // sometimes URL is indeterminate, eg, "http://website.com/myfolder"
        // in that case the folder and file resolution MUST be done on
        // the server, and returned to the client as ContentLocation
        _ContentLocation = response.Headers["Content-Location"];
        if (_ContentLocation == null)
            _ContentLocation = "";
         
        // if we have string content, determine encoding type
        // (must cast to prevent null)
        // HACK We determine the content type based on the uri extension,
        // as the header returned by the FileWebResponse is always "application/octet-stream" (hard coded in .NET!!)
        // text/html
        String ext = Path.GetExtension(uri.LocalPath).TrimStart(new char[]{ '.' });
        System.String __dummyScrutVar0 = ext;
        // What's important here is to identify TEXT mime types. Because, the default will resort to binary file.
        if (__dummyScrutVar0.equals("htm") || __dummyScrutVar0.equals("html"))
        {
            _DetectedContentType = "text/html";
        }
        else if (__dummyScrutVar0.equals("css"))
        {
            _DetectedContentType = "text/css";
        }
        else if (__dummyScrutVar0.equals("csv"))
        {
            _DetectedContentType = "text/csv";
        }
        else if (__dummyScrutVar0.equals("rtf"))
        {
            _DetectedContentType = "text/rtf";
        }
        else if (__dummyScrutVar0.equals("aspx") || __dummyScrutVar0.equals("xsl") || __dummyScrutVar0.equals("xml"))
        {
            _DetectedContentType = "text/xml";
        }
        else if (__dummyScrutVar0.equals("bmp"))
        {
            _DetectedContentType = "image/bmp";
        }
        else if (__dummyScrutVar0.equals("gif"))
        {
            _DetectedContentType = "image/gif";
        }
        else if (__dummyScrutVar0.equals("ico"))
        {
            _DetectedContentType = "image/x-icon";
        }
        else if (__dummyScrutVar0.equals("jpg") || __dummyScrutVar0.equals("jpeg"))
        {
            _DetectedContentType = "image/jpeg";
        }
        else if (__dummyScrutVar0.equals("png"))
        {
            _DetectedContentType = "image/png";
        }
        else if (__dummyScrutVar0.equals("tif") || __dummyScrutVar0.equals("tiff"))
        {
            _DetectedContentType = "image/tiff";
        }
        else if (__dummyScrutVar0.equals("js"))
        {
            _DetectedContentType = "application/x-javascript";
        }
        else
        {
            // Line commented: we don't change it
            _DetectedContentType = response.Headers["Content-Type"];
        }            
        // Always "application/octet-stream" ...
        if (_DetectedContentType == null)
            _DetectedContentType = "";
         
        if (getResponseIsBinary())
            _DetectedEncoding = null;
        else if (_ForcedEncoding == null)
            _DetectedEncoding = detectEncoding(_DetectedContentType,_ResponseBytes);
          
    }

    /**
    * attempt to convert this charset string into a named .NET text encoding
    */
    private Encoding charsetToEncoding(String charset) throws Exception {
        if (StringSupport.equals(charset, ""))
            return null;
         
        try
        {
            return Encoding.GetEncoding(charset);
        }
        catch (ArgumentException __dummyCatchVar0)
        {
            return null;
        }
    
    }

    /**
    * try to determine string encoding using Content-Type HTTP header and
    * raw HTTP content bytes
    * "Content-Type: text/html; charset=us-ascii"
    * <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    */
    private Encoding detectEncoding(String contentTypeHeader, byte[] responseBytes) throws Exception {
        // first try the header
        String s = Regex.Match(contentTypeHeader, "charset=([^;\"\'/>]+)", RegexOptions.IgnoreCase).Groups[1].ToString().ToLower();
        // if we can't get it from header, try the body bytes forced to ASCII
        Encoding encoding = charsetToEncoding(s);
        if (encoding == null)
        {
            s = Regex.Match(Encoding.ASCII.GetString(responseBytes), "<meta[^>]+content-type[^>]+charset=([^;\"\'/>]+)", RegexOptions.IgnoreCase).Groups[1].ToString().ToLower();
            encoding = charsetToEncoding(s);
            if (encoding == null)
                return _DefaultEncoding;
             
        }
         
        return encoding;
    }

    private void saveResponseToFile(String filePath) throws Exception {
        FileStream fs = new FileStream(filePath, FileMode.OpenOrCreate);
        try
        {
            {
                BinaryWriter bw = new BinaryWriter(fs);
                bw.Write(_ResponseBytes);
                bw.Close();
            }
        }
        finally
        {
            if (fs != null)
                Disposable.mkDisposable(fs).dispose();
             
        }
    }

    /**
    * Extends the 
    *  {@link #System.IO.BinaryReader}
    *  class by a 
    *  {@link #ReadToEnd}
    * 
    * method that can be used to read a whole file.
    * 
    * See http://dotnet.mvps.org/dotnet/faqs/?id=readfile&lang=en
    */
    public static class ExtendedBinaryReader  extends BinaryReader 
    {
        /**
        * Creates a new instance of the 
        *  {@code ExtendedBinaryReader}
        *  class.
        * 
        *  @param Input A stream.
        */
        public ExtendedBinaryReader(Stream Input) throws Exception {
            super(Input);
        }

        /**
        * Creates a new instance of the 
        *  {@code ExtendedBinaryReader}
        *  class.
        * 
        *  @param Input The provided stream.
        *  @param Encoding The character encoding.
        */
        public ExtendedBinaryReader(Stream Input, Encoding Encoding) throws Exception {
            super(Input, Encoding);
        }

        /**
        * Reads the whole data in the base stream and returns it in an
        * array of bytes.
        * 
        *  @return The streams whole binary data.This method use a buffer of 32k.
        */
        public byte[] readToEnd() throws Exception {
            return this.ReadToEnd(short.MaxValue);
        }

        /**
        * Reads the whole data in the base stream and returns it in an
        * array of bytes.
        * 
        *  @param initialLength The initial buffer length.
        *  @return The stream's whole binary data.
        * Based on an implementation by Jon Skeet [MVP].
        * See 
        *  {@link #}
        */
        public byte[] readToEnd(int initialLength) throws Exception {
            // If an unhelpful initial length was passed, just use 32K.
            if (initialLength < 1)
                initialLength = short.MaxValue;
             
            byte[] buffer = new byte[initialLength];
            int read = 0;
            int chunk = BaseStream.Read(buffer, 0, buffer.Length);
            while (chunk > 0)
            {
                //				for (int chunk = this.BaseStream.Read(buffer, read, buffer.Length - read); chunk > 0; chunk = this.BaseStream.Read(buffer, read, buffer.Length - read))
                read += chunk;
                // If the end of the buffer is reached, check to see if there is
                // any more data.
                if (read == buffer.Length)
                {
                    int nextByte = BaseStream.ReadByte();
                    // If the end of the stream is reached, we are done.
                    if (nextByte == -1)
                        return buffer;
                     
                    // Nope.  Resize the buffer, put in the byte we have just
                    // read, and continue.
                    byte[] newBuffer = new byte[buffer.Length * 2];
                    Buffer.BlockCopy(buffer, 0, newBuffer, 0, buffer.Length);
                    //						Array.Copy(buffer, newBuffer, buffer.Length);
                    newBuffer[read] = (byte)nextByte;
                    buffer = newBuffer;
                    read++;
                }
                 
                chunk = BaseStream.Read(buffer, read, buffer.Length - read);
            }
            // The buffer is now too big. Shrink it.
            byte[] returnBuffer = new byte[read];
            Buffer.BlockCopy(buffer, 0, returnBuffer, 0, read);
            return returnBuffer;
        }
    
    }

}


//				Array.Copy(buffer, returnBuffer, read);