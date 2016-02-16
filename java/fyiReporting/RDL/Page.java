//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:32 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import fyiReporting.RDL.PageItem;
import fyiReporting.RDL.PageLine;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.Rows;

public class Page  extends IEnumerable 
{
    // note: all sizes are in points
    int _pageno = new int();
    List<PageItem> _items = new List<PageItem>();
    // array of items on the page
    float _yOffset = new float();
    // current y offset; top margin, page header, other details, ...
    float _xOffset = new float();
    // current x offset; margin, body taken into account?
    int _emptyItems = new int();
    // # of items which constitute empty
    boolean _needSort = new boolean();
    // need sort
    int _lastZIndex = new int();
    // last ZIndex
    System.Collections.Generic.Dictionary<String, Rows> _PageExprReferences = new System.Collections.Generic.Dictionary<String, Rows>();
    // needed to save page header/footer expressions
    public Page(int page) throws Exception {
        _pageno = page;
        _items = new List<PageItem>();
        _emptyItems = 0;
        _needSort = false;
    }

    public void insertObject(PageItem pi) throws Exception {
        addObjectInternal(pi);
        _items.Insert(0, pi);
    }

    public void addObject(PageItem pi) throws Exception {
        addObjectInternal(pi);
        _items.Add(pi);
    }

    private void addObjectInternal(PageItem pi) throws Exception {
        pi.setItemNumber(_items.Count);
        if (_items.Count == 0)
            _lastZIndex = pi.getZIndex();
        else if (_lastZIndex != pi.getZIndex())
            _needSort = true;
          
        // adjust the page item locations
        pi.setX(pi.getX() + _xOffset);
        pi.setY(pi.getY() + _yOffset);
        if (pi instanceof PageLine)
        {
            PageLine pl = pi instanceof PageLine ? (PageLine)pi : (PageLine)null;
            pl.setX2(pl.getX2() + _xOffset);
            pl.setY2(pl.getY2() + _yOffset);
        }
         
    }

    public boolean isEmpty() throws Exception {
        return _items.Count > _emptyItems ? false : true;
    }

    public void sortPageItems() throws Exception {
        if (!_needSort)
            return ;
         
        _items.Sort();
    }

    public void resetEmpty() throws Exception {
        _emptyItems = 0;
    }

    public void setEmpty() throws Exception {
        _emptyItems = _items.Count;
    }

    public int getPageNumber() throws Exception {
        return _pageno;
    }

    public float getXOffset() throws Exception {
        return _xOffset;
    }

    public void setXOffset(float value) throws Exception {
        _xOffset = value;
    }

    public float getYOffset() throws Exception {
        return _yOffset;
    }

    public void setYOffset(float value) throws Exception {
        _yOffset = value;
    }

    public void addPageExpressionRow(fyiReporting.RDL.Report rpt, String exprname, Row r) throws Exception {
        if (exprname == null || r == null)
            return ;
         
        if (_PageExprReferences == null)
            _PageExprReferences = new Dictionary<String, Rows>();
         
        Rows rows = null;
        RefSupport<Rows> refVar___0 = new RefSupport<Rows>();
        _PageExprReferences.TryGetValue(exprname, refVar___0);
        rows = refVar___0.getValue();
        if (rows == null)
        {
            rows = new Rows(rpt);
            rows.setData(new List<Row>());
            _PageExprReferences.Add(exprname, rows);
        }
         
        Row row = new Row(rows,r);
        // have to make a new copy
        row.setRowNumber(rows.getData().Count);
        rows.getData().Add(row);
        return ;
    }

    // add row to rows
    public Rows getPageExpressionRows(String exprname) throws Exception {
        if (_PageExprReferences == null)
            return null;
         
        Rows rows = null;
        RefSupport<Rows> refVar___1 = new RefSupport<Rows>();
        _PageExprReferences.TryGetValue(exprname, refVar___1);
        rows = refVar___1.getValue();
        return rows;
    }

    public void resetPageExpressions() throws Exception {
        _PageExprReferences = null;
    }

    // clear it out; not needed once page header/footer are processed
    public IEnumerator getEnumerator() throws Exception {
        return _items.GetEnumerator();
    }

}


// just loop thru the pages