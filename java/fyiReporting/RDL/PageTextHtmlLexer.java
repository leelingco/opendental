//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:32 PM
//

package fyiReporting.RDL;


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
* A simple lexer that is used by PageTextHtml to break an HTML string into components.
*/
public class PageTextHtmlLexer   
{
    private List<String> tokens = new List<String>();
    private CharReader reader;
    // we reserve some of the strings for control purposes
    static public String EOF = '\ufffe'.ToString();
    static public String WHITESPACE = '\ufffd'.ToString();
    static public char HTMLCMD = '\ufffc';
    /**
    * Initializes a new instance of the Lexer class with the specified
    * TextReader to lex.
    * 
    *  @param source A TextReader to lex.
    */
    public PageTextHtmlLexer(String html) throws Exception {
        // token queue
        tokens = new List<String>();
        // read the file contents
        reader = new CharReader(html);
    }

    /**
    * Breaks the input stream onto the tokens list and returns it.
    * 
    *  @return The tokens list.
    */
    public List<String> lex() throws Exception {
        String token = getNextToken();
        while (true)
        {
            if (token == null)
            {
                tokens.Add(EOF);
                return tokens;
            }
             
            // add EOF token
            tokens.Add(token);
            token = getNextToken();
        }
    }

    private String getNextToken() throws Exception {
        boolean bWhiteSpace = false;
        while (!reader.endOfInput())
        {
            char ch = reader.getNext();
            // skipping whitespaces	at front of token
            if (Char.IsWhiteSpace(ch))
            {
                bWhiteSpace = true;
                continue;
            }
             
            // If we had any white space
            if (bWhiteSpace)
            {
                reader.unGet();
                return WHITESPACE;
            }
             
            // indicates white space in location
            switch(ch)
            {
                case '<': 
                    return readHtml(ch,'>');
                default: 
                    return readWord(ch,'<');
            
            }
        }
        return null;
    }

    // end of switch
    // Read html command string
    private String readHtml(char firstc, char ch) throws Exception {
        char qChar = ch;
        StringBuilder lt = new StringBuilder();
        lt.Append(HTMLCMD.ToString());
        // mark as HTML command
        lt.Append(firstc);
        while (!reader.endOfInput())
        {
            ch = reader.getNext();
            if (ch == '&')
                ch = readSpecial();
             
            lt.Append(ch);
            if (ch == qChar)
            {
                String cmd = lt.ToString();
                System.String.APPLY __dummyScrutVar1 = cmd.Substring(1);
                if (__dummyScrutVar1.equals("<q>") || __dummyScrutVar1.equals("</q>"))
                {
                    return "\"";
                }
                else
                {
                    return lt.ToString();
                } 
            }
             
        }
        return lt.ToString();
    }

    // handles special characters; that is those beginning with &
    private char readSpecial() throws Exception {
        StringBuilder lt = new StringBuilder();
        int MAXSPECIAL = 7;
        while (!reader.endOfInput() && MAXSPECIAL-- > 0)
        {
            // should be the size of the maximum escaped character
            char ch = reader.getNext();
            if (ch == ';')
                break;
             
            lt.Append(ch);
        }
        if (MAXSPECIAL <= 0)
        {
            for (int i = 1;i <= lt.Length;i++)
            {
                // can't be an escaped character; undo all the reading
                // unget all the characters
                reader.unGet();
            }
            return '&';
        }
         
        String s = lt.ToString();
        if (s.Length == 0)
            return ' ';
         
        // not a valid character; return blank
        if (s[0] == '#')
        {
            try
            {
                // number character
                String num = s.Substring(1);
                // this can cause an exception
                int nv = Convert.ToInt32(num);
                //    and this can too
                if (nv > 255)
                    return ' ';
                 
                return Convert.ToChar(nv);
            }
            catch (Exception __dummyCatchVar0)
            {
                return ' ';
            }
        
        }
         
        // not a valid number; return blank
        // not a valid number; return blank
        System.String __dummyScrutVar2 = s;
        if (__dummyScrutVar2.equals("quot"))
        {
            return '"';
        }
        else if (__dummyScrutVar2.equals("amp"))
        {
            return '&';
        }
        else if (__dummyScrutVar2.equals("lt"))
        {
            return '<';
        }
        else if (__dummyScrutVar2.equals("gt"))
        {
            return '>';
        }
        else if (__dummyScrutVar2.equals("nbsp"))
        {
            return ' ';
        }
        else if (__dummyScrutVar2.equals("iexcl"))
        {
            return Convert.ToChar(161);
        }
        else if (__dummyScrutVar2.equals("cent"))
        {
            return Convert.ToChar(162);
        }
        else if (__dummyScrutVar2.equals("pound"))
        {
            return Convert.ToChar(163);
        }
        else if (__dummyScrutVar2.equals("curren"))
        {
            return Convert.ToChar(164);
        }
        else if (__dummyScrutVar2.equals("yen"))
        {
            return Convert.ToChar(165);
        }
        else if (__dummyScrutVar2.equals("brvbar"))
        {
            return Convert.ToChar(166);
        }
        else if (__dummyScrutVar2.equals("sect"))
        {
            return Convert.ToChar(167);
        }
        else if (__dummyScrutVar2.equals("uml"))
        {
            return Convert.ToChar(168);
        }
        else if (__dummyScrutVar2.equals("copy"))
        {
            return Convert.ToChar(169);
        }
        else if (__dummyScrutVar2.equals("ordf"))
        {
            return Convert.ToChar(170);
        }
        else if (__dummyScrutVar2.equals("laquo"))
        {
            return Convert.ToChar(171);
        }
        else if (__dummyScrutVar2.equals("not"))
        {
            return Convert.ToChar(172);
        }
        else if (__dummyScrutVar2.equals("shy"))
        {
            return Convert.ToChar(173);
        }
        else if (__dummyScrutVar2.equals("reg"))
        {
            return Convert.ToChar(174);
        }
        else if (__dummyScrutVar2.equals("macr"))
        {
            return Convert.ToChar(175);
        }
        else if (__dummyScrutVar2.equals("deg"))
        {
            return Convert.ToChar(176);
        }
        else if (__dummyScrutVar2.equals("plusmn"))
        {
            return Convert.ToChar(177);
        }
        else if (__dummyScrutVar2.equals("sup2"))
        {
            return Convert.ToChar(178);
        }
        else if (__dummyScrutVar2.equals("sup3"))
        {
            return Convert.ToChar(179);
        }
        else if (__dummyScrutVar2.equals("acute"))
        {
            return Convert.ToChar(180);
        }
        else if (__dummyScrutVar2.equals("micro"))
        {
            return Convert.ToChar(181);
        }
        else if (__dummyScrutVar2.equals("para"))
        {
            return Convert.ToChar(182);
        }
        else if (__dummyScrutVar2.equals("middot"))
        {
            return Convert.ToChar(183);
        }
        else if (__dummyScrutVar2.equals("cedil"))
        {
            return Convert.ToChar(184);
        }
        else if (__dummyScrutVar2.equals("sup1"))
        {
            return Convert.ToChar(185);
        }
        else if (__dummyScrutVar2.equals("ordm"))
        {
            return Convert.ToChar(186);
        }
        else if (__dummyScrutVar2.equals("raquo"))
        {
            return Convert.ToChar(187);
        }
        else if (__dummyScrutVar2.equals("frac14"))
        {
            return Convert.ToChar(188);
        }
        else if (__dummyScrutVar2.equals("frac12"))
        {
            return Convert.ToChar(189);
        }
        else if (__dummyScrutVar2.equals("frac34"))
        {
            return Convert.ToChar(190);
        }
        else if (__dummyScrutVar2.equals("iquest"))
        {
            return Convert.ToChar(191);
        }
        else if (__dummyScrutVar2.equals("Agrave"))
        {
            return Convert.ToChar(192);
        }
        else if (__dummyScrutVar2.equals("Aacute"))
        {
            return Convert.ToChar(193);
        }
        else if (__dummyScrutVar2.equals("Acirc"))
        {
            return Convert.ToChar(194);
        }
        else if (__dummyScrutVar2.equals("Atilde"))
        {
            return Convert.ToChar(195);
        }
        else if (__dummyScrutVar2.equals("Auml"))
        {
            return Convert.ToChar(196);
        }
        else if (__dummyScrutVar2.equals("Aring"))
        {
            return Convert.ToChar(197);
        }
        else if (__dummyScrutVar2.equals("AElig"))
        {
            return Convert.ToChar(198);
        }
        else if (__dummyScrutVar2.equals("Ccedil"))
        {
            return Convert.ToChar(199);
        }
        else if (__dummyScrutVar2.equals("Egrave"))
        {
            return Convert.ToChar(200);
        }
        else if (__dummyScrutVar2.equals("Eacute"))
        {
            return Convert.ToChar(201);
        }
        else if (__dummyScrutVar2.equals("Ecirc"))
        {
            return Convert.ToChar(202);
        }
        else if (__dummyScrutVar2.equals("Euml"))
        {
            return Convert.ToChar(203);
        }
        else if (__dummyScrutVar2.equals("lgrave"))
        {
            return Convert.ToChar(204);
        }
        else if (__dummyScrutVar2.equals("lacute"))
        {
            return Convert.ToChar(205);
        }
        else if (__dummyScrutVar2.equals("lcirc"))
        {
            return Convert.ToChar(206);
        }
        else if (__dummyScrutVar2.equals("luml"))
        {
            return Convert.ToChar(207);
        }
        else if (__dummyScrutVar2.equals("EHT"))
        {
            return Convert.ToChar(208);
        }
        else if (__dummyScrutVar2.equals("Ntilde"))
        {
            return Convert.ToChar(209);
        }
        else if (__dummyScrutVar2.equals("Ograve"))
        {
            return Convert.ToChar(210);
        }
        else if (__dummyScrutVar2.equals("Oacute"))
        {
            return Convert.ToChar(211);
        }
        else if (__dummyScrutVar2.equals("Ocirc"))
        {
            return Convert.ToChar(212);
        }
        else if (__dummyScrutVar2.equals("Otilde"))
        {
            return Convert.ToChar(213);
        }
        else if (__dummyScrutVar2.equals("Ouml"))
        {
            return Convert.ToChar(214);
        }
        else if (__dummyScrutVar2.equals("times"))
        {
            return Convert.ToChar(215);
        }
        else if (__dummyScrutVar2.equals("Oslash"))
        {
            return Convert.ToChar(216);
        }
        else if (__dummyScrutVar2.equals("Ugrave"))
        {
            return Convert.ToChar(217);
        }
        else if (__dummyScrutVar2.equals("Uacute"))
        {
            return Convert.ToChar(218);
        }
        else if (__dummyScrutVar2.equals("Ucirc"))
        {
            return Convert.ToChar(219);
        }
        else if (__dummyScrutVar2.equals("Uuml"))
        {
            return Convert.ToChar(220);
        }
        else if (__dummyScrutVar2.equals("Yacute"))
        {
            return Convert.ToChar(221);
        }
        else if (__dummyScrutVar2.equals("THORN"))
        {
            return Convert.ToChar(222);
        }
        else if (__dummyScrutVar2.equals("szlig"))
        {
            return Convert.ToChar(223);
        }
        else if (__dummyScrutVar2.equals("agrave"))
        {
            return Convert.ToChar(224);
        }
        else if (__dummyScrutVar2.equals("aacute"))
        {
            return Convert.ToChar(225);
        }
        else if (__dummyScrutVar2.equals("acirc"))
        {
            return Convert.ToChar(226);
        }
        else if (__dummyScrutVar2.equals("atilde"))
        {
            return Convert.ToChar(227);
        }
        else if (__dummyScrutVar2.equals("auml"))
        {
            return Convert.ToChar(228);
        }
        else if (__dummyScrutVar2.equals("aring"))
        {
            return Convert.ToChar(229);
        }
        else if (__dummyScrutVar2.equals("aelig"))
        {
            return Convert.ToChar(230);
        }
        else if (__dummyScrutVar2.equals("ccedil"))
        {
            return Convert.ToChar(231);
        }
        else if (__dummyScrutVar2.equals("egrave"))
        {
            return Convert.ToChar(232);
        }
        else if (__dummyScrutVar2.equals("eacute"))
        {
            return Convert.ToChar(233);
        }
        else if (__dummyScrutVar2.equals("ecirc"))
        {
            return Convert.ToChar(234);
        }
        else if (__dummyScrutVar2.equals("euml"))
        {
            return Convert.ToChar(235);
        }
        else if (__dummyScrutVar2.equals("igrave"))
        {
            return Convert.ToChar(236);
        }
        else if (__dummyScrutVar2.equals("iacute"))
        {
            return Convert.ToChar(237);
        }
        else if (__dummyScrutVar2.equals("icirc"))
        {
            return Convert.ToChar(238);
        }
        else if (__dummyScrutVar2.equals("iuml"))
        {
            return Convert.ToChar(239);
        }
        else if (__dummyScrutVar2.equals("eth"))
        {
            return Convert.ToChar(240);
        }
        else if (__dummyScrutVar2.equals("ntilde"))
        {
            return Convert.ToChar(241);
        }
        else if (__dummyScrutVar2.equals("ograve"))
        {
            return Convert.ToChar(242);
        }
        else if (__dummyScrutVar2.equals("oacute"))
        {
            return Convert.ToChar(243);
        }
        else if (__dummyScrutVar2.equals("ocirc"))
        {
            return Convert.ToChar(244);
        }
        else if (__dummyScrutVar2.equals("otilde"))
        {
            return Convert.ToChar(245);
        }
        else if (__dummyScrutVar2.equals("ouml"))
        {
            return Convert.ToChar(246);
        }
        else if (__dummyScrutVar2.equals("divide"))
        {
            return Convert.ToChar(247);
        }
        else if (__dummyScrutVar2.equals("oslash"))
        {
            return Convert.ToChar(248);
        }
        else if (__dummyScrutVar2.equals("ugrave"))
        {
            return Convert.ToChar(249);
        }
        else if (__dummyScrutVar2.equals("uacute"))
        {
            return Convert.ToChar(250);
        }
        else if (__dummyScrutVar2.equals("ucirc"))
        {
            return Convert.ToChar(251);
        }
        else if (__dummyScrutVar2.equals("uuml"))
        {
            return Convert.ToChar(252);
        }
        else if (__dummyScrutVar2.equals("yacute"))
        {
            return Convert.ToChar(253);
        }
        else if (__dummyScrutVar2.equals("thorn"))
        {
            return Convert.ToChar(254);
        }
        else if (__dummyScrutVar2.equals("yuml"))
        {
            return Convert.ToChar(255);
        }
        else
        {
            return ' ';
        }                                                                                                    
    }

    // not a valid special character
    // Read to next white space or to specified character
    private String readWord(char firstc, char ch) throws Exception {
        char qChar = ch;
        StringBuilder lt = new StringBuilder();
        lt.Append(firstc == '&' ? readSpecial() : firstc);
        while (!reader.endOfInput())
        {
            ch = reader.getNext();
            if (Char.IsWhiteSpace(ch) || ch == qChar)
            {
                reader.unGet();
                break;
            }
             
            // put it back on the stack
            if (ch == '&')
                ch = readSpecial();
             
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


