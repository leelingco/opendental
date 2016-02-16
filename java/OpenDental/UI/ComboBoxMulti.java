//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental.UI;


/**
* Summary description for ComboBoxMulti.
*/
public class ComboBoxMulti  extends System.Windows.Forms.UserControl 
{
    private ArrayList items = new ArrayList();
    private System.Windows.Forms.PictureBox dropButton = new System.Windows.Forms.PictureBox();
    private boolean droppedDown = new boolean();
    private System.Windows.Forms.TextBox textMain = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.ContextMenu cMenu = new System.Windows.Forms.ContextMenu();
    private ArrayList selectedIndices = new ArrayList();
    private boolean useCommas = new boolean();
    /**
    * 
    */
    public ComboBoxMulti() throws Exception {
        // This call is required by the Windows.Forms Form Designer.
        initializeComponent();
        selectedIndices = new ArrayList();
        items = new ArrayList();
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.Resources.ResourceManager resources = new System.Resources.ResourceManager(OpenDental.UI.ComboBoxMulti.class);
        this.dropButton = new System.Windows.Forms.PictureBox();
        this.textMain = new System.Windows.Forms.TextBox();
        this.cMenu = new System.Windows.Forms.ContextMenu();
        this.SuspendLayout();
        //
        // dropButton
        //
        this.dropButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.dropButton.Image = ((System.Drawing.Image)(resources.GetObject("dropButton.Image")));
        this.dropButton.Location = new System.Drawing.Point(102, 1);
        this.dropButton.Name = "dropButton";
        this.dropButton.Size = new System.Drawing.Size(17, 19);
        this.dropButton.TabIndex = 1;
        this.dropButton.TabStop = false;
        this.dropButton.Click += new System.EventHandler(this.dropButton_Click);
        //
        // textMain
        //
        this.textMain.BackColor = System.Drawing.Color.White;
        this.textMain.BorderStyle = System.Windows.Forms.BorderStyle.None;
        this.textMain.Location = new System.Drawing.Point(3, 4);
        this.textMain.Name = "textMain";
        this.textMain.ReadOnly = true;
        this.textMain.Size = new System.Drawing.Size(95, 13);
        this.textMain.TabIndex = 2;
        this.textMain.Text = "";
        //
        // ComboBoxMulti
        //
        this.BackColor = System.Drawing.SystemColors.Window;
        this.Controls.Add(this.textMain);
        this.Controls.Add(this.dropButton);
        this.Name = "ComboBoxMulti";
        this.Size = new System.Drawing.Size(120, 21);
        this.Load += new System.EventHandler(this.ComboBoxMulti_Load);
        this.Paint += new System.Windows.Forms.PaintEventHandler(this.ComboBoxMulti_Paint);
        this.Leave += new System.EventHandler(this.ComboBoxMulti_Leave);
        this.Layout += new System.Windows.Forms.LayoutEventHandler(this.ComboBoxMulti_Layout);
        this.ResumeLayout(false);
    }

    /**
    * The items to display in the combo box.
    */
    public ArrayList getItems() throws Exception {
        return items;
    }

    public void setItems(ArrayList value) throws Exception {
        items = value;
    }

    /**
    * Gets or sets a value indicating whether the combo box is displaying its drop-down portion.
    */
    public boolean getDroppedDown() throws Exception {
        return droppedDown;
    }

    public void setDroppedDown(boolean value) throws Exception {
        droppedDown = value;
    }

    /**
    * The indices of selected items.
    */
    public ArrayList getSelectedIndices() throws Exception {
        return selectedIndices;
    }

    public void setSelectedIndices(ArrayList value) throws Exception {
        selectedIndices = value;
    }

    /**
    * Use commas instead of OR in the display when muliple selected.
    */
    public boolean getUseCommas() throws Exception {
        return useCommas;
    }

    public void setUseCommas(boolean value) throws Exception {
        useCommas = value;
    }

    /**
    * Force text being displayed to be refreshed.
    */
    public void refreshText() throws Exception {
        fillText();
    }

    private void comboBoxMulti_Paint(Object sender, System.Windows.Forms.PaintEventArgs e) throws Exception {
        //blue
        e.Graphics.DrawRectangle(new Pen(Color.FromArgb(127, 157, 185)), 0, 0, Width - 1, Height - 1);
    }

    private void dropButton_Click(Object sender, System.EventArgs e) throws Exception {
        textMain.Select();
        //this is critical to trigger the enter and leave events.
        if (droppedDown)
        {
            droppedDown = false;
        }
        else
        {
            //show the list
            cMenu = new ContextMenu();
            MenuItem menuItem = new MenuItem();
            for (int i = 0;i < items.Count;i++)
            {
                menuItem = new MenuItem(items[i].ToString(), new System.EventHandler(MenuItem_Click));
                menuItem.OwnerDraw = true;
                menuItem.MeasureItem += new System.Windows.Forms.MeasureItemEventHandler(MenuItem_MeasureItem);
                menuItem.DrawItem += new System.Windows.Forms.DrawItemEventHandler(MenuItem_DrawItem);
                cMenu.MenuItems.Add(menuItem);
            }
            cMenu.Show(this, new Point(0, 20));
            /*
            				listBox2=new ListBox();
            				listBox2.Click += new System.EventHandler(listBox2_Click);
            				listBox2.SelectionMode=SelectionMode.MultiExtended;
            				//MessageBox.Show(items.Count.ToString());
            				listBox2.Size=new Size(Width,14*items.Count);
            				listBox2.Location=new Point(0,21);
            				Height=listBox2.Height+21;
            				//MessageBox.Show(Height.ToString());
            				for(int i=0;i<items.Count;i++){
            					listBox2.Items.Add(items[i]);
            					if(selectedIndices.Contains(i)){
            						listBox2.SetSelected(i,true);
            					}
            				}
            				Controls.Add(listBox2);
            				this.BringToFront();*/
            //((Control)Container).Height;
            //this.Location;
            droppedDown = true;
        } 
    }

    private void menuItem_Click(Object sender, System.EventArgs e) throws Exception {
        int index = ((MenuItem)sender).Index;
        if (selectedIndices.Contains(index))
        {
            //this item was already selected
            selectedIndices.Remove(index);
        }
        else
        {
            selectedIndices.Add(index);
        } 
        fillText();
        cMenu.Show(this, new Point(0, 20));
    }

    private void menuItem_MeasureItem(Object sender, System.Windows.Forms.MeasureItemEventArgs e) throws Exception {
        e.ItemWidth = Width - 18;
        //not sure why I have to subtract 18 to make it the proper width.
        e.ItemHeight = 14;
    }

    private void menuItem_DrawItem(Object sender, System.Windows.Forms.DrawItemEventArgs e) throws Exception {
        String myCaption = items[e.Index].ToString();
        Brush brushBack = new Brush();
        if (selectedIndices.Contains(e.Index))
            brushBack = SystemBrushes.Highlight;
        else
            brushBack = SystemBrushes.Window; 
        Font myFont = new Font(FontFamily.GenericSansSerif, 8);
        e.Graphics.FillRectangle(brushBack, e.Bounds);
        e.Graphics.DrawString(myCaption, e.Font, Brushes.Black, e.Bounds.X, e.Bounds.Y);
    }

    private void fillText() throws Exception {
        textMain.Text = "";
        if (useCommas)
        {
            for (int i = 0;i < selectedIndices.Count;i++)
            {
                if (i > 0)
                {
                    textMain.Text += ", ";
                }
                 
                textMain.Text += items[(int)selectedIndices[i]];
            }
        }
        else
        {
            for (int i = 0;i < selectedIndices.Count;i++)
            {
                if (i > 0)
                {
                    textMain.Text += " OR ";
                }
                 
                textMain.Text += items[(int)selectedIndices[i]];
            }
        } 
    }

    private void comboBoxMulti_Layout(Object sender, System.Windows.Forms.LayoutEventArgs e) throws Exception {
        textMain.Width = Width - 21;
    }

    //private void listBox2_Click(object sender, System.EventArgs e) {
    //	selectedIndices=new ArrayList();
    //	for(int i=0;i<listBox2.SelectedIndices.Count;i++){
    //		selectedIndices.Add(listBox2.SelectedIndices[i]);
    //	}
    //	FillText();
    //}
    private void comboBoxMulti_Load(Object sender, System.EventArgs e) throws Exception {
        fillText();
    }

    private void comboBoxMulti_Leave(Object sender, System.EventArgs e) throws Exception {
        droppedDown = false;
    }

    /**
    * 
    */
    public void setSelected(int index, boolean setToValue) throws Exception {
        if (setToValue)
            selectedIndices.Add(index);
        else
            selectedIndices.Remove(index); 
    }

}


