//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:55 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormTranslation;
import OpenDental.FormTranslationEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Language;
import OpenDentBusiness.LanguageForeign;
import OpenDentBusiness.LanguageForeigns;
import OpenDentBusiness.Lans;

/**
* 
*/
public class FormTranslation  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private System.ComponentModel.Container components = null;
    private Language[] LanList = new Language[]();
    private String ClassType = new String();
    private OpenDental.UI.ODGrid gridLan;
    private OpenDental.UI.Button butDeleteUnused;
    private Label label1 = new Label();
    private OpenDental.UI.Button butDelete;
    private LanguageForeign[] ListForType = new LanguageForeign[]();
    /**
    * 
    */
    public FormTranslation(String classType) throws Exception {
        initializeComponent();
        gridLan.setTitle(classType + " Words");
        //tbLan.Fields[2]=CultureInfo.CurrentCulture.Parent.DisplayName;
        //tbLan.Fields[3]=CultureInfo.CurrentCulture.Parent.DisplayName + " Comments";
        //no need to translate much here
        Lan.C("All", new System.Windows.Forms.Control[]{ butClose });
        ClassType = classType;
    }

    /**
    * 
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

    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormTranslation.class);
        this.butClose = new OpenDental.UI.Button();
        this.gridLan = new OpenDental.UI.ODGrid();
        this.butDeleteUnused = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.butDelete = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butClose.Location = new System.Drawing.Point(847, 671);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 26);
        this.butClose.TabIndex = 3;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // gridLan
        //
        this.gridLan.setHScrollVisible(false);
        this.gridLan.Location = new System.Drawing.Point(18, 12);
        this.gridLan.Name = "gridLan";
        this.gridLan.setScrollValue(0);
        this.gridLan.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridLan.Size = new System.Drawing.Size(905, 643);
        this.gridLan.TabIndex = 7;
        this.gridLan.setTitle("Translations");
        this.gridLan.setTranslationName("TableLan");
        this.gridLan.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridLan.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridLan_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butDeleteUnused
        //
        this.butDeleteUnused.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDeleteUnused.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDeleteUnused.setAutosize(true);
        this.butDeleteUnused.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDeleteUnused.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDeleteUnused.setCornerRadius(4F);
        this.butDeleteUnused.Image = Resources.getdeleteX();
        this.butDeleteUnused.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDeleteUnused.Location = new System.Drawing.Point(18, 682);
        this.butDeleteUnused.Name = "butDeleteUnused";
        this.butDeleteUnused.Size = new System.Drawing.Size(111, 24);
        this.butDeleteUnused.TabIndex = 13;
        this.butDeleteUnused.Text = "Delete Unused";
        this.butDeleteUnused.Click += new System.EventHandler(this.butDeleteUnused_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(135, 672);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(447, 18);
        this.label1.TabIndex = 12;
        this.label1.Text = "It is very safe to delete entries.  Missing entries will be automatically added b" + "ack.";
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(18, 657);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(111, 24);
        this.butDelete.TabIndex = 11;
        this.butDelete.Text = "Delete Selected";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // FormTranslation
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butClose;
        this.ClientSize = new System.Drawing.Size(958, 708);
        this.Controls.Add(this.butDeleteUnused);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.gridLan);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormTranslation";
        this.ShowInTaskbar = false;
        this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Translation";
        this.Load += new System.EventHandler(this.FormLanguage_Load);
        this.ResumeLayout(false);
    }

    private void formLanguage_Load(Object sender, System.EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        LanList = Lans.getListForCat(ClassType);
        ListForType = LanguageForeigns.getListForType(ClassType);
        LanguageForeigns.Refresh(CultureInfo.CurrentCulture.Name, CultureInfo.CurrentCulture.TwoLetterISOLanguageName);
        gridLan.beginUpdate();
        gridLan.getColumns().Clear();
        ODGridColumn column = new ODGridColumn("English",220);
        gridLan.getColumns().add(column);
        column = new ODGridColumn(CultureInfo.CurrentCulture.DisplayName, 220);
        gridLan.getColumns().add(column);
        column = new ODGridColumn("Other " + CultureInfo.CurrentCulture.Parent.DisplayName + " Translation",220);
        gridLan.getColumns().add(column);
        column = new ODGridColumn(CultureInfo.CurrentCulture.DisplayName + " Comments", 220);
        gridLan.getColumns().add(column);
        //gridLan.Columns[1].Heading=;
        //gridLan.Columns[2].Heading="Other "+CultureInfo.CurrentCulture.Parent.DisplayName+" Translation";
        //gridLan.Columns[3].Heading=CultureInfo.CurrentCulture.DisplayName+" Comments";
        gridLan.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        LanguageForeign lanForeign;
        LanguageForeign lanForeignOther;
        for (int i = 0;i < LanList.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(LanList[i].English);
            lanForeign = LanguageForeigns.GetForCulture(ListForType, LanList[i].English, CultureInfo.CurrentCulture.Name);
            lanForeignOther = LanguageForeigns.GetOther(ListForType, LanList[i].English, CultureInfo.CurrentCulture.Name);
            if (lanForeign == null)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().add(lanForeign.Translation);
            } 
            if (lanForeignOther == null)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().add(lanForeignOther.Translation);
            } 
            if (lanForeign == null)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().add(lanForeign.Comments);
            } 
            gridLan.getRows().add(row);
        }
        gridLan.endUpdate();
    }

    private void gridLan_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        Language LanCur = LanList[e.getRow()];
        LanguageForeign lanForeign = LanguageForeigns.GetForCulture(ListForType, LanCur.English, CultureInfo.CurrentCulture.Name);
        LanguageForeign lanForeignOther = LanguageForeigns.GetOther(ListForType, LanCur.English, CultureInfo.CurrentCulture.Name);
        String otherTrans = "";
        if (lanForeignOther != null)
        {
            otherTrans = lanForeignOther.Translation;
        }
         
        FormTranslationEdit FormTE = new FormTranslationEdit(LanCur,lanForeign,otherTrans);
        FormTE.ShowDialog();
        fillGrid();
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (gridLan.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select a row first.");
            return ;
        }
         
        List<String> strList = new List<String>();
        for (int i = 0;i < gridLan.getSelectedIndices().Length;i++)
        {
            strList.Add(LanList[gridLan.getSelectedIndices()[i]].English);
        }
        Lans.DeleteItems(ClassType, strList);
        fillGrid();
    }

    private void butDeleteUnused_Click(Object sender, EventArgs e) throws Exception {
        List<String> strList = new List<String>();
        LanguageForeign lanForeign;
        LanguageForeign lanForeignOther;
        for (int i = 0;i < LanList.Length;i++)
        {
            lanForeign = LanguageForeigns.GetForCulture(ListForType, LanList[i].English, CultureInfo.CurrentCulture.Name);
            lanForeignOther = LanguageForeigns.GetOther(ListForType, LanList[i].English, CultureInfo.CurrentCulture.Name);
            if (lanForeign == null && lanForeignOther == null)
            {
                strList.Add(LanList[i].English);
            }
             
        }
        if (strList.Count == 0)
        {
            MsgBox.show(this,"All unused rows have already been deleted.");
            return ;
        }
         
        Lans.DeleteItems(ClassType, strList);
        fillGrid();
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


