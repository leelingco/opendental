//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:37 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Carrier;
import OpenDentBusiness.Db;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class Carriers   
{
    private static Carrier[] listt = new Carrier[]();
    private static Hashtable hList = new Hashtable();
    //No need to check RemotingRole; no call to db.
    public static Carrier[] getListt() throws Exception {
        if (listt == null)
        {
            refreshCache();
        }
         
        return listt;
    }

    public static void setListt(Carrier[] value) throws Exception {
        listt = value;
    }

    /**
    * A hashtable of all carriers.
    */
    //No need to check RemotingRole; no call to db.
    public static Hashtable getHList() throws Exception {
        if (hList == null)
        {
            refreshCache();
        }
         
        return hList;
    }

    public static void setHList(Hashtable value) throws Exception {
        hList = value;
    }

    /**
    * Carriers are not refreshed as local data, but are refreshed as needed. A full refresh is frequently triggered if a carrierNum cannot be found in the HList.  Important retrieval is done directly from the db.
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM carrier ORDER BY CarrierName";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodInfo.GetCurrentMethod(), command);
        table.TableName = "Carrier";
        fillCache(table);
        return table;
    }

    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        setListt(Crud.CarrierCrud.TableToList(table).ToArray());
        setHList(new Hashtable());
        for (int i = 0;i < getListt().Length;i++)
        {
            getHList().Add(getListt()[i].CarrierNum, getListt()[i]);
        }
    }

    /**
    * Used to get a list of carriers to display in the FormCarriers window.
    */
    public static DataTable getBigList(boolean isCanadian, boolean showHidden, String carrierName, String carrierPhone) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), isCanadian, showHidden, carrierName, carrierPhone);
        }
         
        DataTable tableRaw = new DataTable();
        DataTable table = new DataTable();
        String command = new String();
        //if(isCanadian){
        //Strip out the digits from the phone number.
        String phonedigits = "";
        for (int i = 0;i < carrierPhone.Length;i++)
        {
            if (Regex.IsMatch(carrierPhone[i].ToString(), "[0-9]"))
            {
                phonedigits = phonedigits + carrierPhone[i];
            }
             
        }
        //Create a regular expression so that the phone search uses only numbers.
        String regexp = "";
        for (int i = 0;i < phonedigits.Length;i++)
        {
            if (i != 0)
            {
                regexp += "[^0-9]*";
            }
             
            //zero or more intervening digits that are not numbers
            regexp += phonedigits[i];
        }
        command = "SELECT Address,Address2,canadiannetwork.Abbrev,carrier.CarrierNum," + "CarrierName,CDAnetVersion,City,ElectID," + "COUNT(insplan.PlanNum) insPlanCount,IsCDA," + "carrier.IsHidden,Phone,State,Zip " + "FROM carrier " + "LEFT JOIN canadiannetwork ON canadiannetwork.CanadianNetworkNum=carrier.CanadianNetworkNum " + "LEFT JOIN insplan ON insplan.CarrierNum=carrier.CarrierNum " + "WHERE " + "CarrierName LIKE '%" + POut.string(carrierName) + "%' ";
        if (!StringSupport.equals(regexp, ""))
        {
            if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
            {
                command += "AND Phone REGEXP '" + POut.string(regexp) + "' ";
            }
            else
            {
                //oracle
                command += "AND (SELECT REGEXP_INSTR(Phone,'" + POut.string(regexp) + "') FROM dual)<>0";
            } 
        }
         
        if (isCanadian)
        {
            command += "AND IsCDA=1 ";
        }
         
        if (!showHidden)
        {
            command += "AND carrier.IsHidden=0 ";
        }
         
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            command += "GROUP BY carrier.CarrierNum ";
        }
        else
        {
            //Oracle
            command += "GROUP BY Address,Address2,canadiannetwork.Abbrev,carrier.CarrierNum," + "CarrierName,CDAnetVersion,City,ElectID,IsCDA," + "carrier.IsHidden,Phone,State,Zip ";
        } 
        command += "ORDER BY CarrierName";
        tableRaw = Db.getTable(command);
        table = new DataTable();
        table.Columns.Add("Address");
        table.Columns.Add("Address2");
        table.Columns.Add("CarrierNum");
        table.Columns.Add("CarrierName");
        table.Columns.Add("City");
        table.Columns.Add("ElectID");
        table.Columns.Add("insPlanCount");
        table.Columns.Add("isCDA");
        table.Columns.Add("isHidden");
        table.Columns.Add("Phone");
        //table.Columns.Add("pMP");
        //table.Columns.Add("network");
        table.Columns.Add("State");
        //table.Columns.Add("version");
        table.Columns.Add("Zip");
        DataRow row = new DataRow();
        for (int i = 0;i < tableRaw.Rows.Count;i++)
        {
            row = table.NewRow();
            row["Address"] = tableRaw.Rows[i]["Address"].ToString();
            row["Address2"] = tableRaw.Rows[i]["Address2"].ToString();
            row["CarrierNum"] = tableRaw.Rows[i]["CarrierNum"].ToString();
            row["CarrierName"] = tableRaw.Rows[i]["CarrierName"].ToString();
            row["City"] = tableRaw.Rows[i]["City"].ToString();
            row["ElectID"] = tableRaw.Rows[i]["ElectID"].ToString();
            if (PIn.Bool(tableRaw.Rows[i]["IsCDA"].ToString()))
            {
                row["isCDA"] = "X";
            }
            else
            {
                row["isCDA"] = "";
            } 
            if (PIn.Bool(tableRaw.Rows[i]["IsHidden"].ToString()))
            {
                row["isHidden"] = "X";
            }
            else
            {
                row["isHidden"] = "";
            } 
            row["insPlanCount"] = tableRaw.Rows[i]["insPlanCount"].ToString();
            row["Phone"] = tableRaw.Rows[i]["Phone"].ToString();
            //if(PIn.Bool(tableRaw.Rows[i]["IsPMP"].ToString())){
            //	row["pMP"]="X";
            //}
            //else{
            //	row["pMP"]="";
            //}
            //row["network"]=tableRaw.Rows[i]["Abbrev"].ToString();
            row["State"] = tableRaw.Rows[i]["State"].ToString();
            //row["version"]=tableRaw.Rows[i]["CDAnetVersion"].ToString();
            row["Zip"] = tableRaw.Rows[i]["Zip"].ToString();
            table.Rows.Add(row);
        }
        return table;
    }

    /**
    * Surround with try/catch.
    */
    public static void update(Carrier carrier) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), carrier);
            return ;
        }
         
        String command = new String();
        DataTable table = new DataTable();
        if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            //Canadian. en-CA or fr-CA
            if (carrier.IsCDA)
            {
                if (StringSupport.equals(carrier.ElectID, ""))
                {
                    throw new ApplicationException(Lans.g("Carriers","Carrier Identification Number required."));
                }
                 
                if (!Regex.IsMatch(carrier.ElectID, "^[0-9]{6}$"))
                {
                    throw new ApplicationException(Lans.g("Carriers","Carrier Identification Number must be exactly 6 numbers."));
                }
                 
            }
             
            /*Duplication is allowed
            					command="SELECT CarrierNum FROM carrier WHERE "
            						+"ElectID = '"+POut.String(Cur.ElectID)+"' "
            						+"AND IsCDA=1 "
            						+"AND CarrierNum != "+POut.Long(Cur.CarrierNum);
            					table=Db.GetTable(command);
            					if(table.Rows.Count>0) {//if there already exists a Canadian carrier with that ElectID
            						throw new ApplicationException(Lans.g("Carriers","EDI Code already in use."));
            					}
            					*/
            //so the edited carrier looks good, but now we need to make sure that the original was allowed to be changed.
            command = "SELECT ElectID,IsCDA FROM carrier WHERE CarrierNum = '" + POut.long(carrier.CarrierNum) + "'";
            table = Db.getTable(command);
            //if original carrier IsCDA
            //and the ElectID was already set
            if (PIn.Bool(table.Rows[0]["IsCDA"].ToString()) && !StringSupport.equals(PIn.String(table.Rows[0]["ElectID"].ToString()).Trim(), "") && !StringSupport.equals(PIn.String(table.Rows[0]["ElectID"].ToString()), carrier.ElectID))
            {
                //and the ElectID was changed
                command = "SELECT COUNT(*) FROM etrans WHERE CarrierNum= " + POut.long(carrier.CarrierNum) + " OR CarrierNum2=" + POut.long(carrier.CarrierNum);
                if (!StringSupport.equals(Db.getCount(command), "0"))
                {
                    throw new ApplicationException(Lans.g("Carriers","Not allowed to change Carrier Identification Number because it's in use in the claim history."));
                }
                 
            }
             
        }
         
        Crud.CarrierCrud.Update(carrier);
    }

    /**
    * Surround with try/catch if possibly adding a Canadian carrier.
    */
    public static long insert(Carrier carrier) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            carrier.CarrierNum = Meth.GetLong(MethodBase.GetCurrentMethod(), carrier);
            return carrier.CarrierNum;
        }
         
        //string command;
        if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            //Canadian. en-CA or fr-CA
            if (carrier.IsCDA)
            {
                if (StringSupport.equals(carrier.ElectID, ""))
                {
                    throw new ApplicationException(Lans.g("Carriers","Carrier Identification Number required."));
                }
                 
                if (!Regex.IsMatch(carrier.ElectID, "^[0-9]{6}$"))
                {
                    throw new ApplicationException(Lans.g("Carriers","Carrier Identification Number must be exactly 6 numbers."));
                }
                 
            }
             
        }
         
        return Crud.CarrierCrud.Insert(carrier);
    }

    /*Duplication actually seems to be allowed
    					command="SELECT CarrierNum FROM carrier WHERE "
    						+"ElectID = '"+POut.String(Cur.ElectID)+"' ";
    						//+"AND IsCDA=1";//no duplication allowed regardless.
    					DataTable table=Db.GetTable(command);
    					if(table.Rows.Count>0){//if there already exists a Canadian carrier with that ElectID
    						throw new ApplicationException(Lans.g("Carriers","EDI Code already in use."));
    					}
    					*/
    /**
    * Surround with try/catch.  If there are any dependencies, then this will throw an exception.  This is currently only called from FormCarrierEdit.
    */
    public static void delete(Carrier Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        //look for dependencies in insplan table.
        String command = "SELECT insplan.PlanNum,CONCAT(CONCAT(LName,', '),FName) FROM insplan " + "LEFT JOIN inssub ON insplan.PlanNum=inssub.PlanNum " + "LEFT JOIN patient ON inssub.Subscriber=patient.PatNum " + "WHERE insplan.CarrierNum = " + POut.long(Cur.CarrierNum) + " " + "ORDER BY LName,FName";
        DataTable table = Db.getTable(command);
        String strInUse = new String();
        if (table.Rows.Count > 0)
        {
            strInUse = "";
            for (int i = 0;i < table.Rows.Count;i++)
            {
                //new string[table.Rows.Count];
                if (i > 0)
                {
                    strInUse += "; ";
                }
                 
                strInUse += PIn.String(table.Rows[i][1].ToString());
            }
            throw new ApplicationException(Lans.g("Carriers","Not allowed to delete carrier because it is in use.  Subscribers using this carrier include ") + strInUse);
        }
         
        //look for dependencies in etrans table.
        command = "SELECT DateTimeTrans FROM etrans WHERE CarrierNum=" + POut.long(Cur.CarrierNum) + " OR CarrierNum2=" + POut.long(Cur.CarrierNum);
        table = Db.getTable(command);
        if (table.Rows.Count > 0)
        {
            strInUse = "";
            for (int i = 0;i < table.Rows.Count;i++)
            {
                if (i > 0)
                {
                    strInUse += ", ";
                }
                 
                strInUse += PIn.DateT(table.Rows[i][0].ToString()).ToShortDateString();
            }
            throw new ApplicationException(Lans.g("Carriers","Not allowed to delete carrier because it is in use in the etrans table.  Dates of claim sent history include ") + strInUse);
        }
         
        command = "DELETE from carrier WHERE CarrierNum = " + POut.long(Cur.CarrierNum);
        Db.nonQ(command);
    }

    /**
    * Returns a list of insplans that are dependent on the Cur carrier. Used to display in carrier edit.
    */
    public static String[] dependentPlans(Carrier Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<String[]>GetObject(MethodBase.GetCurrentMethod(), Cur);
        }
         
        String command = "SELECT CONCAT(CONCAT(LName,', '),FName) FROM patient,insplan,inssub" + " WHERE patient.PatNum=inssub.Subscriber" + " AND insplan.PlanNum=inssub.PlanNum" + " AND insplan.CarrierNum = '" + POut.long(Cur.CarrierNum) + "'" + " ORDER BY LName,FName";
        DataTable table = Db.getTable(command);
        String[] retStr = new String[table.Rows.Count];
        for (int i = 0;i < table.Rows.Count;i++)
        {
            retStr[i] = PIn.String(table.Rows[i][0].ToString());
        }
        return retStr;
    }

    /**
    * Gets the name of a carrier based on the carrierNum.  This also refreshes the list if necessary, so it will work even if the list has not been refreshed recently.
    */
    public static String getName(long carrierNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (getHList().ContainsKey(carrierNum))
        {
            return ((Carrier)getHList()[carrierNum]).CarrierName;
        }
         
        //if the carrierNum could not be found:
        refreshCache();
        if (getHList().ContainsKey(carrierNum))
        {
            return ((Carrier)getHList()[carrierNum]).CarrierName;
        }
         
        return "";
    }

    //this could only happen if corrupted:
    /**
    * Gets the specified carrier from Cache. This also refreshes the list if necessary, so it will work even if the list has not been refreshed recently.
    */
    public static Carrier getCarrier(long carrierNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (carrierNum == 0)
        {
            Carrier retVal = new Carrier();
            retVal.CarrierName = "";
            return retVal;
        }
         
        //instead of null. Helps prevent crash.
        if (getHList().ContainsKey(carrierNum))
        {
            return (Carrier)getHList()[carrierNum];
        }
         
        //if the carrierNum could not be found:
        refreshCache();
        if (getHList().ContainsKey(carrierNum))
        {
            return (Carrier)getHList()[carrierNum];
        }
         
        //this could only happen if corrupted:
        Carrier retVall = new Carrier();
        retVall.CarrierName = "";
        return retVall;
    }

    //instead of null. Helps prevent crash.
    /**
    * Primarily used when user clicks OK from the InsPlan window.  Gets a carrierNum from the database based on the other supplied carrier data.  Sets the CarrierNum accordingly. If there is no matching carrier, then a new carrier is created.  The end result is a valid carrierNum to use.
    */
    public static Carrier getIndentical(Carrier carrier) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Carrier>GetObject(MethodBase.GetCurrentMethod(), carrier);
        }
         
        if (StringSupport.equals(carrier.CarrierName, ""))
        {
            return new Carrier();
        }
         
        //should probably be null instead
        Carrier retVal = carrier.copy();
        //This allows user to remove trailing spaces from the FormInsPlan interface.
        String command = "SELECT CarrierNum FROM carrier WHERE " + "CarrierName = '" + POut.string(carrier.CarrierName) + "' " + "AND Address = '" + POut.string(carrier.Address) + "' " + "AND Address2 = '" + POut.string(carrier.Address2) + "' " + "AND City = '" + POut.string(carrier.City) + "' " + "AND State LIKE '" + POut.string(carrier.State) + "' " + "AND Zip = '" + POut.string(carrier.Zip) + "' " + "AND Phone = '" + POut.string(carrier.Phone) + "' " + "AND ElectID = '" + POut.string(carrier.ElectID) + "' " + "AND NoSendElect = '" + POut.bool(carrier.NoSendElect) + "'";
        DataTable table = Db.getTable(command);
        if (table.Rows.Count > 0)
        {
            //A matching carrier was found in the database, so we will use it.
            retVal.CarrierNum = PIn.Long(table.Rows[0][0].ToString());
            return retVal;
        }
         
        //No match found.  Decide what to do.  Usually add carrier.--------------------------------------------------------------
        //Canada:
        if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            throw new ApplicationException(Lans.g("Carriers","Carrier not found."));
        }
         
        //Canadian. en-CA or fr-CA
        //gives user a chance to add manually.
        /*if(carrier.ElectID!=""){
        					command="SELECT CarrierNum FROM carrier WHERE "
        						+"ElectID = '"+POut.String(carrier.ElectID)+"' "
        						+"AND IsCDA=1";
        					table=Db.GetTable(command);
        					if(table.Rows.Count>0){//if there already exists a Canadian carrier with that ElectID
        						retVal.CarrierNum=PIn.Long(table.Rows[0][0].ToString());
        						//set carrier.CarrierNum to the carrier found (all other carrier fields will still be wrong)
        						//throw new ApplicationException(Lans.g("Carriers","The carrier information was changed based on the EDI Code provided."));
        						return retVal;
        					}
        				}*/
        //Notice that if inserting a carrier, it's never possible to create a canadian carrier.
        insert(carrier);
        retVal.CarrierNum = carrier.CarrierNum;
        return retVal;
    }

    /**
    * Returns an arraylist of Carriers with names similar to the supplied string.  Used in dropdown list from carrier field for faster entry.  There is a small chance that the list will not be completely refreshed when this is run, but it won't really matter if one carrier doesn't show in dropdown.
    */
    public static List<Carrier> getSimilarNames(String carrierName) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<Carrier> retVal = new List<Carrier>();
        for (int i = 0;i < getListt().Length;i++)
        {
            //if(i>0 && List[i].CarrierName==List[i-1].CarrierName){
            //	continue;//ignore all duplicate names
            //}
            //if(Regex.IsMatch(List[i].CarrierName,"^"+carrierName,RegexOptions.IgnoreCase))
            if (getListt()[i].IsHidden)
            {
                continue;
            }
             
            if (getListt()[i].CarrierName.ToUpper().IndexOf(carrierName.ToUpper()) == 0)
            {
                retVal.Add(getListt()[i]);
            }
             
        }
        return retVal;
    }

    /**
    * Surround with try/catch Combines all the given carriers into one. The carrier that will be used as the basis of the combination is specified in the pickedCarrier argument. Updates insplan, then deletes all the other carriers.
    */
    public static void combine(List<long> carrierNums, long pickedCarrierNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetBool(MethodBase.GetCurrentMethod(), carrierNums, pickedCarrierNum);
            return ;
        }
         
        if (carrierNums.Count == 1)
        {
            return ;
        }
         
        //nothing to do
        //remove pickedCarrierNum from the carrierNums list to make the queries easier to construct.
        List<long> carrierNumList = new List<long>();
        for (int i = 0;i < carrierNums.Count;i++)
        {
            if (carrierNums[i] == pickedCarrierNum)
                continue;
             
            carrierNumList.Add(carrierNums[i]);
        }
        //Make sure that none of the carrierNums are in use in the etrans table
        String command = "SELECT COUNT(*) FROM etrans WHERE";
        for (int i = 0;i < carrierNumList.Count;i++)
        {
            if (i > 0)
            {
                command += " OR";
            }
             
            command += " (CarrierNum=" + carrierNumList[i].ToString() + " AND CarrierTransCounter>0)";
        }
        for (int i = 0;i < carrierNumList.Count;i++)
        {
            command += " OR (CarrierNum2=" + carrierNumList[i].ToString() + " AND CarrierTransCounter2>0)";
        }
        DataTable table = Db.getTable(command);
        String ecount = table.Rows[0][0].ToString();
        if (!StringSupport.equals(ecount, "0"))
        {
            throw new ApplicationException(Lans.g("Carriers","Not allowed to combine carriers because some are in use in the etrans table.  Number of entries involved: ") + ecount);
        }
         
        for (int i = 0;i < carrierNums.Count;i++)
        {
            //Now, do the actual combining----------------------------------------------------------------------------------
            if (carrierNums[i] == pickedCarrierNum)
                continue;
             
            command = "UPDATE insplan SET CarrierNum = '" + POut.long(pickedCarrierNum) + "' WHERE CarrierNum = " + POut.Long(carrierNums[i]);
            Db.nonQ(command);
            command = "DELETE FROM carrier" + " WHERE CarrierNum = '" + carrierNums[i].ToString() + "'";
            Db.nonQ(command);
        }
    }

    /**
    * Used in the FormCarrierCombine window.
    */
    public static List<Carrier> getCarriers(List<long> carrierNums) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<Carrier> retVal = new List<Carrier>();
        for (int i = 0;i < getListt().Length;i++)
        {
            for (int j = 0;j < carrierNums.Count;j++)
            {
                if (getListt()[i].CarrierNum == carrierNums[j])
                {
                    retVal.Add(getListt()[i]);
                    break;
                }
                 
            }
        }
        return retVal;
    }

    /**
    * Used in FormInsPlan to check whether another carrier is already using this id.  That way, it won't tell the user that this might be an invalid id.
    */
    public static boolean electIdInUse(String electID) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (StringSupport.equals(electID, ""))
        {
            return true;
        }
         
        for (int i = 0;i < getListt().Length;i++)
        {
            if (StringSupport.equals(getListt()[i].ElectID, electID))
            {
                return true;
            }
             
        }
        return false;
    }

    /**
    * Used from insplan window when requesting benefits.  Gets carrier based on electID.
    */
    public static Carrier getCanadian(String electID) throws Exception {
        for (int i = 0;i < getListt().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (StringSupport.equals(getListt()[i].ElectID, electID))
            {
                return getListt()[i];
            }
             
        }
        return null;
    }

    public static Carrier getByNameAndPhone(String carrierName, String phone) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (StringSupport.equals(carrierName, ""))
        {
            throw new ApplicationException("Carrier cannot be blank");
        }
         
        for (int i = 0;i < getListt().Length;i++)
        {
            if (StringSupport.equals(carrierName, getListt()[i].CarrierName) && StringSupport.equals(phone, getListt()[i].Phone))
            {
                return getListt()[i].Copy();
            }
             
        }
        Carrier carrier = new Carrier();
        carrier.CarrierName = carrierName;
        carrier.Phone = phone;
        insert(carrier);
        return carrier;
    }

    /**
    * Returns null if no match is found. You are allowed to pass in nulls.
    */
    public static Carrier getByNameAndPhoneNoInsert(String carrierName, String phone) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (carrierName == null || phone == null)
        {
            return null;
        }
         
        if (StringSupport.equals(carrierName, "") || StringSupport.equals(phone, ""))
        {
            return null;
        }
         
        for (int i = 0;i < getListt().Length;i++)
        {
            if (StringSupport.equals(carrierName, getListt()[i].CarrierName) && StringSupport.equals(phone, getListt()[i].Phone))
            {
                return getListt()[i].Copy();
            }
             
        }
        return null;
    }

}


/*
		///<summary>Gets a dictionary of carrier names for the supplied patient list.</summary>
		public static Dictionary<long,string> GetCarrierNames(List<Patient> patients){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<Dictionary<long,string>>(MethodBase.GetCurrentMethod(),patients);
			}
			if(patients.Count==0){
				return new Dictionary<long,string>();
			}
			string command="SELECT patient.PatNum,carrier.CarrierName "
				+"FROM patient "
				+"LEFT JOIN patplan ON patient.PatNum=patplan.PatNum "
				+"LEFT JOIN insplan ON patplan.PlanNum=insplan.PlanNum "
				+"LEFT JOIN carrier ON carrier.CarrierNum=insplan.CarrierNum "
				+"WHERE";
			for(int i=0;i<patients.Count;i++){
				if(i>0){
					command+=" OR";
				}
				command+=" patient.PatNum="+POut.Long(patients[i].PatNum);
			}
			command+=" GROUP BY patient.PatNum,carrier.CarrierName";
			DataTable table=Db.GetTable(command);
			Dictionary<long,string> retVal=new Dictionary<long,string>();
			for(int i=0;i<table.Rows.Count;i++){
				retVal.Add(PIn.Long(table.Rows[i]["PatNum"].ToString()),table.Rows[i]["CarrierName"].ToString());
			}
			return retVal;
		}*/