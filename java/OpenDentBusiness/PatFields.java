//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:45 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PatField;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class PatFields   
{
    /**
    * Gets a list of all PatFields for a given patient.
    */
    public static PatField[] refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<PatField[]>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM patfield WHERE PatNum=" + POut.long(patNum);
        return Crud.PatFieldCrud.SelectMany(command).ToArray();
    }

    /**
    * 
    */
    public static void update(PatField patField) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patField);
            return ;
        }
         
        Crud.PatFieldCrud.Update(patField);
    }

    /**
    * 
    */
    public static long insert(PatField patField) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            patField.PatFieldNum = Meth.GetLong(MethodBase.GetCurrentMethod(), patField);
            return patField.PatFieldNum;
        }
         
        return Crud.PatFieldCrud.Insert(patField);
    }

    /**
    * 
    */
    public static void delete(PatField pf) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), pf);
            return ;
        }
         
        String command = "DELETE FROM patfield WHERE PatFieldNum =" + POut.long(pf.PatFieldNum);
        Db.nonQ(command);
    }

    /**
    * Frequently returns null.
    */
    public static PatField getByName(String name, PatField[] fieldList) throws Exception {
        for (int i = 0;i < fieldList.Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (StringSupport.equals(fieldList[i].FieldName, name))
            {
                return fieldList[i];
            }
             
        }
        return null;
    }

}


