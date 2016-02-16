//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:47 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.CommItemTypeAuto;
import OpenDentBusiness.Commlogs;
import OpenDentBusiness.ContactMethod;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.DeletedObjects;
import OpenDentBusiness.DeletedObjectType;
import OpenDentBusiness.Interval;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Recall;
import OpenDentBusiness.RecallComparer;
import OpenDentBusiness.RecallListShowNumberReminders;
import OpenDentBusiness.RecallListSort;
import OpenDentBusiness.Recalls;
import OpenDentBusiness.RecallTriggers;
import OpenDentBusiness.RecallType;
import OpenDentBusiness.RecallTypes;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class Recalls   
{
    /**
    * Gets all recalls for the supplied patients, usually a family or single pat.  Result might have a length of zero.  Each recall will also have the DateScheduled filled by pulling that info from other tables.
    */
    public static List<Recall> getList(List<long> patNums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Recall>>GetObject(MethodBase.GetCurrentMethod(), patNums);
        }
         
        String wherePats = "";
        for (int i = 0;i < patNums.Count;i++)
        {
            if (i != 0)
            {
                wherePats += " OR ";
            }
             
            wherePats += "PatNum=" + patNums[i].ToString();
        }
        String command = "SELECT * FROM recall WHERE " + wherePats;
        return Crud.RecallCrud.SelectMany(command);
    }

    public static List<Recall> getList(long patNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<long> patNums = new List<long>();
        patNums.Add(patNum);
        return GetList(patNums);
    }

    /**
    * 
    */
    public static List<Recall> getList(List<Patient> patients) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<long> patNums = new List<long>();
        for (int i = 0;i < patients.Count;i++)
        {
            patNums.Add(patients[i].PatNum);
        }
        return GetList(patNums);
    }

    public static Recall getRecall(long recallNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Recall>GetObject(MethodBase.GetCurrentMethod(), recallNum);
        }
         
        return Crud.RecallCrud.SelectOne(recallNum);
    }

    /**
    * Will return a recall or null.
    */
    public static Recall getRecallProphyOrPerio(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Recall>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM recall WHERE PatNum=" + POut.long(patNum) + " AND (RecallTypeNum=" + RecallTypes.getProphyType() + " OR RecallTypeNum=" + RecallTypes.getPerioType() + ")";
        return Crud.RecallCrud.SelectOne(command);
    }

    public static List<Recall> getChangedSince(DateTime changedSince) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Recall>>GetObject(MethodBase.GetCurrentMethod(), changedSince);
        }
         
        String command = "SELECT * FROM recall WHERE DateTStamp > " + POut.dateT(changedSince);
        return Crud.RecallCrud.SelectMany(command);
    }

    /**
    * Only used in FormRecallList to get a list of patients with recall.  Supply a date range, using min and max values if user left blank.  If provNum=0, then it will get all provnums.  It looks for both provider match in either PriProv or SecProv.
    */
    public static DataTable getRecallList(DateTime fromDate, DateTime toDate, boolean groupByFamilies, long provNum, long clinicNum, long siteNum, RecallListSort sortBy, RecallListShowNumberReminders showReminders, List<long> excludePatNums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), fromDate, toDate, groupByFamilies, provNum, clinicNum, siteNum, sortBy, showReminders, excludePatNums);
        }
         
        DataTable table = new DataTable();
        DataRow row = new DataRow();
        //columns that start with lowercase are altered for display rather than being raw data.
        table.Columns.Add("age");
        table.Columns.Add("billingType");
        table.Columns.Add("contactMethod");
        //text representation for display
        table.Columns.Add("ClinicNum");
        table.Columns.Add("dateLastReminder");
        table.Columns.Add("DateDue", DateTime.class);
        table.Columns.Add("dueDate");
        //blank if minVal
        table.Columns.Add("Email");
        table.Columns.Add("FName");
        table.Columns.Add("Guarantor");
        table.Columns.Add("guarFName");
        table.Columns.Add("guarLName");
        table.Columns.Add("LName");
        table.Columns.Add("maxDateDue", DateTime.class);
        table.Columns.Add("Note");
        table.Columns.Add("numberOfReminders");
        table.Columns.Add("patientName");
        table.Columns.Add("PatNum");
        table.Columns.Add("PreferRecallMethod");
        table.Columns.Add("recallInterval");
        table.Columns.Add("RecallNum");
        table.Columns.Add("recallType");
        table.Columns.Add("status");
        List<DataRow> rows = new List<DataRow>();
        String command = new String();
        command = "SELECT patguar.BalTotal,patient.BillingType,patient.Birthdate,recall.DateDue,patient.ClinicNum,MAX(CommDateTime) \"_dateLastReminder\",\r\n" + 
        "\t\t\t\tDisableUntilBalance,DisableUntilDate,\r\n" + 
        "\t\t\t\tpatient.Email,patguar.Email \"_guarEmail\",patguar.FName \"_guarFName\",\r\n" + 
        "\t\t\t\tpatguar.LName \"_guarLName\",patient.FName,\r\n" + 
        "\t\t\t\tpatient.Guarantor,patient.HmPhone,patguar.InsEst,patient.LName,recall.Note,\r\n" + 
        "\t\t\t\tCOUNT(commlog.CommlogNum) \"_numberOfReminders\",\r\n" + 
        "\t\t\t\trecall.PatNum,patient.PreferRecallMethod,patient.Preferred,\r\n" + 
        "\t\t\t\trecall.RecallInterval,recall.RecallNum,recall.RecallStatus,\r\n" + 
        "\t\t\t\trecalltype.Description \"_recalltype\",patient.WirelessPhone,patient.WkPhone\r\n" + 
        "\t\t\t\tFROM recall\r\n" + 
        "\t\t\t\tLEFT JOIN patient ON recall.PatNum=patient.PatNum\r\n" + 
        "\t\t\t\tLEFT JOIN patient patguar ON patient.Guarantor=patguar.PatNum\r\n" + 
        "\t\t\t\tLEFT JOIN recalltype ON recall.RecallTypeNum=recalltype.RecallTypeNum\r\n" + 
        "\t\t\t\tLEFT JOIN commlog ON commlog.PatNum=recall.PatNum\r\n" + 
        "\t\t\t\tAND CommType=" + POut.long(Commlogs.getTypeAuto(CommItemTypeAuto.RECALL)) + " " + "AND CommDateTime > recall.DatePrevious " + "WHERE patient.patstatus=0 ";
        //We need to make commlog more restrictive for situations where a manually added recall has no date previous,
        if (provNum > 0)
        {
            command += "AND (patient.PriProv=" + POut.long(provNum) + " " + "OR patient.SecProv=" + POut.long(provNum) + ") ";
        }
         
        if (clinicNum > 0)
        {
            command += "AND patient.ClinicNum=" + POut.long(clinicNum) + " ";
        }
         
        if (siteNum > 0)
        {
            command += "AND patient.SiteNum=" + POut.long(siteNum) + " ";
        }
         
        command += "AND recall.DateDue >= " + POut.date(fromDate) + " " + "AND recall.DateDue <= " + POut.date(toDate) + " " + "AND recall.IsDisabled = 0 " + "AND recall.RecallTypeNum IN(" + PrefC.getString(PrefName.RecallTypesShowingInList) + ") ";
        if (PrefC.getBool(PrefName.RecallExcludeIfAnyFutureAppt))
        {
            String datesql = "CURDATE()";
            if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
            {
                datesql = "(SELECT CURRENT_DATE FROM dual)";
            }
             
            command += "AND NOT EXISTS(SELECT * FROM appointment WHERE\r\n" + 
            "\t\t\t\t\tappointment.PatNum=recall.PatNum AND appointment.AptDateTime > " + datesql + " AND appointment.AptStatus IN(1,4)) ";
        }
        else
        {
            //early this morning
            //scheduled,ASAP
            command += "AND recall.DateScheduled='0001-01-01' ";
        } 
        //Only show rows where no future recall appointment.
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            command += "GROUP BY recall.PatNum,recall.RecallTypeNum ";
        }
        else
        {
            //GROUP BY RecallTypeNum forces both manual and prophy types to show independently.
            command += "GROUP BY  patguar.BalTotal,patient.BillingType,\r\n" + 
            "\t\t\t\t\tpatient.Birthdate,recall.DateDue,\r\n" + 
            "\t\t\t\t\tDisableUntilBalance,DisableUntilDate,\r\n" + 
            "\t\t\t\t\tpatient.Email,patguar.Email,patguar.FName,\r\n" + 
            "\t\t\t\t\tpatguar.LName,patient.FName,\r\n" + 
            "\t\t\t\t\tpatient.Guarantor,patient.HmPhone,patguar.InsEst,patient.LName,recall.Note,\r\n" + 
            "\t\t\t\t\trecall.PatNum,patient.PreferRecallMethod,patient.Preferred,\r\n" + 
            "\t\t\t\t\trecall.RecallInterval,recall.RecallNum,recall.RecallStatus,\r\n" + 
            "\t\t\t\t\trecalltype.Description,patient.WirelessPhone,patient.WkPhone,recall.RecallTypeNum ";
        } 
        DataTable rawtable = Db.getTable(command);
        DateTime dateDue = new DateTime();
        DateTime dateRemind = new DateTime();
        Interval interv = new Interval();
        Patient pat;
        ContactMethod contmeth = ContactMethod.None;
        int numberOfReminders = new int();
        int maxNumberReminders = (int)PrefC.getLong(PrefName.RecallMaxNumberReminders);
        long patNum = new long();
        DateTime disableUntilDate = new DateTime();
        double disableUntilBalance = new double();
        double familyBalance = new double();
        for (int i = 0;i < rawtable.Rows.Count;i++)
        {
            patNum = PIn.Long(rawtable.Rows[i]["PatNum"].ToString());
            dateDue = PIn.Date(rawtable.Rows[i]["DateDue"].ToString());
            dateRemind = PIn.Date(rawtable.Rows[i]["_dateLastReminder"].ToString());
            numberOfReminders = PIn.Int(rawtable.Rows[i]["_numberOfReminders"].ToString());
            if (numberOfReminders == 0)
            {
            }
            else //always show
            if (numberOfReminders == 1)
            {
                if (PrefC.getLong(PrefName.RecallShowIfDaysFirstReminder) == -1)
                {
                    continue;
                }
                 
                if (dateRemind.AddDays(PrefC.getLong(PrefName.RecallShowIfDaysFirstReminder)).Date > DateTime.Today)
                {
                    continue;
                }
                 
            }
            else
            {
                //> toDate
                //|| dateRemind.AddDays(PrefC.GetLong(PrefName.RecallShowIfDaysFirstReminder)) < fromDate)
                //2 or more reminders
                if (PrefC.getLong(PrefName.RecallShowIfDaysSecondReminder) == -1)
                {
                    continue;
                }
                 
                if (dateRemind.AddDays(PrefC.getLong(PrefName.RecallShowIfDaysSecondReminder)).Date > DateTime.Today)
                {
                    continue;
                }
                 
            }  
            //> toDate
            //|| dateRemind.AddDays(PrefC.GetLong(PrefName.RecallShowIfDaysSecondReminder)) < fromDate)
            if (maxNumberReminders != -1 && numberOfReminders > maxNumberReminders)
            {
                continue;
            }
             
            if (showReminders == RecallListShowNumberReminders.Zero)
            {
                if (numberOfReminders != 0)
                {
                    continue;
                }
                 
            }
            else if (showReminders == RecallListShowNumberReminders.One)
            {
                if (numberOfReminders != 1)
                {
                    continue;
                }
                 
            }
            else if (showReminders == RecallListShowNumberReminders.Two)
            {
                if (numberOfReminders != 2)
                {
                    continue;
                }
                 
            }
            else if (showReminders == RecallListShowNumberReminders.Three)
            {
                if (numberOfReminders != 3)
                {
                    continue;
                }
                 
            }
            else if (showReminders == RecallListShowNumberReminders.Four)
            {
                if (numberOfReminders != 4)
                {
                    continue;
                }
                 
            }
            else if (showReminders == RecallListShowNumberReminders.Five)
            {
                if (numberOfReminders != 5)
                {
                    continue;
                }
                 
            }
            else if (showReminders == RecallListShowNumberReminders.SixPlus)
            {
                if (numberOfReminders < 6)
                {
                    continue;
                }
                 
            }
                   
            if (excludePatNums.Contains(patNum))
            {
                continue;
            }
             
            disableUntilDate = PIn.Date(rawtable.Rows[i]["DisableUntilDate"].ToString());
            if (disableUntilDate.Year > 1880 && disableUntilDate > DateTime.Today)
            {
                continue;
            }
             
            disableUntilBalance = PIn.Double(rawtable.Rows[i]["DisableUntilBalance"].ToString());
            if (disableUntilBalance > 0)
            {
                familyBalance = PIn.Double(rawtable.Rows[i]["BalTotal"].ToString());
                if (!PrefC.getBool(PrefName.BalancesDontSubtractIns))
                {
                    //typical
                    familyBalance -= PIn.Double(rawtable.Rows[i]["InsEst"].ToString());
                }
                 
                if (familyBalance > disableUntilBalance)
                {
                    continue;
                }
                 
            }
             
            row = table.NewRow();
            row["age"] = Patients.DateToAge(PIn.Date(rawtable.Rows[i]["Birthdate"].ToString())).ToString();
            //we don't care about m/y.
            row["billingType"] = DefC.GetName(DefCat.BillingTypes, PIn.Long(rawtable.Rows[i]["BillingType"].ToString()));
            row["ClinicNum"] = PIn.Long(rawtable.Rows[i]["ClinicNum"].ToString());
            contmeth = (ContactMethod)PIn.Long(rawtable.Rows[i]["PreferRecallMethod"].ToString());
            if (contmeth == ContactMethod.None)
            {
                if (!PrefC.getBool(PrefName.RecallUseEmailIfHasEmailAddress))
                {
                    //if user only wants to use email if contact method is email (it isn't for this patient)
                    row["contactMethod"] = Lans.g("FormRecallList","Hm:") + rawtable.Rows[i]["HmPhone"].ToString();
                }
                else
                {
                    //if user wants to use email if there is an email address
                    if (groupByFamilies)
                    {
                        if (!StringSupport.equals(rawtable.Rows[i]["_guarEmail"].ToString(), ""))
                        {
                            //since there is an email,
                            row["contactMethod"] = rawtable.Rows[i]["_guarEmail"].ToString();
                        }
                        else
                        {
                            row["contactMethod"] = Lans.g("FormRecallList","Hm:") + rawtable.Rows[i]["HmPhone"].ToString();
                        } 
                    }
                    else
                    {
                        if (!StringSupport.equals(rawtable.Rows[i]["Email"].ToString(), ""))
                        {
                            //since there is an email,
                            row["contactMethod"] = rawtable.Rows[i]["Email"].ToString();
                        }
                        else
                        {
                            row["contactMethod"] = Lans.g("FormRecallList","Hm:") + rawtable.Rows[i]["HmPhone"].ToString();
                        } 
                    } 
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
                if (groupByFamilies)
                {
                    //always use guarantor email
                    row["contactMethod"] = rawtable.Rows[i]["_guarEmail"].ToString();
                }
                else
                {
                    row["contactMethod"] = rawtable.Rows[i]["Email"].ToString();
                } 
            }
             
            if (contmeth == ContactMethod.Mail)
            {
                row["contactMethod"] = Lans.g("FormRecallList","Mail");
            }
             
            if (contmeth == ContactMethod.DoNotCall || contmeth == ContactMethod.SeeNotes)
            {
                row["contactMethod"] = Lans.g("enumContactMethod", contmeth.ToString());
            }
             
            if (dateRemind.Year < 1880)
            {
                row["dateLastReminder"] = "";
            }
            else
            {
                row["dateLastReminder"] = dateRemind.ToShortDateString();
            } 
            row["DateDue"] = dateDue;
            if (dateDue.Year < 1880)
            {
                row["dueDate"] = "";
            }
            else
            {
                row["dueDate"] = dateDue.ToShortDateString();
            } 
            if (groupByFamilies)
            {
                row["Email"] = rawtable.Rows[i]["_guarEmail"].ToString();
            }
            else
            {
                row["Email"] = rawtable.Rows[i]["Email"].ToString();
            } 
            row["FName"] = rawtable.Rows[i]["FName"].ToString();
            row["Guarantor"] = rawtable.Rows[i]["Guarantor"].ToString();
            row["guarFName"] = rawtable.Rows[i]["_guarFName"].ToString();
            row["guarLName"] = rawtable.Rows[i]["_guarLName"].ToString();
            row["LName"] = rawtable.Rows[i]["LName"].ToString();
            row["maxDateDue"] = DateTime.MinValue;
            //we'll set the actual value in a subsequent loop
            row["Note"] = rawtable.Rows[i]["Note"].ToString();
            if (numberOfReminders == 0)
            {
                row["numberOfReminders"] = "";
            }
            else
            {
                row["numberOfReminders"] = numberOfReminders.ToString();
            } 
            pat = new Patient();
            pat.LName = rawtable.Rows[i]["LName"].ToString();
            pat.FName = rawtable.Rows[i]["FName"].ToString();
            pat.Preferred = rawtable.Rows[i]["Preferred"].ToString();
            row["patientName"] = pat.getNameLF();
            row["PatNum"] = rawtable.Rows[i]["PatNum"].ToString();
            /*if(contmeth==ContactMethod.None){
            					if(groupByFamilies) {
            						if(rawtable.Rows[i]["_guarEmail"].ToString() != "") {//since there is an email,
            							row["PreferRecallMethod"]=((int)ContactMethod.Email).ToString();
            						}
            						else {
            							row["PreferRecallMethod"]=((int)ContactMethod.None).ToString();
            						}
            					}
            					else {
            						if(rawtable.Rows[i]["Email"].ToString() != "") {//since there is an email,
            							row["PreferRecallMethod"]=((int)ContactMethod.Email).ToString();
            						}
            						else {
            							row["PreferRecallMethod"]=((int)ContactMethod.None).ToString();
            						}
            					}
            				}
            				else{*/
            row["PreferRecallMethod"] = rawtable.Rows[i]["PreferRecallMethod"].ToString();
            //}
            interv = new Interval(PIn.Int(rawtable.Rows[i]["RecallInterval"].ToString()));
            row["recallInterval"] = interv.toString();
            row["RecallNum"] = rawtable.Rows[i]["RecallNum"].ToString();
            row["recallType"] = rawtable.Rows[i]["_recalltype"].ToString();
            row["status"] = DefC.GetName(DefCat.RecallUnschedStatus, PIn.Long(rawtable.Rows[i]["RecallStatus"].ToString()));
            rows.Add(row);
        }
        //Now that we have eliminated some rows, this next section calculates the maxDateDue date for each family
        //key=guarantor, value=maxDateDue
        Dictionary<long, DateTime> dictMaxDateDue = new Dictionary<long, DateTime>();
        long guarNum = new long();
        for (int i = 0;i < rows.Count;i++)
        {
            guarNum = PIn.Long(rows[i]["Guarantor"].ToString());
            dateDue = (DateTime)rows[i]["DateDue"];
            if (dictMaxDateDue.ContainsKey(guarNum))
            {
                if (dateDue > dictMaxDateDue[guarNum])
                {
                    dictMaxDateDue[guarNum] = dateDue;
                }
                 
            }
            else
            {
                //no decision necessary
                dictMaxDateDue.Add(guarNum, dateDue);
            } 
        }
        for (int i = 0;i < rows.Count;i++)
        {
            guarNum = PIn.Long(rows[i]["Guarantor"].ToString());
            if (dictMaxDateDue.ContainsKey(guarNum))
            {
                rows[i]["maxDateDue"] = dictMaxDateDue[guarNum];
            }
            else
            {
                rows[i]["maxDateDue"] = DateTime.MinValue;
            } 
        }
        //should never happen
        RecallComparer comparer = new RecallComparer();
        comparer.GroupByFamilies = groupByFamilies;
        comparer.SortBy = sortBy;
        rows.Sort(comparer);
        for (int i = 0;i < rows.Count;i++)
        {
            table.Rows.Add(rows[i]);
        }
        return table;
    }

    /**
    * 
    */
    public static long insert(Recall recall) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            recall.RecallNum = Meth.GetLong(MethodBase.GetCurrentMethod(), recall);
            return recall.RecallNum;
        }
         
        return Crud.RecallCrud.Insert(recall);
    }

    /**
    * 
    */
    public static void update(Recall recall) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), recall);
            return ;
        }
         
        Crud.RecallCrud.Update(recall);
    }

    /**
    * 
    */
    public static void delete(Recall recall) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), recall);
            return ;
        }
         
        String command = "DELETE from recall WHERE RecallNum = " + POut.long(recall.RecallNum);
        Db.nonQ(command);
        DeletedObjects.setDeleted(DeletedObjectType.RecallPatNum,recall.PatNum);
    }

    /*//<summary>Will only return true if not disabled, date previous is empty, DateDue is same as DateDueCalc, etc.</summary>
    		public static bool IsAllDefault(Recall recall) {
    			if(recall.IsDisabled
    				|| recall.DatePrevious.Year>1880
    				|| recall.DateDue != recall.DateDueCalc
    				|| recall.RecallInterval!=RecallTypes.GetInterval(recall.RecallTypeNum)//new Interval(0,0,6,0)
    				|| recall.RecallStatus!=0
    				|| recall.Note!="") 
    			{
    				return false;
    			}
    			return true;
    		}*/
    /**
    * Synchronizes all recalls for one patient. If datePrevious has changed, then it completely deletes the old status and note information and sets a new DatePrevious and dateDueCalc.  Also updates dateDue to match dateDueCalc if not disabled.  Creates any recalls as necessary.  Recalls will never get automatically deleted except when all triggers are removed.  Otherwise, the dateDueCalc just gets cleared.
    */
    public static void synch(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patNum);
            return ;
        }
         
        List<RecallType> typeListActive = RecallTypes.getActive();
        List<RecallType> typeList = new List<RecallType>(typeListActive);
        String command = "SELECT * FROM recall WHERE PatNum=" + POut.long(patNum);
        List<Recall> recallList = Crud.RecallCrud.SelectMany(command);
        //determine if this patient is a perio patient.
        boolean isPerio = false;
        for (int i = 0;i < recallList.Count;i++)
        {
            if (PrefC.getLong(PrefName.RecallTypeSpecialPerio) == recallList[i].RecallTypeNum)
            {
                isPerio = true;
                break;
            }
             
        }
        for (int i = 0;i < typeList.Count;i++)
        {
            //remove types from the list which do not apply to this patient.
            //it's ok to not go backwards because we immediately break.
            if (isPerio)
            {
                if (PrefC.getLong(PrefName.RecallTypeSpecialProphy) == typeList[i].RecallTypeNum)
                {
                    typeList.RemoveAt(i);
                    break;
                }
                 
            }
            else
            {
                if (PrefC.getLong(PrefName.RecallTypeSpecialPerio) == typeList[i].RecallTypeNum)
                {
                    typeList.RemoveAt(i);
                    break;
                }
                 
            } 
        }
        //get previous dates for all types at once.
        //Because of the inner join, this will not include recall types with no trigger.
        command = "SELECT RecallTypeNum,MAX(ProcDate) procDate_ " + "FROM procedurelog,recalltrigger " + "WHERE PatNum=" + POut.long(patNum) + " AND procedurelog.CodeNum=recalltrigger.CodeNum " + "AND (";
        if (typeListActive.Count > 0)
        {
            for (int i = 0;i < typeListActive.Count;i++)
            {
                //This will include both prophy and perio, regardless of whether this is a prophy or perio patient.
                if (i > 0)
                {
                    command += " OR";
                }
                 
                command += " RecallTypeNum=" + POut.Long(typeListActive[i].RecallTypeNum);
            }
        }
        else
        {
            command += " RecallTypeNum=0";
        } 
        //Effectively forces an empty result set, without changing the returned table structure.
        command += ") AND (ProcStatus = " + POut.Long(((Enum)OpenDentBusiness.ProcStat.C).ordinal()) + " " + "OR ProcStatus = " + POut.Long(((Enum)OpenDentBusiness.ProcStat.EC).ordinal()) + " " + "OR ProcStatus = " + POut.Long(((Enum)OpenDentBusiness.ProcStat.EO).ordinal()) + ") " + "GROUP BY RecallTypeNum";
        DataTable tableDates = Db.getTable(command);
        //Go through the type list and either update recalls, or create new recalls.
        //Recalls that are no longer active because their type has no triggers will be ignored.
        //It is assumed that there are no duplicate recall types for a patient.
        DateTime prevDate = new DateTime();
        Recall matchingRecall;
        Recall recallNew;
        DateTime prevDateProphy = DateTime.MinValue;
        DateTime dateProphyTesting = new DateTime();
        for (int i = 0;i < typeListActive.Count;i++)
        {
            if (PrefC.getLong(PrefName.RecallTypeSpecialProphy) != typeListActive[i].RecallTypeNum && PrefC.getLong(PrefName.RecallTypeSpecialPerio) != typeListActive[i].RecallTypeNum)
            {
                continue;
            }
             
            for (int d = 0;d < tableDates.Rows.Count;d++)
            {
                //we are only working with prophy and perio in this loop.
                //procs for patient
                if (tableDates.Rows[d]["RecallTypeNum"].ToString() == typeListActive[i].RecallTypeNum.ToString())
                {
                    dateProphyTesting = PIn.Date(tableDates.Rows[d]["procDate_"].ToString());
                    //but patient could have both perio and prophy.
                    //So must test to see if the date is newer
                    if (dateProphyTesting > prevDateProphy)
                    {
                        prevDateProphy = dateProphyTesting;
                    }
                     
                    break;
                }
                 
            }
        }
        for (int i = 0;i < typeList.Count;i++)
        {
            //active types for this patient.
            if (RecallTriggers.GetForType(typeList[i].RecallTypeNum).Count == 0)
            {
                continue;
            }
             
            //if no triggers for this recall type, then skip it.  Don't try to add or alter.
            //set prevDate:
            if (PrefC.getLong(PrefName.RecallTypeSpecialProphy) == typeList[i].RecallTypeNum || PrefC.getLong(PrefName.RecallTypeSpecialPerio) == typeList[i].RecallTypeNum)
            {
                prevDate = prevDateProphy;
            }
            else
            {
                prevDate = DateTime.MinValue;
                for (int d = 0;d < tableDates.Rows.Count;d++)
                {
                    //procs for patient
                    if (tableDates.Rows[d]["RecallTypeNum"].ToString() == typeList[i].RecallTypeNum.ToString())
                    {
                        prevDate = PIn.Date(tableDates.Rows[d]["procDate_"].ToString());
                        break;
                    }
                     
                }
            } 
            matchingRecall = null;
            for (int r = 0;r < recallList.Count;r++)
            {
                //recalls for patient
                if (recallList[r].RecallTypeNum == typeList[i].RecallTypeNum)
                {
                    matchingRecall = recallList[r];
                }
                 
            }
            if (matchingRecall == null)
            {
                //if there is no existing recall,
                if (PrefC.getLong(PrefName.RecallTypeSpecialProphy) == typeList[i].RecallTypeNum || PrefC.getLong(PrefName.RecallTypeSpecialPerio) == typeList[i].RecallTypeNum || prevDate.Year > 1880)
                {
                    //for other types, if date is not minVal, then add a recall
                    //add a recall
                    recallNew = new Recall();
                    recallNew.RecallTypeNum = typeList[i].RecallTypeNum;
                    recallNew.PatNum = patNum;
                    recallNew.DatePrevious = prevDate;
                    //will be min val for prophy/perio with no previous procs
                    recallNew.RecallInterval = typeList[i].DefaultInterval;
                    if (prevDate.Year < 1880)
                    {
                        recallNew.DateDueCalc = DateTime.MinValue;
                    }
                    else
                    {
                        recallNew.DateDueCalc = prevDate + recallNew.RecallInterval;
                    } 
                    recallNew.DateDue = recallNew.DateDueCalc;
                    Recalls.insert(recallNew);
                }
                 
            }
            else
            {
                //alter the existing recall
                //this protects recalls that were manually added as part of a conversion
                if (!matchingRecall.IsDisabled && matchingRecall.DisableUntilBalance == 0 && matchingRecall.DisableUntilDate.Year < 1880 && prevDate.Year > 1880 && prevDate != matchingRecall.DatePrevious)
                {
                    //if datePrevious has changed, reset
                    matchingRecall.RecallStatus = 0;
                    matchingRecall.Note = "";
                    matchingRecall.DateDue = matchingRecall.DateDueCalc;
                }
                 
                //now it is allowed to be changed in the steps below
                if (prevDate.Year < 1880)
                {
                    //if no previous date
                    matchingRecall.DatePrevious = DateTime.MinValue;
                    if (matchingRecall.DateDue == matchingRecall.DateDueCalc)
                    {
                        //user did not enter a DateDue
                        matchingRecall.DateDue = DateTime.MinValue;
                    }
                     
                    matchingRecall.DateDueCalc = DateTime.MinValue;
                    Recalls.update(matchingRecall);
                }
                else
                {
                    //if previous date is a valid date
                    matchingRecall.DatePrevious = prevDate;
                    if (matchingRecall.IsDisabled)
                    {
                        //if the existing recall is disabled
                        matchingRecall.DateDue = DateTime.MinValue;
                    }
                    else
                    {
                        //DateDue is always blank
                        //but if not disabled
                        //if user did not enter a DateDue
                        if (matchingRecall.DateDue == matchingRecall.DateDueCalc || matchingRecall.DateDue.Year < 1880)
                        {
                            //or DateDue was blank
                            matchingRecall.DateDue = matchingRecall.DatePrevious + matchingRecall.RecallInterval;
                        }
                         
                    } 
                    //set same as DateDueCalc
                    matchingRecall.DateDueCalc = matchingRecall.DatePrevious + matchingRecall.RecallInterval;
                    Recalls.update(matchingRecall);
                } 
            } 
        }
    }

    //now, we need to loop through all the inactive recall types and clear the DateDueCalc
    //We don't do this anymore. User must explicitly delete recalls, either one-by-one, or from the recall type window.
    /*
    			List<RecallType> typeListInactive=RecallTypes.GetInactive();
    			for(int i=0;i<typeListInactive.Count;i++){
    				matchingRecall=null;
    				for(int r=0;r<recallList.Count;r++){
    					if(recallList[r].RecallTypeNum==typeListInactive[i].RecallTypeNum){
    						matchingRecall=recallList[r];
    					}
    				}
    				if(matchingRecall==null){//if there is no existing recall,
    					continue;
    				}
    				Recalls.Delete(matchingRecall);//we'll just delete it
    				//There is an existing recall, so alter it if certain conditions are met
    				//matchingRecall.DatePrevious=DateTime.MinValue;
    				//if(matchingRecall.DateDue==matchingRecall.DateDueCalc){//if user did not enter a DateDue
    					//we can safely alter the DateDue
    				//	matchingRecall.DateDue=DateTime.MinValue;
    				//}
    				//matchingRecall.DateDueCalc=DateTime.MinValue;
    				//Recalls.Update(matchingRecall);
    			}*/
    /**
    * Synchronizes DateScheduled column in recall table for one patient.  This must be used instead of lazy synch in RecallsForPatient, when deleting an appointment, when sending to unscheduled list, setting an appointment complete, etc.  This is fast, but it would be inefficient to call it too much.
    */
    public static void synchScheduledApptFull(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patNum);
            return ;
        }
         
        //Clear out DateScheduled column for this pat before changing
        String command = "UPDATE recall " + "SET recall.DateScheduled=" + POut.Date(DateTime.MinValue) + " " + "WHERE recall.PatNum=" + POut.long(patNum);
        Db.nonQ(command);
        //Get table of future appointments dates with recall type for this patient, where a procedure is attached that is a recall trigger procedure
        //Scheduled
        //ASAP
        command = "SELECT recalltrigger.RecallTypeNum,MIN(" + DbHelper.dateColumn("appointment.AptDateTime") + ") AS AptDateTime\r\n" + 
        "\t\t\t\tFROM appointment,procedurelog,recalltrigger,recall\r\n" + 
        "\t\t\t\tWHERE appointment.AptNum=procedurelog.AptNum \r\n" + 
        "\t\t\t\tAND appointment.PatNum=" + POut.long(patNum) + " \r\n" + 
        "\t\t\t\tAND procedurelog.CodeNum=recalltrigger.CodeNum \r\n" + 
        "\t\t\t\tAND recall.PatNum=appointment.PatNum \r\n" + 
        "\t\t\t\tAND recalltrigger.RecallTypeNum=recall.RecallTypeNum \r\n" + 
        "\t\t\t\tAND (appointment.AptStatus=1 " + "OR appointment.AptStatus=4) " + "AND appointment.AptDateTime > " + DbHelper.curdate() + " " + "GROUP BY recalltrigger.RecallTypeNum";
        //early this morning
        DataTable table = Db.getTable(command);
        for (int i = 0;i < table.Rows.Count;i++)
        {
            //Update the recalls for this patient with DATE(AptDateTime) where there is a future appointment with recall proc on it
            if (StringSupport.equals(table.Rows[i]["RecallTypeNum"].ToString(), ""))
            {
                continue;
            }
             
            command = "UPDATE recall\tSET recall.DateScheduled=" + POut.Date(PIn.Date(table.Rows[i]["AptDateTime"].ToString())) + " " + "WHERE recall.RecallTypeNum=" + POut.Long(PIn.Long(table.Rows[i]["RecallTypeNum"].ToString())) + " " + "AND recall.PatNum=" + POut.long(patNum) + " ";
            Db.nonQ(command);
        }
    }

    /*
    		/// <summary>Whenever an appointment is scheduled or moved, or whenever procs are added to an appt.  Uses the final apt date and all the procs on the appt.  Pass in procs in the D#### format.</summary>
    		public static void SynchScheduledApptLazy(long patNum,DateTime date,List<string> procCodes) {
    			//compare procCodes to triggers so that you know which types of recalls to update.
    			List<long> recalltypes=new List<long>();//List of recall types that have procs that match one of the procCodes sent in
    			for(int i=0;i<procCodes.Count;i++){
    				long codeNum=ProcedureCodes.GetCodeNum(procCodes[i]);
    				for(int j=0;j<RecallTriggerC.Listt.Count;j++) {
    					if(codeNum==RecallTriggerC.Listt[j].CodeNum) {
    						recalltypes.Add(RecallTriggerC.Listt[j].RecallTypeNum);
    						break;
    					}
    				}
    			}
    			if(recalltypes.Count>0) {
    				//for each type that needs to be updated:
    					//blind updates to the db to set the date based on trigger procs.
    				//Update date of all recalls of types that need updating based on procCodes sent in
    				string command=@"UPDATE recall
    					SET recall.DateScheduled="+POut.Date(date)+" "
    					+"WHERE recall.PatNum="+POut.Long(patNum)+" "
    					+"AND (";
    				for(int i=0;i<recalltypes.Count;i++) {
    					if(i>0) {
    						command+=" OR ";
    					}
    					command+="recall.RecallTypeNum="+recalltypes[i].ToString();
    				}
    				command+=") ";
    				Db.NonQ(command);
    			}
    		}*/
    /**
    * Updates RecallInterval and DueDate for all patients that have the recallTypeNum and defaultIntervalOld to use the defaultIntervalNew.
    */
    public static void updateDefaultIntervalForPatients(long recallTypeNum, Interval defaultIntervalOld, Interval defaultIntervalNew) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), recallTypeNum, defaultIntervalOld, defaultIntervalNew);
            return ;
        }
         
        String command = "SELECT * FROM recall WHERE IsDisabled=0 AND RecallTypeNum=" + POut.long(recallTypeNum) + " AND RecallInterval=" + POut.int(defaultIntervalOld.toInt());
        List<Recall> recallList = Crud.RecallCrud.SelectMany(command);
        for (int i = 0;i < recallList.Count;i++)
        {
            if (recallList[i].DateDue != recallList[i].DateDueCalc)
            {
            }
            else
            {
                //User entered a DueDate.
                //Don't change the DateDue since user already overrode it
                recallList[i].DateDue = recallList[i].DatePrevious + defaultIntervalNew;
            } 
            recallList[i].DateDueCalc = recallList[i].DatePrevious + defaultIntervalNew;
            recallList[i].RecallInterval = defaultIntervalNew;
            Update(recallList[i]);
        }
    }

    public static void deleteAllOfType(long recallTypeNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), recallTypeNum);
            return ;
        }
         
        String command = "DELETE FROM recall WHERE RecallTypeNum= " + POut.long(recallTypeNum);
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static void synchAllPatients() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod());
            return ;
        }
         
        //get all active patients
        String command = "SELECT PatNum " + "FROM patient " + "WHERE PatStatus=0";
        DataTable table = Db.getTable(command);
        for (int i = 0;i < table.Rows.Count;i++)
        {
            Synch(PIn.Long(table.Rows[i][0].ToString()));
        }
        //get all active patients with future scheduled appointments that have a procedure attached which is a recall trigger procedure
        command = "SELECT DISTINCT patient.PatNum " + "FROM patient " + "INNER JOIN appointment ON appointment.PatNum=patient.PatNum AND AptDateTime>CURDATE() AND AptStatus IN (1,4,5) " + "INNER JOIN procedurelog ON procedurelog.AptNum=appointment.AptNum " + "INNER JOIN recalltrigger ON recalltrigger.CodeNum=procedurelog.CodeNum " + "WHERE PatStatus=0";
        //Scheduled,ASAP, or Broken
        //Broken is only included to fix a bug that existed between versions 12.4 and 13.2.  It clears out the datesched
        //if broken future appt is the only appt with a recall trigger on it so the patient will be on the recall list again.
        table = Db.getTable(command);
        for (int i = 0;i < table.Rows.Count;i++)
        {
            SynchScheduledApptFull(PIn.Long(table.Rows[i][0].ToString()));
        }
    }

    /**
    * 
    */
    public static DataTable getAddrTable(List<long> recallNums, boolean groupByFamily, RecallListSort sortBy) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), recallNums, groupByFamily, sortBy);
        }
         
        //get maxDateDue for each family.
        String command = "DROP TABLE IF EXISTS temprecallmaxdate;\r\n" + 
        "\t\t\t\tCREATE table temprecallmaxdate(\r\n" + 
        "\t\t\t\t\tGuarantor bigint NOT NULL,\r\n" + 
        "\t\t\t\t\tMaxDateDue date NOT NULL,\r\n" + 
        "\t\t\t\t\tPRIMARY KEY (Guarantor)\r\n" + 
        "\t\t\t\t);\r\n" + 
        "\t\t\t\tINSERT INTO temprecallmaxdate \r\n" + 
        "\t\t\t\tSELECT patient.Guarantor,MAX(recall.DateDue) maxDateDue\r\n" + 
        "\t\t\t\tFROM patient\r\n" + 
        "\t\t\t\tLEFT JOIN recall ON patient.PatNum=recall.PatNum\r\n" + 
        "\t\t\t\tAND (";
        for (int i = 0;i < recallNums.Count;i++)
        {
            if (i > 0)
            {
                command += " OR ";
            }
             
            command += "recall.RecallNum=" + POut.Long(recallNums[i]);
        }
        command += ") GROUP BY patient.Guarantor";
        Db.nonQ(command);
        command = "SELECT patient.Address,patguar.Address guarAddress,\r\n" + 
        "\t\t\t\tpatient.Address2,patguar.Address2 guarAddress2,\r\n" + 
        "\t\t\t\tpatient.City,patguar.City guarCity,patient.ClinicNum,patguar.ClinicNum guarClinicNum,\r\n" + 
        "\t\t\t\trecall.DateDue,patient.Email,patguar.Email guarEmail,\r\n" + 
        "\t\t\t\tpatient.FName,patguar.FName guarFName,patient.Guarantor,\r\n" + 
        "\t\t\t\tpatient.LName,patguar.LName guarLName,temprecallmaxdate.MaxDateDue maxDateDue,\r\n" + 
        "\t\t\t\tpatient.MiddleI,\r\n" + 
        "\t\t\t\tCOUNT(commlog.CommlogNum) numberOfReminders,\r\n" + 
        "\t\t\t\tpatient.PatNum,patient.Preferred,recall.RecallNum,\r\n" + 
        "\t\t\t\tpatient.State,patguar.State guarState,patient.Zip,patguar.Zip guarZip\r\n" + 
        "\t\t\t\tFROM recall \r\n" + 
        "\t\t\t\tLEFT JOIN patient ON patient.PatNum=recall.PatNum \r\n" + 
        "\t\t\t\tLEFT JOIN patient patguar ON patient.Guarantor=patguar.PatNum\r\n" + 
        "\t\t\t\tLEFT JOIN commlog ON commlog.PatNum=recall.PatNum\r\n" + 
        "\t\t\t\tAND CommType=" + POut.long(Commlogs.getTypeAuto(CommItemTypeAuto.RECALL)) + " " + "AND CommDateTime > recall.DatePrevious " + "LEFT JOIN temprecallmaxdate ON temprecallmaxdate.Guarantor=patient.Guarantor " + "WHERE ";
        for (int i = 0;i < recallNums.Count;i++)
        {
            //+"AND SentOrReceived = "+POut.Long((int)CommSentOrReceived.Sent)+" "
            if (i > 0)
            {
                command += " OR ";
            }
             
            command += "recall.RecallNum=" + POut.Long(recallNums[i]);
        }
        command += " GROUP BY patient.Address,patguar.Address,\r\n" + 
        "\t\t\t\tpatient.Address2,patguar.Address2,\r\n" + 
        "\t\t\t\tpatient.City,patguar.City,patient.ClinicNum,patguar.ClinicNum,\r\n" + 
        "\t\t\t\trecall.DateDue,patient.Email,patguar.Email,\r\n" + 
        "\t\t\t\tpatient.FName,patguar.FName,patient.Guarantor,\r\n" + 
        "\t\t\t\tpatient.LName,patguar.LName,temprecallmaxdate.MaxDateDue,\r\n" + 
        "\t\t\t\tpatient.MiddleI,patient.PatNum,patient.Preferred,recall.RecallNum,\r\n" + 
        "\t\t\t\tpatient.State,patguar.State,patient.Zip,patguar.Zip";
        DataTable rawTable = Db.getTable(command);
        command = "DROP TABLE IF EXISTS temprecallmaxdate";
        Db.nonQ(command);
        List<DataRow> rawRows = new List<DataRow>();
        for (int i = 0;i < rawTable.Rows.Count;i++)
        {
            rawRows.Add(rawTable.Rows[i]);
        }
        RecallComparer comparer = new RecallComparer();
        comparer.GroupByFamilies = groupByFamily;
        comparer.SortBy = sortBy;
        rawRows.Sort(comparer);
        DataTable table = new DataTable();
        table.Columns.Add("address");
        //includes address2. Can be guar.
        table.Columns.Add("City");
        //Can be guar.
        table.Columns.Add("clinicNum");
        //will be the guar clinicNum if grouped.
        table.Columns.Add("dateDue");
        table.Columns.Add("email");
        //Will be guar if grouped by family
        table.Columns.Add("emailPatNum");
        //Will be guar if grouped by family
        table.Columns.Add("famList");
        table.Columns.Add("guarLName");
        table.Columns.Add("numberOfReminders");
        //for a family, this will be the max for the family
        table.Columns.Add("patientNameF");
        //Only used when single email
        table.Columns.Add("patientNameFL");
        table.Columns.Add("patNums");
        //Comma delimited.  Used in email.
        table.Columns.Add("recallNums");
        //Comma delimited.  Used during e-mail and eCards
        table.Columns.Add("State");
        //Can be guar.
        table.Columns.Add("Zip");
        //Can be guar.
        String familyAptList = "";
        String recallNumStr = "";
        String patNumStr = "";
        DataRow row = new DataRow();
        List<DataRow> rows = new List<DataRow>();
        int maxNumReminders = 0;
        int maxRemindersThisPat = new int();
        Patient pat;
        for (int i = 0;i < rawRows.Count;i++)
        {
            if (!groupByFamily)
            {
                row = table.NewRow();
                row["address"] = rawRows[i]["Address"].ToString();
                if (!StringSupport.equals(rawRows[i]["Address2"].ToString(), ""))
                {
                    row["address"] += "\r\n" + rawRows[i]["Address2"].ToString();
                }
                 
                row["City"] = rawRows[i]["City"].ToString();
                row["clinicNum"] = rawRows[i]["ClinicNum"].ToString();
                row["dateDue"] = PIn.Date(rawRows[i]["DateDue"].ToString()).ToShortDateString();
                //since not grouping by family, this is always just the patient email
                row["email"] = rawRows[i]["Email"].ToString();
                row["emailPatNum"] = rawRows[i]["PatNum"].ToString();
                row["famList"] = "";
                row["guarLName"] = rawRows[i]["guarLName"].ToString();
                //even though we won't use it.
                row["numberOfReminders"] = PIn.Long(rawRows[i]["numberOfReminders"].ToString()).ToString();
                pat = new Patient();
                pat.LName = rawRows[i]["LName"].ToString();
                pat.FName = rawRows[i]["FName"].ToString();
                pat.Preferred = rawRows[i]["Preferred"].ToString();
                row["patientNameF"] = pat.getNameFirstOrPreferred();
                row["patientNameFL"] = pat.getNameFLnoPref();
                // GetNameFirstOrPrefL();
                row["patNums"] = rawRows[i]["PatNum"].ToString();
                row["recallNums"] = rawRows[i]["RecallNum"].ToString();
                row["State"] = rawRows[i]["State"].ToString();
                row["Zip"] = rawRows[i]["Zip"].ToString();
                rows.Add(row);
                continue;
            }
             
            //groupByFamily----------------------------------------------------------------------
            if (StringSupport.equals(familyAptList, ""))
            {
                //if this is the first patient in the family
                maxNumReminders = 0;
                for (int f = i;f < rawRows.Count;f++)
                {
                    //loop through the whole family, and determine the maximum number of reminders
                    maxRemindersThisPat = PIn.Int(rawRows[f]["numberOfReminders"].ToString());
                    if (maxRemindersThisPat > maxNumReminders)
                    {
                        maxNumReminders = maxRemindersThisPat;
                    }
                     
                    //if this is the last row
                    if (f == rawRows.Count - 1 || rawRows[i]["Guarantor"].ToString() != rawRows[f + 1]["Guarantor"].ToString())
                    {
                        break;
                    }
                     
                }
                //or if the guarantor on next line is different
                //now we know the max number of reminders for the family
                //if this is the last row
                if (i == rawRows.Count - 1 || rawRows[i]["Guarantor"].ToString() != rawRows[i + 1]["Guarantor"].ToString())
                {
                    //or if the guarantor on next line is different
                    //then this is a single patient, and there are no other family members in the list.
                    row = table.NewRow();
                    row["address"] = rawRows[i]["Address"].ToString();
                    if (!StringSupport.equals(rawRows[i]["Address2"].ToString(), ""))
                    {
                        row["address"] += "\r\n" + rawRows[i]["Address2"].ToString();
                    }
                     
                    row["City"] = rawRows[i]["City"].ToString();
                    row["State"] = rawRows[i]["State"].ToString();
                    row["Zip"] = rawRows[i]["Zip"].ToString();
                    row["clinicNum"] = rawRows[i]["ClinicNum"].ToString();
                    row["dateDue"] = PIn.Date(rawRows[i]["DateDue"].ToString()).ToShortDateString();
                    //this will always be the guarantor email
                    row["email"] = rawRows[i]["guarEmail"].ToString();
                    row["emailPatNum"] = rawRows[i]["Guarantor"].ToString();
                    row["famList"] = "";
                    row["guarLName"] = rawRows[i]["guarLName"].ToString();
                    //even though we won't use it.
                    row["numberOfReminders"] = maxNumReminders.ToString();
                    //if(rawRows[i]["Preferred"].ToString()=="") {
                    row["patientNameF"] = rawRows[i]["FName"].ToString();
                    //}
                    //else {
                    //	row["patientNameF"]=rawRows[i]["Preferred"].ToString();
                    //}
                    row["patientNameFL"] = rawRows[i]["FName"].ToString() + " " + rawRows[i]["MiddleI"].ToString() + " " + rawRows[i]["LName"].ToString();
                    row["patNums"] = rawRows[i]["PatNum"].ToString();
                    row["recallNums"] = rawRows[i]["RecallNum"].ToString();
                    rows.Add(row);
                    continue;
                }
                else
                {
                    //this is the first patient of a family with multiple family members
                    familyAptList = rawRows[i]["FName"].ToString() + ":  " + PIn.Date(rawRows[i]["DateDue"].ToString()).ToShortDateString();
                    patNumStr = rawRows[i]["PatNum"].ToString();
                    recallNumStr = rawRows[i]["RecallNum"].ToString();
                    continue;
                } 
            }
            else
            {
                //not the first patient
                familyAptList += "\r\n" + rawRows[i]["FName"].ToString() + ":  " + PIn.Date(rawRows[i]["DateDue"].ToString()).ToShortDateString();
                patNumStr += "," + rawRows[i]["PatNum"].ToString();
                recallNumStr += "," + rawRows[i]["RecallNum"].ToString();
            } 
            //if this is the last row
            if (i == rawRows.Count - 1 || rawRows[i]["Guarantor"].ToString() != rawRows[i + 1]["Guarantor"].ToString())
            {
                //or if the guarantor on next line is different
                //This part only happens for the last family member of a grouped family
                row = table.NewRow();
                row["address"] = rawRows[i]["guarAddress"].ToString();
                if (!StringSupport.equals(rawRows[i]["guarAddress2"].ToString(), ""))
                {
                    row["address"] += "\r\n" + rawRows[i]["guarAddress2"].ToString();
                }
                 
                row["City"] = rawRows[i]["City"].ToString();
                row["State"] = rawRows[i]["State"].ToString();
                row["Zip"] = rawRows[i]["Zip"].ToString();
                row["clinicNum"] = rawRows[i]["guarClinicNum"].ToString();
                row["dateDue"] = PIn.Date(rawRows[i]["DateDue"].ToString()).ToShortDateString();
                row["email"] = rawRows[i]["guarEmail"].ToString();
                row["emailPatNum"] = rawRows[i]["Guarantor"].ToString();
                row["famList"] = familyAptList;
                row["guarLName"] = rawRows[i]["guarLName"].ToString();
                row["numberOfReminders"] = maxNumReminders.ToString();
                row["patientNameF"] = "";
                //not used here
                row["patientNameFL"] = "";
                //we won't use this
                row["patNums"] = patNumStr;
                row["recallNums"] = recallNumStr;
                rows.Add(row);
                familyAptList = "";
            }
             
        }
        for (int i = 0;i < rows.Count;i++)
        {
            table.Rows.Add(rows[i]);
        }
        return table;
    }

    /**
    * 
    */
    public static void updateStatus(long recallNum, long newStatus) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), recallNum, newStatus);
            return ;
        }
         
        String command = "UPDATE recall SET RecallStatus=" + newStatus.ToString() + " WHERE RecallNum=" + recallNum.ToString();
        Db.nonQ(command);
    }

    public static int getCountForType(long recallTypeNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetInt(MethodBase.GetCurrentMethod(), recallTypeNum);
        }
         
        String command = "SELECT COUNT(*) FROM recall " + "JOIN recalltype ON recall.RecallTypeNum=recalltype.RecallTypeNum " + "WHERE recalltype.recallTypeNum=" + POut.long(recallTypeNum);
        return PIn.int(Db.getCount(command));
    }

    /**
    * Return RecallNums that have changed since a paticular time.
    */
    public static List<long> getChangedSinceRecallNums(DateTime changedSince) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<long>>GetObject(MethodBase.GetCurrentMethod(), changedSince);
        }
         
        String command = "SELECT RecallNum FROM recall WHERE DateTStamp > " + POut.dateT(changedSince);
        DataTable dt = Db.getTable(command);
        List<long> recallnums = new List<long>(dt.Rows.Count);
        for (int i = 0;i < dt.Rows.Count;i++)
        {
            recallnums.Add(PIn.Long(dt.Rows[i]["RecallNum"].ToString()));
        }
        return recallnums;
    }

    /**
    * Returns recalls with given list of RecallNums. Used along with GetChangedSinceRecallNums.
    */
    public static List<Recall> getMultRecalls(List<long> recallNums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Recall>>GetObject(MethodBase.GetCurrentMethod(), recallNums);
        }
         
        String strRecallNums = "";
        DataTable table = new DataTable();
        if (recallNums.Count > 0)
        {
            for (int i = 0;i < recallNums.Count;i++)
            {
                if (i > 0)
                {
                    strRecallNums += "OR ";
                }
                 
                strRecallNums += "RecallNum='" + recallNums[i].ToString() + "' ";
            }
            String command = "SELECT * FROM recall WHERE " + strRecallNums;
            table = Db.getTable(command);
        }
        else
        {
            table = new DataTable();
        } 
        Recall[] multRecalls = Crud.RecallCrud.TableToList(table).ToArray();
        List<Recall> recallList = new List<Recall>(multRecalls);
        return recallList;
    }

}


