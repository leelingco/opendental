//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:28 PM
//

package OpenDental.Bridges;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDental.PIn;
import OpenDentBusiness.Carrier;
import OpenDentBusiness.Carriers;
import OpenDentBusiness.CommItemMode;
import OpenDentBusiness.CommItemTypeAuto;
import OpenDentBusiness.Commlog;
import OpenDentBusiness.Commlogs;
import OpenDentBusiness.CommSentOrReceived;
import OpenDentBusiness.ContactMethod;
import OpenDentBusiness.Disease;
import OpenDentBusiness.DiseaseDefs;
import OpenDentBusiness.Diseases;
import OpenDentBusiness.Employer;
import OpenDentBusiness.Employers;
import OpenDentBusiness.Family;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.PatientPosition;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatientStatus;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.Program;
import OpenDentBusiness.Referral;
import OpenDentBusiness.Referrals;
import OpenDentBusiness.Security;
import OpenDentBusiness.TelephoneNumbers;

/**
* PT Dental.  www.gopaperlessnow.com.  This bridge only works on Windows and in English, so some shortcuts were taken.
*/
public class PaperlessTechnology   
{
    private static String exportAddCsv = "addpatient_OD.csv";
    private static String exportUpdateCsv = "updatepatient_OD.csv";
    private static String importCsv = "patientinfo_PT.csv";
    private static String importMedCsv = "patientmedalerts.csv";
    private static String exportAddExe = "addpatient_OD.exe";
    private static String exportUpdateExe = "updatepatient_OD.exe";
    private static String dir = "C:\\PT\\USI";
    private static FileSystemWatcher watcher = new FileSystemWatcher();
    private static String[] fieldNames = new String[]();
    private static String[] fieldVals = new String[]();
    /**
    * 
    */
    public PaperlessTechnology() throws Exception {
    }

    /**
    * There might be incoming files that we have to watch for.  They will get processed and deleted.  There is no user interface for this function.  This method is called when OD first starts up.
    */
    public static void initializeFileWatcher() throws Exception {
        if (!Directory.Exists(dir))
        {
            if (watcher != null)
            {
                watcher.Dispose();
            }
             
            return ;
        }
         
        watcher = new FileSystemWatcher();
        watcher.Path = dir;
        //watcher.NotifyFilter = NotifyFilters.CreationTime;
        String importFileName = importCsv;
        if (File.Exists(dir + "\\" + importCsv))
        {
            File.Delete(dir + "\\" + importCsv);
        }
         
        watcher.Filter = importFileName;
        watcher.Created += new FileSystemEventHandler(OnCreated);
        watcher.EnableRaisingEvents = true;
    }

    private static void onCreated(Object source, FileSystemEventArgs e) throws Exception {
        //MessageBox.Show("File created.  It will now be deleted.");
        Thread.Sleep(200);
        //just to make sure the other process is done writing.
        String[] lines = File.ReadAllLines(e.FullPath);
        File.Delete(e.FullPath);
        if (lines.Length != 1)
        {
            MessageBox.Show(e.FullPath + " was supposed to have exactly one line.  Invalid file.");
            return ;
        }
         
        String rawFieldNames = "PAT_PK,PAT_LOGFK,PAT_LANFK,PAT_TITLE,PAT_FNAME,PAT_MI,PAT_LNAME,PAT_CALLED,PAT_ADDR1,PAT_ADDR2,PAT_CITY,PAT_ST,PAT_ZIP,PAT_HPHN,PAT_WPHN,PAT_EXT,PAT_FAX,PAT_PAGER,PAT_CELL,PAT_EMAIL,PAT_SEX,PAT_EDOCS,PAT_STATUS,PAT_TYPE,PAT_BIRTH,PAT_SSN,PAT_NOCALL,PAT_NOCORR,PAT_DISRES,PAT_LSTUPD,PAT_INSNM,PAT_INSGPL,PAT_INSAD1,PAT_INSAD2,PAT_INSCIT,PAT_INSST,PAT_INSZIP,PAT_INSPHN,PAT_INSEXT,PAT_INSCON,PAT_INSGNO,PAT_EMPNM,PAT_EMPAD1,PAT_EMPAD2,PAT_EMPCIT,PAT_EMPST,PAT_EMPZIP,PAT_EMPPHN,PAT_REFLNM,PAT_REFFNM,PAT_REFMI,PAT_REFPHN,PAT_REFEML,PAT_REFSPE,PAT_NOTES,PAT_NOTE1,PAT_NOTE2,PAT_NOTE3,PAT_NOTE4,PAT_NOTE5,PAT_NOTE6,PAT_NOTE7,PAT_NOTE8,PAT_NOTE9,PAT_NOTE10,PAT_FPSCAN,PAT_PREMED,PAT_MEDS,PAT_FTSTUD,PAT_PTSTUD,PAT_COLLEG,PAT_CHRTNO,PAT_OTHID,PAT_RESPRT,PAT_POLHLD,PAT_CUSCD,PAT_PMPID";
        fieldNames = rawFieldNames.Split(',');
        fieldVals = lines[0].Split(',');
        if (fieldNames.Length != fieldVals.Length)
        {
            MessageBox.Show(e.FullPath + " contains " + fieldNames.Length.ToString() + " field names, but " + fieldVals.Length.ToString() + " field values.  Invalid file.");
            return ;
        }
         
        for (int i = 0;i < fieldVals.Length;i++)
        {
            fieldVals[i] = fieldVals[i].Replace("\"", "");
        }
        //remove quotes
        long patNum = PIn.Long(getVal("PAT_OTHID"));
        if (patNum == 0)
        {
            MessageBox.Show(patNum.ToString() + " is not a recognized PatNum.");
            return ;
        }
         
        Family fam = Patients.getFamily(patNum);
        if (fam == null)
        {
            MessageBox.Show("Could not find patient based on PatNum " + patNum.ToString());
            return ;
        }
         
        Patient pat = fam.getPatient(patNum);
        Patient patOld = pat.copy();
        String txt = new String();
        String note = "PT Dental import processed.  Some information is shown below which was too complex to import automatically.\r\n";
        txt = getVal("PAT_FNAME");
        if (!StringSupport.equals(txt, ""))
        {
            pat.FName = txt;
        }
         
        txt = getVal("PAT_MI");
        if (!StringSupport.equals(txt, ""))
        {
            pat.MiddleI = txt;
        }
         
        txt = getVal("PAT_LNAME");
        if (!StringSupport.equals(txt, ""))
        {
            pat.LName = txt;
        }
         
        txt = getVal("PAT_CALLED");
        if (!StringSupport.equals(txt, ""))
        {
            pat.Preferred = txt;
        }
         
        txt = getVal("PAT_ADDR1");
        if (!StringSupport.equals(txt, ""))
        {
            pat.Address = txt;
        }
         
        txt = getVal("PAT_ADDR2");
        if (!StringSupport.equals(txt, ""))
        {
            pat.Address2 = txt;
        }
         
        txt = getVal("PAT_CITY");
        if (!StringSupport.equals(txt, ""))
        {
            pat.City = txt;
        }
         
        txt = getVal("PAT_ST");
        if (!StringSupport.equals(txt, ""))
        {
            pat.State = txt;
        }
         
        txt = getVal("PAT_ZIP");
        if (!StringSupport.equals(txt, ""))
        {
            pat.Zip = txt;
        }
         
        txt = getVal("PAT_HPHN");
        //No punct
        if (!StringSupport.equals(txt, ""))
        {
            pat.HmPhone = TelephoneNumbers.reFormat(txt);
        }
         
        txt = getVal("PAT_WPHN");
        if (!StringSupport.equals(txt, ""))
        {
            pat.WkPhone = TelephoneNumbers.reFormat(txt);
        }
         
        //no matching fields for these three:
        txt = getVal("PAT_EXT");
        if (!StringSupport.equals(txt, ""))
        {
            note += "Ph extension: " + txt + "\r\n";
        }
         
        txt = getVal("PAT_FAX");
        if (!StringSupport.equals(txt, ""))
        {
            note += "Fax: " + txt + "\r\n";
        }
         
        txt = getVal("PAT_PAGER");
        if (!StringSupport.equals(txt, ""))
        {
            note += "Pager: " + txt + "\r\n";
        }
         
        txt = getVal("PAT_CELL");
        if (!StringSupport.equals(txt, ""))
        {
            pat.WirelessPhone = TelephoneNumbers.reFormat(txt);
        }
         
        txt = getVal("PAT_EMAIL");
        if (!StringSupport.equals(txt, ""))
        {
            pat.Email = txt;
        }
         
        txt = getVal("PAT_SEX");
        //M or F
        if (StringSupport.equals(txt, "M"))
        {
            pat.Gender = PatientGender.Male;
        }
         
        if (StringSupport.equals(txt, "F"))
        {
            pat.Gender = PatientGender.Male;
        }
         
        txt = getVal("PAT_STATUS");
        //our patStatus, Any text allowed
        System.String __dummyScrutVar0 = txt;
        if (__dummyScrutVar0.equals("Archived"))
        {
            pat.PatStatus = PatientStatus.Archived;
        }
        else if (__dummyScrutVar0.equals("Deceased"))
        {
            pat.PatStatus = PatientStatus.Deceased;
        }
        else //case "Archived": pat.PatStatus=PatientStatus.Deleted; break;
        if (__dummyScrutVar0.equals("Inactive"))
        {
            pat.PatStatus = PatientStatus.Inactive;
        }
        else if (__dummyScrutVar0.equals("NonPatient"))
        {
            pat.PatStatus = PatientStatus.NonPatient;
        }
        else if (__dummyScrutVar0.equals("Patient"))
        {
            pat.PatStatus = PatientStatus.Patient;
        }
             
        txt = getVal("PAT_TYPE");
        //our Position, Any text allowed
        System.String __dummyScrutVar1 = txt;
        if (__dummyScrutVar1.equals("Child"))
        {
            pat.Position = PatientPosition.Child;
        }
        else if (__dummyScrutVar1.equals("Divorced"))
        {
            pat.Position = PatientPosition.Divorced;
        }
        else if (__dummyScrutVar1.equals("Married"))
        {
            pat.Position = PatientPosition.Married;
        }
        else if (__dummyScrutVar1.equals("Single"))
        {
            pat.Position = PatientPosition.Single;
        }
        else if (__dummyScrutVar1.equals("Widowed"))
        {
            pat.Position = PatientPosition.Widowed;
        }
             
        txt = getVal("PAT_BIRTH");
        // yyyyMMdd
        if (!StringSupport.equals(txt, ""))
        {
            pat.Birthdate = PIn.Date(txt);
        }
         
        txt = getVal("PAT_SSN");
        // No punct
        if (!StringSupport.equals(txt, ""))
        {
            pat.SSN = txt;
        }
         
        txt = getVal("PAT_NOCALL");
        // T if no call
        if (!StringSupport.equals(txt, ""))
        {
            note += "No Call Patient: " + txt + "\r\n";
        }
         
        txt = getVal("PAT_NOCORR");
        // T/F
        if (!StringSupport.equals(txt, ""))
        {
            note += "No Correspondence: " + txt + "\r\n";
        }
         
        txt = getVal("PAT_NOTES");
        // No limits.
        if (!StringSupport.equals(txt, ""))
        {
            note += txt + "\r\n";
        }
         
        txt = getVal("PAT_PREMED");
        // F or T
        //I don't like giving the patient control of this field, but I guess the office has the option of not showing this on forms.
        if (StringSupport.equals(txt, "T"))
        {
            pat.Premed = true;
        }
         
        if (StringSupport.equals(txt, "F"))
        {
            pat.Premed = false;
        }
         
        txt = getVal("PAT_MEDS");
        // The meds that they must premedicate with.
        if (!StringSupport.equals(txt, ""))
        {
            note += "Patient Meds: " + txt + "\r\n";
        }
         
        String ft = getVal("PAT_FTSTUD");
        // T/F
        String pt = getVal("PAT_PTSTUD");
        //parttime
        if (StringSupport.equals(ft, "T"))
        {
            pat.StudentStatus = "F";
        }
        else //fulltime
        if (StringSupport.equals(pt, "T"))
        {
            pat.StudentStatus = "P";
        }
        else //parttime
        if (StringSupport.equals(ft, "F") && StringSupport.equals(pt, "F"))
        {
            pat.StudentStatus = "";
        }
        else //nonstudent
        if (StringSupport.equals(ft, "F") && StringSupport.equals(pat.StudentStatus, "F"))
        {
            pat.StudentStatus = "";
        }
        else if (StringSupport.equals(pt, "F") && StringSupport.equals(pat.StudentStatus, "P"))
        {
            pat.StudentStatus = "";
        }
             
        txt = getVal("PAT_COLLEG");
        if (!StringSupport.equals(txt, ""))
        {
            pat.SchoolName = txt;
        }
         
        txt = getVal("PAT_CHRTNO");
        //I don't think patient should have control of this field.
        if (!StringSupport.equals(txt, ""))
        {
            pat.ChartNumber = txt;
        }
         
        txt = getVal("PAT_RESPRT");
        // Responsible party checkbox T/F
        if (StringSupport.equals(txt, "T") && pat.PatNum != pat.Guarantor)
        {
            note += "Responsible party: True\r\n";
        }
         
        if (StringSupport.equals(txt, "F") && pat.PatNum == pat.Guarantor)
        {
            note += "Responsible party: False\r\n";
        }
         
        txt = getVal("PAT_POLHLD");
        // Policy holder checkbox T/F
        if (StringSupport.equals(txt, "T"))
        {
            note += "Policy holder: True\r\n";
        }
         
        if (StringSupport.equals(txt, "F"))
        {
            note += "Policy holder: False\r\n";
        }
         
        txt = getVal("PAT_INSNM");
        if (!StringSupport.equals(txt, ""))
        {
            note += "Insurance name: " + txt + "\r\n";
        }
         
        txt = getVal("PAT_INSGPL");
        if (!StringSupport.equals(txt, ""))
        {
            note += "Ins group plan name: " + txt + "\r\n";
        }
         
        txt = getVal("PAT_INSAD1");
        if (!StringSupport.equals(txt, ""))
        {
            note += "Ins address: " + txt + "\r\n";
        }
         
        txt = getVal("PAT_INSAD2");
        if (!StringSupport.equals(txt, ""))
        {
            note += "Ins address2: " + txt + "\r\n";
        }
         
        txt = getVal("PAT_INSCIT");
        if (!StringSupport.equals(txt, ""))
        {
            note += "Ins city: " + txt + "\r\n";
        }
         
        txt = getVal("PAT_INSST");
        if (!StringSupport.equals(txt, ""))
        {
            note += "Ins state: " + txt + "\r\n";
        }
         
        txt = getVal("PAT_INSZIP");
        if (!StringSupport.equals(txt, ""))
        {
            note += "Ins zip: " + txt + "\r\n";
        }
         
        txt = getVal("PAT_INSPHN");
        if (!StringSupport.equals(txt, ""))
        {
            note += "Ins phone: " + TelephoneNumbers.reFormat(txt) + "\r\n";
        }
         
        txt = getVal("PAT_INSGNO");
        // Ins group number
        if (!StringSupport.equals(txt, ""))
        {
            note += "Ins group number: " + txt + "\r\n";
        }
         
        txt = getVal("PAT_EMPNM");
        if (!StringSupport.equals(txt, ""))
        {
            note += "Employer name: " + txt + "\r\n";
        }
         
        txt = getVal("PAT_REFLNM");
        if (!StringSupport.equals(txt, ""))
        {
            note += "Referral last name: " + txt + "\r\n";
        }
         
        txt = getVal("PAT_REFFNM");
        if (!StringSupport.equals(txt, ""))
        {
            note += "Referral first name: " + txt + "\r\n";
        }
         
        txt = getVal("PAT_REFMI");
        if (!StringSupport.equals(txt, ""))
        {
            note += "Referral middle init: " + txt + "\r\n";
        }
         
        txt = getVal("PAT_REFPHN");
        if (!StringSupport.equals(txt, ""))
        {
            note += "Referral phone: " + txt + "\r\n";
        }
         
        txt = getVal("PAT_REFEML");
        // Referral source email
        if (!StringSupport.equals(txt, ""))
        {
            note += "Referral email: " + txt + "\r\n";
        }
         
        txt = getVal("PAT_REFSPE");
        // Referral specialty. Customizable, so any allowed
        if (!StringSupport.equals(txt, ""))
        {
            note += "Referral specialty: " + txt + "\r\n";
        }
         
        Patients.update(pat,patOld);
        if (File.Exists(dir + "\\" + importMedCsv))
        {
            lines = File.ReadAllLines(dir + "\\" + importMedCsv);
            File.Delete(dir + "\\" + importMedCsv);
            if (lines.Length < 1)
            {
                MessageBox.Show(e.FullPath + " was supposed to have at least one line.  Invalid file.");
                return ;
            }
             
            fieldNames = lines[0].Split(',');
            long diseaseDefNum = new long();
            Disease disease;
            String diseaseNote = new String();
            for (int i = 1;i < lines.Length;i++)
            {
                fieldVals = lines[i].Split(',');
                txt = getVal("PMA_MALDES");
                diseaseNote = getVal("PMA_NOTES");
                if (StringSupport.equals(txt, ""))
                {
                    continue;
                }
                 
                diseaseDefNum = DiseaseDefs.getNumFromName(txt);
                if (diseaseDefNum == 0)
                {
                    note += "Disease: " + txt + ", " + diseaseNote + "\r\n";
                }
                 
                disease = Diseases.getSpecificDiseaseForPatient(patNum,diseaseDefNum);
                if (disease == null)
                {
                    disease = new Disease();
                    disease.DiseaseDefNum = diseaseDefNum;
                    disease.PatNum = patNum;
                    disease.PatNote = diseaseNote;
                    Diseases.insert(disease);
                }
                else
                {
                    if (!StringSupport.equals(txt, ""))
                    {
                        if (!StringSupport.equals(disease.PatNote, ""))
                        {
                            disease.PatNote += "  ";
                        }
                         
                        disease.PatNote += diseaseNote;
                        Diseases.update(disease);
                    }
                     
                } 
            }
        }
         
        Commlog comm = new Commlog();
        comm.PatNum = patNum;
        comm.SentOrReceived = CommSentOrReceived.Received;
        comm.CommDateTime = DateTime.Now;
        comm.CommType = Commlogs.getTypeAuto(CommItemTypeAuto.MISC);
        comm.Mode_ = CommItemMode.None;
        comm.Note = note;
        comm.UserNum = Security.getCurUser().UserNum;
        Commlogs.insert(comm);
        MessageBox.Show("PT Dental import complete.");
    }

    private static String getVal(String fieldname) throws Exception {
        for (int i = 0;i < fieldNames.Length;i++)
        {
            if (StringSupport.equals(fieldNames[i], fieldname))
            {
                if (fieldVals.Length != fieldNames.Length)
                {
                    return "";
                }
                 
                return fieldVals[i];
            }
             
        }
        return "";
    }

    /**
    * Sends data for Patient.Cur to an export file and then launches an exe to notify PT.  If patient exists, this simply opens the patient.  If patient does not exist, then this triggers creation of the patient in PT Dental.  If isUpdate is true, then the export file and exe will have different names. In PT, update is a separate programlink with a separate button.
    */
    public static void sendData(Program ProgramCur, Patient pat, boolean isUpdate) throws Exception {
        //ArrayList ForProgram=ProgramProperties.GetForProgram(ProgramCur.ProgramNum);
        //ProgramProperty PPCur=ProgramProperties.GetCur(ForProgram, "InfoFile path");
        //string infoFile=PPCur.PropertyValue;
        if (!Directory.Exists(dir))
        {
            MessageBox.Show(dir + " does not exist.  PT Dental doesn't seem to be properly installed on this computer.");
            return ;
        }
         
        if (pat == null)
        {
            MessageBox.Show("No patient is selected.");
            return ;
        }
         
        if (!File.Exists(dir + "\\" + exportAddExe))
        {
            MessageBox.Show(dir + "\\" + exportAddExe + " does not exist.  PT Dental doesn't seem to be properly installed on this computer.");
            return ;
        }
         
        if (!File.Exists(dir + "\\" + exportUpdateExe))
        {
            MessageBox.Show(dir + "\\" + exportUpdateExe + " does not exist.  PT Dental doesn't seem to be properly installed on this computer.");
            return ;
        }
         
        String filename = dir + "\\" + exportAddCsv;
        if (isUpdate)
        {
            filename = dir + "\\" + exportUpdateCsv;
        }
         
        StreamWriter sw = new StreamWriter(filename, false);
        try
        {
            {
                //overwrites if it already exists.
                sw.WriteLine("PAT_PK,PAT_LOGFK,PAT_LANFK,PAT_TITLE,PAT_FNAME,PAT_MI,PAT_LNAME,PAT_CALLED,PAT_ADDR1,PAT_ADDR2,PAT_CITY,PAT_ST,PAT_ZIP,PAT_HPHN,PAT_WPHN,PAT_EXT,PAT_FAX,PAT_PAGER,PAT_CELL,PAT_EMAIL,PAT_SEX,PAT_EDOCS,PAT_STATUS,PAT_TYPE,PAT_BIRTH,PAT_SSN,PAT_NOCALL,PAT_NOCORR,PAT_DISRES,PAT_LSTUPD,PAT_INSNM,PAT_INSGPL,PAT_INSAD1,PAT_INSAD2,PAT_INSCIT,PAT_INSST,PAT_INSZIP,PAT_INSPHN,PAT_INSEXT,PAT_INSCON,PAT_INSGNO,PAT_EMPNM,PAT_EMPAD1,PAT_EMPAD2,PAT_EMPCIT,PAT_EMPST,PAT_EMPZIP,PAT_EMPPHN,PAT_REFLNM,PAT_REFFNM,PAT_REFMI,PAT_REFPHN,PAT_REFEML,PAT_REFSPE,PAT_NOTES,PAT_FPSCAN,PAT_PREMED,PAT_MEDS,PAT_FTSTUD,PAT_PTSTUD,PAT_COLLEG,PAT_CHRTNO,PAT_OTHID,PAT_RESPRT,PAT_POLHLD,PAT_CUSCD,PAT_PMPID");
                sw.Write(",");
                //PAT_PK  Primary key. Long alphanumeric. We do not use.
                sw.Write(",");
                //PAT_LOGFK Internal PT logical, it can be ignored.
                sw.Write(",");
                //PAT_LANFK Internal PT logical, it can be ignored.
                sw.Write(",");
                //PAT_TITLE We do not have this field yet
                sw.Write(tidy(pat.FName) + ",");
                //PAT_FNAME
                sw.Write(tidy(pat.MiddleI) + ",");
                //PAT_MI
                sw.Write(tidy(pat.LName) + ",");
                //PAT_LNAME
                sw.Write(tidy(pat.Preferred) + ",");
                //PAT_CALLED Nickname
                sw.Write(tidy(pat.Address) + ",");
                //PAT_ADDR1
                sw.Write(tidy(pat.Address2) + ",");
                //PAT_ADDR2
                sw.Write(tidy(pat.City) + ",");
                //PAT_CITY
                sw.Write(tidy(pat.State) + ",");
                //PAT_ST
                sw.Write(tidy(pat.Zip) + ",");
                //PAT_ZIP
                sw.Write(TelephoneNumbers.formatNumbersOnly(pat.HmPhone) + ",");
                //PAT_HPHN No punct
                sw.Write(TelephoneNumbers.formatNumbersOnly(pat.WkPhone) + ",");
                //PAT_WPHN
                sw.Write(",");
                //PAT_EXT
                sw.Write(",");
                //PAT_FAX
                sw.Write(",");
                //PAT_PAGER
                sw.Write(TelephoneNumbers.formatNumbersOnly(pat.WirelessPhone) + ",");
                //PAT_CELL
                sw.Write(tidy(pat.Email) + ",");
                //PAT_EMAIL
                if (pat.Gender == PatientGender.Female)
                {
                    sw.Write("Female");
                }
                else if (pat.Gender == PatientGender.Male)
                {
                    sw.Write("Male");
                }
                  
                sw.Write(",");
                //PAT_SEX might be blank if unknown
                sw.Write(",");
                //PAT_EDOCS Internal PT logical, it can be ignored.
                sw.Write(pat.PatStatus.ToString() + ",");
                //PAT_STATUS Any text allowed
                sw.Write(pat.Position.ToString() + ",");
                //PAT_TYPE Any text allowed
                if (pat.Birthdate.Year > 1880)
                {
                    sw.Write(pat.Birthdate.ToString("MM/dd/yyyy"));
                }
                 
                //PAT_BIRTH MM/dd/yyyy
                sw.Write(",");
                sw.Write(tidy(pat.SSN) + ",");
                //PAT_SSN No punct
                if (pat.PreferContactMethod == ContactMethod.DoNotCall || pat.PreferConfirmMethod == ContactMethod.DoNotCall || pat.PreferRecallMethod == ContactMethod.DoNotCall)
                {
                    sw.Write("1");
                }
                 
                sw.Write(",");
                //PAT_NOCALL T if no call
                sw.Write(",");
                //PAT_NOCORR No correspondence HIPAA
                sw.Write(",");
                //PAT_DISRES Internal PT logical, it can be ignored.
                sw.Write(",");
                //PAT_LSTUPD Internal PT logical, it can be ignored.
                List<PatPlan> patPlanList = PatPlans.refresh(pat.PatNum);
                Family fam = Patients.getFamily(pat.PatNum);
                List<InsSub> subList = InsSubs.refreshForFam(fam);
                List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
                PatPlan patplan = null;
                InsSub sub = null;
                InsPlan plan = null;
                Carrier carrier = null;
                Employer emp = null;
                if (patPlanList.Count > 0)
                {
                    patplan = patPlanList[0];
                    sub = InsSubs.GetSub(patplan.InsSubNum, subList);
                    plan = InsPlans.GetPlan(sub.PlanNum, planList);
                    carrier = Carriers.getCarrier(plan.CarrierNum);
                    if (plan.EmployerNum != 0)
                    {
                        emp = Employers.getEmployer(plan.EmployerNum);
                    }
                     
                }
                 
                if (plan == null)
                {
                    sw.Write(",");
                    //PAT_INSNM
                    sw.Write(",");
                    //PAT_INSGPL Ins group plan name
                    sw.Write(",");
                    //PAT_INSAD1
                    sw.Write(",");
                    //PAT_INSAD2
                    sw.Write(",");
                    //PAT_INSCIT
                    sw.Write(",");
                    //PAT_INSST
                    sw.Write(",");
                    //PAT_INSZIP
                    sw.Write(",");
                }
                else
                {
                    //PAT_INSPHN
                    sw.Write(tidy(carrier.CarrierName) + ",");
                    //PAT_INSNM
                    sw.Write(tidy(plan.GroupName) + ",");
                    //PAT_INSGPL Ins group plan name
                    sw.Write(tidy(carrier.Address) + ",");
                    //PAT_INSAD1
                    sw.Write(tidy(carrier.Address2) + ",");
                    //PAT_INSAD2
                    sw.Write(tidy(carrier.City) + ",");
                    //PAT_INSCIT
                    sw.Write(tidy(carrier.State) + ",");
                    //PAT_INSST
                    sw.Write(tidy(carrier.Zip) + ",");
                    //PAT_INSZIP
                    sw.Write(TelephoneNumbers.formatNumbersOnly(carrier.Phone) + ",");
                } 
                //PAT_INSPHN
                sw.Write(",");
                //PAT_INSEXT
                sw.Write(",");
                //PAT_INSCON Ins contact person
                if (plan == null)
                {
                    sw.Write(",");
                }
                else
                {
                    sw.Write(tidy(plan.GroupNum) + ",");
                } 
                //PAT_INSGNO Ins group number
                if (emp == null)
                {
                    sw.Write(",");
                }
                else
                {
                    //PAT_EMPNM
                    sw.Write(tidy(emp.EmpName) + ",");
                } 
                //PAT_EMPNM
                sw.Write(",");
                //PAT_EMPAD1
                sw.Write(",");
                //PAT_EMPAD2
                sw.Write(",");
                //PAT_EMPCIT
                sw.Write(",");
                //PAT_EMPST
                sw.Write(",");
                //PAT_EMPZIP
                sw.Write(",");
                //PAT_EMPPHN
                /*we don't support employer info yet.
                				sw.Write(Tidy(emp.Address)+",");//PAT_EMPAD1
                				sw.Write(Tidy(emp.Address2)+",");//PAT_EMPAD2
                				sw.Write(Tidy(emp.City)+",");//PAT_EMPCIT
                				sw.Write(Tidy(emp.State)+",");//PAT_EMPST
                				sw.Write(Tidy(emp.State)+",");//PAT_EMPZIP
                				sw.Write(TelephoneNumbers.FormatNumbersOnly(emp.Phone)+",");//PAT_EMPPHN*/
                Referral referral = Referrals.getReferralForPat(pat.PatNum);
                //could be null
                if (referral == null)
                {
                    sw.Write(",");
                    //PAT_REFLNM
                    sw.Write(",");
                    //PAT_REFFNM
                    sw.Write(",");
                    //PAT_REFMI
                    sw.Write(",");
                    //PAT_REFPHN
                    sw.Write(",");
                    //PAT_REFEML Referral source email
                    sw.Write(",");
                }
                else
                {
                    //PAT_REFSPE Referral specialty. Customizable, so any allowed
                    sw.Write(tidy(referral.LName) + ",");
                    //PAT_REFLNM
                    sw.Write(tidy(referral.FName) + ",");
                    //PAT_REFFNM
                    sw.Write(tidy(referral.MName) + ",");
                    //PAT_REFMI
                    sw.Write(referral.Telephone + ",");
                    //PAT_REFPHN
                    sw.Write(tidy(referral.EMail) + ",");
                    //PAT_REFEML Referral source email
                    if (referral.PatNum == 0 && !referral.NotPerson)
                    {
                        //not a patient, and is a person
                        sw.Write(referral.Specialty.ToString());
                    }
                     
                    sw.Write(",");
                } 
                //PAT_REFSPE Referral specialty. Customizable, so any allowed
                sw.Write(",");
                //PAT_NOTES No limits.  We won't use this right now for exports.
                //sw.Write(",");//PAT_NOTE1-PAT_NOTE10 skipped
                sw.Write(",");
                //PAT_FPSCAN Internal PT logical, it can be ignored.
                if (pat.Premed)
                {
                    sw.Write("1");
                }
                else
                {
                    sw.Write("0");
                } 
                sw.Write(",");
                //PAT_PREMED F or T
                sw.Write(tidy(pat.MedUrgNote) + ",");
                //PAT_MEDS The meds that they must premedicate with.
                if (StringSupport.equals(pat.StudentStatus, "F"))
                {
                    //fulltime
                    sw.Write("1");
                }
                else
                {
                    sw.Write("0");
                } 
                sw.Write(",");
                //PAT_FTSTUD T/F
                if (StringSupport.equals(pat.StudentStatus, "P"))
                {
                    //parttime
                    sw.Write("1");
                }
                else
                {
                    sw.Write("0");
                } 
                sw.Write(",");
                //PAT_PTSTUD
                sw.Write(tidy(pat.SchoolName) + ",");
                //PAT_COLLEG Name of college
                sw.Write(tidy(pat.ChartNumber) + ",");
                //PAT_CHRTNO
                sw.Write(pat.PatNum.ToString() + ",");
                //PAT_OTHID The primary key in Open Dental ************IMPORTANT***************
                if (pat.PatNum == pat.Guarantor)
                {
                    sw.Write("1");
                }
                else
                {
                    sw.Write("0");
                } 
                sw.Write(",");
                //PAT_RESPRT Responsible party checkbox T/F
                if (plan != null && pat.PatNum == sub.Subscriber)
                {
                    //if current patient is the subscriber on their primary plan
                    sw.Write("1");
                }
                else
                {
                    sw.Write("0");
                } 
                sw.Write(",");
                //PAT_POLHLD Policy holder checkbox T/F
                sw.Write(",");
                //PAT_CUSCD Web sync folder, used internally this can be ignored.
                sw.Write("");
                //PAT_PMPID Practice Management Program ID. Can be ignored
                sw.WriteLine();
            }
        }
        finally
        {
            if (sw != null)
                Disposable.mkDisposable(sw).dispose();
             
        }
        try
        {
            if (isUpdate)
            {
                Process.Start(dir + "\\" + exportUpdateExe);
            }
            else
            {
                //already validated to exist
                Process.Start(dir + "\\" + exportAddExe);
            } 
        }
        catch (Exception ex)
        {
            //already validated to exist
            //MessageBox.Show("done");
            MessageBox.Show(ex.Message);
        }
    
    }

    /**
    * removes commas.
    */
    private static String tidy(String str) throws Exception {
        String retval = str.Replace(",", "");
        retval = retval.Replace("\r", "");
        retval = retval.Replace("\n", "");
        retval = retval.Replace("\t", "");
        return retval;
    }

}


/*
		///<summary>removes commas, dashes, parentheses, and spaces.  It would be better to use regex to strip all non-numbers.</summary>
		private static string TidyNumber(string str) {
			//Regex.
			str=str.Replace(",","");
			str=str.Replace("-","");
			str=str.Replace("(","");
			str=str.Replace(")","");
			str=str.Replace(" ","");
			return str;
		}*/