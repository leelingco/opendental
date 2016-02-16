//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:27 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.util.ListSupport;
import fyiReporting.RDL.__MultiNeedPassword;
import fyiReporting.RDL.NeedPassword;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
* Delegate used to ask for a Data Source Reference password used to decrypt the file.
*/
public class __MultiNeedPassword   implements NeedPassword
{
    public String invoke() throws Exception {
        IList<NeedPassword> copy = new IList<NeedPassword>(), members = this.getInvocationList();
        synchronized (members)
        {
            copy = new LinkedList<NeedPassword>(members);
        }
        NeedPassword prev = null;
        for (Object __dummyForeachVar0 : copy)
        {
            NeedPassword d = (NeedPassword)__dummyForeachVar0;
            if (prev != null)
                prev.invoke();
             
            prev = d;
        }
        return prev.invoke();
    }

    private System.Collections.Generic.IList<NeedPassword> _invocationList = new ArrayList<NeedPassword>();
    public static NeedPassword combine(NeedPassword a, NeedPassword b) throws Exception {
        if (a == null)
            return b;
         
        if (b == null)
            return a;
         
        __MultiNeedPassword ret = new __MultiNeedPassword();
        ret._invocationList = a.getInvocationList();
        /**
        * Main Report definition; this is the top of the tree that contains the complete
        * definition of a instance of a report.
        */
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    // master object counter
    // report log
    // Name of the report
    public static NeedPassword remove(NeedPassword a, NeedPassword b) throws Exception {
        // Description of the report
        // Author of the report
        // Rate at which the report page automatically refreshes, in seconds.  Must be nonnegative.
        if (a == null || b == null)
            return a;
         
        //    If omitted or zero, the report page should not automatically refresh.
        //    Max: 2147483647
        // Describes the data sources from which
        //		data sets are taken for this report.
        // Describes the data that is displayed as
        // part of the report
        System.Collections.Generic.IList<NeedPassword> aInvList = a.getInvocationList();
        // Describes how the body of the report is structured
        // Parameters for the report
        // Custom information to be handed to the report engine
        // Width of the report
        // The header that is output at the top of each page of the report.
        // The footer that is output at the bottom of each page of the report.
        System.Collections.Generic.IList<NeedPassword> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        // Default height for the report.  Default is 11 in.
        // Default width for the report. Default is 8.5 in.
        // Width of the left margin. Default: 0 in
        if (aInvList == newInvList)
        {
            return a;
        }
        else
        {
            // Width of the right margin. Default: 0 in
            // Width of the top margin. Default: 0 in
            // Width of the bottom margin. Default: 0 in
            __MultiNeedPassword ret = new __MultiNeedPassword();
            // Images embedded within the report
            // The primary language of the text. Default is server language.
            ret._invocationList = newInvList;
            return ret;
        } 
    }

    // The <Code> element support; ie VB functions
    // Code modules to make available to the
    //							report for use in expressions.
    // Classes 0-1 Element Classes to instantiate during report initialization
    // The location to a transformation to apply
    // to a report data rendering. This can be a full folder path (e.g. “/xsl/xfrm.xsl”),
    // relative path (e.g. “xfrm.xsl”).
    public System.Collections.Generic.IList<NeedPassword> getInvocationList() throws Exception {
        return _invocationList;
    }

}


// The schema or namespace to use for a report data rendering.