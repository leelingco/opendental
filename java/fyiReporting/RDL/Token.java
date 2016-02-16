//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
//

package fyiReporting.RDL;

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
* Token class that used by LangParser.
*/
public class Token   
{
    public String Value = new String();
    public int StartLine = new int();
    public int EndLine = new int();
    public int StartCol = new int();
    public int EndCol = new int();
    public TokenTypes Type = TokenTypes.AND;
    /**
    * Initializes a new instance of the Token class.
    */
    public Token(String value, int startLine, int startCol, int endLine, int endCol, TokenTypes type) throws Exception {
        Value = value;
        StartLine = startLine;
        EndLine = endLine;
        StartCol = startCol;
        EndCol = endCol;
        Type = type;
    }

    /**
    * Initializes a new instance of the Token class.
    */
    public Token(String value, TokenTypes type) throws Exception {
        this(value, 0, 0, 0, 0, type);
    }

    // use this
    /**
    * Initializes a new instance of the Token class.
    */
    public Token(TokenTypes type) throws Exception {
        this(null, 0, 0, 0, 0, type);
    }

    // use this
    /**
    * Returns a string representation of the Token.
    */
    public String toString() {
        try
        {
            return "<" + Type + "> " + Value;
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

}


