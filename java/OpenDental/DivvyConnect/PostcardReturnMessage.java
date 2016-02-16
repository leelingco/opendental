//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental.DivvyConnect;


public class PostcardReturnMessage  extends Object implements System.Runtime.Serialization.IExtensibleDataObject
{

    private System.Runtime.Serialization.ExtensionDataObject extensionDataField = new System.Runtime.Serialization.ExtensionDataObject();
    private String MessageField = new String();
    private OpenDental.DivvyConnect.MessageCode MessageCodeField = OpenDental.DivvyConnect.MessageCode.CompletedSucessfully;
    private OpenDental.DivvyConnect.SinglePostcardMessage[] PostcardMessagesField = new OpenDental.DivvyConnect.SinglePostcardMessage[]();
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

    public OpenDental.DivvyConnect.MessageCode getMessageCode() throws Exception {
        return this.MessageCodeField;
    }

    public void setMessageCode(OpenDental.DivvyConnect.MessageCode value) throws Exception {
        this.MessageCodeField = value;
    }

    public OpenDental.DivvyConnect.SinglePostcardMessage[] getPostcardMessages() throws Exception {
        return this.PostcardMessagesField;
    }

    public void setPostcardMessages(OpenDental.DivvyConnect.SinglePostcardMessage[] value) throws Exception {
        this.PostcardMessagesField = value;
    }

}


