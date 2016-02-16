//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:32 PM
//

package fyiReporting.RDL;

import CS2JNet.System.StringSupport;

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
* A simple lexer that is used by PageTextHtml to break an HTML command into name value pairs.
*/
public class PageTextHtmlCmdLexer   
{
    private CharReader reader;
    /**
    * Initializes a new instance of the Lexer class with the specified
    * TextReader to lex.
    * 
    *  @param source A TextReader to lex.
    */
    public PageTextHtmlCmdLexer(String htmlcmd) throws Exception {
        // read the file contents
        reader = new CharReader(htmlcmd);
    }

    /**
    * Breaks the input stream onto the tokens list and returns a hash table with the name value pairs.
    * 
    *  @return The hash table.
    */
    public Hashtable lex() throws Exception {
        Hashtable ht = new Hashtable();
        while (true)
        {
            String cmd = getNextToken();
            if (cmd == null)
                return ht;
             
            if (!StringSupport.equals(getNextToken(), "="))
                return ht;
             
            String val = getNextToken();
            if (val == null)
                return ht;
             
            ht.Add(cmd, val);
        }
    }

    private String getNextToken() throws Exception {
        while (!reader.endOfInput())
        {
            char ch = reader.getNext();
            // skipping whitespaces	at front of token
            if (Char.IsWhiteSpace(ch))
                continue;
             
            switch(ch)
            {
                case '\'': 
                case '"': 
                    return readQuoted(ch);
                default: 
                    return readWord(ch);
            
            }
        }
        return null;
    }

    // end of switch
    // Read html command string
    private String readQuoted(char firstc) throws Exception {
        char qChar = firstc;
        StringBuilder lt = new StringBuilder();
        while (!reader.endOfInput())
        {
            char ch = reader.getNext();
            if (ch == qChar)
                break;
             
            lt.Append(ch);
        }
        return lt.ToString();
    }

    // Read to next white space or to specified character
    private String readWord(char ch) throws Exception {
        if (ch == '=')
            return "=";
         
        StringBuilder lt = new StringBuilder();
        lt.Append(ch);
        while (!reader.endOfInput())
        {
            ch = reader.getNext();
            if (Char.IsWhiteSpace(ch) || ch == '=')
            {
                reader.unGet();
                break;
            }
             
            // put it back on the stack
            lt.Append(ch);
        }
        return lt.ToString();
    }

    static class CharReader   
    {
        String file = null;
        int ptr = 0;
        /**
        * Initializes a new instance of the CharReader class.
        */
        public CharReader(String text) throws Exception {
            file = text;
        }

        /**
        * Returns the next char from the stream.
        */
        public char getNext() throws Exception {
            if (endOfInput())
            {
                return '\0';
            }
             
            char ch = file[ptr++];
            return ch;
        }

        /**
        * Returns the next char from the stream without removing it.
        */
        public char peek() throws Exception {
            if (endOfInput())
                return '\0';
             
            return file[ptr];
        }

        // ok to peek at end of file
        /**
        * Undoes the extracting of the last char.
        */
        public void unGet() throws Exception {
            --ptr;
            if (ptr < 0)
                throw new Exception("error : ungetted first char");
             
            char ch = file[ptr];
        }

        /**
        * Returns True if end of input was reached; otherwise False.
        */
        public boolean endOfInput() throws Exception {
            return ptr >= file.Length;
        }
    
    }

}


