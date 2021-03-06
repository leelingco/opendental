//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:07 PM
//

package OpenDentBusiness.Properties;

import OpenDentBusiness.Properties.Resources;

//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.18052
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------
/**
* A strongly-typed resource class, for looking up localized strings, etc.
*/
// This class was auto-generated by the StronglyTypedResourceBuilder
// class via a tool like ResGen or Visual Studio.
// To add or remove a member, edit your .ResX file then rerun ResGen
// with the /str option, or rebuild your VS project.
public class Resources   
{
    private static System.Resources.ResourceManager resourceMan = new System.Resources.ResourceManager();
    private static System.Globalization.CultureInfo resourceCulture = new System.Globalization.CultureInfo();
    public Resources() throws Exception {
    }

    /**
    * Returns the cached ResourceManager instance used by this class.
    */
    public static System.Resources.ResourceManager getResourceManager() throws Exception {
        if (Object.ReferenceEquals(resourceMan, null))
        {
            System.Resources.ResourceManager temp = new System.Resources.ResourceManager("OpenDentBusiness.Properties.Resources", Resources.class.Assembly);
            resourceMan = temp;
        }
         
        return resourceMan;
    }

    /**
    * Overrides the current thread's CurrentUICulture property for all
    * resource lookups using this strongly typed resource class.
    */
    public static System.Globalization.CultureInfo getCulture() throws Exception {
        return resourceCulture;
    }

    public static void setCulture(System.Globalization.CultureInfo value) throws Exception {
        resourceCulture = value;
    }

    /**
    * Looks up a localized resource of type System.Drawing.Bitmap.
    */
    public static System.Drawing.Bitmap getApptBackTest() throws Exception {
        Object obj = getResourceManager().GetObject("ApptBackTest", resourceCulture);
        return ((System.Drawing.Bitmap)(obj));
    }

    /**
    * Looks up a localized string similar to The primary key could not be overriden, because either the object is not new and has been loaded from the database or the object does not contain an Identity key..
    */
    public static String getCannotOverridePrimaryKey() throws Exception {
        return getResourceManager().GetString("CannotOverridePrimaryKey", resourceCulture);
    }

    /**
    * Looks up a localized string similar to The object has been deleted, and can not be saved again to the database..
    */
    public static String getCannotSaveDeletedObject() throws Exception {
        return getResourceManager().GetString("CannotSaveDeletedObject", resourceCulture);
    }

    /**
    * Looks up a localized string similar to The current data type is not supported by the PIn class..
    */
    public static String getDataTypeNotSupportedByPIn() throws Exception {
        return getResourceManager().GetString("DataTypeNotSupportedByPIn", resourceCulture);
    }

    /**
    * Looks up a localized string similar to The current data type ({0}) is not supported by the POut class..
    */
    public static String getDataTypeNotSupportedByPOut() throws Exception {
        return getResourceManager().GetString("DataTypeNotSupportedByPOut", resourceCulture);
    }

    /**
    * Looks up a localized string similar to A DTO of type {0} is not supported..
    */
    public static String getDtoNotSupportedException() throws Exception {
        return getResourceManager().GetString("DtoNotSupportedException", resourceCulture);
    }

    /**
    * Looks up a localized string similar to The current object does not contain any data fields..
    */
    public static String getNoFields() throws Exception {
        return getResourceManager().GetString("NoFields", resourceCulture);
    }

    /**
    * Looks up a localized string similar to This object has a primary key that consists of multiple columns..
    */
    public static String getNotASinglePrimaryKey() throws Exception {
        return getResourceManager().GetString("NotASinglePrimaryKey", resourceCulture);
    }

    /**
    * Looks up a localized string similar to The object has already been deleted, and cannot be deleted twice..
    */
    public static String getObjectAlreadyDeleted() throws Exception {
        return getResourceManager().GetString("ObjectAlreadyDeleted", resourceCulture);
    }

    /**
    * Looks up a localized string similar to The object does not exist in the database, and can therefore not be deleted..
    */
    public static String getObjectNotSaved() throws Exception {
        return getResourceManager().GetString("ObjectNotSaved", resourceCulture);
    }

    /**
    * Looks up a localized string similar to The primary key is not of the type Integer..
    */
    public static String getPrimaryKeyNotAnInteger() throws Exception {
        return getResourceManager().GetString("PrimaryKeyNotAnInteger", resourceCulture);
    }

    /**
    * Looks up a localized string similar to An attribute of type "DataFieldAttribute" can only be specified once..
    */
    public static String getTooManyDataFieldAttributes() throws Exception {
        return getResourceManager().GetString("TooManyDataFieldAttributes", resourceCulture);
    }

}


