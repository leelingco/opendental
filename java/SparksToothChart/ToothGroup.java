//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:35 PM
//

package SparksToothChart;

import SparksToothChart.Face;
import SparksToothChart.ToothGroup;
import SparksToothChart.ToothGroupType;

/**
* For 3D tooth graphics, this is a group of faces within a single tooth.  Different groups can be assigned different colors and visibility.  Groups might include enamel, the filling surfaces, pulp, canals, and cementum.  If a group does not apply, such as a F on a posterior tooth, then that tooth will not have that group.  The code must be resiliant enough to handle missing groups.  We might add more groups later and subdivide existing groups.  For instance pits, grooves, cusps, cervical areas, etc.  Over the years, this could get quite extensive and complex.  We would have to add a table to the database to handle additional groups painted by user.
*/
public class ToothGroup   
{
    /**
    * 
    */
    public boolean Visible = new boolean();
    /**
    * 
    */
    public Color PaintColor = new Color();
    /**
    * 
    */
    public ToothGroupType GroupType = ToothGroupType.Enamel;
    /**
    * dim 1=the face. dim 2=the vertex. dim 3 always has length=2, with 1st vertex, and 2nd normal.
    */
    //public int[][][] Faces;
    public List<Face> Faces = new List<Face>();
    /**
    * Corresponds to the Faces list. Indicies must be cached to draw DirectX triangles.
    */
    public IndexBuffer facesDirectX = null;
    /**
    * Corresponds to the number of indicies referenced by the facesDirectX IndexBuffer. This relates to triangles, not to Polygons as in the Faces list. Must be a multiple of 3.
    */
    public int NumIndicies = 0;
    public ToothGroup() throws Exception {
        Faces = new List<Face>();
    }

    /**
    * If using DirectX, the facesDirectX IndexBuffer variable must be instantiated in a subsequent call to PrepareForDirectX().
    */
    public ToothGroup copy() throws Exception {
        ToothGroup tg = new ToothGroup();
        tg.Visible = this.Visible;
        tg.PaintColor = this.PaintColor;
        tg.GroupType = this.GroupType;
        tg.Faces = new List<Face>();
        for (int i = 0;i < this.Faces.Count;i++)
        {
            tg.Faces.Add(this.Faces[i].Copy());
        }
        return tg;
    }

    public void prepareForDirectX(Device device) throws Exception {
        cleanupDirectX();
        //Prepare the indicies into an index buffer.
        //When drawing with a single index buffer inside of DirectX, all primitives must be the same type.
        //Furthermore, there are no polygons inside of DirectX, only triangles. Therefore, at this point
        //we break down all faces from polygons into triangles inside of the index buffer so all faces
        //can be drawn using triangles only. This will also allow us to optimize face order later to
        //make the faces display more quickly using caching techniques as well.
        List<int> indexList = new List<int>();
        for (int i = 0;i < Faces.Count;i++)
        {
            for (int j = 1;j < Faces[i].IndexList.Count - 1;j++)
            {
                //We create a triangle fan out of the indcies here for simplicity, preserving the original polygon normal directionality.
                indexList.Add(Faces[i].IndexList[0]);
                indexList.Add(Faces[i].IndexList[j]);
                indexList.Add(Faces[i].IndexList[j + 1]);
            }
        }
        int[] indicies = indexList.ToArray();
        //Calculate the number of unique indicies so we know exactly now many verticies are referenced
        //and then we can properly optimize our face order with Geometry.OptimizeFaces() below.
        List<int> uniqueIndicies = new List<int>();
        for (int i = 0;i < indicies.Length;i++)
        {
            if (uniqueIndicies.IndexOf(indicies[i]) < 0)
            {
                uniqueIndicies.Add(indicies[i]);
            }
             
        }
        //Optimize the triangle order for excellent vertex caching.
        int[] optimizedIndicies = Geometry.OptimizeFaces(indicies, uniqueIndicies.Count);
        int[] optimalFaces = new int[optimizedIndicies.Length];
        for (int f = 0;f < optimalFaces.Length;f += 3)
        {
            int oldFaceBase = 3 * optimizedIndicies[f / 3];
            optimalFaces[f] = indicies[oldFaceBase];
            optimalFaces[f + 1] = indicies[oldFaceBase + 1];
            optimalFaces[f + 2] = indicies[oldFaceBase + 2];
        }
        facesDirectX = new IndexBuffer(int.class, optimalFaces.Length, device, Usage.None, Pool.Managed);
        facesDirectX.SetData(optimalFaces, 0, LockFlags.None);
        NumIndicies = optimalFaces.Length;
    }

    public void cleanupDirectX() throws Exception {
        if (facesDirectX != null)
        {
            facesDirectX.Dispose();
            facesDirectX = null;
        }
         
    }

    public String toString() {
        try
        {
            return GroupType.ToString() + ". Faces:" + Faces.Count.ToString();
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


