//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
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
* CodeModule definition and processing.
*/
public class CodeModule  extends ReportLink 
{
    String _CodeModule = new String();
    // Name of the code module to load
    Assembly _LoadedAssembly = null;
    //
    boolean bLoadFailed = false;
    public CodeModule(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _CodeModule = xNode.InnerText;
    }

    public Assembly loadedAssembly() throws Exception {
        if (bLoadFailed)
            return null;
         
        // We only try to load once.
        if (_LoadedAssembly == null)
        {
            try
            {
                _LoadedAssembly = XmlUtil.assemblyLoadFrom(_CodeModule);
            }
            catch (Exception e)
            {
                OwnerReport.rl.LogError(4, String.Format("CodeModule {0} failed to load.  {1}", _CodeModule, e.Message));
                bLoadFailed = true;
            }
        
        }
         
        return _LoadedAssembly;
    }

    public void finalPass() throws Exception {
        return ;
    }

    public String getCdModule() throws Exception {
        return _CodeModule;
    }

    public void setCdModule(String value) throws Exception {
        _CodeModule = value;
    }

}


