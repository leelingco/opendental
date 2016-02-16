//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:46 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.BenefitLogic;
import OpenDentBusiness.ContactMethod;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Family;
import OpenDentBusiness.IdentifierType;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.OIDExternal;
import OpenDentBusiness.OIDExternals;
import OpenDentBusiness.OIDInternals;
import OpenDentBusiness.PatAging;
import OpenDentBusiness.PatField;
import OpenDentBusiness.PatFields;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.PatientNote;
import OpenDentBusiness.PatientNotes;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatientStatus;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Referrals;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Sites;
import OpenDentBusiness.TaskObjectType;
import OpenDentBusiness.TelephoneNumbers;

/**
* 
*/
public class Patients   
{
    /**
    * Returns a Family object for the supplied patNum.  Use Family.GetPatient to extract the desired patient from the family.
    */
    public static Family getFamily(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Family>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        //GetFamilySelectCommand(patNum);
        String command = "SELECT patient.*,CASE WHEN Guarantor!=PatNum THEN 1 ELSE 0 END AS IsNotGuar FROM patient WHERE Guarantor = (" + "SELECT Guarantor FROM patient WHERE PatNum=" + POut.long(patNum) + ") " + "ORDER BY IsNotGuar,Birthdate";
        Family fam = new Family();
        List<Patient> patients = Crud.PatientCrud.SelectMany(command);
        for (Object __dummyForeachVar0 : patients)
        {
            Patient patient = (Patient)__dummyForeachVar0;
            patient.setAge(dateToAge(patient.Birthdate));
        }
        fam.ListPats = new Patient[patients.Count];
        patients.CopyTo(fam.ListPats, 0);
        return fam;
    }

    /**
    * Returns a patient, or null, based on an internally defined or externally defined globaly unique identifier.  This can be an OID, GUID, IID, UUID, etc.
    *  @param IDNumber The extension portion of the GUID/OID.  Example: 333224444 if using SSN as a the unique identifier
    *  @param OID root OID that the IDNumber extends.  Example: 2.16.840.1.113883.4.1 is the OID for the Social Security Numbers.
    */
    public static Patient getByGUID(String IDNumber, String OID) throws Exception {
        if (StringSupport.equals(OID, OIDInternals.getForType(IdentifierType.Patient).IDRoot))
        {
            return Patients.getPat(PIn.long(IDNumber));
        }
        else
        {
            //OID matches the localy defined patnum OID.
            OIDExternal oidExt = OIDExternals.getByRootAndExtention(OID,IDNumber);
            if (oidExt == null || oidExt.IDType != IdentifierType.Patient)
            {
                return null;
            }
             
            return Patients.getPat(oidExt.IDInternal);
        } 
    }

    //OID either not found, or does not represent a patient.
    //patcur=Patients.GetByGUID(fields[3].Split('~')[i].Split('^')[1],								//ID Number
    //													fields[3].Split('~')[i].Split('^')[4].Split('&')[2],	//Assigning Authority ID
    //													fields[3].Split('~')[i].Split('^')[5]);								//Identifier Type Code
    /*
    		public static string GetFamilySelectCommand(long patNum) {
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    				return Meth.GetString(MethodBase.GetCurrentMethod(),patNum);
    			}
    			string command= 
    				"SELECT guarantor FROM patient "
    				+"WHERE patnum = '"+POut.Long(patNum)+"'";
     			DataTable table=Db.GetTable(command);
    			if(table.Rows.Count==0){
    				return null;
    			}
    			command= 
    				"SELECT patient.* "
    				+"FROM patient "
    				+"WHERE Guarantor = '"+table.Rows[0][0].ToString()+"'"
    				+" ORDER BY Guarantor!=PatNum,Birthdate";
    			return command;
    		}*/
    /**
    * This is a way to get a single patient from the database if you don't already have a family object to use.  Will return null if not found.
    */
    public static Patient getPat(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Patient>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        if (patNum == 0)
        {
            return null;
        }
         
        String command = "SELECT * FROM patient WHERE PatNum=" + POut.long(patNum);
        Patient pat = null;
        try
        {
            pat = Crud.PatientCrud.SelectOne(patNum);
        }
        catch (Exception __dummyCatchVar0)
        {
        }

        if (pat == null)
        {
            return null;
        }
         
        //used in eCW bridge
        pat.setAge(dateToAge(pat.Birthdate));
        return pat;
    }

    /**
    * Will return null if not found.
    */
    public static Patient getPatByChartNumber(String chartNumber) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Patient>GetObject(MethodBase.GetCurrentMethod(), chartNumber);
        }
         
        if (StringSupport.equals(chartNumber, ""))
        {
            return null;
        }
         
        String command = "SELECT * FROM patient WHERE ChartNumber='" + POut.string(chartNumber) + "'";
        Patient pat = null;
        try
        {
            pat = Crud.PatientCrud.SelectOne(command);
        }
        catch (Exception __dummyCatchVar1)
        {
        }

        if (pat == null)
        {
            return null;
        }
         
        pat.setAge(dateToAge(pat.Birthdate));
        return pat;
    }

    /**
    * Will return null if not found.
    */
    public static Patient getPatBySSN(String ssn) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Patient>GetObject(MethodBase.GetCurrentMethod(), ssn);
        }
         
        if (StringSupport.equals(ssn, ""))
        {
            return null;
        }
         
        String command = "SELECT * FROM patient WHERE SSN='" + POut.string(ssn) + "'";
        Patient pat = null;
        try
        {
            pat = Crud.PatientCrud.SelectOne(command);
        }
        catch (Exception __dummyCatchVar2)
        {
        }

        if (pat == null)
        {
            return null;
        }
         
        pat.setAge(dateToAge(pat.Birthdate));
        return pat;
    }

    public static List<Patient> getChangedSince(DateTime changedSince) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Patient>>GetObject(MethodBase.GetCurrentMethod(), changedSince);
        }
         
        String command = "SELECT * FROM patient WHERE DateTStamp > " + POut.dateT(changedSince);
        return Crud.PatientCrud.SelectMany(command);
    }

    //command+=" "+DbHelper.LimitAnd(1000);
    /**
    * Used if the number of records are very large, in which case using GetChangedSince(DateTime changedSince) is not the preffered route due to memory problems caused by large recordsets.
    */
    public static List<long> getChangedSincePatNums(DateTime changedSince) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<long>>GetObject(MethodBase.GetCurrentMethod(), changedSince);
        }
         
        String command = "SELECT PatNum From patient WHERE DateTStamp > " + POut.dateT(changedSince);
        DataTable dt = Db.getTable(command);
        List<long> patnums = new List<long>(dt.Rows.Count);
        for (int i = 0;i < dt.Rows.Count;i++)
        {
            patnums.Add(PIn.Long(dt.Rows[i]["PatNum"].ToString()));
        }
        return patnums;
    }

    /**
    * Gets PatNums of patients whose online password is not blank
    */
    public static List<long> getPatNumsEligibleForSynch() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<long>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT PatNum From patient where OnlinePassword!=''";
        DataTable dt = Db.getTable(command);
        List<long> patnums = new List<long>(dt.Rows.Count);
        for (int i = 0;i < dt.Rows.Count;i++)
        {
            patnums.Add(PIn.Long(dt.Rows[i]["PatNum"].ToString()));
        }
        return patnums;
    }

    /**
    * Gets PatNums of patients whose online password is  blank
    */
    public static List<long> getPatNumsForDeletion() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<long>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT PatNum From patient where OnlinePassword=''";
        DataTable dt = Db.getTable(command);
        List<long> patnums = new List<long>(dt.Rows.Count);
        for (int i = 0;i < dt.Rows.Count;i++)
        {
            patnums.Add(PIn.Long(dt.Rows[i]["PatNum"].ToString()));
        }
        return patnums;
    }

    /**
    * ONLY for new patients. Set includePatNum to true for use the patnum from the import function.  Used in HL7.  Otherwise, uses InsertID to fill PatNum.
    */
    public static long insert(Patient pat, boolean useExistingPK) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            pat.PatNum = Meth.GetLong(MethodBase.GetCurrentMethod(), pat, useExistingPK);
            return pat.PatNum;
        }
         
        if (!useExistingPK)
        {
            return Crud.PatientCrud.Insert(pat);
        }
         
        return Crud.PatientCrud.Insert(pat, useExistingPK);
    }

    /**
    * Updates only the changed columns and returns the number of rows affected.  Supply the old Patient object to compare for changes.
    */
    public static void update(Patient patient, Patient oldPatient) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patient, oldPatient);
            return ;
        }
         
        Crud.PatientCrud.Update(patient, oldPatient);
    }

    //This can never be used anymore, or it will mess up
    /**
    * This is only used when entering a new patient and user clicks cancel.  It used to actually delete the patient, but that will mess up UAppoint synch function.  DateTStamp needs to track deleted patients. So now, the PatStatus is simply changed to 4.
    */
    public static void delete(Patient pat) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), pat);
            return ;
        }
         
        String command = "UPDATE patient SET PatStatus=" + POut.Long(((Enum)PatientStatus.Deleted).ordinal()) + ", " + "Guarantor=PatNum " + "WHERE PatNum =" + pat.PatNum.ToString();
        Db.nonQ(command);
    }

    /**
    * Only used for the Select Patient dialog.  Pass in a billing type of 0 for all billing types.
    */
    public static DataTable getPtDataTable(boolean limit, String lname, String fname, String phone, String address, boolean hideInactive, String city, String state, String ssn, String patnum, String chartnumber, long billingtype, boolean guarOnly, boolean showArchived, long clinicNum, DateTime birthdate, long siteNum, String subscriberId, String email) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), limit, lname, fname, phone, address, hideInactive, city, state, ssn, patnum, chartnumber, billingtype, guarOnly, showArchived, clinicNum, birthdate, siteNum, subscriberId, email);
        }
         
        String billingsnippet = " ";
        if (billingtype != 0)
        {
            billingsnippet += "AND BillingType=" + POut.long(billingtype) + " ";
        }
         
        /*for(int i=0;i<billingtypes.Length;i++){//if length==0, it will get all billing types
        				if(i==0){
        					billingsnippet+="AND (";
        				}
        				else{
        					billingsnippet+="OR ";
        				}
        				billingsnippet+="BillingType ='"+billingtypes[i].ToString()+"' ";
        				if(i==billingtypes.Length-1){//if there is only one row, this will also be triggered.
        					billingsnippet+=") ";
        				}
        			}*/
        String phonedigits = "";
        for (int i = 0;i < phone.Length;i++)
        {
            if (Regex.IsMatch(phone[i].ToString(), "[0-9]"))
            {
                phonedigits = phonedigits + phone[i];
            }
             
        }
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
        String command = "SELECT patient.PatNum,LName,FName,MiddleI,Preferred,Birthdate,SSN,HmPhone,WkPhone,Address,PatStatus" + ",BillingType,ChartNumber,City,State,PriProv,SiteNum,Email ";
        if (PrefC.getBool(PrefName.DistributorKey))
        {
            //if for OD HQ, so never going to be Oracle
            command += ",GROUP_CONCAT(DISTINCT phonenumber.PhoneNumberVal) AS OtherPhone ";
        }
         
        //this customer might have multiple extra phone numbers that match the param.
        if (!StringSupport.equals(subscriberId, ""))
        {
            command += ",inssub.SubscriberId ";
        }
         
        command += "FROM patient ";
        if (PrefC.getBool(PrefName.DistributorKey))
        {
            //if for OD HQ, so never going to be Oracle
            command += "LEFT JOIN phonenumber ON phonenumber.PatNum=patient.PatNum ";
            if (!StringSupport.equals(regexp, ""))
            {
                command += "AND phonenumber.PhoneNumberVal REGEXP '" + POut.string(regexp) + "' ";
            }
             
        }
         
        if (!StringSupport.equals(subscriberId, ""))
        {
            command += "LEFT JOIN patplan ON patplan.PatNum=patient.PatNum " + "LEFT JOIN inssub ON patplan.InsSubNum=inssub.InsSubNum " + "LEFT JOIN insplan ON inssub.PlanNum=insplan.PlanNum ";
        }
         
        command += "WHERE PatStatus != '4' ";
        //not status 'deleted'
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            //LIKE is case insensitive in mysql.
            if (lname.Length > 0)
            {
                if (limit)
                {
                    //normal behavior is fast
                    if (PrefC.getBool(PrefName.DistributorKey))
                    {
                        command += "AND (LName LIKE '" + POut.string(lname) + "%' OR Preferred LIKE '" + POut.string(lname) + "%') ";
                    }
                    else
                    {
                        command += "AND LName LIKE '" + POut.string(lname) + "%' ";
                    } 
                }
                else
                {
                    //slower, but more inclusive.  User explicitly looking for all matches.
                    if (PrefC.getBool(PrefName.DistributorKey))
                    {
                        command += "AND (LName LIKE '" + POut.string(lname) + "%' OR Preferred LIKE '" + POut.string(lname) + "%') ";
                    }
                    else
                    {
                        command += "AND LName LIKE '" + POut.string(lname) + "%' ";
                    } 
                } 
            }
             
            if (fname.Length > 0)
            {
                if (PrefC.getBool(PrefName.DistributorKey))
                {
                    command += "AND (FName LIKE '" + POut.string(fname) + "%' OR Preferred LIKE '" + POut.string(fname) + "%') ";
                }
                else
                {
                    command += "AND FName LIKE '" + POut.string(fname) + "%' ";
                } 
            }
             
        }
        else
        {
            //oracle, case matters in a like statement
            if (lname.Length > 0)
            {
                if (limit)
                {
                    command += "AND LOWER(LName) LIKE '" + POut.string(lname) + "%' ";
                }
                else
                {
                    command += "AND LOWER(LName) LIKE '%" + POut.string(lname) + "%' ";
                } 
            }
             
            if (fname.Length > 0)
            {
                command += "AND LOWER(FName) LIKE '" + POut.string(fname) + "%' ";
            }
             
        } 
        if (!StringSupport.equals(regexp, ""))
        {
            if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
            {
                command += "AND (HmPhone REGEXP '" + POut.string(regexp) + "' " + "OR WkPhone REGEXP '" + POut.string(regexp) + "' " + "OR WirelessPhone REGEXP '" + POut.string(regexp) + "' ";
                if (PrefC.getBool(PrefName.DistributorKey))
                {
                    //if for OD HQ, so never going to be Oracle
                    command += "OR phonenumber.PhoneNumberVal REGEXP '" + POut.string(regexp) + "' ";
                }
                 
                command += ") ";
            }
            else
            {
                //oracle
                command += "AND ((SELECT REGEXP_INSTR(p.HmPhone,'" + POut.string(regexp) + "') FROM dual)<>0" + "OR (SELECT REGEXP_INSTR(p.WkPhone,'" + POut.string(regexp) + "') FROM dual)<>0 " + "OR (SELECT REGEXP_INSTR(p.WirelessPhone,'" + POut.string(regexp) + "') FROM dual)<>0) ";
            } 
        }
         
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            //LIKE is case insensitive in mysql.
            //LIKE is case insensitive in mysql.
            //LIKE is case insensitive in mysql.
            //LIKE is case insensitive in mysql.
            //LIKE is case insensitive in mysql.
            //LIKE is case insensitive in mysql.
            command += (address.Length > 0 ? "AND Address LIKE '%" + POut.string(address) + "%' " : "") + (city.Length > 0 ? "AND City LIKE '" + POut.string(city) + "%' " : "") + (state.Length > 0 ? "AND State LIKE '" + POut.string(state) + "%' " : "") + (ssn.Length > 0 ? "AND SSN LIKE '" + POut.string(ssn) + "%' " : "") + (patnum.Length > 0 ? "AND patient.PatNum LIKE '" + POut.string(patnum) + "%' " : "") + (chartnumber.Length > 0 ? "AND ChartNumber LIKE '" + POut.string(chartnumber) + "%' " : "") + (email.Length > 0 ? "AND Email LIKE '%" + POut.string(email) + "%' " : "");
        }
        else
        {
            //LIKE is case insensitive in mysql.
            //oracle
            //case matters in a like statement in oracle.
            //case matters in a like statement in oracle.
            //case matters in a like statement in oracle.
            //In case an office uses this field for something else.
            //case matters in a like statement in oracle.
            //case matters in a like statement in oracle.
            command += (address.Length > 0 ? "AND LOWER(Address) LIKE '%" + POut.string(address).ToLower() + "%' " : "") + (city.Length > 0 ? "AND LOWER(City) LIKE '" + POut.string(city).ToLower() + "%' " : "") + (state.Length > 0 ? "AND LOWER(State) LIKE '" + POut.string(state).ToLower() + "%' " : "") + (ssn.Length > 0 ? "AND LOWER(SSN) LIKE '" + POut.string(ssn).ToLower() + "%' " : "") + (patnum.Length > 0 ? "AND patient.PatNum LIKE '" + POut.string(patnum) + "%' " : "") + (chartnumber.Length > 0 ? "AND LOWER(ChartNumber) LIKE '" + POut.string(chartnumber).ToLower() + "%' " : "") + (email.Length > 0 ? "AND LOWER(Email) LIKE '%" + POut.string(email).ToLower() + "%' " : "");
        } 
        //LIKE is case insensitive in mysql.
        if (birthdate.Year > 1880 && birthdate.Year < 2100)
        {
            command += "AND Birthdate =" + POut.date(birthdate) + " ";
        }
         
        command += billingsnippet;
        if (hideInactive)
        {
            command += "AND PatStatus != '" + POut.int(((Enum)PatientStatus.Inactive).ordinal()) + "' ";
        }
         
        if (!showArchived)
        {
            command += "AND PatStatus != '" + POut.int(((Enum)PatientStatus.Archived).ordinal()) + "' AND PatStatus != '" + POut.int(((Enum)PatientStatus.Deceased).ordinal()) + "' ";
        }
         
        //if(showProspectiveOnly) {
        //	command+="AND PatStatus = "+POut.Int((int)PatientStatus.Prospective)+" ";
        //}
        //if(!showProspectiveOnly) {
        //	command+="AND PatStatus != "+POut.Int((int)PatientStatus.Prospective)+" ";
        //}
        if (guarOnly)
        {
            command += "AND patient.PatNum = Guarantor ";
        }
         
        if (clinicNum != 0)
        {
            command += "AND ClinicNum=" + POut.long(clinicNum) + " ";
        }
         
        if (siteNum > 0)
        {
            command += "AND SiteNum=" + POut.long(siteNum) + " ";
        }
         
        if (!StringSupport.equals(subscriberId, ""))
        {
            command += "AND inssub.SubscriberId LIKE '%" + POut.string(subscriberId) + "%' ";
        }
         
        if (PrefC.getBool(PrefName.DistributorKey))
        {
            //if for OD HQ
            command += "GROUP BY patient.PatNum ";
        }
         
        command += "ORDER BY LName,FName ";
        if (limit)
        {
            command = DbHelper.limitOrderBy(command,40);
        }
         
        //MessageBox.Show(command);
        DataTable table = Db.getTable(command);
        DataTable PtDataTable = table.Clone();
        //does not copy any data
        PtDataTable.TableName = "table";
        PtDataTable.Columns.Add("age");
        PtDataTable.Columns.Add("site");
        for (int i = 0;i < PtDataTable.Columns.Count;i++)
        {
            PtDataTable.Columns[i].DataType = String.class;
        }
        //if(limit && table.Rows.Count==36){
        //	retval=true;
        //}
        DataRow r = new DataRow();
        DateTime date = new DateTime();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            //table.Rows.Count && i<44;i++){
            r = PtDataTable.NewRow();
            //PatNum,LName,FName,MiddleI,Preferred,Birthdate,SSN,HmPhone,WkPhone,Address,PatStatus"
            //+",BillingType,ChartNumber,City,State
            r["PatNum"] = table.Rows[i]["PatNum"].ToString();
            r["LName"] = table.Rows[i]["LName"].ToString();
            r["FName"] = table.Rows[i]["FName"].ToString();
            r["MiddleI"] = table.Rows[i]["MiddleI"].ToString();
            r["Preferred"] = table.Rows[i]["Preferred"].ToString();
            date = PIn.Date(table.Rows[i]["Birthdate"].ToString());
            if (date.Year > 1880)
            {
                r["age"] = dateToAge(date);
                r["Birthdate"] = date.ToShortDateString();
            }
            else
            {
                r["age"] = "";
                r["Birthdate"] = "";
            } 
            r["SSN"] = table.Rows[i]["SSN"].ToString();
            r["HmPhone"] = table.Rows[i]["HmPhone"].ToString();
            r["WkPhone"] = table.Rows[i]["WkPhone"].ToString();
            r["Address"] = table.Rows[i]["Address"].ToString();
            r["PatStatus"] = ((PatientStatus)PIn.Long(table.Rows[i]["PatStatus"].ToString())).ToString();
            r["BillingType"] = DefC.GetName(DefCat.BillingTypes, PIn.Long(table.Rows[i]["BillingType"].ToString()));
            r["ChartNumber"] = table.Rows[i]["ChartNumber"].ToString();
            r["City"] = table.Rows[i]["City"].ToString();
            r["State"] = table.Rows[i]["State"].ToString();
            r["PriProv"] = Providers.GetAbbr(PIn.Long(table.Rows[i]["PriProv"].ToString()));
            r["site"] = Sites.GetDescription(PIn.Long(table.Rows[i]["SiteNum"].ToString()));
            r["Email"] = table.Rows[i]["Email"].ToString();
            if (PrefC.getBool(PrefName.DistributorKey))
            {
                //if for OD HQ
                r["OtherPhone"] = table.Rows[i]["OtherPhone"].ToString();
            }
             
            PtDataTable.Rows.Add(r);
        }
        return PtDataTable;
    }

    /**
    * Used when filling appointments for an entire day. Gets a list of Pats, multPats, of all the specified patients.  Then, use GetOnePat to pull one patient from this list.  This process requires only one call to the database.
    */
    public static Patient[] getMultPats(List<long> patNums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Patient[]>GetObject(MethodBase.GetCurrentMethod(), patNums);
        }
         
        //MessageBox.Show(patNums.Length.ToString());
        String strPatNums = "";
        DataTable table = new DataTable();
        if (patNums.Count > 0)
        {
            for (int i = 0;i < patNums.Count;i++)
            {
                if (i > 0)
                {
                    strPatNums += "OR ";
                }
                 
                strPatNums += "PatNum='" + patNums[i].ToString() + "' ";
            }
            String command = "SELECT * FROM patient WHERE " + strPatNums;
            //MessageBox.Show(string command);
            table = Db.getTable(command);
        }
        else
        {
            table = new DataTable();
        } 
        Patient[] multPats = Crud.PatientCrud.TableToList(table).ToArray();
        return multPats;
    }

    /**
    * First call GetMultPats to fill the list of multPats. Then, use this to return one patient from that list.
    */
    public static Patient getOnePat(Patient[] multPats, long patNum) throws Exception {
        for (int i = 0;i < multPats.Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (multPats[i].PatNum == patNum)
            {
                return multPats[i];
            }
             
        }
        return new Patient();
    }

    /**
    * Gets nine of the most useful fields from the db for the given patnum.  If invalid PatNum, returns new Patient rather than null.
    */
    public static Patient getLim(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Patient>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        if (patNum == 0)
        {
            return new Patient();
        }
         
        String command = "SELECT PatNum,LName,FName,MiddleI,Preferred,CreditType,Guarantor,HasIns,SSN " + "FROM patient " + "WHERE PatNum = '" + patNum.ToString() + "'";
        DataTable table = Db.getTable(command);
        if (table.Rows.Count == 0)
        {
            return new Patient();
        }
         
        Patient Lim = new Patient();
        Lim.PatNum = PIn.Long(table.Rows[0][0].ToString());
        Lim.LName = PIn.String(table.Rows[0][1].ToString());
        Lim.FName = PIn.String(table.Rows[0][2].ToString());
        Lim.MiddleI = PIn.String(table.Rows[0][3].ToString());
        Lim.Preferred = PIn.String(table.Rows[0][4].ToString());
        Lim.CreditType = PIn.String(table.Rows[0][5].ToString());
        Lim.Guarantor = PIn.Long(table.Rows[0][6].ToString());
        Lim.HasIns = PIn.String(table.Rows[0][7].ToString());
        Lim.SSN = PIn.String(table.Rows[0][8].ToString());
        return Lim;
    }

    /**
    * Gets the patient and provider balances for all patients in the family.  Used from the payment window to help visualize and automate the family splits.
    */
    public static DataTable getPaymentStartingBalances(long guarNum, long excludePayNum) throws Exception {
        return getPaymentStartingBalances(guarNum,excludePayNum,false);
    }

    /**
    * Gets the patient and provider balances for all patients in the family.  Used from the payment window to help visualize and automate the family splits. groupByProv means group by provider only not provider/clinic.
    */
    public static DataTable getPaymentStartingBalances(long guarNum, long excludePayNum, boolean groupByProv) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), guarNum, excludePayNum);
        }
         
        /*command=@"SELECT (SELECT EstBalance FROM patient WHERE PatNum="+POut.PInt(patNum)+" GROUP BY PatNum) EstBalance, "
        				+"IFNULL((SELECT SUM(ProcFee) FROM procedurelog WHERE PatNum="+POut.PInt(patNum)+" AND ProcStatus=2 GROUP BY PatNum),0)"//complete
        				+"+IFNULL((SELECT SUM(InsPayAmt) FROM claimproc WHERE PatNum="+POut.PInt(patNum)
        				+" AND (Status=1 OR Status=4 OR Status=5) GROUP BY PatNum),0) "//received,supplemental,capclaim"
        				+"+IFNULL((SELECT SUM(AdjAmt) FROM adjustment WHERE PatNum="+POut.PInt(patNum)+" GROUP BY PatNum),0) "
        				+"-IFNULL((SELECT SUM(SplitAmt) FROM paysplit WHERE PatNum="+POut.PInt(patNum)+" GROUP BY PatNum),0) CalcBalance, "
        				+"IFNULL((SELECT SUM(InsPayEst) FROM claimproc WHERE PatNum="+POut.PInt(patNum)+" GROUP BY PatNum),0) Estimate ";
        			DataTable table=Db.GetTable(command);
        				+" GROUP BY PatNum,"
        				+" ORDER BY Guarantor!=PatNum,Birthdate,";
        			*/
        Random rnd = new Random();
        String rndStr = rnd.Next(1000000).ToString();
        String command = "SET @GuarNum=" + POut.long(guarNum) + ";" + "SET @ExcludePayNum=" + POut.long(excludePayNum) + ";";
        command += "\r\n" + 
        "\t\t\t\tDROP TABLE IF EXISTS tempfambal" + rndStr + ";\r\n" + 
        "\t\t\t\tCREATE TABLE tempfambal" + rndStr + "( \r\n" + 
        "\t\t\t\t\tFamBalNum int NOT NULL auto_increment,\r\n" + 
        "\t\t\t\t\tPatNum bigint NOT NULL DEFAULT 0,\r\n" + 
        "\t\t\t\t\tProvNum bigint NOT NULL DEFAULT 0,\r\n" + 
        "\t\t\t\t\tClinicNum bigint NOT NULL DEFAULT 0,\r\n" + 
        "\t\t\t\t\tAmtBal double NOT NULL DEFAULT 0,\r\n" + 
        "\t\t\t\t\tInsEst double NOT NULL DEFAULT 0,\r\n" + 
        "\t\t\t\t\tPRIMARY KEY (FamBalNum));\r\n" + 
        "\t\t\t\t\r\n" + 
        "\t\t\t\t/*Completed procedures*/\r\n" + 
        "\t\t\t\tINSERT INTO tempfambal" + rndStr + " (PatNum,ProvNum,ClinicNum,AmtBal)\r\n" + 
        "\t\t\t\tSELECT patient.PatNum,procedurelog.ProvNum,procedurelog.ClinicNum,SUM(ProcFee)\r\n" + 
        "\t\t\t\tFROM procedurelog,patient\r\n" + 
        "\t\t\t\tWHERE patient.PatNum=procedurelog.PatNum\r\n" + 
        "\t\t\t\tAND ProcStatus=2\r\n" + 
        "\t\t\t\tAND patient.Guarantor=@GuarNum\r\n" + 
        "\t\t\t\tGROUP BY patient.PatNum,ProvNum,ClinicNum;\r\n" + 
        "\t\t\t\r\n" + 
        "\t\t\t\t/*Received insurance payments*/\r\n" + 
        "\t\t\t\tINSERT INTO tempfambal" + rndStr + " (PatNum,ProvNum,ClinicNum,AmtBal)\r\n" + 
        "\t\t\t\tSELECT patient.PatNum,claimproc.ProvNum,claimproc.ClinicNum,-SUM(InsPayAmt)-SUM(Writeoff)\r\n" + 
        "\t\t\t\tFROM claimproc,patient\r\n" + 
        "\t\t\t\tWHERE patient.PatNum=claimproc.PatNum\r\n" + 
        "\t\t\t\tAND (Status=1 OR Status=4 OR Status=5 OR Status=7)/*received,supplemental,capclaim. (7-capcomplete writeoff)*/\r\n" + 
        "\t\t\t\tAND patient.Guarantor=@GuarNum\r\n" + 
        "\t\t\t\tGROUP BY patient.PatNum,ProvNum,ClinicNum;\r\n" + 
        "\r\n" + 
        "\t\t\t\t/*Insurance estimates*/\r\n" + 
        "\t\t\t\tINSERT INTO tempfambal" + rndStr + " (PatNum,ProvNum,ClinicNum,InsEst)\r\n" + 
        "\t\t\t\tSELECT patient.PatNum,claimproc.ProvNum,claimproc.ClinicNum,SUM(InsPayEst)+SUM(Writeoff)\r\n" + 
        "\t\t\t\tFROM claimproc,patient\r\n" + 
        "\t\t\t\tWHERE patient.PatNum=claimproc.PatNum\r\n" + 
        "\t\t\t\tAND Status=0 /*NotReceived*/\r\n" + 
        "\t\t\t\tAND patient.Guarantor=@GuarNum\r\n" + 
        "\t\t\t\tGROUP BY patient.PatNum,ProvNum,ClinicNum;\r\n" + 
        "\r\n" + 
        "\t\t\t\t/*Adjustments*/\r\n" + 
        "\t\t\t\tINSERT INTO tempfambal" + rndStr + " (PatNum,ProvNum,ClinicNum,AmtBal)\r\n" + 
        "\t\t\t\tSELECT patient.PatNum,adjustment.ProvNum,adjustment.ClinicNum,SUM(AdjAmt)\r\n" + 
        "\t\t\t\tFROM adjustment,patient\r\n" + 
        "\t\t\t\tWHERE patient.PatNum=adjustment.PatNum\r\n" + 
        "\t\t\t\tAND patient.Guarantor=@GuarNum\r\n" + 
        "\t\t\t\tGROUP BY patient.PatNum,ProvNum,ClinicNum;\r\n" + 
        "\r\n" + 
        "\t\t\t\t/*Patient payments*/\r\n" + 
        "\t\t\t\tINSERT INTO tempfambal" + rndStr + " (PatNum,ProvNum,ClinicNum,AmtBal)\r\n" + 
        "\t\t\t\tSELECT patient.PatNum,paysplit.ProvNum,paysplit.ClinicNum,-SUM(SplitAmt)\r\n" + 
        "\t\t\t\tFROM paysplit,patient\r\n" + 
        "\t\t\t\tWHERE patient.PatNum=paysplit.PatNum\r\n" + 
        "\t\t\t\tAND paysplit.PayNum!=@ExcludePayNum\r\n" + 
        "\t\t\t\tAND patient.Guarantor=@GuarNum\r\n" + 
        "\t\t\t\tAND paysplit.PayPlanNum=0\r\n" + 
        "\t\t\t\tGROUP BY patient.PatNum,ProvNum,ClinicNum;\r\n" + 
        "\r\n" + 
        "\t\t\t\t/*payplan princ reduction*/\r\n" + 
        "\t\t\t\tINSERT INTO tempfambal" + rndStr + " (PatNum,ProvNum,ClinicNum,AmtBal)\r\n" + 
        "\t\t\t\tSELECT patient.PatNum,payplancharge.ProvNum,payplancharge.ClinicNum,-SUM(Principal)\r\n" + 
        "\t\t\t\tFROM payplancharge,patient\r\n" + 
        "\t\t\t\tWHERE patient.PatNum=payplancharge.PatNum\r\n" + 
        "\t\t\t\tAND patient.Guarantor=@GuarNum\r\n" + 
        "\t\t\t\tGROUP BY patient.PatNum,ProvNum,ClinicNum;\r\n" + 
        "\r\n" + 
        "\t\t\t\tSELECT tempfambal" + rndStr + ".PatNum,tempfambal" + rndStr + ".ProvNum,\r\n" + 
        "\t\t\t\t\ttempfambal" + rndStr + ".ClinicNum,tempfambal" + rndStr + ".ClinicNum,SUM(AmtBal) StartBal,\r\n" + 
        "\t\t\t\t\tSUM(AmtBal-tempfambal" + rndStr + ".InsEst) AfterIns,FName,Preferred,\'0\' EndBal,\r\n" + 
        "\t\t\t\t\tCASE WHEN Guarantor!=patient.PatNum THEN 1 ELSE 0 END AS IsNotGuar\r\n" + 
        "\t\t\t\tFROM tempfambal" + rndStr + ",patient\r\n" + 
        "\t\t\t\tWHERE tempfambal" + rndStr + ".PatNum=patient.PatNum\r\n" + 
        "\t\t\t\tGROUP BY PatNum,ProvNum,";
        if (!groupByProv)
        {
            command += "ClinicNum,";
        }
         
        command += "FName,Preferred\r\n" + 
        "\t\t\t\tHAVING ((StartBal>0.005 OR StartBal<-0.005) OR (AfterIns>0.005 OR AfterIns<-0.005))\r\n" + 
        "\t\t\t\tORDER BY IsNotGuar,Birthdate,ProvNum,FName,Preferred;\r\n" + 
        "\r\n" + 
        "\t\t\t\tDROP TABLE IF EXISTS tempfambal" + rndStr + "\"";
        return Db.getTable(command);
    }

    /**
    * 
    */
    public static void changeGuarantorToCur(Family Fam, Patient Pat) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Fam, Pat);
            return ;
        }
         
        //Move famfinurgnote to current patient:
        String command = "UPDATE patient SET " + "FamFinUrgNote = '" + POut.String(Fam.ListPats[0].FamFinUrgNote) + "' " + "WHERE PatNum = " + POut.long(Pat.PatNum);
        Db.nonQ(command);
        command = "UPDATE patient SET FamFinUrgNote = '' " + "WHERE PatNum = '" + Pat.Guarantor.ToString() + "'";
        Db.nonQ(command);
        //Move family financial note to current patient:
        command = "SELECT FamFinancial FROM patientnote " + "WHERE PatNum = " + POut.long(Pat.Guarantor);
        DataTable table = Db.getTable(command);
        if (table.Rows.Count == 1)
        {
            command = "UPDATE patientnote SET " + "FamFinancial = '" + POut.String(table.Rows[0][0].ToString()) + "' " + "WHERE PatNum = " + POut.long(Pat.PatNum);
            Db.nonQ(command);
        }
         
        command = "UPDATE patientnote SET FamFinancial = '' " + "WHERE PatNum = " + POut.long(Pat.Guarantor);
        Db.nonQ(command);
        //change guarantor of all family members:
        command = "UPDATE patient SET " + "Guarantor = " + POut.long(Pat.PatNum) + " WHERE Guarantor = " + POut.long(Pat.Guarantor);
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static void combineGuarantors(Family Fam, Patient Pat) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Fam, Pat);
            return ;
        }
         
        //concat cur notes with guarantor notes
        //+"addrnote = '"+POut.PString(FamilyList[GuarIndex].FamAddrNote)
        //									+POut.PString(cur.FamAddrNote)+"', "
        String command = "UPDATE patient SET " + "famfinurgnote = '" + POut.String(Fam.ListPats[0].FamFinUrgNote) + POut.string(Pat.FamFinUrgNote) + "' " + "WHERE patnum = '" + Pat.Guarantor.ToString() + "'";
        Db.nonQ(command);
        //delete cur notes
        //+"famaddrnote = '', "
        command = "UPDATE patient SET " + "famfinurgnote = '' " + "WHERE patnum = '" + Pat.PatNum + "'";
        Db.nonQ(command);
        //concat family financial notes
        PatientNote PatientNoteCur = PatientNotes.refresh(Pat.PatNum,Pat.Guarantor);
        //patientnote table must have been refreshed for this to work.
        //Makes sure there are entries for patient and for guarantor.
        //Also, PatientNotes.cur.FamFinancial will now have the guar info in it.
        String strGuar = PatientNoteCur.FamFinancial;
        command = "SELECT famfinancial " + "FROM patientnote WHERE patnum ='" + POut.long(Pat.PatNum) + "'";
        //MessageBox.Show(string command);
        DataTable table = Db.getTable(command);
        String strCur = PIn.String(table.Rows[0][0].ToString());
        command = "UPDATE patientnote SET " + "famfinancial = '" + POut.string(strGuar + strCur) + "' " + "WHERE patnum = '" + Pat.Guarantor.ToString() + "'";
        Db.nonQ(command);
        //delete cur financial notes
        command = "UPDATE patientnote SET " + "famfinancial = ''" + "WHERE patnum = '" + Pat.PatNum.ToString() + "'";
        Db.nonQ(command);
    }

    /**
    * Key=patNum, value=formatted name.  Used for reports, FormASAP, FormTrackNext, and FormUnsched.
    */
    public static Dictionary<long, String> getAllPatientNames() throws Exception {
        //No need to check RemotingRole; no call to db.
        DataTable table = getAllPatientNamesTable();
        Dictionary<long, String> dict = new Dictionary<long, String>();
        long patnum = new long();
        String lname = new String(), fname = new String(), middlei = new String(), preferred = new String();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            patnum = PIn.Long(table.Rows[i][0].ToString());
            lname = PIn.String(table.Rows[i][1].ToString());
            fname = PIn.String(table.Rows[i][2].ToString());
            middlei = PIn.String(table.Rows[i][3].ToString());
            preferred = PIn.String(table.Rows[i][4].ToString());
            if (StringSupport.equals(preferred, ""))
            {
                dict.Add(patnum, lname + ", " + fname + " " + middlei);
            }
            else
            {
                dict.Add(patnum, lname + ", '" + preferred + "' " + fname + " " + middlei);
            } 
        }
        return dict;
    }

    public static DataTable getAllPatientNamesTable() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT patnum,lname,fname,middlei,preferred " + "FROM patient";
        DataTable table = Db.getTable(command);
        return table;
    }

    /**
    * 
    */
    public static void updateAddressForFam(Patient pat) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), pat);
            return ;
        }
         
        String command = "UPDATE patient SET " + "Address = '" + POut.string(pat.Address) + "'" + ",Address2 = '" + POut.string(pat.Address2) + "'" + ",City = '" + POut.string(pat.City) + "'" + ",State = '" + POut.string(pat.State) + "'" + ",Country = '" + POut.string(pat.Country) + "'" + ",Zip = '" + POut.string(pat.Zip) + "'" + ",HmPhone = '" + POut.string(pat.HmPhone) + "'" + ",credittype = '" + POut.string(pat.CreditType) + "'" + ",priprov = '" + POut.long(pat.PriProv) + "'" + ",secprov = '" + POut.long(pat.SecProv) + "'" + ",feesched = '" + POut.long(pat.FeeSched) + "'" + ",billingtype = '" + POut.long(pat.BillingType) + "'" + " WHERE guarantor = '" + POut.long(pat.Guarantor) + "'";
        Db.nonQ(command);
    }

    /**
    * Used in patient terminal, aka sheet import.  Synchs less fields than the normal synch.
    */
    public static void updateAddressForFamTerminal(Patient pat) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), pat);
            return ;
        }
         
        String command = "UPDATE patient SET " + "Address = '" + POut.string(pat.Address) + "'" + ",Address2 = '" + POut.string(pat.Address2) + "'" + ",City = '" + POut.string(pat.City) + "'" + ",State = '" + POut.string(pat.State) + "'" + ",Zip = '" + POut.string(pat.Zip) + "'" + ",HmPhone = '" + POut.string(pat.HmPhone) + "'" + " WHERE guarantor = '" + POut.long(pat.Guarantor) + "'";
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static void updateArriveEarlyForFam(Patient pat) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), pat);
            return ;
        }
         
        String command = "UPDATE patient SET " + "AskToArriveEarly = '" + POut.int(pat.AskToArriveEarly) + "'" + " WHERE guarantor = '" + POut.long(pat.Guarantor) + "'";
        DataTable table = Db.getTable(command);
    }

    /**
    * 
    */
    public static void updateNotesForFam(Patient pat) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), pat);
            return ;
        }
         
        String command = "UPDATE patient SET " + "addrnote = '" + POut.string(pat.AddrNote) + "'" + " WHERE guarantor = '" + POut.long(pat.Guarantor) + "'";
        Db.nonQ(command);
    }

    /**
    * Only used from FormRecallListEdit.  Updates two fields for family if they are already the same for the entire family.  If they start out different for different family members, then it only changes the two fields for the single patient.
    */
    public static void updatePhoneAndNoteIfNeeded(String newphone, String newnote, long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), newphone, newnote, patNum);
            return ;
        }
         
        String command = "SELECT Guarantor,HmPhone,AddrNote FROM patient WHERE Guarantor=" + "(SELECT Guarantor FROM patient WHERE PatNum=" + POut.long(patNum) + ")";
        DataTable table = Db.getTable(command);
        boolean phoneIsSame = true;
        boolean noteIsSame = true;
        long guar = PIn.Long(table.Rows[0]["Guarantor"].ToString());
        for (int i = 0;i < table.Rows.Count;i++)
        {
            if (table.Rows[i]["HmPhone"].ToString() != table.Rows[0]["HmPhone"].ToString())
            {
                phoneIsSame = false;
            }
             
            if (table.Rows[i]["AddrNote"].ToString() != table.Rows[0]["AddrNote"].ToString())
            {
                noteIsSame = false;
            }
             
        }
        command = "UPDATE patient SET HmPhone='" + POut.string(newphone) + "' WHERE ";
        if (phoneIsSame)
        {
            command += "Guarantor=" + POut.long(guar);
        }
        else
        {
            command += "PatNum=" + POut.long(patNum);
        } 
        Db.nonQ(command);
        command = "UPDATE patient SET AddrNote='" + POut.string(newnote) + "' WHERE ";
        if (noteIsSame)
        {
            command += "Guarantor=" + POut.long(guar);
        }
        else
        {
            command += "PatNum=" + POut.long(patNum);
        } 
        Db.nonQ(command);
    }

    /**
    * This is only used in the Billing dialog
    */
    public static List<PatAging> getAgingList(String age, DateTime lastStatement, List<long> billingNums, boolean excludeAddr, boolean excludeNeg, double excludeLessThan, boolean excludeInactive, boolean includeChanged, boolean excludeInsPending, boolean excludeIfUnsentProcs, boolean ignoreInPerson, long clinicNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<PatAging>>GetObject(MethodBase.GetCurrentMethod(), age, lastStatement, billingNums, excludeAddr, excludeNeg, excludeLessThan, excludeInactive, includeChanged, excludeInsPending, excludeIfUnsentProcs, ignoreInPerson, clinicNum);
        }
         
        String command = "";
        Random rnd = new Random();
        String rndStr = rnd.Next(1000000).ToString();
        if (includeChanged)
        {
            command += "DROP TABLE IF EXISTS templastproc" + rndStr + ";\r\n" + 
            "\t\t\t\t\tCREATE TABLE templastproc" + rndStr + "(\r\n" + 
            "\t\t\t\t\tGuarantor bigint unsigned NOT NULL,\r\n" + 
            "\t\t\t\t\tLastProc date NOT NULL,\r\n" + 
            "\t\t\t\t\tPRIMARY KEY (Guarantor));\r\n" + 
            "\t\t\t\t\tINSERT INTO templastproc" + rndStr + "\r\n" + 
            "\t\t\t\t\tSELECT patient.Guarantor,MAX(ProcDate)\r\n" + 
            "\t\t\t\t\tFROM procedurelog,patient\r\n" + 
            "\t\t\t\t\tWHERE patient.PatNum=procedurelog.PatNum\r\n" + 
            "\t\t\t\t\tAND procedurelog.ProcStatus=2\r\n" + 
            "\t\t\t\t\tAND procedurelog.ProcFee>0\r\n" + 
            "\t\t\t\t\tGROUP BY patient.Guarantor;\r\n" + 
            "\t\t\t\t\t\r\n" + 
            "\t\t\t\t\tDROP TABLE IF EXISTS templastpay" + rndStr + ";\r\n" + 
            "\t\t\t\t\tCREATE TABLE templastpay" + rndStr + "(\r\n" + 
            "\t\t\t\t\tGuarantor bigint unsigned NOT NULL,\r\n" + 
            "\t\t\t\t\tLastPay date NOT NULL,\r\n" + 
            "\t\t\t\t\tPRIMARY KEY (Guarantor));\r\n" + 
            "\t\t\t\t\tINSERT INTO templastpay" + rndStr + "\r\n" + 
            "\t\t\t\t\tSELECT patient.Guarantor,MAX(DateCP)\r\n" + 
            "\t\t\t\t\tFROM claimproc,patient\r\n" + 
            "\t\t\t\t\tWHERE claimproc.PatNum=patient.PatNum\r\n" + 
            "\t\t\t\t\tAND claimproc.InsPayAmt>0\r\n" + 
            "\t\t\t\t\tGROUP BY patient.Guarantor;";
        }
         
        if (excludeInsPending)
        {
            command += "DROP TABLE IF EXISTS tempclaimspending" + rndStr + ";\r\n" + 
            "\t\t\t\t\tCREATE TABLE tempclaimspending" + rndStr + "(\r\n" + 
            "\t\t\t\t\tGuarantor bigint unsigned NOT NULL,\r\n" + 
            "\t\t\t\t\tPendingClaimCount int NOT NULL,\r\n" + 
            "\t\t\t\t\tPRIMARY KEY (Guarantor));\r\n" + 
            "\t\t\t\t\tINSERT INTO tempclaimspending" + rndStr + "\r\n" + 
            "\t\t\t\t\tSELECT patient.Guarantor,COUNT(*)\r\n" + 
            "\t\t\t\t\tFROM claim,patient\r\n" + 
            "\t\t\t\t\tWHERE claim.PatNum=patient.PatNum\r\n" + 
            "\t\t\t\t\tAND (ClaimStatus=\'U\' OR ClaimStatus=\'H\' OR ClaimStatus=\'W\' OR ClaimStatus=\'S\')\r\n" + 
            "\t\t\t\t\tAND (ClaimType=\'P\' OR ClaimType=\'S\' OR ClaimType=\'Other\')\r\n" + 
            "\t\t\t\t\tGROUP BY patient.Guarantor;";
        }
         
        if (excludeIfUnsentProcs)
        {
            command += "DROP TABLE IF EXISTS tempunsentprocs" + rndStr + ";\r\n" + 
            "\t\t\t\t\tCREATE TABLE tempunsentprocs" + rndStr + "(\r\n" + 
            "\t\t\t\t\tGuarantor bigint unsigned NOT NULL,\r\n" + 
            "\t\t\t\t\tUnsentProcCount int NOT NULL,\r\n" + 
            "\t\t\t\t\tPRIMARY KEY (Guarantor));\r\n" + 
            "\t\t\t\t\tINSERT INTO tempunsentprocs" + rndStr + "\r\n" + 
            "\t\t\t\t\tSELECT patient.Guarantor,COUNT(*)\r\n" + 
            "\t\t\t\t\tFROM patient,procedurecode,procedurelog,claimproc \r\n" + 
            "\t\t\t\t\tWHERE claimproc.procnum=procedurelog.procnum\r\n" + 
            "\t\t\t\t\tAND patient.PatNum=procedurelog.PatNum\r\n" + 
            "\t\t\t\t\tAND procedurelog.CodeNum=procedurecode.CodeNum\r\n" + 
            "\t\t\t\t\tAND claimproc.NoBillIns=0\r\n" + 
            "\t\t\t\t\tAND procedurelog.ProcFee>0\r\n" + 
            "\t\t\t\t\tAND claimproc.Status=6\r\n" + 
            "\t\t\t\t\tAND procedurelog.procstatus=2\r\n" + 
            "\t\t\t\t\tAND procedurelog.ProcDate > " + DbHelper.dateAddMonth(DbHelper.curdate(),"-6") + " " + "GROUP BY patient.Guarantor;";
        }
         
        command += "SELECT patient.PatNum,Bal_0_30,Bal_31_60,Bal_61_90,BalOver90,BalTotal,BillingType," + "InsEst,LName,FName,MiddleI,PayPlanDue,Preferred, " + "IFNULL(MAX(statement.DateSent),'0001-01-01') AS LastStatement ";
        if (includeChanged)
        {
            command += ",IFNULL(templastproc" + rndStr + ".LastProc,\'0001-01-01\') AS LastChange," + "IFNULL(templastpay" + rndStr + ".LastPay,\'0001-01-01\') AS LastPayment ";
        }
         
        if (excludeInsPending)
        {
            command += ",IFNULL(tempclaimspending" + rndStr + ".PendingClaimCount,\'0\') AS ClaimCount ";
        }
         
        if (excludeIfUnsentProcs)
        {
            command += ",IFNULL(tempunsentprocs" + rndStr + ".UnsentProcCount,\'0\') AS unsentProcCount_ ";
        }
         
        command += "FROM patient " + "LEFT JOIN statement ON patient.PatNum=statement.PatNum ";
        //actually only gets guarantors since others are 0.
        if (ignoreInPerson)
        {
            command += "AND statement.Mode_ != 1 ";
        }
         
        if (includeChanged)
        {
            command += "LEFT JOIN templastproc" + rndStr + " ON patient.PatNum=templastproc" + rndStr + ".Guarantor " + "LEFT JOIN templastpay" + rndStr + " ON patient.PatNum=templastpay" + rndStr + ".Guarantor ";
        }
         
        if (excludeInsPending)
        {
            command += "LEFT JOIN tempclaimspending" + rndStr + " ON patient.PatNum=tempclaimspending" + rndStr + ".Guarantor ";
        }
         
        if (excludeIfUnsentProcs)
        {
            command += "LEFT JOIN tempunsentprocs" + rndStr + " ON patient.PatNum=tempunsentprocs" + rndStr + ".Guarantor ";
        }
         
        command += "WHERE ";
        if (excludeInactive)
        {
            command += "EXISTS (SELECT * FROM patient p2 WHERE p2.Guarantor=patient.Guarantor AND p2.PatStatus != " + POut.int(((Enum)PatientStatus.Inactive).ordinal()) + ") AND ";
        }
         
        if (PrefC.getBool(PrefName.BalancesDontSubtractIns))
        {
            command += "(BalTotal";
        }
        else
        {
            command += "(BalTotal - InsEst";
        } 
        command += " > '" + POut.double(excludeLessThan + .005) + "'" + " OR PayPlanDue > 0";
        //add half a penny for rounding error
        if (!excludeNeg)
        {
            if (PrefC.getBool(PrefName.BalancesDontSubtractIns))
            {
                command += " OR BalTotal < '-.005')";
            }
            else
            {
                command += " OR BalTotal - InsEst < '-.005')";
            } 
        }
        else
        {
            command += ")";
        } 
        System.String __dummyScrutVar0 = age;
        //where is age 0. Is it missing because no restriction
        if (__dummyScrutVar0.equals("30"))
        {
            command += " AND (Bal_31_60 > '0' OR Bal_61_90 > '0' OR BalOver90 > '0' OR PayPlanDue > 0)";
        }
        else if (__dummyScrutVar0.equals("60"))
        {
            command += " AND (Bal_61_90 > '0' OR BalOver90 > '0' OR PayPlanDue > 0)";
        }
        else if (__dummyScrutVar0.equals("90"))
        {
            command += " AND (BalOver90 > '0' OR PayPlanDue > 0)";
        }
           
        for (int i = 0;i < billingNums.Count;i++)
        {
            //if billingNums.Count==0, then we'll include all billing types
            if (i == 0)
            {
                command += " AND (billingtype = '";
            }
            else
            {
                command += " OR billingtype = '";
            } 
            command += POut.Long(billingNums[i]) + "'";
            //DefC.Short[(int)DefCat.BillingTypes][billingIndices[i]].DefNum.ToString()+"'";
            if (i == billingNums.Count - 1)
            {
                command += ")";
            }
             
        }
        if (excludeAddr)
        {
            command += " AND (zip !='')";
        }
         
        if (clinicNum > 0)
        {
            command += " AND patient.ClinicNum=" + clinicNum + " ";
        }
         
        command += " GROUP BY patient.PatNum,Bal_0_30,Bal_31_60,Bal_61_90,BalOver90,BalTotal,BillingType," + "InsEst,LName,FName,MiddleI,PayPlanDue,Preferred " + "HAVING (LastStatement < " + POut.Date(lastStatement.AddDays(1)) + " ";
        //<midnight of lastStatement date
        //+"OR PayPlanDue>0 ";we don't have a great way to trigger due to a payplancharge yet
        if (includeChanged)
        {
            command += "OR LastChange > LastStatement " + "OR LastPayment > LastStatement) ";
        }
        else
        {
            //eg '2005-10-25' > '2005-10-24 15:00:00'
            command += ") ";
        } 
        if (excludeInsPending)
        {
            command += "AND ClaimCount=0 ";
        }
         
        if (excludeIfUnsentProcs)
        {
            command += "AND unsentProcCount_=0 ";
        }
         
        command += "ORDER BY LName,FName";
        //Debug.WriteLine(command);
        DataTable table = Db.getTable(command);
        List<PatAging> agingList = new List<PatAging>();
        PatAging patage;
        Patient pat;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            patage = new PatAging();
            patage.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
            patage.Bal_0_30 = PIn.Double(table.Rows[i]["Bal_0_30"].ToString());
            patage.Bal_31_60 = PIn.Double(table.Rows[i]["Bal_31_60"].ToString());
            patage.Bal_61_90 = PIn.Double(table.Rows[i]["Bal_61_90"].ToString());
            patage.BalOver90 = PIn.Double(table.Rows[i]["BalOver90"].ToString());
            patage.BalTotal = PIn.Double(table.Rows[i]["BalTotal"].ToString());
            patage.InsEst = PIn.Double(table.Rows[i]["InsEst"].ToString());
            pat = new Patient();
            pat.LName = PIn.String(table.Rows[i]["LName"].ToString());
            pat.FName = PIn.String(table.Rows[i]["FName"].ToString());
            pat.MiddleI = PIn.String(table.Rows[i]["MiddleI"].ToString());
            pat.Preferred = PIn.String(table.Rows[i]["Preferred"].ToString());
            patage.PatName = pat.getNameLF();
            patage.AmountDue = patage.BalTotal - patage.InsEst;
            patage.DateLastStatement = PIn.Date(table.Rows[i]["LastStatement"].ToString());
            patage.BillingType = PIn.Long(table.Rows[i]["BillingType"].ToString());
            patage.PayPlanDue = PIn.Double(table.Rows[i]["PayPlanDue"].ToString());
            //if(excludeInsPending && patage.InsEst>0){
            //don't add
            //}
            //else{
            agingList.Add(patage);
        }
        //}
        //PatAging[] retVal=new PatAging[agingList.Count];
        //for(int i=0;i<retVal.Length;i++){
        //	retVal[i]=agingList[i];
        //}
        if (includeChanged)
        {
            command = "DROP TABLE IF EXISTS templastproc" + rndStr + "\"";
            Db.nonQ(command);
            command = "DROP TABLE IF EXISTS templastpay" + rndStr + "\"";
            Db.nonQ(command);
        }
         
        if (excludeInsPending)
        {
            command = "DROP TABLE IF EXISTS tempclaimspending" + rndStr + "\"";
            Db.nonQ(command);
        }
         
        if (excludeIfUnsentProcs)
        {
            command = "DROP TABLE IF EXISTS tempunsentprocs" + rndStr + "\"";
            Db.nonQ(command);
        }
         
        return agingList;
    }

    /**
    * Used only to run finance charges, so it ignores negative balances.
    */
    public static PatAging[] getAgingListArray() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<PatAging[]>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT patnum,Bal_0_30,Bal_31_60,Bal_61_90,BalOver90,BalTotal,InsEst,LName,FName,MiddleI,PriProv,BillingType " + "FROM patient " + " WHERE Bal_0_30 + Bal_31_60 + Bal_61_90 + BalOver90 - InsEst > '0.005'" + " ORDER BY LName,FName";
        //actually only gets guarantors since others are 0.
        //more that 1/2 cent
        DataTable table = Db.getTable(command);
        PatAging[] AgingList = new PatAging[table.Rows.Count];
        for (int i = 0;i < table.Rows.Count;i++)
        {
            AgingList[i] = new PatAging();
            AgingList[i].PatNum = PIn.Long(table.Rows[i][0].ToString());
            AgingList[i].Bal_0_30 = PIn.Double(table.Rows[i][1].ToString());
            AgingList[i].Bal_31_60 = PIn.Double(table.Rows[i][2].ToString());
            AgingList[i].Bal_61_90 = PIn.Double(table.Rows[i][3].ToString());
            AgingList[i].BalOver90 = PIn.Double(table.Rows[i][4].ToString());
            AgingList[i].BalTotal = PIn.Double(table.Rows[i][5].ToString());
            AgingList[i].InsEst = PIn.Double(table.Rows[i][6].ToString());
            AgingList[i].PatName = PIn.String(table.Rows[i][7].ToString()) + ", " + PIn.String(table.Rows[i][8].ToString()) + " " + PIn.String(table.Rows[i][9].ToString());
                ;
            //AgingList[i].Balance=AgingList[i].Bal_0_30+AgingList[i].Bal_31_60
            //	+AgingList[i].Bal_61_90+AgingList[i].BalOver90;
            AgingList[i].AmountDue = AgingList[i].BalTotal - AgingList[i].InsEst;
            AgingList[i].PriProv = PIn.Long(table.Rows[i][10].ToString());
            AgingList[i].BillingType = PIn.Long(table.Rows[i][11].ToString());
        }
        return AgingList;
    }

    /**
    * Gets the next available integer chart number.  Will later add a where clause based on preferred format.
    */
    public static String getNextChartNum() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT ChartNumber from patient WHERE " + DbHelper.regexp("ChartNumber","^[0-9]+$") + " " + "ORDER BY (chartnumber+0) DESC";
        //matches any positive number of digits
        //1/13/05 by Keyush Shaw-added 0.
        command = DbHelper.limitOrderBy(command,1);
        DataTable table = Db.getTable(command);
        if (table.Rows.Count == 0)
        {
            return "1";
        }
         
        //no existing chart numbers
        String lastChartNum = PIn.String(table.Rows[0][0].ToString());
        try
        {
            return (Convert.ToInt64(lastChartNum) + 1).ToString();
        }
        catch (Exception __dummyCatchVar3)
        {
            throw new ApplicationException(lastChartNum + " is an existing ChartNumber.  It's too big to convert to a long int, so it's not possible to add one to automatically increment.");
        }
    
    }

    //or could add more match conditions
    /**
    * Returns the name(only one) of the patient using this chartnumber.
    */
    public static String chartNumUsedBy(String chartNum, long excludePatNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), chartNum, excludePatNum);
        }
         
        String command = "SELECT LName,FName from patient WHERE " + "ChartNumber = '" + chartNum + "' AND PatNum != '" + excludePatNum.ToString() + "'";
        DataTable table = Db.getTable(command);
        String retVal = "";
        if (table.Rows.Count != 0)
        {
            //found duplicate chart number
            retVal = PIn.String(table.Rows[0][1].ToString()) + " " + PIn.String(table.Rows[0][0].ToString());
        }
         
        return retVal;
    }

    /**
    * Used in the patient select window to determine if a trial version user is over their limit.
    */
    public static int getNumberPatients() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetInt(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT Count(*) FROM patient";
        DataTable table = Db.getTable(command);
        return PIn.Int(table.Rows[0][0].ToString());
    }

    /**
    * Makes a call to the db to figure out if the current HasIns status is correct.  If not, then it changes it.
    */
    public static void setHasIns(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patNum);
            return ;
        }
         
        String command = "SELECT patient.HasIns,COUNT(patplan.PatNum) FROM patient " + "LEFT JOIN patplan ON patplan.PatNum=patient.PatNum" + " WHERE patient.PatNum=" + POut.long(patNum) + " GROUP BY patplan.PatNum,patient.HasIns";
        DataTable table = Db.getTable(command);
        String newVal = "";
        if (!StringSupport.equals(table.Rows[0][1].ToString(), "0"))
        {
            newVal = "I";
        }
         
        if (!StringSupport.equals(newVal, table.Rows[0][0].ToString()))
        {
            command = "UPDATE patient SET HasIns='" + POut.string(newVal) + "' WHERE PatNum=" + POut.long(patNum);
            Db.nonQ(command);
        }
         
    }

    /**
    * 
    */
    public static DataTable getBirthdayList(DateTime dateFrom, DateTime dateTo) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), dateFrom, dateTo);
        }
         
        String command = "SELECT LName,FName,Preferred,Address,Address2,City,State,Zip,Birthdate " + "FROM patient " + "WHERE SUBSTRING(Birthdate,6,5) >= '" + dateFrom.ToString("MM-dd") + "' " + "AND SUBSTRING(Birthdate,6,5) <= '" + dateTo.ToString("MM-dd") + "' " + "AND Birthdate > '1880-01-01' " + "AND PatStatus=0	" + "ORDER BY MONTH(Birthdate),DAY(Birthdate)";
        //+"ORDER BY "+DbHelper.DateFormatColumn("Birthdate","%m/%d/%Y");
        DataTable table = Db.getTable(command);
        table.Columns.Add("Age");
        for (int i = 0;i < table.Rows.Count;i++)
        {
            table.Rows[i]["Age"] = DateToAge(PIn.Date(table.Rows[i]["Birthdate"].ToString()), dateTo.AddDays(1)).ToString();
        }
        return table;
    }

    /**
    * Gets the provider for this patient.  If provNum==0, then it gets the practice default prov.
    */
    public static long getProvNum(Patient pat) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetLong(MethodBase.GetCurrentMethod(), pat);
        }
         
        if (pat.PriProv != 0)
            return pat.PriProv;
         
        if (PrefC.getLong(PrefName.PracticeDefaultProv) == 0)
        {
            MessageBox.Show(Lans.g("Patients","Please set a default provider in the practice setup window."));
            return ProviderC.getListShort()[0].ProvNum;
        }
         
        return PrefC.getLong(PrefName.PracticeDefaultProv);
    }

    /**
    * Gets the list of all valid patient primary keys. Used when checking for missing ADA procedure codes after a user has begun entering them manually. This function is necessary because not all patient numbers are necessarily consecutive (say if the database was created due to a conversion from another program and the customer wanted to keep their old patient ids after the conversion).
    */
    public static long[] getAllPatNums() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<long[]>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT PatNum From patient";
        DataTable dt = Db.getTable(command);
        long[] patnums = new long[dt.Rows.Count];
        for (int i = 0;i < patnums.Length;i++)
        {
            patnums[i] = PIn.Long(dt.Rows[i]["PatNum"].ToString());
        }
        return patnums;
    }

    /**
    * Converts a date to an age. If age is over 115, then returns 0.
    */
    public static int dateToAge(DateTime date) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (date.Year < 1880)
            return 0;
         
        if (date.Month < DateTime.Now.Month)
        {
            return DateTime.Now.Year - date.Year;
        }
         
        //birthday in previous month
        if (date.Month == DateTime.Now.Month && date.Day <= DateTime.Now.Day)
        {
            return DateTime.Now.Year - date.Year;
        }
         
        return DateTime.Now.Year - date.Year - 1;
    }

    //birthday in this month
    /**
    * Converts a date to an age. If age is over 115, then returns 0.
    */
    public static int dateToAge(DateTime birthdate, DateTime asofDate) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (birthdate.Year < 1880)
            return 0;
         
        if (birthdate.Month < asofDate.Month)
        {
            return asofDate.Year - birthdate.Year;
        }
         
        //birthday in previous month
        if (birthdate.Month == asofDate.Month && birthdate.Day <= asofDate.Day)
        {
            return asofDate.Year - birthdate.Year;
        }
         
        return asofDate.Year - birthdate.Year - 1;
    }

    //birthday in this month
    /**
    * If zero, returns empty string.  Otherwise returns simple year.  Also see PatientLogic.DateToAgeString().
    */
    public static String ageToString(int age) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (age == 0)
        {
            return "";
        }
        else
        {
            return age.ToString();
        } 
    }

    public static void reformatAllPhoneNumbers() throws Exception {
        String oldTel = new String();
        String newTel = new String();
        String idNum = new String();
        String command = "select * from patient";
        DataTable table = Db.getTable(command);
        for (int i = 0;i < table.Rows.Count;i++)
        {
            idNum = PIn.String(table.Rows[i][0].ToString());
            //home
            oldTel = PIn.String(table.Rows[i][15].ToString());
            newTel = TelephoneNumbers.reFormat(oldTel);
            if (!StringSupport.equals(oldTel, newTel))
            {
                command = "UPDATE patient SET hmphone = '" + POut.string(newTel) + "' WHERE patNum = '" + idNum + "'";
                Db.nonQ(command);
            }
             
            //wk:
            oldTel = PIn.String(table.Rows[i][16].ToString());
            newTel = TelephoneNumbers.reFormat(oldTel);
            if (!StringSupport.equals(oldTel, newTel))
            {
                command = "UPDATE patient SET wkphone = '" + POut.string(newTel) + "' WHERE patNum = '" + idNum + "'";
                Db.nonQ(command);
            }
             
            //wireless
            oldTel = PIn.String(table.Rows[i][17].ToString());
            newTel = TelephoneNumbers.reFormat(oldTel);
            if (!StringSupport.equals(oldTel, newTel))
            {
                command = "UPDATE patient SET wirelessphone = '" + POut.string(newTel) + "' WHERE patNum = '" + idNum + "'";
                Db.nonQ(command);
            }
             
        }
        command = "select * from carrier";
        Db.nonQ(command);
        for (int i = 0;i < table.Rows.Count;i++)
        {
            idNum = PIn.String(table.Rows[i][0].ToString());
            //ph
            oldTel = PIn.String(table.Rows[i][7].ToString());
            newTel = TelephoneNumbers.reFormat(oldTel);
            if (!StringSupport.equals(oldTel, newTel))
            {
                command = "UPDATE carrier SET Phone = '" + POut.string(newTel) + "' WHERE CarrierNum = '" + idNum + "'";
                Db.nonQ(command);
            }
             
        }
    }

    public static DataTable getGuarantorInfo(long PatientID) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), PatientID);
        }
         
        String command = "SELECT FName,MiddleI,LName,Guarantor,Address,\r\n" + 
        "\t\t\t\t\t\t\t\tAddress2,City,State,Zip,Email,EstBalance,\r\n" + 
        "\t\t\t\t\t\t\t\tBalTotal,Bal_0_30,Bal_31_60,Bal_61_90,BalOver90\r\n" + 
        "\t\t\t\t\t\tFROM Patient Where Patnum=" + PatientID + " AND patnum=guarantor";
        return Db.getTable(command);
    }

    /**
    * Will return 0 if can't find exact matching pat.
    */
    public static long getPatNumByNameAndBirthday(String lName, String fName, DateTime birthdate) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetLong(MethodBase.GetCurrentMethod(), lName, fName, birthdate);
        }
         
        //Not Archived
        String command = "SELECT PatNum FROM patient WHERE " + "LName='" + POut.string(lName) + "' " + "AND FName='" + POut.string(fName) + "' " + "AND Birthdate=" + POut.date(birthdate) + " " + "AND PatStatus!=" + POut.int(((Enum)PatientStatus.Archived).ordinal()) + " " + "AND PatStatus!=" + POut.int(((Enum)PatientStatus.Deleted).ordinal());
        return PIn.long(Db.getScalar(command));
    }

    //Not Deleted
    /**
    * Will return 0 if can't find an exact matching pat.  Because it does not include birthdate, it's not specific enough for most situations.
    */
    public static long getPatNumByName(String lName, String fName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetLong(MethodBase.GetCurrentMethod(), lName, fName);
        }
         
        String command = "SELECT PatNum FROM patient WHERE " + "LName='" + POut.string(lName) + "' " + "AND FName='" + POut.string(fName) + "' " + "AND PatStatus!=4 " + "LIMIT 1";
        return PIn.long(Db.getScalar(command));
    }

    //not deleted
    /**
    * When importing webforms, if it can't find an exact match, this method attempts a similar match.
    */
    public static List<Patient> getSimilarList(String lName, String fName, DateTime birthdate) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Patient>>GetObject(MethodBase.GetCurrentMethod(), lName, fName, birthdate);
        }
         
        int subStrIndexlName = 2;
        int subStrIndexfName = 2;
        if (lName.Length < 2)
        {
            subStrIndexlName = lName.Length;
        }
         
        if (fName.Length < 2)
        {
            subStrIndexfName = fName.Length;
        }
         
        //either a matching bd
        //or no bd
        //Not Archived
        String command = "SELECT * FROM patient WHERE " + "LName LIKE '" + POut.String(lName.Substring(0, subStrIndexlName)) + "%' " + "AND FName LIKE '" + POut.String(fName.Substring(0, subStrIndexfName)) + "%' " + "AND (Birthdate=" + POut.date(birthdate) + " " + "OR Birthdate < " + POut.date(new DateTime(1880, 1, 1)) + ") " + "AND PatStatus!=" + POut.int(((Enum)PatientStatus.Archived).ordinal()) + " " + "AND PatStatus!=" + POut.int(((Enum)PatientStatus.Deleted).ordinal());
        return Crud.PatientCrud.SelectMany(command);
    }

    //Not Deleted
    /**
    * Returns a list of patients that match last and first name.
    */
    public static List<Patient> getListByName(String lName, String fName, long PatNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Patient>>GetObject(MethodBase.GetCurrentMethod(), lName, fName, PatNum);
        }
         
        String command = "SELECT * FROM patient WHERE " + "LOWER(LName)=LOWER('" + POut.string(lName) + "') " + "AND LOWER(FName)=LOWER('" + POut.string(fName) + "') " + "AND PatNum!=" + POut.long(PatNum) + " AND PatStatus!=4";
        return Crud.PatientCrud.SelectMany(command);
    }

    //not deleted
    public static void updateFamilyBillingType(long billingType, long Guarantor) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), billingType, Guarantor);
            return ;
        }
         
        String command = "UPDATE patient SET BillingType=" + POut.long(billingType) + " WHERE Guarantor=" + POut.long(Guarantor);
        Db.nonQ(command);
    }

    public static DataTable getPartialPatientData(long PatNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), PatNum);
        }
         
        String command = "SELECT FName,LName," + DbHelper.dateFormatColumn("birthdate","%m/%d/%Y") + " BirthDate,Gender " + "FROM patient WHERE patient.PatNum=" + PatNum;
        return Db.getTable(command);
    }

    public static DataTable getPartialPatientData2(long PatNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), PatNum);
        }
         
        String command = "SELECT FName,LName," + DbHelper.dateFormatColumn("birthdate","%m/%d/%Y") + " BirthDate,Gender " + "FROM patient WHERE PatNum In (SELECT Guarantor FROM PATIENT WHERE patnum = " + PatNum + ")";
        return Db.getTable(command);
    }

    public static String getEligibilityDisplayName(long patId) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), patId);
        }
         
        String command = "SELECT FName,LName," + DbHelper.dateFormatColumn("birthdate","%m/%d/%Y") + " BirthDate,Gender " + "FROM patient WHERE patient.PatNum=" + POut.long(patId);
        DataTable table = Db.getTable(command);
        if (table.Rows.Count == 0)
        {
            return "Patient(???) is Eligible";
        }
         
        return PIn.String(table.Rows[0][1].ToString()) + ", " + PIn.String(table.Rows[0][0].ToString()) + " is Eligible";
    }

    /**
    * Gets the DataTable to display for treatment finder report
    */
    public static DataTable getTreatmentFinderList(boolean noIns, boolean patsWithAppts, int monthStart, DateTime dateSince, double aboveAmount, ArrayList providerFilter, ArrayList billingFilter, String code1, String code2) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), noIns);
        }
         
        DataTable table = new DataTable();
        DataRow row = new DataRow();
        //columns that start with lowercase are altered for display rather than being raw data.
        table.Columns.Add("PatNum");
        table.Columns.Add("LName");
        table.Columns.Add("FName");
        table.Columns.Add("contactMethod");
        table.Columns.Add("address");
        table.Columns.Add("City");
        table.Columns.Add("State");
        table.Columns.Add("Zip");
        table.Columns.Add("annualMax");
        table.Columns.Add("amountUsed");
        table.Columns.Add("amountPending");
        table.Columns.Add("amountRemaining");
        table.Columns.Add("treatmentPlan");
        table.Columns.Add("carrierName");
        List<DataRow> rows = new List<DataRow>();
        String command = "\r\n" + 
        "\t\t\t\tDROP TABLE IF EXISTS tempused;\r\n" + 
        "\t\t\t\tDROP TABLE IF EXISTS temppending;\r\n" + 
        "\t\t\t\tDROP TABLE IF EXISTS tempplanned;\r\n" + 
        "\t\t\t\tDROP TABLE IF EXISTS tempannualmax;\r\n" + 
        "\r\n" + 
        "\t\t\t\tCREATE TABLE tempused(\r\n" + 
        "\t\t\t\tPatPlanNum bigint unsigned NOT NULL,\r\n" + 
        "\t\t\t\tAmtUsed double NOT NULL,\r\n" + 
        "\t\t\t\tPRIMARY KEY (PatPlanNum));\r\n" + 
        "\r\n" + 
        "\t\t\t\tCREATE TABLE temppending(\r\n" + 
        "\t\t\t\tPatPlanNum bigint unsigned NOT NULL,\r\n" + 
        "\t\t\t\tPendingAmt double NOT NULL,\r\n" + 
        "\t\t\t\tPRIMARY KEY (PatPlanNum));\r\n" + 
        "\r\n" + 
        "\t\t\t\tCREATE TABLE tempplanned(\r\n" + 
        "\t\t\t\tPatNum bigint unsigned NOT NULL,\r\n" + 
        "\t\t\t\tAmtPlanned double NOT NULL,\r\n" + 
        "\t\t\t\tPRIMARY KEY (PatNum));\r\n" + 
        "\r\n" + 
        "\t\t\t\tCREATE TABLE tempannualmax(\r\n" + 
        "\t\t\t\tPlanNum bigint unsigned NOT NULL,\r\n" + 
        "\t\t\t\tAnnualMax double,\r\n" + 
        "\t\t\t\tPRIMARY KEY (PlanNum));";
        Db.nonQ(command);
        DateTime renewDate = BenefitLogic.ComputeRenewDate(DateTime.Now, monthStart);
        //  MAKEDATE("+renewDate.Year+", "+renewDate.Month+") "
        command = "INSERT INTO tempused\r\n" + 
        "SELECT patplan.PatPlanNum,\r\n" + 
        "SUM(IFNULL(claimproc.InsPayAmt,0))\r\n" + 
        "FROM claimproc\r\n" + 
        "LEFT JOIN patplan ON patplan.PatNum = claimproc.PatNum\r\n" + 
        "AND patplan.InsSubNum = claimproc.InsSubNum\r\n" + 
        "WHERE claimproc.Status IN (1, 3, 4) /*Received, Adjustment, Supplemental*/\r\n" + 
        "AND claimproc.ProcDate >= " + POut.date(renewDate) + " " + "AND claimproc.ProcDate < " + POut.Date(renewDate.AddYears(1)) + " " + "GROUP BY patplan.PatPlanNum";
        //MAKEDATE("+renewDate.Year+"+1, "+renewDate.Month+") "
        Db.nonQ(command);
        command = "INSERT INTO temppending\r\n" + 
        "SELECT patplan.PatPlanNum,\r\n" + 
        "SUM(IFNULL(claimproc.InsPayEst,0))\r\n" + 
        "FROM claimproc\r\n" + 
        "LEFT JOIN patplan ON patplan.PatNum = claimproc.PatNum\r\n" + 
        "AND patplan.InsSubNum = claimproc.InsSubNum\r\n" + 
        "WHERE claimproc.Status = 0 /*NotReceived*/\r\n" + 
        "AND claimproc.InsPayAmt = 0\r\n" + 
        "AND claimproc.ProcDate >= " + POut.date(renewDate) + " \r\n" + 
        "AND claimproc.ProcDate < " + POut.Date(renewDate.AddYears(1)) + " \r\n" + 
        "GROUP BY patplan.PatPlanNum";
        Db.nonQ(command);
        command = "INSERT INTO tempplanned\r\n" + 
        "SELECT PatNum, SUM(ProcFee)\r\n" + 
        "FROM procedurelog\r\n" + 
        "LEFT JOIN procedurecode ON procedurecode.CodeNum = procedurelog.CodeNum\r\n" + 
        "WHERE ProcStatus = 1 /*treatment planned*/";
        if (!StringSupport.equals(code1, ""))
        {
            command += " AND procedurecode.ProcCode >= '" + POut.string(code1) + "' " + " AND procedurecode.ProcCode <= '" + POut.string(code2) + "' ";
        }
         
        //command+=@" AND (((SELECT STRCMP('"+POut.String(code1)+@"', ProcCode))=0) OR ((SELECT STRCMP('"+POut.String(code1)+@"', ProcCode))=-1))
        //AND (((SELECT STRCMP('"+POut.String(code2)+@"', ProcCode))=0) OR ((SELECT STRCMP('"+POut.String(code2)+@"', ProcCode))=1))";
        command += "AND procedurelog.ProcDate>" + POut.dateT(dateSince) + " " + "GROUP BY PatNum";
        Db.nonQ(command);
        command = "INSERT INTO tempannualmax\r\n" + 
        "SELECT insplan.PlanNum, \r\n" + 
        "(SELECT MAX(MonetaryAmt)/*for oracle in case there\'s more than one*/\r\n" + 
        "FROM benefit\r\n" + 
        "LEFT JOIN covcat ON benefit.CovCatNum=covcat.CovCatNum\r\n" + 
        "WHERE benefit.PlanNum=insplan.PlanNum \r\n" + 
        "AND (covcat.EbenefitCat=1 OR ISNULL(covcat.EbenefitCat))\r\n" + 
        "AND benefit.BenefitType = 5 /* limitation */\r\n" + 
        "AND benefit.MonetaryAmt > 0\r\n" + 
        "AND benefit.QuantityQualifier=0\r\n" + 
        "GROUP BY insplan.PlanNum)\r\n" + 
        "FROM insplan";
        //WHERE insplan.MonthRenew='"+POut.Int(monthStart)+"'";
        //if(monthStart!=13) {//belongs further down, in the WHERE of the final query
        //	command+="AND insplan.MonthRenew='"+POut.Int(monthStart)+"' ";
        //}
        Db.nonQ(command);
        command = "SELECT patient.PatNum, patient.LName, patient.FName,\r\n" + 
        "\t\t\t\tpatient.Email, patient.HmPhone, patient.PreferRecallMethod,\r\n" + 
        "\t\t\t\tpatient.WirelessPhone, patient.WkPhone, patient.Address,\r\n" + 
        "\t\t\t\tpatient.Address2, patient.City, patient.State, patient.Zip,\r\n" + 
        "\t\t\t\tpatient.PriProv, patient.BillingType,\r\n" + 
        "\t\t\t\ttempannualmax.AnnualMax \"$AnnualMax\",\r\n" + 
        "\t\t\t\ttempused.AmtUsed \"$AmountUsed\",\r\n" + 
        "\t\t\t\ttemppending.PendingAmt \"$AmountPending\",\r\n" + 
        "\t\t\t\ttempannualmax.AnnualMax-IFNULL(tempused.AmtUsed,0)-IFNULL(temppending.PendingAmt,0) \"$AmtRemaining\",\r\n" + 
        "\t\t\t\ttempplanned.AmtPlanned \"$TreatmentPlan\", carrier.CarrierName\r\n" + 
        "\t\t\t\tFROM patient\r\n" + 
        "\t\t\t\tLEFT JOIN tempplanned ON tempplanned.PatNum=patient.PatNum\r\n" + 
        "\t\t\t\tLEFT JOIN patplan ON patient.PatNum=patplan.PatNum\r\n" + 
        "\t\t\t\tLEFT JOIN inssub ON patplan.InsSubNum=inssub.InsSubNum\r\n" + 
        "\t\t\t\tLEFT JOIN insplan ON insplan.PlanNum=inssub.PlanNum\r\n" + 
        "\t\t\t\tLEFT JOIN carrier ON insplan.CarrierNum=carrier.CarrierNum\r\n" + 
        "\t\t\t\tLEFT JOIN tempused ON tempused.PatPlanNum=patplan.PatPlanNum\r\n" + 
        "\t\t\t\tLEFT JOIN temppending ON temppending.PatPlanNum=patplan.PatPlanNum\r\n" + 
        "\t\t\t\tLEFT JOIN tempannualmax ON tempannualmax.PlanNum=inssub.PlanNum\r\n" + 
        "\t\t\t\tAND (tempannualmax.AnnualMax IS NOT NULL AND tempannualmax.AnnualMax>0)/*may not be necessary*/\r\n" + 
        "\t\t\t\tWHERE tempplanned.AmtPlanned>0 ";
        if (!noIns)
        {
            //if we don't want patients without insurance
            command += "AND patplan.Ordinal=1 AND insplan.MonthRenew=" + POut.int(monthStart) + " ";
        }
         
        if (!patsWithAppts)
        {
            command += "AND patient.PatNum NOT IN (SELECT PatNum FROM appointment WHERE AptDateTime > NOW()) ";
        }
         
        if (aboveAmount > 0)
        {
            command += "AND (tempannualmax.AnnualMax IS NULL OR tempannualmax.AnnualMax-IFNULL(tempused.AmtUsed,0)>" + POut.double(aboveAmount) + ") ";
        }
         
        for (int i = 0;i < providerFilter.Count;i++)
        {
            if (i == 0)
            {
                command += " AND (patient.PriProv=";
            }
            else
            {
                command += " OR patient.PriProv=";
            } 
            command += POut.Long(ProviderC.getListShort()[(int)providerFilter[i] - 1].ProvNum);
            if (i == providerFilter.Count - 1)
            {
                command += ") ";
            }
             
        }
        for (int i = 0;i < billingFilter.Count;i++)
        {
            if (i == 0)
            {
                command += " AND (patient.BillingType=";
            }
            else
            {
                command += " OR patient.BillingType=";
            } 
            command += POut.Long(DefC.getShort()[((Enum)DefCat.BillingTypes).ordinal()][(int)billingFilter[i] - 1].DefNum);
            if (i == billingFilter.Count - 1)
            {
                command += ") ";
            }
             
        }
        command += "\r\n" + 
        "\t\t\t\tAND patient.PatStatus =0\r\n" + 
        "\t\t\t\tORDER BY tempplanned.AmtPlanned DESC";
        DataTable rawtable = Db.getTable(command);
        command = "DROP TABLE tempused;\r\n" + 
        "\t\t\t\tDROP TABLE temppending;\r\n" + 
        "\t\t\t\tDROP TABLE tempplanned;\r\n" + 
        "\t\t\t\tDROP TABLE tempannualmax;";
        Db.nonQ(command);
        ContactMethod contmeth = ContactMethod.None;
        for (int i = 0;i < rawtable.Rows.Count;i++)
        {
            row = table.NewRow();
            row["PatNum"] = PIn.Long(rawtable.Rows[i]["PatNum"].ToString());
            row["LName"] = rawtable.Rows[i]["LName"].ToString();
            row["FName"] = rawtable.Rows[i]["FName"].ToString();
            contmeth = (ContactMethod)PIn.Long(rawtable.Rows[i]["PreferRecallMethod"].ToString());
            if (contmeth == ContactMethod.None)
            {
                if (PrefC.getBool(PrefName.RecallUseEmailIfHasEmailAddress))
                {
                    //if user only wants to use email if contact method is email
                    if (!StringSupport.equals(rawtable.Rows[i]["Email"].ToString(), ""))
                    {
                        row["contactMethod"] = rawtable.Rows[i]["Email"].ToString();
                    }
                    else
                    {
                        row["contactMethod"] = Lans.g("FormRecallList","Hm:") + rawtable.Rows[i]["HmPhone"].ToString();
                    } 
                }
                else
                {
                    row["contactMethod"] = Lans.g("FormRecallList","Hm:") + rawtable.Rows[i]["HmPhone"].ToString();
                } 
            }
             
            if (contmeth == ContactMethod.HmPhone)
            {
                row["contactMethod"] = Lans.g("FormRecallList","Hm:") + rawtable.Rows[i]["HmPhone"].ToString();
            }
             
            if (contmeth == ContactMethod.WkPhone)
            {
                row["contactMethod"] = Lans.g("FormRecallList","Wk:") + rawtable.Rows[i]["WkPhone"].ToString();
            }
             
            if (contmeth == ContactMethod.WirelessPh)
            {
                row["contactMethod"] = Lans.g("FormRecallList","Cell:") + rawtable.Rows[i]["WirelessPhone"].ToString();
            }
             
            if (contmeth == ContactMethod.Email)
            {
                row["contactMethod"] = rawtable.Rows[i]["Email"].ToString();
            }
             
            if (contmeth == ContactMethod.Mail)
            {
                row["contactMethod"] = Lans.g("FormRecallList","Mail");
            }
             
            if (contmeth == ContactMethod.DoNotCall || contmeth == ContactMethod.SeeNotes)
            {
                row["contactMethod"] = Lans.g("enumContactMethod", contmeth.ToString());
            }
             
            row["address"] = rawtable.Rows[i]["Address"].ToString();
            if (!StringSupport.equals(rawtable.Rows[i]["Address2"].ToString(), ""))
            {
                row["address"] += "\r\n" + rawtable.Rows[i]["Address2"].ToString();
            }
             
            row["City"] = rawtable.Rows[i]["City"].ToString();
            row["State"] = rawtable.Rows[i]["State"].ToString();
            row["Zip"] = rawtable.Rows[i]["Zip"].ToString();
            row["annualMax"] = (PIn.Double(rawtable.Rows[i]["$AnnualMax"].ToString())).ToString("N");
            row["amountUsed"] = (PIn.Double(rawtable.Rows[i]["$AmountUsed"].ToString())).ToString("N");
            row["amountPending"] = (PIn.Double(rawtable.Rows[i]["$AmountPending"].ToString())).ToString("N");
            row["amountRemaining"] = (PIn.Double(rawtable.Rows[i]["$AmtRemaining"].ToString())).ToString("N");
            row["treatmentPlan"] = (PIn.Double(rawtable.Rows[i]["$TreatmentPlan"].ToString())).ToString("N");
            row["carrierName"] = rawtable.Rows[i]["CarrierName"].ToString();
            rows.Add(row);
        }
        for (int i = 0;i < rows.Count;i++)
        {
            table.Rows.Add(rows[i]);
        }
        return table;
    }

    /**
    * Only a partial folderName will be sent in.  Not the .rvg part.
    */
    public static boolean isTrophyFolderInUse(String folderName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), folderName);
        }
         
        String command = "SELECT COUNT(*) FROM patient WHERE TrophyFolder LIKE '%" + POut.string(folderName) + "%'";
        if (StringSupport.equals(Db.getCount(command), "0"))
        {
            return false;
        }
         
        return true;
    }

    /**
    * Used to check if a billing type is in use when user is trying to hide it.
    */
    public static boolean isBillingTypeInUse(long defNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), defNum);
        }
         
        String command = "SELECT COUNT(*) FROM patient WHERE BillingType=" + POut.long(defNum) + " AND PatStatus!=" + POut.int(((Enum)PatientStatus.Deleted).ordinal());
        if (StringSupport.equals(Db.getCount(command), "0"))
        {
            return false;
        }
         
        return true;
    }

    /**
    * To prevent orphaned patients, if patFrom is a guarantor then all family members of patFrom are moved into the family patTo belongs to, and then the merge of the two specified accounts is performed.  Returns false if the merge was canceled by the user.
    */
    public static boolean mergeTwoPatients(long patTo, long patFrom, String atoZpath) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patTo, patFrom);
            return true;
        }
         
        if (patTo == patFrom)
        {
            return true;
        }
         
        //Do not merge the same patient onto itself.
        String[] patNumForeignKeys = new String[]{ "adjustment.PatNum", "allergy.PatNum", "anestheticrecord.PatNum", "anesthvsdata.PatNum", "appointment.PatNum", "claim.PatNum", "claimproc.PatNum", "commlog.PatNum", "creditcard.PatNum", "custreference.PatNum", "disease.PatNum", "document.PatNum", "ehrmeasureevent.PatNum", "ehrnotperformed.PatNum", "ehrprovkey.PatNum", "ehrquarterlykey.PatNum", "ehrsummaryccd.PatNum", "emailmessage.PatNum", "erxlog.PatNum", "etrans.PatNum", "familyhealth.PatNum", "formpat.PatNum", "hl7msg.PatNum", "installmentplan.PatNum", "intervention.PatNum", "inssub.Subscriber", "labcase.PatNum", "labpanel.PatNum", "medicalorder.PatNum", "medicationpat.PatNum", "mount.PatNum", "orthochart.PatNum", "patient.ResponsParty", "patientrace.PatNum", "patplan.PatNum", "payment.PatNum", "payortype.PatNum", "payplan.Guarantor", "payplan.PatNum", "payplancharge.Guarantor", "payplancharge.PatNum", "paysplit.PatNum", "perioexam.PatNum", "phonenumber.PatNum", "plannedappt.PatNum", "popup.PatNum", "procedurelog.PatNum", "procnote.PatNum", "proctp.PatNum", "question.PatNum", "recall.PatNum", "refattach.PatNum", "registrationkey.PatNum", "repeatcharge.PatNum", "reqstudent.PatNum", "reseller.PatNum", "rxpat.PatNum", "screenpat.PatNum", "securitylog.PatNum", "sheet.PatNum", "statement.PatNum", "terminalactive.PatNum", "toothinitial.PatNum", "treatplan.PatNum", "treatplan.ResponsParty", "vaccinepat.PatNum", "vitalsign.PatNum", "xchargetransaction.PatNum" };
        //This list is up to date as of 11/11/2013 up to version v13.3
        //Taken care of below
        //"patfield.PatNum",
        //The patientnote table is ignored because only one record can exist for each patient.
        //The record in 'patFrom' remains so it can be accessed again if needed.
        //"patientnote.PatNum"
        //Treated as a patnum, because it is actually a guarantor for the payment plan, and not a patient guarantor.
        //Treated as a patnum, because it is actually a guarantor for the payment plan, and not a patient guarantor.
        //This is synched with the new information below.
        //"referral.PatNum",
        //task.KeyNum,//Taken care of in a seperate step, because it is not always a patnum.
        String command = "";
        Patient patientFrom = Patients.getPat(patFrom);
        Patient patientTo = Patients.getPat(patTo);
        String atozFrom = ImageStore.getPatientFolder(patientFrom,atoZpath);
        String atozTo = ImageStore.getPatientFolder(patientTo,atoZpath);
        //We need to test patfields before doing anything else because the user may wish to cancel and abort the merge.
        PatField[] patToFields = PatFields.refresh(patTo);
        PatField[] patFromFields = PatFields.refresh(patFrom);
        for (int i = 0;i < patFromFields.Length;i++)
        {
            boolean hasMatch = false;
            for (int j = 0;j < patToFields.Length;j++)
            {
                //Check patient fields that are the same to see if they have different values.
                if (patFromFields[i].FieldName == patToFields[j].FieldName)
                {
                    hasMatch = true;
                    if (patFromFields[i].FieldValue != patToFields[j].FieldValue)
                    {
                        //Get input from user on which value to use.
                        DialogResult result = MessageBox.Show("The two patients being merged have different values set for the patient field:\r\n\"" + patFromFields[i].FieldName + "\"\r\n\r\n" + "The merge into patient has the value: \"" + patToFields[j].FieldValue + "\"\r\n" + "The merge from patient has the value: \"" + patFromFields[i].FieldValue + "\"\r\n\r\n" + "Would you like to overwrite the merge into value with the merge from value?\r\n(Cancel will abort the merge)", "Warning", MessageBoxButtons.YesNoCancel);
                        if (result == DialogResult.Yes)
                        {
                            //User chose to use the merge from patient field info.
                            patFromFields[i].PatNum = patTo;
                            PatFields.Update(patFromFields[i]);
                            PatFields.Delete(patToFields[j]);
                        }
                        else if (result == DialogResult.Cancel)
                        {
                            return false;
                        }
                          
                    }
                     
                }
                 
            }
            if (!hasMatch)
            {
                //The patient field does not exist in the merge into account.
                patFromFields[i].PatNum = patTo;
                PatFields.Update(patFromFields[i]);
            }
             
        }
        //Move the patient documents within the 'patFrom' A to Z folder to the 'patTo' A to Z folder.
        //We have to be careful here of documents with the same name. We have to rename such documents
        //so that no documents are overwritten/lost.
        String[] fromFiles = Directory.GetFiles(atozFrom);
        for (int i = 0;i < fromFiles.Length;i++)
        {
            String fileName = Path.GetFileName(fromFiles[i]);
            String destFilePath = CodeBase.ODFileUtils.combinePaths(atozTo,fileName);
            if (File.Exists(destFilePath))
            {
                //The file being copied has the same name as a possibly different file within the destination a to z folder.
                //We need to copy the file under a unique file name and then make sure to update the document table to reflect
                //the change.
                destFilePath = CodeBase.ODFileUtils.CombinePaths(atozTo, patientFrom.PatNum.ToString() + "_" + fileName);
                while (File.Exists(destFilePath))
                {
                    destFilePath = CodeBase.ODFileUtils.CombinePaths(atozTo, patientFrom.PatNum.ToString() + "_" + DateTime.Now.ToString("yyyy_MM_dd_hh_mm_ss") + "_" + fileName);
                }
                command = "UPDATE document " + "SET FileName='" + POut.String(Path.GetFileName(destFilePath)) + "' " + "WHERE FileName='" + POut.string(fileName) + "' AND PatNum=" + POut.long(patFrom) + " " + DbHelper.limitAnd(1);
                Db.nonQ(command);
            }
             
            File.Copy(fromFiles[i], destFilePath);
            try
            {
                //Will throw exception if file already exists.
                File.Delete(fromFiles[i]);
            }
            catch (Exception __dummyCatchVar4)
            {
            }
        
        }
        //If we were unable to delete the file then it is probably because someone has the document open currently.
        //Just skip deleting the file. This means that occasionally there will be an extra file in their backup
        //which is just clutter but at least the merge is guaranteed this way.
        //If the 'patFrom' had any ties to guardians, they should be deleted to prevent duplicate entries.
        command = "DELETE FROM guardian" + " WHERE PatNumChild=" + POut.long(patFrom) + " OR PatNumGuardian=" + POut.long(patFrom);
        Db.nonQ(command);
        //Update all guarantor foreign keys to change them from 'patFrom' to
        //the guarantor of 'patTo'. This will effectively move all 'patFrom' family members
        //to the family defined by 'patTo' in the case that 'patFrom' is a guarantor. If
        //'patFrom' is not a guarantor, then this command will have no effect and is
        //thus safe to always be run.
        command = "UPDATE patient " + "SET Guarantor=" + POut.long(patientTo.Guarantor) + " " + "WHERE Guarantor=" + POut.long(patFrom);
        Db.nonQ(command);
        for (int i = 0;i < patNumForeignKeys.Length;i++)
        {
            //At this point, the 'patFrom' is a regular patient and is absoloutely not a guarantor.
            //Now modify all PatNum foreign keys from 'patFrom' to 'patTo' to complete the majority of the
            //merge of the records between the two accounts.
            String[] tableAndKeyName = patNumForeignKeys[i].Split(new char[]{ '.' });
            command = "UPDATE " + tableAndKeyName[0] + " SET " + tableAndKeyName[1] + "=" + POut.long(patTo) + " WHERE " + tableAndKeyName[1] + "=" + POut.long(patFrom);
            Db.nonQ(command);
        }
        //We have to move over the tasks belonging to the 'patFrom' patient in a seperate step because
        //the KeyNum field of the task table might be a foreign key to something other than a patnum,
        //including possibly an appointment number.
        command = "UPDATE task " + "SET KeyNum=" + POut.long(patTo) + " " + "WHERE KeyNum=" + POut.long(patFrom) + " AND ObjectType=" + (((Enum)TaskObjectType.Patient).ordinal());
        Db.nonQ(command);
        //Mark the patient where data was pulled from as archived unless the patient is already marked as deceased.
        //We need to have the patient marked either archived or deceased so that it is hidden by default, and
        //we also need the customer to be able to access the account again in case a particular table gets missed
        //in the merge tool after an update to Open Dental. This will allow our customers to remerge the missing
        //data after a bug fix is released.
        command = "UPDATE patient " + "SET PatStatus=" + (((Enum)PatientStatus.Archived).ordinal()) + " " + "WHERE PatNum=" + POut.long(patFrom) + " " + "AND PatStatus<>" + (((Enum)PatientStatus.Deceased).ordinal()) + " " + DbHelper.limitAnd(1);
        Db.nonQ(command);
        for (int i = 0;i < Referrals.getList().Length;i++)
        {
            //This updates the referrals with the new patient information from the merge.
            if (Referrals.getList()[i].PatNum == patFrom)
            {
                //Referrals.Cur=Referrals.List[i];
                Referrals.getList()[i].PatNum = patientTo.PatNum;
                Referrals.getList()[i].LName = patientTo.LName;
                Referrals.getList()[i].FName = patientTo.FName;
                Referrals.getList()[i].MName = patientTo.MiddleI;
                Referrals.getList()[i].Address = patientTo.Address;
                Referrals.getList()[i].Address2 = patientTo.Address2;
                Referrals.getList()[i].City = patientTo.City;
                Referrals.getList()[i].ST = patientTo.State;
                Referrals.getList()[i].SSN = patientTo.SSN;
                Referrals.getList()[i].Zip = patientTo.Zip;
                Referrals.getList()[i].Telephone = TelephoneNumbers.formatNumbersExactTen(patientTo.HmPhone);
                Referrals.getList()[i].EMail = patientTo.Email;
                Referrals.Update(Referrals.getList()[i]);
                Referrals.refreshCache();
                break;
            }
             
        }
        return true;
    }

    /**
    * LName, 'Preferred' FName M
    */
    public static String getNameLF(String LName, String FName, String Preferred, String MiddleI) throws Exception {
        //No need to check RemotingRole; no call to db.
        String retVal = "";
        //if(Title!=""){
        //	retVal+=Title+" ";
        //}
        retVal += LName + ", ";
        if (!StringSupport.equals(Preferred, ""))
        {
            retVal += "'" + Preferred + "' ";
        }
         
        retVal += FName;
        if (!StringSupport.equals(MiddleI, ""))
        {
            retVal += " " + MiddleI;
        }
         
        return retVal;
    }

    /**
    * FName 'Preferred' M LName
    */
    public static String getNameFL(String LName, String FName, String Preferred, String MiddleI) throws Exception {
        //No need to check RemotingRole; no call to db.
        String retVal = "";
        //if(Title!="") {
        //retVal+=Title+" ";
        //}
        retVal += FName + " ";
        if (!StringSupport.equals(Preferred, ""))
        {
            retVal += "'" + Preferred + "' ";
        }
         
        if (!StringSupport.equals(MiddleI, ""))
        {
            retVal += MiddleI + " ";
        }
         
        retVal += LName;
        return retVal;
    }

    /**
    * FName M LName
    */
    public static String getNameFLnoPref(String LName, String FName, String MiddleI) throws Exception {
        //No need to check RemotingRole; no call to db.
        String retVal = "";
        retVal += FName + " ";
        if (!StringSupport.equals(MiddleI, ""))
        {
            retVal += MiddleI + " ";
        }
         
        retVal += LName;
        return retVal;
    }

    /**
    * FName/Preferred LName
    */
    public static String getNameFirstOrPrefL(String LName, String FName, String Preferred) throws Exception {
        //No need to check RemotingRole; no call to db.
        String retVal = "";
        if (StringSupport.equals(Preferred, ""))
        {
            retVal += FName + " ";
        }
        else
        {
            retVal += Preferred + " ";
        } 
        retVal += LName;
        return retVal;
    }

    /**
    * FName/Preferred M. LName
    */
    public static String getNameFirstOrPrefML(String LName, String FName, String Preferred, String MiddleI) throws Exception {
        //No need to check RemotingRole; no call to db.
        String retVal = "";
        if (StringSupport.equals(Preferred, ""))
        {
            retVal += FName + " ";
        }
        else
        {
            retVal += Preferred + " ";
        } 
        if (!StringSupport.equals(MiddleI, ""))
        {
            retVal += MiddleI + ". ";
        }
         
        retVal += LName;
        return retVal;
    }

    /**
    * Title FName M LName
    */
    public static String getNameFLFormal(String LName, String FName, String MiddleI, String Title) throws Exception {
        //No need to check RemotingRole; no call to db.
        String retVal = "";
        if (!StringSupport.equals(Title, ""))
        {
            retVal += Title + " ";
        }
         
        retVal += FName + " " + MiddleI + " " + LName;
        return retVal;
    }

    /**
    * Includes preferred.
    */
    public static String getNameFirst(String FName, String Preferred) throws Exception {
        //No need to check RemotingRole; no call to db.
        String retVal = FName;
        if (!StringSupport.equals(Preferred, ""))
        {
            retVal += " '" + Preferred + "'";
        }
         
        return retVal;
    }

    /**
    * Returns preferred name if one exists, otherwise returns first name.
    */
    public static String getNameFirstOrPreferred(String FName, String Preferred) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (!StringSupport.equals(Preferred, ""))
        {
            return Preferred;
        }
         
        return FName;
    }

    /**
    * Dear __.  Does not include the "Dear" or the comma.
    */
    public static String getSalutation(String Salutation, String Preferred, String FName) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (!StringSupport.equals(Salutation, ""))
        {
            return Salutation;
        }
         
        if (!StringSupport.equals(Preferred, ""))
        {
            return Preferred;
        }
         
        return FName;
    }

    /**
    * Result will be multiline.
    */
    public static String getAddressFull(String address, String address2, String city, String state, String zip) throws Exception {
        String retVal = address;
        if (!StringSupport.equals(address2, ""))
        {
            retVal += "\r\n" + address2;
        }
         
        retVal += "\r\n" + city + ", " + state + " " + zip;
        return retVal;
    }

    /**
    * Change preferred provider for all patients with provNumFrom to provNumTo.
    */
    public static void changeProviders(long provNumFrom, long provNumTo) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), provNumFrom, provNumTo);
        }
         
        String command = "UPDATE patient " + "SET PriProv = '" + provNumTo + "' " + "WHERE PriProv = '" + provNumFrom + "'";
        Db.nonQ(command);
    }

    /**
    * Gets all patients whose primary provider PriProv is in the list provNums.
    */
    public static DataTable getPatsByPriProvs(List<long> provNums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<DataTable>GetObject(MethodBase.GetCurrentMethod(), provNums);
        }
         
        if (provNums.Count == 0)
        {
            return null;
        }
         
        String providers = "";
        for (int i = 0;i < provNums.Count;i++)
        {
            providers += provNums[i].ToString();
            if (i < provNums.Count - 1)
            {
                providers += ",";
            }
             
        }
        String command = "SELECT PatNum,PriProv FROM patient WHERE PriProv IN (" + providers + ")";
        return Db.getTable(command);
    }

    /**
    * Find the most used provider for a single patient. Bias towards the most recently used provider if they have done an equal number of procedures.
    */
    public static long reassignProvGetMostUsed(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetLong(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT ProvNum,MAX(ProcDate) MaxProcDate,COUNT(ProvNum) ProcCount " + "FROM procedurelog " + "WHERE PatNum=" + POut.long(patNum) + " " + "AND ProcStatus=" + POut.int(((Enum)OpenDentBusiness.ProcStat.C).ordinal()) + " " + "GROUP BY ProvNum";
        DataTable table = Db.getTable(command);
        long newProv = 0;
        int mostVisits = 0;
        DateTime maxProcDate = new DateTime();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            //loop through providers
            if (PIn.Int(table.Rows[i]["ProcCount"].ToString()) > mostVisits)
            {
                //New leader for most visits.
                mostVisits = PIn.Int(table.Rows[i]["ProcCount"].ToString());
                maxProcDate = PIn.DateT(table.Rows[i]["MaxProcDate"].ToString());
                newProv = PIn.Long(table.Rows[i]["ProvNum"].ToString());
            }
            else if (PIn.Int(table.Rows[i]["ProcCount"].ToString()) == mostVisits)
            {
                //Tie for most visits, use MaxProcDate as a tie breaker.
                if (PIn.DateT(table.Rows[i]["MaxProcDate"].ToString()) > maxProcDate)
                {
                    //mostVisits same as before
                    maxProcDate = PIn.DateT(table.Rows[i]["MaxProcDate"].ToString());
                    newProv = PIn.Long(table.Rows[i]["ProvNum"].ToString());
                }
                 
            }
              
        }
        return newProv;
    }

    /**
    * Change preferred provider PriProv to provNum for patient with PatNum=patNum.
    */
    public static void reassignProv(long patNum, long provNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patNum, provNum);
        }
         
        String command = "UPDATE patient " + "SET PriProv = '" + POut.long(provNum) + "' " + "WHERE PatNum = '" + POut.long(patNum) + "'";
        Db.nonQ(command);
    }

    /**
    * Gets the number of patients with unknown Zip.
    */
    public static int getZipUnknown(DateTime dateFrom, DateTime dateTo) throws Exception {
        //Does not start with five numbers
        String command = "SELECT COUNT(*) " + "FROM patient " + "WHERE " + DbHelper.regexp("Zip","^[0-9]{5}",false) + " " + "AND PatNum IN ( " + "SELECT DISTINCT PatNum FROM procedurelog " + "WHERE ProcStatus=" + POut.int(((Enum)OpenDentBusiness.ProcStat.C).ordinal()) + " " + "AND DateEntryC >= " + POut.date(dateFrom) + " " + "AND DateEntryC <= " + POut.date(dateTo) + ") " + "AND Birthdate<=CURDATE() " + "AND Birthdate>SUBDATE(CURDATE(),INTERVAL 200 YEAR) ";
        return PIn.int(Db.getCount(command));
    }

    //Birthday not in the future (at least 0 years old)
    //Younger than 200 years old
    /**
    * Gets the number of qualified patients (having a completed procedure within the given time frame) in zip codes with less than 9 other qualified patients in that same zip code.
    */
    public static int getZipOther(DateTime dateFrom, DateTime dateTo) throws Exception {
        //Column headings Zip_Code and Patients are provided by the USD 2010 Manual.
        //Starts with five numbers
        String command = "SELECT SUM(Patients) FROM " + "(SELECT SUBSTR(Zip,1,5) Zip_Code,COUNT(*) Patients " + "FROM patient " + "WHERE " + DbHelper.regexp("Zip","^[0-9]{5}") + " " + "AND PatNum IN ( " + "SELECT DISTINCT PatNum FROM procedurelog " + "WHERE ProcStatus=" + POut.int(((Enum)OpenDentBusiness.ProcStat.C).ordinal()) + " " + "AND DateEntryC >= " + POut.date(dateFrom) + " " + "AND DateEntryC <= " + POut.date(dateTo) + ") " + "AND Birthdate<=CURDATE() " + "AND Birthdate>SUBDATE(CURDATE(),INTERVAL 200 YEAR) " + "GROUP BY Zip " + "HAVING COUNT(*) < 10) patzip";
        return PIn.int(Db.getCount(command));
    }

    //Birthday not in the future (at least 0 years old)
    //Younger than 200 years old
    //Has less than 10 patients in that zip code for the given time frame.
    /**
    * Gets the total number of patients with completed procedures between dateFrom and dateTo. Also checks for age between 0 and 200.
    */
    public static int getPatCount(DateTime dateFrom, DateTime dateTo) throws Exception {
        String command = "SELECT COUNT(*) " + "FROM patient " + "WHERE PatNum IN ( " + "SELECT DISTINCT PatNum FROM procedurelog " + "WHERE ProcStatus=" + POut.int(((Enum)OpenDentBusiness.ProcStat.C).ordinal()) + " " + "AND DateEntryC >= " + POut.date(dateFrom) + " " + "AND DateEntryC <= " + POut.date(dateTo) + ") " + "AND Birthdate<=CURDATE() " + "AND Birthdate>SUBDATE(CURDATE(),INTERVAL 200 YEAR) ";
        return PIn.int(Db.getCount(command));
    }

    //Birthday not in the future (at least 0 years old)
    //Younger than 200 years old
    /**
    * Gets the total number of patients with completed procedures between dateFrom and dateTo who are at least agelow and strictly younger than agehigh.
    */
    public static int getAgeGenderCount(int agelow, int agehigh, PatientGender gender, DateTime dateFrom, DateTime dateTo) throws Exception {
        boolean male = true;
        //Since all the numbers must add up to equal, we count unknown and other genders as female.
        if (gender != 0)
        {
            male = false;
        }
         
        //Born before this date
        String command = "SELECT COUNT(*) " + "FROM patient pat " + "WHERE PatNum IN ( " + "SELECT DISTINCT PatNum FROM procedurelog " + "WHERE ProcStatus=" + POut.int(((Enum)OpenDentBusiness.ProcStat.C).ordinal()) + " " + "AND DateEntryC >= " + POut.date(dateFrom) + " " + "AND DateEntryC <= " + POut.date(dateTo) + ") " + "AND Gender" + (male ? "=0" : "!=0") + " " + "AND Birthdate<=SUBDATE(CURDATE(),INTERVAL " + agelow + " YEAR) " + "AND Birthdate>SUBDATE(CURDATE(),INTERVAL " + agehigh + " YEAR)";
        return PIn.int(Db.getCount(command));
    }

    //Born after this date
    /**
    * Returns a list of patients belonging to the SuperFamily
    */
    public static List<Patient> getBySuperFamily(long SuperFamilyNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Patient>>GetObject(MethodBase.GetCurrentMethod(), SuperFamilyNum);
        }
         
        if (SuperFamilyNum == 0)
        {
            return new List<Patient>();
        }
         
        //return empty list
        String command = "SELECT * FROM patient WHERE SuperFamily=" + POut.long(SuperFamilyNum);
        return Crud.PatientCrud.TableToList(Db.getTable(command));
    }

    /**
    * Returns a list of patients that are the guarantors for the patients in the Super Family
    */
    public static List<Patient> getSuperFamilyGuarantors(long SuperFamilyNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Patient>>GetObject(MethodBase.GetCurrentMethod(), SuperFamilyNum);
        }
         
        if (SuperFamilyNum == 0)
        {
            return new List<Patient>();
        }
         
        //return empty list
        //Should also work in Oracle.
        //this query was taking 2.5 seconds on a large database
        //string command = "SELECT DISTINCT * FROM patient WHERE PatNum IN (SELECT Guarantor FROM patient WHERE SuperFamily="+POut.Long(SuperFamilyNum)+") "
        //	+"AND PatStatus!="+POut.Int((int)PatientStatus.Deleted);
        //optimized to 0.001 second runtime on same db
        String command = "SELECT DISTINCT * FROM patient WHERE SuperFamily=" + POut.long(SuperFamilyNum) + " AND PatStatus!=" + POut.int(((Enum)PatientStatus.Deleted).ordinal()) + " AND PatNum=Guarantor";
        return Crud.PatientCrud.TableToList(Db.getTable(command));
    }

    public static void assignToSuperfamily(long guarantor, long superFamilyNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), guarantor, superFamilyNum);
        }
         
        String command = "UPDATE patient SET SuperFamily=" + POut.long(superFamilyNum) + " WHERE Guarantor=" + POut.long(guarantor);
        Db.nonQ(command);
    }

    public static void moveSuperFamily(long oldSuperFamilyNum, long newSuperFamilyNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), oldSuperFamilyNum, newSuperFamilyNum);
        }
         
        if (oldSuperFamilyNum == 0)
        {
            return ;
        }
         
        String command = "UPDATE patient SET SuperFamily=" + newSuperFamilyNum + " WHERE SuperFamily=" + oldSuperFamilyNum;
        Db.nonQ(command);
    }

    public static void disbandSuperFamily(long SuperFamilyNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), SuperFamilyNum);
        }
         
        if (SuperFamilyNum == 0)
        {
            return ;
        }
         
        String command = "UPDATE patient SET SuperFamily=0 WHERE SuperFamily=" + POut.long(SuperFamilyNum);
        Db.nonQ(command);
    }

    public static List<Patient> getPatsForScreenGroup(long screenGroupNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Patient>>GetObject(MethodBase.GetCurrentMethod(), screenGroupNum);
        }
         
        if (screenGroupNum == 0)
        {
            return new List<Patient>();
        }
         
        String command = "SELECT * FROM patient WHERE PatNum IN (SELECT PatNum FROM screenpat WHERE ScreenGroupNum=" + POut.long(screenGroupNum) + ")";
        return Crud.PatientCrud.SelectMany(command);
    }

    /**
    * Get a list of patients for FormEhrPatientExport. If provNum, clinicNum, or siteNum are =0 get all.
    */
    public static DataTable getExportList(long patNum, String firstName, String lastName, long provNum, long clinicNum, long siteNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), firstName, lastName, provNum, clinicNum, siteNum);
        }
         
        String command = "SELECT patient.PatNum, patient.FName, patient.LName, provider.Abbr AS Provider, clinic.Description AS Clinic, site.Description AS Site " + "FROM patient " + "INNER JOIN provider ON patient.PriProv=provider.ProvNum " + "LEFT JOIN clinic ON patient.ClinicNum=clinic.ClinicNum " + "LEFT JOIN site ON patient.SiteNum=site.SiteNum " + "WHERE patient.PatStatus=0 ";
        if (patNum != 0)
        {
            command += "AND patient.PatNum LIKE '%" + POut.long(patNum) + "%' ";
        }
         
        if (!StringSupport.equals(firstName, ""))
        {
            command += "AND patient.FName LIKE '%" + POut.string(firstName) + "%' ";
        }
         
        if (!StringSupport.equals(lastName, ""))
        {
            command += "AND patient.LName LIKE '%" + POut.string(lastName) + "%' ";
        }
         
        if (provNum > 0)
        {
            command += "AND provider.ProvNum = " + POut.long(provNum) + " ";
        }
         
        if (clinicNum > 0)
        {
            command += "AND clinic.ClinicNum = " + POut.long(clinicNum) + " ";
        }
         
        if (siteNum > 0)
        {
            command += "AND site.SiteNum = " + POut.long(siteNum) + " ";
        }
         
        command += "ORDER BY patient.LName,patient.FName ";
        return (Db.getTable(command));
    }

}


