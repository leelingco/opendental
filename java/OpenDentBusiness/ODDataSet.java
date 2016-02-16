//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:07 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.ODDataRow;
import OpenDentBusiness.ODDataTable;
import OpenDentBusiness.ODDataTableCollection;

public class ODDataSet   
{
    public ODDataTableCollection Tables;
    public ODDataSet(String xmlData) throws Exception {
        Tables = new ODDataTableCollection();
        XmlDocument doc = new XmlDocument();
        doc.LoadXml(xmlData);
        ODDataTable currentTable = new ODDataTable();
        ODDataRow currentRow;
        XmlNodeList nodesRows = doc.DocumentElement.ChildNodes;
        for (int i = 0;i < nodesRows.Count;i++)
        {
            currentRow = new ODDataRow();
            if (StringSupport.equals(currentTable.Name, ""))
            {
                currentTable.Name = nodesRows[i].Name;
            }
            else if (!StringSupport.equals(currentTable.Name, nodesRows[i].Name))
            {
                this.Tables.Add(currentTable);
                currentTable = new ODDataTable();
                currentTable.Name = nodesRows[i].Name;
            }
              
            for (Object __dummyForeachVar0 : nodesRows[i].ChildNodes)
            {
                XmlNode nodeCell = (XmlNode)__dummyForeachVar0;
                currentRow.Add(nodeCell.Name, nodeCell.InnerXml);
            }
            currentTable.Rows.Add(currentRow);
        }
        this.Tables.Add(currentTable);
    }

}


