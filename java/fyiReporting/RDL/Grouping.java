//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:26 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Chart;
import fyiReporting.RDL.Custom;
import fyiReporting.RDL.DataElementOutputEnum;
import fyiReporting.RDL.Expression;
import fyiReporting.RDL.ExpressionType;
import fyiReporting.RDL.Filters;
import fyiReporting.RDL.GroupExpressions;
import fyiReporting.RDL.List;
import fyiReporting.RDL.Matrix;
import fyiReporting.RDL.Name;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Rows;
import fyiReporting.RDL.Table;
import fyiReporting.RDL.Textbox;
import fyiReporting.RDL.XmlUtil;

/* ====================================================================
    Copyright (C) 2004-2006  fyiReporting Software, LLC
    This file is part of the fyiReporting RDL project.
	
    This library is free software; you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation; either version 2.1 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
    You should have received a copy of the GNU Lesser General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
    For additional information, email info@fyireporting.com or visit
    the website www.fyiReporting.com.
*/
/**
* Grouping definition: expressions forming group, paging forced when group changes, ...
*/
public class Grouping  extends ReportLink 
{
    Name _Name;
    // Name of the Grouping (for use in
    // RunningValue and RowNumber)
    // No two grouping elements may have the
    // same name. No grouping element may
    // have the same name as a data set or a data
    // region
    Expression _Label;
    // (string) A label to identify an instance of the group
    //within the client UI (to provide a userfriendly
    // label for searching). See ReportItem.Label
    GroupExpressions _GroupExpressions;
    //The expressions to group the data by
    boolean _PageBreakAtStart = new boolean();
    // Indicates the report should page break at
    // the start of the group.
    // Not valid for column groupings in Matrix regions.
    boolean _PageBreakAtEnd = new boolean();
    // Indicates the report should page break at
    // the end of the group.
    // Not valid for column groupings in Matrix regions.
    Custom _Custom;
    // Custom information to be passed to the
    // report output component.
    Filters _Filters;
    // Filters to apply to each instance of the group.
    Expression _ParentGroup;
    //(Variant)
    //An expression that identifies the parent
    //group in a recursive hierarchy. Only
    //allowed if the group has exactly one group
    //expression.
    //Indicates the following:
    //1. Groups should be sorted according
    //to the recursive hierarchy (Sort is
    //still used to sort peer groups).
    //2. Labels (in the document map)
    //should be placed/indented
    //according to the recursive
    //hierarchy.
    //3. Intra-group show/hide should
    //toggle items according to the
    //recursive hierarchy (see
    //ToggleItem)
    //If filters on the group eliminate a group
    // instance’s parent, it is instead treated as a
    // child of the parent’s parent.
    String _DataElementName = new String();
    // The name to use for the data element for
    // instances of this group.
    // Default: Name of the group
    String _DataCollectionName = new String();
    // The name to use for the data element for
    // the collection of all instances of this group.
    // Default: “DataElementName_Collection”
    DataElementOutputEnum _DataElementOutput = DataElementOutputEnum.Output;
    // Indicates whether the group should appear
    // in a data rendering.  Default: Output
    List<Textbox> _HideDuplicates = new List<Textbox>();
    // holds any textboxes that use this as a hideduplicate scope
    boolean _InMatrix = new boolean();
    // true if grouping is in a matrix
    public Grouping(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Name = null;
        _Label = null;
        _GroupExpressions = null;
        _PageBreakAtStart = false;
        _PageBreakAtEnd = false;
        _Custom = null;
        _Filters = null;
        _ParentGroup = null;
        _DataElementName = null;
        _DataCollectionName = null;
        _DataElementOutput = DataElementOutputEnum.Output;
        _HideDuplicates = null;
        for (Object __dummyForeachVar0 : xNode.Attributes)
        {
            // Run thru the attributes
            XmlAttribute xAttr = (XmlAttribute)__dummyForeachVar0;
            Name __dummyScrutVar0 = xAttr.Name;
            if (__dummyScrutVar0.equals("Name"))
            {
                _Name = new Name(xAttr.Value);
            }
             
        }
        for (Object __dummyForeachVar1 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar1;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar1 = xNodeLoop.Name;
            if (__dummyScrutVar1.equals("Label"))
            {
                _Label = new Expression(r,this,xNodeLoop,ExpressionType.String);
            }
            else if (__dummyScrutVar1.equals("GroupExpressions"))
            {
                _GroupExpressions = new GroupExpressions(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar1.equals("PageBreakAtStart"))
            {
                _PageBreakAtStart = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar1.equals("PageBreakAtEnd"))
            {
                _PageBreakAtEnd = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar1.equals("Custom"))
            {
                _Custom = new Custom(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar1.equals("Filters"))
            {
                _Filters = new Filters(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar1.equals("Parent"))
            {
                _ParentGroup = new Expression(r,this,xNodeLoop,ExpressionType.Variant);
            }
            else if (__dummyScrutVar1.equals("DataElementName"))
            {
                _DataElementName = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar1.equals("DataCollectionName"))
            {
                _DataCollectionName = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar1.equals("DataElementOutput"))
            {
                _DataElementOutput = fyiReporting.RDL.DataElementOutput.GetStyle(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown Grouping element '" + xNodeLoop.Name + "' ignored.");
            }          
        }
        if (this.getName() != null)
        {
            try
            {
                OwnerReport.getLUAggrScope().Add(this.getName().getNm(), this);
            }
            catch (Exception __dummyCatchVar0)
            {
                // add to referenceable Grouping's
                // wish duplicate had its own exception
                OwnerReport.rl.logError(8,"Duplicate Grouping name '" + this.getName().getNm() + "'.");
            }
        
        }
         
        if (_GroupExpressions == null)
            OwnerReport.rl.logError(8,"Group Expressions are required within group '" + (this.getName() == null ? "unnamed" : this.getName().getNm()) + "'.");
         
    }

    // Handle parsing of function in final pass
    public void finalPass() throws Exception {
        if (_Label != null)
            _Label.finalPass();
         
        if (_GroupExpressions != null)
            _GroupExpressions.finalPass();
         
        if (_Custom != null)
            _Custom.finalPass();
         
        if (_Filters != null)
            _Filters.finalPass();
         
        if (_ParentGroup != null)
            _ParentGroup.finalPass();
         
        // Determine if group is defined inside of a Matrix;  these get
        //   different runtime expression handling in FunctionAggr
        _InMatrix = false;
        for (ReportLink rl = this.Parent;rl != null;rl = rl.Parent)
        {
            if (rl instanceof Matrix)
            {
                _InMatrix = true;
                break;
            }
             
            if (rl instanceof Table || rl instanceof List || rl instanceof Chart)
                break;
             
        }
        return ;
    }

    public void addHideDuplicates(Textbox tb) throws Exception {
        if (_HideDuplicates == null)
            _HideDuplicates = new List<Textbox>();
         
        _HideDuplicates.Add(tb);
    }

    public void resetHideDuplicates(fyiReporting.RDL.Report rpt) throws Exception {
        if (_HideDuplicates == null)
            return ;
         
        for (Object __dummyForeachVar2 : _HideDuplicates)
        {
            Textbox tb = (Textbox)__dummyForeachVar2;
            tb.resetPrevious(rpt);
        }
    }

    public boolean getInMatrix() throws Exception {
        return _InMatrix;
    }

    public Name getName() throws Exception {
        return _Name;
    }

    public void setName(Name value) throws Exception {
        _Name = value;
    }

    public Expression getLabel() throws Exception {
        return _Label;
    }

    public void setLabel(Expression value) throws Exception {
        _Label = value;
    }

    public GroupExpressions getGroupExpressions() throws Exception {
        return _GroupExpressions;
    }

    public void setGroupExpressions(GroupExpressions value) throws Exception {
        _GroupExpressions = value;
    }

    public boolean getPageBreakAtStart() throws Exception {
        return _PageBreakAtStart;
    }

    public void setPageBreakAtStart(boolean value) throws Exception {
        _PageBreakAtStart = value;
    }

    public boolean getPageBreakAtEnd() throws Exception {
        return _PageBreakAtEnd;
    }

    public void setPageBreakAtEnd(boolean value) throws Exception {
        _PageBreakAtEnd = value;
    }

    public Custom getCustom() throws Exception {
        return _Custom;
    }

    public void setCustom(Custom value) throws Exception {
        _Custom = value;
    }

    public Filters getFilters() throws Exception {
        return _Filters;
    }

    public void setFilters(Filters value) throws Exception {
        _Filters = value;
    }

    public Expression getParentGroup() throws Exception {
        return _ParentGroup;
    }

    public void setParentGroup(Expression value) throws Exception {
        _ParentGroup = value;
    }

    public String getDataElementName() throws Exception {
        if (_DataElementName == null)
        {
            if (this.getName() != null)
                return this.getName().getNm();
             
        }
         
        return _DataElementName;
    }

    public void setDataElementName(String value) throws Exception {
        _DataElementName = value;
    }

    public String getDataCollectionName() throws Exception {
        if (_DataCollectionName == null)
            return getDataElementName() + "_Collection";
         
        return _DataCollectionName;
    }

    public void setDataCollectionName(String value) throws Exception {
        _DataCollectionName = value;
    }

    public DataElementOutputEnum getDataElementOutput() throws Exception {
        return _DataElementOutput;
    }

    public void setDataElementOutput(DataElementOutputEnum value) throws Exception {
        _DataElementOutput = value;
    }

    public int getIndex(fyiReporting.RDL.Report rpt) throws Exception {
        WorkClass wc = getValue(rpt);
        return wc.index;
    }

    public void setIndex(fyiReporting.RDL.Report rpt, int i) throws Exception {
        WorkClass wc = getValue(rpt);
        wc.index = i;
        return ;
    }

    public Rows getRows(fyiReporting.RDL.Report rpt) throws Exception {
        WorkClass wc = getValue(rpt);
        return wc.rows;
    }

    public void setRows(fyiReporting.RDL.Report rpt, Rows rows) throws Exception {
        WorkClass wc = getValue(rpt);
        wc.rows = rows;
        return ;
    }

    private WorkClass getValue(fyiReporting.RDL.Report rpt) throws Exception {
        WorkClass wc = rpt.getCache().get(this,"wc") instanceof WorkClass ? (WorkClass)rpt.getCache().get(this,"wc") : (WorkClass)null;
        if (wc == null)
        {
            wc = new WorkClass();
            rpt.getCache().add(this,"wc",wc);
        }
         
        return wc;
    }

    static class WorkClass   
    {
        public int index = new int();
        // used by tables (and others) to set grouping values
        public Rows rows;
        // used by matrixes to get/set grouping values
        public WorkClass() throws Exception {
            index = -1;
        }
    
    }

}


