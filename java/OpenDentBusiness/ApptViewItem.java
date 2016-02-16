//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:08 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ApptViewAlignment;
import OpenDentBusiness.TableBase;

/**
* Each item is attached to a row in the apptview table.  Each item specifies ONE of: OpNum, ProvNum, ElementDesc, or ApptFieldDefNum.  The other three will be 0 or "".
*/
public class ApptViewItem  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ApptViewItemNum = new long();
    //
    /**
    * FK to apptview.
    */
    public long ApptViewNum = new long();
    /**
    * FK to operatory.OperatoryNum.
    */
    public long OpNum = new long();
    /**
    * FK to provider.ProvNum.
    */
    public long ProvNum = new long();
    /**
    * Must be one of the hard coded strings picked from the available list.
    */
    public String ElementDesc = new String();
    /**
    * If this is a row Element, then this is the 0-based order within its area.  For example, UR starts over with 0 ordering.
    */
    public byte ElementOrder = new byte();
    /**
    * If this is an element, then this is the color.
    */
    public Color ElementColor = new Color();
    /**
    * Enum:ApptViewAlignment If this is an element, then this is the alignment of the element within the appointment.
    */
    public ApptViewAlignment ElementAlignment = ApptViewAlignment.Main;
    /**
    * FK to apptfielddef.ApptFieldDefNum.  If this is an element, and the element is an appt field, then this tells us which one.
    */
    public long ApptFieldDefNum = new long();
    /**
    * FK to patfielddef.PatFieldDefNum.  If this is an element, and the element is an appt field, then this tells us which one.  Not implemented yet.
    */
    public long PatFieldDefNum = new long();
    public ApptViewItem() throws Exception {
    }

    /**
    * this constructor is just used in GetForCurView when no view selected.
    */
    public ApptViewItem(String elementDesc, byte elementOrder, Color elementColor) throws Exception {
        ApptViewItemNum = 0;
        ApptViewNum = 0;
        OpNum = 0;
        ProvNum = 0;
        ElementDesc = elementDesc;
        ElementOrder = elementOrder;
        ElementColor = elementColor;
        ElementAlignment = ApptViewAlignment.Main;
    }

    /**
    * Used only for serialization purposes
    */
    public int getElementColorXml() throws Exception {
        return ElementColor.ToArgb();
    }

    public void setElementColorXml(int value) throws Exception {
        ElementColor = Color.FromArgb(value);
    }

}


