//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Operatory;
import OpenDentBusiness.TableBase;

/**
* Each row is a single operatory or column in the appts module.
*/
public class Operatory  extends TableBase 
{
    /**
    * Primary key
    */
    public long OperatoryNum = new long();
    /**
    * The full name to show in the column.
    */
    public String OpName = new String();
    /**
    * 5 char or less. Not used much.
    */
    public String Abbrev = new String();
    /**
    * The order that this op column will show.  Changing views only hides some ops; it does not change their order.  Zero based.
    */
    public int ItemOrder = new int();
    /**
    * Used instead of deleting to hide an op that is no longer used.
    */
    public boolean IsHidden = new boolean();
    /**
    * FK to provider.ProvNum.  The dentist assigned to this op.  If more than one dentist might be assigned to an op, then create a second op and use one for each dentist. If 0, then no dentist is assigned.
    */
    public long ProvDentist = new long();
    /**
    * FK to provider.ProvNum.  The hygienist assigned to this op.  If 0, then no hygienist is assigned.
    */
    public long ProvHygienist = new long();
    /**
    * Set true if this is a hygiene operatory.  The hygienist will then be considered the main provider for this op.
    */
    public boolean IsHygiene = new boolean();
    /**
    * FK to clinic.ClinicNum.  0 if no clinic.
    */
    public long ClinicNum = new long();
    /**
    * If true patients put into this operatory will have status set to prospective.
    */
    public boolean SetProspective = new boolean();
    //DateTStamp
    /**
    * Returns a copy of this Operatory.
    */
    public Operatory copy() throws Exception {
        return (Operatory)this.MemberwiseClone();
    }

}


/*
		public Operatory(){
		}
		
		public Operatory(long operatoryNum,string opName,string abbrev,int itemOrder,bool isHidden,long provDentist,
			long provHygienist,bool isHygiene,long clinicNum)
		{
			OperatoryNum=operatoryNum;
			OpName=opName;
			Abbrev=abbrev;
			ItemOrder=itemOrder;
			IsHidden=isHidden;
			ProvDentist=provDentist;
			ProvHygienist=provHygienist;
			IsHygiene=isHygiene;
			ClinicNum=clinicNum;
		}*/