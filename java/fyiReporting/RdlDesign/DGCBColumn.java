//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:17 PM
//

package fyiReporting.RdlDesign;


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
public class DGCBColumn  extends DataGridColumnStyle 
{
    private ComboBox _cb = new ComboBox();
    private boolean editing = false;
    private String originalText = new String();
    CurrencyManager _CM = new CurrencyManager();
    int _Row = new int();
    public DGCBColumn() throws Exception {
        this(ComboBoxStyle.DropDownList);
    }

    public DGCBColumn(ComboBoxStyle cbs) throws Exception {
        _cb.Visible = false;
        _cb.DropDownStyle = cbs;
    }

    protected void abort(int row) throws Exception {
        rollBack();
        hideComboBox();
        endEdit();
    }

    public ComboBox getCB() throws Exception {
        return _cb;
    }

    protected boolean commit(CurrencyManager cm, int row) throws Exception {
        try
        {
            //			if (!editing)
            //				return true;
            Object o = _cb.Text;
            if (NullText.Equals(o))
                o = System.Convert.DBNull;
             
            SetColumnValueAtRow(cm, row, o);
        }
        catch (Exception __dummyCatchVar0)
        {
            return false;
        }

        return true;
    }

    //				EndEdit();
    //			HideComboBox();
    //			EndEdit();
    protected void concedeFocus() throws Exception {
        if (editing)
        {
            Object o = _cb.Text;
            if (NullText.Equals(o))
                o = System.Convert.DBNull;
             
            SetColumnValueAtRow(_CM, _Row, o);
        }
         
        hideComboBox();
        endEdit();
    }

    protected void edit(CurrencyManager cm, int row, Rectangle rect, boolean readOnly, String text, boolean visible) throws Exception {
        _CM = cm;
        _Row = row;
        originalText = _cb.Text;
        _cb.Text = String.Empty;
        _cb.Bounds = rect;
        _cb.RightToLeft = DataGridTableStyle.DataGrid.RightToLeft;
        _cb.Visible = visible;
        if (text != null)
            _cb.Text = text;
        else
        {
            String temp = getText(GetColumnValueAtRow(cm, row));
            _cb.Text = temp;
        } 
        _cb.Select(_cb.Text.Length, 0);
        if (_cb.Visible)
            DataGridTableStyle.DataGrid.Invalidate(_cb.Bounds);
         
        if (ReadOnly)
            _cb.Enabled = false;
         
        editing = true;
    }

    protected int getMinimumHeight() throws Exception {
        return _cb.PreferredHeight;
    }

    protected int getPreferredHeight(Graphics g, Object o) throws Exception {
        return 0;
    }

    protected Size getPreferredSize(Graphics g, Object o) throws Exception {
        return new Size(0, 0);
    }

    protected void paint(Graphics g, Rectangle rect, CurrencyManager cm, int row) throws Exception {
        paint(g,rect,cm,row,false);
    }

    protected void paint(Graphics g, Rectangle rect, CurrencyManager cm, int row, boolean alignToRight) throws Exception {
        String text = getText(GetColumnValueAtRow(cm, row));
        paintText(g,rect,text,alignToRight);
    }

    protected void paint(Graphics g, Rectangle rect, CurrencyManager cm, int row, Brush backBrush, Brush foreBrush, boolean alignToRight) throws Exception {
        String text = getText(GetColumnValueAtRow(cm, row));
        paintText(g,rect,text,backBrush,foreBrush,alignToRight);
    }

    protected void setDataGridInColumn(DataGrid dg) throws Exception {
        super.SetDataGridInColumn(dg);
        if (_cb.Parent != dg)
        {
            if (_cb.Parent != null)
            {
                _cb.Parent.Controls.Remove(_cb);
            }
             
        }
         
        if (dg != null)
            dg.Controls.Add(_cb);
         
    }

    protected void updateUI(CurrencyManager cm, int row, String text) throws Exception {
        _cb.Text = getText(GetColumnValueAtRow(cm, row));
        if (text != null)
            _cb.Text = text;
         
    }

    private int getDataGridTableGridLineWidth() throws Exception {
        return (DataGridTableStyle.GridLineStyle == DataGridLineStyle.Solid) ? 1 : 0;
    }

    public void endEdit() throws Exception {
        editing = false;
        Invalidate();
    }

    private String getText(Object o) throws Exception {
        if (o == System.DBNull.Value)
            return NullText;
         
        if (o != null)
            return o.ToString();
        else
            return String.Empty; 
    }

    private void hideComboBox() throws Exception {
        if (_cb.Focused)
            DataGridTableStyle.DataGrid.Focus();
         
        _cb.Visible = false;
    }

    private void rollBack() throws Exception {
        _cb.Text = originalText;
    }

    protected void paintText(Graphics g, Rectangle rect, String text, boolean alignToRight) throws Exception {
        Brush backBrush = new SolidBrush(DataGridTableStyle.BackColor);
        Brush foreBrush = new SolidBrush(DataGridTableStyle.ForeColor);
        paintText(g,rect,text,backBrush,foreBrush,alignToRight);
    }

    protected void paintText(Graphics g, Rectangle rect, String text, Brush backBrush, Brush foreBrush, boolean alignToRight) throws Exception {
        StringFormat format = new StringFormat();
        if (alignToRight)
            format.FormatFlags = StringFormatFlags.DirectionRightToLeft;
         
        Alignment __dummyScrutVar0 = Alignment;
        if (__dummyScrutVar0.equals(HorizontalAlignment.Left))
        {
            format.Alignment = StringAlignment.Near;
        }
        else if (__dummyScrutVar0.equals(HorizontalAlignment.Right))
        {
            format.Alignment = StringAlignment.Far;
        }
        else if (__dummyScrutVar0.equals(HorizontalAlignment.Center))
        {
            format.Alignment = StringAlignment.Center;
        }
           
        format.LineAlignment = StringAlignment.Center;
        format.FormatFlags = StringFormatFlags.NoWrap;
        g.FillRectangle(backBrush, rect);
        g.DrawString(text, DataGridTableStyle.DataGrid.Font, foreBrush, rect, format);
        format.Dispose();
    }

}


