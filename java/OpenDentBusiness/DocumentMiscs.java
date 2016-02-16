//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:39 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.DocumentMisc;
import OpenDentBusiness.DocumentMiscType;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class DocumentMiscs   
{
    /**
    * Can return null
    */
    public static DocumentMisc getUpdateFilesZip() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<DocumentMisc>GetObject(MethodBase.GetCurrentMethod());
        }
         
        //There will be either one or zero
        String command = "SELECT * FROM documentmisc WHERE DocMiscType=" + POut.Long(((Enum)DocumentMiscType.UpdateFiles).ordinal());
        return Crud.DocumentMiscCrud.SelectOne(command);
    }

    /**
    * 
    */
    public static void setUpdateFilesZip(String rawBase64zipped) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), rawBase64zipped);
            return ;
        }
         
        String command = "DELETE FROM documentmisc WHERE DocMiscType=" + POut.Long(((Enum)DocumentMiscType.UpdateFiles).ordinal());
        Db.nonQ(command);
        DocumentMisc doc = new DocumentMisc();
        doc.DateCreated = DateTime.Today;
        doc.DocMiscType = DocumentMiscType.UpdateFiles;
        doc.FileName = "UpdateFiles.zip";
        doc.RawBase64 = rawBase64zipped;
        Crud.DocumentMiscCrud.Insert(doc);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<DocumentMisc> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<DocumentMisc>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM documentmisc WHERE PatNum = "+POut.Long(patNum);
			return Crud.DocumentMiscCrud.SelectMany(command);
		}
		///<summary></summary>
		public static long Insert(DocumentMisc documentMisc){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				documentMisc.DocMiscNum=Meth.GetLong(MethodBase.GetCurrentMethod(),documentMisc);
				return documentMisc.DocMiscNum;
			}
			return Crud.DocumentMiscCrud.Insert(documentMisc);
		}
		///<summary></summary>
		public static void Update(DocumentMisc documentMisc){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				Meth.GetVoid(MethodBase.GetCurrentMethod(),documentMisc);
				return;
			}
			Crud.DocumentMiscCrud.Update(documentMisc);
		}
		///<summary></summary>
		public static void Delete(long docMiscNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),docMiscNum);
				return;
			}
			string command= "DELETE FROM documentmisc WHERE DocMiscNum = "+POut.Long(docMiscNum);
			Db.NonQ(command);
		}
		*/