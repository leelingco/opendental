//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:28 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.DataElementOutputEnum;
import fyiReporting.RDL.DataRegion;
import fyiReporting.RDL.Details;
import fyiReporting.RDL.Filter;
import fyiReporting.RDL.Footer;
import fyiReporting.RDL.GroupEntry;
import fyiReporting.RDL.GroupEntryCompare;
import fyiReporting.RDL.GroupExpression;
import fyiReporting.RDL.Grouping;
import fyiReporting.RDL.Header;
import fyiReporting.RDL.IExpr;
import fyiReporting.RDL.IPresent;
import fyiReporting.RDL.Page;
import fyiReporting.RDL.Pages;
import fyiReporting.RDL.RecursiveCompare;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.Rows;
import fyiReporting.RDL.RSize;
import fyiReporting.RDL.Sorting;
import fyiReporting.RDL.TableCell;
import fyiReporting.RDL.TableColumn;
import fyiReporting.RDL.TableColumns;
import fyiReporting.RDL.TableGroup;
import fyiReporting.RDL.TableGroups;
import fyiReporting.RDL.TableRow;
import fyiReporting.RDL.TableRows;
import fyiReporting.RDL.TableWorkClass;
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
* Table definition and processing.  Inherits from DataRegion which inherits from ReportItem.
*/
public class Table  extends DataRegion 
{
    TableColumns _TableColumns;
    // The columns in the table
    Header _Header;
    // The table header rows
    TableGroups _TableGroups;
    // The groups (group expressions/headers/footers) for the table
    Details _Details;
    // The details rows for the table
    //	The table must have at least one of:
    //	Details, Header or Footer
    Footer _Footer;
    // The table footer rows
    boolean _FillPage = new boolean();
    // Indicates the table should expand to
    //	fill the page, pushing items below it
    //	to the bottom of the page.
    String _DetailDataElementName = new String();
    //The name to use for the data element
    // for instances of this group. Ignored if
    // there is a grouping defined for the details.
    // Default: “Details”
    String _DetailDataCollectionName = new String();
    // The name to use for the data element
    // for the collection of all instances of this group.
    // Default: “Details_Collection”
    DataElementOutputEnum _DetailDataElementOutput = DataElementOutputEnum.Output;
    // Indicates whether the details should appear in a data rendering.
    public Table(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p, xNode);
        _TableColumns = null;
        _Header = null;
        _TableGroups = null;
        _Details = null;
        _Footer = null;
        _FillPage = true;
        _DetailDataElementName = "Details";
        _DetailDataCollectionName = "Details_Collection";
        _DetailDataElementOutput = DataElementOutputEnum.Output;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("TableColumns"))
            {
                _TableColumns = new TableColumns(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Header"))
            {
                _Header = new Header(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("TableGroups"))
            {
                _TableGroups = new TableGroups(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Details"))
            {
                _Details = new Details(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Footer"))
            {
                _Footer = new Footer(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("FillPage"))
            {
                _FillPage = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar0.equals("DetailDataElementName"))
            {
                _DetailDataElementName = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar0.equals("DetailDataCollectionName"))
            {
                _DetailDataCollectionName = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar0.equals("DetailDataElementOutput"))
            {
                _DetailDataElementOutput = fyiReporting.RDL.DataElementOutput.GetStyle(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else
            {
                if (dataRegionElement(xNodeLoop))
                    break;
                 
                // try at DataRegion level
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown Table element '" + xNodeLoop.Name + "' ignored.");
            }         
        }
        dataRegionFinish();
        // Tidy up the DataRegion
        if (_TableColumns == null)
        {
            OwnerReport.rl.logError(8,"TableColumns element must be specified for a Table.");
            return ;
        }
         
        if (OwnerReport.rl.getMaxSeverity() < 8)
            verifyCC();
         
    }

    // Verify column count
    private void verifyCC() throws Exception {
        int colCount = this._TableColumns.getItems().Count;
        if (this._Header != null)
            verifyCCTableRows("Table Header",_Header.getTableRows(),colCount);
         
        if (this._Footer != null)
            verifyCCTableRows("Table Footer",_Footer.getTableRows(),colCount);
         
        if (this._Details != null)
            verifyCCTableRows("Table Details",_Details.getTableRows(),colCount);
         
        if (this._TableGroups != null)
        {
            for (Object __dummyForeachVar1 : _TableGroups.getItems())
            {
                TableGroup tg = (TableGroup)__dummyForeachVar1;
                if (tg.getHeader() != null)
                    verifyCCTableRows("TableGroup Header",tg.getHeader().getTableRows(),colCount);
                 
                if (tg.getFooter() != null)
                    verifyCCTableRows("TableGroup Footer",tg.getFooter().getTableRows(),colCount);
                 
            }
        }
         
    }

    private void verifyCCTableRows(String label, TableRows trs, int colCount) throws Exception {
        for (Object __dummyForeachVar3 : trs.getItems())
        {
            TableRow tr = (TableRow)__dummyForeachVar3;
            int cols = 0;
            for (Object __dummyForeachVar2 : tr.getTableCells().getItems())
            {
                TableCell tc = (TableCell)__dummyForeachVar2;
                cols += tc.getColSpan();
            }
            if (cols != colCount)
                OwnerReport.rl.LogError(8, String.Format("{0} must have the same number of columns as TableColumns.", label));
             
        }
        return ;
    }

    public void finalPass() throws Exception {
        super.finalPass();
        float totalHeight = 0;
        if (_TableColumns != null)
            _TableColumns.finalPass();
         
        if (_Header != null)
        {
            _Header.finalPass();
            totalHeight += _Header.getTableRows().defnHeight();
        }
         
        if (_TableGroups != null)
        {
            _TableGroups.finalPass();
            totalHeight += _TableGroups.defnHeight();
        }
         
        if (_Details != null)
        {
            _Details.finalPass();
            totalHeight += _Details.getTableRows().defnHeight();
        }
         
        if (_Footer != null)
        {
            _Footer.finalPass();
            totalHeight += _Footer.getTableRows().defnHeight();
        }
         
        if (this.getHeight() == null)
        {
            // Calculate a height based on the sum of the TableRows
            this.setHeight(new RSize(this.OwnerReport, String.Format(NumberFormatInfo.InvariantInfo, "{0:0.00}pt", totalHeight)));
        }
         
        return ;
    }

    public void run(IPresent ip, Row row) throws Exception {
        fyiReporting.RDL.Report r = ip.report();
        TableWorkClass wc = getValue(r);
        wc.Data = getFilteredData(r,row);
        if (!anyRows(ip,wc.Data))
            return ;
         
        // if no rows return
        //   nothing left to do
        runPrep(r,row,wc);
        if (!ip.tableStart(this,row))
            return ;
         
        // render doesn't want to continue
        if (_TableColumns != null)
            _TableColumns.run(ip,row);
         
        // Header
        if (_Header != null)
        {
            ip.tableHeaderStart(_Header,row);
            Row frow = wc.Data.getData().Count > 0 ? wc.Data.getData()[0] : null;
            _Header.run(ip,frow);
            ip.tableHeaderEnd(_Header,row);
        }
         
        // Body
        ip.tableBodyStart(this,row);
        if (wc.RecursiveGroup != null)
            runRecursiveGroups(ip,wc);
        else
            runGroups(ip,wc.Groups,wc); 
        ip.tableBodyEnd(this,row);
        // Footer
        if (_Footer != null)
        {
            ip.tableFooterStart(_Footer,row);
            Row lrow = wc.Data.getData().Count > 0 ? wc.Data.getData()[wc.Data.getData().Count - 1] : null;
            _Footer.run(ip,lrow);
            ip.tableFooterEnd(_Footer,row);
        }
         
        ip.tableEnd(this,row);
        removeValue(r);
    }

    public void runPage(Pages pgs, Row row) throws Exception {
        fyiReporting.RDL.Report r = pgs.getReport();
        if (isHidden(r,row))
            return ;
         
        TableWorkClass wc = getValue(r);
        wc.Data = getFilteredData(r,row);
        setPagePositionBegin(pgs);
        if (!anyRowsPage(pgs,wc.Data))
            return ;
         
        // if no rows return
        //   nothing left to do
        runPrep(r,row,wc);
        runPageRegionBegin(pgs);
        Page p = pgs.getCurrentPage();
        p.setYOffset(p.getYOffset() + this.relativeY(r));
        // Calculate the xpositions of the columns
        getTableColumns().calculateXPositions(r,getOffsetCalc(r) + leftCalc(r),row);
        RunPageHeader(pgs, wc.Data.getData()[0], true, null);
        if (wc.RecursiveGroup != null)
            runRecursiveGroupsPage(pgs,wc);
        else
            RunGroupsPage(pgs, wc, wc.Groups, wc.Data.getData().Count - 1, 0); 
        // Footer
        if (_Footer != null)
        {
            Row lrow = wc.Data.getData().Count > 0 ? wc.Data.getData()[wc.Data.getData().Count - 1] : null;
            p = pgs.getCurrentPage();
            // make sure the footer fits on the page
            if (p.getYOffset() + _Footer.heightOfRows(pgs,lrow) > pgs.getBottomOfPage())
            {
                p = runPageNew(pgs,p);
                runPageHeader(pgs,row,false,null);
            }
             
            _Footer.runPage(pgs,lrow);
        }
         
        runPageRegionEnd(pgs);
        setPagePositionEnd(pgs,pgs.getCurrentPage().getYOffset());
        removeValue(r);
    }

    public void runPageHeader(Pages pgs, Row frow, boolean bFirst, TableGroup stoptg) throws Exception {
        // Do the table headers
        boolean isEmpty = pgs.getCurrentPage().isEmpty();
        if (_Header != null && _Header.getRepeatOnNewPage())
        {
            _Header.runPage(pgs,frow);
            if (isEmpty)
                pgs.getCurrentPage().setEmpty();
             
        }
         
        // Consider this empty of data
        if (bFirst)
            return ;
         
        // the very first time we'll output the header (and not consider page empty)
        if (_TableGroups == null)
        {
            pgs.getCurrentPage().setEmpty();
            return ;
        }
         
        for (Object __dummyForeachVar4 : _TableGroups.getItems())
        {
            // Consider this empty of data
            // Do the group headers
            TableGroup tg = (TableGroup)__dummyForeachVar4;
            if (stoptg != null && tg == stoptg)
                break;
             
            if (tg.getHeader() != null)
            {
                if (tg.getHeader().getRepeatOnNewPage())
                {
                    tg.getHeader().runPage(pgs,frow);
                }
                 
            }
             
        }
        pgs.getCurrentPage().setEmpty();
        return ;
    }

    // Consider this empty of data
    void runPrep(fyiReporting.RDL.Report rpt, Row row, TableWorkClass wc) throws Exception {
        GroupEntry[] currentGroups = new GroupEntry[]();
        // We have some data
        if (_TableGroups != null || (_Details != null && (_Details.getSorting() != null || _Details.getGrouping() != null)))
        {
            // fix up the data
            List<Row> saveData = wc.Data.getData();
            Grouping gr;
            Sorting srt;
            if (_Details == null)
            {
                gr = null;
                srt = null;
            }
            else
            {
                gr = _Details.getGrouping();
                srt = _Details.getSorting();
            } 
            wc.Data = new Rows(rpt,_TableGroups,gr,srt);
            wc.Data.setData(saveData);
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
        else if (_TableGroups != null)
        {
            int count = _TableGroups.getItems().Count;
            if (_Details != null && _Details.getGrouping() != null)
                count++;
             
            currentGroups = new GroupEntry[count];
        }
        else
            currentGroups = new GroupEntry[1];  
        wc.Data.setCurrentGroups(currentGroups);
        sortGroups(rpt,wc.Groups,wc);
    }

    private void sortGroups(fyiReporting.RDL.Report rpt, List<GroupEntry> groupEntries, TableWorkClass wc) throws Exception {
        GroupEntry fge = (GroupEntry)(groupEntries[0]);
        if (fge.getSort() != null)
        {
            GroupEntryCompare gec = new GroupEntryCompare(rpt,wc);
            RunGroupsSetGroups(rpt, wc, groupEntries);
            groupEntries.Sort(gec);
        }
         
        for (Object __dummyForeachVar5 : groupEntries)
        {
            // drill down into nested groups
            GroupEntry ge = (GroupEntry)__dummyForeachVar5;
            if (ge.getNestedGroup().Count > 0)
                sortGroups(rpt,ge.getNestedGroup(),wc);
             
        }
    }

    private void prepGroups(fyiReporting.RDL.Report rpt, TableWorkClass wc) throws Exception {
        wc.RecursiveGroup = null;
        if (_TableGroups == null)
        {
            // no tablegroups; check to ensure details is grouped
            if (_Details == null || _Details.getGrouping() == null)
                return ;
             
        }
         
        // no groups to prepare
        int i = 0;
        // 1) Build array of all GroupExpression objects
        List<GroupExpression> gea = new List<GroupExpression>();
        //    count the number of groups
        int countG = 0;
        if (_TableGroups != null)
            countG = _TableGroups.getItems().Count;
         
        Grouping dg = null;
        Sorting ds = null;
        if (_Details != null && _Details.getGrouping() != null)
        {
            dg = _Details.getGrouping();
            ds = _Details.getSorting();
            countG++;
        }
         
        GroupEntry[] currentGroups = new GroupEntry[countG++];
        if (_TableGroups != null)
        {
            for (Object __dummyForeachVar7 : _TableGroups.getItems())
            {
                // add in the groups for the tablegroup
                TableGroup tg = (TableGroup)__dummyForeachVar7;
                if (tg.getGrouping().getParentGroup() != null)
                    wc.RecursiveGroup = tg.getGrouping();
                 
                tg.getGrouping().setIndex(rpt,i);
                // set the index of this group (so we can find the GroupEntry)
                currentGroups[i++] = new GroupEntry(tg.getGrouping(),tg.getSorting(),0);
                for (Object __dummyForeachVar6 : tg.getGrouping().getGroupExpressions().getItems())
                {
                    GroupExpression ge = (GroupExpression)__dummyForeachVar6;
                    gea.Add(ge);
                }
            }
        }
         
        if (dg != null)
        {
            // add in the groups for the details grouping
            if (dg.getParentGroup() != null)
                wc.RecursiveGroup = dg;
             
            dg.setIndex(rpt,i);
            // set the index of this group (so we can find the GroupEntry)
            currentGroups[i++] = new GroupEntry(dg,ds,0);
            for (Object __dummyForeachVar8 : dg.getGroupExpressions().getItems())
            {
                GroupExpression ge = (GroupExpression)__dummyForeachVar8;
                gea.Add(ge);
            }
        }
         
        if (wc.RecursiveGroup != null)
        {
            if (gea.Count != 1)
                throw new Exception("Error: Recursive groups must be the only group definition.");
             
            // Limitiation of implementation
            prepRecursiveGroup(rpt,wc);
            return ;
        }
         
        // only one group and it's recursive: optimization
        // Save the typecodes, and grouping by groupexpression; for later use
        TypeCode[] tcs = new TypeCode[gea.Count];
        Grouping[] grp = new Grouping[gea.Count];
        i = 0;
        for (Object __dummyForeachVar9 : gea)
        {
            GroupExpression ge = (GroupExpression)__dummyForeachVar9;
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
        for (Object __dummyForeachVar11 : wc.Data.getData())
        {
            Row row = (Row)__dummyForeachVar11;
            // Get the values for all the group expressions
            if (grpValues == null)
                grpValues = new Object[gea.Count];
             
            i = 0;
            for (Object __dummyForeachVar10 : gea)
            {
                GroupExpression ge = (GroupExpression)__dummyForeachVar10;
                if (((Grouping)(ge.Parent.Parent)).getParentGroup() == null)
                    grpValues[i++] = ge.getExpression().evaluate(rpt,row);
                else
                    grpValues[i++] = null; 
            }
            // Want all the parentGroup to evaluate equal
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

    private void prepRecursiveGroup(fyiReporting.RDL.Report rpt, TableWorkClass wc) throws Exception {
        // Prepare for processing recursive group
        Grouping g = wc.RecursiveGroup;
        IExpr parentExpr = g.getParentGroup();
        GroupExpression gexpr = g.getGroupExpressions().getItems()[0] instanceof GroupExpression ? (GroupExpression)g.getGroupExpressions().getItems()[0] : (GroupExpression)null;
        IExpr groupExpr = gexpr.getExpression();
        TypeCode tc = groupExpr.getTypeCode();
        List<Row> odata = new List<Row>(wc.Data.getData());
        // this is the old data that we'll recreate using the recursive hierarchy
        List<Row> newrows = new List<Row>(odata.Count);
        // For now we assume on a single top of tree (and it must sort first as null)
        //   spec is incomplete: should have ability to specify starting value of tree
        // TODO: pull all of the rows that start with null
        newrows.Add(odata[0]);
        // add the starting row
        odata.RemoveAt(0);
        //   remove olddata
        // we need to build the group entry stack
        // Build the initial one
        wc.Groups = new List<GroupEntry>();
        GroupEntry ge = new GroupEntry(null,null,0);
        ge.setEndRow(odata.Count - 1);
        wc.Groups.Add(ge);
        // top group
        List<GroupEntry> ges = new List<GroupEntry>();
        ges.Add(ge);
        // loop thru the rows and find their children
        //   we place the children right after the parent
        //   this reorders the rows in the form of the hierarchy
        Row r;
        RecursiveCompare rc = new RecursiveCompare(rpt,parentExpr,tc,groupExpr);
        for (int iRow = 0;iRow < newrows.Count;iRow++)
        {
            // go thru the temp rows
            r = newrows[iRow];
            r.setGroupEntry(ge = new GroupEntry(g,null,iRow));
            // TODO: sort for this group??
            r.getGroupEntry().setEndRow(iRow);
            // pull out all the rows that match this value
            int iMainRow = odata.BinarySearch(r, rc);
            if (iMainRow < 0)
            {
                for (int i = 0;i <= r.getLevel() + 1 && i < ges.Count;i++)
                {
                    ge = ges[i] instanceof GroupEntry ? (GroupEntry)ges[i] : (GroupEntry)null;
                    Row rr = newrows[ge.getStartRow()];
                    // start row has the base level of group
                    if (rr.getLevel() < r.getLevel())
                        ge.setEndRow(iRow);
                     
                }
                continue;
            }
             
            // look backward for starting row;
            //   in case of duplicates, BinarySearch can land on any of the rows
            Object cmpvalue = groupExpr.evaluate(rpt,r);
            int sRow = iMainRow - 1;
            while (sRow >= 0)
            {
                Object v = parentExpr.Evaluate(rpt, odata[sRow]);
                if (Filter.applyCompare(tc,cmpvalue,v) != 0)
                    break;
                 
                sRow--;
            }
            sRow++;
            // adjust; since we went just prior it
            // look forward for ending row
            int eRow = iMainRow + 1;
            while (eRow < odata.Count)
            {
                Object v = parentExpr.Evaluate(rpt, odata[eRow]);
                if (Filter.applyCompare(tc,cmpvalue,v) != 0)
                    break;
                 
                eRow++;
            }
            // Build a group entry for this
            GroupEntry ge2 = ges[r.getLevel()] instanceof GroupEntry ? (GroupEntry)ges[r.getLevel()] : (GroupEntry)null;
            ge2.getNestedGroup().Add(ge);
            if (r.getLevel() + 1 >= ges.Count)
                // ensure we have room in the array (based on level)
                ges.Add(ge);
            else
            {
                // add to the array
                ges[r.getLevel() + 1] = ge;
            } 
            // put this in the array
            // add all of them in; want the same order for these rows.
            int offset = 1;
            for (int tRow = sRow;tRow < eRow;tRow++)
            {
                Row tr = odata[tRow];
                tr.setLevel(r.getLevel() + 1);
                newrows.Insert(iRow + offset, tr);
                offset++;
            }
            // remove from old data
            odata.RemoveRange(sRow, eRow - sRow);
        }
        // update the groupentries for the very last row
        int lastrow = newrows.Count - 1;
        r = newrows[lastrow];
        for (int i = 0;i < r.getLevel() + 1 && i < ges.Count;i++)
        {
            ge = ges[i] instanceof GroupEntry ? (GroupEntry)ges[i] : (GroupEntry)null;
            ge.setEndRow(lastrow);
        }
        wc.Data.setData(newrows);
        return ;
    }

    // we've completely replaced the rows
    private void runGroups(IPresent ip, List<GroupEntry> groupEntries, TableWorkClass wc) throws Exception {
        fyiReporting.RDL.Report rpt = ip.report();
        GroupEntry fge = (GroupEntry)(groupEntries[0]);
        if (fge.getGroup() != null)
            ip.groupingStart(fge.getGroup());
         
        for (Object __dummyForeachVar12 : groupEntries)
        {
            GroupEntry ge = (GroupEntry)__dummyForeachVar12;
            // set the group entry value
            int index = new int();
            if (ge.getGroup() != null)
            {
                // groups?
                ip.groupingInstanceStart(ge.getGroup());
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
             
            // Handle the group header
            if (ge.getGroup() != null && ge.getGroup().Parent != null)
            {
                TableGroup tg = ge.getGroup().Parent instanceof TableGroup ? (TableGroup)ge.getGroup().Parent : (TableGroup)null;
                if (tg != null && tg.getHeader() != null)
                {
                    // Calculate the number of table rows below this group; header, footer, details count
                    if (ge.getNestedGroup().Count > 0)
                        wc.GroupNestCount = runGroupsCount(ge.getNestedGroup(),0);
                    else
                        wc.GroupNestCount = (ge.getEndRow() - ge.getStartRow() + 1) * getDetailsCount(); 
                    tg.getHeader().Run(ip, wc.Data.getData()[ge.getStartRow()]);
                    wc.GroupNestCount = 0;
                }
                 
            }
             
            // Handle the nested groups if any
            if (ge.getNestedGroup().Count > 0)
                runGroups(ip,ge.getNestedGroup(),wc);
            else // If no nested groups then handle the detail rows for the group
            if (_Details != null)
            {
                if (ge.getGroup() != null && (ge.getGroup().Parent instanceof TableGroup ? (TableGroup)ge.getGroup().Parent : (TableGroup)null) == null)
                {
                    // Group defined on table; means that Detail rows only put out once per group
                    _Details.run(ip,wc.Data,ge.getStartRow(),ge.getStartRow());
                }
                else
                    _Details.run(ip,wc.Data,ge.getStartRow(),ge.getEndRow()); 
            }
              
            // Do the group footer
            if (ge.getGroup() != null)
            {
                if (ge.getGroup().Parent != null)
                {
                    TableGroup tg = ge.getGroup().Parent instanceof TableGroup ? (TableGroup)ge.getGroup().Parent : (TableGroup)null;
                    // detail groups will result in null
                    if (tg != null && tg.getFooter() != null)
                        tg.getFooter().Run(ip, wc.Data.getData()[ge.getEndRow()]);
                     
                }
                 
                ip.groupingInstanceEnd(ge.getGroup());
            }
             
        }
        if (fge.getGroup() != null)
            ip.groupingEnd(fge.getGroup());
         
    }

    private void runGroupsPage(Pages pgs, TableWorkClass wc, List<GroupEntry> groupEntries, int endRow, float groupHeight) throws Exception {
        fyiReporting.RDL.Report rpt = pgs.getReport();
        for (Object __dummyForeachVar13 : groupEntries)
        {
            GroupEntry ge = (GroupEntry)__dummyForeachVar13;
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
             
            // Handle the group header
            if (ge.getGroup() != null)
            {
                TableGroup tg = ge.getGroup().Parent instanceof TableGroup ? (TableGroup)ge.getGroup().Parent : (TableGroup)null;
                if (ge.getGroup().getPageBreakAtStart() && !pgs.getCurrentPage().isEmpty())
                {
                    runPageNew(pgs,pgs.getCurrentPage());
                    RunPageHeader(pgs, wc.Data.getData()[ge.getStartRow()], false, tg);
                }
                 
                if (tg != null && tg.getHeader() != null)
                {
                    // Calculate the number of table rows below this group; header, footer, details count
                    if (ge.getNestedGroup().Count > 0)
                        wc.GroupNestCount = runGroupsCount(ge.getNestedGroup(),0);
                    else
                        wc.GroupNestCount = (ge.getEndRow() - ge.getStartRow() + 1) * getDetailsCount(); 
                    tg.getHeader().RunPage(pgs, wc.Data.getData()[ge.getStartRow()]);
                    wc.GroupNestCount = 0;
                }
                 
            }
             
            float footerHeight = runGroupsFooterHeight(pgs,wc,ge);
            if (ge.getEndRow() == endRow)
                footerHeight += groupHeight;
             
            // Handle the nested groups if any
            if (ge.getNestedGroup().Count > 0)
                runGroupsPage(pgs,wc,ge.getNestedGroup(),ge.getEndRow(),footerHeight);
            else // If no nested groups then handle the detail rows for the group
            if (_Details != null)
            {
                if (ge.getGroup() != null && (ge.getGroup().Parent instanceof TableGroup ? (TableGroup)ge.getGroup().Parent : (TableGroup)null) == null)
                {
                    // Group defined on table; means that Detail rows only put out once per group
                    _Details.runPage(pgs,wc.Data,ge.getStartRow(),ge.getStartRow(),footerHeight);
                }
                else
                {
                    _Details.runPage(pgs,wc.Data,ge.getStartRow(),ge.getEndRow(),footerHeight);
                } 
            }
            else
            {
                // When no details we need to figure out whether any more fits on a page
                Page p = pgs.getCurrentPage();
                if (p.getYOffset() + footerHeight > pgs.getBottomOfPage())
                {
                    //	Do we need new page to fit footer?
                    p = runPageNew(pgs,p);
                    RunPageHeader(pgs, wc.Data.getData()[ge.getEndRow()], false, null);
                }
                 
            }  
            // Do the group footer
            if (ge.getGroup() != null && ge.getGroup().Parent != null)
            {
                TableGroup tg = ge.getGroup().Parent instanceof TableGroup ? (TableGroup)ge.getGroup().Parent : (TableGroup)null;
                // detail groups will result in null
                if (tg != null && tg.getFooter() != null)
                    tg.getFooter().RunPage(pgs, wc.Data.getData()[ge.getEndRow()]);
                 
                if (ge.getGroup().getPageBreakAtEnd() && !pgs.getCurrentPage().isEmpty())
                {
                    runPageNew(pgs,pgs.getCurrentPage());
                    RunPageHeader(pgs, wc.Data.getData()[ge.getStartRow()], false, tg);
                }
                 
            }
             
        }
    }

    private float runGroupsFooterHeight(Pages pgs, TableWorkClass wc, GroupEntry ge) throws Exception {
        Grouping g = ge.getGroup();
        if (g == null)
            return 0;
         
        TableGroup tg = g.Parent instanceof TableGroup ? (TableGroup)g.Parent : (TableGroup)null;
        // detail groups will result in null
        if (tg == null || tg.getFooter() == null)
            return 0;
         
        return tg.getFooter().HeightOfRows(pgs, wc.Data.getData()[ge.getEndRow()]);
    }

    private int runGroupsCount(List<GroupEntry> groupEntries, int count) throws Exception {
        for (Object __dummyForeachVar14 : groupEntries)
        {
            GroupEntry ge = (GroupEntry)__dummyForeachVar14;
            Grouping g = ge.getGroup();
            if (g != null)
            {
                TableGroup tg = g.Parent instanceof TableGroup ? (TableGroup)g.Parent : (TableGroup)null;
                if (tg != null)
                    count += (tg.getHeaderCount() + tg.getFooterCount());
                 
            }
             
            if (ge.getNestedGroup().Count > 0)
                count = runGroupsCount(ge.getNestedGroup(),count);
            else
                count += (ge.getEndRow() - ge.getStartRow() + 1) * getDetailsCount(); 
        }
        return count;
    }

    public void runGroupsSetGroups(fyiReporting.RDL.Report rpt, TableWorkClass wc, List<GroupEntry> groupEntries) throws Exception {
        // set the group entry value
        GroupEntry ge = groupEntries[0];
        wc.Data.getCurrentGroups()[ge.getGroup().getIndex(rpt)] = ge;
        if (ge.getNestedGroup().Count > 0)
            runGroupsSetGroups(rpt,wc,ge.getNestedGroup());
         
    }

    private void runRecursiveGroups(IPresent ip, TableWorkClass wc) throws Exception {
        List<Row> rows = wc.Data.getData();
        Row r;
        // Get any header/footer information for use in loop
        Header header = null;
        Footer footer = null;
        TableGroup tg = wc.RecursiveGroup.Parent instanceof TableGroup ? (TableGroup)wc.RecursiveGroup.Parent : (TableGroup)null;
        if (tg != null)
        {
            header = tg.getHeader();
            footer = tg.getFooter();
        }
         
        boolean bHeader = true;
        for (int iRow = 0;iRow < rows.Count;iRow++)
        {
            r = rows[iRow];
            wc.Data.getCurrentGroups()[0] = r.getGroupEntry();
            wc.GroupNestCount = r.getGroupEntry().getEndRow() - r.getGroupEntry().getStartRow();
            if (bHeader)
            {
                bHeader = false;
                if (header != null)
                    header.run(ip,r);
                 
            }
             
            if (_Details != null)
            {
                _Details.run(ip,wc.Data,iRow,iRow);
            }
             
            // determine need for group headers and/or footers
            if (iRow < rows.Count - 1)
            {
                Row r2 = rows[iRow + 1];
                if (r.getLevel() > r2.getLevel())
                {
                    if (footer != null)
                        footer.run(ip,r);
                     
                }
                else if (r.getLevel() < r2.getLevel())
                    bHeader = true;
                  
            }
             
        }
        if (footer != null)
            footer.run(ip,rows[rows.Count - 1] instanceof Row ? (Row)rows[rows.Count - 1] : (Row)null);
         
    }

    private void runRecursiveGroupsPage(Pages pgs, TableWorkClass wc) throws Exception {
        List<Row> rows = wc.Data.getData();
        Row r;
        // Get any header/footer information for use in loop
        Header header = null;
        Footer footer = null;
        TableGroup tg = wc.RecursiveGroup.Parent instanceof TableGroup ? (TableGroup)wc.RecursiveGroup.Parent : (TableGroup)null;
        if (tg != null)
        {
            header = tg.getHeader();
            footer = tg.getFooter();
        }
         
        boolean bHeader = true;
        for (int iRow = 0;iRow < rows.Count;iRow++)
        {
            r = rows[iRow];
            wc.Data.getCurrentGroups()[0] = r.getGroupEntry();
            wc.GroupNestCount = r.getGroupEntry().getEndRow() - r.getGroupEntry().getStartRow();
            if (bHeader)
            {
                bHeader = false;
                if (header != null)
                {
                    Page p = pgs.getCurrentPage();
                    // this can change after running a row
                    float height = p.getYOffset() + header.heightOfRows(pgs,r);
                    if (height > pgs.getBottomOfPage())
                    {
                        p = runPageNew(pgs,p);
                        runPageHeader(pgs,r,false,null);
                        if (!header.getRepeatOnNewPage())
                            header.runPage(pgs,r);
                         
                    }
                    else
                        header.runPage(pgs,r); 
                }
                 
            }
             
            // determine need for group headers and/or footers
            boolean bFooter = false;
            float footerHeight = 0;
            if (iRow < rows.Count - 1)
            {
                Row r2 = rows[iRow + 1];
                if (r.getLevel() > r2.getLevel())
                {
                    if (footer != null)
                    {
                        bFooter = true;
                        footerHeight = footer.heightOfRows(pgs,r);
                    }
                     
                }
                else if (r.getLevel() < r2.getLevel())
                    bHeader = true;
                  
            }
             
            if (_Details != null)
            {
                _Details.runPage(pgs,wc.Data,iRow,iRow,footerHeight);
            }
             
            // and output the footer if needed
            if (bFooter)
                footer.runPage(pgs,r);
             
        }
        if (footer != null)
            footer.runPage(pgs,rows[rows.Count - 1] instanceof Row ? (Row)rows[rows.Count - 1] : (Row)null);
         
    }

    public TableColumns getTableColumns() throws Exception {
        return _TableColumns;
    }

    public void setTableColumns(TableColumns value) throws Exception {
        _TableColumns = value;
    }

    public int getColumnCount() throws Exception {
        return _TableColumns.getItems().Count;
    }

    public Header getHeader() throws Exception {
        return _Header;
    }

    public void setHeader(Header value) throws Exception {
        _Header = value;
    }

    public TableGroups getTableGroups() throws Exception {
        return _TableGroups;
    }

    public void setTableGroups(TableGroups value) throws Exception {
        _TableGroups = value;
    }

    public Details getDetails() throws Exception {
        return _Details;
    }

    public void setDetails(Details value) throws Exception {
        _Details = value;
    }

    public int getDetailsCount() throws Exception {
        if (_Details == null)
            return 0;
         
        return _Details.getTableRows().getItems().Count;
    }

    public Footer getFooter() throws Exception {
        return _Footer;
    }

    public void setFooter(Footer value) throws Exception {
        _Footer = value;
    }

    public boolean getFillPage() throws Exception {
        return _FillPage;
    }

    public void setFillPage(boolean value) throws Exception {
        _FillPage = value;
    }

    public String getDetailDataElementName() throws Exception {
        return _DetailDataElementName;
    }

    public void setDetailDataElementName(String value) throws Exception {
        _DetailDataElementName = value;
    }

    public String getDetailDataCollectionName() throws Exception {
        return _DetailDataCollectionName;
    }

    public void setDetailDataCollectionName(String value) throws Exception {
        _DetailDataCollectionName = value;
    }

    public DataElementOutputEnum getDetailDataElementOutput() throws Exception {
        return _DetailDataElementOutput;
    }

    public void setDetailDataElementOutput(DataElementOutputEnum value) throws Exception {
        _DetailDataElementOutput = value;
    }

    public int getGroupNestCount(fyiReporting.RDL.Report rpt) throws Exception {
        TableWorkClass wc = getValue(rpt);
        return wc.GroupNestCount;
    }

    public int widthInPixels(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        // Calculate this based on the sum of TableColumns
        int width = 0;
        for (Object __dummyForeachVar15 : this.getTableColumns().getItems())
        {
            TableColumn tc = (TableColumn)__dummyForeachVar15;
            if (tc.getVisibility() == null || !tc.getVisibility().getHidden().evaluateBoolean(rpt,row))
                width += tc.getWidth().getPixelsX();
             
        }
        return width;
    }

    public int getWidthInUnits() throws Exception {
        // Calculate this based on the sum of TableColumns
        int width = 0;
        for (Object __dummyForeachVar16 : this.getTableColumns().getItems())
        {
            TableColumn tc = (TableColumn)__dummyForeachVar16;
            width += tc.getWidth().getSize();
        }
        return width;
    }

    private TableWorkClass getValue(fyiReporting.RDL.Report rpt) throws Exception {
        TableWorkClass wc = rpt.getCache().get(this,"wc") instanceof TableWorkClass ? (TableWorkClass)rpt.getCache().get(this,"wc") : (TableWorkClass)null;
        if (wc == null)
        {
            wc = new TableWorkClass();
            rpt.getCache().add(this,"wc",wc);
        }
         
        return wc;
    }

    private void removeValue(fyiReporting.RDL.Report rpt) throws Exception {
        rpt.getCache().remove(this,"wc");
    }

}


