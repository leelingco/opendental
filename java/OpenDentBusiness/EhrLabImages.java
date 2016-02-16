//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:41 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.EhrLab;
import OpenDentBusiness.EhrLabImage;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class EhrLabImages   
{
    /**
    * 
    */
    public static List<EhrLabImage> refresh(long ehrLabNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EhrLabImage>>GetObject(MethodBase.GetCurrentMethod(), ehrLabNum);
        }
         
        String command = "SELECT * FROM ehrlabimage WHERE EhrLabNum = " + POut.long(ehrLabNum) + " AND DocNum > 0";
        return Crud.EhrLabImageCrud.SelectMany(command);
    }

    /**
    * Returns true if a row containing the given EhrLabNum and DocNum==-1 is found.  Otherwise returns false.
    */
    public static boolean isWaitingForImages(long ehrLabNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), ehrLabNum);
        }
         
        String command = "SELECT * FROM ehrlabimage WHERE EhrLabNum = " + POut.long(ehrLabNum) + " AND DocNum = -1";
        return Crud.EhrLabImageCrud.SelectOne(command) != null;
    }

    /**
    * EhrLab first EhrLab which this docNum is attached to. Or returns null if docNum is not attached to any EhrLabs.
    */
    public static EhrLab getFirstLabForDocNum(long docNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<EhrLab>GetObject(MethodBase.GetCurrentMethod(), docNum);
        }
         
        //Get first EhrLabImage that has this docNum attached.
        String command = "SELECT * FROM ehrlabimage WHERE DocNum = " + POut.long(docNum) + " LIMIT 1";
        EhrLabImage labImage = Crud.EhrLabImageCrud.SelectOne(command);
        if (labImage == null)
        {
            return null;
        }
         
        //Not found so return
        //Get the EhrLab which this labImage is attached to
        command = "SELECT * FROM ehrlab WHERE EhrLabNum = " + POut.long(labImage.EhrLabNum);
        return Crud.EhrLabCrud.SelectOne(labImage.EhrLabNum);
    }

    /**
    * Create an entry per each docNum in the list. If setting isWaiting flag to true then create a row containing the given EhrLabNum and DocNum==-1.  Otherwise omit such a row.
    */
    public static void insertAllForLabNum(long ehrLabNum, boolean isWaiting, List<long> docNums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ehrLabNum, isWaiting, docNums);
            return ;
        }
         
        //Delete existing rows for this EhrLabNum.
        deleteForLab(ehrLabNum);
        //Create the waiting flag if necessary
        if (isWaiting)
        {
            EhrLabImage labImage = new EhrLabImage();
            labImage.EhrLabNum = ehrLabNum;
            labImage.DocNum = -1;
            insert(labImage);
        }
         
        for (int i = 0;i < docNums.Count;i++)
        {
            //Create the rest of the links
            EhrLabImage labImage = new EhrLabImage();
            labImage.EhrLabNum = ehrLabNum;
            labImage.DocNum = docNums[i];
            insert(labImage);
        }
    }

    /**
    * Delete all rows for a given EhrLabNum.
    */
    public static void deleteForLab(long ehrLabNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ehrLabNum);
            return ;
        }
         
        //Delete existing rows for this EhrLabNum.
        String cmd = "DELETE FROM ehrlabimage WHERE EhrLabNum = " + POut.long(ehrLabNum);
        Db.nonQ(cmd);
    }

    /**
    * From local list. Returns true if EhrLabImage is found. Otherwise returns false.
    */
    public static boolean getDocNumExistsInList(long ehrLabNum, long docNum, List<EhrLabImage> listImages) throws Exception {
        return GetFromList(ehrLabNum, docNum, listImages) != null;
    }

    //No need to check RemotingRole; no call to db.
    /**
    * From local list. Returns EhrLabImage if found. Returns null if not found.
    */
    public static EhrLabImage getFromList(long ehrLabNum, long docNum, List<EhrLabImage> listImages) throws Exception {
        for (int i = 0;i < listImages.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (listImages[i].DocNum == docNum && listImages[i].EhrLabNum == ehrLabNum)
            {
                return listImages[i];
            }
             
        }
        return null;
    }

    /**
    * 
    */
    public static long insert(EhrLabImage ehrLabImage) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            ehrLabImage.EhrLabImageNum = Meth.GetLong(MethodBase.GetCurrentMethod(), ehrLabImage);
            return ehrLabImage.EhrLabImageNum;
        }
         
        return Crud.EhrLabImageCrud.Insert(ehrLabImage);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		 * ///<summary>Gets one EhrLabImage from the db.</summary>
		public static EhrLabImage GetOne(long ehrLabImageNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<EhrLabImage>(MethodBase.GetCurrentMethod(),ehrLabImageNum);
			}
			return Crud.EhrLabImageCrud.SelectOne(ehrLabImageNum);
		}
		///<summary></summary>
		public static void Update(EhrLabImage ehrLabImage){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				Meth.GetVoid(MethodBase.GetCurrentMethod(),ehrLabImage);
				return;
			}
			Crud.EhrLabImageCrud.Update(ehrLabImage);
		}
		*/