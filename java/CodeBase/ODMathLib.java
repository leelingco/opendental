//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:25 PM
//

package CodeBase;

import CS2JNet.JavaSupport.language.RefSupport;

public class ODMathLib   
{
    public static void swap(RefSupport<float> x1, RefSupport<float> x2) throws Exception {
        float temp = x1.getValue();
        x1.setValue(x2.getValue());
        x2.setValue(temp);
    }

    /**
    * Returns the closed intersection of the given two segments [x1,x2] and [x3,x4]. Returns no floats is there is no intersection and returns 2 floats if the intersection is a segment (although both segment points may be the same).
    */
    public static float[] intersectSegments(float x1, float x2, float x3, float x4) throws Exception {
        if (x2 < x1)
        {
            RefSupport<float> refVar___0 = new RefSupport<float>(x1);
            RefSupport<float> refVar___1 = new RefSupport<float>(x2);
            swap(refVar___0,refVar___1);
            x1 = refVar___0.getValue();
            x2 = refVar___1.getValue();
        }
         
        if (x4 < x3)
        {
            RefSupport<float> refVar___2 = new RefSupport<float>(x3);
            RefSupport<float> refVar___3 = new RefSupport<float>(x4);
            swap(refVar___2,refVar___3);
            x3 = refVar___2.getValue();
            x4 = refVar___3.getValue();
        }
         
        if (x4 < x1 || x3 > x2)
        {
            return new float[0];
        }
         
        return new float[]{ Math.Max(x1, x3), Math.Min(x2, x4) };
    }

    //No intersection.
    /**
    * Returns the intersection of the given two rectangles as a set of 4 floats in the order (x,y,w,h), or returns an array of 0 floats if the two rectangles do not intersect.
    */
    public static float[] intersectRectangles(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2) throws Exception {
        float[] xIntersect = intersectSegments(x1,x1 + w1,x2,x2 + w2);
        float[] yIntersect = intersectSegments(y1,y1 + h1,y2,y2 + h2);
        if (xIntersect.Length == 0 || yIntersect.Length == 0)
        {
            return new float[0];
        }
         
        return new float[]{ xIntersect[0], yIntersect[0], xIntersect[1] - xIntersect[0], yIntersect[1] - yIntersect[0] };
    }

}


//No intersection?