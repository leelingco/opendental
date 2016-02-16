//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.Sheet;
import OpenDentBusiness.SheetField;
import OpenDentBusiness.SheetParameter;
import OpenDentBusiness.SheetTypeEnum;
import OpenDentBusiness.TableBase;

/*A better name for this object would be a Form, but that name is obviously too ambiguous and has been overused.  There are two different aspects of the database tables:
	1. Customization of sheets.
	2. Saving data filled in on sheets.
	Sheets do not include reports, which are better handled by the RDL framework or something even simpler.  Examples of what sheets might be used for include statements, tx plans, rx, lab slips, postcards, referral slips, patient registration forms, medical histories, consent forms, and labels.
	The interesting thing about this framework is that it is able to support incoming data as well as outgoing data using the following elements:
	-background image
	-static text
	-text generated from database
	-user input
	Class names:
	Data: Sheet, SheetField. (Parameters are saved as part of fields, except PatNum is part of Sheet)
	Defs:	SheetDef, SheetFieldDef (SheetParameters are hardcoded based on type)
  SheetImage (handling this with files for now)
	
	Note that we have tried to do similar things before, but not with as much clarity and organization.  See the ReportingOld2 folder for an example of a similar framework that never took off because:
	a) It was overwhelming because it was trying to handle 'reporting' functions as its main purpose.
	b) It did not start with a simpler framework and build iteratively.
	c) It was modeled after Crystal Reports, which was only designed for reports, not forms.
	d) We did not have generics.
	*/
/**
* One sheet for one patient.
*/
public class Sheet  extends TableBase 
{
    /**
    * Primary key.
    */
    public long SheetNum = new long();
    /**
    * Enum:SheetTypeEnum
    */
    public SheetTypeEnum SheetType = SheetTypeEnum.LabelPatient;
    /**
    * FK to patient.PatNum.  A saved sheet is always attached to a patient (except deposit slip).  There are a few sheets that are so minor that they don't get saved, such as a Carrier label.
    */
    public long PatNum = new long();
    /**
    * The date and time of the sheet as it will be displayed in the commlog.
    */
    public DateTime DateTimeSheet = new DateTime();
    /**
    * The default fontSize for the sheet.  The actual font must still be saved with each sheetField.
    */
    public float FontSize = new float();
    /**
    * The default fontName for the sheet.  The actual font must still be saved with each sheetField.
    */
    public String FontName = new String();
    /**
    * Width of the sheet in pixels, 100 pixels per inch.
    */
    public int Width = new int();
    /**
    * Height of the sheet in pixels, 100 pixels per inch.
    */
    public int Height = new int();
    /**
    * .
    */
    public boolean IsLandscape = new boolean();
    /**
    * An internal note for the use of the office staff regarding the sheet.  Not to be printed on the sheet in any way.
    */
    public String InternalNote = new String();
    /**
    * Copied from the SheetDef description.
    */
    public String Description = new String();
    /**
    * The order that this sheet will show in the patient terminal for the patient to fill out.  Or zero if not set.
    */
    public byte ShowInTerminal = new byte();
    /**
    * True if this sheet was downloaded from the webforms service.
    */
    public boolean IsWebForm = new boolean();
    public Sheet copy() throws Exception {
        return (Sheet)this.MemberwiseClone();
    }

    /**
    * A collection of all parameters for this sheetdef.  There's usually only one parameter.  The first parameter will be a List long if it's a batch.  If a sheet has already been filled, saved to the database, and printed, then there is no longer any need for the parameters in order to fill the data.  So a retrieved sheet will have no parameters, signalling a skip in the fill phase.  There will still be parameters tucked away in the Field data in the database, but they won't become part of the sheet.
    */
    public List<SheetParameter> Parameters = new List<SheetParameter>();
    /**
    * 
    */
    public List<SheetField> SheetFields = new List<SheetField>();
    /*Parameters are not serialized as part of a sheet because it causes serialization to fail.
    		///<summary>Used only for serialization purposes</summary>
    		[XmlElement("Parameters",typeof(SheetParameter[]))]
    		public SheetParameter[] ParametersXml {
    			get {
    				if(Parameters==null || Parameters.Count==0) {
    					return new SheetParameter[0];
    				}
    				return Parameters.ToArray();
    			}
    			set {
    				Parameters=new List<SheetParameter>();
    				for(int i=0;i<value.Length;i++) {
    					Parameters.Add(value[i]);
    				}
    			}
    		}*/
    /**
    * Used only for serialization purposes
    */
    public SheetField[] getSheetFieldsXml() throws Exception {
        if (SheetFields == null)
        {
            return new SheetField[0];
        }
         
        return SheetFields.ToArray();
    }

    public void setSheetFieldsXml(SheetField[] value) throws Exception {
        SheetFields = new List<SheetField>();
        for (int i = 0;i < value.Length;i++)
        {
            SheetFields.Add(value[i]);
        }
    }

}


