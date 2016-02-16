//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:20 PM
//

package fyiReporting.RdlDesign;

import CS2JNet.JavaSupport.util.ListSupport;
import CS2JNet.System.StringSupport;
import fyiReporting.RDL.StyleInfo;
import fyiReporting.RdlDesign.DesignCtl;
import fyiReporting.RdlDesign.DesignCtl.__MultiHeightEventHandler;
import fyiReporting.RdlDesign.DesignCtl.__MultiOpenSubreportEventHandler;
import fyiReporting.RdlDesign.DesignXmlDraw;
import fyiReporting.RdlDesign.HeightEventArgs;
import fyiReporting.RdlDesign.MDIChild;
import fyiReporting.RdlDesign.RdlEditPreview.__MultiRdlChangeHandler;
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
* RdlReader is a application for displaying reports based on RDL.
*/
public class MDIChild  extends Form 
{
    public static class __MultiRdlChangeHandler   implements RdlChangeHandler
    {
        public void invoke(Object sender, EventArgs e) throws Exception {
            IList<RdlChangeHandler> copy = new IList<RdlChangeHandler>(), members = this.getInvocationList();
            synchronized (members)
            {
                copy = new LinkedList<RdlChangeHandler>(members);
            }
            for (Object __dummyForeachVar0 : copy)
            {
                RdlChangeHandler d = (RdlChangeHandler)__dummyForeachVar0;
                d.invoke(sender, e);
            }
        }

        private System.Collections.Generic.IList<RdlChangeHandler> _invocationList = new ArrayList<RdlChangeHandler>();
        public static RdlChangeHandler combine(RdlChangeHandler a, RdlChangeHandler b) throws Exception {
            if (a == null)
                return b;
             
            if (b == null)
                return a;
             
            __MultiRdlChangeHandler ret = new __MultiRdlChangeHandler();
            ret._invocationList = a.getInvocationList();
            ret._invocationList.addAll(b.getInvocationList());
            return ret;
        }

        public static RdlChangeHandler remove(RdlChangeHandler a, RdlChangeHandler b) throws Exception {
            if (a == null || b == null)
                return a;
             
            System.Collections.Generic.IList<RdlChangeHandler> aInvList = a.getInvocationList();
            // TabPage for this MDI Child
            System.Collections.Generic.IList<RdlChangeHandler> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
            //
            if (aInvList == newInvList)
            {
                return a;
            }
            else
            {
                // rdlDesigner
                //
                __MultiRdlChangeHandler ret = new __MultiRdlChangeHandler();
                ret._invocationList = newInvList;
                return ret;
            } 
        }

        public System.Collections.Generic.IList<RdlChangeHandler> getInvocationList() throws Exception {
            return _invocationList;
        }
    
    }

    public static interface RdlChangeHandler   
    {
        void invoke(Object sender, EventArgs e) throws Exception ;

        System.Collections.Generic.IList<RdlChangeHandler> getInvocationList() throws Exception ;
    
    }

    public RdlChangeHandler OnSelectionChanged;
    public RdlChangeHandler OnSelectionMoved;
    public RdlChangeHandler OnReportItemInserted;
    public RdlChangeHandler OnDesignTabChanged;
    public fyiReporting.RdlDesign.DesignCtl.OpenSubreportEventHandler OnOpenSubreport;
    public fyiReporting.RdlDesign.DesignCtl.HeightEventHandler OnHeightChanged;
    private fyiReporting.RdlDesign.RdlEditPreview rdlDesigner;
    String _SourceFile = new String();
    TabPage _Tab = new TabPage();
    public MDIChild(int width, int height) throws Exception {
        this.rdlDesigner = new fyiReporting.RdlDesign.RdlEditPreview();
        this.SuspendLayout();
        this.rdlDesigner.Dock = System.Windows.Forms.DockStyle.Fill;
        this.rdlDesigner.Location = new System.Drawing.Point(0, 0);
        this.rdlDesigner.Name = "rdlDesigner";
        this.rdlDesigner.Size = new System.Drawing.Size(width, height);
        this.rdlDesigner.TabIndex = 0;
        rdlDesigner.OnRdlChanged = __MultiRdlChangeHandler.combine(rdlDesigner.OnRdlChanged,new fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler() 
          { 
            // register event for RDL changed.
            public System.Void invoke(System.Object sender, EventArgs e) throws Exception {
                rdlDesigner_RdlChanged(sender, e);
            }

            public List<fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler> getInvocationList() throws Exception {
                List<fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler> ret = new ArrayList<fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        rdlDesigner.OnHeightChanged = __MultiHeightEventHandler.combine(rdlDesigner.OnHeightChanged,new fyiReporting.RdlDesign.DesignCtl.HeightEventHandler() 
          { 
            public System.Void invoke(System.Object source, HeightEventArgs e) throws Exception {
                rdlDesigner_HeightChanged(source, e);
            }

            public List<fyiReporting.RdlDesign.DesignCtl.HeightEventHandler> getInvocationList() throws Exception {
                List<fyiReporting.RdlDesign.DesignCtl.HeightEventHandler> ret = new ArrayList<fyiReporting.RdlDesign.DesignCtl.HeightEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        rdlDesigner.OnSelectionChanged = __MultiRdlChangeHandler.combine(rdlDesigner.OnSelectionChanged,new fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler() 
          { 
            public System.Void invoke(System.Object sender, EventArgs e) throws Exception {
                rdlDesigner_SelectionChanged(sender, e);
            }

            public List<fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler> getInvocationList() throws Exception {
                List<fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler> ret = new ArrayList<fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        rdlDesigner.OnSelectionMoved = __MultiRdlChangeHandler.combine(rdlDesigner.OnSelectionMoved,new fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler() 
          { 
            public System.Void invoke(System.Object sender, EventArgs e) throws Exception {
                rdlDesigner_SelectionMoved(sender, e);
            }

            public List<fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler> getInvocationList() throws Exception {
                List<fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler> ret = new ArrayList<fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        rdlDesigner.OnReportItemInserted = __MultiRdlChangeHandler.combine(rdlDesigner.OnReportItemInserted,new fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler() 
          { 
            public System.Void invoke(System.Object sender, EventArgs e) throws Exception {
                rdlDesigner_ReportItemInserted(sender, e);
            }

            public List<fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler> getInvocationList() throws Exception {
                List<fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler> ret = new ArrayList<fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        rdlDesigner.OnDesignTabChanged = __MultiRdlChangeHandler.combine(rdlDesigner.OnDesignTabChanged,new fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler() 
          { 
            public System.Void invoke(System.Object sender, EventArgs e) throws Exception {
                rdlDesigner_DesignTabChanged(sender, e);
            }

            public List<fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler> getInvocationList() throws Exception {
                List<fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler> ret = new ArrayList<fyiReporting.RdlDesign.RdlEditPreview.RdlChangeHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        rdlDesigner.OnOpenSubreport = __MultiOpenSubreportEventHandler.combine(rdlDesigner.OnOpenSubreport,new fyiReporting.RdlDesign.DesignCtl.OpenSubreportEventHandler() 
          { 
            public System.Void invoke(System.Object source, SubReportEventArgs e) throws Exception {
                rdlDesigner_OpenSubreport(source, e);
            }

            public List<fyiReporting.RdlDesign.DesignCtl.OpenSubreportEventHandler> getInvocationList() throws Exception {
                List<fyiReporting.RdlDesign.DesignCtl.OpenSubreportEventHandler> ret = new ArrayList<fyiReporting.RdlDesign.DesignCtl.OpenSubreportEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // MDIChild
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(width, height);
        this.Controls.Add(this.rdlDesigner);
        this.Name = "";
        this.Text = "";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.MDIChild_Closing);
        this.ResumeLayout(false);
    }

    public TabPage getTab() throws Exception {
        return _Tab;
    }

    public void setTab(TabPage value) throws Exception {
        _Tab = value;
    }

    public fyiReporting.RdlDesign.RdlEditPreview getEditor() throws Exception {
        return rdlDesigner.getCanEdit() ? rdlDesigner : null;
    }

    // only return when it can edit
    public fyiReporting.RdlDesign.RdlEditPreview getRdlEditor() throws Exception {
        return rdlDesigner;
    }

    // always return
    public void showEditLines(boolean bShow) throws Exception {
        rdlDesigner.showEditLines(bShow);
    }

    public boolean getShowReportItemOutline() throws Exception {
        return rdlDesigner.getShowReportItemOutline();
    }

    public void setShowReportItemOutline(boolean value) throws Exception {
        rdlDesigner.setShowReportItemOutline(value);
    }

    public String getCurrentInsert() throws Exception {
        return rdlDesigner.getCurrentInsert();
    }

    public void setCurrentInsert(String value) throws Exception {
        rdlDesigner.setCurrentInsert(value);
    }

    public int getCurrentLine() throws Exception {
        return rdlDesigner.getCurrentLine();
    }

    public int getCurrentCh() throws Exception {
        return rdlDesigner.getCurrentCh();
    }

    public String getDesignTab() throws Exception {
        return rdlDesigner.getDesignTab();
    }

    public void setDesignTab(String value) throws Exception {
        rdlDesigner.setDesignTab(value);
    }

    public DesignXmlDraw getDrawCtl() throws Exception {
        return rdlDesigner.getDrawCtl();
    }

    public XmlDocument getReportDocument() throws Exception {
        return rdlDesigner.getReportDocument();
    }

    public void setFocus() throws Exception {
        rdlDesigner.setFocus();
    }

    public StyleInfo getSelectedStyle() throws Exception {
        return rdlDesigner.getSelectedStyle();
    }

    public String getSelectionName() throws Exception {
        return rdlDesigner.getSelectionName();
    }

    public PointF getSelectionPosition() throws Exception {
        return rdlDesigner.getSelectionPosition();
    }

    public SizeF getSelectionSize() throws Exception {
        return rdlDesigner.getSelectionSize();
    }

    public void applyStyleToSelected(String name, String v) throws Exception {
        rdlDesigner.applyStyleToSelected(name,v);
    }

    public boolean fileSave() throws Exception {
        String file = getSourceFile();
        if (StringSupport.equals(file, "") || file == null)
        {
            return fileSaveAs();
        }
         
        // if no file name then do SaveAs
        String rdl = getRdlText();
        return fileSave(file,rdl);
    }

    private boolean fileSave(String file, String rdl) throws Exception {
        StreamWriter writer = null;
        boolean bOK = true;
        try
        {
            writer = new StreamWriter(file);
            writer.Write(rdl);
        }
        catch (Exception ae)
        {
            //				editRDL.ClearUndo();
            //				editRDL.Modified = false;
            //				SetTitle();
            //				statusBar.Text = "Saved " + curFileName;
            bOK = false;
            MessageBox.Show(ae.Message + "\r\n" + ae.StackTrace);
        }
        finally
        {
            //				statusBar.Text = "Save of file '" + curFileName + "' failed";
            writer.Close();
        }
        if (bOK)
            this.setModified(false);
         
        return bOK;
    }

    public boolean export(String type) throws Exception {
        SaveFileDialog sfd = new SaveFileDialog();
        sfd.Title = "Export to " + type.ToUpper();
        System.String.APPLY __dummyScrutVar0 = type.ToLower();
        if (__dummyScrutVar0.equals("xml"))
        {
            sfd.Filter = "XML file (*.xml)|*.xml|All files (*.*)|*.*";
        }
        else if (__dummyScrutVar0.equals("pdf"))
        {
            sfd.Filter = "PDF file (*.pdf)|*.pdf|All files (*.*)|*.*";
        }
        else if (__dummyScrutVar0.equals("html") || __dummyScrutVar0.equals("htm"))
        {
            sfd.Filter = "Web Page (*.html, *.htm)|*.html;*.htm|All files (*.*)|*.*";
        }
        else if (__dummyScrutVar0.equals("mhtml") || __dummyScrutVar0.equals("mht"))
        {
            sfd.Filter = "MHT (*.mht)|*.mhtml;*.mht|All files (*.*)|*.*";
        }
        else
        {
            throw new Exception("Only HTML, MHT, XML and PDF are allowed as Export types.");
        }    
        sfd.FilterIndex = 1;
        if (getSourceFile() != null)
            sfd.FileName = Path.GetFileNameWithoutExtension(getSourceFile()) + "." + type;
        else
            sfd.FileName = "*." + type; 
        if (sfd.ShowDialog(this) != DialogResult.OK)
            return false;
         
        // save the report in the requested rendered format
        boolean rc = true;
        try
        {
            SaveAs(sfd.FileName, type);
        }
        catch (Exception ex)
        {
            MessageBox.Show(this, ex.Message, "Export Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            rc = false;
        }

        return rc;
    }

    public boolean fileSaveAs() throws Exception {
        SaveFileDialog sfd = new SaveFileDialog();
        sfd.Filter = "RDL files (*.rdl)|*.rdl|All files (*.*)|*.*";
        sfd.FilterIndex = 1;
        String file = getSourceFile();
        sfd.FileName = file == null ? "*.rdl" : file;
        if (sfd.ShowDialog(this) != DialogResult.OK)
            return false;
         
        // User wants to save!
        String rdl = getRdlText();
        if (FileSave(sfd.FileName, rdl))
        {
            // Save was successful
            Text = sfd.FileName;
            getTab().Text = Path.GetFileName(sfd.FileName);
            _SourceFile = sfd.FileName;
            getTab().ToolTipText = sfd.FileName;
            return true;
        }
         
        return false;
    }

    public String getRdlText() throws Exception {
        return this.rdlDesigner.getRdlText();
    }

    public boolean getModified() throws Exception {
        return rdlDesigner.getModified();
    }

    public void setModified(boolean value) throws Exception {
        rdlDesigner.setModified(value);
        setTitle();
    }

    /**
    * The RDL file that should be displayed.
    */
    public String getSourceFile() throws Exception {
        return _SourceFile;
    }

    public void setSourceFile(String value) throws Exception {
        _SourceFile = value;
        String rdl = getRdlSource();
        this.rdlDesigner.setRdlText(rdl == null ? "" : rdl);
    }

    public String getSourceRdl() throws Exception {
        return this.rdlDesigner.getRdlText();
    }

    public void setSourceRdl(String value) throws Exception {
        this.rdlDesigner.setRdlText(value);
    }

    private String getRdlSource() throws Exception {
        StreamReader fs = null;
        String prog = null;
        try
        {
            fs = new StreamReader(_SourceFile);
            prog = fs.ReadToEnd();
        }
        finally
        {
            if (fs != null)
                fs.Close();
             
        }
        return prog;
    }

    /**
    * Number of pages in the report.
    */
    public int getPageCount() throws Exception {
        return this.rdlDesigner.getPageCount();
    }

    /**
    * Current page in view on report
    */
    public int getPageCurrent() throws Exception {
        return this.rdlDesigner.getPageCurrent();
    }

    /**
    * Page height of the report.
    */
    public float getPageHeight() throws Exception {
        return this.rdlDesigner.getPageHeight();
    }

    /**
    * Page width of the report.
    */
    public float getPageWidth() throws Exception {
        return this.rdlDesigner.getPageWidth();
    }

    /**
    * Zoom
    */
    public float getZoom() throws Exception {
        return this.rdlDesigner.getZoom();
    }

    public void setZoom(float value) throws Exception {
        this.rdlDesigner.setZoom(value);
    }

    /**
    * ZoomMode
    */
    public fyiReporting.RdlViewer.ZoomEnum getZoomMode() throws Exception {
        return this.rdlDesigner.getZoomMode();
    }

    public void setZoomMode(fyiReporting.RdlViewer.ZoomEnum value) throws Exception {
        this.rdlDesigner.setZoomMode(value);
    }

    /**
    * Print the report.
    */
    public void print(PrintDocument pd) throws Exception {
        this.rdlDesigner.print(pd);
    }

    public void saveAs(String filename, String ext) throws Exception {
        rdlDesigner.saveAs(filename,ext);
    }

    private void mDIChild_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (!okToClose())
        {
            e.Cancel = true;
            return ;
        }
         
        if (getTab() == null)
            return ;
         
        Control ctl = getTab().Parent;
        ctl.Controls.Remove(getTab());
        getTab().Tag = null;
        // this is the Tab reference to this
        setTab(null);
    }

    public boolean okToClose() throws Exception {
        if (!this.getModified())
            return true;
         
        DialogResult r = MessageBox.Show(this, String.Format("Do you want to save changes you made to '{0}'?", _SourceFile == null ? "Untitled" : Path.GetFileName(_SourceFile)), "fyiReporting Designer", MessageBoxButtons.YesNoCancel, MessageBoxIcon.Exclamation, MessageBoxDefaultButton.Button3);
        boolean bOK = true;
        if (r == DialogResult.Cancel)
            bOK = false;
        else if (r == DialogResult.Yes)
        {
            if (!fileSave())
                bOK = false;
             
        }
          
        return bOK;
    }

    private void rdlDesigner_RdlChanged(Object sender, System.EventArgs e) throws Exception {
        setTitle();
    }

    private void rdlDesigner_HeightChanged(Object sender, HeightEventArgs e) throws Exception {
        if (OnHeightChanged != null)
            OnHeightChanged.invoke(this,e);
         
    }

    private void rdlDesigner_SelectionChanged(Object sender, System.EventArgs e) throws Exception {
        if (OnSelectionChanged != null)
            OnSelectionChanged(this, e);
         
    }

    private void rdlDesigner_DesignTabChanged(Object sender, System.EventArgs e) throws Exception {
        if (OnDesignTabChanged != null)
            OnDesignTabChanged(this, e);
         
    }

    private void rdlDesigner_ReportItemInserted(Object sender, System.EventArgs e) throws Exception {
        if (OnReportItemInserted != null)
            OnReportItemInserted(this, e);
         
    }

    private void rdlDesigner_SelectionMoved(Object sender, System.EventArgs e) throws Exception {
        if (OnSelectionMoved != null)
            OnSelectionMoved(this, e);
         
    }

    private void rdlDesigner_OpenSubreport(Object sender, SubReportEventArgs e) throws Exception {
        if (OnOpenSubreport != null)
        {
            OnOpenSubreport.invoke(this,e);
        }
         
    }

    private void initializeComponent() throws Exception {
        System.Resources.ResourceManager resources = new System.Resources.ResourceManager(MDIChild.class);
        //
        // MDIChild
        //
        this.AutoScaleMode = AutoScaleMode.None;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(292, 266);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "MDIChild";
    }

    private void setTitle() throws Exception {
        String title = this.Text;
        if (title.Length < 1)
            return ;
         
        char m = title[title.Length - 1];
        if (this.getModified())
        {
            if (m != '*')
                title = title + "*";
             
        }
        else if (m == '*')
            title = title.Substring(0, title.Length - 1);
          
        if (!StringSupport.equals(title, this.Text))
            this.Text = title;
         
        return ;
    }

    public fyiReporting.RdlViewer.RdlViewer getViewer() throws Exception {
        return rdlDesigner.getViewer();
    }

}


