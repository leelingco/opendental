//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Token;

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
* Represents a list of the tokens.
*/
public class TokenList  extends IEnumerable 
{
    private List<Token> tokens = null;
    public TokenList() throws Exception {
        tokens = new List<Token>();
    }

    public void add(Token token) throws Exception {
        tokens.Add(token);
    }

    public void push(Token token) throws Exception {
        tokens.Insert(0, token);
    }

    public Token peek() throws Exception {
        return tokens[0];
    }

    public Token extract() throws Exception {
        Token token = tokens[0];
        tokens.RemoveAt(0);
        return token;
    }

    public int getCount() throws Exception {
        return tokens.Count;
    }

    public IEnumerator getEnumerator() throws Exception {
        return tokens.GetEnumerator();
    }

}


