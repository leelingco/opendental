//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:19 PM
//

package fyiReporting.RdlDesign;

import CS2JNet.System.StringSupport;
import fyiReporting.RdlDesign.DesignXmlDraw;

/**
* Summary description for DialogDataSourceRef.
*/
public class DialogNewChart  extends System.Windows.Forms.Form 
{
    private DesignXmlDraw _Draw;
    private System.Windows.Forms.Button bOK = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox cbDataSets = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox lbFields = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.ListBox lbChartCategories = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Button bCategoryUp = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bCategoryDown = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bCategory = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bSeries = new System.Windows.Forms.Button();
    private System.Windows.Forms.ListBox lbChartSeries = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Button bCategoryDelete = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bSeriesDelete = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bSeriesDown = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button bSeriesUp = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox cbChartData = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox cbSubType = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.ComboBox cbChartType = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public DialogNewChart(DesignXmlDraw dxDraw, XmlNode container) throws Exception {
        _Draw = dxDraw;
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        initValues(container);
    }

    private void initValues(XmlNode container) throws Exception {
        this.bOK.Enabled = false;
        //
        // Obtain the existing DataSets info
        //
        Object[] datasets = _Draw.getDataSetNames();
        if (datasets == null)
            return ;
         
        // not much to do if no DataSets
        if (_Draw.isDataRegion(container))
        {
            String s = _Draw.getDataSetNameValue(container);
            if (s == null)
                return ;
             
            this.cbDataSets.Items.Add(s);
            this.cbDataSets.Enabled = false;
        }
        else
            this.cbDataSets.Items.AddRange(datasets); 
        cbDataSets.SelectedIndex = 0;
        this.cbChartType.SelectedIndex = 2;
    }

    public String getChartXml() throws Exception {
        StringBuilder chart = new StringBuilder("<Chart><Height>2in</Height><Width>4in</Width>");
        chart.AppendFormat("<DataSetName>{0}</DataSetName>", this.cbDataSets.Text);
        chart.Append("<NoRows>Query returned no rows!</NoRows><Style>" + "<BorderStyle><Default>Solid</Default></BorderStyle>" + "<BackgroundColor>White</BackgroundColor>" + "<BackgroundGradientType>LeftRight</BackgroundGradientType>" + "<BackgroundGradientEndColor>Azure</BackgroundGradientEndColor>" + "</Style>");
        chart.AppendFormat("<Type>{0}</Type><Subtype>{1}</Subtype>", this.cbChartType.Text, this.cbSubType.Text);
        // do the categories
        String tcat = "";
        if (this.lbChartCategories.Items.Count > 0)
        {
            chart.Append("<CategoryGroupings>");
            for (Object __dummyForeachVar0 : this.lbChartCategories.Items)
            {
                String cname = (String)__dummyForeachVar0;
                if (StringSupport.equals(tcat, ""))
                    tcat = cname;
                 
                chart.Append("<CategoryGrouping>");
                chart.Append("<DynamicCategories>");
                chart.AppendFormat("<Grouping><GroupExpressions>" + "<GroupExpression>=Fields!{0}.Value</GroupExpression>" + "</GroupExpressions></Grouping>", cname);
                chart.Append("</DynamicCategories>");
                chart.Append("</CategoryGrouping>");
            }
            chart.Append("</CategoryGroupings>");
            // Do the category axis
            chart.AppendFormat("<CategoryAxis><Axis><Visible>True</Visible>" + "<MajorTickMarks>Inside</MajorTickMarks>" + "<MajorGridLines><ShowGridLines>True</ShowGridLines>" + "<Style><BorderStyle><Default>Solid</Default></BorderStyle>" + "</Style></MajorGridLines>" + "<MinorGridLines><ShowGridLines>True</ShowGridLines>" + "<Style><BorderStyle><Default>Solid</Default></BorderStyle>" + "</Style></MinorGridLines>" + "<Title><Caption>{0}</Caption>" + "</Title></Axis></CategoryAxis>", tcat);
        }
         
        // do the series
        String tser = "";
        if (this.lbChartSeries.Items.Count > 0)
        {
            chart.Append("<SeriesGroupings>");
            for (Object __dummyForeachVar1 : this.lbChartSeries.Items)
            {
                String sname = (String)__dummyForeachVar1;
                if (StringSupport.equals(tser, ""))
                    tser = sname;
                 
                chart.Append("<SeriesGrouping>");
                chart.Append("<DynamicSeries>");
                chart.AppendFormat("<Grouping><GroupExpressions>" + "<GroupExpression>=Fields!{0}.Value</GroupExpression>" + "</GroupExpressions></Grouping>", sname);
                chart.AppendFormat("<Label>=Fields!{0}.Value</Label>", sname);
                chart.Append("</DynamicSeries>");
                chart.Append("</SeriesGrouping>");
            }
            chart.Append("</SeriesGroupings>");
        }
         
        // Chart Data
        if (this.cbChartData.Text.Length > 0)
        {
            chart.AppendFormat("<ChartData><ChartSeries><DataPoints><DataPoint>" + "<DataValues><DataValue><Value>{0}</Value></DataValue></DataValues>" + "</DataPoint></DataPoints></ChartSeries></ChartData>", this.cbChartData.Text);
            // Do the value axis
            String vtitle = new String();
            int start = this.cbChartData.Text.LastIndexOf("!");
            if (start > 0)
            {
                int end = this.cbChartData.Text.LastIndexOf(".Value");
                if (end < 0 || end <= start + 1)
                    vtitle = this.cbChartData.Text.Substring(start + 1);
                else
                    vtitle = this.cbChartData.Text.Substring(start + 1, end - start - 1); 
            }
            else
                vtitle = "Values"; 
            chart.AppendFormat("<ValueAxis><Axis><Visible>True</Visible>" + "<MajorTickMarks>Inside</MajorTickMarks>" + "<MajorGridLines><ShowGridLines>True</ShowGridLines>" + "<Style><BorderStyle><Default>Solid</Default></BorderStyle>" + "<FontSize>8pt</FontSize>" + "</Style></MajorGridLines>" + "<MinorGridLines><ShowGridLines>True</ShowGridLines>" + "<Style><BorderStyle><Default>Solid</Default></BorderStyle>" + "</Style></MinorGridLines>" + "<Title><Caption>{0}</Caption>" + "<Style><WritingMode>tb-rl</WritingMode></Style>" + "</Title></Axis></ValueAxis>", vtitle);
        }
         
        // Legend
        chart.Append("<Legend><Style><BorderStyle><Default>Solid</Default>" + "</BorderStyle><PaddingLeft>5pt</PaddingLeft>" + "<FontSize>8pt</FontSize></Style><Visible>True</Visible>" + "<Position>RightCenter</Position></Legend>");
        // Title
        chart.AppendFormat("<Title><Style><FontWeight>Bold</FontWeight>" + "<FontSize>14pt</FontSize><TextAlign>Center</TextAlign>" + "</Style><Caption>{0} {1} Chart</Caption></Title>", tcat, tser);
        // end of Chart defintion
        chart.Append("</Chart>");
        return chart.ToString();
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
        this.bOK = new System.Windows.Forms.Button();
        this.bCancel = new System.Windows.Forms.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.cbDataSets = new System.Windows.Forms.ComboBox();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.lbFields = new System.Windows.Forms.ListBox();
        this.lbChartCategories = new System.Windows.Forms.ListBox();
        this.bCategoryUp = new System.Windows.Forms.Button();
        this.bCategoryDown = new System.Windows.Forms.Button();
        this.bCategory = new System.Windows.Forms.Button();
        this.bSeries = new System.Windows.Forms.Button();
        this.lbChartSeries = new System.Windows.Forms.ListBox();
        this.bCategoryDelete = new System.Windows.Forms.Button();
        this.bSeriesDelete = new System.Windows.Forms.Button();
        this.bSeriesDown = new System.Windows.Forms.Button();
        this.bSeriesUp = new System.Windows.Forms.Button();
        this.label4 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.cbChartData = new System.Windows.Forms.ComboBox();
        this.label6 = new System.Windows.Forms.Label();
        this.cbSubType = new System.Windows.Forms.ComboBox();
        this.cbChartType = new System.Windows.Forms.ComboBox();
        this.label7 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // bOK
        //
        this.bOK.Location = new System.Drawing.Point(272, 392);
        this.bOK.Name = "bOK";
        this.bOK.TabIndex = 13;
        this.bOK.Text = "OK";
        this.bOK.Click += new System.EventHandler(this.bOK_Click);
        //
        // bCancel
        //
        this.bCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.bCancel.Location = new System.Drawing.Point(368, 392);
        this.bCancel.Name = "bCancel";
        this.bCancel.TabIndex = 14;
        this.bCancel.Text = "Cancel";
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(16, 16);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(48, 23);
        this.label1.TabIndex = 11;
        this.label1.Text = "DataSet";
        //
        // cbDataSets
        //
        this.cbDataSets.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.cbDataSets.Location = new System.Drawing.Point(80, 16);
        this.cbDataSets.Name = "cbDataSets";
        this.cbDataSets.Size = new System.Drawing.Size(360, 21);
        this.cbDataSets.TabIndex = 0;
        this.cbDataSets.SelectedIndexChanged += new System.EventHandler(this.cbDataSets_SelectedIndexChanged);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(16, 80);
        this.label2.Name = "label2";
        this.label2.TabIndex = 13;
        this.label2.Text = "DataSet Fields";
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(224, 80);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(184, 23);
        this.label3.TabIndex = 14;
        this.label3.Text = "Chart Categories (X) Groupings";
        //
        // lbFields
        //
        this.lbFields.Location = new System.Drawing.Point(16, 104);
        this.lbFields.Name = "lbFields";
        this.lbFields.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.lbFields.Size = new System.Drawing.Size(152, 225);
        this.lbFields.TabIndex = 1;
        //
        // lbChartCategories
        //
        this.lbChartCategories.Location = new System.Drawing.Point(232, 104);
        this.lbChartCategories.Name = "lbChartCategories";
        this.lbChartCategories.Size = new System.Drawing.Size(152, 94);
        this.lbChartCategories.TabIndex = 3;
        //
        // bCategoryUp
        //
        this.bCategoryUp.Location = new System.Drawing.Point(392, 104);
        this.bCategoryUp.Name = "bCategoryUp";
        this.bCategoryUp.Size = new System.Drawing.Size(48, 24);
        this.bCategoryUp.TabIndex = 4;
        this.bCategoryUp.Text = "Up";
        this.bCategoryUp.Click += new System.EventHandler(this.bCategoryUp_Click);
        //
        // bCategoryDown
        //
        this.bCategoryDown.Location = new System.Drawing.Point(392, 136);
        this.bCategoryDown.Name = "bCategoryDown";
        this.bCategoryDown.Size = new System.Drawing.Size(48, 24);
        this.bCategoryDown.TabIndex = 5;
        this.bCategoryDown.Text = "Down";
        this.bCategoryDown.Click += new System.EventHandler(this.bCategoryDown_Click);
        //
        // bCategory
        //
        this.bCategory.Location = new System.Drawing.Point(184, 112);
        this.bCategory.Name = "bCategory";
        this.bCategory.Size = new System.Drawing.Size(32, 24);
        this.bCategory.TabIndex = 2;
        this.bCategory.Text = ">";
        this.bCategory.Click += new System.EventHandler(this.bCategory_Click);
        //
        // bSeries
        //
        this.bSeries.Location = new System.Drawing.Point(184, 240);
        this.bSeries.Name = "bSeries";
        this.bSeries.Size = new System.Drawing.Size(32, 24);
        this.bSeries.TabIndex = 7;
        this.bSeries.Text = ">";
        this.bSeries.Click += new System.EventHandler(this.bSeries_Click);
        //
        // lbChartSeries
        //
        this.lbChartSeries.Location = new System.Drawing.Point(232, 232);
        this.lbChartSeries.Name = "lbChartSeries";
        this.lbChartSeries.Size = new System.Drawing.Size(152, 94);
        this.lbChartSeries.TabIndex = 8;
        //
        // bCategoryDelete
        //
        this.bCategoryDelete.Location = new System.Drawing.Point(392, 168);
        this.bCategoryDelete.Name = "bCategoryDelete";
        this.bCategoryDelete.Size = new System.Drawing.Size(48, 24);
        this.bCategoryDelete.TabIndex = 6;
        this.bCategoryDelete.Text = "Delete";
        this.bCategoryDelete.Click += new System.EventHandler(this.bCategoryDelete_Click);
        //
        // bSeriesDelete
        //
        this.bSeriesDelete.Location = new System.Drawing.Point(392, 296);
        this.bSeriesDelete.Name = "bSeriesDelete";
        this.bSeriesDelete.Size = new System.Drawing.Size(48, 24);
        this.bSeriesDelete.TabIndex = 11;
        this.bSeriesDelete.Text = "Delete";
        this.bSeriesDelete.Click += new System.EventHandler(this.bSeriesDelete_Click);
        //
        // bSeriesDown
        //
        this.bSeriesDown.Location = new System.Drawing.Point(392, 264);
        this.bSeriesDown.Name = "bSeriesDown";
        this.bSeriesDown.Size = new System.Drawing.Size(48, 24);
        this.bSeriesDown.TabIndex = 10;
        this.bSeriesDown.Text = "Down";
        this.bSeriesDown.Click += new System.EventHandler(this.bSeriesDown_Click);
        //
        // bSeriesUp
        //
        this.bSeriesUp.Location = new System.Drawing.Point(392, 232);
        this.bSeriesUp.Name = "bSeriesUp";
        this.bSeriesUp.Size = new System.Drawing.Size(48, 24);
        this.bSeriesUp.TabIndex = 9;
        this.bSeriesUp.Text = "Up";
        this.bSeriesUp.Click += new System.EventHandler(this.bSeriesUp_Click);
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(224, 208);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(184, 23);
        this.label4.TabIndex = 31;
        this.label4.Text = "Chart Series";
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(16, 344);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(120, 23);
        this.label5.TabIndex = 32;
        this.label5.Text = "Chart Cell Expression";
        //
        // cbChartData
        //
        this.cbChartData.Location = new System.Drawing.Point(16, 360);
        this.cbChartData.Name = "cbChartData";
        this.cbChartData.Size = new System.Drawing.Size(368, 21);
        this.cbChartData.TabIndex = 12;
        this.cbChartData.TextChanged += new System.EventHandler(this.cbChartData_TextChanged);
        this.cbChartData.Enter += new System.EventHandler(this.cbChartData_Enter);
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(232, 48);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(64, 23);
        this.label6.TabIndex = 36;
        this.label6.Text = "Sub-type";
        //
        // cbSubType
        //
        this.cbSubType.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.cbSubType.Location = new System.Drawing.Point(312, 48);
        this.cbSubType.Name = "cbSubType";
        this.cbSubType.Size = new System.Drawing.Size(80, 21);
        this.cbSubType.TabIndex = 35;
        //
        // cbChartType
        //
        this.cbChartType.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.cbChartType.Items.AddRange(new Object[]{ "Area", "Bar", "Column", "Doughnut", "Line", "Pie" });
        this.cbChartType.Location = new System.Drawing.Point(96, 48);
        this.cbChartType.Name = "cbChartType";
        this.cbChartType.Size = new System.Drawing.Size(121, 21);
        this.cbChartType.TabIndex = 34;
        this.cbChartType.SelectedIndexChanged += new System.EventHandler(this.cbChartType_SelectedIndexChanged);
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(16, 48);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(72, 16);
        this.label7.TabIndex = 33;
        this.label7.Text = "Chart Type";
        //
        // DialogNewChart
        //
        this.AcceptButton = this.bOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.bCancel;
        this.ClientSize = new System.Drawing.Size(456, 424);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.cbSubType);
        this.Controls.Add(this.cbChartType);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.cbChartData);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.bSeriesDelete);
        this.Controls.Add(this.bSeriesDown);
        this.Controls.Add(this.bSeriesUp);
        this.Controls.Add(this.bCategoryDelete);
        this.Controls.Add(this.lbChartSeries);
        this.Controls.Add(this.bSeries);
        this.Controls.Add(this.bCategory);
        this.Controls.Add(this.bCategoryDown);
        this.Controls.Add(this.bCategoryUp);
        this.Controls.Add(this.lbChartCategories);
        this.Controls.Add(this.lbFields);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.cbDataSets);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.bCancel);
        this.Controls.Add(this.bOK);
        this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "DialogNewChart";
        this.ShowInTaskbar = false;
        this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
        this.Text = "New Chart";
        this.ResumeLayout(false);
    }

    private void bOK_Click(Object sender, System.EventArgs e) throws Exception {
        // apply the result
        DialogResult = DialogResult.OK;
    }

    private void cbDataSets_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        this.lbChartCategories.Items.Clear();
        this.lbChartSeries.Items.Clear();
        bOK.Enabled = false;
        this.lbFields.Items.Clear();
        String[] fields = _Draw.GetFields(cbDataSets.Text, false);
        if (fields != null)
            lbFields.Items.AddRange(fields);
         
    }

    private void bCategory_Click(Object sender, System.EventArgs e) throws Exception {
        ICollection sic = lbFields.SelectedIndices;
        int count = sic.Count;
        for (Object __dummyForeachVar2 : sic)
        {
            int i = (Integer)__dummyForeachVar2;
            String fname = (String)lbFields.Items[i];
            if (this.lbChartCategories.Items.IndexOf(fname) < 0)
                lbChartCategories.Items.Add(fname);
             
        }
        okEnable();
    }

    private void bSeries_Click(Object sender, System.EventArgs e) throws Exception {
        ICollection sic = lbFields.SelectedIndices;
        int count = sic.Count;
        for (Object __dummyForeachVar3 : sic)
        {
            int i = (Integer)__dummyForeachVar3;
            String fname = (String)lbFields.Items[i];
            if (this.lbChartSeries.Items.IndexOf(fname) < 0)
                lbChartSeries.Items.Add(fname);
             
        }
        okEnable();
    }

    private void bCategoryUp_Click(Object sender, System.EventArgs e) throws Exception {
        int index = lbChartCategories.SelectedIndex;
        if (index <= 0)
            return ;
         
        String prename = (String)lbChartCategories.Items[index - 1];
        lbChartCategories.Items.RemoveAt(index - 1);
        lbChartCategories.Items.Insert(index, prename);
    }

    private void bCategoryDown_Click(Object sender, System.EventArgs e) throws Exception {
        int index = lbChartCategories.SelectedIndex;
        if (index < 0 || index + 1 == lbChartCategories.Items.Count)
            return ;
         
        String postname = (String)lbChartCategories.Items[index + 1];
        lbChartCategories.Items.RemoveAt(index + 1);
        lbChartCategories.Items.Insert(index, postname);
    }

    private void bCategoryDelete_Click(Object sender, System.EventArgs e) throws Exception {
        int index = lbChartCategories.SelectedIndex;
        if (index < 0)
            return ;
         
        lbChartCategories.Items.RemoveAt(index);
        okEnable();
    }

    private void bSeriesUp_Click(Object sender, System.EventArgs e) throws Exception {
        int index = lbChartSeries.SelectedIndex;
        if (index <= 0)
            return ;
         
        String prename = (String)lbChartSeries.Items[index - 1];
        lbChartSeries.Items.RemoveAt(index - 1);
        lbChartSeries.Items.Insert(index, prename);
    }

    private void bSeriesDown_Click(Object sender, System.EventArgs e) throws Exception {
        int index = lbChartSeries.SelectedIndex;
        if (index < 0 || index + 1 == lbChartSeries.Items.Count)
            return ;
         
        String postname = (String)lbChartSeries.Items[index + 1];
        lbChartSeries.Items.RemoveAt(index + 1);
        lbChartSeries.Items.Insert(index, postname);
    }

    private void bSeriesDelete_Click(Object sender, System.EventArgs e) throws Exception {
        int index = lbChartSeries.SelectedIndex;
        if (index < 0)
            return ;
         
        lbChartSeries.Items.RemoveAt(index);
        okEnable();
    }

    private void okEnable() throws Exception {
        // We need values in datasets and Categories or Series for OK to work correctly
        bOK.Enabled = (this.lbChartCategories.Items.Count > 0 || this.lbChartSeries.Items.Count > 0) && this.cbDataSets.Text != null && this.cbDataSets.Text.Length > 0;
    }

    private void cbChartData_Enter(Object sender, System.EventArgs e) throws Exception {
        cbChartData.Items.Clear();
        for (Object __dummyForeachVar4 : this.lbFields.Items)
        {
            String field = (String)__dummyForeachVar4;
            if (this.lbChartCategories.Items.IndexOf(field) >= 0 || this.lbChartSeries.Items.IndexOf(field) >= 0)
                continue;
             
            // Field selected in columns and rows
            this.cbChartData.Items.Add(String.Format("=Sum(Fields!{0}.Value)", field));
        }
    }

    private void cbChartData_TextChanged(Object sender, System.EventArgs e) throws Exception {
        okEnable();
    }

    private void cbChartType_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        // Change the potential sub-types
        String savesub = cbSubType.Text;
        String[] subItems = new String[]();
        Text __dummyScrutVar0 = cbChartType.Text;
        if (__dummyScrutVar0.equals("Column"))
        {
            subItems = new String[]{ "Plain", "Stacked", "PercentStacked" };
        }
        else if (__dummyScrutVar0.equals("Bar"))
        {
            subItems = new String[]{ "Plain", "Stacked", "PercentStacked" };
        }
        else if (__dummyScrutVar0.equals("Line"))
        {
            subItems = new String[]{ "Plain", "Smooth" };
        }
        else if (__dummyScrutVar0.equals("Pie"))
        {
            subItems = new String[]{ "Plain", "Exploded" };
        }
        else if (__dummyScrutVar0.equals("Area"))
        {
            subItems = new String[]{ "Plain", "Stacked" };
        }
        else if (__dummyScrutVar0.equals("Doughnut"))
        {
            subItems = new String[]{ "Plain" };
        }
        else if (__dummyScrutVar0.equals("Scatter"))
        {
            subItems = new String[]{ "Plain", "Line", "SmoothLine" };
        }
        else
        {
            subItems = new String[]{ "Plain" };
        }       
        cbSubType.Items.Clear();
        cbSubType.Items.AddRange(subItems);
        int i = 0;
        for (Object __dummyForeachVar5 : subItems)
        {
            String s = (String)__dummyForeachVar5;
            if (StringSupport.equals(s, savesub))
            {
                cbSubType.SelectedIndex = i;
                return ;
            }
             
            i++;
        }
        // Didn't match old style
        cbSubType.SelectedIndex = 0;
    }

}


