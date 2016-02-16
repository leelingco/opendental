//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CodeBase.MsgBoxCopyPaste;
import CS2JNet.System.StringSupport;
import OpenDental.FormCDSIntervention;
import OpenDental.FormEhrLabOrderEdit2014;
import OpenDental.FormEhrLabOrderImport;
import OpenDental.PIn;
import OpenDental.UI.GridSortingStrategy;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.CDSPermissions;
import OpenDentBusiness.EhrLab;
import OpenDentBusiness.EhrLabs;
import OpenDentBusiness.EhrMeasureEvent;
import OpenDentBusiness.EhrMeasureEvents;
import OpenDentBusiness.EhrMeasureEventType;
import OpenDentBusiness.EhrTriggers;
import OpenDentBusiness.Loinc;
import OpenDentBusiness.Loincs;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Security;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEhrLabOrders  extends Form 
{

    public List<EhrLab> ListEhrLabs = new List<EhrLab>();
    public Patient PatCur;
    public FormEhrLabOrders() throws Exception {
        initializeComponent();
    }

    private void formEhrLabOrders_Load(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn("Date Time", 80, HorizontalAlignment.Center);
        //Formatted yyyyMMdd
        col.setSortingStrategy(GridSortingStrategy.DateParse);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Placer Order Number", 130, HorizontalAlignment.Center);
        //Should be PK but might not be. Instead use Placer Order Num.
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Filler Order Number", 130, HorizontalAlignment.Center);
        //Should be PK but might not be. Instead use Placer Order Num.
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Test Performed",430);
        //Should be PK but might not be. Instead use Placer Order Num.
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Results In", 80, HorizontalAlignment.Center);
        //Or date of latest result? or both?
        gridMain.getColumns().add(col);
        ListEhrLabs = EhrLabs.getAllForPat(PatCur.PatNum);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ListEhrLabs.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            String dateSt = ListEhrLabs[i].ResultDateTime.PadRight(8, '0').Substring(0, 8);
            //stored in DB as yyyyMMddhhmmss-zzzz
            DateTime dateT = PIn.Date(dateSt.Substring(4, 2) + "/" + dateSt.Substring(6, 2) + "/" + dateSt.Substring(0, 4));
            row.getCells().Add(dateT.ToShortDateString());
            //date only
            row.getCells().Add(ListEhrLabs[i].PlacerOrderNum);
            row.getCells().Add(ListEhrLabs[i].FillerOrderNum);
            row.getCells().Add(ListEhrLabs[i].UsiText);
            row.getCells().Add(ListEhrLabs[i].ListEhrLabResults.Count.ToString());
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void butImport_Click(Object sender, EventArgs e) throws Exception {
        MsgBoxCopyPaste MBCP = new MsgBoxCopyPaste("Paste HL7 Lab Message Text Here.");
        MBCP.textMain.SelectAll();
        MBCP.ShowDialog();
        if (MBCP.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        List<EhrLab> listEhrLabs = new List<EhrLab>();
        try
        {
            listEhrLabs = EhrLabs.ProcessHl7Message(MBCP.textMain.Text);
            //Not a typical use of the msg box copy paste
            if (listEhrLabs[0].PatNum == PatCur.PatNum)
            {
            }
            else
            {
                //only need to check the first lab.
                //nothing to do here. Imported lab matches the current patient.
                //does not match current patient, redirect to import form which displays patient information and is build for importing.
                FormEhrLabOrderImport FormLOI = new FormEhrLabOrderImport();
                FormLOI.PatCur = PatCur;
                FormLOI.Hl7LabMessage = MBCP.textMain.Text;
                FormLOI.ShowDialog();
                fillGrid();
                return ;
            } 
        }
        catch (Exception Ex)
        {
            //else if(listEhrLabs[0].PatNum==0) {
            //	if(MessageBox.Show("Lab patient does not match current patient. Lab patient name is "
            //		+MBCP.textMain.Text.Split(new string[] { "\r\n" },StringSplitOptions.RemoveEmptyEntries)[1].Split('|')[5].Split('~')[0].Split('^')[1]+" "//first name
            //		+MBCP.textMain.Text.Split(new string[] { "\r\n" },StringSplitOptions.RemoveEmptyEntries)[1].Split('|')[5].Split('~')[0].Split('^')[1]+" "//last name
            //		+"\r\nWould you like to import lab for the current patient?","",MessageBoxButtons.OKCancel)!=DialogResult.OK)
            //	{
            //		return;
            //	}
            //	//User agreed to import current lab(s) for current patient.
            //	for(int i=0;i<listEhrLabs.Count;i++) {
            //		listEhrLabs[i].PatNum=PatCur.PatNum;
            //		//TODO: Import external OIDs and PatIDs so that we can identify this patient next time.
            //	}
            //}
            //else {//Patnum is already associated with another patient.
            //	MessageBox.Show("This lab contains patient information for a different patient. Lab patient name is "
            //		+MBCP.textMain.Text.Split(new string[] { "\r\n" },StringSplitOptions.RemoveEmptyEntries)[1].Split('|')[5].Split('~')[0].Split('^')[1]+" "//first name
            //		+MBCP.textMain.Text.Split(new string[] { "\r\n" },StringSplitOptions.RemoveEmptyEntries)[1].Split('|')[5].Split('~')[0].Split('^')[1]);
            //	return;
            //}
            MessageBox.Show(this, "Unable to import lab.\r\n" + Ex.Message);
            return ;
        }

        for (int i = 0;i < listEhrLabs.Count;i++)
        {
            EhrLab tempLab = null;
            //lab from DB if it exists.
            tempLab = EhrLabs.GetByGUID(listEhrLabs[i].PlacerOrderUniversalID, listEhrLabs[i].PlacerOrderNum);
            if (tempLab == null)
            {
                tempLab = EhrLabs.GetByGUID(listEhrLabs[i].FillerOrderUniversalID, listEhrLabs[i].FillerOrderNum);
            }
             
            if (tempLab != null)
            {
            }
             
            //Date validation.
            //if(tempLab.ResultDateTime.CompareTo(listEhrLabs[i].ResultDateTime)<=0) {//string compare dates will return 1+ if tempLab Date is greater.
            //	MsgBox.Show(this,"This lab already exists in the database and has a more recent timestamp.");
            //	continue;
            //}
            //TODO: The code above works, but ignores more recent lab results. Although the lab order my be unchanged there may be updated lab results.
            //It would be better to check for updated results, unfortunately results have no unique identifiers.
            if (Security.getCurUser().ProvNum != 0 && !StringSupport.equals(Providers.getProv(Security.getCurUser().ProvNum).EhrKey, ""))
            {
                //The user who is currently logged in is a provider and has a valid EHR key.
                ListEhrLabs[i].IsCpoe = true;
            }
             
            listEhrLabs[i] = EhrLabs.SaveToDB(listEhrLabs[i]);
            for (int j = 0;j < listEhrLabs[i].ListEhrLabResults.Count;j++)
            {
                //SAVE
                //EHR TRIGGER
                if (CDSPermissions.getForUser(Security.getCurUser().UserNum).ShowCDS && CDSPermissions.getForUser(Security.getCurUser().UserNum).LabTestCDS)
                {
                    FormCDSIntervention FormCDSI = new FormCDSIntervention();
                    FormCDSI.ListCDSI = EhrTriggers.triggerMatch(listEhrLabs[i].ListEhrLabResults[j],PatCur);
                    FormCDSI.showIfRequired(false);
                }
                 
            }
        }
        fillGrid();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        FormEhrLabOrderEdit2014 FormLOE = new FormEhrLabOrderEdit2014();
        FormLOE.EhrLabCur = new EhrLab();
        FormLOE.EhrLabCur.PatNum = PatCur.PatNum;
        FormLOE.IsNew = true;
        FormLOE.ShowDialog();
        if (FormLOE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        EhrMeasureEvent newMeasureEvent = new EhrMeasureEvent();
        newMeasureEvent.DateTEvent = DateTime.Now;
        newMeasureEvent.EventType = EhrMeasureEventType.CPOE_LabOrdered;
        //default
        Loinc loinc = Loincs.getByCode(FormLOE.EhrLabCur.UsiID);
        if (loinc != null && StringSupport.equals(loinc.ClassType, "RAD"))
        {
            //short circuit logic
            newMeasureEvent.EventType = EhrMeasureEventType.CPOE_RadOrdered;
        }
         
        newMeasureEvent.PatNum = FormLOE.EhrLabCur.PatNum;
        newMeasureEvent.MoreInfo = "";
        newMeasureEvent.FKey = FormLOE.EhrLabCur.EhrLabNum;
        EhrMeasureEvents.insert(newMeasureEvent);
        EhrLabs.saveToDB(FormLOE.EhrLabCur);
        for (int i = 0;i < FormLOE.EhrLabCur.getListEhrLabResults().Count;i++)
        {
            if (CDSPermissions.getForUser(Security.getCurUser().UserNum).ShowCDS && CDSPermissions.getForUser(Security.getCurUser().UserNum).LabTestCDS)
            {
                FormCDSIntervention FormCDSI = new FormCDSIntervention();
                FormCDSI.ListCDSI = EhrTriggers.triggerMatch(FormLOE.EhrLabCur.getListEhrLabResults()[i],PatCur);
                FormCDSI.showIfRequired(false);
            }
             
        }
        fillGrid();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormEhrLabOrderEdit2014 FormLOE = new FormEhrLabOrderEdit2014();
        FormLOE.EhrLabCur = ListEhrLabs[e.getRow()];
        FormLOE.ShowDialog();
        if (FormLOE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        fillGrid();
    }

    //TODO:maybe add more code here for when we come back from form... In case we delete a lab from the form.
    private void butClose_Click(Object sender, EventArgs e) throws Exception {
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
        this.butAdd = new System.Windows.Forms.Button();
        this.butImport = new System.Windows.Forms.Button();
        this.butClose = new System.Windows.Forms.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.SuspendLayout();
        //
        // butAdd
        //
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butAdd.Location = new System.Drawing.Point(887, 41);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 23);
        this.butAdd.TabIndex = 8;
        this.butAdd.Text = "Add";
        this.butAdd.UseVisualStyleBackColor = true;
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butImport
        //
        this.butImport.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butImport.Location = new System.Drawing.Point(887, 12);
        this.butImport.Name = "butImport";
        this.butImport.Size = new System.Drawing.Size(75, 23);
        this.butImport.TabIndex = 7;
        this.butImport.Text = "Import";
        this.butImport.UseVisualStyleBackColor = true;
        this.butImport.Click += new System.EventHandler(this.butImport_Click);
        //
        // butClose
        //
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.Location = new System.Drawing.Point(887, 367);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 23);
        this.butClose.TabIndex = 9;
        this.butClose.Text = "Close";
        this.butClose.UseVisualStyleBackColor = true;
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(862, 378);
        this.gridMain.TabIndex = 5;
        this.gridMain.setTitle("Laboratory Orders");
        this.gridMain.setTranslationName(null);
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
        // FormEhrLabOrders
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(974, 402);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butImport);
        this.Controls.Add(this.gridMain);
        this.Name = "FormEhrLabOrders";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Lab Orders";
        this.Load += new System.EventHandler(this.FormEhrLabOrders_Load);
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.Button butAdd = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butImport = new System.Windows.Forms.Button();
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.Button butClose = new System.Windows.Forms.Button();
}


