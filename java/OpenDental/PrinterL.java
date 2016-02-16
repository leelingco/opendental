//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:30 PM
//

package OpenDental;

import OpenDentBusiness.Permissions;
import OpenDentBusiness.Printer;
import OpenDentBusiness.Printers;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.SecurityLogs;

public class PrinterL   
{
    /**
    * Called from many places in the program.  Every single time we print, this function is used to figure out which printer to use.  It also handles displaying the dialog if necessary.  Tests to see if the selected printer is valid, and if not, then it gives user the option to print to an available printer.  PatNum and AuditDescription used to make audit log entry.  PatNum can be 0.  Audit Log Text will show AuditDescription exactly.
    */
    public static boolean setPrinter(PrintDocument pd, PrintSituation sit, long patNum, String auditDescription) throws Exception {
        PrinterSettings pSet = pd.PrinterSettings;
        //pSet will always be new when this function is called
        //0.5. Get the name of the Windows default printer.
        //This method only works when the pSet is still new.
        //string winDefault=pSet.PrinterName;
        //1. If a default printer is set in OD,
        //and it is in the list of installed printers, use it.
        boolean doPrompt = false;
        Printer printerForSit = Printers.getForSit(PrintSituation.Default);
        //warning: this changes
        String printerName = "";
        if (printerForSit != null)
        {
            printerName = printerForSit.PrinterName;
            doPrompt = printerForSit.DisplayPrompt;
            if (Printers.printerIsInstalled(printerName))
            {
                pSet.PrinterName = printerName;
            }
             
        }
         
        //2. If a printer is set for this situation,
        //and it is in the list of installed printers, use it.
        if (sit != PrintSituation.Default)
        {
            printerForSit = Printers.getForSit(sit);
            printerName = "";
            if (printerForSit != null)
            {
                printerName = printerForSit.PrinterName;
                doPrompt = printerForSit.DisplayPrompt;
                if (Printers.printerIsInstalled(printerName))
                {
                    pSet.PrinterName = printerName;
                }
                 
            }
             
        }
         
        //4. Present the dialog
        if (!doPrompt)
        {
            //Create audit log entry for printing.  PatNum can be 0.
            SecurityLogs.makeLogEntry(Permissions.Printing,patNum,auditDescription);
            return true;
        }
         
        PrintDialog dialog = new PrintDialog();
        //pSet.Collate is true here
        dialog.PrinterSettings = pSet;
        dialog.UseEXDialog = true;
        DialogResult result = dialog.ShowDialog();
        //but dialog.PrinterSettings.Collate is false here.  I don't know what triggers the change.
        pSet.Collate = true;
        //force it back to true.
        if (result != DialogResult.OK)
        {
            return false;
        }
         
        //if(!dialog.PrinterSettings.IsValid){//not needed since we have already checked each name.
        //pd2.PrinterSettings=printDialog2.PrinterSettings;
        //}
        //Create audit log entry for printing.  PatNum can be 0.
        SecurityLogs.makeLogEntry(Permissions.Printing,patNum,auditDescription);
        return true;
    }

}


