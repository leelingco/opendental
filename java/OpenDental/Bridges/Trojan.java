//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:29 PM
//

package OpenDental.Bridges;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormNotePick;
import OpenDental.FormPrintReport;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDentBusiness.Benefit;
import OpenDentBusiness.BenefitCoverageLevel;
import OpenDentBusiness.BenefitTimePeriod;
import OpenDentBusiness.Carrier;
import OpenDentBusiness.Carriers;
import OpenDentBusiness.CovCatC;
import OpenDentBusiness.CovCats;
import OpenDentBusiness.EbenefitCategory;
import OpenDentBusiness.InsBenefitType;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.Programs;
import OpenDentBusiness.TrojanObject;
import OpenDentBusiness.TrojanQueries;

public class Trojan   
{
    private static Collection<String[]> deletePatientRecords = new Collection<String[]>();
    private static Collection<String[]> deleteTrojanRecords = new Collection<String[]>();
    private static DataTable pendingDeletionTable = new DataTable();
    private static DataTable pendingDeletionTableTrojan = new DataTable();
    public static void startupCheck() throws Exception {
        //Skip all if not using Trojan.
        Program ProgramCur = Programs.getCur(ProgramName.Trojan);
        if (!ProgramCur.Enabled)
        {
            return ;
        }
         
        //Ensure that Trojan has a sane install.
        RegistryKey regKey = Registry.LocalMachine.OpenSubKey("Software\\TROJAN BENEFIT SERVICE");
        String file = "";
        if (regKey == null || regKey.GetValue("INSTALLDIR") == null)
        {
            return ;
        }
         
        //jsparks: The below is wrong.  The user should create a registry key manually.
        //The old trojan registry key is missing. Try to locate the new Trojan registry key.
        //regKey=Registry.LocalMachine.OpenSubKey("Software\\Trojan Eligibility");
        //if(regKey==null||regKey.GetValue("INSTALLDIR")==null) {//Unix OS will exit here.
        //	return;
        //}
        //Process DELETEDPLANS.TXT for recently deleted insurance plans.
        file = regKey.GetValue("INSTALLDIR").ToString() + "\\DELETEDPLANS.TXT";
        //C:\ETW\DELETEDPLANS.TXT
        processDeletedPlans(file);
        //Process ALLPLANS.TXT for new insurance plan information.
        file = regKey.GetValue("INSTALLDIR").ToString() + "\\ALLPLANS.TXT";
        //C:\ETW\ALLPLANS.TXT
        processTrojanPlanUpdates(file);
    }

    /**
    * Process the deletion of existing insurance plans.
    */
    private static void processDeletedPlans(String file) throws Exception {
        if (!File.Exists(file))
        {
            return ;
        }
         
        //Nothing to process.
        String deleteplantext = File.ReadAllText(file);
        if (StringSupport.equals(deleteplantext, ""))
        {
            return ;
        }
         
        //Nothing to process. Don't delete the file in-case Trojan is filling the file right now.
        deletePatientRecords = new Collection<String[]>();
        deleteTrojanRecords = new Collection<String[]>();
        String[] trojanplans = deleteplantext.Split(new String[]{ "\n" }, StringSplitOptions.RemoveEmptyEntries);
        Collection<String[]> records = new Collection<String[]>();
        for (int i = 0;i < trojanplans.Length;i++)
        {
            String[] record = trojanplans[i].Split(new String[]{ "\t" }, StringSplitOptions.None);
            for (int j = 0;j < record.Length;j++)
            {
                //Remove any white space around the field and remove the surrounding quotes.
                record[j] = record[j].Trim().Substring(1);
                record[j] = record[j].Substring(0, record[j].Length - 1);
            }
            records.Add(record);
            String whoToContact = record[3].ToUpper();
            if (StringSupport.equals(whoToContact, "T"))
            {
                deleteTrojanRecords.Add(record);
            }
            else
            {
                //whoToContact="P"
                deletePatientRecords.Add(record);
            } 
        }
        if (deletePatientRecords.Count > 0)
        {
            pendingDeletionTable = TrojanQueries.getPendingDeletionTable(deletePatientRecords);
            if (pendingDeletionTable.Rows.Count > 0)
            {
                FormPrintReport fpr = new FormPrintReport();
                fpr.Text = "Trojan Plans Pending Deletion: Contact Patients";
                fpr.setScrollAmount(10);
                fpr.printGenerator = new PrintCallback() 
                  { 
                    public System.Void invoke(FormPrintReport fpr) throws Exception {
                        showPendingDeletionReportForPatients(fpr);
                    }

                    public List<PrintCallback> getInvocationList() throws Exception {
                        List<PrintCallback> ret = new ArrayList<PrintCallback>();
                        ret.add(this);
                        return ret;
                    }
                
                  };
                fpr.usePageNumbers(new Font(FontFamily.GenericMonospace, 8));
                fpr.setMinimumTimesToPrint(1);
                fpr.ShowDialog();
            }
             
        }
         
        if (deleteTrojanRecords.Count > 0)
        {
            pendingDeletionTableTrojan = TrojanQueries.getPendingDeletionTableTrojan(deleteTrojanRecords);
            if (pendingDeletionTableTrojan.Rows.Count > 0)
            {
                FormPrintReport fpr = new FormPrintReport();
                fpr.Text = "Trojan Plans Pending Deletion: Contact Trojan";
                fpr.setScrollAmount(10);
                fpr.printGenerator = new PrintCallback() 
                  { 
                    public System.Void invoke(FormPrintReport fpr) throws Exception {
                        showPendingDeletionReportForTrojan(fpr);
                    }

                    public List<PrintCallback> getInvocationList() throws Exception {
                        List<PrintCallback> ret = new ArrayList<PrintCallback>();
                        ret.add(this);
                        return ret;
                    }
                
                  };
                fpr.usePageNumbers(new Font(FontFamily.GenericMonospace, 8));
                fpr.setMinimumTimesToPrint(1);
                fpr.setLandscape(true);
                fpr.ShowDialog();
            }
             
        }
         
        for (int i = 0;i < records.Count;i++)
        {
            //Now that the plans have been reported, drop the plans that are marked finally deleted.
            if (StringSupport.equals(records[i][1], "F"))
            {
                try
                {
                    InsPlan[] insplans = InsPlans.GetByTrojanID(records[i][0]);
                    for (int j = 0;j < insplans.Length;j++)
                    {
                        insplans[j].PlanNote = "PLAN DROPPED BY TROJAN" + Environment.NewLine + insplans[j].PlanNote;
                        insplans[j].TrojanID = "";
                        InsPlans.Update(insplans[j]);
                        PatPlan[] patplans = PatPlans.GetByPlanNum(insplans[j].PlanNum);
                        for (int k = 0;k < patplans.Length;k++)
                        {
                            PatPlans.Delete(patplans[k].PatPlanNum);
                        }
                    }
                }
                catch (ApplicationException ex)
                {
                    MessageBox.Show(ex.Message);
                    return ;
                }
            
            }
             
        }
        File.Delete(file);
    }

    private static void showPendingDeletionReportForPatients(FormPrintReport fpr) throws Exception {
        //Print the header on the report.
        Font font = new Font(FontFamily.GenericMonospace, 12);
        String text = PrefC.getString(PrefName.PracticeTitle);
        SizeF size = fpr.getGraph().MeasureString(text, font);
        float y = 20;
        fpr.getGraph().DrawString(text, font, Brushes.Black, fpr.getGraphWidth() / 2 - size.Width / 2, y);
        text = DateTime.Today.ToShortDateString();
        size = fpr.getGraph().MeasureString(text, font);
        fpr.getGraph().DrawString(text, font, Brushes.Black, fpr.getGraphWidth() - size.Width, y);
        y += size.Height;
        text = "PLANS PENDING DELETION WHICH REQUIRE YOUR ATTENTION";
        size = fpr.getGraph().MeasureString(text, font);
        fpr.getGraph().DrawString(text, font, Brushes.Black, fpr.getGraphWidth() / 2 - fpr.getGraph().MeasureString(text, font).Width / 2, y);
        y += size.Height;
        y += 20;
        //Skip a line or so.
        text = "INSTRUCTIONS: These plans no longer exist, please do not contact Trojan. Please contact your patient for current benefit information.";
        fpr.getGraph().DrawString(text, new Font(font, FontStyle.Bold), Brushes.Black, new RectangleF(0, y, 650, 500));
        y += 70;
        //Skip a line or so.
        text = "Patient&Insured";
        font = new Font(font.FontFamily, 9);
        fpr.getGraph().DrawString(text, font, Brushes.Black, 20, y);
        text = "TrojanID";
        fpr.getGraph().DrawString(text, font, Brushes.Black, 240, y);
        text = "Employer";
        fpr.getGraph().DrawString(text, font, Brushes.Black, 330, y);
        text = "Carrier";
        fpr.getGraph().DrawString(text, font, Brushes.Black, 500, y);
        y += 20;
        //Use a static height for the records, to keep the math simple.
        float recordHeight = 140;
        float recordSpacing = 10;
        //Calculate the total number of pages in the report the first time this function is called only.
        if (fpr.getTotalPages() == 0)
        {
            fpr.setTotalPages((int)Math.Ceiling((y + recordHeight * pendingDeletionTable.Rows.Count + ((pendingDeletionTable.Rows.Count > 1) ? pendingDeletionTable.Rows.Count - 1 : 0) * recordSpacing) / fpr.getPageHeight()));
        }
         
        float pageBoundry = fpr.getPageHeight();
        for (int i = 0;i < pendingDeletionTable.Rows.Count;i++)
        {
            //Draw the outlines around this record.
            fpr.getGraph().DrawLine(Pens.Black, new PointF(0, y), new PointF(fpr.getGraphWidth() - 1, y));
            fpr.getGraph().DrawLine(Pens.Black, new PointF(0, y + recordHeight), new PointF(fpr.getGraphWidth() - 1, y + recordHeight));
            fpr.getGraph().DrawLine(Pens.Black, new PointF(0, y), new PointF(0, y + recordHeight));
            fpr.getGraph().DrawLine(Pens.Black, new PointF(fpr.getGraphWidth() - 1, y), new PointF(fpr.getGraphWidth() - 1, y + recordHeight));
            fpr.getGraph().DrawLine(Pens.Black, new PointF(0, y + recordHeight - 40), new PointF(fpr.getGraphWidth() - 1, y + recordHeight - 40));
            fpr.getGraph().DrawLine(Pens.Black, new PointF(235, y), new PointF(235, y + recordHeight - 40));
            fpr.getGraph().DrawLine(Pens.Black, new PointF(325, y), new PointF(325, y + recordHeight - 40));
            fpr.getGraph().DrawLine(Pens.Black, new PointF(500, y), new PointF(500, y + recordHeight - 40));
            //Install the information for the record into the outline box.
            //Patient name, Guarantor name, guarantor SSN, guarantor birthdate, insurance plan group number,
            //and reason for pending deletion.
            fpr.getGraph().DrawString(PIn.String(pendingDeletionTable.Rows[i][0].ToString()) + " " + PIn.String(pendingDeletionTable.Rows[i][1].ToString()) + Environment.NewLine + PIn.String(pendingDeletionTable.Rows[i][2].ToString()) + " " + PIn.String(pendingDeletionTable.Rows[i][3].ToString()) + Environment.NewLine + " SSN: " + PIn.String(pendingDeletionTable.Rows[i][4].ToString()) + Environment.NewLine + " Birth: " + PIn.Date(pendingDeletionTable.Rows[i][5].ToString()).ToShortDateString() + Environment.NewLine + " Group: " + PIn.String(pendingDeletionTable.Rows[i][6].ToString()), font, Brushes.Black, new RectangleF(20, y + 5, 215, 95));
            for (int j = 0;j < deletePatientRecords.Count;j++)
            {
                //Pending deletion reason.
                if (deletePatientRecords[j][0] == PIn.String(pendingDeletionTable.Rows[i][8].ToString()))
                {
                    text = "REASON FOR DELETION: " + deletePatientRecords[j][7];
                    if (StringSupport.equals(deletePatientRecords[j][1].ToUpper(), "F"))
                    {
                        text = "FINALLY DELETED" + Environment.NewLine + text;
                    }
                     
                    fpr.getGraph().DrawString(text, font, Brushes.Black, new RectangleF(20, y + 100, fpr.getGraphWidth() - 40, 40));
                    break;
                }
                 
            }
            //Trojan ID.
            fpr.getGraph().DrawString(PIn.String(pendingDeletionTable.Rows[i][8].ToString()), font, Brushes.Black, new RectangleF(240, y + 5, 85, 95));
            //Employer Name and Phone.
            fpr.getGraph().DrawString(PIn.String(pendingDeletionTable.Rows[i][9].ToString()) + Environment.NewLine + PIn.String(pendingDeletionTable.Rows[i][10].ToString()), font, Brushes.Black, new RectangleF(330, y + 5, 170, 95));
            //Carrier Name and Phone
            fpr.getGraph().DrawString(PIn.String(pendingDeletionTable.Rows[i][11].ToString()) + Environment.NewLine + PIn.String(pendingDeletionTable.Rows[i][12].ToString()), font, Brushes.Black, new RectangleF(500, y + 5, 150, 95));
            //Leave space between records.
            y += recordHeight + recordSpacing;
            //Watch out for the bottom of each page for the next record.
            if (y + recordHeight > pageBoundry)
            {
                y = pageBoundry + fpr.getMarginBottom() + 20;
                pageBoundry += fpr.getPageHeight() + fpr.getMarginBottom();
                text = "Patient&Insured";
                font = new Font(font.FontFamily, 9);
                fpr.getGraph().DrawString(text, font, Brushes.Black, 20, y);
                text = "TrojanID";
                fpr.getGraph().DrawString(text, font, Brushes.Black, 240, y);
                text = "Employer";
                fpr.getGraph().DrawString(text, font, Brushes.Black, 330, y);
                text = "Carrier";
                fpr.getGraph().DrawString(text, font, Brushes.Black, 500, y);
                y += 20;
            }
             
        }
    }

    private static void showPendingDeletionReportForTrojan(FormPrintReport fpr) throws Exception {
        //Print the header on the report.
        Font font = new Font(FontFamily.GenericMonospace, 12);
        String text = PrefC.getString(PrefName.PracticeTitle);
        SizeF size = fpr.getGraph().MeasureString(text, font);
        float y = 20;
        fpr.getGraph().DrawString(text, font, Brushes.Black, fpr.getGraphWidth() / 2 - size.Width / 2, y);
        text = DateTime.Today.ToShortDateString();
        size = fpr.getGraph().MeasureString(text, font);
        fpr.getGraph().DrawString(text, font, Brushes.Black, fpr.getGraphWidth() - size.Width, y);
        y += size.Height;
        text = "PLANS PENDING DELETION: Please Fax or Mail to Trojan";
        size = fpr.getGraph().MeasureString(text, font);
        fpr.getGraph().DrawString(text, font, Brushes.Black, fpr.getGraphWidth() / 2 - fpr.getGraph().MeasureString(text, font).Width / 2, y);
        y += size.Height;
        text = "Fax: 800-232-9788";
        size = fpr.getGraph().MeasureString(text, font);
        fpr.getGraph().DrawString(text, font, Brushes.Black, fpr.getGraphWidth() / 2 - fpr.getGraph().MeasureString(text, font).Width / 2, y);
        y += size.Height;
        y += 20;
        //Skip a line or so.
        text = "INSTRUCTIONS: Please complete the information requested below to help Trojan research these plans.\n" + "Active Patient: \"Yes\" means the patient has been in the office within the past 6 to 8 months.\n" + "Correct Employer: \"Yes\" means the insured currently is insured through this employer.\n" + "Correct Carrier: \"Yes\" means the insured currently has coverage with this carrier.";
        fpr.getGraph().DrawString(text, new Font(new Font(font.FontFamily, 10), FontStyle.Bold), Brushes.Black, new RectangleF(0, y, 900, 500));
        y += 85;
        //Skip a line or so.
        font = new Font(font.FontFamily, 9);
        text = "Active\nPatient?";
        fpr.getGraph().DrawString(text, font, Brushes.Black, 5, y);
        text = "\nPatient&Insured";
        fpr.getGraph().DrawString(text, font, Brushes.Black, 80, y);
        text = "\nTrojanID";
        fpr.getGraph().DrawString(text, font, Brushes.Black, 265, y);
        text = "Correct\nEmployer?";
        fpr.getGraph().DrawString(text, font, Brushes.Black, 345, y);
        text = "\nEmployer";
        fpr.getGraph().DrawString(text, font, Brushes.Black, 420, y);
        text = "Correct\nCarrier?";
        fpr.getGraph().DrawString(text, font, Brushes.Black, 600, y);
        text = "\nCarrier";
        fpr.getGraph().DrawString(text, font, Brushes.Black, 670, y);
        y += 30;
        //Use a static height for the records, to keep the math simple.
        float recordHeight = 200;
        float recordSpacing = 10;
        //Calculate the total number of pages in the report the first time this function is called only.
        if (fpr.getTotalPages() == 0)
        {
            fpr.setTotalPages((int)Math.Ceiling((y + recordHeight * pendingDeletionTableTrojan.Rows.Count + ((pendingDeletionTableTrojan.Rows.Count > 1) ? pendingDeletionTableTrojan.Rows.Count - 1 : 0) * recordSpacing) / fpr.getPageHeight()));
        }
         
        float pageBoundry = fpr.getPageHeight();
        for (int i = 0;i < pendingDeletionTableTrojan.Rows.Count;i++)
        {
            //Draw the outlines around this record.
            fpr.getGraph().DrawLine(Pens.Black, new PointF(0, y), new PointF(fpr.getGraphWidth() - 1, y));
            fpr.getGraph().DrawLine(Pens.Black, new PointF(0, y + recordHeight), new PointF(fpr.getGraphWidth() - 1, y + recordHeight));
            fpr.getGraph().DrawLine(Pens.Black, new PointF(0, y), new PointF(0, y + recordHeight));
            fpr.getGraph().DrawLine(Pens.Black, new PointF(fpr.getGraphWidth() - 1, y), new PointF(fpr.getGraphWidth() - 1, y + recordHeight));
            fpr.getGraph().DrawLine(Pens.Black, new PointF(0, y + recordHeight - 40), new PointF(fpr.getGraphWidth() - 1, y + recordHeight - 40));
            fpr.getGraph().DrawLine(Pens.Black, new PointF(260, y), new PointF(260, y + recordHeight - 40));
            fpr.getGraph().DrawLine(Pens.Black, new PointF(340, y), new PointF(340, y + recordHeight - 40));
            fpr.getGraph().DrawLine(Pens.Black, new PointF(595, y), new PointF(595, y + recordHeight - 40));
            //Patient active boxes.
            text = "Yes No";
            fpr.getGraph().DrawString(text, font, Brushes.Black, 10, y);
            fpr.getGraph().DrawRectangle(Pens.Black, new Rectangle(15, (int)(y + 15), 15, 15));
            fpr.getGraph().DrawRectangle(Pens.Black, new Rectangle(40, (int)(y + 15), 15, 15));
            //Install the information for the record into the outline box.
            //Patient name, Guarantor name, guarantor SSN, guarantor birthdate, insurance plan group number,
            //and reason for pending deletion.
            fpr.getGraph().DrawString(PIn.String(pendingDeletionTableTrojan.Rows[i][0].ToString()) + " " + PIn.String(pendingDeletionTableTrojan.Rows[i][1].ToString()) + Environment.NewLine + PIn.String(pendingDeletionTableTrojan.Rows[i][2].ToString()) + " " + PIn.String(pendingDeletionTableTrojan.Rows[i][3].ToString()) + Environment.NewLine + " SSN: " + PIn.String(pendingDeletionTableTrojan.Rows[i][4].ToString()) + Environment.NewLine + " Birth: " + PIn.Date(pendingDeletionTableTrojan.Rows[i][5].ToString()).ToShortDateString() + Environment.NewLine + " Group: " + PIn.String(pendingDeletionTableTrojan.Rows[i][6].ToString()), font, Brushes.Black, new RectangleF(80, y + 5, 185, 95));
            for (int j = 0;j < deleteTrojanRecords.Count;j++)
            {
                //Pending deletion reason.
                if (deleteTrojanRecords[j][0] == PIn.String(pendingDeletionTableTrojan.Rows[i][8].ToString()))
                {
                    text = "REASON FOR DELETION: " + deleteTrojanRecords[j][7];
                    if (StringSupport.equals(deleteTrojanRecords[j][1].ToUpper(), "F"))
                    {
                        text = "FINALLY DELETED" + Environment.NewLine + text;
                    }
                     
                    fpr.getGraph().DrawString(text, font, Brushes.Black, new RectangleF(5, y + recordHeight - 40, fpr.getGraphWidth() - 10, 40));
                    break;
                }
                 
            }
            //Trojan ID.
            fpr.getGraph().DrawString(PIn.String(pendingDeletionTableTrojan.Rows[i][8].ToString()), font, Brushes.Black, new RectangleF(265, y + 5, 85, 95));
            //Correct Employer boxes and arrow.
            text = "Yes No";
            fpr.getGraph().DrawString(text, font, Brushes.Black, 345, y);
            fpr.getGraph().DrawRectangle(Pens.Black, new Rectangle(350, (int)(y + 15), 15, 15));
            fpr.getGraph().DrawRectangle(Pens.Black, new Rectangle(375, (int)(y + 15), 15, 15));
            //Employer Name and Phone.
            fpr.getGraph().DrawString(PIn.String(pendingDeletionTableTrojan.Rows[i][9].ToString()) + Environment.NewLine + PIn.String(pendingDeletionTableTrojan.Rows[i][10].ToString()), font, Brushes.Black, new RectangleF(420, y + 5, 175, 95));
            //New employer information if necessary.
            text = "New\nEmployer:";
            fpr.getGraph().DrawString(text, font, Brushes.Black, 345, y + 85);
            fpr.getGraph().DrawLine(Pens.Black, 415, y + 110, 590, y + 110);
            fpr.getGraph().DrawLine(Pens.Black, 415, y + 125, 590, y + 125);
            text = "Phone:";
            fpr.getGraph().DrawString(text, font, Brushes.Black, 345, y + 130);
            fpr.getGraph().DrawLine(Pens.Black, 415, y + 140, 590, y + 140);
            //Correct Carrier boxes and arrow.
            text = "Yes No";
            fpr.getGraph().DrawString(text, font, Brushes.Black, 600, y);
            fpr.getGraph().DrawRectangle(Pens.Black, new Rectangle(605, (int)(y + 15), 15, 15));
            fpr.getGraph().DrawRectangle(Pens.Black, new Rectangle(630, (int)(y + 15), 15, 15));
            //Carrier Name and Phone
            fpr.getGraph().DrawString(PIn.String(pendingDeletionTableTrojan.Rows[i][11].ToString()) + Environment.NewLine + PIn.String(pendingDeletionTableTrojan.Rows[i][12].ToString()), font, Brushes.Black, new RectangleF(670, y + 5, 225, 95));
            //New carrier information if necessary.
            text = "New\nCarrier:";
            fpr.getGraph().DrawString(text, font, Brushes.Black, 600, y + 85);
            fpr.getGraph().DrawLine(Pens.Black, 670, y + 110, 895, y + 110);
            fpr.getGraph().DrawLine(Pens.Black, 670, y + 125, 895, y + 125);
            text = "Phone:";
            fpr.getGraph().DrawString(text, font, Brushes.Black, 600, y + 130);
            fpr.getGraph().DrawLine(Pens.Black, 670, y + 140, 895, y + 140);
            //Leave space between records.
            y += recordHeight + recordSpacing;
            //Watch out for the bottom of each page for the next record.
            if (y + recordHeight > pageBoundry)
            {
                y = pageBoundry + fpr.getMarginBottom() + 20;
                pageBoundry += fpr.getPageHeight() + fpr.getMarginBottom();
                text = "Active\nPatient?";
                fpr.getGraph().DrawString(text, font, Brushes.Black, 5, y);
                text = "\nPatient&Insured";
                fpr.getGraph().DrawString(text, font, Brushes.Black, 80, y);
                text = "\nTrojanID";
                fpr.getGraph().DrawString(text, font, Brushes.Black, 265, y);
                text = "Correct\nEmployer?";
                fpr.getGraph().DrawString(text, font, Brushes.Black, 345, y);
                text = "\nEmployer";
                fpr.getGraph().DrawString(text, font, Brushes.Black, 420, y);
                text = "Correct\nCarrier?";
                fpr.getGraph().DrawString(text, font, Brushes.Black, 600, y);
                text = "\nCarrier";
                fpr.getGraph().DrawString(text, font, Brushes.Black, 670, y);
                y += 30;
            }
             
        }
    }

    /**
    * Process existing insurance plan updates from the ALLPLANS.TXT file.
    */
    private static void processTrojanPlanUpdates(String file) throws Exception {
        if (!File.Exists(file))
        {
            return ;
        }
         
        //Nothing to process.
        MessageBox.Show("Trojan update found.  Please print or save the text file when it opens, then close it.  You will be given a chance to cancel the update after that.");
        Process.Start(file);
        if (!MsgBox.show("Trojan",true,"Trojan plans will now be updated."))
        {
            return ;
        }
         
        Cursor.Current = Cursors.WaitCursor;
        String allplantext = "";
        StreamReader sr = new StreamReader(file);
        try
        {
            {
                allplantext = sr.ReadToEnd();
            }
        }
        finally
        {
            if (sr != null)
                Disposable.mkDisposable(sr).dispose();
             
        }
        if (StringSupport.equals(allplantext, ""))
        {
            Cursor.Current = Cursors.Default;
            MessageBox.Show("Could not read file contents: " + file);
            return ;
        }
         
        boolean updateBenefits = MsgBox.show("Trojan",MsgBoxButtons.YesNo,"Also update benefits?  Any customized benefits will be overwritten.");
        boolean updateNote = MsgBox.show("Trojan",MsgBoxButtons.YesNo,"Automatically update plan notes?  Any existing notes will be overwritten.  If you click No, you will be presented with each change for each plan so that you can edit the notes as needed.");
        String[] trojanplans = allplantext.Split(new String[]{ "TROJANID" }, StringSplitOptions.RemoveEmptyEntries);
        int plansAffected = 0;
        try
        {
            for (int i = 0;i < trojanplans.Length;i++)
            {
                trojanplans[i] = "TROJANID" + trojanplans[i];
                plansAffected += ProcessTrojanPlan(trojanplans[i], updateBenefits, updateNote);
            }
        }
        catch (Exception e)
        {
            //this will happen if user clicks cancel in a note box.
            MessageBox.Show("Error: " + e.Message + "\r\n\r\nWe will need a copy of the ALLPLANS.txt.");
            return ;
        }

        Cursor.Current = Cursors.Default;
        MessageBox.Show(plansAffected.ToString() + " plans updated.");
        try
        {
            File.Delete(file);
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show(file + " could not be deleted.  Please delete manually.");
        }
    
    }

    /**
    * Returns number of subscribers affected.  Can throw an exception if user clicks cancel in a note box.
    */
    private static int processTrojanPlan(String trojanPlan, boolean updateBenefits, boolean updateNoteAutomatic) throws Exception {
        TrojanObject troj = processTextToObject(trojanPlan);
        Carrier carrier = new Carrier();
        carrier.Phone = troj.ELIGPHONE;
        carrier.ElectID = troj.PAYERID;
        carrier.CarrierName = troj.MAILTO;
        carrier.Address = troj.MAILTOST;
        carrier.City = troj.MAILCITYONLY;
        carrier.State = troj.MAILSTATEONLY;
        carrier.Zip = troj.MAILZIPONLY;
        carrier.NoSendElect = false;
        //regardless of what Trojan says.  Nobody sends paper anymore.
        if (carrier.CarrierName == null || StringSupport.equals(carrier.CarrierName, ""))
        {
            return 0;
        }
         
        //if, for some reason, carrier is absent from the file, we can't do a thing with it.
        carrier = Carriers.getIndentical(carrier);
        //now, save this all to the database.
        troj.CarrierNum = carrier.CarrierNum;
        InsPlan plan = TrojanQueries.getPlanWithTrojanID(troj.TROJANID);
        if (plan == null)
        {
            return 0;
        }
         
        TrojanQueries.updatePlan(troj,plan.PlanNum,updateBenefits);
        plan = InsPlans.refreshOne(plan.PlanNum);
        if (updateNoteAutomatic)
        {
            if (!StringSupport.equals(plan.PlanNote, troj.PlanNote))
            {
                plan.PlanNote = troj.PlanNote;
                InsPlans.update(plan);
            }
             
        }
        else
        {
            //let user pick note
            if (!StringSupport.equals(plan.PlanNote, troj.PlanNote))
            {
                String[] notes = new String[2];
                notes[0] = plan.PlanNote;
                notes[1] = troj.PlanNote;
                FormNotePick FormN = new FormNotePick(notes);
                FormN.ShowDialog();
                if (FormN.DialogResult == DialogResult.OK)
                {
                    if (!StringSupport.equals(plan.PlanNote, FormN.SelectedNote))
                    {
                        plan.PlanNote = FormN.SelectedNote;
                        InsPlans.update(plan);
                    }
                     
                }
                 
            }
             
        } 
        return 1;
    }

    /**
    * Converts the text for one plan into an object which will then be processed as needed.
    */
    public static TrojanObject processTextToObject(String text) throws Exception {
        String[] lines = text.Split(new String[]{ "\r\n" }, StringSplitOptions.RemoveEmptyEntries);
        String line = new String();
        String[] fields = new String[]();
        int percent = new int();
        double amt = new double();
        String rowVal = new String();
        TrojanObject troj = new TrojanObject();
        troj.BenefitList = new List<Benefit>();
        troj.BenefitNotes = "";
        boolean usesAnnivers = false;
        Benefit ben;
        Benefit benCrownMajor = null;
        Benefit benCrownOnly = null;
        for (int i = 0;i < lines.Length;i++)
        {
            line = lines[i];
            fields = line.Split(new char[]{ '\t' });
            if (fields.Length != 3)
            {
                continue;
            }
             
            //remove any trailing or leading spaces:
            fields[0] = fields[0].Trim();
            fields[1] = fields[1].Trim();
            fields[2] = fields[2].Trim();
            rowVal = fields[2].Trim();
            if (StringSupport.equals(fields[2], ""))
            {
                continue;
            }
            else
            {
                //as long as there is data, add it to the notes
                if (!StringSupport.equals(troj.BenefitNotes, ""))
                {
                    troj.BenefitNotes += "\r\n";
                }
                 
                troj.BenefitNotes += fields[1] + ": " + fields[2];
                if (fields.Length == 4)
                {
                    troj.BenefitNotes += " " + fields[3];
                }
                 
            } 
            System.Array<System.String>.INDEXER __dummyScrutVar0 = fields[0];
            //default://for all rows that are not handled below
            if (__dummyScrutVar0.equals("TROJANID"))
            {
                troj.TROJANID = fields[2];
            }
            else if (__dummyScrutVar0.equals("ENAME"))
            {
                troj.ENAME = fields[2];
            }
            else if (__dummyScrutVar0.equals("PLANDESC"))
            {
                troj.PLANDESC = fields[2];
            }
            else if (__dummyScrutVar0.equals("ELIGPHONE"))
            {
                troj.ELIGPHONE = fields[2];
            }
            else if (__dummyScrutVar0.equals("POLICYNO"))
            {
                troj.POLICYNO = fields[2];
            }
            else if (__dummyScrutVar0.equals("ECLAIMS"))
            {
                if (StringSupport.equals(fields[2], "YES"))
                {
                    //accepts eclaims
                    troj.ECLAIMS = true;
                }
                else
                {
                    troj.ECLAIMS = false;
                } 
            }
            else if (__dummyScrutVar0.equals("PAYERID"))
            {
                troj.PAYERID = fields[2];
            }
            else if (__dummyScrutVar0.equals("MAILTO"))
            {
                troj.MAILTO = fields[2];
            }
            else if (__dummyScrutVar0.equals("MAILTOST"))
            {
                troj.MAILTOST = fields[2];
            }
            else if (__dummyScrutVar0.equals("MAILCITYONLY"))
            {
                troj.MAILCITYONLY = fields[2];
            }
            else if (__dummyScrutVar0.equals("MAILSTATEONLY"))
            {
                troj.MAILSTATEONLY = fields[2];
            }
            else if (__dummyScrutVar0.equals("MAILZIPONLY"))
            {
                troj.MAILZIPONLY = fields[2];
            }
            else if (__dummyScrutVar0.equals("PLANMAX"))
            {
                //eg $3000 per person per year
                if (!fields[2].StartsWith("$"))
                    break;
                 
                fields[2] = fields[2].Remove(0, 1);
                fields[2] = fields[2].Split(new char[]{ ' ' })[0];
                if (CovCatC.getListShort().Count > 0)
                {
                    ben = new Benefit();
                    ben.BenefitType = InsBenefitType.Limitations;
                    ben.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.General).CovCatNum;
                    ben.MonetaryAmt = PIn.Double(fields[2]);
                    ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                    ben.CoverageLevel = BenefitCoverageLevel.Individual;
                    troj.BenefitList.Add(ben.copy());
                }
                 
            }
            else if (__dummyScrutVar0.equals("PLANYR"))
            {
                //eg Calendar year or Anniversary year
                if (!StringSupport.equals(fields[2], "Calendar year"))
                {
                    usesAnnivers = true;
                }
                 
            }
            else //MessageBox.Show("Warning.  Plan uses Anniversary year rather than Calendar year.  Please verify the Plan Start Date.");
            if (__dummyScrutVar0.equals("DEDUCT"))
            {
                //eg There is no deductible
                if (!fields[2].StartsWith("$"))
                {
                    amt = 0;
                }
                else
                {
                    fields[2] = fields[2].Remove(0, 1);
                    fields[2] = fields[2].Split(new char[]{ ' ' })[0];
                    amt = PIn.Double(fields[2]);
                } 
                ben = new Benefit();
                ben.BenefitType = InsBenefitType.Deductible;
                ben.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.General).CovCatNum;
                ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                ben.MonetaryAmt = amt;
                ben.CoverageLevel = BenefitCoverageLevel.Individual;
                troj.BenefitList.Add(ben.copy());
                ben = new Benefit();
                ben.BenefitType = InsBenefitType.Deductible;
                ben.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.Diagnostic).CovCatNum;
                ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                ben.MonetaryAmt = 0;
                //amt;
                ben.CoverageLevel = BenefitCoverageLevel.Individual;
                troj.BenefitList.Add(ben.copy());
                ben = new Benefit();
                ben.BenefitType = InsBenefitType.Deductible;
                ben.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.RoutinePreventive).CovCatNum;
                ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                ben.MonetaryAmt = 0;
                //amt;
                ben.CoverageLevel = BenefitCoverageLevel.Individual;
                troj.BenefitList.Add(ben.copy());
            }
            else if (__dummyScrutVar0.equals("PREV"))
            {
                //eg 100% or 'Incentive begins at 70%' or '80% Endo Major see notes'
                if (StringSupport.equals(rowVal.ToLower(), "not covered"))
                {
                    percent = 0;
                }
                else
                {
                    percent = convertPercentToInt(rowVal);
                } 
                //remove %
                if (percent < 0 || percent > 100)
                {
                    break;
                }
                 
                ben = new Benefit();
                ben.BenefitType = InsBenefitType.CoInsurance;
                ben.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.Diagnostic).CovCatNum;
                ben.Percent = percent;
                ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                troj.BenefitList.Add(ben.copy());
                ben = new Benefit();
                ben.BenefitType = InsBenefitType.CoInsurance;
                ben.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.RoutinePreventive).CovCatNum;
                ben.Percent = percent;
                ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                troj.BenefitList.Add(ben.copy());
            }
            else if (__dummyScrutVar0.equals("BASIC"))
            {
                if (StringSupport.equals(rowVal.ToLower(), "not covered"))
                {
                    percent = 0;
                }
                else
                {
                    percent = convertPercentToInt(rowVal);
                } 
                //remove %
                if (percent < 0 || percent > 100)
                {
                    break;
                }
                 
                ben = new Benefit();
                ben.BenefitType = InsBenefitType.CoInsurance;
                ben.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.Restorative).CovCatNum;
                ben.Percent = percent;
                ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                troj.BenefitList.Add(ben.copy());
                ben = new Benefit();
                ben.BenefitType = InsBenefitType.CoInsurance;
                ben.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.Endodontics).CovCatNum;
                ben.Percent = percent;
                ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                troj.BenefitList.Add(ben.copy());
                ben = new Benefit();
                ben.BenefitType = InsBenefitType.CoInsurance;
                ben.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.Periodontics).CovCatNum;
                ben.Percent = percent;
                ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                troj.BenefitList.Add(ben.copy());
                ben = new Benefit();
                ben.BenefitType = InsBenefitType.CoInsurance;
                ben.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.OralSurgery).CovCatNum;
                ben.Percent = percent;
                ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                troj.BenefitList.Add(ben.copy());
            }
            else if (__dummyScrutVar0.equals("MAJOR"))
            {
                if (StringSupport.equals(rowVal.ToLower(), "not covered"))
                {
                    percent = 0;
                }
                else
                {
                    percent = convertPercentToInt(rowVal);
                } 
                //remove %
                if (percent < 0 || percent > 100)
                {
                    break;
                }
                 
                ben = new Benefit();
                ben.BenefitType = InsBenefitType.CoInsurance;
                ben.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.Prosthodontics).CovCatNum;
                ben.Percent = percent;
                ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                troj.BenefitList.Add(ben.copy());
                benCrownMajor = new Benefit();
                benCrownMajor.BenefitType = InsBenefitType.CoInsurance;
                benCrownMajor.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.Crowns).CovCatNum;
                benCrownMajor.Percent = percent;
                benCrownMajor.TimePeriod = BenefitTimePeriod.CalendarYear;
            }
            else //troj.BenefitList.Add(ben.Copy());//later
            if (__dummyScrutVar0.equals("CROWNS"))
            {
                //Examples: Paid Major, or 80%.  We will only process percentages.
                if (StringSupport.equals(rowVal.ToLower(), "not covered"))
                {
                    percent = 0;
                }
                else
                {
                    percent = convertPercentToInt(rowVal);
                } 
                //remove %
                if (percent < 0 || percent > 100)
                {
                    break;
                }
                 
                benCrownOnly = new Benefit();
                benCrownOnly.BenefitType = InsBenefitType.CoInsurance;
                benCrownOnly.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.Crowns).CovCatNum;
                benCrownOnly.Percent = percent;
                benCrownOnly.TimePeriod = BenefitTimePeriod.CalendarYear;
            }
            else //troj.BenefitList.Add(ben.Copy());
            if (__dummyScrutVar0.equals("ORMAX"))
            {
                //eg $3500 lifetime
                if (!fields[2].StartsWith("$"))
                {
                    break;
                }
                 
                fields[2] = fields[2].Remove(0, 1);
                fields[2] = fields[2].Split(new char[]{ ' ' })[0];
                if (CovCatC.getListShort().Count > 0)
                {
                    ben = new Benefit();
                    ben.BenefitType = InsBenefitType.Limitations;
                    ben.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.Orthodontics).CovCatNum;
                    ben.MonetaryAmt = PIn.Double(fields[2]);
                    ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                    troj.BenefitList.Add(ben.copy());
                }
                 
            }
            else if (__dummyScrutVar0.equals("ORPCT"))
            {
                if (StringSupport.equals(rowVal.ToLower(), "not covered"))
                {
                    percent = 0;
                }
                else
                {
                    percent = convertPercentToInt(rowVal);
                } 
                //remove %
                if (percent < 0 || percent > 100)
                {
                    break;
                }
                 
                ben = new Benefit();
                ben.BenefitType = InsBenefitType.CoInsurance;
                ben.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.Orthodontics).CovCatNum;
                ben.Percent = percent;
                ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                troj.BenefitList.Add(ben.copy());
            }
            else /*case "FEE":
            						if(!ProcedureCodes.IsValidCode(fields[1])) {
            							break;//skip
            						}
            						if(textTrojanID.Text==""){
            							break;
            						}
            						feeSchedNum=Fees.ImportTrojan(fields[1],PIn.PDouble(fields[3]),textTrojanID.Text);
            						//the step above probably created a new feeschedule, requiring a reset of the three listboxes.
            						resetFeeSched=true;
            						break;*/
            if (__dummyScrutVar0.equals("NOTES"))
            {
                //typically multiple instances
                if (troj.PlanNote != null && !StringSupport.equals(troj.PlanNote, ""))
                {
                    troj.PlanNote += "\r\n";
                }
                 
                troj.PlanNote += fields[2];
            }
                                  
        }
        //switch
        //for
        //Set crowns
        if (benCrownOnly != null)
        {
            troj.BenefitList.Add(benCrownOnly.copy());
        }
        else if (benCrownMajor != null)
        {
            troj.BenefitList.Add(benCrownMajor.copy());
        }
          
        //set calendar vs serviceyear
        if (usesAnnivers)
        {
            for (int i = 0;i < troj.BenefitList.Count;i++)
            {
                troj.BenefitList[i].TimePeriod = BenefitTimePeriod.ServiceYear;
            }
        }
         
        return troj;
    }

    /**
    * Takes a string percentage and returns the integer value.  Returns -1 if no match.
    */
    private static int convertPercentToInt(String percent) throws Exception {
        Match regMatch = Regex.Match(percent, "([0-9]+)\\%");
        if (regMatch.Success)
        {
            return PIn.Int(regMatch.Groups[1].Value);
        }
         
        return -1;
    }

}


