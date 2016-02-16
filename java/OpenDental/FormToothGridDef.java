//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:55 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormToothGridDef;
import OpenDental.Lan;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDentBusiness.ToothGridDef;

/**
* 
*/
public class FormToothGridDef  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.ODGrid gridMain;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.Button butDown;
    private OpenDental.UI.Button butUp;
    private ListBox listAvailable = new ListBox();
    private Label labelAvailable = new Label();
    private OpenDental.UI.Button butRight;
    private OpenDental.UI.Button butLeft;
    //private bool changed;
    private OpenDental.UI.Button butOK;
    private Label labelCategory = new Label();
    private Label labelCustomField = new Label();
    private TextBox textCustomField = new TextBox();
    //public DisplayFieldCategory category;
    /**
    * When this form opens, this is the list of display fields that the user has already explicitly set to be showing.  If the user did not set any to be showing yet, then this will start out as a blank list. As this window is used, items are added to this list but not saved until window closes with OK.
    */
    private List<ToothGridDef> ListShowing = new List<ToothGridDef>();
    /**
    * This is the list of all "hardcoded" display fields. Users may add additional fields that will be used to populate ListShowing, but will not be added to AvailList.
    */
    private List<String> AvailList = new List<String>();
    /**
    * 
    */
    public FormToothGridDef() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormToothGridDef.class);
        this.listAvailable = new System.Windows.Forms.ListBox();
        this.labelAvailable = new System.Windows.Forms.Label();
        this.labelCategory = new System.Windows.Forms.Label();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.labelCustomField = new System.Windows.Forms.Label();
        this.textCustomField = new System.Windows.Forms.TextBox();
        this.butOK = new OpenDental.UI.Button();
        this.butRight = new OpenDental.UI.Button();
        this.butLeft = new OpenDental.UI.Button();
        this.butDown = new OpenDental.UI.Button();
        this.butUp = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // listAvailable
        //
        this.listAvailable.FormattingEnabled = true;
        this.listAvailable.IntegralHeight = false;
        this.listAvailable.Location = new System.Drawing.Point(373, 89);
        this.listAvailable.Name = "listAvailable";
        this.listAvailable.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listAvailable.Size = new System.Drawing.Size(158, 187);
        this.listAvailable.TabIndex = 15;
        this.listAvailable.Click += new System.EventHandler(this.listAvailable_Click);
        //
        // labelAvailable
        //
        this.labelAvailable.Location = new System.Drawing.Point(370, 69);
        this.labelAvailable.Name = "labelAvailable";
        this.labelAvailable.Size = new System.Drawing.Size(213, 17);
        this.labelAvailable.TabIndex = 16;
        this.labelAvailable.Text = "Available Fields";
        this.labelAvailable.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // labelCategory
        //
        this.labelCategory.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelCategory.Location = new System.Drawing.Point(12, 9);
        this.labelCategory.Name = "labelCategory";
        this.labelCategory.Size = new System.Drawing.Size(213, 25);
        this.labelCategory.TabIndex = 57;
        this.labelCategory.Text = "Category Description";
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 76);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMain.Size = new System.Drawing.Size(292, 425);
        this.gridMain.TabIndex = 3;
        this.gridMain.setTitle("Fields Showing");
        this.gridMain.setTranslationName("FormDisplayFields");
        this.gridMain.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // labelCustomField
        //
        this.labelCustomField.Location = new System.Drawing.Point(370, 279);
        this.labelCustomField.Name = "labelCustomField";
        this.labelCustomField.Size = new System.Drawing.Size(213, 17);
        this.labelCustomField.TabIndex = 58;
        this.labelCustomField.Text = "New Field";
        this.labelCustomField.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textCustomField
        //
        this.textCustomField.Location = new System.Drawing.Point(372, 299);
        this.textCustomField.Name = "textCustomField";
        this.textCustomField.Size = new System.Drawing.Size(158, 20);
        this.textCustomField.TabIndex = 59;
        this.textCustomField.Click += new System.EventHandler(this.textCustomField_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(566, 474);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 56;
        this.butOK.Text = "OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butRight
        //
        this.butRight.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRight.setAutosize(true);
        this.butRight.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRight.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRight.setCornerRadius(4F);
        this.butRight.Image = Resources.getRight();
        this.butRight.Location = new System.Drawing.Point(320, 292);
        this.butRight.Name = "butRight";
        this.butRight.Size = new System.Drawing.Size(35, 24);
        this.butRight.TabIndex = 55;
        this.butRight.Click += new System.EventHandler(this.butRight_Click);
        //
        // butLeft
        //
        this.butLeft.setAdjustImageLocation(new System.Drawing.Point(-1, 0));
        this.butLeft.setAutosize(true);
        this.butLeft.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butLeft.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butLeft.setCornerRadius(4F);
        this.butLeft.Image = Resources.getLeft();
        this.butLeft.Location = new System.Drawing.Point(320, 252);
        this.butLeft.Name = "butLeft";
        this.butLeft.Size = new System.Drawing.Size(35, 24);
        this.butLeft.TabIndex = 54;
        this.butLeft.Click += new System.EventHandler(this.butLeft_Click);
        //
        // butDown
        //
        this.butDown.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDown.setAutosize(true);
        this.butDown.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDown.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDown.setCornerRadius(4F);
        this.butDown.Image = Resources.getdown();
        this.butDown.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDown.Location = new System.Drawing.Point(109, 507);
        this.butDown.Name = "butDown";
        this.butDown.Size = new System.Drawing.Size(82, 24);
        this.butDown.TabIndex = 14;
        this.butDown.Text = "&Down";
        this.butDown.Click += new System.EventHandler(this.butDown_Click);
        //
        // butUp
        //
        this.butUp.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butUp.setAutosize(true);
        this.butUp.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butUp.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butUp.setCornerRadius(4F);
        this.butUp.Image = Resources.getup();
        this.butUp.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butUp.Location = new System.Drawing.Point(12, 507);
        this.butUp.Name = "butUp";
        this.butUp.Size = new System.Drawing.Size(82, 24);
        this.butUp.TabIndex = 13;
        this.butUp.Text = "&Up";
        this.butUp.Click += new System.EventHandler(this.butUp_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(566, 504);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormToothGridDef
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(664, 556);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.textCustomField);
        this.Controls.Add(this.labelCustomField);
        this.Controls.Add(this.labelCategory);
        this.Controls.Add(this.butRight);
        this.Controls.Add(this.butLeft);
        this.Controls.Add(this.labelAvailable);
        this.Controls.Add(this.listAvailable);
        this.Controls.Add(this.butDown);
        this.Controls.Add(this.butUp);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormToothGridDef";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Setup Toothgrid Def Fields";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormDisplayFields_FormClosing);
        this.Load += new System.EventHandler(this.FormDisplayFields_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formDisplayFields_Load(Object sender, EventArgs e) throws Exception {
        fillAvailList();
        //fills local variable
        fillListAvailable();
        //fills list in UI
        //category = DisplayFieldCategory.ToothGrid;
        fillGrids();
    }

    //ListShowing = ToothGridDefs
    //labelCategory.Text=category.ToString();
    //textCustomField.Visible=false;
    //labelCustomField.Visible=false;
    //listAvailable.Height=412;
    //DisplayFields.RefreshCache();
    //ListShowing=DisplayFields.GetForCategory(category);
    //if(category==DisplayFieldCategory.OrthoChart) {
    //  textCustomField.Visible=true;
    //  labelCustomField.Visible=true;
    //  listAvailable.Height=227;//227px for short, 412px for tall
    //  labelAvailable.Text=Lan.g(this,"Previously Used Fields");
    //}
    //FillGrids();
    private void fillAvailList() throws Exception {
        AvailList = new List<String>();
        AvailList.Add("Tooth");
        AvailList.Add("IsPrimary");
        AvailList.Add("IsMissing");
    }

    private void fillListAvailable() throws Exception {
        for (Object __dummyForeachVar0 : AvailList)
        {
            String field = (String)__dummyForeachVar0;
            listAvailable.Items.Add(field);
        }
    }

    private void fillGrids() throws Exception {
    }

    //AvailList=DisplayFields.GetAllAvailableList(category);//This one needs to be called repeatedly.
    //gridMain.BeginUpdate();
    //gridMain.Columns.Clear();
    //ODGridColumn col=new ODGridColumn(Lan.g("FormDisplayFields","FieldName"),110);
    //gridMain.Columns.Add(col);
    //col=new ODGridColumn(Lan.g("FormDisplayFields","New Descript"),110);
    //gridMain.Columns.Add(col);
    //col=new ODGridColumn(Lan.g("FormDisplayFields","Width"),60);
    //gridMain.Columns.Add(col);
    //gridMain.Rows.Clear();
    //ODGridRow row;
    //for(int i=0;i<ListShowing.Count;i++){
    //  row=new ODGridRow();
    //  row.Cells.Add(ListShowing[i].InternalName);
    //  row.Cells.Add(ListShowing[i].Description);
    //  row.Cells.Add(ListShowing[i].ColumnWidth.ToString());
    //  gridMain.Rows.Add(row);
    //}
    //gridMain.EndUpdate();
    /**
    * /Remove things from AvailList that are in the ListShowing.
    */
    //for(int i=0;i<ListShowing.Count;i++){
    //  for(int j=0;j<AvailList.Count;j++) {
    //    //Only removing one item from AvailList per iteration of i, so RemoveAt() is safe without going backwards.
    //    if(category==DisplayFieldCategory.OrthoChart) {
    //      //OrthoChart category does not use InternalNames.
    //      if(ListShowing[i].Description==AvailList[j].Description) {
    //        AvailList.RemoveAt(j);
    //        break;
    //      }
    //    }
    //    else {
    //      if(ListShowing[i].InternalName==AvailList[j].InternalName) {
    //        AvailList.RemoveAt(j);
    //        break;
    //      }
    //    }
    //  }
    //}
    //listAvailable.Items.Clear();
    //if(category==DisplayFieldCategory.OrthoChart) {
    //  for(int i=0;i<AvailList.Count;i++) {
    //    listAvailable.Items.Add(AvailList[i].Description);
    //  }
    //}
    //else {
    //  for(int i=0;i<AvailList.Count;i++) {
    //    listAvailable.Items.Add(AvailList[i]);
    //  }
    //}
    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
    }

    //FormDisplayFieldEdit formD=new FormDisplayFieldEdit();
    //formD.FieldCur=ListShowing[e.Row];
    //DisplayField tempField=ListShowing[e.Row].Copy();
    //formD.ShowDialog();
    //if(formD.DialogResult!=DialogResult.OK) {
    //  ListShowing[e.Row]=tempField.Copy();
    //  return;
    //}
    //if(category==DisplayFieldCategory.OrthoChart) {
    //  if(ListShowing[e.Row].Description=="") {
    //    ListShowing[e.Row]=tempField.Copy();
    //    MsgBox.Show(this,"Description cannot be blank.");
    //    return;
    //  }
    //  for(int i=0;i<ListShowing.Count;i++) {//Check against ListShowing only
    //    if(i==e.Row) {
    //      continue;
    //    }
    //    if(ListShowing[e.Row].Description==ListShowing[i].Description) {
    //      ListShowing[e.Row]=tempField;
    //      MsgBox.Show(this,"That field name already exists.");
    //      return;
    //    }
    //  }
    //  for(int i=0;i<AvailList.Count;i++) {//check against AvailList only
    //    if(ListShowing[e.Row].Description==AvailList[i].Description) {
    //      ListShowing[e.Row]=tempField;
    //      MsgBox.Show(this,"That field name already exists.");
    //      return;
    //    }
    //  }
    //}
    //FillGrids();
    //changed=true;
    private void butDefault_Click(Object sender, EventArgs e) throws Exception {
    }

    //ListShowing=DisplayFields.GetDefaultList(category);//empty for ortho
    //FillGrids();
    //changed=true;
    private void butLeft_Click(Object sender, EventArgs e) throws Exception {
    }

    //if(listAvailable.SelectedItems.Count==0 && textCustomField.Text=="") {
    //  MsgBox.Show(this,"Please select an item in the list on the right or create a new field first.");
    //  return;
    //}
    //if(textCustomField.Text!="") {//Add new ortho chart field
    //  foreach(ToothGridDef defShowing in ListShowing) {
    //    if(textCustomField.Text==defShowing.NameInternal || textCustomField.Text==defShowing.NameShowing) {
    //      MsgBox.Show(this,"That field is already displaying.");
    //      return;
    //    }
    //  }
    //  for(int i=0;i<AvailList.Count;i++) {
    //    if(textCustomField.Text==AvailList[i].Description) {
    //      ListShowing.Add(AvailList[i]);
    //      textCustomField.Text="";
    //      changed=true;
    //      FillGrids();
    //      return;
    //    }
    //  }
    //  DisplayField df=new DisplayField("",100,DisplayFieldCategory.OrthoChart);
    //  df.Description=textCustomField.Text;
    //  ListShowing.Add(df);
    //  textCustomField.Text="";
    //}
    //else {//add existing ortho chart field(s)
    //  DisplayField field;
    //  for(int i=0;i<listAvailable.SelectedItems.Count;i++) {
    //    field=AvailList[listAvailable.SelectedIndices[i]];
    //    field.ColumnWidth=100;
    //    ListShowing.Add(field);
    //  }
    //if(category==DisplayFieldCategory.OrthoChart) {//Ortho Chart
    //  if(listAvailable.SelectedItems.Count==0 && textCustomField.Text=="") {
    //    MsgBox.Show(this,"Please select an item in the list on the right or create a new field first.");
    //    return;
    //  }
    //  if(textCustomField.Text!="") {//Add new ortho chart field
    //    for(int i=0;i<ListShowing.Count;i++) {
    //      if(textCustomField.Text==ListShowing[i].Description) {
    //        MsgBox.Show(this,"That field is already displaying.");
    //        return;
    //      }
    //    }
    //    for(int i=0;i<AvailList.Count;i++) {
    //      if(textCustomField.Text==AvailList[i].Description) {
    //        ListShowing.Add(AvailList[i]);
    //        textCustomField.Text="";
    //        changed=true;
    //        FillGrids();
    //        return;
    //      }
    //    }
    //    DisplayField df=new DisplayField("",100,DisplayFieldCategory.OrthoChart);
    //    df.Description=textCustomField.Text;
    //    ListShowing.Add(df);
    //    textCustomField.Text="";
    //  }
    //  else {//add existing ortho chart field(s)
    //    DisplayField field;
    //    for(int i=0;i<listAvailable.SelectedItems.Count;i++) {
    //      field=AvailList[listAvailable.SelectedIndices[i]];
    //      field.ColumnWidth=100;
    //      ListShowing.Add(field);
    //    }
    //  }
    //}
    //else {//All other display field types
    //  if(listAvailable.SelectedItems.Count==0) {
    //    MsgBox.Show(this,"Please select an item in the list on the right first.");
    //    return;
    //  }
    //  DisplayField field;
    //  for(int i=0;i<listAvailable.SelectedItems.Count;i++) {
    //    field=(DisplayField)listAvailable.SelectedItems[i];
    //    ListShowing.Add(field);
    //  }
    //}
    //changed=true;
    //FillGrids();
    //}
    private void butRight_Click(Object sender, EventArgs e) throws Exception {
    }

    //if(gridMain.SelectedIndices.Length==0) {
    //  MsgBox.Show(this,"Please select an item in the grid on the left first.");
    //  return;
    //}
    //for(int i=gridMain.SelectedIndices.Length-1;i>=0;i--){//go backwards
    //  ListShowing.RemoveAt(gridMain.SelectedIndices[i]);
    //}
    //FillGrids();
    //changed=true;
    private void butUp_Click(Object sender, EventArgs e) throws Exception {
    }

    //if(gridMain.SelectedIndices.Length==0) {
    //  MsgBox.Show(this,"Please select an item in the grid first.");
    //  return;
    //}
    //int[] selected=new int[gridMain.SelectedIndices.Length];
    //for(int i=0;i<gridMain.SelectedIndices.Length;i++){
    //  selected[i]=gridMain.SelectedIndices[i];
    //}
    //if(selected[0]==0){
    //  return;
    //}
    //for(int i=0;i<selected.Length;i++){
    //  ListShowing.Reverse(selected[i]-1,2);
    //}
    //FillGrids();
    //for(int i=0;i<selected.Length;i++){
    //  gridMain.SetSelected(selected[i]-1,true);
    //}
    //changed=true;
    private void butDown_Click(Object sender, EventArgs e) throws Exception {
    }

    //if(gridMain.SelectedIndices.Length==0) {
    //  MsgBox.Show(this,"Please select an item in the grid first.");
    //  return;
    //}
    //int[] selected=new int[gridMain.SelectedIndices.Length];
    //for(int i=0;i<gridMain.SelectedIndices.Length;i++) {
    //  selected[i]=gridMain.SelectedIndices[i];
    //}
    //if(selected[selected.Length-1]==ListShowing.Count-1) {
    //  return;
    //}
    //for(int i=selected.Length-1;i>=0;i--) {//go backwards
    //  ListShowing.Reverse(selected[i],2);
    //}
    //FillGrids();
    //for(int i=0;i<selected.Length;i++) {
    //  gridMain.SetSelected(selected[i]+1,true);
    //}
    //changed=true;
    private void listAvailable_Click(Object sender, EventArgs e) throws Exception {
    }

    //textCustomField.Text="";
    private void textCustomField_Click(Object sender, EventArgs e) throws Exception {
    }

    //listAvailable.SelectedIndex=-1;
    private void butOK_Click(Object sender, EventArgs e) throws Exception {
    }

    //if(!changed) {
    //  DialogResult=DialogResult.OK;
    //  return;
    //}
    //if(category==DisplayFieldCategory.OrthoChart) {
    //  DisplayFields.SaveListForOrthoChart(ListShowing);
    //}
    //else {
    //  DisplayFields.SaveListForCategory(ListShowing,category);
    //}
    //DataValid.SetInvalid(InvalidType.DisplayFields);
    //DialogResult=DialogResult.OK;
    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formDisplayFields_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
    }

}


