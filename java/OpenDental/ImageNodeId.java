//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:19 PM
//

package OpenDental;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.ImageNodeType;

/**
* Because this is a struct, equivalency is based on values, not references.
*/
public class ImageNodeId   
{
    public ImageNodeId() {
    }

    public ImageNodeType NodeType = ImageNodeType.None;
    /**
    * The table to which the primary key refers will differ based on the node type.
    */
    public long PriKey = new long();
}


