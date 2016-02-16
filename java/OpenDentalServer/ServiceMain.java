//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDentalServer;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.DataTransferObject;
import OpenDentBusiness.Db;
import OpenDentBusiness.DtoException;
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
import OpenDentBusiness.Reports;
import OpenDentBusiness.Userods;
import OpenDentBusiness.XmlConverter;

/**
* 
*/
public class ServiceMain  extends System.Web.Services.WebService 
{
    /**
    * Pass in a serialized dto.  It returns a dto which must be deserialized by the client.
    */
    public String processRequest(String dtoString) throws Exception {
        //The web service (xml) serializer/deserializer is removing the '\r' portion of our newlines during the data transfer.
        //Replacing the string is not the best solution but it works for now. The replacing happens here (server side) and after result is returned on the client side.
        //It's done server side for usage purposes within the methods being called (exampe: inserting into db) and then on the client side for displaying purposes.
        dtoString = dtoString.Replace("\n", "\r\n");
        DataTransferObject dto = DataTransferObject.deserialize(dtoString);
        try
        {
            //XmlSerializer serializer;
            Type type = dto.GetType();
            if (type == DtoGetTable.class)
            {
                DtoGetTable dtoGetTable = (DtoGetTable)dto;
                Userods.checkCredentials(dtoGetTable.Credentials);
                //will throw exception if fails.
                String className = dtoGetTable.MethodName.Split('.')[0];
                String methodName = dtoGetTable.MethodName.Split('.')[1];
                String assemb = Assembly.GetAssembly(Db.class).FullName;
                //any OpenDentBusiness class will do.
                Type classType = Type.GetType("OpenDentBusiness." + className + "," + assemb);
                DtoObject[] parameters = dtoGetTable.Params;
                Type[] paramTypes = DtoObject.GenerateTypes(parameters, assemb);
                MethodInfo methodInfo = classType.GetMethod(methodName, paramTypes);
                if (methodInfo == null)
                {
                    throw new ApplicationException("Method not found with " + parameters.Length.ToString() + " parameters: " + dtoGetTable.MethodName);
                }
                 
                Object[] paramObjs = DtoObject.GenerateObjects(parameters);
                DataTable dt = (DataTable)methodInfo.Invoke(null, paramObjs);
                String response = XmlConverter.tableToXml(dt);
                return response;
            }
            else if (type == DtoGetTableLow.class)
            {
                DtoGetTableLow dtoGetTableLow = (DtoGetTableLow)dto;
                Userods.checkCredentials(dtoGetTableLow.Credentials);
                //will throw exception if fails.
                DtoObject[] parameters = dtoGetTableLow.Params;
                Object[] paramObjs = DtoObject.GenerateObjects(parameters);
                DataTable dt = Reports.getTable((String)paramObjs[0]);
                String response = XmlConverter.tableToXml(dt);
                return response;
            }
            else if (type == DtoGetDS.class)
            {
                DtoGetDS dtoGetDS = (DtoGetDS)dto;
                Userods.checkCredentials(dtoGetDS.Credentials);
                //will throw exception if fails.
                String className = dtoGetDS.MethodName.Split('.')[0];
                String methodName = dtoGetDS.MethodName.Split('.')[1];
                String assemb = Assembly.GetAssembly(Db.class).FullName;
                //any OpenDentBusiness class will do.
                Type classType = Type.GetType("OpenDentBusiness." + className + "," + assemb);
                DtoObject[] parameters = dtoGetDS.Params;
                Type[] paramTypes = DtoObject.GenerateTypes(parameters, assemb);
                MethodInfo methodInfo = classType.GetMethod(methodName, paramTypes);
                if (methodInfo == null)
                {
                    throw new ApplicationException("Method not found with " + parameters.Length.ToString() + " parameters: " + dtoGetDS.MethodName);
                }
                 
                Object[] paramObjs = DtoObject.GenerateObjects(parameters);
                DataSet ds = (DataSet)methodInfo.Invoke(null, paramObjs);
                String response = XmlConverter.dsToXml(ds);
                return response;
            }
            else if (type == DtoGetLong.class)
            {
                DtoGetLong dtoGetLong = (DtoGetLong)dto;
                Userods.checkCredentials(dtoGetLong.Credentials);
                //will throw exception if fails.
                String className = dtoGetLong.MethodName.Split('.')[0];
                String methodName = dtoGetLong.MethodName.Split('.')[1];
                String assemb = Assembly.GetAssembly(Db.class).FullName;
                //any OpenDentBusiness class will do.
                Type classType = Type.GetType("OpenDentBusiness." + className + "," + assemb);
                DtoObject[] parameters = dtoGetLong.Params;
                Type[] paramTypes = DtoObject.GenerateTypes(parameters, assemb);
                MethodInfo methodInfo = classType.GetMethod(methodName, paramTypes);
                if (methodInfo == null)
                {
                    throw new ApplicationException("Method not found with " + parameters.Length.ToString() + " parameters: " + dtoGetLong.MethodName);
                }
                 
                Object[] paramObjs = DtoObject.GenerateObjects(parameters);
                long longResult = (long)methodInfo.Invoke(null, paramObjs);
                return longResult.ToString();
            }
            else if (type == DtoGetInt.class)
            {
                DtoGetInt dtoGetInt = (DtoGetInt)dto;
                Userods.checkCredentials(dtoGetInt.Credentials);
                //will throw exception if fails.
                String className = dtoGetInt.MethodName.Split('.')[0];
                String methodName = dtoGetInt.MethodName.Split('.')[1];
                String assemb = Assembly.GetAssembly(Db.class).FullName;
                //any OpenDentBusiness class will do.
                Type classType = Type.GetType("OpenDentBusiness." + className + "," + assemb);
                DtoObject[] parameters = dtoGetInt.Params;
                Type[] paramTypes = DtoObject.GenerateTypes(parameters, assemb);
                MethodInfo methodInfo = classType.GetMethod(methodName, paramTypes);
                if (methodInfo == null)
                {
                    throw new ApplicationException("Method not found with " + parameters.Length.ToString() + " parameters: " + dtoGetInt.MethodName);
                }
                 
                Object[] paramObjs = DtoObject.GenerateObjects(parameters);
                int intResult = (int)methodInfo.Invoke(null, paramObjs);
                return intResult.ToString();
            }
            else if (type == DtoGetVoid.class)
            {
                DtoGetVoid dtoGetVoid = (DtoGetVoid)dto;
                Userods.checkCredentials(dtoGetVoid.Credentials);
                //will throw exception if fails.
                String className = dtoGetVoid.MethodName.Split('.')[0];
                String methodName = dtoGetVoid.MethodName.Split('.')[1];
                String assemb = Assembly.GetAssembly(Db.class).FullName;
                //any OpenDentBusiness class will do.
                Type classType = Type.GetType("OpenDentBusiness." + className + "," + assemb);
                DtoObject[] parameters = dtoGetVoid.Params;
                Type[] paramTypes = DtoObject.GenerateTypes(parameters, assemb);
                MethodInfo methodInfo = classType.GetMethod(methodName, paramTypes);
                if (methodInfo == null)
                {
                    throw new ApplicationException("Method not found with " + parameters.Length.ToString() + " parameters: " + dtoGetVoid.MethodName);
                }
                 
                Object[] paramObjs = DtoObject.GenerateObjects(parameters);
                methodInfo.Invoke(null, paramObjs);
                return "0";
            }
            else if (type == DtoGetObject.class)
            {
                DtoGetObject dtoGetObject = (DtoGetObject)dto;
                String className = dtoGetObject.MethodName.Split('.')[0];
                String methodName = dtoGetObject.MethodName.Split('.')[1];
                if (!StringSupport.equals(className, "Security") || !StringSupport.equals(methodName, "LogInWeb"))
                {
                    //because credentials will be checked inside that method
                    Userods.checkCredentials(dtoGetObject.Credentials);
                }
                 
                //will throw exception if fails.
                String assemb = Assembly.GetAssembly(Db.class).FullName;
                //any OpenDentBusiness class will do.
                Type classType = Type.GetType("OpenDentBusiness." + className + "," + assemb);
                DtoObject[] parameters = dtoGetObject.Params;
                Type[] paramTypes = DtoObject.GenerateTypes(parameters, assemb);
                MethodInfo methodInfo = classType.GetMethod(methodName, paramTypes);
                if (methodInfo == null)
                {
                    throw new ApplicationException("Method not found with " + parameters.Length.ToString() + " parameters: " + dtoGetObject.MethodName);
                }
                 
                if (StringSupport.equals(className, "Security") && StringSupport.equals(methodName, "LogInWeb"))
                {
                    String mappedPath = Server.MapPath(".");
                    parameters[2] = new DtoObject(mappedPath, String.class);
                    //because we can't access this variable from within OpenDentBusiness.
                    RemotingClient.RemotingRole = RemotingRole.ServerWeb;
                }
                 
                Object[] paramObjs = DtoObject.GenerateObjects(parameters);
                Object objResult = methodInfo.Invoke(null, paramObjs);
                Type returnType = methodInfo.ReturnType;
                return XmlConverter.serialize(returnType,objResult);
            }
            else if (type == DtoGetString.class)
            {
                DtoGetString dtoGetString = (DtoGetString)dto;
                Userods.checkCredentials(dtoGetString.Credentials);
                //will throw exception if fails.
                String className = dtoGetString.MethodName.Split('.')[0];
                String methodName = dtoGetString.MethodName.Split('.')[1];
                String assemb = Assembly.GetAssembly(Db.class).FullName;
                //any OpenDentBusiness class will do.
                Type classType = Type.GetType("OpenDentBusiness." + className + "," + assemb);
                DtoObject[] parameters = dtoGetString.Params;
                Type[] paramTypes = DtoObject.GenerateTypes(parameters, assemb);
                MethodInfo methodInfo = classType.GetMethod(methodName, paramTypes);
                if (methodInfo == null)
                {
                    throw new ApplicationException("Method not found with " + parameters.Length.ToString() + " parameters: " + dtoGetString.MethodName);
                }
                 
                Object[] paramObjs = DtoObject.GenerateObjects(parameters);
                String strResult = (String)methodInfo.Invoke(null, paramObjs);
                return strResult;
            }
            else //strResult=strResult.Replace("\r","\\r");
            //return XmlConverter.Serialize(typeof(string),strResult);
            if (type == DtoGetBool.class)
            {
                DtoGetBool dtoGetBool = (DtoGetBool)dto;
                Userods.checkCredentials(dtoGetBool.Credentials);
                //will throw exception if fails.
                String className = dtoGetBool.MethodName.Split('.')[0];
                String methodName = dtoGetBool.MethodName.Split('.')[1];
                String assemb = Assembly.GetAssembly(Db.class).FullName;
                //any OpenDentBusiness class will do.
                Type classType = Type.GetType("OpenDentBusiness." + className + "," + assemb);
                DtoObject[] parameters = dtoGetBool.Params;
                Type[] paramTypes = DtoObject.GenerateTypes(parameters, assemb);
                MethodInfo methodInfo = classType.GetMethod(methodName, paramTypes);
                if (methodInfo == null)
                {
                    throw new ApplicationException("Method not found with " + parameters.Length.ToString() + " parameters: " + dtoGetBool.MethodName);
                }
                 
                Object[] paramObjs = DtoObject.GenerateObjects(parameters);
                boolean boolResult = (boolean)methodInfo.Invoke(null, paramObjs);
                return boolResult.ToString();
            }
            else
            {
                throw new NotSupportedException("Dto type not supported: " + type.FullName);
            }         
        }
        catch (Exception e)
        {
            DtoException exception = new DtoException();
            if (e.InnerException == null)
            {
                exception.Message = e.Message;
            }
            else
            {
                exception.Message = e.InnerException.Message;
            } 
            return exception.serialize();
        }
    
    }

}


