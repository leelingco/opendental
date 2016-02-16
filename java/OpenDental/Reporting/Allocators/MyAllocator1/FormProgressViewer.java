//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental.Reporting.Allocators.MyAllocator1;

import CS2JNet.System.StringSupport;


/*
This Program is free software; you can redistribute it and/or modify it under the terms of the
GNU Db Public License as published by the Free Software Foundation; either version 2 of the License,
or (at your option) any later version.
This program is distributed in the hope that it will be useful, but without any warranty. See the GNU Db Public License
for more details, available at http://www.opensource.org/licenses/gpl-license.php
Any changes to this program must follow the guidelines of the GPL license if a modified version is to be
redistributed.
*//**
* This Form is a simple encapsulation of the BacgroundWorker with a progressbar control.
* 
* Several buttons are available for users to control the process.
* 
* Basic plan is for the Programmer to design a method that fits
* the DoWorkEventHandler signature --->void bw_DoWork(object sender, DoWorkEventArgs e)
* 
* Progress Reporting is enabled
* Support for canceling is enabled.
* 
* Remeber if DoWorkEventArgs e.Cancled is set to true (by you in the bw_DoWork) then
* you cannot get the e.Result and thus the _FinalResult object will be null.
* 
* Steps:
* SET_WORKER_METHOD first
* START_WORK()
* 
* Other Features
* RUN_ONLY_ONCE		if set to false a Start button pops up and you can re run the method assigned. Default is true
* FINAL_RESULT		the value set to e.Result in your DoWork method
* CANCLED_BY_USER		true if user cancled activity
* MESSAGE_LAST		Last Progress Message assigned.  Will be overwritten by bw_RunWorkerCompleted code
* MESSAGES_ALL		A string containing all the Progress Messages sent
* CONTINOUS_PROGRESS	The progress bar will just cycle while work is done.  Does not affect messages.
* Author:	Daniel W. Krueger
*/
public class FormProgressViewer  extends Form 
{

    private BackgroundWorker bw = new BackgroundWorker();
    private DoWorkEventHandler _AsyncMethod = null;
    //void bw_DoWork(object sender, DoWorkEventArgs e)
    private String _Message = "";
    // update these instead of form variables.  Then you can control when they update.
    private String _Message_Cummulative = "";
    private int _ProgValue = 10;
    private boolean _RunOnceOnly = true;
    //default value to indicate that you can only run the bw.dowork event only once
    private boolean _HasRunOnce = false;
    private Object _FinalResult = null;
    private boolean _UserCanceled = false;
    private boolean _Continuos_progress = false;
    //private bool _TimerOn = false;
    public FormProgressViewer() throws Exception {
        initializeComponent();
    }

    /**
    * Design your method to have DoWorkEventHandler signature
    * void bw_DoWork(object sender, DoWorkEventArgs e)
    * 
    * You can cast sender as backgroundworker.
    * The backgroundworker supports canceling and reporting progress.
    * 
    * Public properties expose parameters after job is finished.
    * Set your DoWorkEventArgs e.Result to the final result of your
    * project then when WorkerRun is complete it will become available
    * in the _FinalResult Object.  Final Result's ToString will display in
    * the Message area.  If you do not want this create an object that
    * will overide the Result object's ToString() method.
    */
    public void setSET_WORKER_METHOD(DoWorkEventHandler value) throws Exception {
        bw = new BackgroundWorker();
        bw.ProgressChanged += new ProgressChangedEventHandler(bw_ProgressChanged);
        bw.RunWorkerCompleted += new RunWorkerCompletedEventHandler(bw_RunWorkerCompleted);
        bw.WorkerReportsProgress = true;
        bw.WorkerSupportsCancellation = true;
        bw.DoWork += value;
        _AsyncMethod = value;
        // just so I can check to see if it exists
        setButtonState();
    }

    public void sTART_WORK() throws Exception {
        if (_AsyncMethod == null)
        {
            MessageBox.Show(this, "No background task assigned.  Task Complete!", "No Task!");
            return ;
        }
         
        this._UserCanceled = false;
        this._FinalResult = null;
        this._Message_Cummulative += this._Message = "Process Started\n";
        bw.RunWorkerAsync();
        _HasRunOnce = true;
        setButtonState();
        this.timer1.Enabled = true;
    }

    /**
    * Gets or Sets whether to allow the user to run the even more than
    * one by pressing start after the DoWork event is complete.
    * Default is true.  Set to False to allow pressing start again.
    */
    public boolean getRUN_ONLY_ONCE() throws Exception {
        return this._RunOnceOnly;
    }

    public void setRUN_ONLY_ONCE(boolean value) throws Exception {
        this._RunOnceOnly = value;
    }

    /**
    * Reference should be okay because it is only set in the bw_RunWorkerCompleted and
    * is only used by setting to null with the START_WORK()
    */
    public Object getFINAL_RESULT() throws Exception {
        return this._FinalResult;
    }

    public boolean getCANCLED_BY_USER() throws Exception {
        return this._UserCanceled;
    }

    public String getMESSAGE_LAST() throws Exception {
        return this._Message;
    }

    public String getMESSAGES_ALL() throws Exception {
        return this._Message_Cummulative;
    }

    /**
    * Indicates that the Progress Bar does not update its value with the
    * bw_ProgressChanged handler.  Instead it increments whenever the timer
    * fires up.  Default is false.  Cannot change the value when
    * backgroundworker is running.
    */
    public boolean getCONTINOUS_PROGRESS() throws Exception {
        return this._Continuos_progress;
    }

    public void setCONTINOUS_PROGRESS(boolean value) throws Exception {
        if (bw != null)
        {
            if (!bw.IsBusy)
                this._Continuos_progress = value;
             
        }
        else
            this._Continuos_progress = value; 
    }

    private void setButtonState() throws Exception {
        if (bw != null)
        {
            if (bw.IsBusy)
            {
                this.butStart.Visible = false;
                this.butOK.Visible = true;
                this.butOK.Enabled = false;
                this.butCancel.Visible = true;
                this.butCancel.Enabled = true;
            }
            else
            {
                if (!this._RunOnceOnly)
                    this.butStart.Visible = true;
                else if (!this._HasRunOnce)
                    this.butStart.Visible = true;
                else
                    this.butStart.Visible = false;  
                this.butOK.Visible = true;
                this.butOK.Enabled = true;
                this.butCancel.Visible = true;
                this.butCancel.Enabled = false;
            } 
        }
        else
        {
            this.butStart.Visible = false;
            this.butOK.Visible = true;
            this.butOK.Enabled = true;
            this.butCancel.Visible = true;
            this.butCancel.Enabled = false;
        } 
    }

    private void bw_RunWorkerCompleted(Object sender, RunWorkerCompletedEventArgs e) throws Exception {
        if (e.Cancelled)
        {
            // if you set e.cancled to true in your doWork method you cannot get any value out of Result becuase it will throw an exception. So either don't set e.cancled or you need to have your own RunWorkerCompletedEventArgs object if you want to control this.
            _Message = "User Cancelled";
            _Message_Cummulative += "User Cancelled\n";
        }
        else if (e.Error != null)
        {
            _Message = "Error: " + e.Error.Message + "\n";
            _Message_Cummulative += _Message;
        }
        else if (e.Result instanceof int)
        {
            _Message = "Result is: " + e.Result.ToString() + "\n";
            this._FinalResult = e.Result;
        }
        else
        {
            //if ((int)e.Result >= 0 || (int)e.Result <= 100)
            //    _ProgValue = (int)e.Result;
            if (e.Result != null)
            {
                _Message = "Task Finished\n" + e.Result.ToString() + "\n";
                _Message_Cummulative += _Message;
            }
             
            this._FinalResult = e.Result;
        }   
        //this.progressBar1.Value = (int)e.Result;
        setButtonState();
    }

    private void bw_ProgressChanged(Object sender, ProgressChangedEventArgs e) throws Exception {
        if (!this.timer1.Enabled)
        {
            this.timer1.Enabled = true;
            this.timer1.Start();
        }
         
        if (e.UserState instanceof String)
        {
            _Message = e.UserState.ToString();
            _Message_Cummulative += e.UserState.ToString() + "\n";
        }
         
        if (!_Continuos_progress)
            if (e.ProgressPercentage >= 0 || e.ProgressPercentage <= 100)
                this._ProgValue = e.ProgressPercentage;
             
         
    }

    // user timer to update Form
    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        if (bw != null)
            bw.CancelAsync();
         
        setButtonState();
        this._UserCanceled = true;
    }

    private void butViewLog_Click(Object sender, EventArgs e) throws Exception {
        this.richTextBox1.Visible = !this.richTextBox1.Visible;
        this.richTextBox1.Text = _Message_Cummulative;
        this.butViewLog.Text = (this.richTextBox1.Visible ? "Hide Log" : "View Log");
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        this.DialogResult = DialogResult.OK;
    }

    private void butStart_Click(Object sender, EventArgs e) throws Exception {
        this.sTART_WORK();
    }

    private void timer1_Tick(Object sender, EventArgs e) throws Exception {
        this.lblMessages.Text = _Message;
        if (!StringSupport.equals(this.richTextBox1.Text, _Message_Cummulative))
            this.richTextBox1.Text = _Message_Cummulative;
         
        if (bw != null)
            if (!bw.IsBusy)
                this.timer1.Enabled = false;
             
         
        if (_Continuos_progress)
        {
            int newValue = this.progressBar1.Value + 1;
            this.progressBar1.Value = (newValue > 100 ? 0 : newValue);
        }
        else
            this.progressBar1.Value = _ProgValue; 
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
        this.components = new System.ComponentModel.Container();
        this.butOK = new System.Windows.Forms.Button();
        this.lblMessages = new System.Windows.Forms.Label();
        this.richTextBox1 = new System.Windows.Forms.RichTextBox();
        this.butViewLog = new System.Windows.Forms.Button();
        this.progressBar1 = new System.Windows.Forms.ProgressBar();
        this.butCancel = new System.Windows.Forms.Button();
        this.butStart = new System.Windows.Forms.Button();
        this.timer1 = new System.Windows.Forms.Timer(this.components);
        this.backgroundWorker1 = new System.ComponentModel.BackgroundWorker();
        this.panel1 = new System.Windows.Forms.Panel();
        this.panel3 = new System.Windows.Forms.Panel();
        this.panel1.SuspendLayout();
        this.panel3.SuspendLayout();
        this.SuspendLayout();
        //
        // butOK
        //
        this.butOK.Enabled = false;
        this.butOK.Location = new System.Drawing.Point(3, 91);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 23);
        this.butOK.TabIndex = 0;
        this.butOK.Text = "OK";
        this.butOK.UseVisualStyleBackColor = true;
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // lblMessages
        //
        this.lblMessages.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
        this.lblMessages.Dock = System.Windows.Forms.DockStyle.Fill;
        this.lblMessages.Location = new System.Drawing.Point(0, 0);
        this.lblMessages.Name = "lblMessages";
        this.lblMessages.Size = new System.Drawing.Size(409, 122);
        this.lblMessages.TabIndex = 1;
        this.lblMessages.Text = "Messages";
        //
        // richTextBox1
        //
        this.richTextBox1.Dock = System.Windows.Forms.DockStyle.Fill;
        this.richTextBox1.Location = new System.Drawing.Point(0, 0);
        this.richTextBox1.Name = "richTextBox1";
        this.richTextBox1.ReadOnly = true;
        this.richTextBox1.Size = new System.Drawing.Size(409, 99);
        this.richTextBox1.TabIndex = 2;
        this.richTextBox1.Text = "";
        this.richTextBox1.Visible = false;
        //
        // butViewLog
        //
        this.butViewLog.Location = new System.Drawing.Point(3, 3);
        this.butViewLog.Name = "butViewLog";
        this.butViewLog.Size = new System.Drawing.Size(75, 23);
        this.butViewLog.TabIndex = 4;
        this.butViewLog.Text = "View Log";
        this.butViewLog.UseVisualStyleBackColor = true;
        this.butViewLog.Click += new System.EventHandler(this.butViewLog_Click);
        //
        // progressBar1
        //
        this.progressBar1.Dock = System.Windows.Forms.DockStyle.Bottom;
        this.progressBar1.Location = new System.Drawing.Point(0, 99);
        this.progressBar1.Name = "progressBar1";
        this.progressBar1.Size = new System.Drawing.Size(409, 23);
        this.progressBar1.TabIndex = 4;
        //
        // butCancel
        //
        this.butCancel.Enabled = false;
        this.butCancel.Location = new System.Drawing.Point(84, 91);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 23);
        this.butCancel.TabIndex = 1;
        this.butCancel.Text = "Cancel";
        this.butCancel.UseVisualStyleBackColor = true;
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butStart
        //
        this.butStart.Location = new System.Drawing.Point(3, 66);
        this.butStart.Name = "butStart";
        this.butStart.Size = new System.Drawing.Size(75, 23);
        this.butStart.TabIndex = 3;
        this.butStart.Text = "Start";
        this.butStart.UseVisualStyleBackColor = true;
        this.butStart.Click += new System.EventHandler(this.butStart_Click);
        //
        // timer1
        //
        this.timer1.Tick += new System.EventHandler(this.timer1_Tick);
        //
        // backgroundWorker1
        //
        this.backgroundWorker1.WorkerReportsProgress = true;
        this.backgroundWorker1.WorkerSupportsCancellation = true;
        this.backgroundWorker1.ProgressChanged += new System.ComponentModel.ProgressChangedEventHandler(this.bw_ProgressChanged);
        //
        // panel1
        //
        this.panel1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.panel1.Controls.Add(this.richTextBox1);
        this.panel1.Controls.Add(this.progressBar1);
        this.panel1.Controls.Add(this.lblMessages);
        this.panel1.Location = new System.Drawing.Point(12, 12);
        this.panel1.Name = "panel1";
        this.panel1.Size = new System.Drawing.Size(409, 122);
        this.panel1.TabIndex = 6;
        //
        // panel3
        //
        this.panel3.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Right)));
        this.panel3.Controls.Add(this.butViewLog);
        this.panel3.Controls.Add(this.butOK);
        this.panel3.Controls.Add(this.butCancel);
        this.panel3.Controls.Add(this.butStart);
        this.panel3.Location = new System.Drawing.Point(425, 12);
        this.panel3.Name = "panel3";
        this.panel3.Size = new System.Drawing.Size(166, 122);
        this.panel3.TabIndex = 8;
        //
        // FormProgressViewer
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(596, 144);
        this.Controls.Add(this.panel3);
        this.Controls.Add(this.panel1);
        this.MaximumSize = new System.Drawing.Size(1000, 800);
        this.MinimumSize = new System.Drawing.Size(525, 166);
        this.Name = "FormProgressViewer";
        this.Text = "FormProgressViewer";
        this.panel1.ResumeLayout(false);
        this.panel3.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.Button butOK = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label lblMessages = new System.Windows.Forms.Label();
    private System.Windows.Forms.RichTextBox richTextBox1 = new System.Windows.Forms.RichTextBox();
    private System.Windows.Forms.Button butViewLog = new System.Windows.Forms.Button();
    private System.Windows.Forms.ProgressBar progressBar1 = new System.Windows.Forms.ProgressBar();
    private System.Windows.Forms.Button butCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butStart = new System.Windows.Forms.Button();
    private System.Windows.Forms.Timer timer1 = new System.Windows.Forms.Timer();
    public System.ComponentModel.BackgroundWorker backgroundWorker1 = new System.ComponentModel.BackgroundWorker();
    private System.Windows.Forms.Panel panel1 = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Panel panel3 = new System.Windows.Forms.Panel();
}


