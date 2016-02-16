//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;

import OpenDentBusiness.DbSchemaCol;
import OpenDentBusiness.OdDbType;
import OpenDentBusiness.TextSizeMySqlOracle;

//This is outdated, but will remain here for reference for a while.
//Functionality was moved to the CrudGenerator project.
public class DbSchemaCol   
{
    public String ColumnName = new String();
    public OdDbType DataType = OdDbType.Bool;
    //<summary>Specify Indexed true if column should be indexed.</summary>
    //public bool Indexed;
    /**
    * Specify textSize if there's any chance of it being greater than 4000 char.
    */
    public TextSizeMySqlOracle TextSize = TextSizeMySqlOracle.Small;
    /**
    * If specifying an int, it uses int by default.  Set this to true to instead use smallint.
    */
    public boolean IntUseSmallInt = new boolean();
    /*
    		/// <summary>Takes DbSchemaCol and makes a new instance of it. </summary>
    		public DbSchemaCol(DbSchemaCol newCol) {
    			ColumnName=newCol.ColumnName;
    			DataType=newCol.DataType;
    			//Indexed=newCol.Indexed;
    			IntUseSmallInt=newCol.IntUseSmallInt;
    			TextSize=newCol.TextSize;
    		}*/
    public DbSchemaCol(String columnName, OdDbType dataType) throws Exception {
        ColumnName = columnName;
        DataType = dataType;
    }

    //Indexed=false;
    public DbSchemaCol(String columnName, OdDbType dataType, TextSizeMySqlOracle textSize) throws Exception {
        ColumnName = columnName;
        DataType = dataType;
        //Indexed=indexed;
        TextSize = textSize;
    }

    public DbSchemaCol(String columnName, OdDbType dataType, TextSizeMySqlOracle textSize, boolean intUseSmallInt) throws Exception {
        ColumnName = columnName;
        DataType = dataType;
        //Indexed=indexed;
        TextSize = textSize;
        IntUseSmallInt = intUseSmallInt;
    }

    //public DbSchemaCol(string columnName,OdDbType dataType) {
    //	ColumnName=columnName;
    //	DataType=dataType;
    //Indexed=indexed;
    //}
    /**
    * Creates a new instance of this object with identical variable values.
    */
    public DbSchemaCol copy() throws Exception {
        return (DbSchemaCol)this.MemberwiseClone();
    }

}


