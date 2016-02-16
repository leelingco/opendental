//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CodeBase.MsgBoxCopyPaste;
import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.JavaSupport.util.ListSupport;
import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.FormEHR;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.CodeSystem;
import OpenDentBusiness.CodeSystems;
import OpenDentBusiness.EhrQuarterlyKey;
import OpenDentBusiness.EhrQuarterlyKeys;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

public class FormCodeSystemsImport  extends Form 
{

    /**
    * All code systems available.
    */
    private List<CodeSystem> _listCodeSystems = new List<CodeSystem>();
    /**
    * Indicates if quarterly EHR key is valid. If true then SNOMED CT codes will be made available for download.
    */
    private boolean _isMemberNation = new boolean();
    /**
    * Track current status of each code system.
    */
    private Dictionary<String, String> _mapCodeSystemStatus = new Dictionary<String, String>();
    /*code system name*/
    /*status to be printed to grid*/
    public FormCodeSystemsImport() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formCodeSystemsImport_Load(Object sender, EventArgs e) throws Exception {
        _isMemberNation = false;
        //This check is here to prevent Snomeds from being available in non-member nations.
        List<EhrQuarterlyKey> ehrKeys = EhrQuarterlyKeys.getAllKeys();
        for (int i = 0;i < ehrKeys.Count;i++)
        {
            if (FormEHR.QuarterlyKeyIsValid(ehrKeys[i].YearValue.ToString(), ehrKeys[i].QuarterValue.ToString(), ehrKeys[i].PracticeName, ehrKeys[i].KeyValue))
            {
                _isMemberNation = true;
                break;
            }
             
        }
        UpdateCodeSystemThread.Finished += new EventHandler(UpdateCodeSystemThread_FinishedSafe);
    }

    /**
    * If there are still import threads running then prompt the user to see if they want to abort the imports prematurely.
    */
    private void formCodeSystemsImport_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        if (!UpdateCodeSystemThread.getIsRunning())
        {
            return ;
        }
         
        //All done, exit.
        if (MsgBox.show("CodeSystemImporter",true,"Import in progress. Would you like to abort?"))
        {
            //User wants abort the threads.
            UpdateCodeSystemThread.stopAll();
            return ;
        }
         
        //User elected to continue waiting so cancel the Close event.
        e.Cancel = true;
    }

    private void fillGrid() throws Exception {
        _listCodeSystems = CodeSystems.getForCurrentVersion(_isMemberNation);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn("Code System",200,false);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Current Version",100,false);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Available Version",100,false);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Download Status",100,false);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < _listCodeSystems.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(_listCodeSystems[i].CodeSystemName);
            row.getCells().Add(_listCodeSystems[i].VersionCur);
            row.getCells().Add(_listCodeSystems[i].VersionAvail);
            //Initialize with the status which may have been set during pre-download in butDownload_Click. This cell will be updated on download progress updates.
            String status = "";
            RefSupport<String> refVar___0 = new RefSupport<String>();
            _mapCodeSystemStatus.TryGetValue(_listCodeSystems[i].CodeSystemName, refVar___0);
            status = refVar___0.getValue();
            row.getCells().add(status);
            row.setTag(_listCodeSystems[i]);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void butCheckUpdates_Click(Object sender, EventArgs e) throws Exception {
        butDownload.Enabled = false;
        try
        {
            String result = "";
            result = requestCodeSystemsXml();
            XmlDocument doc = new XmlDocument();
            doc.LoadXml(result);
            List<CodeSystem> listCodeSystemsAvailable = CodeSystems.getForCurrentVersion(_isMemberNation);
            for (int i = 0;i < listCodeSystemsAvailable.Count;i++)
            {
                String codeSystemName = listCodeSystemsAvailable[i].CodeSystemName;
                try
                {
                    XmlNode node = doc.SelectSingleNode("//" + codeSystemName);
                    if (node != null)
                    {
                        listCodeSystemsAvailable[i].VersionAvail = node.Attributes["VersionAvailable"].InnerText;
                    }
                    else
                    {
                        listCodeSystemsAvailable[i].VersionAvail = "N\\A";
                    } 
                    CodeSystems.Update(listCodeSystemsAvailable[i]);
                }
                catch (Exception __dummyCatchVar0)
                {
                    continue;
                }
            
            }
            //Might happen if they are running this tool without the right rows in the CodeSystem table? Maybe.
            //Don't prevent the rest of the code systems from being downloaded just because 1 failed.
            fillGrid();
            //It is now safe to allow downloading.
            butDownload.Enabled = true;
        }
        catch (Exception ex)
        {
            MessageBox.Show(Lan.g("CodeSystemImporter","Error" + ": " + ex.Message));
        }
    
    }

    private void butDownload_Click(Object sender, EventArgs e) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            //Do not let users download code systems when using the middle tier.
            MsgBox.show("CodeSystemImporter","Cannot download code systems when using the middle tier.");
            return ;
        }
         
        if (gridMain.getSelectedIndex() == -1)
        {
            MsgBox.show("CodeSystemImporter","No code systems selected.");
            return ;
        }
         
        _mapCodeSystemStatus.Clear();
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            CodeSystem codeSystem = _listCodeSystems[gridMain.getSelectedIndices()[i]];
            try
            {
                //Show warnings and prompts
                if (!preDownloadHelper(codeSystem.CodeSystemName))
                {
                    _mapCodeSystemStatus[codeSystem.CodeSystemName] = Lan.g("CodeSystemImporter","Import cancelled");
                    continue;
                }
                 
                //CPT codes require user to choose a local file so we will not do this on a thread.
                //We will handle the CPT import right here on the main thread before we start all other imports in parallel below.
                if (StringSupport.equals(codeSystem.CodeSystemName, "CPT"))
                {
                    //Default status for CPT codes. We will clear this below if the file is selected and unzipped succesfully.
                    _mapCodeSystemStatus[codeSystem.CodeSystemName] = Lan.g("CodeSystemImporter","To purchase CPT 2014 codes go to https://commerce.ama-assn.org/store/");
                    if (!MsgBox.show("CodeSystemImporter",MsgBoxButtons.OKCancel,"CPT 2014 codes must be purchased from the American Medical Association seperately in the data file format and must be named \"cpt-2014-data-files-download.zip\". " + "More information can be found in the online manual. " + "If you have already purchased the code file click OK to browse to the downloaded file."))
                    {
                        continue;
                    }
                     
                    OpenFileDialog fdlg = new OpenFileDialog();
                    fdlg.Title = Lan.g("CodeSystemImporter","Choose cpt-2014-data-files-download.zip CPT File");
                    fdlg.InitialDirectory = Environment.GetFolderPath(Environment.SpecialFolder.MyDocuments);
                    fdlg.Filter = "zip|*.zip";
                    fdlg.RestoreDirectory = true;
                    fdlg.Multiselect = false;
                    if (fdlg.ShowDialog() != DialogResult.OK)
                    {
                        continue;
                    }
                     
                    if (!fdlg.FileName.ToLower().Contains("cpt-2014-data-files-download.zip"))
                    {
                        _mapCodeSystemStatus[codeSystem.CodeSystemName] = Lan.g("CodeSystemImporter","Could not locate cpt-2014-data-files-download.zip in specified folder.");
                        continue;
                    }
                     
                    //Unzip the compressed file-----------------------------------------------------------------------------------------------------
                    boolean foundFile = false;
                    MemoryStream ms = new MemoryStream();
                    ZipFile unzipped = ZipFile.Read(fdlg.FileName);
                    try
                    {
                        {
                            for (int unzipIndex = 0;unzipIndex < unzipped.Count;unzipIndex++)
                            {
                                //unzip/write all files to the temp directory
                                ZipEntry ze = unzipped[unzipIndex];
                                if (!ze.FileName.ToLower().EndsWith("medu.txt.txt"))
                                {
                                    continue;
                                }
                                 
                                ze.Extract(Path.GetTempPath(), ExtractExistingFileAction.OverwriteSilently);
                                foundFile = true;
                            }
                        }
                    }
                    finally
                    {
                        if (unzipped != null)
                            Disposable.mkDisposable(unzipped).dispose();
                         
                    }
                    if (!foundFile)
                    {
                        _mapCodeSystemStatus[codeSystem.CodeSystemName] = Lan.g("CodeSystemImporter","MEDU.txt.txt file not found in zip archive.");
                        continue;
                    }
                     
                    UpdateCodeSystemThread.Add(CodeBase.ODFileUtils.CombinePaths(Path.GetTempPath(), "MEDU.txt.txt"), _listCodeSystems[gridMain.getSelectedIndices()[i]], new OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs() 
                      { 
                        //Add a new thread. We will run these all in parallel once we have them all queued.
                        //MEDU.txt.txt is not a typo. That is litterally how the resource file is realeased to the public!
                        public System.Void invoke(CodeSystem codeSystem, System.String status, System.Double percentDone, System.Boolean done, System.Boolean success) throws Exception {
                            updateCodeSystemThread_UpdateSafe(codeSystem, status, percentDone, done, success);
                        }

                        public List<OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs> getInvocationList() throws Exception {
                            List<OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs> ret = new ArrayList<OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs>();
                            ret.add(this);
                            return ret;
                        }
                    
                      });
                    //We got this far so the local file was retreived successfully. No initial status to report.
                    _mapCodeSystemStatus[codeSystem.CodeSystemName] = "";
                }
                else
                {
                    UpdateCodeSystemThread.Add(_listCodeSystems[gridMain.getSelectedIndices()[i]], new OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs() 
                      { 
                        //Add a new thread. We will run these all in parallel once we have them all queued.
                        //This codes system file does not exist on the system so it will be downloaded before being imported.
                        public System.Void invoke(CodeSystem codeSystem, System.String status, System.Double percentDone, System.Boolean done, System.Boolean success) throws Exception {
                            updateCodeSystemThread_UpdateSafe(codeSystem, status, percentDone, done, success);
                        }

                        public List<OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs> getInvocationList() throws Exception {
                            List<OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs> ret = new ArrayList<OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs>();
                            ret.add(this);
                            return ret;
                        }
                    
                      });
                } 
            }
            catch (Exception ex)
            {
                //Set status for this code system.
                _mapCodeSystemStatus[codeSystem.CodeSystemName] = Lan.g("CodeSystemImporter", ex.Message);
            }
        
        }
        //Threads are all ready to go start them all in parallel. We will re-enable these buttons when we handle the UpdateCodeSystemThread.Finished event.
        if (UpdateCodeSystemThread.startAll())
        {
            butDownload.Enabled = false;
            butCheckUpdates.Enabled = false;
        }
         
        fillGrid();
    }

    /**
    * Returns a list of available code systems.  Throws exceptions, put in try catch block.
    */
    private static String requestCodeSystemsXml() throws Exception {
        OpenDental.customerUpdates.Service1 updateService = new OpenDental.customerUpdates.Service1();
        updateService.setUrl(PrefC.getString(PrefName.UpdateServerAddress));
        if (!StringSupport.equals(PrefC.getString(PrefName.UpdateWebProxyAddress), ""))
        {
            IWebProxy proxy = new WebProxy(PrefC.getString(PrefName.UpdateWebProxyAddress));
            ICredentials cred = new NetworkCredential(PrefC.getString(PrefName.UpdateWebProxyUserName), PrefC.getString(PrefName.UpdateWebProxyPassword));
            proxy.Credentials = cred;
            updateService.Proxy = proxy;
        }
         
        return updateService.requestCodeSystems("");
    }

    //may throw error.  No security on this webmethod.
    /**
    * Used to show EULA or other pre-download actions.  Displays message boxes. Returns false if pre-download checks not satisfied.
    */
    private boolean preDownloadHelper(String codeSystemName) throws Exception {
        String programVersion = PrefC.getString(PrefName.ProgramVersion);
        System.String __dummyScrutVar0 = codeSystemName;
        //Code system specific pre-download actions.
        if (__dummyScrutVar0.equals("SNOMEDCT"))
        {
            String EULA = "Open Dental " + programVersion + " includes SNOMED Clinical Terms® (SNOMED CT®) which is used by permission of the International Health Terminology Standards Development Organization (IHTSDO). All rights reserved. SNOMED CT® was originally created by the College of American Pathologists. “SNOMED”, “SNOMED CT” and “SNOMED Clinical Terms” are registered trademarks of the IHTSDO (www.ihtsdo.org).\r\n" + 
            "Use of SNOMED CT in Open Dental " + programVersion + " is governed by the conditions of the following SNOMED CT Sub-license issued by Open Dental Software Inc.\r\n" + 
            "1. The meaning of the terms “Affiliate”, or “Data Analysis System”, “Data Creation System”, “Derivative”, “End User”, “Extension”, “Member”, “Non-Member Territory”, “SNOMED CT” and “SNOMED CT Content” are as defined in the IHTSDO Affiliate License Agreement (see www.ihtsdo.org/license.pdf).\r\n" + 
            "2. Information about Affiliate Licensing is available at www.ihtsdo.org/license. Individuals or organizations wishing to register as IHTSDO Affiliates can register at www.ihtsdo.org/salsa, subject to acceptance of the Affiliate License Agreement (see www.ihtsdo.org/license.pdf).\r\n" + 
            "3. The current list of IHTSDO Member Territories can be viewed at www.ihtsdo.org/members. Countries not included in that list are “Non-Member Territories”.\r\n" + 
            "4. End Users, that do not hold an IHTSDO Affiliate License, may access SNOMED CT® using Open Dental subject to acceptance of and adherence to the following sub-license limitations:\r\n" + 
            "  a) The sub-licensee is only permitted to access SNOMED CT® using this software (or service) for the purpose of exploring and evaluating the terminology.\r\n" + 
            "  b) The sub-licensee is not permitted the use of this software as part of a system that constitutes a SNOMED CT “Data Creation System” or “Data Analysis System”, as defined in the IHTSDO Affiliate License. This means that the sub-licensee must not use Open Dental " + programVersion + " to add or copy SNOMED CT identifiers into any type of record system, database or document.\r\n" + 
            "  c) The sub-licensee is not permitted to translate or modify SNOMED CT Content or Derivatives.\r\n" + 
            "  d) The sub-licensee is not permitted to distribute or share SNOMED CT Content or Derivatives.\r\n" + 
            "5. IHTSDO Affiliates may use Open Dental " + programVersion + " as part of a “Data Creation System” or “Data Analysis System” subject to the following conditions:\r\n" + 
            "  a) The IHTSDO Affiliate, using Open Dental " + programVersion + " must accept full responsibility for any reporting and fees due for use or deployment of such a system in a Non-Member Territory.\r\n" + 
            "  b) The IHTSDO Affiliate must not use Open Dental " + programVersion + " to access or interact with SNOMED CT in any way that is not permitted by the Affiliate License Agreement.\r\n" + 
            "  c) In the event of termination of the Affiliate License Agreement, the use of Open Dental " + programVersion + " will be subject to the End User limitations noted in 4.";
            MsgBoxCopyPaste FormMBCP = new MsgBoxCopyPaste(EULA);
            FormMBCP.ShowDialog();
            if (FormMBCP.DialogResult != DialogResult.OK)
            {
                MsgBox.show("CodeSystemImporter","SNOMED CT codes will not be imported.");
                return false;
            }
             
        }
        else //next selected index
        if (__dummyScrutVar0.equals("LOINC"))
        {
            //Main + third party
            String LoincEULA = "LOINC and RELMA version 2.44\r\n" + 
            "Released June 29, 2013\r\n" + 
            "\r\n" + 
            "This product includes all or a portion of the LOINC® table, LOINC panels and forms file, LOINC document ontology file, and/or LOINC hierarchies file, or is derived from one or more of the foregoing, subject to a license from Regenstrief Institute, Inc. Your use of the LOINC table, LOINC codes, LOINC panels and forms file, LOINC document ontology file, and LOINC hierarchies file also is subject to this license, a copy of which is available at http://loinc.org/terms-of-use. The current complete LOINC table, LOINC Users\' Guide, LOINC panels and forms file, LOINC document ontology file, and LOINC hierarchies file are available for download at http://loinc.org. The LOINC table and LOINC codes are copyright © 1995-2013, Regenstrief Institute, Inc. and the Logical Observation Identifiers Names and Codes (LOINC) Committee. The LOINC panels and forms file, LOINC document ontology file, and LOINC hierarchies file are copyright © 1995-2013, Regenstrief Institute, Inc. All rights reserved. THE LOINC TABLE (IN ALL FORMATS), LOINC PANELS AND FORMS FILE, LOINC DOCUMENT ONTOLOGY FILE, AND LOINC HIERARCHIES ARE PROVIDED \"AS IS.\"  ANY EXPRESS OR IMPLIED WARRANTIES ARE DISCLAIMED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. LOINC® is a registered United States trademark of Regenstrief Institute, Inc. A small portion of the LOINC table may include content (e.g., survey instruments) that is subject to copyrights owned by third parties. Such content has been mapped to LOINC terms under applicable copyright and terms of use. Notice of such third party copyright and license terms would need to be included if such content is included.\r\n" + 
            "\r\n" + 
            "Copyright Notice and License: http://loinc.org/terms-of-use \r\n" + 
            "The LOINC® codes, LOINC® table (regardless of format), LOINC® Release Notes, LOINC® Changes File, and LOINC® Users\' Guide are copyright © 1995-2013, Regenstrief Institute, Inc. and the Logical Observation Identifiers Names and Codes (LOINC) Committee. All rights reserved. \r\n" + 
            "The RELMA® program, RELMA® database and associated search index files (subject to the copyright above with respect to the LOINC® codes and LOINC® table included therein), RELMA® Community Mapping Feature Database, RELMA® Release Notes, and RELMA® Users\' Manual are copyright © 1995-2013, Regenstrief Institute, Inc. All rights reserved.\r\n" + 
            "The LOINC® panels and forms file, LOINC® document ontology file, and the LOINC® hierarchies file (subject to the copyright above with respect to the LOINC® codes and LOINC® table to the extent included therein), are copyright © 1995-2013, Regenstrief Institute, Inc. All rights reserved.\r\n" + 
            "LOINC® and RELMA® are registered United States trademarks of Regenstrief Institute, Inc.  \r\n" + 
            "Permission is hereby granted in perpetuity, without payment of license fees or royalties, to use, copy, or distribute the RELMA® program, RELMA® Users\' Manual, RELMA® Release Notes, RELMA® database and associated search index files, LOINC® codes, LOINC® Users\' Guide, LOINC® table (in all formats in which it is distributed by Regenstrief Institute, Inc. and the LOINC Committee), LOINC® Release Notes, LOINC® Changes File, LOINC® panels and forms file, LOINC® document ontology file, and LOINC® hierarchies file (collectively, the \"Licensed Materials\") for any commercial or non-commercial purpose, subject to the following terms and conditions:\r\n" + 
            "1. To prevent the dilution of the purpose of the LOINC codes and LOINC table of providing a definitive standard for identifying clinical information in electronic reports, users shall not use any of the Licensed Materials for the purpose of developing or promulgating a different standard for identifying patient observations, such as laboratory test results; other diagnostic service test results; clinical observations and measurements; reports produced by clinicians and diagnostic services about patients; panels, forms, and collections that define aggregations of these observations; and orders for these entities in electronic reports and messages.\r\n" + 
            "2. If the user elects to use the RELMA program, users receive the full RELMA database and associated search index files with the RELMA program, including the LOINC table and other database tables comprising the RELMA database. In addition to its use with the RELMA program, users may use the LOINC table by itself and may modify the LOINC table as permitted herein. Users may not use or modify the other database tables from the RELMA database or the associated search index files except in conjunction with their authorized use of the RELMA program, unless prior written permission is granted by the Regenstrief Institute, Inc. To request written permission, please contact loinc@regenstrief.org. The RELMA program also provides access to certain internet-based content copyrighted by Regenstrief Institute. No additional permission to modify or distribute this internet-based content is granted through the user’s use of the RELMA program.\r\n" + 
            "3. The RELMA program also includes the RELMA Community Mappings feature and access to the RELMA Community Mappings feature database. The accuracy and completeness of the information in the RELMA Community Mappings feature is not verified by Regenstrief or the LOINC Committee. Through the RELMA Community Mappings feature, users will have the option of submitting information, including user’s local mappings, back to the RELMA Community Mappings feature database.\r\n" + 
            "a. By using the RELMA Community Mappings feature, users agree as follows:\r\n" + 
            "i. Users may not copy, distribute, or share access to the information provided by the RELMA Community Mappings feature.\r\n" + 
            "ii. Users accept the risk of using the information provided by the RELMA Community Mappings feature, recognize that such information is submitted by other users, and understand that neither Regenstrief Institute, Inc. nor the LOINC Committee are liable for the information provided by the RELMA Community Mappings feature.\r\n" + 
            "iii. Regenstrief may contact users regarding:\r\n" + 
            "1. Use of the RELMA Community Mappings feature;\r\n" + 
            "2. Submission requests for additional information; and\r\n" + 
            "3. Any mapping submissions that the user makes to the RELMA Community Mappings feature database;\r\n" + 
            "iv. Others may contact user about submissions made to the RELMA Community Mappings feature database;\r\n" + 
            "v. Regenstrief may collect information about use of these services including, but not limited to:\r\n" + 
            "1. Device specific information such as hardware model, operating system, and version;\r\n" + 
            "2. Internet Protocol address;\r\n" + 
            "3. How user used the service (such as search queries run and about which LOINC code terms accessory information was reviewed);\r\n" + 
            "4. User’s contact name, email, and organization; and\r\n" + 
            "5. Regenstrief may associate this information with a user’s account on loinc.org;\r\n" + 
            "vi. User will make reasonable efforts to submit user’s mappings back to the RELMA Community Mappings feature database, which may contain the following information (as applicable):\r\n" + 
            "1. Local battery/panel/test code\r\n" + 
            "2. Local battery/panel/test name/description\r\n" + 
            "3. Units of Measure\r\n" + 
            "4. LOINC code to which it is mapped\r\n" + 
            "5. Date of mapping\r\n" + 
            "6. Language of test names\r\n" + 
            "7. Version of LOINC used to do the mapping\r\n" + 
            "8. Contact information;\r\n" + 
            "vii. If a user submits mappings on behalf of an organization, the user represents that the user has the authority to agree to these terms on behalf of user’s organization.\r\n" + 
            "viii. If a user submits mappings back to the RELMA Community Mappings feature database, then the user hereby grants, on behalf of themselves and user’s organization, Regenstrief a non-exclusive license without payment or fees to submitted mappings in perpetuity for purposes related to LOINC, RELMA, and Regenstrief’s mission, including, but not limited to:\r\n" + 
            "1. Making information publicly available;\r\n" + 
            "2. Performing aggregate analysis;\r\n" + 
            "3. Conducting and publishing research that does not identify user or user’s organization by name;\r\n" + 
            "4. Developing and enhancing LOINC and associated software tools. \r\n" + 
            "4. Users shall not change the meaning of any of the LOINC codes. Users shall not change the name of, or any contents of, any fields in the LOINC table. Users may add new fields to the LOINC table to attach additional information to existing LOINC records. Users shall not change the content or structure of the LOINC document ontology file or the LOINC panels and forms from the LOINC panels and forms file, but may notify the Regenstrief Institute of any potential inconsistencies or corrections needed by contacting loinc@regenstrief.org.\r\n" + 
            "5. A user may delete records from the LOINC table to deal with the user\'s local requirements. A user also may add new records to the LOINC table to deal with the users\' local requirements, provided that if new records are added, any new entry in the LOINC_NUM field of such new records must contain a leading alphabetic \"X\" so that the new codes and records cannot be confused with existing LOINC codes or new LOINC codes as they are defined in later releases of the LOINC table. Records deleted or added by users to deal with local requirements are not reflected in the official LOINC table maintained by the Regenstrief Institute and the LOINC Committee. Users must also make reasonable efforts to submit requests to LOINC for new records to cover observations that are not found in the LOINC table in order to minimize the need for X-codes.\r\n" + 
            "6. LOINC codes and other information from the LOINC table may be used in electronic messages for laboratory test results and clinical observations such as HL7 ORU messages, without the need to include this Copyright Notice and License or a reference thereto in the message (and without the need to include all fields required by Section 8 hereof). When the LOINC code (from the LOINC_NUM field) is included in the message, users are encouraged, but not required, to include the corresponding LOINC short name (from the SHORTNAME field) or the LOINC long common name (from the LONG_COMMON_NAME field) in the message if the message provides a place for a text name representation of the code. \r\n" + 
            "7. Users may make and distribute an unlimited number of copies of the Licensed Materials. Each copy thereof must include this Copyright Notice and License, and must include the appropriate version number of the Licensed Materials if the Licensed Materials have a version number, or the release date if the Licensed Materials do not have a version number. This Copyright Notice and License must appear on every printed copy of the LOINC table. Where the Licensed Materials are distributed on a fixed storage medium (such as CD-ROM), a printed copy of this Copyright Notice and License must be included on or with the storage medium, and a text file containing this information also must be stored on the storage medium in a file called \"license.txt\". Where the Licensed Materials are distributed via the Internet, this Copyright Notice and License must be accessible on the same Internet page from which the Licensed Materials are available for download. This Copyright Notice and License must appear verbatim on every electronic or printed copy of the RELMA Users\' Manual and the LOINC Users\' Guide. The RELMA Users\' Manual and the LOINC Users\' Guide may not be modified, nor may derivative works of the RELMA Users\' Manual or LOINC Users\' Guide be created, without the prior written permission of the Regenstrief Institute, Inc. To request written permission, please contact loinc@regenstrief.org. The Regenstrief Institute retains the right to approve any modification to, or derivative work of, the RELMA Users\' Manual or the LOINC Users\' Guide.\r\n" + 
            "8.  Subject to Section 1 and the other restrictions hereof, users may incorporate portions of the LOINC table, LOINC panels and forms file, LOINC document ontology file, and LOINC hierarchies file into another master term dictionary (e.g. laboratory test definition database), or software program for distribution outside of the user\'s corporation or organization, provided that any such master term dictionary or software program includes the following fields reproduced in their entirety from the LOINC table: LOINC_NUM, COMPONENT, PROPERTY, TIME_ASPCT, SYSTEM, SCALE_TYP, METHOD_TYP, STATUS, and SHORTNAME. Users are also required to either: (1) include the EXTERNAL_COPYRIGHT_NOTICE or (2) delete the rows that include third party copyrighted content (e.g., third party survey instruments and answers). If third party content is included, users are required to comply with any such third party copyright license terms. Users are encouraged, but not required, to also include the RelatedNames2 and the LONG_COMMON_NAME in any such database. Further description of these fields is provided in Appendix A of the LOINC Users\' Guide. Every copy of the LOINC table, LOINC panels and forms file, LOINC document ontology file, and/or LOINC hierarchies file incorporated into or distributed in conjunction with another database or software program must include the following notice:\n" + 
            "\n" + 
            "\"This product includes all or a portion of the LOINC® table, LOINC panels and forms file, LOINC document ontology file, and/or LOINC hierarchies file, or is derived from one or more of the foregoing, subject to a license from Regenstrief Institute, Inc. Your use of the LOINC table, LOINC codes, LOINC panels and forms file, LOINC document ontology file, and LOINC hierarchies file also is subject to this license, a copy of which is available at http://loinc.org/terms-of-use. The current complete LOINC table, LOINC Users\' Guide, LOINC panels and forms file, LOINC document ontology file, and LOINC hierarchies file are available for download athttp://loinc.org. The LOINC table and LOINC codes are copyright © 1995-2013, Regenstrief Institute, Inc. and the Logical Observation Identifiers Names and Codes (LOINC) Committee. The LOINC panels and forms file, LOINC document ontology file, and LOINC hierarchies file are copyright © 1995-2013, Regenstrief Institute, Inc. All rights reserved. THE LOINC TABLE (IN ALL FORMATS), LOINC PANELS AND FORMS FILE, LOINC DOCUMENT ONTOLOGY FILE, AND LOINC HIERARCHIES ARE PROVIDED \"AS IS.\"  ANY EXPRESS OR IMPLIED WARRANTIES ARE DISCLAIMED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. LOINC® is a registered United States trademark of Regenstrief Institute, Inc. A small portion of the LOINC table may include content (e.g., survey instruments) that is subject to copyrights owned by third parties. Such content has been mapped to LOINC terms under applicable copyright and terms of use. Notice of such third party copyright and license terms would need to be included if such content is included.\"\n" + 
            "\n" + 
            "If the master term dictionary or software program containing the LOINC table, LOINC panels and forms file, LOINC document ontology file, and/or LOINC hierarchies file is distributed with a printed license, this statement must appear in the printed license. Where the master term dictionary or software program containing the LOINC table, LOINC panels and forms file, LOINC document ontology file, and/or LOINC hierarchies file is distributed on a fixed storage medium, a text file containing this information also must be stored on the storage medium in a file called \"LOINC_short_license.txt\". Where the master term dictionary or software program containing the LOINC table, LOINC panels and forms file, LOINC document ontology file, and/or LOINC hierarchies file is distributed via the Internet, this information must be accessible on the same Internet page from which the product is available for download. \r\n" + 
            "9. Subject to Section 1 and the other restrictions hereof, users may incorporate portions of the LOINC table and LOINC panels and forms file into another document (e.g., an implementation guide or other technical specification) for distribution outside of the user\'s corporation or organization, subject to these terms:\r\n" + 
            "a. Every copy of the document that contains portions of the LOINC table or LOINC panels and forms file must include the following notice:\n" + 
            "\n" + 
            "\"This material contains content from LOINC® (http://loinc.org). The LOINC table, LOINC codes, and LOINC panels and forms file are copyright © 1995-2013, Regenstrief Institute, Inc. and the Logical Observation Identifiers Names and Codes (LOINC) Committee and available at no cost under the license at http://loinc.org/terms-of-use.”\r\n" + 
            "b. Users are strongly encouraged, but not required, to indicate the appropriate version number of the Licensed Material used.\r\n" + 
            "c. Any information in the document that is extracted from the LOINC table or LOINC panels and forms file must always be associated with the corresponding LOINC code.\r\n" + 
            "d. Along with the LOINC code, users are required to include one of the following LOINC display names:\r\n" + 
            "i. The fully-specified name, which includes the information from the COMPONENT, PROPERTY, TIME_ASPCT, SYSTEM, SCALE_TYP, and METHOD_TYP fields;\r\n" + 
            "ii. The LOINC short name (from the SHORTNAME field); and\r\n" + 
            "iii. The LOINC long common name (from the LONG_COMMON_NAME field).\r\n" + 
            "e. Users are also required to either:\r\n" + 
            "i. Include the EXTERNAL_COPYRIGHT_NOTICE, or\r\n" + 
            "ii. Exclude information from the rows that include third party copyrighted content (e.g., third party survey instruments and answers). If third party content is included, users are required to comply with any such third party copyright license terms.\r\n" + 
            "10. Use and distribution of the Licensed Materials in ways that are not specifically discussed herein shall always be accompanied by the notice provided in Section 8 hereof. The guidelines for providing the notice that are contained in the last paragraph of Section 8 also shall apply. If a user has a question about whether a particular use of any of the Licensed Materials is permissible, the user is invited to contact the Regenstrief Institute by e-mail at loinc@regenstrief.org.\r\n" + 
            "11. If the user desires to translate any of the Licensed Materials into a language other than English, then user shall notify Regenstrief via email at loinc@regenstrief.org. Any such translation is a derivative work, and the user agrees and does hereby assign all right, title and interest in and to such derivative work: (1) to Regenstrief and the LOINC Committee if the translation is a derivative of the LOINC codes, LOINC Users\' Guide, or LOINC table, and (2) to Regenstrief if the translation is a derivative work of the RELMA program, LOINC panels and forms file, LOINC document ontology file, LOINC hierarchies file, RELMA Users\' Manual, RELMA database or associated search index files. Further, user shall fully cooperate with Regenstrief in the filing and reviewing of any copyright applications or other legal documents, and signing any documents (such as declarations, assignments, affidavits, and the like) that are reasonably necessary to the preparation of any such copyright application. The assignment granted by this paragraph extends to all proprietary rights both in the United States, and in all foreign countries. No other right to create a derivative work of any of the Licensed Materials is hereby granted (except the right to translate into a language other than English granted in this Section), and Regenstrief and the LOINC Committee respectively reserve all other rights not specifically granted herein. All such translations shall be electronically transmitted to Regenstrief, and such translations shall be made available and are subject to the same license rights and restrictions contained herein. Regenstrief will give credit on the LOINC website (and on screens in RELMA) to the user and/or entity that did the translation.\r\n" + 
            "12. The Regenstrief Institute, Inc. and the LOINC Committee welcome requests for new LOINC content (terms, codes, or associated material such as text descriptions and synonyms) and suggestions about revisions to existing content within the Licensed Materials. Any content submitted in conjunction with such a request is subject to the LOINC Submissions Policy, which is available at http://loinc.org/submissions-policy.\r\n" + 
            "13. The names \"Regenstrief,\" \"Regenstrief Foundation,\" \"Regenstrief Institute,\" and \"LOINC Committee\" may not be used in a way which could be interpreted as an endorsement or a promotion of any product or service without prior written permission of the Regenstrief Institute, Inc. Further, no right to use the trademarks of Regenstrief is licensed hereunder. To request written permission, please contact loinc@regenstrief.org.\r\n" + 
            "14. DISCLAIMER:  REGENSTRIEF INSTITUTE, INC. AND THE LOINC COMMITTEE, AS WELL AS ANY CONTRIBUTORS WHO HAVE PROVIDED TRANSLATIONS OF THE LICENSED MATERIALS, DO NOT ACCEPT LIABILITY FOR ANY OMISSIONS OR ERRORS IN THE LICENSED MATERIALS OR ANY OTHER MATERIALS OBTAINED FROM REGENSTRIEF INSTITUTE, INC. AND/OR THE LOINC COMMITTEE. THE LICENSED MATERIALS AND ALL OTHER MATERIALS OBTAINED FROM REGENSTRIEF INSTITUTE, INC. AND/OR THE LOINC COMMITTEE ARE PROVIDED \"AS IS,\" WITHOUT WARRANTY OF ANY KIND. ANY EXPRESSED OR IMPLIED WARRANTIES ARE HEREBY DISCLAIMED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF TITLE, NON-INFRINGEMENT, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE AND WARRANTIES ARISING FROM A COURSE OF DEALING, TRADE USAGE, OR TRADE PRACTICE. FURTHER, NO WARRANTY OR REPRESENTATION IS MADE CONCERNING THE ACCURACY, COMPLETENESS, SEQUENCE, TIMELINESS OR AVAILABILITY OF THE LICENSED MATERIALS OR ANY OTHER MATERIALS OBTAINED FROM REGENSTRIEF INSTITUTE, INC. AND/OR THE LOINC COMMITTEE, OR ANY TRANSLATIONS OR DERIVATIVE WORKS OF ANY OF THE FOREGOING. IN NO EVENT SHALL REGENSTRIEF INSTITUTE, INC. OR THE LOINC COMMITTEE OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, RELIANCE, OR CONSEQUENTIAL DAMAGES OR ATTORNEYS\' FEES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; OPPORTUNITY COSTS; LOSS OF USE, DATA, SAVINGS OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THE LICENSED MATERIALS OR ANY OTHER MATERIALS OBTAINED FROM REGENSTRIEF INSTITUTE, INC. AND/OR THE LOINC COMMITTEE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE OR IF SUCH DAMAGES WERE FORESEEABLE. SOME JURISDICTIONS DO NOT ALLOW THE LIMITATION OR EXCLUSION OF CERTAIN WARRANTIES OR CONDITIONS, SO SOME OF THE FOREGOING MAY NOT APPLY TO YOU.\r\n" + 
            "15. This license shall be construed and interpreted in accordance with the laws of the State of Indiana, United States of America, excluding its conflicts of law rules.\r\n" + 
            " \r\n" + 
            "Notice of Third Party Content and Copyright Terms\r\n" + 
            "A small portion of the content of the LOINC table, LOINC panels and forms file, LOINC document ontology file, LOINC hierarchies file, RELMA database and associated search index files consists of content subject to copyright from third parties. This third party content is either used with permission or under the applicable terms of use. In all such cases, we have included the copyright notice. The copyright of the LOINC codes per se remain owned by Regenstrief Institute, Inc. and the LOINC Committee and subject to the LOINC Copyright Notice and License.\r\n" + 
            "The third party content is identified in the LOINC table by the applicable copyright notice (up to 250 characters) stored in the EXTERNAL_COPYRIGHT_NOTICE field. In RELMA and our web-based search application (http://search.loinc.org), the third party content is highlighted as follows: When such content appears in a search result grid, the programs will display a field with a link to a page containing the copyright notice and terms of use for that content. The programs may also visually highlight the rows of these LOINC codes.\r\n" + 
            "We have included third party content that allows use and distribution at least for clinical, administrative, and research purposes. The third party copyright owners generally ask for attribution of the source, allow the free use of the content for treatment, health care management, and research purposes. They generally forbid alteration of their content (e.g., survey questions and/or answers) and use for commercial purpose, which usually means the direct sale of the survey instruments. They often do allow use of their content in commercial software, medical record and other clinical database systems, and the messaging of patient information collected through the use of these instruments.";
            MsgBoxCopyPaste FormLoincEula = new MsgBoxCopyPaste(LoincEULA);
            FormLoincEula.ShowDialog();
            if (FormLoincEula.DialogResult != DialogResult.OK)
            {
                MsgBox.show("CodeSystemImporter","LOINC codes will not be imported.");
                return false;
            }
             
            //next selected index
            String externalCopyright = "External Copyright Notice\r\n" + 
            "\r\n" + 
            "Copyright © 2010 FACIT.org. Used with permission\r\n" + 
            "\r\n" + 
            "InterRAI holds the copyright to Version 2.0 of the RAI for long term care outside of the US. Content for MDS items in LOINC was derived from Version 2.0 of the RAI/MDS, and should not be reproduced outside of the United States without permission of InterRAI. Within the US, Version 2.0 is in the public domain.\r\n" + 
            "\r\n" + 
            "©2010 PROMIS Health Organization or other individuals/entities that have contributed information and materials to Assessment Center, and are being used with the permission of the copyright holders. Use of PROMIS instruments (e.g., item banks, short forms, profile measures) are subject to the PROMIS Terms and Conditions available at: http://www.nihpromis.org/Web%20Pages/Network%20Testing.aspx\r\n" + 
            "\r\n" + 
            "©2010 David Cella, PhD. Used with permission. All of these Neuro-QOL instruments can be used at no charge. We do ask, however, that you contact Neuro-QOL project manager, Vitali Ustsinovich, at v-ustsinovich@northwestern.edu and let him know that you intend to use Neuro-QOL.\r\n" + 
            "\r\n" + 
            "The Outcome and Assessment Information Set (OASIS)© Copyright © 2002 Center for Health Services Research, UCHSC, Denver, CO. All Rights Reserved.\r\n" + 
            "\r\n" + 
            "Copyright © Pfizer Inc. All rights reserved. Developed by Drs. Robert L. Spitzer, Janet B.W. Williams, Kurt Kroenke and colleagues, with an educational grant from Pfizer Inc. No permission required to reproduce, translate, display or distribute.\r\n" + 
            "\r\n" + 
            "©2005 American Physical Therapy Association. All rights reserved. Used with permission per LOINC Terms of Use, and in addition: Any further distribution or reproduction must include the following copyright statement: \"Copyright �2005 American Physical Therapy Association. All rights reserved.\" Contact permissions@apta.org with questions or for further information.\r\n" + 
            "\r\n" + 
            "©2004. All rights reserved. Used with permission per LOINC Terms of Use. The Veterans RAND 12 Item Health Survey (VR-12) was developed from the Veterans RAND 36 Item Health Survey (VR-36) which was developed from the MOS RAND SF-36 Version 1.0. Details involving the VR-36 and VR-12 questionnaires, scoring algorithms for PCS and MCS summary measures, and imputation programs for missing values can be obtained on request by agreeing to the stipulations given by the RAND Corporation website (See http://www.rand.org/health/surveys_tools/mos/mos_core_36item_terms.html) in a letter to Dr. Lewis Kazis (lek@bu.edu) on institutional letter head. Users of the VR-12 or VR-36 in clinical research studies should also notify Dr. Kazis.\r\n" + 
            "\r\n" + 
            "©2001 Authors: Suzann K. Campbell, Gay L. Girolami, Thubi, H. A. Kolobe, Elizabeth T. Osten, Maureen C. Lenke. All rights reserved. Reproduced with permission.\r\n" + 
            "\r\n" + 
            "Copyright ©PSM ZI Mannheim /Germany\r\n" + 
            "\r\n" + 
            "© World Health Organization 2006© World Health Organization 2006. All rights reserved. Used with permission. Publications of the World Health Organization can be obtained from WHO Press, World Health Organization, 20 Avenue Appia, 1211 Geneva 27, Switzerland (tel: +41 22 791 2476; fax: +41 22 791 4857; email: bookorders@who.int). Requests for permission to reproduce or translate WHO publications - whether for sale or for noncommercial distribution - should be addressed to WHO Press, at the above address (fax: +41 22 791 4806; email: permissions@who.int).The designations employed and the presentation of the material in this publication do not imply the expression of any opinion whatsoever on the part of the World Health Organization concerning the legal status of any country, territory, city or area or of its authorities, or concerning the delimitation of its frontiers or boundaries. Dotted lines on maps represent approximate border lines for which there may not yet be full agreement.\r\n" + 
            "\r\n" + 
            "Used with permission\r\n" + 
            "\r\n" + 
            "© World Health Organization 2006. All rights reserved. Used with permission. Publications of the World Health Organization can be obtained from WHO Press, World Health Organization, 20 Avenue Appia, 1211 Geneva 27, Switzerland (tel: +41 22 791 2476; fax: +41 22 791 4857; email: bookorders@who.int). Requests for permission to reproduce or translate WHO publications - whether for sale or for noncommercial distribution - should be addressed to WHO Press, at the above address (fax: +41 22 791 4806; email: permissions@who.int).The designations employed and the presentation of the material in this publication do not imply the expression of any opinion whatsoever on the part of the World Health Organization concerning the legal status of any country, territory, city or area or of its authorities, or concerning the delimitation of its frontiers or boundaries. Dotted lines on maps represent approximate border lines for which there may not yet be full agreement.The mention of specific companies or of certain manufacturers\' products does not imply that they are endorsed or recommended by the World Health Organization in preference to others of a similar nature that are not mentioned. Errors and omissions excepted, the names of proprietary products are distinguished by initial capital letters.All reasonable precautions have been taken by WHO to verify the information contained in this publication. However, the published material is being distributed without warranty of any kind, either express or implied. The responsibility for the interpretation and use of the material lies with the reader. In no event shall the World Health Organization be liable for damages arising from its use.\r\n" + 
            "\r\n" + 
            "Copyright © 1996-2005 John Spertus MD MPH, used with permission\r\n" + 
            "\r\n" + 
            "Adapted from: Inouye SK, vanDyck CH, Alessi CA, Balkin S, Siegal AP, Horwitz RI. Clarifying confusion: The Confusion Assessment Method. A new method for detection of delirium. Ann Intern Med. 1990; 113: 941-948. Confusion Assessment Method: Training Manual and Coding Guide, Copyright 2003, Sharon K. Inouye, M.D., MPH.\r\n" + 
            "\r\n" + 
            "Portions © 1997 Barbara Huffines, used with permission\r\n" + 
            "\r\n" + 
            "© Barbara Braden and Nancy Bergstrom 1988. All rights reserved. Used with permission. It is understood that the name of the instrument and the indication that the copyright belongs to Braden and Bergstrom remain on any copies and that you do not make any changes to the wording or scoring of this tool.\r\n" + 
            "\r\n" + 
            "© 2002, The Regents of the University of Michigan. Include the following when printing the FLACC on documentation records, etc: Printed with permission © 2002, The Regents of the University of Michigan\r\n" + 
            "\r\n" + 
            "Used with permission.\r\n" + 
            "\r\n" + 
            "© World Health Organization 2006. All rights reserved. Used with permission. Publications of the World Health Organization can be obtained from WHO Press, World Health Organization, 20 Avenue Appia, 1211 Geneva 27, Switzerland (tel: +41 22 791 2476; fax: +41 22 791 4857; email: bookorders@who.int). Requests for permission to reproduce or translate WHO publications - whether for sale or for noncommercial distribution - should be addressed to WHO Press, at the above address (fax: +41 22 791 4806; email: permissions@who.int).The designations employed and the presentation of the material in this publication do not imply the expression of any opinion whatsoever on the part of the World Health Organization concerning the legal status of any country, territory, city or area or of its authorities, or concerning the delimitation of its frontiers or boundaries. Dotted lines on maps represent approximate border lines for which there may not yet be full agreement.\r\n" + 
            "\r\n" + 
            "©1986 Regents of the University of Minnesota, All rights reserved.  Do not copy or reproduce without permission. LIVING WITH HEART FAILURE� is a registered trademark of the Regents of the University of Minnesota.\r\n" + 
            "\r\n" + 
            "Copyright © The Hartford Institute for Geriatric Nursing, College of Nursing, New York University, used with permission\r\n" + 
            "\r\n" + 
            "MiniCog\r\n" + 
            "\r\n" + 
            "Copyright © Pfizer Inc. All rights reserved. Reproduced with permission.Developed by Drs. Robert L. Spitzer, Janet B.W. Williams, Kurt Kroenke and colleagues, with an educational grant from Pfizer Inc. No permission required to reproduce, translate, display or distribute.\r\n" + 
            "\r\n" + 
            "Copyright 2011 International Association for the Study of Pain (IASP). Used with permission. Before translating text into a language other than found on the Pediatric Pain Sourcebook (www.painsourcebook.ca), please contact IASP to gain permission.\r\n" + 
            "\r\n" + 
            "Copyright © Pfizer Inc. All rights reserved. Reproduced with permission.\r\n" + 
            "\r\n" + 
            "Copyright © 2009 by Paul H. Brookes Publishing Co., Inc\r\n" + 
            "\r\n" + 
            "Copyright ©NPUAP, used with permission of the National Pressure Ulcer Advisory Panel 2012\r\n";
            MsgBoxCopyPaste FormLoincEula2 = new MsgBoxCopyPaste(externalCopyright);
            FormLoincEula2.ShowDialog();
            if (FormLoincEula2.DialogResult != DialogResult.OK)
            {
                MsgBox.show("CodeSystemImporter","LOINC codes will not be imported.");
                return false;
            }
             
        }
        else //next selected index
        if (__dummyScrutVar0.equals("UCUM"))
        {
            String UcumEULA = "Unified Codes for Units of Measures (UCUM) version 1.7\r\n" + 
            "This product includes all or a portion of the UCUM table, UCUM codes, and UCUM definitions or is derived from it, subject to a license from Regenstrief Institute, Inc. and The UCUM Organization. Your use of the UCUM table, UCUM codes, UCUM definitions also is subject to this license, a copy of which is available at http://unitsofmeasure.org. The current complete UCUM table, UCUM Specification are available for download at http://unitsofmeasure.org. The UCUM table and UCUM codes are copyright © 1995-2009, Regenstrief Institute, Inc. and the Unified Codes for Units of Measures (UCUM) Organization. All rights reserved.\r\n" + 
            "\r\n" + 
            "THE UCUM TABLE (IN ALL FORMATS), UCUM DEFINITIONS, AND SPECIFICATION ARE PROVIDED \"AS IS.\" ANY EXPRESS OR IMPLIED WARRANTIES ARE DISCLAIMED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. \r\n" + 
            "\r\n" + 
            "Copyright Notice and License\r\n" + 
            "The UCUM codes, UCUM table (regardless of format), and UCUM Specification are copyright © 1999-2009, Regenstrief Institute, Inc. and the Unified Codes for Units of Measures (UCUM) Organization. All rights reserved.\r\n" + 
            "Regenstrief Institute, Inc. and the Unified Codes for Units of Measures (UCUM) Organization are hereunder collectively referred to as \"The Organization\".\r\n" + 
            "Permission is hereby granted in perpetuity, without payment of license fees or royalties, to use, copy, or distribute the UCUM codes, UCUM Specification, and UCUM table (in all formats in which it is distributed by The Organization and the UCUM Organization) (collectively, the \"Licensed Materials\") for any commercial or non-commercial purpose, subject to the following terms and conditions:\r\n" + 
            "1) To prevent the dilution of the purpose of the Licensed Materials, i.e., that of providing a definitive standard for identifying units of measures in electronic documents and messages, users shall not use any of the Licensed Materials for the purpose of developing or promulgating a different standard for identifying units of measure, regardless of whether the intended use is in the field of medicine, or any other field of science or trade.\r\n" + 
            "2) Users shall not modify the Licensed Materials and may not distribute modified versions of the UCUM table (regardless of format) or UCUM Specification. Users shall not modify any existing contents, fields, description, or comments of the Licensed Materials, and may not add any new contents to it.\r\n" + 
            "3) Users shall not use any of the UCUM codes in a way that expressly or implicitly changes their meaning.\r\n" + 
            "4) RESERVED\r\n" + 
            "5) UCUM codes and other information from the UCUM table may be used in electronic messages communicating measurements without the need to include this Copyright Notice and License or a reference thereto in the message (and without the need to include all fields required by Section 7 hereof).\r\n" + 
            "6) Users may make and distribute an unlimited number of copies of the Licensed Materials. Each copy thereof must include this Copyright Notice and License, and must include the appropriate version or revision number of the Licensed Materials if the Licensed Materials have a version or revision number, or the release date if the Licensed Materials do not have a version or revision number. This Copyright Notice and License must appear on every printed copy of the Licensed Materials. Where the Licensed Materials are distributed on a fixed storage medium (such as a CD-ROM), a printed copy of this Copyright Notice and License must be included on or with the storage medium, and a text file containing this information also must be stored on the storage medium in a file called \"license.txt\". Where the Licensed Materials are distributed via the Internet, this Copyright Notice and License must be accessible on the same Internet page from which the Licensed Materials are available for download. This Copyright Notice and License must appear verbatim on every electronic or printed copy of the Licensed Materials. The UCUM Specification and related documents may not be modified, nor may derivative works be created from it, without the prior written permission of The Organization. To request written permission, please contact ucum@… (at unitsofmeasure.org). The Organization retains the right to approve any modification to, or derivative work of the Licensed Materials.\r\n" + 
            "7) Subject to Section 1 and the other restrictions hereof, users may incorporate portions of the UCUM table and definitions into another master term dictionary (e.g. laboratory test definition database), or software program for distribution outside of the user\'s corporation or organization, provided that any such master term dictionary or software program includes the following fields reproduced in their entirety from the UCUM table: UCUM code, definition value and unit. Every copy of the UCUM table incorporated into or distributed in conjunction with another database or software program must include the following notice:\r\n" + 
            "\"This product includes all or a portion of the UCUM table, UCUM codes, and UCUM definitions or is derived from it, subject to a license from Regenstrief Institute, Inc. and The UCUM Organization. Your use of the UCUM table, UCUM codes, UCUM definitions also is subject to this license, a copy of which is available at http://unitsofmeasure.org. The current complete UCUM table, UCUM Specification are available for download at http://unitsofmeasure.org. The UCUM table and UCUM codes are copyright © 1995-2009, Regenstrief Institute, Inc. and the Unified Codes for Units of Measures (UCUM) Organization. All rights reserved.\r\n" + 
            "THE UCUM TABLE (IN ALL FORMATS), UCUM DEFINITIONS, AND SPECIFICATION ARE PROVIDED \"AS IS.\" ANY EXPRESS OR IMPLIED WARRANTIES ARE DISCLAIMED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.\"\r\n" + 
            "If the master term dictionary or software program containing the UCUM table, UCUM definitions and/or UCUM specification is distributed with a printed license, this statement must appear in the printed license. Where the master term dictionary or software program containing the UCUM table, UCUM definitions, and/or UCUM specification is distributed on a fixed storage medium, a text file containing this information also must be stored on the storage medium in a file called \"UCUM_short_license.txt\". Where the master term dictionary or software program containing the UCUM table, UCUM definitions, and/or UCUM specification is distributed via the Internet, this information must be accessible on the same Internet page from which the product is available for download.\r\n" + 
            "8) Use and distribution of the Licensed Materials in ways that are not specifically discussed herein shall always be accompanied by the notice provided in Section 7 hereof. The guidelines for providing the notice that are contained in the last paragraph of Section 7 also shall apply. If a user has a question about whether a particular use of any of the Licensed Materials is permissible, the user is invited to contact The Organization by e-mail at ucum@… (at unitsofmeasure.org).\r\n" + 
            "9) If the user desires to translate any of the Licensed Materials into a language other than English, then user shall notify The Organization via email at ucum@… (at unitsofmeasure.org). Any such translation is a derivative work, and the user agrees and does hereby assign all right, title and interest in and to such derivative work to The Organization. Further, user shall fully cooperate with The Organization in the filing and reviewing of any copyright applications or other legal documents, and signing any documents (such as declarations, assignments, affidavits, and the like) that are reasonably necessary to the preparation of any such copyright application. The assignment granted by this paragraph extends to all proprietary rights both in the United States, and in all foreign countries. No other right to create a derivative work of any of the Licensed Materials is hereby granted (except the right to translate into a language other than English granted in this Section 9), and The Organization reserves all other rights not specifically granted herein. All such translations shall be electronically transmitted to The Organization, and such translations shall be made available and are subject to the same license rights and restrictions contained herein. The Organization will give credit on its website to the user and/or entity that did the translation.\r\n" + 
            "10) The Organization welcome requests for new UCUM content (terms, codes or associated material such as text descriptions and synonyms) and suggestions about revisions to existing content within the Licensed Materials. Such submissions should be done on The Organization\'s web site http://unitsofmeasure.org/trac/newticket.\r\n" + 
            "11) The names \"UCUM Organization\" may not be used in a way which could be interpreted as an endorsement or a promotion of any product or service without prior written permission of The Organization. To request written permission, please contact ucum@… (at unitsofmeasure.org).\r\n" + 
            "12) DISCLAIMER: REGENSTRIEF INSTITUTE, INC. AND THE UCUM ORGANIZATION DO NOT ACCEPT LIABILITY FOR ANY OMISSIONS OR ERRORS IN THE LICENSED MATERIALS OR ANY OTHER MATERIALS OBTAINED FROM REGENSTRIEF INSTITUTE, INC. AND/OR THE UCUM ORGANIZATION. THE LICENSED MATERIALS AND ALL OTHER MATERIALS OBTAINED FROM REGENSTRIEF INSTITUTE, INC. AND/OR THE UCUM ORGANIZATION ARE PROVIDED \"AS IS,\" WITHOUT WARRANTY OF ANY KIND. ANY EXPRESSED OR IMPLIED WARRANTIES ARE HEREBY DISCLAIMED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF TITLE, NON-INFRINGEMENT, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE AND WARRANTIES ARISING FROM A COURSE OF DEALING, TRADE USAGE, OR TRADE PRACTICE. FURTHER, NO WARRANTY OR REPRESENTATION IS MADE CONCERNING THE ACCURACY, COMPLETENESS, SEQUENCE, TIMELINESS OR AVAILABILITY OF THE LICENSED MATERIALS OR ANY OTHER MATERIALS OBTAINED FROM REGENSTRIEF INSTITUTE, INC. AND/OR THE UCUM ORGANIZATION, OR ANY TRANSLATIONS OR DERIVATIVE WORKS OF ANY OF THE FOREGOING. IN NO EVENT SHALL REGENSTRIEF INSTITUTE, INC. OR THE UCUM ORGANIZATION OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, RELIANCE, OR CONSEQUENTIAL DAMAGES OR ATTORNEYS\' FEES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; OPPORTUNITY COSTS; LOSS OF USE, DATA, SAVINGS OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THE LICENSED MATERIALS OR ANY OTHER MATERIALS OBTAINED FROM REGENSTRIEF INSTITUTE, INC. AND/OR THE UCUM ORGANIZATION, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE OR IF SUCH DAMAGES WERE FORESEEABLE. SOME JURISDICTIONS DO NOT ALLOW THE LIMITATION OR EXCLUSION OF CERTAIN WARRANTIES OR CONDITIONS, SO SOME OF THE FOREGOING MAY NOT APPLY TO YOU.\r\n" + 
            "13) This license shall be construed and interpreted in accordance with the laws of the State of Indiana, United States of America, excluding its conflicts of law rules.";
            MsgBoxCopyPaste FormUcumEULA = new MsgBoxCopyPaste(UcumEULA);
            FormUcumEULA.ShowDialog();
            if (FormUcumEULA.DialogResult != DialogResult.OK)
            {
                MsgBox.show("CodeSystemImporter","UCUM codes will not be imported.");
                return false;
            }
             
        }
           
        return true;
    }

    //next selected index
    /**
    * Call this from external thread. Invokes to main thread to avoid cross-thread collision.
    */
    private void updateCodeSystemThread_FinishedSafe(Object sender, EventArgs e) throws Exception {
        try
        {
            this.BeginInvoke(new EventHandler(UpdateCodeSystemThread_FinishedUnsafe), new Object[]{ sender, e });
        }
        catch (Exception __dummyCatchVar1)
        {
        }
    
    }

    //most likely because form is no longer available to invoke to
    /**
    * Do not call this directly from external thread. Use UpdateCodeSystemThread_FinishedSafe.
    */
    private void updateCodeSystemThread_FinishedUnsafe(Object sender, EventArgs e) throws Exception {
        butCheckUpdates.Enabled = true;
        butDownload.Enabled = true;
    }

    /**
    * Call this from external thread. Invokes to main thread to avoid cross-thread collision.
    */
    private void updateCodeSystemThread_UpdateSafe(CodeSystem codeSystem, String status, double percentDone, boolean done, boolean success) throws Exception {
        try
        {
            this.BeginInvoke(new OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs() 
              { 
                public System.Void invoke(CodeSystem codeSystem, System.String status, System.Double percentDone, System.Boolean done, System.Boolean success) throws Exception {
                    updateCodeSystemThread_UpdateUnsafe(codeSystem, status, percentDone, done, success);
                }

                public List<OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs> getInvocationList() throws Exception {
                    List<OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs> ret = new ArrayList<OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs>();
                    ret.add(this);
                    return ret;
                }
            
              }, new Object[]{ codeSystem, status, percentDone, done, success });
        }
        catch (Exception __dummyCatchVar2)
        {
        }
    
    }

    //most likely because form is no longer available to invoke to
    /**
    * Do not call this directly from external thread. Use UpdateCodeSystemThread_UpdateSafe.
    */
    private void updateCodeSystemThread_UpdateUnsafe(CodeSystem codeSystem, String status, double percentDone, boolean done, boolean success) throws Exception {
        for (int i = 0;i < gridMain.getRows().Count;i++)
        {
            //This is called a lot from the import threads so don't bother with the full FillGrid. Just find our row and column and update the cell's text.
            if (gridMain.getRows().get___idx(i).getTag() == null || !(gridMain.getRows().get___idx(i).getTag() instanceof CodeSystem) || !(StringSupport.equals(((CodeSystem)gridMain.getRows().get___idx(i).getTag()).CodeSystemName, codeSystem.CodeSystemName)))
            {
                continue;
            }
             
            String cellText = ((int)percentDone) + "%" + " -- " + status;
            if (done)
            {
                if (success)
                {
                    cellText = Lan.g("CodeSystemImporter","Import complete") + "!";
                }
                else
                {
                    cellText = Lan.g("CodeSystemImporter","Import failed") + "! -- " + status;
                } 
            }
             
            gridMain.getRows().get___idx(i).getCells()[3].Text = cellText;
        }
        gridMain.Invalidate();
    }

    /**
    * Worker thread class. 1 thread will be spawned for each code sytem being downloaded. All threads will run in parallel.
    */
    private static class UpdateCodeSystemThread   
    {
        /**
        * Number of bytes in a kilobyte.
        */
        private static final int KB_SIZE = 1024;
        /**
        * Number of kilobytes to download in each chunk.
        */
        private static final int CHUNK_SIZE = 10;
        /**
        * Static lis of threads. All managed internally. Must always be locked by _lock when accessed!!!
        */
        private static List<UpdateCodeSystemThread> _threads = new List<UpdateCodeSystemThread>();
        /**
        * All access of _threads member MUST BE enclosed with lock statment in order to prevent thread-lock and race conditions.
        */
        private static Object _lock = new Object();
        /**
        * The code system being updated.
        */
        private CodeSystem _codeSystem;
        /**
        * Download and import functions will check this flag occasionally to see if they should abort prematurely.
        */
        private boolean _quit = false;
        /**
        * Function signature required to send an update.
        */
        public static class __MultiUpdateCodeSystemArgs   implements OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs
        {
            public void invoke(CodeSystem codeSystem, String status, double percentDone, boolean done, boolean success) throws Exception {
                IList<OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs> copy = new IList<OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs>(), members = this.getInvocationList();
                synchronized (members)
                {
                    copy = new LinkedList<OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs>(members);
                }
                for (Object __dummyForeachVar0 : copy)
                {
                    OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs d = (OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs)__dummyForeachVar0;
                    d.invoke(codeSystem, status, percentDone, done, success);
                }
            }

            private System.Collections.Generic.IList<OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs> _invocationList = new ArrayList<OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs>();
            public static OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs combine(OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs a, OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs b) throws Exception {
                if (a == null)
                    return b;
                 
                if (b == null)
                    return a;
                 
                __MultiUpdateCodeSystemArgs ret = new __MultiUpdateCodeSystemArgs();
                ret._invocationList = a.getInvocationList();
                ret._invocationList.addAll(b.getInvocationList());
                return ret;
            }

            public static OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs remove(OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs a, OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs b) throws Exception {
                if (a == null || b == null)
                    return a;
                 
                System.Collections.Generic.IList<OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs> aInvList = a.getInvocationList();
                System.Collections.Generic.IList<OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
                if (aInvList == newInvList)
                {
                    return a;
                }
                else
                {
                    __MultiUpdateCodeSystemArgs ret = new __MultiUpdateCodeSystemArgs();
                    ret._invocationList = newInvList;
                    return ret;
                } 
            }

            public System.Collections.Generic.IList<OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs> getInvocationList() throws Exception {
                return _invocationList;
            }
        
        }

        public static interface UpdateCodeSystemArgs   
        {
            void invoke(CodeSystem codeSystem, String status, double percentDone, boolean done, boolean success) throws Exception ;

            System.Collections.Generic.IList<OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs> getInvocationList() throws Exception ;
        
        }

        /**
        * Required by ctor. Used to keep main thread aware of update progress.
        */
        private OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs _updateHandler;
        /**
        * Event will be fired when the final thread has finished and all threads have been cleared from the list.
        */
        public static EventHandler Finished = new EventHandler();
        /**
        * If this is a CPT import then the file must exist localally and the file location will be provided by the user. All other code system files are held behind the Customer Update web service and will be downloaded to a temp file location in order to be imported.
        */
        private String _localFilePath = new String();
        /**
        * Aborts the thread. Only called by StopAll.
        */
        private void quit() throws Exception {
            _quit = true;
        }

        /**
        * Indicates if there are still 1 or more active threads.
        */
        public static boolean getIsRunning() throws Exception {
            synchronized (_lock)
            {
                {
                    return _threads.Count >= 1;
                }
            }
        }

        /**
        * Private ctor. Will only be used internally by Add. If localFilePath is set here then it is assumed that the file exists locally and file download will be skipped before importing data from the file. This will only happen for the CPT code system.
        */
        private UpdateCodeSystemThread(String localFilePath, CodeSystem codeSystem, OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs onUpdateHandler) throws Exception {
            _localFilePath = localFilePath;
            _codeSystem = codeSystem;
            _updateHandler = __MultiUpdateCodeSystemArgs.combine(_updateHandler,onUpdateHandler);
        }

        /**
        * Provide a nice ledgible identifier.
        */
        public String toString() {
            try
            {
                return _codeSystem.CodeSystemName;
            }
            catch (RuntimeException __dummyCatchVar3)
            {
                throw __dummyCatchVar3;
            }
            catch (Exception __dummyCatchVar3)
            {
                throw new RuntimeException(__dummyCatchVar3);
            }
        
        }

        /**
        * Thread list manager needs this to remove threads. Required for List.Contains.
        */
        public boolean equals(Object obj) {
            try
            {
                return ((UpdateCodeSystemThread)obj)._codeSystem.CodeSystemNum == _codeSystem.CodeSystemNum;
            }
            catch (RuntimeException __dummyCatchVar4)
            {
                throw __dummyCatchVar4;
            }
            catch (Exception __dummyCatchVar4)
            {
                throw new RuntimeException(__dummyCatchVar4);
            }
        
        }

        /**
        * Add a thread to the queue. These threads will not be started until StartAll is called subsequent to adding all necessary threads. If localFilePath is set here then it is assumed that the file exists locally and file download will be skipped before importing data from the file. This will only happen for the CPT code system.
        */
        public static void add(String localFilePath, CodeSystem codeSystem, OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs onUpdateHandler) throws Exception {
            UpdateCodeSystemThread thread = new UpdateCodeSystemThread(localFilePath,codeSystem,onUpdateHandler);
            synchronized (_lock)
            {
                {
                    _threads.Add(thread);
                }
            }
        }

        /**
        * Add a thread to the queue. These threads will not be started until StartAll is called subsequent to adding all necessary threads. This version assures that code system file will be downloaded before import. Use for all code system except CPT.
        */
        public static void add(CodeSystem codeSystem, OpenDental.FormCodeSystemsImport.UpdateCodeSystemThread.UpdateCodeSystemArgs onUpdateHandler) throws Exception {
            add("",codeSystem,onUpdateHandler);
        }

        /**
        * Use this to start the threads once all threads have been added using Add.
        */
        public static boolean startAll() throws Exception {
            boolean startedAtLeastOne = false;
            synchronized (_lock)
            {
                {
                    for (Object __dummyForeachVar1 : _threads)
                    {
                        UpdateCodeSystemThread thread = (UpdateCodeSystemThread)__dummyForeachVar1;
                        Thread th = new Thread(new ThreadStart(thread.Run));
                        th.Name = thread.toString();
                        th.Start();
                        startedAtLeastOne = true;
                    }
                }
            }
            return startedAtLeastOne;
        }

        /**
        * Sets the Quit flag for all threads. Use this if early abort is desired.
        */
        public static void stopAll() throws Exception {
            synchronized (_lock)
            {
                {
                    for (Object __dummyForeachVar2 : _threads)
                    {
                        UpdateCodeSystemThread thread = (UpdateCodeSystemThread)__dummyForeachVar2;
                        thread.quit();
                    }
                    _threads.Clear();
                }
            }
        }

        /**
        * Called internally each time time a thread has completed. Will trigger the Finished event if this is the last thread to complete.
        */
        private void done(String status, boolean success) throws Exception {
            _updateHandler(_codeSystem, status, 100, true, success);
            boolean finished = false;
            synchronized (_lock)
            {
                {
                    if (_threads.Contains(this))
                    {
                        _threads.Remove(this);
                    }
                     
                    finished = _threads.Count <= 0;
                }
            }
            if (finished && Finished != null)
            {
                Finished("UpdateCodeSystemThread", new EventArgs());
            }
             
        }

        /**
        * Update the current status of this import thread. Thread owner is required to handle this as the delegat is required in the ctor.
        */
        private void update(String status, int numDone, int numTotal) throws Exception {
            double percentDone = 0;
            //Guard against illegal division.
            if (numTotal > 0)
            {
                percentDone = 100 * (numDone / (double)numTotal);
            }
             
            _updateHandler.invoke(_codeSystem,status,percentDone,false,true);
        }

        /**
        * Helper used internally.
        */
        private void importProgress(int numDone, int numTotal) throws Exception {
            update(Lan.g("CodeSystemImporter","Importing"),numDone,numTotal);
        }

        /**
        * Helper used internally.
        */
        private void downloadProgress(int numDone, int numTotal) throws Exception {
            update(Lan.g("CodeSystemImporter","Downloading"),numDone,numTotal);
        }

        /**
        * The thread function.
        */
        private void run() throws Exception {
            try
            {
                String failText = "";
                RefSupport<String> refVar___1 = new RefSupport<String>(failText);
                boolean boolVar___0 = !requestCodeSystemDownloadHelper(refVar___1);
                failText = refVar___1.getValue();
                if (boolVar___0)
                {
                    throw new Exception(failText);
                }
                 
                //set current version=available version
                CodeSystems.updateCurrentVersion(_codeSystem);
                //All good!
                done(Lan.g("CodeSystemImporter","Import Complete"),true);
            }
            catch (Exception ex)
            {
                //Something failed!
                done(Lan.g("CodeSystemImporter","Error") + ": " + ex.Message,false);
            }
        
        }

        /**
        * Will request, download, and import codeSystem from webservice. Returns false if unsuccessful.
        */
        private boolean requestCodeSystemDownloadHelper(RefSupport<String> failText) throws Exception {
            try
            {
                //If local file was not provided then try to download it from Customer Update web service.
                //Local file will only be provided for CPT code system.
                if (String.IsNullOrEmpty(_localFilePath))
                {
                    String result = sendAndReceiveDownloadXml(_codeSystem.CodeSystemName);
                    XmlDocument doc = new XmlDocument();
                    doc.LoadXml(result);
                    XmlNode node = doc.SelectSingleNode("//Error");
                    if (node != null)
                    {
                        throw new Exception(node.InnerText);
                    }
                     
                    node = doc.SelectSingleNode("//CodeSystemURL");
                    if (node == null)
                    {
                        throw new Exception(Lan.g("CodeSystemImporter","Code System URL is empty for ") + ": " + _codeSystem.CodeSystemName);
                    }
                     
                    //Node's inner text contains the URL
                    _localFilePath = DownloadFileHelper(node.InnerText);
                }
                 
                if (!File.Exists(_localFilePath))
                {
                    throw new Exception(Lan.g("CodeSystemImporter","Local file not found ") + ": " + _localFilePath);
                }
                 
                System.String __dummyScrutVar1 = _codeSystem.CodeSystemName;
                if (__dummyScrutVar1.equals("CDCREC"))
                {
                    RefSupport refVar___2 = new RefSupport(_quit);
                    CodeSystems.importCdcrec(_localFilePath,new OpenDentBusiness.CodeSystems.ProgressArgs() 
                      { 
                        public System.Void invoke(System.Int32 numTotal, System.Int32 numDone) throws Exception {
                            importProgress(numTotal, numDone);
                        }

                        public List<OpenDentBusiness.CodeSystems.ProgressArgs> getInvocationList() throws Exception {
                            List<OpenDentBusiness.CodeSystems.ProgressArgs> ret = new ArrayList<OpenDentBusiness.CodeSystems.ProgressArgs>();
                            ret.add(this);
                            return ret;
                        }
                    
                      },refVar___2);
                    _quit = refVar___2.getValue();
                }
                else if (__dummyScrutVar1.equals("CVX"))
                {
                    RefSupport refVar___3 = new RefSupport(_quit);
                    CodeSystems.importCvx(_localFilePath,new OpenDentBusiness.CodeSystems.ProgressArgs() 
                      { 
                        public System.Void invoke(System.Int32 numTotal, System.Int32 numDone) throws Exception {
                            importProgress(numTotal, numDone);
                        }

                        public List<OpenDentBusiness.CodeSystems.ProgressArgs> getInvocationList() throws Exception {
                            List<OpenDentBusiness.CodeSystems.ProgressArgs> ret = new ArrayList<OpenDentBusiness.CodeSystems.ProgressArgs>();
                            ret.add(this);
                            return ret;
                        }
                    
                      },refVar___3);
                    _quit = refVar___3.getValue();
                }
                else if (__dummyScrutVar1.equals("HCPCS"))
                {
                    RefSupport refVar___4 = new RefSupport(_quit);
                    CodeSystems.importHcpcs(_localFilePath,new OpenDentBusiness.CodeSystems.ProgressArgs() 
                      { 
                        public System.Void invoke(System.Int32 numTotal, System.Int32 numDone) throws Exception {
                            importProgress(numTotal, numDone);
                        }

                        public List<OpenDentBusiness.CodeSystems.ProgressArgs> getInvocationList() throws Exception {
                            List<OpenDentBusiness.CodeSystems.ProgressArgs> ret = new ArrayList<OpenDentBusiness.CodeSystems.ProgressArgs>();
                            ret.add(this);
                            return ret;
                        }
                    
                      },refVar___4);
                    _quit = refVar___4.getValue();
                }
                else if (__dummyScrutVar1.equals("ICD10CM"))
                {
                    RefSupport refVar___5 = new RefSupport(_quit);
                    CodeSystems.importIcd10(_localFilePath,new OpenDentBusiness.CodeSystems.ProgressArgs() 
                      { 
                        public System.Void invoke(System.Int32 numTotal, System.Int32 numDone) throws Exception {
                            importProgress(numTotal, numDone);
                        }

                        public List<OpenDentBusiness.CodeSystems.ProgressArgs> getInvocationList() throws Exception {
                            List<OpenDentBusiness.CodeSystems.ProgressArgs> ret = new ArrayList<OpenDentBusiness.CodeSystems.ProgressArgs>();
                            ret.add(this);
                            return ret;
                        }
                    
                      },refVar___5);
                    _quit = refVar___5.getValue();
                }
                else if (__dummyScrutVar1.equals("ICD9CM"))
                {
                    RefSupport refVar___6 = new RefSupport(_quit);
                    CodeSystems.importIcd9(_localFilePath,new OpenDentBusiness.CodeSystems.ProgressArgs() 
                      { 
                        public System.Void invoke(System.Int32 numTotal, System.Int32 numDone) throws Exception {
                            importProgress(numTotal, numDone);
                        }

                        public List<OpenDentBusiness.CodeSystems.ProgressArgs> getInvocationList() throws Exception {
                            List<OpenDentBusiness.CodeSystems.ProgressArgs> ret = new ArrayList<OpenDentBusiness.CodeSystems.ProgressArgs>();
                            ret.add(this);
                            return ret;
                        }
                    
                      },refVar___6);
                    _quit = refVar___6.getValue();
                }
                else if (__dummyScrutVar1.equals("LOINC"))
                {
                    RefSupport refVar___7 = new RefSupport(_quit);
                    CodeSystems.importLoinc(_localFilePath,new OpenDentBusiness.CodeSystems.ProgressArgs() 
                      { 
                        public System.Void invoke(System.Int32 numTotal, System.Int32 numDone) throws Exception {
                            importProgress(numTotal, numDone);
                        }

                        public List<OpenDentBusiness.CodeSystems.ProgressArgs> getInvocationList() throws Exception {
                            List<OpenDentBusiness.CodeSystems.ProgressArgs> ret = new ArrayList<OpenDentBusiness.CodeSystems.ProgressArgs>();
                            ret.add(this);
                            return ret;
                        }
                    
                      },refVar___7);
                    _quit = refVar___7.getValue();
                }
                else if (__dummyScrutVar1.equals("RXNORM"))
                {
                    RefSupport refVar___8 = new RefSupport(_quit);
                    CodeSystems.importRxNorm(_localFilePath,new OpenDentBusiness.CodeSystems.ProgressArgs() 
                      { 
                        public System.Void invoke(System.Int32 numTotal, System.Int32 numDone) throws Exception {
                            importProgress(numTotal, numDone);
                        }

                        public List<OpenDentBusiness.CodeSystems.ProgressArgs> getInvocationList() throws Exception {
                            List<OpenDentBusiness.CodeSystems.ProgressArgs> ret = new ArrayList<OpenDentBusiness.CodeSystems.ProgressArgs>();
                            ret.add(this);
                            return ret;
                        }
                    
                      },refVar___8);
                    _quit = refVar___8.getValue();
                }
                else if (__dummyScrutVar1.equals("SNOMEDCT"))
                {
                    RefSupport refVar___9 = new RefSupport(_quit);
                    CodeSystems.importSnomed(_localFilePath,new OpenDentBusiness.CodeSystems.ProgressArgs() 
                      { 
                        public System.Void invoke(System.Int32 numTotal, System.Int32 numDone) throws Exception {
                            importProgress(numTotal, numDone);
                        }

                        public List<OpenDentBusiness.CodeSystems.ProgressArgs> getInvocationList() throws Exception {
                            List<OpenDentBusiness.CodeSystems.ProgressArgs> ret = new ArrayList<OpenDentBusiness.CodeSystems.ProgressArgs>();
                            ret.add(this);
                            return ret;
                        }
                    
                      },refVar___9);
                    _quit = refVar___9.getValue();
                }
                else if (__dummyScrutVar1.equals("SOP"))
                {
                    RefSupport refVar___10 = new RefSupport(_quit);
                    CodeSystems.importSop(_localFilePath,new OpenDentBusiness.CodeSystems.ProgressArgs() 
                      { 
                        public System.Void invoke(System.Int32 numTotal, System.Int32 numDone) throws Exception {
                            importProgress(numTotal, numDone);
                        }

                        public List<OpenDentBusiness.CodeSystems.ProgressArgs> getInvocationList() throws Exception {
                            List<OpenDentBusiness.CodeSystems.ProgressArgs> ret = new ArrayList<OpenDentBusiness.CodeSystems.ProgressArgs>();
                            ret.add(this);
                            return ret;
                        }
                    
                      },refVar___10);
                    _quit = refVar___10.getValue();
                }
                else if (__dummyScrutVar1.equals("UCUM"))
                {
                    RefSupport refVar___11 = new RefSupport(_quit);
                    CodeSystems.importUcum(_localFilePath,new OpenDentBusiness.CodeSystems.ProgressArgs() 
                      { 
                        public System.Void invoke(System.Int32 numTotal, System.Int32 numDone) throws Exception {
                            importProgress(numTotal, numDone);
                        }

                        public List<OpenDentBusiness.CodeSystems.ProgressArgs> getInvocationList() throws Exception {
                            List<OpenDentBusiness.CodeSystems.ProgressArgs> ret = new ArrayList<OpenDentBusiness.CodeSystems.ProgressArgs>();
                            ret.add(this);
                            return ret;
                        }
                    
                      },refVar___11);
                    _quit = refVar___11.getValue();
                }
                else if (__dummyScrutVar1.equals("CPT"))
                {
                    RefSupport refVar___12 = new RefSupport(_quit);
                    CodeSystems.importCpt(_localFilePath,new OpenDentBusiness.CodeSystems.ProgressArgs() 
                      { 
                        public System.Void invoke(System.Int32 numTotal, System.Int32 numDone) throws Exception {
                            importProgress(numTotal, numDone);
                        }

                        public List<OpenDentBusiness.CodeSystems.ProgressArgs> getInvocationList() throws Exception {
                            List<OpenDentBusiness.CodeSystems.ProgressArgs> ret = new ArrayList<OpenDentBusiness.CodeSystems.ProgressArgs>();
                            ret.add(this);
                            return ret;
                        }
                    
                      },refVar___12);
                    _quit = refVar___12.getValue();
                }
                else
                {
                    throw new Exception(Lan.g("CodeSystemImporter","Unsupported Code System") + ": " + _codeSystem.CodeSystemName);
                }           
                //import not supported
                //import not supported
                //new code system perhaps?
                //Import succeded so delete the import file where necessary.
                deleteImportFileIfNecessary();
                return true;
            }
            catch (Exception ex)
            {
                //We got here so everything succeeded.
                failText.setValue(ex.Message);
            }

            return false;
        }

        //We got here so something failed.
        /**
        * Delete the import file which was created locally. This file was either downloaded or extracted from a zip archive. Either way it is temporary and can be deleted.
        */
        private void deleteImportFileIfNecessary() throws Exception {
            //Don't bother if the file isn't there.
            if (!File.Exists(_localFilePath))
            {
                return ;
            }
             
            //We got this far so assume the file is safe to delete.
            File.Delete(_localFilePath);
        }

        /**
        * Returns temp file name used to download file.  Can throw exception.
        */
        private String downloadFileHelper(String codeSystemURL) throws Exception {
            String zipFileDestination = Path.GetTempFileName();
            //Cleanup existing.
            File.Delete(zipFileDestination);
            try
            {
                //Perform the download
                downloadFileWorker(codeSystemURL,zipFileDestination);
                Thread.Sleep(100);
                //allow file to be released for use by the unzipper.
                //Unzip the compressed file-----------------------------------------------------------------------------------------------------
                MemoryStream ms = new MemoryStream();
                try
                {
                    {
                        ZipFile unzipped = ZipFile.Read(zipFileDestination);
                        try
                        {
                            {
                                ZipEntry ze = unzipped[0];
                                ze.Extract(Path.GetTempPath(), ExtractExistingFileAction.OverwriteSilently);
                                return Path.GetTempPath() + unzipped[0].FileName;
                            }
                        }
                        finally
                        {
                            if (unzipped != null)
                                Disposable.mkDisposable(unzipped).dispose();
                             
                        }
                    }
                }
                finally
                {
                    if (ms != null)
                        Disposable.mkDisposable(ms).dispose();
                     
                }
            }
            finally
            {
                //We are done with the zip file.
                File.Delete(zipFileDestination);
            }
        }

        /**
        * Download given URI to given local path. Can throw exception.
        */
        private void downloadFileWorker(String codeSystemURL, String destinationPath) throws Exception {
            byte[] buffer = new byte[]();
            int chunkIndex = 0;
            WebRequest wr = WebRequest.Create(codeSystemURL);
            int fileSize = 0;
            WebResponse webResp = wr.GetResponse();
            try
            {
                {
                    //Quickly get the size of the entire package to be downloaded.
                    fileSize = (int)webResp.ContentLength;
                }
            }
            finally
            {
                if (webResp != null)
                    Disposable.mkDisposable(webResp).dispose();
                 
            }
            WebClient myWebClient = new WebClient();
            try
            {
                {
                    Stream readStream = myWebClient.OpenRead(codeSystemURL);
                    try
                    {
                        {
                            BinaryReader br = new BinaryReader(readStream);
                            try
                            {
                                {
                                    FileStream writeStream = new FileStream(destinationPath, FileMode.Create);
                                    try
                                    {
                                        {
                                            BinaryWriter bw = new BinaryWriter(writeStream);
                                            try
                                            {
                                                {
                                                    while (true)
                                                    {
                                                        if (_quit)
                                                        {
                                                            throw new Exception(Lan.g("CodeSystemImporter","Download aborted"));
                                                        }
                                                         
                                                        //Update the progress.
                                                        downloadProgress(CHUNK_SIZE * KB_SIZE * chunkIndex,fileSize);
                                                        //Download another chunk.
                                                        buffer = br.ReadBytes(CHUNK_SIZE * KB_SIZE);
                                                        if (buffer.Length == 0)
                                                        {
                                                            break;
                                                        }
                                                         
                                                        //Nothing left to download so we are done.
                                                        //Write out to the file.
                                                        bw.Write(buffer);
                                                        chunkIndex++;
                                                    }
                                                }
                                            }
                                            finally
                                            {
                                                if (bw != null)
                                                    Disposable.mkDisposable(bw).dispose();
                                                 
                                            }
                                        }
                                    }
                                    finally
                                    {
                                        if (writeStream != null)
                                            Disposable.mkDisposable(writeStream).dispose();
                                         
                                    }
                                }
                            }
                            finally
                            {
                                if (br != null)
                                    Disposable.mkDisposable(br).dispose();
                                 
                            }
                        }
                    }
                    finally
                    {
                        if (readStream != null)
                            Disposable.mkDisposable(readStream).dispose();
                         
                    }
                }
            }
            finally
            {
                if (myWebClient != null)
                    Disposable.mkDisposable(myWebClient).dispose();
                 
            }
        }

        /**
        * Can throw exception.
        */
        private static String sendAndReceiveDownloadXml(String codeSystemName) throws Exception {
            //prepare the xml document to send--------------------------------------------------------------------------------------
            XmlWriterSettings settings = new XmlWriterSettings();
            settings.Indent = true;
            settings.IndentChars = ("    ");
            StringBuilder strbuild = new StringBuilder();
            XmlWriter writer = XmlWriter.Create(strbuild, settings);
            try
            {
                {
                    //TODO: include more user information
                    writer.WriteStartElement("UpdateRequest");
                    writer.WriteStartElement("RegistrationKey");
                    writer.WriteString(PrefC.getString(PrefName.RegistrationKey));
                    writer.WriteEndElement();
                    writer.WriteStartElement("PracticeTitle");
                    writer.WriteString(PrefC.getString(PrefName.PracticeTitle));
                    writer.WriteEndElement();
                    writer.WriteStartElement("PracticeAddress");
                    writer.WriteString(PrefC.getString(PrefName.PracticeAddress));
                    writer.WriteEndElement();
                    writer.WriteStartElement("PracticePhone");
                    writer.WriteString(PrefC.getString(PrefName.PracticePhone));
                    writer.WriteEndElement();
                    writer.WriteStartElement("ProgramVersion");
                    writer.WriteString(PrefC.getString(PrefName.ProgramVersion));
                    writer.WriteEndElement();
                    writer.WriteStartElement("CodeSystemRequested");
                    writer.WriteString(codeSystemName);
                    writer.WriteEndElement();
                    writer.WriteEndElement();
                }
            }
            finally
            {
                if (writer != null)
                    Disposable.mkDisposable(writer).dispose();
                 
            }
            OpenDental.customerUpdates.Service1 updateService = new OpenDental.customerUpdates.Service1();
            updateService.setUrl(PrefC.getString(PrefName.UpdateServerAddress));
            if (!StringSupport.equals(PrefC.getString(PrefName.UpdateWebProxyAddress), ""))
            {
                IWebProxy proxy = new WebProxy(PrefC.getString(PrefName.UpdateWebProxyAddress));
                ICredentials cred = new NetworkCredential(PrefC.getString(PrefName.UpdateWebProxyUserName), PrefC.getString(PrefName.UpdateWebProxyPassword));
                proxy.Credentials = cred;
                updateService.Proxy = proxy;
            }
             
            return updateService.RequestCodeSystemDownload(strbuild.ToString());
        }
    
    }

    //may throw error
    private void butClose_Click(Object sender, EventArgs e) throws Exception {
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
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butCheckUpdates = new OpenDental.UI.Button();
        this.butDownload = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setEditableAcceptsCR(true);
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 42);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMain.Size = new System.Drawing.Size(857, 317);
        this.gridMain.TabIndex = 27;
        this.gridMain.setTitle("Code Systems Available");
        this.gridMain.setTranslationName("");
        //
        // butCheckUpdates
        //
        this.butCheckUpdates.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCheckUpdates.setAutosize(true);
        this.butCheckUpdates.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCheckUpdates.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCheckUpdates.setCornerRadius(4F);
        this.butCheckUpdates.Location = new System.Drawing.Point(12, 12);
        this.butCheckUpdates.Name = "butCheckUpdates";
        this.butCheckUpdates.Size = new System.Drawing.Size(106, 24);
        this.butCheckUpdates.TabIndex = 28;
        this.butCheckUpdates.Text = "Check for Updates";
        this.butCheckUpdates.Click += new System.EventHandler(this.butCheckUpdates_Click);
        //
        // butDownload
        //
        this.butDownload.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDownload.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butDownload.setAutosize(true);
        this.butDownload.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDownload.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDownload.setCornerRadius(4F);
        this.butDownload.Enabled = false;
        this.butDownload.Location = new System.Drawing.Point(550, 370);
        this.butDownload.Name = "butDownload";
        this.butDownload.Size = new System.Drawing.Size(106, 24);
        this.butDownload.TabIndex = 3;
        this.butDownload.Text = "Download Updates";
        this.butDownload.Click += new System.EventHandler(this.butDownload_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(794, 370);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "Close";
        this.butCancel.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormCodeSystemsImport
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(881, 406);
        this.Controls.Add(this.butCheckUpdates);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butDownload);
        this.Controls.Add(this.butCancel);
        this.Name = "FormCodeSystemsImport";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Import Code Systems";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormCodeSystemsImport_FormClosing);
        this.Load += new System.EventHandler(this.FormCodeSystemsImport_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butDownload;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butCheckUpdates;
}


