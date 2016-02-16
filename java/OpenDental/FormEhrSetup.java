//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDental.DataValid;
import OpenDental.FormAllergySetup;
import OpenDental.FormCodeSystemsImport;
import OpenDental.FormDrugManufacturerSetup;
import OpenDental.FormDrugUnitSetup;
import OpenDental.FormEduResourceSetup;
import OpenDental.FormEhrQuarterlyKeys;
import OpenDental.FormEhrSettings;
import OpenDental.FormEhrTimeSynch;
import OpenDental.FormEhrTriggers;
import OpenDental.FormEmailSetupEHR;
import OpenDental.FormIcd9s;
import OpenDental.FormLoincs;
import OpenDental.FormOIDRegistryInternal;
import OpenDental.FormReminderRules;
import OpenDental.FormRxNorms;
import OpenDental.FormSnomeds;
import OpenDental.FormVaccineDefSetup;
import OpenDental.Lan;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.Security;
import OpenDental.FormEhrSetup;

public class FormEhrSetup  extends Form 
{

    public FormEhrSetup() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formEhrSetup_Load(Object sender, EventArgs e) throws Exception {
        if (PrefC.getBool(PrefName.EhrEmergencyNow))
        {
            panelEmergencyNow.BackColor = Color.Red;
        }
        else
        {
            panelEmergencyNow.BackColor = SystemColors.Control;
        } 
        if (!Security.isAuthorized(Permissions.Setup,true))
        {
            //Hide all the buttons except Emergency Now and Close.
            //Unhiding all code system buttons since code systems can no longer be edited.
            butAllergies.Visible = false;
            //Forumularies will now be checked through New Crop
            //butFormularies.Visible=false;
            butVaccineDef.Visible = false;
            butDrugManufacturer.Visible = false;
            butDrugUnit.Visible = false;
            butInboundEmail.Visible = false;
            butReminderRules.Visible = false;
            butEducationalResources.Visible = false;
            butKeys.Visible = false;
            menuItemSettings.Enabled = false;
            butTimeSynch.Visible = false;
            butEhrTriggers.Visible = false;
        }
         
    }

    private void menuItemSettings_Click(Object sender, EventArgs e) throws Exception {
        FormEhrSettings FormES = new FormEhrSettings();
        FormES.ShowDialog();
    }

    private void butICD9s_Click(Object sender, EventArgs e) throws Exception {
        FormIcd9s FormE = new FormIcd9s();
        FormE.ShowDialog();
    }

    private void butAllergies_Click(Object sender, EventArgs e) throws Exception {
        FormAllergySetup FAS = new FormAllergySetup();
        FAS.ShowDialog();
    }

    //Formularies will now be checked through New Crop
    //private void butFormularies_Click(object sender,EventArgs e) {
    //	FormFormularies FormE=new FormFormularies();
    //	FormE.ShowDialog();
    //}
    private void butVaccineDef_Click(Object sender, EventArgs e) throws Exception {
        FormVaccineDefSetup FormE = new FormVaccineDefSetup();
        FormE.ShowDialog();
    }

    private void butDrugManufacturer_Click(Object sender, EventArgs e) throws Exception {
        FormDrugManufacturerSetup FormE = new FormDrugManufacturerSetup();
        FormE.ShowDialog();
    }

    private void butDrugUnit_Click(Object sender, EventArgs e) throws Exception {
        FormDrugUnitSetup FormE = new FormDrugUnitSetup();
        FormE.ShowDialog();
    }

    private void butInboundEmail_Click(Object sender, EventArgs e) throws Exception {
        FormEmailSetupEHR FormES = new FormEmailSetupEHR();
        FormES.ShowDialog();
    }

    private void butEmergencyNow_Click(Object sender, EventArgs e) throws Exception {
        if (PrefC.getBool(PrefName.EhrEmergencyNow))
        {
            panelEmergencyNow.BackColor = SystemColors.Control;
            Prefs.updateBool(PrefName.EhrEmergencyNow,false);
        }
        else
        {
            panelEmergencyNow.BackColor = Color.Red;
            Prefs.updateBool(PrefName.EhrEmergencyNow,true);
        } 
        DataValid.setInvalid(InvalidType.Prefs);
    }

    private void butReminderRules_Click(Object sender, EventArgs e) throws Exception {
        FormReminderRules FormRR = new FormReminderRules();
        FormRR.ShowDialog();
    }

    private void butEducationalResources_Click(Object sender, EventArgs e) throws Exception {
        FormEduResourceSetup FormEDUSetup = new FormEduResourceSetup();
        FormEDUSetup.ShowDialog();
    }

    private void butRxNorm_Click(Object sender, EventArgs e) throws Exception {
        FormRxNorms FormR = new FormRxNorms();
        FormR.ShowDialog();
    }

    private void butLoincs_Click(Object sender, EventArgs e) throws Exception {
        FormLoincs FormL = new FormLoincs();
        FormL.ShowDialog();
    }

    private void butSnomeds_Click(Object sender, EventArgs e) throws Exception {
        FormSnomeds FormS = new FormSnomeds();
        FormS.ShowDialog();
    }

    //private void button1_Click(object sender,EventArgs e) {
    //	Random rand=new Random();
    //	FormCDSIntervention FormCDSI=new FormCDSIntervention();
    //	FormCDSI.DictEhrTriggerResults=EhrTriggers.TriggerMatch(ICD9s.GetOne(rand.Next(8000)),Patients.GetPat(1));
    //	FormCDSI.ShowIfRequired();
    //	if(FormCDSI.DialogResult==DialogResult.Cancel) {//using ==DialogResult.Cancel instead of !=DialogResult.OK
    //		MsgBox.Show(this,"You have disarmed the nuke.");
    //		return;//effectively canceling the action.
    //	}
    //	MsgBox.Show(this,"Yay, you did it.");
    //	//stuff you might do, like prescribe meds or diagnose problem.
    //}
    //		private void butICD9CM31_Click(object sender,EventArgs e) {
    //			if(!MsgBox.Show(this,MsgBoxButtons.OKCancel,"This button does not perform error checking and only works from Ryan's computer.")) {
    //				return;
    //			}
    //			Cursor=Cursors.WaitCursor;
    //			try {
    //				//Import ICD9-CM DX codes with long description----------------------------------------------------------------------------------------------------------
    //				MsgBox.Show(this,"Importing ICD9-CM DX codes with long description.");
    //				System.IO.StreamReader sr=new System.IO.StreamReader(@"C:\Users\Ryan\Desktop\ICD9CM31\DXL.txt");
    //				string command=@"DROP TABLE IF EXISTS ICD9CMDX";
    //				DataCore.NonQ(command);
    //				command=@"CREATE TABLE `ICD9CMDX` (
    //									`ICD9CMDXNum` BIGINT(20) NOT NULL AUTO_INCREMENT,
    //									`CodeString` VARCHAR(255) DEFAULT '',
    //									`DescriptionLong` VARCHAR(255) DEFAULT '',
    //									`DescriptionShort` VARCHAR(255) DEFAULT '',
    //									INDEX(CodeString),
    //									PRIMARY KEY (`ICD9CMDXNum`)
    //									) DEFAULT CHARSET=utf8;";
    //				DataCore.NonQ(command);
    //				string[] arrayICD9CM=new string[2];
    //				string line="";
    //				while(!sr.EndOfStream) {//each loop should read exactly one line of code.
    //					line=sr.ReadLine();
    //					arrayICD9CM[0]=line.Substring(0,5).Trim();//fixed width column
    //					if(arrayICD9CM[0].Length>3){
    //						arrayICD9CM[0]=arrayICD9CM[0].Substring(0,3)+"."+arrayICD9CM[0].Substring(3);
    //					}
    //					arrayICD9CM[1]=line.Substring(6);
    //					command="INSERT INTO ICD9CMDX (CodeString,DescriptionLong) VALUES ('"+POut.String(arrayICD9CM[0])+"','"+POut.String(arrayICD9CM[1])+"')";
    //					DataCore.NonQ(command);
    //				}
    //				//Import ICD9-CM DX codes with short description----------------------------------------------------------------------------------------------------------
    //				MsgBox.Show(this,"Importing ICD9-CM DX codes with short description.");
    //				sr.Dispose();
    //				sr=new System.IO.StreamReader(@"C:\Users\Ryan\Desktop\ICD9CM31\DXS.txt");
    //				while(!sr.EndOfStream) {//each loop should read exactly one line of code.
    //					line=sr.ReadLine();
    //					arrayICD9CM[0]=line.Substring(0,5).Trim();//fixed width column
    //					if(arrayICD9CM[0].Length>3) {
    //						arrayICD9CM[0]=arrayICD9CM[0].Substring(0,3)+"."+arrayICD9CM[0].Substring(3);
    //					}
    //					arrayICD9CM[1]=line.Substring(6);
    //					command="UPDATE ICD9CMDX SET DescriptionShort='"+POut.String(arrayICD9CM[1])+"' WHERE CodeString='"+POut.String(arrayICD9CM[0])+"'";
    //					DataCore.NonQ(command);
    //				}
    //				//Import ICD9-CM SG codes with long description----------------------------------------------------------------------------------------------------------
    //				MsgBox.Show(this,"Importing ICD9-CM SG codes with long description.");
    //				sr.Dispose();
    //				sr=new System.IO.StreamReader(@"C:\Users\Ryan\Desktop\ICD9CM31\SGL.txt");
    //				command=@"DROP TABLE IF EXISTS ICD9CMSG";
    //				DataCore.NonQ(command);
    //				command=@"CREATE TABLE `ICD9CMSG` (
    //									`ICD9CMSGNum` BIGINT(20) NOT NULL AUTO_INCREMENT,
    //									`CodeString` VARCHAR(255) DEFAULT '',
    //									`DescriptionLong` VARCHAR(255) DEFAULT '',
    //									`DescriptionShort` VARCHAR(255) DEFAULT '',
    //									INDEX(CodeString),
    //									PRIMARY KEY (`ICD9CMSGNum`)
    //									) DEFAULT CHARSET=utf8;";
    //				DataCore.NonQ(command);
    //				while(!sr.EndOfStream) {//each loop should read exactly one line of code.
    //					line=sr.ReadLine();
    //					arrayICD9CM[0]=line.Substring(0,5).Trim();//fixed width column
    //					if(arrayICD9CM[0].Length>2) {
    //						arrayICD9CM[0]=arrayICD9CM[0].Substring(0,2)+"."+arrayICD9CM[0].Substring(2);
    //					}
    //					arrayICD9CM[1]=line.Substring(5);
    //					command="INSERT INTO ICD9CMSG (CodeString,DescriptionLong) VALUES ('"+POut.String(arrayICD9CM[0])+"','"+POut.String(arrayICD9CM[1])+"')";
    //					DataCore.NonQ(command);
    //				}
    //				//Import ICD9-CM SG codes with short description----------------------------------------------------------------------------------------------------------
    //				MsgBox.Show(this,"Importing ICD9-CM SG codes with short description.");
    //				sr.Dispose();
    //				sr=new System.IO.StreamReader(@"C:\Users\Ryan\Desktop\ICD9CM31\SGS.txt");
    //				while(!sr.EndOfStream) {//each loop should read exactly one line of code.
    //					line=sr.ReadLine();
    //					arrayICD9CM[0]=line.Substring(0,5).Trim();//fixed width column
    //					if(arrayICD9CM[0].Length>2) {
    //						arrayICD9CM[0]=arrayICD9CM[0].Substring(0,2)+"."+arrayICD9CM[0].Substring(2);
    //					}
    //					arrayICD9CM[1]=line.Substring(5);
    //					command="UPDATE ICD9CMSG SET DescriptionShort='"+POut.String(arrayICD9CM[1])+"' WHERE CodeString='"+POut.String(arrayICD9CM[0])+"'";
    //					DataCore.NonQ(command);
    //				}
    //			}
    //			catch(Exception ex) {
    //				MessageBox.Show(this,Lan.g(this,"Error importing ICD9CM codes:")+"\r\n"+ex.Message);
    //			}
    //			Cursor=Cursors.Default;
    //		}
    private void butTimeSynch_Click(Object sender, EventArgs e) throws Exception {
        FormEhrTimeSynch formET = new FormEhrTimeSynch();
        formET.ShowDialog();
    }

    private void butKeys_Click(Object sender, EventArgs e) throws Exception {
        FormEhrQuarterlyKeys formK = new FormEhrQuarterlyKeys();
        formK.ShowDialog();
    }

    private void butCodeImport_Click(Object sender, EventArgs e) throws Exception {
        FormCodeSystemsImport FormCSI = new FormCodeSystemsImport();
        FormCSI.ShowDialog();
    }

    //private void butEHRCodes_Click(object sender,EventArgs e) {
    //	if(!MsgBox.Show(this,MsgBoxButtons.OKCancel,"This will import codes required for CQMs to function properly, and may take up to 5 minutes.  Before running this tool, you should use the code system import tool.")) {
    //		return;
    //	}
    //	Cursor=Cursors.WaitCursor;
    //	//request URL from webservice
    //	string result="";
    //	try {
    //		result=SendAndReceiveDownloadXml("EHRCODES");
    //	}
    //	catch(Exception ex) {
    //		Cursor=Cursors.Default;
    //		MessageBox.Show("Error: "+ex.Message);
    //		return;
    //	}
    //	XmlDocument doc=new XmlDocument();
    //	doc.LoadXml(result);
    //	XmlNode node=doc.SelectSingleNode("//Error");
    //	if(node!=null) {
    //		throw new Exception(node.InnerText);
    //	}
    //	node=doc.SelectSingleNode("//CodeSystemURL");
    //	string codeSystemURL="";
    //	if(node!=null) {
    //		codeSystemURL=node.InnerText;
    //	}
    //	//Download File to local machine
    //	Thread.Sleep(2000);//wait 2 seconds between downloads.
    //	string tempFile="";
    //	int newCodeCount=0;
    //	int totalCodeCount=0;
    //	int availableCodeCount=0;
    //	try {
    //		tempFile=downloadFileHelper(codeSystemURL,"EHRCODES");//shows progress bar.
    //		CodeSystems.ImportEhrCodes(tempFile,out newCodeCount,out totalCodeCount,out availableCodeCount);
    //	}
    //	catch(Exception ex) {
    //		Cursor=Cursors.Default;//Just in case.
    //		MessageBox.Show(Lan.g(this,"Error encountered while setting up codes required for EHR")+":\r\n"+ex.Message);
    //	}
    //	Cursor=Cursors.Default;
    //	if(totalCodeCount<availableCodeCount) {
    //		MessageBox.Show(newCodeCount+" new codes imported.  The database now contains "+totalCodeCount+" out of "+availableCodeCount+" total available codes.  To import additional codes, you must import the corresponding code systems using the code system import tool.");
    //	}
    //	else {
    //		MessageBox.Show(newCodeCount+" new codes imported.  The database now all "+availableCodeCount+" available codes.");
    //	}
    //}
    private static String downloadFileHelper(String codeSystemURL, String codeSystemName) throws Exception {
        String zipFileDestination = Path.GetTempFileName();
        //@"c:\\users\ryan\desktop\"+codeSystemName+".txt";
        File.Delete(zipFileDestination);
        WebRequest wr = WebRequest.Create(codeSystemURL);
        WebResponse webResp = null;
        try
        {
            webResp = wr.GetResponse();
        }
        catch (Exception ex)
        {
            return null;
        }

        downloadFileWorker(codeSystemURL,zipFileDestination);
        Thread.Sleep(100);
        //allow file to be released for use by the unzipper.
        //Unzip the compressed file-----------------------------------------------------------------------------------------------------
        MemoryStream ms = new MemoryStream();
        ZipFile unzipped = ZipFile.Read(zipFileDestination);
        try
        {
            {
                ZipEntry ze = unzipped[0];
                ze.Extract(Path.GetTempPath(), ExtractExistingFileAction.OverwriteSilently);
                return Path.GetTempPath() + unzipped[0].FileName;
            }
        }
        finally
        {
            if (unzipped != null)
                Disposable.mkDisposable(unzipped).dispose();
             
        }
    }

    /**
    * This is the function that the worker thread uses to actually perform the download.  Can also call this method in the ordinary way if the file to be transferred is short.
    */
    private static void downloadFileWorker(String downloadUri, String destinationPath) throws Exception {
        int chunk = 10;
        //KB
        byte[] buffer = new byte[]();
        int i = 0;
        WebClient myWebClient = new WebClient();
        Stream readStream = myWebClient.OpenRead(downloadUri);
        BinaryReader br = new BinaryReader(readStream);
        FileStream writeStream = new FileStream(destinationPath, FileMode.Create);
        BinaryWriter bw = new BinaryWriter(writeStream);
        try
        {
            while (true)
            {
                buffer = br.ReadBytes(chunk * 1024);
                if (buffer.Length == 0)
                {
                    break;
                }
                 
                bw.Write(buffer);
                i++;
            }
        }
        catch (Exception __dummyCatchVar0)
        {
            //for instance, if abort.
            br.Close();
            bw.Close();
            File.Delete(destinationPath);
        }
        finally
        {
            br.Close();
            bw.Close();
        }
    }

    //myWebClient.DownloadFile(downloadUri,ODFileUtils.CombinePaths(FormPath.GetPreferredImagePath(),"Setup.exe"));
    private static String sendAndReceiveDownloadXml(String codeSystemName) throws Exception {
        //prepare the xml document to send--------------------------------------------------------------------------------------
        XmlWriterSettings settings = new XmlWriterSettings();
        settings.Indent = true;
        settings.IndentChars = ("    ");
        StringBuilder strbuild = new StringBuilder();
        XmlWriter writer = XmlWriter.Create(strbuild, settings);
        try
        {
            {
                //TODO: include more user information
                writer.WriteStartElement("UpdateRequest");
                writer.WriteStartElement("RegistrationKey");
                writer.WriteString(PrefC.getString(PrefName.RegistrationKey));
                writer.WriteEndElement();
                writer.WriteStartElement("PracticeTitle");
                writer.WriteString(PrefC.getString(PrefName.PracticeTitle));
                writer.WriteEndElement();
                writer.WriteStartElement("PracticeAddress");
                writer.WriteString(PrefC.getString(PrefName.PracticeAddress));
                writer.WriteEndElement();
                writer.WriteStartElement("PracticePhone");
                writer.WriteString(PrefC.getString(PrefName.PracticePhone));
                writer.WriteEndElement();
                writer.WriteStartElement("ProgramVersion");
                writer.WriteString(PrefC.getString(PrefName.ProgramVersion));
                writer.WriteEndElement();
                writer.WriteStartElement("CodeSystemRequested");
                writer.WriteString(codeSystemName);
                writer.WriteEndElement();
                writer.WriteEndElement();
            }
        }
        finally
        {
            if (writer != null)
                Disposable.mkDisposable(writer).dispose();
             
        }
        OpenDental.customerUpdates.Service1 updateService = new OpenDental.customerUpdates.Service1();
        updateService.setUrl(PrefC.getString(PrefName.UpdateServerAddress));
        if (!StringSupport.equals(PrefC.getString(PrefName.UpdateWebProxyAddress), ""))
        {
            IWebProxy proxy = new WebProxy(PrefC.getString(PrefName.UpdateWebProxyAddress));
            ICredentials cred = new NetworkCredential(PrefC.getString(PrefName.UpdateWebProxyUserName), PrefC.getString(PrefName.UpdateWebProxyPassword));
            proxy.Credentials = cred;
            updateService.Proxy = proxy;
        }
         
        String result = "";
        try
        {
            result = updateService.RequestCodeSystemDownload(strbuild.ToString());
        }
        catch (Exception ex)
        {
            //may throw error
            //Cursor=Cursors.Default;
            MessageBox.Show("Error: " + ex.Message);
            return "";
        }

        return result;
    }

    private void butEhrTriggers_Click(Object sender, EventArgs e) throws Exception {
        FormEhrTriggers FormET = new FormEhrTriggers();
        FormET.ShowDialog();
    }

    private void butOIDs_Click(Object sender, EventArgs e) throws Exception {
        FormOIDRegistryInternal FormOIDI = new FormOIDRegistryInternal();
        FormOIDI.ShowDialog();
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private List<String> getTestMessagesHelper() throws Exception {
        List<String> retVal = new List<String>();
        retVal.Add("MSH|^~\\&#|NIST Test Lab APP^2.16.840.1.113883.3.72.5.20^ISO|NIST Lab Facility^2.16.840.1.113883.3.72.5.21^ISO||NIST EHR Facility^2.16.840.1.113883.3.72.5.23^ISO|20110531140551-0500||ORU^R01^ORU_R01|NIST-LRI-GU-001.00|T|2.5.1|||AL|NE|||||LRI_Common_Component^Profile Component^2.16.840.1.113883.9.16^ISO~LRI_GU_Component^Profile Component^2.16.840.1.113883.9.12^ISO~LRI_RU_Component^Profile Component^2.16.840.1.113883.9.14^ISO\r\n" + 
        "PID|1||PATID1234^^^NIST MPI&2.16.840.1.113883.3.72.5.30.2&ISO^MR||Jones^William^A^JR^^^L||19610615|M||2106-3^White^HL70005^CAUC^Caucasian^L\r\n" + 
        "ORC|RE|ORD723222^NIST EHR^2.16.840.1.113883.3.72.5.24^ISO|R-783274^NIST Lab Filler^2.16.840.1.113883.3.72.5.25^ISO|GORD874211^NIST EHR^2.16.840.1.113883.3.72.5.24^ISO||||||||57422^Radon^Nicholas^M^JR^DR^^^NIST-AA-1&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI\r\n" + 
        "OBR|1|ORD723222^NIST EHR^2.16.840.1.113883.3.72.5.24^ISO|R-783274^NIST Lab Filler^2.16.840.1.113883.3.72.5.25^ISO|30341-2^Erythrocyte sedimentation rate^LN^815115^Erythrocyte sedimentation rate^99USI^^^Erythrocyte sedimentation rate|||20110331140551-0800||||L||7520000^fever of unknown origin^SCT^22546000^fever, origin unknown^99USI^^^Fever of unknown origin|||57422^Radon^Nicholas^M^JR^DR^^^NIST-AA-1&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI||||||20110331160428-0800|||F|||10092^Hamlin^Pafford^M^Sr.^Dr.^^^NIST-AA-1&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI|||||||||||||||||||||CC^Carbon Copy^HL70507^C^Send Copy^L^^^Copied Requested\r\n" + 
        "NTE|1||Patient is extremely anxious about needles used for drawing blood.\r\n" + 
        "TQ1|1||||||20110331150028-0800|20110331152028-0800\r\n" + 
        "OBX|1|NM|30341-2^Erythrocyte sedimentation rate^LN^815117^ESR^99USI^^^Erythrocyte sedimentation rate||10|mm/h^millimeter per hour^UCUM|0 to 17|N|||F|||20110331140551-0800|||||20110331150551-0800||||Century Hospital^^^^^NIST-AA-1&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^USA^B^^06037|2343242^Knowsalot^Phil^J.^III^Dr.^^^NIST-AA-1&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "SPM|1|||119297000^BLD^SCT^BldSpc^Blood^99USA^^^Blood Specimen|||||||||||||20110331140551-0800|||||||COOL^Cool^HL70493^CL^Cool^99USA^^^Cool");
        retVal.Add("MSH|^~\\&#|NIST Test Lab APP^2.16.840.1.113883.3.72.5.20^ISO|NIST Lab Facility^2.16.840.1.113883.3.72.5.21^ISO||NIST EHR Facility^2.16.840.1.113883.3.72.5.23^ISO|20110531140551-0500||ORU^R01^ORU_R01|NIST-LRI-GU-001.01|T|2.5.1|||AL|NE|||||LRI_Common_Component^Profile Component^2.16.840.1.113883.9.16^ISO~LRI_GU_Component^Profile Component^2.16.840.1.113883.9.12^ISO~LRI_RU_Component^Profile Component^2.16.840.1.113883.9.14^ISO\r\n" + 
        "PID|1||PATID1234^^^NIST MPI&2.16.840.1.113883.3.72.5.30.2&ISO^MR||Jones^William^A^JR^^^L||19610615|M||2106-3^White^HL70005^CAUC^Caucasian^L\r\n" + 
        "ORC|RE|ORD723222^NIST EHR^2.16.840.1.113883.3.72.5.24^ISO|R-783274^NIST Lab Filler^2.16.840.1.113883.3.72.5.25^ISO|GORD874211^NIST EHR^2.16.840.1.113883.3.72.5.24^ISO||||||||57422^Radon^Nicholas^M^JR^DR^^^NIST-AA-1&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI\r\n" + 
        "OBR|1|ORD723222^NIST EHR^2.16.840.1.113883.3.72.5.24^ISO|R-783274^NIST Lab Filler^2.16.840.1.113883.3.72.5.25^ISO|30341-2^Erythrocyte sedimentation rate^LN^815115^Erythrocyte sedimentation rate^99USI^^^Erythrocyte sedimentation rate|||20110331140551-0800||||L||7520000^fever of unknown origin^SCT^22546000^fever, origin unknown^99USI^^^Fever of unknown origin|||57422^Radon^Nicholas^M^JR^DR^^^NIST-AA-1&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI||||||20110331160428-0800|||C|||10092^Hamlin^Pafford^M^Sr.^Dr.^^^NIST-AA-1&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI|||||||||||||||||||||CC^Carbon Copy^HL70507^C^Send Copy^L^^^Copied Requested\r\n" + 
        "NTE|1||Patient is extremely anxious about needles used for drawing blood.\r\n" + 
        "TQ1|1||||||20110331150028-0800|20110331152028-0800\r\n" + 
        "OBX|1|NM|30341-2^Erythrocyte sedimentation rate^LN^815117^ESR^99USI^^^Erythrocyte sedimentation rate||20|mm/h^millimeter per hour^UCUM|0 to 17|H|||C|||20110331140551-0800|||||20110331150551-0800||||Century Hospital^^^^^NIST-AA-1&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^USA^B^^06037|2343242^Knowsalot^Phil^J.^III^Dr.^^^NIST-AA-1&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "NTE|1||Specimen re-analyzed per request of ordering provider.\r\n" + 
        "SPM|1|||119297000^BLD^SCT^BldSpc^Blood^99USA^^^Blood Specimen|||||||||||||20110331140551-0800|||||||COOL^Cool^HL70493^CL^Cool^99USA^^^Cool");
        retVal.Add("MSH|^~\\&|^2.16.840.1.113883.3.72.5.20^ISO|^2.16.840.1.113883.3.72.5.21^ISO||^2.16.840.1.113883.3.72.5.23^ISO|20110531140551-0500||ORU^R01^ORU_R01|NIST-LRI-GU-001.04|T|2.5.1|||AL|NE|||||LRI_Common_Component^^2.16.840.1.113883.9.16^ISO~LRI_GU_Component^^2.16.840.1.113883.9.12^ISO~LRI_RU_Component^^2.16.840.1.113883.9.14^ISO\r\n" + 
        "PID|1||PATID1236^^^&2.16.840.1.113883.3.72.5.30.2&ISO^MR||Anderson^Janet||19860930|F||2106-3^White^HL70005\r\n" + 
        "ORC|RE|ORD723222-1^^2.16.840.1.113883.3.72.5.24^ISO|R-783274-1^^2.16.840.1.113883.3.72.5.25^ISO|GORD874222^^2.16.840.1.113883.3.72.5.24^ISO||||||||57422^Radon^Nicholas^^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI\r\n" + 
        "OBR|1|ORD723222-1^^2.16.840.1.113883.3.72.5.24^ISO|R-783274-1^^2.16.840.1.113883.3.72.5.25^ISO|30341-2^Erythrocyte sedimentation rate^LN^815115^Erythrocyte sedimentation rate^99USI^^^Erythrocyte sedimentation rate|||20110331140551-0800|||||||||57422^Radon^Nicholas^^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI||||||20110331160428-0800|||X\r\n" + 
        "SPM|1|||119297000^BLD^SCT^BldSpc^Blood^99USA^^^Blood Specimen|||||||||||||20110331140551-0800||||QS^Quantity not sufficient^HL70490^NSQ^Not sufficient quantity^99USA^^^Quantity not sufficient|||COOL^Cool^HL70493^CL^Cool^99USA^^^Cool");
        retVal.Add("MSH|^~\\&|^2.16.840.1.113883.3.72.5.20^ISO|^2.16.840.1.113883.3.72.5.21^ISO||^2.16.840.1.113883.3.72.5.23^ISO|20110531140551-0500||ORU^R01^ORU_R01|NIST-LRI-GU-002.00|T|2.5.1|||AL|NE|||||LRI_Common_Component^^2.16.840.1.113883.9.16^ISO~LRI_GU_Component^^2.16.840.1.113883.9.12^ISO~LRI_RU_Component^^2.16.840.1.113883.9.14^ISO\r\n" + 
        "PID|1||PATID1234^^^&2.16.840.1.113883.3.72.5.30.2&ISO^MR||Jones^William^A||19610615|M||2106-3^White^HL70005\r\n" + 
        "ORC|RE|ORD666555^^2.16.840.1.113883.3.72.5.24^ISO|R-991133^^2.16.840.1.113883.3.72.5.25^ISO|GORD874233^^2.16.840.1.113883.3.72.5.24^ISO||||||||57422^Radon^Nicholas^^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI\r\n" + 
        "OBR|1|ORD666555^^2.16.840.1.113883.3.72.5.24^ISO|R-991133^^2.16.840.1.113883.3.72.5.25^ISO|57021-8^CBC W Auto Differential panel in Blood^LN^4456544^CBC^99USI^^^CBC W Auto Differential panel in Blood|||20110103143428-0800|||||||||57422^Radon^Nicholas^^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI||||||20110104170028-0800|||F|||10093^Deluca^Naddy^^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI|||||||||||||||||||||CC^Carbon Copy^HL70507\r\n" + 
        "OBX|1|NM|26453-1^Erythrocytes [#/volume] in Blood^LN^^^^^^Erythrocytes [#/volume] in Blood||4.41|10*6/uL^million per microliter^UCUM|4.3 to 6.2|N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "OBX|2|NM|718-7^Hemoglobin [Mass/volume] in Blood^LN^^^^^^Hemoglobin [Mass/volume] in Blood||12.5|g/mL^grams per milliliter^UCUM|13 to 18|L|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "OBX|3|NM|20570-8^Hematocrit [Volume Fraction] of Blood^LN^^^^^^Hematocrit [Volume Fraction] of Blood||41|%^percent^UCUM|40 to 52|N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "OBX|4|NM|26464-8^Leukocytes [#/volume] in Blood^LN^^^^^^Leukocytes [#/volume] in Blood||105600|{cells}/uL^cells per microliter^UCUM|4300 to 10800|HH|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "OBX|5|NM|26515-7^Platelets [#/volume] in Blood^LN^^^^^^Platelets [#/volume] in Blood||210000|{cells}/uL^cells per microliter^UCUM|150000 to 350000|N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "OBX|6|NM|30428-7^Erythrocyte mean corpuscular volume [Entitic volume]^LN^^^^^^Erythrocyte mean corpuscular volume [Entitic volume]||91|fL^femtoliter^UCUM|80 to 95|N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "OBX|7|NM|28539-5^Erythrocyte mean corpuscular hemoglobin [Entitic mass]^LN^^^^^^Erythrocyte mean corpuscular hemoglobin [Entitic mass]||29|pg/{cell}^picograms per cell^UCUM|27 to 31|N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "OBX|8|NM|28540-3^Erythrocyte mean corpuscular hemoglobin concentration [Mass/volume]^LN^^^^^^Erythrocyte mean corpuscular hemoglobin concentration [Mass/volume]||32.4|g/dL^grams per deciliter^UCUM|32 to 36|N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "OBX|9|NM|30385-9^Erythrocyte distribution width [Ratio]^LN^^^^^^Erythrocyte distribution width [Ratio]||10.5|%^percent^UCUM|10.2 to 14.5|N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "OBX|10|NM|26444-0^Basophils [#/volume] in Blood^LN^^^^^^Basophils [#/volume] in Blood||0.1|10*3/uL^thousand per microliter^UCUM|0 to 0.3|N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "OBX|11|NM|30180-4^Basophils/100 leukocytes in Blood^LN^^^^^^Basophils/100 leukocytes in Blood||0.1|%^percent^UCUM|0 to 2|N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "OBX|12|NM|26484-6^Monocytes [#/volume] in Blood^LN^^^^^^Monocytes [#/volume] in Blood||3|10*3/uL^thousand per microliter^UCUM|0.0 to 13.0|N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "OBX|13|NM|26485-3^Monocytes/100 leukocytes in Blood^LN^^^^^^Monocytes/100 leukocytes in Blood||3|%^percent^UCUM|0 to 10|N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "OBX|14|NM|26449-9^Eosinophils [#/volume] in Blood^LN^^^^^^Eosinophils [#/volume] in Blood||2.1|10*3/uL^thousand per microliter^UCUM|0.0 to 0.45|HH|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "OBX|15|NM|26450-7^Eosinophils/100 leukocytes in Blood^LN^^^^^^Eosinophils/100 leukocytes in Blood||2|%^percent^UCUM|0 to 6|N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "OBX|16|NM|26474-7^Lymphocytes [#/volume] in Blood^LN^^^^^^Lymphocytes [#/volume] in Blood||41.2|10*3/uL^thousand per microliter^UCUM|1.0 to 4.8|HH|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "OBX|17|NM|26478-8^Lymphocytes/100 leukocytes in Blood^LN^^^^^^Lymphocytes/100 leukocytes in Blood||39|%^percent^UCUM|15.0 to 45.0|N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "OBX|18|NM|26499-4^Neutrophils [#/volume] in Blood^LN^^^^^^Neutrophils [#/volume] in Blood||58|10*3/uL^thousand per microliter^UCUM|1.5 to 7.0|HH|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "OBX|19|NM|26511-6^Neutrophils/100 leukocytes in Blood^LN^^^^^^Neutrophils/100 leukocytes in Blood||55|%^percent^UCUM|50 to 73|N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "OBX|20|CWE|38892-6^Anisocytosis [Presence] in Blood^LN^^^^^^Anisocytosis [Presence] in Blood||260348001^Present ++ out of ++++^SCT^^^^^^Moderate Anisocytosis|||A|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "OBX|21|CWE|30400-6^Hypochromia [Presence] in Blood^LN^^^^^^Hypochromia [Presence] in Blood||260415000^not detected^SCT^^^^^^None seen|||N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "OBX|22|CWE|30424-6^Macrocytes [Presence] in Blood^LN^^^^^^Macrocytes [Presence] in Blood||260415000^not detected^SCT^^^^^^None seen|||N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "OBX|23|CWE|30434-5^Microcytes [Presence] in Blood^LN^^^^^^Microcytes [Presence] in Blood||260415000^not detected^SCT^^^^^^None seen|||N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "OBX|24|CWE|779-9^Poikilocytosis [Presence] in Blood by Light microscopy^LN^^^^^^Poikilocytosis [Presence] in Blood by Light microscopy||260415000^not detected^SCT^^^^^^None seen|||N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "OBX|25|CWE|10378-8^Polychromasia [Presence] in Blood by Light microscopy^LN^^^^^^Polychromasia [Presence] in Blood by Light microscopy||260415000^not detected^SCT^^^^^^None seen|||N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "OBX|26|TX|6742-1^Erythrocyte morphology finding [Identifier] in Blood^LN^^^^^^Erythrocyte morphology finding [Identifier] in Blood||Many spherocytes present.|||A|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "OBX|27|TX|11156-7^Leukocyte morphology finding [Identifier] in Blood^LN^^^^^^Leukocyte morphology finding [Identifier] in Blood||Reactive morphology in lymphoid cells.|||A|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "OBX|28|TX|11125-2^Platelet morphology finding [Identifier] in Blood^LN^^^^^^Platelet morphology finding [Identifier] in Blood||Platelets show defective granulation.|||A|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "SPM|1|||119297000^BLD^SCT^^^^^^Blood|||||||||||||20110103143428-0800\r\n");
        retVal.Add("MSH|^~\\&|^2.16.840.1.113883.3.72.5.20^ISO|^2.16.840.1.113883.3.72.5.21^ISO||^2.16.840.1.113883.3.72.5.23^ISO|20110531140551-0500||ORU^R01^ORU_R01|NIST-LRI-GU-003.00|T|2.5.1|||AL|NE|||||LRI_Common_Component^^2.16.840.1.113883.9.16^ISO~LRI_GU_Component^^2.16.840.1.113883.9.12^ISO~LRI_RU_Component^^2.16.840.1.113883.9.14^ISO\r\n" + 
        "PID|1||PATID1234^^^&2.16.840.1.113883.3.72.5.30.2&ISO^MR||Jones^William^A||19610615|M||2106-3^White^HL70005\r\n" + 
        "ORC|RE|ORD777888^^2.16.840.1.113883.3.72.5.24^ISO|R-220713^^2.16.840.1.113883.3.72.5.25^ISO|GORD874244^^2.16.840.1.113883.3.72.5.24^ISO||||||||57422^Radon^Nicholas^^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI\r\n" + 
        "OBR|1|ORD777888^^2.16.840.1.113883.3.72.5.24^ISO|R-220713^^2.16.840.1.113883.3.72.5.25^ISO|24331-1^Lipid 1996 panel in Serum or Plasma^LN^345789^Lipid Panel^99USI^^^Lipid 1996 panel in Serum or Plasma|||20110531123551-0800||||||56388000^hyperlipidemia^99USI^3744001^hyperlipoproteinemia^SCT^^^hyperlipoproteinemia|||57422^Radon^Nicholas^^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI||||||20110611140428-0800|||F|||10092^Hamlin^Pafford^^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI|||||||||||||||||||||BCC^Blind Copy^HL70507\r\n" + 
        "OBX|1|NM|2093-3^Cholesterol [Mass/volume] in Serum or Plasma^LN^^^^^^Cholesterol [Mass/volume] in Serum or Plasma||196|mg/dL^milligrams per deciliter^UCUM|Recommended: <200; Moderate Risk: 200-239 ; High Risk: >240|N|||F|||20110531123551-0800|||||20110601130551-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "OBX|2|NM|2571-8^Triglyceride [Mass/volume] in Serum or Plasma^LN^^^^^^Triglyceride [Mass/volume] in Serum or Plasma||100|mg/dL^milligrams per deciliter^UCUM|40 to 160|N|||F|||20110531123551-0800|||||20110601130551-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "OBX|3|NM|2085-9^Cholesterol in HDL [Mass/volume] in Serum or Plasma^LN^^^^^^Cholesterol in HDL [Mass/volume] in Serum or Plasma||60|mg/dL^milligrams per deciliter^UCUM|29 to 72|N|||F|||20110531123551-0800|||||20110601130551-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "OBX|4|NM|2089-1^Cholesterol in LDL [Mass/volume] in Serum or Plasma^LN^^^^^^Cholesterol in LDL [Mass/volume] in Serum or Plasma||116|mg/dL^milligrams per deciliter^UCUM|Recommended: <130; Moderate Risk: 130-159; High Risk: >160|N|||F|||20110531123551-0800|||||20110601130551-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN\r\n" + 
        "SPM|1|||119297000^BLD^SCT^^^^^^Blood|||||||||||||20110531123551-0800\r\n");
        retVal.Add("MSH|^~\\&|^2.16.840.1.113883.3.72.5.20^ISO|^2.16.840.1.113883.3.72.5.21^ISO||^2.16.840.1.113883.3.72.5.23^ISO|20110531140551-0500||ORU^R01^ORU_R01|NIST-LRI-GU-004.00|T|2.5.1|||AL|NE|||||LRI_Common_Component^^2.16.840.1.113883.9.16^ISO~LRI_GU_Component^^2.16.840.1.113883.9.12^ISO~LRI_RU_Component^^2.16.840.1.113883.9.14^ISO\r\n" + 
        "PID|1||PATID1234^^^&2.16.840.1.113883.3.72.5.30.2&ISO^MR||Jones^William^A||19610615|M||2106-3^White^HL70005\r\n" + 
        "ORC|RE|ORD723222-4^^2.16.840.1.113883.3.72.5.24^ISO|R-783274-4^^2.16.840.1.113883.3.72.5.25^ISO|GORD874211^^2.16.840.1.113883.3.72.5.24^ISO||||||||57422^Radon^Nicholas^^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI\r\n" + 
        "OBR|1|ORD723222-4^^2.16.840.1.113883.3.72.5.24^ISO|R-783274-4^^2.16.840.1.113883.3.72.5.25^ISO|625-4^Bacteria identified in Stool by Culture^LN^3456543^CULTURE STOOL^99USI^^^Stool Culture|||20110530123551-0500||||||787.91^DIARRHEA^I9CDX^^^^^^DIARRHEA|||57422^Radon^Nicholas^^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI||||||20110531140428-0500|||P\r\n" + 
        "OBX|1|CWE|625-4^Bacteria identified in Stool by Culture^LN^^^^^^Stool Culture|1|103429008^Enterohemorrhagic Escherichia coli, serotype O157:H7^SCT^^^^^^Shiga toxin producing E. coli O157:H7 isolated|||A|||P|||20110530123551-0500|||||20110531130655-0500||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|9876543^Slide^Stan^S^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI\r\n" + 
        "OBX|2|CWE|625-4^Bacteria identified in Stool by Culture^LN^^^^^^Stool Culture|2|398567006^Salmonella I, group O:4^SCT^^^^^^Salmonella I, group O:4 isolated|||A|||P|||20110530123551-0500|||||20110531130655-0500||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|9876543^Slide^Stan^S^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI\r\n" + 
        "OBX|3|CWE|625-4^Bacteria identified in Stool by Culture^LN^^^^^^Stool Culture|3|85729005^Shigella flexneri^SCT^^^^^^Shigella flexneri isolated|||A|||P|||20110530123551-0500|||||20110531130655-0500||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|9876543^Slide^Stan^S^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI\r\n" + 
        "SPM|1|||119339001^Stool specimen^SCT^^^^^^Stool|||||||||||||20110530123551-0500\r\n");
        retVal.Add("MSH|^~\\&|^2.16.840.1.113883.3.72.5.20^ISO|^2.16.840.1.113883.3.72.5.21^ISO||^2.16.840.1.113883.3.72.5.23^ISO|20110531140551-0500||ORU^R01^ORU_R01|NIST-LRI-GU-RU-004.01|T|2.5.1|||AL|NE|||||LRI_Common_Component^^2.16.840.1.113883.9.16^ISO~LRI_GU_Component^^2.16.840.1.113883.9.12^ISO~LRI_RU_Component^^2.16.840.1.113883.9.14^ISO\r\n" + 
        "PID|1||PATID1234^^^&2.16.840.1.113883.3.72.5.30.2&ISO^MR||Jones^William^A||19610615|M||2106-3^White^HL70005\r\n" + 
        "ORC|RE|ORD723222-4^^2.16.840.1.113883.3.72.5.24^ISO|R-783274-4^^2.16.840.1.113883.3.72.5.25^ISO|GORD874211^^2.16.840.1.113883.3.72.5.24^ISO||||||||57422^Radon^Nicholas^^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI\r\n" + 
        "OBR|1|ORD723222-4^^2.16.840.1.113883.3.72.5.24^ISO|R-783274-4^^2.16.840.1.113883.3.72.5.25^ISO|625-4^Bacteria identified in Stool by Culture^LN^3456543^CULTURE STOOL^99USI^^^Stool Culture|||20110530123551-0500||||||787.91^DIARRHEA^I9CDX^^^^^^DIARRHEA|||57422^Radon^Nicholas^^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI||||||20110531140428-0500|||F\r\n" + 
        "OBX|1|CWE|625-4^Bacteria identified in Stool by Culture^LN^^^^^^Stool Culture|1|103429008^Enterohemorrhagic Escherichia coli, serotype O157:H7^SCT^^^^^^Shiga toxin producing E. coli O157:H7 isolated|||A|||F|||20110530123551-0500|||||20110531130655-0500||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|9876543^Slide^Stan^S^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI\r\n" + 
        "OBX|2|CWE|625-4^Bacteria identified in Stool by Culture^LN^^^^^^Stool Culture|2|398567006^Salmonella I, group O:4^SCT^^^^^^Salmonella I, group O:4 isolated|||A|||F|||20110530123551-0500|||||20110531130655-0500||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|9876543^Slide^Stan^S^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI\r\n" + 
        "OBX|3|CWE|625-4^Bacteria identified in Stool by Culture^LN^^^^^^Stool Culture|3|85729005^Shigella flexneri^SCT^^^^^^Shigella flexneri isolated|||A|||F|||20110530123551-0500|||||20110531130655-0500||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|9876543^Slide^Stan^S^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI\r\n" + 
        "SPM|1|||119339001^Stool specimen^SCT^^^^^^Stool|||||||||||||20110530123551-0500\r\n" + 
        "ORC|RE||R-783274-5^^2.16.840.1.113883.3.72.5.25^ISO|GORD874211^^2.16.840.1.113883.3.72.5.24^ISO||||||||57422^Radon^Nicholas^^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI\r\n" + 
        "OBR|2||R-783274-5^^2.16.840.1.113883.3.72.5.25^ISO|50545-3^Bacterial susceptibility panel in Isolate by Minimum inhibitory concentration (MIC)^LN^^^^^^Bacteria susceptibility|||20110530123551-0500||||G|||||57422^Radon^Nicholas^^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI||||||20110601140428-0500|||F|625-4&Bacteria identified in Stool by Culture&LN&&&&&&Stool Culture^1|||ORD723222-4&&2.16.840.1.113883.3.72.5.24&ISO^R-783274-4&&2.16.840.1.113883.3.72.5.25&ISO\r\n" + 
        "OBX|1|SN|28-1^Ampicillin [Susceptibility] by Minimum inhibitory concentration (MIC)^LN^^^^^^AMPICILLIN|1|<^0.06|ug/mL^^UCUM||S|||F|||20110530123551-0500|||||20110601130655-0500||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|9876543^Slide^Stan^S^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI\r\n" + 
        "OBX|2|SN|267-5^Gentamicin [Susceptibility] by Minimum inhibitory concentration (MIC)^LN^^^^^^GENTAMICIN|1|^0.05|ug/mL^^UCUM||S|||F|||20110530123551-0500|||||20110601130655-0500||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|9876543^Slide^Stan^S^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI\r\n" + 
        "OBX|3|SN|185-9^Ciprofloxacin [Susceptibility] by Minimum inhibitory concentration (MIC)^LN^^^^^^CIPROFLOXACIN|1|^0.05|ug/mL^^UCUM||S|||F|||20110530123551-0500|||||20110601130655-0500||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|9876543^Slide^Stan^S^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI\r\n" + 
        "ORC|RE||R-783274-6^^2.16.840.1.113883.3.72.5.25^ISO|GORD874211^^2.16.840.1.113883.3.72.5.24^ISO||||||||57422^Radon^Nicholas^^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI\r\n" + 
        "OBR|3||R-783274-6^^2.16.840.1.113883.3.72.5.25^ISO|50545-3^Bacterial susceptibility panel in Isolate by Minimum inhibitory concentration (MIC)^LN^^^^^^Bacteria susceptibility|||20110530123551-0500||||G|||||57422^Radon^Nicholas^^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI||||||20110601140428-0500|||F|625-4&Bacteria identified in Stool by Culture&LN&&&&&&Stool Culture^2|||ORD723222-4&&2.16.840.1.113883.3.72.5.24&ISO^R-783274-4&&2.16.840.1.113883.3.72.5.25&ISO\r\n" + 
        "OBX|1|SN|28-1^Ampicillin [Susceptibility] by Minimum inhibitory concentration (MIC)^LN^^^^^^AMPICILLIN|1|<^0.06|ug/mL^^UCUM||S|||F|||20110530123551-0500|||||20110601130655-0500||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|9876543^Slide^Stan^S^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI\r\n" + 
        "OBX|2|SN|267-5^Gentamicin [Susceptibility] by Minimum inhibitory concentration (MIC)^LN^^^^^^GENTAMICIN|1|^0.05|ug/mL^^UCUM||S|||F|||20110530123551-0500|||||20110601130655-0500||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|9876543^Slide^Stan^S^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI\r\n" + 
        "OBX|3|SN|185-9^Ciprofloxacin [Susceptibility] by Minimum inhibitory concentration (MIC)^LN^^^^^^CIPROFLOXACIN|1|^0.05|ug/mL^^UCUM||S|||F|||20110530123551-0500|||||20110601130655-0500||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|9876543^Slide^Stan^S^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI\r\n" + 
        "ORC|RE||R-783274-7^^2.16.840.1.113883.3.72.5.25^ISO|GORD874211^^2.16.840.1.113883.3.72.5.24^ISO||||||||57422^Radon^Nicholas^^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI\r\n" + 
        "OBR|4||R-783274-7^^2.16.840.1.113883.3.72.5.25^ISO|50545-3^Bacterial susceptibility panel in Isolate by Minimum inhibitory concentration (MIC)^LN^^^^^^Bacteria susceptibility|||20110530123551-0500||||G|||||57422^Radon^Nicholas^^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI||||||20110601140428-0500|||F|625-4&Bacteria identified in Stool by Culture&LN&&&&&&Stool Culture^3|||ORD723222-4&&2.16.840.1.113883.3.72.5.24&ISO^R-783274-4&&2.16.840.1.113883.3.72.5.25&ISO\r\n" + 
        "OBX|1|SN|28-1^Ampicillin [Susceptibility] by Minimum inhibitory concentration (MIC)^LN^^^^^^AMPICILLIN|1|<^0.06|ug/mL^^UCUM||S|||F|||20110530123551-0500|||||20110601130655-0500||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|9876543^Slide^Stan^S^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI\r\n" + 
        "OBX|2|SN|267-5^Gentamicin [Susceptibility] by Minimum inhibitory concentration (MIC)^LN^^^^^^GENTAMICIN|1|^0.05|ug/mL^^UCUM||S|||F|||20110530123551-0500|||||20110601130655-0500||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|9876543^Slide^Stan^S^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI\r\n" + 
        "OBX|3|SN|185-9^Ciprofloxacin [Susceptibility] by Minimum inhibitory concentration (MIC)^LN^^^^^^CIPROFLOXACIN|1|^0.05|ug/mL^^UCUM||S|||F|||20110530123551-0500|||||20110601130655-0500||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|9876543^Slide^Stan^S^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI\r\n");
        retVal.Add("MSH|^~\\&|^2.16.840.1.113883.3.72.5.20^ISO|^2.16.840.1.113883.3.72.5.21^ISO||^2.16.840.1.113883.3.72.5.23^ISO|20110531140551-0500||ORU^R01^ORU_R01|NIST-LRI-GU-RU-005.00|T|2.5.1|||AL|NE|||||LRI_Common_Component^^2.16.840.1.113883.9.16^ISO~LRI_GU_Component^^2.16.840.1.113883.9.12^ISO~LRI_RU_Component^^2.16.840.1.113883.9.14^ISO\r\n" + 
        "PID|1||PATID1239^^^&2.16.840.1.113883.3.72.5.30.2&ISO^MR||Smirnoff^Peggy^^^^^M||19750401|F||2106-3^White^HL70005^wh^white^L\r\n" + 
        "ORC|RE|ORD448811^^2.16.840.1.113883.3.72.5.24^ISO|R-511^^2.16.840.1.113883.3.72.5.25^ISO|||||||||1234567890^Fine^Larry^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI\r\n" + 
        "OBR|1|ORD448811^^2.16.840.1.113883.3.72.5.24^ISO|R-511^^2.16.840.1.113883.3.72.5.25^ISO|HepABC Panel^Hepatitis A B C Panel^L|||20120628070100|||||||||1234567890^Fine^Larry^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI||||||20120629132900-0500|||F\r\n" + 
        "OBX|1|CWE|22314-9^Hepatitis A virus IgM Ab [Presence] in Serum^LN^HAVM^Hepatitis A IgM antibodies (IgM anti-HAV)^L||260385009^Negative (qualifier value)^SCT^NEG^NEGATIVE^L^^^Negative (qualifier value)||Negative|N|||F|||20120628070100|||||20120628100500||||Princeton Hospital Laboratory^^^^^NIST HCAA-1&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^34D4567890|123 High Street^^Princeton^NJ^08540^USA^O^^34021|^Martin^Steven^M^^Dr.\r\n" + 
        "OBX|2|CWE|20575-7^Hepatitis A virus Ab [Presence] in Serum^LN^HAVAB^Hepatitis A antibodies (anti-HAV)^L||260385009^Negative (qualifier value)^SCT^NEG^NEGATIVE^L^^^Negative (qualifier value)||Negative|N|||F|||20120628070100|||||20120628100500||||Princeton Hospital Laboratory^^^^^NIST HCAA-1&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^34D4567890|123 High Street^^Princeton^NJ^08540^USA^O^^34021|^Martin^Steven^M^^Dr.\r\n" + 
        "OBX|3|CWE|16933-4^Hepatitis B virus core Ab [Presence] in Serum^LN^HBVcAB^Hepatitis B core antibodies (anti-HBVc)^L||260385009^Negative (qualifier value)^SCT^NEG^NEGATIVE^L^^^Negative (qualifier value)||Negative|N|||F|||20120628070100|||||20120628100500||||Princeton Hospital Laboratory^^^^^NIST HCAA-1&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^34D4567890|123 High Street^^Princeton^NJ^08540^USA^O^^34021|^Martin^Steven^M^^Dr.\r\n" + 
        "OBX|4|SN|22316-4^Hepatitis B virus core Ab [Units/volume] in Serum^LN^HBcAbQ^Hepatitis B core antibodies (anti-HBVc) Quant^L||^0.40|[IU]/mL^international unit per milliliter^UCUM^IU/ml^^L|<0.50 IU/mL|N|||F|||20120628070100|||||20120628100500||||Princeton Hospital Laboratory^^^^^NIST HCAA-1&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^34D4567890|123 High Street^^Princeton^NJ^08540^USA^O^^34021|^Martin^Steven^M^^Dr.\r\n" + 
        "OBX|5|CWE|22320-6^Hepatitis B virus e Ab [Presence] in Serum^LN^HBVeAB^Hepatitis B e antibodies (anti-HBVe)^L||260385009^Negative (qualifier value)^SCT^NEG^NEGATIVE^L^^^Negative (qualifier value)||Negative|N|||F|||20120628070100|||||20120628100500||||Princeton Hospital Laboratory^^^^^NIST HCAA-1&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^34D4567890|123 High Street^^Princeton^NJ^08540^USA^O^^34021|^Martin^Steven^M^^Dr.\r\n" + 
        "OBX|6|CWE|5195-3^Hepatitis B virus surface Ag [Presence] in Serum^LN^HBVsAG^Hepatitis B surface antigen (HBsAg)^L||260385009^Negative (qualifier value)^SCT^NEG^NEGATIVE^L^^^Negative (qualifier value)||Negative|N|||F|||20120628070100|||||20120628100500||||Princeton Hospital Laboratory^^^^^NIST HCAA-1&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^34D4567890|123 High Street^^Princeton^NJ^08540^USA^O^^34021|^Martin^Steven^M^^Dr.\r\n" + 
        "OBX|7|CWE|22322-2^Hepatitis B virus surface Ab [Presence] in Serum^LN^HBVSAB^Hepatitis B surface antibody (anti-HBVs)^L||260385009^Negative (qualifier value)^SCT^NEG^NEGATIVE^L^^^Negative (qualifier value)||Negative|N|||F|||20120628070100|||||20120628100500||||Princeton Hospital Laboratory^^^^^NIST HCAA-1&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^34D4567890|123 High Street^^Princeton^NJ^08540^USA^O^^34021|^Martin^Steven^M^^Dr.\r\n" + 
        "OBX|8|CWE|16128-1^Hepatitis C virus Ab [Presence] in Serum^LN^HCVAB^Hepatitis C antibody screen  (anti-HCV)^L||10828004^Positive (qualifier value)^SCT^POS^POSITIVE^L^^^Positive (qualifier value)||Negative|A|||F|||20120628070100|||||20120628100500||||Princeton Hospital Laboratory^^^^^NIST HCAA-1&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^34D4567890|123 High Street^^Princeton^NJ^08540^USA^O^^34021|^Martin^Steven^M^^Dr.\r\n" + 
        "OBX|9|SN|48159-8^Hepatitis C virus Ab Signal/Cutoff in Serum or Plasma by Immunoassay^LN^HCVSCO^Hepatitis C antibodies Signal to Cut-off Ratio^L||^10.8|{s_co_ratio}^Signal to cutoff ratio^UCUM^s/co^^L|0.0-0.9 s/co|H|||F|||20120628070100|||||20120628100500||||Princeton Hospital Laboratory^^^^^NIST HCAA-1&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^34D4567890|123 High Street^^Princeton^NJ^08540^USA^O^^34021|^Martin^Steven^M^^Dr.\r\n" + 
        "NTE|1||Negative:   < 0.8; Indeterminate 0.8 - 0.9; Positive:  > 0.9.  In order to reduce the incidence of a false positive result, the CDC recommends that all s/co ratios between 1.0 and 10.9 be confirmed with additional Verification or PCR testing.\r\n" + 
        "SPM|1|||119364003^Serum specimen (specimen)^SCT^SER^Serum^L|||||||||||||20120628070100\r\n" + 
        "ORC|RE||R-512^^2.16.840.1.113883.3.72.5.25^ISO|||||||||1234567890^Fine^Larry^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI\r\n" + 
        "OBR|2||R-512^^2.16.840.1.113883.3.72.5.25^ISO|11011-4^Hepatitis C virus RNA [Units/volume] (viral load) in Serum or Plasma by Probe and target amplification method^LN^HCVRNA^Hepatitis C RNA PCR^L|||20120628070100||||G|||||1234567890^Fine^Larry^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI||||||20120629132900-0500|||F|16128-1&Hepatitis C virus Ab [Presence] in Serum&LN&HCVAB&Hepatitis C antibody screen  (anti-HCV)&L|||ORD448811&&2.16.840.1.113883.3.72.5.24&ISO^R-511&&2.16.840.1.113883.3.72.5.25&ISO\r\n" + 
        "OBX|1|SN|11011-4^Hepatitis C virus RNA [Units/volume] (viral load) in Serum or Plasma by Probe and target amplification method^LN^HCVRNA^Hepatitis C RNA PCR^L||^7611200|[IU]/mL^international unit per milliliter^UCUM^IU/ml^^L|<43 IU/mL|H|||F|||20120628070100|||||20120629092700||||Princeton Hospital Laboratory^^^^^NIST HCAA-1&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^34D4567890|123 High Street^^Princeton^NJ^08540^USA^O^^34021|^Martin^Steven^M^^Dr.");
        return retVal;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEhrSetup.class);
        this.mainMenu1 = new System.Windows.Forms.MainMenu(this.components);
        this.menuItemSettings = new System.Windows.Forms.MenuItem();
        this.groupCodeSystems = new System.Windows.Forms.GroupBox();
        this.butCodeImport = new OpenDental.UI.Button();
        this.butRxNorm = new OpenDental.UI.Button();
        this.butICD9s = new OpenDental.UI.Button();
        this.butSnomeds = new OpenDental.UI.Button();
        this.butCDSSetup = new OpenDental.UI.Button();
        this.butEhrTriggers = new OpenDental.UI.Button();
        this.butTimeSynch = new OpenDental.UI.Button();
        this.butLoincs = new OpenDental.UI.Button();
        this.butKeys = new OpenDental.UI.Button();
        this.butEducationalResources = new OpenDental.UI.Button();
        this.butInboundEmail = new OpenDental.UI.Button();
        this.butReminderRules = new OpenDental.UI.Button();
        this.panelEmergencyNow = new OpenDental.UI.PanelOD();
        this.butEmergencyNow = new OpenDental.UI.Button();
        this.butDrugUnit = new OpenDental.UI.Button();
        this.butDrugManufacturer = new OpenDental.UI.Button();
        this.butVaccineDef = new OpenDental.UI.Button();
        this.butAllergies = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.butOIDs = new OpenDental.UI.Button();
        this.groupCodeSystems.SuspendLayout();
        this.SuspendLayout();
        //
        // mainMenu1
        //
        this.mainMenu1.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemSettings });
        //
        // menuItemSettings
        //
        this.menuItemSettings.Index = 0;
        this.menuItemSettings.Text = "Settings";
        this.menuItemSettings.Click += new System.EventHandler(this.menuItemSettings_Click);
        //
        // groupCodeSystems
        //
        this.groupCodeSystems.Controls.Add(this.butCodeImport);
        this.groupCodeSystems.Controls.Add(this.butRxNorm);
        this.groupCodeSystems.Controls.Add(this.butICD9s);
        this.groupCodeSystems.Controls.Add(this.butSnomeds);
        this.groupCodeSystems.Location = new System.Drawing.Point(202, 0);
        this.groupCodeSystems.Name = "groupCodeSystems";
        this.groupCodeSystems.Size = new System.Drawing.Size(152, 206);
        this.groupCodeSystems.TabIndex = 136;
        this.groupCodeSystems.TabStop = false;
        this.groupCodeSystems.Text = "Code Systems";
        //
        // butCodeImport
        //
        this.butCodeImport.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCodeImport.setAutosize(true);
        this.butCodeImport.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCodeImport.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCodeImport.setCornerRadius(4F);
        this.butCodeImport.Location = new System.Drawing.Point(12, 19);
        this.butCodeImport.Name = "butCodeImport";
        this.butCodeImport.Size = new System.Drawing.Size(128, 24);
        this.butCodeImport.TabIndex = 14;
        this.butCodeImport.Text = "Code System Importer";
        this.butCodeImport.UseVisualStyleBackColor = false;
        this.butCodeImport.Click += new System.EventHandler(this.butCodeImport_Click);
        //
        // butRxNorm
        //
        this.butRxNorm.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRxNorm.setAutosize(true);
        this.butRxNorm.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRxNorm.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRxNorm.setCornerRadius(4F);
        this.butRxNorm.Location = new System.Drawing.Point(12, 98);
        this.butRxNorm.Name = "butRxNorm";
        this.butRxNorm.Size = new System.Drawing.Size(128, 24);
        this.butRxNorm.TabIndex = 10;
        this.butRxNorm.Text = "RxNorms";
        this.butRxNorm.Click += new System.EventHandler(this.butRxNorm_Click);
        //
        // butICD9s
        //
        this.butICD9s.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butICD9s.setAutosize(true);
        this.butICD9s.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butICD9s.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butICD9s.setCornerRadius(4F);
        this.butICD9s.Location = new System.Drawing.Point(12, 60);
        this.butICD9s.Name = "butICD9s";
        this.butICD9s.Size = new System.Drawing.Size(128, 24);
        this.butICD9s.TabIndex = 9;
        this.butICD9s.Text = "ICD9s";
        this.butICD9s.Click += new System.EventHandler(this.butICD9s_Click);
        //
        // butSnomeds
        //
        this.butSnomeds.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSnomeds.setAutosize(true);
        this.butSnomeds.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSnomeds.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSnomeds.setCornerRadius(4F);
        this.butSnomeds.Location = new System.Drawing.Point(12, 136);
        this.butSnomeds.Name = "butSnomeds";
        this.butSnomeds.Size = new System.Drawing.Size(128, 24);
        this.butSnomeds.TabIndex = 12;
        this.butSnomeds.Text = "SNOMED CTs";
        this.butSnomeds.Click += new System.EventHandler(this.butSnomeds_Click);
        //
        // butCDSSetup
        //
        this.butCDSSetup.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCDSSetup.setAutosize(true);
        this.butCDSSetup.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCDSSetup.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCDSSetup.setCornerRadius(4F);
        this.butCDSSetup.Location = new System.Drawing.Point(214, 288);
        this.butCDSSetup.Name = "butCDSSetup";
        this.butCDSSetup.Size = new System.Drawing.Size(128, 24);
        this.butCDSSetup.TabIndex = 139;
        this.butCDSSetup.Text = "CDS Setup";
        this.butCDSSetup.Visible = false;
        //
        // butEhrTriggers
        //
        this.butEhrTriggers.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butEhrTriggers.setAutosize(true);
        this.butEhrTriggers.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butEhrTriggers.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butEhrTriggers.setCornerRadius(4F);
        this.butEhrTriggers.Location = new System.Drawing.Point(214, 212);
        this.butEhrTriggers.Name = "butEhrTriggers";
        this.butEhrTriggers.Size = new System.Drawing.Size(128, 24);
        this.butEhrTriggers.TabIndex = 138;
        this.butEhrTriggers.Text = "EHR Triggers";
        this.butEhrTriggers.Click += new System.EventHandler(this.butEhrTriggers_Click);
        //
        // butTimeSynch
        //
        this.butTimeSynch.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butTimeSynch.setAutosize(true);
        this.butTimeSynch.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butTimeSynch.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butTimeSynch.setCornerRadius(4F);
        this.butTimeSynch.Location = new System.Drawing.Point(214, 250);
        this.butTimeSynch.Name = "butTimeSynch";
        this.butTimeSynch.Size = new System.Drawing.Size(128, 24);
        this.butTimeSynch.TabIndex = 15;
        this.butTimeSynch.Text = "Time Synchronization";
        this.butTimeSynch.Click += new System.EventHandler(this.butTimeSynch_Click);
        //
        // butLoincs
        //
        this.butLoincs.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butLoincs.setAutosize(true);
        this.butLoincs.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butLoincs.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butLoincs.setCornerRadius(4F);
        this.butLoincs.Location = new System.Drawing.Point(214, 174);
        this.butLoincs.Name = "butLoincs";
        this.butLoincs.Size = new System.Drawing.Size(128, 24);
        this.butLoincs.TabIndex = 11;
        this.butLoincs.Text = "Loincs";
        this.butLoincs.Click += new System.EventHandler(this.butLoincs_Click);
        //
        // butKeys
        //
        this.butKeys.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butKeys.setAutosize(true);
        this.butKeys.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butKeys.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butKeys.setCornerRadius(4F);
        this.butKeys.Location = new System.Drawing.Point(27, 288);
        this.butKeys.Name = "butKeys";
        this.butKeys.Size = new System.Drawing.Size(128, 24);
        this.butKeys.TabIndex = 8;
        this.butKeys.Text = "Quarterly Keys";
        this.butKeys.Click += new System.EventHandler(this.butKeys_Click);
        //
        // butEducationalResources
        //
        this.butEducationalResources.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butEducationalResources.setAutosize(true);
        this.butEducationalResources.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butEducationalResources.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butEducationalResources.setCornerRadius(4F);
        this.butEducationalResources.Location = new System.Drawing.Point(27, 250);
        this.butEducationalResources.Name = "butEducationalResources";
        this.butEducationalResources.Size = new System.Drawing.Size(128, 24);
        this.butEducationalResources.TabIndex = 7;
        this.butEducationalResources.Text = "Educational Resources";
        this.butEducationalResources.Click += new System.EventHandler(this.butEducationalResources_Click);
        //
        // butInboundEmail
        //
        this.butInboundEmail.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butInboundEmail.setAutosize(true);
        this.butInboundEmail.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butInboundEmail.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butInboundEmail.setCornerRadius(4F);
        this.butInboundEmail.Location = new System.Drawing.Point(27, 212);
        this.butInboundEmail.Name = "butInboundEmail";
        this.butInboundEmail.Size = new System.Drawing.Size(128, 24);
        this.butInboundEmail.TabIndex = 6;
        this.butInboundEmail.Text = "Inbound Email";
        this.butInboundEmail.Click += new System.EventHandler(this.butInboundEmail_Click);
        //
        // butReminderRules
        //
        this.butReminderRules.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butReminderRules.setAutosize(true);
        this.butReminderRules.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butReminderRules.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butReminderRules.setCornerRadius(4F);
        this.butReminderRules.Location = new System.Drawing.Point(27, 174);
        this.butReminderRules.Name = "butReminderRules";
        this.butReminderRules.Size = new System.Drawing.Size(128, 24);
        this.butReminderRules.TabIndex = 5;
        this.butReminderRules.Text = "Reminder Rules";
        this.butReminderRules.Click += new System.EventHandler(this.butReminderRules_Click);
        //
        // panelEmergencyNow
        //
        this.panelEmergencyNow.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.panelEmergencyNow.Location = new System.Drawing.Point(504, 60);
        this.panelEmergencyNow.Name = "panelEmergencyNow";
        this.panelEmergencyNow.Size = new System.Drawing.Size(24, 24);
        this.panelEmergencyNow.TabIndex = 125;
        //
        // butEmergencyNow
        //
        this.butEmergencyNow.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butEmergencyNow.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butEmergencyNow.setAutosize(true);
        this.butEmergencyNow.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butEmergencyNow.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butEmergencyNow.setCornerRadius(4F);
        this.butEmergencyNow.Location = new System.Drawing.Point(400, 60);
        this.butEmergencyNow.Name = "butEmergencyNow";
        this.butEmergencyNow.Size = new System.Drawing.Size(98, 24);
        this.butEmergencyNow.TabIndex = 15;
        this.butEmergencyNow.Text = "Emergency Now";
        this.butEmergencyNow.Click += new System.EventHandler(this.butEmergencyNow_Click);
        //
        // butDrugUnit
        //
        this.butDrugUnit.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDrugUnit.setAutosize(true);
        this.butDrugUnit.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDrugUnit.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDrugUnit.setCornerRadius(4F);
        this.butDrugUnit.Location = new System.Drawing.Point(27, 136);
        this.butDrugUnit.Name = "butDrugUnit";
        this.butDrugUnit.Size = new System.Drawing.Size(128, 24);
        this.butDrugUnit.TabIndex = 4;
        this.butDrugUnit.Text = "Drug Unit";
        this.butDrugUnit.Click += new System.EventHandler(this.butDrugUnit_Click);
        //
        // butDrugManufacturer
        //
        this.butDrugManufacturer.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDrugManufacturer.setAutosize(true);
        this.butDrugManufacturer.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDrugManufacturer.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDrugManufacturer.setCornerRadius(4F);
        this.butDrugManufacturer.Location = new System.Drawing.Point(27, 98);
        this.butDrugManufacturer.Name = "butDrugManufacturer";
        this.butDrugManufacturer.Size = new System.Drawing.Size(128, 24);
        this.butDrugManufacturer.TabIndex = 3;
        this.butDrugManufacturer.Text = "Drug Manufacturer";
        this.butDrugManufacturer.Click += new System.EventHandler(this.butDrugManufacturer_Click);
        //
        // butVaccineDef
        //
        this.butVaccineDef.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butVaccineDef.setAutosize(true);
        this.butVaccineDef.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butVaccineDef.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butVaccineDef.setCornerRadius(4F);
        this.butVaccineDef.Location = new System.Drawing.Point(27, 60);
        this.butVaccineDef.Name = "butVaccineDef";
        this.butVaccineDef.Size = new System.Drawing.Size(128, 24);
        this.butVaccineDef.TabIndex = 2;
        this.butVaccineDef.Text = "Vaccine Def";
        this.butVaccineDef.Click += new System.EventHandler(this.butVaccineDef_Click);
        //
        // butAllergies
        //
        this.butAllergies.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAllergies.setAutosize(true);
        this.butAllergies.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAllergies.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAllergies.setCornerRadius(4F);
        this.butAllergies.Location = new System.Drawing.Point(27, 22);
        this.butAllergies.Name = "butAllergies";
        this.butAllergies.Size = new System.Drawing.Size(128, 24);
        this.butAllergies.TabIndex = 1;
        this.butAllergies.Text = "Allergies";
        this.butAllergies.Click += new System.EventHandler(this.butAllergies_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(461, 280);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 16;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butOIDs
        //
        this.butOIDs.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOIDs.setAutosize(true);
        this.butOIDs.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOIDs.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOIDs.setCornerRadius(4F);
        this.butOIDs.Location = new System.Drawing.Point(400, 174);
        this.butOIDs.Name = "butOIDs";
        this.butOIDs.Size = new System.Drawing.Size(128, 24);
        this.butOIDs.TabIndex = 140;
        this.butOIDs.Text = "OIDs";
        this.butOIDs.Click += new System.EventHandler(this.butOIDs_Click);
        //
        // FormEhrSetup
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(561, 331);
        this.Controls.Add(this.butOIDs);
        this.Controls.Add(this.butCDSSetup);
        this.Controls.Add(this.butEhrTriggers);
        this.Controls.Add(this.butTimeSynch);
        this.Controls.Add(this.butLoincs);
        this.Controls.Add(this.groupCodeSystems);
        this.Controls.Add(this.butKeys);
        this.Controls.Add(this.butEducationalResources);
        this.Controls.Add(this.butInboundEmail);
        this.Controls.Add(this.butReminderRules);
        this.Controls.Add(this.panelEmergencyNow);
        this.Controls.Add(this.butEmergencyNow);
        this.Controls.Add(this.butDrugUnit);
        this.Controls.Add(this.butDrugManufacturer);
        this.Controls.Add(this.butVaccineDef);
        this.Controls.Add(this.butAllergies);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Menu = this.mainMenu1;
        this.Name = "FormEhrSetup";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Electronic Health Record (EHR) Setup";
        this.Load += new System.EventHandler(this.FormEhrSetup_Load);
        this.groupCodeSystems.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butClose;
    private OpenDental.UI.Button butICD9s;
    private OpenDental.UI.Button butAllergies;
    private OpenDental.UI.Button butVaccineDef;
    private OpenDental.UI.Button butDrugManufacturer;
    private OpenDental.UI.Button butDrugUnit;
    private OpenDental.UI.Button butEmergencyNow;
    private OpenDental.UI.PanelOD panelEmergencyNow;
    private OpenDental.UI.Button butReminderRules;
    private OpenDental.UI.Button butInboundEmail;
    private OpenDental.UI.Button butEducationalResources;
    private OpenDental.UI.Button butRxNorm;
    private OpenDental.UI.Button butKeys;
    private System.Windows.Forms.MainMenu mainMenu1 = new System.Windows.Forms.MainMenu();
    private System.Windows.Forms.MenuItem menuItemSettings = new System.Windows.Forms.MenuItem();
    private OpenDental.UI.Button butLoincs;
    private OpenDental.UI.Button butSnomeds;
    private OpenDental.UI.Button butCodeImport;
    private System.Windows.Forms.GroupBox groupCodeSystems = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butTimeSynch;
    private OpenDental.UI.Button butEhrTriggers;
    private OpenDental.UI.Button butCDSSetup;
    private OpenDental.UI.Button butOIDs;
}


