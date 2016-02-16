//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:34 PM
//

package SparksToothChart;

import SparksToothChart.Face;

/**
* A face is a single polygon, usually a rectangle.  Will soon be only triangles.
*/
public class Face   
{
    //public List<VertexNormal> VertexNormals;
    /**
    * A list of indices to the VertexNormal list contained in the ToothGraphic object.
    */
    public List<int> IndexList = new List<int>();
    public Face() throws Exception {
        //VertexNormals=new List<VertexNormal>();
        IndexList = new List<int>();
    }

    public String toString() {
        try
        {
            String retVal = "";
            for (int i = 0;i < IndexList.Count;i++)
            {
                if (i > 0)
                {
                    retVal += ",";
                }
                 
                retVal += IndexList[i].ToString();
            }
            return retVal;
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

    public Face copy() throws Exception {
        Face f = new Face();
        f.IndexList = new List<int>(this.IndexList);
        return f;
    }

}


