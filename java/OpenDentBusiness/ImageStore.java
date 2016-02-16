//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:58 PM
//

package OpenDentBusiness;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDentBusiness.ComputerPrefs;
import OpenDentBusiness.Documents;
import OpenDentBusiness.EhrAmendment;
import OpenDentBusiness.EhrAmendments;
import OpenDentBusiness.EobAttach;
import OpenDentBusiness.EobAttaches;
import OpenDentBusiness.ImageHelper;
import OpenDentBusiness.ImageType;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Mount;
import OpenDentBusiness.MountItem;
import OpenDentBusiness.MountItems;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PIn;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.ReplicationServers;

/**
* 
*/
public class ImageStore   
{
    /**
    * Remembers the computerpref.AtoZpath.  Set to empty string on startup.  If set to something else, this path will override all other paths.
    */
    public static String LocalAtoZpath = null;
    /**
    * Only makes a call to the database on startup.  After that, just uses cached data.  Does not validate that the path exists except if the main one is used.  ONLY used from Client layer; no S classes.
    */
    public static String getPreferredAtoZpath() throws Exception {
        if (!PrefC.getAtoZfolderUsed())
        {
            return null;
        }
         
        if (LocalAtoZpath == null)
        {
            //on startup
            LocalAtoZpath = ComputerPrefs.getLocalComputer().AtoZpath;
        }
         
        String replicationAtoZ = ReplicationServers.getAtoZpath();
        if (!StringSupport.equals(replicationAtoZ, ""))
        {
            return replicationAtoZ;
        }
         
        if (!StringSupport.equals(LocalAtoZpath, ""))
        {
            return LocalAtoZpath;
        }
         
        return getValidPathFromString(PrefC.getString(PrefName.DocPath));
    }

    //use this to handle possible multiple paths separated by semicolons.
    public static String getValidPathFromString(String documentPaths) throws Exception {
        String[] preferredPathsByOrder = documentPaths.Split(new char[]{ ';' });
        for (int i = 0;i < preferredPathsByOrder.Length;i++)
        {
            String path = preferredPathsByOrder[i];
            String tryPath = CodeBase.ODFileUtils.combinePaths(path,"A");
            if (Directory.Exists(tryPath))
            {
                return path;
            }
             
        }
        return null;
    }

    /**
    * Will create folder if needed.  Will validate that folder exists.  It will alter the pat.ImageFolder if needed, but still make sure to pass in a very new Patient because we do not want an invalid patFolder.
    */
    public static String getPatientFolder(Patient pat, String AtoZpath) throws Exception {
        String retVal = "";
        if (StringSupport.equals(pat.ImageFolder, ""))
        {
            //creates new folder for patient if none present
            String name = pat.LName + pat.FName;
            String folder = "";
            for (int i = 0;i < name.Length;i++)
            {
                if (Char.IsLetter(name, i))
                {
                    folder += name.Substring(i, 1);
                }
                 
            }
            folder += pat.PatNum.ToString();
            try
            {
                //ensures unique name
                Patient PatOld = pat.copy();
                pat.ImageFolder = folder;
                retVal = CodeBase.ODFileUtils.CombinePaths(AtoZpath, pat.ImageFolder.Substring(0, 1).ToUpper(), pat.ImageFolder);
                Directory.CreateDirectory(retVal);
                Patients.update(pat,PatOld);
            }
            catch (Exception __dummyCatchVar0)
            {
                throw new Exception(Lans.g("ContrDocs","Error.  Could not create folder for patient. "));
            }
        
        }
        else
        {
            //patient folder already created once
            retVal = CodeBase.ODFileUtils.CombinePaths(AtoZpath, pat.ImageFolder.Substring(0, 1).ToUpper(), pat.ImageFolder);
        } 
        if (!Directory.Exists(retVal))
        {
            try
            {
                //this makes it more resiliant and allows copies
                //of the opendentaldata folder to be used in read-only situations.
                Directory.CreateDirectory(retVal);
            }
            catch (Exception __dummyCatchVar1)
            {
                throw new Exception(Lans.g("ContrDocs","Error.  Could not create folder for patient: ") + retVal);
            }
        
        }
         
        return retVal;
    }

    /**
    * Will create folder if needed.  Will validate that folder exists.
    */
    public static String getEobFolder() throws Exception {
        String retVal = CodeBase.ODFileUtils.combinePaths(getPreferredAtoZpath(),"EOBs");
        if (!Directory.Exists(retVal))
        {
            Directory.CreateDirectory(retVal);
        }
         
        return retVal;
    }

    /**
    * Will create folder if needed.  Will validate that folder exists.
    */
    public static String getAmdFolder() throws Exception {
        String retVal = CodeBase.ODFileUtils.combinePaths(getPreferredAtoZpath(),"Amendments");
        if (!Directory.Exists(retVal))
        {
            Directory.CreateDirectory(retVal);
        }
         
        return retVal;
    }

    /**
    * When the Image module is opened, this loads newly added files.
    */
    public static void addMissingFilesToDatabase(Patient pat) throws Exception {
        String patFolder = getPatientFolder(pat,getPreferredAtoZpath());
        DirectoryInfo di = new DirectoryInfo(patFolder);
        FileInfo[] fiList = di.GetFiles();
        List<String> fileList = new List<String>();
        for (int i = 0;i < fiList.Length;i++)
        {
            fileList.Add(fiList[i].FullName);
        }
        int countAdded = Documents.InsertMissing(pat, fileList);
    }

    //should notify user
    //if(countAdded > 0) {
    //	Debug.WriteLine(countAdded.ToString() + " documents found and added to the first category.");
    //}
    //it will refresh in FillDocList
    public static String getHashString(OpenDentBusiness.Document doc, String patFolder) throws Exception {
        //the key data is the bytes of the file, concatenated with the bytes of the note.
        byte[] textbytes = new byte[]();
        if (doc.Note == null)
        {
            textbytes = Encoding.UTF8.GetBytes("");
        }
        else
        {
            textbytes = Encoding.UTF8.GetBytes(doc.Note);
        } 
        byte[] filebytes = getBytes(doc,patFolder);
        int fileLength = filebytes.Length;
        byte[] buffer = new byte[textbytes.Length + filebytes.Length];
        Array.Copy(filebytes, 0, buffer, 0, fileLength);
        Array.Copy(textbytes, 0, buffer, fileLength, textbytes.Length);
        HashAlgorithm algorithm = MD5.Create();
        byte[] hash = algorithm.ComputeHash(buffer);
        return Encoding.ASCII.GetString(hash);
    }

    //always results in length of 16.
    public static Collection<Bitmap> openImages(IList<OpenDentBusiness.Document> documents, String patFolder) throws Exception {
        //string patFolder=GetPatientFolder(pat);
        Collection<Bitmap> bitmaps = new Collection<Bitmap>();
        for (Object __dummyForeachVar0 : documents)
        {
            OpenDentBusiness.Document document = (OpenDentBusiness.Document)__dummyForeachVar0;
            if (document == null)
            {
                bitmaps.Add(null);
            }
            else
            {
                bitmaps.Add(openImage(document,patFolder));
            } 
        }
        return bitmaps;
    }

    public static Bitmap[] openImages(OpenDentBusiness.Document[] documents, String patFolder) throws Exception {
        Bitmap[] values = new Bitmap[documents.Length];
        Collection<Bitmap> bitmaps = OpenImages(new Collection<OpenDentBusiness.Document>(documents), patFolder);
        bitmaps.CopyTo(values, 0);
        return values;
    }

    public static Bitmap openImage(OpenDentBusiness.Document doc, String patFolder) throws Exception {
        if (PrefC.getAtoZfolderUsed())
        {
            String srcFileName = CodeBase.ODFileUtils.combinePaths(patFolder,doc.FileName);
            if (hasImageExtension(srcFileName))
            {
                //if(File.Exists(srcFileName) && HasImageExtension(srcFileName)) {
                if (File.Exists(srcFileName))
                {
                    return new Bitmap(srcFileName);
                }
                else
                {
                    return null;
                } 
            }
            else
            {
                return null;
            } 
        }
        else
        {
            //throw new Exception();
            if (hasImageExtension(doc.FileName))
            {
                return PIn.bitmap(doc.RawBase64);
            }
            else
            {
                return null;
            } 
        } 
    }

    public static Bitmap[] openImagesEob(EobAttach eob) throws Exception {
        Bitmap[] values = new Bitmap[1];
        if (PrefC.getAtoZfolderUsed())
        {
            String eobFolder = getEobFolder();
            String srcFileName = CodeBase.ODFileUtils.combinePaths(eobFolder,eob.FileName);
            if (hasImageExtension(srcFileName))
            {
                if (File.Exists(srcFileName))
                {
                    values[0] = new Bitmap(srcFileName);
                }
                else
                {
                    //throw new Exception();
                    values[0] = null;
                } 
            }
            else
            {
                values[0] = null;
            } 
        }
        else
        {
            if (hasImageExtension(eob.FileName))
            {
                values[0] = PIn.bitmap(eob.RawBase64);
            }
            else
            {
                values[0] = null;
            } 
        } 
        return values;
    }

    public static Bitmap[] openImagesAmd(EhrAmendment amd) throws Exception {
        Bitmap[] values = new Bitmap[1];
        if (PrefC.getAtoZfolderUsed())
        {
            String amdFolder = getAmdFolder();
            String srcFileName = CodeBase.ODFileUtils.combinePaths(amdFolder,amd.FileName);
            if (hasImageExtension(srcFileName))
            {
                if (File.Exists(srcFileName))
                {
                    values[0] = new Bitmap(srcFileName);
                }
                else
                {
                    //throw new Exception();
                    values[0] = null;
                } 
            }
            else
            {
                values[0] = null;
            } 
        }
        else
        {
            if (hasImageExtension(amd.FileName))
            {
                values[0] = PIn.bitmap(amd.RawBase64);
            }
            else
            {
                values[0] = null;
            } 
        } 
        return values;
    }

    /**
    * Takes in a mount object and finds all the images pertaining to the mount, then combines them together into one large, unscaled image and returns that image. For use in other modules.
    */
    public static Bitmap getMountImage(Mount mount, String patFolder) throws Exception {
        //string patFolder=GetPatientFolder(pat);
        List<MountItem> mountItems = MountItems.getItemsForMount(mount.MountNum);
        OpenDentBusiness.Document[] documents = Documents.GetDocumentsForMountItems(mountItems);
        Bitmap[] originalImages = OpenImages(documents, patFolder);
        Bitmap mountImage = new Bitmap(mount.Width, mount.Height);
        ImageHelper.RenderMountImage(mountImage, originalImages, mountItems, documents, -1);
        return mountImage;
    }

    public static byte[] getBytes(OpenDentBusiness.Document doc, String patFolder) throws Exception {
        /*if(ImageStoreIsDatabase) {not supported
        				byte[] buffer;
        				using(IDbConnection connection = DataSettings.GetConnection())
        				using(IDbCommand command = connection.CreateCommand()) {
        					command.CommandText =	@"SELECT Data FROM files WHERE DocNum = ?DocNum";
        					IDataParameter docNumParameter = command.CreateParameter();
        					docNumParameter.ParameterName = "?DocNum";
        					docNumParameter.Value = doc.DocNum;
        					command.Parameters.Add(docNumParameter);
        					connection.Open();
        					buffer = (byte[])command.ExecuteScalar();
        					connection.Close();
        				}
        				return buffer;
        			}
        			else {*/
        String path = CodeBase.ODFileUtils.combinePaths(patFolder,doc.FileName);
        if (!File.Exists(path))
        {
            return new byte[]{  };
        }
         
        byte[] buffer = new byte[]();
        FileStream fs = new FileStream(path, FileMode.Open, FileAccess.Read, FileShare.Read);
        try
        {
            {
                int fileLength = (int)fs.Length;
                buffer = new byte[fileLength];
                fs.Read(buffer, 0, fileLength);
            }
        }
        finally
        {
            if (fs != null)
                Disposable.mkDisposable(fs).dispose();
             
        }
        return buffer;
    }

    /**
    * 
    */
    public static OpenDentBusiness.Document import(String pathImportFrom, long docCategory, Patient pat) throws Exception {
        String patFolder = "";
        if (PrefC.getAtoZfolderUsed())
        {
            patFolder = getPatientFolder(pat,getPreferredAtoZpath());
        }
         
        OpenDentBusiness.Document doc = new OpenDentBusiness.Document();
        //Document.Insert will use this extension when naming:
        if (StringSupport.equals(Path.GetExtension(pathImportFrom), ""))
        {
            //If the file has no extension
            doc.FileName = ".jpg";
        }
        else
        {
            doc.FileName = Path.GetExtension(pathImportFrom);
        } 
        doc.DateCreated = File.GetLastWriteTime(pathImportFrom);
        doc.PatNum = pat.PatNum;
        if (hasImageExtension(doc.FileName))
        {
            doc.ImgType = ImageType.Photo;
        }
        else
        {
            doc.ImgType = ImageType.Document;
        } 
        doc.DocCategory = docCategory;
        Documents.insert(doc,pat);
        //this assigns a filename and saves to db
        doc = Documents.getByNum(doc.DocNum);
        try
        {
            saveDocument(doc,pathImportFrom,patFolder);
            if (PrefC.getAtoZfolderUsed())
            {
                Documents.update(doc);
            }
             
        }
        catch (Exception __dummyCatchVar2)
        {
            Documents.delete(doc);
            throw __dummyCatchVar2;
        }

        return doc;
    }

    /**
    * Saves to either AtoZ folder or to db.  Saves image as a jpg.  Compression will differ depending on imageType.
    */
    public static OpenDentBusiness.Document import(Bitmap image, long docCategory, ImageType imageType, Patient pat) throws Exception {
        String patFolder = "";
        if (PrefC.getAtoZfolderUsed())
        {
            patFolder = getPatientFolder(pat,getPreferredAtoZpath());
        }
         
        OpenDentBusiness.Document doc = new OpenDentBusiness.Document();
        doc.ImgType = imageType;
        doc.FileName = ".jpg";
        doc.DateCreated = DateTime.Today;
        doc.PatNum = pat.PatNum;
        doc.DocCategory = docCategory;
        Documents.insert(doc,pat);
        //creates filename and saves to db
        doc = Documents.getByNum(doc.DocNum);
        long qualityL = 0;
        if (imageType == ImageType.Radiograph)
        {
            qualityL = 100;
        }
        else if (imageType == ImageType.Photo)
        {
            qualityL = 100;
        }
        else
        {
            //Assume document
            //Possible values 0-100?
            qualityL = (long)ComputerPrefs.getLocalComputer().ScanDocQuality;
        }  
        ImageCodecInfo myImageCodecInfo = new ImageCodecInfo();
        ImageCodecInfo[] encoders = new ImageCodecInfo[]();
        encoders = ImageCodecInfo.GetImageEncoders();
        myImageCodecInfo = null;
        for (int j = 0;j < encoders.Length;j++)
        {
            if (StringSupport.equals(encoders[j].MimeType, "image/jpeg"))
            {
                myImageCodecInfo = encoders[j];
            }
             
        }
        EncoderParameters myEncoderParameters = new EncoderParameters(1);
        EncoderParameter myEncoderParameter = new EncoderParameter(System.Drawing.Imaging.Encoder.Quality, qualityL);
        myEncoderParameters.Param[0] = myEncoderParameter;
        try
        {
            //AutoCrop()?
            saveDocument(doc,image,myImageCodecInfo,myEncoderParameters,patFolder);
            if (!PrefC.getAtoZfolderUsed())
            {
                Documents.update(doc);
            }
             
        }
        catch (Exception __dummyCatchVar3)
        {
            //because SaveDocument stuck the image in doc.RawBase64.
            //no thumbnail yet
            Documents.delete(doc);
            throw __dummyCatchVar3;
        }

        return doc;
    }

    /**
    * Obviously no support for db storage
    */
    public static OpenDentBusiness.Document importForm(String form, long docCategory, Patient pat) throws Exception {
        String patFolder = getPatientFolder(pat,getPreferredAtoZpath());
        String pathSourceFile = CodeBase.ODFileUtils.combinePaths(getPreferredAtoZpath(),"Forms",form);
        if (!File.Exists(pathSourceFile))
        {
            throw new Exception(Lans.g("ContrDocs","Could not find file: ") + pathSourceFile);
        }
         
        OpenDentBusiness.Document doc = new OpenDentBusiness.Document();
        doc.FileName = Path.GetExtension(pathSourceFile);
        doc.DateCreated = DateTime.Today;
        doc.DocCategory = docCategory;
        doc.PatNum = pat.PatNum;
        doc.ImgType = ImageType.Document;
        Documents.insert(doc,pat);
        //this assigns a filename and saves to db
        doc = Documents.getByNum(doc.DocNum);
        try
        {
            saveDocument(doc,pathSourceFile,patFolder);
        }
        catch (Exception __dummyCatchVar4)
        {
            Documents.delete(doc);
            throw __dummyCatchVar4;
        }

        return doc;
    }

    /**
    * Always saves as bmp.  So the 'paste to mount' logic needs to be changed to prevent conversion to bmp.
    */
    public static OpenDentBusiness.Document importImageToMount(Bitmap image, short rotationAngle, long mountItemNum, long docCategory, Patient pat) throws Exception {
        String patFolder = getPatientFolder(pat,getPreferredAtoZpath());
        String fileExtention = ".bmp";
        //The file extention to save the greyscale image as.
        OpenDentBusiness.Document doc = new OpenDentBusiness.Document();
        doc.MountItemNum = mountItemNum;
        doc.DegreesRotated = rotationAngle;
        doc.ImgType = ImageType.Radiograph;
        doc.FileName = fileExtention;
        doc.DateCreated = DateTime.Today;
        doc.PatNum = pat.PatNum;
        doc.DocCategory = docCategory;
        doc.WindowingMin = PrefC.getInt(PrefName.ImageWindowingMin);
        doc.WindowingMax = PrefC.getInt(PrefName.ImageWindowingMax);
        Documents.insert(doc,pat);
        //creates filename and saves to db
        doc = Documents.getByNum(doc.DocNum);
        try
        {
            SaveDocument(doc, image, ImageFormat.Bmp, patFolder);
        }
        catch (Exception __dummyCatchVar5)
        {
            Documents.delete(doc);
            throw __dummyCatchVar5;
        }

        return doc;
    }

    /**
    * Saves to either AtoZ folder or to db.  Saves image as a jpg.  Compression will be according to user setting.
    */
    public static EobAttach importEobAttach(Bitmap image, long claimPaymentNum) throws Exception {
        String eobFolder = "";
        if (PrefC.getAtoZfolderUsed())
        {
            eobFolder = getEobFolder();
        }
         
        EobAttach eob = new EobAttach();
        eob.FileName = ".jpg";
        eob.DateTCreated = DateTime.Now;
        eob.ClaimPaymentNum = claimPaymentNum;
        EobAttaches.insert(eob);
        //creates filename and saves to db
        eob = EobAttaches.getOne(eob.EobAttachNum);
        long qualityL = (long)ComputerPrefs.getLocalComputer().ScanDocQuality;
        ImageCodecInfo myImageCodecInfo = new ImageCodecInfo();
        ImageCodecInfo[] encoders = new ImageCodecInfo[]();
        encoders = ImageCodecInfo.GetImageEncoders();
        myImageCodecInfo = null;
        for (int j = 0;j < encoders.Length;j++)
        {
            if (StringSupport.equals(encoders[j].MimeType, "image/jpeg"))
            {
                myImageCodecInfo = encoders[j];
            }
             
        }
        EncoderParameters myEncoderParameters = new EncoderParameters(1);
        EncoderParameter myEncoderParameter = new EncoderParameter(System.Drawing.Imaging.Encoder.Quality, qualityL);
        myEncoderParameters.Param[0] = myEncoderParameter;
        try
        {
            saveEobAttach(eob,image,myImageCodecInfo,myEncoderParameters,eobFolder);
            if (!PrefC.getAtoZfolderUsed())
            {
                EobAttaches.update(eob);
            }
             
        }
        catch (Exception __dummyCatchVar6)
        {
            //because SaveEobAttach stuck the image in EobAttach.RawBase64.
            //no thumbnail
            EobAttaches.delete(eob.EobAttachNum);
            throw __dummyCatchVar6;
        }

        return eob;
    }

    /**
    * 
    */
    public static EobAttach importEobAttach(String pathImportFrom, long claimPaymentNum) throws Exception {
        String eobFolder = "";
        if (PrefC.getAtoZfolderUsed())
        {
            eobFolder = getEobFolder();
        }
         
        EobAttach eob = new EobAttach();
        if (StringSupport.equals(Path.GetExtension(pathImportFrom), ""))
        {
            //If the file has no extension
            eob.FileName = ".jpg";
        }
        else
        {
            eob.FileName = Path.GetExtension(pathImportFrom);
        } 
        eob.DateTCreated = File.GetLastWriteTime(pathImportFrom);
        eob.ClaimPaymentNum = claimPaymentNum;
        EobAttaches.insert(eob);
        //creates filename and saves to db
        eob = EobAttaches.getOne(eob.EobAttachNum);
        try
        {
            saveEobAttach(eob,pathImportFrom,eobFolder);
            if (PrefC.getAtoZfolderUsed())
            {
                EobAttaches.update(eob);
            }
             
        }
        catch (Exception __dummyCatchVar7)
        {
            EobAttaches.delete(eob.EobAttachNum);
            throw __dummyCatchVar7;
        }

        return eob;
    }

    public static EhrAmendment importAmdAttach(Bitmap image, EhrAmendment amd) throws Exception {
        String amdFolder = "";
        if (PrefC.getAtoZfolderUsed())
        {
            amdFolder = getAmdFolder();
        }
         
        amd.FileName = DateTime.Now.ToString("yyyyMMdd_HHmmss_") + amd.EhrAmendmentNum;
        amd.FileName += ".jpg";
        amd.DateTAppend = DateTime.Now;
        EhrAmendments.update(amd);
        amd = EhrAmendments.getOne(amd.EhrAmendmentNum);
        long qualityL = (long)ComputerPrefs.getLocalComputer().ScanDocQuality;
        ImageCodecInfo myImageCodecInfo = new ImageCodecInfo();
        ImageCodecInfo[] encoders = new ImageCodecInfo[]();
        encoders = ImageCodecInfo.GetImageEncoders();
        myImageCodecInfo = null;
        for (int j = 0;j < encoders.Length;j++)
        {
            if (StringSupport.equals(encoders[j].MimeType, "image/jpeg"))
            {
                myImageCodecInfo = encoders[j];
            }
             
        }
        EncoderParameters myEncoderParameters = new EncoderParameters(1);
        EncoderParameter myEncoderParameter = new EncoderParameter(System.Drawing.Imaging.Encoder.Quality, qualityL);
        myEncoderParameters.Param[0] = myEncoderParameter;
        try
        {
            saveAmdAttach(amd,image,myImageCodecInfo,myEncoderParameters,amdFolder);
        }
        catch (Exception __dummyCatchVar8)
        {
            throw __dummyCatchVar8;
        }

        //EhrAmendments.Delete(amd.EhrAmendmentNum);
        if (!PrefC.getAtoZfolderUsed())
        {
        }
         
        return amd;
    }

    //EhrAmendments.Update(amd);
    //no thumbnail
    public static EhrAmendment importAmdAttach(String pathImportFrom, EhrAmendment amd) throws Exception {
        String amdFolder = "";
        String amdFilename = "";
        if (PrefC.getAtoZfolderUsed())
        {
            amdFolder = getAmdFolder();
            amdFilename = amd.FileName;
        }
         
        amd.FileName = DateTime.Now.ToString("yyyyMMdd_HHmmss_") + amd.EhrAmendmentNum + Path.GetExtension(pathImportFrom);
        if (StringSupport.equals(Path.GetExtension(pathImportFrom), ""))
        {
            //If the file has no extension
            amd.FileName += ".jpg";
        }
         
        try
        {
            //EhrAmendments.Update(amd);
            //amd=EhrAmendments.GetOne(amd.EhrAmendmentNum);
            saveAmdAttach(amd,pathImportFrom,amdFolder);
        }
        catch (Exception __dummyCatchVar9)
        {
            throw __dummyCatchVar9;
        }

        //EhrAmendments.Delete(amd.EhrAmendmentNum);
        if (PrefC.getAtoZfolderUsed())
        {
            amd.DateTAppend = DateTime.Now;
            EhrAmendments.update(amd);
            cleanAmdAttach(amdFilename);
        }
         
        return amd;
    }

    /**
    * Save a Document to another location on the disk (outside of Open Dental).
    */
    public static void export(String saveToPath, OpenDentBusiness.Document doc, Patient pat) throws Exception {
        if (PrefC.getAtoZfolderUsed())
        {
            String patFolder = getPatientFolder(pat,getPreferredAtoZpath());
            File.Copy(CodeBase.ODFileUtils.combinePaths(patFolder,doc.FileName), saveToPath);
        }
        else
        {
            //image is in database
            byte[] rawData = Convert.FromBase64String(doc.RawBase64);
            Image image = null;
            MemoryStream stream = new MemoryStream();
            try
            {
                {
                    stream.Read(rawData, 0, rawData.Length);
                    image = Image.FromStream(stream);
                }
            }
            finally
            {
                if (stream != null)
                    Disposable.mkDisposable(stream).dispose();
                 
            }
            image.Save(saveToPath);
        } 
    }

    /**
    * Save an Eob to another location on the disk (outside of Open Dental).
    */
    public static void exportEobAttach(String saveToPath, EobAttach eob) throws Exception {
        if (PrefC.getAtoZfolderUsed())
        {
            String eobFolder = getEobFolder();
            File.Copy(CodeBase.ODFileUtils.combinePaths(eobFolder,eob.FileName), saveToPath);
        }
        else
        {
            //image is in database
            byte[] rawData = Convert.FromBase64String(eob.RawBase64);
            Image image = null;
            MemoryStream stream = new MemoryStream();
            try
            {
                {
                    stream.Read(rawData, 0, rawData.Length);
                    image = Image.FromStream(stream);
                }
            }
            finally
            {
                if (stream != null)
                    Disposable.mkDisposable(stream).dispose();
                 
            }
            image.Save(saveToPath);
        } 
    }

    /**
    * Save an Eob to another location on the disk (outside of Open Dental).
    */
    public static void exportAmdAttach(String saveToPath, EhrAmendment amd) throws Exception {
        if (PrefC.getAtoZfolderUsed())
        {
            String amdFolder = getAmdFolder();
            File.Copy(CodeBase.ODFileUtils.combinePaths(amdFolder,amd.FileName), saveToPath);
        }
        else
        {
            //image is in database
            byte[] rawData = Convert.FromBase64String(amd.RawBase64);
            Image image = null;
            MemoryStream stream = new MemoryStream();
            try
            {
                {
                    stream.Read(rawData, 0, rawData.Length);
                    image = Image.FromStream(stream);
                }
            }
            finally
            {
                if (stream != null)
                    Disposable.mkDisposable(stream).dispose();
                 
            }
            image.Save(saveToPath);
        } 
    }

    /**
    * If using AtoZ folder, then patFolder must be fully qualified and valid.  If not usingAtoZ folder, this fills the doc.RawBase64 which must then be updated to db.  The image format can be bmp, jpg, etc, but this overload does not allow specifying jpg compression quality.
    */
    public static void saveDocument(OpenDentBusiness.Document doc, Bitmap image, ImageFormat format, String patFolder) throws Exception {
        if (PrefC.getAtoZfolderUsed())
        {
            String pathFileOut = CodeBase.ODFileUtils.combinePaths(patFolder,doc.FileName);
            image.Save(pathFileOut);
        }
        else
        {
            //saving to db
            MemoryStream stream = new MemoryStream();
            try
            {
                {
                    image.Save(stream, format);
                    byte[] rawData = stream.ToArray();
                    doc.RawBase64 = Convert.ToBase64String(rawData);
                }
            }
            finally
            {
                if (stream != null)
                    Disposable.mkDisposable(stream).dispose();
                 
            }
        } 
    }

    /**
    * If usingAtoZfoler, then patFolder must be fully qualified and valid.  If not usingAtoZ folder, this fills the doc.RawBase64 which must then be updated to db.
    */
    public static void saveDocument(OpenDentBusiness.Document doc, Bitmap image, ImageCodecInfo codec, EncoderParameters encoderParameters, String patFolder) throws Exception {
        if (!PrefC.getAtoZfolderUsed())
        {
            //if saving to db
            MemoryStream stream = new MemoryStream();
            try
            {
                {
                    image.Save(stream, codec, encoderParameters);
                    byte[] rawData = stream.ToArray();
                    doc.RawBase64 = Convert.ToBase64String(rawData);
                }
            }
            finally
            {
                if (stream != null)
                    Disposable.mkDisposable(stream).dispose();
                 
            }
        }
        else
        {
            //if saving to AtoZ folder
            image.Save(CodeBase.ODFileUtils.combinePaths(patFolder,doc.FileName), codec, encoderParameters);
        } 
    }

    /**
    * If usingAtoZfoler, then patFolder must be fully qualified and valid.  If not usingAtoZ folder, this fills the eob.RawBase64 which must then be updated to db.
    */
    public static void saveDocument(OpenDentBusiness.Document doc, String pathSourceFile, String patFolder) throws Exception {
        if (PrefC.getAtoZfolderUsed())
        {
            File.Copy(pathSourceFile, CodeBase.ODFileUtils.combinePaths(patFolder,doc.FileName));
        }
        else
        {
            //saving to db
            byte[] rawData = File.ReadAllBytes(pathSourceFile);
            doc.RawBase64 = Convert.ToBase64String(rawData);
        } 
    }

    /**
    * If usingAtoZfoler, then patFolder must be fully qualified and valid.  If not usingAtoZ folder, this fills the eob.RawBase64 which must then be updated to db.
    */
    public static void saveEobAttach(EobAttach eob, Bitmap image, ImageCodecInfo codec, EncoderParameters encoderParameters, String eobFolder) throws Exception {
        if (PrefC.getAtoZfolderUsed())
        {
            image.Save(CodeBase.ODFileUtils.combinePaths(eobFolder,eob.FileName), codec, encoderParameters);
        }
        else
        {
            //saving to db
            MemoryStream stream = new MemoryStream();
            try
            {
                {
                    image.Save(stream, codec, encoderParameters);
                    byte[] rawData = stream.ToArray();
                    eob.RawBase64 = Convert.ToBase64String(rawData);
                }
            }
            finally
            {
                if (stream != null)
                    Disposable.mkDisposable(stream).dispose();
                 
            }
        } 
    }

    /**
    * If usingAtoZfolder, then patFolder must be fully qualified and valid.  If not usingAtoZ folder, this fills the eob.RawBase64 which must then be updated to db.
    */
    public static void saveAmdAttach(EhrAmendment amd, Bitmap image, ImageCodecInfo codec, EncoderParameters encoderParameters, String amdFolder) throws Exception {
        if (PrefC.getAtoZfolderUsed())
        {
            image.Save(CodeBase.ODFileUtils.combinePaths(amdFolder,amd.FileName), codec, encoderParameters);
        }
        else
        {
            //saving to db
            MemoryStream stream = new MemoryStream();
            try
            {
                {
                    image.Save(stream, codec, encoderParameters);
                    byte[] rawData = stream.ToArray();
                    amd.RawBase64 = Convert.ToBase64String(rawData);
                    EhrAmendments.update(amd);
                }
            }
            finally
            {
                if (stream != null)
                    Disposable.mkDisposable(stream).dispose();
                 
            }
        } 
    }

    /**
    * If usingAtoZfolder, then eobFolder must be fully qualified and valid.  If not usingAtoZ folder, this fills the eob.RawBase64 which must then be updated to db.
    */
    public static void saveEobAttach(EobAttach eob, String pathSourceFile, String eobFolder) throws Exception {
        if (PrefC.getAtoZfolderUsed())
        {
            File.Copy(pathSourceFile, CodeBase.ODFileUtils.combinePaths(eobFolder,eob.FileName));
        }
        else
        {
            //saving to db
            byte[] rawData = File.ReadAllBytes(pathSourceFile);
            eob.RawBase64 = Convert.ToBase64String(rawData);
        } 
    }

    /**
    * If usingAtoZfoler, then eobFolder must be fully qualified and valid.  If not usingAtoZ folder, this fills the eob.RawBase64 which must then be updated to db.
    */
    public static void saveAmdAttach(EhrAmendment amd, String pathSourceFile, String amdFolder) throws Exception {
        if (PrefC.getAtoZfolderUsed())
        {
            File.Copy(pathSourceFile, CodeBase.ODFileUtils.combinePaths(amdFolder,amd.FileName));
        }
        else
        {
            //saving to db
            byte[] rawData = File.ReadAllBytes(pathSourceFile);
            amd.RawBase64 = Convert.ToBase64String(rawData);
            EhrAmendments.update(amd);
        } 
    }

    /**
    * For each of the documents in the list, deletes row from db and image from AtoZ folder if needed.  Throws exception if the file cannot be deleted.  Surround in try/catch.
    */
    public static void deleteDocuments(IList<OpenDentBusiness.Document> documents, String patFolder) throws Exception {
        for (int i = 0;i < documents.Count;i++)
        {
            if (documents[i] == null)
            {
                continue;
            }
             
            if (PrefC.getAtoZfolderUsed())
            {
                try
                {
                    String filePath = CodeBase.ODFileUtils.CombinePaths(patFolder, documents[i].FileName);
                    if (File.Exists(filePath))
                    {
                        File.Delete(filePath);
                    }
                     
                }
                catch (Exception __dummyCatchVar10)
                {
                    throw new Exception(Lans.g("ContrImages","Could not delete file, it may be in use."));
                }
            
            }
             
            //Row from db.  This deletes the "image file" also if it's stored in db.
            Documents.Delete(documents[i]);
        }
    }

    /**
    * Also handles deletion of db object.
    */
    public static void deleteEobAttach(EobAttach eob) throws Exception {
        if (PrefC.getAtoZfolderUsed())
        {
            String eobFolder = getEobFolder();
            String filePath = CodeBase.ODFileUtils.combinePaths(eobFolder,eob.FileName);
            if (File.Exists(filePath))
            {
                try
                {
                    File.Delete(filePath);
                }
                catch (Exception __dummyCatchVar11)
                {
                }
            
            }
             
        }
         
        //file seems to be frequently locked.
        //db
        EobAttaches.delete(eob.EobAttachNum);
    }

    /**
    * Also handles deletion of db object.
    */
    public static void deleteAmdAttach(EhrAmendment amendment) throws Exception {
        if (PrefC.getAtoZfolderUsed())
        {
            String amdFolder = getAmdFolder();
            String filePath = CodeBase.ODFileUtils.combinePaths(amdFolder,amendment.FileName);
            if (File.Exists(filePath))
            {
                try
                {
                    File.Delete(filePath);
                }
                catch (Exception __dummyCatchVar12)
                {
                    MessageBox.Show("Delete was unsuccessful. The file may be in use.");
                    return ;
                }
            
            }
             
        }
         
        //file seems to be frequently locked.
        //db
        amendment.DateTAppend = DateTime.MinValue;
        amendment.FileName = "";
        amendment.RawBase64 = "";
        EhrAmendments.update(amendment);
    }

    /**
    * Cleans up unreferenced Amendments
    */
    public static void cleanAmdAttach(String amdFileName) throws Exception {
        if (PrefC.getAtoZfolderUsed())
        {
            String amdFolder = getAmdFolder();
            String filePath = CodeBase.ODFileUtils.combinePaths(amdFolder,amdFileName);
            if (File.Exists(filePath))
            {
                try
                {
                    File.Delete(filePath);
                }
                catch (Exception __dummyCatchVar13)
                {
                    return ;
                }
            
            }
             
        }
         
    }

    //MessageBox.Show("Delete was unsuccessful. The file may be in use.");
    //file seems to be frequently locked.
    /**
    * 
    */
    public static void deleteThumbnailImage(OpenDentBusiness.Document doc, String patFolder) throws Exception {
        /*if(ImageStoreIsDatabase) {
        				using(IDbConnection connection = DataSettings.GetConnection())
        				using(IDbCommand command = connection.CreateCommand()) {
        					command.CommandText =
        					@"UPDATE files SET Thumbnail = NULL WHERE DocNum = ?DocNum";
        					IDataParameter docNumParameter = command.CreateParameter();
        					docNumParameter.ParameterName = "?DocNum";
        					docNumParameter.Value = doc.DocNum;
        					command.Parameters.Add(docNumParameter);
        					connection.Open();
        					command.ExecuteNonQuery();
        					connection.Close();
        				}
        			}
        			else {*/
        //string patFolder=GetPatientFolder(pat);
        String thumbnailFile = CodeBase.ODFileUtils.combinePaths(patFolder,"Thumbnails",doc.FileName);
        if (File.Exists(thumbnailFile))
        {
            try
            {
                File.Delete(thumbnailFile);
            }
            catch (Exception __dummyCatchVar14)
            {
            }
        
        }
         
    }

    //Two users *might* edit the same image at the same time, so the image might already be deleted.
    public static String getExtension(OpenDentBusiness.Document doc) throws Exception {
        return Path.GetExtension(doc.FileName).ToLower();
    }

    public static String getFilePath(OpenDentBusiness.Document doc, String patFolder) throws Exception {
        return CodeBase.ODFileUtils.combinePaths(patFolder,doc.FileName);
    }

    //string patFolder=GetPatientFolder(pat);
    /*
    		public static bool IsImageFile(string filename) {
    			try {
    				Bitmap bitmap = new Bitmap(filename);
    				bitmap.Dispose();
    				bitmap=null;
    				return true;
    			}
    			catch {
    				return false;
    			}
    		}*/
    /**
    * Returns true if the given filename contains a supported file image extension.
    */
    public static boolean hasImageExtension(String fileName) throws Exception {
        String ext = Path.GetExtension(fileName).ToLower();
        return (StringSupport.equals(ext, ".jpg") || StringSupport.equals(ext, ".jpeg") || StringSupport.equals(ext, ".tga") || StringSupport.equals(ext, ".bmp") || StringSupport.equals(ext, ".tif") || StringSupport.equals(ext, ".tiff") || StringSupport.equals(ext, ".gif") || StringSupport.equals(ext, ".emf") || StringSupport.equals(ext, ".exif") || StringSupport.equals(ext, ".ico") || StringSupport.equals(ext, ".png") || StringSupport.equals(ext, ".wmf"));
    }

}


//The following supported bitmap types were found on a microsoft msdn page: