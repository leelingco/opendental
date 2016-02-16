//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormEmailMessageEdit;
import OpenDental.FormSheetOutputFormat;
import OpenDental.GraphicsHelper;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDental.SheetCheckBox;
import OpenDental.SheetPrinting;
import OpenDental.SheetUtil;
import OpenDentBusiness.EmailAddress;
import OpenDentBusiness.EmailAddresses;
import OpenDentBusiness.EmailAttach;
import OpenDentBusiness.EmailMessage;
import OpenDentBusiness.EmailMessages;
import OpenDentBusiness.GrowthBehaviorEnum;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Referral;
import OpenDentBusiness.Referrals;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.Sheet;
import OpenDentBusiness.SheetField;
import OpenDentBusiness.SheetFields;
import OpenDentBusiness.SheetFieldType;
import OpenDentBusiness.SheetParameter;
import OpenDentBusiness.Sheets;
import OpenDentBusiness.SheetTypeEnum;
import OpenDentBusiness.TerminalActive;
import OpenDentBusiness.TerminalActives;

public class FormSheetFillEdit  extends Form 
{

    public Sheet SheetCur;
    private boolean mouseIsDown = new boolean();
    /**
    * A list of points for a line currently being drawn.  Once the mouse is raised, this list gets cleared.
    */
    private List<Point> PointList = new List<Point>();
    private PictureBox pictDraw = new PictureBox();
    private Image imgDraw = new Image();
    private boolean drawingsAltered = new boolean();
    public boolean RxIsControlled = new boolean();
    /**
    * When in terminal, some options are not visible.
    */
    public boolean IsInTerminal = new boolean();
    /**
    * If set to true, then this form will poll the database every 4 seconds, listening for a signal to close itself.  This is useful if the receptionist accidentally loads up the wrong patient.
    */
    public boolean TerminalListenShut = new boolean();
    public FormSheetFillEdit(Sheet sheet) throws Exception {
        initializeComponent();
        Lan.F(this);
        SheetCur = sheet;
        if (sheet.IsLandscape)
        {
            Width = sheet.Height + 190;
            Height = sheet.Width + 65;
        }
        else
        {
            Width = sheet.Width + 190;
            Height = sheet.Height + 65;
        } 
        if (Width > SystemInformation.WorkingArea.Width)
        {
            Width = SystemInformation.WorkingArea.Width;
        }
         
        if (Height > SystemInformation.WorkingArea.Height)
        {
            Height = SystemInformation.WorkingArea.Height;
        }
         
        PointList = new List<Point>();
    }

    private void formSheetFillEdit_Load(Object sender, EventArgs e) throws Exception {
        if (IsInTerminal)
        {
            labelDateTime.Visible = false;
            textDateTime.Visible = false;
            labelDescription.Visible = false;
            textDescription.Visible = false;
            labelNote.Visible = false;
            textNote.Visible = false;
            labelShowInTerminal.Visible = false;
            textShowInTerminal.Visible = false;
            butPrint.Visible = false;
            butPDF.Visible = false;
            butDelete.Visible = false;
            if (TerminalListenShut)
            {
                timer1.Enabled = true;
            }
             
        }
         
        if (SheetCur.IsLandscape)
        {
            panelMain.Width = SheetCur.Height;
            panelMain.Height = SheetCur.Width;
        }
        else
        {
            panelMain.Width = SheetCur.Width;
            panelMain.Height = SheetCur.Height;
        } 
        textDateTime.Text = SheetCur.DateTimeSheet.ToShortDateString() + " " + SheetCur.DateTimeSheet.ToShortTimeString();
        textDescription.Text = SheetCur.Description;
        textNote.Text = SheetCur.InternalNote;
        if (SheetCur.ShowInTerminal > 0)
        {
            textShowInTerminal.Text = SheetCur.ShowInTerminal.ToString();
        }
         
        layoutFields();
        if (SheetCur.getIsNew())
        {
            return ;
        }
         
        //from here on, only applies to existing sheets.
        if (!Security.isAuthorized(Permissions.SheetEdit,SheetCur.DateTimeSheet))
        {
            panelMain.Enabled = false;
            butOK.Enabled = false;
            butDelete.Enabled = false;
            return ;
        }
         
        //So user has permission
        boolean isSigned = false;
        for (int i = 0;i < SheetCur.SheetFields.Count;i++)
        {
            if (SheetCur.SheetFields[i].FieldType == SheetFieldType.SigBox && SheetCur.SheetFields[i].FieldValue.Length > 1)
            {
                isSigned = true;
                break;
            }
             
        }
        if (isSigned)
        {
            panelMain.Enabled = false;
            butUnlock.Visible = true;
        }
         
    }

    /**
    * Runs as the final step of loading the form, and also immediately after fields are moved down due to growth.
    */
    private void layoutFields() throws Exception {
        panelMain.Controls.Clear();
        RichTextBox textbox = new RichTextBox();
        //has to be richtextbox due to MS bug that doesn't show cursor.
        FontStyle style = new FontStyle();
        SheetCheckBox checkbox;
        //first, draw images---------------------------------------------------------------------------------------
        //might change this to only happen once when first loading form:
        if (pictDraw != null)
        {
            if (panelMain.Controls.Contains(pictDraw))
            {
                Controls.Remove(pictDraw);
            }
             
            pictDraw = null;
        }
         
        imgDraw = null;
        pictDraw = new PictureBox();
        if (SheetCur.IsLandscape)
        {
            imgDraw = new Bitmap(SheetCur.Height, SheetCur.Width);
            pictDraw.Width = SheetCur.Height;
            pictDraw.Height = SheetCur.Width;
        }
        else
        {
            imgDraw = new Bitmap(SheetCur.Width, SheetCur.Height);
            pictDraw.Width = SheetCur.Width;
            pictDraw.Height = SheetCur.Height;
        } 
        pictDraw.Location = new Point(0, 0);
        pictDraw.Image = (Image)imgDraw.Clone();
        pictDraw.SizeMode = PictureBoxSizeMode.StretchImage;
        panelMain.Controls.Add(pictDraw);
        panelMain.SendToBack();
        Graphics pictGraphics = Graphics.FromImage(imgDraw);
        for (Object __dummyForeachVar0 : SheetCur.SheetFields)
        {
            SheetField field = (SheetField)__dummyForeachVar0;
            if (field.FieldType != SheetFieldType.Image)
            {
                continue;
            }
             
            String filePathAndName = CodeBase.ODFileUtils.combinePaths(SheetUtil.getImagePath(),field.FieldName);
            Image img = null;
            if (StringSupport.equals(field.FieldName, "Patient Info.gif"))
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
            pictGraphics.DrawImage(img, field.XPos, field.YPos, field.Width, field.Height);
        }
        pictGraphics.Dispose();
        //Set mouse events for the pictDraw
        pictDraw.MouseDown += new MouseEventHandler(pictDraw_MouseDown);
        pictDraw.MouseMove += new MouseEventHandler(pictDraw_MouseMove);
        pictDraw.MouseUp += new MouseEventHandler(pictDraw_MouseUp);
        //draw drawings, rectangles, and lines-----------------------------------------------------------------------
        refreshPanel();
        for (Object __dummyForeachVar1 : SheetCur.SheetFields)
        {
            //draw textboxes----------------------------------------------------------------------------------------------
            SheetField field = (SheetField)__dummyForeachVar1;
            if (field.FieldType != SheetFieldType.InputField && field.FieldType != SheetFieldType.OutputText && field.FieldType != SheetFieldType.StaticText)
            {
                continue;
            }
             
            textbox = new RichTextBox();
            textbox.BorderStyle = BorderStyle.None;
            textbox.TabStop = false;
            //Only input fields allow tab stop (set below for input text).
            //textbox.Multiline=true;//due to MS malfunction at 9pt which cuts off the bottom of the text.
            if (field.FieldType == SheetFieldType.OutputText || field.FieldType == SheetFieldType.StaticText)
            {
            }
            else //textbox.BackColor=Color.White;
            //textbox.BackColor=Color.FromArgb(245,245,200);
            if (field.FieldType == SheetFieldType.InputField)
            {
                textbox.BackColor = Color.FromArgb(245, 245, 200);
                textbox.TabStop = (field.TabOrder == 0 ? false : true);
                textbox.TabIndex = field.TabOrder;
            }
              
            textbox.Location = new Point(field.XPos, field.YPos);
            textbox.Width = field.Width;
            textbox.ScrollBars = RichTextBoxScrollBars.None;
            textbox.Text = field.FieldValue;
            style = FontStyle.Regular;
            if (field.FontIsBold)
            {
                style = FontStyle.Bold;
            }
             
            textbox.Font = new Font(field.FontName, field.FontSize, style);
            if (field.Height < textbox.Font.Height + 2)
            {
                textbox.Multiline = false;
            }
            else
            {
                //textbox.AcceptsReturn=false;
                textbox.Multiline = true;
            } 
            //textbox.AcceptsReturn=true;
            textbox.Height = field.Height;
            //textbox.ScrollBars=RichTextBoxScrollBars.None;
            textbox.Tag = field;
            textbox.TextChanged += new EventHandler(text_TextChanged);
            panelMain.Controls.Add(textbox);
            textbox.BringToFront();
        }
        for (Object __dummyForeachVar2 : SheetCur.SheetFields)
        {
            //draw checkboxes----------------------------------------------------------------------------------------------
            SheetField field = (SheetField)__dummyForeachVar2;
            if (field.FieldType != SheetFieldType.CheckBox)
            {
                continue;
            }
             
            checkbox = new SheetCheckBox();
            if (StringSupport.equals(field.FieldValue, "X"))
            {
                checkbox.setIsChecked(true);
            }
             
            checkbox.Location = new Point(field.XPos, field.YPos);
            checkbox.Width = field.Width;
            checkbox.Height = field.Height;
            checkbox.Tag = field;
            checkbox.Click += new EventHandler(text_TextChanged);
            checkbox.TabStop = (field.TabOrder == 0 ? false : true);
            checkbox.TabIndex = field.TabOrder;
            panelMain.Controls.Add(checkbox);
            checkbox.BringToFront();
        }
        for (Object __dummyForeachVar3 : SheetCur.SheetFields)
        {
            //draw signature boxes----------------------------------------------------------------------------------------------
            SheetField field = (SheetField)__dummyForeachVar3;
            if (field.FieldType != SheetFieldType.SigBox)
            {
                continue;
            }
             
            OpenDental.UI.SignatureBoxWrapper sigBox = new OpenDental.UI.SignatureBoxWrapper();
            sigBox.Location = new Point(field.XPos, field.YPos);
            sigBox.Width = field.Width;
            sigBox.Height = field.Height;
            if (field.FieldValue.Length > 0)
            {
                //a signature is present
                boolean sigIsTopaz = false;
                if (field.FieldValue[0] == '1')
                {
                    sigIsTopaz = true;
                }
                 
                String signature = "";
                if (field.FieldValue.Length > 1)
                {
                    signature = field.FieldValue.Substring(1);
                }
                 
                String keyData = Sheets.getSignatureKey(SheetCur);
                sigBox.fillSignature(sigIsTopaz,keyData,signature);
            }
             
            sigBox.Tag = field;
            sigBox.TabStop = (field.TabOrder == 0 ? false : true);
            sigBox.TabIndex = field.TabOrder;
            panelMain.Controls.Add(sigBox);
            sigBox.BringToFront();
        }
    }

    /**
    * Only if TerminalListenShut.  Occurs every 4 seconds. Checks database for status changes.  Shouldn't happen very often, because it means user will lose all data that they may have entered.
    */
    private void timer1_Tick(Object sender, EventArgs e) throws Exception {
        TerminalActive terminal = TerminalActives.GetTerminal(Environment.MachineName);
        if (terminal == null)
        {
            //no terminal is supposed to be running here.
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (terminal.PatNum == SheetCur.PatNum)
        {
            return ;
        }
         
        //So terminal.PatNum must either be 0 or be an entirely different patient.
        DialogResult = DialogResult.Cancel;
    }

    /**
    * Triggered when any field value changes.  This immediately invalidates signatures.  It also causes fields to grow as needed and deselects other radiobuttons in a group.
    */
    private void text_TextChanged(Object sender, EventArgs e) throws Exception {
        for (Object __dummyForeachVar4 : panelMain.Controls)
        {
            Control control = (Control)__dummyForeachVar4;
            if (control.GetType() != OpenDental.UI.SignatureBoxWrapper.class)
            {
                continue;
            }
             
            if (control.Tag == null)
            {
                continue;
            }
             
            SheetField field = (SheetField)control.Tag;
            OpenDental.UI.SignatureBoxWrapper sigBox = (OpenDental.UI.SignatureBoxWrapper)control;
            sigBox.setInvalid();
        }
        if (sender.GetType() == SheetCheckBox.class)
        {
            SheetCheckBox checkbox = (SheetCheckBox)sender;
            if (checkbox.Tag == null)
            {
                return ;
            }
             
            if (!checkbox.getIsChecked())
            {
                return ;
            }
             
            //if user unchecked a radiobutton, nothing else happens
            SheetField fieldThis = (SheetField)checkbox.Tag;
            if (StringSupport.equals(fieldThis.RadioButtonGroup, "") && StringSupport.equals(fieldThis.RadioButtonValue, ""))
            {
                return ;
            }
             
            for (Object __dummyForeachVar5 : panelMain.Controls)
            {
                //if it's a checkbox instead of a radiobutton
                Control control = (Control)__dummyForeachVar5;
                //set some other radiobuttons to be not checked
                if (control.GetType() != SheetCheckBox.class)
                {
                    continue;
                }
                 
                if (control.Tag == null)
                {
                    continue;
                }
                 
                if (control == sender)
                {
                    continue;
                }
                 
                SheetField fieldOther = (SheetField)control.Tag;
                if (!StringSupport.equals(fieldThis.FieldName, fieldOther.FieldName))
                {
                    continue;
                }
                 
                //different radio group
                //If both checkbox field names are set to "misc" then we instead use the RadioButtonGroup as the actual radio button group name.
                if (StringSupport.equals(fieldThis.FieldName, "misc") && !StringSupport.equals(fieldThis.RadioButtonGroup, fieldOther.RadioButtonGroup))
                {
                    continue;
                }
                 
                ((SheetCheckBox)control).setIsChecked(false);
            }
            return ;
        }
         
        if (sender.GetType() != RichTextBox.class)
        {
            return ;
        }
         
        //since CheckBoxes also trigger this event for sig invalid.
        //everything below here is for growth calc.
        RichTextBox textBox = (RichTextBox)sender;
        //remember where we were
        int cursorPos = textBox.SelectionStart;
        //int boxX=textBox.Location.X;
        //int boxY=textBox.Location.Y;
        //string originalFieldValue=((SheetField)((RichTextBox)control).Tag).FieldValue;
        SheetField fld = (SheetField)textBox.Tag;
        if (fld.GrowthBehavior == GrowthBehaviorEnum.None)
        {
            return ;
        }
         
        fld.FieldValue = textBox.Text;
        Graphics g = this.CreateGraphics();
        FontStyle fontstyle = FontStyle.Regular;
        if (fld.FontIsBold)
        {
            fontstyle = FontStyle.Bold;
        }
         
        Font font = new Font(fld.FontName, fld.FontSize, fontstyle);
        int calcH = GraphicsHelper.measureStringH(g,fld.FieldValue,font,fld.Width);
        //(int)(g.MeasureString(fld.FieldValue,font,fld.Width).Height * 1.133f);//Seems to need 2 pixels per line of text to prevent hidden text due to scroll.
        calcH += font.Height + 2;
        //add one line just in case.
        g.Dispose();
        if (calcH <= fld.Height)
        {
            return ;
        }
         
        //no growth needed
        //the field height needs to change, so:
        int amountOfGrowth = calcH - fld.Height;
        fld.Height = calcH;
        fillFieldsFromControls();
        //We already changed the value of this field manually,
        //but if the other fields don't get changed, they will erroneously 'reset'.
        if (fld.GrowthBehavior == GrowthBehaviorEnum.DownGlobal)
        {
            SheetUtil.moveAllDownBelowThis(SheetCur,fld,amountOfGrowth);
        }
        else if (fld.GrowthBehavior == GrowthBehaviorEnum.DownLocal)
        {
            SheetUtil.moveAllDownWhichIntersect(SheetCur,fld,amountOfGrowth);
        }
          
        layoutFields();
        for (Object __dummyForeachVar6 : panelMain.Controls)
        {
            //find the original textbox, and put the cursor back where it belongs
            Control control = (Control)__dummyForeachVar6;
            if (control.GetType() == RichTextBox.class)
            {
                if ((SheetField)(control.Tag) == fld)
                {
                    ((RichTextBox)control).Select(cursorPos, 0);
                    ((RichTextBox)control).Focus();
                }
                 
            }
             
        }
    }

    //((RichTextBox)control).SelectionStart=cursorPos;
    /*private void panelDraw_Paint(object sender,PaintEventArgs e) {
    			//e.Graphics.DrawLine(Pens.Black,0,0,300,300);
    			for(int i=1;i<PointList.Count;i++){
    				e.Graphics.DrawLine(Pens.Black,PointList[i-1].X,PointList[i-1].Y,PointList[i].X,PointList[i].Y);
    			}
    		}*/
    private void pictDraw_MouseDown(Object sender, MouseEventArgs e) throws Exception {
        mouseIsDown = true;
        if (checkErase.Checked)
        {
            return ;
        }
         
        PointList.Add(new Point(e.X, e.Y));
        drawingsAltered = true;
    }

    private void pictDraw_MouseEnter(Object sender, EventArgs e) throws Exception {
    }

    private void pictDraw_MouseLeave(Object sender, EventArgs e) throws Exception {
    }

    private void pictDraw_MouseMove(Object sender, MouseEventArgs e) throws Exception {
        if (!mouseIsDown)
        {
            return ;
        }
         
        if (checkErase.Checked)
        {
            //look for any lines that intersect the "eraser".
            //since the line segments are so short, it's sufficient to check end points.
            //Point point;
            String[] xy = new String[]();
            String[] pointStr = new String[]();
            float x = new float();
            float y = new float();
            float dist = new float();
            //the distance between the point being tested and the center of the eraser circle.
            float radius = 8f;
            //by trial and error to achieve best feel.
            PointF eraserPt = new PointF(e.X + pictDraw.Left + 8.49f, e.Y + pictDraw.Top + 8.49f);
            for (Object __dummyForeachVar7 : SheetCur.SheetFields)
            {
                SheetField field = (SheetField)__dummyForeachVar7;
                if (field.FieldType != SheetFieldType.Drawing)
                {
                    continue;
                }
                 
                pointStr = field.FieldValue.Split(';');
                for (int p = 0;p < pointStr.Length;p++)
                {
                    xy = pointStr[p].Split(',');
                    if (xy.Length == 2)
                    {
                        x = PIn.Float(xy[0]);
                        y = PIn.Float(xy[1]);
                        dist = (float)Math.Sqrt(Math.Pow(Math.Abs(x - eraserPt.X), 2) + Math.Pow(Math.Abs(y - eraserPt.Y), 2));
                        if (dist <= radius)
                        {
                            //testing circle intersection here
                            SheetCur.SheetFields.Remove(field);
                            drawingsAltered = true;
                            refreshPanel();
                            return ;
                                ;
                        }
                         
                    }
                     
                }
            }
            return ;
        }
         
        PointList.Add(new Point(e.X, e.Y));
        //RefreshPanel();
        //just add the last line segment instead of redrawing the whole thing.
        Graphics g = Graphics.FromImage(pictDraw.Image);
        g.SmoothingMode = SmoothingMode.HighQuality;
        Pen pen = new Pen(Brushes.Black, 2f);
        int i = PointList.Count - 1;
        g.DrawLine(pen, PointList[i - 1].X, PointList[i - 1].Y, PointList[i].X, PointList[i].Y);
        pictDraw.Invalidate();
        g.Dispose();
    }

    private void pictDraw_MouseUp(Object sender, MouseEventArgs e) throws Exception {
        mouseIsDown = false;
        if (checkErase.Checked)
        {
            return ;
        }
         
        SheetField field = new SheetField();
        field.FieldType = SheetFieldType.Drawing;
        field.FieldName = "";
        field.FieldValue = "";
        for (int i = 0;i < PointList.Count;i++)
        {
            if (i > 0)
            {
                field.FieldValue += ";";
            }
             
            field.FieldValue += (PointList[i].X + pictDraw.Left) + "," + (PointList[i].Y + pictDraw.Top);
        }
        field.FontName = "";
        field.RadioButtonValue = "";
        SheetCur.SheetFields.Add(field);
        PointList.Clear();
        refreshPanel();
    }

    private void refreshPanel() throws Exception {
        Image img = (Image)imgDraw.Clone();
        Graphics g = Graphics.FromImage(img);
        g.SmoothingMode = SmoothingMode.HighQuality;
        //g.CompositingQuality=CompositingQuality.Default;
        Pen pen = new Pen(Brushes.Black, 2f);
        Pen pen2 = new Pen(Brushes.Black, 1f);
        String[] pointStr = new String[]();
        List<Point> points = new List<Point>();
        Point point = new Point();
        String[] xy = new String[]();
        for (int f = 0;f < SheetCur.SheetFields.Count;f++)
        {
            if (SheetCur.SheetFields[f].FieldType == SheetFieldType.Drawing)
            {
                pointStr = SheetCur.SheetFields[f].FieldValue.Split(';');
                points = new List<Point>();
                for (int p = 0;p < pointStr.Length;p++)
                {
                    xy = pointStr[p].Split(',');
                    if (xy.Length == 2)
                    {
                        point = new Point(PIn.Int(xy[0]), PIn.Int(xy[1]));
                        points.Add(point);
                    }
                     
                }
                for (int i = 1;i < points.Count;i++)
                {
                    g.DrawLine(pen, points[i - 1].X - pictDraw.Left, points[i - 1].Y - pictDraw.Top, points[i].X - pictDraw.Left, points[i].Y - pictDraw.Top);
                }
            }
             
            if (SheetCur.SheetFields[f].FieldType == SheetFieldType.Line)
            {
                g.DrawLine(pen2, SheetCur.SheetFields[f].XPos - pictDraw.Left, SheetCur.SheetFields[f].YPos - pictDraw.Top, SheetCur.SheetFields[f].XPos + SheetCur.SheetFields[f].Width - pictDraw.Left, SheetCur.SheetFields[f].YPos + SheetCur.SheetFields[f].Height - pictDraw.Top);
            }
             
            if (SheetCur.SheetFields[f].FieldType == SheetFieldType.Rectangle)
            {
                g.DrawRectangle(pen2, SheetCur.SheetFields[f].XPos - pictDraw.Left, SheetCur.SheetFields[f].YPos - pictDraw.Top, SheetCur.SheetFields[f].Width, SheetCur.SheetFields[f].Height);
            }
             
        }
        pictDraw.Image = img;
        g.Dispose();
    }

    private void checkErase_Click(Object sender, EventArgs e) throws Exception {
        if (checkErase.Checked)
        {
            pictDraw.Cursor = new Cursor(GetType(), "EraseCircle.cur");
        }
        else
        {
            pictDraw.Cursor = Cursors.Default;
        } 
    }

    private void panelColor_DoubleClick(Object sender, EventArgs e) throws Exception {
    }

    private void butPrint_Click(Object sender, EventArgs e) throws Exception {
        if (!tryToSaveData())
        {
            return ;
        }
         
        SheetCur = Sheets.getSheet(SheetCur.SheetNum);
        //whether this is a new sheet, or one pulled from the database,
        //it will have the extra parameter we are looking for.
        //A new sheet will also have a PatNum parameter which we will ignore.
        FormSheetOutputFormat FormS = new FormSheetOutputFormat();
        if (SheetCur.SheetType == SheetTypeEnum.ReferralSlip || SheetCur.SheetType == SheetTypeEnum.ReferralLetter)
        {
            FormS.PaperCopies = 2;
        }
        else
        {
            FormS.PaperCopies = 1;
        } 
        if (SheetCur.PatNum != 0 && SheetCur.SheetType != SheetTypeEnum.DepositSlip)
        {
            Patient pat = Patients.getPat(SheetCur.PatNum);
            if (SheetCur.SheetType == SheetTypeEnum.LabSlip)
            {
                FormS.IsForLab = true;
            }
            else //Changes label to "E-mail to Lab:"
            if (!StringSupport.equals(pat.Email, ""))
            {
                FormS.EmailPatOrLabAddress = pat.Email;
                //No need to email to a patient for sheet types: LabelPatient (0), LabelCarrier (1), LabelReferral (2), ReferralSlip (3), LabelAppointment (4), Rx (5), Consent (6), ReferralLetter (8), ExamSheet (13), DepositSlip (14)
                //The data is too private to email unencrypted for sheet types: PatientForm (9), RoutingSlip (10), MedicalHistory (11), LabSlip (12)
                //A patient might want email for the following sheet types and the data is not very private: PatientLetter (7)
                if (SheetCur.SheetType == SheetTypeEnum.PatientLetter)
                {
                    //This just defines the default selection. The user can manually change selections in FormSheetOutputFormat.
                    FormS.EmailPatOrLab = true;
                    FormS.PaperCopies--;
                }
                 
            }
              
        }
         
        Referral referral = null;
        if (SheetCur.SheetType == SheetTypeEnum.ReferralSlip || SheetCur.SheetType == SheetTypeEnum.ReferralLetter)
        {
            FormS.Email2Visible = true;
            SheetParameter parameter = SheetParameter.getParamByName(SheetCur.Parameters,"ReferralNum");
            if (parameter == null)
            {
                //it can be null sometimes because of old bug in db.
                FormS.Email2Visible = false;
            }
            else
            {
                //prevents trying to attach email to nonexistent referral.
                long referralNum = PIn.Long(parameter.ParamValue.ToString());
                referral = Referrals.getReferral(referralNum);
                if (!StringSupport.equals(referral.EMail, ""))
                {
                    FormS.Email2Address = referral.EMail;
                    FormS.Email2 = true;
                    FormS.PaperCopies--;
                }
                 
            } 
        }
        else
        {
            FormS.Email2Visible = false;
        } 
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        if (FormS.PaperCopies > 0)
        {
            SheetPrinting.print(SheetCur,FormS.PaperCopies,RxIsControlled);
        }
         
        EmailMessage message;
        Random rnd = new Random();
        String attachPath = EmailMessages.getEmailAttachPath();
        String fileName = new String();
        String filePathAndName = new String();
        EmailAddress emailAddress;
        Patient patCur = Patients.getPat(SheetCur.PatNum);
        if (patCur == null)
        {
            emailAddress = EmailAddresses.GetByClinic(0);
        }
        else
        {
            emailAddress = EmailAddresses.getByClinic(patCur.ClinicNum);
        } 
        //Graphics g=this.CreateGraphics();
        if (FormS.EmailPatOrLab)
        {
            fileName = DateTime.Now.ToString("yyyyMMdd") + "_" + DateTime.Now.TimeOfDay.Ticks.ToString() + rnd.Next(1000).ToString() + ".pdf";
            filePathAndName = CodeBase.ODFileUtils.combinePaths(attachPath,fileName);
            SheetPrinting.createPdf(SheetCur,filePathAndName);
            //Process.Start(filePathAndName);
            message = new EmailMessage();
            message.PatNum = SheetCur.PatNum;
            message.ToAddress = FormS.EmailPatOrLabAddress;
            message.FromAddress = emailAddress.SenderAddress;
            //Can be blank just as it could with the old pref.
            message.Subject = SheetCur.Description.ToString();
            //this could be improved
            EmailAttach attach = new EmailAttach();
            String shortFileName = Regex.Replace(SheetCur.Description.ToString(), "[^\\w\'@-_()&]", "");
            attach.DisplayedFileName = shortFileName + ".pdf";
            attach.ActualFileName = fileName;
            message.Attachments.Add(attach);
            FormEmailMessageEdit FormE = new FormEmailMessageEdit(message);
            FormE.IsNew = true;
            FormE.ShowDialog();
        }
         
        if ((SheetCur.SheetType == SheetTypeEnum.ReferralSlip || SheetCur.SheetType == SheetTypeEnum.ReferralLetter) && FormS.Email2)
        {
            //email referral
            fileName = DateTime.Now.ToString("yyyyMMdd") + "_" + DateTime.Now.TimeOfDay.Ticks.ToString() + rnd.Next(1000).ToString() + ".pdf";
            filePathAndName = CodeBase.ODFileUtils.combinePaths(attachPath,fileName);
            SheetPrinting.createPdf(SheetCur,filePathAndName);
            //Process.Start(filePathAndName);
            message = new EmailMessage();
            message.PatNum = SheetCur.PatNum;
            message.ToAddress = FormS.Email2Address;
            message.FromAddress = emailAddress.SenderAddress;
            //Can be blank just as it could with the old pref.
            message.Subject = Lan.g(this,"RE: ") + Patients.getLim(SheetCur.PatNum).getNameLF();
            //works even if patnum invalid
            //SheetCur.Description.ToString()+" to "+Referrals.GetNameFL(referral.ReferralNum);//this could be improved
            EmailAttach attach = new EmailAttach();
            String shortFileName = Regex.Replace(SheetCur.Description.ToString(), "[^\\w,\'@-_()&]", "");
            attach.DisplayedFileName = shortFileName + ".pdf";
            attach.ActualFileName = fileName;
            message.Attachments.Add(attach);
            FormEmailMessageEdit FormE = new FormEmailMessageEdit(message);
            FormE.IsNew = true;
            FormE.ShowDialog();
        }
         
        //g.Dispose();
        DialogResult = DialogResult.OK;
    }

    private void butPDF_Click(Object sender, EventArgs e) throws Exception {
        if (!tryToSaveData())
        {
            return ;
        }
         
        SheetCur = Sheets.getSheet(SheetCur.SheetNum);
        String filePathAndName = Path.ChangeExtension(Path.GetTempFileName(), ".pdf");
        //Graphics g=this.CreateGraphics();
        SheetPrinting.createPdf(SheetCur,filePathAndName);
        //g.Dispose();
        Process.Start(filePathAndName);
        SecurityLogs.makeLogEntry(Permissions.SheetEdit,SheetCur.PatNum,SheetCur.Description + " from " + SheetCur.DateTimeSheet.ToShortDateString() + " pdf was created");
        DialogResult = DialogResult.OK;
    }

    private void butUnlock_Click(Object sender, EventArgs e) throws Exception {
        //we already know the user has permission, because otherwise, button is not visible.
        panelMain.Enabled = true;
        butUnlock.Visible = false;
    }

    private boolean tryToSaveData() throws Exception {
        if (!butOK.Enabled)
        {
            return true;
        }
         
        //if the OK button is not enabled, user does not have permission.
        if (!StringSupport.equals(textShowInTerminal.errorProvider1.GetError(textShowInTerminal), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return false;
        }
         
        DateTime dateTimeSheet = DateTime.MinValue;
        try
        {
            dateTimeSheet = DateTime.Parse(textDateTime.Text);
        }
        catch (Exception __dummyCatchVar0)
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return false;
        }

        SheetCur.DateTimeSheet = dateTimeSheet;
        SheetCur.Description = textDescription.Text;
        SheetCur.InternalNote = textNote.Text;
        SheetCur.ShowInTerminal = PIn.Byte(textShowInTerminal.Text);
        fillFieldsFromControls();
        //But SheetNums will still be 0 for a new sheet.
        boolean isNew = SheetCur.getIsNew();
        if (isNew)
        {
            Sheets.insert(SheetCur);
        }
        else
        {
            Sheets.update(SheetCur);
        } 
        List<SheetField> drawingList = new List<SheetField>();
        for (Object __dummyForeachVar8 : SheetCur.SheetFields)
        {
            SheetField fld = (SheetField)__dummyForeachVar8;
            if (fld.FieldType == SheetFieldType.SigBox)
            {
                continue;
            }
             
            //done in a separate step
            if (fld.FieldType == SheetFieldType.Image || fld.FieldType == SheetFieldType.Rectangle || fld.FieldType == SheetFieldType.Line)
            {
                if (!fld.getIsNew())
                {
                    continue;
                }
                 
            }
             
            //it only saves them when the sheet is first created because user can't edit anyway.
            fld.SheetNum = SheetCur.SheetNum;
            //whether or not isnew
            if (fld.FieldType == SheetFieldType.Drawing)
            {
                fld.setIsNew(true);
                drawingList.Add(fld);
            }
            else
            {
                if (fld.getIsNew())
                {
                    SheetFields.insert(fld);
                }
                else
                {
                    SheetFields.update(fld);
                } 
            } 
        }
        if (drawingsAltered)
        {
            //drawings get saved as a group rather than with the other fields.
            SheetFields.SetDrawings(drawingList, SheetCur.SheetNum);
        }
         
        if (isNew)
        {
            Sheets.saveParameters(SheetCur);
        }
         
        //SigBoxes must come after ALL other types in order for the keyData to be in the right order.
        SheetField field;
        for (Object __dummyForeachVar9 : panelMain.Controls)
        {
            Control control = (Control)__dummyForeachVar9;
            if (control.GetType() != OpenDental.UI.SignatureBoxWrapper.class)
            {
                continue;
            }
             
            if (control.Tag == null)
            {
                continue;
            }
             
            field = (SheetField)control.Tag;
            OpenDental.UI.SignatureBoxWrapper sigBox = (OpenDental.UI.SignatureBoxWrapper)control;
            if (sigBox.getSigChanged())
            {
                //refresh the fields so they are in the correct order
                SheetFields.getFieldsAndParameters(SheetCur);
                boolean sigIsTopaz = sigBox.getSigIsTopaz();
                String keyData = Sheets.getSignatureKey(SheetCur);
                String signature = sigBox.getSignature(keyData);
                field.FieldValue = "";
                if (!StringSupport.equals(signature, ""))
                {
                    if (sigIsTopaz)
                    {
                        field.FieldValue += "1";
                    }
                    else
                    {
                        field.FieldValue += "0";
                    } 
                    field.FieldValue += signature;
                }
                 
            }
             
            field.SheetNum = SheetCur.SheetNum;
            //whether or not isnew
            if (isNew)
            {
                //is this really testing the proper object?
                SheetFields.insert(field);
            }
            else
            {
                SheetFields.update(field);
            } 
        }
        return true;
    }

    /**
    * This is always done before the save process.  But it's also done before bumping down fields due to growth behavior.
    */
    private void fillFieldsFromControls() throws Exception {
        for (Object __dummyForeachVar10 : panelMain.Controls)
        {
            //SheetField field;
            //Images------------------------------------------------------
            //Images can't be changed in this UI
            //RichTextBoxes-----------------------------------------------
            Control control = (Control)__dummyForeachVar10;
            if (control.GetType() != RichTextBox.class)
            {
                continue;
            }
             
            if (control.Tag == null)
            {
                continue;
            }
             
            //field=(SheetField)control.Tag;
            ((SheetField)control.Tag).FieldValue = control.Text;
        }
        for (Object __dummyForeachVar11 : panelMain.Controls)
        {
            //CheckBoxes-----------------------------------------------
            Control control = (Control)__dummyForeachVar11;
            if (control.GetType() != SheetCheckBox.class)
            {
                continue;
            }
             
            if (control.Tag == null)
            {
                continue;
            }
             
            //field=(SheetField)control.Tag;
            if (((SheetCheckBox)control).getIsChecked())
            {
                ((SheetField)control.Tag).FieldValue = "X";
            }
            else
            {
                ((SheetField)control.Tag).FieldValue = "";
            } 
        }
    }

    //Rectangles and lines-----------------------------------------
    //Rectangles and lines can't be changed in this UI
    //Drawings----------------------------------------------------
    //Drawings data is already saved to fields
    //SigBoxes---------------------------------------------------
    //SigBoxes won't be strictly checked for validity
    //or data saved to the field until it's time to actually save to the database.
    /**
    * Returns true when all of the sheet fields with IsRequired set to true have a value set. Otherwise, a message box shows and false is returned.
    */
    private boolean verifyRequiredFields() throws Exception {
        fillFieldsFromControls();
        for (Object __dummyForeachVar13 : panelMain.Controls)
        {
            Control control = (Control)__dummyForeachVar13;
            if (control.Tag == null)
            {
                continue;
            }
             
            if (control.GetType() == RichTextBox.class)
            {
                SheetField field = (SheetField)control.Tag;
                if (field.FieldType != SheetFieldType.InputField)
                {
                    continue;
                }
                 
                RichTextBox inputBox = (RichTextBox)control;
                if (field.IsRequired && StringSupport.equals(inputBox.Text.Trim(), ""))
                {
                    MessageBox.Show(Lan.g(this,"You must enter a value for") + " " + field.FieldName + " " + Lan.g(this,"before continuing."));
                    return false;
                }
                 
            }
            else if (control.GetType() == OpenDental.UI.SignatureBoxWrapper.class)
            {
                SheetField field = (SheetField)control.Tag;
                if (field.FieldType != SheetFieldType.SigBox)
                {
                    continue;
                }
                 
                OpenDental.UI.SignatureBoxWrapper sigBox = (OpenDental.UI.SignatureBoxWrapper)control;
                if (field.IsRequired && (!sigBox.getIsValid() || sigBox.getSigIsBlank()))
                {
                    MsgBox.show(this,"Signature required");
                    return false;
                }
                 
            }
            else if (control.GetType() == SheetCheckBox.class)
            {
                //Radio button groups or misc checkboxes
                SheetField field = (SheetField)control.Tag;
                if (field.IsRequired && !StringSupport.equals(field.FieldValue, "X"))
                {
                    //required but this one not checked
                    //first, checkboxes that are not radiobuttons.  For example, a checkbox at bottom of web form used in place of signature.
                    //doesn't belong to a built-in group
                    if (StringSupport.equals(field.RadioButtonValue, "") && StringSupport.equals(field.RadioButtonGroup, ""))
                    {
                        //doesn't belong to a custom group
                        //field.FieldName is always "misc"
                        //int widthActual=(SheetCur.IsLandscape?SheetCur.Height:SheetCur.Width);
                        //int heightActual=(SheetCur.IsLandscape?SheetCur.Width:SheetCur.Height);
                        //int topMidBottom=(heightActual/3)
                        MessageBox.Show(Lan.g(this,"You must check the required checkbox."));
                        return false;
                    }
                    else
                    {
                        //then radiobuttons (of both kinds)
                        //All radio buttons within a group should either all be marked required or all be marked not required.
                        //Not the most efficient check, but there won't usually be more than a few hundred items so the user will not ever notice. We can speed up later if needed.
                        boolean valueSet = false;
                        //we will be checking to see if at least one in the group has a value
                        int numGroupButtons = 0;
                        for (Object __dummyForeachVar12 : panelMain.Controls)
                        {
                            //a count of the buttons in the group
                            Control control2 = (Control)__dummyForeachVar12;
                            //loop through all controls in the sheet
                            if (control2.GetType() != SheetCheckBox.class)
                            {
                                continue;
                            }
                             
                            //skip everything that's not a checkbox
                            SheetField field2 = (SheetField)control2.Tag;
                            //whether built-in or custom, this makes sure it's a match.
                            //the other comparison will also match because they are empty strings
                            //if they are in the same group ("" for built-in, some string for custom group)
                            if (field2.RadioButtonGroup.ToLower() == field.RadioButtonGroup.ToLower() && StringSupport.equals(field2.FieldName, field.FieldName))
                            {
                                //"misc" for custom group, some string for built in groups.
                                numGroupButtons++;
                                if (StringSupport.equals(field2.FieldValue, "X"))
                                {
                                    valueSet = true;
                                    break;
                                }
                                 
                            }
                             
                        }
                        if (numGroupButtons > 0 && !valueSet)
                        {
                            //there is not at least one radiobutton in the group that's checked.
                            if (!StringSupport.equals(field.RadioButtonGroup, ""))
                            {
                                //if they are in a custom group
                                MessageBox.Show(Lan.g(this,"You must select a value for radio button group") + " '" + field.RadioButtonGroup + "'. ");
                            }
                            else
                            {
                                MessageBox.Show(Lan.g(this,"You must select a value for radio button group") + " '" + field.FieldName + "'. ");
                            } 
                            return false;
                        }
                         
                    } 
                }
                 
            }
               
        }
        return true;
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (SheetCur.getIsNew())
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (!MsgBox.show(this,true,"Delete?"))
        {
            return ;
        }
         
        Sheets.deleteObject(SheetCur.SheetNum);
        SecurityLogs.makeLogEntry(Permissions.SheetEdit,SheetCur.PatNum,SheetCur.Description + " deleted from " + SheetCur.DateTimeSheet.ToShortDateString());
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (!verifyRequiredFields())
        {
            return ;
        }
         
        if (!tryToSaveData())
        {
            return ;
        }
         
        SecurityLogs.makeLogEntry(Permissions.SheetEdit,SheetCur.PatNum,SheetCur.Description + " from " + SheetCur.DateTimeSheet.ToShortDateString());
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
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        this.components = new System.ComponentModel.Container();
        this.textNote = new System.Windows.Forms.TextBox();
        this.labelNote = new System.Windows.Forms.Label();
        this.labelDateTime = new System.Windows.Forms.Label();
        this.panel1 = new System.Windows.Forms.Panel();
        this.panelMain = new System.Windows.Forms.Panel();
        this.checkErase = new System.Windows.Forms.CheckBox();
        this.textDescription = new System.Windows.Forms.TextBox();
        this.labelDescription = new System.Windows.Forms.Label();
        this.labelShowInTerminal = new System.Windows.Forms.Label();
        this.timer1 = new System.Windows.Forms.Timer(this.components);
        this.textDateTime = new System.Windows.Forms.TextBox();
        this.butUnlock = new OpenDental.UI.Button();
        this.textShowInTerminal = new OpenDental.ValidNumber();
        this.butPDF = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.butPrint = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.panel1.SuspendLayout();
        this.SuspendLayout();
        //
        // textNote
        //
        this.textNote.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.textNote.Location = new System.Drawing.Point(633, 107);
        this.textNote.Multiline = true;
        this.textNote.Name = "textNote";
        this.textNote.Size = new System.Drawing.Size(146, 90);
        this.textNote.TabIndex = 6;
        this.textNote.TabStop = false;
        //
        // labelNote
        //
        this.labelNote.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.labelNote.Location = new System.Drawing.Point(632, 88);
        this.labelNote.Name = "labelNote";
        this.labelNote.Size = new System.Drawing.Size(147, 16);
        this.labelNote.TabIndex = 7;
        this.labelNote.Text = "Internal Note";
        this.labelNote.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // labelDateTime
        //
        this.labelDateTime.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.labelDateTime.Location = new System.Drawing.Point(632, 6);
        this.labelDateTime.Name = "labelDateTime";
        this.labelDateTime.Size = new System.Drawing.Size(84, 16);
        this.labelDateTime.TabIndex = 76;
        this.labelDateTime.Text = "Date time";
        this.labelDateTime.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // panel1
        //
        this.panel1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.panel1.AutoScroll = true;
        this.panel1.Controls.Add(this.panelMain);
        this.panel1.Location = new System.Drawing.Point(3, 3);
        this.panel1.Name = "panel1";
        this.panel1.Size = new System.Drawing.Size(617, 657);
        this.panel1.TabIndex = 78;
        //
        // panelMain
        //
        this.panelMain.BackColor = System.Drawing.SystemColors.Window;
        this.panelMain.Location = new System.Drawing.Point(0, 0);
        this.panelMain.Name = "panelMain";
        this.panelMain.Size = new System.Drawing.Size(549, 513);
        this.panelMain.TabIndex = 0;
        //
        // checkErase
        //
        this.checkErase.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.checkErase.Location = new System.Drawing.Point(698, 342);
        this.checkErase.Name = "checkErase";
        this.checkErase.Size = new System.Drawing.Size(89, 20);
        this.checkErase.TabIndex = 81;
        this.checkErase.TabStop = false;
        this.checkErase.Text = "Eraser Tool";
        this.checkErase.UseVisualStyleBackColor = true;
        this.checkErase.Click += new System.EventHandler(this.checkErase_Click);
        //
        // textDescription
        //
        this.textDescription.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.textDescription.Location = new System.Drawing.Point(633, 66);
        this.textDescription.Name = "textDescription";
        this.textDescription.Size = new System.Drawing.Size(146, 20);
        this.textDescription.TabIndex = 84;
        this.textDescription.TabStop = false;
        //
        // labelDescription
        //
        this.labelDescription.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.labelDescription.Location = new System.Drawing.Point(632, 47);
        this.labelDescription.Name = "labelDescription";
        this.labelDescription.Size = new System.Drawing.Size(147, 16);
        this.labelDescription.TabIndex = 85;
        this.labelDescription.Text = "Description";
        this.labelDescription.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // labelShowInTerminal
        //
        this.labelShowInTerminal.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.labelShowInTerminal.Location = new System.Drawing.Point(632, 204);
        this.labelShowInTerminal.Name = "labelShowInTerminal";
        this.labelShowInTerminal.Size = new System.Drawing.Size(120, 16);
        this.labelShowInTerminal.TabIndex = 86;
        this.labelShowInTerminal.Text = "Show Order In Kiosk";
        this.labelShowInTerminal.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // timer1
        //
        this.timer1.Interval = 4000;
        this.timer1.Tick += new System.EventHandler(this.timer1_Tick);
        //
        // textDateTime
        //
        this.textDateTime.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.textDateTime.Location = new System.Drawing.Point(635, 25);
        this.textDateTime.Name = "textDateTime";
        this.textDateTime.Size = new System.Drawing.Size(144, 20);
        this.textDateTime.TabIndex = 88;
        this.textDateTime.TabStop = false;
        //
        // butUnlock
        //
        this.butUnlock.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butUnlock.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butUnlock.setAutosize(true);
        this.butUnlock.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butUnlock.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butUnlock.setCornerRadius(4F);
        this.butUnlock.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butUnlock.Location = new System.Drawing.Point(697, 492);
        this.butUnlock.Name = "butUnlock";
        this.butUnlock.Size = new System.Drawing.Size(81, 24);
        this.butUnlock.TabIndex = 89;
        this.butUnlock.TabStop = false;
        this.butUnlock.Text = "Unlock";
        this.butUnlock.Visible = false;
        this.butUnlock.Click += new System.EventHandler(this.butUnlock_Click);
        //
        // textShowInTerminal
        //
        this.textShowInTerminal.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.textShowInTerminal.Location = new System.Drawing.Point(750, 203);
        this.textShowInTerminal.setMaxVal(127);
        this.textShowInTerminal.setMinVal(1);
        this.textShowInTerminal.Name = "textShowInTerminal";
        this.textShowInTerminal.Size = new System.Drawing.Size(29, 20);
        this.textShowInTerminal.TabIndex = 87;
        this.textShowInTerminal.TabStop = false;
        //
        // butPDF
        //
        this.butPDF.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPDF.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butPDF.setAutosize(true);
        this.butPDF.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPDF.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPDF.setCornerRadius(4F);
        this.butPDF.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPDF.Location = new System.Drawing.Point(698, 425);
        this.butPDF.Name = "butPDF";
        this.butPDF.Size = new System.Drawing.Size(81, 24);
        this.butPDF.TabIndex = 83;
        this.butPDF.TabStop = false;
        this.butPDF.Text = "Create PDF";
        this.butPDF.Click += new System.EventHandler(this.butPDF_Click);
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
        this.butDelete.Location = new System.Drawing.Point(698, 550);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(81, 24);
        this.butDelete.TabIndex = 79;
        this.butDelete.TabStop = false;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butPrint
        //
        this.butPrint.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrint.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butPrint.setAutosize(true);
        this.butPrint.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrint.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrint.setCornerRadius(4F);
        this.butPrint.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrint.Location = new System.Drawing.Point(698, 395);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(81, 24);
        this.butPrint.TabIndex = 80;
        this.butPrint.TabStop = false;
        this.butPrint.Text = "Print/Email";
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(698, 606);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(81, 24);
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
        this.butCancel.Location = new System.Drawing.Point(698, 636);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(81, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.TabStop = false;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormSheetFillEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(790, 672);
        this.Controls.Add(this.butUnlock);
        this.Controls.Add(this.textDateTime);
        this.Controls.Add(this.textShowInTerminal);
        this.Controls.Add(this.labelShowInTerminal);
        this.Controls.Add(this.textDescription);
        this.Controls.Add(this.labelDescription);
        this.Controls.Add(this.butPDF);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.checkErase);
        this.Controls.Add(this.panel1);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.labelDateTime);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.labelNote);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormSheetFillEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Fill Sheet";
        this.Load += new System.EventHandler(this.FormSheetFillEdit_Load);
        this.panel1.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.TextBox textNote = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelNote = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelDateTime = new System.Windows.Forms.Label();
    private System.Windows.Forms.Panel panel1 = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Panel panelMain = new System.Windows.Forms.Panel();
    private OpenDental.UI.Button butDelete;
    private OpenDental.UI.Button butPrint;
    private System.Windows.Forms.CheckBox checkErase = new System.Windows.Forms.CheckBox();
    private OpenDental.UI.Button butPDF;
    private System.Windows.Forms.TextBox textDescription = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelDescription = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelShowInTerminal = new System.Windows.Forms.Label();
    private OpenDental.ValidNumber textShowInTerminal;
    private System.Windows.Forms.Timer timer1 = new System.Windows.Forms.Timer();
    private System.Windows.Forms.TextBox textDateTime = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butUnlock;
}


