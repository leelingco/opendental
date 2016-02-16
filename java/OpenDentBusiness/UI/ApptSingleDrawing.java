//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:12 PM
//

package OpenDentBusiness.UI;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDentBusiness.ApptFieldDefs;
import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.ApptView;
import OpenDentBusiness.ApptViewAlignment;
import OpenDentBusiness.ApptViewItem;
import OpenDentBusiness.ApptViewStackBehavior;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Lans;
import OpenDentBusiness.PatFieldDefs;
import OpenDentBusiness.PIn;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Providers;
import OpenDentBusiness.UI.ApptDrawing;

public class ApptSingleDrawing   
{
    public static float ApptSingleHeight = new float();
    public static float ApptSingleWidth = new float();
    private static Point location = new Point();
    /**
    * Set default fontSize to 8 unless printing.
    */
    public static void drawEntireAppt(Graphics g, DataRow dataRoww, String patternShowing, float totalWidth, float totalHeight, boolean isSelected, boolean thisIsPinBoard, long selectedAptNum, List<ApptViewItem> apptRows, ApptView apptViewCur, DataTable tableApptFields, DataTable tablePatFields, int fontSize, boolean isPrinting) throws Exception {
        Pen penB = new Pen(Color.Black);
        Pen penW = new Pen(Color.White);
        Pen penGr = new Pen(Color.SlateGray);
        Pen penDG = new Pen(Color.DarkSlateGray);
        Pen penO = new Pen();
        //provider outline color
        Color backColor = new Color();
        Color provColor = new Color();
        if (!StringSupport.equals(dataRoww["ProvNum"].ToString(), "0") && StringSupport.equals(dataRoww["IsHygiene"].ToString(), "0"))
        {
            //dentist
            provColor = Providers.GetColor(PIn.Long(dataRoww["ProvNum"].ToString()));
            penO = new Pen(Providers.GetOutlineColor(PIn.Long(dataRoww["ProvNum"].ToString())));
        }
        else if (!StringSupport.equals(dataRoww["ProvHyg"].ToString(), "0") && StringSupport.equals(dataRoww["IsHygiene"].ToString(), "1"))
        {
            //hygienist
            provColor = Providers.GetColor(PIn.Long(dataRoww["ProvHyg"].ToString()));
            penO = new Pen(Providers.GetOutlineColor(PIn.Long(dataRoww["ProvHyg"].ToString())));
        }
        else
        {
            //unknown
            provColor = Color.White;
            penO = new Pen(Color.Black);
        }  
        if (PIn.Long(dataRoww["AptStatus"].ToString()) == ((Enum)ApptStatus.Complete).ordinal())
        {
            backColor = DefC.getLong()[((Enum)DefCat.AppointmentColors).ordinal()][2].ItemColor;
        }
        else if (PIn.Long(dataRoww["AptStatus"].ToString()) == ((Enum)ApptStatus.PtNote).ordinal())
        {
            backColor = DefC.getLong()[((Enum)DefCat.AppointmentColors).ordinal()][5].ItemColor;
        }
        else if (PIn.Long(dataRoww["AptStatus"].ToString()) == ((Enum)ApptStatus.PtNoteCompleted).ordinal())
        {
            backColor = DefC.getLong()[((Enum)DefCat.AppointmentColors).ordinal()][6].ItemColor;
        }
        else if (PIn.Int(dataRoww["ColorOverride"].ToString()) != 0)
        {
            backColor = Color.FromArgb(PIn.Int(dataRoww["ColorOverride"].ToString()));
        }
        else
        {
            backColor = provColor;
        }    
        //We might want to do something interesting here.
        SolidBrush backBrush = new SolidBrush(backColor);
        g.FillRectangle(backBrush, 7, 0, totalWidth - 7, (int)totalHeight);
        g.FillRectangle(Brushes.White, 0, 0, 7, (int)totalHeight);
        Pen penTimediv = Pens.Silver;
        for (int i = 0;i < patternShowing.Length;i++)
        {
            //Info.MyApt.Pattern.Length;i++){
            if (StringSupport.equals(patternShowing.Substring(i, 1), "X"))
            {
                if (isPrinting)
                {
                    g.FillRectangle(new SolidBrush(provColor), 0, i * ApptDrawing.LineH, 7, ApptDrawing.LineH);
                }
                else
                {
                    g.FillRectangle(new SolidBrush(provColor), 1, i * ApptDrawing.LineH + 1, 6, ApptDrawing.LineH);
                } 
            }
            else
            {
            } 
            //leave empty
            if (Math.IEEERemainder((double)i, (double)ApptDrawing.RowsPerIncr) == 0)
            {
                //0/1
                if (isPrinting)
                {
                    g.DrawLine(penTimediv, 0, i * ApptDrawing.LineH, 7, i * ApptDrawing.LineH);
                }
                else
                {
                    g.DrawLine(penTimediv, 1, i * ApptDrawing.LineH, 6, i * ApptDrawing.LineH);
                } 
            }
             
        }
        g.DrawLine(penB, 7, 0, 7, (int)totalHeight);
        if (isSelected || (!thisIsPinBoard && dataRoww["AptNum"].ToString() == selectedAptNum.ToString()))
        {
            //Left
            g.DrawLine(penO, 8, 1, 8, totalHeight - 2);
            g.DrawLine(penO, 9, 1, 9, totalHeight - 3);
            //Right
            g.DrawLine(penO, totalWidth - 2, 1, totalWidth - 2, totalHeight - 2);
            g.DrawLine(penO, totalWidth - 3, 2, totalWidth - 3, totalHeight - 3);
            //Top
            g.DrawLine(penO, 8, 1, totalWidth - 2, 1);
            g.DrawLine(penO, 8, 2, totalWidth - 3, 2);
            //bottom
            g.DrawLine(penO, 9, totalHeight - 2, totalWidth - 2, totalHeight - 2);
            g.DrawLine(penO, 10, totalHeight - 3, totalWidth - 3, totalHeight - 3);
        }
         
        Point drawLoc = new Point(9, 0);
        int elementI = 0;
        while (drawLoc.Y < totalHeight && elementI < apptRows.Count)
        {
            if (apptRows[elementI].ElementAlignment != ApptViewAlignment.Main)
            {
                elementI++;
                continue;
            }
             
            drawLoc = DrawElement(g, elementI, drawLoc, ApptViewStackBehavior.Vertical, ApptViewAlignment.Main, backBrush, dataRoww, apptRows, tableApptFields, tablePatFields, totalWidth, totalHeight, fontSize, isPrinting);
            //set the drawLoc to a new point, based on space used by element
            elementI++;
        }
        drawLoc = new Point((int)totalWidth - 1, 0);
        //in the UR area, we refer to the upper right corner of each element.
        elementI = 0;
        while (drawLoc.Y < totalHeight && elementI < apptRows.Count)
        {
            if (apptRows[elementI].ElementAlignment != ApptViewAlignment.UR)
            {
                elementI++;
                continue;
            }
             
            drawLoc = DrawElement(g, elementI, drawLoc, apptViewCur.StackBehavUR, ApptViewAlignment.UR, backBrush, dataRoww, apptRows, tableApptFields, tablePatFields, totalWidth, totalHeight, fontSize, isPrinting);
            elementI++;
        }
        drawLoc = new Point((int)totalWidth - 1, (int)totalHeight - 1);
        //in the LR area, we refer to the lower right corner of each element.
        elementI = apptRows.Count - 1;
        while (drawLoc.Y > 0 && elementI >= 0)
        {
            //For lower right, draw the list backwards.
            if (apptRows[elementI].ElementAlignment != ApptViewAlignment.LR)
            {
                elementI--;
                continue;
            }
             
            drawLoc = DrawElement(g, elementI, drawLoc, apptViewCur.StackBehavLR, ApptViewAlignment.LR, backBrush, dataRoww, apptRows, tableApptFields, tablePatFields, totalWidth, totalHeight, fontSize, isPrinting);
            elementI--;
        }
        //Main outline
        if (isPrinting)
        {
            g.DrawRectangle(new Pen(Color.Black), 0, 0, totalWidth, totalHeight);
        }
        else
        {
            g.DrawRectangle(new Pen(Color.Black), 0, 0, totalWidth - 1, totalHeight - 1);
        } 
        //broken X
        if (dataRoww["AptStatus"].ToString() == (((Enum)ApptStatus.Broken).ordinal()).ToString())
        {
            g.DrawLine(new Pen(Color.Black), 8, 1, totalWidth - 1, totalHeight - 1);
            g.DrawLine(new Pen(Color.Black), 8, totalHeight - 1, totalWidth - 1, 1);
        }
         
        //Dispose of the objects.
        disposeObjects(penB,penW,penGr,penDG,penO,backBrush);
    }

    /**
    * 
    */
    public static Point drawElement(Graphics g, int elementI, Point drawLoc, ApptViewStackBehavior stackBehavior, ApptViewAlignment align, Brush backBrush, DataRow dataRoww, List<ApptViewItem> apptRows, DataTable tableApptFields, DataTable tablePatFields, float totalWidth, float totalHeight, int fontSize, boolean isPrinting) throws Exception {
        Font baseFont = new Font("Arial", fontSize);
        String text = "";
        boolean isNote = false;
        if (PIn.Long(dataRoww["AptStatus"].ToString()) == ((Enum)ApptStatus.PtNote).ordinal() || PIn.Long(dataRoww["AptStatus"].ToString()) == ((Enum)ApptStatus.PtNoteCompleted).ordinal())
        {
            isNote = true;
        }
         
        boolean isGraphic = false;
        if (StringSupport.equals(apptRows[elementI].ElementDesc, "ConfirmedColor"))
        {
            isGraphic = true;
        }
         
        if (apptRows[elementI].ApptFieldDefNum > 0)
        {
            String fieldName = ApptFieldDefs.GetFieldName(apptRows[elementI].ApptFieldDefNum);
            for (int i = 0;i < tableApptFields.Rows.Count;i++)
            {
                if (tableApptFields.Rows[i]["AptNum"].ToString() != dataRoww["AptNum"].ToString())
                {
                    continue;
                }
                 
                if (!StringSupport.equals(tableApptFields.Rows[i]["FieldName"].ToString(), fieldName))
                {
                    continue;
                }
                 
                text = tableApptFields.Rows[i]["FieldValue"].ToString();
            }
        }
        else if (apptRows[elementI].PatFieldDefNum > 0)
        {
            String fieldName = PatFieldDefs.GetFieldName(apptRows[elementI].PatFieldDefNum);
            for (int i = 0;i < tablePatFields.Rows.Count;i++)
            {
                if (tablePatFields.Rows[i]["PatNum"].ToString() != dataRoww["PatNum"].ToString())
                {
                    continue;
                }
                 
                if (!StringSupport.equals(tablePatFields.Rows[i]["FieldName"].ToString(), fieldName))
                {
                    continue;
                }
                 
                text = tablePatFields.Rows[i]["FieldValue"].ToString();
            }
        }
        else
        {
            ElementDesc __dummyScrutVar0 = apptRows[elementI].ElementDesc;
            if (__dummyScrutVar0.equals("Address"))
            {
                if (isNote)
                {
                    text = "";
                }
                else
                {
                    text = dataRoww["address"].ToString();
                } 
            }
            else if (__dummyScrutVar0.equals("AddrNote"))
            {
                if (isNote)
                {
                    text = "";
                }
                else
                {
                    text = dataRoww["addrNote"].ToString();
                } 
            }
            else if (__dummyScrutVar0.equals("Age"))
            {
                if (isNote)
                {
                    text = "";
                }
                else
                {
                    text = dataRoww["age"].ToString();
                } 
            }
            else if (__dummyScrutVar0.equals("ASAP"))
            {
                if (isNote)
                {
                    text = "";
                }
                else
                {
                    if (dataRoww["AptStatus"].ToString() == (((Enum)ApptStatus.ASAP).ordinal()).ToString())
                    {
                        text = Lans.g("enumApptStatus","ASAP");
                    }
                     
                } 
            }
            else if (__dummyScrutVar0.equals("ASAP[A]"))
            {
                if (dataRoww["AptStatus"].ToString() == (((Enum)ApptStatus.ASAP).ordinal()).ToString())
                {
                    text = "A";
                }
                 
            }
            else if (__dummyScrutVar0.equals("AssistantAbbr"))
            {
                if (isNote)
                {
                    text = "";
                }
                else
                {
                    text = dataRoww["assistantAbbr"].ToString();
                } 
            }
            else if (__dummyScrutVar0.equals("ChartNumAndName"))
            {
                text = dataRoww["chartNumAndName"].ToString();
            }
            else if (__dummyScrutVar0.equals("ChartNumber"))
            {
                text = dataRoww["chartNumber"].ToString();
            }
            else if (__dummyScrutVar0.equals("CreditType"))
            {
                text = dataRoww["CreditType"].ToString();
            }
            else if (__dummyScrutVar0.equals("Guardians"))
            {
                if (isNote)
                {
                    text = "";
                }
                else
                {
                    text = dataRoww["guardians"].ToString();
                } 
            }
            else if (__dummyScrutVar0.equals("HasIns[I]"))
            {
                text = dataRoww["hasIns[I]"].ToString();
            }
            else if (__dummyScrutVar0.equals("HmPhone"))
            {
                if (isNote)
                {
                    text = "";
                }
                else
                {
                    text = dataRoww["hmPhone"].ToString();
                } 
            }
            else if (__dummyScrutVar0.equals("InsToSend[!]"))
            {
                text = dataRoww["insToSend[!]"].ToString();
            }
            else if (__dummyScrutVar0.equals("Lab"))
            {
                if (isNote)
                {
                    text = "";
                }
                else
                {
                    text = dataRoww["lab"].ToString();
                } 
            }
            else if (__dummyScrutVar0.equals("MedOrPremed[+]"))
            {
                if (isNote)
                {
                    text = "";
                }
                else
                {
                    text = dataRoww["medOrPremed[+]"].ToString();
                } 
            }
            else if (__dummyScrutVar0.equals("MedUrgNote"))
            {
                if (isNote)
                {
                    text = "";
                }
                else
                {
                    text = dataRoww["MedUrgNote"].ToString();
                } 
            }
            else if (__dummyScrutVar0.equals("Note"))
            {
                text = dataRoww["Note"].ToString();
            }
            else if (__dummyScrutVar0.equals("PatientName"))
            {
                text = dataRoww["patientName"].ToString();
            }
            else if (__dummyScrutVar0.equals("PatientNameF"))
            {
                text = dataRoww["patientNameF"].ToString();
            }
            else if (__dummyScrutVar0.equals("PatNum"))
            {
                text = dataRoww["patNum"].ToString();
            }
            else if (__dummyScrutVar0.equals("PatNumAndName"))
            {
                text = dataRoww["patNumAndName"].ToString();
            }
            else if (__dummyScrutVar0.equals("PremedFlag"))
            {
                if (isNote)
                {
                    text = "";
                }
                else
                {
                    text = dataRoww["preMedFlag"].ToString();
                } 
            }
            else if (__dummyScrutVar0.equals("Procs"))
            {
                if (isNote)
                {
                    text = "";
                }
                else
                {
                    text = dataRoww["procs"].ToString();
                } 
            }
            else if (__dummyScrutVar0.equals("ProcsColored"))
            {
                String value = dataRoww["procsColored"].ToString();
                String[] lines = value.Split(new String[]{ "</span>" }, StringSplitOptions.RemoveEmptyEntries);
                Point tempPt = new Point();
                tempPt = drawLoc;
                int lastH = 0;
                int count = 1;
                for (int i = 0;i < lines.Length;i++)
                {
                    Match m = Regex.Match(lines[i], "^<span color=\"(-?[0-9]*)\">(.*)$");
                    String rgbInt = m.Result("$1");
                    String proc = m.Result("$2");
                    if (lines[i] != lines[lines.Length - 1])
                    {
                        proc += ",";
                    }
                     
                    if (StringSupport.equals(rgbInt, ""))
                    {
                        rgbInt = apptRows[elementI].ElementColorXml.ToString();
                    }
                     
                    Color c = Color.FromArgb(Convert.ToInt32(rgbInt));
                    StringFormat procFormat = new StringFormat();
                    RectangleF procRect = new RectangleF(0, 0, 1000, 1000);
                    CharacterRange[] ranges;
                    Region[] regions = new Region[1];
                    procFormat.SetMeasurableCharacterRanges(ranges);
                    regions = g.MeasureCharacterRanges(proc, baseFont, procRect, procFormat);
                    if (regions.Length == 0)
                    {
                        procRect = new RectangleF(0, 0, 0, 0);
                    }
                    else
                    {
                        procRect = regions[0].GetBounds(g);
                    } 
                    if (tempPt.X + procRect.Width > totalWidth)
                    {
                        tempPt.X = drawLoc.X;
                        tempPt.Y += lastH;
                        count++;
                    }
                     
                    SolidBrush sb = new SolidBrush(c);
                    g.DrawString(proc, baseFont, sb, tempPt);
                    disposeObjects(procFormat,sb);
                    tempPt.X += (int)procRect.Width + 3;
                    //+3 is room for spaces
                    if ((int)procRect.Height > lastH)
                    {
                        lastH = (int)procRect.Height;
                    }
                     
                }
                drawLoc.Y += lastH * count;
                return drawLoc;
            }
            else if (__dummyScrutVar0.equals("Production"))
            {
                if (isNote)
                {
                    text = "";
                }
                else
                {
                    text = dataRoww["production"].ToString();
                } 
            }
            else if (__dummyScrutVar0.equals("Provider"))
            {
                if (isNote)
                {
                    text = "";
                }
                else
                {
                    text = dataRoww["provider"].ToString();
                } 
            }
            else if (__dummyScrutVar0.equals("TimeAskedToArrive"))
            {
                if (isNote)
                {
                    text = "";
                }
                else
                {
                    text = dataRoww["timeAskedToArrive"].ToString();
                } 
            }
            else //could be blank
            if (__dummyScrutVar0.equals("WirelessPhone"))
            {
                if (isNote)
                {
                    text = "";
                }
                else
                {
                    text = dataRoww["wirelessPhone"].ToString();
                } 
            }
            else if (__dummyScrutVar0.equals("WkPhone"))
            {
                if (isNote)
                {
                    text = "";
                }
                else
                {
                    text = dataRoww["wkPhone"].ToString();
                } 
            }
                                         
        }  
        if (StringSupport.equals(text, "") && !isGraphic)
        {
            return drawLoc;
        }
         
        //next element will draw at the same position as this one would have.
        SolidBrush brush = new SolidBrush(apptRows[elementI].ElementColor);
        SolidBrush brushWhite = new SolidBrush(Color.White);
        //SolidBrush noteTitlebrush = new SolidBrush(DefC.Long[(int)DefCat.AppointmentColors][8].ItemColor);
        StringFormat format = new StringFormat();
        format.Alignment = StringAlignment.Near;
        int charactersFitted = new int();
        //not used, but required as 'out' param for measureString.
        int linesFilled = new int();
        SizeF noteSize = new SizeF();
        RectangleF rect = new RectangleF();
        RectangleF rectBack = new RectangleF();
        if (align == ApptViewAlignment.Main)
        {
            //always stacks vertical
            if (isGraphic)
            {
                Bitmap bitmap = new Bitmap(12, 12);
                noteSize = new SizeF(bitmap.Width, bitmap.Height);
                rect = new RectangleF(drawLoc, noteSize);
                Graphics gfx = Graphics.FromImage(bitmap);
                try
                {
                    {
                        gfx.SmoothingMode = SmoothingMode.HighQuality;
                        Color confirmColor = DefC.GetColor(DefCat.ApptConfirmed, PIn.Long(dataRoww["Confirmed"].ToString()));
                        SolidBrush confirmBrush = new SolidBrush(confirmColor);
                        gfx.FillEllipse(confirmBrush, 0, 0, 11, 11);
                        gfx.DrawEllipse(Pens.Black, 0, 0, 11, 11);
                        confirmBrush.Dispose();
                    }
                }
                finally
                {
                    if (gfx != null)
                        Disposable.mkDisposable(gfx).dispose();
                     
                }
                g.DrawImage(bitmap, drawLoc.X, drawLoc.Y);
                disposeObjects(brush,brushWhite,format,bitmap);
                return new Point(drawLoc.X, drawLoc.Y + (int)noteSize.Height);
            }
            else
            {
                noteSize = g.MeasureString(text, baseFont, (int)totalWidth - 9);
                //Problem: "limited-tooth bothering him ", the trailing space causes measuring error, resulting in m getting partially chopped off.
                //Tried TextRenderer, but it caused premature wrapping
                //Size noteSizeInt=TextRenderer.MeasureText(text,baseFont,new Size(totalWidth-9,1000));
                //noteSize=new SizeF(noteSizeInt.totalWidth,noteSizeInt.totalHeight);
                noteSize.Width = (float)Math.Ceiling((double)noteSize.Width);
                //round up to nearest int solves specific problem discussed above.
                if (drawLoc.Y + noteSize.Height > totalHeight && isPrinting)
                {
                    //This keeps text from drawing off the appointment when font is large. Only if isPrinting cause not sure if this will cause bugs.
                    //No need to do this check when drawing to the appt screen cause its just an image on a control which clips itself.
                    noteSize.Height = totalHeight - drawLoc.Y;
                }
                 
                RefSupport<int> refVar___0 = new RefSupport<int>();
                RefSupport<int> refVar___1 = new RefSupport<int>();
                g.MeasureString(text, baseFont, noteSize, format, refVar___0, refVar___1);
                charactersFitted = refVar___0.getValue();
                linesFilled = refVar___1.getValue();
                rect = new RectangleF(drawLoc, noteSize);
                g.DrawString(text, baseFont, brush, rect, format);
                disposeObjects(brush,brushWhite,format);
                return new Point(drawLoc.X, drawLoc.Y + linesFilled * ApptDrawing.LineH);
            } 
        }
        else if (align == ApptViewAlignment.UR)
        {
            if (stackBehavior == ApptViewStackBehavior.Vertical)
            {
                float w = totalWidth - 9;
                if (isGraphic)
                {
                    Bitmap bitmap = new Bitmap(12, 12);
                    noteSize = new SizeF(bitmap.Width, bitmap.Height);
                    Point drawLocThis = new Point(drawLoc.X - (int)noteSize.Width, drawLoc.Y + 1);
                    //upper left corner of this element
                    rect = new RectangleF(drawLoc, noteSize);
                    Graphics gfx = Graphics.FromImage(bitmap);
                    try
                    {
                        {
                            gfx.SmoothingMode = SmoothingMode.HighQuality;
                            Color confirmColor = DefC.GetColor(DefCat.ApptConfirmed, PIn.Long(dataRoww["Confirmed"].ToString()));
                            SolidBrush confirmBrush = new SolidBrush(confirmColor);
                            gfx.FillEllipse(confirmBrush, 0, 0, 11, 11);
                            gfx.DrawEllipse(Pens.Black, 0, 0, 11, 11);
                            confirmBrush.Dispose();
                        }
                    }
                    finally
                    {
                        if (gfx != null)
                            Disposable.mkDisposable(gfx).dispose();
                         
                    }
                    g.DrawImage(bitmap, drawLocThis.X, drawLocThis.Y);
                    disposeObjects(brush,brushWhite,format,bitmap);
                    return new Point(drawLoc.X, drawLoc.Y + (int)noteSize.Height);
                }
                else
                {
                    noteSize = g.MeasureString(text, baseFont, (int)w);
                    noteSize = new SizeF(noteSize.Width, ApptDrawing.LineH + 1);
                    //only allowed to be one line high.
                    if (noteSize.Width < 5)
                    {
                        noteSize = new SizeF(5, noteSize.Height);
                    }
                     
                    //g.MeasureString(text,baseFont,noteSize,format,out charactersFitted,out linesFilled);
                    Point drawLocThis = new Point(drawLoc.X - (int)noteSize.Width, drawLoc.Y);
                    //upper left corner of this element
                    rect = new RectangleF(drawLocThis, noteSize);
                    rectBack = new RectangleF(drawLocThis.X, drawLocThis.Y + 1, noteSize.Width, ApptDrawing.LineH);
                    if (StringSupport.equals(apptRows[elementI].ElementDesc, "MedOrPremed[+]") || StringSupport.equals(apptRows[elementI].ElementDesc, "HasIns[I]") || StringSupport.equals(apptRows[elementI].ElementDesc, "InsToSend[!]"))
                    {
                        g.FillRectangle(brush, rectBack);
                        g.DrawString(text, baseFont, Brushes.Black, rect, format);
                    }
                    else
                    {
                        g.FillRectangle(brushWhite, rectBack);
                        g.DrawString(text, baseFont, brush, rect, format);
                    } 
                    g.DrawRectangle(Pens.Black, rectBack.X, rectBack.Y, rectBack.Width, rectBack.Height);
                    disposeObjects(brush,brushWhite,format);
                    return new Point(drawLoc.X, drawLoc.Y + ApptDrawing.LineH);
                } 
            }
            else
            {
                //move down a certain number of lines for next element.
                //horizontal
                int w = drawLoc.X - 9;
                //drawLoc is upper right of each element.  The first element draws at (totalWidth-1,0).
                if (isGraphic)
                {
                    Bitmap bitmap = new Bitmap(12, 12);
                    noteSize = new SizeF(bitmap.Width, bitmap.Height);
                    Point drawLocThis = new Point(drawLoc.X - (int)noteSize.Width, drawLoc.Y + 1);
                    //upper left corner of this element
                    rect = new RectangleF(drawLoc, noteSize);
                    Graphics gfx = Graphics.FromImage(bitmap);
                    try
                    {
                        {
                            gfx.SmoothingMode = SmoothingMode.HighQuality;
                            Color confirmColor = DefC.GetColor(DefCat.ApptConfirmed, PIn.Long(dataRoww["Confirmed"].ToString()));
                            SolidBrush confirmBrush = new SolidBrush(confirmColor);
                            gfx.FillEllipse(confirmBrush, 0, 0, 11, 11);
                            gfx.DrawEllipse(Pens.Black, 0, 0, 11, 11);
                            confirmBrush.Dispose();
                        }
                    }
                    finally
                    {
                        if (gfx != null)
                            Disposable.mkDisposable(gfx).dispose();
                         
                    }
                    g.DrawImage(bitmap, drawLocThis.X, drawLocThis.Y);
                    disposeObjects(brush,brushWhite,format,bitmap);
                    return new Point(drawLoc.X - (int)noteSize.Width - 2, drawLoc.Y);
                }
                else
                {
                    noteSize = g.MeasureString(text, baseFont, w);
                    noteSize = new SizeF(noteSize.Width, ApptDrawing.LineH + 1);
                    //only allowed to be one line high.  Needs an extra pixel.
                    if (noteSize.Width < 5)
                    {
                        noteSize = new SizeF(5, noteSize.Height);
                    }
                     
                    Point drawLocThis = new Point(drawLoc.X - (int)noteSize.Width, drawLoc.Y);
                    //upper left corner of this element
                    rect = new RectangleF(drawLocThis, noteSize);
                    rectBack = new RectangleF(drawLocThis.X, drawLocThis.Y + 1, noteSize.Width, ApptDrawing.LineH);
                    if (StringSupport.equals(apptRows[elementI].ElementDesc, "MedOrPremed[+]") || StringSupport.equals(apptRows[elementI].ElementDesc, "HasIns[I]") || StringSupport.equals(apptRows[elementI].ElementDesc, "InsToSend[!]"))
                    {
                        g.FillRectangle(brush, rectBack);
                        g.DrawString(text, baseFont, Brushes.Black, rect, format);
                    }
                    else
                    {
                        g.FillRectangle(brushWhite, rectBack);
                        g.DrawString(text, baseFont, brush, rect, format);
                    } 
                    g.DrawRectangle(Pens.Black, rectBack.X, rectBack.Y, rectBack.Width, rectBack.Height);
                    disposeObjects(brush,brushWhite,format);
                    return new Point(drawLoc.X - (int)noteSize.Width - 1, drawLoc.Y);
                } 
            } 
        }
        else
        {
            //Move to left.  Might also have to subtract a little from x to space out elements.
            //LR
            if (stackBehavior == ApptViewStackBehavior.Vertical)
            {
                float w = totalWidth - 9;
                if (isGraphic)
                {
                    Bitmap bitmap = new Bitmap(12, 12);
                    noteSize = new SizeF(bitmap.Width, bitmap.Height);
                    Point drawLocThis = new Point(drawLoc.X - (int)noteSize.Width, drawLoc.Y + 1 - ApptDrawing.LineH);
                    //upper left corner of this element
                    rect = new RectangleF(drawLoc, noteSize);
                    Graphics gfx = Graphics.FromImage(bitmap);
                    try
                    {
                        {
                            gfx.SmoothingMode = SmoothingMode.HighQuality;
                            Color confirmColor = DefC.GetColor(DefCat.ApptConfirmed, PIn.Long(dataRoww["Confirmed"].ToString()));
                            SolidBrush confirmBrush = new SolidBrush(confirmColor);
                            gfx.FillEllipse(confirmBrush, 0, 0, 11, 11);
                            gfx.DrawEllipse(Pens.Black, 0, 0, 11, 11);
                            confirmBrush.Dispose();
                        }
                    }
                    finally
                    {
                        if (gfx != null)
                            Disposable.mkDisposable(gfx).dispose();
                         
                    }
                    g.DrawImage(bitmap, drawLocThis.X, drawLocThis.Y);
                    disposeObjects(brush,brushWhite,format,bitmap);
                    return new Point(drawLoc.X, drawLoc.Y - (int)noteSize.Height);
                }
                else
                {
                    noteSize = g.MeasureString(text, baseFont, (int)w);
                    noteSize = new SizeF(noteSize.Width, ApptDrawing.LineH + 1);
                    //only allowed to be one line high.  Needs an extra pixel.
                    if (noteSize.Width < 5)
                    {
                        noteSize = new SizeF(5, noteSize.Height);
                    }
                     
                    //g.MeasureString(text,baseFont,noteSize,format,out charactersFitted,out linesFilled);
                    Point drawLocThis = new Point(drawLoc.X - (int)noteSize.Width, drawLoc.Y - ApptDrawing.LineH);
                    //upper left corner of this element
                    rect = new RectangleF(drawLocThis, noteSize);
                    rectBack = new RectangleF(drawLocThis.X, drawLocThis.Y + 1, noteSize.Width, ApptDrawing.LineH);
                    if (StringSupport.equals(apptRows[elementI].ElementDesc, "MedOrPremed[+]") || StringSupport.equals(apptRows[elementI].ElementDesc, "HasIns[I]") || StringSupport.equals(apptRows[elementI].ElementDesc, "InsToSend[!]"))
                    {
                        g.FillRectangle(brush, rectBack);
                        g.DrawString(text, baseFont, Brushes.Black, rect, format);
                    }
                    else
                    {
                        g.FillRectangle(brushWhite, rectBack);
                        g.DrawString(text, baseFont, brush, rect, format);
                    } 
                    g.DrawRectangle(Pens.Black, rectBack.X, rectBack.Y, rectBack.Width, rectBack.Height);
                    disposeObjects(brush,brushWhite,format);
                    return new Point(drawLoc.X, drawLoc.Y - ApptDrawing.LineH);
                } 
            }
            else
            {
                //move up a certain number of lines for next element.
                //horizontal
                int w = drawLoc.X - 9;
                //drawLoc is upper right of each element.  The first element draws at (totalWidth-1,0).
                if (isGraphic)
                {
                    Bitmap bitmap = new Bitmap(12, 12);
                    noteSize = new SizeF(bitmap.Width, bitmap.Height);
                    Point drawLocThis = new Point(drawLoc.X - (int)noteSize.Width + 1, drawLoc.Y + 1 - ApptDrawing.LineH);
                    //upper left corner of this element
                    rect = new RectangleF(drawLoc, noteSize);
                    Graphics gfx = Graphics.FromImage(bitmap);
                    try
                    {
                        {
                            gfx.SmoothingMode = SmoothingMode.HighQuality;
                            Color confirmColor = DefC.GetColor(DefCat.ApptConfirmed, PIn.Long(dataRoww["Confirmed"].ToString()));
                            SolidBrush confirmBrush = new SolidBrush(confirmColor);
                            gfx.FillEllipse(confirmBrush, 0, 0, 11, 11);
                            gfx.DrawEllipse(Pens.Black, 0, 0, 11, 11);
                            confirmBrush.Dispose();
                        }
                    }
                    finally
                    {
                        if (gfx != null)
                            Disposable.mkDisposable(gfx).dispose();
                         
                    }
                    g.DrawImage(bitmap, drawLocThis.X, drawLocThis.Y);
                    disposeObjects(brush,brushWhite,format,bitmap);
                    return new Point(drawLoc.X - (int)noteSize.Width - 1, drawLoc.Y);
                }
                else
                {
                    noteSize = g.MeasureString(text, baseFont, w);
                    noteSize = new SizeF(noteSize.Width, ApptDrawing.LineH + 1);
                    //only allowed to be one line high.  Needs an extra pixel.
                    if (noteSize.Width < 5)
                    {
                        noteSize = new SizeF(5, noteSize.Height);
                    }
                     
                    Point drawLocThis = new Point(drawLoc.X - (int)noteSize.Width, drawLoc.Y - ApptDrawing.LineH);
                    //upper left corner of this element
                    rect = new RectangleF(drawLocThis, noteSize);
                    rectBack = new RectangleF(drawLocThis.X, drawLocThis.Y + 1, noteSize.Width, ApptDrawing.LineH);
                    if (StringSupport.equals(apptRows[elementI].ElementDesc, "MedOrPremed[+]") || StringSupport.equals(apptRows[elementI].ElementDesc, "HasIns[I]") || StringSupport.equals(apptRows[elementI].ElementDesc, "InsToSend[!]"))
                    {
                        g.FillRectangle(brush, rectBack);
                        g.DrawString(text, baseFont, Brushes.Black, rect, format);
                    }
                    else
                    {
                        g.FillRectangle(brushWhite, rectBack);
                        g.DrawString(text, baseFont, brush, rect, format);
                    } 
                    g.DrawRectangle(Pens.Black, rectBack.X, rectBack.Y, rectBack.Width, rectBack.Height);
                    disposeObjects(brush,brushWhite,format);
                    return new Point(drawLoc.X - (int)noteSize.Width - 1, drawLoc.Y);
                } 
            } 
        }  
    }

    //Move to left.  Subtract a little from x to space out elements.
    /**
    * This is only called when viewing appointments on the Appt module.  For Planned apt and pinboard, use SetSize instead so that the location won't change.  Pass 0 for startHour unless printing.  Pass visible ops for colsPerPage unless printing.  Pass 0 for pageColumn unless printing.
    */
    public static Point setLocation(DataRow dataRoww, int beginHour, int colsPerPage, int pageColumn) throws Exception {
        if (ApptDrawing.IsWeeklyView)
        {
            ApptSingleWidth = (int)ApptDrawing.ColAptWidth;
            location = new Point(convertToX(dataRoww,colsPerPage,pageColumn), convertToY(dataRoww,beginHour));
        }
        else
        {
            location = new Point(convertToX(dataRoww,colsPerPage,pageColumn) + 2, convertToY(dataRoww,beginHour));
            ApptSingleWidth = ApptDrawing.ColWidth - 5;
        } 
        return location;
    }

    /**
    * Used for Planned apt and pinboard instead of SetLocation so that the location won't be altered.
    */
    public static Size setSize(DataRow dataRoww) throws Exception {
        ApptSingleWidth = ApptDrawing.ColWidth - 5;
        if (ApptDrawing.IsWeeklyView)
        {
            ApptSingleWidth = (int)ApptDrawing.ColAptWidth;
        }
         
        //height is based on original 5 minute pattern. Might result in half-rows
        ApptSingleHeight = dataRoww["Pattern"].ToString().Length * ApptDrawing.LineH * ApptDrawing.RowsPerIncr;
        if (PrefC.getLong(PrefName.AppointmentTimeIncrement) == 10)
        {
            ApptSingleHeight = ApptSingleHeight / 2;
        }
         
        if (PrefC.getLong(PrefName.AppointmentTimeIncrement) == 15)
        {
            ApptSingleHeight = ApptSingleHeight / 3;
        }
         
        return new Size((int)ApptSingleWidth, (int)ApptSingleHeight);
    }

    /**
    * Called from SetLocation to establish X position of control.
    */
    public static int convertToX(DataRow dataRoww, int colsPerPage, int pageColumn) throws Exception {
        if (ApptDrawing.IsWeeklyView)
        {
            //the next few lines are because we start on Monday instead of Sunday
            int dayofweek = (int)PIn.DateT(dataRoww["AptDateTime"].ToString()).DayOfWeek - 1;
            if (dayofweek == -1)
            {
                dayofweek = 6;
            }
             
            return (int)(ApptDrawing.TimeWidth + ApptDrawing.ColDayWidth * (dayofweek)+1 + (ApptDrawing.ColAptWidth * (ApptDrawing.GetIndexOp(PIn.Long(dataRoww["Op"].ToString())) - (colsPerPage * pageColumn))));
        }
        else
        {
            return (int)(ApptDrawing.TimeWidth + ApptDrawing.ProvWidth * ApptDrawing.ProvCount + ApptDrawing.ColWidth * (ApptDrawing.GetIndexOp(PIn.Long(dataRoww["Op"].ToString())) - (colsPerPage * pageColumn)) + 1);
        } 
    }

    //Info.MyApt.Op))+1;
    /**
    * Called from SetLocation to establish Y position of control.  Also called from ContrAppt.RefreshDay when determining ProvBar markings. Does not round to the nearest row.
    */
    public static int convertToY(DataRow dataRoww, int beginHour) throws Exception {
        DateTime aptDateTime = PIn.DateT(dataRoww["AptDateTime"].ToString());
        int retVal = (int)(((double)(aptDateTime.Hour - beginHour) * (double)60 / (double)PrefC.getLong(PrefName.AppointmentTimeIncrement) + (double)aptDateTime.Minute / (double)PrefC.getLong(PrefName.AppointmentTimeIncrement)) * (double)ApptDrawing.LineH * ApptDrawing.RowsPerIncr);
        return retVal;
    }

    /**
    * This converts the dbPattern in 5 minute interval into the pattern that will be viewed based on RowsPerIncrement and AppointmentTimeIncrement.  So it will always depend on the current view.Therefore, it should only be used for visual display purposes rather than within the FormAptEdit. If height of appointment allows a half row, then this includes an increment for that half row.
    */
    public static String getPatternShowing(String dbPattern) throws Exception {
        StringBuilder strBTime = new StringBuilder();
        for (int i = 0;i < dbPattern.Length;i++)
        {
            for (int j = 0;j < ApptDrawing.RowsPerIncr;j++)
            {
                strBTime.Append(dbPattern.Substring(i, 1));
            }
            if (PrefC.getLong(PrefName.AppointmentTimeIncrement) == 10)
            {
                i++;
            }
             
            //skip
            if (PrefC.getLong(PrefName.AppointmentTimeIncrement) == 15)
            {
                i++;
                i++;
            }
             
        }
        return strBTime.ToString();
    }

    //skip two
    /**
    * Tests if the appt is in the allotted time frame and is in a visible operatory.  Returns false in order to skip drawing for apptointment printing.
    */
    public static boolean apptWithinTimeFrame(DataRow dataRoww, DateTime beginTime, DateTime endTime, int colsPerPage, int pageColumn) throws Exception {
        //Test if appts op is currently visible.
        boolean visible = false;
        if (!ApptDrawing.IsWeeklyView)
        {
            for (int i = 0;i < colsPerPage;i++)
            {
                if (i == ApptDrawing.VisOps.Count)
                {
                    return false;
                }
                 
                int k = colsPerPage * pageColumn + i;
                if (k >= ApptDrawing.VisOps.Count)
                {
                    return false;
                }
                 
                if (k == ApptDrawing.GetIndexOp(PIn.Long(dataRoww["Op"].ToString())))
                {
                    visible = true;
                    break;
                }
                 
            }
            if (!visible)
            {
                return false;
            }
             
        }
         
        //Op not visible so don't test time frame.
        //Test if any portion of appt is within time frame.
        TimeSpan aptTimeBegin = PIn.DateT(dataRoww["AptDateTime"].ToString()).TimeOfDay;
        TimeSpan aptTimeEnd = aptTimeBegin.Add(new TimeSpan(0, dataRoww["Pattern"].ToString().Length * 5, 0));
        int aptHourBegin = aptTimeBegin.Hours;
        int aptHourEnd = aptTimeEnd.Hours;
        if (aptHourEnd == 0)
        {
            aptHourEnd = 24;
        }
         
        int beginHour = beginTime.Hour;
        int endHour = endTime.Hour;
        if (endHour == 0)
        {
            endHour = 24;
        }
         
        if (aptHourBegin >= endHour || aptHourEnd < beginHour)
        {
            return false;
        }
         
        return true;
    }

    /**
    * Disposes objects with typeof Brush, Pen, StringFormat or Bitmap.
    */
    private static void disposeObjects(Object... disposables) throws Exception {
        for (int i = 0;i < disposables.Length;i++)
        {
            if (disposables[i] == null)
            {
                continue;
            }
             
            if (disposables[i].GetType() == Brush.class)
            {
                Brush b = (Brush)disposables[i];
                b.Dispose();
            }
            else if (disposables[i].GetType() == Pen.class)
            {
                Pen p = (Pen)disposables[i];
                p.Dispose();
            }
            else if (disposables[i].GetType() == StringFormat.class)
            {
                StringFormat sf = (StringFormat)disposables[i];
                sf.Dispose();
            }
            else if (disposables[i].GetType() == Bitmap.class)
            {
                Bitmap bmp = (Bitmap)disposables[i];
                bmp.Dispose();
            }
                
        }
    }

}


