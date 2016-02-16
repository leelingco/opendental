//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.EhrOperand;
import OpenDentBusiness.EhrPatListElement;
import OpenDentBusiness.EhrRestrictionType;
import OpenDentBusiness.Lans;

public class FormEhrPatListElementEdit  extends Form 
{

    public EhrPatListElement Element;
    public boolean IsNew = new boolean();
    public boolean Delete = new boolean();
    public FormEhrPatListElementEdit() throws Exception {
        initializeComponent();
    }

    private void formPatListElementEdit_Load(Object sender, EventArgs e) throws Exception {
        listRestriction.Items.Clear();
        for (int i = 0;i < Enum.GetNames(EhrRestrictionType.class).Length;i++)
        {
            listRestriction.Items.Add(Enum.GetNames(EhrRestrictionType.class)[i]);
        }
        listRestriction.SelectedIndex = ((Enum)Element.Restriction).ordinal();
        listOperand.SelectedIndex = ((Enum)Element.Operand).ordinal();
        textCompareString.Text = Element.CompareString;
        textLabValue.Text = Element.LabValue;
        checkOrderBy.Checked = Element.OrderBy;
        changeLayout();
    }

    private void listRestriction_SelectedIndexChanged(Object sender, EventArgs e) throws Exception {
        changeLayout();
    }

    private void changeLayout() throws Exception {
        labelCompareString.Visible = true;
        textCompareString.Visible = true;
        labelExample.Visible = true;
        labelLabValue.Visible = false;
        textLabValue.Visible = false;
        labelOperand.Visible = false;
        listOperand.Visible = false;
        if (listRestriction.SelectedIndex == 0)
        {
            //Birthdate
            labelCompareString.Text = "Enter age";
            labelExample.Text = "Ex: 22";
            labelOperand.Visible = true;
            listOperand.Visible = true;
        }
         
        if (listRestriction.SelectedIndex == 1)
        {
            //Disease
            labelCompareString.Text = "Enter ICD9 code";
            labelExample.Text = "Ex: 414.0";
        }
         
        if (listRestriction.SelectedIndex == 2)
        {
            //Medication
            labelCompareString.Text = "Medication name";
            labelExample.Text = "Ex: Coumadin";
        }
         
        if (listRestriction.SelectedIndex == 3)
        {
            //LabResult
            labelCompareString.Text = "Test name (exact)";
            labelExample.Text = "Ex: HDL-cholesterol";
            labelLabValue.Visible = true;
            textLabValue.Visible = true;
            labelOperand.Visible = true;
            listOperand.Visible = true;
        }
         
        if (listRestriction.SelectedIndex == 4)
        {
            //Gender
            labelCompareString.Text = "For display and sorting";
            labelExample.Visible = false;
            textCompareString.Visible = false;
        }
         
    }

    private boolean isValid() throws Exception {
        int index = listRestriction.SelectedIndex;
        if (StringSupport.equals(textCompareString.Text.Trim(), "") && index != 4)
        {
            //4-Gender
            MessageBox.Show(Lans.g(this,"Please enter a value."));
            return false;
        }
         
        if (index == 0)
        {
            try
            {
                //Birthdate
                System.Convert.ToInt32(textCompareString.Text);
            }
            catch (Exception __dummyCatchVar0)
            {
                //Must be number.
                MessageBox.Show("Please enter a valid age.");
                return false;
            }
        
        }
         
        if (index == 1)
        {
            try
            {
                //Disease
                System.Convert.ToDecimal(textCompareString.Text);
            }
            catch (Exception __dummyCatchVar1)
            {
                //Must be number.
                MessageBox.Show("Please enter a valid number ICD9 code.");
                return false;
            }
        
        }
         
        if (index == 3)
        {
            try
            {
                //LabResult
                System.Convert.ToDecimal(textLabValue.Text);
            }
            catch (Exception __dummyCatchVar2)
            {
                //Must be number.
                MessageBox.Show("Please enter a valid number for Lab value.");
                return false;
            }
        
        }
         
        if (index == 4)
        {
            //Gender
            textCompareString.Text = "";
        }
         
        if (index != 3)
        {
            //Not LabResult
            textLabValue.Text = "";
        }
         
        return true;
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (!IsNew)
        {
            Delete = true;
        }
         
        DialogResult = DialogResult.Cancel;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (!isValid())
        {
            return ;
        }
         
        Element.Restriction = (EhrRestrictionType)listRestriction.SelectedIndex;
        Element.Operand = (EhrOperand)listOperand.SelectedIndex;
        Element.CompareString = textCompareString.Text;
        Element.LabValue = textLabValue.Text;
        Element.OrderBy = checkOrderBy.Checked;
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
        this.labelOperand = new System.Windows.Forms.Label();
        this.butCancel = new System.Windows.Forms.Button();
        this.butOK = new System.Windows.Forms.Button();
        this.butDelete = new System.Windows.Forms.Button();
        this.labelRestriction = new System.Windows.Forms.Label();
        this.labelCompareString = new System.Windows.Forms.Label();
        this.textCompareString = new System.Windows.Forms.TextBox();
        this.labelLabValue = new System.Windows.Forms.Label();
        this.checkOrderBy = new System.Windows.Forms.CheckBox();
        this.textLabValue = new System.Windows.Forms.TextBox();
        this.listRestriction = new System.Windows.Forms.ListBox();
        this.listOperand = new System.Windows.Forms.ListBox();
        this.labelExample = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // labelOperand
        //
        this.labelOperand.Location = new System.Drawing.Point(87, 87);
        this.labelOperand.Name = "labelOperand";
        this.labelOperand.Size = new System.Drawing.Size(61, 17);
        this.labelOperand.TabIndex = 10;
        this.labelOperand.Text = "Operand";
        this.labelOperand.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butCancel
        //
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.Location = new System.Drawing.Point(312, 236);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 23);
        this.butCancel.TabIndex = 7;
        this.butCancel.Text = "Cancel";
        this.butCancel.UseVisualStyleBackColor = true;
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOK
        //
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.Location = new System.Drawing.Point(231, 236);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 23);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "OK";
        this.butOK.UseVisualStyleBackColor = true;
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butDelete
        //
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.Location = new System.Drawing.Point(24, 236);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 23);
        this.butDelete.TabIndex = 8;
        this.butDelete.TabStop = false;
        this.butDelete.Text = "Delete";
        this.butDelete.UseVisualStyleBackColor = true;
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // labelRestriction
        //
        this.labelRestriction.Location = new System.Drawing.Point(73, 12);
        this.labelRestriction.Name = "labelRestriction";
        this.labelRestriction.Size = new System.Drawing.Size(75, 17);
        this.labelRestriction.TabIndex = 13;
        this.labelRestriction.Text = "Restriction";
        this.labelRestriction.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelCompareString
        //
        this.labelCompareString.Location = new System.Drawing.Point(2, 137);
        this.labelCompareString.Name = "labelCompareString";
        this.labelCompareString.Size = new System.Drawing.Size(146, 17);
        this.labelCompareString.TabIndex = 9;
        this.labelCompareString.Text = "Compare string";
        this.labelCompareString.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textCompareString
        //
        this.textCompareString.Location = new System.Drawing.Point(149, 136);
        this.textCompareString.Name = "textCompareString";
        this.textCompareString.Size = new System.Drawing.Size(128, 20);
        this.textCompareString.TabIndex = 1;
        //
        // labelLabValue
        //
        this.labelLabValue.Location = new System.Drawing.Point(55, 163);
        this.labelLabValue.Name = "labelLabValue";
        this.labelLabValue.Size = new System.Drawing.Size(93, 17);
        this.labelLabValue.TabIndex = 14;
        this.labelLabValue.Text = "Lab value";
        this.labelLabValue.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.labelLabValue.Visible = false;
        //
        // checkOrderBy
        //
        this.checkOrderBy.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkOrderBy.Location = new System.Drawing.Point(52, 187);
        this.checkOrderBy.Name = "checkOrderBy";
        this.checkOrderBy.Size = new System.Drawing.Size(112, 24);
        this.checkOrderBy.TabIndex = 16;
        this.checkOrderBy.Text = "Order by";
        this.checkOrderBy.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkOrderBy.UseVisualStyleBackColor = true;
        //
        // textLabValue
        //
        this.textLabValue.Location = new System.Drawing.Point(149, 161);
        this.textLabValue.Name = "textLabValue";
        this.textLabValue.Size = new System.Drawing.Size(128, 20);
        this.textLabValue.TabIndex = 2;
        this.textLabValue.Visible = false;
        //
        // listRestriction
        //
        this.listRestriction.FormattingEnabled = true;
        this.listRestriction.Items.AddRange(new Object[]{ "Birthdate", "Disease", "Medication", "Lab result", "Gender" });
        this.listRestriction.Location = new System.Drawing.Point(149, 12);
        this.listRestriction.Name = "listRestriction";
        this.listRestriction.Size = new System.Drawing.Size(75, 69);
        this.listRestriction.TabIndex = 18;
        this.listRestriction.SelectedIndexChanged += new System.EventHandler(this.listRestriction_SelectedIndexChanged);
        //
        // listOperand
        //
        this.listOperand.FormattingEnabled = true;
        this.listOperand.Items.AddRange(new Object[]{ "GreaterThan", "LessThan", "Equals" });
        this.listOperand.Location = new System.Drawing.Point(149, 87);
        this.listOperand.Name = "listOperand";
        this.listOperand.Size = new System.Drawing.Size(75, 43);
        this.listOperand.TabIndex = 19;
        //
        // labelExample
        //
        this.labelExample.Location = new System.Drawing.Point(278, 137);
        this.labelExample.Name = "labelExample";
        this.labelExample.Size = new System.Drawing.Size(119, 17);
        this.labelExample.TabIndex = 20;
        this.labelExample.Text = "Example";
        this.labelExample.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // FormPatListElementEdit
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(399, 271);
        this.Controls.Add(this.labelExample);
        this.Controls.Add(this.listOperand);
        this.Controls.Add(this.listRestriction);
        this.Controls.Add(this.textLabValue);
        this.Controls.Add(this.checkOrderBy);
        this.Controls.Add(this.labelLabValue);
        this.Controls.Add(this.labelRestriction);
        this.Controls.Add(this.textCompareString);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.labelCompareString);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.labelOperand);
        this.Name = "FormPatListElementEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "PatList Element Edit";
        this.Load += new System.EventHandler(this.FormPatListElementEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Label labelOperand = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butOK = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butDelete = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label labelRestriction = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelCompareString = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textCompareString = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelLabValue = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox checkOrderBy = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.TextBox textLabValue = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.ListBox listRestriction = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.ListBox listOperand = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label labelExample = new System.Windows.Forms.Label();
}


