//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import OpenDental.FormSheetDef;
import OpenDental.FormSheetFieldCheckBox;
import OpenDental.FormSheetFieldImage;
import OpenDental.FormSheetFieldInput;
import OpenDental.FormSheetFieldLine;
import OpenDental.FormSheetFieldOutput;
import OpenDental.FormSheetFieldRect;
import OpenDental.FormSheetFieldSigBox;
import OpenDental.FormSheetFieldSpecial;
import OpenDental.FormSheetFieldStatic;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.Properties.Resources;
import OpenDental.SheetUtil;
import OpenDentBusiness.OutInCheck;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetDefs;
import OpenDentBusiness.SheetFieldDef;
import OpenDentBusiness.SheetFieldsAvailable;
import OpenDentBusiness.SheetFieldType;
import OpenDentBusiness.SheetTypeEnum;

public class FormSheetDefEdit  extends Form 
{

    public SheetDef SheetDefCur;
    public boolean IsInternal = new boolean();
    private boolean MouseIsDown = new boolean();
    private boolean CtrlIsDown = new boolean();
    private Point MouseOriginalPos = new Point();
    private Point MouseCurrentPos = new Point();
    private List<Point> OriginalControlPositions = new List<Point>();
    /**
    * When you first mouse down, if you clicked on a valid control, this will be false.  For drag selection, this must be true.
    */
    private boolean ClickedOnBlankSpace = new boolean();
    private boolean AltIsDown = new boolean();
    /**
    * This is our 'clipboard' for copy/paste of fields.
    */
    private List<SheetFieldDef> ListSheetFieldDefsCopyPaste = new List<SheetFieldDef>();
    private int PasteOffset = 0;
    /**
    * After each 10 pastes to the upper left origin, this increments 10 to shift the next 10 down.
    */
    private int PasteOffsetY = 0;
    private boolean IsTabMode = new boolean();
    private List<SheetFieldDef> ListSheetFieldDefsTabOrder = new List<SheetFieldDef>();
    public static Font tabOrderFont = new Font("Times New Roman", 12f, FontStyle.Regular, GraphicsUnit.Pixel);
    private Bitmap BmBackground = new Bitmap();
    private Graphics GraphicsBackground = new Graphics();
    /**
    * This stores the previous calculations so that we don't have to recal unless certain things have changed.  The key is the index of the sheetfield.  The data is an array of objects of different types as seen in the code.
    */
    private Hashtable HashRtfStringCache = new Hashtable();
    /**
    * Some controls (panels in this case) do not pass key events to the parent (the form in this case) even when the property KeyPreview is set.  Instead, the default key functionality occurs.  An example would be the arrow keys.  By default, arrow keys set focus to the "next" control.  Instead, want all key presses on this form and all of it's child controls to always call the FormSheetDefEdit_KeyDown method.
    */
    protected boolean processCmdKey(RefSupport<Message> msg, Keys keyData) throws Exception {
        formSheetDefEdit_KeyDown(this,new KeyEventArgs(keyData));
        return true;
    }

    //This indicates that all keys have been processed.
    //return base.ProcessCmdKey(ref msg,keyData);//We don't need this right now, because no textboxes, for example.
    public FormSheetDefEdit(SheetDef sheetDef) throws Exception {
        initializeComponent();
        Lan.F(this);
        SheetDefCur = sheetDef;
        /*if(SheetDefCur.IsLandscape){
        				Width=SheetDefCur.Height+185;
        				Height=SheetDefCur.Width+60;
        			}
        			else{
        				Width=SheetDefCur.Width+185;
        				Height=SheetDefCur.Height+60;
        			}*/
        if (sheetDef.IsLandscape)
        {
            Width = sheetDef.Height + 190;
            Height = sheetDef.Width + 65;
        }
        else
        {
            Width = sheetDef.Width + 190;
            Height = sheetDef.Height + 65;
        } 
        if (Width < 600)
        {
            Width = 600;
        }
         
        if (Height < 600)
        {
            Height = 600;
        }
         
        if (Width > SystemInformation.WorkingArea.Width)
        {
            Width = SystemInformation.WorkingArea.Width;
        }
         
        if (Height > SystemInformation.WorkingArea.Height)
        {
            Height = SystemInformation.WorkingArea.Height;
        }
         
    }

    private void formSheetDefEdit_Load(Object sender, EventArgs e) throws Exception {
        if (IsInternal)
        {
            butDelete.Visible = false;
            butOK.Visible = false;
            butCancel.Text = Lan.g(this,"Close");
            groupAddNew.Visible = false;
            butAlignLeft.Visible = false;
            butAlignTop.Visible = false;
            linkLabelTips.Visible = false;
            butCopy.Visible = false;
            butPaste.Visible = false;
            butTabOrder.Visible = false;
        }
        else
        {
            labelInternal.Visible = false;
        } 
        //butAlignLeft.Visible=true;
        //butAlignTop.Visible=true;
        //butCopy.Visible=true;
        //butPaste.Visible=true;
        if (SheetDefCur.SheetType == SheetTypeEnum.DepositSlip || SheetDefCur.SheetType == SheetTypeEnum.LabelCarrier)
        {
            butAddSpecial.Enabled = false;
        }
         
        //grey out button
        textDescription.Text = SheetDefCur.Description;
        if (SheetDefCur.IsLandscape)
        {
            panelMain.Width = SheetDefCur.Height;
            panelMain.Height = SheetDefCur.Width;
        }
        else
        {
            panelMain.Width = SheetDefCur.Width;
            panelMain.Height = SheetDefCur.Height;
        } 
        fillFieldList();
        refreshDoubleBuffer();
        panelMain.Refresh();
        panelMain.Focus();
    }

    //textDescription.Focus();
    private void fillFieldList() throws Exception {
        listFields.Items.Clear();
        String txt = new String();
        SheetDefCur.SheetFieldDefs.Sort(CompareTabOrder);
        for (int i = 0;i < SheetDefCur.SheetFieldDefs.Count;i++)
        {
            if (SheetDefCur.SheetFieldDefs[i].FieldType == SheetFieldType.StaticText)
            {
                listFields.Items.Add(SheetDefCur.SheetFieldDefs[i].FieldValue);
            }
            else if (SheetDefCur.SheetFieldDefs[i].FieldType == SheetFieldType.Image)
            {
                listFields.Items.Add(Lan.g(this,"Image:") + SheetDefCur.SheetFieldDefs[i].FieldName);
            }
            else if (SheetDefCur.SheetFieldDefs[i].FieldType == SheetFieldType.Line)
            {
                listFields.Items.Add(Lan.g(this,"Line:") + SheetDefCur.SheetFieldDefs[i].XPos.ToString() + "," + SheetDefCur.SheetFieldDefs[i].YPos.ToString() + "," + "W:" + SheetDefCur.SheetFieldDefs[i].Width.ToString() + "," + "H:" + SheetDefCur.SheetFieldDefs[i].Height.ToString());
            }
            else if (SheetDefCur.SheetFieldDefs[i].FieldType == SheetFieldType.Rectangle)
            {
                listFields.Items.Add(Lan.g(this,"Rect:") + SheetDefCur.SheetFieldDefs[i].XPos.ToString() + "," + SheetDefCur.SheetFieldDefs[i].YPos.ToString() + "," + "W:" + SheetDefCur.SheetFieldDefs[i].Width.ToString() + "," + "H:" + SheetDefCur.SheetFieldDefs[i].Height.ToString());
            }
            else if (SheetDefCur.SheetFieldDefs[i].FieldType == SheetFieldType.SigBox)
            {
                listFields.Items.Add(Lan.g(this,"Signature Box"));
            }
            else if (SheetDefCur.SheetFieldDefs[i].FieldType == SheetFieldType.CheckBox)
            {
                txt = SheetDefCur.SheetFieldDefs[i].TabOrder.ToString() + ": ";
                if (SheetDefCur.SheetFieldDefs[i].FieldName.StartsWith("allergy:") || SheetDefCur.SheetFieldDefs[i].FieldName.StartsWith("problem:"))
                {
                    txt += SheetDefCur.SheetFieldDefs[i].FieldName.Remove(0, 8);
                }
                else
                {
                    txt += SheetDefCur.SheetFieldDefs[i].FieldName;
                } 
                if (!StringSupport.equals(SheetDefCur.SheetFieldDefs[i].RadioButtonValue, ""))
                {
                    txt += " - " + SheetDefCur.SheetFieldDefs[i].RadioButtonValue;
                }
                 
                listFields.Items.Add(txt);
            }
            else if (SheetDefCur.SheetFieldDefs[i].FieldType == SheetFieldType.InputField)
            {
                listFields.Items.Add(SheetDefCur.SheetFieldDefs[i].TabOrder.ToString() + ": " + SheetDefCur.SheetFieldDefs[i].FieldName);
            }
            else
            {
                listFields.Items.Add(SheetDefCur.SheetFieldDefs[i].FieldName);
            }       
            if (ListSheetFieldDefsCopyPaste != null)
            {
                for (int cp = 0;cp < ListSheetFieldDefsCopyPaste.Count;cp++)
                {
                    //reselect pasted controls
                    if (SheetDefCur.SheetFieldDefs[i].SheetFieldDefNum == ListSheetFieldDefsCopyPaste[cp].SheetFieldDefNum)
                    {
                        listFields.SetSelected(i, true);
                    }
                     
                }
            }
             
        }
    }

    //safe to run multiple times.
    /**
    * This is a comparator function used by List<T>.Sort()
    * When compairing SheetFieldDef.TabOrder it returns a negative number if def1<def2, 0 if def1==def2, and a positive number if def1>def2.
    * Does not handle null values, but there should never be any instances of null being passed in.
    * Must always return 0 when compairing item to itself.
    * This function should probably be moved to SheetFieldDefs.
    */
    private static int compareTabOrder(SheetFieldDef def1, SheetFieldDef def2) throws Exception {
        if (def1.FieldType == def2.FieldType)
        {
        }
        else //do nothing
        if (def1.FieldType == SheetFieldType.Image)
        {
            return -1;
        }
        else //Always move images to the top of the list. This is because of the way the sheet is drawn.
        if (def1.FieldType == SheetFieldType.Special)
        {
            return -1;
        }
        else //Move Special to the top of the list under special.
        if (def1.FieldType == SheetFieldType.OutputText)
        {
            return -1;
        }
            
        //Move Output text to the top of the list under images.
        if (def1.TabOrder - def2.TabOrder == 0)
        {
            int comp = (def1.FieldName + def1.RadioButtonValue).CompareTo(def2.FieldName + def2.RadioButtonValue);
            //RadioButtionValuecan be filled or ""
            if (comp != 0)
            {
                return comp;
            }
             
            comp = def1.YPos - def2.YPos;
            //arbitrarily order by YPos if both controls have the same tab orer and name. This will only happen if both fields are either identical or if they are both misc fields.
            if (comp != 0)
            {
                return comp;
            }
             
            return def1.XPos - def2.XPos;
        }
         
        return def1.TabOrder - def2.TabOrder;
    }

    //If tabOrder, Name, and YPos are equal then compare based on X coordinate.
    private void panelMain_Paint(Object sender, PaintEventArgs e) throws Exception {
        Bitmap doubleBuffer = new Bitmap(panelMain.Width, panelMain.Height);
        Graphics g = Graphics.FromImage(doubleBuffer);
        g.DrawImage(BmBackground, 0, 0);
        drawFields(g,false);
        e.Graphics.DrawImage(doubleBuffer, 0, 0);
        g.Dispose();
        doubleBuffer.Dispose();
        doubleBuffer = null;
    }

    /**
    * Whenever a user might have edited or moved a background image, this gets called.
    */
    private void refreshDoubleBuffer() throws Exception {
        GraphicsBackground.FillRectangle(Brushes.White, 0, 0, BmBackground.Width, BmBackground.Height);
        drawFields(GraphicsBackground,true);
    }

    /**
    * If drawImages is true then only image fields will be drawn. Otherwise, all fields but images will be drawn.
    */
    private void drawFields(Graphics g, boolean onlyDrawImages) throws Exception {
        g.SmoothingMode = SmoothingMode.HighQuality;
        g.CompositingQuality = CompositingQuality.HighQuality;
        //This has to be here or the line thicknesses are wrong.
        //g.InterpolationMode=InterpolationMode.High;//This doesn't seem to help
        Pen penBlue = new Pen(Color.Blue);
        Pen penRed = new Pen(Color.Red);
        Pen penBlueThick = new Pen(Color.Blue, 1.6f);
        Pen penRedThick = new Pen(Color.Red, 1.6f);
        Pen penBlack = new Pen(Color.Black);
        Pen penSelection = new Pen(Color.Black);
        Pen pen = new Pen();
        Brush brush = new Brush();
        SolidBrush brushBlue = new SolidBrush(Color.Blue);
        SolidBrush brushRed = new SolidBrush(Color.Red);
        Font font = new Font();
        FontStyle fontstyle = new FontStyle();
        for (int i = 0;i < SheetDefCur.SheetFieldDefs.Count;i++)
        {
            if (SheetDefCur.SheetFieldDefs[i].FieldType == SheetFieldType.Parameter)
            {
                continue;
            }
             
            if (SheetDefCur.SheetFieldDefs[i].FieldType == SheetFieldType.Image)
            {
                if (onlyDrawImages)
                {
                    String filePathAndName = CodeBase.ODFileUtils.CombinePaths(SheetUtil.getImagePath(), SheetDefCur.SheetFieldDefs[i].FieldName);
                    Image img = null;
                    if (StringSupport.equals(SheetDefCur.SheetFieldDefs[i].FieldName, "Patient Info.gif"))
                    {
                        img = Resources.getPatient_Info();
                    }
                    else if (File.Exists(filePathAndName))
                    {
                        img = Image.FromFile(filePathAndName);
                    }
                    else
                    {
                        continue;
                    }  
                    g.DrawImage(img, SheetDefCur.SheetFieldDefs[i].XPos, SheetDefCur.SheetFieldDefs[i].YPos, SheetDefCur.SheetFieldDefs[i].Width, SheetDefCur.SheetFieldDefs[i].Height);
                }
                 
                continue;
            }
             
            if (onlyDrawImages)
            {
                continue;
            }
             
            //Only draw the images for the background.
            if (SheetDefCur.SheetFieldDefs[i].FieldType == SheetFieldType.Line)
            {
                if (listFields.SelectedIndices.Contains(i))
                {
                    pen = penRed;
                }
                else
                {
                    pen = penBlack;
                } 
                g.DrawLine(pen, SheetDefCur.SheetFieldDefs[i].XPos, SheetDefCur.SheetFieldDefs[i].YPos, SheetDefCur.SheetFieldDefs[i].XPos + SheetDefCur.SheetFieldDefs[i].Width, SheetDefCur.SheetFieldDefs[i].YPos + SheetDefCur.SheetFieldDefs[i].Height);
                continue;
            }
             
            if (SheetDefCur.SheetFieldDefs[i].FieldType == SheetFieldType.Rectangle)
            {
                if (listFields.SelectedIndices.Contains(i))
                {
                    pen = penRed;
                }
                else
                {
                    pen = penBlack;
                } 
                g.DrawRectangle(pen, SheetDefCur.SheetFieldDefs[i].XPos, SheetDefCur.SheetFieldDefs[i].YPos, SheetDefCur.SheetFieldDefs[i].Width, SheetDefCur.SheetFieldDefs[i].Height);
                continue;
            }
             
            if (SheetDefCur.SheetFieldDefs[i].FieldType == SheetFieldType.CheckBox)
            {
                if (listFields.SelectedIndices.Contains(i))
                {
                    pen = penRedThick;
                }
                else
                {
                    pen = penBlueThick;
                } 
                //g.DrawRectangle(pen,SheetDefCur.SheetFieldDefs[i].XPos,SheetDefCur.SheetFieldDefs[i].YPos,
                //	SheetDefCur.SheetFieldDefs[i].Width,SheetDefCur.SheetFieldDefs[i].Height);
                g.DrawLine(pen, SheetDefCur.SheetFieldDefs[i].XPos, SheetDefCur.SheetFieldDefs[i].YPos, SheetDefCur.SheetFieldDefs[i].XPos + SheetDefCur.SheetFieldDefs[i].Width - 1, SheetDefCur.SheetFieldDefs[i].YPos + SheetDefCur.SheetFieldDefs[i].Height - 1);
                g.DrawLine(pen, SheetDefCur.SheetFieldDefs[i].XPos + SheetDefCur.SheetFieldDefs[i].Width - 1, SheetDefCur.SheetFieldDefs[i].YPos, SheetDefCur.SheetFieldDefs[i].XPos, SheetDefCur.SheetFieldDefs[i].YPos + SheetDefCur.SheetFieldDefs[i].Height - 1);
                if (IsTabMode)
                {
                    //X
                    //Y
                    Rectangle tabRect = new Rectangle(SheetDefCur.SheetFieldDefs[i].XPos - 1, SheetDefCur.SheetFieldDefs[i].YPos - 1, (int)g.MeasureString(SheetDefCur.SheetFieldDefs[i].TabOrder.ToString(), tabOrderFont).Width + 1, 12);
                    //Width
                    //height
                    if (ListSheetFieldDefsTabOrder.Contains(SheetDefCur.SheetFieldDefs[i]))
                    {
                        //blue border, white box, blue letters
                        g.FillRectangle(Brushes.White, tabRect);
                        g.DrawRectangle(Pens.Blue, tabRect);
                        g.DrawString(SheetDefCur.SheetFieldDefs[i].TabOrder.ToString(), tabOrderFont, Brushes.Blue, tabRect.X, tabRect.Y - 1);
                    }
                    else
                    {
                        //GraphicsHelper.DrawString(g,g,SheetDefCur.SheetFieldDefs[i].TabOrder.ToString(),SheetDefCur.GetFont(),Brushes.Blue,tabRect);
                        //Blue border, blue box, white letters
                        g.FillRectangle(brushBlue, tabRect);
                        g.DrawString(SheetDefCur.SheetFieldDefs[i].TabOrder.ToString(), tabOrderFont, Brushes.White, tabRect.X, tabRect.Y - 1);
                    } 
                }
                 
                continue;
            }
             
            //GraphicsHelper.DrawString(g,g,SheetDefCur.SheetFieldDefs[i].TabOrder.ToString(),SheetDefCur.GetFont(),Brushes.White,tabRect);
            if (SheetDefCur.SheetFieldDefs[i].FieldType == SheetFieldType.SigBox)
            {
                //font=new Font(Font,
                if (listFields.SelectedIndices.Contains(i))
                {
                    pen = penRed;
                    brush = brushRed;
                }
                else
                {
                    pen = penBlue;
                    brush = brushBlue;
                } 
                g.DrawRectangle(pen, SheetDefCur.SheetFieldDefs[i].XPos, SheetDefCur.SheetFieldDefs[i].YPos, SheetDefCur.SheetFieldDefs[i].Width, SheetDefCur.SheetFieldDefs[i].Height);
                g.DrawString("(signature box)", Font, brush, SheetDefCur.SheetFieldDefs[i].XPos, SheetDefCur.SheetFieldDefs[i].YPos);
                continue;
            }
             
            if (SheetDefCur.SheetFieldDefs[i].FieldType == SheetFieldType.Special)
            {
                //TODO:
                if (listFields.SelectedIndices.Contains(i))
                {
                    pen = penRed;
                    brush = brushRed;
                }
                else
                {
                    pen = penBlue;
                    brush = brushBlue;
                } 
                g.DrawRectangle(pen, SheetDefCur.SheetFieldDefs[i].XPos, SheetDefCur.SheetFieldDefs[i].YPos, SheetDefCur.SheetFieldDefs[i].Width, SheetDefCur.SheetFieldDefs[i].Height);
                g.DrawString("(Special:Tooth Grid)", Font, brush, SheetDefCur.SheetFieldDefs[i].XPos, SheetDefCur.SheetFieldDefs[i].YPos);
                continue;
            }
             
            fontstyle = FontStyle.Regular;
            if (SheetDefCur.SheetFieldDefs[i].FontIsBold)
            {
                fontstyle = FontStyle.Bold;
            }
             
            font = new Font(SheetDefCur.SheetFieldDefs[i].FontName, SheetDefCur.SheetFieldDefs[i].FontSize, fontstyle);
            if (listFields.SelectedIndices.Contains(i))
            {
                g.DrawRectangle(penRed, SheetDefCur.SheetFieldDefs[i].Bounds);
                brush = brushRed;
            }
            else
            {
                g.DrawRectangle(penBlue, SheetDefCur.SheetFieldDefs[i].Bounds);
                brush = brushBlue;
            } 
            String str = new String();
            if (SheetDefCur.SheetFieldDefs[i].FieldType == SheetFieldType.StaticText)
            {
                str = SheetDefCur.SheetFieldDefs[i].FieldValue;
            }
            else
            {
                //g.DrawString(SheetDefCur.SheetFieldDefs[i].FieldValue,font,
                //	brush,SheetDefCur.SheetFieldDefs[i].Bounds);
                str = SheetDefCur.SheetFieldDefs[i].FieldName;
            } 
            //g.DrawString(SheetDefCur.SheetFieldDefs[i].FieldName,font,
            //	brush,SheetDefCur.SheetFieldDefs[i].Bounds);
            if (ClickedOnBlankSpace)
            {
                //The math functions are used below to account for users clicking and dragging up, down, left, or right.
                //X
                //Y
                //Width
                g.DrawRectangle(penSelection, Math.Min(MouseOriginalPos.X, MouseCurrentPos.X), Math.Min(MouseOriginalPos.Y, MouseCurrentPos.Y), Math.Abs(MouseCurrentPos.X - MouseOriginalPos.X), Math.Abs(MouseCurrentPos.Y - MouseOriginalPos.Y));
            }
             
            //Height
            //g.DrawString(str,font,brush,SheetDefCur.SheetFieldDefs[i].Bounds);//This was drawing differently than in RichTextBox, so problems with large text.
            drawRTFstring(i,str,font,brush,g);
            //GraphicsHelper.DrawString(g,g,str,font,brush,SheetDefCur.SheetFieldDefs[i].Bounds);
            if (IsTabMode && SheetDefCur.SheetFieldDefs[i].FieldType == SheetFieldType.InputField)
            {
                //X
                //Y
                Rectangle tabRect = new Rectangle(SheetDefCur.SheetFieldDefs[i].XPos - 1, SheetDefCur.SheetFieldDefs[i].YPos - 1, (int)g.MeasureString(SheetDefCur.SheetFieldDefs[i].TabOrder.ToString(), tabOrderFont).Width + 1, 12);
                //Width
                //height
                if (ListSheetFieldDefsTabOrder.Contains(SheetDefCur.SheetFieldDefs[i]))
                {
                    //blue border, white box, blue letters
                    g.FillRectangle(Brushes.White, tabRect);
                    g.DrawRectangle(Pens.Blue, tabRect);
                    g.DrawString(SheetDefCur.SheetFieldDefs[i].TabOrder.ToString(), tabOrderFont, Brushes.Blue, tabRect.X, tabRect.Y - 1);
                }
                else
                {
                    //GraphicsHelper.DrawString(g,g,SheetDefCur.SheetFieldDefs[i].TabOrder.ToString(),SheetDefCur.GetFont(),Brushes.Blue,tabRect);
                    //Blue border, blue box, white letters
                    g.FillRectangle(brushBlue, tabRect);
                    g.DrawString(SheetDefCur.SheetFieldDefs[i].TabOrder.ToString(), tabOrderFont, Brushes.White, tabRect.X, tabRect.Y - 1);
                } 
            }
             
        }
    }

    //GraphicsHelper.DrawString(g,g,SheetDefCur.SheetFieldDefs[i].TabOrder.ToString(),SheetDefCur.GetFont(),Brushes.White,tabRect);
    /**
    * We need this special function to draw strings just like the RichTextBox control does, because sheet text is displayed using RichTextBoxes within FormSheetFillEdit.
    * Graphics.DrawString() uses a different font spacing than the RichTextBox control does.
    */
    private void drawRTFstring(int index, String str, Font font, Brush brush, Graphics g) throws Exception {
        str = str.Replace("\r", "");
        //For some reason '\r' throws off character position calculations.  \n still handles the CRs.
        SheetFieldDef field = SheetDefCur.SheetFieldDefs[index];
        //Font spacing is different for g.DrawString() as compared to RichTextBox and TextBox controls.
        //We create a RichTextBox here in the same manner as in FormSheetFillEdit, but we only use it to determine where to draw text.
        //We do not add the RichTextBox control to this form, because its background will overwrite everything behind that we have already drawn.
        boolean doCalc = true;
        Object[] data = (Object[])HashRtfStringCache[index.ToString()];
        if (data != null)
        {
            //That field has been calculated
            //If any of the following factors change, then that could potentially change text positions.
            //Has font name changed since last pass?
            //Has font size changed since last pass?
            //Has font boldness changed since last pass?
            //Has field width changed since last pass?
            //Has field height changed since last pass?
            if (field.FontName.CompareTo(data[1]) == 0 && field.FontSize.CompareTo(data[2]) == 0 && field.FontIsBold.CompareTo(data[3]) == 0 && field.Width.CompareTo(data[4]) == 0 && field.Height.CompareTo(data[5]) == 0 && str.CompareTo(data[6]) == 0)
            {
                //Has field text changed since last pass?
                doCalc = false;
            }
             
        }
         
        //Nothing has changed. Do not recalculate.
        if (doCalc)
        {
            //Data has not yet been cached for this text field, or the field has changed and needs to be recalculated.
            //All of these textbox fields are set using the same logic as in FormSheetFillEdit, so that text in this form matches exaclty.
            RichTextBox textbox = new RichTextBox();
            textbox.Visible = false;
            textbox.BorderStyle = BorderStyle.None;
            textbox.ScrollBars = RichTextBoxScrollBars.None;
            textbox.Location = new Point(field.XPos, field.YPos);
            textbox.Width = field.Width;
            textbox.Height = field.Height;
            textbox.Font = font;
            textbox.ForeColor = ((SolidBrush)brush).Color;
            if (field.Height < textbox.Font.Height + 2)
            {
                //Same logic as FormSheetFillEdit.
                textbox.Multiline = false;
            }
            else
            {
                textbox.Multiline = true;
            } 
            textbox.Text = str;
            Point[] positions = new Point[str.Length];
            for (int j = 0;j < str.Length;j++)
            {
                positions[j] = textbox.GetPositionFromCharIndex(j);
            }
            //This line is slow, so we try to minimize calling it by chaching positions each time there are changes.
            textbox.Dispose();
            data = new Object[]{ positions, field.FontName, field.FontSize, field.FontIsBold, field.Width, field.Height, str };
            HashRtfStringCache[index.ToString()] = data;
        }
         
        Point[] charPositions = (Point[])data[0];
        for (int j = 0;j < charPositions.Length;j++)
        {
            //This will draw text below the bottom line if the text is long. This is by design, so the user can see that the text is too big.
            g.DrawString(str.Substring(j, 1), font, brush, field.getBounds().X + charPositions[j].X, field.getBounds().Y + charPositions[j].Y);
        }
    }

    private void butEdit_Click(Object sender, EventArgs e) throws Exception {
        FormSheetDef FormS = new FormSheetDef();
        FormS.SheetDefCur = SheetDefCur;
        if (this.IsInternal)
        {
            FormS.IsReadOnly = true;
        }
         
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        textDescription.Text = SheetDefCur.Description;
        //resize
        if (SheetDefCur.IsLandscape)
        {
            panelMain.Width = SheetDefCur.Height;
            panelMain.Height = SheetDefCur.Width;
        }
        else
        {
            panelMain.Width = SheetDefCur.Width;
            panelMain.Height = SheetDefCur.Height;
        } 
        fillFieldList();
        refreshDoubleBuffer();
        panelMain.Refresh();
    }

    private void butAddOutputText_Click(Object sender, EventArgs e) throws Exception {
        if (SheetFieldsAvailable.getList(SheetDefCur.SheetType,OutInCheck.Out).Count == 0)
        {
            MsgBox.show(this,"There are no output fields available for this type of sheet.");
            return ;
        }
         
        Font font = new Font(SheetDefCur.FontName, SheetDefCur.FontSize);
        FormSheetFieldOutput FormS = new FormSheetFieldOutput();
        FormS.SheetDefCur = SheetDefCur;
        FormS.SheetFieldDefCur = SheetFieldDef.NewOutput("", SheetDefCur.FontSize, SheetDefCur.FontName, false, 0, 0, 100, font.Height);
        if (this.IsInternal)
        {
            FormS.IsReadOnly = true;
        }
         
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        SheetDefCur.SheetFieldDefs.Add(FormS.SheetFieldDefCur);
        fillFieldList();
        panelMain.Refresh();
    }

    private void butAddStaticText_Click(Object sender, EventArgs e) throws Exception {
        Font font = new Font(SheetDefCur.FontName, SheetDefCur.FontSize);
        FormSheetFieldStatic FormS = new FormSheetFieldStatic();
        FormS.SheetDefCur = SheetDefCur;
        FormS.SheetFieldDefCur = SheetFieldDef.NewStaticText("", SheetDefCur.FontSize, SheetDefCur.FontName, false, 0, 0, 100, font.Height);
        if (this.IsInternal)
        {
            FormS.IsReadOnly = true;
        }
         
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        SheetDefCur.SheetFieldDefs.Add(FormS.SheetFieldDefCur);
        fillFieldList();
        panelMain.Refresh();
    }

    private void butAddInputField_Click(Object sender, EventArgs e) throws Exception {
        if (SheetFieldsAvailable.getList(SheetDefCur.SheetType,OutInCheck.In).Count == 0)
        {
            MsgBox.show(this,"There are no input fields available for this type of sheet.");
            return ;
        }
         
        Font font = new Font(SheetDefCur.FontName, SheetDefCur.FontSize);
        FormSheetFieldInput FormS = new FormSheetFieldInput();
        FormS.SheetDefCur = SheetDefCur;
        FormS.SheetFieldDefCur = SheetFieldDef.NewInput("", SheetDefCur.FontSize, SheetDefCur.FontName, false, 0, 0, 100, font.Height);
        if (this.IsInternal)
        {
            FormS.IsReadOnly = true;
        }
         
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        SheetDefCur.SheetFieldDefs.Add(FormS.SheetFieldDefCur);
        fillFieldList();
        panelMain.Refresh();
    }

    private void butAddImage_Click(Object sender, EventArgs e) throws Exception {
        if (!PrefC.getAtoZfolderUsed())
        {
            MsgBox.show(this,"Not allowed because not using AtoZ folder");
            return ;
        }
         
        //Font font=new Font(SheetDefCur.FontName,SheetDefCur.FontSize);
        FormSheetFieldImage FormS = new FormSheetFieldImage();
        FormS.SheetDefCur = SheetDefCur;
        FormS.SheetFieldDefCur = SheetFieldDef.newImage("",0,0,100,100);
        if (this.IsInternal)
        {
            FormS.IsReadOnly = true;
        }
         
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        SheetDefCur.SheetFieldDefs.Insert(0, FormS.SheetFieldDefCur);
        fillFieldList();
        refreshDoubleBuffer();
        panelMain.Refresh();
    }

    private void butAddLine_Click(Object sender, EventArgs e) throws Exception {
        FormSheetFieldLine FormS = new FormSheetFieldLine();
        FormS.SheetDefCur = SheetDefCur;
        FormS.SheetFieldDefCur = SheetFieldDef.newLine(0,0,0,0);
        if (this.IsInternal)
        {
            FormS.IsReadOnly = true;
        }
         
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        SheetDefCur.SheetFieldDefs.Add(FormS.SheetFieldDefCur);
        fillFieldList();
        panelMain.Refresh();
    }

    private void butAddRect_Click(Object sender, EventArgs e) throws Exception {
        FormSheetFieldRect FormS = new FormSheetFieldRect();
        FormS.SheetDefCur = SheetDefCur;
        FormS.SheetFieldDefCur = SheetFieldDef.newRect(0,0,0,0);
        if (this.IsInternal)
        {
            FormS.IsReadOnly = true;
        }
         
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        SheetDefCur.SheetFieldDefs.Add(FormS.SheetFieldDefCur);
        fillFieldList();
        panelMain.Refresh();
    }

    private void butAddCheckBox_Click(Object sender, EventArgs e) throws Exception {
        if (SheetFieldsAvailable.getList(SheetDefCur.SheetType,OutInCheck.Check).Count == 0)
        {
            MsgBox.show(this,"There are no checkbox fields available for this type of sheet.");
            return ;
        }
         
        FormSheetFieldCheckBox FormS = new FormSheetFieldCheckBox();
        FormS.IsNew = true;
        FormS.SheetDefCur = SheetDefCur;
        FormS.SheetFieldDefCur = SheetFieldDef.newCheckBox("",0,0,11,11);
        if (this.IsInternal)
        {
            FormS.IsReadOnly = true;
        }
         
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        SheetDefCur.SheetFieldDefs.Add(FormS.SheetFieldDefCur);
        fillFieldList();
        panelMain.Refresh();
    }

    private void butAddSigBox_Click(Object sender, EventArgs e) throws Exception {
        FormSheetFieldSigBox FormS = new FormSheetFieldSigBox();
        FormS.SheetDefCur = SheetDefCur;
        FormS.SheetFieldDefCur = SheetFieldDef.newSigBox(0,0,364,81);
        if (this.IsInternal)
        {
            FormS.IsReadOnly = true;
        }
         
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        SheetDefCur.SheetFieldDefs.Add(FormS.SheetFieldDefCur);
        fillFieldList();
        panelMain.Refresh();
    }

    private void butAddSpecial_Click(Object sender, EventArgs e) throws Exception {
        FormSheetFieldSpecial FormSFS = new FormSheetFieldSpecial();
        FormSFS.SheetDefCur = SheetDefCur;
        FormSFS.SheetFieldDefCur = SheetFieldDef.newSpecial(0,0,500,300);
        if (this.IsInternal)
        {
            FormSFS.IsReadOnly = true;
        }
         
        FormSFS.ShowDialog();
        if (FormSFS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        SheetDefCur.SheetFieldDefs.Add(FormSFS.SheetFieldDefCur);
        fillFieldList();
        panelMain.Refresh();
    }

    private void butAddPatImage_Click(Object sender, EventArgs e) throws Exception {
        if (!PrefC.getAtoZfolderUsed())
        {
            MsgBox.show(this,"Not allowed because not using AtoZ folder");
            return ;
        }
         
        //Font font=new Font(SheetDefCur.FontName,SheetDefCur.FontSize);
        FormSheetFieldImage FormS = new FormSheetFieldImage();
        FormS.SheetDefCur = SheetDefCur;
        FormS.SheetFieldDefCur = SheetFieldDef.newImage("",0,0,100,100);
        if (this.IsInternal)
        {
            FormS.IsReadOnly = true;
        }
         
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        SheetDefCur.SheetFieldDefs.Insert(0, FormS.SheetFieldDefCur);
        fillFieldList();
        panelMain.Refresh();
    }

    private void listFields_Click(Object sender, EventArgs e) throws Exception {
        //if(listFields.SelectedIndices.Count==0){
        //	return;
        //}
        panelMain.Refresh();
    }

    private void listFields_MouseDoubleClick(Object sender, MouseEventArgs e) throws Exception {
        int idx = listFields.IndexFromPoint(e.Location);
        if (idx == -1)
        {
            return ;
        }
         
        listFields.SelectedIndices.Clear();
        listFields.SetSelected(idx, true);
        panelMain.Refresh();
        SheetFieldDef field = SheetDefCur.SheetFieldDefs[idx];
        SheetFieldDef fieldold = field.copy();
        launchEditWindow(field);
        if (field.TabOrder != fieldold.TabOrder)
        {
            //otherwise a different control will be selected.
            listFields.SelectedIndices.Clear();
        }
         
    }

    /**
    * Only for editing fields that already exist.
    */
    private void launchEditWindow(SheetFieldDef field) throws Exception {
        boolean refreshBuffer = false;
        //not every field will have been saved to the database, so we can't depend on SheetFieldDefNum.
        int idx = SheetDefCur.SheetFieldDefs.IndexOf(field);
        switch(field.FieldType)
        {
            case InputField: 
                FormSheetFieldInput FormS = new FormSheetFieldInput();
                FormS.SheetDefCur = SheetDefCur;
                FormS.SheetFieldDefCur = field;
                if (this.IsInternal)
                {
                    FormS.IsReadOnly = true;
                }
                 
                FormS.ShowDialog();
                if (FormS.DialogResult != DialogResult.OK)
                {
                    return ;
                }
                 
                if (FormS.SheetFieldDefCur == null)
                {
                    SheetDefCur.SheetFieldDefs.RemoveAt(idx);
                }
                 
                break;
            case OutputText: 
                FormSheetFieldOutput FormSO = new FormSheetFieldOutput();
                FormSO.SheetDefCur = SheetDefCur;
                FormSO.SheetFieldDefCur = field;
                if (this.IsInternal)
                {
                    FormSO.IsReadOnly = true;
                }
                 
                FormSO.ShowDialog();
                if (FormSO.DialogResult != DialogResult.OK)
                {
                    return ;
                }
                 
                if (FormSO.SheetFieldDefCur == null)
                {
                    SheetDefCur.SheetFieldDefs.RemoveAt(idx);
                }
                 
                break;
            case StaticText: 
                FormSheetFieldStatic FormSS = new FormSheetFieldStatic();
                FormSS.SheetDefCur = SheetDefCur;
                FormSS.SheetFieldDefCur = field;
                if (this.IsInternal)
                {
                    FormSS.IsReadOnly = true;
                }
                 
                FormSS.ShowDialog();
                if (FormSS.DialogResult != DialogResult.OK)
                {
                    return ;
                }
                 
                if (FormSS.SheetFieldDefCur == null)
                {
                    SheetDefCur.SheetFieldDefs.RemoveAt(idx);
                }
                 
                break;
            case Image: 
                FormSheetFieldImage FormSI = new FormSheetFieldImage();
                FormSI.SheetDefCur = SheetDefCur;
                FormSI.SheetFieldDefCur = field;
                if (this.IsInternal)
                {
                    FormSI.IsReadOnly = true;
                }
                 
                FormSI.ShowDialog();
                if (FormSI.DialogResult != DialogResult.OK)
                {
                    return ;
                }
                 
                if (FormSI.SheetFieldDefCur == null)
                {
                    SheetDefCur.SheetFieldDefs.RemoveAt(idx);
                }
                 
                refreshBuffer = true;
                break;
            case Line: 
                FormSheetFieldLine FormSL = new FormSheetFieldLine();
                FormSL.SheetDefCur = SheetDefCur;
                FormSL.SheetFieldDefCur = field;
                if (this.IsInternal)
                {
                    FormSL.IsReadOnly = true;
                }
                 
                FormSL.ShowDialog();
                if (FormSL.DialogResult != DialogResult.OK)
                {
                    return ;
                }
                 
                if (FormSL.SheetFieldDefCur == null)
                {
                    SheetDefCur.SheetFieldDefs.RemoveAt(idx);
                }
                 
                break;
            case Rectangle: 
                FormSheetFieldRect FormSR = new FormSheetFieldRect();
                FormSR.SheetDefCur = SheetDefCur;
                FormSR.SheetFieldDefCur = field;
                if (this.IsInternal)
                {
                    FormSR.IsReadOnly = true;
                }
                 
                FormSR.ShowDialog();
                if (FormSR.DialogResult != DialogResult.OK)
                {
                    return ;
                }
                 
                if (FormSR.SheetFieldDefCur == null)
                {
                    SheetDefCur.SheetFieldDefs.RemoveAt(idx);
                }
                 
                break;
            case CheckBox: 
                FormSheetFieldCheckBox FormSB = new FormSheetFieldCheckBox();
                FormSB.SheetDefCur = SheetDefCur;
                FormSB.SheetFieldDefCur = field;
                if (this.IsInternal)
                {
                    FormSB.IsReadOnly = true;
                }
                 
                FormSB.ShowDialog();
                if (FormSB.DialogResult != DialogResult.OK)
                {
                    return ;
                }
                 
                if (FormSB.SheetFieldDefCur == null)
                {
                    SheetDefCur.SheetFieldDefs.RemoveAt(idx);
                }
                 
                break;
            case SigBox: 
                FormSheetFieldSigBox FormSBx = new FormSheetFieldSigBox();
                FormSBx.SheetDefCur = SheetDefCur;
                FormSBx.SheetFieldDefCur = field;
                if (this.IsInternal)
                {
                    FormSBx.IsReadOnly = true;
                }
                 
                FormSBx.ShowDialog();
                if (FormSBx.DialogResult != DialogResult.OK)
                {
                    return ;
                }
                 
                if (FormSBx.SheetFieldDefCur == null)
                {
                    SheetDefCur.SheetFieldDefs.RemoveAt(idx);
                }
                 
                break;
            case Special: 
                FormSheetFieldSpecial FormSFS = new FormSheetFieldSpecial();
                FormSFS.SheetDefCur = SheetDefCur;
                FormSFS.SheetFieldDefCur = field;
                if (this.IsInternal)
                {
                    FormSFS.IsReadOnly = true;
                }
                 
                FormSFS.ShowDialog();
                if (FormSFS.DialogResult != DialogResult.OK)
                {
                    return ;
                }
                 
                if (FormSFS.SheetFieldDefCur == null)
                {
                    SheetDefCur.SheetFieldDefs.RemoveAt(idx);
                }
                 
                break;
        
        }
        if (IsTabMode)
        {
            if (ListSheetFieldDefsTabOrder.Contains(field))
            {
                ListSheetFieldDefsTabOrder.RemoveAt(ListSheetFieldDefsTabOrder.IndexOf(field));
            }
             
            if (field.TabOrder > 0 && field.TabOrder <= (ListSheetFieldDefsTabOrder.Count + 1))
            {
                ListSheetFieldDefsTabOrder.Insert(field.TabOrder - 1, field);
            }
             
            renumberTabOrderHelper();
            return ;
        }
         
        fillFieldList();
        if (refreshBuffer)
        {
            //Only when image was edited.
            refreshDoubleBuffer();
        }
         
        if (listFields.Items.Count - 1 >= idx)
        {
            listFields.SelectedIndex = idx;
        }
         
        //reselect the item.
        panelMain.Refresh();
    }

    private void panelMain_MouseDown(Object sender, MouseEventArgs e) throws Exception {
        panel1.Select();
        if (AltIsDown)
        {
            PasteControlsFromMemory(e.Location);
            return ;
        }
         
        MouseIsDown = true;
        ClickedOnBlankSpace = false;
        MouseOriginalPos = e.Location;
        MouseCurrentPos = e.Location;
        SheetFieldDef field = HitTest(e.X, e.Y);
        if (IsTabMode)
        {
            MouseIsDown = false;
            CtrlIsDown = false;
            AltIsDown = false;
            //Some of the fields below are redundant and should never be returned from HitTest but are here to explicity exclude them.
            if (field == null || field.FieldType == SheetFieldType.Drawing || field.FieldType == SheetFieldType.Image || field.FieldType == SheetFieldType.Line || field.FieldType == SheetFieldType.OutputText || field.FieldType == SheetFieldType.Parameter || field.FieldType == SheetFieldType.PatImage || field.FieldType == SheetFieldType.Rectangle || field.FieldType == SheetFieldType.StaticText)
            {
                return ;
            }
             
            if (ListSheetFieldDefsTabOrder.Contains(field))
            {
                field.TabOrder = 0;
                ListSheetFieldDefsTabOrder.RemoveAt(ListSheetFieldDefsTabOrder.IndexOf(field));
            }
            else
            {
                ListSheetFieldDefsTabOrder.Add(field);
            } 
            renumberTabOrderHelper();
            return ;
        }
         
        if (field == null)
        {
            ClickedOnBlankSpace = true;
            if (CtrlIsDown)
            {
                return ;
            }
             
            //so that you can add more to the previous selection
            listFields.SelectedIndices.Clear();
            //clear the existing selection
            panelMain.Refresh();
            return ;
        }
         
        int idx = SheetDefCur.SheetFieldDefs.IndexOf(field);
        if (CtrlIsDown)
        {
            if (listFields.SelectedIndices.Contains(idx))
            {
                listFields.SetSelected(idx, false);
            }
            else
            {
                listFields.SetSelected(idx, true);
            } 
        }
        else
        {
            //Ctrl not down
            if (listFields.SelectedIndices.Contains(idx))
            {
            }
            else
            {
                //clicking on the group, probably to start a drag.
                listFields.SelectedIndices.Clear();
                listFields.SetSelected(idx, true);
            } 
        } 
        OriginalControlPositions = new List<Point>();
        Point point = new Point();
        for (int i = 0;i < listFields.SelectedIndices.Count;i++)
        {
            point = new Point(SheetDefCur.SheetFieldDefs[listFields.SelectedIndices[i]].XPos, SheetDefCur.SheetFieldDefs[listFields.SelectedIndices[i]].YPos);
            OriginalControlPositions.Add(point);
        }
        panelMain.Refresh();
    }

    private void panelMain_MouseMove(Object sender, MouseEventArgs e) throws Exception {
        if (!MouseIsDown)
        {
            return ;
        }
         
        if (IsInternal)
        {
            return ;
        }
         
        if (IsTabMode)
        {
            return ;
        }
         
        if (ClickedOnBlankSpace)
        {
            MouseCurrentPos = e.Location;
            panelMain.Refresh();
            return ;
        }
         
        for (int i = 0;i < listFields.SelectedIndices.Count;i++)
        {
            SheetDefCur.SheetFieldDefs[listFields.SelectedIndices[i]].XPos = OriginalControlPositions[i].X + e.X - MouseOriginalPos.X;
            SheetDefCur.SheetFieldDefs[listFields.SelectedIndices[i]].YPos = OriginalControlPositions[i].Y + e.Y - MouseOriginalPos.Y;
        }
        panelMain.Refresh();
    }

    private void panelMain_MouseUp(Object sender, MouseEventArgs e) throws Exception {
        MouseIsDown = false;
        OriginalControlPositions = null;
        if (ClickedOnBlankSpace)
        {
            //if initial mouse down was not on a control.  ie, if we are dragging to select.
            //X
            //Y
            //Width
            Rectangle selectionBounds = new Rectangle(Math.Min(MouseOriginalPos.X, MouseCurrentPos.X), Math.Min(MouseOriginalPos.Y, MouseCurrentPos.Y), Math.Abs(MouseCurrentPos.X - MouseOriginalPos.X), Math.Abs(MouseCurrentPos.Y - MouseOriginalPos.Y));
            for (int i = 0;i < SheetDefCur.SheetFieldDefs.Count;i++)
            {
                //Height
                SheetFieldDef tempDef = SheetDefCur.SheetFieldDefs[i];
                //to speed this process up instead of referencing the array every time.
                if (tempDef.FieldType == SheetFieldType.Line || tempDef.FieldType == SheetFieldType.Image)
                {
                    continue;
                }
                 
                //lines and images are currently not selectable by drag and drop. will require lots of calculations, completely possible, but complex.
                //If the selection is contained within the "hollow" portion of the rectangle, it shouldn't be selected.
                if (tempDef.FieldType == SheetFieldType.Rectangle)
                {
                    Rectangle tempDefBounds = new Rectangle(tempDef.getBounds().X + 4, tempDef.getBounds().Y + 4, tempDef.getBounds().Width - 8, tempDef.getBounds().Height - 8);
                    if (tempDefBounds.Contains(selectionBounds))
                    {
                        continue;
                    }
                     
                }
                 
                if (tempDef.getBoundsF().IntersectsWith(selectionBounds))
                {
                    listFields.SetSelected(i, true);
                }
                 
            }
        }
         
        //Add to selected indicies
        ClickedOnBlankSpace = false;
        panelMain.Refresh();
    }

    private void panelMain_MouseDoubleClick(Object sender, MouseEventArgs e) throws Exception {
        if (AltIsDown)
        {
            return ;
        }
         
        SheetFieldDef field = HitTest(e.X, e.Y);
        if (field == null)
        {
            return ;
        }
         
        SheetFieldDef fieldold = field.copy();
        launchEditWindow(field);
    }

    //if(field.TabOrder!=fieldold.TabOrder) {
    //  listFields.SelectedIndices.Clear();
    //}
    //if(isTabMode) {
    //  if(ListSheetFieldDefsTabOrder.Contains(field)){
    //    ListSheetFieldDefsTabOrder.RemoveAt(ListSheetFieldDefsTabOrder.IndexOf(field));
    //  }
    //  if(field.TabOrder>0 && field.TabOrder<ListSheetFieldDefsTabOrder.Count+1) {
    //    ListSheetFieldDefsTabOrder.Insert(field.TabOrder-1,field);
    //  }
    //  RenumberTabOrderHelper();
    //}
    private void panelMain_Resize(Object sender, EventArgs e) throws Exception {
        if (BmBackground != null && panelMain.Size == BmBackground.Size)
        {
            return ;
        }
         
        if (GraphicsBackground != null)
        {
            GraphicsBackground.Dispose();
        }
         
        if (BmBackground != null)
        {
            BmBackground.Dispose();
        }
         
        BmBackground = new Bitmap(panelMain.Width, panelMain.Height);
        GraphicsBackground = Graphics.FromImage(BmBackground);
        panelMain.Refresh();
    }

    /**
    * Used To renumber TabOrder on controls
    */
    private void renumberTabOrderHelper() throws Exception {
        for (int i = 0;i < ListSheetFieldDefsTabOrder.Count;i++)
        {
            ListSheetFieldDefsTabOrder[i].TabOrder = i + 1;
        }
        //Start number tab order at 1
        fillFieldList();
        panelMain.Refresh();
    }

    /**
    * Images will be ignored in the hit test since they frequently fill the entire background.  Lines will be ignored too, since a diagonal line could fill a large area.
    */
    private SheetFieldDef hitTest(int x, int y) throws Exception {
        for (int i = 0;i < SheetDefCur.SheetFieldDefs.Count;i++)
        {
            if (SheetDefCur.SheetFieldDefs[i].FieldType == SheetFieldType.Image)
            {
                continue;
            }
             
            if (SheetDefCur.SheetFieldDefs[i].FieldType == SheetFieldType.Line)
            {
                continue;
            }
             
            Rectangle fieldDefBounds = SheetDefCur.SheetFieldDefs[i].Bounds;
            if (fieldDefBounds.Contains(x, y))
            {
                //Center of the rectangle will not be considered a hit.
                if (SheetDefCur.SheetFieldDefs[i].FieldType == SheetFieldType.Rectangle && (new Rectangle(fieldDefBounds.X + 4, fieldDefBounds.Y + 4, fieldDefBounds.Width - 8, fieldDefBounds.Height - 8)).Contains(x, y))
                {
                    continue;
                }
                 
                return SheetDefCur.SheetFieldDefs[i];
            }
             
        }
        return null;
    }

    private void formSheetDefEdit_KeyDown(Object sender, KeyEventArgs e) throws Exception {
        boolean refreshBuffer = false;
        e.Handled = true;
        if (e.KeyCode == Keys.ControlKey && CtrlIsDown)
        {
            return ;
        }
         
        if (e.Control)
        {
            CtrlIsDown = true;
        }
         
        if (CtrlIsDown && e.KeyCode == Keys.C)
        {
            //CTRL-C
            copyControlsToMemory();
        }
        else if (CtrlIsDown && e.KeyCode == Keys.V)
        {
            //CTRL-V
            pasteControlsFromMemory(new Point(0, 0));
        }
        else if (e.Alt)
        {
            Cursor = Cursors.Cross;
            //change cursor to rubber stamp cursor
            AltIsDown = true;
        }
        else if (e.KeyCode == Keys.Delete || e.KeyCode == Keys.Back)
        {
            if (listFields.SelectedIndices.Count == 0)
            {
                return ;
            }
             
            if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Delete selected fields?"))
            {
                return ;
            }
             
            for (int i = listFields.SelectedIndices.Count - 1;i >= 0;i--)
            {
                //iterate backwards through list
                if (SheetDefCur.SheetFieldDefs[listFields.SelectedIndices[i]].FieldType == SheetFieldType.Image)
                {
                    refreshBuffer = true;
                }
                 
                SheetDefCur.SheetFieldDefs.RemoveAt(listFields.SelectedIndices[i]);
            }
            fillFieldList();
        }
            
        for (int i = 0;i < listFields.SelectedIndices.Count;i++)
        {
            if (SheetDefCur.SheetFieldDefs[listFields.SelectedIndices[i]].FieldType == SheetFieldType.Image)
            {
                refreshBuffer = true;
            }
             
            KeyCode __dummyScrutVar1 = e.KeyCode;
            if (__dummyScrutVar1.equals(Keys.Up))
            {
                if (e.Shift)
                    SheetDefCur.SheetFieldDefs[listFields.SelectedIndices[i]].YPos -= 7;
                else
                    SheetDefCur.SheetFieldDefs[listFields.SelectedIndices[i]].YPos--; 
            }
            else if (__dummyScrutVar1.equals(Keys.Down))
            {
                if (e.Shift)
                    SheetDefCur.SheetFieldDefs[listFields.SelectedIndices[i]].YPos += 7;
                else
                    SheetDefCur.SheetFieldDefs[listFields.SelectedIndices[i]].YPos++; 
            }
            else if (__dummyScrutVar1.equals(Keys.Left))
            {
                if (e.Shift)
                    SheetDefCur.SheetFieldDefs[listFields.SelectedIndices[i]].XPos -= 7;
                else
                    SheetDefCur.SheetFieldDefs[listFields.SelectedIndices[i]].XPos--; 
            }
            else if (__dummyScrutVar1.equals(Keys.Right))
            {
                if (e.Shift)
                    SheetDefCur.SheetFieldDefs[listFields.SelectedIndices[i]].XPos += 7;
                else
                    SheetDefCur.SheetFieldDefs[listFields.SelectedIndices[i]].XPos++; 
            }
            else
            {
            }    
        }
        if (refreshBuffer)
        {
            //Only when an image was selected.
            refreshDoubleBuffer();
        }
         
        panelMain.Refresh();
    }

    private void formSheetDefEdit_KeyUp(Object sender, KeyEventArgs e) throws Exception {
        if ((e.KeyCode & Keys.ControlKey) == Keys.ControlKey)
        {
            CtrlIsDown = false;
        }
         
        if (!e.Alt)
        {
            Cursor = Cursors.Default;
            AltIsDown = false;
        }
         
    }

    private void copyControlsToMemory() throws Exception {
        if (IsTabMode)
        {
            return ;
        }
         
        if (listFields.SelectedIndices.Count == 0)
        {
            return ;
        }
         
        //List<SheetFieldDef> listDuplicates=new List<SheetFieldDef>();
        String strPrompt = Lan.g(this,"The following selected fields can cause conflicts if they are copied:\r\n");
        boolean conflictingfield = false;
        for (int i = 0;i < listFields.SelectedIndices.Count;i++)
        {
            SheetFieldDef fielddef = SheetDefCur.SheetFieldDefs[listFields.SelectedIndices[i]];
            switch(fielddef.FieldType)
            {
                case Drawing: 
                case Image: 
                case Line: 
                case PatImage: 
                case Rectangle: 
                case SigBox: 
                case StaticText: 
                    break;
                case CheckBox: 
                    //case SheetFieldType.Parameter://would not be seen on the sheet.
                    //it will always be ok to copy the types of fields above.
                    if (!StringSupport.equals(fielddef.FieldName, "misc"))
                    {
                        //custom fields should be okay to copy
                        strPrompt += fielddef.FieldName + "." + fielddef.RadioButtonValue + "\r\n";
                        conflictingfield = true;
                    }
                     
                    break;
                case InputField: 
                case OutputText: 
                    if (!StringSupport.equals(fielddef.FieldName, "misc"))
                    {
                        //custom fields should be okay to copy
                        strPrompt += fielddef.FieldName + "\r\n";
                        conflictingfield = true;
                    }
                     
                    break;
            
            }
        }
        strPrompt += Lan.g(this,"Would you like to continue anyways?");
        if (conflictingfield && MessageBox.Show(strPrompt, Lan.g(this,"Warning"), MessageBoxButtons.OKCancel) != DialogResult.OK)
        {
            panel1.Select();
            CtrlIsDown = false;
            return ;
        }
         
        ListSheetFieldDefsCopyPaste = new List<SheetFieldDef>();
        for (int i = 0;i < listFields.SelectedIndices.Count;i++)
        {
            //empty the remembered field list
            ListSheetFieldDefsCopyPaste.Add(SheetDefCur.SheetFieldDefs[listFields.SelectedIndices[i]].Copy());
        }
        //fill clipboard with copies of the controls.
        //It would probably be safe to fill the clipboard with the originals. but it is safer to fill it with copies.
        PasteOffset = 0;
        PasteOffsetY = 0;
    }

    //reset PasteOffset for pasting a new set of fields.
    private void pasteControlsFromMemory(Point origin) throws Exception {
        if (IsTabMode)
        {
            return ;
        }
         
        if (ListSheetFieldDefsCopyPaste == null || ListSheetFieldDefsCopyPaste.Count == 0)
        {
            return ;
        }
         
        if (origin.X == 0 && origin.Y == 0)
        {
            //allows for cascading pastes in the upper right hand corner.
            Rectangle r = panelMain.Bounds;
            //Gives relative position of panel (scroll position)
            int h = panel1.Height;
            //Current resized height/width of parent panel
            int w = panel1.Width;
            int maxH = 0;
            int maxW = 0;
            for (int i = 0;i < ListSheetFieldDefsCopyPaste.Count;i++)
            {
                //calculate height/width of control to be pasted
                maxH = Math.Max(maxH, ListSheetFieldDefsCopyPaste[i].Height);
                maxW = Math.Max(maxW, ListSheetFieldDefsCopyPaste[i].Width);
            }
            origin = new Point((-1) * r.X + w / 2 - maxW / 2 - 10, (-1) * r.Y + h / 2 - maxH / 2 - 10);
            //Center: scroll position * (-1) + 1/2 size of window - 1/2 the size of the field - 10 for scroll bar
            origin.X += PasteOffset;
            origin.Y += PasteOffset + PasteOffsetY;
        }
         
        listFields.ClearSelected();
        int minX = int.MaxValue;
        int minY = int.MaxValue;
        for (int i = 0;i < ListSheetFieldDefsCopyPaste.Count;i++)
        {
            //calculate offset
            minX = Math.Min(minX, ListSheetFieldDefsCopyPaste[i].XPos);
            minY = Math.Min(minY, ListSheetFieldDefsCopyPaste[i].YPos);
        }
        for (int i = 0;i < ListSheetFieldDefsCopyPaste.Count;i++)
        {
            //create new controls
            Random rand = new Random();
            //this new key is only used for copy and paste function.
            //When this sheet is saved, all sheetfielddefs are deleted and reinserted, so the dummy PKs are harmless.
            //There's a VERY slight chance of PK duplication, but the only result would be selection of wrong field.
            int newDefNum = rand.Next(int.MaxValue);
            ListSheetFieldDefsCopyPaste[i].SheetFieldDefNum = newDefNum;
            SheetFieldDef fielddef = ListSheetFieldDefsCopyPaste[i].Copy();
            fielddef.XPos = fielddef.XPos - minX + origin.X;
            fielddef.YPos = fielddef.YPos - minY + origin.Y;
            SheetDefCur.SheetFieldDefs.Add(fielddef);
        }
        if (!AltIsDown)
        {
            PasteOffsetY += ((PasteOffset + 10) / 100) * 10;
            //this will shift the pastes down 10 pixels every 10 pastes.
            PasteOffset = (PasteOffset + 10) % 100;
        }
         
        //cascades and allows for 90 consecutive pastes without overlap
        fillFieldList();
        //for(int i=0;i<ListSheetFieldDefsCopyPaste.Count;i++) {//reselect newly added controls
        //  listFields.SetSelected((listFields.Items.Count-1)-i,true);//Add to selected indicies, which will be the newest clipboard.count controls on the bottom of the list.
        //}
        panelMain.Refresh();
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (IsTabMode)
        {
            return ;
        }
         
        if (SheetDefCur.getIsNew())
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (!MsgBox.show(this,true,"Delete entire sheet?"))
        {
            return ;
        }
         
        try
        {
            SheetDefs.deleteObject(SheetDefCur.SheetDefNum);
            DialogResult = DialogResult.OK;
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    private boolean verifyDesign() throws Exception {
        //Keep a temporary list of every medical input and check box so it saves time checking for duplicates.
        List<SheetFieldDef> medChkBoxList = new List<SheetFieldDef>();
        List<SheetFieldDef> inputMedList = new List<SheetFieldDef>();
        for (int i = 0;i < SheetDefCur.SheetFieldDefs.Count;i++)
        {
            //Verify radio button groups.
            SheetFieldDef field = SheetDefCur.SheetFieldDefs[i];
            //for misc radio groups
            if (field.FieldType == SheetFieldType.CheckBox && field.IsRequired && (!StringSupport.equals(field.RadioButtonGroup, "") || !StringSupport.equals(field.RadioButtonValue, "")))
            {
                for (int j = 0;j < SheetDefCur.SheetFieldDefs.Count;j++)
                {
                    //for built-in radio groups
                    //All radio buttons within a group must either all be marked required or all be marked not required.
                    //Not the most efficient check, but there won't usually be more than a few hundred items so the user will not ever notice. We can speed up later if needed.
                    SheetFieldDef field2 = SheetDefCur.SheetFieldDefs[j];
                    //for misc groups
                    if (field2.FieldType == SheetFieldType.CheckBox && !field2.IsRequired && field2.RadioButtonGroup.ToLower() == field.RadioButtonGroup.ToLower() && field2.FieldName.ToLower() == field.FieldName.ToLower())
                    {
                        //for misc groups
                        MessageBox.Show(Lan.g(this,"Radio buttons in radio button group") + " '" + (StringSupport.equals(field.RadioButtonGroup, "") ? field.FieldName : field.RadioButtonGroup) + "' " + Lan.g(this,"must all be marked required or all be marked not required."));
                        return false;
                    }
                     
                }
            }
             
            if (field.FieldType == SheetFieldType.CheckBox && (field.FieldName.StartsWith("allergy:")) || field.FieldName.StartsWith("checkMed") || field.FieldName.StartsWith("problem:"))
            {
                for (int j = 0;j < medChkBoxList.Count;j++)
                {
                    //Check for duplicates.
                    if (StringSupport.equals(medChkBoxList[j].FieldName, field.FieldName) && StringSupport.equals(medChkBoxList[j].RadioButtonValue, field.RadioButtonValue))
                    {
                        MessageBox.Show(Lan.g(this,"Duplicate check box found") + ": '" + field.FieldName + " " + field.RadioButtonValue + "'. " + Lan.g(this,"Only one of each type is allowed."));
                        return false;
                    }
                     
                }
                //Not a duplicate so add it to the med chk box list.
                medChkBoxList.Add(field);
            }
            else if (field.FieldType == SheetFieldType.InputField && field.FieldName.StartsWith("inputMed"))
            {
                for (int j = 0;j < inputMedList.Count;j++)
                {
                    if (StringSupport.equals(inputMedList[j].FieldName, field.FieldName))
                    {
                        MessageBox.Show(Lan.g(this,"Duplicate inputMed boxes found") + ": '" + field.FieldName + "'. " + Lan.g(this,"Only one of each is allowed."));
                        return false;
                    }
                     
                }
                inputMedList.Add(field);
            }
              
        }
        return true;
    }

    private void linkLabelTips_LinkClicked(Object sender, LinkLabelLinkClickedEventArgs e) throws Exception {
        if (IsTabMode)
        {
            return ;
        }
         
        String tips = "";
        tips += "The following shortcuts and hotkeys are supported:\r\n";
        tips += "\r\n";
        tips += "CTRL + C : Copy selected field(s).\r\n";
        tips += "\r\n";
        tips += "CTRL + V : Paste.\r\n";
        tips += "\r\n";
        tips += "ALT + Click : 'Rubber stamp' paste to the cursor position.\r\n";
        tips += "\r\n";
        tips += "Click + Drag : Click on a blank space and then drag to group select.\r\n";
        tips += "\r\n";
        tips += "CTRL + Click + Drag : Add a group of fields to the selection.\r\n";
        tips += "\r\n";
        tips += "Delete or Backspace : Delete selected field(s).\r\n";
        MessageBox.Show(Lan.g(this,tips));
    }

    /**
    * When clicked it will set all selected elements' Y coordinates to the smallest Y coordinate in the group, unless two controls have the same X coordinate.
    */
    private void butAlignTop_Click(Object sender, EventArgs e) throws Exception {
        if (listFields.SelectedIndices.Count < 2)
        {
            return ;
        }
         
        float minY = SheetDefCur.SheetFieldDefs[listFields.SelectedIndices[0]].BoundsF.Top;
        for (int i = 0;i < listFields.SelectedIndices.Count;i++)
        {
            if (SheetDefCur.SheetFieldDefs[listFields.SelectedIndices[i]].BoundsF.Top < minY)
            {
                //current element is higher up than the current 'highest' element.
                minY = SheetDefCur.SheetFieldDefs[listFields.SelectedIndices[i]].BoundsF.Top;
            }
             
            for (int j = 0;j < listFields.SelectedIndices.Count;j++)
            {
                if (i == j)
                {
                    continue;
                }
                 
                //Don't compare element to itself.
                //compair the int bounds not the boundsF for practical use
                if (SheetDefCur.SheetFieldDefs[listFields.SelectedIndices[i]].Bounds.X == SheetDefCur.SheetFieldDefs[listFields.SelectedIndices[j]].Bounds.X)
                {
                    //compair the int bounds not the boundsF for practical use
                    MsgBox.show(this,"Cannot align controls. Two or more selected controls will overlap.");
                    return ;
                }
                 
            }
        }
        for (int i = 0;i < listFields.SelectedIndices.Count;i++)
        {
            //Actually move the controls now
            SheetDefCur.SheetFieldDefs[listFields.SelectedIndices[i]].YPos = (int)minY;
        }
        panelMain.Refresh();
    }

    private void butAlignLeft_Click(Object sender, EventArgs e) throws Exception {
        if (listFields.SelectedIndices.Count < 2)
        {
            return ;
        }
         
        float minX = SheetDefCur.SheetFieldDefs[listFields.SelectedIndices[0]].BoundsF.Left;
        for (int i = 0;i < listFields.SelectedIndices.Count;i++)
        {
            if (SheetDefCur.SheetFieldDefs[listFields.SelectedIndices[i]].BoundsF.Left < minX)
            {
                //current element is higher up than the current 'highest' element.
                minX = SheetDefCur.SheetFieldDefs[listFields.SelectedIndices[i]].BoundsF.Left;
            }
             
            for (int j = 0;j < listFields.SelectedIndices.Count;j++)
            {
                if (i == j)
                {
                    continue;
                }
                 
                //Don't compare element to itself.
                //compare the int bounds not the boundsF for practical use
                if (SheetDefCur.SheetFieldDefs[listFields.SelectedIndices[i]].Bounds.Y == SheetDefCur.SheetFieldDefs[listFields.SelectedIndices[j]].Bounds.Y)
                {
                    //compare the int bounds not the boundsF for practical use
                    MsgBox.show(this,"Cannot align controls. Two or more selected controls will overlap.");
                    return ;
                }
                 
            }
        }
        for (int i = 0;i < listFields.SelectedIndices.Count;i++)
        {
            //Actually move the controls now
            SheetDefCur.SheetFieldDefs[listFields.SelectedIndices[i]].XPos = (int)minX;
        }
        panelMain.Refresh();
    }

    private void butPaste_Click(Object sender, EventArgs e) throws Exception {
        pasteControlsFromMemory(new Point(0, 0));
    }

    private void butCopy_Click(Object sender, EventArgs e) throws Exception {
        copyControlsToMemory();
    }

    private void butTabOrder_Click(Object sender, EventArgs e) throws Exception {
        IsTabMode = !IsTabMode;
        if (IsTabMode)
        {
            butOK.Enabled = false;
            butCancel.Enabled = false;
            butDelete.Enabled = false;
            groupAddNew.Enabled = false;
            butCopy.Enabled = false;
            butPaste.Enabled = false;
            butAlignLeft.Enabled = false;
            butAlignTop.Enabled = false;
            butEdit.Enabled = false;
            ListSheetFieldDefsTabOrder = new List<SheetFieldDef>();
        }
        else
        {
            //clear or create the list of tab orders.
            butOK.Enabled = true;
            butCancel.Enabled = true;
            butDelete.Enabled = true;
            groupAddNew.Enabled = true;
            butCopy.Enabled = true;
            butPaste.Enabled = true;
            butAlignLeft.Enabled = true;
            butAlignTop.Enabled = true;
            butEdit.Enabled = true;
        } 
        panelMain.Refresh();
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (!verifyDesign())
        {
            return ;
        }
         
        SheetDefs.insertOrUpdate(SheetDefCur);
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    /**
    * Required designer variable.
    */
    private System.ComponentModel.IContainer components = null;
    /**
    * Clean up any resources being used.
    * 
    *  @param disposing true if managed resources should be disposed; otherwise, false.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing && (components != null))
        {
            components.Dispose();
        }
         
        if (GraphicsBackground != null)
        {
            GraphicsBackground.Dispose();
            GraphicsBackground = null;
        }
         
        if (BmBackground != null)
        {
            BmBackground.Dispose();
            BmBackground = null;
        }
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        this.textDescription = new System.Windows.Forms.TextBox();
        this.panel1 = new System.Windows.Forms.Panel();
        this.labelInternal = new System.Windows.Forms.Label();
        this.listFields = new System.Windows.Forms.ListBox();
        this.label2 = new System.Windows.Forms.Label();
        this.groupAddNew = new System.Windows.Forms.GroupBox();
        this.linkLabelTips = new System.Windows.Forms.LinkLabel();
        this.butTabOrder = new OpenDental.UI.Button();
        this.butPaste = new OpenDental.UI.Button();
        this.butCopy = new OpenDental.UI.Button();
        this.butAlignLeft = new OpenDental.UI.Button();
        this.butAlignTop = new OpenDental.UI.Button();
        this.butEdit = new OpenDental.UI.Button();
        this.butAddSpecial = new OpenDental.UI.Button();
        this.butAddPatImage = new OpenDental.UI.Button();
        this.butAddSigBox = new OpenDental.UI.Button();
        this.butAddCheckBox = new OpenDental.UI.Button();
        this.butAddRect = new OpenDental.UI.Button();
        this.butAddLine = new OpenDental.UI.Button();
        this.butAddImage = new OpenDental.UI.Button();
        this.butAddStaticText = new OpenDental.UI.Button();
        this.butAddInputField = new OpenDental.UI.Button();
        this.butAddOutputText = new OpenDental.UI.Button();
        this.panelMain = new OpenDental.PanelGraphics();
        this.butDelete = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.panel1.SuspendLayout();
        this.groupAddNew.SuspendLayout();
        this.SuspendLayout();
        //
        // textDescription
        //
        this.textDescription.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.textDescription.Location = new System.Drawing.Point(638, 3);
        this.textDescription.Name = "textDescription";
        this.textDescription.ReadOnly = true;
        this.textDescription.Size = new System.Drawing.Size(144, 20);
        this.textDescription.TabIndex = 0;
        //
        // panel1
        //
        this.panel1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.panel1.AutoScroll = true;
        this.panel1.Controls.Add(this.panelMain);
        this.panel1.Location = new System.Drawing.Point(3, 3);
        this.panel1.Name = "panel1";
        this.panel1.Size = new System.Drawing.Size(617, 630);
        this.panel1.TabIndex = 81;
        //
        // labelInternal
        //
        this.labelInternal.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.labelInternal.Location = new System.Drawing.Point(640, 530);
        this.labelInternal.Name = "labelInternal";
        this.labelInternal.Size = new System.Drawing.Size(144, 46);
        this.labelInternal.TabIndex = 82;
        this.labelInternal.Text = "This is an internal sheet, so it may not be edited.  Make a copy instead.";
        this.labelInternal.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // listFields
        //
        this.listFields.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Right)));
        this.listFields.FormattingEnabled = true;
        this.listFields.Location = new System.Drawing.Point(640, 186);
        this.listFields.Name = "listFields";
        this.listFields.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listFields.Size = new System.Drawing.Size(142, 290);
        this.listFields.TabIndex = 83;
        this.listFields.Click += new System.EventHandler(this.listFields_Click);
        this.listFields.MouseDoubleClick += new System.Windows.Forms.MouseEventHandler(this.listFields_MouseDoubleClick);
        //
        // label2
        //
        this.label2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.label2.Location = new System.Drawing.Point(638, 169);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(108, 16);
        this.label2.TabIndex = 84;
        this.label2.Text = "Fields";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // groupAddNew
        //
        this.groupAddNew.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.groupAddNew.Controls.Add(this.butAddSpecial);
        this.groupAddNew.Controls.Add(this.butAddPatImage);
        this.groupAddNew.Controls.Add(this.butAddSigBox);
        this.groupAddNew.Controls.Add(this.butAddCheckBox);
        this.groupAddNew.Controls.Add(this.butAddRect);
        this.groupAddNew.Controls.Add(this.butAddLine);
        this.groupAddNew.Controls.Add(this.butAddImage);
        this.groupAddNew.Controls.Add(this.butAddStaticText);
        this.groupAddNew.Controls.Add(this.butAddInputField);
        this.groupAddNew.Controls.Add(this.butAddOutputText);
        this.groupAddNew.Location = new System.Drawing.Point(638, 48);
        this.groupAddNew.Name = "groupAddNew";
        this.groupAddNew.Size = new System.Drawing.Size(144, 119);
        this.groupAddNew.TabIndex = 86;
        this.groupAddNew.TabStop = false;
        this.groupAddNew.Text = "Add new";
        //
        // linkLabelTips
        //
        this.linkLabelTips.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.linkLabelTips.Location = new System.Drawing.Point(751, 535);
        this.linkLabelTips.Name = "linkLabelTips";
        this.linkLabelTips.Size = new System.Drawing.Size(31, 17);
        this.linkLabelTips.TabIndex = 93;
        this.linkLabelTips.TabStop = true;
        this.linkLabelTips.Text = "tips";
        this.linkLabelTips.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.linkLabelTips.LinkClicked += new System.Windows.Forms.LinkLabelLinkClickedEventHandler(this.linkLabelTips_LinkClicked);
        //
        // butTabOrder
        //
        this.butTabOrder.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butTabOrder.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butTabOrder.setAutosize(true);
        this.butTabOrder.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butTabOrder.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butTabOrder.setCornerRadius(4F);
        this.butTabOrder.Location = new System.Drawing.Point(640, 535);
        this.butTabOrder.Name = "butTabOrder";
        this.butTabOrder.Size = new System.Drawing.Size(70, 24);
        this.butTabOrder.TabIndex = 94;
        this.butTabOrder.TabStop = false;
        this.butTabOrder.Text = "Tab Order";
        this.butTabOrder.Click += new System.EventHandler(this.butTabOrder_Click);
        //
        // butPaste
        //
        this.butPaste.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPaste.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butPaste.setAutosize(true);
        this.butPaste.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPaste.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPaste.setCornerRadius(4F);
        this.butPaste.Location = new System.Drawing.Point(710, 508);
        this.butPaste.Name = "butPaste";
        this.butPaste.Size = new System.Drawing.Size(72, 24);
        this.butPaste.TabIndex = 91;
        this.butPaste.TabStop = false;
        this.butPaste.Text = "Paste";
        this.butPaste.Click += new System.EventHandler(this.butPaste_Click);
        //
        // butCopy
        //
        this.butCopy.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCopy.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCopy.setAutosize(true);
        this.butCopy.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCopy.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCopy.setCornerRadius(4F);
        this.butCopy.Location = new System.Drawing.Point(640, 508);
        this.butCopy.Name = "butCopy";
        this.butCopy.Size = new System.Drawing.Size(70, 24);
        this.butCopy.TabIndex = 90;
        this.butCopy.TabStop = false;
        this.butCopy.Text = "Copy";
        this.butCopy.Click += new System.EventHandler(this.butCopy_Click);
        //
        // butAlignLeft
        //
        this.butAlignLeft.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAlignLeft.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butAlignLeft.setAutosize(true);
        this.butAlignLeft.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAlignLeft.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAlignLeft.setCornerRadius(4F);
        this.butAlignLeft.Location = new System.Drawing.Point(640, 482);
        this.butAlignLeft.Name = "butAlignLeft";
        this.butAlignLeft.Size = new System.Drawing.Size(70, 24);
        this.butAlignLeft.TabIndex = 89;
        this.butAlignLeft.TabStop = false;
        this.butAlignLeft.Text = "Align Left";
        this.butAlignLeft.Click += new System.EventHandler(this.butAlignLeft_Click);
        //
        // butAlignTop
        //
        this.butAlignTop.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAlignTop.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butAlignTop.setAutosize(true);
        this.butAlignTop.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAlignTop.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAlignTop.setCornerRadius(4F);
        this.butAlignTop.Location = new System.Drawing.Point(710, 482);
        this.butAlignTop.Name = "butAlignTop";
        this.butAlignTop.Size = new System.Drawing.Size(72, 24);
        this.butAlignTop.TabIndex = 88;
        this.butAlignTop.TabStop = false;
        this.butAlignTop.Text = "Align Top";
        this.butAlignTop.Click += new System.EventHandler(this.butAlignTop_Click);
        //
        // butEdit
        //
        this.butEdit.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butEdit.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butEdit.setAutosize(true);
        this.butEdit.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butEdit.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butEdit.setCornerRadius(4F);
        this.butEdit.Location = new System.Drawing.Point(692, 24);
        this.butEdit.Name = "butEdit";
        this.butEdit.Size = new System.Drawing.Size(90, 24);
        this.butEdit.TabIndex = 87;
        this.butEdit.TabStop = false;
        this.butEdit.Text = "Edit Properties";
        this.butEdit.Click += new System.EventHandler(this.butEdit_Click);
        //
        // butAddSpecial
        //
        this.butAddSpecial.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddSpecial.setAutosize(true);
        this.butAddSpecial.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddSpecial.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddSpecial.setCornerRadius(4F);
        this.butAddSpecial.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddSpecial.Location = new System.Drawing.Point(3, 95);
        this.butAddSpecial.Name = "butAddSpecial";
        this.butAddSpecial.Size = new System.Drawing.Size(69, 20);
        this.butAddSpecial.TabIndex = 94;
        this.butAddSpecial.TabStop = false;
        this.butAddSpecial.Text = "Special";
        this.butAddSpecial.Visible = false;
        this.butAddSpecial.Click += new System.EventHandler(this.butAddSpecial_Click);
        //
        // butAddPatImage
        //
        this.butAddPatImage.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddPatImage.setAutosize(true);
        this.butAddPatImage.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddPatImage.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddPatImage.setCornerRadius(4F);
        this.butAddPatImage.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddPatImage.Location = new System.Drawing.Point(72, 95);
        this.butAddPatImage.Name = "butAddPatImage";
        this.butAddPatImage.Size = new System.Drawing.Size(69, 20);
        this.butAddPatImage.TabIndex = 93;
        this.butAddPatImage.TabStop = false;
        this.butAddPatImage.Text = "Pat Image";
        this.butAddPatImage.Visible = false;
        this.butAddPatImage.Click += new System.EventHandler(this.butAddPatImage_Click);
        //
        // butAddSigBox
        //
        this.butAddSigBox.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddSigBox.setAutosize(true);
        this.butAddSigBox.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddSigBox.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddSigBox.setCornerRadius(4F);
        this.butAddSigBox.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddSigBox.Location = new System.Drawing.Point(72, 75);
        this.butAddSigBox.Name = "butAddSigBox";
        this.butAddSigBox.Size = new System.Drawing.Size(69, 20);
        this.butAddSigBox.TabIndex = 92;
        this.butAddSigBox.TabStop = false;
        this.butAddSigBox.Text = "Signature";
        this.butAddSigBox.Click += new System.EventHandler(this.butAddSigBox_Click);
        //
        // butAddCheckBox
        //
        this.butAddCheckBox.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddCheckBox.setAutosize(true);
        this.butAddCheckBox.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddCheckBox.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddCheckBox.setCornerRadius(4F);
        this.butAddCheckBox.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddCheckBox.Location = new System.Drawing.Point(3, 55);
        this.butAddCheckBox.Name = "butAddCheckBox";
        this.butAddCheckBox.Size = new System.Drawing.Size(69, 20);
        this.butAddCheckBox.TabIndex = 91;
        this.butAddCheckBox.TabStop = false;
        this.butAddCheckBox.Text = "CheckBox";
        this.butAddCheckBox.Click += new System.EventHandler(this.butAddCheckBox_Click);
        //
        // butAddRect
        //
        this.butAddRect.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddRect.setAutosize(true);
        this.butAddRect.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddRect.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddRect.setCornerRadius(4F);
        this.butAddRect.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddRect.Location = new System.Drawing.Point(72, 55);
        this.butAddRect.Name = "butAddRect";
        this.butAddRect.Size = new System.Drawing.Size(69, 20);
        this.butAddRect.TabIndex = 90;
        this.butAddRect.TabStop = false;
        this.butAddRect.Text = "Rectangle";
        this.butAddRect.Click += new System.EventHandler(this.butAddRect_Click);
        //
        // butAddLine
        //
        this.butAddLine.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddLine.setAutosize(true);
        this.butAddLine.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddLine.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddLine.setCornerRadius(4F);
        this.butAddLine.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddLine.Location = new System.Drawing.Point(72, 35);
        this.butAddLine.Name = "butAddLine";
        this.butAddLine.Size = new System.Drawing.Size(69, 20);
        this.butAddLine.TabIndex = 89;
        this.butAddLine.TabStop = false;
        this.butAddLine.Text = "Line";
        this.butAddLine.Click += new System.EventHandler(this.butAddLine_Click);
        //
        // butAddImage
        //
        this.butAddImage.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddImage.setAutosize(true);
        this.butAddImage.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddImage.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddImage.setCornerRadius(4F);
        this.butAddImage.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddImage.Location = new System.Drawing.Point(3, 75);
        this.butAddImage.Name = "butAddImage";
        this.butAddImage.Size = new System.Drawing.Size(69, 20);
        this.butAddImage.TabIndex = 88;
        this.butAddImage.TabStop = false;
        this.butAddImage.Text = "StaticImage";
        this.butAddImage.Click += new System.EventHandler(this.butAddImage_Click);
        //
        // butAddStaticText
        //
        this.butAddStaticText.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddStaticText.setAutosize(true);
        this.butAddStaticText.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddStaticText.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddStaticText.setCornerRadius(4F);
        this.butAddStaticText.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddStaticText.Location = new System.Drawing.Point(72, 15);
        this.butAddStaticText.Name = "butAddStaticText";
        this.butAddStaticText.Size = new System.Drawing.Size(69, 20);
        this.butAddStaticText.TabIndex = 87;
        this.butAddStaticText.TabStop = false;
        this.butAddStaticText.Text = "StaticText";
        this.butAddStaticText.Click += new System.EventHandler(this.butAddStaticText_Click);
        //
        // butAddInputField
        //
        this.butAddInputField.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddInputField.setAutosize(true);
        this.butAddInputField.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddInputField.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddInputField.setCornerRadius(4F);
        this.butAddInputField.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddInputField.Location = new System.Drawing.Point(3, 35);
        this.butAddInputField.Name = "butAddInputField";
        this.butAddInputField.Size = new System.Drawing.Size(69, 20);
        this.butAddInputField.TabIndex = 86;
        this.butAddInputField.TabStop = false;
        this.butAddInputField.Text = "InputField";
        this.butAddInputField.Click += new System.EventHandler(this.butAddInputField_Click);
        //
        // butAddOutputText
        //
        this.butAddOutputText.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddOutputText.setAutosize(true);
        this.butAddOutputText.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddOutputText.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddOutputText.setCornerRadius(4F);
        this.butAddOutputText.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddOutputText.Location = new System.Drawing.Point(3, 15);
        this.butAddOutputText.Name = "butAddOutputText";
        this.butAddOutputText.Size = new System.Drawing.Size(69, 20);
        this.butAddOutputText.TabIndex = 85;
        this.butAddOutputText.TabStop = false;
        this.butAddOutputText.Text = "OutputText";
        this.butAddOutputText.Click += new System.EventHandler(this.butAddOutputText_Click);
        //
        // panelMain
        //
        this.panelMain.BackColor = System.Drawing.Color.Transparent;
        this.panelMain.Location = new System.Drawing.Point(0, 0);
        this.panelMain.Name = "panelMain";
        this.panelMain.Size = new System.Drawing.Size(549, 513);
        this.panelMain.TabIndex = 0;
        this.panelMain.TabStop = true;
        this.panelMain.Paint += new System.Windows.Forms.PaintEventHandler(this.panelMain_Paint);
        this.panelMain.MouseDoubleClick += new System.Windows.Forms.MouseEventHandler(this.panelMain_MouseDoubleClick);
        this.panelMain.MouseDown += new System.Windows.Forms.MouseEventHandler(this.panelMain_MouseDown);
        this.panelMain.MouseMove += new System.Windows.Forms.MouseEventHandler(this.panelMain_MouseMove);
        this.panelMain.MouseUp += new System.Windows.Forms.MouseEventHandler(this.panelMain_MouseUp);
        this.panelMain.Resize += new System.EventHandler(this.panelMain_Resize);
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(640, 609);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(70, 24);
        this.butDelete.TabIndex = 80;
        this.butDelete.TabStop = false;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(710, 580);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(72, 24);
        this.butOK.TabIndex = 3;
        this.butOK.TabStop = false;
        this.butOK.Text = "OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(710, 609);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(72, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.TabStop = false;
        this.butCancel.Text = "Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormSheetDefEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(789, 641);
        this.Controls.Add(this.butTabOrder);
        this.Controls.Add(this.linkLabelTips);
        this.Controls.Add(this.butPaste);
        this.Controls.Add(this.butCopy);
        this.Controls.Add(this.butAlignLeft);
        this.Controls.Add(this.butAlignTop);
        this.Controls.Add(this.butEdit);
        this.Controls.Add(this.groupAddNew);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.listFields);
        this.Controls.Add(this.labelInternal);
        this.Controls.Add(this.panel1);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.textDescription);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.DoubleBuffered = true;
        this.KeyPreview = true;
        this.Name = "FormSheetDefEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Sheet Def";
        this.Load += new System.EventHandler(this.FormSheetDefEdit_Load);
        this.KeyUp += new System.Windows.Forms.KeyEventHandler(this.FormSheetDefEdit_KeyUp);
        this.panel1.ResumeLayout(false);
        this.groupAddNew.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.TextBox textDescription = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.Panel panel1 = new System.Windows.Forms.Panel();
    private OpenDental.PanelGraphics panelMain;
    private System.Windows.Forms.Label labelInternal = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listFields = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butAddOutputText;
    private System.Windows.Forms.GroupBox groupAddNew = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butAddStaticText;
    private OpenDental.UI.Button butAddInputField;
    private OpenDental.UI.Button butEdit;
    private OpenDental.UI.Button butAddImage;
    private OpenDental.UI.Button butAddRect;
    private OpenDental.UI.Button butAddLine;
    private OpenDental.UI.Button butAddCheckBox;
    private OpenDental.UI.Button butAddSigBox;
    private OpenDental.UI.Button butAddPatImage;
    private OpenDental.UI.Button butAlignTop;
    private OpenDental.UI.Button butAlignLeft;
    private OpenDental.UI.Button butCopy;
    private OpenDental.UI.Button butPaste;
    private System.Windows.Forms.LinkLabel linkLabelTips = new System.Windows.Forms.LinkLabel();
    private OpenDental.UI.Button butTabOrder;
    private OpenDental.UI.Button butAddSpecial;
}


