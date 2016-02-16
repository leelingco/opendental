//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:50 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Vitalsign;

/**
* 
*/
public class Vitalsigns   
{
    /**
    * 
    */
    public static List<Vitalsign> refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Vitalsign>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM vitalsign WHERE PatNum = " + POut.long(patNum) + " ORDER BY DateTaken";
        return Crud.VitalsignCrud.SelectMany(command);
    }

    /**
    * Gets one Vitalsign from the db.
    */
    public static Vitalsign getOne(long vitalsignNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Vitalsign>GetObject(MethodBase.GetCurrentMethod(), vitalsignNum);
        }
         
        return Crud.VitalsignCrud.SelectOne(vitalsignNum);
    }

    /**
    * Get vitalsign that this EhrNotPerformed object is linked to. Returns null if not found.
    */
    public static Vitalsign getFromEhrNotPerformedNum(long ehrNotPerfNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Vitalsign>GetObject(MethodBase.GetCurrentMethod(), ehrNotPerfNum);
        }
         
        String command = "SELECT * FROM vitalsign WHERE EhrNotPerformedNum=" + POut.long(ehrNotPerfNum);
        return Crud.VitalsignCrud.SelectOne(command);
    }

    /**
    * Gets one Vitalsign with the given DiseaseNum as the PregDiseaseNum.
    */
    public static List<Vitalsign> getListFromPregDiseaseNum(long pregDiseaseNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Vitalsign>>GetObject(MethodBase.GetCurrentMethod(), pregDiseaseNum);
        }
         
        String command = "SELECT * FROM vitalsign WHERE vitalsign.PregDiseaseNum=" + POut.long(pregDiseaseNum);
        return Crud.VitalsignCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(Vitalsign vitalsign) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            vitalsign.VitalsignNum = Meth.GetLong(MethodBase.GetCurrentMethod(), vitalsign);
            return vitalsign.VitalsignNum;
        }
         
        return Crud.VitalsignCrud.Insert(vitalsign);
    }

    /**
    * 
    */
    public static void update(Vitalsign vitalsign) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), vitalsign);
            return ;
        }
         
        Crud.VitalsignCrud.Update(vitalsign);
    }

    /**
    * 
    */
    public static void delete(long vitalsignNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), vitalsignNum);
            return ;
        }
         
        String command = "DELETE FROM vitalsign WHERE VitalsignNum = " + POut.long(vitalsignNum);
        Db.nonQ(command);
    }

    public static float calcBMI(float weight, float height) throws Exception {
        //No need to check RemotingRole; no call to db.
        //BMI = (lbs*703)/(in^2)
        if (weight == 0 || height == 0)
        {
            return 0;
        }
         
        float bmi = (float)((weight * 703f) / (height * height));
        return bmi;
    }

}


