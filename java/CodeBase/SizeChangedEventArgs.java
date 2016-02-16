//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:25 PM
//

package CodeBase;


public class SizeChangedEventArgs  extends EventArgs 
{
    private Size newSize = new Size();
    /**
    * The new size of the control that has been resized.
    */
    public Size getNewSize() throws Exception {
        return (newSize);
    }

    public SizeChangedEventArgs(Size newSize) throws Exception {
        this.newSize = newSize;
    }

}


