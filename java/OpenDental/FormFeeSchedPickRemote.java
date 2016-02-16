//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;
import OpenDental.Lan;
import OpenDental.UI.ODGridColumn;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormFeeSchedPickRemote;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormFeeSchedPickRemote  extends Form 
{

    /**
    * Url must include trailing slash.
    */
    public String Url = "";
    private List<String> ListFeeSchedFilesOntario = new List<String>();
    /**
    * Returns the full URL of the chosen file.
    */
    public String getFileChosenUrl() throws Exception {
        return Url + getFileChosenName();
    }

    /**
    * Returns just the file name of the chosen file.
    */
    public String getFileChosenName() throws Exception {
        if (gridFeeSchedFiles.getSelectedIndex() == -1)
        {
            return "";
        }
         
        return gridFeeSchedFiles.getRows().get___idx(gridFeeSchedFiles.getSelectedIndex()).getCells()[0].Text;
    }

    /**
    * Returns true if the file chosen must be downloaded from the web service. Currently, Ontario fees must be downloaded from the web service.
    * Otherwise, if false is returned, the file can be downloaded by anyone directly from our website.
    */
    public boolean getIsFileChosenProtected() throws Exception {
        return ListFeeSchedFilesOntario.Contains(getFileChosenName());
    }

    public FormFeeSchedPickRemote() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formFeeSchedPickRemote_Load(Object sender, EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        fillListFeeSchedFilesAll();
        Cursor = Cursors.Default;
    }

    private void fillListFeeSchedFilesAll() throws Exception {
        fillListFeeSchedFilesOntario();
        List<String> ListFeeSchedFilesAll = new List<String>();
        ListFeeSchedFilesAll.AddRange(ListFeeSchedFilesOntario);
        HttpWebRequest request = (HttpWebRequest)WebRequest.Create(Url);
        HttpWebResponse response = (HttpWebResponse)request.GetResponse();
        try
        {
            {
                StreamReader reader = new StreamReader(response.GetResponseStream());
                try
                {
                    {
                        String html = reader.ReadToEnd();
                        int startIndex = html.IndexOf("<body>") + 6;
                        int bodyLength = html.Substring(startIndex).IndexOf("</body>");
                        String fileListStr = html.Substring(startIndex, bodyLength).Trim();
                        String[] files = fileListStr.Split(new String[]{ "\n", "\r\n" }, StringSplitOptions.RemoveEmptyEntries);
                        for (int i = 0;i < files.Length;i++)
                        {
                            if (files[i].ToLower().StartsWith("procedurecodes"))
                            {
                                continue;
                            }
                             
                            //Skip any files which contain procedure codes, because we only want to display fee files.
                            ListFeeSchedFilesAll.Add(files[i]);
                        }
                    }
                }
                finally
                {
                    if (reader != null)
                        Disposable.mkDisposable(reader).dispose();
                     
                }
            }
        }
        finally
        {
            if (response != null)
                Disposable.mkDisposable(response).dispose();
             
        }
        ListFeeSchedFilesAll.Sort();
        gridFeeSchedFiles.beginUpdate();
        gridFeeSchedFiles.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("",35);
        gridFeeSchedFiles.getColumns().add(col);
        gridFeeSchedFiles.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ListFeeSchedFilesAll.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(ListFeeSchedFilesAll[i]);
            gridFeeSchedFiles.getRows().add(row);
        }
        gridFeeSchedFiles.endUpdate();
    }

    private void fillListFeeSchedFilesOntario() throws Exception {
        ListFeeSchedFilesOntario = new List<String>();
        ListFeeSchedFilesOntario.Add("ON_ODA_2013_AN.txt");
        //Ontario Dental Association 2013 fee schedule for Anaesthesiologists.
        ListFeeSchedFilesOntario.Add("ON_ODA_2013_EN.txt");
        //Ontario Dental Association 2013 fee schedule for Endodontists.
        ListFeeSchedFilesOntario.Add("ON_ODA_2013_GP.txt");
        //Ontario Dental Association 2013 fee schedule for General Practitioners.
        ListFeeSchedFilesOntario.Add("ON_ODA_2013_OS.txt");
        //Ontario Dental Association 2013 fee schedule for Oral & Maxillofacial Surgeons.
        ListFeeSchedFilesOntario.Add("ON_ODA_2013_PA.txt");
        //Ontario Dental Association 2013 fee schedule for Paediatric Dentists.
        ListFeeSchedFilesOntario.Add("ON_ODA_2013_PE.txt");
        //Ontario Dental Association 2013 fee schedule for Periodontists.
        ListFeeSchedFilesOntario.Add("ON_ODA_2013_PR.txt");
        //Ontario Dental Association 2013 fee schedule for Prosthodontists.
        ListFeeSchedFilesOntario.Add("ON_ODA_2014_AN.txt");
        //Ontario Dental Association 2014 fee schedule for Anaesthesiologists.
        ListFeeSchedFilesOntario.Add("ON_ODA_2014_EN.txt");
        //Ontario Dental Association 2014 fee schedule for Endodontists.
        ListFeeSchedFilesOntario.Add("ON_ODA_2014_GP.txt");
        //Ontario Dental Association 2014 fee schedule for General Practitioners.
        ListFeeSchedFilesOntario.Add("ON_ODA_2014_OS.txt");
        //Ontario Dental Association 2014 fee schedule for Oral & Maxillofacial Surgeons.
        ListFeeSchedFilesOntario.Add("ON_ODA_2014_PA.txt");
        //Ontario Dental Association 2014 fee schedule for Paediatric Dentists.
        ListFeeSchedFilesOntario.Add("ON_ODA_2014_PE.txt");
        //Ontario Dental Association 2014 fee schedule for Periodontists.
        ListFeeSchedFilesOntario.Add("ON_ODA_2014_PR.txt");
    }

    //Ontario Dental Association 2014 fee schedule for Prosthodontists.
    private void gridFeeSchedFiles_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        butOK.Enabled = (gridFeeSchedFiles.getSelectedIndices().Length > 0);
    }

    private void gridFeeSchedFiles_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    /**
    * Required designer variable.
    */
    private System.ComponentModel.IContainer components = null;
    /**
    * Clean up any resources being used.
    * 
    *  @param disposing true if managed resources should be disposed; otherwise, false.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing && (components != null))
        {
            components.Dispose();
        }
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormFeeSchedPickRemote.class);
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.gridFeeSchedFiles = new OpenDental.UI.ODGrid();
        this.SuspendLayout();
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Enabled = false;
        this.butOK.Location = new System.Drawing.Point(277, 525);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(358, 525);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // gridFeeSchedFiles
        //
        this.gridFeeSchedFiles.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridFeeSchedFiles.setHScrollVisible(false);
        this.gridFeeSchedFiles.Location = new System.Drawing.Point(12, 12);
        this.gridFeeSchedFiles.Name = "gridFeeSchedFiles";
        this.gridFeeSchedFiles.setScrollValue(0);
        this.gridFeeSchedFiles.Size = new System.Drawing.Size(421, 503);
        this.gridFeeSchedFiles.TabIndex = 140;
        this.gridFeeSchedFiles.setTitle("Fee Sched Files");
        this.gridFeeSchedFiles.setTranslationName("TableApptProcs");
        this.gridFeeSchedFiles.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridFeeSchedFiles.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridFeeSchedFiles_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridFeeSchedFiles.CellClick = __MultiODGridClickEventHandler.combine(this.gridFeeSchedFiles.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridFeeSchedFiles_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // FormFeeSchedPickRemote
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(445, 561);
        this.Controls.Add(this.gridFeeSchedFiles);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormFeeSchedPickRemote";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Fee Sched Pick Remote";
        this.Load += new System.EventHandler(this.FormFeeSchedPickRemote_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.ODGrid gridFeeSchedFiles;
}


