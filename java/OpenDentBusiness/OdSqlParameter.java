//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:52 PM
//

package OpenDentBusiness;

import OpenDentBusiness.OdDbType;

/**
* Hold parameter info in a database independent manner.
*/
public class OdSqlParameter   
{
    private String parameterName = new String();
    private OdDbType dbType = OdDbType.Bool;
    private Object value = new Object();
    public OdDbType getDbType() throws Exception {
        return dbType;
    }

    public void setDbType(OdDbType value) throws Exception {
        dbType = value;
    }

    /**
    * parameterName should not include the leading character such as @ or : . And DbHelper.ParamChar() should be used to determine the char in the query itself.
    */
    public String getParameterName() throws Exception {
        return parameterName;
    }

    public void setParameterName(String value) throws Exception {
        parameterName = value;
    }

    public Object getValue() throws Exception {
        return this.value;
    }

    public void setValue(Object value) throws Exception {
        this.value = value;
    }

    /**
    * parameterName should not include the leading character such as @ or : . And DbHelper.ParamChar() should be used to determine the char in the query itself.
    */
    public OdSqlParameter(String parameterName, OdDbType dbType, Object value) throws Exception {
        this.parameterName = parameterName;
        this.dbType = dbType;
        this.value = value;
    }

    public MySqlDbType getMySqlDbType() throws Exception {
        switch(this.dbType)
        {
            case Text: 
                return MySqlDbType.MediumText;
            default: 
                throw new ApplicationException("Type not found");
        
        }
    }

    //case OdDbType.Blob:
    //	return MySqlDbType.MediumBlob;
    //none of these other types will use parameters.
    /*
    				case OdDbType.Bool:
    					return MySqlDbType.UByte;
    				case OdDbType.Byte:
    					return MySqlDbType.UByte;
    				case OdDbType.Currency:
    					return MySqlDbType.Double;
    				case OdDbType.Date:
    					return MySqlDbType.Date;
    				case OdDbType.DateTime:
    					return MySqlDbType.DateTime;
    				case OdDbType.DateTimeStamp:
    					return MySqlDbType.Timestamp;
    				case OdDbType.Float:
    					return MySqlDbType.Float;
    				case OdDbType.Int:
    					return MySqlDbType.Int32;
    				case OdDbType.Long:
    					return MySqlDbType.Int64;
    				case OdDbType.Text:
    					return MySqlDbType.MediumText;//hope this will work
    				case OdDbType.TimeOfDay:
    					return MySqlDbType.Time;
    				case OdDbType.TimeSpan:
    					return MySqlDbType.Time;
    				case OdDbType.VarChar255:
    					return MySqlDbType.VarChar;*/
    public MySqlParameter getMySqlParameter() throws Exception {
        MySqlParameter param = new MySqlParameter();
        param.ParameterName = this.parameterName;
        param.MySqlDbType = getMySqlDbType();
        return param;
    }

    public OracleDbType getOracleDbType() throws Exception {
        switch(this.dbType)
        {
            case Text: 
                return OracleDbType.Clob;
            default: 
                throw new ApplicationException("Type not found");
        
        }
    }

    //case OdDbType.Blob:
    //	return OracleDbType.Blob;
    //none of these other types will use parameters.
    /*
    				case OdDbType.Bool:
    					return OracleDbType.Byte;
    				case OdDbType.Byte:
    					return OracleDbType.Byte;
    				case OdDbType.Currency:
    					return OracleDbType.Decimal;
    				case OdDbType.Date:
    					return OracleDbType.Date;
    				case OdDbType.DateTime:
    					return OracleDbType.Date;
    				case OdDbType.DateTimeStamp:
    					return OracleDbType.Date;
    				case OdDbType.Float:
    					return OracleDbType.Double;
    				case OdDbType.Int:
    					return OracleDbType.Int32;
    				case OdDbType.Long:
    					return OracleDbType.Int64;
    				case OdDbType.Text:
    					return OracleDbType.Clob;
    				case OdDbType.TimeOfDay:
    					return OracleDbType.Date;
    				case OdDbType.TimeSpan:
    					return OracleDbType.Varchar2;
    				case OdDbType.VarChar255:
    					return OracleDbType.Varchar2;*/
    public OracleParameter getOracleParameter() throws Exception {
        OracleParameter param = new OracleParameter();
        param.ParameterName = this.parameterName;
        param.OracleDbType = getOracleDbType();
        return param;
    }

}


