//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
//

package fyiReporting.RDL;

import CS2JNet.System.StringSupport;
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
* A simple Lexer that is used by Parser.
*/
public class Lexer   
{
    private TokenList tokens;
    private CharReader reader;
    /**
    * Initializes a new instance of the Lexer class with the specified
    * expression syntax to lex.
    * 
    *  @param expr An expression to lex.
    */
    public Lexer(String expr) throws Exception {
        this(new StringReader(expr));
    }

    // use this
    /**
    * Initializes a new instance of the Lexer class with the specified
    * TextReader to lex.
    * 
    *  @param source A TextReader to lex.
    */
    public Lexer(TextReader source) throws Exception {
        // token queue
        tokens = new TokenList();
        // read the file contents
        reader = new CharReader(source);
    }

    /**
    * Breaks the input stream onto the tokens list and returns it.
    * 
    *  @return The tokens list.
    */
    public TokenList lex() throws Exception {
        Token token = getNextToken();
        while (true)
        {
            if (token != null)
                tokens.add(token);
            else
            {
                tokens.add(new Token(null,reader.getLine(),reader.getColumn(),reader.getLine(),reader.getColumn(),TokenTypes.EOF));
                return tokens;
            } 
            token = getNextToken();
        }
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
                case '=': 
                    return new Token(ch.ToString(), reader.getLine(), reader.getColumn(), reader.getLine(), reader.getColumn(), TokenTypes.EQUAL);
                case '+': 
                    return new Token(ch.ToString(), reader.getLine(), reader.getColumn(), reader.getLine(), reader.getColumn(), TokenTypes.PLUS);
                case '-': 
                    return new Token(ch.ToString(), reader.getLine(), reader.getColumn(), reader.getLine(), reader.getColumn(), TokenTypes.MINUS);
                case '(': 
                    return new Token(ch.ToString(), reader.getLine(), reader.getColumn(), reader.getLine(), reader.getColumn(), TokenTypes.LPAREN);
                case ')': 
                    return new Token(ch.ToString(), reader.getLine(), reader.getColumn(), reader.getLine(), reader.getColumn(), TokenTypes.RPAREN);
                case ',': 
                    return new Token(ch.ToString(), reader.getLine(), reader.getColumn(), reader.getLine(), reader.getColumn(), TokenTypes.COMMA);
                case '^': 
                    return new Token(ch.ToString(), reader.getLine(), reader.getColumn(), reader.getLine(), reader.getColumn(), TokenTypes.EXP);
                case '%': 
                    return new Token(ch.ToString(), reader.getLine(), reader.getColumn(), reader.getLine(), reader.getColumn(), TokenTypes.MODULUS);
                case '!': 
                    if (reader.peek() == '=')
                    {
                        reader.getNext();
                        return new Token(ch.ToString(), reader.getLine(), reader.getColumn(), reader.getLine(), reader.getColumn(), TokenTypes.NOTEQUAL);
                    }
                    else
                        return new Token(ch.ToString(), reader.getLine(), reader.getColumn(), reader.getLine(), reader.getColumn(), TokenTypes.NOT); 
                case '&': 
                    return new Token(ch.ToString(), reader.getLine(), reader.getColumn(), reader.getLine(), reader.getColumn(), TokenTypes.PLUSSTRING);
                case '|': 
                    // go past the equal
                    if (reader.peek() == '|')
                    {
                        reader.getNext();
                        return new Token(ch.ToString(), reader.getLine(), reader.getColumn(), reader.getLine(), reader.getColumn(), TokenTypes.OR);
                    }
                     
                    break;
                case '>': 
                    // go past the '|'
                    if (reader.peek() == '=')
                    {
                        reader.getNext();
                        return new Token(ch.ToString(), reader.getLine(), reader.getColumn(), reader.getLine(), reader.getColumn(), TokenTypes.GREATERTHANOREQUAL);
                    }
                    else
                        return new Token(ch.ToString(), reader.getLine(), reader.getColumn(), reader.getLine(), reader.getColumn(), TokenTypes.GREATERTHAN); 
                case '/': 
                    // go past the equal
                    if (reader.peek() == '*')
                    {
                        // beginning of a comment of form /* a comment */
                        reader.getNext();
                        // go past the '*'
                        readComment();
                        continue;
                    }
                    else
                        return new Token(ch.ToString(), reader.getLine(), reader.getColumn(), reader.getLine(), reader.getColumn(), TokenTypes.FORWARDSLASH); 
                case '<': 
                    if (reader.peek() == '=')
                    {
                        reader.getNext();
                        return new Token(ch.ToString(), reader.getLine(), reader.getColumn(), reader.getLine(), reader.getColumn(), TokenTypes.LESSTHANOREQUAL);
                    }
                    else
                        return new Token(ch.ToString(), reader.getLine(), reader.getColumn(), reader.getLine(), reader.getColumn(), TokenTypes.LESSTHAN); 
                case '*': 
                    return new Token(ch.ToString(), reader.getLine(), reader.getColumn(), reader.getLine(), reader.getColumn(), TokenTypes.STAR);
                case '"': 
                case '\'': 
                    return readQuoted(ch);
                default: 
                    break;
            
            }
            // go past the equal
            // end of swith
            if (Char.IsDigit(ch) || ch == '.')
                return readNumber(ch);
            else if (Char.IsLetter(ch) || ch == '_')
                return readIdentifier(ch);
            else
                return new Token(ch.ToString(), reader.getLine(), reader.getColumn(), reader.getLine(), reader.getColumn(), TokenTypes.OTHER);  
        }
        return null;
    }

    // Reads a decimal number with optional exponentiation
    private Token readNumber(char ch) throws Exception {
        int startLine = reader.getLine();
        int startCol = reader.getColumn();
        boolean bDecimal = ch == '.' ? true : false;
        boolean bDecimalType = false;
        // found d or D in number
        boolean bFloat = false;
        // found e or E in number
        char cPeek = new char();
        String number = ch.ToString();
        while (!reader.endOfInput())
        {
            cPeek = reader.peek();
            if (Char.IsWhiteSpace(cPeek))
                break;
             
            if (Char.IsDigit(cPeek))
                number += reader.getNext();
            else if (cPeek == 'd' || cPeek == 'D' && !bFloat)
            {
                reader.getNext();
                // skip the 'd'
                bDecimalType = true;
                break;
            }
            else if (cPeek == 'e' || cPeek == 'E' && !bFloat)
            {
                number += reader.getNext();
                // add the 'e'
                cPeek = reader.peek();
                if (cPeek == '-' || cPeek == '+')
                {
                    number += reader.getNext();
                    bFloat = true;
                    if (Char.IsDigit(reader.peek()))
                        continue;
                     
                }
                 
                throw new ParserException("Invalid number constant.");
            }
            else if (!bDecimal && !bFloat && cPeek == '.')
            {
                // can't already be decimal or float
                bDecimal = true;
                number += reader.getNext();
            }
            else
                break;    
        }
        // another character
        if (number.CompareTo(".") == 0)
            throw new ParserException("'.' should be followed by a number");
         
        TokenTypes t = TokenTypes.AND;
        if (bDecimalType)
            t = TokenTypes.NUMBER;
        else if (bFloat || bDecimal)
            t = TokenTypes.DOUBLE;
        else
            t = TokenTypes.INTEGER;  
        return new Token(number,startLine,startCol,reader.getLine(),reader.getColumn(),t);
    }

    // Reads an identifier:
    //  Must consist of letters, digits, "_".  "!", "." are allowed
    //  but have special meaning that is disambiguated later
    private Token readIdentifier(char ch) throws Exception {
        int startLine = reader.getLine();
        int startCol = reader.getColumn();
        char cPeek = new char();
        StringBuilder identifier = new StringBuilder(30);
        // initial capacity 30 characters
        identifier.Append(ch.ToString());
        while (!reader.endOfInput())
        {
            cPeek = reader.peek();
            if (Char.IsLetterOrDigit(cPeek) || cPeek == '.' || cPeek == '!' || cPeek == '_')
                identifier.Append(reader.getNext());
            else
                break; 
        }
        String key = identifier.ToString().ToLower();
        if (StringSupport.equals(key, "and"))
            return new Token(identifier.ToString(), reader.getLine(), reader.getColumn(), reader.getLine(), reader.getColumn(), TokenTypes.AND);
        else if (StringSupport.equals(key, "or"))
            return new Token(identifier.ToString(), reader.getLine(), reader.getColumn(), reader.getLine(), reader.getColumn(), TokenTypes.OR);
        else if (StringSupport.equals(key, "mod"))
            return new Token(ch.ToString(), reader.getLine(), reader.getColumn(), reader.getLine(), reader.getColumn(), TokenTypes.MODULUS);
           
        return new Token(identifier.ToString(), startLine, startCol, reader.getLine(), reader.getColumn(), TokenTypes.IDENTIFIER);
    }

    // normal identifier
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

    // Comment string like /* this is a comment */
    private void readComment() throws Exception {
        char ch = new char();
        while (!reader.endOfInput())
        {
            ch = reader.getNext();
            if (ch == '*' && reader.peek() == '/')
            {
                reader.getNext();
                return ;
            }
             
        }
        throw new ParserException("Unterminated comment!");
    }

}


// skip past the '/'
//		// Handles case of "<", "<=", and "<! ... xml string  !>
//		private Token ReadXML(char ch)
//		{
//			int startLine = reader.Line;
//			int startCol = reader.Column;
//
//			if (reader.EndOfInput())
//				return  new Token(ch.ToString(), startLine, startCol, startLine, startCol, TokenTypes.LESSTHAN);
//			ch = reader.GetNext();
//			if (ch == '=')
//				return  new Token("<=", startLine, startCol, reader.Line, reader.Column, TokenTypes.LESSTHANOREQUAL);
//			if (ch != '!')					// If it's not '!' then it's not XML
//			{
//				reader.UnGet();				// put back the character
//				return  new Token("<", startLine, startCol, reader.Line, reader.Column, TokenTypes.LESSTHAN);
//			}
//
//			string xml = "";				// intialize our string
//
//			while(!reader.EndOfInput())
//			{
//				ch = reader.GetNext();
//
//				if(ch == '!')				// check for end of XML denoted by "!>"
//				{
//					if (!reader.EndOfInput() && reader.Peek() == '>')
//					{
//						reader.GetNext();	// pull the '>' off the input
//						return new Token(xml, startLine, startCol, reader.Line, reader.Column, TokenTypes.XML);
//					}
//				}
//
//				xml += ch.ToString();
//			}
//			throw new ParserException("Unterminated XML clause!");
//		}