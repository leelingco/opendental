//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:44 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.Family;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class InsSubs   
{
    /**
    * It's fastest if you supply a sub list that contains the sub, but it also works just fine if it can't initally locate the sub in the list.  You can supply an empty list.  If still not found, returns a new InsSub. The reason for the new InsSub is because it is common to immediately get an insplan using inssub.InsSubNum.  And, of course, that would fail if inssub was null.
    */
    public static InsSub getSub(long insSubNum, List<InsSub> subList) throws Exception {
        //No need to check RemotingRole; no call to db.
        InsSub retVal = new InsSub();
        if (insSubNum == 0)
        {
            return new InsSub();
        }
         
        if (subList == null)
        {
            subList = new List<InsSub>();
        }
         
        boolean found = false;
        for (int i = 0;i < subList.Count;i++)
        {
            if (subList[i].InsSubNum == insSubNum)
            {
                found = true;
                retVal = subList[i];
            }
             
        }
        if (!found)
        {
            retVal = getOne(insSubNum);
        }
         
        //retVal will now be null if not found
        if (retVal == null)
        {
            return new InsSub();
        }
         
        return retVal;
    }

    //MessageBox.Show(Lans.g("InsPlans","Database is inconsistent.  Please run the database maintenance tool."));
    /**
    * Gets one InsSub from the db.
    */
    public static InsSub getOne(long insSubNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<InsSub>GetObject(MethodBase.GetCurrentMethod(), insSubNum);
        }
         
        return Crud.InsSubCrud.SelectOne(insSubNum);
    }

    /**
    * Gets new List for the specified family.  The only insSubs it misses are for claims with no current coverage.  These are handled as needed.
    */
    public static List<InsSub> refreshForFam(Family Fam) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<InsSub>>GetObject(MethodBase.GetCurrentMethod(), Fam);
        }
         
        //The command is written in a nested fashion in order to be compatible with both MySQL and Oracle.
        String command = "SELECT D.* FROM inssub D," + "((SELECT A.InsSubNum FROM inssub A WHERE";
        for (int i = 0;i < Fam.ListPats.Length;i++)
        {
            //subscribers in family
            if (i > 0)
            {
                command += " OR";
            }
             
            command += " A.Subscriber=" + POut.Long(Fam.ListPats[i].PatNum);
        }
        //in union, distinct is implied
        command += ") UNION (SELECT B.InsSubNum FROM inssub B,patplan P WHERE B.InsSubNum=P.InsSubNum AND (";
        for (int i = 0;i < Fam.ListPats.Length;i++)
        {
            if (i > 0)
            {
                command += " OR";
            }
             
            command += " P.PatNum=" + POut.Long(Fam.ListPats[i].PatNum);
        }
        command += "))) C " + "WHERE D.InsSubNum=C.InsSubNum " + "ORDER BY " + DbHelper.unionOrderBy("DateEffective",4);
        return Crud.InsSubCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(InsSub insSub) throws Exception {
        return insert(insSub,false);
    }

    /**
    * 
    */
    public static long insert(InsSub insSub, boolean useExistingPK) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            insSub.InsSubNum = Meth.GetLong(MethodBase.GetCurrentMethod(), insSub, useExistingPK);
            return insSub.InsSubNum;
        }
         
        return Crud.InsSubCrud.Insert(insSub, useExistingPK);
    }

    /**
    * 
    */
    public static void update(InsSub insSub) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), insSub);
            return ;
        }
         
        Crud.InsSubCrud.Update(insSub);
    }

    /**
    * Throws exception if dependencies.  Doesn't delete anything else.
    */
    public static void delete(long insSubNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), insSubNum);
            return ;
        }
         
        try
        {
            validateNoKeys(insSubNum,true);
        }
        catch (ApplicationException ex)
        {
            throw new ApplicationException(Lans.g("FormInsPlan","Not allowed to delete: ") + ex.Message);
        }

        String command = new String();
        DataTable table = new DataTable();
        //Remove from the patplan table just in case it is still there.
        command = "SELECT PatPlanNum FROM patplan WHERE InsSubNum = " + POut.long(insSubNum);
        table = Db.getTable(command);
        for (int i = 0;i < table.Rows.Count;i++)
        {
            //benefits with this PatPlanNum are also deleted here
            PatPlans.Delete(PIn.Long(table.Rows[i]["PatPlanNum"].ToString()));
        }
        command = "DELETE FROM claimproc WHERE InsSubNum = " + POut.long(insSubNum);
        Db.nonQ(command);
        Crud.InsSubCrud.Delete(insSubNum);
    }

    /**
    * Will throw an exception if this InsSub is being used anywhere. Set strict true to test against every check.
    */
    public static void validateNoKeys(long subNum, boolean strict) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), subNum, strict);
            return ;
        }
         
        String command = new String();
        String result = new String();
        //claim.InsSubNum/2
        command = "SELECT COUNT(*) FROM claim WHERE InsSubNum = " + POut.long(subNum);
        result = Db.getScalar(command);
        if (!StringSupport.equals(result, "0"))
        {
            throw new ApplicationException(Lans.g("FormInsPlan","Subscriber has existing claims and so the subscriber cannot be deleted."));
        }
         
        command = "SELECT COUNT(*) FROM claim WHERE InsSubNum2 = " + POut.long(subNum);
        result = Db.getScalar(command);
        if (!StringSupport.equals(result, "0"))
        {
            throw new ApplicationException(Lans.g("FormInsPlan","Subscriber has existing claims and so the subscriber cannot be deleted."));
        }
         
        //claimproc.InsSubNum
        if (strict)
        {
            command = "SELECT COUNT(*) FROM claimproc WHERE InsSubNum = " + POut.long(subNum) + " AND Status != " + POut.int(((Enum)OpenDentBusiness.ClaimProcStatus.Estimate).ordinal());
            //ignore estimates
            result = Db.getScalar(command);
            if (!StringSupport.equals(result, "0"))
            {
                throw new ApplicationException(Lans.g("FormInsPlan","Subscriber has existing claim procedures and so the subscriber cannot be deleted."));
            }
             
        }
         
        //etrans.InsSubNum
        command = "SELECT COUNT(*) FROM etrans WHERE InsSubNum = " + POut.long(subNum);
        result = Db.getScalar(command);
        if (!StringSupport.equals(result, "0"))
        {
            throw new ApplicationException(Lans.g("FormInsPlan","Subscriber has existing etrans entry and so the subscriber cannot be deleted."));
        }
         
        //payplan.InsSubNum
        command = "SELECT COUNT(*) FROM payplan WHERE InsSubNum = " + POut.long(subNum);
        result = Db.getScalar(command);
        if (!StringSupport.equals(result, "0"))
        {
            throw new ApplicationException(Lans.g("FormInsPlan","Subscriber has existing insurance linked payment plans and so the subscriber cannot be deleted."));
        }
         
    }

    /* jsalmon (11/15/2013) Depricated because inssubs should not be blindly deleted.
    		///<summary>A quick delete that is only used when cancelling out of a new edit window.</summary>
    		public static void Delete(long insSubNum) {
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    				Meth.GetVoid(MethodBase.GetCurrentMethod(),insSubNum);
    				return;
    			}
    			Crud.InsSubCrud.Delete(insSubNum);
    		}
    		 */
    /**
    * Used in FormInsSelectSubscr to get a list of insplans for one subscriber directly from the database.
    */
    public static List<InsSub> getListForSubscriber(long subscriber) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<InsSub>>GetObject(MethodBase.GetCurrentMethod(), subscriber);
        }
         
        String command = "SELECT * FROM inssub WHERE Subscriber=" + POut.long(subscriber);
        return Crud.InsSubCrud.SelectMany(command);
    }

    /**
    * Only used once.  Gets a list of subscriber names from the database that have the specified plan. Used to display in the insplan window.  The returned list never includes the inssub that we're viewing.
    */
    public static String[] getSubscribersForPlan(long planNum, long excludeSub) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<String[]>GetObject(MethodBase.GetCurrentMethod(), planNum, excludeSub);
        }
         
        String command = "SELECT CONCAT(CONCAT(LName,', '),FName) " + "FROM inssub LEFT JOIN patient ON patient.PatNum=inssub.Subscriber " + "WHERE inssub.PlanNum=" + POut.long(planNum) + " " + "AND inssub.InsSubNum !=" + POut.long(excludeSub) + " " + " ORDER BY LName,FName";
        DataTable table = Db.getTable(command);
        String[] retStr = new String[table.Rows.Count];
        for (int i = 0;i < table.Rows.Count;i++)
        {
            retStr[i] = PIn.String(table.Rows[i][0].ToString());
        }
        return retStr;
    }

    /**
    * Called from FormInsPlan when user wants to view a benefit note for other subscribers on a plan.  Should never include the current subscriber that the user is editing.  This function will get one note from the database, not including blank notes.  If no note can be found, then it returns empty string.
    */
    public static String getBenefitNotes(long planNum, long subNumExclude) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), planNum, subNumExclude);
        }
         
        String command = "SELECT BenefitNotes FROM inssub WHERE BenefitNotes != '' AND PlanNum=" + POut.long(planNum) + " AND InsSubNum !=" + POut.long(subNumExclude) + " " + DbHelper.limitAnd(1);
        DataTable table = Db.getTable(command);
        if (table.Rows.Count == 0)
        {
            return "";
        }
         
        return PIn.String(table.Rows[0][0].ToString());
    }

    /**
    * Returns the number of subs affected.
    */
    public static long setAllSubsAssignBen() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetLong(MethodBase.GetCurrentMethod());
        }
         
        String command = "UPDATE inssub SET AssignBen=0 WHERE AssignBen<>0";
        return Db.nonQ(command);
    }

    /**
    * This will assign all PlanNums to new value when Create New Plan If Needed is selected and there are multiple subscribers to a plan and an inssub object has been updated to point at a new PlanNum.  The PlanNum values need to be reflected in the claim, claimproc, payplan, and etrans tables, since those all both store inssub.InsSubNum and insplan.PlanNum.
    */
    public static void synchPlanNumsForNewPlan(InsSub SubCur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), SubCur);
            return ;
        }
         
        //claim.PlanNum
        String command = "UPDATE claim SET claim.PlanNum=" + POut.long(SubCur.PlanNum) + " " + "WHERE claim.InsSubNum=" + POut.long(SubCur.InsSubNum) + " AND claim.PlanNum!=" + POut.long(SubCur.PlanNum);
        Db.nonQ(command);
        //claim.PlanNum2
        command = "UPDATE claim SET claim.PlanNum2=" + POut.long(SubCur.PlanNum) + " " + "WHERE claim.InsSubNum2=" + POut.long(SubCur.InsSubNum) + " AND claim.PlanNum2!=" + POut.long(SubCur.PlanNum);
        Db.nonQ(command);
        //claimproc.PlanNum
        command = "UPDATE claimproc SET claimproc.PlanNum=" + POut.long(SubCur.PlanNum) + " " + "WHERE claimproc.InsSubNum=" + POut.long(SubCur.InsSubNum) + " AND claimproc.PlanNum!=" + POut.long(SubCur.PlanNum);
        Db.nonQ(command);
        //payplan.PlanNum
        command = "UPDATE payplan SET payplan.PlanNum=" + POut.long(SubCur.PlanNum) + " " + "WHERE payplan.InsSubNum=" + POut.long(SubCur.InsSubNum) + " AND payplan.PlanNum!=" + POut.long(SubCur.PlanNum);
        Db.nonQ(command);
        //etrans.PlanNum, only used if EtransType.BenefitInquiry270 and BenefitResponse271 and Eligibility_CA.
        command = "UPDATE etrans SET etrans.PlanNum=" + POut.long(SubCur.PlanNum) + " " + "WHERE etrans.InsSubNum!=0 AND etrans.InsSubNum=" + POut.long(SubCur.InsSubNum) + " AND etrans.PlanNum!=" + POut.long(SubCur.PlanNum);
        Db.nonQ(command);
    }

}


