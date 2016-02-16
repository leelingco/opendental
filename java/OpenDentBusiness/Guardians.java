//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:44 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.Guardian;
import OpenDentBusiness.GuardianRelationship;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class Guardians   
{
    /**
    * Get all guardians for a one dependant/child.
    */
    public static List<Guardian> refresh(long patNumChild) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Guardian>>GetObject(MethodBase.GetCurrentMethod(), patNumChild);
        }
         
        String command = "SELECT * FROM guardian WHERE PatNumChild = " + POut.long(patNumChild) + " ORDER BY Relationship";
        return Crud.GuardianCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(Guardian guardian) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            guardian.GuardianNum = Meth.GetLong(MethodBase.GetCurrentMethod(), guardian);
            return guardian.GuardianNum;
        }
         
        return Crud.GuardianCrud.Insert(guardian);
    }

    /**
    * 
    */
    public static void update(Guardian guardian) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), guardian);
            return ;
        }
         
        Crud.GuardianCrud.Update(guardian);
    }

    /**
    * 
    */
    public static void delete(long guardianNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), guardianNum);
            return ;
        }
         
        Crud.GuardianCrud.Delete(guardianNum);
    }

    /**
    * 
    */
    public static void deleteForFamily(long patNumGuar) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patNumGuar);
            return ;
        }
         
        String command = "DELETE FROM guardian " + "WHERE PatNumChild IN (SELECT p.PatNum FROM patient p WHERE p.Guarantor=" + POut.long(patNumGuar) + ")";
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static boolean existForFamily(long patNumGuar) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), patNumGuar);
        }
         
        String command = "SELECT COUNT(*) FROM guardian " + "WHERE PatNumChild IN (SELECT p.PatNum FROM patient p WHERE p.Guarantor=" + POut.long(patNumGuar) + ")";
        if (StringSupport.equals(Db.getCount(command), "0"))
        {
            return false;
        }
         
        return true;
    }

    /**
    * Short abbreviation of relationship within parentheses.
    */
    public static String getGuardianRelationshipStr(GuardianRelationship relat) throws Exception {
        //No need to check RemotingRole; no call to db.
        switch(relat)
        {
            case Brother: 
                return "(br)";
            case CareGiver: 
                return "(cg)";
            case Child: 
                return "(c)";
            case Father: 
                return "(d)";
            case FosterChild: 
                return "(fc)";
            case Friend: 
                return "(f)";
            case Grandchild: 
                return "(gc)";
            case Grandfather: 
                return "(gf)";
            case Grandmother: 
                return "(gm)";
            case Grandparent: 
                return "(gp)";
            case Guardian: 
                return "(g)";
            case LifePartner: 
                return "(lp)";
            case Mother: 
                return "(m)";
            case Other: 
                return "(o)";
            case Parent: 
                return "(p)";
            case Self: 
                return "(se)";
            case Sibling: 
                return "(sb)";
            case Sister: 
                return "(ss)";
            case Sitter: 
                return "(s)";
            case Spouse: 
                return "(sp)";
            case Stepchild: 
                return "(sc)";
            case Stepfather: 
                return "(sf)";
            case Stepmother: 
                return "(sm)";
        
        }
        return "";
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary>Gets one Guardian from the db.</summary>
		public static Guardian GetOne(long guardianNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<Guardian>(MethodBase.GetCurrentMethod(),guardianNum);
			}
			return Crud.GuardianCrud.SelectOne(guardianNum);
		}
		
		
		*/