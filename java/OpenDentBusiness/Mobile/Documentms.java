//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Db;
import OpenDentBusiness.Documents;
import OpenDentBusiness.Mobile.Crud.DocumentmCrud;
import OpenDentBusiness.Mobile.Documentm;
import OpenDentBusiness.POut;

/**
* 
*/
public class Documentms   
{
    /**
    * Gets one Documentm from the db.
    */
    public static Documentm getOne(long customerNum, long docNum) throws Exception {
        return DocumentmCrud.selectOne(customerNum,docNum);
    }

    /**
    * Gets all Documentm for a single patient
    */
    public static List<Documentm> getDocumentms(long customerNum, long patNum) throws Exception {
        String command = "SELECT * from documentm " + "WHERE CustomerNum = " + POut.long(customerNum) + " AND PatNum = " + POut.long(patNum);
        return DocumentmCrud.selectMany(command);
    }

    /**
    * Limits the number of documents in the database for a single patient
    */
    public static void limitDocumentmsPerPatient(long customerNum) throws Exception {
        String command = "DELETE FROM documentm WHERE CustomerNum = " + POut.long(customerNum) + " AND DocNum NOT IN (SELECT DocNum from statementm  WHERE CustomerNum = " + POut.long(customerNum) + " )";
            ;
        Db.nonQ(command);
    }

    /**
    * Only documents listed in the corresponding rows of the statement table are uploaded
    */
    public static List<long> getChangedSinceDocumentNums(DateTime changedSince, List<long> statementNumList) throws Exception {
        return Documents.GetChangedSinceDocumentNums(changedSince, statementNumList);
    }

    /**
    * The values returned are sent to the webserver.
    */
    public static List<Documentm> getMultDocumentms(List<long> documentNums, String AtoZpath) throws Exception {
        List<OpenDentBusiness.Document> documentList = Documents.GetMultDocuments(documentNums, AtoZpath);
        List<Documentm> documentmList = ConvertListToM(documentList);
        return documentmList;
    }

    /**
    * First use GetChangedSince.  Then, use this to convert the list a list of 'm' objects.
    */
    public static List<Documentm> convertListToM(List<OpenDentBusiness.Document> list) throws Exception {
        List<Documentm> retVal = new List<Documentm>();
        for (int i = 0;i < list.Count;i++)
        {
            retVal.Add(DocumentmCrud.ConvertToM(list[i]));
        }
        return retVal;
    }

    /**
    * Only run on server for mobile.  Takes the list of changes from the dental office and makes updates to those items in the mobile server db.  Also, make sure to run DeletedObjects.DeleteForMobile().
    */
    public static void updateFromChangeList(List<Documentm> list, long customerNum) throws Exception {
        for (int i = 0;i < list.Count;i++)
        {
            list[i].CustomerNum = customerNum;
            Documentm documentm = DocumentmCrud.SelectOne(customerNum, list[i].DocNum);
            if (documentm == null)
            {
                //not in db
                DocumentmCrud.Insert(list[i], true);
            }
            else
            {
                DocumentmCrud.Update(list[i]);
            } 
        }
    }

    /**
    * used in tandem with Full synch
    */
    public static void deleteAll(long customerNum) throws Exception {
        String command = "DELETE FROM documentm WHERE CustomerNum = " + POut.long(customerNum);
            ;
        Db.nonQ(command);
    }

    /**
    * Delete all documents of a particular patient
    */
    public static void delete(long customerNum, long PatNum) throws Exception {
        String command = "DELETE FROM documentm WHERE CustomerNum = " + POut.long(customerNum) + " AND PatNum = " + POut.long(PatNum);
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<Documentm> Refresh(long patNum){
			string command="SELECT * FROM documentm WHERE PatNum = "+POut.Long(patNum);
			return Crud.DocumentmCrud.SelectMany(command);
		}
		///<summary></summary>
		public static long Insert(Documentm documentm){
			return Crud.DocumentmCrud.Insert(documentm,true);
		}
		///<summary></summary>
		public static void Update(Documentm documentm){
			Crud.DocumentmCrud.Update(documentm);
		}
		///<summary></summary>
		public static void Delete(long customerNum,long docNum) {
			string command= "DELETE FROM documentm WHERE CustomerNum = "+POut.Long(customerNum)+" AND DocNum = "+POut.Long(docNum);
			Db.NonQ(command);
		}
		///<summary>First use GetChangedSince.  Then, use this to convert the list a list of 'm' objects.</summary>
		public static List<Documentm> ConvertListToM(List<Document> list) {
			List<Documentm> retVal=new List<Documentm>();
			for(int i=0;i<list.Count;i++){
				retVal.Add(Crud.DocumentmCrud.ConvertToM(list[i]));
			}
			return retVal;
		}
		///<summary>Only run on server for mobile.  Takes the list of changes from the dental office and makes updates to those items in the mobile server db.  Also, make sure to run DeletedObjects.DeleteForMobile().</summary>
		public static void UpdateFromChangeList(List<Documentm> list,long customerNum) {
			for(int i=0;i<list.Count;i++){
				list[i].CustomerNum=customerNum;
				Documentm documentm=Crud.DocumentmCrud.SelectOne(customerNum,list[i].DocNum);
				if(documentm==null){//not in db
					Crud.DocumentmCrud.Insert(list[i],true);
				}
				else{
					Crud.DocumentmCrud.Update(list[i]);
				}
			}
		}
		*/