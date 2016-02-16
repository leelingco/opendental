//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:55 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormTPsign;
import OpenDental.Lan;
import OpenDental.PrinterL;
import OpenDental.UI.__MultiODToolBarButtonClickEventHandler;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.ProcTP;
import OpenDentBusiness.ProcTPs;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.TreatPlan;
import OpenDentBusiness.TreatPlans;

//using Microsoft.Vsa;
/**
* 
*/
public class FormTPsign  extends System.Windows.Forms.Form 
{
    private System.ComponentModel.IContainer components = new System.ComponentModel.IContainer();
    /**
    * 
    */
    public int TotalPages = new int();
    private OpenDental.UI.ODToolBar ToolBarMain;
    private System.Windows.Forms.ImageList imageListMain = new System.Windows.Forms.ImageList();
    private System.Windows.Forms.PrintPreviewControl previewContr = new System.Windows.Forms.PrintPreviewControl();
    /**
    * 
    */
    public PrintDocument Document = new PrintDocument();
    private OpenDental.UI.SignatureBox sigBox;
    private Panel panelSig = new Panel();
    private Label label1 = new Label();
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butTopazSign;
    private OpenDental.UI.Button butClearSig;
    private Label labelInvalidSig = new Label();
    /**
    * Used to display Topaz signatures on Windows. Is added dynamically to avoid native code references crashing MONO.
    */
    private Control sigBoxTopaz = new Control();
    private boolean SigChanged = new boolean();
    public TreatPlan TPcur;
    /**
    * Must be sorted by primary key.
    */
    private List<ProcTP> proctpList = new List<ProcTP>();
    //private bool allowTopaz;
    /**
    * 
    */
    public FormTPsign() throws Exception {
        initializeComponent();
        //Required for Windows Form Designer support
        //allowTopaz=(Environment.OSVersion.Platform!=PlatformID.Unix && !CodeBase.ODEnvironment.Is64BitOperatingSystem());
        sigBox.setTabletState(1);
        //It starts out accepting input. It will be set to 0 if a sig is already present.  It will be set back to 1 if note changes or if user clicks Clear.
        //if(!allowTopaz) {
        //  butTopazSign.Visible=false;
        //          sigBox.Visible=true;
        //}
        //else{
        //Add signature box for Topaz signatures.
        sigBoxTopaz = CodeBase.TopazWrapper.getTopaz();
        sigBoxTopaz.Location = sigBox.Location;
        //this puts both boxes in the same spot.
        sigBoxTopaz.Name = "sigBoxTopaz";
        sigBoxTopaz.Size = new System.Drawing.Size(362, 79);
        sigBoxTopaz.TabIndex = 92;
        sigBoxTopaz.Text = "sigPlusNET1";
        sigBoxTopaz.Visible = false;
        panelSig.Controls.Add(sigBoxTopaz);
    }

    //}
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

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormTPsign.class);
        this.imageListMain = new System.Windows.Forms.ImageList(this.components);
        this.previewContr = new System.Windows.Forms.PrintPreviewControl();
        this.panelSig = new System.Windows.Forms.Panel();
        this.labelInvalidSig = new System.Windows.Forms.Label();
        this.butTopazSign = new OpenDental.UI.Button();
        this.butClearSig = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.sigBox = new OpenDental.UI.SignatureBox();
        this.ToolBarMain = new OpenDental.UI.ODToolBar();
        this.panelSig.SuspendLayout();
        this.SuspendLayout();
        //
        // imageListMain
        //
        this.imageListMain.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageListMain.ImageStream")));
        this.imageListMain.TransparentColor = System.Drawing.Color.Transparent;
        this.imageListMain.Images.SetKeyName(0, "");
        this.imageListMain.Images.SetKeyName(1, "");
        this.imageListMain.Images.SetKeyName(2, "");
        //
        // previewContr
        //
        this.previewContr.AutoZoom = false;
        this.previewContr.Location = new System.Drawing.Point(10, 41);
        this.previewContr.Name = "previewContr";
        this.previewContr.Size = new System.Drawing.Size(806, 423);
        this.previewContr.TabIndex = 6;
        //
        // panelSig
        //
        this.panelSig.Controls.Add(this.labelInvalidSig);
        this.panelSig.Controls.Add(this.butTopazSign);
        this.panelSig.Controls.Add(this.butClearSig);
        this.panelSig.Controls.Add(this.butCancel);
        this.panelSig.Controls.Add(this.butOK);
        this.panelSig.Controls.Add(this.label1);
        this.panelSig.Controls.Add(this.sigBox);
        this.panelSig.Dock = System.Windows.Forms.DockStyle.Bottom;
        this.panelSig.Location = new System.Drawing.Point(0, 562);
        this.panelSig.Name = "panelSig";
        this.panelSig.Size = new System.Drawing.Size(842, 92);
        this.panelSig.TabIndex = 92;
        //
        // labelInvalidSig
        //
        this.labelInvalidSig.BackColor = System.Drawing.SystemColors.Window;
        this.labelInvalidSig.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelInvalidSig.Location = new System.Drawing.Point(251, 13);
        this.labelInvalidSig.Name = "labelInvalidSig";
        this.labelInvalidSig.Size = new System.Drawing.Size(196, 59);
        this.labelInvalidSig.TabIndex = 99;
        this.labelInvalidSig.Text = "Invalid Signature -  Document or note has changed since it was signed.";
        this.labelInvalidSig.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        //
        // butTopazSign
        //
        this.butTopazSign.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butTopazSign.setAutosize(true);
        this.butTopazSign.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butTopazSign.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butTopazSign.setCornerRadius(4F);
        this.butTopazSign.Location = new System.Drawing.Point(537, 35);
        this.butTopazSign.Name = "butTopazSign";
        this.butTopazSign.Size = new System.Drawing.Size(81, 25);
        this.butTopazSign.TabIndex = 98;
        this.butTopazSign.Text = "Sign Topaz";
        this.butTopazSign.UseVisualStyleBackColor = true;
        this.butTopazSign.Click += new System.EventHandler(this.butTopazSign_Click);
        //
        // butClearSig
        //
        this.butClearSig.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClearSig.setAutosize(true);
        this.butClearSig.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClearSig.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClearSig.setCornerRadius(4F);
        this.butClearSig.Location = new System.Drawing.Point(537, 4);
        this.butClearSig.Name = "butClearSig";
        this.butClearSig.Size = new System.Drawing.Size(81, 25);
        this.butClearSig.TabIndex = 97;
        this.butClearSig.Text = "Clear Sig";
        this.butClearSig.Click += new System.EventHandler(this.butClearSig_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(741, 57);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 25);
        this.butCancel.TabIndex = 94;
        this.butCancel.Text = "Cancel";
        this.butCancel.UseVisualStyleBackColor = true;
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(741, 25);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 25);
        this.butOK.TabIndex = 93;
        this.butOK.Text = "OK";
        this.butOK.UseVisualStyleBackColor = true;
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // label1
        //
        this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label1.Location = new System.Drawing.Point(7, 4);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(153, 41);
        this.label1.TabIndex = 92;
        this.label1.Text = "Please Sign Here --->";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // sigBox
        //
        this.sigBox.Location = new System.Drawing.Point(162, 3);
        this.sigBox.Name = "sigBox";
        this.sigBox.Size = new System.Drawing.Size(362, 79);
        this.sigBox.TabIndex = 91;
        this.sigBox.MouseUp += new System.Windows.Forms.MouseEventHandler(this.sigBox_MouseUp);
        //
        // ToolBarMain
        //
        this.ToolBarMain.Dock = System.Windows.Forms.DockStyle.Top;
        this.ToolBarMain.setImageList(this.imageListMain);
        this.ToolBarMain.Location = new System.Drawing.Point(0, 0);
        this.ToolBarMain.Name = "ToolBarMain";
        this.ToolBarMain.Size = new System.Drawing.Size(842, 25);
        this.ToolBarMain.TabIndex = 5;
        this.ToolBarMain.ButtonClick = __MultiODToolBarButtonClickEventHandler.combine(this.ToolBarMain.ButtonClick,new OpenDental.UI.ODToolBarButtonClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
                this.ToolBarMain_ButtonClick(sender, e);
            }

            public List<OpenDental.UI.ODToolBarButtonClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODToolBarButtonClickEventHandler> ret = new ArrayList<OpenDental.UI.ODToolBarButtonClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // FormTPsign
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(842, 654);
        this.Controls.Add(this.panelSig);
        this.Controls.Add(this.ToolBarMain);
        this.Controls.Add(this.previewContr);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormTPsign";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Report";
        this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
        this.Load += new System.EventHandler(this.FormTPsign_Load);
        this.Layout += new System.Windows.Forms.LayoutEventHandler(this.FormReport_Layout);
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormTPsign_FormClosing);
        this.panelSig.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private void formTPsign_Load(Object sender, System.EventArgs e) throws Exception {
        //this window never comes up for new TP.  Always saved ahead of time.
        if (!Security.isAuthorized(Permissions.TreatPlanEdit,TPcur.DateTP))
        {
            butOK.Enabled = false;
            sigBox.Enabled = false;
            butClearSig.Enabled = false;
            butTopazSign.Enabled = false;
        }
         
        layoutToolBar();
        ToolBarMain.getButtons().get___idx("FullPage").setPushed(true);
        previewContr.Location = new Point(0, ToolBarMain.Bottom);
        previewContr.Size = new Size(ClientRectangle.Width, ClientRectangle.Height - ToolBarMain.Height - panelSig.Height);
        if (Document.DefaultPageSettings.PrintableArea.Height == 0)
        {
            Document.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
        }
         
        setSize();
        previewContr.Document = Document;
        ToolBarMain.getButtons().get___idx("PageNum").setText((previewContr.StartPage + 1).ToString() + " / " + TotalPages.ToString());
        labelInvalidSig.Visible = false;
        sigBox.Visible = true;
        proctpList = ProcTPs.refreshForTP(TPcur.TreatPlanNum);
        if (TPcur.SigIsTopaz)
        {
            if (!StringSupport.equals(TPcur.Signature, ""))
            {
                //if(allowTopaz) {
                sigBox.Visible = false;
                sigBoxTopaz.Visible = true;
                CodeBase.TopazWrapper.clearTopaz(sigBoxTopaz);
                CodeBase.TopazWrapper.setTopazCompressionMode(sigBoxTopaz,0);
                CodeBase.TopazWrapper.setTopazEncryptionMode(sigBoxTopaz,0);
                String keystring = TreatPlans.getHashString(TPcur,proctpList);
                CodeBase.TopazWrapper.setTopazKeyString(sigBoxTopaz,keystring);
                CodeBase.TopazWrapper.setTopazEncryptionMode(sigBoxTopaz,2);
                //high encryption
                CodeBase.TopazWrapper.setTopazCompressionMode(sigBoxTopaz,2);
                //high encryption
                CodeBase.TopazWrapper.setTopazSigString(sigBoxTopaz,TPcur.Signature);
                sigBoxTopaz.Refresh();
                //If sig is not showing, then try encryption mode 3 for signatures signed with old SigPlusNet.dll.
                if (CodeBase.TopazWrapper.getTopazNumberOfTabletPoints(sigBoxTopaz) == 0)
                {
                    CodeBase.TopazWrapper.setTopazEncryptionMode(sigBoxTopaz,3);
                    //Unknown mode (told to use via TopazSystems)
                    CodeBase.TopazWrapper.setTopazSigString(sigBoxTopaz,TPcur.Signature);
                }
                 
                if (CodeBase.TopazWrapper.getTopazNumberOfTabletPoints(sigBoxTopaz) == 0)
                {
                    labelInvalidSig.Visible = true;
                }
                 
            }
             
        }
        else
        {
            //}
            if (!StringSupport.equals(TPcur.Signature, ""))
            {
                sigBox.Visible = true;
                sigBoxTopaz.Visible = false;
                sigBox.clearTablet();
                //sigBox.SetSigCompressionMode(0);
                //sigBox.SetEncryptionMode(0);
                sigBox.setKeyString(TreatPlans.getHashString(TPcur,proctpList));
                //"0000000000000000");
                //sigBox.SetAutoKeyData(ProcCur.Note+ProcCur.UserNum.ToString());
                //sigBox.SetEncryptionMode(2);//high encryption
                //sigBox.SetSigCompressionMode(2);//high compression
                sigBox.setSigString(TPcur.Signature);
                if (sigBox.numberOfTabletPoints() == 0)
                {
                    labelInvalidSig.Visible = true;
                }
                 
                sigBox.setTabletState(0);
            }
             
        } 
    }

    //not accepting input.  To accept input, change the note, or clear the sig.
    private void setSize() throws Exception {
        if (ToolBarMain.getButtons().get___idx("FullPage").getPushed())
        {
            //if document fits within window, then don't zoom it bigger; leave it at 100%
            if (Document.DefaultPageSettings.PaperSize.Height < previewContr.ClientSize.Height && Document.DefaultPageSettings.PaperSize.Width < previewContr.ClientSize.Width)
            {
                previewContr.Zoom = 1;
            }
            else //if document ratio is taller than screen ratio, shrink by height.
            if (Document.DefaultPageSettings.PaperSize.Height / Document.DefaultPageSettings.PaperSize.Width > previewContr.ClientSize.Height / previewContr.ClientSize.Width)
            {
                previewContr.Zoom = ((double)previewContr.ClientSize.Height / (double)Document.DefaultPageSettings.PaperSize.Height);
            }
            else
            {
                //otherwise, shrink by width
                previewContr.Zoom = ((double)previewContr.ClientSize.Width / (double)Document.DefaultPageSettings.PaperSize.Width);
            }  
        }
        else
        {
            //100%
            previewContr.Zoom = 1;
        } 
    }

    /**
    * Causes the toolbar to be laid out again.
    */
    public void layoutToolBar() throws Exception {
        ToolBarMain.getButtons().Clear();
        //ToolBarMain.Buttons.Add(new ODToolBarButton(Lan.g(this,"Print"),0,"","Print"));
        //ToolBarMain.Buttons.Add(new ODToolBarButton(ODToolBarButtonStyle.Separator));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton("", 1, "Go Back One Page", "Back"));
        OpenDental.UI.ODToolBarButton button = new OpenDental.UI.ODToolBarButton("", -1, "", "PageNum");
        button.setStyle(OpenDental.UI.ODToolBarButtonStyle.Label);
        ToolBarMain.getButtons().add(button);
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton("", 2, "Go Forward One Page", "Fwd"));
        button = new OpenDental.UI.ODToolBarButton(Lan.g(this,"FullPage"), -1, Lan.g(this,"FullPage"), "FullPage");
        button.setStyle(OpenDental.UI.ODToolBarButtonStyle.ToggleButton);
        ToolBarMain.getButtons().add(button);
        button = new OpenDental.UI.ODToolBarButton(Lan.g(this,"100%"), -1, Lan.g(this,"100%"), "100");
        button.setStyle(OpenDental.UI.ODToolBarButtonStyle.ToggleButton);
        ToolBarMain.getButtons().add(button);
    }

    //ToolBarMain.Buttons.Add(new ODToolBarButton(ODToolBarButtonStyle.Separator));
    //ToolBarMain.Buttons.Add(new ODToolBarButton(Lan.g(this,"Close"),-1,"Close This Window","Close"));
    private void formReport_Layout(Object sender, System.Windows.Forms.LayoutEventArgs e) throws Exception {
        previewContr.Width = ClientSize.Width;
        previewContr.Height = ClientSize.Height - panelSig.Height - ToolBarMain.Height;
    }

    /*//I don't think we need this:
    		///<summary></summary>
    		private void FillSignature() {
    			textNote.Text="";
    			sigBox.ClearTablet();
    			if(!panelNote.Visible) {
    				return;
    			}
    			DataRow obj=(DataRow)TreeDocuments.SelectedNode.Tag;
    			textNote.Text=DocSelected.Note;
    			sigBox.Visible=true;
    			sigBox.SetTabletState(0);//never accepts input here
    			labelInvalidSig.Visible=false;
    			//Topaz box is not supported in Unix, since the required dll is Windows native.
    			if(Environment.OSVersion.Platform!=PlatformID.Unix) {
    				sigBoxTopaz.Location=sigBox.Location;//this puts both boxes in the same spot.
    				sigBoxTopaz.Visible=false;
    				((Topaz.SigPlusNET)sigBoxTopaz).SetTabletState(0);
    			}
    			//A machine running Unix will have DocSelected.SigIsTopaz set to false here, because the visibility of the panelNote
    			//will be set to false in the case of Unix and SigIsTopaz. Therefore, the else part of this if-else clause is always
    			//run on Unix systems.
    			if(DocSelected.SigIsTopaz) {
    				if(DocSelected.Signature!=null && DocSelected.Signature!="") {
    					sigBox.Visible=false;
    					sigBoxTopaz.Visible=true;
    					((Topaz.SigPlusNET)sigBoxTopaz).ClearTablet();
    					((Topaz.SigPlusNET)sigBoxTopaz).SetSigCompressionMode(0);
    					((Topaz.SigPlusNET)sigBoxTopaz).SetEncryptionMode(0);
    					((Topaz.SigPlusNET)sigBoxTopaz).SetKeyString(GetHashString(DocSelected));
    					((Topaz.SigPlusNET)sigBoxTopaz).SetEncryptionMode(2);//high encryption
    					((Topaz.SigPlusNET)sigBoxTopaz).SetSigCompressionMode(2);//high compression
    					((Topaz.SigPlusNET)sigBoxTopaz).SetSigString(DocSelected.Signature);
    					if(((Topaz.SigPlusNET)sigBoxTopaz).NumberOfTabletPoints() == 0) {
    						labelInvalidSig.Visible=true;
    					}
    				}
    			}
    			else {
    				sigBox.ClearTablet();
    				if(DocSelected.Signature!=null && DocSelected.Signature!="") {
    					sigBox.Visible=true;
    					sigBoxTopaz.Visible=false;
    					sigBox.SetKeyString(GetHashString(DocSelected));
    					sigBox.SetSigString(DocSelected.Signature);
    					if(sigBox.NumberOfTabletPoints()==0) {
    						labelInvalidSig.Visible=true;
    					}
    					sigBox.SetTabletState(0);//not accepting input.
    				}
    			}
    		}*/
    private void toolBarMain_ButtonClick(Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
        //MessageBox.Show(e.Button.Tag.ToString());
        Object.APPLY __dummyScrutVar0 = e.getButton().getTag().ToString();
        //case "Print":
        //	ToolBarPrint_Click();
        //	break;
        if (__dummyScrutVar0.equals("Back"))
        {
            onBack_Click();
        }
        else if (__dummyScrutVar0.equals("Fwd"))
        {
            onFwd_Click();
        }
        else if (__dummyScrutVar0.equals("FullPage"))
        {
            onFullPage_Click();
        }
        else if (__dummyScrutVar0.equals("100"))
        {
            on100_Click();
        }
            
    }

    //case "Close":
    //	OnClose_Click();
    //	break;
    private void onPrint_Click() throws Exception {
        if (!PrinterL.setPrinter(Document,PrintSituation.TPPerio,TPcur.PatNum,"Signed treatment plan from " + TPcur.DateTP.ToShortDateString() + " printed"))
        {
            return ;
        }
         
        if (Document.OriginAtMargins)
        {
            //In the sheets framework,we had to set margins to 20 because of a bug in their preview control.
            //We now need to set it back to 0 for the actual printing.
            //Hopefully, this doesn't break anything else.
            Document.DefaultPageSettings.Margins = new Margins(0, 0, 0, 0);
        }
         
        try
        {
            Document.Print();
        }
        catch (Exception e)
        {
            MessageBox.Show(Lan.g(this,"Error: ") + e.Message);
        }

        DialogResult = DialogResult.OK;
    }

    private void onClose_Click() throws Exception {
        this.Close();
    }

    private void onBack_Click() throws Exception {
        if (previewContr.StartPage == 0)
            return ;
         
        previewContr.StartPage--;
        ToolBarMain.getButtons().get___idx("PageNum").setText((previewContr.StartPage + 1).ToString() + " / " + TotalPages.ToString());
        ToolBarMain.Invalidate();
    }

    private void onFwd_Click() throws Exception {
        //if(printPreviewControl2.StartPage==totalPages-1) return;
        previewContr.StartPage++;
        ToolBarMain.getButtons().get___idx("PageNum").setText((previewContr.StartPage + 1).ToString() + " / " + TotalPages.ToString());
        ToolBarMain.Invalidate();
    }

    private void onFullPage_Click() throws Exception {
        ToolBarMain.getButtons().get___idx("100").setPushed(!ToolBarMain.getButtons().get___idx("FullPage").getPushed());
        ToolBarMain.Invalidate();
        setSize();
    }

    private void on100_Click() throws Exception {
        ToolBarMain.getButtons().get___idx("FullPage").setPushed(!ToolBarMain.getButtons().get___idx("100").getPushed());
        ToolBarMain.Invalidate();
        setSize();
    }

    private void butClearSig_Click(Object sender, EventArgs e) throws Exception {
        sigBox.clearTablet();
        sigBox.Visible = true;
        //if(allowTopaz) {
        CodeBase.TopazWrapper.clearTopaz(sigBoxTopaz);
        sigBoxTopaz.Visible = false;
        //until user explicitly starts it.
        //}
        sigBox.setTabletState(1);
        //on-screen box is now accepting input.
        SigChanged = true;
        labelInvalidSig.Visible = false;
    }

    private void butTopazSign_Click(Object sender, EventArgs e) throws Exception {
        sigBox.Visible = false;
        sigBoxTopaz.Visible = true;
        //if(allowTopaz){
        CodeBase.TopazWrapper.clearTopaz(sigBoxTopaz);
        CodeBase.TopazWrapper.setTopazEncryptionMode(sigBoxTopaz,0);
        CodeBase.TopazWrapper.setTopazState(sigBoxTopaz,1);
        //}
        SigChanged = true;
        labelInvalidSig.Visible = false;
    }

    private void sigBox_MouseUp(Object sender, MouseEventArgs e) throws Exception {
        //this is done on mouse up so that the initial pen capture won't be delayed.
        //if accepting input.
        if (sigBox.getTabletState() == 1 && !SigChanged)
        {
            //and sig not changed yet
            //sigBox handles its own pen input.
            SigChanged = true;
        }
         
    }

    private void saveSignature() throws Exception {
        if (SigChanged)
        {
            //This check short-circuits so that sigBoxTopaz.Visible will not be checked in MONO ever.
            //if(allowTopaz && sigBoxTopaz.Visible) {
            if (sigBoxTopaz.Visible)
            {
                TPcur.SigIsTopaz = true;
                if (CodeBase.TopazWrapper.getTopazNumberOfTabletPoints(sigBoxTopaz) == 0)
                {
                    TPcur.Signature = "";
                    return ;
                }
                 
                CodeBase.TopazWrapper.setTopazCompressionMode(sigBoxTopaz,0);
                CodeBase.TopazWrapper.setTopazEncryptionMode(sigBoxTopaz,0);
                CodeBase.TopazWrapper.setTopazKeyString(sigBoxTopaz,TreatPlans.getHashString(TPcur,proctpList));
                CodeBase.TopazWrapper.setTopazEncryptionMode(sigBoxTopaz,2);
                CodeBase.TopazWrapper.setTopazCompressionMode(sigBoxTopaz,2);
                TPcur.Signature = CodeBase.TopazWrapper.getTopazString(sigBoxTopaz);
            }
            else
            {
                TPcur.SigIsTopaz = false;
                if (sigBox.numberOfTabletPoints() == 0)
                {
                    TPcur.Signature = "";
                    return ;
                }
                 
                //sigBox.SetSigCompressionMode(0);
                //sigBox.SetEncryptionMode(0);
                sigBox.setKeyString(TreatPlans.getHashString(TPcur,proctpList));
                //"0000000000000000");
                //sigBox.SetAutoKeyData(ProcCur.Note+ProcCur.UserNum.ToString());
                //sigBox.SetEncryptionMode(2);
                //sigBox.SetSigCompressionMode(2);
                TPcur.Signature = sigBox.getSigString();
            } 
        }
         
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        saveSignature();
        TreatPlans.update(TPcur);
        SecurityLogs.makeLogEntry(Permissions.TreatPlanEdit,TPcur.PatNum,"Sign TP");
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formTPsign_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
    }

}


//if(allowTopaz){
//  sigBoxTopaz.Dispose();
//}