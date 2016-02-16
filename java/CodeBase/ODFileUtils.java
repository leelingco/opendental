//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:25 PM
//

package CodeBase;


public class ODFileUtils   
{
    /**
    * Removes a trailing path separator from the given string if one exists.
    */
    public static String removeTrailingSeparators(String path) throws Exception {
        while (path != null && path.Length > 0 && (path[path.Length - 1] == '\\' || path[path.Length - 1] == '/'))
        {
            path = path.Substring(0, path.Length - 1);
        }
        return path;
    }

    public static String combinePaths(String path1, String path2) throws Exception {
        return CombinePaths(new String[]{ path1, path2 });
    }

    public static String combinePaths(String path1, String path2, String path3) throws Exception {
        return CombinePaths(new String[]{ path1, path2, path3 });
    }

    public static String combinePaths(String path1, String path2, String path3, String path4) throws Exception {
        return CombinePaths(new String[]{ path1, path2, path3, path4 });
    }

    /**
    * OS independent path cominations. Ensures that each of the given path pieces are separated by the correct path separator for the current operating system. There is guaranteed not to be a trailing path separator at the end of the returned string (to accomodate file paths), unless the last specified path piece in the array is the empty string.
    */
    public static String combinePaths(String[] paths) throws Exception {
        String finalPath = "";
        for (int i = 0;i < paths.Length;i++)
        {
            String path = RemoveTrailingSeparators(paths[i]);
            //Add an appropriate slash to divide the path peices, but do not use a trailing slash on the last piece.
            if (i < paths.Length - 1)
            {
                if (path != null && path.Length > 0)
                {
                    path = path + Path.DirectorySeparatorChar;
                }
                 
            }
             
            finalPath = finalPath + path;
        }
        return finalPath;
    }

    /**
    * Returns the directory in which the program executable rests. To get the full path for the program executable, use Applicaiton.ExecutablePath.
    */
    public static String getProgramDirectory() throws Exception {
        int endPos = Application.ExecutablePath.LastIndexOf(Path.DirectorySeparatorChar);
        return Application.ExecutablePath.Substring(0, endPos + 1);
    }

    /**
    * Creates a new randomly named file in the given directory path with the given extension and returns the full path to the new file.
    */
    public static String createRandomFile(String dir, String ext) throws Exception {
        if (ext.Length > 0 && ext[0] != '.')
        {
            ext = '.' + ext;
        }
         
        boolean fileCreated = false;
        String filePath = "";
        ;
        Random rand = new Random();
        do
        {
            String fileName = "";
            for (int i = 0;i < 6;i++)
            {
                fileName += randChrs[rand.Next(0, randChrs.Length - 1)];
            }
            fileName += DateTime.Now.ToString("yyyyMMddhhmmss");
            filePath = combinePaths(dir,fileName + ext);
            FileStream fs = null;
            try
            {
                fs = File.Create(filePath);
                fs.Dispose();
                fileCreated = true;
            }
            catch (Exception __dummyCatchVar0)
            {
            }
        
        }
        while (!fileCreated);
        return filePath;
    }

}


