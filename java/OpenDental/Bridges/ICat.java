//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:28 PM
//

package OpenDental.Bridges;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.ProgramProperty;
import OpenDentBusiness.Programs;

/**
* 
*/
public class ICat   
{
    /**
    * 
    */
    public ICat() throws Exception {
    }

    /**
    * Command line.
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        if (pat == null)
        {
            try
            {
                Process.Start(path);
            }
            catch (Exception __dummyCatchVar0)
            {
                //Start iCat without bringing up a pt.
                MessageBox.Show(path + " is not available.");
            }

            return ;
        }
         
        ArrayList ForProgram = ProgramProperties.getForProgram(ProgramCur.ProgramNum);
        ProgramProperty PPCur = ProgramProperties.getCur(ForProgram,"Enter 0 to use PatientNum, or 1 to use ChartNum");
        if (StringSupport.equals(PPCur.PropertyValue, "1") && StringSupport.equals(pat.ChartNumber, ""))
        {
            MessageBox.Show("This patient must have a ChartNumber entered first.");
            return ;
        }
         
        PPCur = ProgramProperties.getCur(ForProgram,"Acquisition computer name");
        try
        {
            if (Environment.MachineName.ToUpper() == PPCur.PropertyValue.ToUpper())
            {
                sendDataServer(ProgramCur,ForProgram,pat);
            }
            else
            {
                sendDataWorkstation(ProgramCur,ForProgram,pat);
            } 
        }
        catch (Exception e)
        {
            MessageBox.Show("Error: " + e.Message);
        }
    
    }

    /**
    * XML file.
    */
    private static void sendDataServer(Program ProgramCur, ArrayList ForProgram, Patient pat) throws Exception {
        ProgramProperty PPCur = ProgramProperties.getCur(ForProgram,"Enter 0 to use PatientNum, or 1 to use ChartNum");
        String id = "";
        if (StringSupport.equals(PPCur.PropertyValue, "0"))
        {
            id = pat.PatNum.ToString();
        }
        else
        {
            id = pat.ChartNumber;
        } 
        PPCur = ProgramProperties.getCur(ForProgram,"XML output file path");
        String xmlOutputFile = PPCur.PropertyValue;
        XmlDocument doc = new XmlDocument();
        if (File.Exists(xmlOutputFile))
        {
            try
            {
                doc.Load(xmlOutputFile);
            }
            catch (Exception __dummyCatchVar1)
            {
            }
        
        }
        else
        {
            if (!Directory.Exists(Path.GetDirectoryName(xmlOutputFile)))
            {
                Directory.CreateDirectory(Path.GetDirectoryName(xmlOutputFile));
            }
             
        } 
        XmlElement elementPatients = doc.DocumentElement;
        if (elementPatients == null)
        {
            //if no Patients element, then document is corrupt or new
            doc = new XmlDocument();
            elementPatients = doc.CreateElement("Patients");
            doc.AppendChild(elementPatients);
        }
         
        //figure out if patient is already in the list------------------------------------------
        boolean patAlreadyExists = false;
        XmlElement elementPat = null;
        for (int i = 0;i < elementPatients.ChildNodes.Count;i++)
        {
            if (StringSupport.equals(elementPatients.ChildNodes[i].SelectSingleNode("ID").InnerXml, id))
            {
                patAlreadyExists = true;
                elementPat = (XmlElement)elementPatients.ChildNodes[i];
            }
             
        }
        if (patAlreadyExists)
        {
            elementPat.RemoveAll();
        }
        else
        {
            //clear it out.
            elementPat = doc.CreateElement("Patient");
        } 
        //add or edit patient-------------------------------------------------------------------------
        XmlElement el = doc.CreateElement("ID");
        el.InnerXml = id;
        elementPat.AppendChild(el);
        //LastName
        el = doc.CreateElement("LastName");
        el.InnerXml = pat.LName;
        elementPat.AppendChild(el);
        //FirstName
        el = doc.CreateElement("FirstName");
        el.InnerXml = pat.FName;
        elementPat.AppendChild(el);
        //MiddleName
        el = doc.CreateElement("MiddleName");
        el.InnerXml = pat.MiddleI;
        elementPat.AppendChild(el);
        //Birthdate
        el = doc.CreateElement("BirthDate");
        el.InnerXml = pat.Birthdate.ToString("yyyy/MM/dd");
        elementPat.AppendChild(el);
        //Gender
        el = doc.CreateElement("Gender");
        if (pat.Gender == PatientGender.Female)
        {
            el.InnerXml = "Female";
        }
        else
        {
            el.InnerXml = "Male";
        } 
        elementPat.AppendChild(el);
        //Remarks
        el = doc.CreateElement("Remarks");
        el.InnerXml = "";
        elementPat.AppendChild(el);
        //ReturnPath
        el = doc.CreateElement("ReturnPath");
        PPCur = ProgramProperties.getCur(ForProgram,"Return folder path");
        String returnFolder = PPCur.PropertyValue;
        if (!Directory.Exists(returnFolder))
        {
            Directory.CreateDirectory(returnFolder);
        }
         
        el.InnerXml = returnFolder.Replace("\\", "/");
        elementPat.AppendChild(el);
        if (!patAlreadyExists)
        {
            elementPatients.AppendChild(elementPat);
        }
         
        //lock file--------------------------------------------------------------------------
        //they say to lock the file.  But I'm the only one writing to it.
        //write text-----------------------------------------------------------------------
        XmlWriterSettings settings = new XmlWriterSettings();
        settings.Indent = true;
        settings.IndentChars = "   ";
        XmlWriter writer = XmlWriter.Create(xmlOutputFile, settings);
        doc.Save(writer);
        writer.Close();
        //This probably "unlocks" the file.
        //unlock file----------------------------------------------------------------------
        //They say to unlock the file here
        //Process.Start(xmlOutputFile);
        //MessageBox.Show(
        MessageBox.Show("Done.");
    }

    /**
    * Command line.
    */
    private static void sendDataWorkstation(Program ProgramCur, ArrayList ForProgram, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        ProgramProperty PPCur = ProgramProperties.getCur(ForProgram,"Enter 0 to use PatientNum, or 1 to use ChartNum");
        String id = "";
        if (StringSupport.equals(PPCur.PropertyValue, "0"))
        {
            id = pat.PatNum.ToString();
        }
        else
        {
            id = pat.ChartNumber;
        } 
        //We are actually supposed to get the program path from the registry. We can enhance that later.
        Process.Start(path, "PatientID" + id);
    }

    /**
    * 
    */
    public static void startFileWatcher() throws Exception {
        ArrayList ForProgram = ProgramProperties.getForProgram(Programs.getProgramNum(ProgramName.iCat));
        ProgramProperty PPCur = ProgramProperties.getCur(ForProgram,"Return folder path");
        String returnFolder = PPCur.PropertyValue;
        if (!Directory.Exists(returnFolder))
        {
            return ;
        }
         
        FileSystemWatcher watcher = new FileSystemWatcher(returnFolder, "*.xml");
        watcher.Created += new FileSystemEventHandler(OnCreated);
        watcher.Renamed += new RenamedEventHandler(OnRenamed);
        watcher.EnableRaisingEvents = true;
        //process all waiting files
        String[] existingFiles = Directory.GetFiles(returnFolder, "*.xml");
        for (int i = 0;i < existingFiles.Length;i++)
        {
            ProcessFile(existingFiles[i]);
        }
    }

    private static void onCreated(Object source, FileSystemEventArgs e) throws Exception {
        int i = 0;
        while (i < 5)
        {
            try
            {
                ProcessFile(e.FullPath);
                break;
            }
            catch (Exception __dummyCatchVar2)
            {
            }

            i++;
        }
    }

    private static void onRenamed(Object source, RenamedEventArgs e) throws Exception {
        int i = 0;
        while (i < 5)
        {
            try
            {
                ProcessFile(e.FullPath);
                break;
            }
            catch (Exception __dummyCatchVar3)
            {
            }

            i++;
        }
    }

    private static void processFile(String fullPath) throws Exception {
        String filename = Path.GetFileName(fullPath);
        ArrayList ForProgram = ProgramProperties.getForProgram(Programs.getProgramNum(ProgramName.iCat));
        ProgramProperty PPCur = ProgramProperties.getCur(ForProgram,"XML output file path");
        String xmlOutputFile = PPCur.PropertyValue;
        if (!File.Exists(xmlOutputFile))
        {
            return ;
        }
         
        try
        {
            //No xml file, so nothing to process.
            String patId = filename.Split(new char[]{ '_' }, StringSplitOptions.RemoveEmptyEntries)[0];
            XmlDocument docOut = new XmlDocument();
            docOut.Load(xmlOutputFile);
            XmlElement elementPatients = docOut.DocumentElement;
            if (elementPatients == null)
            {
                return ;
            }
             
            //if no Patients element, then document is corrupt or new
            //figure out if patient is in the list------------------------------------------
            XmlElement elementPat = null;
            for (int i = 0;i < elementPatients.ChildNodes.Count;i++)
            {
                if (StringSupport.equals(elementPatients.ChildNodes[i].SelectSingleNode("ID").InnerXml, patId))
                {
                    elementPat = (XmlElement)elementPatients.ChildNodes[i];
                }
                 
            }
            if (elementPat == null)
            {
                return ;
            }
             
            //patient not in xml document
            elementPatients.RemoveChild(elementPat);
            XmlWriterSettings settings = new XmlWriterSettings();
            settings.Indent = true;
            settings.IndentChars = "   ";
            XmlWriter writer = XmlWriter.Create(xmlOutputFile, settings);
            docOut.Save(writer);
            writer.Close();
            File.Delete(fullPath);
        }
        catch (Exception __dummyCatchVar4)
        {
            return ;
        }
    
    }

}


