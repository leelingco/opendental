//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:51 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Eclaims.BCBSGA;
import OpenDental.Eclaims.DentiCal;
import OpenDental.Eclaims.EmdeonMedical;
import OpenDental.Eclaims.MercuryDE;
import OpenDental.Eclaims.WebMD;
import OpenDental.FormClaimReports;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.Clearinghouses;
import OpenDentBusiness.EclaimsCommBridge;
import OpenDentBusiness.ElectronicClaimFormat;
import OpenDentBusiness.Etranss;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;

/**
* Summary description for FormBasicTemplate.
*/
public class FormClaimReports  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.Label labelRetrieving = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboClearhouse = new System.Windows.Forms.ComboBox();
    private OpenDental.UI.Button butRetrieve;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * If true, then reports will be automatically retrieved for default clearinghouse.  Then this form will close.
    */
    public boolean AutomaticMode = new boolean();
    /**
    * 
    */
    public FormClaimReports() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormClaimReports.class);
        this.labelRetrieving = new System.Windows.Forms.Label();
        this.comboClearhouse = new System.Windows.Forms.ComboBox();
        this.butRetrieve = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // labelRetrieving
        //
        this.labelRetrieving.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelRetrieving.Location = new System.Drawing.Point(12, 72);
        this.labelRetrieving.Name = "labelRetrieving";
        this.labelRetrieving.Size = new System.Drawing.Size(366, 20);
        this.labelRetrieving.TabIndex = 1;
        this.labelRetrieving.Text = "Retrieving reports from selected clearinghouse.";
        this.labelRetrieving.Visible = false;
        //
        // comboClearhouse
        //
        this.comboClearhouse.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboClearhouse.Location = new System.Drawing.Point(18, 32);
        this.comboClearhouse.Name = "comboClearhouse";
        this.comboClearhouse.Size = new System.Drawing.Size(187, 21);
        this.comboClearhouse.TabIndex = 2;
        //
        // butRetrieve
        //
        this.butRetrieve.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRetrieve.setAutosize(true);
        this.butRetrieve.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRetrieve.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRetrieve.setCornerRadius(4F);
        this.butRetrieve.Location = new System.Drawing.Point(222, 29);
        this.butRetrieve.Name = "butRetrieve";
        this.butRetrieve.Size = new System.Drawing.Size(90, 26);
        this.butRetrieve.TabIndex = 5;
        this.butRetrieve.Text = "Retrieve";
        this.butRetrieve.Click += new System.EventHandler(this.butRetrieve_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(289, 152);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 26);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormClaimReports
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(401, 202);
        this.Controls.Add(this.butRetrieve);
        this.Controls.Add(this.comboClearhouse);
        this.Controls.Add(this.labelRetrieving);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormClaimReports";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "E-claim Reports";
        this.Shown += new System.EventHandler(this.FormClaimReports_Shown);
        this.Load += new System.EventHandler(this.FormClaimReports_Load);
        this.ResumeLayout(false);
    }

    private void formClaimReports_Load(Object sender, System.EventArgs e) throws Exception {
        for (int i = 0;i < Clearinghouses.getListt().Length;i++)
        {
            comboClearhouse.Items.Add(Clearinghouses.getListt()[i].Description);
            if (PrefC.getLong(PrefName.ClearinghouseDefaultDent) == Clearinghouses.getListt()[i].ClearinghouseNum)
            {
                comboClearhouse.SelectedIndex = i;
            }
             
        }
        if (comboClearhouse.Items.Count > 0 && comboClearhouse.SelectedIndex == -1)
        {
            comboClearhouse.SelectedIndex = 0;
        }
         
    }

    private void formClaimReports_Shown(Object sender, EventArgs e) throws Exception {
        if (AutomaticMode)
        {
            labelRetrieving.Visible = true;
            retrieveReports();
            importReportFiles();
            Close();
        }
         
    }

    private void butRetrieve_Click(Object sender, EventArgs e) throws Exception {
        if (comboClearhouse.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a clearinghouse first.");
            return ;
        }
         
        if (!Directory.Exists(Clearinghouses.getListt()[comboClearhouse.SelectedIndex].ResponsePath))
        {
            MsgBox.show(this,"Clearinghouse does not have a valid Report Path set.");
            return ;
        }
         
        //For Tesia, user wouldn't normally manually retrieve.
        if (StringSupport.equals(Clearinghouses.getListt()[comboClearhouse.SelectedIndex].ISA08, "113504607"))
        {
            //See FormClaimsSend_Load
            if ((PrefC.getRandomKeys() && !PrefC.getBool(PrefName.EasyNoClinics)) || PrefC.getLong(PrefName.ClearinghouseDefaultDent) != Clearinghouses.getListt()[comboClearhouse.SelectedIndex].ClearinghouseNum)
            {
                //But they might need to in these situations.
                importReportFiles();
                MsgBox.show(this,"Done");
            }
            else
            {
                MsgBox.show(this,"No need to Retrieve.  Available reports are automatically downloaded every three minutes.");
            } 
            return ;
        }
         
        if (Clearinghouses.getListt()[comboClearhouse.SelectedIndex].CommBridge == EclaimsCommBridge.None || Clearinghouses.getListt()[comboClearhouse.SelectedIndex].CommBridge == EclaimsCommBridge.Renaissance || Clearinghouses.getListt()[comboClearhouse.SelectedIndex].CommBridge == EclaimsCommBridge.RECS)
        {
            MsgBox.show(this,"No built-in functionality for retrieving reports from this clearinghouse.");
            return ;
        }
         
        if (!MsgBox.show(this,true,"Connect to clearinghouse to retrieve reports?"))
        {
            return ;
        }
         
        labelRetrieving.Visible = true;
        retrieveReports();
        importReportFiles();
        labelRetrieving.Visible = false;
        MsgBox.show(this,"Done");
    }

    private void retrieveReports() throws Exception {
        if (StringSupport.equals(Clearinghouses.getListt()[comboClearhouse.SelectedIndex].ISA08, "113504607"))
        {
            return ;
        }
         
        //TesiaLink
        //But the import will still happen
        if (Clearinghouses.getListt()[comboClearhouse.SelectedIndex].CommBridge == EclaimsCommBridge.None || Clearinghouses.getListt()[comboClearhouse.SelectedIndex].CommBridge == EclaimsCommBridge.Renaissance || Clearinghouses.getListt()[comboClearhouse.SelectedIndex].CommBridge == EclaimsCommBridge.RECS)
        {
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        /*if(Clearinghouses.List[comboClearhouse.SelectedIndex].CommBridge==EclaimsCommBridge.Tesia) {
        				try{
        					DateTime curtime=DateTime.Now;
        					while (DateTime.Now<curtime.AddSeconds(2)){
        						Application.DoEvents();
        					}
        					MessageBox.Show("Incomplete");
        					Tesia.GetReports();
        				}
        				catch(Exception ex){
        					Cursor=Cursors.Default;
        					MessageBox.Show(ex.Message);
        					return;
        				}
        			}*/
        if (Clearinghouses.getListt()[comboClearhouse.SelectedIndex].CommBridge == EclaimsCommBridge.WebMD)
        {
            if (!WebMD.Launch(Clearinghouses.getListt()[comboClearhouse.SelectedIndex], 0))
            {
                Cursor = Cursors.Default;
                MessageBox.Show(Lan.g(this,"Error retrieving."));
                return ;
            }
             
        }
        else if (Clearinghouses.getListt()[comboClearhouse.SelectedIndex].CommBridge == EclaimsCommBridge.BCBSGA)
        {
            if (!BCBSGA.Retrieve(Clearinghouses.getListt()[comboClearhouse.SelectedIndex]))
            {
                Cursor = Cursors.Default;
                MessageBox.Show(Lan.g(this,"Error retrieving."));
                return ;
            }
             
        }
        else if (Clearinghouses.getListt()[comboClearhouse.SelectedIndex].CommBridge == EclaimsCommBridge.ClaimConnect)
        {
            if (AutomaticMode)
            {
                Cursor = Cursors.Default;
                return ;
            }
             
            try
            {
                Process.Start("http://www.dentalxchange.com");
            }
            catch (Exception __dummyCatchVar0)
            {
                MessageBox.Show("Could not locate the site.");
            }

            Cursor = Cursors.Default;
            return ;
        }
        else if (Clearinghouses.getListt()[comboClearhouse.SelectedIndex].CommBridge == EclaimsCommBridge.AOS)
        {
            try
            {
                //his path would never exist on Unix, so no need to handle back slashes.
                Process.Start("C:\\Program files\\AOS\\AOSCommunicator\\AOSCommunicator.exe");
            }
            catch (Exception __dummyCatchVar1)
            {
                Cursor = Cursors.Default;
                MessageBox.Show("Could not locate the file.");
                return ;
            }
        
        }
        else if (Clearinghouses.getListt()[comboClearhouse.SelectedIndex].CommBridge == EclaimsCommBridge.MercuryDE)
        {
            if (!MercuryDE.Launch(Clearinghouses.getListt()[comboClearhouse.SelectedIndex], 0))
            {
                Cursor = Cursors.Default;
                MessageBox.Show(Lan.g(this,"Error retrieving."));
                return ;
            }
             
        }
        else if (Clearinghouses.getListt()[comboClearhouse.SelectedIndex].CommBridge == EclaimsCommBridge.EmdeonMedical)
        {
            if (!EmdeonMedical.Retrieve(Clearinghouses.getListt()[comboClearhouse.SelectedIndex]))
            {
                Cursor = Cursors.Default;
                MessageBox.Show(Lan.g(this,"Error retrieving."));
                return ;
            }
             
        }
        else if (Clearinghouses.getListt()[comboClearhouse.SelectedIndex].CommBridge == EclaimsCommBridge.DentiCal)
        {
            if (!DentiCal.Launch(Clearinghouses.getListt()[comboClearhouse.SelectedIndex], 0))
            {
                Cursor = Cursors.Default;
                MessageBox.Show(Lan.g(this,"Error retrieving."));
                return ;
            }
             
        }
               
        Cursor = Cursors.Default;
        if (!AutomaticMode)
        {
            MsgBox.show(this,"Retrieval successful");
        }
         
    }

    /**
    * Takes any files found in the reports folder for the clearinghouse, and imports them into the database.  Deletes the original files.  No longer any such thing as archive.
    */
    private void importReportFiles() throws Exception {
        if (!Directory.Exists(Clearinghouses.getListt()[comboClearhouse.SelectedIndex].ResponsePath))
        {
            return ;
        }
         
        //MsgBox.Show(this,"Clearinghouse does not have a valid Report Path set.");
        if (Clearinghouses.getListt()[comboClearhouse.SelectedIndex].Eformat == ElectronicClaimFormat.Canadian)
        {
            return ;
        }
         
        //the report path is shared with many other important files.  Do not process anything.  Comm is synchronous only.
        String[] files = Directory.GetFiles(Clearinghouses.getListt()[comboClearhouse.SelectedIndex].ResponsePath);
        String archiveDir = CodeBase.ODFileUtils.CombinePaths(Clearinghouses.getListt()[comboClearhouse.SelectedIndex].ResponsePath, "Archive");
        if (!Directory.Exists(archiveDir))
        {
            Directory.CreateDirectory(archiveDir);
        }
         
        for (int i = 0;i < files.Length;i++)
        {
            Etranss.ProcessIncomingReport(File.GetCreationTime(files[i]), Clearinghouses.getListt()[comboClearhouse.SelectedIndex].ClearinghouseNum, File.ReadAllText(files[i]));
            try
            {
                File.Copy(files[i], CodeBase.ODFileUtils.CombinePaths(archiveDir, Path.GetFileName(files[i])));
            }
            catch (Exception __dummyCatchVar2)
            {
            }

            File.Delete(files[i]);
        }
    }

    /*private void listMain_DoubleClick(object sender, System.EventArgs e) {
    			if(listMain.SelectedIndices.Count==0){
    				return;
    			}
    			string messageText=File.ReadAllText((string)listMain.SelectedItem);
    			if(X12object.IsX12(messageText)){
    				X12object xobj=new X12object(messageText);
    				if(X277U.Is277U(xobj)){
    					MsgBoxCopyPaste msgbox=new MsgBoxCopyPaste(X277U.MakeHumanReadable(xobj));
    					msgbox.ShowDialog();
    				}
    				else if(X997.Is997(xobj)) {
    					//MsgBoxCopyPaste msgbox=new MsgBoxCopyPaste(X997.MakeHumanReadable(xobj));
    					//msgbox.ShowDialog();
    				}
    			}
    			else{
    				MsgBoxCopyPaste msgbox=new MsgBoxCopyPaste(messageText);
    				msgbox.ShowDialog();
    			}
    			
    			//if the file is an X12 file (277 for now), then display it differently
    			if(Path.GetExtension((string)listMain.SelectedItem)==".txt"){
    				//List<string> messageLines=new List<string>();
    				//X12object xObj=new X12object(File.ReadAllText(fileName));
    				string firstLine="";
    				using(StreamReader sr=new StreamReader((string)listMain.SelectedItem)){
    					firstLine=sr.ReadLine();
    				}
    				if(firstLine!=null && firstLine.Length==106 && firstLine.Substring(0,3)=="ISA"){
    					//try{
    						string humanText=X277U.MakeHumanReadable((string)listMain.SelectedItem);
    						ArchiveFile((string)listMain.SelectedItem);
    						//now the file will be gone
    						//create a new file from humanText with same name as original file
    						StreamWriter sw=File.CreateText((string)listMain.SelectedItem);
    						sw.Write(humanText);
    						sw.Close();
    						//now, it will try to launch the new text file
    					//}
    					//catch(Exception ex){
    					//	MessageBox.Show(ex.Message);
    					//	return;
    					//}
    				}
    			}
    			try{
    				Process.Start((string)listMain.SelectedItem);
    			}
    			catch{
    				MsgBox.Show(this,"Could not open the item. You could try open it directly from the folder where it is located.");
    			}
    			//FillGrid();
    		}*/
    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

}


