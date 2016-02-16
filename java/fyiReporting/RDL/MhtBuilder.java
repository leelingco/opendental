//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:31 PM
//

package fyiReporting.RDL;

import CS2JNet.System.StringSupport;
import fyiReporting.RDL.MhtWebClientLocal;
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
* This class builds the following from a URL:
* 
* .mht file (Web Archive, single file)
* .htm file with dereferenced (absolute) references (Web Page, HTML Only)
* .htm file plus all referenced files in a local subfolder (Web Page, complete)
* .txt file (non-HTML contents of Web Page)
* 
* The .mht format is based on RFC2557
* "compliant Multipart MIME Message (mhtml web archive)"
* http://www.ietf.org/rfc/rfc2557.txt
* 
* Jeff Atwood
* http://www.codinghorror.com/
*/
public class MhtBuilder   
{
    StringBuilder _MhtBuilder = new StringBuilder();
    boolean _StripScriptFromHtml = false;
    boolean _StripIframeFromHtml = false;
    boolean _AllowRecursion = true;
    boolean _AddWebMark = true;
    Encoding _ForcedEncoding = null;
    MhtWebFile _HtmlFile;
    public MhtWebClientLocal WebClient = new MhtWebClientLocal();
    public SortedList WebFiles = new SortedList();
    static final String _MimeFinalBoundaryTag = "----=_NextPart_000_00";
    // note that chunk size is equal to maximum line width (expanded = 75 chars)
    static final int _ChunkSize = 57;
    public enum FileStorage
    {
        Memory,
        DiskPermanent,
        DiskTemporary
    }
    public MhtBuilder() throws Exception {
        _HtmlFile = new MhtWebFile(this);
    }

    /**
    * Add the "Mark of the web" to retrieved HTML content so it can run
    * locally on Windows XP SP2
    * 
    * http://www.microsoft.com/technet/prodtechnol/winxppro/maintain/sp2brows.mspx#XSLTsection133121120120
    */
    public boolean getAddWebMark() throws Exception {
        return this._AddWebMark;
    }

    public void setAddWebMark(boolean value) throws Exception {
        this._AddWebMark = value;
    }

    /**
    * allow recursive retrieval of any embedded HTML (typically IFRAME or FRAME)
    * turn off to prevent infinite recursion in the case of pages that reference themselves..
    */
    public boolean getAllowRecursiveFileRetrieval() throws Exception {
        return _AllowRecursion;
    }

    public void setAllowRecursiveFileRetrieval(boolean value) throws Exception {
        _AllowRecursion = value;
    }

    /**
    * returns the Mime content-type string designation of a mht file
    */
    public String getMhtContentType() throws Exception {
        return "message/rfc822";
    }

    /**
    * Strip all <IFRAME> blocks from any retrieved HTML
    */
    public boolean getStripIframes() throws Exception {
        return this._StripIframeFromHtml;
    }

    public void setStripIframes(boolean value) throws Exception {
        this._StripIframeFromHtml = value;
    }

    /**
    * Strip all <SCRIPT> blocks from any retrieved HTML
    */
    public boolean getStripScripts() throws Exception {
        return this._StripScriptFromHtml;
    }

    public void setStripScripts(boolean value) throws Exception {
        this._StripScriptFromHtml = value;
    }

    /**
    * *only* set this if you want to FORCE a specific text encoding for all the HTML pages you're downloading;
    * otherwise the text encoding is autodetected, which is generally what you want
    */
    public Encoding getTextEncoding() throws Exception {
        return this._ForcedEncoding;
    }

    public void setTextEncoding(Encoding value) throws Exception {
        this._ForcedEncoding = value;
    }

    /**
    * Specifies the target Url we want to save
    */
    public String getUrl() throws Exception {
        return _HtmlFile.getUrl();
    }

    public void setUrl(String value) throws Exception {
        WebFiles.Clear();
        _HtmlFile.setUrl(value);
    }

    /**
    * Appends a downloaded external binary file to our MhtBuilder using Base64 encoding
    */
    void appendMhtBinaryFile(MhtWebFile ef) throws Exception {
        appendMhtBoundary();
        appendMhtLine("Content-Type: " + ef.getContentType());
        appendMhtLine("Content-Transfer-Encoding: base64");
        appendMhtLine("Content-Location: " + ef.getUrl());
        appendMhtLine();
        // note that chunk size is equal to maximum line width (expanded = 75 chars)
        int len = ef.getDownloadedBytes().Length;
        if (len <= _ChunkSize)
            AppendMhtLine(Convert.ToBase64String(ef.getDownloadedBytes(), 0, len));
        else
        {
            int i = 0;
            while ((i + _ChunkSize) < len)
            {
                AppendMhtLine(Convert.ToBase64String(ef.getDownloadedBytes(), i, _ChunkSize));
                i += _ChunkSize;
            }
            if (i != len)
                AppendMhtLine(Convert.ToBase64String(ef.getDownloadedBytes(), i, len - i));
             
        } 
    }

    /**
    * appends a boundary marker to our MhtBuilder
    */
    void appendMhtBoundary() throws Exception {
        appendMhtLine();
        appendMhtLine("--" + _MimeFinalBoundaryTag);
    }

    /**
    * appends a boundary marker to our MhtBuilder
    */
    void appendMhtFinalBoundary() throws Exception {
        appendMhtLine();
        appendMhtLine("--" + _MimeFinalBoundaryTag + "--");
    }

    /**
    * Appends a downloaded external file to our MhtBuilder
    */
    void appendMhtFile(MhtWebFile ef) throws Exception {
        if (ef.getWasDownloaded() && !ef.WasAppended)
        {
            if (ef.getIsBinary())
                appendMhtBinaryFile(ef);
            else
                appendMhtTextFile(ef); 
        }
         
        ef.WasAppended = true;
    }

    /**
    * appends all downloaded files (from _ExternalFiles) to our MhtBuilder
    * 
    *  @param st type of storage to use when downloading external files
    *  @param storagePath path to use for downloaded external files
    */
    void appendMhtFiles() throws Exception {
        for (Object __dummyForeachVar0 : WebFiles.Values)
        {
            MhtWebFile ef = (MhtWebFile)__dummyForeachVar0;
            appendMhtFile(ef);
        }
        appendMhtFinalBoundary();
    }

    /**
    * appends the Mht header, which includes the root HTML
    */
    void appendMhtHeader(MhtWebFile ef) throws Exception {
        // clear the stringbuilder contents
        _MhtBuilder = new StringBuilder();
        appendMhtLine("From: <Saved by " + Environment.UserName + " on " + Environment.MachineName + ">");
        appendMhtLine("Subject: " + ef.getHtmlTitle());
        appendMhtLine("Date: " + DateTime.Now.ToString("ddd, dd MMM yyyy HH:mm:ss zzz"));
        appendMhtLine("MIME-Version: 1.0");
        appendMhtLine("Content-Type: multipart/related;");
        AppendMhtLine(Convert.ToChar(9).ToString() + "type=\"text/html\";");
        AppendMhtLine(Convert.ToChar(9).ToString() + "boundary=\"----=_NextPart_000_00\"");
        appendMhtLine("X-MimeOLE: Produced by " + this.GetType().ToString() + " " + Assembly.GetExecutingAssembly().GetName().Version.ToString());
        appendMhtLine("");
        appendMhtLine("This is a multi-part message in MIME format.");
        appendMhtFile(ef);
    }

    /**
    * append a single line, with trailing CRLF, to our MhtBuilder
    */
    void appendMhtLine() throws Exception {
        _MhtBuilder.Append(Environment.NewLine);
    }

    /**
    * append a single line, with trailing CRLF, to our MhtBuilder
    */
    void appendMhtLine(String s) throws Exception {
        if (s != null)
            _MhtBuilder.Append(s);
         
        _MhtBuilder.Append(Environment.NewLine);
    }

    /**
    * Appends a downloaded external text file to our MhtBuilder using Quoted-Printable encoding
    */
    void appendMhtTextFile(MhtWebFile ef) throws Exception {
        appendMhtBoundary();
        appendMhtLine("Content-Type: " + ef.getContentType() + ";");
        AppendMhtLine(Convert.ToChar(9).ToString() + "charset=\" + ef.TextEncoding.WebName + @\"");
        appendMhtLine("Content-Transfer-Encoding: quoted-printable");
        appendMhtLine("Content-Location: " + ef.getUrl());
        appendMhtLine();
        appendMhtLine(quotedPrintableEncode(ef.toString(),ef.getTextEncoding()));
    }

    /**
    * returns the root HTML we'll use to generate everything else;
    * this is tracked in the _HtmlFile object, which is always FileStorage.Memory
    */
    void downloadHtmlFile(String url) throws Exception {
        if (!StringSupport.equals(url, ""))
            this.setUrl(url);
         
        _HtmlFile.WasAppended = false;
        _HtmlFile.download();
        if (!_HtmlFile.getWasDownloaded())
            throw new Exception("unable to download '" + this.getUrl() + "': " + _HtmlFile.getDownloadException().Message, _HtmlFile.getDownloadException());
         
    }

    /**
    * dumps our MhtBuilder as a string and clears it
    */
    String finalizeMht() throws Exception {
        String s = _MhtBuilder.ToString();
        _MhtBuilder = null;
        return s;
    }

    /**
    * dumps our MhtBuilder to disk and clears it
    */
    void finalizeMht(String outputFilePath) throws Exception {
        StreamWriter writer = new StreamWriter(outputFilePath, false, _HtmlFile.getTextEncoding());
        writer.Write(_MhtBuilder.ToString());
        writer.Close();
        _MhtBuilder = null;
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
    String makeValidFilename(String s) throws Exception {
        return Regex.Replace(s, "[\\/\\\\\\:\\*\\?\\\"\\<\\>\\|]", "_");
    }

    // replace any invalid filesystem chars with underscore
    /**
    * ensures that the path, if it contains a filename, matches one of the semicolon delimited
    * filetypes provided in fileExtension
    */
    void validateFilename(String FilePath, String fileExtensions) throws Exception {
        if (this.isDirectory(FilePath))
            return ;
         
        String ext = Path.GetExtension(FilePath);
        if (StringSupport.equals(ext, ""))
        {
            throw new Exception("The filename provided, '" + Path.GetFileName(FilePath) + "', has no extension. If are specifying a folder, make sure it ends in a trailing slash. The expected file extension(s) are '" + fileExtensions + "'");
        }
         
        if (!Regex.IsMatch(fileExtensions, ext + "(;|$)", RegexOptions.IgnoreCase))
        {
            throw new Exception("The extension of the filename provided + '" + Path.GetFileName(FilePath) + "', does not have the expected extension(s) '" + fileExtensions + "'");
        }
         
    }

    /**
    * Generates a string representation of the current URL as a Mht archive file
    * using exclusively in-memory storage
    * 
    *  @return string representation of MHT file
    */
    public String getPageArchive() throws Exception {
        return GetPageArchive(String.Empty);
    }

    /**
    * Generates a string representation of the URL as a Mht archive file
    * using exclusively in-memory storage
    * 
    *  @param url fully qualified URL you wish to render to Mht
    *  @return string representation of MHT file
    */
    public String getPageArchive(String url) throws Exception {
        downloadHtmlFile(url);
        // download all references
        _HtmlFile.downloadExternalFiles(_AllowRecursion);
        // build the Mht
        appendMhtHeader(_HtmlFile);
        appendMhtFiles();
        return this.finalizeMht();
    }

    /**
    * Saves the current URL to disk as a single file Mht archive
    * if a folder is provided instead of a filename, the TITLE tag is used to name the file
    * 
    *  @param outputFilePath path to generate to, or filename to generate
    *  @param st type of storage to use when generating the Mht archive
    *  @return the complete path of the Mht archive file that was generated
    */
    public String savePageArchive(String outputFilePath) throws Exception {
        return SavePageArchive(outputFilePath, String.Empty);
    }

    /**
    * Saves URL to disk as a single file Mht archive
    * if a folder is provided instead of a filename, the TITLE tag is used to name the file
    * 
    *  @param outputFilePath path to generate to, or filename to generate
    *  @param st type of storage to use when generating the Mht archive
    *  @param url fully qualified URL you wish to save as Mht
    *  @return the complete path of the Mht archive file that was generated
    */
    public String savePageArchive(String outputFilePath, String url) throws Exception {
        validateFilename(outputFilePath,".mht");
        downloadHtmlFile(url);
        _HtmlFile.setDownloadPath(outputFilePath);
        _HtmlFile.setUseHtmlTitleAsFilename(true);
        // download all references
        _HtmlFile.downloadExternalFiles(_AllowRecursion);
        // build the Mht
        appendMhtHeader(_HtmlFile);
        appendMhtFiles();
        FinalizeMht(Path.ChangeExtension(_HtmlFile.getDownloadPath(), ".mht"));
        WebFiles.Clear();
        return Path.ChangeExtension(_HtmlFile.getDownloadPath(), ".mht");
    }

    /**
    * converts a string into Quoted-Printable encoding
    * http://www.freesoft.org/CIE/RFC/1521/6.htm
    */
    String quotedPrintableEncode(String s, Encoding e) throws Exception {
        int lastSpace = 0;
        int lineLength = 0;
        int lineBreaks = 0;
        StringBuilder sb = new StringBuilder();
        if (s == null || s.Length == 0)
            return "";
         
        for (Object __dummyForeachVar2 : s)
        {
            char c = (Character)__dummyForeachVar2;
            int ascii = Convert.ToInt32(c);
            if (ascii == 61 || ascii > 126)
            {
                if (ascii <= 255)
                {
                    sb.Append("=");
                    sb.Append(Convert.ToString(ascii, 16).ToUpper());
                    lineLength += 3;
                }
                else
                {
                    for (Object __dummyForeachVar1 : e.GetBytes(c.ToString()))
                    {
                        // double-byte land..
                        byte b = (Byte)__dummyForeachVar1;
                        sb.Append("=");
                        sb.Append(Convert.ToString(b, 16).ToUpper());
                        lineLength += 3;
                    }
                } 
            }
            else
            {
                sb.Append(c);
                lineLength++;
                if (ascii == 32)
                    lastSpace = sb.Length;
                 
            } 
            if (lineLength >= 73)
            {
                if (lastSpace == 0)
                {
                    sb.Insert(sb.Length, "=" + Environment.NewLine);
                    lineLength = 0;
                }
                else
                {
                    sb.Insert(lastSpace, "=" + Environment.NewLine);
                    lineLength = sb.Length - lastSpace - 1;
                } 
                lineBreaks++;
                lastSpace = 0;
            }
             
        }
        // if we split the line, have to indicate trailing spaces
        if (lineBreaks > 0 && sb[sb.Length - 1] == ' ')
        {
            sb.Remove(sb.Length - 1, 1);
            sb.Append("=20");
        }
         
        return sb.ToString();
    }

}


