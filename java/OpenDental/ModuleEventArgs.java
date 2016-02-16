//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:22 PM
//

package OpenDental;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
* 
*/
public class ModuleEventArgs  extends System.EventArgs 
{
    private DateTime dateSelected = new DateTime();
    private List<long> pinAppts = new List<long>();
    private long selectedAptNum = new long();
    private int iModule = new int();
    private long claimNum = new long();
    private long patNum = new long();
    private long docNum = new long();
    //image
    /**
    * 
    */
    public ModuleEventArgs(DateTime dateSelected, List<long> pinAppts, long selectedAptNum, int iModule, long claimNum, long patNum, long docNum) throws Exception {
        super();
        this.dateSelected = dateSelected;
        this.pinAppts = pinAppts;
        this.selectedAptNum = selectedAptNum;
        this.iModule = iModule;
        this.claimNum = claimNum;
        this.patNum = patNum;
        this.docNum = docNum;
    }

    /**
    * If going to the ApptModule, this lets you pick a date.
    */
    public DateTime getDateSelected() throws Exception {
        return dateSelected;
    }

    /**
    * The aptNums of the appointments that we want to put on the pinboard of the Apt Module.
    */
    public List<long> getPinAppts() throws Exception {
        return pinAppts;
    }

    /**
    * 
    */
    public long getSelectedAptNum() throws Exception {
        return selectedAptNum;
    }

    /**
    * 
    */
    public int getIModule() throws Exception {
        return iModule;
    }

    /**
    * If going to Account module, this lets you pick a claim.
    */
    public long getClaimNum() throws Exception {
        return claimNum;
    }

    /**
    * 
    */
    public long getPatNum() throws Exception {
        return patNum;
    }

    /**
    * If going to Images module, this lets you pick which image.
    */
    public long getDocNum() throws Exception {
        return docNum;
    }

}


