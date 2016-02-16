//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ComputerPref;
import OpenDentBusiness.TableBase;

/**
* Enables preference specification for individual computers on a customer network.
*/
public class ComputerPref  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ComputerPrefNum = new long();
    /**
    * The human-readable name of the computer on the network (not the IP address).
    */
    public String ComputerName = new String();
    /**
    * Set to true if the tooth chart is to use a hardware accelerated OpenGL window when available. Set to false to use software rendering when available. Of course, the final pixel format on the customer machine depends on the list of available formats. Best match pixel format is always used. This option only applies if GraphicsSimple is set to false.
    */
    public boolean GraphicsUseHardware = new boolean();
    /**
    * Enum:DrawingMode Set to 1 to use the low-quality 2D tooth chart in the chart module. Set to 0 to use a 3D DirectX based tooth chart in the chart module. This option helps the program run even when the local graphics hardware is buggy or unavailable.
    */
    public OpenDentBusiness.DrawingMode GraphicsSimple = OpenDentBusiness.DrawingMode.DirectX;
    /**
    * Indicates the type of Suni sensor connected to the local computer (if any). This can be a value of A, B, C, or D.
    */
    public String SensorType = new String();
    /**
    * Indicates wether or not the Suni sensor uses binned operation.
    */
    public boolean SensorBinned = new boolean();
    /**
    * Indicates which Suni box port to connect with. There are 2 ports on a box (ports 0 and 1).
    */
    public int SensorPort = new int();
    /**
    * Indicates the exposure level to use when capturing from a Suni sensor. Values can be 1 through 7.
    */
    public int SensorExposure = new int();
    /**
    * Indicates if the user prefers double-buffered 3D tooth-chart (where applicable).
    */
    public boolean GraphicsDoubleBuffering = new boolean();
    /**
    * Indicates the current pixel format by number which the user prefers.
    */
    public int PreferredPixelFormatNum = new int();
    /**
    * The path of the A-Z folder for the specified computer.  Overrides the officewide default.  Used when multiple locations are on a single virtual database and they each want to look to the local data folder for images.
    */
    public String AtoZpath = new String();
    /**
    * If the global setting for showing the Task List is on, this controls if it should be hidden on this specified computer
    */
    public boolean TaskKeepListHidden = new boolean();
    /**
    * Dock task bar on bottom (0) or right (1).
    */
    public int TaskDock = new int();
    /**
    * X pos for right docked task list.
    */
    public int TaskX = new int();
    /**
    * Y pos for bottom docked task list.
    */
    public int TaskY = new int();
    /**
    * Holds a semi-colon separated list of enumeration names and values representing a DirectX format. If blank, then
    * no format is currently set and the best theoretical foramt will be chosen at program startup. If this value is set to
    * 'opengl' then this computer is using OpenGL and a DirectX format will not be picked.
    */
    public String DirectXFormat = new String();
    /**
    * The index of the most recent appt view for this computer.  Uses it when opening.
    */
    public byte RecentApptView = new byte();
    /**
    * Show the select scanner dialog when scanning documents.
    */
    public boolean ScanDocSelectSource = new boolean();
    /**
    * Show the scanner options dialog when scanning documents.
    */
    public boolean ScanDocShowOptions = new boolean();
    /**
    * Attempt to scan in duplex mode when scanning multipage documents with an ADF.
    */
    public boolean ScanDocDuplex = new boolean();
    /**
    * Scan in gray scale when scanning documents.
    */
    public boolean ScanDocGrayscale = new boolean();
    /**
    * Scan at the specified resolution when scanning documents.
    */
    public int ScanDocResolution = new int();
    /**
    * 0-100. Quality of jpeg after compression when scanning documents.  100 indicates full quality.  Opposite of compression.
    */
    public byte ScanDocQuality = new byte();
    public ComputerPref copy() throws Exception {
        return (ComputerPref)MemberwiseClone();
    }

}


