//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
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
* char reader simply reads entire file into a string and processes.
*/
public class CharReader   
{
    String file = null;
    int ptr = 0;
    int col = 1;
    // column within line
    int savecol = 1;
    //   saved column before a line feed
    int line = 1;
    // line within file
    /**
    * Initializes a new instance of the CharReader class.
    * 
    *  @param textReader TextReader with DPL definition.
    */
    public CharReader(TextReader textReader) throws Exception {
        file = textReader.ReadToEnd();
        textReader.Close();
    }

    /**
    * Returns the next char from the stream.
    * 
    *  @return The next char.
    */
    public char getNext() throws Exception {
        if (endOfInput())
        {
            Console.WriteLine("warning : FileReader.GetNext : Read char over EndOfInput.");
            return '\0';
        }
         
        char ch = file[ptr++];
        col++;
        // increment column counter
        if (ch == '\n')
        {
            line++;
            // got new line
            savecol = col;
            col = 1;
        }
         
        return ch;
    }

    // restart column counter
    /**
    * Returns the next char from the stream without removing it.
    * 
    *  @return The top char.
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
            throw new Exception("error : FileReader.UnGet : ungetted first char");
         
        char ch = file[ptr];
        if (ch == '\n')
        {
            // did we unget a new line?
            line--;
            // back up a line
            col = savecol;
        }
         
    }

    // go back to previous column too
    /**
    * Returns True if end of input was reached; otherwise False.
    * 
    *  @return True if end of input was reached; otherwise False.
    */
    public boolean endOfInput() throws Exception {
        return ptr >= file.Length;
    }

    /**
    * Gets the current column.
    */
    public int getColumn() throws Exception {
        return col;
    }

    /**
    * Gets the current line.
    */
    public int getLine() throws Exception {
        return line;
    }

}


