//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:45 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.IdentifierType;
import OpenDentBusiness.Meth;
import OpenDentBusiness.OIDInternal;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class OIDInternals   
{
    /**
    * Returns the currently defined OID for a given IndentifierType.  If not defined, IDroot will be empty string.
    */
    public static OIDInternal getForType(IdentifierType IDType) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<OIDInternal>GetObject(MethodBase.GetCurrentMethod(), IDType);
        }
         
        insertMissingValues();
        //
        String command = "SELECT * FROM oidinternal WHERE IDType='" + IDType.ToString() + "'";
        return Crud.OIDInternalCrud.SelectOne(command);
    }

    //should only return one row.
    /**
    * There should always be one entry in the DB per IdentifierType enumeration.
    */
    public static void insertMissingValues() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod());
            return ;
        }
         
        //string command= "SELECT COUNT(*) FROM oidinternal";
        //if(PIn.Long(Db.GetCount(command))==Enum.GetValues(typeof(IdentifierType)).Length) {
        //	return;//The DB table has the right count. Which means there is probably nothing wrong with the values in it. This may need to be enhanced if customers have any issues.
        //}
        String command = "SELECT * FROM oidinternal";
        List<OIDInternal> listOIDInternals = Crud.OIDInternalCrud.SelectMany(command);
        List<IdentifierType> listIDTypes = new List<IdentifierType>();
        for (int i = 0;i < listOIDInternals.Count;i++)
        {
            listIDTypes.Add(listOIDInternals[i].IDType);
        }
        for (int i = 0;i < Enum.GetValues(IdentifierType.class).Length;i++)
        {
            if (listIDTypes.Contains(IdentifierType.values()[i]))
            {
                continue;
            }
             
            //DB contains a row for this enum value.
            //Insert missing row with blank OID.
            if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
            {
                command = "INSERT INTO oidinternal (IDType,IDRoot) " + "VALUES('" + (IdentifierType.values()[i]).ToString() + "','')";
                Db.nonQ32(command);
            }
            else
            {
                //oracle
                command = "INSERT INTO oidinternal (OIDInternalNum,IDType,IDRoot) " + "VALUES((SELECT MAX(OIDInternalNum)+1 FROM oidinternal),'" + (IdentifierType.values()[i]).ToString() + "','')";
                Db.nonQ32(command);
            } 
        }
    }

    /**
    * 
    */
    public static List<OIDInternal> getAll() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<OIDInternal>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM oidinternal";
        return Crud.OIDInternalCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static void update(OIDInternal oIDInternal) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), oIDInternal);
            return ;
        }
         
        Crud.OIDInternalCrud.Update(oIDInternal);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<OIDInternal> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<OIDInternal>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM oidinternal WHERE PatNum = "+POut.Long(patNum);
			return Crud.OIDInternalCrud.SelectMany(command);
		}
		///<summary>Gets one OIDInternal from the db.</summary>
		public static OIDInternal GetOne(long ehrOIDNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<OIDInternal>(MethodBase.GetCurrentMethod(),ehrOIDNum);
			}
			return Crud.OIDInternalCrud.SelectOne(ehrOIDNum);
		}
		///<summary></summary>
		public static long Insert(OIDInternal oIDInternal){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				oIDInternal.EhrOIDNum=Meth.GetLong(MethodBase.GetCurrentMethod(),oIDInternal);
				return oIDInternal.EhrOIDNum;
			}
			return Crud.OIDInternalCrud.Insert(oIDInternal);
		}
		///<summary></summary>
		public static void Delete(long ehrOIDNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),ehrOIDNum);
				return;
			}
			string command= "DELETE FROM oidinternal WHERE EhrOIDNum = "+POut.Long(ehrOIDNum);
			Db.NonQ(command);
		}
		*/