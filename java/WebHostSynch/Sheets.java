//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:40 PM
//

package WebHostSynch;

import OpenDentBusiness.SheetDef;
import WebForms.Logger;
import WebForms.ODWebServiceEntities;
import WebForms.webforms_preference;
import WebForms.webforms_sheet;
import WebForms.webforms_sheetdef;
import WebForms.webforms_sheetfield;
import WebForms.webforms_sheetfielddef;
import WebHostSynch.Properties.Settings;

/**
* Dennis Mathew: For using ADO.NET Entity Data Model/LINQ with Mysql/Visual Studio 2010, download and install Connector/Net from http://dev.mysql.com/downloads/connector/net/
* Connector/Net is a ADO.NET driver for MySQL.
* The web server which hosts the webservice will also need this install.
* The integration with Visual Studio can be flaky. So a few cycles of install/uninstall/restart may be needed. I've also tried the non-install options of adding dlls but they don't seem to work in the few attempts that I made.
* 
* Summary description for WebHostSynch
*/
// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line.
// [System.Web.Script.Services.ScriptService]
public class Sheets  extends System.Web.Services.WebService 
{
    private WebHostSynch.Util util = new WebHostSynch.Util();
    /**
    * An inner class made  for just transferring both the sheets and it's fields simultaneously in a web service which otherwise has to transferred seperately.
    */
    public static class SheetAndSheetField   
    {
        public webforms_sheet web_sheet = null;
        public List<webforms_sheetfield> web_sheetfieldlist = null;
        public SheetAndSheetField() throws Exception {
        }

        public SheetAndSheetField(webforms_sheet web_sheet, List<webforms_sheetfield> web_sheetfieldlist) throws Exception {
            this.web_sheet = web_sheet;
            this.web_sheetfieldlist = web_sheetfieldlist;
        }
    
    }

    /**
    * Dennis: This method is for backward compatibilty only. Older version of OD may be using it. It may be deleted later. 14 April, 2011
    */
    public boolean setPreferences(String RegistrationKey, int ColorBorder) throws Exception {
        long DentalOfficeID = util.getDentalOfficeID(RegistrationKey);
        try
        {
            webforms_preference wspNewObj = new webforms_preference();
            wspNewObj.setDentalOfficeID(DentalOfficeID);
            wspNewObj.setColorBorder(ColorBorder);
            wspNewObj.setCultureName("");
            //empty string because null is not allowed
            setPreferencesV2(RegistrationKey,wspNewObj);
        }
        catch (Exception ex)
        {
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + DentalOfficeID,ex);
            return false;
        }

        return true;
    }

    /**
    * Newer version of SetPreferences. From OD version 7.9. Note: Cannot easily overload a method in webservices hence the suffix V2
    */
    public boolean setPreferencesV2(String RegistrationKey, webforms_preference prefObj) throws Exception {
        long DentalOfficeID = util.getDentalOfficeID(RegistrationKey);
        try
        {
            ODWebServiceEntities db = new ODWebServiceEntities();
            if (DentalOfficeID == 0)
            {
            }
             
            /* [UNSUPPORTED] 'var' as type is unsupported "var" */ wspObj = db.getwebforms_preference().Where(/* [UNSUPPORTED] to translate lambda expressions we need an explicit delegate type, try adding a cast "(wsp) => {
                return wsp.DentalOfficeID == DentalOfficeID;
            }" */);
            //update preference
            if (wspObj.Count() > 0)
            {
                wspObj.First().ColorBorder = prefObj.getColorBorder();
                wspObj.First().CultureName = prefObj.getCultureName();
            }
             
            // if there is no entry for that dental office make a new entry.
            if (wspObj.Count() == 0)
            {
                prefObj.setDentalOfficeID(DentalOfficeID);
                db.addTowebforms_preference(prefObj);
            }
             
            db.SaveChanges();
            Logger.information("Preferences saved IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + DentalOfficeID);
        }
        catch (Exception ex)
        {
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + DentalOfficeID,ex);
            return false;
        }

        return true;
    }

    public webforms_preference getPreferences(String RegistrationKey) throws Exception {
        Logger.information("In GetPreferences IpAddress=" + HttpContext.Current.Request.UserHostAddress + " RegistrationKey=" + RegistrationKey);
        ODWebServiceEntities db = new ODWebServiceEntities();
        webforms_preference wspObj = null;
        int DefaultColorBorder = -12550016;
        long DentalOfficeID = util.getDentalOfficeID(RegistrationKey);
        try
        {
            if (DentalOfficeID == 0)
            {
                return wspObj;
            }
             
            /* [UNSUPPORTED] 'var' as type is unsupported "var" */ wspRes = db.getwebforms_preference().Where(/* [UNSUPPORTED] to translate lambda expressions we need an explicit delegate type, try adding a cast "(wsp) => {
                return wsp.DentalOfficeID == DentalOfficeID;
            }" */);
            if (wspRes.Count() > 0)
            {
                wspObj = wspRes.First();
            }
             
            // if there is no entry for that dental office make a new entry.
            if (wspRes.Count() == 0)
            {
                wspObj = new webforms_preference();
                wspObj.setDentalOfficeID(DentalOfficeID);
                wspObj.setColorBorder(DefaultColorBorder);
                wspObj.setCultureName("");
                //empty string because null is not allowed
                setPreferencesV2(RegistrationKey,wspObj);
                Logger.information("new entry IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + DentalOfficeID);
            }
             
            Logger.information("In GetPreferences IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + DentalOfficeID);
        }
        catch (Exception ex)
        {
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + DentalOfficeID,ex);
            return wspObj;
        }

        return wspObj;
    }

    public List<SheetAndSheetField> getSheets(String RegistrationKey) throws Exception {
        List<SheetAndSheetField> sAndsfList = new List<SheetAndSheetField>();
        long DentalOfficeID = util.getDentalOfficeID(RegistrationKey);
        try
        {
            if (DentalOfficeID == 0)
            {
                return sAndsfList;
            }
             
            ODWebServiceEntities db = new ODWebServiceEntities();
            /* [UNSUPPORTED] 'var' as type is unsupported "var" */ wsRes;
            for (int i = 0;i < wsRes.Count();i++)
            {
                //Only download 20 sheets at a time.  This an attempt to fix DeleteSheetData from timing out after all sheets have been imported.
                if (i > 19)
                {
                    break;
                }
                 
                /* [UNSUPPORTED] 'var' as type is unsupported "var" */ wsobj = wsRes.ToList()[i];
                wsobj.webforms_sheetfield.Load();
                /* [UNSUPPORTED] 'var' as type is unsupported "var" */ sheetfieldList = wsobj.webforms_sheetfield;
                SheetAndSheetField sAnds = new SheetAndSheetField(wsobj, sheetfieldList.ToList());
                sAndsfList.Add(sAnds);
            }
            Logger.information("In GetSheetData IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + DentalOfficeID + " Sheets sent to Client=" + wsRes.Count());
            return sAndsfList;
        }
        catch (Exception ex)
        {
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + DentalOfficeID,ex);
            return sAndsfList;
        }
    
    }

    public void deleteSheetData(String RegistrationKey, List<long> SheetsForDeletion) throws Exception {
        long DentalOfficeID = util.getDentalOfficeID(RegistrationKey);
        try
        {
            if (DentalOfficeID == 0)
            {
                return ;
            }
             
            ODWebServiceEntities db = new ODWebServiceEntities();
            for (int i = 0;i < SheetsForDeletion.Count();i++)
            {
                long SheetID = SheetsForDeletion.ElementAt(i);
                // LINQ throws an error if this is directly put into the select expression
                // first delete all sheet field then delete the sheet so that a foreign key error is not thrown
                /* [UNSUPPORTED] 'var' as type is unsupported "var" */ delSheetField;
                for (int j = 0;j < delSheetField.Count();j++)
                {
                    // the ElementAt operator only works with lists. Hence ToList()
                    db.DeleteObject(delSheetField.ToList().ElementAt(j));
                }
                /* [UNSUPPORTED] 'var' as type is unsupported "var" */ delSheet;
                db.DeleteObject(delSheet.First());
                Logger.information("deleted SheetID=" + SheetID + " DentalOfficeID=" + DentalOfficeID);
            }
            db.SaveChanges();
            Logger.information("In DeleteSheetData IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + DentalOfficeID);
        }
        catch (Exception ex)
        {
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + DentalOfficeID,ex);
        }
    
    }

    /**
    * This method is redundant. It may be deleted later. Older version of OD may be using it.
    */
    public boolean checkRegistrationKey(String RegistrationKeyFromDentalOffice) throws Exception {
        return util.checkRegistrationKey(RegistrationKeyFromDentalOffice);
    }

    public long getDentalOfficeID(String RegistrationKeyFromDentalOffice) throws Exception {
        return util.getDentalOfficeID(RegistrationKeyFromDentalOffice);
    }

    /**
    * An empty method to test if the webservice is up and running. this was made with the intention of testing the correctness of the webservice URL on an Open Dental Installation. If an incorrect webservice URL is used in a background thread of OD the exception cannot be handled easily.
    */
    public boolean serviceExists() throws Exception {
        return true;
    }

    public String getSheetDefAddress(String RegistrationKey) throws Exception {
        long DentalOfficeID = util.getDentalOfficeID(RegistrationKey);
        if (DentalOfficeID == 0)
        {
            return "";
        }
         
        String SheetDefAddress = "";
        try
        {
            //SheetDefAddress=ConfigurationManager.AppSettings["SheetDefAddress"];
            SheetDefAddress = Settings.getDefault().getSheetDefAddress();
        }
        catch (Exception ex)
        {
            Logger.logError(ex);
        }

        Logger.information("In GetSheetDefAddress SheetDefAddress=" + SheetDefAddress);
        return SheetDefAddress;
    }

    public List<webforms_sheetdef> downloadSheetDefs(String RegistrationKey) throws Exception {
        List<webforms_sheetdef> sheetDefList = null;
        try
        {
            long DentalOfficeID = util.getDentalOfficeID(RegistrationKey);
            if (DentalOfficeID == 0)
            {
                return sheetDefList;
            }
             
            ODWebServiceEntities db = new ODWebServiceEntities();
            /* [UNSUPPORTED] 'var' as type is unsupported "var" */ SheetDefResult = db.getwebforms_sheetdef().Where(/* [UNSUPPORTED] to translate lambda expressions we need an explicit delegate type, try adding a cast "(sheetdef) => {
                return sheetdef.webforms_preference.DentalOfficeID == DentalOfficeID;
            }" */);
            sheetDefList = SheetDefResult.ToList();
        }
        catch (Exception ex)
        {
            Logger.Information(ex.Message.ToString());
            return sheetDefList;
        }

        return sheetDefList;
    }

    public void deleteSheetDef(String RegistrationKey, long WebSheetDefID) throws Exception {
        long DentalOfficeID = util.getDentalOfficeID(RegistrationKey);
        try
        {
            if (DentalOfficeID == 0)
            {
                return ;
            }
             
            ODWebServiceEntities db = new ODWebServiceEntities();
            webforms_sheetdef SheetDefObj = null;
            /* [UNSUPPORTED] 'var' as type is unsupported "var" */ SheetDefResult = db.getwebforms_sheetdef().Where(/* [UNSUPPORTED] to translate lambda expressions we need an explicit delegate type, try adding a cast "(sd) => {
                return sd.WebSheetDefID == WebSheetDefID;
            }" */);
            if (SheetDefResult.Count() > 0)
            {
                SheetDefObj = SheetDefResult.First();
                //load and delete existing child objects i.e sheetfielddefs objects
                SheetDefObj.getwebforms_sheetfielddef().Load();
                /* [UNSUPPORTED] 'var' as type is unsupported "var" */ SheetFieldDefResult = SheetDefObj.getwebforms_sheetfielddef();
                while (SheetFieldDefResult.Count() > 0)
                {
                    db.DeleteObject(SheetFieldDefResult.First());
                }
                //Delete SheetFieldDefObj
                db.DeleteObject(SheetDefResult.First());
                //Delete SheetDefObj
                Logger.information("deleted WebSheetDefID=" + WebSheetDefID + " DentalOfficeID=" + DentalOfficeID);
            }
             
            db.SaveChanges();
            Logger.information("In DeleteSheetDef IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + DentalOfficeID);
        }
        catch (Exception ex)
        {
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + DentalOfficeID,ex);
        }
    
    }

    /**
    * Here a single SheetDef can be uploaded via webhostsync from an Open Dental installation.
    */
    public void upLoadSheetDef(String RegistrationKey, SheetDef sheetDef) throws Exception {
        ODWebServiceEntities db = new ODWebServiceEntities();
        long DentalOfficeID = util.getDentalOfficeID(RegistrationKey);
        try
        {
            if (DentalOfficeID == 0)
            {
                return ;
            }
             
            /* [UNSUPPORTED] 'var' as type is unsupported "var" */ PreferenceResult = db.getwebforms_preference().Where(/* [UNSUPPORTED] to translate lambda expressions we need an explicit delegate type, try adding a cast "(pref) => {
                return pref.DentalOfficeID == DentalOfficeID;
            }" */);
            webforms_sheetdef SheetDefObj = null;
            SheetDefObj = new webforms_sheetdef();
            PreferenceResult.First().webforms_sheetdef.Add(SheetDefObj);
            fillSheetDef(sheetDef,SheetDefObj);
            fillFieldSheetDef(sheetDef,SheetDefObj);
            db.SaveChanges();
        }
        catch (Exception ex)
        {
            Logger.logError("IpAddress=" + HttpContext.Current.Request.UserHostAddress + " DentalOfficeID=" + DentalOfficeID,ex);
            return ;
        }
    
    }

    private void fillSheetDef(SheetDef sheetDef, webforms_sheetdef SheetDefObj) throws Exception {
        SheetDefObj.setDescription(sheetDef.Description);
        SheetDefObj.setFontName(sheetDef.FontName);
        SheetDefObj.setSheetType(((Enum)sheetDef.SheetType).ordinal());
        SheetDefObj.setFontSize(sheetDef.FontSize);
        SheetDefObj.setWidth(sheetDef.Width);
        SheetDefObj.setHeight(sheetDef.Height);
        if (sheetDef.IsLandscape == true)
        {
            SheetDefObj.setIsLandscape((sbyte)1);
        }
        else
        {
            SheetDefObj.setIsLandscape((sbyte)0);
        } 
    }

    private void fillFieldSheetDef(SheetDef sheetDef, webforms_sheetdef SheetDefObj) throws Exception {
        for (int i = 0;i < sheetDef.SheetFieldDefs.Count();i++)
        {
            //assign several webforms_sheetfielddef
            webforms_sheetfielddef SheetFieldDefObj = new webforms_sheetfielddef();
            SheetDefObj.getwebforms_sheetfielddef().Add(SheetFieldDefObj);
            for (Object __dummyForeachVar1 : sheetDef.SheetFieldDefs[i].GetType().GetFields())
            {
                // assign each property of a single webforms_sheetfielddef with corresponding values.
                FieldInfo fieldinfo = (FieldInfo)__dummyForeachVar1;
                for (Object __dummyForeachVar0 : SheetFieldDefObj.GetType().GetProperties())
                {
                    PropertyInfo propertyinfo = (PropertyInfo)__dummyForeachVar0;
                    if (fieldinfo.Name == propertyinfo.Name)
                    {
                        if (propertyinfo.PropertyType == SByte.class)
                        {
                            if ((boolean)fieldinfo.GetValue(sheetDef.SheetFieldDefs[i]) == true)
                            {
                                propertyinfo.SetValue(SheetFieldDefObj, (sbyte)1, null);
                            }
                            else
                            {
                                propertyinfo.SetValue(SheetFieldDefObj, (sbyte)0, null);
                            } 
                        }
                        else
                        {
                            if (fieldinfo.GetValue(sheetDef.SheetFieldDefs[i]) == null)
                            {
                                propertyinfo.SetValue(SheetFieldDefObj, "", null);
                            }
                            else
                            {
                                propertyinfo.SetValue(SheetFieldDefObj, fieldinfo.GetValue(sheetDef.SheetFieldDefs[i]), null);
                            } 
                        } 
                    }
                     
                }
            }
        }
    }

}


//foreach propertyinfo
//foreach fieldinfo