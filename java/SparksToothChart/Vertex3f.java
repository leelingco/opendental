//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:35 PM
//

package SparksToothChart;

import SparksToothChart.Vertex3f;

/**
* 
*/
public class Vertex3f   
{
    public float X = new float();
    public float Y = new float();
    public float Z = new float();
    public Vertex3f() throws Exception {
    }

    public Vertex3f(float x, float y, float z) throws Exception {
        X = x;
        Y = y;
        Z = z;
    }

    public float[] getFloatArray() throws Exception {
        float[] retVal = new float[3];
        retVal[0] = X;
        retVal[1] = Y;
        retVal[2] = Z;
        return retVal;
    }

    public String toString() {
        try
        {
            return X.ToString() + "," + Y.ToString() + "," + Z.ToString();
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

    public Vertex3f copy() throws Exception {
        Vertex3f vf = new Vertex3f(this.X,this.Y,this.Z);
        return vf;
    }

}


