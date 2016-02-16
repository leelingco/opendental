//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:23 PM
//

package fyiReporting.RdlDesign;

import fyiReporting.RdlDesign.UndoItem;

public class NodeInsertedUndo   implements UndoItem
{
    XmlNode iNode = new XmlNode();
    public NodeInsertedUndo(XmlNodeChangedEventArgs e, Object previous) throws Exception {
        iNode = e.Node;
    }

    public void undo() throws Exception {
        XmlNode parent = iNode.ParentNode;
        if (parent == null)
        {
            return ;
        }
         
        // happens with attributes but doesn't affect the undo??
        parent.RemoveChild(iNode);
    }

    public String getDescription() throws Exception {
        return "insert";
    }

    // could be more explicit using XmlNodeType but ...
    static public Object previousState(XmlNodeChangedEventArgs e) throws Exception {
        return null;
    }

}


