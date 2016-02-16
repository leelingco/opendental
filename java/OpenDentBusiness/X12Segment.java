//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:13 PM
//

package OpenDentBusiness;

import OpenDentBusiness.X12Segment;
import OpenDentBusiness.X12Separators;

/**
* An X12 segment is a single row of the text file.
*/
public class X12Segment   
{
    /**
    * Usually 2 or 3 letters. Can also be found at Elements[0].
    */
    public String SegmentID = new String();
    /**
    * 
    */
    public String[] Elements = new String[]();
    /**
    * 
    */
    private X12Separators Separators = new X12Separators();
    private String rawText = new String();
    /**
    * 
    */
    public X12Segment(String rawTxt, X12Separators separators) throws Exception {
        rawText = rawTxt.ToString();
        Separators = separators;
        //first, remove the segment terminator
        rawTxt = rawTxt.Replace(separators.Segment, "");
        //then, split the row into elements, eliminating the DataElementSeparator
        Elements = rawText.Split(Char.Parse(separators.Element));
        SegmentID = Elements[0];
    }

    private X12Segment() throws Exception {
    }

    public String toString() {
        try
        {
            return rawText;
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
    * Returns a copy of this segement
    */
    public X12Segment copy() throws Exception {
        X12Segment retVal = new X12Segment();
        retVal.SegmentID = SegmentID;
        retVal.Elements = (String[])Elements.Clone();
        return retVal;
    }

    //shallow copy is fine since just strings.
    /**
    * Returns the string representation of the given element within this segment. If the element does not exist, as can happen with optional elements, then "" is returned.
    */
    public String get(int elementPosition) throws Exception {
        if (Elements.Length <= elementPosition)
        {
            return "";
        }
         
        return Elements[elementPosition];
    }

    /**
    * Returns the string representation of the given element,subelement within this segment. If the element or subelement does not exist, as can happen with optional elements, then "" is returned.  Subelement is 1-based, just like the x12 specs.
    */
    public String get(int elementPosition, int subelementPosition) throws Exception {
        if (Elements.Length <= elementPosition)
        {
            return "";
        }
         
        String[] subelements = Elements[elementPosition].Split(Char.Parse(Separators.Subelement));
        //example, subelement passed in is 2.  Convert to 0-indexed means [1].  If Length < 2, then we have a problem.
        if (subelements.Length < subelementPosition)
        {
            return "";
        }
         
        return subelements[subelementPosition - 1];
    }

}


