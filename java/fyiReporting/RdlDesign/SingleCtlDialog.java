//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:22 PM
//

package fyiReporting.RdlDesign;

import fyiReporting.RdlDesign.DesignXmlDraw;
import fyiReporting.RdlDesign.IProperty;
import fyiReporting.RdlDesign.SingleCtlTypeEnum;

// don't have a single ctl dialog right now
/**
* Summary description for PropertyDialog.
*/
public class SingleCtlDialog  extends System.Windows.Forms.Form 
{
    private DesignXmlDraw _Draw;
    // design draw
    private List<XmlNode> _Nodes = new List<XmlNode>();
    // selected nodes
    private SingleCtlTypeEnum _Type = SingleCtlTypeEnum.NoneForNow;
    IProperty _Ctl;
    private boolean _Changed = false;
    private System.Windows.Forms.Panel panel1 = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Button bCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bOK = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bApply = new System.Windows.Forms.Button();
    private System.Windows.Forms.Panel panelMain = new System.Windows.Forms.Panel();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public SingleCtlDialog(DesignXmlDraw dxDraw, List<XmlNode> sNodes, SingleCtlTypeEnum type) throws Exception {
        this._Draw = dxDraw;
        this._Nodes = sNodes;
        this._Type = type;
        _Ctl = null;
        // when we have a case this should be initialized
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
    }

    //   Add the controls for the selected ReportItems
    public boolean getChanged() throws Exception {
        return _Changed;
    }

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
        this.panel1 = new System.Windows.Forms.Panel();
        this.bApply = new System.Windows.Forms.Button();
        this.bOK = new System.Windows.Forms.Button();
        this.bCancel = new System.Windows.Forms.Button();
        this.panelMain = new System.Windows.Forms.Panel();
        this.panel1.SuspendLayout();
        this.SuspendLayout();
        //
        // panel1
        //
        this.panel1.CausesValidation = false;
        this.panel1.Controls.Add(this.bApply);
        this.panel1.Controls.Add(this.bOK);
        this.panel1.Controls.Add(this.bCancel);
        this.panel1.Dock = System.Windows.Forms.DockStyle.Bottom;
        this.panel1.Location = new System.Drawing.Point(0, 310);
        this.panel1.Name = "panel1";
        this.panel1.Size = new System.Drawing.Size(458, 56);
        this.panel1.TabIndex = 1;
        //
        // bApply
        //
        this.bApply.Location = new System.Drawing.Point(376, 16);
        this.bApply.Name = "bApply";
        this.bApply.TabIndex = 2;
        this.bApply.Text = "Apply";
        this.bApply.Click += new System.EventHandler(this.bApply_Click);
        //
        // bOK
        //
        this.bOK.Location = new System.Drawing.Point(200, 17);
        this.bOK.Name = "bOK";
        this.bOK.TabIndex = 0;
        this.bOK.Text = "OK";
        this.bOK.Click += new System.EventHandler(this.bOK_Click);
        //
        // bCancel
        //
        this.bCancel.CausesValidation = false;
        this.bCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.bCancel.Location = new System.Drawing.Point(288, 16);
        this.bCancel.Name = "bCancel";
        this.bCancel.TabIndex = 1;
        this.bCancel.Text = "Cancel";
        //
        // panelMain
        //
        this.panelMain.Dock = System.Windows.Forms.DockStyle.Fill;
        this.panelMain.Location = new System.Drawing.Point(0, 0);
        this.panelMain.Name = "panelMain";
        this.panelMain.Size = new System.Drawing.Size(458, 310);
        this.panelMain.TabIndex = 2;
        //
        // PropertyDialog
        //
        this.AcceptButton = this.bOK;
        this.AutoScaleMode = AutoScaleMode.None;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.bCancel;
        this.ClientSize = new System.Drawing.Size(458, 366);
        this.Controls.Add(this.panelMain);
        this.Controls.Add(this.panel1);
        this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "PropertyDialog";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
        this.Text = "Properties";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.PropertyDialog_Closing);
        this.panel1.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private void bApply_Click(Object sender, System.EventArgs e) throws Exception {
        this._Changed = true;
        _Ctl.apply();
        this._Draw.Invalidate();
    }

    // Force screen to redraw
    private void bOK_Click(Object sender, System.EventArgs e) throws Exception {
        bApply_Click(sender,e);
        // Apply does all the work
        this.DialogResult = DialogResult.OK;
    }

    private void propertyDialog_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
    }

}


