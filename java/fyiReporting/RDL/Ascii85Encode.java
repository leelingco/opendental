//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:32 PM
//

package fyiReporting.RDL;


public class Ascii85Encode   
{
    byte[] bain = new byte[]();
    final uint width = 72;
    // max characters per line
    uint pos = new uint();
    // tracks # of characters put out in line
    uint tuple = 0;
    int count = 0;
    StringWriter sw = new StringWriter();
    public Ascii85Encode(byte[] ba) throws Exception {
        bain = ba;
    }

    public String toString() {
        try
        {
            sw = new StringWriter();
            tuple = 0;
            count = 0;
            sw.Write("<~");
            pos = 2;
            byte b = new byte();
            for (int i = 0;i < bain.Length;i++)
            {
                b = bain[i];
                switch(count++)
                {
                    case 0: 
                        tuple |= ((uint)b << 24);
                        break;
                    case 1: 
                        tuple |= ((uint)b << 16);
                        break;
                    case 2: 
                        tuple |= ((uint)b << 8);
                        break;
                    case 3: 
                        tuple |= b;
                        if (tuple == 0)
                        {
                            sw.Write('z');
                            if (pos++ >= width)
                            {
                                pos = 0;
                                sw.Write('\n');
                            }
                             
                        }
                        else
                        {
                            encode(tuple,count);
                        } 
                        tuple = 0;
                        count = 0;
                        break;
                
                }
            }
            // handle some clean up at end of processing
            if (count > 0)
                encode(tuple,count);
             
            if (pos + 2 > width)
                sw.Write('\n');
             
            sw.Write("~>\n");
            String baout = sw.ToString();
            sw.Close();
            sw = null;
            return baout;
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

    void encode(uint tuple, int count) throws Exception {
        int j = new int();
        char[] buf = new char[5];
        int s = 0;
        j = 5;
        do
        {
            buf[s++] = (char)(tuple % 85);
            tuple /= 85;
        }
        while (--j > 0);
        j = count;
        do
        {
            sw.Write((char)(buf[--s] + '!'));
            // '!' == 32
            if (pos++ >= width)
            {
                pos = 0;
                sw.Write('\n');
            }
             
        }
        while (j-- > 0);
    }

}


