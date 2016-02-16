//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import CS2JNet.System.StringSupport;
import fyiReporting.RDL.CodeModule;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;

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
* CodeModules definition and processing.
*/
public class CodeModules  extends ReportLink implements IEnumerable
{
    List<CodeModule> _Items = new List<CodeModule>();
    // list of code module
    public CodeModules(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Items = new List<CodeModule>();
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            if (StringSupport.equals(xNodeLoop.Name, "CodeModule"))
            {
                CodeModule cm = new CodeModule(r,this,xNodeLoop);
                _Items.Add(cm);
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown CodeModules element '" + xNodeLoop.Name + "' ignored.");
            } 
        }
        if (_Items.Count == 0)
            OwnerReport.rl.logError(8,"For CodeModules at least one CodeModule is required.");
        else
            _Items.TrimExcess(); 
    }

    /**
    * Return the Type given a class name.  Searches the CodeModules that are specified
    * in the report.
    */
    public Type get___idx(String s) throws Exception {
        Type tp = null;
        try
        {
            for (Object __dummyForeachVar1 : _Items)
            {
                // loop thru all the codemodules looking for the assembly
                //  that contains this type
                CodeModule cm = (CodeModule)__dummyForeachVar1;
                Assembly a = cm.loadedAssembly();
                if (a != null)
                {
                    tp = a.GetType(s);
                    if (tp != null)
                        break;
                     
                }
                 
            }
        }
        catch (Exception ex)
        {
            OwnerReport.rl.LogError(4, String.Format("Exception finding type. {1}", ex.Message));
        }

        return tp;
    }

    public void finalPass() throws Exception {
        for (Object __dummyForeachVar2 : _Items)
        {
            CodeModule cm = (CodeModule)__dummyForeachVar2;
            cm.finalPass();
        }
        return ;
    }

    public void loadModules() throws Exception {
        for (Object __dummyForeachVar3 : _Items)
        {
            CodeModule cm = (CodeModule)__dummyForeachVar3;
            cm.loadedAssembly();
        }
    }

    public List<CodeModule> getItems() throws Exception {
        return _Items;
    }

    public IEnumerator getEnumerator() throws Exception {
        return _Items.GetEnumerator();
    }

}


