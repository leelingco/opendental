//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:49 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.SigElement;
import OpenDentBusiness.Signalod;

/**
* 
*/
public class SigElements   
{
    /**
    * Gets all SigElements for a set of Signals, ordered by type: user,extras, message.
    */
    public static SigElement[] getElements(List<Signalod> signalList) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<SigElement[]>GetObject(MethodBase.GetCurrentMethod(), signalList);
        }
         
        if (signalList.Count == 0)
        {
            return new SigElement[0];
        }
         
        String command = "SELECT sigelement.* FROM sigelement,sigelementdef WHERE " + "sigelement.SigElementDefNum=sigelementdef.SigElementDefNum AND SignalNum IN (";
        for (int i = 0;i < signalList.Count;i++)
        {
            if (i > 0)
            {
                command += ",";
            }
             
            command += POut.Long(signalList[i].SignalNum);
        }
        command += ") ORDER BY sigelementdef.SigElementType";
        return Crud.SigElementCrud.SelectMany(command).ToArray();
    }

    /*
    		///<summary>This will never happen</summary>
    		public void Update(){
    			string command= "UPDATE sigelement SET " 
    				+"FromUser = '"    +POut.PString(FromUser)+"'"
    				+",ITypes = '"     +POut.PInt   ((int)ITypes)+"'"
    				+",DateViewing = '"+POut.PDate  (DateViewing)+"'"
    				+",SigType = '"    +POut.PInt   ((int)SigType)+"'"
    				+" WHERE SigElementNum = '"+POut.PInt(SigElementNum)+"'";
    			DataConnection dcon=new DataConnection();
     			Db.NonQ(command);
    		}*/
    /**
    * 
    */
    public static long insert(SigElement se) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            se.SigElementNum = Meth.GetLong(MethodBase.GetCurrentMethod(), se);
            return se.SigElementNum;
        }
         
        return Crud.SigElementCrud.Insert(se);
    }

    //<summary>There's no such thing as deleting a SigElement</summary>
    /*public void Delete(){
    			string command= "DELETE from SigElement WHERE SigElementNum = '"
    				+POut.PInt(SigElementNum)+"'";
    			DataConnection dcon=new DataConnection();
     			Db.NonQ(command);
    		}*/
    /**
    * Loops through the supplied sigElement list and pulls out all elements for one specific signal.
    */
    public static SigElement[] getForSig(SigElement[] elementList, long signalNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        ArrayList AL = new ArrayList();
        for (int i = 0;i < elementList.Length;i++)
        {
            if (elementList[i].SignalNum == signalNum)
            {
                AL.Add(elementList[i].Copy());
            }
             
        }
        SigElement[] retVal = new SigElement[AL.Count];
        AL.CopyTo(retVal);
        return retVal;
    }

}


