//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:15 PM
//

package OpenDentBusiness.Tests;

import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.Tests.ServiceWrapper;

public abstract class DataTests <T extends DataObjectBase>  
{
    private static final int DatabaseMaxInteger = short.MaxValue;
    // For types like double and float, saving the max value will not work:
    // http://bugs.mysql.com/bug.php?id=9262
    // That's why we use a haircut, to save a large (but not too large) value to the
    // database. Right now we go up to 5 nine's.
    private static final double LargeValuesHaircut = 0.99999;
    Random random = new Random();
    AppDomain domain = new AppDomain();
    ServiceWrapper wrapper;
    public void intialize() throws Exception {
        random = new Random();
        startService();
    }

    public void cleanUp() throws Exception {
        stopService();
    }

    private void startService() throws Exception {
        // Start the OD service in a new app domain
        domain = AppDomain.CreateDomain("ServiceDomain");
        wrapper = (ServiceWrapper)domain.CreateInstanceFromAndUnwrap(Assembly.GetExecutingAssembly().CodeBase, ServiceWrapper.class.FullName);
        wrapper.start();
    }

    private void stopService() throws Exception {
        wrapper.stop();
        AppDomain.Unload(domain);
    }

    public void test() throws Exception {
        RemotingClient.OpenDentBusinessIsLocal = true;
        // Connect to the local mysql server.
        OpenDentBusiness.DataConnection connection = new OpenDentBusiness.DataConnection();
        connection.setDb("localhost","opendental","root","","root","",OpenDentBusiness.DatabaseType.MySql);
        DataObjectFactory<T>.UseParameters = false;
        testTableType();
        DataObjectFactory<T>.UseParameters = true;
        testTableType();
        RemotingClient.OpenDentBusinessIsLocal = false;
        RemotingClient.ServerName = "localhost";
        RemotingClient.ServerPort = 9390;
        DataObjectFactory<T>.UseParameters = false;
        testTableType();
        DataObjectFactory<T>.UseParameters = true;
        testTableType();
        RemotingClient.Disconnect();
    }

    private void testTableType() throws Exception {
        // Create a new object
        T t = DataObjectFactory<T>.NewObject();
        Assert.IsNotNull(t, "#1 (Object should not be null)");
        Assert.IsTrue(t.IsNew, "#5 (IsNew should be set)");
        Assert.IsFalse(t.IsDeleted, "#6 (IsDeleted should be false)");
        Assert.IsTrue(t.IsDirty, "#7 (IsDirty should be true)");
        // Fill the data
        Collection<DataFieldInfo> dataFields = DataObjectInfo<T>.GetDataFields(DataFieldMask.Data);
        for (Object __dummyForeachVar0 : dataFields)
        {
            DataFieldInfo dataField = (DataFieldInfo)__dummyForeachVar0;
            DataObjectInfo<T>.SetProperty(dataField, t, Random(dataField.Field.FieldType));
        }
        // Save the object to the database
        DataObjectFactory<T>.WriteObject(t);
        if (!DataObjectInfo<T>.HasPrimaryKeyWithAutoNumber())
            throw new NotSupportedException();
         
        int id = DataObjectInfo<T>.GetPrimaryKey(t);
        Assert.AreNotEqual(0, id, "#2 (ID should not be zero)");
        Assert.IsFalse(t.IsDirty, "#8 (IsDirty should be false)");
        Assert.IsFalse(t.IsDeleted, "#9 (IsDeleted should be false)");
        Assert.IsFalse(t.IsNew, "#16 (IsNew should be false)");
        // Load a new object
        T database = DataObjectFactory<T>.CreateObject(id);
        for (Object __dummyForeachVar1 : dataFields)
        {
            DataFieldInfo dataField = (DataFieldInfo)__dummyForeachVar1;
            Object localValue = dataField.Field.GetValue(t);
            Object databaseValue = dataField.Field.GetValue(database);
            // Text strings may be trimmed by the database. For now,
            // ignore that.
            if (dataField.Field.FieldType == String.class)
            {
                String localText = (String)localValue;
                String databaseText = (String)databaseValue;
                if (databaseText.Length < localText.Length)
                {
                    localValue = localText.Substring(0, databaseText.Length);
                }
                 
            }
             
            if (dataField.Field.FieldType == double.class)
            {
                Assert.AreEqual((Double)localValue, (Double)databaseValue, 0.01, String.Format("#3a - {0} (Value retrieved from database should equal stored value)", formatFieldName(dataField)));
            }
            else if (dataField.Field.FieldType == float.class)
            {
                Assert.AreEqual((Float)localValue, (Float)databaseValue, 0.01, String.Format("#3b - {0} (Value retrieved from database should equal stored value)", formatFieldName(dataField)));
            }
            else
            {
                Assert.AreEqual(localValue, databaseValue, String.Format("#3x - {0} (Value retrieved from database should equal stored value)", formatFieldName(dataField)));
            }  
        }
        for (Object __dummyForeachVar2 : dataFields)
        {
            // Modify the object
            DataFieldInfo dataField = (DataFieldInfo)__dummyForeachVar2;
            DataObjectInfo<T>.SetProperty(dataField, t, Random(dataField.Field.FieldType));
            Assert.IsTrue(DataObjectInfo<T>.HasChanged(dataField, t), String.Format("#11 - {0} (Field should be marked as dirty)", formatFieldName(dataField)));
        }
        Assert.IsTrue(t.IsDirty, "#12 (Object should be dirty)");
        DataObjectFactory<T>.WriteObject(t);
        Assert.AreEqual(id, DataObjectInfo<T>.GetPrimaryKey(t), "#13 (Id should not change after saving)");
        Assert.IsFalse(t.IsNew, "#14 (IsDirty should not be set");
        Assert.IsFalse(t.IsNew, "#15 (IsNew should not be set");
        database = DataObjectFactory<T>.CreateObject(id);
        for (Object __dummyForeachVar3 : dataFields)
        {
            DataFieldInfo dataField = (DataFieldInfo)__dummyForeachVar3;
            Object localValue = dataField.Field.GetValue(t);
            Object databaseValue = dataField.Field.GetValue(database);
            // Text strings may be trimmed by the database. For now,
            // ignore that.
            if (dataField.Field.FieldType == String.class)
            {
                String localText = (String)localValue;
                String databaseText = (String)databaseValue;
                if (databaseText.Length < localText.Length)
                {
                    localValue = localText.Substring(0, databaseText.Length);
                }
                 
            }
             
            if (dataField.Field.FieldType == double.class)
            {
                Assert.AreEqual((Double)localValue, (Double)databaseValue, 0.01, String.Format("#4a - {0}", formatFieldName(dataField)));
            }
            else if (dataField.Field.FieldType == float.class)
            {
                Assert.AreEqual((Float)localValue, (Float)databaseValue, 0.01, String.Format("#4b - {0}", formatFieldName(dataField)));
            }
            else
            {
                Assert.AreEqual(localValue, databaseValue, String.Format("#4x - {0}", formatFieldName(dataField)));
            }  
        }
        // For all fields of value types, try to store the maximum and minimum value, to make sure
        // the type in the database can hold the values given by the program.
        testExtremum(t,id,ExtremumType.Maximum);
        testExtremum(t,id,ExtremumType.Minimum);
        // Delete the object
        DataObjectFactory<T>.DeleteObject(t);
    }

    private void testExtremum(T value, int id, ExtremumType extremumType) throws Exception {
        Collection<DataFieldInfo> dataFields = DataObjectInfo<T>.GetDataFields(DataFieldMask.Data);
        for (Object __dummyForeachVar4 : dataFields)
        {
            // Set the value
            DataFieldInfo field = (DataFieldInfo)__dummyForeachVar4;
            Type fieldType = field.Field.FieldType;
            if (fieldType.IsValueType && fieldType.IsPrimitive)
            {
                DataObjectInfo<T>.SetProperty(field, value, extremum(fieldType,extremumType));
            }
             
        }
        // Save the object
        DataObjectFactory<T>.WriteObject(value);
        value = DataObjectFactory<T>.CreateObject(id);
        for (Object __dummyForeachVar5 : dataFields)
        {
            // Verify the value
            DataFieldInfo field = (DataFieldInfo)__dummyForeachVar5;
            Type fieldType = field.Field.FieldType;
            if (fieldType == double.class)
            {
                Object storedValue = field.Field.GetValue(value);
                Assert.AreEqual((Double)extremum(fieldType,extremumType), (Double)storedValue, Math.Abs(1E-5 * (Double)storedValue), String.Format("#17 - {0}: Maximum value should store correctly.", formatFieldName(field)));
            }
            else if (fieldType.IsValueType && fieldType.IsPrimitive)
            {
                Object storedValue = field.Field.GetValue(value);
                Assert.AreEqual(extremum(fieldType,extremumType), storedValue, String.Format("#17 - {0}: Maximum value should store correctly.", formatFieldName(field)));
            }
              
        }
    }

    private String formatFieldName(DataFieldInfo field) throws Exception {
        return String.Format("{0}.{1}", T.class.Name, field.Field.Name);
    }

    private enum ExtremumType
    {
        Minimum,
        Maximum
    }
    private Object extremum(Type dataType, ExtremumType extremumType) throws Exception {
        if (dataType == boolean.class)
        {
            switch(extremumType)
            {
                case Maximum: 
                    return true;
                case Minimum: 
                    return false;
            
            }
        }
         
        if (dataType == byte.class)
        {
            switch(extremumType)
            {
                case Maximum: 
                    return byte.MaxValue;
                case Minimum: 
                    return byte.MinValue;
            
            }
        }
        else if (dataType == short.class)
        {
            switch(extremumType)
            {
                case Maximum: 
                    return short.MaxValue;
                case Minimum: 
                    return short.MinValue;
            
            }
        }
        else if (dataType == int.class)
        {
            switch(extremumType)
            {
                case Maximum: 
                    return int.MaxValue;
                case Minimum: 
                    return int.MinValue;
            
            }
        }
        else if (dataType == long.class)
        {
            switch(extremumType)
            {
                case Maximum: 
                    return long.MaxValue;
                case Minimum: 
                    return long.MinValue;
            
            }
        }
        else if (dataType == float.class)
        {
            switch(extremumType)
            {
                case Maximum: 
                    return float.MaxValue;
                case Minimum: 
                    return float.MinValue;
            
            }
        }
        else if (dataType == double.class)
        {
            switch(extremumType)
            {
                case Maximum: 
                    return LargeValuesHaircut * double.MaxValue;
                case Minimum: 
                    return LargeValuesHaircut * double.MinValue;
            
            }
        }
        else if (dataType == double.class)
        {
            switch(extremumType)
            {
                case Maximum: 
                    return double.MaxValue;
                case Minimum: 
                    return double.MinValue;
            
            }
        }
               
        throw new NotSupportedException();
    }

    private Object random(Type dataType) throws Exception {
        if (dataType == String.class)
        {
            String value = String.Empty;
            for (int i = 0;i < 20;i++)
            {
                value += (char)(random.Next(33, 126));
            }
            return value;
        }
        else if (dataType == boolean.class)
        {
            return Convert.ToBoolean(random.Next(0, 1));
        }
        else if (dataType == Byte.class)
        {
            byte[] buffer = new byte[1];
            random.NextBytes(buffer);
            return buffer[0];
        }
        else if (dataType == DateTime.class)
        {
            DateTime value = DateTime.Today;
            value = value.AddDays(random.Next(-1000, 1000));
            return value;
        }
        else if (dataType == TimeSpan.class)
        {
            return new TimeSpan(0, random.Next(0, 60), 0);
        }
        else if (dataType == double.class)
        {
            return random.NextDouble();
        }
        else if (dataType == float.class)
        {
            return (float)random.NextDouble();
        }
        else if (dataType == int.class)
        {
            return random.Next(DatabaseMaxInteger + 1);
        }
        else if (dataType == short.class)
        {
            return (short)random.Next(short.MaxValue);
        }
        else if (dataType.IsEnum)
        {
            Array values = Enum.GetValues(dataType);
            int index = random.Next(0, values.Length);
            return values.GetValue(index);
        }
        else
        {
            throw new NotSupportedException("The type " + dataType.Name + " is not supported");
        }          
    }

}


