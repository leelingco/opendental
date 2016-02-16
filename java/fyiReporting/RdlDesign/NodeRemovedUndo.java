//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:23 PM
//

package fyiReporting.RdlDesign;

import fyiReporting.RdlDesign.UndoItem;

public class NodeRemovedUndo   implements UndoItem
{
    public XmlNode removedNode = new XmlNode();
    public XmlNode parentNode = new XmlNode();
    public XmlNode nextSibling = new XmlNode();
    public NodeRemovedUndo(XmlNodeChangedEventArgs e, Object previous) throws Exception {
        removedNode = e.Node;
        parentNode = e.OldParent;
        nextSibling = previous instanceof XmlNode ? (XmlNode)previous : (XmlNode)null;
    }

    public void undo() throws Exception {
        parentNode.InsertBefore(removedNode, nextSibling);
    }

    public String getDescription() throws Exception {
        return "remove";
    }

    // could be more specific using NodeType
    static public Object previousState(XmlNodeChangedEventArgs e) throws Exception {
        return e.Node.NextSibling;
    }

}


