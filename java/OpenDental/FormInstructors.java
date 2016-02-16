//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:17 PM
//

package OpenDental;

import OpenDental.FormInstructors;
import OpenDental.Lan;
import OpenDental.Properties.Resources;

/**
* 
*/
public class FormInstructors  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.Button butAdd;
    private System.ComponentModel.Container components = null;
    private System.Windows.Forms.ListBox listInstructors = new System.Windows.Forms.ListBox();
    //private bool changed;
    /**
    * 
    */
    public FormInstructors() throws Exception {
        initializeComponent();
        //Providers.Selected=-1;
        Lan.f(this);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormInstructors.class);
        this.listInstructors = new System.Windows.Forms.ListBox();
        this.butClose = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // listInstructors
        //
        this.listInstructors.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.listInstructors.Location = new System.Drawing.Point(16, 12);
        this.listInstructors.Name = "listInstructors";
        this.listInstructors.Size = new System.Drawing.Size(790, 524);
        this.listInstructors.TabIndex = 4;
        this.listInstructors.DoubleClick += new System.EventHandler(this.listInstructors_DoubleClick);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butClose.Location = new System.Drawing.Point(734, 554);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 26);
        this.butClose.TabIndex = 3;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(16, 553);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(79, 26);
        this.butAdd.TabIndex = 10;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // FormInstructors
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butClose;
        this.ClientSize = new System.Drawing.Size(825, 596);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.listInstructors);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormInstructors";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Instructors";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormInstructors_Closing);
        this.Load += new System.EventHandler(this.FormInstructors_Load);
        this.ResumeLayout(false);
    }

    private void formInstructors_Load(Object sender, System.EventArgs e) throws Exception {
    }

    //FillList();
    private void fillList() throws Exception {
    }

    /*int previousSelected=-1;
    			if(listInstructors.SelectedIndex!=-1){
    				previousSelected=Instructors.List[listInstructors.SelectedIndex].InstructorNum;
    			}
    			Instructors.Refresh();
    			listInstructors.Items.Clear();
    			for(int i=0;i<Instructors.List.Length;i++){
    				listInstructors.Items.Add(Instructors.List[i].LName+", "+Instructors.List[i].FName+", "+Instructors.List[i].Suffix);
    				if(Instructors.List[i].InstructorNum==previousSelected){
    					listInstructors.SelectedIndex=i;
    				}
    			}*/
    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
    }

    /*Instructor cur=new Instructor();
    			FormInstructorEdit FormI=new FormInstructorEdit(cur);
    			FormI.IsNew=true;
    			FormI.ShowDialog();
    			if(FormI.DialogResult!=DialogResult.OK){
    				return;
    			}
    			changed=true;
    			FillList();
    			listInstructors.SelectedIndex=-1;*/
    private void listInstructors_DoubleClick(Object sender, System.EventArgs e) throws Exception {
    }

    /*if(listInstructors.SelectedIndex==-1)
    				return;
    			FormInstructorEdit FormI=new FormInstructorEdit(Instructors.List[listInstructors.SelectedIndex]);
    			FormI.ShowDialog();
    			if(FormI.DialogResult!=DialogResult.OK){
    				return;
    			}
    			changed=true;
    			FillList();*/
    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
    }

    //Close();
    private void formInstructors_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
    }

}


//if(changed){
//	DataValid.SetInvalid(InvalidTypes.DentalSchools);
//}