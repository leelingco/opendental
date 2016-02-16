//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:26 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Expression;
import fyiReporting.RDL.ExpressionType;
import fyiReporting.RDL.FilterOperator;
import fyiReporting.RDL.FilterOperatorEnum;
import fyiReporting.RDL.FilterValue;
import fyiReporting.RDL.FilterValues;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.Rows;
import fyiReporting.RDL.RowsSortExpression;

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
* Definition of the filter on a DataSet.  If boolean expression evaluates to false
* then row is not added to DataSet.
*/
public class Filter  extends ReportLink 
{
    Expression _FilterExpression;
    //(Variant)
    //An expression that is evaluated for each
    //instance within the group or each row of the
    //data set or data region and compared (via the
    //Operator) to the FilterValues. Failed
    //comparisons result in the row/instance being
    //filtered out of the data set, data region or
    //grouping. See Filter Expression Restrictions
    //below.
    FilterOperatorEnum _FilterOperator = FilterOperatorEnum.Equal;
    //Notes: Top and Bottom operators include ties
    //in the resulting data. string comparisons are
    //locale-dependent. Null equals Null.
    FilterValues _FilterValues;
    // The values to compare to the FilterExpression.
    //For Equal, Like, NotEqual, GreaterThan,
    //GreaterThanOrEqual, LessThan, LessThanOrEqual, TopN, BottomN,
    //TopPercent and BottomPercent, there must be
    //exactly one FilterValue
    //For TopN and BottomN, the FilterValue
    //expression must evaluate to an integer.
    //For TopPercent and BottomPercent, the
    //FilterValue expression must evaluate to an
    //integer or float.1
    //For Between, there must be exactly two FilterValue elements.
    //For In, the FilterValues are treated as a set (if
    //the FilterExpression value appears anywhere in
    //the set of FilterValues, the instance is not
    //filtered out.)
    //Like uses the same special characters as the
    //Visual Basic LIKE operator (e.g. “?” to
    //represent a single character and “*” to
    //represent any series of characers). See
    //http://msdn.microsoft.com/library/enus/vblr7/html/vaoprlike.asp.
    boolean _FilterOperatorSingleRow = new boolean();
    // false for Top/Bottom N and Percent; otherwise true
    public Filter(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _FilterExpression = null;
        _FilterOperator = FilterOperatorEnum.Unknown;
        _FilterValues = null;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("FilterExpression"))
            {
                _FilterExpression = new Expression(r,this,xNodeLoop,ExpressionType.Variant);
            }
            else if (__dummyScrutVar0.equals("Operator"))
            {
                _FilterOperator = FilterOperator.GetStyle(xNodeLoop.InnerText);
                if (_FilterOperator == FilterOperatorEnum.Unknown)
                    OwnerReport.rl.logError(8,"Unknown Filter operator '" + xNodeLoop.InnerText + "'.");
                 
            }
            else if (__dummyScrutVar0.equals("FilterValues"))
            {
                _FilterValues = new FilterValues(r,this,xNodeLoop);
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown Filter element '" + xNodeLoop.Name + "' ignored.");
            }   
        }
        if (_FilterExpression == null)
            OwnerReport.rl.logError(8,"Filter requires the FilterExpression element.");
         
        if (_FilterValues == null)
        {
            OwnerReport.rl.logError(8,"Filter requires the FilterValues element.");
            return ;
        }
         
        // some of the filter operator checks require values
        _FilterOperatorSingleRow = true;
        switch(_FilterOperator)
        {
            case Like: 
            case Equal: 
            case NotEqual: 
            case GreaterThan: 
            case GreaterThanOrEqual: 
            case LessThan: 
            case LessThanOrEqual: 
                if (_FilterValues.getItems().Count != 1)
                    OwnerReport.rl.logError(8,"Filter Operator requires exactly 1 FilterValue.");
                 
                break;
            case TopN: 
            case BottomN: 
            case TopPercent: 
            case BottomPercent: 
                _FilterOperatorSingleRow = false;
                if (_FilterValues.getItems().Count != 1)
                    OwnerReport.rl.logError(8,"Filter Operator requires exactly 1 FilterValue.");
                 
                break;
            case In: 
                break;
            case Between: 
                if (_FilterValues.getItems().Count != 2)
                    OwnerReport.rl.logError(8,"Filter Operator Between requires exactly 2 FilterValues.");
                 
                break;
            default: 
                OwnerReport.rl.logError(8,"Valid Filter operator must be specified.");
                break;
        
        }
    }

    // Handle parsing of function in final pass
    public void finalPass() throws Exception {
        _FilterExpression.finalPass();
        _FilterValues.finalPass();
        return ;
    }

    // Apply the filters to a row to determine if row is valid
    public boolean apply(fyiReporting.RDL.Report rpt, Row datarow) throws Exception {
        Object left = _FilterExpression.evaluate(rpt,datarow);
        TypeCode tc = _FilterExpression.getTypeCode();
        Object right = ((FilterValue)(_FilterValues.getItems()[0])).getExpression().evaluate(rpt,datarow);
        switch(_FilterOperator)
        {
            case Equal: 
                return applyCompare(tc,left,right) == 0 ? true : false;
            case Like: 
                // TODO - this is really regex (not like)
                if (left == null || right == null)
                    return false;
                 
                String s1 = Convert.ToString(left);
                String s2 = Convert.ToString(right);
                return Regex.IsMatch(s1, s2);
            case NotEqual: 
                return applyCompare(tc,left,right) == 0 ? false : true;
            case GreaterThan: 
                return applyCompare(tc,left,right) > 0 ? true : false;
            case GreaterThanOrEqual: 
                return applyCompare(tc,left,right) >= 0 ? true : false;
            case LessThan: 
                return applyCompare(tc,left,right) < 0 ? true : false;
            case LessThanOrEqual: 
                return applyCompare(tc,left,right) <= 0 ? true : false;
            case TopN: 
            case BottomN: 
            case TopPercent: 
            case BottomPercent: 
                return true;
            case In: 
                for (Object __dummyForeachVar2 : _FilterValues.getItems())
                {
                    // This is handled elsewhere
                    FilterValue fv = (FilterValue)__dummyForeachVar2;
                    right = fv.getExpression().evaluate(rpt,datarow);
                    if (right instanceof ArrayList)
                    {
                        for (Object __dummyForeachVar1 : right instanceof ArrayList ? (ArrayList)right : (ArrayList)null)
                        {
                            // this can only happen with MultiValue parameters
                            // check each object in the array
                            Object v = (Object)__dummyForeachVar1;
                            if (applyCompare(tc,left,v) == 0)
                                return true;
                             
                        }
                    }
                    else if (applyCompare(tc,left,right) == 0)
                        return true;
                      
                }
                return false;
            case Between: 
                if (applyCompare(tc,left,right) < 0)
                    return false;
                 
                right = ((FilterValue)(_FilterValues.getItems()[1])).getExpression().evaluate(rpt,datarow);
                return applyCompare(tc,left,right) <= 0 ? true : false;
            default: 
                return true;
        
        }
    }

    public void apply(fyiReporting.RDL.Report rpt, Rows data) throws Exception {
        if (this._FilterOperatorSingleRow)
            applySingleRowFilter(rpt,data);
        else
            applyTopBottomFilter(rpt,data); 
    }

    private void applySingleRowFilter(fyiReporting.RDL.Report rpt, Rows data) throws Exception {
        List<Row> ar = data.getData();
        // handle a single row operator; by looping thru the rows and applying
        //   the filter
        int iRow = 0;
        while (iRow < ar.Count)
        {
            Row datarow = ar[iRow];
            if (apply(rpt,datarow))
                iRow++;
            else
                ar.RemoveAt(iRow); 
        }
        return ;
    }

    private void applyTopBottomFilter(fyiReporting.RDL.Report rpt, Rows data) throws Exception {
        if (data.getData().Count <= 0)
            return ;
         
        // No data; nothing to do
        // Get the filter value and validate it
        FilterValue fv = this._FilterValues.getItems()[0];
        double val = fv.getExpression().EvaluateDouble(rpt, data.getData()[0]);
        if (val <= 0)
        {
            // if less than equal 0; then request results in no data
            data.getData().Clear();
            return ;
        }
         
        // Calculate the row number of the affected item and do additional validation
        int ival = new int();
        if (_FilterOperator == FilterOperatorEnum.TopN || _FilterOperator == FilterOperatorEnum.BottomN)
        {
            ival = (int)val;
            if (ival != val)
                throw new Exception(String.Format("Filter operators TopN and BottomN require an integer value got {0}.", val));
             
            if (ival >= data.getData().Count)
                return ;
             
            // includes all the data?
            ival--;
        }
        else
        {
            // make zero based
            if (val >= 100)
                return ;
             
            // greater than 100% means all the data
            ival = (int)(data.getData().Count * (val / 100));
            if (ival <= 0)
            {
                // if less than equal 0; then request results in no data
                data.getData().Clear();
                return ;
            }
             
            if (ival >= data.getData().Count)
                return ;
             
            // make sure rounding hasn't forced us past 100%
            ival--;
        } 
        // make zero based
        // Sort the data by the FilterExpression
        List<RowsSortExpression> sl = new List<RowsSortExpression>();
        sl.Add(new RowsSortExpression(this._FilterExpression));
        data.setSortBy(sl);
        // update the sort by
        data.sort();
        // sort the data
        // reverse the order of the data for top so that data is in the beginning
        if (_FilterOperator == FilterOperatorEnum.TopN || _FilterOperator == FilterOperatorEnum.TopPercent)
            data.getData().Reverse();
         
        List<Row> ar = data.getData();
        TypeCode tc = _FilterExpression.getTypeCode();
        Object o = this._FilterExpression.Evaluate(rpt, data.getData()[ival]);
        // adjust the ival based on duplicate values
        ival++;
        while (ival < ar.Count)
        {
            Object n = this._FilterExpression.Evaluate(rpt, data.getData()[ival]);
            if (applyCompare(tc,o,n) != 0)
                break;
             
            ival++;
        }
        if (ival < ar.Count)
        {
            // if less than we need to remove the rest of the rows
            ar.RemoveRange(ival, ar.Count - ival);
        }
         
        return ;
    }

    static public int applyCompare(TypeCode tc, Object left, Object right) throws Exception {
        if (left == null)
        {
            return (right == null) ? 0 : -1;
        }
         
        if (right == null)
            return 1;
         
        try
        {
            TypeCode __dummyScrutVar3 = tc;
            if (__dummyScrutVar3.equals(TypeCode.DateTime))
            {
                return ((DateTime)left).CompareTo(Convert.ToDateTime(right));
            }
            else if (__dummyScrutVar3.equals(TypeCode.Int16))
            {
                return ((Short)left).CompareTo(Convert.ToInt16(right));
            }
            else if (__dummyScrutVar3.equals(TypeCode.UInt16))
            {
                return ((ushort)left).CompareTo(Convert.ToUInt16(right));
            }
            else if (__dummyScrutVar3.equals(TypeCode.Int32))
            {
                return ((Integer)left).CompareTo(Convert.ToInt32(right));
            }
            else if (__dummyScrutVar3.equals(TypeCode.UInt32))
            {
                return ((uint)left).CompareTo(Convert.ToUInt32(right));
            }
            else if (__dummyScrutVar3.equals(TypeCode.Int64))
            {
                return ((Long)left).CompareTo(Convert.ToInt64(right));
            }
            else if (__dummyScrutVar3.equals(TypeCode.UInt64))
            {
                return ((ulong)left).CompareTo(Convert.ToUInt64(right));
            }
            else if (__dummyScrutVar3.equals(TypeCode.String))
            {
                return ((String)left).CompareTo(Convert.ToString(right));
            }
            else if (__dummyScrutVar3.equals(TypeCode.Decimal))
            {
                return ((Decimal)left).CompareTo(Convert.ToDecimal(right, NumberFormatInfo.InvariantInfo));
            }
            else if (__dummyScrutVar3.equals(TypeCode.Single))
            {
                return ((Float)left).CompareTo(Convert.ToSingle(right, NumberFormatInfo.InvariantInfo));
            }
            else if (__dummyScrutVar3.equals(TypeCode.Double))
            {
                return ((Double)left).CompareTo(Convert.ToDouble(right, NumberFormatInfo.InvariantInfo));
            }
            else if (__dummyScrutVar3.equals(TypeCode.Boolean))
            {
                return ((Boolean)left).CompareTo(Convert.ToBoolean(right));
            }
            else if (__dummyScrutVar3.equals(TypeCode.Char))
            {
                return ((Character)left).CompareTo(Convert.ToChar(right));
            }
            else if (__dummyScrutVar3.equals(TypeCode.SByte))
            {
                return ((sbyte)left).CompareTo(Convert.ToSByte(right));
            }
            else if (__dummyScrutVar3.equals(TypeCode.Byte))
            {
                return ((Byte)left).CompareTo(Convert.ToByte(right));
            }
            else if (__dummyScrutVar3.equals(TypeCode.Empty) || __dummyScrutVar3.equals(TypeCode.DBNull))
            {
                if (right == null)
                    return 0;
                else
                    return -1; 
            }
            else
            {
                return applyCompare(left,right);
            }                
        }
        catch (Exception __dummyCatchVar0)
        {
            return applyCompare(left,right);
        }
    
    }

    // ok we do this based on the actual type of the arguments
    // do based on actual type of arguments
    static public int applyCompare(Object left, Object right) throws Exception {
        if (left instanceof String)
            return ((String)left).CompareTo(Convert.ToString(right));
         
        if (left instanceof double)
            return ((Decimal)left).CompareTo(Convert.ToDecimal(right, NumberFormatInfo.InvariantInfo));
         
        if (left instanceof Single)
            return ((Float)left).CompareTo(Convert.ToSingle(right, NumberFormatInfo.InvariantInfo));
         
        if (left instanceof double)
            return ((Double)left).CompareTo(Convert.ToDouble(right, NumberFormatInfo.InvariantInfo));
         
        if (left instanceof DateTime)
            return ((DateTime)left).CompareTo(Convert.ToDateTime(right));
         
        if (left instanceof short)
            return ((Short)left).CompareTo(Convert.ToInt16(right));
         
        if (left instanceof ushort)
            return ((ushort)left).CompareTo(Convert.ToUInt16(right));
         
        if (left instanceof int)
            return ((Integer)left).CompareTo(Convert.ToInt32(right));
         
        if (left instanceof uint)
            return ((uint)left).CompareTo(Convert.ToUInt32(right));
         
        if (left instanceof long)
            return ((Long)left).CompareTo(Convert.ToInt64(right));
         
        if (left instanceof ulong)
            return ((ulong)left).CompareTo(Convert.ToUInt64(right));
         
        if (left instanceof boolean)
            return ((Boolean)left).CompareTo(Convert.ToBoolean(right));
         
        if (left instanceof char)
            return ((Character)left).CompareTo(Convert.ToChar(right));
         
        if (left instanceof sbyte)
            return ((sbyte)left).CompareTo(Convert.ToSByte(right));
         
        if (left instanceof byte)
            return ((Byte)left).CompareTo(Convert.ToByte(right));
         
        if (left instanceof DBNull)
        {
            if (right == null)
                return 0;
            else
                return -1; 
        }
         
        return 0;
    }

    public Expression getFilterExpression() throws Exception {
        return _FilterExpression;
    }

    public void setFilterExpression(Expression value) throws Exception {
        _FilterExpression = value;
    }

    public FilterOperatorEnum getFilterOperator() throws Exception {
        return _FilterOperator;
    }

    public void setFilterOperator(FilterOperatorEnum value) throws Exception {
        _FilterOperator = value;
    }

    public FilterValues getFilterValues() throws Exception {
        return _FilterValues;
    }

    public void setFilterValues(FilterValues value) throws Exception {
        _FilterValues = value;
    }

    public boolean getFilterOperatorSingleRow() throws Exception {
        return _FilterOperatorSingleRow;
    }

}


