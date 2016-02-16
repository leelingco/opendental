//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:33 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.CharReader;
import fyiReporting.RDL.ParserException;
import fyiReporting.RDL.Token;
import fyiReporting.RDL.TokenList;
import fyiReporting.RDL.TokenTypes;

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
* A simple Lexer that is used to create a list of parameters.
*/
public class ParameterLexer   
{
    private TokenList tokens;
    private CharReader reader;
    /**
    * Initializes a new instance of the Lexer class with the specified
    * expression syntax to lex.
    * 
    *  @param expr An expression to lex.
    */
    public ParameterLexer(String expr) throws Exception {
        this(new StringReader(expr));
    }

    // use this
    /**
    * Initializes a new instance of the Lexer class with the specified
    * TextReader to lex.
    * 
    *  @param source A TextReader to lex.
    */
    public ParameterLexer(TextReader source) throws Exception {
        // token queue
        tokens = new TokenList();
        // read the file contents
        reader = new CharReader(source);
    }

    /**
    * Breaks the input stream onto the tokens list and returns an ArrayList of strings.
    * 
    *  @return The array of items broken out from the string
    */
    public ArrayList lex() throws Exception {
        Token token = getNextToken();
        while (true)
        {
            if (token != null)
                tokens.add(token);
            else
            {
                tokens.add(new Token(null,reader.getLine(),reader.getColumn(),reader.getLine(),reader.getColumn(),TokenTypes.EOF));
                break;
            } 
            token = getNextToken();
        }
        // now just do a list of strings
        ArrayList ar = new ArrayList(tokens.getCount());
        for (Object __dummyForeachVar0 : tokens)
        {
            Token t = (Token)__dummyForeachVar0;
            if (t.Type == TokenTypes.QUOTE)
                ar.Add(t.Value);
             
        }
        return ar;
    }

    private Token getNextToken() throws Exception {
        while (!reader.endOfInput())
        {
            char ch = reader.getNext();
            // skipping whitespaces
            if (Char.IsWhiteSpace(ch))
            {
                continue;
            }
             
            switch(ch)
            {
                case ',': 
                    return new Token(ch.ToString(), reader.getLine(), reader.getColumn(), reader.getLine(), reader.getColumn(), TokenTypes.COMMA);
                case '"': 
                case '\'': 
                    return readQuoted(ch);
                default: 
                    return readToComma(ch);
            
            }
        }
        return null;
    }

    // end of swith
    // Reads a decimal number with optional exponentiation
    private Token readToComma(char ch) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.Append(ch);
        while (!reader.endOfInput())
        {
            char cPeek = reader.peek();
            if (cPeek == ',')
                break;
             
            cPeek = reader.getNext();
            sb.Append(cPeek);
        }
        return new Token(sb.ToString(), TokenTypes.QUOTE);
    }

    // Quoted string like " asdf " or ' asdf '
    private Token readQuoted(char ch) throws Exception {
        char qChar = ch;
        int startLine = reader.getLine();
        int startCol = reader.getColumn();
        StringBuilder quoted = new StringBuilder();
        while (!reader.endOfInput())
        {
            ch = reader.getNext();
            if (ch == '\\')
            {
                char pChar = reader.peek();
                if (pChar == qChar)
                    ch = reader.getNext();
                else // got one skip escape char
                if (pChar == 'n')
                {
                    ch = '\n';
                    reader.getNext();
                }
                else // skip the character
                if (pChar == 'r')
                {
                    ch = '\r';
                    reader.getNext();
                }
                   
            }
            else // skip the character
            if (ch == qChar)
                return new Token(quoted.ToString(), startLine, startCol, reader.getLine(), reader.getColumn(), TokenTypes.QUOTE);
              
            quoted.Append(ch);
        }
        throw new ParserException("Unterminated string!");
    }

}


