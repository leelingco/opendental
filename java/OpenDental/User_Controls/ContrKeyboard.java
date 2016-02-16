//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental.User_Controls;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ContrKeyboard  extends UserControl 
{

    public OpenDental.User_Controls.KeyboardClickEventHandler KeyClick = null;
    public ContrKeyboard() throws Exception {
        initializeComponent();
        this.SetStyle(ControlStyles.Selectable, false);
    }

    protected void onKeyClicked(String myTxt, Keys myKeyData) throws Exception {
        OpenDental.User_Controls.KeyboardClickEventArgs kArgs = new OpenDental.User_Controls.KeyboardClickEventArgs(myTxt,myKeyData);
        if (KeyClick != null)
            KeyClick.invoke(this,kArgs);
         
    }

    private void but_Click(Object sender, EventArgs e) throws Exception {
        String txt = ((OpenDental.UI.Button)sender).Text;
        OnKeyClicked(txt, Keys.None);
    }

    private void butBack_Click(Object sender, EventArgs e) throws Exception {
        OnKeyClicked("", Keys.Back);
    }

    private void butSpace_Click(Object sender, EventArgs e) throws Exception {
        OnKeyClicked(" ", Keys.None);
    }

    private void but_MouseDown(Object sender, MouseEventArgs e) throws Exception {
        this.OnMouseDown(e);
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
        this.butBack = new OpenDental.UI.Button();
        this.butSpace = new OpenDental.UI.Button();
        this.button17 = new OpenDental.UI.Button();
        this.button25 = new OpenDental.UI.Button();
        this.button26 = new OpenDental.UI.Button();
        this.button27 = new OpenDental.UI.Button();
        this.button28 = new OpenDental.UI.Button();
        this.button30 = new OpenDental.UI.Button();
        this.button33 = new OpenDental.UI.Button();
        this.button34 = new OpenDental.UI.Button();
        this.button35 = new OpenDental.UI.Button();
        this.button36 = new OpenDental.UI.Button();
        this.button29 = new OpenDental.UI.Button();
        this.button31 = new OpenDental.UI.Button();
        this.button32 = new OpenDental.UI.Button();
        this.button18 = new OpenDental.UI.Button();
        this.button19 = new OpenDental.UI.Button();
        this.button20 = new OpenDental.UI.Button();
        this.button21 = new OpenDental.UI.Button();
        this.button22 = new OpenDental.UI.Button();
        this.button23 = new OpenDental.UI.Button();
        this.button24 = new OpenDental.UI.Button();
        this.button9 = new OpenDental.UI.Button();
        this.button10 = new OpenDental.UI.Button();
        this.button11 = new OpenDental.UI.Button();
        this.button12 = new OpenDental.UI.Button();
        this.button13 = new OpenDental.UI.Button();
        this.button14 = new OpenDental.UI.Button();
        this.button15 = new OpenDental.UI.Button();
        this.button16 = new OpenDental.UI.Button();
        this.button5 = new OpenDental.UI.Button();
        this.button6 = new OpenDental.UI.Button();
        this.button7 = new OpenDental.UI.Button();
        this.button8 = new OpenDental.UI.Button();
        this.button3 = new OpenDental.UI.Button();
        this.button4 = new OpenDental.UI.Button();
        this.button2 = new OpenDental.UI.Button();
        this.button1 = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // butBack
        //
        this.butBack.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBack.setAutosize(true);
        this.butBack.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBack.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBack.Location = new System.Drawing.Point(250, 0);
        this.butBack.Name = "butBack";
        this.butBack.Size = new System.Drawing.Size(25, 25);
        this.butBack.TabIndex = 39;
        this.butBack.Text = "<";
        this.butBack.UseVisualStyleBackColor = true;
        this.butBack.Click += new System.EventHandler(this.butBack_Click);
        this.butBack.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // butSpace
        //
        this.butSpace.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSpace.setAutosize(true);
        this.butSpace.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSpace.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSpace.Location = new System.Drawing.Point(219, 75);
        this.butSpace.Name = "butSpace";
        this.butSpace.Size = new System.Drawing.Size(56, 25);
        this.butSpace.TabIndex = 38;
        this.butSpace.Text = "Space";
        this.butSpace.UseVisualStyleBackColor = true;
        this.butSpace.Click += new System.EventHandler(this.butSpace_Click);
        this.butSpace.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button17
        //
        this.button17.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button17.setAutosize(true);
        this.button17.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button17.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button17.Location = new System.Drawing.Point(225, 0);
        this.button17.Name = "button17";
        this.button17.Size = new System.Drawing.Size(25, 25);
        this.button17.TabIndex = 37;
        this.button17.Text = "0";
        this.button17.UseVisualStyleBackColor = true;
        this.button17.Click += new System.EventHandler(this.but_Click);
        this.button17.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button25
        //
        this.button25.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button25.setAutosize(true);
        this.button25.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button25.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button25.Location = new System.Drawing.Point(200, 0);
        this.button25.Name = "button25";
        this.button25.Size = new System.Drawing.Size(25, 25);
        this.button25.TabIndex = 36;
        this.button25.Text = "9";
        this.button25.UseVisualStyleBackColor = true;
        this.button25.Click += new System.EventHandler(this.but_Click);
        this.button25.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button26
        //
        this.button26.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button26.setAutosize(true);
        this.button26.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button26.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button26.Location = new System.Drawing.Point(175, 0);
        this.button26.Name = "button26";
        this.button26.Size = new System.Drawing.Size(25, 25);
        this.button26.TabIndex = 35;
        this.button26.Text = "8";
        this.button26.UseVisualStyleBackColor = true;
        this.button26.Click += new System.EventHandler(this.but_Click);
        this.button26.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button27
        //
        this.button27.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button27.setAutosize(true);
        this.button27.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button27.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button27.Location = new System.Drawing.Point(150, 0);
        this.button27.Name = "button27";
        this.button27.Size = new System.Drawing.Size(25, 25);
        this.button27.TabIndex = 34;
        this.button27.Text = "7";
        this.button27.UseVisualStyleBackColor = true;
        this.button27.Click += new System.EventHandler(this.but_Click);
        this.button27.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button28
        //
        this.button28.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button28.setAutosize(true);
        this.button28.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button28.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button28.Location = new System.Drawing.Point(125, 0);
        this.button28.Name = "button28";
        this.button28.Size = new System.Drawing.Size(25, 25);
        this.button28.TabIndex = 33;
        this.button28.Text = "6";
        this.button28.UseVisualStyleBackColor = true;
        this.button28.Click += new System.EventHandler(this.but_Click);
        this.button28.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button30
        //
        this.button30.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button30.setAutosize(true);
        this.button30.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button30.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button30.Location = new System.Drawing.Point(100, 0);
        this.button30.Name = "button30";
        this.button30.Size = new System.Drawing.Size(25, 25);
        this.button30.TabIndex = 32;
        this.button30.Text = "5";
        this.button30.UseVisualStyleBackColor = true;
        this.button30.Click += new System.EventHandler(this.but_Click);
        this.button30.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button33
        //
        this.button33.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button33.setAutosize(true);
        this.button33.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button33.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button33.Location = new System.Drawing.Point(75, 0);
        this.button33.Name = "button33";
        this.button33.Size = new System.Drawing.Size(25, 25);
        this.button33.TabIndex = 31;
        this.button33.Text = "4";
        this.button33.UseVisualStyleBackColor = true;
        this.button33.Click += new System.EventHandler(this.but_Click);
        this.button33.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button34
        //
        this.button34.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button34.setAutosize(true);
        this.button34.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button34.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button34.Location = new System.Drawing.Point(50, 0);
        this.button34.Name = "button34";
        this.button34.Size = new System.Drawing.Size(25, 25);
        this.button34.TabIndex = 30;
        this.button34.Text = "3";
        this.button34.UseVisualStyleBackColor = true;
        this.button34.Click += new System.EventHandler(this.but_Click);
        this.button34.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button35
        //
        this.button35.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button35.setAutosize(true);
        this.button35.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button35.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button35.Location = new System.Drawing.Point(25, 0);
        this.button35.Name = "button35";
        this.button35.Size = new System.Drawing.Size(25, 25);
        this.button35.TabIndex = 29;
        this.button35.Text = "2";
        this.button35.UseVisualStyleBackColor = true;
        this.button35.Click += new System.EventHandler(this.but_Click);
        this.button35.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button36
        //
        this.button36.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button36.setAutosize(true);
        this.button36.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button36.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button36.Location = new System.Drawing.Point(0, 0);
        this.button36.Name = "button36";
        this.button36.Size = new System.Drawing.Size(25, 25);
        this.button36.TabIndex = 28;
        this.button36.Text = "1";
        this.button36.UseVisualStyleBackColor = true;
        this.button36.Click += new System.EventHandler(this.but_Click);
        this.button36.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button29
        //
        this.button29.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button29.setAutosize(true);
        this.button29.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button29.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button29.Location = new System.Drawing.Point(219, 50);
        this.button29.Name = "button29";
        this.button29.Size = new System.Drawing.Size(25, 25);
        this.button29.TabIndex = 27;
        this.button29.Text = "L";
        this.button29.UseVisualStyleBackColor = true;
        this.button29.Click += new System.EventHandler(this.but_Click);
        this.button29.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button31
        //
        this.button31.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button31.setAutosize(true);
        this.button31.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button31.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button31.Location = new System.Drawing.Point(237, 25);
        this.button31.Name = "button31";
        this.button31.Size = new System.Drawing.Size(25, 25);
        this.button31.TabIndex = 25;
        this.button31.Text = "P";
        this.button31.UseVisualStyleBackColor = true;
        this.button31.Click += new System.EventHandler(this.but_Click);
        this.button31.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button32
        //
        this.button32.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button32.setAutosize(true);
        this.button32.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button32.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button32.Location = new System.Drawing.Point(212, 25);
        this.button32.Name = "button32";
        this.button32.Size = new System.Drawing.Size(25, 25);
        this.button32.TabIndex = 24;
        this.button32.Text = "O";
        this.button32.UseVisualStyleBackColor = true;
        this.button32.Click += new System.EventHandler(this.but_Click);
        this.button32.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button18
        //
        this.button18.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button18.setAutosize(true);
        this.button18.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button18.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button18.Location = new System.Drawing.Point(181, 75);
        this.button18.Name = "button18";
        this.button18.Size = new System.Drawing.Size(25, 25);
        this.button18.TabIndex = 22;
        this.button18.Text = "M";
        this.button18.UseVisualStyleBackColor = true;
        this.button18.Click += new System.EventHandler(this.but_Click);
        this.button18.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button19
        //
        this.button19.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button19.setAutosize(true);
        this.button19.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button19.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button19.Location = new System.Drawing.Point(156, 75);
        this.button19.Name = "button19";
        this.button19.Size = new System.Drawing.Size(25, 25);
        this.button19.TabIndex = 21;
        this.button19.Text = "N";
        this.button19.UseVisualStyleBackColor = true;
        this.button19.Click += new System.EventHandler(this.but_Click);
        this.button19.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button20
        //
        this.button20.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button20.setAutosize(true);
        this.button20.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button20.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button20.Location = new System.Drawing.Point(131, 75);
        this.button20.Name = "button20";
        this.button20.Size = new System.Drawing.Size(25, 25);
        this.button20.TabIndex = 20;
        this.button20.Text = "B";
        this.button20.UseVisualStyleBackColor = true;
        this.button20.Click += new System.EventHandler(this.but_Click);
        this.button20.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button21
        //
        this.button21.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button21.setAutosize(true);
        this.button21.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button21.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button21.Location = new System.Drawing.Point(106, 75);
        this.button21.Name = "button21";
        this.button21.Size = new System.Drawing.Size(25, 25);
        this.button21.TabIndex = 19;
        this.button21.Text = "V";
        this.button21.UseVisualStyleBackColor = true;
        this.button21.Click += new System.EventHandler(this.but_Click);
        this.button21.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button22
        //
        this.button22.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button22.setAutosize(true);
        this.button22.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button22.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button22.Location = new System.Drawing.Point(81, 75);
        this.button22.Name = "button22";
        this.button22.Size = new System.Drawing.Size(25, 25);
        this.button22.TabIndex = 18;
        this.button22.Text = "C";
        this.button22.UseVisualStyleBackColor = true;
        this.button22.Click += new System.EventHandler(this.but_Click);
        this.button22.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button23
        //
        this.button23.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button23.setAutosize(true);
        this.button23.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button23.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button23.Location = new System.Drawing.Point(56, 75);
        this.button23.Name = "button23";
        this.button23.Size = new System.Drawing.Size(25, 25);
        this.button23.TabIndex = 17;
        this.button23.Text = "X";
        this.button23.UseVisualStyleBackColor = true;
        this.button23.Click += new System.EventHandler(this.but_Click);
        this.button23.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button24
        //
        this.button24.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button24.setAutosize(true);
        this.button24.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button24.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button24.Location = new System.Drawing.Point(31, 75);
        this.button24.Name = "button24";
        this.button24.Size = new System.Drawing.Size(25, 25);
        this.button24.TabIndex = 16;
        this.button24.Text = "Z";
        this.button24.UseVisualStyleBackColor = true;
        this.button24.Click += new System.EventHandler(this.but_Click);
        this.button24.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button9
        //
        this.button9.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button9.setAutosize(true);
        this.button9.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button9.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button9.Location = new System.Drawing.Point(194, 50);
        this.button9.Name = "button9";
        this.button9.Size = new System.Drawing.Size(25, 25);
        this.button9.TabIndex = 15;
        this.button9.Text = "K";
        this.button9.UseVisualStyleBackColor = true;
        this.button9.Click += new System.EventHandler(this.but_Click);
        this.button9.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button10
        //
        this.button10.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button10.setAutosize(true);
        this.button10.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button10.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button10.Location = new System.Drawing.Point(169, 50);
        this.button10.Name = "button10";
        this.button10.Size = new System.Drawing.Size(25, 25);
        this.button10.TabIndex = 14;
        this.button10.Text = "J";
        this.button10.UseVisualStyleBackColor = true;
        this.button10.Click += new System.EventHandler(this.but_Click);
        this.button10.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button11
        //
        this.button11.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button11.setAutosize(true);
        this.button11.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button11.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button11.Location = new System.Drawing.Point(144, 50);
        this.button11.Name = "button11";
        this.button11.Size = new System.Drawing.Size(25, 25);
        this.button11.TabIndex = 13;
        this.button11.Text = "H";
        this.button11.UseVisualStyleBackColor = true;
        this.button11.Click += new System.EventHandler(this.but_Click);
        this.button11.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button12
        //
        this.button12.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button12.setAutosize(true);
        this.button12.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button12.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button12.Location = new System.Drawing.Point(119, 50);
        this.button12.Name = "button12";
        this.button12.Size = new System.Drawing.Size(25, 25);
        this.button12.TabIndex = 12;
        this.button12.Text = "G";
        this.button12.UseVisualStyleBackColor = true;
        this.button12.Click += new System.EventHandler(this.but_Click);
        this.button12.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button13
        //
        this.button13.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button13.setAutosize(true);
        this.button13.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button13.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button13.Location = new System.Drawing.Point(94, 50);
        this.button13.Name = "button13";
        this.button13.Size = new System.Drawing.Size(25, 25);
        this.button13.TabIndex = 11;
        this.button13.Text = "F";
        this.button13.UseVisualStyleBackColor = true;
        this.button13.Click += new System.EventHandler(this.but_Click);
        this.button13.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button14
        //
        this.button14.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button14.setAutosize(true);
        this.button14.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button14.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button14.Location = new System.Drawing.Point(69, 50);
        this.button14.Name = "button14";
        this.button14.Size = new System.Drawing.Size(25, 25);
        this.button14.TabIndex = 10;
        this.button14.Text = "D";
        this.button14.UseVisualStyleBackColor = true;
        this.button14.Click += new System.EventHandler(this.but_Click);
        this.button14.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button15
        //
        this.button15.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button15.setAutosize(true);
        this.button15.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button15.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button15.Location = new System.Drawing.Point(44, 50);
        this.button15.Name = "button15";
        this.button15.Size = new System.Drawing.Size(25, 25);
        this.button15.TabIndex = 9;
        this.button15.Text = "S";
        this.button15.UseVisualStyleBackColor = true;
        this.button15.Click += new System.EventHandler(this.but_Click);
        this.button15.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button16
        //
        this.button16.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button16.setAutosize(true);
        this.button16.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button16.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button16.Location = new System.Drawing.Point(19, 50);
        this.button16.Name = "button16";
        this.button16.Size = new System.Drawing.Size(25, 25);
        this.button16.TabIndex = 8;
        this.button16.Text = "A";
        this.button16.UseVisualStyleBackColor = true;
        this.button16.Click += new System.EventHandler(this.but_Click);
        this.button16.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button5
        //
        this.button5.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button5.setAutosize(true);
        this.button5.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button5.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button5.Location = new System.Drawing.Point(187, 25);
        this.button5.Name = "button5";
        this.button5.Size = new System.Drawing.Size(25, 25);
        this.button5.TabIndex = 7;
        this.button5.Text = "I";
        this.button5.UseVisualStyleBackColor = true;
        this.button5.Click += new System.EventHandler(this.but_Click);
        this.button5.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button6
        //
        this.button6.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button6.setAutosize(true);
        this.button6.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button6.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button6.Location = new System.Drawing.Point(162, 25);
        this.button6.Name = "button6";
        this.button6.Size = new System.Drawing.Size(25, 25);
        this.button6.TabIndex = 6;
        this.button6.Text = "U";
        this.button6.UseVisualStyleBackColor = true;
        this.button6.Click += new System.EventHandler(this.but_Click);
        this.button6.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button7
        //
        this.button7.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button7.setAutosize(true);
        this.button7.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button7.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button7.Location = new System.Drawing.Point(137, 25);
        this.button7.Name = "button7";
        this.button7.Size = new System.Drawing.Size(25, 25);
        this.button7.TabIndex = 5;
        this.button7.Text = "Y";
        this.button7.UseVisualStyleBackColor = true;
        this.button7.Click += new System.EventHandler(this.but_Click);
        this.button7.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button8
        //
        this.button8.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button8.setAutosize(true);
        this.button8.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button8.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button8.Location = new System.Drawing.Point(112, 25);
        this.button8.Name = "button8";
        this.button8.Size = new System.Drawing.Size(25, 25);
        this.button8.TabIndex = 4;
        this.button8.Text = "T";
        this.button8.UseVisualStyleBackColor = true;
        this.button8.Click += new System.EventHandler(this.but_Click);
        this.button8.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button3
        //
        this.button3.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button3.setAutosize(true);
        this.button3.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button3.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button3.Location = new System.Drawing.Point(87, 25);
        this.button3.Name = "button3";
        this.button3.Size = new System.Drawing.Size(25, 25);
        this.button3.TabIndex = 3;
        this.button3.Text = "R";
        this.button3.UseVisualStyleBackColor = true;
        this.button3.Click += new System.EventHandler(this.but_Click);
        this.button3.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button4
        //
        this.button4.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button4.setAutosize(true);
        this.button4.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button4.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button4.Location = new System.Drawing.Point(62, 25);
        this.button4.Name = "button4";
        this.button4.Size = new System.Drawing.Size(25, 25);
        this.button4.TabIndex = 2;
        this.button4.Text = "E";
        this.button4.UseVisualStyleBackColor = true;
        this.button4.Click += new System.EventHandler(this.but_Click);
        this.button4.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button2
        //
        this.button2.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button2.setAutosize(true);
        this.button2.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button2.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button2.Location = new System.Drawing.Point(37, 25);
        this.button2.Name = "button2";
        this.button2.Size = new System.Drawing.Size(25, 25);
        this.button2.TabIndex = 1;
        this.button2.Text = "W";
        this.button2.UseVisualStyleBackColor = true;
        this.button2.Click += new System.EventHandler(this.but_Click);
        this.button2.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // button1
        //
        this.button1.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button1.setAutosize(true);
        this.button1.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button1.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button1.Location = new System.Drawing.Point(12, 25);
        this.button1.Name = "button1";
        this.button1.Size = new System.Drawing.Size(25, 25);
        this.button1.TabIndex = 0;
        this.button1.Text = "Q";
        this.button1.UseVisualStyleBackColor = true;
        this.button1.Click += new System.EventHandler(this.but_Click);
        this.button1.MouseDown += new System.Windows.Forms.MouseEventHandler(this.but_MouseDown);
        //
        // ContrKeyboard
        //
        this.Controls.Add(this.butBack);
        this.Controls.Add(this.butSpace);
        this.Controls.Add(this.button17);
        this.Controls.Add(this.button25);
        this.Controls.Add(this.button26);
        this.Controls.Add(this.button27);
        this.Controls.Add(this.button28);
        this.Controls.Add(this.button30);
        this.Controls.Add(this.button33);
        this.Controls.Add(this.button34);
        this.Controls.Add(this.button35);
        this.Controls.Add(this.button36);
        this.Controls.Add(this.button29);
        this.Controls.Add(this.button31);
        this.Controls.Add(this.button32);
        this.Controls.Add(this.button18);
        this.Controls.Add(this.button19);
        this.Controls.Add(this.button20);
        this.Controls.Add(this.button21);
        this.Controls.Add(this.button22);
        this.Controls.Add(this.button23);
        this.Controls.Add(this.button24);
        this.Controls.Add(this.button9);
        this.Controls.Add(this.button10);
        this.Controls.Add(this.button11);
        this.Controls.Add(this.button12);
        this.Controls.Add(this.button13);
        this.Controls.Add(this.button14);
        this.Controls.Add(this.button15);
        this.Controls.Add(this.button16);
        this.Controls.Add(this.button5);
        this.Controls.Add(this.button6);
        this.Controls.Add(this.button7);
        this.Controls.Add(this.button8);
        this.Controls.Add(this.button3);
        this.Controls.Add(this.button4);
        this.Controls.Add(this.button2);
        this.Controls.Add(this.button1);
        this.Name = "ContrKeyboard";
        this.Size = new System.Drawing.Size(275, 100);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button button1;
    private OpenDental.UI.Button button2;
    private OpenDental.UI.Button button3;
    private OpenDental.UI.Button button4;
    private OpenDental.UI.Button button5;
    private OpenDental.UI.Button button6;
    private OpenDental.UI.Button button7;
    private OpenDental.UI.Button button8;
    private OpenDental.UI.Button button9;
    private OpenDental.UI.Button button10;
    private OpenDental.UI.Button button11;
    private OpenDental.UI.Button button12;
    private OpenDental.UI.Button button13;
    private OpenDental.UI.Button button14;
    private OpenDental.UI.Button button15;
    private OpenDental.UI.Button button16;
    private OpenDental.UI.Button button18;
    private OpenDental.UI.Button button19;
    private OpenDental.UI.Button button20;
    private OpenDental.UI.Button button21;
    private OpenDental.UI.Button button22;
    private OpenDental.UI.Button button23;
    private OpenDental.UI.Button button24;
    private OpenDental.UI.Button button29;
    private OpenDental.UI.Button button31;
    private OpenDental.UI.Button button32;
    private OpenDental.UI.Button button17;
    private OpenDental.UI.Button button25;
    private OpenDental.UI.Button button26;
    private OpenDental.UI.Button button27;
    private OpenDental.UI.Button button28;
    private OpenDental.UI.Button button30;
    private OpenDental.UI.Button button33;
    private OpenDental.UI.Button button34;
    private OpenDental.UI.Button button35;
    private OpenDental.UI.Button button36;
    private OpenDental.UI.Button butSpace;
    private OpenDental.UI.Button butBack;
}


