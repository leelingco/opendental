//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:41 PM
//

package OpenDentBusiness;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.Def;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Documents;
import OpenDentBusiness.ImageHelper;
import OpenDentBusiness.ImageSettingFlags;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.MountItem;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* Handles documents and images for the Images module
*/
public class Documents   
{
    /**
    * 
    */
    public static OpenDentBusiness.Document[] getAllWithPat(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<OpenDentBusiness.Document[]>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM document WHERE PatNum=" + POut.long(patNum) + " ORDER BY DateCreated";
        DataTable table = Db.getTable(command);
        return Crud.DocumentCrud.TableToList(table).ToArray();
    }

    /**
    * Gets the document with the specified document number.
    */
    public static OpenDentBusiness.Document getByNum(long docNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<OpenDentBusiness.Document>GetObject(MethodBase.GetCurrentMethod(), docNum);
        }
         
        OpenDentBusiness.Document doc = Crud.DocumentCrud.SelectOne(docNum);
        if (doc == null)
        {
            return new OpenDentBusiness.Document();
        }
         
        return doc;
    }

    public static OpenDentBusiness.Document[] fill(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (table == null)
        {
            return new OpenDentBusiness.Document[0];
        }
         
        List<OpenDentBusiness.Document> list = Crud.DocumentCrud.TableToList(table);
        return list.ToArray();
    }

    /*
    		private static Document[] RefreshAndFill(string command) {
    			//No need to check RemotingRole; no call to db.
    			return Fill(Db.GetTable(command));
    		}*/
    /**
    * Usually, set just the extension before passing in the doc.  Inserts a new document into db, creates a filename based on Cur.DocNum, and then updates the db with this filename.  Should always refresh the document after calling this method in order to get the correct filename for RemotingRole.ClientWeb.
    */
    public static long insert(OpenDentBusiness.Document doc, Patient pat) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            doc.DocNum = Meth.GetLong(MethodBase.GetCurrentMethod(), doc, pat);
            return doc.DocNum;
        }
         
        doc.DocNum = Crud.DocumentCrud.Insert(doc);
        //If the current filename is just an extension, then assign it a unique name.
        if (StringSupport.equals(doc.FileName, Path.GetExtension(doc.FileName)))
        {
            String extension = doc.FileName;
            doc.FileName = "";
            String s = pat.LName + pat.FName;
            for (int i = 0;i < s.Length;i++)
            {
                if (Char.IsLetter(s, i))
                {
                    doc.FileName += s.Substring(i, 1);
                }
                 
            }
            doc.FileName += doc.DocNum.ToString() + extension;
            //ensures unique name
            //there is still a slight chance that someone manually added a file with this name, so quick fix:
            String command = "SELECT FileName FROM document WHERE PatNum=" + POut.long(doc.PatNum);
            DataTable table = Db.getTable(command);
            String[] usedNames = new String[table.Rows.Count];
            for (int i = 0;i < table.Rows.Count;i++)
            {
                usedNames[i] = PIn.String(table.Rows[i][0].ToString());
            }
            while (IsFileNameInList(doc.FileName, usedNames))
            {
                doc.FileName = "x" + doc.FileName;
            }
            /*Document[] docList=GetAllWithPat(doc.PatNum);
            				while(IsFileNameInList(doc.FileName,docList)) {
            					doc.FileName="x"+doc.FileName;
            				}*/
            update(doc);
        }
         
        return doc.DocNum;
    }

    /**
    * 
    */
    public static void update(OpenDentBusiness.Document doc) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), doc);
            return ;
        }
         
        Crud.DocumentCrud.Update(doc);
    }

    /**
    * 
    */
    public static void delete(OpenDentBusiness.Document doc) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), doc);
            return ;
        }
         
        String command = "DELETE from document WHERE DocNum = '" + doc.DocNum.ToString() + "'";
        Db.nonQ(command);
    }

    /**
    * This is used by FormImageViewer to get a list of paths based on supplied list of DocNums. The reason is that later we will allow sharing of documents, so the paths may not be in the current patient folder. Rewritten by Ryan on 10/26/2011 to use List<> instead of ArrayList.
    */
    public static List<String> getPaths(List<long> docNums, String atoZPath) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<String>>GetObject(MethodBase.GetCurrentMethod(), docNums, atoZPath);
        }
         
        if (docNums.Count == 0)
        {
            return new List<String>();
        }
         
        String command = "SELECT document.DocNum,document.FileName,patient.ImageFolder " + "FROM document " + "LEFT JOIN patient ON patient.PatNum=document.PatNum " + "WHERE document.DocNum = '" + docNums[0].ToString() + "'";
        for (int i = 1;i < docNums.Count;i++)
        {
            command += " OR document.DocNum = '" + docNums[i].ToString() + "'";
        }
        //remember, they will not be in the correct order.
        DataTable table = Db.getTable(command);
        Hashtable hList = new Hashtable();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            //key=docNum, value=path
            //one row for each document, but in the wrong order
            //We do not need to check if A to Z folders are being used here, because
            //thumbnails are not visible from the chart module when A to Z are disabled,
            //making it impossible to launch the form image viewer (the only place this
            //function is called from).
            hList.Add(PIn.Long(table.Rows[i][0].ToString()), CodeBase.ODFileUtils.CombinePaths(new String[]{ atoZPath, PIn.String(table.Rows[i][2].ToString()).Substring(0, 1).ToUpper(), PIn.String(table.Rows[i][2].ToString()), PIn.String(table.Rows[i][1].ToString()) }));
        }
        List<String> retVal = new List<String>();
        for (int i = 0;i < docNums.Count;i++)
        {
            retVal.Add((String)hList[(long)docNums[i]]);
        }
        return retVal;
    }

    /**
    * Will return null if no picture for this patient.
    */
    public static OpenDentBusiness.Document getPatPictFromDb(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<OpenDentBusiness.Document>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        //first establish which category pat pics are in
        long defNumPicts = 0;
        Def[] defs = DefC.getList(DefCat.ImageCats);
        for (int i = 0;i < defs.Length;i++)
        {
            if (Regex.IsMatch(defs[i].ItemValue, "P"))
            {
                defNumPicts = defs[i].DefNum;
                break;
            }
             
        }
        if (defNumPicts == 0)
        {
            return null;
        }
         
        //no category set for picts
        //then find, limit 1 to get the most recent
        String command = "SELECT * FROM document " + "WHERE document.PatNum=" + POut.long(patNum) + " AND document.DocCategory=" + POut.long(defNumPicts) + " ORDER BY DateCreated DESC";
        command = DbHelper.limitOrderBy(command,1);
        DataTable table = Db.getTable(command);
        OpenDentBusiness.Document[] pictureDocs = fill(table);
        if (pictureDocs == null || pictureDocs.Length < 1)
        {
            return null;
        }
         
        return pictureDocs[0];
    }

    //no pictures
    /**
    * Makes one call to the database to retrieve the document of the patient for the given patNum, then uses that document and the patFolder to load and process the patient picture so it appears the same way it did in the image module.  It first creates a 100x100 thumbnail if needed, then it uses the thumbnail so no scaling needed. Returns false if there is no patient picture, true otherwise. Sets the value of patientPict equal to a new instance of the patient's processed picture, but will be set to null on error. Assumes WithPat will always be same as patnum.
    */
    public static boolean getPatPict(long patNum, String patFolder, RefSupport<Bitmap> patientPict) throws Exception {
        //No need to check RemotingRole; no call to db.
        OpenDentBusiness.Document pictureDoc = getPatPictFromDb(patNum);
        if (pictureDoc == null)
        {
            patientPict.setValue(null);
            return false;
        }
         
        patientPict.setValue(getThumbnail(pictureDoc,patFolder,100));
        return (patientPict.getValue() != null);
    }

    /**
    * Gets the corresponding thumbnail image for the given document object. The document is expected to be an image, and a 'not available' image is returned if the document is not an image. The thumbnail for every document is in a folder named 'thumbnails' within the same directly level.
    */
    public static Bitmap getThumbnail(OpenDentBusiness.Document doc, String patFolder, int size) throws Exception {
        //No need to check RemotingRole; no call to db.
        String shortFileName = doc.FileName;
        //If no file name associated with the document, then there cannot be a thumbnail,
        //because thumbnails have the same name as the original image document.
        if (shortFileName.Length < 1)
        {
            return noAvailablePhoto(size);
        }
         
        String fullName = CodeBase.ODFileUtils.combinePaths(patFolder,shortFileName);
        //If the document no longer exists, then there is no corresponding thumbnail image.
        if (!File.Exists(fullName))
        {
            return noAvailablePhoto(size);
        }
         
        //If the specified document is not an image return 'not available'.
        if (!ImageHelper.hasImageExtension(fullName))
        {
            return noAvailablePhoto(size);
        }
         
        //Create Thumbnails folder if it does not already exist for this patient folder.
        String thumbPath = CodeBase.ODFileUtils.combinePaths(patFolder,"Thumbnails");
        if (!Directory.Exists(thumbPath))
        {
            try
            {
                Directory.CreateDirectory(thumbPath);
            }
            catch (Exception __dummyCatchVar0)
            {
                throw new ApplicationException(Lans.g("Documents","Error: Could not create 'Thumbnails' folder for patient: ") + thumbPath);
            }
        
        }
         
        String thumbFileExt = Path.GetExtension(shortFileName);
        String thumbCoreFileName = shortFileName.Substring(0, shortFileName.Length - thumbFileExt.Length);
        String thumbFileName = CodeBase.ODFileUtils.CombinePaths(new String[]{ patFolder, "Thumbnails", thumbCoreFileName + "_" + size + thumbFileExt });
        //Use the existing thumbnail if it already exists and it was created after the last document modification.
        if (File.Exists(thumbFileName))
        {
            DateTime thumbModifiedTime = File.GetLastWriteTime(thumbFileName);
            if (thumbModifiedTime > doc.DateTStamp)
            {
                return (Bitmap)Bitmap.FromFile(thumbFileName);
            }
             
        }
         
        //Add thumbnail
        Bitmap thumbBitmap = new Bitmap();
        //Gets the cropped/flipped/rotated image with any color filtering applied.
        Bitmap sourceImage = new Bitmap(fullName);
        Bitmap fullImage = ImageHelper.applyDocumentSettingsToImage(doc,sourceImage,ImageSettingFlags.ALL);
        sourceImage.Dispose();
        thumbBitmap = ImageHelper.GetThumbnail(fullImage, size);
        fullImage.Dispose();
        try
        {
            thumbBitmap.Save(thumbFileName);
        }
        catch (Exception __dummyCatchVar1)
        {
        }

        return thumbBitmap;
    }

    //Oh well, we can regenerate it next time if we have to!
    public static Bitmap noAvailablePhoto(int size) throws Exception {
        //No need to check RemotingRole; no call to db.
        Bitmap bitmap = new Bitmap(size, size);
        Graphics g = Graphics.FromImage(bitmap);
        g.InterpolationMode = InterpolationMode.High;
        g.FillRectangle(Brushes.Gray, 0, 0, bitmap.Width, bitmap.Height);
        StringFormat notAvailFormat = new StringFormat();
        notAvailFormat.Alignment = StringAlignment.Center;
        notAvailFormat.LineAlignment = StringAlignment.Center;
        Font font = new Font("Courier New", 8.25F, FontStyle.Regular, GraphicsUnit.Point, ((byte)(0)));
        g.DrawString("Thumbnail not available", font, Brushes.Black, new RectangleF(0, 0, size, size), notAvailFormat);
        g.Dispose();
        return bitmap;
    }

    /**
    * Returns the documents which correspond to the given mountitems.
    */
    public static OpenDentBusiness.Document[] getDocumentsForMountItems(List<MountItem> mountItems) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<OpenDentBusiness.Document[]>GetObject(MethodBase.GetCurrentMethod(), mountItems);
        }
         
        if (mountItems == null || mountItems.Count < 1)
        {
            return new OpenDentBusiness.Document[0];
        }
         
        OpenDentBusiness.Document[] documents = new OpenDentBusiness.Document[mountItems.Count];
        for (int i = 0;i < mountItems.Count;i++)
        {
            String command = "SELECT * FROM document WHERE MountItemNum='" + POut.Long(mountItems[i].MountItemNum) + "'";
            DataTable table = Db.getTable(command);
            if (table.Rows.Count < 1)
            {
                documents[i] = null;
            }
            else
            {
                documents[i] = fill(table)[0];
            } 
        }
        return documents;
    }

    /**
    * Any filenames mentioned in the fileList which are not attached to the given patient are properly attached to that patient. Returns the total number of documents that were newly attached to the patient.
    */
    public static int insertMissing(Patient patient, List<String> fileList) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetInt(MethodBase.GetCurrentMethod(), patient, fileList);
        }
         
        int countAdded = 0;
        String command = "SELECT FileName FROM document WHERE PatNum='" + patient.PatNum + "' ORDER BY FileName";
        DataTable table = Db.getTable(command);
        for (int j = 0;j < fileList.Count;j++)
        {
            String fileName = Path.GetFileName(fileList[j]);
            if (!isAcceptableFileName(fileName))
            {
                continue;
            }
             
            boolean inList = false;
            for (int i = 0;i < table.Rows.Count && !inList;i++)
            {
                inList = (StringSupport.equals(table.Rows[i]["FileName"].ToString(), fileName));
            }
            if (!inList)
            {
                OpenDentBusiness.Document doc = new OpenDentBusiness.Document();
                doc.DateCreated = File.GetLastWriteTime(fileList[j]);
                doc.Description = fileName;
                doc.DocCategory = DefC.getList(DefCat.ImageCats)[0].DefNum;
                //First category.
                doc.FileName = fileName;
                doc.PatNum = patient.PatNum;
                insert(doc,patient);
                countAdded++;
            }
             
        }
        return countAdded;
    }

    /**
    * Parameters: 1:PatNum
    */
    public static DataSet refreshForPatient(String[] parameters) throws Exception {
        //No need to check RemotingRole; no call to db.
        DataSet retVal = new DataSet();
        retVal.Tables.Add(GetTreeListTableForPatient(parameters[0]));
        return retVal;
    }

    public static DataTable getTreeListTableForPatient(String patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), patNum);
        }
         
        OpenDentBusiness.DataConnection dcon = new OpenDentBusiness.DataConnection();
        DataTable table = new DataTable("DocumentList");
        DataRow row = new DataRow();
        DataTable raw = new DataTable();
        String command = new String();
        //Rows are first added to the resultSet list so they can be sorted at the end as a larger group, then
        //they are placed in the datatable to be returned.
        List<Object> resultSet = new List<Object>();
        //columns that start with lowercase are altered for display rather than being raw data.
        table.Columns.Add("DocNum");
        table.Columns.Add("MountNum");
        table.Columns.Add("DocCategory");
        table.Columns.Add("DateCreated");
        table.Columns.Add("docFolder");
        //The folder order to which the Document category corresponds.
        table.Columns.Add("description");
        table.Columns.Add("ImgType");
        //Move all documents which are invisible to the first document category.
        command = "SELECT DocNum FROM document WHERE PatNum='" + patNum + "' AND " + "DocCategory<0";
        raw = dcon.getTable(command);
        if (raw.Rows.Count > 0)
        {
            //Are there any invisible documents?
            command = "UPDATE document SET DocCategory='" + DefC.getList(DefCat.ImageCats)[0].DefNum + "' WHERE PatNum='" + patNum + "' AND (";
            for (int i = 0;i < raw.Rows.Count;i++)
            {
                command += "DocNum='" + PIn.Long(raw.Rows[i]["DocNum"].ToString()) + "' ";
                if (i < raw.Rows.Count - 1)
                {
                    command += "OR ";
                }
                 
            }
            command += ")";
            dcon.nonQ(command);
        }
         
        //Load all documents into the result table.
        command = "SELECT DocNum,DocCategory,DateCreated,Description,ImgType,MountItemNum FROM document WHERE PatNum='" + patNum + "'";
        raw = dcon.getTable(command);
        for (int i = 0;i < raw.Rows.Count;i++)
        {
            //Make sure hidden documents are never added (there is a small possibility that one is added after all are made visible).
            if (DefC.GetOrder(DefCat.ImageCats, PIn.Long(raw.Rows[i]["DocCategory"].ToString())) < 0)
            {
                continue;
            }
             
            //Do not add individual documents which are part of a mount object.
            if (PIn.Long(raw.Rows[i]["MountItemNum"].ToString()) != 0)
            {
                continue;
            }
             
            row = table.NewRow();
            row["DocNum"] = PIn.Long(raw.Rows[i]["DocNum"].ToString());
            row["MountNum"] = 0;
            row["DocCategory"] = PIn.Long(raw.Rows[i]["DocCategory"].ToString());
            row["DateCreated"] = PIn.Date(raw.Rows[i]["DateCreated"].ToString());
            row["docFolder"] = DefC.GetOrder(DefCat.ImageCats, PIn.Long(raw.Rows[i]["DocCategory"].ToString()));
            row["description"] = PIn.Date(raw.Rows[i]["DateCreated"].ToString()).ToString("d") + ": " + PIn.String(raw.Rows[i]["Description"].ToString());
            row["ImgType"] = PIn.Long(raw.Rows[i]["ImgType"].ToString());
            resultSet.Add(row);
        }
        //Move all mounts which are invisible to the first document category.
        command = "SELECT MountNum FROM mount WHERE PatNum='" + patNum + "' AND " + "DocCategory<0";
        raw = dcon.getTable(command);
        if (raw.Rows.Count > 0)
        {
            //Are there any invisible mounts?
            command = "UPDATE mount SET DocCategory='" + DefC.getList(DefCat.ImageCats)[0].DefNum + "' WHERE PatNum='" + patNum + "' AND (";
            for (int i = 0;i < raw.Rows.Count;i++)
            {
                command += "MountNum='" + PIn.Long(raw.Rows[i]["MountNum"].ToString()) + "' ";
                if (i < raw.Rows.Count - 1)
                {
                    command += "OR ";
                }
                 
            }
            command += ")";
            dcon.nonQ(command);
        }
         
        //Load all mounts into the result table.
        command = "SELECT MountNum,DocCategory,DateCreated,Description,ImgType FROM mount WHERE PatNum='" + patNum + "'";
        raw = dcon.getTable(command);
        for (int i = 0;i < raw.Rows.Count;i++)
        {
            //Make sure hidden mounts are never added (there is a small possibility that one is added after all are made visible).
            if (DefC.GetOrder(DefCat.ImageCats, PIn.Long(raw.Rows[i]["DocCategory"].ToString())) < 0)
            {
                continue;
            }
             
            row = table.NewRow();
            row["DocNum"] = 0;
            row["MountNum"] = PIn.Long(raw.Rows[i]["MountNum"].ToString());
            row["DocCategory"] = PIn.Long(raw.Rows[i]["DocCategory"].ToString());
            row["DateCreated"] = PIn.Date(raw.Rows[i]["DateCreated"].ToString());
            row["docFolder"] = DefC.GetOrder(DefCat.ImageCats, PIn.Long(raw.Rows[i]["DocCategory"].ToString()));
            row["description"] = PIn.Date(raw.Rows[i]["DateCreated"].ToString()).ToString("d") + ": " + PIn.String(raw.Rows[i]["Description"].ToString());
            row["ImgType"] = PIn.Long(raw.Rows[i]["ImgType"].ToString());
            resultSet.Add(row);
        }
        resultSet.Sort();
        for (int i = 0;i < resultSet.Count;i++)
        {
            //Finally, move the results from the list into a data table.
            table.Rows.Add((DataRow)resultSet[i]);
        }
        return table;
    }

    /**
    * Returns false if the file is a specific short file name that is not accepted or contains one of the unsupported file exentions.
    */
    public static boolean isAcceptableFileName(String file) throws Exception {
        //No need to check RemotingRole; no call to db.
        String[] specificBadFileNames = new String[]{ "thumbs.db" };
        for (int i = 0;i < specificBadFileNames.Length;i++)
        {
            if (file.Length >= specificBadFileNames[i].Length && file.Substring(file.Length - specificBadFileNames[i].Length, specificBadFileNames[i].Length).ToLower() == specificBadFileNames[i])
            {
                return false;
            }
             
        }
        return true;
    }

    /**
    * When first opening the image module, this tests to see whether a given filename is in the database. Also used when naming a new document to ensure unique name.
    */
    public static boolean isFileNameInList(String fileName, String[] usedNames) throws Exception {
        for (int i = 0;i < usedNames.Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (StringSupport.equals(usedNames[i], fileName))
                return true;
             
        }
        return false;
    }

    //public static string GetFull
    /**
    * Only documents listed in the corresponding rows of the statement table are uploaded
    */
    public static List<long> getChangedSinceDocumentNums(DateTime changedSince, List<long> statementNumList) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<long>>GetObject(MethodBase.GetCurrentMethod(), changedSince, statementNumList);
        }
         
        String strStatementNums = "";
        DataTable table = new DataTable();
        if (statementNumList.Count > 0)
        {
            for (int i = 0;i < statementNumList.Count;i++)
            {
                if (i > 0)
                {
                    strStatementNums += "OR ";
                }
                 
                strStatementNums += "StatementNum='" + statementNumList[i].ToString() + "' ";
            }
            String command = "SELECT DocNum FROM document WHERE  DateTStamp > " + POut.dateT(changedSince) + " AND DocNum IN ( SELECT DocNum FROM statement WHERE " + strStatementNums + ")";
            table = Db.getTable(command);
        }
        else
        {
            table = new DataTable();
        } 
        List<long> documentnums = new List<long>(table.Rows.Count);
        for (int i = 0;i < table.Rows.Count;i++)
        {
            documentnums.Add(PIn.Long(table.Rows[i]["DocNum"].ToString()));
        }
        return documentnums;
    }

    /**
    * Used along with GetChangedSinceDocumentNums
    */
    public static List<OpenDentBusiness.Document> getMultDocuments(List<long> documentNums, String AtoZpath) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<OpenDentBusiness.Document>>GetObject(MethodBase.GetCurrentMethod(), documentNums, AtoZpath);
        }
         
        String strDocumentNums = "";
        DataTable table = new DataTable();
        if (documentNums.Count > 0)
        {
            for (int i = 0;i < documentNums.Count;i++)
            {
                if (i > 0)
                {
                    strDocumentNums += "OR ";
                }
                 
                strDocumentNums += "DocNum='" + documentNums[i].ToString() + "' ";
            }
            String command = "SELECT * FROM document WHERE " + strDocumentNums;
            table = Db.getTable(command);
        }
        else
        {
            table = new DataTable();
        } 
        OpenDentBusiness.Document[] multDocuments = Crud.DocumentCrud.TableToList(table).ToArray();
        List<OpenDentBusiness.Document> documentList = new List<OpenDentBusiness.Document>(multDocuments);
        for (Object __dummyForeachVar0 : documentList)
        {
            OpenDentBusiness.Document d = (OpenDentBusiness.Document)__dummyForeachVar0;
            if (String.IsNullOrEmpty(d.RawBase64))
            {
                Patient pat = Patients.getPat(d.PatNum);
                String filePathAndName = ImageStore.getFilePath(Documents.getByNum(d.DocNum),AtoZpath);
                if (File.Exists(filePathAndName))
                {
                    FileStream fs = new FileStream(filePathAndName, FileMode.Open, FileAccess.Read);
                    byte[] rawData = new byte[fs.Length];
                    fs.Read(rawData, 0, (int)fs.Length);
                    fs.Close();
                    d.RawBase64 = Convert.ToBase64String(rawData);
                }
                 
            }
             
        }
        return documentList;
    }

    /**
    * Changes the value of the DateTStamp column to the current time stamp for all documents of a patient
    */
    public static void resetTimeStamps(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patNum);
            return ;
        }
         
        String command = "UPDATE document SET DateTStamp = CURRENT_TIMESTAMP WHERE PatNum =" + POut.long(patNum);
        Db.nonQ(command);
    }

}


