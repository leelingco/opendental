//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:47 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.ProgramProperty;
import OpenDentBusiness.ProgramPropertyC;
import OpenDentBusiness.Programs;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class ProgramProperties   
{
    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM programproperty";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "ProgramProperty";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        ProgramPropertyC.setListt(Crud.ProgramPropertyCrud.TableToList(table));
    }

    /**
    * 
    */
    public static void update(ProgramProperty Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        Crud.ProgramPropertyCrud.Update(Cur);
    }

    /**
    * This can only be called from ClassConversions. Users not allowed to add properties so there is no user interface.
    */
    public static long insert(ProgramProperty Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Cur.ProgramPropertyNum = Meth.GetLong(MethodBase.GetCurrentMethod(), Cur);
            return Cur.ProgramPropertyNum;
        }
         
        return Crud.ProgramPropertyCrud.Insert(Cur);
    }

    /**
    * This can only be called from ClassConversions. Users not allowed to delete properties so there is no user interface.
    */
    public static void delete(ProgramProperty Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        String command = "DELETE from programproperty WHERE programpropertynum = '" + Cur.ProgramPropertyNum.ToString() + "'";
        Db.nonQ(command);
    }

    /**
    * Returns a List of programproperties attached to the specified programNum.  Does not include path overrides.
    */
    public static List<ProgramProperty> getListForProgram(long programNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<ProgramProperty> listProgProp = new List<ProgramProperty>();
        for (int i = 0;i < ProgramPropertyC.getListt().Count;i++)
        {
            if (ProgramPropertyC.getListt()[i].ProgramNum == programNum && !StringSupport.equals(ProgramPropertyC.getListt()[i].PropertyDesc, ""))
            {
                listProgProp.Add(ProgramPropertyC.getListt()[i]);
            }
             
        }
        return listProgProp;
    }

    /**
    * Returns an ArrayList of programproperties attached to the specified programNum.  Does not include path overrides.
    */
    public static ArrayList getForProgram(long programNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        ArrayList ForProgram = new ArrayList();
        for (int i = 0;i < ProgramPropertyC.getListt().Count;i++)
        {
            if (ProgramPropertyC.getListt()[i].ProgramNum == programNum && !StringSupport.equals(ProgramPropertyC.getListt()[i].PropertyDesc, ""))
            {
                ForProgram.Add(ProgramPropertyC.getListt()[i]);
            }
             
        }
        return ForProgram;
    }

    public static void setProperty(long programNum, String desc, String propval) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), programNum, desc, propval);
            return ;
        }
         
        String command = "UPDATE programproperty SET PropertyValue='" + POut.string(propval) + "' " + "WHERE ProgramNum=" + POut.long(programNum) + " " + "AND PropertyDesc='" + POut.string(desc) + "'";
        Db.nonQ(command);
    }

    /**
    * After GetForProgram has been run, this gets one of those properties.
    */
    public static ProgramProperty getCur(ArrayList ForProgram, String desc) throws Exception {
        for (int i = 0;i < ForProgram.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (StringSupport.equals(((ProgramProperty)ForProgram[i]).PropertyDesc, desc))
            {
                return (ProgramProperty)ForProgram[i];
            }
             
        }
        return null;
    }

    public static String getPropVal(long programNum, String desc) throws Exception {
        for (int i = 0;i < ProgramPropertyC.getListt().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (ProgramPropertyC.getListt()[i].ProgramNum != programNum)
            {
                continue;
            }
             
            if (!StringSupport.equals(ProgramPropertyC.getListt()[i].PropertyDesc, desc))
            {
                continue;
            }
             
            return ProgramPropertyC.getListt()[i].PropertyValue;
        }
        throw new ApplicationException("Property not found: " + desc);
    }

    public static String getPropVal(ProgramName progName, String propertyDesc) throws Exception {
        //No need to check RemotingRole; no call to db.
        long programNum = Programs.getProgramNum(progName);
        for (int i = 0;i < ProgramPropertyC.getListt().Count;i++)
        {
            if (ProgramPropertyC.getListt()[i].ProgramNum != programNum)
            {
                continue;
            }
             
            if (!StringSupport.equals(ProgramPropertyC.getListt()[i].PropertyDesc, propertyDesc))
            {
                continue;
            }
             
            return ProgramPropertyC.getListt()[i].PropertyValue;
        }
        throw new ApplicationException("Property not found: " + propertyDesc);
    }

    /**
    * Used in FormUAppoint to get frequent and current data.
    */
    public static String getValFromDb(long programNum, String desc) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), programNum, desc);
        }
         
        String command = "SELECT PropertyValue FROM programproperty WHERE ProgramNum=" + POut.long(programNum) + " AND PropertyDesc='" + POut.string(desc) + "'";
        DataTable table = Db.getTable(command);
        if (table.Rows.Count == 0)
        {
            return "";
        }
         
        return table.Rows[0][0].ToString();
    }

    /**
    * Returns the path override for the current computer and the specified programNum.  Returns empty string if no override found.
    */
    public static String getLocalPathOverrideForProgram(long programNum) throws Exception {
        for (int i = 0;i < ProgramPropertyC.getListt().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (ProgramPropertyC.getListt()[i].ProgramNum == programNum && StringSupport.equals(ProgramPropertyC.getListt()[i].PropertyDesc, "") && ProgramPropertyC.getListt()[i].ComputerName.ToUpper() == Environment.MachineName.ToUpper())
            {
                return ProgramPropertyC.getListt()[i].PropertyValue;
            }
             
        }
        return "";
    }

    /**
    * This will insert or update a local path override property for the specified programNum.
    */
    public static void insertOrUpdateLocalOverridePath(long programNum, String newPath) throws Exception {
        for (int i = 0;i < ProgramPropertyC.getListt().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (ProgramPropertyC.getListt()[i].ProgramNum == programNum && StringSupport.equals(ProgramPropertyC.getListt()[i].PropertyDesc, "") && ProgramPropertyC.getListt()[i].ComputerName.ToUpper() == Environment.MachineName.ToUpper())
            {
                ProgramPropertyC.getListt()[i].PropertyValue = newPath;
                ProgramProperties.Update(ProgramPropertyC.getListt()[i]);
                return ;
            }
             
        }
        //Will only be one override per computer per program.
        //Path override does not exist for the current computer so create a new one.
        ProgramProperty pp = new ProgramProperty();
        pp.ProgramNum = programNum;
        pp.PropertyValue = newPath;
        pp.ComputerName = Environment.MachineName.ToUpper();
        ProgramProperties.insert(pp);
    }

}


