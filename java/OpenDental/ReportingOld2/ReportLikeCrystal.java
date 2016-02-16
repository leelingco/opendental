//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:24 PM
//

package OpenDental.ReportingOld2;

import CS2JNet.System.StringSupport;
import OpenDental.ReportingOld2.AreaSectionKind;
import OpenDental.ReportingOld2.FieldValueType;
import OpenDental.ReportingOld2.FormParameterInput;
import OpenDental.ReportingOld2.ParameterField;
import OpenDental.ReportingOld2.ParameterFieldCollection;
import OpenDental.ReportingOld2.ReportObject;
import OpenDental.ReportingOld2.ReportObjectCollection;
import OpenDental.ReportingOld2.ReportObjectKind;
import OpenDental.ReportingOld2.Section;
import OpenDental.ReportingOld2.SectionCollection;
import OpenDental.ReportingOld2.SpecialFieldType;
import OpenDental.ReportingOld2.SummaryOperation;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.EnumType;
import OpenDentBusiness.ReportFKType;
import OpenDentBusiness.Reports;

/**
* This class is loosely modeled after CrystalReports.ReportDocument, but with less inheritence and heirarchy.
*/
public class ReportLikeCrystal   
{
    private ArrayList dataFields = new ArrayList();
    private SectionCollection sections;
    private ReportObjectCollection reportObjects;
    private ParameterFieldCollection parameterFields;
    //private Margins reportMargins; //Never set anywhere, so it is not needed!
    private boolean isLandscape = new boolean();
    private String query = new String();
    private DataTable reportTable = new DataTable();
    private String reportName = new String();
    private String description = new String();
    private String authorID = new String();
    private int letterOrder = new int();
    /**
    * This is simply used to measure strings for alignment purposes.
    */
    private Graphics grfx = new Graphics();
    /**
    * Collection of strings representing available datatable field names. For now, only one table is allowed, so each string will represent a column.
    */
    public ArrayList getDataFields() throws Exception {
        return dataFields;
    }

    public void setDataFields(ArrayList value) throws Exception {
        dataFields = value;
    }

    /**
    * Collection of Sections.
    */
    public SectionCollection getSections() throws Exception {
        return sections;
    }

    public void setSections(SectionCollection value) throws Exception {
        sections = value;
    }

    /**
    * A collection of ReportObjects
    */
    public ReportObjectCollection getReportObjects() throws Exception {
        return reportObjects;
    }

    public void setReportObjects(ReportObjectCollection value) throws Exception {
        reportObjects = value;
    }

    /**
    * Collection of ParameterFields that are available for the query.
    */
    public ParameterFieldCollection getParameterFields() throws Exception {
        return parameterFields;
    }

    public void setParameterFields(ParameterFieldCollection value) throws Exception {
        parameterFields = value;
    }

    /**
    * margins will be null unless set by user. When printing, if margins are null, the defaults will depend on the page orientation.
    */
    public Margins getReportMargins() throws Exception {
        return null;
    }

    //return reportMargins; //reportMargins is always null!
    /**
    * 
    */
    public boolean getIsLandscape() throws Exception {
        return isLandscape;
    }

    public void setIsLandscape(boolean value) throws Exception {
        isLandscape = value;
    }

    /**
    * The query will get altered before it is actually used to retrieve. Any parameters will be replaced with user entered data without saving those changes.
    */
    public String getQuery() throws Exception {
        return query;
    }

    public void setQuery(String value) throws Exception {
        query = value;
    }

    /**
    * The datatable that is returned from the database.
    */
    public DataTable getReportTable() throws Exception {
        return reportTable;
    }

    public void setReportTable(DataTable value) throws Exception {
        reportTable = value;
    }

    /**
    * The name to display in the menu.
    */
    public String getReportName() throws Exception {
        return reportName;
    }

    public void setReportName(String value) throws Exception {
        reportName = value;
    }

    /**
    * Gives the user a description and some guidelines about what they can expect from this report.
    */
    public String getDescription() throws Exception {
        return description;
    }

    public void setDescription(String value) throws Exception {
        description = value;
    }

    /**
    * For instance OD12 or JoeDeveloper9.  If you are a developer releasing reports, then this should be your name or company followed by a unique number.  This will later make it easier to maintain your reports for your customers.  All reports that we release will be of the form OD##.  Reports that the user creates will have this field blank.
    */
    public String getAuthorID() throws Exception {
        return authorID;
    }

    public void setAuthorID(String value) throws Exception {
        authorID = value;
    }

    /**
    * The 1-based order to show in the Letter menu, or 0 to not show in that menu.
    */
    public int getLetterOrder() throws Exception {
        return letterOrder;
    }

    public void setLetterOrder(int value) throws Exception {
        letterOrder = value;
    }

    /**
    * When a new Report is created, the only section that is added is the details. This makes the logic a little more complicated, but it will minimize calls to the database for unused sections. This also makes the act of adding groups more natural.
    */
    public ReportLikeCrystal() throws Exception {
        //ReportMargins=new Margins(50,50,30,30);//this should work for almost all printers.
        sections = new SectionCollection();
        //sections.Add(new Section(AreaSectionKind.ReportHeader,"Report Header",0));
        //sections.Add(new Section(AreaSectionKind.PageHeader,"Page Header",0));
        //sections.Add("Group Header");
        sections.add(new Section(AreaSectionKind.Detail,0));
        //sections.Add("Group Footer");
        //sections.Add(new Section(AreaSectionKind.PageFooter,"Page Footer",0));
        //sections.Add(new Section(AreaSectionKind.ReportFooter,"Report Footer",0));
        reportObjects = new ReportObjectCollection();
        dataFields = new ArrayList();
        parameterFields = new ParameterFieldCollection();
        grfx = Graphics.FromImage(new Bitmap(1, 1));
    }

    //I'm still trying to find a better way to do this
    /**
    * Adds a ReportObject large, centered, and bold, to the top of the Report Header Section.  Should only be done once, and done before any subTitles.
    *  @param title The text of the title.
    */
    public void addTitle(String title) throws Exception {
        //FormReport FormR=new FormReport();
        //this is just to get a graphics object. There must be a better way.
        //Graphics grfx=FormR.CreateGraphics();
        Font font = new Font("Tahoma", 17, FontStyle.Bold);
        Size size = new Size((int)(grfx.MeasureString(title, font).Width / grfx.DpiX * 100 + 2), (int)(grfx.MeasureString(title, font).Height / grfx.DpiY * 100 + 2));
        int xPos = new int();
        if (isLandscape)
            xPos = 1100 / 2;
        else
            xPos = 850 / 2; 
        //if(reportMargins==null){	//Crashes MONO, but reportMargins would always null since it is never set,
        //so this check is not needed.
        if (getIsLandscape())
            xPos -= 50;
        else
            xPos -= 30; 
        //}
        //else{
        //	xPos-=reportMargins.Left;//to make it look centered
        //}
        xPos -= (int)(size.Width / 2);
        reportObjects.add(new ReportObject("Report Header", new Point(xPos, 0), size, title, font, ContentAlignment.MiddleCenter));
        if (sections.get___idx("Report Header") == null)
        {
            sections.add(new Section(AreaSectionKind.ReportHeader,0));
        }
         
        //this it the only place a white buffer is added to a header.
        sections.get___idx("Report Header").setHeight((int)size.Height + 20);
    }

    //grfx.Dispose();
    //FormR.Dispose();
    /**
    * Adds a ReportObject, centered and bold, at the bottom of the Report Header Section.  Should only be done after AddTitle.  You can add as many subtitles as you want.
    *  @param subTitle The text of the subtitle.
    */
    public void addSubTitle(String subTitle) throws Exception {
        //FormReport FormR=new FormReport();
        //Graphics grfx=FormR.CreateGraphics();
        Font font = new Font("Tahoma", 10, FontStyle.Bold);
        Size size = new Size((int)(grfx.MeasureString(subTitle, font).Width / grfx.DpiX * 100 + 2), (int)(grfx.MeasureString(subTitle, font).Height / grfx.DpiY * 100 + 2));
        int xPos = new int();
        if (isLandscape)
            xPos = 1100 / 2;
        else
            xPos = 850 / 2; 
        //if(reportMargins==null){	//Crashes MONO, but reportMargins would always null since it is never set,
        //so this check is not needed.
        if (isLandscape)
            xPos -= 50;
        else
            xPos -= 30; 
        //}
        //else{
        //	xPos-=reportMargins.Left;//to make it look centered
        //}
        xPos -= (int)(size.Width / 2);
        if (sections.get___idx("Report Header") == null)
        {
            sections.add(new Section(AreaSectionKind.ReportHeader,0));
        }
         
        //find the yPos+Height of the last reportObject in the Report Header section
        int yPos = 0;
        for (Object __dummyForeachVar0 : reportObjects)
        {
            ReportObject reportObject = (ReportObject)__dummyForeachVar0;
            if (!StringSupport.equals(reportObject.getSectionName(), "Report Header"))
                continue;
             
            if (reportObject.getLocation().Y + reportObject.getSize().Height > yPos)
            {
                yPos = reportObject.getLocation().Y + reportObject.getSize().Height;
            }
             
        }
        reportObjects.add(new ReportObject("Report Header", new Point(xPos, yPos + 5), size, subTitle, font, ContentAlignment.MiddleCenter));
        sections.get___idx("Report Header").setHeight(sections.get___idx("Report Header").getHeight() + ((int)size.Height + 5));
    }

    //grfx.Dispose();
    //FormR.Dispose();
    /**
    * Adds all the objects necessary for a typical column, including the textObject for column header and the fieldObject for the data.  Does not add lines or shading. If the column is type Double, then the alignment is set right and a total field is added. Also, default formatstrings are set for dates and doubles.
    *  @param dataField The name of the column title as well as the dataField to add.
    *  @param width 
    *  @param valueType
    */
    public void addColumn(String dataField, int width, FieldValueType valueType) throws Exception {
        dataFields.Add(dataField);
        //FormReport FormR=new FormReport();
        //Graphics grfx=FormR.CreateGraphics();
        Font font = new Font();
        Size size = new Size();
        ContentAlignment textAlign = new ContentAlignment();
        if (valueType == FieldValueType.Number)
        {
            textAlign = ContentAlignment.MiddleRight;
        }
        else
        {
            textAlign = ContentAlignment.MiddleLeft;
        } 
        String formatString = "";
        if (valueType == FieldValueType.Number)
        {
            formatString = "n";
        }
         
        if (valueType == FieldValueType.Date)
        {
            formatString = "d";
        }
         
        if (sections.get___idx("Page Header") == null)
        {
            sections.add(new Section(AreaSectionKind.PageHeader,0));
        }
         
        //add textobject for column header
        font = new Font("Tahoma", 8, FontStyle.Bold);
        size = new Size((int)(grfx.MeasureString(dataField, font).Width / grfx.DpiX * 100 + 2), (int)(grfx.MeasureString(dataField, font).Height / grfx.DpiY * 100 + 2));
        if (sections.get___idx("Page Header").getHeight() == 0)
        {
            sections.get___idx("Page Header").setHeight(size.Height);
        }
         
        int xPos = 0;
        for (Object __dummyForeachVar1 : reportObjects)
        {
            //find next available xPos
            ReportObject reportObject = (ReportObject)__dummyForeachVar1;
            if (!StringSupport.equals(reportObject.getSectionName(), "Page Header"))
                continue;
             
            if (reportObject.getLocation().X + reportObject.getSize().Width > xPos)
            {
                xPos = reportObject.getLocation().X + reportObject.getSize().Width;
            }
             
        }
        getReportObjects().add(new ReportObject("Page Header",new Point(xPos, 0),new Size(width, size.Height),dataField,font,textAlign));
        //add fieldObject for rows in details section
        font = new Font("Tahoma", 9);
        size = new Size((int)(grfx.MeasureString(dataField, font).Width / grfx.DpiX * 100 + 2), (int)(grfx.MeasureString(dataField, font).Height / grfx.DpiY * 100 + 2));
        if (sections.get___idx("Detail").getHeight() == 0)
        {
            sections.get___idx("Detail").setHeight(size.Height);
        }
         
        //,new FieldDef(dataField,valueType)
        reportObjects.add(new ReportObject("Detail",new Point(xPos, 0),new Size(width, size.Height),dataField,valueType,font,textAlign,formatString));
        //add fieldObject for total in ReportFooter
        if (valueType == FieldValueType.Number)
        {
            font = new Font("Tahoma", 9, FontStyle.Bold);
            //use same size as already set for otherFieldObjects above
            if (sections.get___idx("Report Footer") == null)
            {
                sections.add(new Section(AreaSectionKind.ReportFooter,0));
            }
             
            if (sections.get___idx("Report Footer").getHeight() == 0)
            {
                sections.get___idx("Report Footer").setHeight(size.Height);
            }
             
            //,new FieldDef("Sum"+dataField,SummaryOperation.Sum
            //,GetLastRO(ReportObjectKind.FieldObject).DataSource)
            reportObjects.add(new ReportObject("Report Footer",new Point(xPos, 0),new Size(width, size.Height),SummaryOperation.Sum,dataField,font,textAlign,formatString));
        }
         
        return ;
    }

    //tidy up
    //grfx.Dispose();
    //FormR.Dispose();
    /**
    * Gets the last reportObect of a particular kind. Used immediately after entering an Object to alter its properties.
    *  @param objectKind 
    *  @return
    */
    public ReportObject getLastRO(ReportObjectKind objectKind) throws Exception {
        for (int i = reportObjects.Count - 1;i >= 0;i--)
        {
            //ReportObject ro=null;
            //search from the end backwards
            if (reportObjects.get___idx(i).getObjectKind() == objectKind)
            {
                return getReportObjects().get___idx(i);
            }
             
        }
        MessageBox.Show("end of loop");
        return null;
    }

    /**
    * Put a pagenumber object on lower left of page footer section.
    */
    public void addPageNum() throws Exception {
        //FormReport FormR=new FormReport();
        //Graphics grfx=FormR.CreateGraphics();
        //add page number
        Font font = new Font("Tahoma", 9);
        Size size = new Size(150, (int)(grfx.MeasureString("anytext", font).Height / grfx.DpiY * 100 + 2));
        if (sections.get___idx("Page Footer") == null)
        {
            sections.add(new Section(AreaSectionKind.PageFooter,0));
        }
         
        //Section section=Sections.GetOfKind(AreaSectionKind.PageFooter);
        if (sections.get___idx("Page Footer").getHeight() == 0)
        {
            sections.get___idx("Page Footer").setHeight(size.Height);
        }
         
        //,new FieldDef("PageNum",FieldValueType.String
        //,SpecialVarType.PageNumber)
        reportObjects.add(new ReportObject("Page Footer", new Point(0, 0), size, FieldValueType.String, SpecialFieldType.PageNumber, font, ContentAlignment.MiddleLeft, ""));
    }

    //grfx.Dispose();
    //FormR.Dispose();
    /*public void AddParameter(string name,ParameterValueKind valueKind){
    			ParameterFields.Add(new ParameterFieldDefinition(name,valueKind));
    		}*/
    /**
    * Adds a parameterField which will be used in the query to represent user-entered data.
    *  @param myName The unique formula name of the parameter.
    *  @param myValueType The data type that this parameter stores.
    *  @param myDefaultValue The default value of the parameter
    *  @param myPromptingText The text to prompt the user with.
    *  @param mySnippet The SQL snippet that this parameter represents.
    */
    public void addParameter(String myName, FieldValueType myValueType, Object myDefaultValue, String myPromptingText, String mySnippet) throws Exception {
        parameterFields.add(new ParameterField(myName,myValueType,myDefaultValue,myPromptingText,mySnippet));
    }

    /**
    * Overload for ValueKind enum.
    */
    public void addParameter(String myName, FieldValueType myValueType, ArrayList myDefaultValues, String myPromptingText, String mySnippet, EnumType myEnumerationType) throws Exception {
        parameterFields.add(new ParameterField(myName,myValueType,myDefaultValues,myPromptingText,mySnippet,myEnumerationType));
    }

    /**
    * Overload for ValueKind defCat.
    */
    public void addParameter(String myName, FieldValueType myValueType, ArrayList myDefaultValues, String myPromptingText, String mySnippet, DefCat myDefCategory) throws Exception {
        parameterFields.add(new ParameterField(myName,myValueType,myDefaultValues,myPromptingText,mySnippet,myDefCategory));
    }

    /**
    * Overload for ValueKind defCat.
    */
    public void addParameter(String myName, FieldValueType myValueType, ArrayList myDefaultValues, String myPromptingText, String mySnippet, ReportFKType myReportFKType) throws Exception {
        parameterFields.add(new ParameterField(myName,myValueType,myDefaultValues,myPromptingText,mySnippet,myReportFKType));
    }

    /**
    * Submits the Query to the database and fills ReportTable with the results.  Returns false if the user clicks Cancel on the Parameters dialog.
    */
    public boolean submitQuery() throws Exception {
        String outputQuery = getQuery();
        if (parameterFields.Count > 0)
        {
            //djc only display parameter dialog if parameters were specified
            //display a dialog for user to enter parameters
            FormParameterInput FormPI = new FormParameterInput();
            for (int i = 0;i < parameterFields.Count;i++)
            {
                FormPI.addInputItem(parameterFields.get___idx(i).getPromptingText(),parameterFields.get___idx(i).getValueType(),parameterFields.get___idx(i).getDefaultValues(),parameterFields.get___idx(i).getEnumerationType(),parameterFields.get___idx(i).getDefCategory(),parameterFields.get___idx(i).getFKeyType());
            }
            FormPI.ShowDialog();
            if (FormPI.DialogResult != DialogResult.OK)
            {
                return false;
            }
             
            for (int i = 0;i < parameterFields.Count;i++)
            {
                parameterFields.get___idx(i).setCurrentValues(FormPI.getCurrentValues(i));
                parameterFields.get___idx(i).applyParamValues();
            }
            //the outputQuery will get altered without affecting the original Query.
            String replacement = "";
            //the replacement value to put into the outputQuery for each match
            //first replace all parameters with values:
            MatchCollection mc = new MatchCollection();
            Regex regex = new Regex("\\?\\w+");
            //? followed by one or more text characters
            mc = regex.Matches(outputQuery);
            for (int i = 0;i < mc.Count;i++)
            {
                //loop through each occurance of "?etc"
                replacement = parameterFields[mc[i].Value.Substring(1)].OutputValue;
                regex = new Regex("\\" + mc[i].Value);
                outputQuery = regex.Replace(outputQuery, replacement);
            }
        }
         
        //then, submit the query
        //MessageBox.Show(outputQuery);
        reportTable = Reports.getTable(outputQuery);
        return true;
    }

    //ODReportData.SubmitQuery(outputQuery);
    /**
    * If the specified section exists, then this returns its height. Otherwise it returns 0.
    */
    public int getSectionHeight(String sectionName) throws Exception {
        if (!sections.contains(sectionName))
        {
            return 0;
        }
         
        return sections.get___idx(sectionName).getHeight();
    }

}


/*
		/// <summary>Add Simple. This is used when there is only a single page in the report and all elements are added to the report header.  Height is set to one row, and all width is set to full page width of 850. There are no other sections to the report.</summary>
		public void AddSimp(string text,int xPos,int yPos,Font font){
			FormReport FormR=new FormReport();
			Graphics grfx=FormR.CreateGraphics();
			Size size=grfx.MeasureString(text,font);
			Section section=Sections.GetOfKind(AreaSectionKind.ReportHeader);
			if(section.Height<1100)
				section.Height=1100;
			ReportObjects.Add(new TextObject(Sections.IndexOf(section),new Point(xPos,yPos)
				,new Size(850,size.Height+2),text,font,ContentAlignment.MiddleLeft));
			grfx.Dispose();
			FormR.Dispose();
		}
		public void AddSimp(string text,int xPos,int yPos){
			AddSimp(text,xPos,yPos,new Font("Tahoma",9));
		}*/