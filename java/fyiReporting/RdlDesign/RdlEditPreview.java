//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:22 PM
//

package fyiReporting.RdlDesign;

import CS2JNet.JavaSupport.util.ListSupport;
import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import fyiReporting.RDL.StyleInfo;
import fyiReporting.RdlDesign.DesignCtl;
import fyiReporting.RdlDesign.DesignCtl.__MultiHeightEventHandler;
import fyiReporting.RdlDesign.DesignCtl.__MultiOpenSubreportEventHandler;
import fyiReporting.RdlDesign.DesignEditLines;
import fyiReporting.RdlDesign.DesignXmlDraw;
import fyiReporting.RdlDesign.HeightEventArgs;
import fyiReporting.RdlDesign.SubReportEventArgs;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
/**
* Summary description for RdlEditPreview.
*/
public class RdlEditPreview  extends System.Windows.Forms.UserControl 
{
    private System.Windows.Forms.TabControl tcEHP = new System.Windows.Forms.TabControl();
    private System.Windows.Forms.TabPage tpEditor = new System.Windows.Forms.TabPage();
    private System.Windows.Forms.TabPage tpBrowser = new System.Windows.Forms.TabPage();
    private System.Windows.Forms.RichTextBox tbEditor = new System.Windows.Forms.RichTextBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private fyiReporting.RdlViewer.RdlViewer rdlPreview;
    private System.Windows.Forms.TabPage tpDesign = new System.Windows.Forms.TabPage();
    private DesignCtl dcDesign;
    public static class __MultiRdlChangeHandler   implements fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler
    {
        public void invoke(Object sender, EventArgs e) throws Exception {
            IList<fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler> copy = new IList<fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler>(), members = this.getInvocationList();
            synchronized (members)
            {
                copy = new LinkedList<fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler>(members);
            }
            for (Object __dummyForeachVar0 : copy)
            {
                fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler d = (fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler)__dummyForeachVar0;
                d.invoke(sender, e);
            }
        }

        private System.Collections.Generic.IList<fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler> _invocationList = new ArrayList<fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler>();
        public static fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler combine(fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler a, fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler b) throws Exception {
            if (a == null)
                return b;
             
            if (b == null)
                return a;
             
            __MultiRdlChangeHandler ret = new __MultiRdlChangeHandler();
            ret._invocationList = a.getInvocationList();
            ret._invocationList.addAll(b.getInvocationList());
            return ret;
        }

        public static fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler remove(fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler a, fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler b) throws Exception {
            if (a == null || b == null)
                return a;
             
            System.Collections.Generic.IList<fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler> aInvList = a.getInvocationList();
            System.Collections.Generic.IList<fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
            if (aInvList == newInvList)
            {
                return a;
            }
            else
            {
                __MultiRdlChangeHandler ret = new __MultiRdlChangeHandler();
                ret._invocationList = newInvList;
                return ret;
            } 
        }

        public System.Collections.Generic.IList<fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler> getInvocationList() throws Exception {
            return _invocationList;
        }
    
    }

    public static interface RdlChangeHandler   
    {
        void invoke(Object sender, EventArgs e) throws Exception ;

        System.Collections.Generic.IList<fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler> getInvocationList() throws Exception ;
    
    }

    public fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler OnRdlChanged;
    public fyiReporting.RdlDesign.DesignCtl.HeightEventHandler OnHeightChanged;
    public fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler OnSelectionChanged;
    public fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler OnSelectionMoved;
    public fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler OnReportItemInserted;
    public fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler OnDesignTabChanged;
    public fyiReporting.RdlDesign.DesignCtl.OpenSubreportEventHandler OnOpenSubreport;
    // When toggling between the items we need to track who has latest changes
    String _DesignChanged = new String();
    // last designer that triggered change
    String _CurrentTab = "design";
    private DesignEditLines pbLines;
    int filePosition = 0;
    // file position; for use with search
    public RdlEditPreview() throws Exception {
        // This call is required by the Windows.Forms Form Designer.
        initializeComponent();
        rdlPreview.setZoom(1);
        // force default zoom to 1
        // initialize the design tab
        dcDesign = new DesignCtl();
        tpDesign.Controls.Add(dcDesign);
        dcDesign.Dock = System.Windows.Forms.DockStyle.Fill;
        dcDesign.Location = new System.Drawing.Point(0, 0);
        dcDesign.Name = "dcDesign";
        dcDesign.Size = new System.Drawing.Size(480, 350);
        dcDesign.TabIndex = 0;
        dcDesign.ReportChanged += new System.EventHandler(dcDesign_ReportChanged);
        dcDesign.HeightChanged = __MultiHeightEventHandler.combine(dcDesign.HeightChanged,new fyiReporting.RdlDesign.DesignCtl.HeightEventHandler() 
          { 
            public System.Void invoke(System.Object source, HeightEventArgs e) throws Exception {
                dcDesign_HeightChanged(source, e);
            }

            public List<fyiReporting.RdlDesign.DesignCtl.HeightEventHandler> getInvocationList() throws Exception {
                List<fyiReporting.RdlDesign.DesignCtl.HeightEventHandler> ret = new ArrayList<fyiReporting.RdlDesign.DesignCtl.HeightEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        dcDesign.SelectionChanged += new System.EventHandler(dcDesign_SelectionChanged);
        dcDesign.SelectionMoved += new System.EventHandler(dcDesign_SelectionMoved);
        dcDesign.ReportItemInserted += new System.EventHandler(dcDesign_ReportItemInserted);
        dcDesign.OpenSubreport = __MultiOpenSubreportEventHandler.combine(dcDesign.OpenSubreport,new fyiReporting.RdlDesign.DesignCtl.OpenSubreportEventHandler() 
          { 
            public System.Void invoke(System.Object source, SubReportEventArgs e) throws Exception {
                dcDesign_OpenSubreport(source, e);
            }

            public List<fyiReporting.RdlDesign.DesignCtl.OpenSubreportEventHandler> getInvocationList() throws Exception {
                List<fyiReporting.RdlDesign.DesignCtl.OpenSubreportEventHandler> ret = new ArrayList<fyiReporting.RdlDesign.DesignCtl.OpenSubreportEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        tbEditor.SelectionChanged += new EventHandler(tbEditor_SelectionChanged);
        // adjust size of line box by measuring a large #
        Graphics g = this.CreateGraphics();
        try
        {
            {
                this.pbLines.Width = (int)(g.MeasureString("99999", tbEditor.Font).Width);
            }
        }
        finally
        {
            if (g != null)
                Disposable.mkDisposable(g).dispose();
             
        }
    }

    public DesignCtl getDesignCtl() throws Exception {
        return dcDesign;
    }

    public DesignXmlDraw getDrawCtl() throws Exception {
        return dcDesign.getDrawCtl();
    }

    public XmlDocument getReportDocument() throws Exception {
        return dcDesign.getReportDocument();
    }

    public String getDesignTab() throws Exception {
        return _CurrentTab;
    }

    public void setDesignTab(String value) throws Exception {
        System.String __dummyScrutVar0 = value;
        if (__dummyScrutVar0.equals("design"))
        {
            tcEHP.SelectedIndex = 0;
        }
        else if (__dummyScrutVar0.equals("edit"))
        {
            tcEHP.SelectedIndex = 1;
        }
        else if (__dummyScrutVar0.equals("preview"))
        {
            tcEHP.SelectedIndex = 2;
        }
           
    }

    public void setFocus() throws Exception {
        System.String __dummyScrutVar1 = _CurrentTab;
        if (__dummyScrutVar1.equals("edit"))
        {
            tbEditor.Focus();
        }
        else if (__dummyScrutVar1.equals("preview"))
        {
            rdlPreview.Focus();
        }
        else if (__dummyScrutVar1.equals("design"))
        {
            dcDesign.setFocus();
        }
           
    }

    public void showEditLines(boolean bShow) throws Exception {
        pbLines.Visible = bShow;
    }

    public boolean getShowReportItemOutline() throws Exception {
        return dcDesign.getShowReportItemOutline();
    }

    public void setShowReportItemOutline(boolean value) throws Exception {
        dcDesign.setShowReportItemOutline(value);
    }

    public String getText() throws Exception {
        if (StringSupport.equals(_CurrentTab, "design"))
            return dcDesign.getReportSource();
        else
            return tbEditor.Text; 
    }

    public void setText(String value) throws Exception {
        if (StringSupport.equals(_CurrentTab, "edit"))
            tbEditor.Text = value;
        else
        {
            dcDesign.setReportSource(value);
        } 
        if (StringSupport.equals(_CurrentTab, "preview"))
        {
            _CurrentTab = "design";
            tcEHP.SelectedIndex = 0;
        }
         
    }

    // Force current tab to design
    public StyleInfo getSelectedStyle() throws Exception {
        return dcDesign.getSelectedStyle();
    }

    public String getSelectionName() throws Exception {
        return dcDesign.getSelectionName();
    }

    public PointF getSelectionPosition() throws Exception {
        return dcDesign.getSelectionPosition();
    }

    public SizeF getSelectionSize() throws Exception {
        return dcDesign.getSelectionSize();
    }

    public void applyStyleToSelected(String name, String v) throws Exception {
        if (StringSupport.equals(_CurrentTab, "design"))
            dcDesign.applyStyleToSelected(name,v);
         
    }

    public void setSelectedText(String v) throws Exception {
        if (StringSupport.equals(_CurrentTab, "design"))
            dcDesign.setSelectedText(v);
         
    }

    public boolean getCanEdit() throws Exception {
        return StringSupport.equals(_CurrentTab, "edit") || StringSupport.equals(_CurrentTab, "design") ? true : false;
    }

    public boolean getModified() throws Exception {
        return tbEditor.Modified;
    }

    public void setModified(boolean value) throws Exception {
        _DesignChanged = _CurrentTab;
        tbEditor.Modified = value;
    }

    public String getUndoDescription() throws Exception {
        return StringSupport.equals(_CurrentTab, "design") ? dcDesign.getUndoDescription() : "";
    }

    public void startUndoGroup(String description) throws Exception {
        if (StringSupport.equals(_CurrentTab, "design"))
            dcDesign.startUndoGroup(description);
         
    }

    public void endUndoGroup(boolean keepChanges) throws Exception {
        if (StringSupport.equals(_CurrentTab, "design"))
            dcDesign.endUndoGroup(keepChanges);
         
    }

    public boolean getCanUndo() throws Exception {
        System.String __dummyScrutVar2 = _CurrentTab;
        if (__dummyScrutVar2.equals("design"))
        {
            return dcDesign.getCanUndo();
        }
        else if (__dummyScrutVar2.equals("edit"))
        {
            return tbEditor.CanUndo;
        }
        else
        {
            return false;
        }  
    }

    public boolean getCanRedo() throws Exception {
        System.String __dummyScrutVar3 = _CurrentTab;
        if (__dummyScrutVar3.equals("design"))
        {
            return dcDesign.getCanUndo();
        }
        else if (__dummyScrutVar3.equals("edit"))
        {
            return tbEditor.CanUndo;
        }
        else
        {
            return false;
        }  
    }

    public int getSelectionLength() throws Exception {
        System.String __dummyScrutVar4 = _CurrentTab;
        if (__dummyScrutVar4.equals("design"))
        {
            return dcDesign.getSelectionCount();
        }
        else if (__dummyScrutVar4.equals("edit"))
        {
            return tbEditor.SelectionLength;
        }
        else
        {
            return 0;
        }  
    }

    public String getSelectedText() throws Exception {
        System.String __dummyScrutVar5 = _CurrentTab;
        if (__dummyScrutVar5.equals("design"))
        {
            return dcDesign.getSelectedText();
        }
        else if (__dummyScrutVar5.equals("edit"))
        {
            return tbEditor.SelectedText;
        }
        else
        {
            return "";
        }  
    }

    public void setSelectedText(String value) throws Exception {
        if (StringSupport.equals(_CurrentTab, "edit") && tbEditor.SelectionLength >= 0)
            tbEditor.SelectedText = value;
        else if (StringSupport.equals(_CurrentTab, "design") && value.Length == 0)
            dcDesign.delete();
          
    }

    public void cleanUp() throws Exception {
    }

    public void clearUndo() throws Exception {
        System.String __dummyScrutVar6 = _CurrentTab;
        if (__dummyScrutVar6.equals("design"))
        {
            dcDesign.clearUndo();
        }
        else if (__dummyScrutVar6.equals("edit"))
        {
            tbEditor.ClearUndo();
        }
        else
        {
        }  
    }

    public void undo() throws Exception {
        System.String __dummyScrutVar7 = _CurrentTab;
        if (__dummyScrutVar7.equals("design"))
        {
            dcDesign.undo();
        }
        else if (__dummyScrutVar7.equals("edit"))
        {
            tbEditor.Undo();
        }
        else
        {
        }  
    }

    public void redo() throws Exception {
        System.String __dummyScrutVar8 = _CurrentTab;
        if (__dummyScrutVar8.equals("design"))
        {
            dcDesign.redo();
        }
        else if (__dummyScrutVar8.equals("edit"))
        {
            tbEditor.Redo();
        }
        else
        {
        }  
    }

    public void cut() throws Exception {
        System.String __dummyScrutVar9 = _CurrentTab;
        if (__dummyScrutVar9.equals("design"))
        {
            dcDesign.cut();
        }
        else if (__dummyScrutVar9.equals("edit"))
        {
            tbEditor.Cut();
        }
        else
        {
        }  
    }

    public void copy() throws Exception {
        System.String __dummyScrutVar10 = _CurrentTab;
        if (__dummyScrutVar10.equals("design"))
        {
            dcDesign.copy();
        }
        else if (__dummyScrutVar10.equals("edit"))
        {
            tbEditor.Copy();
        }
        else
        {
        }  
    }

    public void clear() throws Exception {
        System.String __dummyScrutVar11 = _CurrentTab;
        if (__dummyScrutVar11.equals("design"))
        {
            dcDesign.clear();
        }
        else if (__dummyScrutVar11.equals("edit"))
        {
            tbEditor.Clear();
        }
        else
        {
        }  
    }

    public void paste() throws Exception {
        System.String __dummyScrutVar12 = _CurrentTab;
        if (__dummyScrutVar12.equals("design"))
        {
            dcDesign.paste();
        }
        else if (__dummyScrutVar12.equals("edit"))
        {
            pasteText();
        }
        else
        {
        }  
    }

    void pasteText() throws Exception {
        // The Paste function of RTF carries too much information; formatting etc.
        //   We only allow pasting of text information
        IDataObject iData = Clipboard.GetDataObject();
        if (iData == null)
            return ;
         
        if (!iData.GetDataPresent(DataFormats.Text))
            return ;
         
        String t = (String)iData.GetData(DataFormats.Text);
        this.tbEditor.SelectedText = t;
    }

    public void selectAll() throws Exception {
        System.String __dummyScrutVar13 = _CurrentTab;
        if (__dummyScrutVar13.equals("design"))
        {
            dcDesign.selectAll();
        }
        else if (__dummyScrutVar13.equals("edit"))
        {
            tbEditor.SelectAll();
        }
        else
        {
        }  
    }

    public String getCurrentInsert() throws Exception {
        return dcDesign.getCurrentInsert();
    }

    public void setCurrentInsert(String value) throws Exception {
        dcDesign.setCurrentInsert(value);
    }

    public int getCurrentLine() throws Exception {
        int v = tbEditor.SelectionStart;
        return this.tbEditor.GetLineFromCharIndex(v) + 1;
    }

    public int getCurrentCh() throws Exception {
        int v = tbEditor.SelectionStart;
        int line = tbEditor.GetLineFromCharIndex(v);
        // Go back a character at a time until you hit previous line
        int c = 0;
        while (--v >= 0 && line == tbEditor.GetLineFromCharIndex(v))
        c++;
        return c + 1;
    }

    /**
    * Zoom
    */
    public float getZoom() throws Exception {
        return this.rdlPreview.getZoom();
    }

    public void setZoom(float value) throws Exception {
        this.rdlPreview.setZoom(value);
    }

    /**
    * ZoomMode
    */
    public fyiReporting.RdlViewer.ZoomEnum getZoomMode() throws Exception {
        return this.rdlPreview.getZoomMode();
    }

    public void setZoomMode(fyiReporting.RdlViewer.ZoomEnum value) throws Exception {
        this.rdlPreview.setZoomMode(value);
    }

    public void findNext(Control ctl, String str, boolean matchCase) throws Exception {
        findNext(ctl,str,matchCase,true);
    }

    public void findNext(Control ctl, String str, boolean matchCase, boolean showEndMsg) throws Exception {
        if (!StringSupport.equals(_CurrentTab, "edit"))
            return ;
         
        int nStart = tbEditor.Find(str, filePosition, matchCase ? RichTextBoxFinds.MatchCase : RichTextBoxFinds.None);
        if (nStart < 0)
        {
            if (showEndMsg)
                MessageBox.Show(ctl, "Reached End of Document.");
             
            filePosition = 0;
            return ;
        }
         
        int nLength = str.Length;
        tbEditor.ScrollToCaret();
        filePosition = nStart + nLength;
    }

    public void replaceNext(Control ctl, String str, String strReplace, boolean matchCase) throws Exception {
        if (!StringSupport.equals(_CurrentTab, "edit"))
            return ;
         
        try
        {
            int nStart = tbEditor.Find(str, filePosition, matchCase ? RichTextBoxFinds.MatchCase : RichTextBoxFinds.None);
            int nLength = str.Length;
            tbEditor.Text = tbEditor.Text.Remove(nStart, nLength);
            tbEditor.Text = tbEditor.Text.Insert(nStart, strReplace);
            tbEditor.ScrollToCaret();
        }
        catch (Exception e)
        {
            e.ToString();
            MessageBox.Show(ctl, "Reached End of Document.");
            filePosition = 0;
        }
    
    }

    public void replaceAll(Control ctl, String str, String strReplace, boolean matchCase) throws Exception {
        if (!StringSupport.equals(_CurrentTab, "edit"))
            return ;
         
        try
        {
            int nStart = tbEditor.Text.IndexOf(str, filePosition);
            int nLength = str.Length;
            tbEditor.Select(nStart, nLength);
            tbEditor.Text = tbEditor.Text.Replace(str, strReplace);
            tbEditor.ScrollToCaret();
            filePosition = nStart + nLength;
        }
        catch (Exception e)
        {
            e.ToString();
            MessageBox.Show(ctl, "Reached End of Document.");
            filePosition = 0;
        }
    
    }

    public void goto(Control ctl, int nLine) throws Exception {
        if (!StringSupport.equals(_CurrentTab, "edit"))
            return ;
         
        int offset = 0;
        nLine = Math.Min(Math.Max(0, nLine - 1), tbEditor.Lines.Length - 1);
        // don't go off the ends
        offset = tbEditor.GetFirstCharIndexFromLine(nLine);
        //   Before .Net 2
        //			for ( int i = 0; i < nLine - 1 && i < tbEditor.Lines.Length; ++i )
        //				offset += this.tbEditor.Lines[i].Length + 1;
        Control savectl = this.ActiveControl;
        tbEditor.Focus();
        tbEditor.Select(offset, this.tbEditor.Lines[nLine].Length);
        this.ActiveControl = savectl;
    }

    /**
    * Clean up any resources being used.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing)
        {
            if (components != null)
            {
                components.Dispose();
            }
             
        }
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        this.tcEHP = new System.Windows.Forms.TabControl();
        this.tpDesign = new System.Windows.Forms.TabPage();
        this.tpEditor = new System.Windows.Forms.TabPage();
        this.tbEditor = new System.Windows.Forms.RichTextBox();
        this.pbLines = new DesignEditLines(this.tbEditor);
        this.tpBrowser = new System.Windows.Forms.TabPage();
        this.rdlPreview = new fyiReporting.RdlViewer.RdlViewer();
        this.tcEHP.SuspendLayout();
        this.tpEditor.SuspendLayout();
        ((System.ComponentModel.ISupportInitialize)(this.pbLines)).BeginInit();
        this.tpBrowser.SuspendLayout();
        this.SuspendLayout();
        //
        // tcEHP
        //
        this.tcEHP.Alignment = System.Windows.Forms.TabAlignment.Bottom;
        this.tcEHP.Controls.Add(this.tpDesign);
        this.tcEHP.Controls.Add(this.tpEditor);
        this.tcEHP.Controls.Add(this.tpBrowser);
        this.tcEHP.Dock = System.Windows.Forms.DockStyle.Fill;
        this.tcEHP.Location = new System.Drawing.Point(0, 0);
        this.tcEHP.Name = "tcEHP";
        this.tcEHP.SelectedIndex = 0;
        this.tcEHP.ShowToolTips = true;
        this.tcEHP.Size = new System.Drawing.Size(488, 376);
        this.tcEHP.TabIndex = 0;
        this.tcEHP.SelectedIndexChanged += new System.EventHandler(this.tcEHP_SelectedIndexChanged);
        //
        // tpDesign
        //
        this.tpDesign.Location = new System.Drawing.Point(4, 4);
        this.tpDesign.Name = "tpDesign";
        this.tpDesign.Size = new System.Drawing.Size(480, 350);
        this.tpDesign.TabIndex = 3;
        this.tpDesign.Tag = "design";
        this.tpDesign.Text = "Designer";
        //
        // tpEditor
        //
        this.tpEditor.Controls.Add(this.tbEditor);
        this.tpEditor.Controls.Add(this.pbLines);
        this.tpEditor.Location = new System.Drawing.Point(4, 4);
        this.tpEditor.Name = "tpEditor";
        this.tpEditor.Size = new System.Drawing.Size(480, 350);
        this.tpEditor.TabIndex = 0;
        this.tpEditor.Tag = "edit";
        this.tpEditor.Text = "RDL Text";
        this.tpEditor.ToolTipText = "Edit Report Syntax";
        //
        // tbEditor
        //
        this.tbEditor.AcceptsTab = true;
        this.tbEditor.BorderStyle = System.Windows.Forms.BorderStyle.None;
        this.tbEditor.Dock = System.Windows.Forms.DockStyle.Fill;
        this.tbEditor.HideSelection = false;
        this.tbEditor.Location = new System.Drawing.Point(32, 0);
        this.tbEditor.Name = "tbEditor";
        this.tbEditor.Size = new System.Drawing.Size(448, 350);
        this.tbEditor.TabIndex = 0;
        this.tbEditor.Text = "";
        this.tbEditor.WordWrap = false;
        this.tbEditor.TextChanged += new System.EventHandler(this.tbEditor_TextChanged);
        //
        // pbLines
        //
        this.pbLines.Dock = System.Windows.Forms.DockStyle.Left;
        this.pbLines.Location = new System.Drawing.Point(0, 0);
        this.pbLines.Name = "pbLines";
        this.pbLines.Size = new System.Drawing.Size(32, 350);
        this.pbLines.TabIndex = 1;
        this.pbLines.TabStop = false;
        //
        // tpBrowser
        //
        this.tpBrowser.Controls.Add(this.rdlPreview);
        this.tpBrowser.Location = new System.Drawing.Point(4, 4);
        this.tpBrowser.Name = "tpBrowser";
        this.tpBrowser.Size = new System.Drawing.Size(480, 350);
        this.tpBrowser.TabIndex = 2;
        this.tpBrowser.Tag = "preview";
        this.tpBrowser.Text = "Preview";
        //
        // rdlPreview
        //
        this.rdlPreview.Cursor = System.Windows.Forms.Cursors.Default;
        this.rdlPreview.Dock = System.Windows.Forms.DockStyle.Fill;
        this.rdlPreview.setFolder(null);
        this.rdlPreview.Location = new System.Drawing.Point(0, 0);
        this.rdlPreview.Name = "rdlPreview";
        this.rdlPreview.setPageCurrent(1);
        this.rdlPreview.setParameters(null);
        this.rdlPreview.setReportName(null);
        this.rdlPreview.setScrollMode(fyiReporting.RdlViewer.ScrollModeEnum.Continuous);
        this.rdlPreview.setShowParameterPanel(true);
        this.rdlPreview.Size = new System.Drawing.Size(480, 350);
        this.rdlPreview.setSourceFile(null);
        this.rdlPreview.setSourceRdl(null);
        this.rdlPreview.TabIndex = 0;
        this.rdlPreview.Text = "rdlViewer1";
        this.rdlPreview.setZoom(0.5474582F);
        this.rdlPreview.setZoomMode(fyiReporting.RdlViewer.ZoomEnum.FitWidth);
        //
        // RdlEditPreview
        //
        this.Controls.Add(this.tcEHP);
        this.Name = "RdlEditPreview";
        this.Size = new System.Drawing.Size(488, 376);
        this.tcEHP.ResumeLayout(false);
        this.tpEditor.ResumeLayout(false);
        ((System.ComponentModel.ISupportInitialize)(this.pbLines)).EndInit();
        this.tpBrowser.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private void tbEditor_TextChanged(Object sender, System.EventArgs e) throws Exception {
        _DesignChanged = "edit";
        if (OnRdlChanged != null)
        {
            OnRdlChanged(this, e);
        }
         
    }

    private void dcDesign_ReportChanged(Object sender, System.EventArgs e) throws Exception {
        _DesignChanged = "design";
        tbEditor.Modified = true;
        if (OnRdlChanged != null)
        {
            OnRdlChanged(this, e);
        }
         
    }

    private void dcDesign_HeightChanged(Object sender, HeightEventArgs e) throws Exception {
        if (OnHeightChanged != null)
        {
            OnHeightChanged.invoke(this,e);
        }
         
    }

    private void dcDesign_ReportItemInserted(Object sender, System.EventArgs e) throws Exception {
        if (OnReportItemInserted != null)
        {
            OnReportItemInserted(this, e);
        }
         
    }

    private void dcDesign_OpenSubreport(Object sender, SubReportEventArgs e) throws Exception {
        if (OnOpenSubreport != null)
        {
            OnOpenSubreport.invoke(this,e);
        }
         
    }

    private void dcDesign_SelectionChanged(Object sender, System.EventArgs e) throws Exception {
        if (OnSelectionChanged != null)
        {
            OnSelectionChanged(this, e);
        }
         
    }

    private void dcDesign_SelectionMoved(Object sender, System.EventArgs e) throws Exception {
        if (OnSelectionMoved != null)
        {
            OnSelectionMoved(this, e);
        }
         
    }

    private void tcEHP_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        TabControl tc = (TabControl)sender;
        String tag = (String)tc.TabPages[tc.SelectedIndex].Tag;
        // Sync up the various pane whenever they switch so the editor is always accurate
        System.String __dummyScrutVar14 = _DesignChanged;
        // Sync up the editor in every case
        if (__dummyScrutVar14.equals("design"))
        {
            // sync up the editor
            tbEditor.Text = dcDesign.getReportSource();
            tbEditor.Modified = true;
        }
        else if (__dummyScrutVar14.equals("edit") || __dummyScrutVar14.equals("preview"))
        {
        }
        else
        {
            // this can happen the first time
            if (tbEditor.Text.Length <= 0)
                tbEditor.Text = dcDesign.getReportSource();
             
        }  
        // Below sync up the changed item
        if (StringSupport.equals(tag, "preview"))
        {
            if (!StringSupport.equals(rdlPreview.getSourceRdl(), tbEditor.Text))
                // sync up preview
                this.rdlPreview.setSourceRdl(this.tbEditor.Text);
             
        }
        else if (StringSupport.equals(tag, "design"))
        {
            if (!StringSupport.equals(_DesignChanged, "design") && _DesignChanged != null)
            {
                try
                {
                    dcDesign.setReportSource(tbEditor.Text);
                }
                catch (Exception ge)
                {
                    MessageBox.Show(ge.Message, "Report");
                    tc.SelectedIndex = 1;
                    return ;
                }
            
            }
             
        }
          
        // Force current tab to edit syntax
        _CurrentTab = tag;
        if (OnDesignTabChanged != null)
            OnDesignTabChanged(this, e);
         
    }

    /**
    * Print the report.
    */
    public void print(PrintDocument pd) throws Exception {
        this.rdlPreview.print(pd);
    }

    public void saveAs(String filename, String ext) throws Exception {
        this.rdlPreview.saveAs(filename,ext);
    }

    public String getRdlText() throws Exception {
        if (StringSupport.equals(_CurrentTab, "design"))
            return dcDesign.getReportSource();
        else
            return this.tbEditor.Text; 
    }

    public void setRdlText(String text) throws Exception {
        if (StringSupport.equals(_CurrentTab, "design"))
        {
            try
            {
                dcDesign.setReportSource(text);
            }
            catch (Exception e)
            {
                MessageBox.Show(e.Message, "Report");
                this.tbEditor.Text = text;
                tcEHP.SelectedIndex = 1;
                // Force current tab to edit syntax
                _DesignChanged = "edit";
            }
        
        }
        else
            this.tbEditor.Text = text; 
    }

    /**
    * Number of pages in the report.
    */
    public int getPageCount() throws Exception {
        return this.rdlPreview.getPageCount();
    }

    /**
    * Current page in view on report
    */
    public int getPageCurrent() throws Exception {
        return this.rdlPreview.getPageCurrent();
    }

    /**
    * Page height of the report.
    */
    public float getPageHeight() throws Exception {
        return this.rdlPreview.getPageHeight();
    }

    /**
    * Page width of the report.
    */
    public float getPageWidth() throws Exception {
        return this.rdlPreview.getPageWidth();
    }

    public fyiReporting.RdlViewer.RdlViewer getViewer() throws Exception {
        return this.rdlPreview;
    }

    private void tbEditor_SelectionChanged(Object sender, EventArgs e) throws Exception {
        if (OnSelectionChanged != null)
        {
            OnSelectionChanged.invoke(this,e);
        }
         
    }

    private void pbLines_Draw(Graphics g) throws Exception {
        if (!pbLines.Visible)
            return ;
         
        int lineHeight = 0;
        try
        {
            // its possible that there are less than 2 lines; so trap the error
            lineHeight = tbEditor.GetPositionFromCharIndex(tbEditor.GetFirstCharIndexFromLine(2)).Y - tbEditor.GetPositionFromCharIndex(tbEditor.GetFirstCharIndexFromLine(1)).Y;
        }
        catch (Exception __dummyCatchVar0)
        {
            return ;
        }

        if (lineHeight <= 0)
            return ;
         
        // Get the first line index and location
        int first_index = new int();
        int first_line = new int();
        int first_line_y = new int();
        first_index = tbEditor.GetCharIndexFromPosition(new Point(0, (int)(g.VisibleClipBounds.Y + lineHeight / 3)));
        first_line = tbEditor.GetLineFromCharIndex(first_index);
        first_line_y = tbEditor.GetPositionFromCharIndex(first_index).Y;
        //  Draw on the PictureBox the visible line numbers of the RichTextBox
        g.Clear(Control.DefaultBackColor);
        int i = first_line;
        float y = first_line_y + 0 + lineHeight * (i - first_line - 1);
        while (y < g.VisibleClipBounds.Y + g.VisibleClipBounds.Height)
        {
            String l = i.ToString();
            g.DrawString(l, tbEditor.Font, Brushes.DarkBlue, pbLines.Width - (g.MeasureString(l, tbEditor.Font).Width + 4), y);
            i += 1;
            if (i > tbEditor.Lines.Length)
                break;
             
            y = first_line_y + 0 + lineHeight * (i - first_line - 1);
        }
    }

}


