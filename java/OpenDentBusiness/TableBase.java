//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;


/**
* The base class for classes that correspond to a table in the database.  Make sure to mark each derived class [Serializable].
*/
abstract public class TableBase   
{
    private boolean isNew = new boolean();
    /**
    * Not a db column.  Always false by default.  Will only be true if explicitly set to true by programmer.  When CRUD grabs a table from db, it is naturally set to False.  Once set, this value is not used by the CRUD in any manner.  Just used by the programmer for making decisions about whether to Insert or Update.
    */
    public boolean getIsNew() throws Exception {
        return isNew;
    }

    public void setIsNew(boolean value) throws Exception {
        isNew = value;
    }

}


