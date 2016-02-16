//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:28 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.PerioCell;
import OpenDental.PIn;
import OpenDentBusiness.BleedingFlags;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.PerioExams;
import OpenDentBusiness.PerioMeasure;
import OpenDentBusiness.PerioMeasures;
import OpenDentBusiness.PerioSequenceType;
import OpenDentBusiness.PerioSurf;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Tooth;

/**
* Summary description for ContrPerio.
*/
public class ContrPerio  extends System.Windows.Forms.Control 
{
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * Width of one measurement. A tooth is 3 times this amount, not counting borders.
    */
    private int Wmeas = new int();
    /**
    * Height of one row of probing. Gives a little extra room for bleeding.
    */
    private int Hprob = new int();
    /**
    * Height of one of the shorter rows (non probing).
    */
    private int Hshort = new int();
    /**
    * Rows of probing depths.
    */
    private int RowsProbing = new int();
    /**
    * First dimension is either UF, UL, LF, or LL. Second dim is an array of the types of rows showing in that section.
    */
    private PerioSequenceType[][] RowTypes = new PerioSequenceType[][]();
    /**
    * Width of the left column that holds descriptions and dates.
    */
    private int Wleft = new int();
    /**
    * Height of the 'tooth' sections. Right now, it just holds the tooth number.
    */
    private int Htooth = new int();
    /**
    * Color of the outer border and the major dividers.
    */
    private Color cBorder = new Color();
    /**
    * Color of the background section of the shorter inner rows.
    */
    private Color cBackShort = new Color();
    /**
    * Color of a highlighted cell.
    */
    private Color cHi = new Color();
    /**
    * Color of the text of a skipped tooth.
    */
    private Color cSkip = new Color();
    /**
    * Color of the vertical lines between each tooth.
    */
    private Color cVertical = new Color();
    /**
    * Color of the minor horizontal lines between rows.
    */
    private Color cHoriz = new Color();
    /**
    * Color of the main background.
    */
    private Color cBack = new Color();
    /**
    * Color of the horizontal lines in the shorter inner rows.
    */
    private Color cHorizShort = new Color();
    /**
    * Color of red probing depths.
    */
    private Color cRedText = new Color();
    /**
    * Color of the dot over a number representing blood.
    */
    private Color cBlood = new Color();
    /**
    * Color of the dot over a number representing suppuration.
    */
    private Color cSupp = new Color();
    /**
    * Color of the dot over a number representing plaque.
    */
    private Color cPlaque = new Color();
    /**
    * Color of the dot over a number representing calculus.
    */
    private Color cCalc = new Color();
    /**
    * Color of previous measurements from a different exam. Slightly grey.
    */
    private Color cOldText = new Color();
    /**
    * Color of previous red measurements from a different exam. Lighter red.
    */
    private Color cOldTextRed = new Color();
    /**
    * This data array gets filled when loading an exam. It is altered as the user makes changes, and then the results are saved to the db by reading from this array.
    */
    private PerioCell[][] DataArray = new PerioCell[][]();
    /**
    * Since it is complex to compute Y coordinate of each cell, the values are stored in this array.  Used by GetBounds.
    */
    private float[] TopCoordinates = new float[]();
    /**
    * Stores the column,row of the currently selected cell. Null if none selected.
    */
    private Point CurCell = new Point();
    /**
    * Set true to go right, false to go left.
    */
    public boolean DirectionIsRight = new boolean();
    /**
    * The index in PerioExams.List of the currently selected exam.
    */
    private int selectedExam = new int();
    /**
    * the offset when there are more rows than will display. Value is set at the same time as SelectedExam. So usually 0. Otherwise 1,2,3 or....
    */
    private int ProbingOffset = new int();
    /**
    * Keeps track of what has changed for current exam. Dim 1 is sequence. Dim 2 is toothNum.
    */
    public boolean[][] HasChanged = new boolean[][]();
    /**
    * Valid values 1-32 int. User can highlight teeth to mark them as skip tooth. The highighting is done completely separately from the normal highlighting functionality because multiple teeth can be highlighted.
    */
    private ArrayList selectedTeeth = new ArrayList();
    /**
    * Valid values 1-32 int. Applies only to the current exam. Loaded from the db durring LoadData().
    */
    private List<int> skippedTeeth = new List<int>();
    /**
    * 
    */
    public EventHandler DirectionChangedRight = null;
    /**
    * 
    */
    public EventHandler DirectionChangedLeft = null;
    /**
    * Causes each data entry to be entered three times. Also, if the data is a bleeding flag entry, then it changes the behavior by causing it to advance also.
    */
    public boolean ThreeAtATime = new boolean();
    //public PerioExam PerioExamCur;
    /**
    * Perio security:  False will only allow user to see information but not edit.
    */
    public boolean perioEdit = new boolean();
    /**
    * True if all gingival margin values entered should be positive. (Stored in DB as negative.)
    */
    public boolean GingMargPlus = new boolean();
    /**
    * The index in PerioExams.List of the currently selected exam.
    */
    public int getSelectedExam() throws Exception {
        return selectedExam;
    }

    public void setSelectedExam(int value) throws Exception {
        selectedExam = value;
        ProbingOffset = 0;
        if (selectedExam > RowsProbing - 1)
            ProbingOffset = selectedExam - RowsProbing + 1;
         
    }

    /**
    * 
    */
    protected Size getDefaultSize() throws Exception {
        return new Size(590, 665);
    }

    /**
    * 
    */
    public ContrPerio() throws Exception {
        //InitializeComponent();// This call is required by the Windows.Forms Form Designer.
        this.BackColor = System.Drawing.SystemColors.Window;
        cBorder = Color.Black;
        //cBackShort=Color.FromArgb(237,237,237);//larger numbers will make it whiter
        cBackShort = Color.FromArgb(225, 225, 225);
        cHi = Color.FromArgb(158, 146, 142);
        //Color.DarkSalmon;
        cSkip = Color.LightGray;
        cVertical = Color.Silver;
        cHoriz = Color.LightGray;
        cBack = Color.White;
        cHorizShort = Color.Silver;
        //or darkgrey
        cRedText = Color.Red;
        setColors();
        cOldText = Color.FromArgb(120, 120, 120);
        cOldTextRed = Color.FromArgb(200, 80, 80);
        RowsProbing = 6;
        RowTypes = new PerioSequenceType[4];
        //Upper facial:
        RowTypes[0] = new PerioSequenceType[5 + RowsProbing];
        RowTypes[0][0] = PerioSequenceType.Mobility;
        RowTypes[0][1] = PerioSequenceType.Furcation;
        RowTypes[0][2] = PerioSequenceType.CAL;
        RowTypes[0][3] = PerioSequenceType.GingMargin;
        RowTypes[0][4] = PerioSequenceType.MGJ;
        for (int i = 0;i < RowsProbing;i++)
        {
            RowTypes[0][5 + i] = PerioSequenceType.Probing;
        }
        //Upper lingual:
        RowTypes[1] = new PerioSequenceType[3 + RowsProbing];
        RowTypes[1][0] = PerioSequenceType.Furcation;
        RowTypes[1][1] = PerioSequenceType.CAL;
        RowTypes[1][2] = PerioSequenceType.GingMargin;
        for (int i = 0;i < RowsProbing;i++)
        {
            RowTypes[1][3 + i] = PerioSequenceType.Probing;
        }
        //Lower lingual:
        RowTypes[2] = new PerioSequenceType[4 + RowsProbing];
        RowTypes[2][0] = PerioSequenceType.Furcation;
        RowTypes[2][1] = PerioSequenceType.CAL;
        RowTypes[2][2] = PerioSequenceType.GingMargin;
        RowTypes[2][3] = PerioSequenceType.MGJ;
        for (int i = 0;i < RowsProbing;i++)
        {
            RowTypes[2][4 + i] = PerioSequenceType.Probing;
        }
        //Lower facial:
        RowTypes[3] = new PerioSequenceType[5 + RowsProbing];
        RowTypes[3][0] = PerioSequenceType.Mobility;
        RowTypes[3][1] = PerioSequenceType.Furcation;
        RowTypes[3][2] = PerioSequenceType.CAL;
        RowTypes[3][3] = PerioSequenceType.GingMargin;
        RowTypes[3][4] = PerioSequenceType.MGJ;
        for (int i = 0;i < RowsProbing;i++)
        {
            RowTypes[3][5 + i] = PerioSequenceType.Probing;
        }
        Wmeas = 10;
        Wleft = 65;
        Hprob = 16;
        Hshort = 12;
        Htooth = 16;
        clearDataArray();
        fillTopCoordinates();
        CurCell = new Point(-1, -1);
    }

    //my way of setting it to null.
    /**
    * Clean up any resources being used.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing)
        {
            if (components != null)
            {
                components.Dispose();
            }
             
        }
         
        super.Dispose(disposing);
    }

    /*
    		#region Component Designer generated code
    		/// <summary> 
    		/// Required method for Designer support - do not modify 
    		/// the contents of this method with the code editor.
    		/// </summary>
    		private void InitializeComponent()
    		{
    			// 
    			// ContrPerio
    			// 
    			this.BackColor = System.Drawing.SystemColors.Window;
    			this.Name = "ContrPerio";
    		}
    		#endregion
    		*/
    /**
    * Sets the user editable colors
    */
    public void setColors() throws Exception {
        if (DefC.getDefShortIsNull())
        {
            cBlood = Color.FromArgb(240, 20, 20);
            cSupp = Color.FromArgb(255, 160, 0);
            cPlaque = Color.FromArgb(240, 20, 20);
            cCalc = Color.FromArgb(255, 160, 0);
        }
        else
        {
            cBlood = DefC.getLong()[((Enum)DefCat.MiscColors).ordinal()][1].ItemColor;
            cSupp = DefC.getLong()[((Enum)DefCat.MiscColors).ordinal()][2].ItemColor;
            cPlaque = DefC.getLong()[((Enum)DefCat.MiscColors).ordinal()][4].ItemColor;
            cCalc = DefC.getLong()[((Enum)DefCat.MiscColors).ordinal()][5].ItemColor;
        } 
    }

    /**
    * 
    */
    protected void onPaint(PaintEventArgs e) throws Exception {
        //base.OnPaint (e);
        //Graphics g=e.Graphics;
        DrawBackground(e);
        DrawSkippedTeeth(e);
        DrawSelectedTeeth(e);
        DrawCurCell(e);
        DrawGridlines(e);
        DrawText(e);
    }

    //DrawTempDots(e);
    //private void DrawTempDots(System.Windows.Forms.PaintEventArgs e){
    //Graphics g=e.Graphics;
    //for(int i=0;i<TopCoordinates.Length;i++){
    //	g.DrawLine(Pens.Red,20,TopCoordinates[24],25,TopCoordinates[24]);
    //}
    //}
    private void drawBackground(System.Windows.Forms.PaintEventArgs e) throws Exception {
        Graphics g = e.Graphics;
        int top = new int();
        int bottom = new int();
        //rect 1
        int yPos1 = 1 + RowsProbing * (Hprob + 1);
        int yPos2 = yPos1 + (RowTypes[0].Length - RowsProbing) * (Hshort + 1) - 1;
        top = yPos1;
        bottom = yPos2;
        if (e.ClipRectangle.Bottom >= top && e.ClipRectangle.Top <= bottom)
        {
            if (e.ClipRectangle.Bottom <= bottom)
            {
                bottom = e.ClipRectangle.Bottom;
            }
             
            if (e.ClipRectangle.Top >= top)
            {
                top = e.ClipRectangle.Top;
            }
             
            g.FillRectangle(new SolidBrush(cBackShort), e.ClipRectangle.X, top, e.ClipRectangle.Width, bottom - top);
        }
         
        //rect 2
        yPos1 = yPos2 + 1 + Htooth + 1;
        yPos2 = yPos1 + (RowTypes[1].Length - RowsProbing) * (Hshort + 1) - 1;
        top = yPos1;
        bottom = yPos2;
        if (e.ClipRectangle.Bottom >= top && e.ClipRectangle.Top <= bottom)
        {
            if (e.ClipRectangle.Bottom <= bottom)
            {
                bottom = e.ClipRectangle.Bottom;
            }
             
            if (e.ClipRectangle.Top >= top)
            {
                top = e.ClipRectangle.Top;
            }
             
            g.FillRectangle(new SolidBrush(cBackShort), e.ClipRectangle.X, top, e.ClipRectangle.Width, bottom - top);
        }
         
        //rect 3
        yPos1 = yPos2 + 1 + RowsProbing * (Hprob + 1) + 1 + RowsProbing * (Hprob + 1);
        yPos2 = yPos1 + (RowTypes[2].Length - RowsProbing) * (Hshort + 1) - 1;
        top = yPos1;
        bottom = yPos2;
        if (e.ClipRectangle.Bottom >= top && e.ClipRectangle.Top <= bottom)
        {
            if (e.ClipRectangle.Bottom <= bottom)
            {
                bottom = e.ClipRectangle.Bottom;
            }
             
            if (e.ClipRectangle.Top >= top)
            {
                top = e.ClipRectangle.Top;
            }
             
            g.FillRectangle(new SolidBrush(cBackShort), e.ClipRectangle.X, top, e.ClipRectangle.Width, bottom - top);
        }
         
        //rect 4
        yPos1 = yPos2 + 1 + Htooth + 1;
        yPos2 = yPos1 + (RowTypes[3].Length - RowsProbing) * (Hshort + 1) - 1;
        top = yPos1;
        bottom = yPos2;
        if (e.ClipRectangle.Bottom >= top && e.ClipRectangle.Top <= bottom)
        {
            if (e.ClipRectangle.Bottom <= bottom)
            {
                bottom = e.ClipRectangle.Bottom;
            }
             
            if (e.ClipRectangle.Top >= top)
            {
                top = e.ClipRectangle.Top;
            }
             
            g.FillRectangle(new SolidBrush(cBackShort), e.ClipRectangle.X, top, e.ClipRectangle.Width, bottom - top);
        }
         
    }

    /**
    * Draws the greyed out background for the skipped teeth.
    */
    private void drawSkippedTeeth(System.Windows.Forms.PaintEventArgs e) throws Exception {
        if (skippedTeeth == null || skippedTeeth.Count == 0)
            return ;
         
        Graphics g = e.Graphics;
        float xLoc = 0;
        float yLoc = 0;
        float h = 0;
        float w = 0;
        RectangleF bounds = new RectangleF();
        for (int i = 0;i < skippedTeeth.Count;i++)
        {
            //used in the loop to represent the bounds of the entire tooth to be greyed
            if ((int)skippedTeeth[i] < 17)
            {
                //max tooth
                xLoc = 1 + Wleft + 1 + ((int)skippedTeeth[i] - 1) * 3 * (Wmeas + 1);
                //xLoc=1+Wleft+1+(col-1)*(Wmeas+1);
                yLoc = 1;
                h = TopCoordinates[GetTableRow(1, RowTypes[1].Length - 1)] - yLoc + Hprob;
                w = 3 * (Wmeas + 1);
            }
            else
            {
                //mand tooth
                xLoc = 1 + Wleft + 1 + (33 - (int)skippedTeeth[i] - 1) * 3 * (Wmeas + 1);
                yLoc = TopCoordinates[GetTableRow(2, RowTypes[2].Length - 1)];
                h = TopCoordinates[GetTableRow(3, RowTypes[3].Length - 1)] - yLoc + Hprob;
                w = 3 * (Wmeas + 1);
            } 
            bounds = new RectangleF(xLoc, yLoc, w, h);
            int top = (int)bounds.Top;
            int bottom = (int)bounds.Bottom;
            int left = (int)bounds.Left;
            int right = (int)bounds.Right;
            //test clipping rect later
            //MessageBox.Show(bounds.ToString());
            g.FillRectangle(new SolidBrush(cBackShort), left, top, right - left, bottom - top);
        }
    }

    /**
    * Draws the highlighted background for selected teeth(not used very often unless user has been clicking on tooth numbers in preparation for setting skipped teeth. Then, highlighting goes away.
    */
    private void drawSelectedTeeth(System.Windows.Forms.PaintEventArgs e) throws Exception {
        if (selectedTeeth == null || selectedTeeth.Count == 0)
            return ;
         
        Graphics g = e.Graphics;
        float xLoc = 0;
        float yLoc = 0;
        float h = 0;
        float w = 0;
        RectangleF bounds = new RectangleF();
        for (int i = 0;i < selectedTeeth.Count;i++)
        {
            //used in the loop to represent the bounds to be greyed
            if ((int)selectedTeeth[i] < 17)
            {
                //max tooth
                xLoc = 1 + Wleft + 1 + ((int)selectedTeeth[i] - 1) * 3 * (Wmeas + 1);
                yLoc = TopCoordinates[getTableRow(true)];
                h = Htooth;
                w = 3 * (Wmeas + 1);
            }
            else
            {
                //mand tooth
                xLoc = 1 + Wleft + 1 + (33 - (int)selectedTeeth[i] - 1) * 3 * (Wmeas + 1);
                yLoc = TopCoordinates[getTableRow(false)];
                h = Htooth;
                w = 3 * (Wmeas + 1);
            } 
            bounds = new RectangleF(xLoc, yLoc, w, h);
            int top = (int)bounds.Top;
            int bottom = (int)bounds.Bottom;
            int left = (int)bounds.Left;
            int right = (int)bounds.Right;
            //test clipping rect later
            g.FillRectangle(new SolidBrush(cHi), left, top, right - left, bottom - top);
        }
    }

    /**
    * Draws the highlighted background for the current cell.
    */
    private void drawCurCell(System.Windows.Forms.PaintEventArgs e) throws Exception {
        if (CurCell.X == -1)
        {
            return ;
        }
         
        if (!perioEdit)
        {
            return ;
        }
         
        Graphics g = e.Graphics;
        RectangleF bounds = GetBounds(CurCell.X, CurCell.Y);
        if (RowTypes[GetSection(CurCell.Y)][GetSectionRow(CurCell.Y)] == PerioSequenceType.Probing)
        {
            bounds = new RectangleF(bounds.X, bounds.Y + Hprob - Hshort, bounds.Width, Hshort);
        }
         
        int top = (int)bounds.Top;
        int bottom = (int)bounds.Bottom;
        int left = (int)bounds.Left;
        int right = (int)bounds.Right;
        if (e.ClipRectangle.Bottom >= bounds.Top && e.ClipRectangle.Top <= bounds.Bottom && e.ClipRectangle.Right >= bounds.Left && e.ClipRectangle.Left <= bounds.Right)
        {
            //if the clipping rect includes any part of the CurCell
            if (e.ClipRectangle.Bottom <= bottom)
            {
                bottom = e.ClipRectangle.Bottom;
            }
             
            if (e.ClipRectangle.Top >= top)
            {
                top = e.ClipRectangle.Top;
            }
             
            if (e.ClipRectangle.Right <= right)
            {
                right = e.ClipRectangle.Right;
            }
             
            if (e.ClipRectangle.Left >= left)
            {
                left = e.ClipRectangle.Left;
            }
             
            g.FillRectangle(new SolidBrush(cHi), left, top, right - left, bottom - top);
        }
         
    }

    private void drawGridlines(System.Windows.Forms.PaintEventArgs e) throws Exception {
        Graphics g = e.Graphics;
        int yPos = 0;
        if (e.ClipRectangle.Top <= yPos && e.ClipRectangle.Bottom >= yPos)
            g.DrawLine(new Pen(cBorder), e.ClipRectangle.Left, yPos, e.ClipRectangle.Right, yPos);
         
        for (int i = RowTypes[0].Length - 1;i >= 0;i--)
        {
            //inside each loop, we are drawing the bottom line of each cell
            //U facial
            if (RowTypes[0][i] == PerioSequenceType.Probing)
            {
                yPos += 1 + Hprob;
                if (i == RowTypes[0].Length - RowsProbing)
                {
                    //if last row, eg 4==10-6
                    if (e.ClipRectangle.Top <= yPos && e.ClipRectangle.Bottom >= yPos)
                        g.DrawLine(new Pen(cBorder), e.ClipRectangle.Left, yPos, e.ClipRectangle.Right, yPos);
                     
                }
                else
                {
                    //regular rows
                    if (e.ClipRectangle.Top <= yPos && e.ClipRectangle.Bottom >= yPos)
                        g.DrawLine(new Pen(cHoriz), e.ClipRectangle.Left, yPos, e.ClipRectangle.Right, yPos);
                     
                } 
            }
            else
            {
                //short rows
                yPos += 1 + Hshort;
                if (i == 0)
                {
                    //if last row
                    if (e.ClipRectangle.Top <= yPos && e.ClipRectangle.Bottom >= yPos)
                        g.DrawLine(new Pen(cBorder), e.ClipRectangle.Left, yPos, e.ClipRectangle.Right, yPos);
                     
                }
                else
                {
                    //regular rows
                    if (e.ClipRectangle.Top <= yPos && e.ClipRectangle.Bottom >= yPos)
                        g.DrawLine(new Pen(cHorizShort), e.ClipRectangle.Left, yPos, e.ClipRectangle.Right, yPos);
                     
                } 
            } 
        }
        yPos += 1 + Htooth;
        if (e.ClipRectangle.Top <= yPos && e.ClipRectangle.Bottom >= yPos)
            g.DrawLine(new Pen(cBorder), e.ClipRectangle.Left, yPos, e.ClipRectangle.Right, yPos);
         
        for (int i = 0;i < RowTypes[1].Length;i++)
        {
            //upper lingual
            if (RowTypes[1][i] == PerioSequenceType.Probing)
            {
                yPos += 1 + Hprob;
                if (i == RowTypes[1].Length - 1)
                {
                    //if last row
                    if (e.ClipRectangle.Top <= yPos && e.ClipRectangle.Bottom >= yPos)
                        g.DrawLine(new Pen(cBorder), e.ClipRectangle.Left, yPos, e.ClipRectangle.Right, yPos);
                     
                }
                else
                {
                    //regular rows
                    if (e.ClipRectangle.Top <= yPos && e.ClipRectangle.Bottom >= yPos)
                        g.DrawLine(new Pen(cHoriz), e.ClipRectangle.Left, yPos, e.ClipRectangle.Right, yPos);
                     
                } 
            }
            else
            {
                //short rows
                yPos += 1 + Hshort;
                if (i == RowTypes[1].Length - RowsProbing - 1)
                {
                    //if last row. eg 8-6-1=1
                    if (e.ClipRectangle.Top <= yPos && e.ClipRectangle.Bottom >= yPos)
                        g.DrawLine(new Pen(cBorder), e.ClipRectangle.Left, yPos, e.ClipRectangle.Right, yPos);
                     
                }
                else
                {
                    //regular rows
                    if (e.ClipRectangle.Top <= yPos && e.ClipRectangle.Bottom >= yPos)
                        g.DrawLine(new Pen(cHorizShort), e.ClipRectangle.Left, yPos, e.ClipRectangle.Right, yPos);
                     
                } 
            } 
        }
        yPos += 1;
        //makes a double line between u and L
        if (e.ClipRectangle.Top <= yPos && e.ClipRectangle.Bottom >= yPos)
            g.DrawLine(new Pen(cBorder), e.ClipRectangle.Left, yPos, e.ClipRectangle.Right, yPos);
         
        for (int i = RowTypes[2].Length - 1;i >= 0;i--)
        {
            //lower lingual
            if (RowTypes[2][i] == PerioSequenceType.Probing)
            {
                yPos += 1 + Hprob;
                if (i == RowTypes[2].Length - RowsProbing)
                {
                    //if last row, eg 4==10-6
                    if (e.ClipRectangle.Top <= yPos && e.ClipRectangle.Bottom >= yPos)
                        g.DrawLine(new Pen(cBorder), e.ClipRectangle.Left, yPos, e.ClipRectangle.Right, yPos);
                     
                }
                else
                {
                    //regular rows
                    if (e.ClipRectangle.Top <= yPos && e.ClipRectangle.Bottom >= yPos)
                        g.DrawLine(new Pen(cHoriz), e.ClipRectangle.Left, yPos, e.ClipRectangle.Right, yPos);
                     
                } 
            }
            else
            {
                //short rows
                yPos += 1 + Hshort;
                if (i == 0)
                {
                    //if last row
                    if (e.ClipRectangle.Top <= yPos && e.ClipRectangle.Bottom >= yPos)
                        g.DrawLine(new Pen(cBorder), e.ClipRectangle.Left, yPos, e.ClipRectangle.Right, yPos);
                     
                }
                else
                {
                    //regular rows
                    if (e.ClipRectangle.Top <= yPos && e.ClipRectangle.Bottom >= yPos)
                        g.DrawLine(new Pen(cHorizShort), e.ClipRectangle.Left, yPos, e.ClipRectangle.Right, yPos);
                     
                } 
            } 
        }
        yPos += 1 + Htooth;
        if (e.ClipRectangle.Top <= yPos && e.ClipRectangle.Bottom >= yPos)
            g.DrawLine(new Pen(cBorder), e.ClipRectangle.Left, yPos, e.ClipRectangle.Right, yPos);
         
        for (int i = 0;i < RowTypes[3].Length;i++)
        {
            //lower facial
            if (RowTypes[3][i] == PerioSequenceType.Probing)
            {
                yPos += 1 + Hprob;
                if (i == RowTypes[3].Length - 1)
                {
                    //if last row
                    if (e.ClipRectangle.Top <= yPos && e.ClipRectangle.Bottom >= yPos)
                        g.DrawLine(new Pen(cBorder), e.ClipRectangle.Left, yPos, e.ClipRectangle.Right, yPos);
                     
                }
                else
                {
                    //regular rows
                    if (e.ClipRectangle.Top <= yPos && e.ClipRectangle.Bottom >= yPos)
                        g.DrawLine(new Pen(cHoriz), e.ClipRectangle.Left, yPos, e.ClipRectangle.Right, yPos);
                     
                } 
            }
            else
            {
                //short rows
                yPos += 1 + Hshort;
                if (i == RowTypes[3].Length - RowsProbing - 1)
                {
                    //if last row. eg 8-6-1=1
                    if (e.ClipRectangle.Top <= yPos && e.ClipRectangle.Bottom >= yPos)
                        g.DrawLine(new Pen(cBorder), e.ClipRectangle.Left, yPos, e.ClipRectangle.Right, yPos);
                     
                }
                else
                {
                    //regular rows
                    if (e.ClipRectangle.Top <= yPos && e.ClipRectangle.Bottom >= yPos)
                        g.DrawLine(new Pen(cHorizShort), e.ClipRectangle.Left, yPos, e.ClipRectangle.Right, yPos);
                     
                } 
            } 
        }
        //VERTICAL LINES
        int xPos = 0;
        if (e.ClipRectangle.Left <= xPos && e.ClipRectangle.Right >= xPos)
            g.DrawLine(new Pen(cBorder), xPos, e.ClipRectangle.Top, xPos, e.ClipRectangle.Bottom);
         
        xPos += Wleft + 1;
        if (e.ClipRectangle.Left <= xPos && e.ClipRectangle.Right >= xPos)
            g.DrawLine(new Pen(cBorder), xPos, e.ClipRectangle.Top, xPos, e.ClipRectangle.Bottom);
         
        for (int i = 0;i < 16;i++)
        {
            xPos += 3 * (Wmeas + 1);
            if (e.ClipRectangle.Left <= xPos && e.ClipRectangle.Right >= xPos)
            {
                if (i == 7 || i == 15)
                    g.DrawLine(new Pen(cBorder), xPos, e.ClipRectangle.Top, xPos, e.ClipRectangle.Bottom);
                else
                    g.DrawLine(new Pen(cVertical), xPos, e.ClipRectangle.Top, xPos, e.ClipRectangle.Bottom); 
            }
             
        }
    }

    private void drawText(System.Windows.Forms.PaintEventArgs e) throws Exception {
        //Graphics g=e.Graphics;
        int dataL = DataArray.GetLength(1);
        for (int i = 0;i < DataArray.GetLength(1);i++)
        {
            //loop through all rows in the table
            //test for clip later
            drawRow(i,e);
        }
    }

    private void drawRow(int row, System.Windows.Forms.PaintEventArgs e) throws Exception {
        e.Graphics.SmoothingMode = SmoothingMode.HighQuality;
        //.AntiAlias;
        //e.Graphics.PixelOffsetMode=PixelOffsetMode.HighQuality;
        //e.Graphics.TextRenderingHint=TextRenderingHint.AntiAlias;
        Font font = new Font();
        Color textColor = new Color();
        StringFormat format = new StringFormat();
        //format.LineAlignment is useless for small vertical changes like we need
        RectangleF rect = new RectangleF();
        boolean drawOld = new boolean();
        int redThresh = 0;
        int cellValue = 0;
        for (int i = 0;i < DataArray.GetLength(0);i++)
        {
            //loop through all columns in the row
            rect = getBounds(i,row);
            font = (Font)Font.Clone();
            //font=new Font("Arial",8,FontStyle.Regular);
            textColor = Color.Black;
            //test for clip later
            if (i == 0)
            {
                //first column (the date of the perio exam).
                format.Alignment = StringAlignment.Far;
                //align right
                e.Graphics.DrawString(DataArray[i, row].Text, font, new SolidBrush(textColor), rect, format);
                continue;
            }
            else if (getSection(row) == -1)
            {
                //tooth row
                //Debug.WriteLine("row:"+row+" col:"+i);
                font = new Font(Font, FontStyle.Bold);
                format.Alignment = StringAlignment.Center;
                rect = new RectangleF(rect.X, rect.Y + 2, rect.Width, rect.Height);
                e.Graphics.DrawString(DataArray[i, row].Text, font, new SolidBrush(textColor), rect, format);
                continue;
            }
              
            //e.Graphics.DrawString(DataArray[i,row].Text,Font,Brushes.Black,rect);
            format.Alignment = StringAlignment.Center;
            //center left/right
            if (RowTypes[getSection(row)][getSectionRow(row)] == PerioSequenceType.Probing)
            {
                if ((DataArray[i, row].Bleeding & BleedingFlags.Plaque) > 0)
                {
                    e.Graphics.FillRectangle(new SolidBrush(cPlaque), rect.X + 0, rect.Y, 2.5f, 3.5f);
                }
                 
                if ((DataArray[i, row].Bleeding & BleedingFlags.Calculus) > 0)
                {
                    e.Graphics.FillRectangle(new SolidBrush(cCalc), rect.X + 2.5f, rect.Y, 2.5f, 3.5f);
                }
                 
                if ((DataArray[i, row].Bleeding & BleedingFlags.Blood) > 0)
                {
                    e.Graphics.FillRectangle(new SolidBrush(cBlood), rect.X + 5f, rect.Y, 2.5f, 3.5f);
                }
                 
                if ((DataArray[i, row].Bleeding & BleedingFlags.Suppuration) > 0)
                {
                    e.Graphics.FillRectangle(new SolidBrush(cSupp), rect.X + 7.5f, rect.Y, 2.5f, 3.5f);
                }
                 
                rect = new RectangleF(rect.X, rect.Y + 4, rect.Width, rect.Height);
            }
             
            if ((DataArray[i, row].Text == null || StringSupport.equals(DataArray[i, row].Text, "")) && (DataArray[i, row].OldText == null || StringSupport.equals(DataArray[i, row].OldText, "")))
            {
                continue;
            }
             
            //no text to draw
            if (DataArray[i, row].Text == null || StringSupport.equals(DataArray[i, row].Text, ""))
            {
                //No data recorded by user for the current cell. Display the old text from the previous perio exam.
                drawOld = true;
                cellValue = PIn.Int(DataArray[i, row].OldText);
                if (cellValue > 100)
                {
                    //used for negative numbers
                    cellValue = 100 - cellValue;
                }
                 
                //i.e. 100-103 = -3
                textColor = Color.Gray;
            }
            else
            {
                drawOld = false;
                cellValue = PIn.Int(DataArray[i, row].Text);
                if (cellValue > 100)
                {
                    //used for negative numbers
                    cellValue = 100 - cellValue;
                }
                 
                //i.e. 100-103 = -3
                textColor = Color.Black;
                if (!perioEdit)
                {
                    textColor = Color.Gray;
                }
                 
            } 
            //test for red
            System.Array'1.INDEXER.INDEXER __dummyScrutVar0 = RowTypes[getSection(row)][getSectionRow(row)];
            if (__dummyScrutVar0.equals(PerioSequenceType.Probing))
            {
                redThresh = PrefC.getInt(PrefName.PerioRedProb);
            }
            else if (__dummyScrutVar0.equals(PerioSequenceType.MGJ))
            {
                redThresh = PrefC.getInt(PrefName.PerioRedMGJ);
            }
            else if (__dummyScrutVar0.equals(PerioSequenceType.GingMargin))
            {
                redThresh = PrefC.getInt(PrefName.PerioRedGing);
            }
            else if (__dummyScrutVar0.equals(PerioSequenceType.CAL))
            {
                redThresh = PrefC.getInt(PrefName.PerioRedCAL);
            }
            else if (__dummyScrutVar0.equals(PerioSequenceType.Furcation))
            {
                redThresh = PrefC.getInt(PrefName.PerioRedFurc);
            }
            else if (__dummyScrutVar0.equals(PerioSequenceType.Mobility))
            {
                redThresh = PrefC.getInt(PrefName.PerioRedMob);
            }
                  
            if ((RowTypes[getSection(row)][getSectionRow(row)] == PerioSequenceType.MGJ && cellValue <= redThresh) || (RowTypes[getSection(row)][getSectionRow(row)] != PerioSequenceType.MGJ && cellValue >= redThresh))
            {
                if (drawOld)
                {
                    textColor = cOldTextRed;
                }
                else
                {
                    textColor = cRedText;
                    if (!perioEdit)
                    {
                        textColor = cOldTextRed;
                    }
                     
                } 
                font = new Font(Font, FontStyle.Bold);
            }
             
            //if number is two digits:
            if (cellValue > 9)
            {
                font = new Font("SmallFont", 7);
                rect = new RectangleF(rect.X - 2, rect.Y + 1, rect.Width + 5, rect.Height);
            }
             
            //if number is negative. "+" symbol is wider than "1" symbol so hand it seperately here.
            if (cellValue < 0)
            {
                font = new Font("SmallFont", 7);
                rect = new RectangleF(rect.X - 3, rect.Y + 1, rect.Width + 5, rect.Height);
            }
             
            //shift text left, 1 more pixel than usual.
            //e.Graphics.DrawString(cellValue.ToString(),Font,Brushes.Black,rect);
            if ((RowTypes[getSection(row)][getSectionRow(row)] == PerioSequenceType.GingMargin))
            {
                e.Graphics.DrawString(cellValue.ToString().Replace('-', '+'), font, new SolidBrush(textColor), rect, format);
            }
            else
            {
                //replace '-' with '+' for Gingival Margin Hyperplasia
                e.Graphics.DrawString(cellValue.ToString(), font, new SolidBrush(textColor), rect, format);
            } 
        }
    }

    //i col
    /**
    * Gets the bounds for a single cell.
    */
    private RectangleF getBounds(int col, int row) throws Exception {
        float xLoc = new float();
        if (col == 0)
        {
            xLoc = 0;
        }
        else
        {
            xLoc = 1 + Wleft + 1 + (col - 1) * (Wmeas + 1);
        } 
        if (getSection(row) == -1)
        {
            //tooth row
            xLoc -= Wmeas;
        }
         
        float h = 0;
        //if(row==24){
        //	MessageBox.Show(RowTypes[GetSection(row)][GetSectionRow(row)].ToString());
        //MessageBox.Show(GetSection(row).ToString()+","+GetSectionRow(row).ToString());
        //}
        //try{
        if (getSection(row) == -1)
        {
            //tooth row
            h = Htooth;
        }
        else if (RowTypes[getSection(row)][getSectionRow(row)] == PerioSequenceType.Probing)
        {
            //probing
            h = Hprob;
        }
        else
        {
            h = Hshort + 1;
        }  
        //added the 1 so that a lower case y can drop a little lower.
        //}
        //catch{
        //	MessageBox.Show(row.ToString());
        //}
        float w = new float();
        if (getSection(row) == -1)
        {
            //tooth row
            w = Wmeas * 3;
        }
        else if (col == 0)
            w = Wleft + 1;
        else
            w = Wmeas;  
        return new RectangleF(xLoc, TopCoordinates[row], w, h);
    }

    //try{
    //if(row==10)
    //	MessageBox.Show(TopCoordinates[row].ToString());
    //}
    //catch{
    //MessageBox.Show(row.ToString());
    //}
    //return new RectangleF(0,0,70,20);
    /**
    * Fills an array with all the Y coordinates of each row for faster retrieval by GetBounds, and calculation of click events.
    */
    private void fillTopCoordinates() throws Exception {
        TopCoordinates = new float[DataArray.GetLength(1)];
        //int curRow=0;
        int yPos = 1;
        for (int i = RowTypes[0].Length - 1;i >= 0;i--)
        {
            //U facial
            TopCoordinates[getTableRow(0,i)] = yPos;
            //MessageBox.Show(GetTableRow(0,i));
            if (RowTypes[0][i] == PerioSequenceType.Probing)
            {
                yPos += Hprob + 1;
            }
            else
            {
                yPos += Hshort + 1;
            } 
        }
        TopCoordinates[getTableRow(true)] = yPos;
        yPos += Htooth + 1;
        for (int i = 0;i < RowTypes[1].Length;i++)
        {
            //upper lingual
            TopCoordinates[getTableRow(1,i)] = yPos;
            if (RowTypes[1][i] == PerioSequenceType.Probing)
            {
                yPos += Hprob + 1;
            }
            else
            {
                yPos += Hshort + 1;
            } 
        }
        yPos += 1;
        for (int i = RowTypes[2].Length - 1;i >= 0;i--)
        {
            //makes a double line between u and L
            //lower lingual
            //MessageBox.Show(GetTableRow(2,3).ToString());
            TopCoordinates[getTableRow(2,i)] = yPos;
            if (RowTypes[2][i] == PerioSequenceType.Probing)
            {
                yPos += Hprob + 1;
            }
            else
            {
                yPos += Hshort + 1;
            } 
        }
        TopCoordinates[getTableRow(false)] = yPos;
        yPos += Htooth + 1;
        for (int i = 0;i < RowTypes[3].Length;i++)
        {
            //lower facial
            TopCoordinates[getTableRow(3,i)] = yPos;
            if (RowTypes[3][i] == PerioSequenceType.Probing)
            {
                yPos += Hprob + 1;
            }
            else
            {
                yPos += Hshort + 1;
            } 
        }
    }

    /**
    * Loads data from the PerioMeasures lists into the visible grid.
    */
    public void loadData() throws Exception {
        clearDataArray();
        selectedTeeth = new ArrayList();
        skippedTeeth = new List<int>();
        //reset all HasChanged values to false
        HasChanged = new boolean[PerioMeasures.List.GetLength(1), 33];
        if (selectedExam == -1)
        {
            return ;
        }
         
        fillDates();
        Point curCell = new Point();
        //int examI=selectedExam;
        String cellText = "";
        int cellBleed = 0;
        for (int examI = 0;examI <= selectedExam;examI++)
        {
            for (int seqI = 0;seqI < PerioMeasures.List.GetLength(1);seqI++)
            {
                for (int toothI = 1;toothI < PerioMeasures.List.GetLength(2);toothI++)
                {
                    //exams, earliest through current
                    //sequences
                    //measurements
                    if (PerioMeasures.List[examI, seqI, toothI] == null)
                        continue;
                     
                    for (int surfI = 0;surfI < Enum.GetNames(PerioSurf.class).Length;surfI++)
                    {
                        //.PerioMeasureNum==0)
                        //no measurement for this seq and tooth
                        //surfaces(6or7)
                        if (seqI == ((Enum)PerioSequenceType.SkipTooth).ordinal())
                        {
                            //There is nothing in the data array to fill because it is not user editable.
                            if (surfI != ((Enum)PerioSurf.None).ordinal())
                            {
                                continue;
                            }
                             
                            if (examI != selectedExam)
                            {
                                continue;
                            }
                             
                            //only mark skipped teeth for current exam
                            if (PerioMeasures.List[examI, seqI, toothI].ToothValue == 1)
                            {
                                skippedTeeth.Add(toothI);
                            }
                             
                            continue;
                        }
                        else if (seqI == ((Enum)PerioSequenceType.Mobility).ordinal())
                        {
                            if (surfI != ((Enum)PerioSurf.None).ordinal())
                            {
                                continue;
                            }
                             
                            curCell = GetCell(examI, PerioMeasures.List[examI, seqI, toothI].SequenceType, toothI, PerioSurf.B);
                            cellText = PerioMeasures.List[examI, seqI, toothI].ToothValue.ToString();
                            if (StringSupport.equals(cellText, "-1"))
                            {
                                cellText = "";
                            }
                             
                            if (examI == selectedExam)
                                DataArray[curCell.X, curCell.Y].Text = cellText;
                            else
                                DataArray[curCell.X, curCell.Y].OldText = cellText; 
                            continue;
                        }
                        else if (seqI == ((Enum)PerioSequenceType.Bleeding).ordinal())
                        {
                            if (surfI == ((Enum)PerioSurf.None).ordinal())
                            {
                                continue;
                            }
                             
                            curCell = getCell(examI,PerioSequenceType.Probing,toothI,PerioSurf.values()[surfI]);
                            if (curCell.X == -1 || curCell.Y == -1)
                                continue;
                             
                            //if there are more exams than will fit.
                            switch(surfI)
                            {
                                case ((Enum)PerioSurf.B).ordinal(): 
                                    cellBleed = PerioMeasures.List[examI, seqI, toothI].Bvalue;
                                    break;
                                case ((Enum)PerioSurf.DB).ordinal(): 
                                    cellBleed = PerioMeasures.List[examI, seqI, toothI].DBvalue;
                                    break;
                                case ((Enum)PerioSurf.DL).ordinal(): 
                                    cellBleed = PerioMeasures.List[examI, seqI, toothI].DLvalue;
                                    break;
                                case ((Enum)PerioSurf.L).ordinal(): 
                                    cellBleed = PerioMeasures.List[examI, seqI, toothI].Lvalue;
                                    break;
                                case ((Enum)PerioSurf.MB).ordinal(): 
                                    cellBleed = PerioMeasures.List[examI, seqI, toothI].MBvalue;
                                    break;
                                case ((Enum)PerioSurf.ML).ordinal(): 
                                    cellBleed = PerioMeasures.List[examI, seqI, toothI].MLvalue;
                                    break;
                            
                            }
                            if (cellBleed == -1)
                                //this shouldn't happen, but just in case
                                cellBleed = 0;
                             
                            DataArray[curCell.X, curCell.Y].Bleeding = BleedingFlags.values()[cellBleed];
                            continue;
                        }
                           
                        curCell = GetCell(examI, PerioMeasures.List[examI, seqI, toothI].SequenceType, toothI, PerioSurf.values()[surfI]);
                        if (curCell.X == -1 || curCell.Y == -1)
                            continue;
                         
                        //for instance, MGJ on Palatal doesn't exist.
                        //also, on probing rows, if there are more exams than will fit.
                        switch(surfI)
                        {
                            case ((Enum)PerioSurf.B).ordinal(): 
                                cellText = PerioMeasures.List[examI, seqI, toothI].Bvalue.ToString();
                                break;
                            case ((Enum)PerioSurf.DB).ordinal(): 
                                cellText = PerioMeasures.List[examI, seqI, toothI].DBvalue.ToString();
                                break;
                            case ((Enum)PerioSurf.DL).ordinal(): 
                                cellText = PerioMeasures.List[examI, seqI, toothI].DLvalue.ToString();
                                break;
                            case ((Enum)PerioSurf.L).ordinal(): 
                                cellText = PerioMeasures.List[examI, seqI, toothI].Lvalue.ToString();
                                break;
                            case ((Enum)PerioSurf.MB).ordinal(): 
                                cellText = PerioMeasures.List[examI, seqI, toothI].MBvalue.ToString();
                                break;
                            case ((Enum)PerioSurf.ML).ordinal(): 
                                cellText = PerioMeasures.List[examI, seqI, toothI].MLvalue.ToString();
                                break;
                        
                        }
                        //switch surfI
                        if (StringSupport.equals(cellText, "-1"))
                        {
                            //seqI==2 means it is gingival margin.
                            cellText = "";
                        }
                         
                        if (examI == selectedExam)
                            DataArray[curCell.X, curCell.Y].Text = cellText;
                        else
                            DataArray[curCell.X, curCell.Y].OldText = cellText; 
                        //calculate CAL. All ging will have been done before any probing.
                        if (seqI == ((Enum)PerioSequenceType.Probing).ordinal())
                        {
                            calculateCAL(curCell,false);
                        }
                         
                    }
                }
            }
        }
        //for surfI
        //for toothI
        //for seqI
        //for examI
        CurCell = new Point(1, getTableRow(selectedExam,0,PerioSequenceType.Probing));
    }

    /**
    * Used in LoadData.
    */
    private void fillDates() throws Exception {
        int tableRow = new int();
        for (int examI = 0;examI < selectedExam + 1;examI++)
        {
            for (int section = 0;section < 4;section++)
            {
                //-ProbingOffset;examI++){
                tableRow = getTableRow(examI,section,PerioSequenceType.Probing);
                if (tableRow == -1)
                    continue;
                 
                DataArray[0, tableRow].Text = PerioExams.ListExams[examI].ExamDate.ToShortDateString();
            }
        }
    }

    //=PerioExams.List[examI+ProbingOffset].ExamDate.ToShortDateString();
    /**
    * Used in LoadData.
    */
    private Point getCell(int examIndex, PerioSequenceType seqType, int intTooth, PerioSurf surf) throws Exception {
        int col = 0;
        int row = 0;
        if (intTooth <= 16)
        {
            col = (intTooth * 3) - 2;
            //left-most column
            if (surf == PerioSurf.B || surf == PerioSurf.L)
            {
                col++;
            }
             
            if (intTooth <= 8)
            {
                if (surf == PerioSurf.MB || surf == PerioSurf.ML)
                    col += 2;
                 
            }
            else
            {
                //9-16
                if (surf == PerioSurf.DB || surf == PerioSurf.DL)
                    col += 2;
                 
            } 
        }
        else
        {
            //17-32
            col = ((33 - intTooth) * 3) - 2;
            //left-most column
            if (surf == PerioSurf.B || surf == PerioSurf.L)
            {
                col++;
            }
             
            if (intTooth >= 25)
            {
                if (surf == PerioSurf.MB || surf == PerioSurf.ML)
                    col += 2;
                 
            }
            else
            {
                //17-24
                if (surf == PerioSurf.DB || surf == PerioSurf.DL)
                    col += 2;
                 
            } 
        } 
        int section = new int();
        if (intTooth <= 16)
        {
            if (surf == PerioSurf.MB || surf == PerioSurf.B || surf == PerioSurf.DB)
            {
                section = 0;
            }
            else
            {
                //Lingual
                section = 1;
            } 
        }
        else
        {
            //17-32
            if (surf == PerioSurf.MB || surf == PerioSurf.B || surf == PerioSurf.DB)
            {
                section = 3;
            }
            else
            {
                //Lingual
                section = 2;
            } 
        } 
        row = getTableRow(examIndex,section,seqType);
        return new Point(col, row);
    }

    /**
    * Saves the cur exam measurements to the db by looping through each tooth and deciding whether the data for that tooth has changed.  If so, it either updates or inserts a measurement.  It won't delete a measurement if all values for that tooth are cleared, but just sets that measurement to all -1's.
    */
    public void saveCurExam(long perioExamNum) throws Exception {
        PerioMeasure PerioMeasureCur;
        for (int seqI = 0;seqI < PerioMeasures.List.GetLength(1);seqI++)
        {
            for (int toothI = 1;toothI < PerioMeasures.List.GetLength(2);toothI++)
            {
                //if bleeding, then check the probing row for change
                //or, for any other type, check the corresponding row
                if ((seqI == ((Enum)PerioSequenceType.Bleeding).ordinal() && HasChanged[((Enum)PerioSequenceType.Probing).ordinal(), toothI]) || HasChanged[seqI, toothI])
                {
                    //new measurement
                    if (PerioMeasures.List[selectedExam, seqI, toothI] == null)
                    {
                        //.PerioMeasureNum==0){
                        //MessageBox.Show(toothI.ToString());
                        PerioMeasureCur = new PerioMeasure();
                        PerioMeasureCur.PerioExamNum = perioExamNum;
                        PerioMeasureCur.SequenceType = PerioSequenceType.values()[seqI];
                        PerioMeasureCur.IntTooth = toothI;
                    }
                    else
                    {
                        PerioMeasureCur = PerioMeasures.List[selectedExam, seqI, toothI];
                    } 
                    //PerioExam
                    //Sequence
                    //tooth
                    if (seqI == ((Enum)PerioSequenceType.Mobility).ordinal() || seqI == ((Enum)PerioSequenceType.SkipTooth).ordinal())
                    {
                        PerioMeasureCur.MBvalue = -1;
                        PerioMeasureCur.Bvalue = -1;
                        PerioMeasureCur.DBvalue = -1;
                        PerioMeasureCur.MLvalue = -1;
                        PerioMeasureCur.Lvalue = -1;
                        PerioMeasureCur.DLvalue = -1;
                        if (seqI == ((Enum)PerioSequenceType.Mobility).ordinal())
                        {
                            PerioMeasureCur.ToothValue = getCellValue(selectedExam,PerioSequenceType.values()[seqI],toothI,PerioSurf.B);
                        }
                        else
                        {
                        } 
                    }
                    else //skiptooth
                    //skipped teeth are only saved when user marks them, not as part of regular saving.
                    if (seqI == ((Enum)PerioSequenceType.Bleeding).ordinal())
                    {
                        PerioMeasureCur.ToothValue = -1;
                        PerioMeasureCur.MBvalue = getCellBleedValue(selectedExam,toothI,PerioSurf.MB);
                        PerioMeasureCur.Bvalue = getCellBleedValue(selectedExam,toothI,PerioSurf.B);
                        PerioMeasureCur.DBvalue = getCellBleedValue(selectedExam,toothI,PerioSurf.DB);
                        PerioMeasureCur.MLvalue = getCellBleedValue(selectedExam,toothI,PerioSurf.ML);
                        PerioMeasureCur.Lvalue = getCellBleedValue(selectedExam,toothI,PerioSurf.L);
                        PerioMeasureCur.DLvalue = getCellBleedValue(selectedExam,toothI,PerioSurf.DL);
                    }
                    else
                    {
                        PerioMeasureCur.ToothValue = -1;
                        PerioMeasureCur.MBvalue = getCellValue(selectedExam,PerioSequenceType.values()[seqI],toothI,PerioSurf.MB);
                        PerioMeasureCur.Bvalue = getCellValue(selectedExam,PerioSequenceType.values()[seqI],toothI,PerioSurf.B);
                        PerioMeasureCur.DBvalue = getCellValue(selectedExam,PerioSequenceType.values()[seqI],toothI,PerioSurf.DB);
                        PerioMeasureCur.MLvalue = getCellValue(selectedExam,PerioSequenceType.values()[seqI],toothI,PerioSurf.ML);
                        PerioMeasureCur.Lvalue = getCellValue(selectedExam,PerioSequenceType.values()[seqI],toothI,PerioSurf.L);
                        PerioMeasureCur.DLvalue = getCellValue(selectedExam,PerioSequenceType.values()[seqI],toothI,PerioSurf.DL);
                    }  
                    //then to the database
                    if (PerioMeasures.List[selectedExam, seqI, toothI] == null)
                    {
                        PerioMeasures.insert(PerioMeasureCur);
                    }
                    else
                    {
                        PerioMeasures.update(PerioMeasureCur);
                    } 
                }
                 
            }
        }
    }

    //if haschanged
    //for toothI
    //for seqI
    /**
    * Used in SaveCurExam to retrieve data from grid to save it.
    */
    private int getCellValue(int examIndex, PerioSequenceType seqType, int intTooth, PerioSurf surf) throws Exception {
        Point curCell = getCell(examIndex,seqType,intTooth,surf);
        if (curCell.X == -1 || curCell.Y == -1)
        {
            return -1;
        }
         
        //if(intTooth==4)
        //MessageBox.Show(DataArray[curCell.X,curCell.Y].Text);
        if (DataArray[curCell.X, curCell.Y].Text == null || StringSupport.equals(DataArray[curCell.X, curCell.Y].Text, ""))
        {
            return -1;
        }
         
        return PIn.Int(DataArray[curCell.X, curCell.Y].Text);
    }

    //MessageBox.Show("empty");
    //MessageBox.Show("full");
    /**
    * Used in SaveCurExam to retrieve data from grid to save it.
    */
    private int getCellBleedValue(int examIndex, int intTooth, PerioSurf surf) throws Exception {
        Point curCell = getCell(examIndex,PerioSequenceType.Probing,intTooth,surf);
        if (curCell.X == -1 || curCell.Y == -1)
        {
            return 0;
        }
         
        return (int)DataArray[curCell.X, curCell.Y].Bleeding;
    }

    private void clearDataArray() throws Exception {
        //MessageBox.Show("clearing");
        DataArray = new PerioCell[49, RowTypes[0].Length + RowTypes[1].Length + RowTypes[2].Length + RowTypes[3].Length + 2];
        //the 2 is for the tooth cells.
        //int curX=0;
        int curY = 0;
        for (int sect = 0;sect < 4;sect++)
        {
            for (int i = 0;i < RowTypes[sect].Length;i++)
            {
                curY = getTableRow(sect,i);
                System.Array'1.INDEXER.INDEXER __dummyScrutVar3 = RowTypes[sect][i];
                if (__dummyScrutVar3.equals(PerioSequenceType.Mobility))
                {
                    DataArray[0, curY].Text = Lan.g(this,"Mobility");
                }
                else if (__dummyScrutVar3.equals(PerioSequenceType.Furcation))
                {
                    DataArray[0, curY].Text = Lan.g(this,"Furc");
                }
                else if (__dummyScrutVar3.equals(PerioSequenceType.CAL))
                {
                    DataArray[0, curY].Text = Lan.g(this,"auto CAL");
                }
                else if (__dummyScrutVar3.equals(PerioSequenceType.GingMargin))
                {
                    DataArray[0, curY].Text = Lan.g(this,"Ging Marg");
                }
                else if (__dummyScrutVar3.equals(PerioSequenceType.MGJ))
                {
                    DataArray[0, curY].Text = Lan.g(this,"MGJ");
                }
                else if (__dummyScrutVar3.equals(PerioSequenceType.Probing))
                {
                }
                else
                {
                    MessageBox.Show("Error in FillDataArray");
                }      
            }
        }
        //draw tooth numbers
        curY = getTableRow(true);
        try
        {
            for (int i = 1;i <= 16;i++)
            {
                DataArray[3 * i - 1, curY].Text = Tooth.ToInternat(i.ToString());
            }
            curY = getTableRow(false);
            for (int i = 1;i <= 16;i++)
            {
                DataArray[3 * i - 1, curY].Text = Tooth.ToInternat((33 - i).ToString());
            }
        }
        catch (Exception __dummyCatchVar0)
        {
        }
    
    }

    //won't work in design mode
    /**
    * Used in GetCell during LoadData. Also used in AdvanceCell when looping to a new section.
    */
    private int getTableRow(int examIndex, int section, PerioSequenceType seqType) throws Exception {
        if (seqType == PerioSequenceType.Probing || seqType == PerioSequenceType.Bleeding)
        {
            if (examIndex - ProbingOffset < 0)
                return -1;
             
            //older exam that won't fit.
            //correct for offset
            int sectionRow = examIndex - ProbingOffset + RowTypes[section].Length - RowsProbing;
            return getTableRow(section,sectionRow);
        }
         
        for (int i = 0;i < RowTypes[section].Length - RowsProbing;i++)
        {
            //plus number of non-probing rows
            //for types other than probing and bleeding, do a loop through the non-probing rows:
            if (RowTypes[section][i] == seqType)
                return getTableRow(section,i);
             
        }
        return -1;
    }

    //MessageBox.Show("Error in GetTableRows: seqType not found");
    private int getTableRow(int section, int sectionRow) throws Exception {
        int retVal = 0;
        if (section == 0)
        {
            retVal = RowTypes[0].Length - sectionRow - 1;
        }
        else if (section == 1)
        {
            retVal = RowTypes[0].Length + 1 + sectionRow;
        }
        else if (section == 2)
        {
            retVal = RowTypes[0].Length + 1 + RowTypes[1].Length + RowTypes[2].Length - sectionRow - 1;
        }
        else
            retVal = RowTypes[0].Length + 1 + RowTypes[1].Length + RowTypes[2].Length + 1 + sectionRow;   
        return retVal;
    }

    /**
    * If true, then returns the row of the max teeth, otherwise mand.
    */
    private int getTableRow(boolean getMax) throws Exception {
        if (getMax)
        {
            return RowTypes[0].Length;
        }
         
        return RowTypes[0].Length + 1 + RowTypes[1].Length + RowTypes[2].Length;
    }

    /**
    * Returns -1 if a tooth row and not a section row. Used in GetBounds, DrawRow, and OnMouseDown.
    */
    private int getSection(int tableRow) throws Exception {
        if (tableRow < RowTypes[0].Length)
        {
            return 0;
        }
         
        if (tableRow == RowTypes[0].Length)
        {
            return -1;
        }
         
        //max teeth
        if (tableRow < RowTypes[0].Length + 1 + RowTypes[1].Length)
        {
            return 1;
        }
         
        if (tableRow < RowTypes[0].Length + 1 + RowTypes[1].Length + RowTypes[2].Length)
        {
            return 2;
        }
         
        if (tableRow == RowTypes[0].Length + 1 + RowTypes[1].Length + RowTypes[2].Length)
        {
            return -1;
        }
         
        return 3;
    }

    //mand teeth
    /**
    * Returns -1 if a tooth row and not a section row.  Used in GetBounds,SetHasChanged, AdvanceCell, and DrawRow
    */
    private int getSectionRow(int tableRow) throws Exception {
        if (tableRow < RowTypes[0].Length)
        {
            return RowTypes[0].Length - tableRow - 1;
        }
         
        //return 0;
        if (tableRow == RowTypes[0].Length)
        {
            return -1;
        }
         
        //max teeth
        if (tableRow < RowTypes[0].Length + 1 + RowTypes[1].Length)
        {
            return tableRow - RowTypes[0].Length - 1;
        }
         
        if (tableRow < RowTypes[0].Length + 1 + RowTypes[1].Length + RowTypes[2].Length)
        {
            return RowTypes[0].Length + 1 + RowTypes[1].Length + RowTypes[2].Length - tableRow - 1;
        }
         
        //-1?
        if (tableRow == RowTypes[0].Length + 1 + RowTypes[1].Length + RowTypes[2].Length)
        {
            return -1;
        }
         
        return tableRow - RowTypes[0].Length - 1 - RowTypes[1].Length - RowTypes[2].Length - 1;
    }

    //mand teeth
    //-1?
    /**
    * Gets the current cell as a col,row based on the x-y pixel coordinate supplied.
    */
    private Point getCellFromPixel(int x, int y) throws Exception {
        int row = 0;
        for (int i = 0;i < TopCoordinates.Length;i++)
        {
            if (y < TopCoordinates[i])
            {
                row = i - 1;
                break;
            }
             
            if (i == TopCoordinates.Length - 1)
            {
                //last row
                row = i;
            }
             
        }
        if (y == -1)
            y = 0;
         
        int col = 0;
        if (x <= Wleft + 1)
        {
            col = 0;
        }
        else
        {
            //1+Wleft+1+(col-1)*(Wmeas+1);
            col = (int)Math.Floor(((double)(x - Wleft - 1)) / ((double)(Wmeas + 1))) + 1;
        } 
        if (col == 49)
            col = 48;
         
        return new Point(col, row);
    }

    /**
    * 
    */
    protected void onMouseDown(MouseEventArgs e) throws Exception {
        if (!perioEdit)
        {
            return ;
        }
         
        super.OnMouseDown(e);
        Point newCell = GetCellFromPixel(e.X, e.Y);
        if (newCell.X == 0)
        {
            return ;
        }
         
        //Left column only for dates
        int section = GetSection(newCell.Y);
        if (section == -1)
        {
            //clicked on a toothNum
            int intTooth = (int)Math.Ceiling((double)newCell.X / 3);
            if (getTableRow(false) == newCell.Y)
            {
                //if clicked on mand
                intTooth = 33 - intTooth;
            }
             
            if (selectedTeeth.Contains(intTooth))
            {
                //tooth was already selected
                selectedTeeth.Remove(intTooth);
            }
            else
            {
                //tooth was not selected
                selectedTeeth.Add(intTooth);
            } 
            Invalidate();
            return ;
        }
         
        //incomplete: just invalidate the area around the tooth num.
        if (selectedTeeth.Count > 0)
        {
            //if not clicked on a toothnum, but teeth were selected,
            //then deselect all.
            //todo(some day): loop through each individually and only invalidate small area.
            selectedTeeth = new ArrayList();
            Invalidate();
        }
         
        int sectRow = GetSectionRow(newCell.Y);
        if (RowTypes[section][sectRow] == PerioSequenceType.Probing)
        {
            //correct for offset
            //plus non-probing rows
            if (this.selectedExam - ProbingOffset + RowTypes[section].Length - RowsProbing != sectRow)
            {
                return ;
            }
             
        }
        else //not allowed to click on probing rows other than selectedRow
        if (RowTypes[section][sectRow] == PerioSequenceType.Mobility)
        {
            if (Math.IEEERemainder(((double)newCell.X + 1), 3) != 0)
            {
                return ;
            }
             
        }
        else //{2,5,8,11};examples of acceptable cols
        //for mobility, not allowed to click on anything but B
        if (RowTypes[section][sectRow] == PerioSequenceType.CAL)
        {
            return ;
        }
           
        //never allowed to edit CAL
        if (section == 0)
            onDirectionChangedLeft();
        else if (section == 1)
            onDirectionChangedRight();
        else if (section == 2)
            onDirectionChangedRight();
        else if (section == 3)
            onDirectionChangedLeft();
            
        SetNewCell(newCell.X, newCell.Y);
        Focus();
    }

    /**
    * 
    */
    protected boolean isInputKey(Keys keyData) throws Exception {
        if (keyData == Keys.Left || keyData == Keys.Right || keyData == Keys.Up || keyData == Keys.Down)
            return true;
         
        return super.IsInputKey(keyData);
    }

    /**
    * 
    */
    protected void onKeyDown(KeyEventArgs e) throws Exception {
        if (!perioEdit)
        {
            return ;
        }
         
        if (selectedExam == -1)
        {
            MessageBox.Show(Lan.g(this,"Please add or select an exam first in the list to the left."));
            return ;
        }
         
        //MessageBox.Show("key down");
        //e.Handled=true;
        //base.OnKeyDown (e);
        if (e.KeyValue >= 96 && e.KeyValue <= 105)
        {
            //keypad 0 through 9
            if (e.Control)
            {
                if (RowTypes[GetSection(CurCell.Y)][GetSectionRow(CurCell.Y)] == PerioSequenceType.GingMargin)
                {
                    int val = (e.KeyValue - 96);
                    buttonPressed((val == 0 ? 0 : val + 100));
                }
                else
                {
                    //0 if val==0, val+100 if val != 0
                    //general case
                    ButtonPressed(e.KeyValue - 96 + 10);
                } 
            }
            else
            {
                ButtonPressed(e.KeyValue - 96);
            } 
        }
        else if (e.KeyValue >= 48 && e.KeyValue <= 57)
        {
            //0 through 9
            if (e.Control)
            {
                if (RowTypes[GetSection(CurCell.Y)][GetSectionRow(CurCell.Y)] == PerioSequenceType.GingMargin)
                {
                    //gm
                    int val = (e.KeyValue - 48);
                    buttonPressed((val == 0 ? 0 : val + 100));
                }
                else
                {
                    //0 if val==0, val+100 if val != 0
                    //general case
                    ButtonPressed(e.KeyValue - 48 + 10);
                } 
            }
            else
            {
                ButtonPressed(e.KeyValue - 48);
            } 
        }
        else if (e.KeyCode == Keys.B)
        {
            buttonPressed("b");
        }
        else if (e.KeyCode == Keys.Space)
        {
            buttonPressed("b");
        }
        else if (e.KeyCode == Keys.S)
        {
            buttonPressed("s");
        }
        else if (e.KeyCode == Keys.P)
        {
            buttonPressed("p");
        }
        else if (e.KeyCode == Keys.C)
        {
            buttonPressed("c");
        }
        else if (e.KeyCode == Keys.Back)
        {
            if (ThreeAtATime)
            {
                for (int i = 0;i < 3;i++)
                {
                    advanceCell(true);
                    clearValue();
                }
            }
            else
            {
                advanceCell(true);
                clearValue();
            } 
        }
        else if (e.KeyCode == Keys.Delete)
        {
            clearValue();
        }
        else if (e.KeyCode == Keys.Left)
        {
            if (ThreeAtATime)
            {
                for (int i = 0;i < 3;i++)
                {
                    if (DirectionIsRight)
                        advanceCell();
                    else
                        advanceCell(true); 
                }
            }
            else
            {
                if (DirectionIsRight)
                    advanceCell();
                else
                    advanceCell(true); 
            } 
        }
        else if (e.KeyCode == Keys.Right)
        {
            if (ThreeAtATime)
            {
                for (int i = 0;i < 3;i++)
                {
                    if (DirectionIsRight)
                        advanceCell(true);
                    else
                        advanceCell(); 
                }
            }
            else
            {
                if (DirectionIsRight)
                    advanceCell(true);
                else
                    advanceCell(); 
            } 
        }
                   
    }

    //else{
    //	return;
    //}
    /**
    * Accepts button clicks from window rather than the usual keyboard entry.  All validation MUST be done before the value is sent here.  Only valid values are b,s,p,or c. Numbers entered using overload.
    */
    public void buttonPressed(String keyValue) throws Exception {
        if (!perioEdit)
        {
            return ;
        }
         
        if (ThreeAtATime)
        {
            for (int i = 0;i < 3;i++)
                enterValue(keyValue);
        }
        else
            enterValue(keyValue); 
    }

    /**
    * Accepts button clicks from window rather than the usual keyboard entry.  All validation MUST be done before the value is sent here.  Only valid values are numbers 0 through 19, and 101 to 109.
    */
    public void buttonPressed(int keyValue) throws Exception {
        if (!perioEdit)
        {
            return ;
        }
         
        if (GingMargPlus && RowTypes[GetSection(CurCell.Y)][GetSectionRow(CurCell.Y)] == PerioSequenceType.GingMargin)
        {
            //Possible values for keyValue are 0-19,101-109
            if (keyValue < 100)
            {
                keyValue = keyValue % 10;
                //in case the +10 was down when the number was pressed on the onscreen keypad.
                if (keyValue != 0)
                {
                    //add 100 to represent negative values unless they pressed 0
                    keyValue += 100;
                }
                 
            }
             
        }
         
        //Possible values for keyValue at this point are 0, 101-109.
        if (ThreeAtATime)
        {
            for (int i = 0;i < 3;i++)
            {
                enterValue(keyValue);
            }
        }
        else
        {
            enterValue(keyValue);
        } 
    }

    /**
    * Only valid values are b,s,p, and c.
    */
    private void enterValue(String keyValue) throws Exception {
        if (!StringSupport.equals(keyValue, "b") && !StringSupport.equals(keyValue, "s") && !StringSupport.equals(keyValue, "p") && !StringSupport.equals(keyValue, "c"))
        {
            MessageBox.Show("Only b,s,p, and c are allowed");
            return ;
        }
         
        //just for debugging
        PerioCell cur = DataArray[CurCell.X, CurCell.Y];
        boolean curCellHasText = false;
        if (ThreeAtATime)
        {
        }
        else //don't backup
        if (cur.Text != null && !StringSupport.equals(cur.Text, ""))
        {
            curCellHasText = true;
        }
        else
        {
            //so enter value for current cell
            curCellHasText = false;
            advanceCell(true);
            //so backup
            cur = DataArray[CurCell.X, CurCell.Y];
            if (cur.Text == null || StringSupport.equals(cur.Text, ""))
            {
                //the previous cell is also empty
                curCellHasText = true;
                //treat it like a cell with text
                advanceCell();
                //go forward again
                cur = DataArray[CurCell.X, CurCell.Y];
            }
             
        }  
        //enter value, then advance.
        if (StringSupport.equals(keyValue, "b"))
        {
            if ((cur.Bleeding & BleedingFlags.Blood) == 0)
                //if it was off
                cur.Bleeding = cur.Bleeding | BleedingFlags.Blood;
            else
                //turn it on
                //if it was on
                cur.Bleeding = cur.Bleeding & ~BleedingFlags.Blood; 
        }
         
        //turn it off
        if (StringSupport.equals(keyValue, "s"))
        {
            if ((cur.Bleeding & BleedingFlags.Suppuration) == 0)
                cur.Bleeding = cur.Bleeding | BleedingFlags.Suppuration;
            else
                cur.Bleeding = cur.Bleeding & ~BleedingFlags.Suppuration; 
        }
         
        if (StringSupport.equals(keyValue, "p"))
        {
            if ((cur.Bleeding & BleedingFlags.Plaque) == 0)
                cur.Bleeding = cur.Bleeding | BleedingFlags.Plaque;
            else
                cur.Bleeding = cur.Bleeding & ~BleedingFlags.Plaque; 
        }
         
        if (StringSupport.equals(keyValue, "c"))
        {
            if ((cur.Bleeding & BleedingFlags.Calculus) == 0)
                cur.Bleeding = cur.Bleeding | BleedingFlags.Calculus;
            else
                cur.Bleeding = cur.Bleeding & ~BleedingFlags.Calculus; 
        }
         
        DataArray[CurCell.X, CurCell.Y] = cur;
        Invalidate(Rectangle.Ceiling(GetBounds(CurCell.X, CurCell.Y)));
        SetHasChanged(CurCell.X, CurCell.Y);
        if (ThreeAtATime)
        {
            advanceCell();
        }
        else if (!curCellHasText)
        {
            advanceCell();
        }
          
    }

    //to return to original location
    /**
    * Only valid values are numbers 0-19, and 101-109. Validation should be done before sending here.
    */
    private void enterValue(int keyValue) throws Exception {
        if ((keyValue < 0 || keyValue > 19) && RowTypes[GetSection(CurCell.Y)][GetSectionRow(CurCell.Y)] != PerioSequenceType.GingMargin)
        {
            //large values are allowed for GingMargin to represent hyperplasia (e.g. 101 to 109 represent -1 to -9)
            MessageBox.Show("Only values 0 through 19 allowed");
            return ;
        }
         
        //just for debugging
        PerioCell cur = DataArray[CurCell.X, CurCell.Y];
        //might be able to eliminate these two lines
        cur.Text = keyValue.ToString();
        DataArray[CurCell.X, CurCell.Y] = cur;
        Invalidate(Rectangle.Ceiling(GetBounds(CurCell.X, CurCell.Y)));
        SetHasChanged(CurCell.X, CurCell.Y);
        if (RowTypes[GetSection(CurCell.Y)][GetSectionRow(CurCell.Y)] == PerioSequenceType.Probing)
        {
            calculateCAL(CurCell,true);
        }
        else if (RowTypes[GetSection(CurCell.Y)][GetSectionRow(CurCell.Y)] == PerioSequenceType.GingMargin)
        {
            calculateCAL(new Point(CurCell.X, GetTableRow(selectedExam, GetSection(CurCell.Y), PerioSequenceType.Probing)),true);
        }
          
        advanceCell();
    }

    private void calculateCAL(Point probingCell, boolean alsoInvalidate) throws Exception {
        Point calLoc = new Point(probingCell.X, GetTableRow(selectedExam, GetSection(probingCell.Y), PerioSequenceType.CAL));
        Point gingLoc = new Point(probingCell.X, GetTableRow(selectedExam, GetSection(probingCell.Y), PerioSequenceType.GingMargin));
        //PerioCell calCell=DataArray[calLoc.X,calLoc.Y];
        if (DataArray[probingCell.X, probingCell.Y].Text == null || StringSupport.equals(DataArray[probingCell.X, probingCell.Y].Text, "") || DataArray[gingLoc.X, gingLoc.Y].Text == null || StringSupport.equals(DataArray[gingLoc.X, gingLoc.Y].Text, ""))
        {
            DataArray[calLoc.X, calLoc.Y].Text = "";
            if (alsoInvalidate)
            {
                Invalidate(Rectangle.Ceiling(GetBounds(calLoc.X, calLoc.Y)));
            }
             
            return ;
        }
         
        int probValue = PIn.Int(DataArray[probingCell.X, probingCell.Y].Text);
        int gingValue = PIn.Int(DataArray[gingLoc.X, gingLoc.Y].Text);
        if (gingValue > 100)
        {
            gingValue = 100 - gingValue;
        }
         
        DataArray[calLoc.X, calLoc.Y].Text = (gingValue + probValue).ToString();
        if (alsoInvalidate)
        {
            Invalidate(Rectangle.Ceiling(GetBounds(calLoc.X, calLoc.Y)));
        }
         
    }

    private void setHasChanged(int col, int row) throws Exception {
        int section = getSection(row);
        int intTooth = (int)Math.Ceiling((double)col / 3);
        //1-16
        if (section == 2 || section == 3)
        {
            intTooth = 33 - intTooth;
        }
         
        int seqI = (int)RowTypes[section][getSectionRow(row)];
        HasChanged[seqI, intTooth] = true;
    }

    //MessageBox.Show(intTooth.ToString());
    /**
    * Used in OnMouseDown to change the currently selected cell.
    */
    private void setNewCell(int x, int y) throws Exception {
        //MessageBox.Show(x.ToString()+","+y.ToString());
        RectangleF oldRect = new Rectangle(0, 0, 0, 0);
        boolean invalidateOld = false;
        if (CurCell.X != -1)
        {
            oldRect = GetBounds(CurCell.X, CurCell.Y);
            invalidateOld = true;
        }
         
        CurCell = new Point(x, y);
        if (invalidateOld)
        {
            Invalidate(Rectangle.Ceiling(oldRect));
        }
         
        Invalidate(Rectangle.Ceiling(GetBounds(CurCell.X, CurCell.Y)));
    }

    private void advanceCell(boolean isReverse) throws Exception {
        PerioSequenceType seqType = RowTypes[GetSection(CurCell.Y)][GetSectionRow(CurCell.Y)];
        int newRow = 0;
        //used when jumping between sections
        int intTooth = 1;
        //used to test skipped teeth
        int section = 0;
        //used to test skipped teeth
        int newSection = 0;
        boolean locIsValid = false;
        //used when testing for skipped tooth and mobility location
        boolean startedOnSkipped = false;
        //special situation:
        intTooth = (int)Math.Ceiling((double)CurCell.X / 3);
        section = GetSection(CurCell.Y);
        newSection = section;
        //in case it doesn't change
        if (section == 2 || section == 3)
        {
            //if on mand
            intTooth = 33 - intTooth;
        }
         
        if (skippedTeeth.Contains(intTooth))
        {
            startedOnSkipped = true;
        }
         
        int limit = 0;
        while (limit < 400)
        {
            //the 400 limit is just a last resort. Should always break manually.
            limit++;
            //to the right
            section = GetSection(CurCell.Y);
            if ((!DirectionIsRight && isReverse) || (DirectionIsRight && !isReverse))
            {
                if (CurCell.X == 1)
                {
                    //if first column
                    if (section == 0)
                    {
                        return ;
                    }
                    else //usually in reverse
                    //no jump.  This is the starting point.
                    if (section == 1)
                    {
                        newSection = 3;
                        newRow = getTableRow(selectedExam,newSection,seqType);
                        onDirectionChangedLeft();
                    }
                    else if (section == 2)
                    {
                        return ;
                    }
                    else //no jump.  End of all sequences.
                    if (section == 3)
                    {
                        //usually in reverse
                        newSection = 1;
                        newRow = getTableRow(selectedExam,newSection,seqType);
                        if (newRow != -1)
                            onDirectionChangedRight();
                         
                    }
                        
                    if (newRow == -1)
                    {
                        //MGJ and mobility aren't present in all 4 sections, so can't loop normally
                        if (RowTypes[section][GetSectionRow(CurCell.Y)] == PerioSequenceType.Mobility)
                        {
                            if (section == 3)
                            {
                                //usually in reverse
                                newSection = 0;
                                setNewCell(1 + 16 * 3,getTableRow(selectedExam,newSection,PerioSequenceType.Mobility));
                            }
                             
                        }
                        else if (RowTypes[section][GetSectionRow(CurCell.Y)] == PerioSequenceType.MGJ)
                        {
                            //section 3. in reverse
                            newSection = 0;
                            setNewCell(16 * 3,getTableRow(selectedExam,newSection,PerioSequenceType.MGJ));
                        }
                          
                    }
                    else
                    {
                        SetNewCell(CurCell.X, newRow);
                    } 
                }
                else
                {
                    //standard advance with no jumping
                    SetNewCell(CurCell.X - 1, CurCell.Y);
                } 
            }
            else
            {
                //to the left
                //((DirectionIsRight && isBackspace) || !DirectionIsRight){
                if (CurCell.X == DataArray.GetLength(0) - 1)
                {
                    //if last column
                    if (section == 0)
                    {
                        newSection = 1;
                        newRow = getTableRow(selectedExam,newSection,seqType);
                        if (newRow != -1)
                            onDirectionChangedRight();
                         
                    }
                    else if (section == 1)
                    {
                        //usually in reverse
                        newSection = 0;
                        newRow = getTableRow(selectedExam,newSection,seqType);
                        onDirectionChangedLeft();
                    }
                    else if (section == 2)
                    {
                        //usually in reverse
                        newSection = 3;
                        newRow = getTableRow(selectedExam,newSection,seqType);
                        onDirectionChangedLeft();
                    }
                    else if (section == 3)
                    {
                        newSection = 2;
                        newRow = getTableRow(selectedExam,newSection,seqType);
                        if (newRow != -1)
                            onDirectionChangedRight();
                         
                    }
                        
                    if (newRow == -1)
                    {
                        //MGJ and mobility aren't present in all 4 sections, so can't loop normally
                        if (RowTypes[section][GetSectionRow(CurCell.Y)] == PerioSequenceType.Mobility)
                        {
                            if (section == 0)
                            {
                                newSection = 3;
                                setNewCell(1,getTableRow(selectedExam,newSection,PerioSequenceType.Mobility));
                            }
                             
                            if (section == 3)
                            {
                                return ;
                            }
                             
                        }
                        else //end of sequence
                        if (RowTypes[section][GetSectionRow(CurCell.Y)] == PerioSequenceType.MGJ)
                        {
                            //section 0
                            newSection = 3;
                            setNewCell(1,getTableRow(selectedExam,newSection,PerioSequenceType.MGJ));
                        }
                          
                    }
                    else
                    {
                        SetNewCell(CurCell.X, newRow);
                    } 
                }
                else
                {
                    //standard advance with no jumping
                    SetNewCell(CurCell.X + 1, CurCell.Y);
                } 
            } 
            if (startedOnSkipped)
                return ;
             
            //since we started on a skipped tooth
            //we can continue entry on a skipped tooth.
            intTooth = (int)Math.Ceiling((double)CurCell.X / 3);
            if (newSection == 2 || newSection == 3)
            {
                //if on mand
                intTooth = 33 - intTooth;
            }
             
            locIsValid = true;
            if (skippedTeeth.Contains(intTooth))
            {
                //if we are on a skipped tooth
                locIsValid = false;
            }
             
            //MessageBox.Show(GetSectionRow(CurCell.Y).ToString());
            if (RowTypes[newSection][GetSectionRow(CurCell.Y)] == PerioSequenceType.Mobility)
            {
                if (Math.IEEERemainder(((double)CurCell.X + 1), 3) != 0)
                {
                    //{2,5,8,11};examples of acceptable cols
                    locIsValid = false;
                }
                 
            }
             
            //for mobility, not allowed to click on anything but B
            if (locIsValid)
            {
                return ;
            }
             
        }
    }

    //otherwise, continue to loop in search of a valid loc
    //while
    private void advanceCell() throws Exception {
        advanceCell(false);
    }

    private void clearValue() throws Exception {
        //MessageBox.Show(DataArray.GetLength(0).ToString());
        //MessageBox.Show(DataArray.GetLength(1).ToString());
        PerioCell cur = DataArray[CurCell.X, CurCell.Y];
        cur.Text = "";
        DataArray[CurCell.X, CurCell.Y] = cur;
        SetHasChanged(CurCell.X, CurCell.Y);
        Invalidate(Rectangle.Ceiling(GetBounds(CurCell.X, CurCell.Y)));
        if (RowTypes[GetSection(CurCell.Y)][GetSectionRow(CurCell.Y)] == PerioSequenceType.Probing)
        {
            calculateCAL(CurCell,true);
        }
        else if (RowTypes[GetSection(CurCell.Y)][GetSectionRow(CurCell.Y)] == PerioSequenceType.GingMargin)
        {
            calculateCAL(new Point(CurCell.X, GetTableRow(selectedExam, GetSection(CurCell.Y), PerioSequenceType.Probing)),true);
        }
          
    }

    /**
    * 
    */
    public void toggleSkip(long perioExamNum) throws Exception {
        if (selectedTeeth.Count == 0)
        {
            MessageBox.Show(Lan.g(this,"Please select teeth first."));
            return ;
        }
         
        for (int i = 0;i < selectedTeeth.Count;i++)
        {
            if (skippedTeeth.Contains((int)selectedTeeth[i]))
            {
                //if the tooth was already marked skipped
                skippedTeeth.Remove((int)selectedTeeth[i]);
            }
            else
            {
                //tooth was not already marked skipped
                skippedTeeth.Add((int)selectedTeeth[i]);
            } 
        }
        PerioMeasures.setSkipped(perioExamNum,skippedTeeth);
        selectedTeeth = new ArrayList();
        Invalidate();
    }

    /**
    * 
    */
    protected void onDirectionChangedRight() throws Exception {
        if (DirectionChangedRight != null)
        {
            DirectionIsRight = true;
            EventArgs eArgs = new EventArgs();
            DirectionChangedRight(this, eArgs);
        }
         
    }

    /**
    * 
    */
    protected void onDirectionChangedLeft() throws Exception {
        if (DirectionChangedLeft != null)
        {
            DirectionIsRight = false;
            EventArgs eArgs = new EventArgs();
            DirectionChangedLeft(this, eArgs);
        }
         
    }

    /**
    * 
    */
    public String computeOrionPlaqueIndex() throws Exception {
        if (this.selectedExam == -1)
        {
            return "";
        }
         
        int counter = 0;
        List<PerioMeasure> pm = PerioMeasures.GetAllForExam(PerioExams.ListExams[selectedExam].PerioExamNum);
        for (int i = 0;i < pm.Count;i++)
        {
            if (pm[i].SequenceType == PerioSequenceType.Bleeding)
            {
                //If tooth has plaque on any of the six points
                if (((BleedingFlags)pm[i].MBvalue & BleedingFlags.Plaque) == BleedingFlags.Plaque)
                {
                    counter++;
                    continue;
                }
                 
                if (((BleedingFlags)pm[i].Bvalue & BleedingFlags.Plaque) == BleedingFlags.Plaque)
                {
                    counter++;
                    continue;
                }
                 
                if (((BleedingFlags)pm[i].DBvalue & BleedingFlags.Plaque) == BleedingFlags.Plaque)
                {
                    counter++;
                    continue;
                }
                 
                if (((BleedingFlags)pm[i].MLvalue & BleedingFlags.Plaque) == BleedingFlags.Plaque)
                {
                    counter++;
                    continue;
                }
                 
                if (((BleedingFlags)pm[i].Lvalue & BleedingFlags.Plaque) == BleedingFlags.Plaque)
                {
                    counter++;
                    continue;
                }
                 
                if (((BleedingFlags)pm[i].DLvalue & BleedingFlags.Plaque) == BleedingFlags.Plaque)
                {
                    counter++;
                    continue;
                }
                 
            }
             
        }
        if (counter == 0)
        {
            return (0).ToString("F0");
        }
         
        return (100 * counter / ((32 - skippedTeeth.Count))).ToString("F0");
    }

    /**
    * 
    */
    public String computeIndex(BleedingFlags bleedFlag) throws Exception {
        if (this.selectedExam == -1)
        {
            return "";
        }
         
        int counter = 0;
        for (int section = 0;section < 4;section++)
        {
            for (int x = 1;x < 1 + 3 * 16;x++)
            {
                if ((DataArray[x, getTableRow(selectedExam,section,PerioSequenceType.Probing)].Bleeding & bleedFlag) > 0)
                {
                    counter++;
                }
                 
            }
        }
        if (counter == 0)
        {
            return (0).ToString("F0");
        }
         
        return (100 * counter / ((32 - skippedTeeth.Count) * 6)).ToString("F0");
    }

    /**
    * Returns a list of strings, each between "1" and "32" (or similar international #'s), representing the teeth with red values based on prefs.  The result can be used to print summary, or can be counted to show # of teeth.
    */
    public ArrayList countTeeth(PerioSequenceType seqType) throws Exception {
        if (selectedExam == -1)
        {
            return new ArrayList();
        }
         
        int prefVal = 0;
        switch(seqType)
        {
            case Probing: 
                prefVal = PrefC.getInt(PrefName.PerioRedProb);
                break;
            case MGJ: 
                prefVal = PrefC.getInt(PrefName.PerioRedMGJ);
                break;
            case GingMargin: 
                prefVal = PrefC.getInt(PrefName.PerioRedGing);
                break;
            case CAL: 
                prefVal = PrefC.getInt(PrefName.PerioRedCAL);
                break;
            case Furcation: 
                prefVal = PrefC.getInt(PrefName.PerioRedFurc);
                break;
            case Mobility: 
                prefVal = PrefC.getInt(PrefName.PerioRedMob);
                break;
        
        }
        ArrayList retList = new ArrayList();
        String cellText = "";
        int intTooth = 0;
        int row = 0;
        for (int section = 0;section < 4;section++)
        {
            for (int x = 1;x < 1 + 3 * 16;x++)
            {
                row = getTableRow(selectedExam,section,seqType);
                if (row == -1)
                    continue;
                 
                //eg MGJ or Mobility
                cellText = DataArray[x, row].Text;
                if (cellText == null || StringSupport.equals(cellText, ""))
                {
                    continue;
                }
                 
                if ((seqType == PerioSequenceType.MGJ && PIn.Long(cellText) <= prefVal) || (seqType != PerioSequenceType.MGJ && PIn.Long(cellText) >= prefVal))
                {
                    intTooth = (int)Math.Ceiling((double)x / 3);
                    if (section == 2 || section == 3)
                    {
                        //if mand
                        intTooth = 33 - intTooth;
                    }
                     
                    if (!retList.Contains(Tooth.ToInternat(intTooth.ToString())))
                    {
                        retList.Add(Tooth.ToInternat(intTooth.ToString()));
                    }
                     
                }
                 
            }
        }
        return retList;
    }

    /**
    * Uses this control to draw onto the specified graphics object (the printer).
    */
    public void drawChart(Graphics g) throws Exception {
        PaintEventArgs e = new PaintEventArgs(g, ClientRectangle);
        g.Clear(Color.White);
        DrawBackground(e);
        DrawSkippedTeeth(e);
        //DrawSelectedTeeth(e);
        //DrawCurCell(e);
        DrawGridlines(e);
        DrawText(e);
    }

}


