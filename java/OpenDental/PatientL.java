//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:30 PM
//

package OpenDental;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDentBusiness.Family;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Plugins;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Security;
import OpenDentBusiness.Sites;

/**
* 
*/
public class PatientL   
{
    /**
    * Collection of Patient Names. The last five patients. Gets displayed on dropdown button.
    */
    private static List<String> buttonLastFiveNames = new List<String>();
    /**
    * Collection of PatNums. The last five patients. Used when clicking on dropdown button.
    */
    private static List<long> buttonLastFivePatNums = new List<long>();
    /**
    * The current patient will already be on the button.  This adds the family members when user clicks dropdown arrow. Can handle null values for pat and fam.  Need to supply the menu to fill as well as the EventHandler to set for each item (all the same).
    */
    public static void addFamilyToMenu(ContextMenu menu, EventHandler onClick, long patNum, Family fam) throws Exception {
        //No need to check RemotingRole; no call to db.
        //fill menu
        menu.MenuItems.Clear();
        for (int i = 0;i < buttonLastFiveNames.Count;i++)
        {
            menu.MenuItems.Add(buttonLastFiveNames[i].ToString(), onClick);
        }
        menu.MenuItems.Add("-");
        menu.MenuItems.Add("FAMILY");
        if (patNum != 0 && fam != null)
        {
            for (int i = 0;i < fam.ListPats.Length;i++)
            {
                menu.MenuItems.Add(fam.ListPats[i].GetNameLF(), onClick);
            }
        }
         
    }

    /**
    * Does not handle null values. Use zero.  Does not handle adding family members.  Returns true if patient has changed.
    */
    public static boolean addPatsToMenu(ContextMenu menu, EventHandler onClick, String nameLF, long patNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        //add current patient
        if (buttonLastFivePatNums == null)
        {
            buttonLastFivePatNums = new List<long>();
        }
         
        if (buttonLastFiveNames == null)
        {
            buttonLastFiveNames = new List<String>();
        }
         
        if (patNum == 0)
        {
            return false;
        }
         
        if (buttonLastFivePatNums.Count > 0 && patNum == buttonLastFivePatNums[0])
        {
            return false;
        }
         
        //same patient selected
        //Patient has changed
        buttonLastFivePatNums.Insert(0, patNum);
        buttonLastFiveNames.Insert(0, nameLF);
        if (buttonLastFivePatNums.Count > 5)
        {
            buttonLastFivePatNums.RemoveAt(5);
            buttonLastFiveNames.RemoveAt(5);
        }
         
        return true;
    }

    /**
    * Determines which menu Item was selected from the Patient dropdown list and returns the patNum for that patient. This will not be activated when click on 'FAMILY' or on separator, because they do not have events attached.  Calling class then does a ModuleSelected.
    */
    public static long buttonSelect(ContextMenu menu, Object sender, Family fam) throws Exception {
        //No need to check RemotingRole; no call to db.
        int index = menu.MenuItems.IndexOf((MenuItem)sender);
        //Patients.PatIsLoaded=true;
        if (index < buttonLastFivePatNums.Count)
        {
            return (long)buttonLastFivePatNums[index];
        }
         
        if (fam == null)
        {
            return 0;
        }
         
        return fam.ListPats[index - buttonLastFivePatNums.Count - 2].PatNum;
    }

    //will never happen
    /**
    * Accepts null.
    */
    public static String getMainTitle(Patient pat) throws Exception {
        String retVal = PrefC.getString(PrefName.MainWindowTitle);
        Object[] parameters;
        Plugins.hookAddCode(null,"PatientL.GetMainTitle_beginning",parameters);
        retVal = (String)parameters[0];
        if (Security.getCurUser() != null)
        {
            retVal += " {" + Security.getCurUser().UserName + "}";
        }
         
        if (pat == null || pat.PatNum == 0 || pat.PatNum == -1)
        {
            return retVal;
        }
         
        retVal += " - " + pat.getNameLF();
        if (PrefC.getLong(PrefName.ShowIDinTitleBar) == 1)
        {
            retVal += " - " + pat.PatNum.ToString();
        }
        else if (PrefC.getLong(PrefName.ShowIDinTitleBar) == 2)
        {
            retVal += " - " + pat.ChartNumber;
        }
        else if (PrefC.getLong(PrefName.ShowIDinTitleBar) == 3)
        {
            if (pat.Birthdate.Year > 1880)
            {
                retVal += " - " + pat.Birthdate.ToShortDateString();
            }
             
        }
           
        if (pat.SiteNum != 0)
        {
            retVal += " - " + Sites.getDescription(pat.SiteNum);
        }
         
        return retVal;
    }

}


