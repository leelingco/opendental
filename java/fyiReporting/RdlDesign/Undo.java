//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:23 PM
//

package fyiReporting.RdlDesign;

import fyiReporting.RdlDesign.NodeChangedUndo;
import fyiReporting.RdlDesign.NodeInsertedUndo;
import fyiReporting.RdlDesign.NodeRemovedUndo;
import fyiReporting.RdlDesign.UndoGroup;
import fyiReporting.RdlDesign.UndoItem;

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
public class Undo   
{
    Stack _actions = new Stack();
    boolean _Undoing = false;
    XmlDocument _doc = new XmlDocument();
    UndoGroup _currentUndoGroup = null;
    Object _pState = null;
    // state saved with previous changing event
    int _UndoLevels = new int();
    boolean _GroupsOnly = false;
    public Undo(XmlDocument doc, int levels) throws Exception {
        _doc = doc;
        _UndoLevels = levels;
        // we don't currently support; need to write special Stack
        _doc.NodeChanging += new XmlNodeChangedEventHandler(NodeChanging);
        _doc.NodeChanged += new XmlNodeChangedEventHandler(NodeChanged);
        _doc.NodeInserting += new XmlNodeChangedEventHandler(NodeChanging);
        _doc.NodeInserted += new XmlNodeChangedEventHandler(NodeChanged);
        _doc.NodeRemoving += new XmlNodeChangedEventHandler(NodeChanging);
        _doc.NodeRemoved += new XmlNodeChangedEventHandler(NodeChanged);
        _actions = new Stack();
    }

    public boolean getGroupsOnly() throws Exception {
        return _GroupsOnly;
    }

    public void setGroupsOnly(boolean value) throws Exception {
        _GroupsOnly = value;
    }

    public boolean getCanUndo() throws Exception {
        return _actions.Count > 0;
    }

    public String getDescription() throws Exception {
        if (_actions.Count == 0)
            return "";
         
        UndoItem ui = (UndoItem)_actions.Peek();
        return ui.getDescription();
    }

    public void reset() throws Exception {
        _actions.Clear();
    }

    public boolean getUndoing() throws Exception {
        return _Undoing;
    }

    public void setUndoing(boolean value) throws Exception {
        _Undoing = value;
    }

    private void nodeChanging(Object sender, XmlNodeChangedEventArgs e) throws Exception {
        if (this._Undoing)
            return ;
         
        Action __dummyScrutVar0 = e.Action;
        if (__dummyScrutVar0.equals(XmlNodeChangedAction.Insert))
        {
            _pState = NodeInsertedUndo.previousState(e);
        }
        else if (__dummyScrutVar0.equals(XmlNodeChangedAction.Remove))
        {
            _pState = NodeRemovedUndo.previousState(e);
        }
        else if (__dummyScrutVar0.equals(XmlNodeChangedAction.Change))
        {
            _pState = NodeChangedUndo.previousState(e);
        }
        else
        {
            throw new Exception("Unknown Action");
        }   
    }

    private void nodeChanged(Object sender, XmlNodeChangedEventArgs e) throws Exception {
        if (this._Undoing)
        {
            // if we're undoing ignore the event since it is the result of an undo
            _pState = null;
            _Undoing = false;
            return ;
        }
         
        UndoItem undo = null;
        Action __dummyScrutVar1 = e.Action;
        if (__dummyScrutVar1.equals(XmlNodeChangedAction.Insert))
        {
            undo = new NodeInsertedUndo(e,_pState);
        }
        else if (__dummyScrutVar1.equals(XmlNodeChangedAction.Remove))
        {
            undo = new NodeRemovedUndo(e,_pState);
        }
        else if (__dummyScrutVar1.equals(XmlNodeChangedAction.Change))
        {
            undo = new NodeChangedUndo(e,_pState);
        }
        else
        {
            throw new Exception("Unknown Action");
        }   
        _pState = null;
        if (_currentUndoGroup != null)
        {
            _currentUndoGroup.addUndoItem(undo);
        }
        else if (getGroupsOnly())
        {
            _pState = null;
        }
        else
        {
            _actions.Push(undo);
        }  
    }

    public void undo() throws Exception {
        UndoItem undoItem = null;
        if (_actions.Count == 0)
            return ;
         
        undoItem = (UndoItem)_actions.Pop();
        _Undoing = true;
        undoItem.undo();
    }

    public void startUndoGroup(String description) throws Exception {
        UndoGroup ug = new UndoGroup(this, description);
        _currentUndoGroup = ug;
        _actions.Push(ug);
    }

    public void endUndoGroup() throws Exception {
        endUndoGroup(true);
    }

    // we want to keep the undo items on the stack
    public void endUndoGroup(boolean keepChanges) throws Exception {
        if (_currentUndoGroup == null)
            return ;
         
        // group contains no items; or user doesn't want changes to part of undo
        //
        if (_currentUndoGroup.getCount() == 0 || !keepChanges)
        {
            UndoGroup ug = _actions.Pop() instanceof UndoGroup ? (UndoGroup)_actions.Pop() : (UndoGroup)null;
            // get rid of the empty group
            if (ug != _currentUndoGroup)
                throw new Exception("Internal logic error: EndGroup doesn't match StartGroup");
             
        }
         
        _currentUndoGroup = null;
    }

}


