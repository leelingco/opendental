//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental.DivvyConnect;



//------------------------------------------------------------------------------// <auto-generated>//     This code was generated by a tool.//     Runtime Version:2.0.50727.5466////     Changes to this file may cause incorrect behavior and will be lost if//     the code is regenerated.// </auto-generated>//------------------------------------------------------------------------------
public class Postcard  extends Object implements System.Runtime.Serialization.IExtensibleDataObject
{

    private System.Runtime.Serialization.ExtensionDataObject extensionDataField = new System.Runtime.Serialization.ExtensionDataObject();
    private System.DateTime AppointmentDateTimeField = new System.DateTime();
    private int DesignIDField = new int();
    private String ExternalPostcardIDField = new String();
    private String MessageField = new String();
    private OpenDental.DivvyConnect.Recipient RecipientField;
    public System.Runtime.Serialization.ExtensionDataObject getExtensionData() throws Exception {
        return this.extensionDataField;
    }

    public void setExtensionData(System.Runtime.Serialization.ExtensionDataObject value) throws Exception {
        this.extensionDataField = value;
    }

    public System.DateTime getAppointmentDateTime() throws Exception {
        return this.AppointmentDateTimeField;
    }

    public void setAppointmentDateTime(System.DateTime value) throws Exception {
        this.AppointmentDateTimeField = value;
    }

    public int getDesignID() throws Exception {
        return this.DesignIDField;
    }

    public void setDesignID(int value) throws Exception {
        this.DesignIDField = value;
    }

    public String getExternalPostcardID() throws Exception {
        return this.ExternalPostcardIDField;
    }

    public void setExternalPostcardID(String value) throws Exception {
        this.ExternalPostcardIDField = value;
    }

    public String getMessage() throws Exception {
        return this.MessageField;
    }

    public void setMessage(String value) throws Exception {
        this.MessageField = value;
    }

    public OpenDental.DivvyConnect.Recipient getRecipient() throws Exception {
        return this.RecipientField;
    }

    public void setRecipient(OpenDental.DivvyConnect.Recipient value) throws Exception {
        this.RecipientField = value;
    }

}


