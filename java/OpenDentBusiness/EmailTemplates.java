//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:43 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.EmailTemplate;
import OpenDentBusiness.Meth;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* emailtemplates are refreshed as local data.
*/
public class EmailTemplates   
{
    private static EmailTemplate[] list = new EmailTemplate[]();
    /**
    * List of all email templates
    */
    //No need to check RemotingRole; no call to db.
    public static EmailTemplate[] getList() throws Exception {
        if (list == null)
        {
            refreshCache();
        }
         
        return list;
    }

    public static void setList(EmailTemplate[] value) throws Exception {
        list = value;
    }

    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * from emailtemplate ORDER BY Subject";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "EmailTemplate";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        list = Crud.EmailTemplateCrud.TableToList(table).ToArray();
    }

    /**
    * 
    */
    public static long insert(EmailTemplate template) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            template.EmailTemplateNum = Meth.GetLong(MethodBase.GetCurrentMethod(), template);
            return template.EmailTemplateNum;
        }
         
        return Crud.EmailTemplateCrud.Insert(template);
    }

    /**
    * 
    */
    public static void update(EmailTemplate template) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), template);
            return ;
        }
         
        Crud.EmailTemplateCrud.Update(template);
    }

    /**
    * 
    */
    public static void delete(EmailTemplate template) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), template);
            return ;
        }
         
        String command = "DELETE from emailtemplate WHERE EmailTemplateNum = '" + template.EmailTemplateNum.ToString() + "'";
        Db.nonQ(command);
    }

}


