//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental.DivvyConnect;


public class SinglePostcardMessage  extends Object implements System.Runtime.Serialization.IExtensibleDataObject
{

    private System.Runtime.Serialization.ExtensionDataObject extensionDataField = new System.Runtime.Serialization.ExtensionDataObject();
    private String MessageField = new String();
    private OpenDental.DivvyConnect.PostcardCode postcardCodeField = OpenDental.DivvyConnect.PostcardCode.Successful;
    private OpenDental.DivvyConnect.Postcard postcardRecordField;
    public System.Runtime.Serialization.ExtensionDataObject getExtensionData() throws Exception {
        return this.extensionDataField;
    }

    public void setExtensionData(System.Runtime.Serialization.ExtensionDataObject value) throws Exception {
        this.extensionDataField = value;
    }

    public String getMessage() throws Exception {
        return this.MessageField;
    }

    public void setMessage(String value) throws Exception {
        this.MessageField = value;
    }

    public OpenDental.DivvyConnect.PostcardCode getpostcardCode() throws Exception {
        return this.postcardCodeField;
    }

    public void setpostcardCode(OpenDental.DivvyConnect.PostcardCode value) throws Exception {
        this.postcardCodeField = value;
    }

    public OpenDental.DivvyConnect.Postcard getpostcardRecord() throws Exception {
        return this.postcardRecordField;
    }

    public void setpostcardRecord(OpenDental.DivvyConnect.Postcard value) throws Exception {
        this.postcardRecordField = value;
    }

}


