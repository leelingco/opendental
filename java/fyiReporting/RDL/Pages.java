//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:32 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Page;

/* ====================================================================
    Copyright (C) 2004-2006  fyiReporting Software, LLC
    This file is part of the fyiReporting RDL project.
	
    This library is free software; you can redistribute it and/or modify
    it under the terms of the GNU Lesser General public License as published by
    the Free Software Foundation; either version 2.1 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General public License for more details.
    You should have received a copy of the GNU Lesser General public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
    For additional information, email info@fyireporting.com or visit
    the website www.fyiReporting.com.
*/
/**
* Represents all the pages of a report.  Needed when you need
* render based on pages.  e.g. PDF
*/
public class Pages  extends IEnumerable 
{
    Bitmap _bm = new Bitmap();
    // bitmap to build graphics object
    Graphics _g = new Graphics();
    // graphics object
    fyiReporting.RDL.Report _report;
    // owner report
    List<Page> _pages = new List<Page>();
    // array of pages
    Page _currentPage;
    // the current page; 1st page if null
    float _BottomOfPage = new float();
    // the bottom of the page
    float _PageHeight = new float();
    // default height for all pages
    float _PageWidth = new float();
    // default width for all pages
    public Pages(fyiReporting.RDL.Report r) throws Exception {
        _report = r;
        _pages = new List<Page>();
        // array of Page objects
        _bm = new Bitmap(10, 10);
        // create a small bitmap to base our graphics
        _g = Graphics.FromImage(_bm);
    }

    public fyiReporting.RDL.Report getReport() throws Exception {
        return _report;
    }

    public Page get___idx(int index) throws Exception {
        return _pages[index];
    }

    public int getCount() throws Exception {
        return _pages.Count;
    }

    public void addPage(Page p) throws Exception {
        _pages.Add(p);
        _currentPage = p;
    }

    public void nextOrNew() throws Exception {
        if (_currentPage == this.getLastPage())
            addPage(new Page(getPageCount() + 1));
        else
        {
            _currentPage = _pages[_currentPage.getPageNumber()];
            _currentPage.setEmpty();
        } 
    }

    /**
    * CleanUp should be called after every render to reduce resource utilization.
    */
    public void cleanUp() throws Exception {
        if (_g != null)
        {
            _g.Dispose();
            _g = null;
        }
         
        if (_bm != null)
        {
            _bm.Dispose();
            _bm = null;
        }
         
    }

    public void sortPageItems() throws Exception {
        for (Object __dummyForeachVar0 : this)
        {
            Page p = (Page)__dummyForeachVar0;
            p.sortPageItems();
        }
    }

    public float getBottomOfPage() throws Exception {
        return _BottomOfPage;
    }

    public void setBottomOfPage(float value) throws Exception {
        _BottomOfPage = value;
    }

    public Page getCurrentPage() throws Exception {
        if (_currentPage != null)
            return _currentPage;
         
        if (_pages.Count >= 1)
        {
            _currentPage = _pages[0];
            return _currentPage;
        }
         
        return null;
    }

    public void setCurrentPage(Page value) throws Exception {
        _currentPage = value;
    }

    public Page getFirstPage() throws Exception {
        if (_pages.Count <= 0)
            return null;
        else
            return _pages[0]; 
    }

    public Page getLastPage() throws Exception {
        if (_pages.Count <= 0)
            return null;
        else
            return _pages[_pages.Count - 1]; 
    }

    public float getPageHeight() throws Exception {
        return _PageHeight;
    }

    public void setPageHeight(float value) throws Exception {
        _PageHeight = value;
    }

    public float getPageWidth() throws Exception {
        return _PageWidth;
    }

    public void setPageWidth(float value) throws Exception {
        _PageWidth = value;
    }

    public void removeLastPage() throws Exception {
        Page lp = getLastPage();
        if (lp == null)
            return ;
         
        // if no last page nothing to do
        _pages.RemoveAt(_pages.Count - 1);
        // remove the page
        if (this.getCurrentPage() == lp)
        {
            // reset the current if necessary
            if (_pages.Count <= 0)
                setCurrentPage(null);
            else
                setCurrentPage(_pages[_pages.Count - 1]); 
        }
         
        return ;
    }

    public Graphics getG() throws Exception {
        if (_g == null)
        {
            _bm = new Bitmap(10, 10);
            // create a small bitmap to base our graphics
            _g = Graphics.FromImage(_bm);
        }
         
        return _g;
    }

    public int getPageCount() throws Exception {
        return _pages.Count;
    }

    public IEnumerator getEnumerator() throws Exception {
        return _pages.GetEnumerator();
    }

}


// just loop thru the pages