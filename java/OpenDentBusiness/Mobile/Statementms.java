//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Db;
import OpenDentBusiness.Mobile.Crud.StatementmCrud;
import OpenDentBusiness.Mobile.Statementm;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.Statement;
import OpenDentBusiness.Statements;

/**
* 
*/
public class Statementms   
{
    /**
    * Gets all Statementm for a single patient
    */
    public static List<Statementm> getStatementms(long customerNum, long patNum) throws Exception {
        String command = "SELECT * from statementm " + "WHERE CustomerNum = " + POut.long(customerNum) + " AND PatNum = " + POut.long(patNum);
        return StatementmCrud.selectMany(command);
    }

    /**
    * Limits the number of statements and documents in the database for a single patient
    */
    public static void limitStatementmsPerPatient(List<long> patList, long customerNum, int limitPerPatient) throws Exception {
        int upperlimit = 500 + limitPerPatient;
        // The figure 500 is somewhat arbitrary.
        String limitStr = "";
        if (limitPerPatient > 0)
        {
            limitStr = "LIMIT " + limitPerPatient + "," + upperlimit;
        }
        else
        {
            return ;
        } 
        for (int i = 0;i < patList.Count;i++)
        {
            String command = "SELECT StatementNum FROM statementm WHERE CustomerNum = " + POut.long(customerNum) + " AND PatNum = " + POut.Long(patList[i]) + " ORDER BY DateSent DESC, StatementNum DESC " + limitStr;
            DataTable table = Db.getTable(command);
            if (table.Rows.Count > 0)
            {
                String strStatementNums = " AND ( ";
                for (int j = 0;j < table.Rows.Count;j++)
                {
                    if (j > 0)
                    {
                        strStatementNums += "OR ";
                    }
                     
                    strStatementNums += "StatementNum='" + PIn.Long(table.Rows[j]["StatementNum"].ToString()) + "' ";
                }
                strStatementNums += " )";
                command = "DELETE FROM statementm WHERE CustomerNum = " + POut.long(customerNum) + " AND PatNum = " + POut.Long(patList[i]) + strStatementNums;
                Db.nonQ(command);
            }
             
        }
    }

    //Note: this statement does not work: error =This version of MySQL doesn't yet support 'LIMIT & IN/ALL/ANY/SOME subquery'
    //DELETE FROM statementm where StatementNum in (SELECT StatementNum FROM statementm WHERE CustomerNum=6566 AND
    //PatNum=7 ORDER BY DateSent DESC, StatementNum DESC LIMIT 5,100)
    /**
    * Fetches StatementNums restricted by the DateTStamp, PatNums and a limit of records per patient. If limitPerPatient is zero all StatementNums of a patient are fetched
    */
    public static List<long> getChangedSinceStatementNums(DateTime changedSince, List<long> eligibleForUploadPatNumList, int limitPerPatient) throws Exception {
        return Statements.GetChangedSinceStatementNums(changedSince, eligibleForUploadPatNumList, limitPerPatient);
    }

    /**
    * The values returned are sent to the webserver.
    */
    public static List<Statementm> getMultStatementms(List<long> statementNums) throws Exception {
        List<Statement> statementList = Statements.GetMultStatements(statementNums);
        List<Statementm> statementmList = ConvertListToM(statementList);
        return statementmList;
    }

    /**
    * First use GetChangedSince.  Then, use this to convert the list a list of 'm' objects.
    */
    public static List<Statementm> convertListToM(List<Statement> list) throws Exception {
        List<Statementm> retVal = new List<Statementm>();
        for (int i = 0;i < list.Count;i++)
        {
            retVal.Add(StatementmCrud.ConvertToM(list[i]));
        }
        return retVal;
    }

    /**
    * Only run on server for mobile.  Takes the list of changes from the dental office and makes updates to those items in the mobile server db.  Also, make sure to run DeletedObjects.DeleteForMobile().
    */
    public static void updateFromChangeList(List<Statementm> list, long customerNum) throws Exception {
        for (int i = 0;i < list.Count;i++)
        {
            list[i].CustomerNum = customerNum;
            Statementm statementm = StatementmCrud.SelectOne(customerNum, list[i].StatementNum);
            if (statementm == null)
            {
                //not in db
                StatementmCrud.Insert(list[i], true);
            }
            else
            {
                StatementmCrud.Update(list[i]);
            } 
        }
    }

    /**
    * used in tandem with Full synch
    */
    public static void deleteAll(long customerNum) throws Exception {
        String command = "DELETE FROM statementm WHERE CustomerNum = " + POut.long(customerNum);
            ;
        Db.nonQ(command);
    }

    /**
    * Delete all statements of a particular patient
    */
    public static void delete(long customerNum, long PatNum) throws Exception {
        String command = "DELETE FROM statementm WHERE CustomerNum = " + POut.long(customerNum) + " AND PatNum = " + POut.long(PatNum);
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<Statementm> Refresh(long patNum){
			string command="SELECT * FROM statementm WHERE PatNum = "+POut.Long(patNum);
			return Crud.StatementmCrud.SelectMany(command);
		}
		///<summary>Gets one Statementm from the db.</summary>
		public static Statementm GetOne(long customerNum,long statementNum){
			return Crud.StatementmCrud.SelectOne(customerNum,statementNum);
		}
		///<summary></summary>
		public static long Insert(Statementm statementm){
			return Crud.StatementmCrud.Insert(statementm,true);
		}
		///<summary></summary>
		public static void Update(Statementm statementm){
			Crud.StatementmCrud.Update(statementm);
		}
		///<summary></summary>
		public static void Delete(long customerNum,long statementNum) {
			string command= "DELETE FROM statementm WHERE CustomerNum = "+POut.Long(customerNum)+" AND StatementNum = "+POut.Long(statementNum);
			Db.NonQ(command);
		}
		///<summary>First use GetChangedSince.  Then, use this to convert the list a list of 'm' objects.</summary>
		public static List<Statementm> ConvertListToM(List<Statement> list) {
			List<Statementm> retVal=new List<Statementm>();
			for(int i=0;i<list.Count;i++){
				retVal.Add(Crud.StatementmCrud.ConvertToM(list[i]));
			}
			return retVal;
		}
		///<summary>Only run on server for mobile.  Takes the list of changes from the dental office and makes updates to those items in the mobile server db.  Also, make sure to run DeletedObjects.DeleteForMobile().</summary>
		public static void UpdateFromChangeList(List<Statementm> list,long customerNum) {
			for(int i=0;i<list.Count;i++){
				list[i].CustomerNum=customerNum;
				Statementm statementm=Crud.StatementmCrud.SelectOne(customerNum,list[i].StatementNum);
				if(statementm==null){//not in db
					Crud.StatementmCrud.Insert(list[i],true);
				}
				else{
					Crud.StatementmCrud.Update(list[i]);
				}
			}
		}
		*/