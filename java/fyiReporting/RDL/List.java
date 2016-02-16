//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:26 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.DataInstanceElementOutputEnum;
import fyiReporting.RDL.DataRegion;
import fyiReporting.RDL.Filter;
import fyiReporting.RDL.GroupEntry;
import fyiReporting.RDL.GroupExpression;
import fyiReporting.RDL.Grouping;
import fyiReporting.RDL.IPresent;
import fyiReporting.RDL.List;
import fyiReporting.RDL.Page;
import fyiReporting.RDL.Pages;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportItem;
import fyiReporting.RDL.ReportItems;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.Rows;
import fyiReporting.RDL.Sorting;
import fyiReporting.RDL.Textbox;

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
* Represents the report item for a List (i.e. absolute positioning)
*/
public class List  extends DataRegion 
{
    Grouping _Grouping;
    //The expressions to group the data by
    // Required if there are any DataRegions
    // contained within this List
    Sorting _Sorting;
    // The expressions to sort the repeated list regions by
    ReportItems _ReportItems;
    // The elements of the list layout
    String _DataInstanceName = new String();
    // The name to use for the data element for the
    // each instance of this list. Ignored if there is a
    // grouping for the list.
    // Default: "Item"
    DataInstanceElementOutputEnum _DataInstanceElementOutput = DataInstanceElementOutputEnum.Output;
    // Indicates whether the list instances should
    // appear in a data rendering. Ignored if there is
    // a grouping for the list.  Default: output
    boolean _CanGrow = new boolean();
    // indicates that row height can increase in size
    List<Textbox> _GrowList = new List<Textbox>();
    // list of TextBox's that need to be checked for growth
    public List(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p, xNode);
        _Grouping = null;
        _Sorting = null;
        _ReportItems = null;
        _DataInstanceName = "Item";
        _DataInstanceElementOutput = DataInstanceElementOutputEnum.Output;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("Grouping"))
            {
                _Grouping = new Grouping(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Sorting"))
            {
                _Sorting = new Sorting(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("ReportItems"))
            {
                _ReportItems = new ReportItems(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("DataInstanceName"))
            {
                _DataInstanceName = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar0.equals("DataInstanceElementOutput"))
            {
                _DataInstanceElementOutput = fyiReporting.RDL.DataInstanceElementOutput.GetStyle(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else
            {
                if (dataRegionElement(xNodeLoop))
                    break;
                 
                // try at DataRegion level
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown List element '" + xNodeLoop.Name + "' ignored.");
            }     
        }
        dataRegionFinish();
    }

    // Tidy up the DataRegion
    public void finalPass() throws Exception {
        super.finalPass();
        if (_Grouping != null)
            _Grouping.finalPass();
         
        if (_Sorting != null)
            _Sorting.finalPass();
         
        if (_ReportItems != null)
            _ReportItems.finalPass();
         
        // determine if the size is dynamic depending on any of its
        //   contained textbox have cangrow true
        if (getReportItems() == null)
            return ;
         
        for (Object __dummyForeachVar1 : this.getReportItems().getItems())
        {
            // no report items in List region
            ReportItem ri = (ReportItem)__dummyForeachVar1;
            if (!(ri instanceof Textbox))
                continue;
             
            Textbox tb = ri instanceof Textbox ? (Textbox)ri : (Textbox)null;
            if (tb.getCanGrow())
            {
                if (this._GrowList == null)
                    _GrowList = new List<Textbox>();
                 
                _GrowList.Add(tb);
                _CanGrow = true;
            }
             
        }
        if (_CanGrow)
            // shrink down the resulting list
            _GrowList.TrimExcess();
         
        return ;
    }

    public void run(IPresent ip, Row row) throws Exception {
        fyiReporting.RDL.Report r = ip.report();
        WorkClass wc = getValue(r);
        wc.Data = getFilteredData(r,row);
        if (!anyRows(ip,wc.Data))
            return ;
         
        // if no rows return
        //   nothing left to do
        runSetGrouping(r,wc);
        super.run(ip,row);
        if (!ip.listStart(this,row))
            return ;
         
        // renderer doesn't want to continue
        runGroups(ip,wc,wc.Groups);
        ip.listEnd(this,row);
        removeValue(r);
    }

    public void runPage(Pages pgs, Row row) throws Exception {
        fyiReporting.RDL.Report r = pgs.getReport();
        if (isHidden(r,row))
            return ;
         
        WorkClass wc = getValue(r);
        wc.Data = getFilteredData(r,row);
        setPagePositionBegin(pgs);
        if (!anyRowsPage(pgs,wc.Data))
            return ;
         
        // if no rows return
        //   nothing left to do
        runPageRegionBegin(pgs);
        runSetGrouping(pgs.getReport(),wc);
        runPageGroups(pgs,wc,wc.Groups);
        runPageRegionEnd(pgs);
        setPagePositionEnd(pgs,pgs.getCurrentPage().getYOffset());
        removeValue(r);
    }

    private void runSetGrouping(fyiReporting.RDL.Report rpt, WorkClass wc) throws Exception {
        GroupEntry[] currentGroups = new GroupEntry[]();
        // We have some data
        if (_Grouping != null || _Sorting != null)
        {
            // fix up the data
            Rows saveData = wc.Data;
            wc.Data = new Rows(rpt,null,_Grouping,_Sorting);
            wc.Data.setData(saveData.getData());
            wc.Data.sort();
            prepGroups(rpt,wc);
        }
         
        // If we haven't formed any groups then form one with all rows
        if (wc.Groups == null)
        {
            wc.Groups = new List<GroupEntry>();
            GroupEntry ge = new GroupEntry(null,null,0);
            if (wc.Data.getData() != null)
                // Do we have any data?
                ge.setEndRow(wc.Data.getData().Count - 1);
            else
                // yes
                ge.setEndRow(-1); 
            // no
            wc.Groups.Add(ge);
            // top group
            currentGroups = new GroupEntry[1];
        }
        else
            currentGroups = new GroupEntry[1]; 
        wc.Data.setCurrentGroups(currentGroups);
        return ;
    }

    private void prepGroups(fyiReporting.RDL.Report rpt, WorkClass wc) throws Exception {
        if (_Grouping == null)
            return ;
         
        int i = 0;
        // 1) Build array of all GroupExpression objects
        List<GroupExpression> gea = _Grouping.getGroupExpressions().getItems();
        GroupEntry[] currentGroups = new GroupEntry[1];
        _Grouping.setIndex(rpt,0);
        // set the index of this group (so we can find the GroupEntry)
        currentGroups[0] = new GroupEntry(_Grouping,_Sorting,0);
        // Save the typecodes, and grouping by groupexpression; for later use
        TypeCode[] tcs = new TypeCode[gea.Count];
        Grouping[] grp = new Grouping[gea.Count];
        i = 0;
        for (Object __dummyForeachVar2 : gea)
        {
            GroupExpression ge = (GroupExpression)__dummyForeachVar2;
            grp[i] = (Grouping)(ge.Parent.Parent);
            // remember the group
            tcs[i++] = ge.getExpression().getTypeCode();
        }
        // remember type of expression
        // 2) Loop thru the data, then loop thru the GroupExpression list
        wc.Groups = new List<GroupEntry>();
        Object[] savValues = null;
        Object[] grpValues = null;
        int rowCurrent = 0;
        for (Object __dummyForeachVar4 : wc.Data.getData())
        {
            Row row = (Row)__dummyForeachVar4;
            // Get the values for all the group expressions
            if (grpValues == null)
                grpValues = new Object[gea.Count];
             
            i = 0;
            for (Object __dummyForeachVar3 : gea)
            {
                GroupExpression ge = (GroupExpression)__dummyForeachVar3;
                // Could optimize to only calculate as needed in comparison loop below??
                grpValues[i++] = ge.getExpression().evaluate(rpt,row);
            }
            // For first row we just primed the pump; action starts on next row
            if (rowCurrent == 0)
            {
                // always start new group on first row
                rowCurrent++;
                savValues = grpValues;
                grpValues = null;
                continue;
            }
             
            for (i = 0;i < savValues.Length;i++)
            {
                // compare the values; if change then we have a group break
                if (Filter.ApplyCompare(tcs[i], savValues[i], grpValues[i]) != 0)
                {
                    // start a new group; and force a break on every subgroup
                    GroupEntry saveGe = null;
                    for (int j = grp[i].GetIndex(rpt);j < currentGroups.Length;j++)
                    {
                        currentGroups[j].EndRow = rowCurrent - 1;
                        if (j == 0)
                            wc.Groups.Add(currentGroups[j]);
                        else // top group
                        if (saveGe == null)
                            currentGroups[j - 1].NestedGroup.Add(currentGroups[j]);
                        else
                            saveGe.getNestedGroup().Add(currentGroups[j]);  
                        saveGe = currentGroups[j];
                        // retain this GroupEntry
                        currentGroups[j] = new GroupEntry(currentGroups[j].Group, currentGroups[j].Sort, rowCurrent);
                    }
                    savValues = grpValues;
                    grpValues = null;
                    break;
                }
                 
            }
            // break out of the value comparison loop
            rowCurrent++;
        }
        for (i = 0;i < currentGroups.Length;i++)
        {
            // End of all rows force break on end of rows
            currentGroups[i].EndRow = rowCurrent - 1;
            if (i == 0)
                wc.Groups.Add(currentGroups[i]);
            else
                // top group
                currentGroups[i - 1].NestedGroup.Add(currentGroups[i]); 
        }
        return ;
    }

    private void runGroups(IPresent ip, WorkClass wc, List<GroupEntry> groupEntries) throws Exception {
        for (Object __dummyForeachVar5 : groupEntries)
        {
            GroupEntry ge = (GroupEntry)__dummyForeachVar5;
            // set the group entry value
            int index = new int();
            if (ge.getGroup() != null)
            {
                // groups?
                ge.getGroup().resetHideDuplicates(ip.report());
                // reset duplicate checking
                index = ge.getGroup().getIndex(ip.report());
            }
            else
                // yes
                // no; must be main dataset
                index = 0; 
            wc.Data.getCurrentGroups()[index] = ge;
            if (ge.getNestedGroup().Count > 0)
                runGroupsSetGroups(ip.report(),wc,ge.getNestedGroup());
             
            if (ge.getGroup() == null)
            {
                for (int r = ge.getStartRow();r <= ge.getEndRow();r++)
                {
                    // need to run all the rows since no group defined
                    ip.ListEntryBegin(this, wc.Data.getData()[r]);
                    _ReportItems.Run(ip, wc.Data.getData()[r]);
                    ip.ListEntryEnd(this, wc.Data.getData()[r]);
                }
            }
            else
            {
                // need to process just whole group as a List entry
                ip.ListEntryBegin(this, wc.Data.getData()[ge.getStartRow()]);
                // pass the first row of the group
                _ReportItems.Run(ip, wc.Data.getData()[ge.getStartRow()]);
                ip.ListEntryEnd(this, wc.Data.getData()[ge.getStartRow()]);
            } 
        }
    }

    private void runPageGroups(Pages pgs, WorkClass wc, List<GroupEntry> groupEntries) throws Exception {
        fyiReporting.RDL.Report rpt = pgs.getReport();
        Page p = pgs.getCurrentPage();
        float pagebottom = OwnerReport.getBottomOfPage();
        p.setYOffset(p.getYOffset() + (getTop() == null ? 0 : this.getTop().getPoints()));
        float listoffset = getOffsetCalc(pgs.getReport()) + leftCalc(pgs.getReport());
        float height = new float();
        Row row;
        for (Object __dummyForeachVar6 : groupEntries)
        {
            GroupEntry ge = (GroupEntry)__dummyForeachVar6;
            // set the group entry value
            int index = new int();
            if (ge.getGroup() != null)
            {
                // groups?
                ge.getGroup().resetHideDuplicates(rpt);
                // reset duplicate checking
                index = ge.getGroup().getIndex(rpt);
            }
            else
                // yes
                // no; must be main dataset
                index = 0; 
            wc.Data.getCurrentGroups()[index] = ge;
            if (ge.getNestedGroup().Count > 0)
                runGroupsSetGroups(rpt,wc,ge.getNestedGroup());
             
            if (ge.getGroup() == null)
            {
                for (int r = ge.getStartRow();r <= ge.getEndRow();r++)
                {
                    // need to run all the rows since no group defined
                    row = wc.Data.getData()[r];
                    height = heightOfList(rpt,pgs.getG(),row);
                    float saveYoffset = p.getYOffset();
                    // this can be affected by other page items
                    _ReportItems.runPage(pgs,row,listoffset);
                    p.setYOffset(saveYoffset + height);
                    if (p.getYOffset() + height > pagebottom)
                    {
                        // need another page for next row?
                        p = runPageNew(pgs,p);
                    }
                     
                }
            }
            else
            {
                // yes; if at end this page is empty
                // and will be cleaned up later
                // need to process just whole group as a List entry
                if (ge.getGroup().getPageBreakAtStart() && !p.isEmpty())
                    p = runPageNew(pgs,p);
                 
                // pass the first row of the group
                row = wc.Data.getData()[ge.getStartRow()];
                height = heightOfList(rpt,pgs.getG(),row);
                float saveYoffset = p.getYOffset();
                // this can be affected by other page items
                _ReportItems.runPage(pgs,row,listoffset);
                p.setYOffset(saveYoffset + height);
                // need another page for next group?
                if (ge.getGroup().getPageBreakAtEnd() || p.getYOffset() + height > pagebottom)
                {
                    p = runPageNew(pgs,p);
                }
                 
            } 
        }
    }

    // yes; if at end empty page will be cleaned up later
    private void runGroupsSetGroups(fyiReporting.RDL.Report rpt, WorkClass wc, List<GroupEntry> groupEntries) throws Exception {
        // set the group entry value
        GroupEntry ge = groupEntries[0];
        wc.Data.getCurrentGroups()[ge.getGroup().getIndex(rpt)] = ge;
        if (ge.getNestedGroup().Count > 0)
            runGroupsSetGroups(rpt,wc,ge.getNestedGroup());
         
    }

    public Grouping getGrouping() throws Exception {
        return _Grouping;
    }

    public void setGrouping(Grouping value) throws Exception {
        _Grouping = value;
    }

    public float heightOfList(fyiReporting.RDL.Report rpt, Graphics g, Row r) throws Exception {
        WorkClass wc = getValue(rpt);
        float defnHeight = this.getHeightOrOwnerHeight();
        if (!_CanGrow)
            return defnHeight;
         
        float height = new float();
        for (Object __dummyForeachVar7 : this._GrowList)
        {
            Textbox tb = (Textbox)__dummyForeachVar7;
            float top = (float)(tb.getTop() == null ? 0.0 : tb.getTop().getPoints());
            height = top + tb.runTextCalcHeight(rpt,g,r);
            if (tb.getStyle() != null)
                height += (tb.getStyle().evalPaddingBottom(rpt,r) + tb.getStyle().evalPaddingTop(rpt,r));
             
            defnHeight = Math.Max(height, defnHeight);
        }
        wc.CalcHeight = defnHeight;
        return defnHeight;
    }

    public Sorting getSorting() throws Exception {
        return _Sorting;
    }

    public void setSorting(Sorting value) throws Exception {
        _Sorting = value;
    }

    public ReportItems getReportItems() throws Exception {
        return _ReportItems;
    }

    public void setReportItems(ReportItems value) throws Exception {
        _ReportItems = value;
    }

    public String getDataInstanceName() throws Exception {
        return _DataInstanceName == null ? "Item" : _DataInstanceName;
    }

    public void setDataInstanceName(String value) throws Exception {
        _DataInstanceName = value;
    }

    public DataInstanceElementOutputEnum getDataInstanceElementOutput() throws Exception {
        return _DataInstanceElementOutput;
    }

    public void setDataInstanceElementOutput(DataInstanceElementOutputEnum value) throws Exception {
        _DataInstanceElementOutput = value;
    }

    private WorkClass getValue(fyiReporting.RDL.Report rpt) throws Exception {
        WorkClass wc = rpt.getCache().get(this,"wc") instanceof WorkClass ? (WorkClass)rpt.getCache().get(this,"wc") : (WorkClass)null;
        if (wc == null)
        {
            wc = new WorkClass(this);
            rpt.getCache().add(this,"wc",wc);
        }
         
        return wc;
    }

    private void removeValue(fyiReporting.RDL.Report rpt) throws Exception {
        rpt.getCache().remove(this,"wc");
    }

    static class WorkClass   
    {
        public float CalcHeight = new float();
        // dynamic when CanGrow true
        public Rows Data;
        // Runtime data; either original query if no groups
        // or sorting or a copied version that is grouped/sorted
        public List<GroupEntry> Groups = new List<GroupEntry>();
        // Runtime groups; array of GroupEntry
        public WorkClass(List l) throws Exception {
            CalcHeight = l.getHeight() == null ? 0 : l.getHeight().getPoints();
            Data = null;
            Groups = null;
        }
    
    }

}


