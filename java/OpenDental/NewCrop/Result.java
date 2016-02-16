//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.StatusType;


/**
* 
*/
public class Result   
{

    private StatusType statusField = StatusType.Unknown;
    private String messageField = new String();
    private String xmlResponseField = new String();
    private int rowCountField = new int();
    private int timingField = new int();
    /**
    * 
    */
    public StatusType getStatus() throws Exception {
        return this.statusField;
    }

    public void setStatus(StatusType value) throws Exception {
        this.statusField = value;
    }

    /**
    * 
    */
    public String getMessage() throws Exception {
        return this.messageField;
    }

    public void setMessage(String value) throws Exception {
        this.messageField = value;
    }

    /**
    * 
    */
    public String getXmlResponse() throws Exception {
        return this.xmlResponseField;
    }

    public void setXmlResponse(String value) throws Exception {
        this.xmlResponseField = value;
    }

    /**
    * 
    */
    public int getRowCount() throws Exception {
        return this.rowCountField;
    }

    public void setRowCount(int value) throws Exception {
        this.rowCountField = value;
    }

    /**
    * 
    */
    public int getTiming() throws Exception {
        return this.timingField;
    }

    public void setTiming(int value) throws Exception {
        this.timingField = value;
    }

}


