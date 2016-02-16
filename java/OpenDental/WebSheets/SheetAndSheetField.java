//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.WebSheets;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.WebSheets.webforms_sheet;
import OpenDental.WebSheets.webforms_sheetfield;


/**
* 
*/
public class SheetAndSheetField   
{

    private webforms_sheet web_sheetField;
    private webforms_sheetfield[] web_sheetfieldlistField = new webforms_sheetfield[]();
    /**
    * 
    */
    public webforms_sheet getweb_sheet() throws Exception {
        return this.web_sheetField;
    }

    public void setweb_sheet(webforms_sheet value) throws Exception {
        this.web_sheetField = value;
    }

    /**
    * 
    */
    public webforms_sheetfield[] getweb_sheetfieldlist() throws Exception {
        return this.web_sheetfieldlistField;
    }

    public void setweb_sheetfieldlist(webforms_sheetfield[] value) throws Exception {
        this.web_sheetfieldlistField = value;
    }

}


