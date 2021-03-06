//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:07:58 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class DocumentMiscCrud   
{
    /**
    * Gets one DocumentMisc object from the database using the primary key.  Returns null if not found.
    */
    public static DocumentMisc selectOne(long docMiscNum) throws Exception {
        String command = "SELECT * FROM documentmisc " + "WHERE DocMiscNum = " + POut.Long(docMiscNum);
        List<DocumentMisc> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one DocumentMisc object from the database using a query.
    */
    public static DocumentMisc selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<DocumentMisc> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of DocumentMisc objects from the database using a query.
    */
    public static List<DocumentMisc> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<DocumentMisc> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<DocumentMisc> tableToList(DataTable table) throws Exception {
        List<DocumentMisc> retVal = new List<DocumentMisc>();
        DocumentMisc documentMisc = new DocumentMisc();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            documentMisc = new DocumentMisc();
            documentMisc.DocMiscNum = PIn.Long(table.Rows[i]["DocMiscNum"].ToString());
            documentMisc.DateCreated = PIn.Date(table.Rows[i]["DateCreated"].ToString());
            documentMisc.FileName = PIn.String(table.Rows[i]["FileName"].ToString());
            documentMisc.DocMiscType = (DocumentMiscType)PIn.Int(table.Rows[i]["DocMiscType"].ToString());
            documentMisc.RawBase64 = PIn.String(table.Rows[i]["RawBase64"].ToString());
            retVal.Add(documentMisc);
        }
        return retVal;
    }

    /**
    * Inserts one DocumentMisc into the database.  Returns the new priKey.
    */
    public static long insert(DocumentMisc documentMisc) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            documentMisc.DocMiscNum = DbHelper.GetNextOracleKey("documentmisc", "DocMiscNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(documentMisc,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        documentMisc.DocMiscNum++;
                        loopcount++;
                    }
                    else
                    {
                        throw ex;
                    } 
                }
            
            }
            throw new ApplicationException("Insert failed.  Could not generate primary key.");
        }
        else
        {
            return insert(documentMisc,false);
        } 
    }

    /**
    * Inserts one DocumentMisc into the database.  Provides option to use the existing priKey.
    */
    public static long insert(DocumentMisc documentMisc, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            documentMisc.DocMiscNum = ReplicationServers.GetKey("documentmisc", "DocMiscNum");
        }
         
        String command = "INSERT INTO documentmisc (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "DocMiscNum,";
        }
         
        command += "DateCreated,FileName,DocMiscType,RawBase64) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(documentMisc.DocMiscNum) + ",";
        }
         
        command += POut.Date(documentMisc.DateCreated) + "," + "'" + POut.String(documentMisc.FileName) + "'," + POut.Int((int)documentMisc.DocMiscType) + "," + DbHelper.ParamChar + "paramRawBase64)";
        if (documentMisc.RawBase64 == null)
        {
            documentMisc.RawBase64 = "";
        }
         
        OdSqlParameter paramRawBase64 = new OdSqlParameter("paramRawBase64", OdDbType.Text, documentMisc.RawBase64);
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command, paramRawBase64);
        }
        else
        {
            documentMisc.DocMiscNum = Db.NonQ(command, true, paramRawBase64);
        } 
        return documentMisc.DocMiscNum;
    }

    /**
    * Updates one DocumentMisc in the database.
    */
    public static void update(DocumentMisc documentMisc) throws Exception {
        String command = "UPDATE documentmisc SET " + "DateCreated=  " + POut.Date(documentMisc.DateCreated) + ", " + "FileName   = '" + POut.String(documentMisc.FileName) + "', " + "DocMiscType=  " + POut.Int((int)documentMisc.DocMiscType) + ", " + "RawBase64  =  " + DbHelper.ParamChar + "paramRawBase64 " + "WHERE DocMiscNum = " + POut.Long(documentMisc.DocMiscNum);
        if (documentMisc.RawBase64 == null)
        {
            documentMisc.RawBase64 = "";
        }
         
        OdSqlParameter paramRawBase64 = new OdSqlParameter("paramRawBase64", OdDbType.Text, documentMisc.RawBase64);
        Db.NonQ(command, paramRawBase64);
    }

    /**
    * Updates one DocumentMisc in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(DocumentMisc documentMisc, DocumentMisc oldDocumentMisc) throws Exception {
        String command = "";
        if (documentMisc.DateCreated != oldDocumentMisc.DateCreated)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DateCreated = " + POut.Date(documentMisc.DateCreated) + "";
        }
         
        if (documentMisc.FileName != oldDocumentMisc.FileName)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FileName = '" + POut.String(documentMisc.FileName) + "'";
        }
         
        if (documentMisc.DocMiscType != oldDocumentMisc.DocMiscType)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DocMiscType = " + POut.Int((int)documentMisc.DocMiscType) + "";
        }
         
        if (documentMisc.RawBase64 != oldDocumentMisc.RawBase64)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "RawBase64 = " + DbHelper.ParamChar + "paramRawBase64";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        if (documentMisc.RawBase64 == null)
        {
            documentMisc.RawBase64 = "";
        }
         
        OdSqlParameter paramRawBase64 = new OdSqlParameter("paramRawBase64", OdDbType.Text, documentMisc.RawBase64);
        command = "UPDATE documentmisc SET " + command + " WHERE DocMiscNum = " + POut.Long(documentMisc.DocMiscNum);
        Db.NonQ(command, paramRawBase64);
    }

    /**
    * Deletes one DocumentMisc from the database.
    */
    public static void delete(long docMiscNum) throws Exception {
        String command = "DELETE FROM documentmisc " + "WHERE DocMiscNum = " + POut.Long(docMiscNum);
        Db.NonQ(command);
    }

}


