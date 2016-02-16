//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:52 PM
//

package OpenDental;

import OpenDental.FormTasks;
import OpenDental.GotoModule;
import OpenDental.MsgBox;
import OpenDental.UserControlTasks;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.Appointments;
import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.TaskObjectType;

/**
* 
*/
public class FormTasks  extends System.Windows.Forms.Form 
{
    //private System.ComponentModel.IContainer components;
    /**
    * //After closing, if this is not zero, then it will jump to the object specified in GotoKeyNum.
    */
    //public TaskObjectType GotoType;
    private UserControlTasks userControlTasks1;
    private IContainer components = new IContainer();
    /**
    * //After closing, if this is not zero, then it will jump to the specified patient.
    */
    //public long GotoKeyNum;
    private boolean IsTriage = new boolean();
    /**
    * 
    */
    public FormTasks() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
    }

    //Lan.F(this);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormTasks.class);
        this.userControlTasks1 = new OpenDental.UserControlTasks();
        this.SuspendLayout();
        //
        // userControlTasks1
        //
        this.userControlTasks1.Dock = System.Windows.Forms.DockStyle.Fill;
        this.userControlTasks1.Location = new System.Drawing.Point(0, 0);
        this.userControlTasks1.Name = "userControlTasks1";
        this.userControlTasks1.Size = new System.Drawing.Size(885, 671);
        this.userControlTasks1.TabIndex = 0;
        this.userControlTasks1.GoToChanged += new System.EventHandler(this.userControlTasks1_GoToChanged);
        //
        // FormTasks
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(885, 671);
        this.Controls.Add(this.userControlTasks1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormTasks";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Tasks";
        this.Load += new System.EventHandler(this.FormTasks_Load);
        this.ResumeLayout(false);
    }

    private void formTasks_Load(Object sender, EventArgs e) throws Exception {
        userControlTasks1.initializeOnStartup();
    }

    private void userControlTasks1_GoToChanged(Object sender, EventArgs e) throws Exception {
        TaskObjectType gotoType = userControlTasks1.GotoType;
        long gotoKeyNum = userControlTasks1.GotoKeyNum;
        if (gotoType == TaskObjectType.Patient)
        {
            if (gotoKeyNum != 0)
            {
                Patient pat = Patients.getPat(gotoKeyNum);
                //OnPatientSelected(pat);
                if (IsTriage)
                {
                    GotoModule.gotoChart(pat.PatNum);
                }
                else
                {
                    GotoModule.gotoAccount(pat.PatNum);
                } 
            }
             
        }
         
        if (gotoType == TaskObjectType.Appointment)
        {
            if (gotoKeyNum != 0)
            {
                Appointment apt = Appointments.getOneApt(gotoKeyNum);
                if (apt == null)
                {
                    MsgBox.show(this,"Appointment has been deleted, so it's not available.");
                    return ;
                }
                 
                //this could be a little better, because window has closed, but they will learn not to push that button.
                DateTime dateSelected = DateTime.MinValue;
                if (apt.AptStatus == ApptStatus.Planned || apt.AptStatus == ApptStatus.UnschedList)
                {
                    //I did not add feature to put planned or unsched apt on pinboard.
                    MsgBox.show(this,"Cannot navigate to appointment.  Use the Other Appointments button.");
                }
                else
                {
                    //return;
                    dateSelected = apt.AptDateTime;
                } 
                Patient pat = Patients.getPat(apt.PatNum);
                //OnPatientSelected(pat);
                GotoModule.gotoAppointment(dateSelected,apt.AptNum);
            }
             
        }
         
    }

    //DialogResult=DialogResult.OK;
    /**
    * Used by OD HQ.
    */
    public void showTriage() throws Exception {
        userControlTasks1.fillGridWithTriageList();
        IsTriage = true;
    }

    /**
    * Simply tells the user task control to refresh the currently showing task list.
    */
    public void refreshUserControlTasks() throws Exception {
        if (userControlTasks1 != null && !userControlTasks1.IsDisposed)
        {
            userControlTasks1.refreshTasks();
        }
         
    }

}


/* private void timer1_Tick(object sender,EventArgs e) {
				if(Security.CurUser!=null) {//Possible if OD auto logged a user off and they left the task window open in the background.
					userControlTasks1.RefreshTasks();
				}
				//this quick and dirty refresh is not as intelligent as the one used when tasks are docked.
				//Sound notification of new task is controlled from main form completely
				//independently of this visual refresh.
			}
		}
		*/