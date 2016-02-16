//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:34 PM
//

package SparksToothChart;

import SparksToothChart.Vertex3f;

/**
* A series of vertices that are all connected into one continuous simple line.
*/
public class LineSimple   
{
    public List<Vertex3f> Vertices = new List<Vertex3f>();
    /**
    * 
    */
    public LineSimple() throws Exception {
        Vertices = new List<Vertex3f>();
    }

    /**
    * Specify a line as a series of points.  It's implied that they are grouped by threes.
    */
    public LineSimple(float... coords) throws Exception {
        Vertices = new List<Vertex3f>();
        Vertex3f vertex = new Vertex3f();
        for (int i = 0;i < coords.Length;i++)
        {
            vertex.X = coords[i];
            i++;
            vertex.Y = coords[i];
            i++;
            vertex.Z = coords[i];
            Vertices.Add(vertex);
            vertex = new Vertex3f();
        }
    }

}


