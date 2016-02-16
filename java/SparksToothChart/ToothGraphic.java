//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:35 PM
//

package SparksToothChart;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import SparksToothChart.Face;
import SparksToothChart.LineSimple;
import SparksToothChart.Properties.Resources;
import SparksToothChart.ToothGraphic;
import SparksToothChart.ToothGroup;
import SparksToothChart.ToothGroupType;
import SparksToothChart.Vertex3f;
import SparksToothChart.VertexNormal;

/**
* A tooth graphic holds all the data for one tooth to be drawn.
*/
public class ToothGraphic   
{
    private String toothID = new String();
    public List<VertexNormal> VertexNormals = new List<VertexNormal>();
    /**
    * Corresponds to VertexNormal list, but DirectX required the verticies to be cached for rendering triangles.
    */
    public VertexBuffer vb = null;
    /**
    * Collection of type ToothGroup.
    */
    public List<ToothGroup> Groups = new List<ToothGroup>();
    private boolean visible = new boolean();
    private static float[] DefaultOrthoXpos = new float[]();
    /**
    * The rotation about the Y axis in degrees. Performed before any other rotation or translation.  Positive numbers are clockwise as viewed from occlusal.
    */
    public float Rotate = new float();
    /**
    * Rotation to the buccal or lingual about the X axis in degrees.  Positive numbers are to buccal. Happens 2nd, after rotateY.  Not common. Usually 0.
    */
    public float TipB = new float();
    /**
    * Rotation about the Z axis in degrees.  Aka tipping.  Positive numbers are mesial.  Happens last after all other rotations.
    */
    public float TipM = new float();
    /**
    * Shifting mesially or distally in millimeters.  Mesial is always positive, distal is negative.  Happens after all three rotations.
    */
    public float ShiftM = new float();
    /**
    * Shifting vertically in millimeters.  Aka supereruption.  Happens after all three rotations.  A positive number is super eruption, whether on the maxillary or mandibular.
    */
    public float ShiftO = new float();
    /**
    * Shifting buccolingually in millimeters.  Rare.  Usually 0.
    */
    public float ShiftB = new float();
    /**
    * This gets set to true if this tooth has an RCT.  The purpose of this flag is to cause the root to be painted transparently on top of the RCT.
    */
    public boolean IsRCT = new boolean();
    /**
    * Set true to hide the number of the tooth from displaying.  Visible should also be set to false in this case.
    */
    private boolean hideNumber = new boolean();
    /**
    * The X indicated that an extraction is planned.
    */
    public boolean DrawBigX = new boolean();
    /**
    * If a big X is to be drawn, this will contain the color.
    */
    public Color colorX = new Color();
    /**
    * If RCT, then this will contain the color.
    */
    public Color colorRCT = new Color();
    /**
    * True if this tooth is set to show the primary letter in addition to the perm number.  This can only be true for 20 of the 32 perm teeth.  A primary tooth would never have this value set.
    */
    public boolean ShowPrimaryLetter = new boolean();
    //<summary>This gets set to true if tooth has a BU or a post.</summary>//no more.  Just set group visibility.
    //public bool IsBU;
    //<summary>If BU, then this will contain the color.</summary>//no more. Just use group color
    //public Color colorBU;
    /**
    * This gets set to true if tooth has an implant.
    */
    public boolean IsImplant = new boolean();
    /**
    * If implant, then this will contain the color.
    */
    public Color colorImplant = new Color();
    /**
    * This will be true if the tooth is a crown or retainer.  The reason it's needed is so that crowns will show on missing teeth with implants.
    */
    public boolean IsCrown = new boolean();
    /**
    * True if the tooth is a bridge pontic, or a tooth on a denture or RPD.  This makes the crown visible from the F view, but not the occlusal.  It also makes the root invisible.
    */
    public boolean IsPontic = new boolean();
    /**
    * This gets set to true if tooth has a sealant.
    */
    public boolean IsSealant = new boolean();
    /**
    * If sealant, then this will contain the color.
    */
    public Color colorSealant = new Color();
    /**
    * This gets set to true if tooth has a watch on it.
    */
    public boolean Watch = new boolean();
    public Color colorWatch = Color.Red;
    /**
    * For perio, 1, 2, or 3.  It's a string because we'll later allow +.  The text gets drawn directly on the front of the tooth.
    */
    public String Mobility = new String();
    /**
    * If a mobility is set, the is the color.
    */
    public Color colorMobility = new Color();
    /**
    * If using DirectX, the vb VertexBuffer variable must be instantiated in a subsequent call to PrepareForDirectX().
    */
    public ToothGraphic copy() throws Exception {
        ToothGraphic tg = new ToothGraphic();
        tg.toothID = this.toothID;
        tg.VertexNormals = new List<VertexNormal>();
        for (int i = 0;i < this.VertexNormals.Count;i++)
        {
            tg.VertexNormals.Add(this.VertexNormals[i].Copy());
        }
        tg.Groups = new List<ToothGroup>();
        for (int i = 0;i < this.Groups.Count;i++)
        {
            tg.Groups.Add(this.Groups[i].Copy());
        }
        tg.visible = this.visible;
        tg.Rotate = this.Rotate;
        tg.TipB = this.TipB;
        tg.TipM = this.TipM;
        tg.ShiftM = this.ShiftM;
        tg.ShiftO = this.ShiftO;
        tg.ShiftB = this.ShiftB;
        tg.IsRCT = this.IsRCT;
        tg.hideNumber = this.hideNumber;
        tg.DrawBigX = this.DrawBigX;
        tg.colorX = this.colorX;
        tg.colorRCT = this.colorRCT;
        tg.ShowPrimaryLetter = this.ShowPrimaryLetter;
        tg.IsImplant = this.IsImplant;
        tg.colorImplant = this.colorImplant;
        tg.IsCrown = this.IsCrown;
        tg.IsPontic = this.IsPontic;
        tg.IsSealant = this.IsSealant;
        tg.colorSealant = this.colorSealant;
        return tg;
    }

    /**
    * Used in Clone()
    */
    public ToothGraphic() throws Exception {
    }

    /**
    * Only called from ToothChartWrapper.ResetTeeth or from ToothChartOpenGL.ResetTeeth when program first loads.  Constructor requires passing in the toothID.  Exception will be thrown if not one of the following: 1-32 or A-T.  Always loads graphics data from local resources even if in simplemode.
    */
    public ToothGraphic(String tooth_id) throws Exception {
        if (!StringSupport.equals(tooth_id, "implant") && !isValidToothID(tooth_id))
        {
            throw new ApplicationException("Invalid tooth ID");
        }
         
        /**
        * This will only happen if bugs in program
        */
        toothID = tooth_id;
        VertexNormals = new List<VertexNormal>();
        importObj();
        setDefaultColors();
    }

    public String toString() {
        try
        {
            String retVal = this.toothID;
            if (IsRCT)
            {
                retVal += ", RCT";
            }
             
            return retVal;
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

    /**
    * ToothID is set when creating a tooth object. It can never be changed.  Valid values are 1-32 or A-T.
    */
    public String getToothID() throws Exception {
        return toothID;
    }

    public void setToothID(String value) throws Exception {
    }

    //
    /**
    * Set false if teeth are missing.  All primary teeth initially set to false.
    */
    public boolean getVisible() throws Exception {
        return visible;
    }

    public void setVisible(boolean value) throws Exception {
        visible = value;
    }

    /**
    * Set true to hide the number of the tooth from displaying.  Visible should also be set to false in this case.
    */
    public boolean getHideNumber() throws Exception {
        return hideNumber;
    }

    public void setHideNumber(boolean value) throws Exception {
        hideNumber = value;
    }

    /**
    * Converts the VertexNormals list into a vertex buffer so the verticies can be used to render DirectX triangles.
    */
    public void prepareForDirectX(Device deviceRef) throws Exception {
        cleanupDirectX();
        CustomVertex.PositionNormal[] verts = new CustomVertex.PositionNormal[VertexNormals.Count];
        for (int i = 0;i < VertexNormals.Count;i++)
        {
            verts[i].X = VertexNormals[i].Vertex.X;
            verts[i].Y = VertexNormals[i].Vertex.Y;
            verts[i].Z = VertexNormals[i].Vertex.Z;
            verts[i].Nx = VertexNormals[i].Normal.X;
            verts[i].Ny = VertexNormals[i].Normal.Y;
            verts[i].Nz = VertexNormals[i].Normal.Z;
        }
        vb = new VertexBuffer(CustomVertex.PositionNormal.class, CustomVertex.PositionNormal.StrideSize * verts.Length, deviceRef, Usage.WriteOnly, CustomVertex.PositionNormal.Format, Pool.Managed);
        vb.SetData(verts, 0, LockFlags.None);
        for (int g = 0;g < Groups.Count;g++)
        {
            Groups[g].PrepareForDirectX(deviceRef);
        }
    }

    public void cleanupDirectX() throws Exception {
        if (vb != null)
        {
            vb.Dispose();
            vb = null;
        }
         
        for (int g = 0;g < Groups.Count;g++)
        {
            Groups[g].CleanupDirectX();
        }
    }

    /**
    * Resets this tooth graphic to original location, visiblity, and no restorations.  If primary tooth, then Visible=false.
    */
    public void reset() throws Exception {
        if (OpenDentBusiness.Tooth.isPrimary(getToothID()))
        {
            visible = false;
        }
        else
        {
            visible = true;
        } 
        TipM = 0;
        TipB = 0;
        Rotate = 0;
        ShiftB = 0;
        ShiftM = 0;
        ShiftO = 0;
        setDefaultColors();
        IsRCT = false;
        hideNumber = false;
        DrawBigX = false;
        ShowPrimaryLetter = false;
        //IsBU=false;
        IsImplant = false;
        IsCrown = false;
        IsPontic = false;
        IsSealant = false;
        Watch = false;
    }

    public void setSurfaceColors(String surfaces, Color color) throws Exception {
        for (int i = 0;i < surfaces.Length;i++)
        {
            if (surfaces[i] == 'M')
            {
                setGroupColor(ToothGroupType.M,color);
                setGroupColor(ToothGroupType.MF,color);
            }
            else if (surfaces[i] == 'O')
            {
                setGroupColor(ToothGroupType.O,color);
            }
            else if (surfaces[i] == 'D')
            {
                setGroupColor(ToothGroupType.D,color);
                setGroupColor(ToothGroupType.DF,color);
            }
            else if (surfaces[i] == 'B')
            {
                setGroupColor(ToothGroupType.B,color);
            }
            else if (surfaces[i] == 'L')
            {
                setGroupColor(ToothGroupType.L,color);
            }
            else if (surfaces[i] == 'V')
            {
                setGroupColor(ToothGroupType.V,color);
            }
            else if (surfaces[i] == 'I')
            {
                setGroupColor(ToothGroupType.I,color);
                setGroupColor(ToothGroupType.IF,color);
            }
            else if (surfaces[i] == 'F')
            {
                setGroupColor(ToothGroupType.F,color);
            }
                    
        }
    }

    /**
    * Used to set enamel, cementum, and BU colors externally.
    */
    public void setGroupColor(ToothGroupType groupType, Color paintColor) throws Exception {
        for (int i = 0;i < Groups.Count;i++)
        {
            if (Groups[i].GroupType != groupType)
            {
                continue;
            }
             
            Groups[i].PaintColor = paintColor;
        }
    }

    //if type not found, then no action is taken
    /**
    * Used in constructor and also in Reset.  Also sets visibility of all groups to true except RCT visible false.
    */
    private void setDefaultColors() throws Exception {
        for (int i = 0;i < Groups.Count;i++)
        {
            if (Groups[i].GroupType == ToothGroupType.Cementum)
            {
                Groups[i].PaintColor = Color.FromArgb(255, 250, 245, 223);
            }
            else
            {
                //255,250,230);
                //enamel
                Groups[i].PaintColor = Color.FromArgb(255, 250, 250, 240);
            } 
            //255,255,245);
            if (Groups[i].GroupType == ToothGroupType.Canals || Groups[i].GroupType == ToothGroupType.Buildup)
            {
                Groups[i].Visible = false;
            }
            else
            {
                Groups[i].Visible = true;
            } 
        }
    }

    /**
    * Used to set the root invisible.
    */
    public void setGroupVisibility(ToothGroupType groupType, boolean setVisible) throws Exception {
        for (int i = 0;i < Groups.Count;i++)
        {
            if (Groups[i].GroupType != groupType)
            {
                continue;
            }
             
            Groups[i].Visible = setVisible;
        }
    }

    /**
    * 
    */
    public ToothGroup getGroup(ToothGroupType groupType) throws Exception {
        for (int i = 0;i < Groups.Count;i++)
        {
            if (Groups[i].GroupType == groupType)
            {
                return Groups[i];
            }
             
        }
        return null;
    }

    /**
    * This is used when calling display lists.
    */
    public int getIndexForDisplayList(ToothGroup group) throws Exception {
        int toothInt = OpenDentBusiness.Tooth.toOrdinal(toothID);
        return (toothInt * 15) + ((Enum)group.GroupType).ordinal();
    }

    //this can be enhanced later, but it's very simple for now.
    //The reason these are all here instead of just using the Tooth class is that the graphical chart does not support supernumerary teeth,
    //so these methods handle that properly.
    /**
    * Strict validator for toothID. Will not handle any international tooth nums since all internal toothID's are in US format.  True if 1-32 or A-T.  Since supernumerary not currently handled, 51-82 and AS-TS return false.  Any invalid values also return false.
    */
    public static boolean isValidToothID(String tooth_id) throws Exception {
        if (!OpenDentBusiness.Tooth.isValidDB(tooth_id))
        {
            return false;
        }
         
        if (OpenDentBusiness.Tooth.isSuperNum(tooth_id))
        {
            return false;
        }
         
        return true;
    }

    /**
    * The tooth_id should be validated before coming here, but it won't crash if invalid.  Primary or perm are ok.  Empty and null are also ok.  Result is always 1-32 or -1 if invalid tooth_id.  Supernumerary not allowed
    */
    public static int idToInt(String tooth_id) throws Exception {
        if (!isValidToothID(tooth_id))
        {
            return -1;
        }
         
        return OpenDentBusiness.Tooth.toInt(tooth_id);
    }

    /**
    * The actual specific width of the tooth in mm.  Used to lay out the teeth on the screen.  Value taken from Wheelers Dental Anatomy.  Used in the orthographic projection.  Also used when building the teeth in the first place.  The perspective projection will need to adjust these numbers because of the curvature of the arch.  Only the widths of permanent teeth are used for layout.  If a primary tooth is passed in, it will give an error.
    */
    public static float getWidth(String tooth_id) throws Exception {
        System.String __dummyScrutVar0 = tooth_id;
        if (__dummyScrutVar0.equals("1") || __dummyScrutVar0.equals("16"))
        {
            return 8.5f;
        }
        else if (__dummyScrutVar0.equals("2") || __dummyScrutVar0.equals("15"))
        {
            return 9f;
        }
        else if (__dummyScrutVar0.equals("3") || __dummyScrutVar0.equals("14"))
        {
            return 10f;
        }
        else if (__dummyScrutVar0.equals("4") || __dummyScrutVar0.equals("5") || __dummyScrutVar0.equals("13") || __dummyScrutVar0.equals("12"))
        {
            return 7f;
        }
        else if (__dummyScrutVar0.equals("6") || __dummyScrutVar0.equals("11"))
        {
            return 7.5f;
        }
        else if (__dummyScrutVar0.equals("7") || __dummyScrutVar0.equals("10"))
        {
            return 6.5f;
        }
        else if (__dummyScrutVar0.equals("8") || __dummyScrutVar0.equals("9"))
        {
            return 8.5f;
        }
        else if (__dummyScrutVar0.equals("17") || __dummyScrutVar0.equals("32"))
        {
            return 10f;
        }
        else if (__dummyScrutVar0.equals("18") || __dummyScrutVar0.equals("31"))
        {
            return 10.5f;
        }
        else if (__dummyScrutVar0.equals("19") || __dummyScrutVar0.equals("30"))
        {
            return 11f;
        }
        else if (__dummyScrutVar0.equals("20") || __dummyScrutVar0.equals("21") || __dummyScrutVar0.equals("29") || __dummyScrutVar0.equals("28"))
        {
            return 7f;
        }
        else if (__dummyScrutVar0.equals("22") || __dummyScrutVar0.equals("27"))
        {
            return 7f;
        }
        else if (__dummyScrutVar0.equals("23") || __dummyScrutVar0.equals("26"))
        {
            return 5f;
        }
        else //5.5f;
        if (__dummyScrutVar0.equals("24") || __dummyScrutVar0.equals("25"))
        {
            return 5f;
        }
        else
        {
            throw new ApplicationException(tooth_id);
        }              
    }

    //return 0;
    /**
    * 
    */
    public static float getWidth(int tooth_num) throws Exception {
        return GetWidth(tooth_num.ToString());
    }

    /**
    * The x position of the center of the given tooth, with midline being 0.  Calculated once, then used to quickly calculate mouse positions and tooth positions.  All values are in mm.
    */
    public static float getDefaultOrthoXpos(int intTooth) throws Exception {
        if (DefaultOrthoXpos == null)
        {
            DefaultOrthoXpos = new float[33];
            //0-32, 0 not used
            float spacing = 0;
            //the distance between each adjacent tooth.
            DefaultOrthoXpos[8] = -spacing / 2f - getWidth(8) / 2f;
            DefaultOrthoXpos[7] = DefaultOrthoXpos[8] - spacing - getWidth(8) / 2f - getWidth(7) / 2f;
            DefaultOrthoXpos[6] = DefaultOrthoXpos[7] - spacing - getWidth(7) / 2f - getWidth(6) / 2f;
            DefaultOrthoXpos[5] = DefaultOrthoXpos[6] - spacing - getWidth(6) / 2f - getWidth(5) / 2f;
            DefaultOrthoXpos[4] = DefaultOrthoXpos[5] - spacing - getWidth(5) / 2f - getWidth(4) / 2f;
            DefaultOrthoXpos[3] = DefaultOrthoXpos[4] - spacing - getWidth(4) / 2f - getWidth(3) / 2f;
            DefaultOrthoXpos[2] = DefaultOrthoXpos[3] - spacing - getWidth(3) / 2f - getWidth(2) / 2f;
            DefaultOrthoXpos[1] = DefaultOrthoXpos[2] - spacing - getWidth(2) / 2f - getWidth(1) / 2f;
            DefaultOrthoXpos[9] = -DefaultOrthoXpos[8];
            DefaultOrthoXpos[10] = -DefaultOrthoXpos[7];
            DefaultOrthoXpos[11] = -DefaultOrthoXpos[6];
            DefaultOrthoXpos[12] = -DefaultOrthoXpos[5];
            DefaultOrthoXpos[13] = -DefaultOrthoXpos[4];
            DefaultOrthoXpos[14] = -DefaultOrthoXpos[3];
            DefaultOrthoXpos[15] = -DefaultOrthoXpos[2];
            DefaultOrthoXpos[16] = -DefaultOrthoXpos[1];
            DefaultOrthoXpos[24] = spacing / 2f + getWidth(24) / 2f;
            DefaultOrthoXpos[23] = DefaultOrthoXpos[24] + spacing + getWidth(24) / 2f + getWidth(23) / 2f;
            DefaultOrthoXpos[22] = DefaultOrthoXpos[23] + spacing + getWidth(23) / 2f + getWidth(22) / 2f;
            DefaultOrthoXpos[21] = DefaultOrthoXpos[22] + spacing + getWidth(22) / 2f + getWidth(21) / 2f;
            DefaultOrthoXpos[20] = DefaultOrthoXpos[21] + spacing + getWidth(21) / 2f + getWidth(20) / 2f;
            DefaultOrthoXpos[19] = DefaultOrthoXpos[20] + spacing + getWidth(20) / 2f + getWidth(19) / 2f;
            DefaultOrthoXpos[18] = DefaultOrthoXpos[19] + spacing + getWidth(19) / 2f + getWidth(18) / 2f;
            DefaultOrthoXpos[17] = DefaultOrthoXpos[18] + spacing + getWidth(18) / 2f + getWidth(17) / 2f;
            DefaultOrthoXpos[25] = -DefaultOrthoXpos[24];
            DefaultOrthoXpos[26] = -DefaultOrthoXpos[23];
            DefaultOrthoXpos[27] = -DefaultOrthoXpos[22];
            DefaultOrthoXpos[28] = -DefaultOrthoXpos[21];
            DefaultOrthoXpos[29] = -DefaultOrthoXpos[20];
            DefaultOrthoXpos[30] = -DefaultOrthoXpos[19];
            DefaultOrthoXpos[31] = -DefaultOrthoXpos[18];
            DefaultOrthoXpos[32] = -DefaultOrthoXpos[17];
        }
         
        if (intTooth < 1 || intTooth > 32)
        {
            throw new ApplicationException("Invalid tooth_num: " + intTooth.ToString());
        }
         
        return DefaultOrthoXpos[intTooth];
    }

    //just for debugging
    /**
    * 
    */
    public static boolean isMaxillary(String tooth_id) throws Exception {
        if (!isValidToothID(tooth_id))
        {
            return false;
        }
         
        return OpenDentBusiness.Tooth.isMaxillary(tooth_id);
    }

    /**
    * 
    */
    public static boolean isAnterior(String tooth_id) throws Exception {
        if (!isValidToothID(tooth_id))
        {
            return false;
        }
         
        return OpenDentBusiness.Tooth.isAnterior(tooth_id);
    }

    /**
    * True if on the right side of the mouth.
    */
    public static boolean isRight(String tooth_id) throws Exception {
        if (!isValidToothID(tooth_id))
        {
            return false;
        }
         
        int intTooth = idToInt(tooth_id);
        if (intTooth >= 1 && intTooth <= 8)
        {
            return true;
        }
         
        if (intTooth >= 25 && intTooth <= 32)
        {
            return true;
        }
         
        return false;
    }

    /**
    * Should only be run on startup for efficiency.
    */
    private void importObj() throws Exception {
        byte[] buffer = null;
        if (StringSupport.equals(toothID, "1") || StringSupport.equals(toothID, "16"))
        {
            buffer = Resources.gettooth1();
        }
        else if (StringSupport.equals(toothID, "2") || StringSupport.equals(toothID, "15"))
        {
            buffer = Resources.gettooth2();
        }
        else if (StringSupport.equals(toothID, "3") || StringSupport.equals(toothID, "14"))
        {
            //file+="3.obj";
            buffer = Resources.gettooth3();
        }
        else if (StringSupport.equals(toothID, "4") || StringSupport.equals(toothID, "13"))
        {
            buffer = Resources.gettooth4();
        }
        else if (StringSupport.equals(toothID, "5") || StringSupport.equals(toothID, "12"))
        {
            buffer = Resources.gettooth5();
        }
        else if (StringSupport.equals(toothID, "6") || StringSupport.equals(toothID, "11"))
        {
            buffer = Resources.gettooth6();
        }
        else if (StringSupport.equals(toothID, "7") || StringSupport.equals(toothID, "10"))
        {
            buffer = Resources.gettooth7();
        }
        else if (StringSupport.equals(toothID, "8") || StringSupport.equals(toothID, "9"))
        {
            buffer = Resources.gettooth8();
        }
        else if (StringSupport.equals(toothID, "17") || StringSupport.equals(toothID, "32"))
        {
            buffer = Resources.gettooth32();
        }
        else if (StringSupport.equals(toothID, "18") || StringSupport.equals(toothID, "31"))
        {
            buffer = Resources.gettooth31();
        }
        else if (StringSupport.equals(toothID, "19") || StringSupport.equals(toothID, "30"))
        {
            buffer = Resources.gettooth30();
        }
        else if (StringSupport.equals(toothID, "20") || StringSupport.equals(toothID, "29"))
        {
            buffer = Resources.gettooth29();
        }
        else if (StringSupport.equals(toothID, "21") || StringSupport.equals(toothID, "28"))
        {
            buffer = Resources.gettooth28();
        }
        else if (StringSupport.equals(toothID, "22") || StringSupport.equals(toothID, "27"))
        {
            buffer = Resources.gettooth27();
        }
        else if (StringSupport.equals(toothID, "23") || StringSupport.equals(toothID, "24") || StringSupport.equals(toothID, "25") || StringSupport.equals(toothID, "26"))
        {
            buffer = Resources.gettooth25();
        }
        else if (StringSupport.equals(toothID, "A") || StringSupport.equals(toothID, "J"))
        {
            buffer = Resources.gettoothA();
        }
        else if (StringSupport.equals(toothID, "B") || StringSupport.equals(toothID, "I"))
        {
            buffer = Resources.gettoothB();
        }
        else if (StringSupport.equals(toothID, "C") || StringSupport.equals(toothID, "H"))
        {
            buffer = Resources.gettoothC();
        }
        else if (StringSupport.equals(toothID, "D") || StringSupport.equals(toothID, "G"))
        {
            buffer = Resources.gettoothD();
        }
        else if (StringSupport.equals(toothID, "E") || StringSupport.equals(toothID, "F"))
        {
            buffer = Resources.gettoothE();
        }
        else if (StringSupport.equals(toothID, "P") || StringSupport.equals(toothID, "O") || StringSupport.equals(toothID, "Q") || StringSupport.equals(toothID, "N"))
        {
            buffer = Resources.gettoothP();
        }
        else if (StringSupport.equals(toothID, "R") || StringSupport.equals(toothID, "M"))
        {
            buffer = Resources.gettoothR();
        }
        else if (StringSupport.equals(toothID, "S") || StringSupport.equals(toothID, "L"))
        {
            buffer = Resources.gettoothS();
        }
        else if (StringSupport.equals(toothID, "T") || StringSupport.equals(toothID, "K"))
        {
            buffer = Resources.gettoothT();
        }
        else if (StringSupport.equals(toothID, "implant"))
        {
            buffer = Resources.getimplant();
        }
                                 
        boolean flipHorizontally = false;
        if (!StringSupport.equals(toothID, "implant") && idToInt(toothID) >= 9 && idToInt(toothID) <= 24)
        {
            flipHorizontally = true;
        }
         
        //There will not necessarily be the same number of vertices as normals.
        //But as they get paired up later, we will create a 1:1 relationship.
        List<Vertex3f> verts = new List<Vertex3f>();
        List<Vertex3f> norms = new List<Vertex3f>();
        Groups = new List<ToothGroup>();
        //ArrayList ALf=new ArrayList();//faces always part of a group
        List<Face> faces = new List<Face>();
        MemoryStream stream = new MemoryStream(buffer);
        StreamReader sr = new StreamReader(stream);
        try
        {
            {
                String line = new String();
                Vertex3f vertex;
                String[] items = new String[]();
                String[] subitems = new String[]();
                Face face;
                ToothGroup group = null;
                while ((line = sr.ReadLine()) != null)
                {
                    //comment
                    //material library.  We build our own.
                    //use material
                    if (line.StartsWith("#") || line.StartsWith("mtllib") || line.StartsWith("usemtl") || line.StartsWith("o"))
                    {
                        continue;
                    }
                     
                    //object. There's only one object
                    if (line.StartsWith("v "))
                    {
                        //vertex
                        items = line.Split(new char[]{ ' ' });
                        vertex = new Vertex3f();
                        //float[3];
                        if (flipHorizontally)
                        {
                            vertex.X = -Convert.ToSingle(items[1], CultureInfo.InvariantCulture);
                        }
                        else
                        {
                            vertex.X = Convert.ToSingle(items[1], CultureInfo.InvariantCulture);
                        } 
                        vertex.Y = Convert.ToSingle(items[2], CultureInfo.InvariantCulture);
                        vertex.Z = Convert.ToSingle(items[3], CultureInfo.InvariantCulture);
                        verts.Add(vertex);
                        continue;
                    }
                     
                    if (line.StartsWith("vn"))
                    {
                        //vertex normal
                        items = line.Split(new char[]{ ' ' });
                        vertex = new Vertex3f();
                        //new float[3];
                        if (flipHorizontally)
                        {
                            vertex.X = -Convert.ToSingle(items[1], CultureInfo.InvariantCulture);
                        }
                        else
                        {
                            vertex.X = Convert.ToSingle(items[1], CultureInfo.InvariantCulture);
                        } 
                        vertex.Y = Convert.ToSingle(items[2], CultureInfo.InvariantCulture);
                        vertex.Z = Convert.ToSingle(items[3], CultureInfo.InvariantCulture);
                        norms.Add(vertex);
                        continue;
                    }
                     
                    if (line.StartsWith("g"))
                    {
                        if (group != null)
                        {
                            //group
                            //move all faces into the existing group
                            group.Faces = new List<Face>(faces);
                            //move the existing group into the AL
                            Groups.Add(group);
                        }
                         
                        //start a new group to which all subsequent faces will be attached.
                        group = new ToothGroup();
                        faces = new List<Face>();
                        //group.PaintColor=Color.FromArgb(255,255,253,209);//default to enamel
                        System.String __dummyScrutVar1 = line;
                        if (__dummyScrutVar1.equals("g cube1_Cementum"))
                        {
                            group.GroupType = ToothGroupType.Cementum;
                        }
                        else if (__dummyScrutVar1.equals("g cube1_Enamel2"))
                        {
                            group.GroupType = ToothGroupType.Enamel;
                        }
                        else if (__dummyScrutVar1.equals("g cube1_M"))
                        {
                            group.GroupType = ToothGroupType.M;
                        }
                        else if (__dummyScrutVar1.equals("g cube1_D"))
                        {
                            group.GroupType = ToothGroupType.D;
                        }
                        else if (__dummyScrutVar1.equals("g cube1_F"))
                        {
                            group.GroupType = ToothGroupType.F;
                        }
                        else if (__dummyScrutVar1.equals("g cube1_I"))
                        {
                            group.GroupType = ToothGroupType.I;
                        }
                        else if (__dummyScrutVar1.equals("g cube1_L"))
                        {
                            group.GroupType = ToothGroupType.L;
                        }
                        else if (__dummyScrutVar1.equals("g cube1_V"))
                        {
                            group.GroupType = ToothGroupType.V;
                        }
                        else if (__dummyScrutVar1.equals("g cube1_B"))
                        {
                            group.GroupType = ToothGroupType.B;
                        }
                        else if (__dummyScrutVar1.equals("g cube1_O"))
                        {
                            group.GroupType = ToothGroupType.O;
                        }
                        else if (__dummyScrutVar1.equals("g cube1_Canals"))
                        {
                            group.GroupType = ToothGroupType.Canals;
                        }
                        else if (__dummyScrutVar1.equals("g cube1_Buildup"))
                        {
                            group.GroupType = ToothGroupType.Buildup;
                        }
                        else if (__dummyScrutVar1.equals("g cube1_Implant"))
                        {
                            group.GroupType = ToothGroupType.Implant;
                        }
                        else if (__dummyScrutVar1.equals("g cube1_EnamelF"))
                        {
                            group.GroupType = ToothGroupType.EnamelF;
                        }
                        else if (__dummyScrutVar1.equals("g cube1_DF"))
                        {
                            group.GroupType = ToothGroupType.DF;
                        }
                        else if (__dummyScrutVar1.equals("g cube1_MF"))
                        {
                            group.GroupType = ToothGroupType.MF;
                        }
                        else if (__dummyScrutVar1.equals("g cube1_IF"))
                        {
                            group.GroupType = ToothGroupType.IF;
                        }
                        else
                        {
                            group.GroupType = ToothGroupType.None;
                        }                 
                    }
                     
                    if (line.StartsWith("f"))
                    {
                        //face. Usually 4 vertices, but not always.
                        items = line.Split(new char[]{ ' ' });
                        face = new Face();
                        VertexNormal vertnorm;
                        int vertIdx = new int();
                        int normIdx = new int();
                        for (int i = 1;i < items.Length;i++)
                        {
                            //do we need to load these backwards for flipping, so they'll still be counterclockwise?
                            //It seems to work anyway, but it's something to keep in mind for later.
                            //face.GetLength(0);i++) {
                            subitems = items[i].Split(new char[]{ '/' });
                            // eg: 9//9  this is an index to a given vertex/normal.
                            vertnorm = new VertexNormal();
                            //unlike the old way of just storing idxs, we will actually store vertices.
                            vertIdx = Convert.ToInt32(subitems[0]) - 1;
                            normIdx = Convert.ToInt32(subitems[2]) - 1;
                            vertnorm.Vertex = verts[vertIdx];
                            vertnorm.Normal = norms[normIdx];
                            face.IndexList.Add(getIndexForVertNorm(vertnorm));
                        }
                        faces.Add(face);
                        continue;
                    }
                     
                }
                //while readline
                //For the very last group, move all faces into the group
                group.Faces = new List<Face>(faces);
                //new int[ALf.Count][][];
                //move the last group into the AL
                Groups.Add(group);
            }
        }
        finally
        {
            if (sr != null)
                Disposable.mkDisposable(sr).dispose();
             
        }
    }

    //MessageBox.Show(Vertices[2,2].ToString());
    /**
    * Tries to find an existing VertexNormal in the list for this tooth.  If it can, then it returns that index.  If it can't then it adds this VertexNormal to the list and returns the last index.
    */
    private int getIndexForVertNorm(VertexNormal vertnorm) throws Exception {
        for (int i = 0;i < VertexNormals.Count;i++)
        {
            //if(VertexNormals
            if (VertexNormals[i].Vertex != vertnorm.Vertex)
            {
                continue;
            }
             
            if (VertexNormals[i].Normal != vertnorm.Normal)
            {
                continue;
            }
             
            return i;
        }
        //couldn't find
        VertexNormals.Add(vertnorm);
        return VertexNormals.Count - 1;
    }

    /**
    * For any given tooth, there may only be one line in the returned list, or some teeth might have a few lines representing the root canals.
    */
    public List<LineSimple> getRctLines() throws Exception {
        List<LineSimple> retVal = new List<LineSimple>();
        LineSimple line;
        if (StringSupport.equals(toothID, "1"))
        {
            line = new LineSimple(-.7f, 9.6f, 1.6f, .6f, 8, 1.6f, 1.2f, 5.8f, 1.6f, .8f, 0, .9f);
            retVal.Add(line);
            line = new LineSimple(-1.8f, 9.5f, 1.6f, -1.6f, 8, 1.6f, -1.6f, 5.8f, 1.6f, -.9f, 0, .9f);
            retVal.Add(line);
        }
         
        if (StringSupport.equals(toothID, "16"))
        {
            line = new LineSimple(.7f, 9.6f, 1.6f, -.6f, 8, 1.6f, -1.2f, 5.8f, 1.6f, -.8f, 0, .9f);
            retVal.Add(line);
            line = new LineSimple(1.8f, 9.5f, 1.6f, 1.6f, 8, 1.6f, 1.6f, 5.8f, 1.6f, .9f, 0, .9f);
            retVal.Add(line);
        }
         
        if (StringSupport.equals(toothID, "2"))
        {
            line = new LineSimple(.3f, 10.6f, 3.4f, 1.4f, 8, 3.2f, 1.7f, 5, 1.9f, .9f, 0, 1f);
            retVal.Add(line);
            line = new LineSimple(-1.8f, 10.5f, 3.4f, -2, 7, 3.2f, -1.7f, 4, 1.9f, -1, 0, 1.1f);
            retVal.Add(line);
            line = new LineSimple(-2, 11.5f, -3.7f, -.6f, 6.3f, -4, 0, 0, -2.3f);
            retVal.Add(line);
        }
         
        if (StringSupport.equals(toothID, "15"))
        {
            line = new LineSimple(-.3f, 10.6f, 3.4f, -1.4f, 8, 3.2f, -1.7f, 5, 1.9f, -.9f, 0, 1f);
            retVal.Add(line);
            line = new LineSimple(1.8f, 10.5f, 3.4f, 2, 7, 3.2f, 1.7f, 4, 1.9f, 1, 0, 1.1f);
            retVal.Add(line);
            line = new LineSimple(2, 11.5f, -3.7f, .6f, 6.3f, -4, 0, 0, -2.3f);
            retVal.Add(line);
        }
         
        if (StringSupport.equals(toothID, "3"))
        {
            line = new LineSimple(1.4f, 11.5f, 3.4f, 2.2f, 9.4f, 3.2f, 2.4f, 6.7f, 3.2f, 1.2f, 0, 1.1f);
            retVal.Add(line);
            line = new LineSimple(-2.7f, 11.5f, 3.4f, -2.9f, 9, 3.2f, -2.6f, 5, 3.2f, -1.2f, 0, 1.1f);
            retVal.Add(line);
            line = new LineSimple(0, 12.5f, -4.3f, 0, 9.4f, -4.3f, 0, 0, -2.2f);
            retVal.Add(line);
        }
         
        if (StringSupport.equals(toothID, "14"))
        {
            line = new LineSimple(-1.4f, 11.5f, 3.4f, -2.2f, 9.4f, 3.2f, -2.4f, 6.7f, 3.2f, -1.2f, 0, 1.1f);
            retVal.Add(line);
            line = new LineSimple(2.7f, 11.5f, 3.4f, 2.9f, 9, 3.2f, 2.6f, 5, 3.2f, 1.2f, 0, 1.1f);
            retVal.Add(line);
            line = new LineSimple(0, 12.5f, -4.3f, 0, 9.4f, -4.3f, 0, 0, -2.2f);
            retVal.Add(line);
        }
         
        if (StringSupport.equals(toothID, "4"))
        {
            line = new LineSimple(0, 13.5f, 1.2f, -.2f, 10, .6f, 0, 0, 0);
            retVal.Add(line);
        }
         
        if (StringSupport.equals(toothID, "13"))
        {
            line = new LineSimple(0, 13.5f, 1.2f, .2f, 10, .6f, 0, 0, 0);
            retVal.Add(line);
        }
         
        if (StringSupport.equals(toothID, "5"))
        {
            line = new LineSimple(-1.1f, 13.5f, 1.6f, 0, 6, 1.6f, 0, 0, 1);
            retVal.Add(line);
        }
         
        if (StringSupport.equals(toothID, "12"))
        {
            line = new LineSimple(1.1f, 13.5f, 1.6f, 0, 6, 1.6f, 0, 0, 1);
            retVal.Add(line);
        }
         
        if (StringSupport.equals(toothID, "6"))
        {
            line = new LineSimple(-.4f, 16.5f, 0, 0, 11, 0, 0, 0, 0);
            retVal.Add(line);
        }
         
        if (StringSupport.equals(toothID, "11"))
        {
            line = new LineSimple(.4f, 16.5f, 0, 0, 11, 0, 0, 0, 0);
            retVal.Add(line);
        }
         
        if (StringSupport.equals(toothID, "7"))
        {
            line = new LineSimple(-.8f, 12.5f, .6f, -.3f, 10, 0, 0, 0, 0);
            retVal.Add(line);
        }
         
        if (StringSupport.equals(toothID, "10"))
        {
            line = new LineSimple(.8f, 12.5f, .6f, .3f, 10, 0, 0, 0, 0);
            retVal.Add(line);
        }
         
        if (StringSupport.equals(toothID, "8"))
        {
            line = new LineSimple(0, 12.6f, 0, 0, 0, 0);
            retVal.Add(line);
        }
         
        if (StringSupport.equals(toothID, "9"))
        {
            line = new LineSimple(0, 12.6f, 0, 0, 0, 0);
            retVal.Add(line);
        }
         
        if (StringSupport.equals(toothID, "25") || StringSupport.equals(toothID, "26"))
        {
            line = new LineSimple(-.5f, -12, 0, -.2f, -9, 0, 0, 0, 0);
            retVal.Add(line);
        }
         
        if (StringSupport.equals(toothID, "24") || StringSupport.equals(toothID, "23"))
        {
            line = new LineSimple(.5f, -12, 0, .2f, -9, 0, 0, 0, 0);
            retVal.Add(line);
        }
         
        if (StringSupport.equals(toothID, "27"))
        {
            line = new LineSimple(-.5f, -15.5f, 0, -.1f, -13, 0, 0, 0, 0);
            retVal.Add(line);
        }
         
        if (StringSupport.equals(toothID, "22"))
        {
            line = new LineSimple(.5f, -15.5f, 0, .1f, -13, 0, 0, 0, 0);
            retVal.Add(line);
        }
         
        if (StringSupport.equals(toothID, "28"))
        {
            line = new LineSimple(-.2f, -13.5f, 0, 0, 0, 0);
            retVal.Add(line);
        }
         
        if (StringSupport.equals(toothID, "21"))
        {
            line = new LineSimple(.2f, -13.5f, 0, 0, 0, 0);
            retVal.Add(line);
        }
         
        if (StringSupport.equals(toothID, "29"))
        {
            line = new LineSimple(-.3f, -14, 0, 0, -12, 0, 0, 0, 0);
            retVal.Add(line);
        }
         
        if (StringSupport.equals(toothID, "20"))
        {
            line = new LineSimple(.3f, -14, 0, 0, -12, 0, 0, 0, 0);
            retVal.Add(line);
        }
         
        if (StringSupport.equals(toothID, "30"))
        {
            line = new LineSimple(.9f, -13.5f, 0, 2.2f, -10, 0, 2.6f, -7, 0, 1.7f, 0, 0);
            retVal.Add(line);
            line = new LineSimple(-4.3f, -13.5f, 0, -4f, -9, 0, -3.3f, -5, 0, -1.7f, 0, 0);
            retVal.Add(line);
        }
         
        if (StringSupport.equals(toothID, "19"))
        {
            line = new LineSimple(-.9f, -13.5f, 0, -2.2f, -10, 0, -2.6f, -7, 0, -1.7f, 0, 0);
            retVal.Add(line);
            line = new LineSimple(4.3f, -13.5f, 0, 4f, -9, 0, 3.3f, -5, 0, 1.7f, 0, 0);
            retVal.Add(line);
        }
         
        if (StringSupport.equals(toothID, "31"))
        {
            line = new LineSimple(0, -12.5f, 0, 1.8f, -7.5f, 0, 2.2f, -4, 0, 1.7f, 0, 0);
            retVal.Add(line);
            line = new LineSimple(-3.4f, -12.5f, 0, -3.4f, -8, 0, -3f, -5, 0, -1.7f, 0, 0);
            retVal.Add(line);
        }
         
        if (StringSupport.equals(toothID, "18"))
        {
            line = new LineSimple(0, -12.5f, 0, -1.8f, -7.5f, 0, -2.2f, -4, 0, -1.7f, 0, 0);
            retVal.Add(line);
            line = new LineSimple(3.4f, -12.5f, 0, 3.4f, -8, 0, 3f, -5, 0, 1.7f, 0, 0);
            retVal.Add(line);
        }
         
        if (StringSupport.equals(toothID, "32"))
        {
            line = new LineSimple(-.7f, -10.5f, 0, .8f, -7.5f, 0, 1.7f, -4, 0, 1.6f, 0, 0);
            retVal.Add(line);
            line = new LineSimple(-3.2f, -10.5f, 0, -3.4f, -8, 0, -3f, -5, 0, -1.7f, 0, 0);
            retVal.Add(line);
        }
         
        if (StringSupport.equals(toothID, "17"))
        {
            line = new LineSimple(.7f, -10.5f, 0, -.8f, -7.5f, 0, -1.7f, -4, 0, -1.6f, 0, 0);
            retVal.Add(line);
            line = new LineSimple(3.2f, -10.5f, 0, 3.4f, -8, 0, 3f, -5, 0, 1.7f, 0, 0);
            retVal.Add(line);
        }
         
        return retVal;
    }

    /**
    * 
    */
    public LineSimple getSealantLine() throws Exception {
        if (isMaxillary(toothID))
        {
            return new LineSimple(1.5f, -4f, 1.5f, .75f, -4f, 2.25f, -.75f, -4f, 2.25f, -1.5f, -4f, 1.5f, -1.5f, -4f, .75f, 1.5f, -4f, -.75f, 1.5f, -4f, -1.5f, .75f, -4f, -2.25f, -.75f, -4f, -2.25f, -1.5f, -4f, -1.5f);
        }
        else
        {
            return new LineSimple(-1.5f, 4f, 1.5f, -.75f, 4f, 2.25f, .75f, 4f, 2.25f, 1.5f, 4f, 1.5f, 1.5f, 4f, .75f, -1.5f, 4f, -.75f, -1.5f, 4f, -1.5f, -.75f, 4f, -2.25f, .75f, 4f, -2.25f, 1.5f, 4f, -1.5f);
        } 
    }

    public LineSimple getWatchLine() throws Exception {
        return new LineSimple(0f, 0f, 0f, .8f, -2.65f, 0f, 1.6f, 0f, 0f, 2.4f, -2.65f, 0f, 3.2f, 0f, 0f);
    }

}


/*
		///<summary></summary>
		public Triangle GetBUpoly() {
			if(toothID=="1") {
				return new Triangle(
					-1.5f,0,0 , 
					-1.5f,2.3f,0 ,
					0,1.5f,0,
					1.4f,2.3f,0 ,
					1.4f,0,0);
			}
			if(toothID=="16") {
				return new Triangle(
					1.5f,0,0 , 
					1.5f,2.3f,0 ,
					0,1.5f,0,
					-1.4f,2.3f,0 ,
					-1.4f,0,0 );
			}
			if(toothID=="2") {
				return new Triangle(
					-1.8f,0,0 , 
					-1.8f,2.3f,0 ,
					0,1.5f,0,
					1.6f,2.3f,0 ,
					1.6f,0,0 );
			}
			if(toothID=="15") {
				return new Triangle(
					1.8f,0,0 , 
					1.8f,2.3f,0 ,
					0,1.5f,0,
					-1.6f,2.3f,0 ,
					-1.6f,0,0);
			}
			if(toothID=="3") {
				return new Triangle(
					-2.3f,0,0 , 
					-2.3f,2.6f,0 ,
					0,1.7f,0,
					2.1f,2.6f,0 ,
					2.1f,0,0 );
			}
			if(toothID=="14") {
				return new Triangle( 
					2.3f,0,0 , 
					2.3f,2.6f,0 ,
					0,1.7f,0,
					-2.1f,2.6f,0 ,
					-2.1f,0,0 );
			}
			if(toothID=="4"
				|| toothID=="5"
				|| toothID=="6"
				|| toothID=="7"
				|| toothID=="8"
				|| toothID=="9"
				|| toothID=="10"
				|| toothID=="11"
				|| toothID=="12"
				|| toothID=="13"
				) {
				return new Triangle(
					-.8f,0,0 , 
					-.8f,3.5f,0 ,
					.8f,3.5f,0 ,
					.8f,0,0 );
			}
			if(toothID=="23"
				|| toothID=="24"
				|| toothID=="25"
				|| toothID=="26"
				) {
				return new Triangle(
					-.7f,0,0 , 
					-.7f,-3.5f,0 ,
					.7f,-3.5f,0 ,
					.7f,0,0);
			}
			if(toothID=="20"
				|| toothID=="21"
				|| toothID=="22"
				|| toothID=="27"
				|| toothID=="28"
				|| toothID=="29"
				) {
				return new Triangle(
					-.8f,0,0 , 
					-.8f,-3.5f,0 ,
					.8f,-3.5f,0 ,
					.8f,0,0 );
			}
			if(toothID=="30") {
				return new Triangle(
					-2.8f,0,0 , 
					-2.8f,-2.4f,0 ,
					0,-1.5f,0,
					2.3f,-2.4f,0 ,
					2.3f,0,0 );
			}
			if(toothID=="19") {
				return new Triangle(
					2.8f,0,0 , 
					2.8f,-2.4f,0 ,
					0,-1.5f,0,
					-2.3f,-2.4f,0 ,
					-2.3f,0,0 );
			}
			if(toothID=="31") {
				return new Triangle( 
					-2.6f,0,0 , 
					-2.6f,-2.1f,0 ,
					0,-1.5f,0,
					2.3f,-2.1f,0 ,
					2.3f,0.5f,0 );
			}
			if(toothID=="18") {
				return new Triangle(
					2.6f,0,0 , 
					2.6f,-2.1f,0 ,
					0,-1.5f,0,
					-2.3f,-2.1f,0 ,
					-2.3f,0.5f,0 );
			}
			if(toothID=="32") {
				return new Triangle(
					-2.6f,0,0 , 
					-2.6f,-2.1f,0 ,
					0,-1.5f,0,
					2.1f,-2.1f,0 ,
					2.1f,0,0 );
			}
			if(toothID=="17") {
				return new Triangle(
					2.6f,0,0 , 
					2.6f,-2.1f,0 ,
					0,-1.5f,0,
					-2.1f,-2.1f,0 ,
					-2.1f,0,0 );
			}
			return new Triangle();
		}
		*/