//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:26 PM
//

package ODR;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import ODR.ComboBoxMulti;
import ODR.FormParameterInput;
import ODR.ParameterCollection;
import ODR.ParamValueType;
import ODR.ValidDate;
import ODR.ValidDouble;
import ODR.ValidNumber;
import OpenDentBusiness.PIn;

/**
* Summary description for FormBasicTemplate.
*/
public class FormParameterInput  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.Button butCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butOK = new System.Windows.Forms.Button();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private System.Windows.Forms.Panel panelMain = new System.Windows.Forms.Panel();
    private ParameterCollection Parameters;
    /**
    * These are the actual input fields that get laid out on the window based on the type.
    */
    private Control[] inputs = new Control[]();
    private Label[] labels = new Label[]();
    /**
    * 
    */
    public FormParameterInput(RefSupport<ParameterCollection> parameters) throws Exception {
        initializeComponent();
        // Required for Windows Form Designer support
        //Lan.C("All", new System.Windows.Forms.Control[] {
        //	butOK,
        //	butCancel,
        //});
        //MultInput2.MultInputItems=new MultInputItemCollection();
        Parameters = parameters.getValue();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormParameterInput.class);
        this.butCancel = new System.Windows.Forms.Button();
        this.butOK = new System.Windows.Forms.Button();
        this.panelMain = new System.Windows.Forms.Panel();
        this.SuspendLayout();
        //
        // butCancel
        //
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(525, 275);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOK
        //
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.Location = new System.Drawing.Point(435, 275);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // panelMain
        //
        this.panelMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.panelMain.AutoScroll = true;
        this.panelMain.Location = new System.Drawing.Point(20, 12);
        this.panelMain.Name = "panelMain";
        this.panelMain.Size = new System.Drawing.Size(580, 247);
        this.panelMain.TabIndex = 2;
        //
        // FormParameterInput
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(628, 315);
        this.Controls.Add(this.panelMain);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormParameterInput";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Enter Parameters";
        this.Load += new System.EventHandler(this.FormParameterInput_Load);
        this.ResumeLayout(false);
    }

    private void formParameterInput_Load(Object sender, System.EventArgs e) throws Exception {
        //For testing
        /*
        			Parameters=new ParameterCollection();
        			Parameter param=new Parameter();
        			param.Prompt="You should put in some text in the box to the right";
        			param.ValueType=ParamValueType.String;
        			Parameters.Add(param);
        			param=new Parameter();
        			param.Prompt="Prompt";
        			param.ValueType=ParamValueType.String;
        			Parameters.Add(param);
        			param=new Parameter();
        			param.Prompt="Yet another prompt for info";
        			param.ValueType=ParamValueType.String;
        			Parameters.Add(param);
        			param=new Parameter();
        			param.Prompt="Testing checkbox";
        			param.ValueType=ParamValueType.Boolean;
        			Parameters.Add(param);
        			param=new Parameter();
        			param.Prompt="Testing Date";
        			param.ValueType=ParamValueType.Date;
        			Parameters.Add(param);
        			param=new Parameter();
        			param.Prompt="Testing Number";
        			param.ValueType=ParamValueType.Number;
        			Parameters.Add(param);
        			
        			param=new Parameter();
        			param.Prompt="Select Dental Specialty";
        			param.ValueType=ParamValueType.Enum;
        			param.EnumerationType=EnumType.DentalSpecialty;
        			Parameters.Add(param);
        		
        			//still need query*/
        Graphics g = this.CreateGraphics();
        this.SuspendLayout();
        int maxH = 100;
        //600
        int requiredH = arrangeControls(g);
        if (requiredH >= maxH)
        {
            this.Height = maxH + (Height - panelMain.Height);
        }
        else
        {
            this.Height = requiredH + (Height - panelMain.Height);
        } 
        /*if(requiredH>Height-2 && requiredH<maxH){//resize, but no scrollbar
        				//this should be skipped on second pass because height adequate
        				Height=requiredH+2;
        				ResumeLayout();
        				return;//Layout will be triggered again
        			}
        			if(requiredH>Height-2){//if the controls were too big to fit even with H at max
        				vScrollBar2.Visible=true;
        				panelSlide.Width-=vScrollBar2.Width;
        				ArrangeControls(g);//then layout again, but we don't care about the H
        			}*/
        g.Dispose();
        ResumeLayout();
    }

    /**
    * Returns the required height.
    */
    private int arrangeControls(Graphics g) throws Exception {
        //580 is the initial width of the panel
        int inputW = 280;
        //The input section on the right.
        int promptW = 580 - 20 - inputW;
        //20 is for the scrollbar on the right
        panelMain.Controls.Clear();
        int yPos = 5;
        int itemH = 0;
        //item height
        labels = new Label[Parameters.Count];
        inputs = new Control[Parameters.Count];
        for (int i = 0;i < Parameters.Count;i++)
        {
            //Calculate height
            itemH = (int)g.MeasureString(Parameters.get___idx(i).getPrompt(), Font, promptW).Height;
            if (itemH < 20)
                itemH = 20;
             
            //promptingText
            labels[i] = new Label();
            labels[i].Location = new Point(5, yPos);
            //labels[i].Name="Label"+i.ToString();
            labels[i].Size = new Size(promptW - 8, itemH);
            labels[i].Text = Parameters.get___idx(i).getPrompt();
            labels[i].TextAlign = ContentAlignment.MiddleRight;
            //labels[i].BorderStyle=BorderStyle.FixedSingle;//just used in debugging layout
            panelMain.Controls.Add(labels[i]);
            if (Parameters.get___idx(i).getValueType() == ParamValueType.Boolean)
            {
                //add a checkbox
                inputs[i] = new CheckBox();
                inputs[i].Location = new Point(promptW, yPos + (itemH - 20) / 2);
                inputs[i].Size = new Size(inputW - 5, 20);
                if (Parameters.get___idx(i).getCurrentValues().Count == 0)
                    ((CheckBox)inputs[i]).Checked = false;
                else
                    ((CheckBox)inputs[i]).Checked = true; 
                ((CheckBox)inputs[i]).FlatStyle = FlatStyle.System;
                panelMain.Controls.Add(inputs[i]);
            }
            else if (Parameters.get___idx(i).getValueType() == ParamValueType.Date)
            {
                //add a validDate box
                inputs[i] = new ValidDate();
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
                if (Parameters.get___idx(i).getCurrentValues().Count > 0)
                {
                    DateTime myDate = (DateTime)Parameters.get___idx(i).getCurrentValues()[0];
                    inputs[i].Text = myDate.ToShortDateString();
                }
                 
                panelMain.Controls.Add(inputs[i]);
            }
            else if (Parameters.get___idx(i).getValueType() == ParamValueType.Enum)
            {
                //add a psuedo combobox filled with values for one enumeration
                inputs[i] = new ComboBoxMulti();
                Type eType = Type.GetType("ODR." + Parameters.get___idx(i).getEnumerationType().ToString());
                for (int j = 0;j < Enum.GetNames(eType).Length;j++)
                {
                    ((ComboBoxMulti)inputs[i]).getItems().Add(Enum.GetNames(eType)[j]);
                    if (Parameters.get___idx(i).getCurrentValues().Count > 0 && Parameters.get___idx(i).getCurrentValues().Contains((int)(Enum.Parse(eType, Enum.GetNames(eType)[j]))))
                    {
                        ((ComboBoxMulti)inputs[i]).setSelected(j,true);
                    }
                     
                }
                inputs[i].Location = new Point(promptW, yPos + (itemH - 20) / 2);
                inputs[i].Size = new Size(inputW - 5, 20);
                panelMain.Controls.Add(inputs[i]);
            }
            else if (Parameters.get___idx(i).getValueType() == ParamValueType.Integer)
            {
                //add a validNumber box
                inputs[i] = new ValidNumber();
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
                if (Parameters.get___idx(i).getCurrentValues().Count > 0)
                {
                    inputs[i].Text = ((int)Parameters.get___idx(i).getCurrentValues()[0]).ToString();
                }
                 
                panelMain.Controls.Add(inputs[i]);
            }
            else if (Parameters.get___idx(i).getValueType() == ParamValueType.Number)
            {
                //add a validDouble box
                inputs[i] = new ValidDouble();
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
                if (Parameters.get___idx(i).getCurrentValues().Count > 0)
                {
                    inputs[i].Text = ((double)Parameters.get___idx(i).getCurrentValues()[0]).ToString("n");
                }
                 
                panelMain.Controls.Add(inputs[i]);
            }
            else if (Parameters.get___idx(i).getValueType() == ParamValueType.String)
            {
                //add a textbox
                inputs[i] = new TextBox();
                inputs[i].Location = new Point(promptW, yPos + (itemH - 20) / 2);
                //inputs[i].Name=
                inputs[i].Size = new Size(inputW - 5, 20);
                if (Parameters.get___idx(i).getCurrentValues().Count > 0)
                {
                    inputs[i].Text = Parameters.get___idx(i).getCurrentValues()[0].ToString();
                }
                 
                panelMain.Controls.Add(inputs[i]);
            }
                  
            yPos += itemH + 5;
        }
        return yPos;
    }

    //if(yPos>panelMain.Height && !vScrollBar2.Visible)
    //	return yPos;//There's not enough room, so stop and make the scrollbar visible.
    //panelSlide.Height=yPos;
    //vScrollBar2.Maximum=panelSlide.Height;
    //vScrollBar2.Minimum=0;
    //vScrollBar2.LargeChange=panelMain.Height;
    //vScrollBar2.SmallChange=5;
    /*
    		///<summary></summary>
    		public void AddInputItem(string myPromptingText,ParamValueType myValueType,ArrayList myCurrentValues,EnumType myEnumerationType,DefCat myDefCategory,ReportFKType myFKType){
    			MultInput2.AddInputItem(myPromptingText,myValueType,myCurrentValues,myEnumerationType,myDefCategory,myFKType);
    		}*/
    /*
    		///<summary>After this form closes, use this method to retrieve the data that the user entered.</summary>
    		public ArrayList GetCurrentValues(int itemIndex){
    			return MultInput2.GetCurrentValues(itemIndex);
    		}*/
    //private void contrParamInput_SizeChanged(object sender, System.EventArgs e) {
    //	Height=contrParamInput.Bottom+90;
    //	Refresh();//this should trigger another layout
    //}
    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        for (int i = 0;i < Parameters.Count;i++)
        {
            //make sure all entries are valid
            if (Parameters.get___idx(i).getValueType() == ParamValueType.Date)
            {
                if (!StringSupport.equals(((ValidDate)inputs[i]).errorProvider1.GetError(inputs[i]), ""))
                {
                    MessageBox.Show("Please fix data entry errors first.");
                    return ;
                }
                 
            }
             
            if (Parameters.get___idx(i).getValueType() == ParamValueType.Integer)
            {
                if (!StringSupport.equals(((ValidNumber)inputs[i]).errorProvider1.GetError(inputs[i]), ""))
                {
                    MessageBox.Show("Please fix data entry errors first.");
                    return ;
                }
                 
            }
             
            if (Parameters.get___idx(i).getValueType() == ParamValueType.Number)
            {
                if (!StringSupport.equals(((ValidDouble)inputs[i]).errorProvider1.GetError(inputs[i]), ""))
                {
                    MessageBox.Show("Please fix data entry errors first.");
                    return ;
                }
                 
            }
             
        }
        for (int i = 0;i < Parameters.Count;i++)
        {
            //then fill the current values and output value.  For most fields, the length of CurrentValues will be 0 or 1.
            Parameters.get___idx(i).setCurrentValues(new ArrayList());
            if (Parameters.get___idx(i).getValueType() == ParamValueType.Boolean)
            {
                if (((CheckBox)inputs[i]).Checked)
                {
                    Parameters.get___idx(i).getCurrentValues().Add(true);
                }
                 
            }
            else if (Parameters.get___idx(i).getValueType() == ParamValueType.Date)
            {
                Parameters.get___idx(i).getCurrentValues().Add(PIn.Date(inputs[i].Text));
            }
            else /*else if(Parameters[i].ValueType==ParamValueType.Def){
            					ComboBoxMulti comboBox=(ComboBoxMulti)inputs[i];
            					for(int j=0;j<comboBox.SelectedIndices.Count;j++){
            						retVal.Add(
            							Defs.Short[(int)Parameters[i].DefCategory]
            							[(int)comboBox.SelectedIndices[j]].DefNum);
            					}
            				}*/
            if (Parameters.get___idx(i).getValueType() == ParamValueType.Enum)
            {
                ComboBoxMulti comboBox = (ComboBoxMulti)inputs[i];
                Type eType = Type.GetType("ODR." + Parameters.get___idx(i).getEnumerationType().ToString());
                for (int j = 0;j < comboBox.getSelectedIndices().Count;j++)
                {
                    Parameters.get___idx(i).getCurrentValues().Add((int)(Enum.Parse(eType, Enum.GetNames(eType)[(int)comboBox.getSelectedIndices()[j]])));
                }
            }
            else if (Parameters.get___idx(i).getValueType() == ParamValueType.Integer)
            {
                Parameters.get___idx(i).getCurrentValues().Add(PIn.Long(inputs[i].Text));
            }
            else if (Parameters.get___idx(i).getValueType() == ParamValueType.Number)
            {
                Parameters.get___idx(i).getCurrentValues().Add(PIn.Double(inputs[i].Text));
            }
            else if (Parameters.get___idx(i).getValueType() == ParamValueType.String)
            {
                if (!StringSupport.equals(inputs[i].Text, ""))
                {
                    //the text is first stripped of any ?'s
                    Parameters.get___idx(i).getCurrentValues().Add(inputs[i].Text.Replace("?", ""));
                }
                 
            }
                  
            //Regex.Replace(inputs[i].Text,@"\?",""));
            Parameters.get___idx(i).fillOutputValue();
        }
        //MessageBox.Show(multInputItems[1].CurrentValues.Count.ToString());
        //return retVal;
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        //comboBox1.DroppedDown
        DialogResult = DialogResult.Cancel;
    }

}


