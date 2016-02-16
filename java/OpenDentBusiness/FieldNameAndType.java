//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.DataTypeHL7;
import OpenDentBusiness.FieldNameAndType;

public class FieldNameAndType   
{
    public String Name = new String();
    public String TableId = new String();
    public DataTypeHL7 DataType = DataTypeHL7.CNE;
    public FieldNameAndType(String name, DataTypeHL7 datatype) throws Exception {
        Name = name;
        DataType = datatype;
        TableId = "";
    }

    public FieldNameAndType(String name, DataTypeHL7 datatype, String tableid) throws Exception {
        Name = name;
        DataType = datatype;
        TableId = tableid;
    }

    public static List<FieldNameAndType> getFullList() throws Exception {
        List<FieldNameAndType> retVal = new List<FieldNameAndType>();
        retVal.Add(new FieldNameAndType("ackCode",DataTypeHL7.ID,"0008"));
        retVal.Add(new FieldNameAndType("apt.AptNum",DataTypeHL7.CX));
        retVal.Add(new FieldNameAndType("apt.lengthStartEnd",DataTypeHL7.TQ));
        retVal.Add(new FieldNameAndType("apt.Note",DataTypeHL7.CWE));
        retVal.Add(new FieldNameAndType("dateTime.Now",DataTypeHL7.DTM));
        retVal.Add(new FieldNameAndType("eventType",DataTypeHL7.ID,"0003"));
        retVal.Add(new FieldNameAndType("guar.addressCityStateZip",DataTypeHL7.XAD));
        retVal.Add(new FieldNameAndType("guar.birthdateTime",DataTypeHL7.DTM));
        retVal.Add(new FieldNameAndType("guar.ChartNumber",DataTypeHL7.CX));
        retVal.Add(new FieldNameAndType("guar.Gender",DataTypeHL7.IS));
        retVal.Add(new FieldNameAndType("guar.HmPhone",DataTypeHL7.XTN));
        retVal.Add(new FieldNameAndType("guar.nameLFM",DataTypeHL7.XPN));
        retVal.Add(new FieldNameAndType("guar.PatNum",DataTypeHL7.CX));
        retVal.Add(new FieldNameAndType("guar.SSN",DataTypeHL7.ST));
        retVal.Add(new FieldNameAndType("guar.WkPhone",DataTypeHL7.XTN));
        retVal.Add(new FieldNameAndType("messageControlId",DataTypeHL7.ST));
        retVal.Add(new FieldNameAndType("messageType",DataTypeHL7.MSG));
        retVal.Add(new FieldNameAndType("pat.addressCityStateZip",DataTypeHL7.XAD));
        retVal.Add(new FieldNameAndType("pat.birthdateTime",DataTypeHL7.DTM));
        retVal.Add(new FieldNameAndType("pat.ChartNumber",DataTypeHL7.CX));
        retVal.Add(new FieldNameAndType("pat.FeeSched",DataTypeHL7.ST));
        retVal.Add(new FieldNameAndType("pat.Gender",DataTypeHL7.IS,"0001"));
        //M,F,U,etc.
        retVal.Add(new FieldNameAndType("pat.HmPhone",DataTypeHL7.XTN));
        retVal.Add(new FieldNameAndType("pat.nameLFM",DataTypeHL7.XPN));
        retVal.Add(new FieldNameAndType("pat.PatNum",DataTypeHL7.CX));
        retVal.Add(new FieldNameAndType("pat.Position",DataTypeHL7.CWE,"0002"));
        retVal.Add(new FieldNameAndType("pat.Race",DataTypeHL7.CWE,"0005"));
        retVal.Add(new FieldNameAndType("pat.SSN",DataTypeHL7.ST));
        retVal.Add(new FieldNameAndType("pat.WkPhone",DataTypeHL7.XTN));
        retVal.Add(new FieldNameAndType("pdfDescription",DataTypeHL7.ST));
        retVal.Add(new FieldNameAndType("pdfDataAsBase64",DataTypeHL7.ST));
        retVal.Add(new FieldNameAndType("proc.DiagnosticCode",DataTypeHL7.CWE,"0051"));
        retVal.Add(new FieldNameAndType("proc.procDateTime",DataTypeHL7.DTM));
        retVal.Add(new FieldNameAndType("proc.ProcFee",DataTypeHL7.CP));
        retVal.Add(new FieldNameAndType("proc.ProcNum",DataTypeHL7.ST));
        retVal.Add(new FieldNameAndType("proc.toothSurfRange",DataTypeHL7.CNE,"0340"));
        retVal.Add(new FieldNameAndType("proccode.ProcCode",DataTypeHL7.CNE,"0088"));
        retVal.Add(new FieldNameAndType("prov.provIdNameLFM",DataTypeHL7.XCN));
        //Provider id table is user defined table and different number depending on what segment it is pulled from.  Example: FT1 Performed By Code table is 0084,
        retVal.Add(new FieldNameAndType("prov.provIdName",DataTypeHL7.XCN));
        retVal.Add(new FieldNameAndType("separators^~\\&",DataTypeHL7.ST));
        retVal.Add(new FieldNameAndType("sequenceNum",DataTypeHL7.SI));
        return retVal;
    }

    public static DataTypeHL7 getTypeFromName(String name) throws Exception {
        List<FieldNameAndType> list = getFullList();
        for (int i = 0;i < list.Count;i++)
        {
            if (StringSupport.equals(list[i].Name, name))
            {
                return list[i].DataType;
            }
             
        }
        throw new ApplicationException("Unknown field name: " + name);
    }

    public static String getTableNumFromName(String name) throws Exception {
        List<FieldNameAndType> list = getFullList();
        for (int i = 0;i < list.Count;i++)
        {
            if (StringSupport.equals(list[i].Name, name))
            {
                return list[i].TableId;
            }
             
        }
        throw new ApplicationException("Unknown field name: " + name);
    }

}


