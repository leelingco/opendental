//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:31 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import fyiReporting.RDL.MhtBuilder;
import fyiReporting.RDL.MhtWebFile;

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
/**
* represents an external file referenced in our parent HTML at the target URL
*/
public class MhtWebFile   
{
    MhtBuilder _Builder;
    String _ContentLocation = new String();
    String _ContentType = new String();
    byte[] _DownloadedBytes = new byte[]();
    Exception _DownloadException = null;
    String _DownloadExtension = "";
    String _DownloadFilename = "";
    String _DownloadFolder = "";
    NameValueCollection _ExternalFileCollection = new NameValueCollection();
    boolean _IsBinary = new boolean();
    Encoding _TextEncoding = new Encoding();
    String _Url = new String();
    String _UrlFolder = new String();
    String _UrlRoot = new String();
    String _UrlUnmodified = new String();
    boolean _UseHtmlFilename = false;
    boolean _WasDownloaded = false;
    public boolean WasAppended = new boolean();
    public MhtWebFile(MhtBuilder parent) throws Exception {
        _Builder = parent;
    }

    public MhtWebFile(MhtBuilder parent, String url) throws Exception {
        _Builder = parent;
        if (!StringSupport.equals(url, ""))
            this.setUrl(url);
         
    }

    /**
    * The Content-Type of this file as returned by the server
    */
    public String getContentType() throws Exception {
        return _ContentType;
    }

    /**
    * The raw bytes returned from the server for this file
    */
    public byte[] getDownloadedBytes() throws Exception {
        return _DownloadedBytes;
    }

    /**
    * If not .WasDownloaded, the exception that prevented download is stored here
    */
    public Exception getDownloadException() throws Exception {
        return _DownloadException;
    }

    /**
    * file type extension to use on downloaded file
    * this property is only used if the DownloadFilename property does not
    * already contain a file extension
    */
    public String getDownloadExtension() throws Exception {
        if (StringSupport.equals(_DownloadExtension, "") && this.getWasDownloaded())
            _DownloadExtension = this.extensionFromContentType();
         
        return _DownloadExtension;
    }

    public void setDownloadExtension(String value) throws Exception {
        _DownloadExtension = value;
    }

    /**
    * filename to download this file as
    * if no filename is provided, a filename will be auto-generated based on
    * the URL; if the UseHtmlTitleAsFilename property is true, then the
    * title tag will be used to generate the filename
    */
    public String getDownloadFilename() throws Exception {
        if (StringSupport.equals(_DownloadFilename, ""))
        {
            if (this._UseHtmlFilename && this.getWasDownloaded() && this.getIsHtml())
            {
                String htmlTitle = this.getHtmlTitle();
                if (!StringSupport.equals(htmlTitle, ""))
                    _DownloadFilename = makeValidFilename(htmlTitle,false) + ".htm";
                 
            }
            else
                _DownloadFilename = this.filenameFromUrl(); 
        }
         
        return _DownloadFilename;
    }

    public void setDownloadFilename(String value) throws Exception {
        _DownloadFilename = value;
    }

    /**
    * folder to download this file to
    * if no folder is provided, the current application folder will be used
    */
    public String getDownloadFolder() throws Exception {
        if (StringSupport.equals(_DownloadFolder, ""))
            _DownloadFolder = AppDomain.CurrentDomain.BaseDirectory;
         
        return _DownloadFolder;
    }

    public void setDownloadFolder(String value) throws Exception {
        this._DownloadFolder = value;
    }

    /**
    * the folder name used in the DownloadFolder
    */
    public String getDownloadFolderName() throws Exception {
        return Regex.Match(this.getDownloadFolder(), "(?<Folder>[^\\\\]+)\\\\*$").Groups["Folder"].Value;
    }

    /**
    * fully qualified path and filename to download this file to
    */
    public String getDownloadPath() throws Exception {
        if (StringSupport.equals(Path.GetExtension(this.getDownloadFilename()), ""))
            return Path.Combine(this.getDownloadFolder(), this.getDownloadFilename() + this.getDownloadExtension());
         
        return Path.Combine(this.getDownloadFolder(), this.getDownloadFilename());
    }

    public void setDownloadPath(String value) throws Exception {
        this._DownloadFilename = Path.GetFileName(value);
        if (StringSupport.equals(_DownloadFilename, ""))
            _DownloadFolder = value;
        else
            _DownloadFolder = value.Replace(_DownloadFilename, ""); 
    }

    /**
    * If this file has external dependencies, the folder they will be stored on disk
    */
    public String getExternalFilesFolder() throws Exception {
        return (Path.Combine(this.getDownloadFolder(), Path.GetFileNameWithoutExtension(this.getDownloadFilename())) + "_files");
    }

    /**
    * If this file is HTML, retrieve the <TITLE> tag from the HTML
    * (maximum of 50 characters)
    */
    public String getHtmlTitle() throws Exception {
        //				if (!this.IsHtml)
        //					throw new Exception("This file isn't HTML, so it has no HTML <TITLE> tag.");
        String remp = this.toString();
        String s = Regex.Match(this.toString(), "<title[^>]*?>(?<text>[^<]+)</title>", RegexOptions.IgnoreCase | RegexOptions.Singleline).Groups["text"].Value;
        if (s.Length > 50)
            return s.Substring(0, 50);
         
        return s;
    }

    /**
    * Does this file contain binary data? If not, it must be text data.
    */
    public boolean getIsBinary() throws Exception {
        return _IsBinary;
    }

    /**
    * Is this file CSS content?
    */
    public boolean getIsCss() throws Exception {
        return Regex.IsMatch(_ContentType, "text/css", RegexOptions.IgnoreCase);
    }

    /**
    * Is this file HTML content?
    */
    public boolean getIsHtml() throws Exception {
        return Regex.IsMatch(_ContentType, "text/html", RegexOptions.IgnoreCase);
    }

    /**
    * If this file is text (eg, it isn't binary), the type of text encoding used
    */
    public Encoding getTextEncoding() throws Exception {
        return _TextEncoding;
    }

    /**
    * The URL target for this file
    */
    public String getUrl() throws Exception {
        return this._Url;
    }

    public void setUrl(String value) throws Exception {
        _UrlUnmodified = value;
        setUrl(value,true);
        _DownloadedBytes = new byte[1];
        _ExternalFileCollection = null;
        _DownloadException = null;
        _TextEncoding = null;
        _ContentType = "";
        _ContentLocation = "";
        _IsBinary = false;
        _WasDownloaded = false;
    }

    /**
    * The Content-Location of this URL as provided by the server,
    * only if the URL was not fully qualified;
    * eg, http://mywebsite.com/ actually maps to http://mywebsite.com/default.htm
    */
    public String getUrlContentLocation() throws Exception {
        return _ContentLocation;
    }

    /**
    * The root and folder of the URL, eg, http://mywebsite.com/myfolder
    */
    public String getUrlFolder() throws Exception {
        return this._UrlFolder;
    }

    /**
    * The root of the URL, eg, http://mywebsite.com/
    */
    public String getUrlRoot() throws Exception {
        return this._UrlRoot;
    }

    /**
    * The unmodified "raw" URL as originally provided
    */
    public String getUrlUnmodified() throws Exception {
        return _UrlUnmodified;
    }

    /**
    * If enabled, will use the first 50 characters of the TITLE tag
    * to form the filename when saved to disk
    */
    public boolean getUseHtmlTitleAsFilename() throws Exception {
        return this._UseHtmlFilename;
    }

    public void setUseHtmlTitleAsFilename(boolean value) throws Exception {
        this._UseHtmlFilename = value;
    }

    /**
    * Was this file successfully downloaded via HTTP?
    */
    public boolean getWasDownloaded() throws Exception {
        return _WasDownloaded;
    }

    /**
    * converts all external Html files (gif, jpg, css, etc) to local refs
    * external ref:
    * <img src="http://mywebsite/myfolder/myimage.gif">
    * into local refs:
    * <img src="mypage_files/myimage.gif">
    */
    public void convertReferencesToLocal() throws Exception {
        if (!this.getIsHtml() && !this.getIsCss())
            throw new Exception("Converting references only makes sense for HTML or CSS files; this file is of type '" + this.getContentType() + "'");
         
        // get a list of all external references
        String html = this.toString();
        NameValueCollection fileCollection = this.externalHtmlFiles();
        // no external refs? nothing to do
        if (fileCollection.Count == 0)
            return ;
         
        String[] keys = fileCollection.AllKeys;
        for (int idx = 0;idx < keys.Length;idx++)
        {
            String delimitedUrl = keys[idx];
            String fileUrl = fileCollection[delimitedUrl];
            if (_Builder.WebFiles.Contains(fileUrl))
            {
                MhtWebFile wf = (MhtWebFile)_Builder.WebFiles[fileUrl];
                String newPath = this.getExternalFilesFolder() + "/" + wf.getDownloadFilename();
                String delimitedReplacement = Regex.Replace(delimitedUrl, "^(?<StartDelim>\"|\'|\\()*(?<Value>[^\'\")]*)(?<EndDelim>\"|\'|\\))*$", "${StartDelim}" + newPath + "${EndDelim}");
                // correct original Url references in Html so they point to our local files
                html = html.Replace(delimitedUrl, delimitedReplacement);
            }
             
        }
        _DownloadedBytes = _TextEncoding.GetBytes(html);
    }

    /**
    * Download this file from the target URL
    */
    public void download() throws Exception {
        Debug.Write("Downloading " + this._Url + "  ..");
        downloadBytes();
        if (_DownloadException == null)
            Debug.WriteLine("OK");
        else
        {
            Debug.WriteLine("failed: ", "Error");
            Debug.WriteLine("    " + _DownloadException.Message, "Error");
            return ;
        } 
        if (this.getIsHtml())
            _DownloadedBytes = _TextEncoding.GetBytes(processHtml(this.toString()));
         
        if (this.getIsCss())
            _DownloadedBytes = _TextEncoding.GetBytes(processHtml(this.toString()));
         
    }

    /**
    * download ALL externally referenced files in this file's html, not recursively,
    * to the default download path for this page
    */
    public void downloadExternalFiles() throws Exception {
        this.downloadExternalFiles(this.getExternalFilesFolder(),false);
    }

    /**
    * download ALL externally referenced files in this file's html, potentially recursively,
    * to the default download path for this page
    */
    public void downloadExternalFiles(boolean recursive) throws Exception {
        this.downloadExternalFiles(this.getExternalFilesFolder(),recursive);
    }

    /**
    * Saves this file to disk as a plain text file
    */
    public void saveAsTextFile() throws Exception {
        this.SaveToFile(Path.ChangeExtension(this.getDownloadPath(), ".txt"), true);
    }

    /**
    * Saves this file to disk as a plain text file, to an arbitrary path
    */
    public void saveAsTextFile(String filePath) throws Exception {
        this.saveToFile(filePath,true);
    }

    /**
    * writes contents of file to DownloadPath, using appropriate encoding as necessary
    */
    public void saveToFile() throws Exception {
        this.saveToFile(this.getDownloadPath(),false);
    }

    /**
    * writes contents of file to DownloadPath, using appropriate encoding as necessary
    */
    public void saveToFile(String filePath) throws Exception {
        this.saveToFile(filePath,false);
    }

    /**
    * Returns a string representation of the data downloaded for this file
    */
    public String toString() {
        try
        {
            if (!_WasDownloaded)
                download();
             
            if (!_WasDownloaded || _DownloadedBytes.Length <= 0)
                return "";
             
            if (_IsBinary)
                return ("[" + _DownloadedBytes.Length.ToString() + " bytes of binary data]");
             
            return this.getTextEncoding().GetString(_DownloadedBytes);
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

    /**
    * Returns the plain text representation of the data in this file,
    * stripping out any HTML tags and codes
    */
    public String toTextString(boolean removeWhitespace) throws Exception {
        /* = false */
        String html = this.toString();
        // get rid of <script> .. </script>
        html = this.stripHtmlTag("script",html);
        // get rid of <style> .. </style>
        html = this.stripHtmlTag("style",html);
        // get rid of all HTML tags
        html = Regex.Replace(html, "<\\w+(\\s+[A-Za-z0-9_\\-]+\\s*=\\s*(\"([^\"]*)\"|\'([^\']*)\'))*\\s*(/)*>|<[^>]+>", " ");
        // convert escaped HTML to plaintext
        html = htmlDecode(html);
        if (removeWhitespace)
        {
            // clean up whitespace (optional, depends what you want..)
            html = Regex.Replace(html, "[\\n\\r\\f\\t]", " ", RegexOptions.Multiline);
            html = Regex.Replace(html, " {2,}", " ", RegexOptions.Multiline);
        }
         
        return html;
    }

    /**
    * appends key=value named matches in a regular expression
    * to a target NameValueCollection
    */
    void addMatchesToCollection(String s, Regex r, RefSupport<NameValueCollection> nvc) throws Exception {
        boolean headerDisplayed = false;
        //			Regex urlRegex = new Regex(@"^https*://\w+", RegexOptions.IgnoreCase);
        Regex urlRegex = new Regex("^files*:///\\w+", RegexOptions.IgnoreCase);
        for (Object __dummyForeachVar0 : r.Matches(s))
        {
            Match match = (Match)__dummyForeachVar0;
            if (!headerDisplayed)
            {
                Debug.WriteLine("Matches added from regex:");
                Debug.WriteLine("'" + match.ToString() + "'");
                headerDisplayed = true;
            }
             
            String key = match.Groups["Key"].ToString();
            String val = match.Groups["Value"].ToString();
            if (nvc.getValue()[key] == null)
            {
                Debug.WriteLine(" Match: " + match.ToString());
                Debug.WriteLine("   Key: " + key);
                Debug.WriteLine(" Value: " + val);
                if (urlRegex.IsMatch(val))
                    nvc.getValue().Add(key, val);
                else
                    Debug.WriteLine("Match discarded; does not appear to be valid fully qualified file:// Url", "Error"); 
            }
             
        }
    }

    //						Debug.WriteLine("Match discarded; does not appear to be valid fully qualified http:// Url", "Error");
    /**
    * converts all relative url references
    * href="myfolder/mypage.htm"
    * into absolute url references
    * href="http://mywebsite/myfolder/mypage.htm"
    */
    String convertRelativeToAbsoluteRefs(String html) throws Exception {
        String urlPattern = "(?<attrib>\\shref|\\ssrc|\\sbackground)\\s*?=\\s*?" + "(?<delim1>[\"\'\\\\]{0,2})(?!\\s*\\+|#|http:|ftp:|mailto:|javascript:)" + "/(?<url>[^\"\'>\\\\]+)(?<delim2>[\"\'\\\\]{0,2})";
        String cssPattern = "(?<attrib>@import\\s|\\S+-image:|background:)\\s*?(url)*[\'\"(]{1,2}" + "(?!http)\\s*/(?<url>[^\"\')]+)[\'\")]{1,2}";
        // href="/anything" to href="http://www.web.com/anything"
        Regex r = new Regex(urlPattern, RegexOptions.Multiline | RegexOptions.IgnoreCase);
        html = r.Replace(html, "${attrib}=${delim1}" + this._UrlRoot + "/${url}${delim2}");
        // href="anything" to href="http://www.web.com/folder/anything"
        r = new Regex(urlPattern.Replace("/", ""), RegexOptions.Multiline | RegexOptions.IgnoreCase);
        html = r.Replace(html, "${attrib}=${delim1}" + this._UrlFolder + "/${url}${delim2}");
        // @import(/anything) to @import url(http://www.web.com/anything)
        r = new Regex(cssPattern, RegexOptions.Multiline | RegexOptions.IgnoreCase);
        html = r.Replace(html, "${attrib} url(" + this._UrlRoot + "/${url})");
        // @import(anything) to @import url(http://www.web.com/folder/anything)
        r = new Regex(cssPattern.Replace("/", ""), RegexOptions.Multiline | RegexOptions.IgnoreCase);
        html = r.Replace(html, "${attrib} url(" + this._UrlFolder + "/${url})");
        return html;
    }

    /**
    * if the user passed in a directory, form the filename automatically using the Html title tag
    * if the user passed in a filename, make sure the extension matches our desired extension
    */
    String deriveFilename(String FilePath, String html, String fileExtension) throws Exception {
        if (isDirectory(FilePath))
        {
            String htmlTitle = this.getHtmlTitle();
            if (StringSupport.equals(htmlTitle, ""))
                throw new Exception("No filename was provided, and the HTML title tag was not found, so a filename could not be automatically generated. You'll need to provide a filename and not a folder.");
             
            return Path.Combine(Path.GetDirectoryName(FilePath), makeValidFilename(htmlTitle,false) + fileExtension);
        }
         
        if (!StringSupport.equals(Path.GetExtension(FilePath), fileExtension))
            return Path.ChangeExtension(FilePath, fileExtension);
         
        return FilePath;
    }

    /**
    * download this file from the target URL;
    * place the bytes downloaded in _DownloadedBytes
    * if an exception occurs, capture it in _DownloadException
    */
    void downloadBytes() throws Exception {
        if (this.getWasDownloaded())
            return ;
         
        try
        {
            // always download to memory first
            _DownloadedBytes = _Builder.WebClient.downloadBytes(_Url);
            _WasDownloaded = true;
        }
        catch (WebException ex)
        {
            _DownloadException = ex;
            _Builder.WebClient.clearDownload();
        }

        // necessary if the original client URL was imprecise;
        // server location is always authoritatitve
        if (!StringSupport.equals(_Builder.WebClient.getContentLocation(), ""))
        {
            _ContentLocation = _Builder.WebClient.getContentLocation();
            setUrl(_ContentLocation,false);
        }
         
        _IsBinary = _Builder.WebClient.getResponseIsBinary();
        _ContentType = _Builder.WebClient.getResponseContentType();
        _TextEncoding = _Builder.WebClient.getDetectedEncoding();
        _Builder.WebClient.clearDownload();
    }

    /**
    * Download a single externally referenced file (if we haven't already downloaded it)
    */
    void downloadExternalFile(String url, String targetFolder, boolean recursive) throws Exception {
        boolean isNew = new boolean();
        MhtWebFile wf;
        // have we already downloaded (or attempted to) this file?
        if (_Builder.WebFiles.Contains(url) || StringSupport.equals(_Builder.getUrl(), url))
        {
            wf = (MhtWebFile)_Builder.WebFiles[url];
            isNew = false;
        }
        else
        {
            wf = new MhtWebFile(_Builder,url);
            isNew = true;
        } 
        wf.download();
        if (isNew)
        {
            // add this (possibly) downloaded file to our shared collection
            _Builder.WebFiles.Add(wf.getUrlUnmodified(), wf);
            // if this is an HTML file, it has dependencies of its own;
            // download them into a subfolder
            if ((wf.getIsHtml() || wf.getIsCss()) && recursive)
                wf.downloadExternalFiles(recursive);
             
        }
         
    }

    /**
    * download ALL externally referenced files in this html, potentially recursively
    * to a specific download path
    */
    void downloadExternalFiles(String targetFolder, boolean recursive) throws Exception {
        NameValueCollection fileCollection = externalHtmlFiles();
        if (!fileCollection.HasKeys())
            return ;
         
        Debug.WriteLine("Downloading all external files collected from URL:");
        Debug.WriteLine("    " + this.getUrl());
        for (Object __dummyForeachVar1 : fileCollection.Keys)
        {
            String key = (String)__dummyForeachVar1;
            DownloadExternalFile(fileCollection[key], targetFolder, recursive);
        }
    }

    /**
    * if we weren't given a filename extension, infer it from the download
    * Content-Type header
    * 
    * http://www.utoronto.ca/webdocs/HTMLdocs/Book/Book-3ed/appb/mimetype.html
    */
    String extensionFromContentType() throws Exception {
        Value.APPLY __dummyScrutVar0 = Regex.Match(this.getContentType(), "^[^ ;]+").Value.ToLower();
        if (__dummyScrutVar0.equals("text/html"))
        {
            return ".htm";
        }
        else if (__dummyScrutVar0.equals("image/gif"))
        {
            return ".gif";
        }
        else if (__dummyScrutVar0.equals("image/jpeg"))
        {
            return ".jpg";
        }
        else if (__dummyScrutVar0.equals("text/javascript") || __dummyScrutVar0.equals("application/x-javascript"))
        {
            return ".js";
        }
        else if (__dummyScrutVar0.equals("image/x-png"))
        {
            return ".png";
        }
        else if (__dummyScrutVar0.equals("text/css"))
        {
            return ".css";
        }
        else if (__dummyScrutVar0.equals("text/plain"))
        {
            return ".txt";
        }
        else
        {
            Debug.WriteLine("Unknown content-type '" + this.getContentType() + "'", "Error");
            return ".htm";
        }       
    }

    /**
    * returns a name/value collection of all external files referenced in HTML:
    * 
    * "/myfolder/blah.png"
    * 'http://mywebsite/blah.gif'
    * src=blah.jpg
    * 
    * note that the Key includes the delimiting quotes or parens (if present), but the Value does not
    * this is important because the delimiters are used for matching and replacement to make the
    * match more specific!
    */
    NameValueCollection externalHtmlFiles() throws Exception {
        // avoid doing this work twice, however, be careful that the HTML hasn't
        // changed since the last time we called this function
        if (_ExternalFileCollection != null)
            return _ExternalFileCollection;
         
        _ExternalFileCollection = new NameValueCollection();
        String html = this.toString();
        Debug.WriteLine("Resolving all external HTML references from URL:");
        Debug.WriteLine("    " + this.getUrl());
        // src='filename.ext' ; background="filename.ext"
        // note that we have to test 3 times to catch all quote styles: '', "", and none
        Regex r = new Regex("(\\ssrc|\\sbackground)\\s*=\\s*((?<Key>\'(?<Value>[^\']+)\')|(?<Key>\"(?<Value>[^\"]+)\")|(?<Key>(?<Value>[^ \\n\\r\\f]+)))", RegexOptions.Multiline | RegexOptions.IgnoreCase);
        RefSupport refVar___0 = new RefSupport(_ExternalFileCollection);
        addMatchesToCollection(html,r,refVar___0);
        _ExternalFileCollection = refVar___0.getValue();
        // @import "style.css" or @import url(style.css)
        r = new Regex("(@import\\s|\\S+-image:|background:)\\s*?(url)*\\s*?(?<Key>[\"\'(]{1,2}(?<Value>[^\"\')]+)[\"\')]{1,2})", RegexOptions.Multiline | RegexOptions.IgnoreCase);
        RefSupport refVar___1 = new RefSupport(_ExternalFileCollection);
        addMatchesToCollection(html,r,refVar___1);
        _ExternalFileCollection = refVar___1.getValue();
        // <link rel=stylesheet href="style.css">
        r = new Regex("<link[^>]+?href\\s*=\\s*(?<Key>(\'|\")*(?<Value>[^\'\">]+)(\'|\")*)", RegexOptions.Multiline | RegexOptions.IgnoreCase);
        RefSupport refVar___2 = new RefSupport(_ExternalFileCollection);
        addMatchesToCollection(html,r,refVar___2);
        _ExternalFileCollection = refVar___2.getValue();
        // <iframe src="mypage.htm"> or <frame src="mypage.aspx">
        r = new Regex("<i*frame[^>]+?src\\s*=\\s*(?<Key>[\'\"]{0,1}(?<Value>[^\'\"\\\\>]+)[\'\"]{0,1})", RegexOptions.Multiline | RegexOptions.IgnoreCase);
        RefSupport refVar___3 = new RefSupport(_ExternalFileCollection);
        addMatchesToCollection(html,r,refVar___3);
        _ExternalFileCollection = refVar___3.getValue();
        return _ExternalFileCollection;
    }

    /**
    * attempt to get a coherent filename out of the Url
    */
    String filenameFromUrl() throws Exception {
        // first, try to get a filename out of the URL itself;
        // this means anything past the final slash that doesn't include another slash
        // or a question mark, eg http://mywebsite/myfolder/crazy?param=1&param=2
        String filename = Regex.Match(this._Url, "/(?<Filename>[^/?]+)[^/]*$").Groups["Filename"].Value;
        if (!StringSupport.equals(filename, ""))
        {
            // that worked, but we need to make sure the filename is unique
            // if query params were passed to the URL file
            Uri u = new Uri(this._Url);
            if (!StringSupport.equals(u.Query, ""))
                filename = Path.GetFileNameWithoutExtension(filename) + "_" + u.Query.GetHashCode().ToString() + this.getDownloadExtension();
             
        }
         
        // ok, that didn't work; if this file is HTML try to get the TITLE tag
        if (StringSupport.equals(filename, "") && this.getIsHtml())
        {
            filename = this.getHtmlTitle();
            if (!StringSupport.equals(filename, ""))
                filename = filename + ".htm";
             
        }
         
        // now we're really desperate. Hash the URL and make that the filename.
        if (StringSupport.equals(filename, ""))
            filename = _Url.GetHashCode().ToString() + this.getDownloadExtension();
         
        return this.makeValidFilename(filename,false);
    }

    /**
    * returns true if this path refers to a directory (vs. a filename)
    */
    boolean isDirectory(String FilePath) throws Exception {
        return FilePath.EndsWith("\\");
    }

    /**
    * removes all unsafe filesystem characters to form a valid filesystem filename
    */
    String makeValidFilename(String s, boolean enforceLength) throws Exception {
        /* = false */
        if (enforceLength)
        {
        }
         
        return Regex.Replace(Regex.Replace(s, "[\\/\\\\\\:\\*\\?\\\"\"\\<\\>\\|]|^\\s+|\\s+$", ""), "\\s{2,}", " ");
    }

    // replace any invalid filesystem chars, plus leading/trailing/doublespaces
    /**
    * Pre-process the CSS using global preference settings
    */
    String processCss(String css) throws Exception {
        return this.convertRelativeToAbsoluteRefs(css);
    }

    /**
    * Pre-process the HTML using global preference settings
    */
    String processHtml(String html) throws Exception {
        Debug.WriteLine("Downloaded content was HTML/CSS -- processing: resolving URLs, getting <base>, etc");
        if (_Builder.getAddWebMark())
        {
            // add "mark of the web":
            // http://www.microsoft.com/technet/prodtechnol/winxppro/maintain/sp2brows.mspx#XSLTsection133121120120
            html = "<!-- saved from url=(" + String.Format("{0:0000}", this._Url.Length) + ")" + this._Url + " -->" + Environment.NewLine + html;
        }
         
        // see if we need to strip elements from the HTML
        if (_Builder.getStripScripts())
            html = this.stripHtmlTag("script",html);
         
        if (_Builder.getStripIframes())
            html = this.stripHtmlTag("iframe",html);
         
        // if we have a <base>, we must use it as the _UrlFolder,
        // not what was parsed from the original _Url
        String baseUrlFolder = Regex.Match(html, "<base[^>]+?href=['\"]{0,1}(?<BaseUrl>[^'\">]+)['\"]{0,1}", RegexOptions.IgnoreCase).Groups["BaseUrl"].Value;
        if (!StringSupport.equals(baseUrlFolder, ""))
        {
            if (baseUrlFolder.EndsWith("/"))
                _UrlFolder = baseUrlFolder.Substring(0, baseUrlFolder.Length - 1);
            else
                _UrlFolder = baseUrlFolder; 
        }
         
        // remove the <base href=''> tag if present; causes problems when viewing locally.
        html = Regex.Replace(html, "<base[^>]*?>", "");
        return this.convertRelativeToAbsoluteRefs(html);
    }

    // relative URLs are a PITA for the processing we're about to do,
    // so convert them all to absolute up front
    /**
    * fully resolves any relative pathing inside the URL, and other URL oddities
    */
    String resolveUrl(String url) throws Exception {
        try
        {
            // resolve any relative pathing
            url = (new Uri(url)).AbsoluteUri;
        }
        catch (UriFormatException ex)
        {
            throw new ArgumentException("'" + url + "' does not appear to be a valid URL.", ex);
        }

        // remove any anchor tags from the end of URLs
        if (url.IndexOf("#") > -1)
        {
            String jump = Regex.Match(url, "/[^/]*?(?<jump>#[^/?.]+$)").Groups["jump"].Value;
            if (!StringSupport.equals(jump, ""))
                url = url.Replace(jump, "");
             
        }
         
        return url;
    }

    /**
    * sets the DownloadPath and writes contents of file, using appropriate encoding as necessary
    */
    void saveToFile(String filePath, boolean asText) throws Exception {
        Debug.WriteLine("Saving to file " + filePath);
        FileStream fs = new FileStream(filePath, FileMode.OpenOrCreate);
        try
        {
            {
                BinaryWriter writer = new BinaryWriter(fs);
                try
                {
                    {
                        if (this.getIsBinary())
                            writer.Write(_DownloadedBytes);
                        else if (asText)
                            writer.Write(this.toTextString(false));
                        else
                            writer.Write(_DownloadedBytes);  
                    }
                }
                finally
                {
                    if (writer != null)
                        Disposable.mkDisposable(writer).dispose();
                     
                }
            }
        }
        finally
        {
            if (fs != null)
                Disposable.mkDisposable(fs).dispose();
             
        }
    }

    void setUrl(String url, boolean validate) throws Exception {
        if (validate)
            this._Url = this.resolveUrl(url);
        else
            this._Url = url; 
        // http://mywebsite
        this._UrlRoot = Regex.Match(url, "http://[^/'\"]+", RegexOptions.IgnoreCase).ToString();
        // http://mywebsite/myfolder
        if (this._Url.LastIndexOf("/") > 7)
            this._UrlFolder = this._Url.Substring(0, this._Url.LastIndexOf("/"));
        else
            this._UrlFolder = this._UrlRoot; 
    }

    /**
    * perform the regex replacement of all <tagName> .. </tagName> blocks
    */
    String stripHtmlTag(String tagName, String html) throws Exception {
        Regex r = new Regex(String.Format("<{0}[^>]*?>[\\w|\\t|\\r|\\W]*?</{0}>", tagName), RegexOptions.Multiline | RegexOptions.IgnoreCase);
        return r.Replace(html, "");
    }

    String htmlDecode(String s) throws Exception {
        if (s == null)
            return null;
         
        if (s.IndexOf('&') < 0)
            return s;
         
        StringBuilder builder = new StringBuilder();
        StringWriter writer = new StringWriter(builder);
        for (int i = 0;i < s.Length;i++)
        {
            char currentChar = s[i];
            if (currentChar != '&')
            {
                writer.Write(currentChar);
                continue;
            }
             
            int pos = s.IndexOf(';', i + 1);
            if (pos <= 0)
            {
                writer.Write(currentChar);
                continue;
            }
             
            String subText = s.Substring(i + 1, (pos - i) - 1);
            if (subText[0] == '#' && subText.Length > 1)
            {
                try
                {
                    if ((subText[1] == 'x') || (subText[1] == 'X'))
                        writer.Write((char)((ushort)int.Parse(subText.Substring(2), System.Globalization.NumberStyles.AllowHexSpecifier)));
                    else
                        writer.Write((char)((ushort)int.Parse(subText.Substring(1)))); 
                    i = pos;
                }
                catch (FormatException __dummyCatchVar1)
                {
                    i++;
                }
                catch (ArgumentException __dummyCatchVar2)
                {
                    i++;
                }
            
            }
            else
            {
                i = pos;
                currentChar = htmlLookup(subText);
                if (currentChar != '\0')
                {
                    writer.Write(currentChar);
                }
                else
                {
                    writer.Write('&');
                    writer.Write(subText);
                    writer.Write(';');
                } 
            } 
        }
        return builder.ToString();
    }

    static Hashtable htmlEntitiesTable = null;
    char htmlLookup(String entity) throws Exception {
        if (htmlEntitiesTable == null)
        {
            synchronized (MhtWebFile.class)
            {
                {
                    if (htmlEntitiesTable == null)
                    {
                        htmlEntitiesTable = new Hashtable();
                        String[] htmlEntities = new String[]{ "\"-quot", "&-amp", "<-lt", ">-gt", "\x00a0-nbsp", "\x00a1-iexcl", "\x00a2-cent", "\x00a3-pound", "\x00a4-curren", "\x00a5-yen", "\x00a6-brvbar", "\x00a7-sect", "\x00a8-uml", "\x00a9-copy", "\x00aa-ordf", "\x00ab-laquo", "\x00ac-not", "\x00ad-shy", "\x00ae-reg", "\x00af-macr", "\x00b0-deg", "\x00b1-plusmn", "\x00b2-sup2", "\x00b3-sup3", "\x00b4-acute", "\x00b5-micro", "\x00b6-para", "\x00b7-middot", "\x00b8-cedil", "\x00b9-sup1", "\x00ba-ordm", "\x00bb-raquo", "\x00bc-frac14", "\x00bd-frac12", "\x00be-frac34", "\x00bf-iquest", "\x00c0-Agrave", "\x00c1-Aacute", "\x00c2-Acirc", "\x00c3-Atilde", "\x00c4-Auml", "\x00c5-Aring", "\x00c6-AElig", "\x00c7-Ccedil", "\x00c8-Egrave", "\x00c9-Eacute", "\x00ca-Ecirc", "\x00cb-Euml", "\x00cc-Igrave", "\x00cd-Iacute", "\x00ce-Icirc", "\x00cf-Iuml", "\x00d0-ETH", "\x00d1-Ntilde", "\x00d2-Ograve", "\x00d3-Oacute", "\x00d4-Ocirc", "\x00d5-Otilde", "\x00d6-Ouml", "\x00d7-times", "\x00d8-Oslash", "\x00d9-Ugrave", "\x00da-Uacute", "\x00db-Ucirc", "\x00dc-Uuml", "\x00dd-Yacute", "\x00de-THORN", "\x00df-szlig", "\x00e0-agrave", "\x00e1-aacute", "\x00e2-acirc", "\x00e3-atilde", "\x00e4-auml", "\x00e5-aring", "\x00e6-aelig", "\x00e7-ccedil", "\x00e8-egrave", "\x00e9-eacute", "\x00ea-ecirc", "\x00eb-euml", "\x00ec-igrave", "\x00ed-iacute", "\x00ee-icirc", "\x00ef-iuml", "\x00f0-eth", "\x00f1-ntilde", "\x00f2-ograve", "\x00f3-oacute", "\x00f4-ocirc", "\x00f5-otilde", "\x00f6-ouml", "\x00f7-divide", "\x00f8-oslash", "\x00f9-ugrave", "\x00fa-uacute", "\x00fb-ucirc", "\x00fc-uuml", "\x00fd-yacute", "\x00fe-thorn", "\x00ff-yuml", "\u0152-OElig", "\u0153-oelig", "\u0160-Scaron", "\u0161-scaron", "\u0178-Yuml", "\u0192-fnof", "\u02c6-circ", "\u02dc-tilde", "\u0391-Alpha", "\u0392-Beta", "\u0393-Gamma", "\u0394-Delta", "\u0395-Epsilon", "\u0396-Zeta", "\u0397-Eta", "\u0398-Theta", "\u0399-Iota", "\u039a-Kappa", "\u039b-Lambda", "\u039c-Mu", "\u039d-Nu", "\u039e-Xi", "\u039f-Omicron", "\u03a0-Pi", "\u03a1-Rho", "\u03a3-Sigma", "\u03a4-Tau", "\u03a5-Upsilon", "\u03a6-Phi", "\u03a7-Chi", "\u03a8-Psi", "\u03a9-Omega", "\u03b1-alpha", "\u03b2-beta", "\u03b3-gamma", "\u03b4-delta", "\u03b5-epsilon", "\u03b6-zeta", "\u03b7-eta", "\u03b8-theta", "\u03b9-iota", "\u03ba-kappa", "\u03bb-lambda", "\u03bc-mu", "\u03bd-nu", "\u03be-xi", "\u03bf-omicron", "\u03c0-pi", "\u03c1-rho", "\u03c2-sigmaf", "\u03c3-sigma", "\u03c4-tau", "\u03c5-upsilon", "\u03c6-phi", "\u03c7-chi", "\u03c8-psi", "\u03c9-omega", "\u03d1-thetasym", "\u03d2-upsih", "\u03d6-piv", "\u2002-ensp", "\u2003-emsp", "\u2009-thinsp", "\u200c-zwnj", "\u200d-zwj", "\u200e-lrm", "\u200f-rlm", "\u2013-ndash", "\u2014-mdash", "\u2018-lsquo", "\u2019-rsquo", "\u201a-sbquo", "\u201c-ldquo", "\u201d-rdquo", "\u201e-bdquo", "\u2020-dagger", "\u2021-Dagger", "\u2022-bull", "\u2026-hellip", "\u2030-permil", "\u2032-prime", "\u2033-Prime", "\u2039-lsaquo", "\u203a-rsaquo", "\u203e-oline", "\u2044-frasl", "\u20ac-euro", "\u2111-image", "\u2118-weierp", "\u211c-real", "\u2122-trade", "\u2135-alefsym", "\u2190-larr", "\u2191-uarr", "\u2192-rarr", "\u2193-darr", "\u2194-harr", "\u21b5-crarr", "\u21d0-lArr", "\u21d1-uArr", "\u21d2-rArr", "\u21d3-dArr", "\u21d4-hArr", "\u2200-forall", "\u2202-part", "\u2203-exist", "\u2205-empty", "\u2207-nabla", "\u2208-isin", "\u2209-notin", "\u220b-ni", "\u220f-prod", "\u2211-sum", "\u2212-minus", "\u2217-lowast", "\u221a-radic", "\u221d-prop", "\u221e-infin", "\u2220-ang", "\u2227-and", "\u2228-or", "\u2229-cap", "\u222a-cup", "\u222b-int", "\u2234-there4", "\u223c-sim", "\u2245-cong", "\u2248-asymp", "\u2260-ne", "\u2261-equiv", "\u2264-le", "\u2265-ge", "\u2282-sub", "\u2283-sup", "\u2284-nsub", "\u2286-sube", "\u2287-supe", "\u2295-oplus", "\u2297-otimes", "\u22a5-perp", "\u22c5-sdot", "\u2308-lceil", "\u2309-rceil", "\u230a-lfloor", "\u230b-rfloor", "\u2329-lang", "\u232a-rang", "\u25ca-loz", "\u2660-spades", "\u2663-clubs", "\u2665-hearts", "\u2666-diams" };
                        for (int i = 0;i < htmlEntities.Length;i++)
                        {
                            String current = htmlEntities[i];
                            htmlEntitiesTable[current.Substring(2)] = current[0];
                        }
                    }
                     
                }
            }
        }
         
        Object oChar = htmlEntitiesTable[entity];
        if (oChar != null)
            return (Character)oChar;
         
        return '\0';
    }

}


