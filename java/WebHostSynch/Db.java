//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:40 PM
//

package WebHostSynch;


public class Db   
{
    private OpenDentBusiness.DataConnection dcon;
    public void setConn(String connectStr) throws Exception {
        dcon = new OpenDentBusiness.DataConnection(connectStr,true);
    }

    public DataTable getTable(String command) throws Exception {
        DataTable table = dcon.getTable(command);
        return table;
    }

    //retVal;
    public long nonQ(String command, boolean getInsertID) throws Exception {
        long rowsChanged = dcon.nonQ(command,getInsertID);
        if (getInsertID)
        {
            return (long)dcon.InsertID;
        }
        else
        {
            return rowsChanged;
        } 
    }

    public long nonQ(String command) throws Exception {
        return nonQ(command,false);
    }

}


