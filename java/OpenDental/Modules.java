//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:30 PM
//

package OpenDental;

import OpenDentBusiness.RemotingClient;

public class Modules   
{
    /**
    * This is just the first version of this function.  It only gets selected parts of the Account refresh.
    */
    public static DataSet getAccount(int patNum) throws Exception {
        try
        {
            if (RemotingClient.OpenDentBusinessIsLocal)
            {
                return ModulesB.GetAccount(patNum);
            }
            else
            {
                DtoModulesGetAccount dto = new DtoModulesGetAccount();
                dto.PatNum = patNum;
                return RemotingClient.ProcessQuery(dto);
            } 
        }
        catch (Exception e)
        {
            MessageBox.Show(e.Message);
            return new DataSet();
        }
    
    }

}


//It might be better to return null.