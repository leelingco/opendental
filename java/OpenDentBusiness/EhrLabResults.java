//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:41 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.EhrLabNotes;
import OpenDentBusiness.EhrLabResult;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class EhrLabResults   
{
    /**
    * 
    */
    public static EhrLabResult insertItem(EhrLabResult ehrLabResult) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            ehrLabResult.EhrLabResultNum = Meth.GetLong(MethodBase.GetCurrentMethod(), ehrLabResult);
            return ehrLabResult;
        }
         
        ehrLabResult.EhrLabResultNum = Crud.EhrLabResultCrud.Insert(ehrLabResult);
        for (int i = 0;i < ehrLabResult.getListEhrLabResultNotes().Count;i++)
        {
            //save attached notes.
            ehrLabResult.getListEhrLabResultNotes()[i].EhrLabNum = ehrLabResult.EhrLabNum;
            ehrLabResult.getListEhrLabResultNotes()[i].EhrLabResultNum = ehrLabResult.EhrLabResultNum;
            EhrLabNotes.Insert(ehrLabResult.getListEhrLabResultNotes()[i]);
        }
        return ehrLabResult;
    }

    /**
    * Does not insert lab result notes if attached.  Use InsertItem instead.
    */
    public static long insert(EhrLabResult ehrLabResult) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetLong(MethodBase.GetCurrentMethod(), ehrLabResult);
        }
         
        return Crud.EhrLabResultCrud.Insert(ehrLabResult);
    }

    /**
    * Get all lab results for one patient.
    */
    public static List<EhrLabResult> getAllForPatient(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EhrLabResult>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM ehrlabresult WHERE EhrLabNum IN (SELECT EhrLabNum FROM ehrlab WHERE PatNum=" + POut.long(patNum) + ")";
        return Crud.EhrLabResultCrud.SelectMany(command);
    }

    /**
    * Returns all EhrLabResults for a given EhrLab.
    */
    public static List<EhrLabResult> getForLab(long ehrLabNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EhrLabResult>>GetObject(MethodBase.GetCurrentMethod(), ehrLabNum);
        }
         
        String command = "SELECT * FROM ehrlabresult WHERE EhrLabNum = " + POut.long(ehrLabNum);
        return Crud.EhrLabResultCrud.SelectMany(command);
    }

    /**
    * Only deletes the notes for the Lab, there may still be notes attached to LabResults, that are attached to the lab.  Those notes are taken care of by DeleteForLabResults().
    */
    public static void deleteForLab(long ehrLabNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ehrLabNum);
            return ;
        }
         
        String command = "DELETE FROM ehrlabresult WHERE EhrLabNum = " + POut.long(ehrLabNum);
        Db.nonQ(command);
    }

    /**
    * Helper function to return a list of descriptions for the HL70078 enumeration.
    */
    public static List<String> getHL70078Descriptions() throws Exception {
        //No need to check RemotingRole;
        List<String> retVal = new List<String>();
        retVal.Add("Abnormal");
        //A
        retVal.Add("Above absolute high-off instrument scale");
        //_gt
        retVal.Add("Above high normal");
        //H
        retVal.Add("Above upper panic limits");
        //HH
        retVal.Add("Below absolute low-off instrument scale");
        //_lt
        retVal.Add("Below low normal");
        //L
        retVal.Add("Below lower panic limits");
        //LL
        retVal.Add("Better");
        //B
        retVal.Add("Intermediate");
        //I
        retVal.Add("Moderately susceptible");
        //MS
        retVal.Add("No range defined");
        //_null
        retVal.Add("Normal");
        //N
        retVal.Add("Resistant");
        //R
        retVal.Add("Significant change down");
        //D
        retVal.Add("Significant change up");
        //U
        retVal.Add("Susceptible");
        //S
        retVal.Add("Very abnormal");
        //AA
        retVal.Add("Very susceptible");
        //VS
        retVal.Add("Worse");
        return retVal;
    }

    //W
    /**
    * Helper function to return a list of descriptions for the HL70085 enumeration.  First item in the list is blank.
    */
    public static List<String> getHL70085Descriptions() throws Exception {
        //No need to check RemotingRole;
        List<String> retVal = new List<String>();
        retVal.Add("");
        //Blank
        retVal.Add("Deletes OBX");
        //D
        retVal.Add("Final results");
        //F
        retVal.Add("Not asked");
        //N
        retVal.Add("Order description only");
        //O
        retVal.Add("Partial results");
        //S
        retVal.Add("Wrong");
        //W
        retVal.Add("Preliminary results");
        //P
        retVal.Add("Correction");
        //C
        retVal.Add("Cannot be obtained");
        //X
        retVal.Add("Results entered (not verified)");
        //R
        retVal.Add("Final w/o retransmitting");
        //U
        retVal.Add("Specimen in lab");
        return retVal;
    }

    //I
    /**
    * Helper function to return a list of descriptions for the HL70125 enumeration.  First item in the list is blank.
    */
    public static List<String> getHL70125Descriptions() throws Exception {
        //No need to check RemotingRole;
        List<String> retVal = new List<String>();
        retVal.Add("");
        //Blank
        retVal.Add("Coded entry");
        //CE
        retVal.Add("Coded with exceptions");
        //CWE
        retVal.Add("Date");
        //DT
        retVal.Add("Formatted text");
        //FT
        retVal.Add("Numeric");
        //NM
        retVal.Add("Structured numeric");
        //SN
        retVal.Add("String data");
        //ST
        retVal.Add("Time");
        //TM
        retVal.Add("Time stamp");
        //TS
        retVal.Add("Text data");
        return retVal;
    }

    //TX
    /**
    * Helper function to return a list of descriptions for the HL70190 enumeration.  First item in the list is blank.
    */
    public static List<String> getHL70190Descriptions() throws Exception {
        //No need to check RemotingRole;
        List<String> retVal = new List<String>();
        retVal.Add("");
        //Blank
        retVal.Add("Bad address");
        //BA
        retVal.Add("Birth address, not otherwise specified");
        //N
        retVal.Add("Birth delivery location");
        //BDL
        retVal.Add("Country of origin");
        //F
        retVal.Add("Current or temporary");
        //C
        retVal.Add("Firm/Business");
        //B
        retVal.Add("Home");
        //H
        retVal.Add("Legal address");
        //L
        retVal.Add("Mailing");
        //M
        retVal.Add("Office");
        //O
        retVal.Add("Permanent");
        //P
        retVal.Add("Registry home");
        //RH
        retVal.Add("Residence at birth");
        return retVal;
    }

    //BR
    /**
    * Helper function to return a list of descriptions for the HL70200 enumeration.  First item in the list is blank.
    */
    public static List<String> getHL70200Descriptions() throws Exception {
        //No need to check RemotingRole;
        List<String> retVal = new List<String>();
        retVal.Add("");
        //Blank
        retVal.Add("Adopted Name");
        //C
        retVal.Add("Alias Name");
        //A
        retVal.Add("Coded Pseudo-Name");
        //S
        retVal.Add("Display Name");
        //D
        retVal.Add("Indigenous/Tribal/Community Name");
        //T
        retVal.Add("Legal Name");
        //L
        retVal.Add("Licensing Name");
        //I
        retVal.Add("Maiden Name");
        //M
        retVal.Add("Name at Birth");
        //B
        retVal.Add("Name of Partner/Spouse");
        //P
        retVal.Add("Nickname");
        //N
        retVal.Add("Registered Name");
        //R
        retVal.Add("Unspecified");
        return retVal;
    }

    //U
    /**
    * Helper function to return a list of descriptions for the USPSAlphaStateCode enumeration.  First item in the list is blank.
    */
    public static List<String> getUSPSAlphaStateCodeDescriptions() throws Exception {
        //No need to check RemotingRole;
        List<String> retVal = new List<String>();
        retVal.Add("");
        //Blank
        retVal.Add("Alabama");
        //AL
        retVal.Add("Alaska");
        //AK
        retVal.Add("Arizona");
        //AZ
        retVal.Add("Arkansas");
        //AR
        retVal.Add("California");
        //CA
        retVal.Add("Colorado");
        //CO
        retVal.Add("Connecticut");
        //CT
        retVal.Add("Delaware");
        //DE
        retVal.Add("District of Columbia");
        //DC
        retVal.Add("Florida");
        //FL
        retVal.Add("Georgia");
        //GA
        retVal.Add("Hawaii");
        //HI
        retVal.Add("Idaho");
        //ID
        retVal.Add("Illinois");
        //IL
        retVal.Add("Indiana");
        //IN
        retVal.Add("Iowa");
        //IA
        retVal.Add("Kansas");
        //KS
        retVal.Add("Kentucky");
        //KY
        retVal.Add("Louisiana");
        //LA
        retVal.Add("Maine");
        //ME
        retVal.Add("Maryland");
        //MD
        retVal.Add("Massachusetts");
        //MA
        retVal.Add("Michigan");
        //MI
        retVal.Add("Minnesota");
        //MN
        retVal.Add("Mississippi");
        //MS
        retVal.Add("Missouri");
        //MO
        retVal.Add("Montana");
        //MT
        retVal.Add("Nebraska");
        //NE
        retVal.Add("Nevada");
        //NV
        retVal.Add("New Hampshire");
        //NH
        retVal.Add("New Jersey");
        //NJ
        retVal.Add("New Mexico");
        //NM
        retVal.Add("New York");
        //NY
        retVal.Add("North Carolina");
        //NC
        retVal.Add("North Dakota");
        //ND
        retVal.Add("Ohio");
        //OH
        retVal.Add("Oklahoma");
        //OK
        retVal.Add("Oregon");
        //OR
        retVal.Add("Pennsylvania");
        //PA
        retVal.Add("Rhode Island");
        //RI
        retVal.Add("South Carolina");
        //SC
        retVal.Add("South Dakota");
        //SD
        retVal.Add("Tennessee");
        //TN
        retVal.Add("Texas");
        //TX
        retVal.Add("Utah");
        //UT
        retVal.Add("Vermont");
        //VT
        retVal.Add("Virginia");
        //VA
        retVal.Add("Washington");
        //WA
        retVal.Add("West Virginia");
        //WV
        retVal.Add("Wisconsin");
        //WI
        retVal.Add("Wyoming");
        //WY
        retVal.Add("American Samoa");
        //AS
        retVal.Add("Federated States of Micronesia");
        //FM
        retVal.Add("Guam");
        //GU
        retVal.Add("Marshall Islands");
        //MH
        retVal.Add("Northern Mariana Islands");
        //MP
        retVal.Add("Palau");
        //PW
        retVal.Add("Puerto Rico");
        //PR
        retVal.Add("U.S. Minor Outlying Islands");
        //UM
        retVal.Add("Virgin Islands of the U.S.");
        return retVal;
    }

}


//VI
//If this table type will exist as cached data, uncomment the CachePattern region below and edit.
/*
		#region CachePattern
		//This region can be eliminated if this is not a table type with cached data.
		//If leaving this region in place, be sure to add RefreshCache and FillCache 
		//to the Cache.cs file with all the other Cache types.
		///<summary>A list of all EhrLabResults.</summary>
		private static List<EhrLabResult> listt;
		///<summary>A list of all EhrLabResults.</summary>
		public static List<EhrLabResult> Listt{
			get {
				if(listt==null) {
					RefreshCache();
				}
				return listt;
			}
			set {
				listt=value;
			}
		}
		///<summary></summary>
		public static DataTable RefreshCache(){
			//No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
			string command="SELECT * FROM ehrlabresult ORDER BY ItemOrder";//stub query probably needs to be changed
			DataTable table=Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(),command);
			table.TableName="EhrLabResult";
			FillCache(table);
			return table;
		}
		///<summary></summary>
		public static void FillCache(DataTable table){
			//No need to check RemotingRole; no call to db.
			listt=Crud.EhrLabResultCrud.TableToList(table);
		}
		#endregion
		*/
/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<EhrLabResult> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<EhrLabResult>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM ehrlabresult WHERE PatNum = "+POut.Long(patNum);
			return Crud.EhrLabResultCrud.SelectMany(command);
		}
		///<summary>Gets one EhrLabResult from the db.</summary>
		public static EhrLabResult GetOne(long ehrLabResultNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<EhrLabResult>(MethodBase.GetCurrentMethod(),ehrLabResultNum);
			}
			return Crud.EhrLabResultCrud.SelectOne(ehrLabResultNum);
		}
		///<summary></summary>
		public static void Update(EhrLabResult ehrLabResult){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				Meth.GetVoid(MethodBase.GetCurrentMethod(),ehrLabResult);
				return;
			}
			Crud.EhrLabResultCrud.Update(ehrLabResult);
		}
		///<summary></summary>
		public static void Delete(long ehrLabResultNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),ehrLabResultNum);
				return;
			}
			string command= "DELETE FROM ehrlabresult WHERE EhrLabResultNum = "+POut.Long(ehrLabResultNum);
			Db.NonQ(command);
		}
		*/