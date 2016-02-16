//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:25 PM
//

package CodeBase;


public class TopazWrapper   
{
    public static Control getTopaz() throws Exception {
        return new Topaz.SigPlusNET();
    }

    public static void clearTopaz(Control topaz) throws Exception {
        ((Topaz.SigPlusNET)topaz).ClearTablet();
        ((Topaz.SigPlusNET)topaz).SetTabletLogicalXSize(2000);
        ((Topaz.SigPlusNET)topaz).SetTabletLogicalYSize(600);
    }

    public static void setTopazCompressionMode(Control topaz, int compressionMode) throws Exception {
        ((Topaz.SigPlusNET)topaz).SetSigCompressionMode(compressionMode);
    }

    public static void setTopazEncryptionMode(Control topaz, int encryptionMode) throws Exception {
        ((Topaz.SigPlusNET)topaz).SetEncryptionMode(encryptionMode);
    }

    public static void setTopazKeyString(Control topaz, String str) throws Exception {
        ((Topaz.SigPlusNET)topaz).SetKeyString(str);
    }

    public static void setTopazAutoKeyData(Control topaz, String data) throws Exception {
        ((Topaz.SigPlusNET)topaz).AutoKeyStart();
        ((Topaz.SigPlusNET)topaz).SetAutoKeyData(data);
        ((Topaz.SigPlusNET)topaz).AutoKeyFinish();
    }

    public static void setTopazSigString(Control topaz, String signature) throws Exception {
        ((Topaz.SigPlusNET)topaz).SetSigString(signature);
    }

    /**
    * 0=disable signature capture.  1=enable.
    */
    public static void setTopazState(Control topaz, int state) throws Exception {
        ((Topaz.SigPlusNET)topaz).SetTabletState(state);
    }

    public static int getTopazNumberOfTabletPoints(Control topaz) throws Exception {
        return ((Topaz.SigPlusNET)topaz).NumberOfTabletPoints();
    }

    public static String getTopazString(Control topaz) throws Exception {
        return ((Topaz.SigPlusNET)topaz).GetSigString();
    }

}


