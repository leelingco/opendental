//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:27 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ReportLog;

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
* Error logging (parse and runtime) within report.
*/
public class ReportLog   
{
    List<String> _ErrorItems = null;
    // list of report items
    int _MaxSeverity = 0;
    // maximum severity encountered
    public ReportLog() throws Exception {
    }

    public ReportLog(ReportLog rl) throws Exception {
        if (rl != null && rl.getErrorItems() != null)
        {
            _MaxSeverity = rl.getMaxSeverity();
            _ErrorItems = new List<String>(rl.getErrorItems());
        }
         
    }

    public void logError(ReportLog rl) throws Exception {
        if (rl.getErrorItems().Count == 0)
            return ;
         
        logError(rl.getMaxSeverity(),rl.getErrorItems());
    }

    public void logError(int severity, String item) throws Exception {
        if (_ErrorItems == null)
            // create log if first time
            _ErrorItems = new List<String>();
         
        if (severity > _MaxSeverity)
            _MaxSeverity = severity;
         
        String msg = "Severity: " + Convert.ToString(severity) + " - " + item;
        _ErrorItems.Add(msg);
        if (severity >= 12)
            throw new Exception(msg);
         
        return ;
    }

    // terminate the processing
    public void logError(int severity, List<String> list) throws Exception {
        if (_ErrorItems == null)
            // create log if first time
            _ErrorItems = new List<String>();
         
        if (severity > _MaxSeverity)
            _MaxSeverity = severity;
         
        _ErrorItems.AddRange(list);
        return ;
    }

    public void reset() throws Exception {
        _ErrorItems = null;
        if (_MaxSeverity < 8)
            // we keep the severity to indicate we can't run report
            _MaxSeverity = 0;
         
    }

    public List<String> getErrorItems() throws Exception {
        return _ErrorItems;
    }

    public int getMaxSeverity() throws Exception {
        return _MaxSeverity;
    }

    public void setMaxSeverity(int value) throws Exception {
        _MaxSeverity = value;
    }

}


