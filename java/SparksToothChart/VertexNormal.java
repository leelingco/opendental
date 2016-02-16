//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:35 PM
//

package SparksToothChart;

import SparksToothChart.Vertex3f;
import SparksToothChart.VertexNormal;

/**
* Contains one vertex (xyz) and one normal.
*/
public class VertexNormal   
{
    public Vertex3f Vertex;
    public Vertex3f Normal;
    public String toString() {
        try
        {
            return "v:" + Vertex.toString() + " n:" + Normal.toString();
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

    public VertexNormal copy() throws Exception {
        VertexNormal vn = new VertexNormal();
        vn.Vertex = this.Vertex.copy();
        vn.Normal = this.Normal.copy();
        return vn;
    }

}


