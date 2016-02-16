//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Filter;
import fyiReporting.RDL.GroupEntry;
import fyiReporting.RDL.SortBy;
import fyiReporting.RDL.SortDirectionEnum;
import fyiReporting.RDL.TableWorkClass;

public class GroupEntryCompare  extends IComparer<GroupEntry> 
{
    fyiReporting.RDL.Report rpt;
    TableWorkClass wc;
    public GroupEntryCompare(fyiReporting.RDL.Report r, TableWorkClass w) throws Exception {
        rpt = r;
        wc = w;
    }

    public int compare(GroupEntry x1, GroupEntry y1) throws Exception {
        Debug.Assert(x1 != null && y1 != null && x1.getSort() != null, "Illegal arguments to GroupEntryCompare", "Compare requires object to be of type GroupEntry and that sort be defined.");
        int rc = 0;
        for (Object __dummyForeachVar0 : x1.getSort().getItems())
        {
            SortBy sb = (SortBy)__dummyForeachVar0;
            TypeCode tc = sb.getSortExpression().getType();
            int index = x1.getGroup().getIndex(rpt);
            wc.Data.getCurrentGroups()[index] = x1;
            Object o1 = sb.getSortExpression().Evaluate(rpt, wc.Data.getData()[x1.getStartRow()]);
            index = y1.getGroup().getIndex(rpt);
            wc.Data.getCurrentGroups()[index] = y1;
            Object o2 = sb.getSortExpression().Evaluate(rpt, wc.Data.getData()[y1.getStartRow()]);
            rc = Filter.applyCompare(tc,o1,o2);
            if (rc != 0)
            {
                if (sb.getDirection() == SortDirectionEnum.Descending)
                    rc = -rc;
                 
                break;
            }
             
        }
        return rc;
    }

}


