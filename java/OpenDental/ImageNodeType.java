//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:19 PM
//

package OpenDental;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public enum ImageNodeType
{
    //could use an == overload here, but don't know syntax right now.
    /**
    * This is the initial empty id.  Used instead of a null ImageNodeId
    */
    None,
    /**
    * PriKey is DefNum
    */
    Category,
    /**
    * PriKey is DocNum
    */
    Doc,
    /**
    * PriKey is MountNum
    */
    Mount,
    /**
    * PriKey is EobAttachNum
    */
    Eob,
    /**
    * PriKey is EhrAmendmentNum
    */
    Amd
}

