//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentalWpf;


public class ExtensionMethods   
{
    /**
    * Extension Method to get the top of the shape.  Only works so far if parent control is a Canvas and if the shape is set relative to
    */
    public static double top(/* parameter modifiers are not yet supported this */Rectangle rect) throws Exception {
        return Canvas.GetTop(rect);
    }

    /**
    * Extension Method to get the bottom of the shape.  Only works so far if parent control is a Canvas.
    */
    public static double bottom(/* parameter modifiers are not yet supported this */Rectangle rect) throws Exception {
        return Canvas.GetTop(rect) + rect.Height;
    }

    /**
    * Extension Method to get the left of the shape.  Only works so far if parent control is a Canvas.
    */
    public static double left(/* parameter modifiers are not yet supported this */Rectangle rect) throws Exception {
        return Canvas.GetLeft(rect);
    }

    /**
    * Extension Method to get the right of the shape.  Only works so far if parent control is a Canvas.
    */
    public static double right(/* parameter modifiers are not yet supported this */Rectangle rect) throws Exception {
        return Canvas.GetLeft(rect) + rect.Width;
    }

}


