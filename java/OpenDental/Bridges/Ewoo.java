//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:27 PM
//

package OpenDental.Bridges;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.Programs;

/**
* 
*/
public class Ewoo   
{
    /**
    * 
    */
    public Ewoo() throws Exception {
    }

    /**
    * Sends data for the patient to linkage.xml and launches the program.
    */
    public static void sendData(Program ProgramCur, Patient pat) throws Exception {
        String path = Programs.getProgramPath(ProgramCur);
        if (!File.Exists(path))
        {
            MessageBox.Show(path + " could not be found.");
            return ;
        }
         
        String dir = Path.GetDirectoryName(path);
        String linkage = CodeBase.ODFileUtils.combinePaths(dir,"linkage.xml");
        if (File.Exists(linkage))
        {
            File.Delete(linkage);
        }
         
        if (pat != null)
        {
            StringBuilder strb = new StringBuilder();
            XmlWriterSettings settings = new XmlWriterSettings();
            settings.Indent = true;
            settings.IndentChars = "   ";
            settings.NewLineChars = "\r\n";
            settings.OmitXmlDeclaration = true;
            XmlWriter writer = XmlWriter.Create(strb, settings);
            writer.WriteStartElement("LinkageParameter");
            writer.WriteStartElement("Patient");
            writer.WriteAttributeString("LastName", pat.LName);
            writer.WriteAttributeString("FirstName", pat.FName);
            if (StringSupport.equals(ProgramProperties.getPropVal(ProgramCur.ProgramNum,"Enter 0 to use PatientNum, or 1 to use ChartNum"), "0"))
            {
                writer.WriteAttributeString("ChartNumber", pat.PatNum.ToString());
            }
            else
            {
                writer.WriteAttributeString("ChartNumber", pat.ChartNumber);
            } 
            writer.WriteElementString("Birthday", pat.Birthdate.ToString("dd/MM/yyyy"));
            String addr = pat.Address;
            if (!StringSupport.equals(pat.Address2, ""))
            {
                addr += ", " + pat.Address2;
            }
             
            addr += ", " + pat.City + ", " + pat.State;
            writer.WriteElementString("Address", addr);
            writer.WriteElementString("ZipCode", pat.Zip);
            writer.WriteElementString("Phone", pat.HmPhone);
            writer.WriteElementString("Mobile", pat.WirelessPhone);
            writer.WriteElementString("SocialID", pat.SSN);
            if (pat.Gender == PatientGender.Female)
            {
                writer.WriteElementString("Gender", "Female");
            }
            else
            {
                writer.WriteElementString("Gender", "Male");
            } 
            writer.WriteEndElement();
            //Patient
            writer.WriteEndElement();
            //LinkageParameter
            writer.Flush();
            writer.Close();
            //MessageBox.Show(strb.ToString());
            File.WriteAllText(linkage, strb.ToString());
        }
         
        try
        {
            //whether there is a patient or not.
            Process.Start(path);
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show("Error launching " + path);
        }
    
    }

}


