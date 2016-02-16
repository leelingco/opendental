//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:22 PM
//

package fyiReporting.RdlDesign;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import fyiReporting.RdlDesign.DesignXmlDraw;

/* ====================================================================
    Copyright (C) 2004-2006  fyiReporting Software, LLC
    This file is part of the fyiReporting RDL project.
	
    The RDL project is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
    For additional information, email info@fyireporting.com or visit
    the website www.fyiReporting.com.
*/
/**
* ReportNames is used to control the names of objects in the report
*/
public class ReportNames   
{
    XmlDocument _doc = new XmlDocument();
    List<XmlNode> _ReportNodes = new List<XmlNode>();
    // array of report nodes; used for tabbing around the nodes
    Dictionary<String, XmlNode> _ReportItems = new Dictionary<String, XmlNode>();
    // name/xmlnode pairs of report items
    Dictionary<String, XmlNode> _Groupings = new Dictionary<String, XmlNode>();
    // name/xmlnode pairs of grouping names
    public ReportNames(XmlDocument rDoc) throws Exception {
        _doc = rDoc;
        buildNames();
    }

    // build the name hash tables
    private void buildNames() throws Exception {
        _ReportItems = new Dictionary<String, XmlNode>();
        _Groupings = new Dictionary<String, XmlNode>();
        _ReportNodes = new List<XmlNode>();
        BuildNamesLoop(_doc.LastChild);
    }

    private void buildNamesLoop(XmlNode xNode) throws Exception {
        if (xNode == null)
            return ;
         
        for (Object __dummyForeachVar0 : xNode)
        {
            XmlNode cNode = (XmlNode)__dummyForeachVar0;
            // this is not a complete list of object names. It doesn't
            //  need to be complete but can be optimized so subobjects aren't
            //  pursued unnecessarily.  However, all reportitems and
            //  grouping must be traversed to get at all the names.
            // List should be built in paint order so
            //  that list of nodes is in correct tab order.
            Name __dummyScrutVar0 = cNode.Name;
            if (__dummyScrutVar0.equals("Report"))
            {
                buildNamesLoop(DesignXmlDraw.findNextInHierarchy(cNode,"PageHeader","ReportItems"));
                buildNamesLoop(DesignXmlDraw.findNextInHierarchy(cNode,"Body","ReportItems"));
                buildNamesLoop(DesignXmlDraw.findNextInHierarchy(cNode,"PageFooter","ReportItems"));
            }
            else // have a name but no subobjects
            if (__dummyScrutVar0.equals("Textbox") || __dummyScrutVar0.equals("Image") || __dummyScrutVar0.equals("Line") || __dummyScrutVar0.equals("Subreport"))
            {
                this.addNode(cNode);
            }
            else if (__dummyScrutVar0.equals("Chart"))
            {
                this.addNode(cNode);
                buildNamesLoop(DesignXmlDraw.findNextInHierarchy(cNode,"SeriesGroupings"));
                buildNamesLoop(DesignXmlDraw.findNextInHierarchy(cNode,"CategoryGroupings"));
            }
            else // named object having subobjects
            if (__dummyScrutVar0.equals("Table"))
            {
                this.addNode(cNode);
                buildNamesLoop(DesignXmlDraw.findNextInHierarchy(cNode,"Header","TableRows"));
                buildNamesLoop(DesignXmlDraw.findNextInHierarchy(cNode,"TableGroups"));
                buildNamesLoop(DesignXmlDraw.findNextInHierarchy(cNode,"Details"));
                buildNamesLoop(DesignXmlDraw.findNextInHierarchy(cNode,"Footer","TableRows"));
            }
            else if (__dummyScrutVar0.equals("List"))
            {
                this.addNode(cNode);
                buildNamesLoop(DesignXmlDraw.findNextInHierarchy(cNode,"Grouping"));
                buildNamesLoop(DesignXmlDraw.findNextInHierarchy(cNode,"ReportItems"));
            }
            else if (__dummyScrutVar0.equals("Rectangle"))
            {
                this.addNode(cNode);
                buildNamesLoop(DesignXmlDraw.findNextInHierarchy(cNode,"ReportItems"));
            }
            else if (__dummyScrutVar0.equals("Matrix"))
            {
                this.addNode(cNode);
                buildNamesLoop(cNode);
            }
            else // don't have a name and don't have named subobjects with names
            if (__dummyScrutVar0.equals("Style") || __dummyScrutVar0.equals("Filters"))
            {
            }
            else if (__dummyScrutVar0.equals("Grouping"))
            {
                XmlAttribute xAttr = cNode.Attributes["Name"];
                if (xAttr == null)
                    this.generateGroupingName(cNode);
                else
                    _Groupings.Add(xAttr.Value, cNode); 
            }
            else
            {
                // don't have a name but could have subobjects with names
                buildNamesLoop(cNode);
            }         
        }
        return ;
    }

    // recursively go thru entire report
    public boolean changeName(XmlNode xNode, String newName) throws Exception {
        XmlNode fNode = new XmlNode();
        RefSupport<XmlNode> refVar___0 = new RefSupport<XmlNode>();
        _ReportItems.TryGetValue(newName, refVar___0);
        fNode = refVar___0.getValue();
        if (fNode != null)
        {
            if (fNode != xNode)
                return false;
             
            return true;
        }
         
        // this would cause a duplicate
        // newName and oldName are the same
        XmlAttribute xAttr = xNode.Attributes["Name"];
        // Remove the old name (if one exists)
        if (xAttr != null)
        {
            String oldName = xAttr.Value;
            this._ReportItems.Remove(oldName);
        }
         
        // Set the new name
        setElementAttribute(xNode,"Name",newName);
        _ReportItems.Add(newName, xNode);
        return true;
    }

    public boolean changeGroupName(XmlNode xNode, String newName) throws Exception {
        XmlNode fNode = new XmlNode();
        RefSupport<XmlNode> refVar___1 = new RefSupport<XmlNode>();
        _Groupings.TryGetValue(newName, refVar___1);
        fNode = refVar___1.getValue();
        if (fNode != null)
        {
            if (fNode != xNode)
                return false;
             
            return true;
        }
         
        // this would cause a duplicate
        // newName and oldName are the same
        XmlAttribute xAttr = xNode.Attributes["Name"];
        // Remove the old name (if one exists)
        if (xAttr != null)
        {
            String oldName = xAttr.Value;
            this._Groupings.Remove(oldName);
        }
         
        // Set the new name
        setElementAttribute(xNode,"Name",newName);
        _Groupings.Add(newName, xNode);
        return true;
    }

    public XmlNode findNext(XmlNode xNode) throws Exception {
        if (_ReportNodes.Count <= 0)
            return null;
         
        if (xNode == null)
            return _ReportNodes[0];
         
        boolean bNext = false;
        for (Object __dummyForeachVar1 : _ReportNodes)
        {
            XmlNode nNode = (XmlNode)__dummyForeachVar1;
            if (bNext)
                return nNode;
             
            if (nNode == xNode)
                bNext = true;
             
        }
        return _ReportNodes[0];
    }

    public XmlNode findPrior(XmlNode xNode) throws Exception {
        if (_ReportItems.Count <= 0)
            return null;
         
        if (xNode == null)
            return _ReportNodes[0];
         
        XmlNode previous = null;
        for (Object __dummyForeachVar2 : _ReportNodes)
        {
            XmlNode nNode = (XmlNode)__dummyForeachVar2;
            if (nNode == xNode)
            {
                if (previous == null)
                    return _ReportNodes[_ReportNodes.Count - 1];
                else
                    return previous; 
            }
             
            previous = nNode;
        }
        return _ReportNodes[_ReportNodes.Count - 1];
    }

    public ICollection getReportItemNames() throws Exception {
        return _ReportItems.Keys;
    }

    public ICollection getReportItems() throws Exception {
        return _ReportItems.Values;
    }

    public void addNode(XmlNode xNode) throws Exception {
        XmlAttribute xAttr = xNode.Attributes["Name"];
        if (xAttr == null)
            generateName(xNode);
        else // when no name; we generate one
        if (_ReportItems.ContainsKey(xAttr.Value))
            generateName(xNode);
        else
        {
            // when duplicate name; we generate another; this can be a problem but...
            this._ReportItems.Add(xAttr.Value, xNode);
            this._ReportNodes.Add(xNode);
        }  
    }

    /**
    * Generates a new name based on the object type.   Replaces the old name in the node but
    * does not delete it from the hash.   Use when you're copying nodes and need another name.
    * 
    *  @param xNode 
    *  @return
    */
    public String generateName(XmlNode xNode) throws Exception {
        String basename = xNode.Name;
        String name = new String();
        int index = 1;
        while (true)
        {
            name = basename + index.ToString();
            if (!_ReportItems.ContainsKey(name))
            {
                setElementAttribute(xNode,"Name",name);
                break;
            }
             
            index++;
        }
        _ReportItems.Add(name, xNode);
        // add generated name
        this._ReportNodes.Add(xNode);
        return name;
    }

    public String generateGroupingName(XmlNode xNode) throws Exception {
        String basename = xNode.ParentNode.Name + "Group";
        String name = new String();
        int index = 1;
        List<String> dsets = new List<String>(this.getDataSetNames());
        while (true)
        {
            name = basename + index.ToString();
            if (_Groupings.ContainsKey(name) == false && dsets.IndexOf(name) < 0 && _ReportItems.ContainsKey(name) == false)
            {
                setElementAttribute(xNode,"Name",name);
                break;
            }
             
            index++;
        }
        _Groupings.Add(name, xNode);
        return name;
    }

    public String nameError(XmlNode xNode, String name) throws Exception {
        if (name == null || name.Trim().Length <= 0)
            return "Name must be provided.";
         
        if (!isNameValid(name))
            return "Invalid characters in name.";
         
        XmlNode fNode = new XmlNode();
        RefSupport<XmlNode> refVar___2 = new RefSupport<XmlNode>();
        _ReportItems.TryGetValue(name, refVar___2);
        fNode = refVar___2.getValue();
        if (fNode == xNode)
            return null;
         
        if (fNode != null)
            return "Duplicate name.";
         
        // Grouping; also restrict to not being same name as any group or dataset
        if (StringSupport.equals(xNode.Name, "Grouping"))
        {
            RefSupport<XmlNode> refVar___3 = new RefSupport<XmlNode>();
            _Groupings.TryGetValue(name, refVar___3);
            fNode = refVar___3.getValue();
            if (fNode != null)
                return "Duplicate name.";
             
            List<String> dsets = new List<String>(this.getDataSetNames());
            if (dsets.IndexOf(name) >= 0)
                return "Duplicate name.";
             
        }
         
        return null;
    }

    public String groupingNameCheck(XmlNode xNode, String name) throws Exception {
        if (name == null || name.Trim().Length <= 0)
            return "Name must be provided.";
         
        if (!isNameValid(name))
            return "Invalid characters in name.";
         
        // Grouping; also restrict to not being same name as any group or dataset
        XmlNode fNode = new XmlNode();
        RefSupport<XmlNode> refVar___4 = new RefSupport<XmlNode>();
        _Groupings.TryGetValue(name, refVar___4);
        fNode = refVar___4.getValue();
        if (fNode != null && fNode != xNode)
            return "Duplicate name.";
         
        List<String> dsets = new List<String>(this.getDataSetNames());
        if (dsets.IndexOf(name) >= 0)
            return "Duplicate name.";
         
        return null;
    }

    static public boolean isNameValid(String name) throws Exception {
        if (name == null || name.Length == 0)
            return false;
         
        // TODO use algorithm in http://www.unicode.org/unicode/reports/tr15/tr15-18.html#Programming%20Language%20Identifiers
        //  below regular expression isn't completely correct but matches most ascii language users
        //  expectations
        Match m = Regex.Match(name, "\\A[a-zA-Z_]+[a-zA-Z_0-9]*\\Z");
        return m.Success;
    }

    public void removeName(XmlNode xNode) throws Exception {
        if (xNode == null)
            return ;
         
        XmlAttribute xAttr = xNode.Attributes["Name"];
        if (xAttr == null)
            return ;
         
        _ReportItems.Remove(xAttr.Value);
        _ReportNodes.Remove(xNode);
        removeChildren(xNode);
    }

    private void removeChildren(XmlNode xNode) throws Exception {
        XmlAttribute xAttr = new XmlAttribute();
        for (Object __dummyForeachVar3 : xNode.ChildNodes)
        {
            XmlNode cNode = (XmlNode)__dummyForeachVar3;
            Name __dummyScrutVar1 = cNode.Name;
            // have a name but no subobjects
            if (__dummyScrutVar1.equals("Textbox") || __dummyScrutVar1.equals("Image") || __dummyScrutVar1.equals("Line") || __dummyScrutVar1.equals("Subreport") || __dummyScrutVar1.equals("Chart"))
            {
                xAttr = cNode.Attributes["Name"];
                if (xAttr != null)
                {
                    _ReportItems.Remove(xAttr.Value);
                    _ReportNodes.Remove(cNode);
                }
                 
            }
            else // named object having subobjects
            if (__dummyScrutVar1.equals("Table") || __dummyScrutVar1.equals("List") || __dummyScrutVar1.equals("Rectangle") || __dummyScrutVar1.equals("Matrix"))
            {
                removeChildren(cNode);
                xAttr = cNode.Attributes["Name"];
                if (xAttr != null)
                {
                    _ReportItems.Remove(xAttr.Value);
                    _ReportNodes.Remove(cNode);
                }
                 
            }
            else // don't have a name and don't have named subobjects with names
            if (__dummyScrutVar1.equals("Style") || __dummyScrutVar1.equals("Filters"))
            {
            }
            else
            {
                // don't have a name but could have subobjects with names
                removeChildren(cNode);
            }   
        }
    }

    // recursively go down the hierarchy
    private void setElementAttribute(XmlNode parent, String name, String val) throws Exception {
        XmlAttribute attr = parent.Attributes[name];
        if (attr != null)
        {
            attr.Value = val;
        }
        else
        {
            attr = _doc.CreateAttribute(name);
            attr.Value = val;
            parent.Attributes.Append(attr);
        } 
        return ;
    }

    /**
    * Returns a collection of the GroupingNames
    */
    public String[] getGroupingNames() throws Exception {
        if (_Groupings == null || _Groupings.Count == 0)
            return null;
         
        String[] gn = new String[_Groupings.Count];
        int i = 0;
        for (Object __dummyForeachVar4 : _Groupings.Keys)
        {
            String o = (String)__dummyForeachVar4;
            gn[i++] = o;
        }
        return gn;
    }

    /**
    * Returns a collection of the DataSetNames
    */
    public String[] getDataSetNames() throws Exception {
        List<String> ds = new List<String>();
        XmlNode rNode = _doc.LastChild;
        XmlNode node = DesignXmlDraw.findNextInHierarchy(rNode,"DataSets");
        if (node == null)
            return ds.ToArray();
         
        for (Object __dummyForeachVar5 : node.ChildNodes)
        {
            XmlNode cNode = (XmlNode)__dummyForeachVar5;
            if (cNode.NodeType != XmlNodeType.Element || !StringSupport.equals(cNode.Name, "DataSet"))
                continue;
             
            XmlAttribute xAttr = cNode.Attributes["Name"];
            if (xAttr != null)
                ds.Add(xAttr.Value);
             
        }
        return ds.ToArray();
    }

    public XmlNode dataSourceName(String dsn) throws Exception {
        XmlNode rNode = _doc.LastChild;
        XmlNode node = DesignXmlDraw.findNextInHierarchy(rNode,"DataSources");
        if (node == null)
            return null;
         
        for (Object __dummyForeachVar6 : node.ChildNodes)
        {
            XmlNode cNode = (XmlNode)__dummyForeachVar6;
            if (!StringSupport.equals(cNode.Name, "DataSource"))
                continue;
             
            XmlAttribute xAttr = cNode.Attributes["Name"];
            if (xAttr != null && StringSupport.equals(xAttr.Value, dsn))
                return cNode;
             
        }
        return null;
    }

    /**
    * Returns a collection of the DataSourceNames
    */
    public String[] getDataSourceNames() throws Exception {
        List<String> ds = new List<String>();
        XmlNode rNode = _doc.LastChild;
        XmlNode node = DesignXmlDraw.findNextInHierarchy(rNode,"DataSources");
        if (node == null)
            return ds.ToArray();
         
        for (Object __dummyForeachVar7 : node.ChildNodes)
        {
            XmlNode cNode = (XmlNode)__dummyForeachVar7;
            if (cNode.NodeType != XmlNodeType.Element || !StringSupport.equals(cNode.Name, "DataSource"))
                continue;
             
            XmlAttribute xAttr = cNode.Attributes["Name"];
            if (xAttr != null)
                ds.Add(xAttr.Value);
             
        }
        return ds.ToArray();
    }

    /**
    * Returns a collection of the EmbeddedImage names
    */
    public String[] getEmbeddedImageNames() throws Exception {
        List<String> ds = new List<String>();
        XmlNode rNode = _doc.LastChild;
        XmlNode node = DesignXmlDraw.findNextInHierarchy(rNode,"EmbeddedImages");
        if (node == null)
            return ds.ToArray();
         
        for (Object __dummyForeachVar8 : node.ChildNodes)
        {
            XmlNode cNode = (XmlNode)__dummyForeachVar8;
            if (cNode.NodeType != XmlNodeType.Element || !StringSupport.equals(cNode.Name, "EmbeddedImage"))
                continue;
             
            XmlAttribute xAttr = cNode.Attributes["Name"];
            if (xAttr != null)
                ds.Add(xAttr.Value);
             
        }
        return ds.ToArray();
    }

    /**
    * Gets the fields within the requested dataset.  If dataset is null then the first
    * dataset is used.
    * 
    *  @param dataSetName 
    *  @param asExpression When true names are returned as expressions.
    *  @return
    */
    public String[] getFields(String dataSetName, boolean asExpression) throws Exception {
        XmlNode nodes = DesignXmlDraw.FindNextInHierarchy(_doc.LastChild, "DataSets");
        if (nodes == null || !nodes.HasChildNodes)
            return null;
         
        // Find the right dataset
        XmlNode dataSet = null;
        for (Object __dummyForeachVar9 : nodes.ChildNodes)
        {
            XmlNode ds = (XmlNode)__dummyForeachVar9;
            if (!StringSupport.equals(ds.Name, "DataSet"))
                continue;
             
            XmlAttribute xAttr = ds.Attributes["Name"];
            if (xAttr == null)
                continue;
             
            if (StringSupport.equals(xAttr.Value, dataSetName) || dataSetName == null || StringSupport.equals(dataSetName, ""))
            {
                dataSet = ds;
                break;
            }
             
        }
        if (dataSet == null)
            return null;
         
        // Find the fields
        XmlNode fields = DesignXmlDraw.findNextInHierarchy(dataSet,"Fields");
        if (fields == null || !fields.HasChildNodes)
            return null;
         
        StringCollection st = new StringCollection();
        for (Object __dummyForeachVar10 : fields.ChildNodes)
        {
            XmlNode f = (XmlNode)__dummyForeachVar10;
            XmlAttribute xAttr = f.Attributes["Name"];
            if (xAttr == null)
                continue;
             
            if (asExpression)
                st.Add(String.Format("=Fields!{0}.Value", xAttr.Value));
            else
                st.Add(xAttr.Value); 
        }
        if (st.Count <= 0)
            return null;
         
        String[] result = new String[st.Count];
        st.CopyTo(result, 0);
        return result;
    }

    public String[] getReportParameters(boolean asExpression) throws Exception {
        XmlNode rNode = _doc.LastChild;
        XmlNode rpsNode = DesignXmlDraw.findNextInHierarchy(rNode,"ReportParameters");
        if (rpsNode == null)
            return null;
         
        StringCollection st = new StringCollection();
        for (Object __dummyForeachVar11 : rpsNode)
        {
            XmlNode repNode = (XmlNode)__dummyForeachVar11;
            if (!StringSupport.equals(repNode.Name, "ReportParameter"))
                continue;
             
            XmlAttribute nAttr = repNode.Attributes["Name"];
            if (nAttr == null)
                continue;
             
            // shouldn't really happen
            if (asExpression)
                st.Add(String.Format("=Parameters!{0}.Value", nAttr.Value));
            else
                st.Add(nAttr.Value); 
        }
        if (st.Count <= 0)
            return null;
         
        String[] result = new String[st.Count];
        st.CopyTo(result, 0);
        return result;
    }

}


