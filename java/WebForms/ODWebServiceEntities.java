//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:44 PM
//

package WebForms;

import WebForms.webforms_preference;
import WebForms.webforms_sheet;
import WebForms.webforms_sheetdef;
import WebForms.webforms_sheetfield;
import WebForms.webforms_sheetfielddef;


//------------------------------------------------------------------------------// <auto-generated>//    This code was generated from a template.////    Manual changes to this file may cause unexpected behavior in your application.//    Manual changes to this file will be overwritten if the code is regenerated.// </auto-generated>//------------------------------------------------------------------------------/**
* No Metadata Documentation available.
*/
public class ODWebServiceEntities  extends ObjectContext 
{

    /**
    * Initializes a new ODWebServiceEntities object using the connection string found in the 'ODWebServiceEntities' section of the application configuration file.
    */
    public ODWebServiceEntities() throws Exception {
        super("name=ODWebServiceEntities", "ODWebServiceEntities");
    
    }

    /**
    * Initialize a new ODWebServiceEntities object.
    */
    public ODWebServiceEntities(String connectionString) throws Exception {
        super(connectionString, "ODWebServiceEntities");
    
    }

    /**
    * Initialize a new ODWebServiceEntities object.
    */
    public ODWebServiceEntities(EntityConnection connection) throws Exception {
        super(connection, "ODWebServiceEntities");
    
    }

    /**
    * No Metadata Documentation available.
    */
    public ObjectSet<webforms_preference> getwebforms_preference() throws Exception {
        if ((_webforms_preference == null))
        {
            _webforms_preference = super.<webforms_preference>CreateObjectSet("webforms_preference");
        }
         
        return _webforms_preference;
    }

    private ObjectSet<webforms_preference> _webforms_preference = new ObjectSet<webforms_preference>();
    /**
    * No Metadata Documentation available.
    */
    public ObjectSet<webforms_sheet> getwebforms_sheet() throws Exception {
        if ((_webforms_sheet == null))
        {
            _webforms_sheet = super.<webforms_sheet>CreateObjectSet("webforms_sheet");
        }
         
        return _webforms_sheet;
    }

    private ObjectSet<webforms_sheet> _webforms_sheet = new ObjectSet<webforms_sheet>();
    /**
    * No Metadata Documentation available.
    */
    public ObjectSet<webforms_sheetdef> getwebforms_sheetdef() throws Exception {
        if ((_webforms_sheetdef == null))
        {
            _webforms_sheetdef = super.<webforms_sheetdef>CreateObjectSet("webforms_sheetdef");
        }
         
        return _webforms_sheetdef;
    }

    private ObjectSet<webforms_sheetdef> _webforms_sheetdef = new ObjectSet<webforms_sheetdef>();
    /**
    * No Metadata Documentation available.
    */
    public ObjectSet<webforms_sheetfield> getwebforms_sheetfield() throws Exception {
        if ((_webforms_sheetfield == null))
        {
            _webforms_sheetfield = super.<webforms_sheetfield>CreateObjectSet("webforms_sheetfield");
        }
         
        return _webforms_sheetfield;
    }

    private ObjectSet<webforms_sheetfield> _webforms_sheetfield = new ObjectSet<webforms_sheetfield>();
    /**
    * No Metadata Documentation available.
    */
    public ObjectSet<webforms_sheetfielddef> getwebforms_sheetfielddef() throws Exception {
        if ((_webforms_sheetfielddef == null))
        {
            _webforms_sheetfielddef = super.<webforms_sheetfielddef>CreateObjectSet("webforms_sheetfielddef");
        }
         
        return _webforms_sheetfielddef;
    }

    private ObjectSet<webforms_sheetfielddef> _webforms_sheetfielddef = new ObjectSet<webforms_sheetfielddef>();
    /**
    * Deprecated Method for adding a new object to the webforms_preference EntitySet. Consider using the .Add method of the associated ObjectSet<T> property instead.
    */
    public void addTowebforms_preference(webforms_preference webforms_preference) throws Exception {
        super.AddObject("webforms_preference", webforms_preference);
    }

    /**
    * Deprecated Method for adding a new object to the webforms_sheet EntitySet. Consider using the .Add method of the associated ObjectSet<T> property instead.
    */
    public void addTowebforms_sheet(webforms_sheet webforms_sheet) throws Exception {
        super.AddObject("webforms_sheet", webforms_sheet);
    }

    /**
    * Deprecated Method for adding a new object to the webforms_sheetdef EntitySet. Consider using the .Add method of the associated ObjectSet<T> property instead.
    */
    public void addTowebforms_sheetdef(webforms_sheetdef webforms_sheetdef) throws Exception {
        super.AddObject("webforms_sheetdef", webforms_sheetdef);
    }

    /**
    * Deprecated Method for adding a new object to the webforms_sheetfield EntitySet. Consider using the .Add method of the associated ObjectSet<T> property instead.
    */
    public void addTowebforms_sheetfield(webforms_sheetfield webforms_sheetfield) throws Exception {
        super.AddObject("webforms_sheetfield", webforms_sheetfield);
    }

    /**
    * Deprecated Method for adding a new object to the webforms_sheetfielddef EntitySet. Consider using the .Add method of the associated ObjectSet<T> property instead.
    */
    public void addTowebforms_sheetfielddef(webforms_sheetfielddef webforms_sheetfielddef) throws Exception {
        super.AddObject("webforms_sheetfielddef", webforms_sheetfielddef);
    }

}

