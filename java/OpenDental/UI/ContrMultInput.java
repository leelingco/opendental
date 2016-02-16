//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental.UI;

import CS2JNet.System.StringSupport;
import OpenDental.PIn;
import OpenDental.ReportingOld2.FieldValueType;
import OpenDental.UI.ContrYN;
import OpenDental.UI.MultInputItem;
import OpenDental.UI.MultInputItemCollection;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.EnumType;
import OpenDentBusiness.ReportFKType;
import OpenDentBusiness.SchoolClasses;
import OpenDentBusiness.YN;

/**
* This control can be used anywhere that the user can enter a series of values.Typically used in reports to let the user specify the parameters.  Also used in the new patient medical questionnaire.  Each item in the list can be of a different input type, and this control will dynamically arrange them from the top down.  It adapts to many different widths.  If the input fields extend beyond the lower edge of the control, then a scrollbar is added.  This control can also be used for a series of values that are not to be displayed, but not altered.
*/
public class ContrMultInput  extends System.Windows.Forms.UserControl 
{
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private MultInputItemCollection multInputItems;
    private Label[] labels = new Label[]();
    private System.Windows.Forms.Panel panelMain = new System.Windows.Forms.Panel();
    private System.Windows.Forms.VScrollBar vScrollBar2 = new System.Windows.Forms.VScrollBar();
    private System.Windows.Forms.Panel panelSlide = new System.Windows.Forms.Panel();
    /**
    * These are the actual input fields that get laid out on the window based on the type.
    */
    private Control[] inputs = new Control[]();
    /**
    * This just changes the alignment slighly to make more room for answers and less for questions.
    */
    public boolean IsQuestionnaire = new boolean();
    /**
    * 
    */
    public ContrMultInput() throws Exception {
        initializeComponent();
        // This call is required by the Windows.Forms Form Designer.
        multInputItems = new MultInputItemCollection();
        panelSlide.MouseWheel += new System.Windows.Forms.MouseEventHandler(panelSlide_MouseWheel);
        vScrollBar2.MouseWheel += new System.Windows.Forms.MouseEventHandler(panelSlide_MouseWheel);
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
        this.panelMain = new System.Windows.Forms.Panel();
        this.vScrollBar2 = new System.Windows.Forms.VScrollBar();
        this.panelSlide = new System.Windows.Forms.Panel();
        this.panelMain.SuspendLayout();
        this.SuspendLayout();
        //
        // panelMain
        //
        this.panelMain.Controls.Add(this.panelSlide);
        this.panelMain.Controls.Add(this.vScrollBar2);
        this.panelMain.Location = new System.Drawing.Point(1, 1);
        this.panelMain.Name = "panelMain";
        this.panelMain.Size = new System.Drawing.Size(262, 313);
        this.panelMain.TabIndex = 0;
        //
        // vScrollBar2
        //
        this.vScrollBar2.Dock = System.Windows.Forms.DockStyle.Right;
        this.vScrollBar2.Location = new System.Drawing.Point(245, 0);
        this.vScrollBar2.Name = "vScrollBar2";
        this.vScrollBar2.Size = new System.Drawing.Size(17, 313);
        this.vScrollBar2.TabIndex = 1;
        this.vScrollBar2.Scroll += new System.Windows.Forms.ScrollEventHandler(this.vScrollBar2_Scroll);
        //
        // panelSlide
        //
        this.panelSlide.Location = new System.Drawing.Point(0, 0);
        this.panelSlide.Name = "panelSlide";
        this.panelSlide.Size = new System.Drawing.Size(224, 276);
        this.panelSlide.TabIndex = 2;
        this.panelSlide.Click += new System.EventHandler(this.panelSlide_Click);
        //
        // ContrMultInput
        //
        this.Controls.Add(this.panelMain);
        this.Name = "ContrMultInput";
        this.Size = new System.Drawing.Size(272, 321);
        this.Paint += new System.Windows.Forms.PaintEventHandler(this.ContrMultInput_Paint);
        this.Layout += new System.Windows.Forms.LayoutEventHandler(this.ContrMultInput_Layout);
        this.panelMain.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    /*
    		///<summary></summary>
    		public MultInputItemCollection MultInputItems{
    			get{
    				if(!RetrieveData())
    					return null;//can't retrieve any values if there is an error or if certain fields are blank.
    				return multInputItems;
    			}
    			set{
    				multInputItems=value;
    			}
    		}*/
    private void contrMultInput_Layout(Object sender, System.Windows.Forms.LayoutEventArgs e) throws Exception {
        //MessageBox.Show("layout");
        if (multInputItems == null)
            return ;
         
        Graphics g = this.CreateGraphics();
        this.SuspendLayout();
        panelMain.Width = Width - 2;
        panelMain.Height = Height - 2;
        vScrollBar2.Visible = false;
        panelSlide.Width = Width - 2;
        int maxH = 200;
        //600
        int requiredH = arrangeControls(g);
        if (requiredH > Height - 2 && requiredH < maxH)
        {
            //resize, but no scrollbar
            //this should be skipped on second pass because height adequate
            Height = requiredH + 2;
            ResumeLayout();
            return ;
        }
         
        //Layout will be triggered again
        if (requiredH > Height - 2)
        {
            //if the controls were too big to fit even with H at max
            vScrollBar2.Visible = true;
            panelSlide.Width -= vScrollBar2.Width;
            arrangeControls(g);
        }
         
        //then layout again, but we don't care about the H
        g.Dispose();
        ResumeLayout();
    }

    /**
    * Returns the required height.
    */
    private int arrangeControls(Graphics g) throws Exception {
        //calculate width of input section
        int inputW = 300;
        //the widest allowed for the input section on the right.
        if (IsQuestionnaire)
        {
            inputW = 450;
        }
         
        if (panelSlide.Width < 600)
        {
            inputW = panelSlide.Width / 2;
        }
         
        int promptW = panelSlide.Width - inputW;
        panelSlide.Controls.Clear();
        int yPos = 5;
        int itemH = 0;
        //item height
        labels = new Label[multInputItems.Count];
        inputs = new Control[multInputItems.Count];
        for (int i = 0;i < multInputItems.Count;i++)
        {
            //Calculate height
            itemH = (int)g.MeasureString(((MultInputItem)multInputItems.get___idx(i)).PromptingText, Font, promptW).Height;
            if (itemH < 20)
                itemH = 20;
             
            //promptingText
            labels[i] = new Label();
            labels[i].Location = new Point(2, yPos);
            //labels[i].Name="Label"+i.ToString();
            labels[i].Size = new Size(promptW - 5, itemH);
            labels[i].Text = multInputItems.get___idx(i).PromptingText;
            labels[i].TextAlign = ContentAlignment.MiddleRight;
            //labels[i].BorderStyle=BorderStyle.FixedSingle;//just used in debugging layout
            panelSlide.Controls.Add(labels[i]);
            if (multInputItems.get___idx(i).ValueType == FieldValueType.Boolean)
            {
                //add a checkbox
                inputs[i] = new CheckBox();
                inputs[i].Location = new Point(promptW, yPos + (itemH - 20) / 2);
                inputs[i].Size = new Size(inputW - 5, 20);
                if (multInputItems.get___idx(i).CurrentValues.Count == 0)
                    ((CheckBox)inputs[i]).Checked = false;
                else
                    ((CheckBox)inputs[i]).Checked = true; 
                ((CheckBox)inputs[i]).FlatStyle = FlatStyle.System;
                panelSlide.Controls.Add(inputs[i]);
            }
            else if (multInputItems.get___idx(i).ValueType == FieldValueType.Date)
            {
                //add a validDate box
                inputs[i] = new OpenDental.ValidDate();
                inputs[i].Location = new Point(promptW, yPos + (itemH - 20) / 2);
                if (inputW < 100)
                {
                    //not enough room for a fullsize box
                    inputs[i].Size = new Size(inputW - 20, 20);
                }
                else
                {
                    inputs[i].Size = new Size(75, 20);
                } 
                    ;
                if (multInputItems.get___idx(i).CurrentValues.Count > 0)
                {
                    DateTime myDate = (DateTime)multInputItems.get___idx(i).CurrentValues[0];
                    inputs[i].Text = myDate.ToShortDateString();
                }
                 
                panelSlide.Controls.Add(inputs[i]);
            }
            else if (multInputItems.get___idx(i).ValueType == FieldValueType.Def)
            {
                //add a psuedo combobox filled with visible defs for one category
                inputs[i] = new OpenDental.UI.ComboBoxMulti();
                for (int j = 0;j < DefC.getShort()[((Enum)multInputItems.get___idx(i).DefCategory).ordinal()].Length;j++)
                {
                    ((OpenDental.UI.ComboBoxMulti)inputs[i]).getItems().Add(DefC.getShort()[((Enum)multInputItems.get___idx(i).DefCategory).ordinal()][j].ItemName);
                    if (multInputItems.get___idx(i).CurrentValues.Count > 0 && multInputItems.get___idx(i).CurrentValues.Contains(DefC.getShort()[((Enum)multInputItems.get___idx(i).DefCategory).ordinal()][j].DefNum))
                    {
                        ((OpenDental.UI.ComboBoxMulti)inputs[i]).setSelected(j,true);
                    }
                     
                }
                inputs[i].Location = new Point(promptW, yPos + (itemH - 20) / 2);
                inputs[i].Size = new Size(inputW - 5, 20);
                panelSlide.Controls.Add(inputs[i]);
            }
            else if (multInputItems.get___idx(i).ValueType == FieldValueType.Enum)
            {
                //add a psuedo combobox filled with values for one enumeration
                inputs[i] = new OpenDental.UI.ComboBoxMulti();
                Type eType = Type.GetType("OpenDental." + multInputItems.get___idx(i).EnumerationType.ToString());
                for (int j = 0;j < Enum.GetNames(eType).Length;j++)
                {
                    ((OpenDental.UI.ComboBoxMulti)inputs[i]).getItems().Add(Enum.GetNames(eType)[j]);
                    if (multInputItems.get___idx(i).CurrentValues.Count > 0 && multInputItems.get___idx(i).CurrentValues.Contains((int)(Enum.Parse(eType, Enum.GetNames(eType)[j]))))
                    {
                        ((OpenDental.UI.ComboBoxMulti)inputs[i]).setSelected(j,true);
                    }
                     
                }
                inputs[i].Location = new Point(promptW, yPos + (itemH - 20) / 2);
                inputs[i].Size = new Size(inputW - 5, 20);
                panelSlide.Controls.Add(inputs[i]);
            }
            else if (multInputItems.get___idx(i).ValueType == FieldValueType.ForeignKey)
            {
                //add a psuedo combobox filled with values from one table
                inputs[i] = new OpenDental.UI.ComboBoxMulti();
                //these two arrays are matched item for item
                String[] foreignRows = getFRows(multInputItems.get___idx(i).FKType);
                long[] foreignKeys = getFKeys(multInputItems.get___idx(i).FKType);
                for (int j = 0;j < foreignRows.Length;j++)
                {
                    ((OpenDental.UI.ComboBoxMulti)inputs[i]).getItems().Add(foreignRows[j]);
                    if (multInputItems.get___idx(i).CurrentValues.Count > 0 && multInputItems.get___idx(i).CurrentValues.Contains(foreignKeys[j]))
                    {
                        ((OpenDental.UI.ComboBoxMulti)inputs[i]).setSelected(j,true);
                    }
                     
                }
                inputs[i].Location = new Point(promptW, yPos + (itemH - 20) / 2);
                inputs[i].Size = new Size(inputW - 5, 20);
                panelSlide.Controls.Add(inputs[i]);
            }
            else if (multInputItems.get___idx(i).ValueType == FieldValueType.Integer)
            {
                //add a validNumber box
                inputs[i] = new OpenDental.ValidNumber();
                inputs[i].Location = new Point(promptW, yPos + (itemH - 20) / 2);
                if (inputW < 100)
                {
                    //not enough room for a fullsize box
                    inputs[i].Size = new Size(inputW - 20, 20);
                }
                else
                {
                    inputs[i].Size = new Size(75, 20);
                } 
                if (multInputItems.get___idx(i).CurrentValues.Count > 0)
                {
                    inputs[i].Text = ((int)multInputItems.get___idx(i).CurrentValues[0]).ToString();
                }
                 
                panelSlide.Controls.Add(inputs[i]);
            }
            else if (multInputItems.get___idx(i).ValueType == FieldValueType.Number)
            {
                //add a validDouble box
                inputs[i] = new OpenDental.ValidDouble();
                inputs[i].Location = new Point(promptW, yPos + (itemH - 20) / 2);
                if (inputW < 100)
                {
                    //not enough room for a fullsize box
                    inputs[i].Size = new Size(inputW - 20, 20);
                }
                else
                {
                    inputs[i].Size = new Size(75, 20);
                } 
                if (multInputItems.get___idx(i).CurrentValues.Count > 0)
                {
                    inputs[i].Text = ((double)multInputItems.get___idx(i).CurrentValues[0]).ToString("n");
                }
                 
                panelSlide.Controls.Add(inputs[i]);
            }
            else if (multInputItems.get___idx(i).ValueType == FieldValueType.String)
            {
                //add a textbox
                inputs[i] = new TextBox();
                inputs[i].Location = new Point(promptW, yPos + (itemH - 20) / 2);
                //inputs[i].Name=
                inputs[i].Size = new Size(inputW - 5, 20);
                if (multInputItems.get___idx(i).CurrentValues.Count > 0)
                {
                    inputs[i].Text = multInputItems.get___idx(i).CurrentValues[0].ToString();
                }
                 
                panelSlide.Controls.Add(inputs[i]);
            }
            else if (multInputItems.get___idx(i).ValueType == FieldValueType.YesNoUnknown)
            {
                //add two checkboxes: Yes(1) and No(2).
                inputs[i] = new ContrYN();
                if (multInputItems.get___idx(i).CurrentValues.Count > 0)
                {
                    ((ContrYN)inputs[i]).setCurrentValue((YN)multInputItems.get___idx(i).CurrentValues[0]);
                }
                 
                inputs[i].Location = new Point(promptW, yPos + (itemH - 20) / 2);
                inputs[i].Size = new Size(inputW - 5, 20);
                panelSlide.Controls.Add(inputs[i]);
            }
                     
            yPos += itemH + 5;
            if (yPos > panelMain.Height && !vScrollBar2.Visible)
                return yPos;
             
        }
        //There's not enough room, so stop and make the scrollbar visible.
        panelSlide.Height = yPos;
        vScrollBar2.Maximum = panelSlide.Height;
        vScrollBar2.Minimum = 0;
        vScrollBar2.LargeChange = panelMain.Height;
        vScrollBar2.SmallChange = 5;
        return -1;
    }

    private String[] getFRows(ReportFKType fKType) throws Exception {
        String[] retVal = new String[0];
        switch(fKType)
        {
            case SchoolClass: 
                retVal = new String[SchoolClasses.getList().Length];
                for (int i = 0;i < SchoolClasses.getList().Length;i++)
                {
                    retVal[i] = SchoolClasses.getList()[i].GradYear + " - " + SchoolClasses.getList()[i].Descript;
                }
                break;
        
        }
        return retVal;
    }

    private long[] getFKeys(ReportFKType fKType) throws Exception {
        long[] retVal = new long[0];
        switch(fKType)
        {
            case SchoolClass: 
                retVal = new long[SchoolClasses.getList().Length];
                for (int i = 0;i < SchoolClasses.getList().Length;i++)
                {
                    retVal[i] = SchoolClasses.getList()[i].SchoolClassNum;
                }
                break;
        
        }
        return retVal;
    }

    private void vScrollBar2_Scroll(Object sender, System.Windows.Forms.ScrollEventArgs e) throws Exception {
        panelSlide.Location = new Point(0, -vScrollBar2.Value);
        if (ActiveControl == null)
        {
            //only activate scroll if no control within this control is active
            vScrollBar2.Select();
        }
         
    }

    private void panelSlide_MouseWheel(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        if (!vScrollBar2.Visible)
        {
            return ;
        }
         
        int max = vScrollBar2.Maximum - vScrollBar2.LargeChange;
        int newScrollVal = vScrollBar2.Value - e.Delta / 8;
        if (newScrollVal > max)
        {
            vScrollBar2.Value = max;
        }
        else if (newScrollVal < vScrollBar2.Minimum)
        {
            vScrollBar2.Value = vScrollBar2.Minimum;
        }
        else
        {
            vScrollBar2.Value = newScrollVal;
        }  
        panelSlide.Location = new Point(0, -vScrollBar2.Value);
    }

    /**
    * 
    */
    public void addInputItem(String promptingText, FieldValueType valueType, ArrayList currentValues, EnumType enumerationType, DefCat defCategory, ReportFKType fKType) throws Exception {
        multInputItems.add(new MultInputItem(promptingText,valueType,currentValues,enumerationType,defCategory,fKType));
    }

    /**
    * Overload for just one simple value.
    */
    public void addInputItem(String promptingText, FieldValueType valueType, Object currentValue) throws Exception {
        ArrayList currentValues = new ArrayList();
        currentValues.Add(currentValue);
        //the enumtype and defcat are completely arbitrary.
        multInputItems.add(new MultInputItem(promptingText,valueType,currentValues,EnumType.ApptStatus,DefCat.AccountColors,ReportFKType.None));
    }

    /**
    * Overload for using DefCat.
    */
    public void addInputItem(String promptingText, FieldValueType valueType, ArrayList currentValues, DefCat defCategory) throws Exception {
        if (currentValues == null)
            currentValues = new ArrayList();
         
        multInputItems.add(new MultInputItem(promptingText,valueType,currentValues,EnumType.ApptStatus,defCategory,ReportFKType.None));
    }

    /**
    * Overload for using Enum.
    */
    public void addInputItem(String promptingText, FieldValueType valueType, ArrayList currentValues, EnumType enumerationType) throws Exception {
        if (currentValues == null)
            currentValues = new ArrayList();
         
        multInputItems.add(new MultInputItem(promptingText,valueType,currentValues,enumerationType,DefCat.AccountColors,ReportFKType.None));
    }

    private void contrMultInput_Paint(Object sender, System.Windows.Forms.PaintEventArgs e) throws Exception {
        //blue
        e.Graphics.DrawRectangle(new Pen(Color.FromArgb(127, 157, 185)), 0, 0, Width - 1, Height - 1);
    }

    private void panelSlide_Click(Object sender, System.EventArgs e) throws Exception {
        panelSlide.Select();
    }

    /**
    * Should run AllFieldsAreValid() first. This is called from the parent form to retrieve the data that the user entered.  Returns an arraylist.  For most fields, the length of the arraylist will be 0 or 1.
    */
    public ArrayList getCurrentValues(int item) throws Exception {
        ArrayList retVal = new ArrayList();
        if (multInputItems.get___idx(item).ValueType == FieldValueType.Boolean)
        {
            if (((CheckBox)inputs[item]).Checked)
            {
                retVal.Add(true);
            }
             
        }
        else if (multInputItems.get___idx(item).ValueType == FieldValueType.Date)
        {
            retVal.Add(PIn.Date(inputs[item].Text));
        }
        else if (multInputItems.get___idx(item).ValueType == FieldValueType.Def)
        {
            OpenDental.UI.ComboBoxMulti comboBox = (OpenDental.UI.ComboBoxMulti)inputs[item];
            for (int j = 0;j < comboBox.getSelectedIndices().Count;j++)
            {
                retVal.Add(DefC.getShort()[((Enum)multInputItems.get___idx(item).DefCategory).ordinal()][(int)comboBox.getSelectedIndices()[j]].DefNum);
            }
        }
        else if (multInputItems.get___idx(item).ValueType == FieldValueType.Enum)
        {
            OpenDental.UI.ComboBoxMulti comboBox = (OpenDental.UI.ComboBoxMulti)inputs[item];
            Type eType = Type.GetType("OpenDental." + multInputItems.get___idx(item).EnumerationType.ToString());
            for (int j = 0;j < comboBox.getSelectedIndices().Count;j++)
            {
                retVal.Add((int)(Enum.Parse(eType, Enum.GetNames(eType)[(int)comboBox.getSelectedIndices()[j]])));
            }
        }
        else if (multInputItems.get___idx(item).ValueType == FieldValueType.Integer)
        {
            retVal.Add(PIn.Long(inputs[item].Text));
        }
        else if (multInputItems.get___idx(item).ValueType == FieldValueType.Number)
        {
            retVal.Add(PIn.Double(inputs[item].Text));
        }
        else if (multInputItems.get___idx(item).ValueType == FieldValueType.String)
        {
            if (!StringSupport.equals(inputs[item].Text, ""))
            {
                //the text is first stripped of any ?'s
                retVal.Add(Regex.Replace(inputs[item].Text, "\\?", ""));
            }
             
        }
        else if (multInputItems.get___idx(item).ValueType == FieldValueType.YesNoUnknown)
        {
            retVal.Add(((ContrYN)inputs[item]).getCurrentValue());
        }
                
        return retVal;
    }

    //MessageBox.Show(multInputItems[1].CurrentValues.Count.ToString());
    /**
    * Called externally to determine whether any of the fields are displaying errors.  Typically used in the OK_Click of the parent form to prevent closing if any field entries are invalid.
    */
    public boolean allFieldsAreValid() throws Exception {
        for (int i = 0;i < multInputItems.Count;i++)
        {
            if (multInputItems.get___idx(i).ValueType == FieldValueType.Date)
            {
                if (!StringSupport.equals(((OpenDental.ValidDate)inputs[i]).errorProvider1.GetError(inputs[i]), ""))
                {
                    return false;
                }
                 
            }
             
            if (multInputItems.get___idx(i).ValueType == FieldValueType.Integer)
            {
                if (!StringSupport.equals(((OpenDental.ValidNumber)inputs[i]).errorProvider1.GetError(inputs[i]), ""))
                {
                    return false;
                }
                 
            }
             
            if (multInputItems.get___idx(i).ValueType == FieldValueType.Number)
            {
                if (!StringSupport.equals(((OpenDental.ValidDouble)inputs[i]).errorProvider1.GetError(inputs[i]), ""))
                {
                    return false;
                }
                 
            }
             
        }
        return true;
    }

    public void clearInputItems() throws Exception {
        multInputItems.Clear();
    }

}


