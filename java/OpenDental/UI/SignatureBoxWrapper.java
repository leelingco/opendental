//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental.UI;

import CS2JNet.System.StringSupport;
import OpenDental.Properties.Resources;


/**
* Wraps the Topaz SigPlusNET control and the alternate SignatureBox control.  Also includes both needed buttons.  Should vastly simplify using signature boxes throughout the program.
*/
public class SignatureBoxWrapper  extends UserControl 
{

    private boolean sigChanged = new boolean();
    //private bool allowTopaz;
    private Control sigBoxTopaz = new Control();
    private String labelText = new String();
    /**
    * The reason for this event is so that if a different user is signing, that it properly records the change in users.  See the example pattern in FormProcGroup.
    */
    public EventHandler SignatureChanged = null;
    public SignatureBoxWrapper() throws Exception {
        initializeComponent();
        //allowTopaz=(Environment.OSVersion.Platform!=PlatformID.Unix && !CodeBase.ODEnvironment.Is64BitOperatingSystem());
        sigBox.setTabletState(1);
        //if(!allowTopaz) {
        //  butTopazSign.Visible=false;
        //  sigBox.Visible=true;
        //}
        //else{
        //Add signature box for Topaz signatures.
        sigBoxTopaz = CodeBase.TopazWrapper.getTopaz();
        sigBoxTopaz.Location = sigBox.Location;
        //this puts both boxes in the same spot.
        sigBoxTopaz.Name = "sigBoxTopaz";
        sigBoxTopaz.Size = sigBox.Size;
        //new System.Drawing.Size(362,79);
        sigBox.Anchor = (AnchorStyles)(AnchorStyles.Bottom | AnchorStyles.Left | AnchorStyles.Top | AnchorStyles.Right);
        sigBoxTopaz.TabIndex = 92;
        sigBoxTopaz.Text = "sigPlusNET1";
        sigBoxTopaz.Visible = false;
        sigBoxTopaz.Leave += new EventHandler(sigBoxTopaz_Leave);
        Controls.Add(sigBoxTopaz);
        //It starts out accepting input. It will be set to 0 if a sig is already present.  It will be set back to 1 if note changes or if user clicks Clear.
        CodeBase.TopazWrapper.setTopazState(sigBoxTopaz,1);
        butTopazSign.BringToFront();
        butClearSig.BringToFront();
    }

    //}
    protected void onSignatureChanged() throws Exception {
        sigChanged = true;
        if (SignatureChanged != null)
        {
            SignatureChanged(this, new EventArgs());
        }
         
    }

    /**
    * Usually "Invalid Signature", but this can be changed for different situations.
    */
    public String getLabelText() throws Exception {
        return labelText;
    }

    public void setLabelText(String value) throws Exception {
        labelText = value;
        labelInvalidSig.Text = value;
    }

    public void fillSignature(boolean sigIsTopaz, String keyData, String signature) throws Exception {
        labelInvalidSig.Visible = false;
        sigBox.Visible = true;
        if (sigIsTopaz)
        {
            if (!StringSupport.equals(signature, ""))
            {
                //if(allowTopaz){
                sigBox.Visible = false;
                sigBoxTopaz.Visible = true;
                CodeBase.TopazWrapper.clearTopaz(sigBoxTopaz);
                CodeBase.TopazWrapper.setTopazCompressionMode(sigBoxTopaz,0);
                CodeBase.TopazWrapper.setTopazEncryptionMode(sigBoxTopaz,0);
                CodeBase.TopazWrapper.setTopazKeyString(sigBoxTopaz,"0000000000000000");
                CodeBase.TopazWrapper.setTopazEncryptionMode(sigBoxTopaz,2);
                //high encryption
                CodeBase.TopazWrapper.setTopazCompressionMode(sigBoxTopaz,2);
                //high compression
                CodeBase.TopazWrapper.setTopazSigString(sigBoxTopaz,signature);
                //older items may have been signed with zeros due to a bug.  We still want to show the sig in that case.
                //but if a sig is not showing, then set the key string to try to get it to show.
                if (CodeBase.TopazWrapper.getTopazNumberOfTabletPoints(sigBoxTopaz) == 0)
                {
                    CodeBase.TopazWrapper.setTopazAutoKeyData(sigBoxTopaz,keyData);
                    CodeBase.TopazWrapper.setTopazSigString(sigBoxTopaz,signature);
                }
                 
                //If sig is not showing, then try encryption mode 3 for signatures signed with old SigPlusNet.dll.
                if (CodeBase.TopazWrapper.getTopazNumberOfTabletPoints(sigBoxTopaz) == 0)
                {
                    CodeBase.TopazWrapper.setTopazEncryptionMode(sigBoxTopaz,3);
                    //Unknown mode (told to use via TopazSystems)
                    CodeBase.TopazWrapper.setTopazSigString(sigBoxTopaz,signature);
                }
                 
                //If sig still not showing it must be invalid.
                if (CodeBase.TopazWrapper.getTopazNumberOfTabletPoints(sigBoxTopaz) == 0)
                {
                    labelInvalidSig.Visible = true;
                }
                 
                CodeBase.TopazWrapper.setTopazState(sigBoxTopaz,0);
            }
             
        }
        else
        {
            //}
            if (signature != null && !StringSupport.equals(signature, ""))
            {
                sigBox.Visible = true;
                sigBoxTopaz.Visible = false;
                sigBox.clearTablet();
                //sigBox.SetSigCompressionMode(0);
                //sigBox.SetEncryptionMode(0);
                sigBox.setKeyString("0000000000000000");
                sigBox.setAutoKeyData(keyData);
                //sigBox.SetEncryptionMode(2);//high encryption
                //sigBox.SetSigCompressionMode(2);//high compression
                sigBox.setSigString(signature);
                if (sigBox.numberOfTabletPoints() == 0)
                {
                    labelInvalidSig.Visible = true;
                }
                 
                sigBox.setTabletState(0);
            }
             
        } 
    }

    //not accepting input.  To accept input, change the note, or clear the sig.
    /**
    * This can be used to determine whether the signature has changed since the control was created.  It is, however, preferrable to have the parent form use the SignatureChanged event to track changes.
    */
    public boolean getSigChanged() throws Exception {
        return sigChanged;
    }

    /**
    * This should NOT be used unless GetSigChanged returns true.
    */
    public boolean getSigIsTopaz() throws Exception {
        //if(allowTopaz && sigBoxTopaz.Visible){
        if (sigBoxTopaz.Visible)
        {
            return true;
        }
         
        return false;
    }

    /*
    		///<summary></summary>
    		public bool GetSigIsValid(){
    			if(labelInvalidSig.Visible){
    				return false;
    			}
    			return true;
    		}*/
    /**
    * This should happen a lot before the box is signed.  Once it's signed, if this happens, then the signature will be invalidated.  The user would have to clear the invalidation manually.  This "invalidation" is just a visual cue; nothing actually happens to the data.
    */
    public void setInvalid() throws Exception {
        //if(allowTopaz && sigBoxTopaz.Visible){
        if (sigBoxTopaz.Visible)
        {
            if (CodeBase.TopazWrapper.getTopazNumberOfTabletPoints(sigBoxTopaz) == 0)
            {
                return ;
            }
             
        }
        else
        {
            //no need to do anything because no signature
            if (sigBox.numberOfTabletPoints() == 0)
            {
                return ;
            }
             
        } 
        //no need to do anything because no signature
        labelInvalidSig.Visible = true;
    }

    public boolean getIsValid() throws Exception {
        return (!labelInvalidSig.Visible);
    }

    public boolean getSigIsBlank() throws Exception {
        //if(allowTopaz && sigBoxTopaz.Visible){
        if (sigBoxTopaz.Visible)
        {
            return (CodeBase.TopazWrapper.getTopazNumberOfTabletPoints(sigBoxTopaz) == 0);
        }
         
        return (sigBox.numberOfTabletPoints() == 0);
    }

    /**
    * This should NOT be used unless GetSigChanged returns true.
    */
    public String getSignature(String keyData) throws Exception {
        //Topaz boxes are written in Windows native code.
        //if(allowTopaz && sigBoxTopaz.Visible){
        if (sigBoxTopaz.Visible)
        {
            //ProcCur.SigIsTopaz=true;
            if (CodeBase.TopazWrapper.getTopazNumberOfTabletPoints(sigBoxTopaz) == 0)
            {
                return "";
            }
             
            CodeBase.TopazWrapper.setTopazCompressionMode(sigBoxTopaz,0);
            CodeBase.TopazWrapper.setTopazEncryptionMode(sigBoxTopaz,0);
            CodeBase.TopazWrapper.setTopazKeyString(sigBoxTopaz,"0000000000000000");
            CodeBase.TopazWrapper.setTopazAutoKeyData(sigBoxTopaz,keyData);
            CodeBase.TopazWrapper.setTopazEncryptionMode(sigBoxTopaz,2);
            CodeBase.TopazWrapper.setTopazCompressionMode(sigBoxTopaz,2);
            return CodeBase.TopazWrapper.getTopazString(sigBoxTopaz);
        }
        else
        {
            //ProcCur.SigIsTopaz=false;
            if (sigBox.numberOfTabletPoints() == 0)
            {
                return "";
            }
             
            //sigBox.SetSigCompressionMode(0);
            //sigBox.SetEncryptionMode(0);
            sigBox.setKeyString("0000000000000000");
            sigBox.setAutoKeyData(keyData);
            return sigBox.getSigString();
        } 
    }

    //sigBox.SetEncryptionMode(2);
    //sigBox.SetSigCompressionMode(2);
    private void butClearSig_Click(Object sender, EventArgs e) throws Exception {
        clearSignature();
        onSignatureChanged();
    }

    private void butTopazSign_Click(Object sender, EventArgs e) throws Exception {
        //this button is not even visible if Topaz is not allowed
        sigBox.Visible = false;
        sigBoxTopaz.Visible = true;
        //if(allowTopaz){
        CodeBase.TopazWrapper.clearTopaz(sigBoxTopaz);
        CodeBase.TopazWrapper.setTopazEncryptionMode(sigBoxTopaz,0);
        CodeBase.TopazWrapper.setTopazState(sigBoxTopaz,1);
        //}
        labelInvalidSig.Visible = false;
        sigBoxTopaz.Focus();
        onSignatureChanged();
    }

    private void sigBox_MouseUp(Object sender, MouseEventArgs e) throws Exception {
        //this is done on mouse up so that the initial pen capture won't be delayed.
        //if accepting input.
        if (sigBox.getTabletState() == 1 && !sigChanged)
        {
            //and sig not changed yet
            //sigBox handles its own pen input.
            onSignatureChanged();
        }
         
    }

    private void sigBoxTopaz_Leave(Object sender, EventArgs e) throws Exception {
        if (sigBox.getTabletState() == 1)
        {
            //if accepting input.
            CodeBase.TopazWrapper.setTopazState(sigBoxTopaz,0);
        }
         
    }

    /**
    * Must set width and height of control and run FillSignature first.
    */
    public Bitmap getSigImage() throws Exception {
        Bitmap sigBitmap = new Bitmap(Width - 2, Height - 2);
        //no outline
        //if(allowTopaz && sigBoxTopaz.Visible){
        if (sigBoxTopaz.Visible)
        {
            sigBoxTopaz.DrawToBitmap(sigBitmap, new Rectangle(0, 0, Width - 2, Height - 2));
        }
        else
        {
            //GetBitmap would probably work.
            sigBitmap = (Bitmap)sigBox.getSigImage(false);
        } 
        return sigBitmap;
    }

    /**
    * If this is called externally, then the event SignatureChanged will also fire.
    */
    public void clearSignature() throws Exception {
        sigBox.clearTablet();
        sigBox.Visible = true;
        //if(allowTopaz) {
        CodeBase.TopazWrapper.clearTopaz(sigBoxTopaz);
        sigBoxTopaz.Visible = false;
        //until user explicitly starts it.
        //}
        sigBox.setTabletState(1);
        //on-screen box is now accepting input.
        sigChanged = true;
        labelInvalidSig.Visible = false;
        onSignatureChanged();
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
        this.toolTip1 = new System.Windows.Forms.ToolTip(this.components);
        this.labelInvalidSig = new System.Windows.Forms.Label();
        this.butTopazSign = new OpenDental.UI.Button();
        this.butClearSig = new OpenDental.UI.Button();
        this.sigBox = new OpenDental.UI.SignatureBox();
        this.SuspendLayout();
        //
        // labelInvalidSig
        //
        this.labelInvalidSig.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.labelInvalidSig.BackColor = System.Drawing.SystemColors.Window;
        this.labelInvalidSig.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelInvalidSig.Location = new System.Drawing.Point(84, 11);
        this.labelInvalidSig.Name = "labelInvalidSig";
        this.labelInvalidSig.Size = new System.Drawing.Size(196, 59);
        this.labelInvalidSig.TabIndex = 110;
        this.labelInvalidSig.Text = "Invalid Signature";
        this.labelInvalidSig.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        this.labelInvalidSig.Visible = false;
        //
        // butTopazSign
        //
        this.butTopazSign.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butTopazSign.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butTopazSign.setAutosize(true);
        this.butTopazSign.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butTopazSign.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butTopazSign.setCornerRadius(1F);
        this.butTopazSign.Image = Resources.gettopazPen10();
        this.butTopazSign.Location = new System.Drawing.Point(336, 0);
        this.butTopazSign.Name = "butTopazSign";
        this.butTopazSign.Size = new System.Drawing.Size(14, 14);
        this.butTopazSign.TabIndex = 82;
        this.toolTip1.SetToolTip(this.butTopazSign, "Sign Topaz");
        this.butTopazSign.Click += new System.EventHandler(this.butTopazSign_Click);
        //
        // butClearSig
        //
        this.butClearSig.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClearSig.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butClearSig.setAutosize(true);
        this.butClearSig.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClearSig.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClearSig.setCornerRadius(1F);
        this.butClearSig.Image = Resources.getdeleteX10();
        this.butClearSig.Location = new System.Drawing.Point(350, 0);
        this.butClearSig.Name = "butClearSig";
        this.butClearSig.Size = new System.Drawing.Size(14, 14);
        this.butClearSig.TabIndex = 81;
        this.toolTip1.SetToolTip(this.butClearSig, "Clear Signature");
        this.butClearSig.Click += new System.EventHandler(this.butClearSig_Click);
        //
        // sigBox
        //
        this.sigBox.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.sigBox.Location = new System.Drawing.Point(1, 1);
        this.sigBox.Name = "sigBox";
        this.sigBox.Size = new System.Drawing.Size(362, 79);
        this.sigBox.TabIndex = 87;
        this.sigBox.MouseUp += new System.Windows.Forms.MouseEventHandler(this.sigBox_MouseUp);
        //
        // SignatureBoxWrapper
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.BackColor = System.Drawing.SystemColors.ControlDark;
        this.Controls.Add(this.labelInvalidSig);
        this.Controls.Add(this.butTopazSign);
        this.Controls.Add(this.butClearSig);
        this.Controls.Add(this.sigBox);
        this.Name = "SignatureBoxWrapper";
        this.Size = new System.Drawing.Size(364, 81);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butClearSig;
    private System.Windows.Forms.ToolTip toolTip1 = new System.Windows.Forms.ToolTip();
    private OpenDental.UI.Button butTopazSign;
    private OpenDental.UI.SignatureBox sigBox;
    private System.Windows.Forms.Label labelInvalidSig = new System.Windows.Forms.Label();
}


