//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:37 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.CentralConnection;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class CentralConnections   
{
    /**
    * 
    */
    public static List<CentralConnection> refresh(String searchString) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<CentralConnection>>GetObject(MethodBase.GetCurrentMethod(), searchString);
        }
         
        String command = "SELECT * FROM centralconnection " + "WHERE ServiceURI LIKE '%" + POut.string(searchString) + "%' " + "OR ServerName LIKE '%" + POut.string(searchString) + "%' " + "OR DatabaseName LIKE '%" + POut.string(searchString) + "%' " + "ORDER BY ItemOrder";
        return Crud.CentralConnectionCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(CentralConnection centralConnection) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            centralConnection.CentralConnectionNum = Meth.GetLong(MethodBase.GetCurrentMethod(), centralConnection);
            return centralConnection.CentralConnectionNum;
        }
         
        return Crud.CentralConnectionCrud.Insert(centralConnection);
    }

    /**
    * 
    */
    public static void update(CentralConnection centralConnection) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), centralConnection);
            return ;
        }
         
        Crud.CentralConnectionCrud.Update(centralConnection);
    }

    /**
    * 
    */
    public static void delete(long centralConnectionNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), centralConnectionNum);
            return ;
        }
         
        String command = "DELETE FROM centralconnection WHERE CentralConnectionNum = " + POut.long(centralConnectionNum);
        Db.nonQ(command);
    }

    /**
    * Encrypts signature text and returns a base 64 string so that it can go directly into the database.
    */
    public static String encrypt(String str, byte[] key) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (StringSupport.equals(str, ""))
        {
            return "";
        }
         
        byte[] ecryptBytes = Encoding.UTF8.GetBytes(str);
        MemoryStream ms = new MemoryStream();
        CryptoStream cs = null;
        Aes aes = new AesManaged();
        aes.Key = key;
        aes.IV = new byte[16];
        ICryptoTransform encryptor = aes.CreateEncryptor(aes.Key, aes.IV);
        cs = new CryptoStream(ms, encryptor, CryptoStreamMode.Write);
        cs.Write(ecryptBytes, 0, ecryptBytes.Length);
        cs.FlushFinalBlock();
        byte[] encryptedBytes = new byte[ms.Length];
        ms.Position = 0;
        ms.Read(encryptedBytes, 0, (int)ms.Length);
        cs.Dispose();
        ms.Dispose();
        if (aes != null)
        {
            aes.Clear();
        }
         
        return Convert.ToBase64String(encryptedBytes);
    }

    public static String decrypt(String str, byte[] key) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (StringSupport.equals(str, ""))
        {
            return "";
        }
         
        try
        {
            byte[] encrypted = Convert.FromBase64String(str);
            MemoryStream ms = null;
            CryptoStream cs = null;
            StreamReader sr = null;
            Aes aes = new AesManaged();
            aes.Key = key;
            aes.IV = new byte[16];
            ICryptoTransform decryptor = aes.CreateDecryptor(aes.Key, aes.IV);
            ms = new MemoryStream(encrypted);
            cs = new CryptoStream(ms, decryptor, CryptoStreamMode.Read);
            sr = new StreamReader(cs);
            String decrypted = sr.ReadToEnd();
            ms.Dispose();
            cs.Dispose();
            sr.Dispose();
            if (aes != null)
            {
                aes.Clear();
            }
             
            return decrypted;
        }
        catch (Exception __dummyCatchVar0)
        {
            return "";
        }
    
    }

    //MessageBox.Show("Text entered was not valid encrypted text.");
    /**
    * 
    */
    private static String generateHash(String message) throws Exception {
        //No need to check RemotingRole; no call to db.
        byte[] data = Encoding.ASCII.GetBytes(message);
        HashAlgorithm algorithm = SHA1.Create();
        byte[] hashbytes = algorithm.ComputeHash(data);
        byte digit1 = new byte();
        byte digit2 = new byte();
        String char1 = new String();
        String char2 = new String();
        StringBuilder strHash = new StringBuilder();
        for (int i = 0;i < hashbytes.Length;i++)
        {
            if (hashbytes[i] == 0)
            {
                digit1 = 0;
                digit2 = 0;
            }
            else
            {
                digit1 = (byte)Math.Floor((double)hashbytes[i] / 16d);
                //double remainder=Math.IEEERemainder((double)hashbytes[i],16d);
                digit2 = (byte)(hashbytes[i] - (byte)(16 * digit1));
            } 
            char1 = ByteToStr(digit1);
            char2 = ByteToStr(digit2);
            strHash.Append(char1);
            strHash.Append(char2);
        }
        return strHash.ToString();
    }

    /**
    * The only valid input is a value between 0 and 15.  Text returned will be 1-9 or a-f.
    */
    private static String byteToStr(Byte byteVal) throws Exception {
        //No need to check RemotingRole; no call to db.
        Byte __dummyScrutVar0 = byteVal;
        if (__dummyScrutVar0.equals(10))
        {
            return "a";
        }
        else if (__dummyScrutVar0.equals(11))
        {
            return "b";
        }
        else if (__dummyScrutVar0.equals(12))
        {
            return "c";
        }
        else if (__dummyScrutVar0.equals(13))
        {
            return "d";
        }
        else if (__dummyScrutVar0.equals(14))
        {
            return "e";
        }
        else if (__dummyScrutVar0.equals(15))
        {
            return "f";
        }
        else
        {
            return byteVal.ToString();
        }      
    }

}


/*
		
		///<summary>Gets one CentralConnection from the db.</summary>
		public static CentralConnection GetOne(long centralConnectionNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<CentralConnection>(MethodBase.GetCurrentMethod(),centralConnectionNum);
			}
			return Crud.CentralConnectionCrud.SelectOne(centralConnectionNum);
		}
		
		*/