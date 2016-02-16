//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:32 PM
//

package fyiReporting.RDL;


public class AsciiHexEncode   
{
    byte[] bain = new byte[]();
    final int width = 72;
    // max characters per line
    public AsciiHexEncode(byte[] ba) throws Exception {
        bain = ba;
    }

    public String toString() {
        try
        {
            StringWriter sw = new StringWriter();
            int pos = 0;
            for (int i = 0;i < bain.Length;i++)
            {
                if (pos >= width)
                {
                    sw.Write('\n');
                    pos = 0;
                }
                 
                String t = Convert.ToString(bain[i], 16);
                if (t.Length == 1)
                    t = "0" + t;
                 
                sw.Write(t);
                pos += 2;
            }
            String baout = sw.ToString();
            sw.Close();
            sw = null;
            return baout;
        }
        catch (RuntimeException __dummyCatchVar1)
        {
            throw __dummyCatchVar1;
        }
        catch (Exception __dummyCatchVar1)
        {
            throw new RuntimeException(__dummyCatchVar1);
        }
    
    }

}


