//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:30 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Bridges.Apixia;
import OpenDental.Bridges.Apteryx;
import OpenDental.Bridges.BioPAK;
import OpenDental.Bridges.Camsight;
import OpenDental.Bridges.CaptureLink;
import OpenDental.Bridges.Cerec;
import OpenDental.Bridges.Cliniview;
import OpenDental.Bridges.ClioSoft;
import OpenDental.Bridges.DBSWin;
import OpenDental.Bridges.DentalEye;
import OpenDental.Bridges.DentalStudio;
import OpenDental.Bridges.DentForms;
import OpenDental.Bridges.DentX;
import OpenDental.Bridges.Dexis;
import OpenDental.Bridges.Digora;
import OpenDental.Bridges.Dolphin;
import OpenDental.Bridges.DrCeph;
import OpenDental.Bridges.Dxis;
import OpenDental.Bridges.Ewoo;
import OpenDental.Bridges.FloridaProbe;
import OpenDental.Bridges.Guru;
import OpenDental.Bridges.ICat;
import OpenDental.Bridges.ImageFX;
import OpenDental.Bridges.Lightyear;
import OpenDental.Bridges.MediaDent;
import OpenDental.Bridges.MiPACS;
import OpenDental.Bridges.Owandy;
import OpenDental.Bridges.PaperlessTechnology;
import OpenDental.Bridges.Patterson;
import OpenDental.Bridges.PerioPal;
import OpenDental.Bridges.Planmeca;
import OpenDental.Bridges.Progeny;
import OpenDental.Bridges.RayMage;
import OpenDental.Bridges.Schick;
import OpenDental.Bridges.Sirona;
import OpenDental.Bridges.Sopro;
import OpenDental.Bridges.TigerView;
import OpenDental.Bridges.Trophy;
import OpenDental.Bridges.TrophyEnhanced;
import OpenDental.Bridges.Tscan;
import OpenDental.Bridges.Vipersoft;
import OpenDental.Bridges.VixWin;
import OpenDental.Bridges.VixWinBase41;
import OpenDental.Bridges.VixWinOld;
import OpenDental.EvaSoft;
import OpenDental.FormHouseCalls;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Plugins;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramC;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.Programs;

/**
* 
*/
public class ProgramL   
{
    /**
    * Typically used when user clicks a button to a Program link.  This method attempts to identify and execute the program based on the given programNum.
    */
    public static void execute(long programNum, Patient pat) throws Exception {
        Program prog = null;
        for (int i = 0;i < ProgramC.getListt().Count;i++)
        {
            if (ProgramC.getListt()[i].ProgramNum == programNum)
            {
                prog = ProgramC.getListt()[i];
            }
             
        }
        if (prog == null)
        {
            //no match was found
            MessageBox.Show("Error, program entry not found in database.");
            return ;
        }
         
        if (!StringSupport.equals(prog.PluginDllName, ""))
        {
            if (pat != null)
            {
                Plugins.launchToolbarButton(programNum,pat.PatNum);
            }
             
            return ;
        }
         
        if (StringSupport.equals(prog.ProgName, ProgramName.Apixia.ToString()))
        {
            Apixia.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.Apteryx.ToString()))
        {
            Apteryx.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.BioPAK.ToString()))
        {
            BioPAK.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.Camsight.ToString()))
        {
            Camsight.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.CaptureLink.ToString()))
        {
            CaptureLink.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.Cerec.ToString()))
        {
            Cerec.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.CliniView.ToString()))
        {
            Cliniview.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.ClioSoft.ToString()))
        {
            ClioSoft.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.DBSWin.ToString()))
        {
            DBSWin.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.DentalEye.ToString()))
        {
            DentalEye.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.DentalStudio.ToString()))
        {
            DentalStudio.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.DentX.ToString()))
        {
            DentX.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.DrCeph.ToString()))
        {
            DrCeph.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.DentForms.ToString()))
        {
            DentForms.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.Dexis.ToString()))
        {
            Dexis.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.Digora.ToString()))
        {
            Digora.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.Dolphin.ToString()))
        {
            Dolphin.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.Dxis.ToString()))
        {
            Dxis.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.EvaSoft.ToString()))
        {
            EvaSoft.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.EwooEZDent.ToString()))
        {
            Ewoo.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.FloridaProbe.ToString()))
        {
            FloridaProbe.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.Guru.ToString()))
        {
            Guru.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.HouseCalls.ToString()))
        {
            FormHouseCalls FormHC = new FormHouseCalls();
            FormHC.ProgramCur = prog;
            FormHC.ShowDialog();
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.iCat.ToString()))
        {
            ICat.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.ImageFX.ToString()))
        {
            ImageFX.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.Lightyear.ToString()))
        {
            Lightyear.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.MediaDent.ToString()))
        {
            MediaDent.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.MiPACS.ToString()))
        {
            MiPACS.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.Owandy.ToString()))
        {
            Owandy.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.Patterson.ToString()))
        {
            Patterson.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.PerioPal.ToString()))
        {
            PerioPal.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.Planmeca.ToString()))
        {
            Planmeca.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.Progeny.ToString()))
        {
            Progeny.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.PT.ToString()))
        {
            PaperlessTechnology.sendData(prog,pat,false);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.PTupdate.ToString()))
        {
            PaperlessTechnology.sendData(prog,pat,true);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.RayMage.ToString()))
        {
            RayMage.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.Schick.ToString()))
        {
            Schick.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.Sirona.ToString()))
        {
            Sirona.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.Sopro.ToString()))
        {
            Sopro.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.TigerView.ToString()))
        {
            TigerView.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.Trophy.ToString()))
        {
            Trophy.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.TrophyEnhanced.ToString()))
        {
            TrophyEnhanced.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.Tscan.ToString()))
        {
            Tscan.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.Vipersoft.ToString()))
        {
            Vipersoft.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.VixWin.ToString()))
        {
            VixWin.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.VixWinBase41.ToString()))
        {
            VixWinBase41.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.VixWinOld.ToString()))
        {
            VixWinOld.sendData(prog,pat);
            return ;
        }
        else if (StringSupport.equals(prog.ProgName, ProgramName.XDR.ToString()))
        {
            Dexis.sendData(prog,pat);
            return ;
        }
                                                        
        try
        {
            //XDR uses the Dexis protocol
            //all remaining programs:
            String cmdline = prog.CommandLine;
            String path = Programs.getProgramPath(prog);
            if (pat != null)
            {
                cmdline = cmdline.Replace("[LName]", pat.LName);
                cmdline = cmdline.Replace("[FName]", pat.FName);
                cmdline = cmdline.Replace("[PatNum]", pat.PatNum.ToString());
                cmdline = cmdline.Replace("[ChartNumber]", pat.ChartNumber);
                cmdline = cmdline.Replace("[WirelessPhone]", pat.WirelessPhone);
                cmdline = cmdline.Replace("[HmPhone]", pat.HmPhone);
                cmdline = cmdline.Replace("[WkPhone]", pat.WkPhone);
                cmdline = cmdline.Replace("[LNameLetter]", pat.LName.Substring(0, 1).ToUpper());
                path = path.Replace("[LName]", pat.LName);
                path = path.Replace("[FName]", pat.FName);
                path = path.Replace("[PatNum]", pat.PatNum.ToString());
                path = path.Replace("[ChartNumber]", pat.ChartNumber);
                path = path.Replace("[WirelessPhone]", pat.WirelessPhone);
                path = path.Replace("[HmPhone]", pat.HmPhone);
                path = path.Replace("[WkPhone]", pat.WkPhone);
                path = path.Replace("[LNameLetter]", pat.LName.Substring(0, 1).ToUpper());
            }
             
            Process.Start(path, cmdline);
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show(prog.ProgDesc + " is not available.");
            return ;
        }
    
    }

}


