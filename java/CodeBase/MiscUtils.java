//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:25 PM
//

package CodeBase;

import CS2JNet.System.StringSupport;

public class MiscUtils   
{
    public static <T>List<T> arrayToList(T[] array) throws Exception {
        List<T> list = new List<T>();
        for (int i = 0;i < array.Length;i++)
        {
            list.Add(array[i]);
        }
        return list;
    }

    public static String createRandomAlphaNumericString(int length) throws Exception {
        String result = "";
        Random rand = new Random();
        String randChrs = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        for (int i = 0;i < length;i++)
        {
            result += randChrs[rand.Next(0, randChrs.Length - 1)];
        }
        return result;
    }

    public static String decrypt(String encString) throws Exception {
        try
        {
            byte[] encrypted = Convert.FromBase64String(encString);
            MemoryStream ms = null;
            CryptoStream cs = null;
            StreamReader sr = null;
            Aes aes = new AesManaged();
            UTF8Encoding enc = new UTF8Encoding();
            aes.Key = enc.GetBytes("AKQjlLUjlcABVbqp");
            //Random string will be key
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
            MessageBox.Show("Text entered was not valid encrypted text.");
            return "";
        }
    
    }

    /**
    * Accepts a 3 character string which represents a neutral culture (for example, "eng" for English) in the ISO639-2 format.  Returns null if the three letter ISO639-2 name is not standard (useful for determining custom languages).
    */
    public static CultureInfo getCultureFromThreeLetter(String strThreeLetterISOname) throws Exception {
        if (strThreeLetterISOname == null || strThreeLetterISOname.Length != 3)
        {
            return null;
        }
         
        //Length check helps quickly identify custom languages.
        CultureInfo[] arrayCulturesNeutral = CultureInfo.GetCultures(CultureTypes.NeutralCultures);
        for (int i = 0;i < arrayCulturesNeutral.Length;i++)
        {
            if (StringSupport.equals(arrayCulturesNeutral[i].ThreeLetterISOLanguageName, strThreeLetterISOname))
            {
                return arrayCulturesNeutral[i];
            }
             
        }
        return null;
    }

}


