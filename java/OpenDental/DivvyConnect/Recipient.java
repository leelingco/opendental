//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental.DivvyConnect;


public class Recipient  extends Object implements System.Runtime.Serialization.IExtensibleDataObject
{

    private System.Runtime.Serialization.ExtensionDataObject extensionDataField = new System.Runtime.Serialization.ExtensionDataObject();
    private String Address1Field = new String();
    private String Address2Field = new String();
    private String CityField = new String();
    private String ExternalRecipientIDField = new String();
    private String NameField = new String();
    private String StateField = new String();
    private String ZipField = new String();
    public System.Runtime.Serialization.ExtensionDataObject getExtensionData() throws Exception {
        return this.extensionDataField;
    }

    public void setExtensionData(System.Runtime.Serialization.ExtensionDataObject value) throws Exception {
        this.extensionDataField = value;
    }

    public String getAddress1() throws Exception {
        return this.Address1Field;
    }

    public void setAddress1(String value) throws Exception {
        this.Address1Field = value;
    }

    public String getAddress2() throws Exception {
        return this.Address2Field;
    }

    public void setAddress2(String value) throws Exception {
        this.Address2Field = value;
    }

    public String getCity() throws Exception {
        return this.CityField;
    }

    public void setCity(String value) throws Exception {
        this.CityField = value;
    }

    public String getExternalRecipientID() throws Exception {
        return this.ExternalRecipientIDField;
    }

    public void setExternalRecipientID(String value) throws Exception {
        this.ExternalRecipientIDField = value;
    }

    public String getName() throws Exception {
        return this.NameField;
    }

    public void setName(String value) throws Exception {
        this.NameField = value;
    }

    public String getState() throws Exception {
        return this.StateField;
    }

    public void setState(String value) throws Exception {
        this.StateField = value;
    }

    public String getZip() throws Exception {
        return this.ZipField;
    }

    public void setZip(String value) throws Exception {
        this.ZipField = value;
    }

}


