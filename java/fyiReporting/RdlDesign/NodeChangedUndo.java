//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:23 PM
//

package fyiReporting.RdlDesign;

import fyiReporting.RdlDesign.UndoItem;

/**
     * Can be used for both CharacterData and ProcessingInstruction nodes.
     */
public class NodeChangedUndo   implements UndoItem
{
    String oldValue = new String();
    XmlNode node = new XmlNode();
    public NodeChangedUndo(XmlNodeChangedEventArgs e, Object pValue) throws Exception {
        oldValue = pValue instanceof String ? (String)pValue : (String)null;
        node = e.Node;
    }

    public void undo() throws Exception {
        node.Value = oldValue;
    }

    public String getDescription() throws Exception {
        return "change";
    }

    static public Object previousState(XmlNodeChangedEventArgs e) throws Exception {
        return e.Node.Value;
    }

}


