//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:07 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Credentials;
import OpenDentBusiness.DtoGetBool;
import OpenDentBusiness.DtoGetDS;
import OpenDentBusiness.DtoGetInt;
import OpenDentBusiness.DtoGetLong;
import OpenDentBusiness.DtoGetObject;
import OpenDentBusiness.DtoGetString;
import OpenDentBusiness.DtoGetTable;
import OpenDentBusiness.DtoGetTableLow;
import OpenDentBusiness.DtoGetVoid;
import OpenDentBusiness.DtoObject;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Security;

/**
* Short for Method.  Calls a method remotely.  ONLY used if ClientWeb.  This must be tested at top of the method in question.  These will be used extensively since ALL data interface classes will need this.  This completely avoids sending queries directly to the server.  Must pass all the parameters from the original method.
*/
public class Meth   
{
    /**
    * 
    */
    public static DataTable getTable(MethodBase methodBase, Object... parameters) throws Exception {
        if (RemotingClient.RemotingRole != RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Meth.GetTable may only be used when RemotingRole is ClientWeb.");
        }
         
        DtoGetTable dto = new DtoGetTable();
        dto.MethodName = methodBase.DeclaringType.Name + "." + methodBase.Name;
        dto.Params = DtoObject.ConstructArray(parameters, getParamTypes(methodBase));
        dto.Credentials = new Credentials();
        dto.Credentials.Username = Security.getCurUser().UserName;
        dto.Credentials.Password = Security.PasswordTyped;
        return RemotingClient.processGetTable(dto);
    }

    //.CurUser.Password;
    /**
    * Uses lower sql permissions, making it safe to pass a query.
    */
    public static DataTable getTableLow(String command) throws Exception {
        if (RemotingClient.RemotingRole != RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Meth.GetTableLow may only be used when RemotingRole is ClientWeb.");
        }
         
        DtoGetTableLow dto = new DtoGetTableLow();
        dto.MethodName = "";
        DtoObject dtoObj = new DtoObject(command, String.class);
        dto.Params = new DtoObject[]{ dtoObj };
        dto.Credentials = new Credentials();
        dto.Credentials.Username = Security.getCurUser().UserName;
        dto.Credentials.Password = Security.PasswordTyped;
        return RemotingClient.processGetTableLow(dto);
    }

    //.CurUser.Password;
    /**
    * 
    */
    public static DataSet getDS(MethodBase methodBase, Object... parameters) throws Exception {
        if (RemotingClient.RemotingRole != RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Meth.GetDS may only be used when RemotingRole is ClientWeb.");
        }
         
        DtoGetDS dto = new DtoGetDS();
        dto.MethodName = methodBase.DeclaringType.Name + "." + methodBase.Name;
        dto.Params = DtoObject.ConstructArray(parameters, getParamTypes(methodBase));
        dto.Credentials = new Credentials();
        dto.Credentials.Username = Security.getCurUser().UserName;
        dto.Credentials.Password = Security.PasswordTyped;
        return RemotingClient.processGetDS(dto);
    }

    //.CurUser.Password;
    /**
    * 
    */
    public static long getLong(MethodBase methodBase, Object... parameters) throws Exception {
        if (RemotingClient.RemotingRole != RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Meth.GetLong may only be used when RemotingRole is ClientWeb.");
        }
         
        DtoGetLong dto = new DtoGetLong();
        dto.MethodName = methodBase.DeclaringType.Name + "." + methodBase.Name;
        dto.Params = DtoObject.ConstructArray(parameters, getParamTypes(methodBase));
        dto.Credentials = new Credentials();
        dto.Credentials.Username = Security.getCurUser().UserName;
        dto.Credentials.Password = Security.PasswordTyped;
        return RemotingClient.processGetLong(dto);
    }

    //.CurUser.Password;
    /**
    * 
    */
    public static int getInt(MethodBase methodBase, Object... parameters) throws Exception {
        if (RemotingClient.RemotingRole != RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Meth.GetInt may only be used when RemotingRole is ClientWeb.");
        }
         
        DtoGetInt dto = new DtoGetInt();
        dto.MethodName = methodBase.DeclaringType.Name + "." + methodBase.Name;
        dto.Params = DtoObject.ConstructArray(parameters, getParamTypes(methodBase));
        dto.Credentials = new Credentials();
        dto.Credentials.Username = Security.getCurUser().UserName;
        dto.Credentials.Password = Security.PasswordTyped;
        return RemotingClient.processGetInt(dto);
    }

    //.CurUser.Password;
    /**
    * 
    */
    public static void getVoid(MethodBase methodBase, Object... parameters) throws Exception {
        if (RemotingClient.RemotingRole != RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Meth.GetVoid may only be used when RemotingRole is ClientWeb.");
        }
         
        DtoGetVoid dto = new DtoGetVoid();
        dto.MethodName = methodBase.DeclaringType.Name + "." + methodBase.Name;
        dto.Params = DtoObject.ConstructArray(parameters, getParamTypes(methodBase));
        dto.Credentials = new Credentials();
        dto.Credentials.Username = Security.getCurUser().UserName;
        dto.Credentials.Password = Security.PasswordTyped;
        //.CurUser.Password;
        RemotingClient.processGetVoid(dto);
    }

    /**
    * 
    */
    public static <T>T getObject(MethodBase methodBase, Object... parameters) throws Exception {
        if (RemotingClient.RemotingRole != RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Meth.GetObject may only be used when RemotingRole is ClientWeb.");
        }
         
        //can't verify return type
        DtoGetObject dto = new DtoGetObject();
        if (T.class.IsGenericType)
        {
            Type listType = T.class.GetGenericArguments()[0];
            dto.ObjectType = "List<" + listType.FullName + ">";
        }
        else
        {
            dto.ObjectType = T.class.FullName;
        } 
        dto.MethodName = methodBase.DeclaringType.Name + "." + methodBase.Name;
        dto.Params = DtoObject.ConstructArray(parameters, getParamTypes(methodBase));
        dto.Credentials = new Credentials();
        dto.Credentials.Username = Security.getCurUser().UserName;
        dto.Credentials.Password = Security.PasswordTyped;
        return RemotingClient.processGetObject(dto);
    }

    //.CurUser.Password;
    /**
    * 
    */
    public static String getString(MethodBase methodBase, Object... parameters) throws Exception {
        if (RemotingClient.RemotingRole != RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Meth.GetString may only be used when RemotingRole is ClientWeb.");
        }
         
        DtoGetString dto = new DtoGetString();
        dto.MethodName = methodBase.DeclaringType.Name + "." + methodBase.Name;
        dto.Params = DtoObject.ConstructArray(parameters, getParamTypes(methodBase));
        dto.Credentials = new Credentials();
        dto.Credentials.Username = Security.getCurUser().UserName;
        dto.Credentials.Password = Security.PasswordTyped;
        return RemotingClient.processGetString(dto);
    }

    //.CurUser.Password;
    /**
    * 
    */
    public static boolean getBool(MethodBase methodBase, Object... parameters) throws Exception {
        if (RemotingClient.RemotingRole != RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Meth.GetBool may only be used when RemotingRole is ClientWeb.");
        }
         
        DtoGetBool dto = new DtoGetBool();
        dto.MethodName = methodBase.DeclaringType.Name + "." + methodBase.Name;
        dto.Params = DtoObject.ConstructArray(parameters, getParamTypes(methodBase));
        dto.Credentials = new Credentials();
        dto.Credentials.Username = Security.getCurUser().UserName;
        dto.Credentials.Password = Security.PasswordTyped;
        return RemotingClient.processGetBool(dto);
    }

    //.CurUser.Password;
    private static Type[] getParamTypes(MethodBase methodBase) throws Exception {
        ParameterInfo[] paramInfo = methodBase.GetParameters();
        Type[] retVal = new Type[paramInfo.Length];
        for (int i = 0;i < paramInfo.Length;i++)
        {
            retVal[i] = paramInfo[i].ParameterType;
        }
        return retVal;
    }

}


