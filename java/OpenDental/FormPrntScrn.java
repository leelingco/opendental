//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:31 PM
//

package OpenDental;

import OpenDental.FormPrntScrn;
import OpenDental.Lan;
import OpenDental.PrinterL;
import OpenDental.Properties.Resources;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.PrintSituation;

/**
* 
*/
public class FormPrntScrn  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Drawing.Printing.PrintDocument pd2 = new System.Drawing.Printing.PrintDocument();
    private System.Windows.Forms.PrintPreviewControl printPreviewControl2 = new System.Windows.Forms.PrintPreviewControl();
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.SaveFileDialog saveFileDialog2 = new System.Windows.Forms.SaveFileDialog();
    private IDataObject clipboard = new IDataObject();
    private Bitmap imageTemp = new Bitmap();
    private int xPos = new int();
    //x position of image being printed
    private int yPos = new int();
    //y position of image being printed
    private int horRes = 100;
    private int vertRes = 100;
    //		private int startCropX;
    //		private int startCropY;
    //		private int endCropX;//for cropping will use later
    //		private int endCropY;//for cropping will use later
    private int docWidth = new int();
    private int docHeight = new int();
    private int leftBound = new int();
    private int rightBound = new int();
    private int topBound = new int();
    private int bottomBound = new int();
    //		private Rectangle recCrop;//for cropping will use later
    //    private bool MouseIsDown=false;//for cropping will use later
    //		private Graphics g;
    private System.Windows.Forms.TextBox textMouseX = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textMouseY = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butPrint;
    private OpenDental.UI.Button butZoomIn;
    private OpenDental.UI.Button butZoomOut;
    private OpenDental.UI.Button butExport;
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public FormPrntScrn() throws Exception {
        initializeComponent();
        Lan.f(this);
    }

    /**
    * 
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

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormPrntScrn.class);
        this.label1 = new System.Windows.Forms.Label();
        this.printPreviewControl2 = new System.Windows.Forms.PrintPreviewControl();
        this.pd2 = new System.Drawing.Printing.PrintDocument();
        this.butCancel = new OpenDental.UI.Button();
        this.saveFileDialog2 = new System.Windows.Forms.SaveFileDialog();
        this.textMouseX = new System.Windows.Forms.TextBox();
        this.textMouseY = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.butPrint = new OpenDental.UI.Button();
        this.butZoomIn = new OpenDental.UI.Button();
        this.butZoomOut = new OpenDental.UI.Button();
        this.butExport = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(190, 100);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(416, 23);
        this.label1.TabIndex = 0;
        this.label1.Text = "Once this is functioning, you can preview the image here before printing";
        //
        // printPreviewControl2
        //
        this.printPreviewControl2.AutoZoom = false;
        this.printPreviewControl2.Location = new System.Drawing.Point(0, 0);
        this.printPreviewControl2.Name = "printPreviewControl2";
        this.printPreviewControl2.Size = new System.Drawing.Size(842, 538);
        this.printPreviewControl2.TabIndex = 1;
        this.printPreviewControl2.Zoom = 1;
        this.printPreviewControl2.MouseMove += new System.Windows.Forms.MouseEventHandler(this.printPreviewControl2_MouseMove);
        this.printPreviewControl2.MouseDown += new System.Windows.Forms.MouseEventHandler(this.printPreviewControl2_MouseDown);
        this.printPreviewControl2.MouseUp += new System.Windows.Forms.MouseEventHandler(this.printPreviewControl2_MouseUp);
        //
        // pd2
        //
        this.pd2.PrintPage += new System.Drawing.Printing.PrintPageEventHandler(this.pd2_PrintPage);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(884, 759);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 4;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // textMouseX
        //
        this.textMouseX.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.textMouseX.Location = new System.Drawing.Point(274, 763);
        this.textMouseX.Name = "textMouseX";
        this.textMouseX.Size = new System.Drawing.Size(56, 20);
        this.textMouseX.TabIndex = 7;
        this.textMouseX.Visible = false;
        //
        // textMouseY
        //
        this.textMouseY.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.textMouseY.Location = new System.Drawing.Point(394, 763);
        this.textMouseY.Name = "textMouseY";
        this.textMouseY.Size = new System.Drawing.Size(56, 20);
        this.textMouseY.TabIndex = 8;
        this.textMouseY.Visible = false;
        //
        // label2
        //
        this.label2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.label2.Location = new System.Drawing.Point(209, 764);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(63, 17);
        this.label2.TabIndex = 11;
        this.label2.Text = "Mouse X";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.label2.Visible = false;
        //
        // label3
        //
        this.label3.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.label3.Location = new System.Drawing.Point(334, 763);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(56, 20);
        this.label3.TabIndex = 12;
        this.label3.Text = "MouseY";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.label3.Visible = false;
        //
        // butPrint
        //
        this.butPrint.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrint.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butPrint.setAutosize(true);
        this.butPrint.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrint.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrint.setCornerRadius(4F);
        this.butPrint.Image = Resources.getbutPrintSmall();
        this.butPrint.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrint.Location = new System.Drawing.Point(678, 759);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(75, 26);
        this.butPrint.TabIndex = 13;
        this.butPrint.Text = "&Print";
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // butZoomIn
        //
        this.butZoomIn.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butZoomIn.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butZoomIn.setAutosize(true);
        this.butZoomIn.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butZoomIn.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butZoomIn.setCornerRadius(4F);
        this.butZoomIn.Image = Resources.getbutZoomIn();
        this.butZoomIn.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butZoomIn.Location = new System.Drawing.Point(502, 759);
        this.butZoomIn.Name = "butZoomIn";
        this.butZoomIn.Size = new System.Drawing.Size(77, 26);
        this.butZoomIn.TabIndex = 14;
        this.butZoomIn.Text = "&Zoom +";
        this.butZoomIn.Click += new System.EventHandler(this.butZoomIn_Click);
        //
        // butZoomOut
        //
        this.butZoomOut.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butZoomOut.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butZoomOut.setAutosize(true);
        this.butZoomOut.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butZoomOut.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butZoomOut.setCornerRadius(4F);
        this.butZoomOut.Image = Resources.getbutZoomOut();
        this.butZoomOut.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butZoomOut.Location = new System.Drawing.Point(591, 759);
        this.butZoomOut.Name = "butZoomOut";
        this.butZoomOut.Size = new System.Drawing.Size(75, 26);
        this.butZoomOut.TabIndex = 15;
        this.butZoomOut.Text = "Zoom -";
        this.butZoomOut.Click += new System.EventHandler(this.butZoomOut_Click);
        //
        // butExport
        //
        this.butExport.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butExport.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butExport.setAutosize(true);
        this.butExport.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butExport.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butExport.setCornerRadius(4F);
        this.butExport.Image = Resources.getbutExport();
        this.butExport.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butExport.Location = new System.Drawing.Point(765, 759);
        this.butExport.Name = "butExport";
        this.butExport.Size = new System.Drawing.Size(75, 26);
        this.butExport.TabIndex = 16;
        this.butExport.Text = "&Export";
        this.butExport.Click += new System.EventHandler(this.butExport_Click);
        //
        // FormPrntScrn
        //
        this.AcceptButton = this.butPrint;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(976, 792);
        this.Controls.Add(this.butExport);
        this.Controls.Add(this.butZoomOut);
        this.Controls.Add(this.butZoomIn);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textMouseY);
        this.Controls.Add(this.textMouseX);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.printPreviewControl2);
        this.Controls.Add(this.label1);
        this.Cursor = System.Windows.Forms.Cursors.Default;
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormPrntScrn";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Prnt Scrn Tool";
        this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
        this.Load += new System.EventHandler(this.FormPrntScrn_Load);
        this.Layout += new System.Windows.Forms.LayoutEventHandler(this.FormPrntScrn_Layout);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formPrntScrn_Load(Object sender, System.EventArgs e) throws Exception {
        try
        {
            clipboard = Clipboard.GetDataObject();
            imageTemp = (Bitmap)(clipboard.GetData(DataFormats.Bitmap));
            //gets image off clipboard
            setImageSize();
            printReport(true);
        }
        catch (Exception __dummyCatchVar0)
        {
            //sets image as preview document
            MessageBox.Show(Lan.g(this,"Before using this tool, you must first save a screen shot by holding the Alt key down and pressing the PrntScrn button which is just above and to the right of the Backspace key.  You will not notice anything happen, but now you will have a screenshot in memory.  Then, open this tool again to view or print your screenshot."));
            butPrint.Enabled = false;
            butExport.Enabled = false;
            DialogResult = DialogResult.Cancel;
        }
    
    }

    private void formPrntScrn_Layout(Object sender, System.Windows.Forms.LayoutEventArgs e) throws Exception {
        printPreviewControl2.Location = new Point(0, 0);
        printPreviewControl2.Height = ClientSize.Height - 43;
        printPreviewControl2.Width = ClientSize.Width;
        docWidth = 811;
        //for cropping will use later
        docHeight = 776;
        //for cropping will use later
        leftBound = (ClientSize.Width - docWidth) / 2;
        //for cropping will use later
        rightBound = ((ClientSize.Width - docWidth) / 2) + docWidth;
        //for cropping will use later
        topBound = (ClientSize.Height - docHeight) / 2;
        //for cropping will use later
        bottomBound = ((ClientSize.Height - docHeight) / 2) + docHeight;
    }

    //for cropping will use later
    private void setImageSize() throws Exception {
        if (imageTemp.Width > 750)
        {
            horRes += (int)((imageTemp.Width - 750) / 8);
        }
        else
        {
            //        horRes-=(int)((750-imageTemp.Width)/8);
            horRes = 100;
        } 
        if (imageTemp.Height > 1000)
        {
            vertRes += ((imageTemp.Height - 1000) / 8);
        }
        else
        {
            //        vertRes-=((1000-imageTemp.Height)/8);
            vertRes = 100;
        } 
        if (horRes > vertRes)
        {
            vertRes = horRes;
        }
        else
        {
            horRes = vertRes;
        } 
        imageTemp.SetResolution(horRes, vertRes);
    }

    //sets resolution to fit image on screen
    /**
    * 
    */
    public void printReport(boolean justPreview) throws Exception {
        pd2 = new PrintDocument();
        pd2.PrintPage += new PrintPageEventHandler(this.pd2_PrintPage);
        try
        {
            //			pd2.DefaultPageSettings.Margins= new Margins(10,40,40,60);
            if (justPreview)
            {
                printPreviewControl2.Document = pd2;
            }
            else if (PrinterL.SetPrinter(pd2, PrintSituation.Default, 0, "Print screen printed"))
            {
                pd2.Print();
            }
              
        }
        catch (Exception __dummyCatchVar1)
        {
            MessageBox.Show(Lan.g(this,"Printer not available"));
        }
    
    }

    private void butPrint_Click(Object sender, System.EventArgs e) throws Exception {
        printReport(false);
        DialogResult = DialogResult.Cancel;
    }

    private void pd2_PrintPage(Object sender, System.Drawing.Printing.PrintPageEventArgs e) throws Exception {
        //		g=e.Graphics;
        xPos = 15;
        //starting pos
        yPos = (int)27.5;
        //starting pos
        e.Graphics.DrawImage(imageTemp, xPos, yPos);
    }

    //e.Graphics.DrawImage(imageTemp,e.Graphics.VisibleClipBounds.Left,e.Graphics.VisibleClipBounds.Top);
    private void butExport_Click(Object sender, System.EventArgs e) throws Exception {
        saveFileDialog2 = new SaveFileDialog();
        saveFileDialog2.AddExtension = true;
        saveFileDialog2.Title = Lan.g(this,"Select Folder to Save Image To");
        saveFileDialog2.InitialDirectory = PrefC.getString(PrefName.ExportPath);
        saveFileDialog2.DefaultExt = "jpg";
        saveFileDialog2.Filter = "jpg files(*.jpg)|*.jpg|All files(*.*)|*.*";
        saveFileDialog2.FilterIndex = 1;
        if (saveFileDialog2.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        try
        {
            imageTemp.Save(saveFileDialog2.FileName, ImageFormat.Jpeg);
        }
        catch (Exception __dummyCatchVar2)
        {
            MessageBox.Show(Lan.g(this,"File in use by another program.  Close and try again."));
        }
    
    }

    private void butZoomIn_Click(Object sender, System.EventArgs e) throws Exception {
        if (horRes > 5)
        {
            horRes = horRes - (int)Math.Round(horRes * .25);
            vertRes = vertRes - (int)Math.Round(vertRes * .25);
            imageTemp.SetResolution(horRes, vertRes);
            printPreviewControl2.Document = pd2;
        }
        else
        {
            horRes = 5;
            vertRes = 5;
            imageTemp.SetResolution(horRes, vertRes);
            printPreviewControl2.Document = pd2;
        } 
    }

    private void butZoomOut_Click(Object sender, System.EventArgs e) throws Exception {
        if (horRes < 1000)
        {
            horRes = horRes + (int)Math.Round(horRes * .25);
            vertRes = vertRes + (int)Math.Round(vertRes * .25);
            imageTemp.SetResolution(horRes, vertRes);
            //sets resolution to fit image on screen
            printPreviewControl2.Document = pd2;
        }
        else
        {
            horRes = 1000;
            vertRes = 1000;
            imageTemp.SetResolution(horRes, vertRes);
            //sets resolution to fit image on screen
            printReport(true);
            printPreviewControl2.Document = pd2;
        } 
    }

    private void printPreviewControl2_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
    }

    /*for cropping will use later
      	
    		  if(e.X >= leftBound && e.X <= rightBound && e.Y >= topBound && e.Y <= bottomBound)
    			  startCropX=e.X;
    		    startCropY=e.Y;
    			  MouseIsDown=true;
    */
    private void printPreviewControl2_MouseUp(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
    }

    /*for cropping will use later
        
    		 if(!MouseIsDown){
            return; 
    			}
    			if(MessageBox.Show("Crop to Rectangle?","",MessageBoxButtons.OKCancel)!=DialogResult.OK) {
    			  g.Clear(Color.Black);
    				printPreviewControl2.Document=pd2;
            MouseIsDown=false;
    				return;
    			}
          MouseIsDown=false;
    			g.Clear(Color.DarkGray);
    			endCropX=e.X;
    			endCropY=e.Y;
          //Math.Abs gets the absolute value of an operation. This ensures positive value 
    			recCrop=new Rectangle(startCropX,startCropY,(int)Math.Abs(endCropX-startCropX),(int)Math.Abs(endCropY-startCropY));
    			MessageBox.Show(imageTemp.Width+"  "+imageTemp.Height);
    			imageTemp=imageTemp.Clone(recCrop,PixelFormat.DontCare);
    			MessageBox.Show(imageTemp.Width+"  "+imageTemp.Height);
    			setImageSize();
          printPreviewControl2.Document=pd2;
    */
    private void printPreviewControl2_MouseMove(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
    }

    /*for cropping will use later	
      	
        	textMouseX.Text=e.X.ToString();
    			textMouseY.Text=e.Y.ToString();
    		  if(MouseIsDown && e.X >= leftBound+xPos && e.X <= rightBound-40 && e.Y >= topBound && e.Y <= bottomBound){
    				g=printPreviewControl2.CreateGraphics();
    //        g.DrawImage(imageTemp,leftBound,topBound,imageTemp.Width,imageTemp.Height);
    //  			g.Clear(Color.DarkGray);
    				g.DrawImageUnscaled(imageTemp,(int)(leftBound+xPos-2.0001),(int)(topBound+5.5));//5
            if(e.X>startCropX){
    					if(e.Y>startCropY){
    				    recCrop=new Rectangle(startCropX,startCropY,(e.X-startCropX),(e.Y-startCropY));
    				  }
    					else{
    				    recCrop=new Rectangle(startCropX,e.Y,(e.X-startCropX),(startCropY-e.Y));
    					}
    				}//end if(e.X>startCropX)
    				else{
    					if(e.Y>startCropY){
    				    recCrop=new Rectangle(e.X,startCropY,(startCropX-e.X),(e.Y-startCropY));
    					}
    					else{
    				    recCrop=new Rectangle(e.X,e.Y,(startCropX-e.X),(startCropY-e.Y));
    					}
    				}//end else
    			  g.DrawRectangle(Pens.Blue,recCrop);
    			}//end of if(MouseIsDown && e.X > leftBound && e.X < rightBound && e.Y > topBound && e.Y < bottomBound)
    */
    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


