//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormSupplyEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PrinterL;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.Supplier;
import OpenDentBusiness.Suppliers;
import OpenDentBusiness.Supplies;
import OpenDentBusiness.Supply;
import java.util.ArrayList;
import java.util.List;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormSupplies  extends Form 
{

    /**
    * //Used to cache all supply items from DB, this list is compaired to ListSupplyAll to determine which elements have changed and need to be updated.
    */
    //private List<Supply> ListSupplyAllCache = new List<Supply>();
    /**
    * Used to cache all supply items from DB and then altered.
    */
    private List<Supply> ListSupplyAll = new List<Supply>();
    /**
    * Used to populate the grid. Filtered version of ListSupplyAll.
    */
    private List<Supply> ListSupply = new List<Supply>();
    /**
    * Used to cach list of all suppliers.
    */
    public List<Supplier> ListSupplier = new List<Supplier>();
    /**
    * Sets the supplier that will first show when opening this window.
    */
    public long SelectedSupplierNum = new long();
    /**
    * Used for selecting supply items to add to orders.
    */
    public boolean IsSelectMode = new boolean();
    /**
    * Possible deprecated with the addition of ListSelectedSupplies.  Selected supply item. Intended to be used to add to an order.
    */
    public Supply SelectedSupply;
    /**
    * Selected supply items. Intended to be used to add to an order.
    */
    public List<Supply> ListSelectedSupplies = new List<Supply>();
    /**
    * Used to cache the selected SupplyNums of the items in the main grid, to reselect them after a refresh.
    */
    private List<long> SelectedGridItems = new List<long>();
    //Variables used for printing are copied and pasted here
    PrintDocument pd2 = new PrintDocument();
    private int pagesPrinted = new int();
    private boolean headingPrinted = new boolean();
    private int headingPrintH = new int();
    public FormSupplies() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formSupplies_Load(Object sender, EventArgs e) throws Exception {
        Height = SystemInformation.WorkingArea.Height;
        //max height
        Location = new Point(Location.X, 0);
        //move to top of screen
        ListSupplier = Suppliers.createObjects();
        ListSupplyAll = Supplies.getAll();
        //Seperate GetAll() function call so that we do not copy by reference.
        fillComboSupplier();
        fillGrid();
        if (IsSelectMode)
        {
            comboSupplier.Enabled = false;
        }
         
    }

    //gridMain.SelectionMode=GridSelectionMode.One;//we now support multi select to add
    private void fillComboSupplier() throws Exception {
        comboSupplier.Items.Clear();
        comboSupplier.Items.Add(Lan.g(this,"All"));
        comboSupplier.SelectedIndex = 0;
        for (int i = 0;i < ListSupplier.Count;i++)
        {
            //default to "All" otherwise selected index will be selected below.
            comboSupplier.Items.Add(ListSupplier[i].Name);
            if (ListSupplier[i].SupplierNum == SelectedSupplierNum)
            {
                comboSupplier.SelectedIndex = i + 1;
            }
             
        }
    }

    //+1 to account for the ALL item.
    private void fillGrid() throws Exception {
        //We don't refresh ListSupplyAll here because we are frequently using FillGrid with a search filter.
        filterListSupply();
        ListSupply.Sort(sortSupplyListByCategoryOrderThenItemOrder);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"Category"),130);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Catalog #"),80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Supplier"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Description"),240);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Price"), 60, HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"StockQty"), 60, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"IsHidden"), 40, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ListSupply.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            if (i == 0 || ListSupply[i].Category != ListSupply[i - 1].Category)
            {
                row.getCells().Add(DefC.GetName(DefCat.SupplyCats, ListSupply[i].Category));
            }
            else
            {
                row.getCells().add("");
            } 
            row.getCells().Add(ListSupply[i].CatalogNumber);
            row.getCells().Add(Suppliers.GetName(ListSupplier, ListSupply[i].SupplierNum));
            row.getCells().Add(ListSupply[i].Descript);
            if (ListSupply[i].Price == 0)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(ListSupply[i].Price.ToString("n"));
            } 
            if (ListSupply[i].LevelDesired == 0)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(ListSupply[i].LevelDesired.ToString());
            } 
            if (ListSupply[i].IsHidden)
            {
                row.getCells().add("X");
            }
            else
            {
                row.getCells().add("");
            } 
            row.setTag(ListSupply[i].SupplyNum);
            //row.Cells.Add(listSupply[i].ItemOrder.ToString());
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        for (int i = 0;i < ListSupply.Count;i++)
        {
            if (SelectedGridItems.Contains(ListSupply[i].SupplyNum))
            {
                gridMain.setSelected(i,true);
            }
             
        }
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        if (DefC.getShort()[((Enum)DefCat.SupplyCats).ordinal()].Length == 0)
        {
            //No supply categories have been entered, not allowed to enter supply
            MsgBox.show(this,"No supply categories have been created.  Go to the supply inventory window, select categories, and enter at least one supply category first.");
            return ;
        }
         
        if (comboSupplier.SelectedIndex < 1)
        {
            //Includes no items or the ALL item being selected
            MsgBox.show(this,"Please select a supplier first.");
            return ;
        }
         
        Supply supp = new Supply();
        supp.setIsNew(true);
        supp.SupplierNum = ListSupplier[comboSupplier.SelectedIndex - 1].SupplierNum;
        //Selected index -1 to account for ALL being at the top of the list.
        if (gridMain.getSelectedIndex() > -1)
        {
            supp.Category = ListSupply[gridMain.getSelectedIndex()].Category;
            supp.ItemOrder = ListSupply[gridMain.getSelectedIndex()].ItemOrder;
        }
         
        FormSupplyEdit FormS = new FormSupplyEdit();
        FormS.Supp = supp;
        FormS.ListSupplier = ListSupplier;
        FormS.ShowDialog();
        //inserts supply in DB if needed.  Item order will be at selected index or end of category.
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        //update listSupplyAll to reflect changes made to DB.
        ListSupplyAll = Supplies.getAll();
        //int scroll=gridMain.ScrollValue;
        SelectedGridItems.Clear();
        SelectedGridItems.Add(FormS.Supp.SupplyNum);
        fillGrid();
    }

    //gridMain.ScrollValue=scroll;
    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        SelectedGridItems.Clear();
        for (Object __dummyForeachVar0 : gridMain.getSelectedIndices())
        {
            int index = (Integer)__dummyForeachVar0;
            SelectedGridItems.Add(ListSupply[index].SupplyNum);
        }
        if (IsSelectMode)
        {
            SelectedSupply = Supplies.getSupply((Long)gridMain.getRows().get___idx(e.getRow()).getTag());
            ListSelectedSupplies.Clear();
            for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
            {
                //just in case
                ListSelectedSupplies.Add(ListSupply[gridMain.getSelectedIndices()[i]]);
            }
            DialogResult = DialogResult.OK;
            return ;
        }
         
        //Supply supplyCached=Supplies.GetSupply((long)gridMain.Rows[e.Row].Tag);
        FormSupplyEdit FormS = new FormSupplyEdit();
        FormS.Supp = Supplies.getSupply((Long)gridMain.getRows().get___idx(e.getRow()).getTag());
        //works with sorting
        FormS.ListSupplier = ListSupplier;
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        ListSupplyAll = Supplies.getAll();
        int scroll = gridMain.getScrollValue();
        fillGrid();
        gridMain.setScrollValue(scroll);
    }

    private void comboSupplier_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        SelectedGridItems.Clear();
        for (Object __dummyForeachVar1 : gridMain.getSelectedIndices())
        {
            int index = (Integer)__dummyForeachVar1;
            SelectedGridItems.Add(ListSupply[index].SupplyNum);
        }
        fillGrid();
    }

    private void checkShowHidden_Click(Object sender, EventArgs e) throws Exception {
        SelectedGridItems.Clear();
        for (Object __dummyForeachVar2 : gridMain.getSelectedIndices())
        {
            int index = (Integer)__dummyForeachVar2;
            SelectedGridItems.Add(ListSupply[index].SupplyNum);
        }
        fillGrid();
    }

    private void butUp_Click(Object sender, EventArgs e) throws Exception {
        //Nothing Selected
        if (gridMain.getSelectedIndices().Length == 0)
        {
            return ;
        }
         
        //remember selected SupplyNums for later
        SelectedGridItems.Clear();
        for (Object __dummyForeachVar3 : gridMain.getSelectedIndices())
        {
            int index = (Integer)__dummyForeachVar3;
            SelectedGridItems.Add(ListSupply[index].SupplyNum);
        }
        if (Supplies.cleanupItemOrders(ListSupplyAll))
        {
            //should only have to run once more... after code updates to supply window.
            MsgBox.show(this,"There was a problem with sorting, but it has been fixed.  You may now try again.");
            fillGrid();
            return ;
        }
         
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            //loop through selected indicies, moving each one as needed, no saves to the database until after all items are moved.
            int index = gridMain.getSelectedIndices()[i];
            //to reduce confusion
            int itemOrder = ListSupply[index].ItemOrder;
            //to reduce confusion
            //Top of category---------------------------------------------------------------------------------------
            if (itemOrder == 0)
            {
                continue;
            }
             
            //already top of category
            //Top of visible category but not actually top of category----------------------------------------------
            //topmost item in visible list
            if (index == 0 || ListSupply[index].Category != ListSupply[index - 1].Category)
            {
                //topmost item in category in visible list
                moveItemOrderHelper(ListSupply[index], 0);
                continue;
            }
             
            //move item to top of respective category.
            //Item is directly below a selected item in same category-----------------------------------------------
            if (indexIsSelectedHelper(index - 1))
            {
                //check for same category is performed above
                moveItemOrderHelper(ListSupply[index], ListSupply[index - 1].ItemOrder + 1);
                continue;
            }
             
            //move this item after the item above it, because both are selected.
            //Item is directly below a non-selected item in same category-------------------------------------------
            //check for same category is performed above.
            moveItemOrderHelper(ListSupply[index], ListSupply[index - 1].ItemOrder);
        }
        saveChangesToDBHelper();
        ListSupplyAll.Sort(sortSupplyListByCategoryOrderThenItemOrder);
        int scrollVal = gridMain.getScrollValue();
        fillGrid();
        gridMain.setScrollValue(scrollVal);
    }

    private void butDown_Click(Object sender, EventArgs e) throws Exception {
        //Nothing Selected
        if (gridMain.getSelectedIndices().Length == 0)
        {
            return ;
        }
         
        //remember selected SupplyNums for later
        SelectedGridItems.Clear();
        for (Object __dummyForeachVar4 : gridMain.getSelectedIndices())
        {
            int index = (Integer)__dummyForeachVar4;
            SelectedGridItems.Add(ListSupply[index].SupplyNum);
        }
        if (Supplies.cleanupItemOrders(ListSupplyAll))
        {
            //should only have to run once more... after code updates to supply window.
            MsgBox.show(this,"There was a problem with sorting, but it has been fixed.  You may now try again.");
            fillGrid();
            return ;
        }
         
        for (int i = gridMain.getSelectedIndices().Length - 1;i >= 0;i--)
        {
            int index = gridMain.getSelectedIndices()[i];
            //to reduce confusion
            int itemOrder = ListSupply[index].ItemOrder;
            //to reduce confusion
            //Bottom----------------------------------------------
            //bottommost item in visible list
            if (index == ListSupply.Count - 1 || ListSupply[index].Category != ListSupply[index + 1].Category)
            {
                continue;
            }
             
            //bottommost item in category in visible list
            //end of list or category already.
            //Item is directly above a selected item in same category-----------------------------------------------
            if (indexIsSelectedHelper(index + 1))
            {
                //check for same category is performed above
                moveItemOrderHelper(ListSupply[index], ListSupply[index + 1].ItemOrder - 1);
                continue;
            }
             
            //move this item after the item above it, because both are selected.
            //Item is directly below a non-selected item in same category-------------------------------------------
            //check for same category is performed above.
            moveItemOrderHelper(ListSupply[index], ListSupply[index + 1].ItemOrder);
        }
        saveChangesToDBHelper();
        ListSupplyAll.Sort(sortSupplyListByCategoryOrderThenItemOrder);
        int scrollVal = gridMain.getScrollValue();
        fillGrid();
        gridMain.setScrollValue(scrollVal);
    }

    /**
    * Updates database based on the values in ListSupplyAll.
    */
    private void saveChangesToDBHelper() throws Exception {
        //Get all supplies from DB to check for changes.-----------------------------------------------------------------
        List<Supply> ListSupplyAllDB = Supplies.getAll();
        for (int i = 0;i < ListSupplyAll.Count;i++)
        {
            //Itterate through current supply list in memory-----------------------------------------------------------------
            //find DB version of supply to pass to UpdateOrInsertIfNeeded
            Supply supplyOriginal = null;
            for (int j = 0;j < ListSupplyAllDB.Count;j++)
            {
                if (ListSupplyAll[i].SupplyNum != ListSupplyAllDB[j].SupplyNum)
                {
                    continue;
                }
                 
                //not the correct supply
                supplyOriginal = ListSupplyAllDB[j];
                break;
            }
            //found match
            Supplies.UpdateOrInsertIfNeeded(supplyOriginal, ListSupplyAll[i]);
        }
        //accepts null for original.
        //Update inmemory list to reflect changes.
        ListSupplyAll = Supplies.getAll();
    }

    private boolean indexIsSelectedHelper(int index) throws Exception {
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            if (gridMain.getSelectedIndices()[i] == index)
            {
                return true;
            }
             
        }
        return false;
    }

    /**
    * Sets item orders appropriately. Does not reorder list, and does not repaint/refill grid.
    */
    private void moveItemOrderHelper(Supply supply, int newItemOrder) throws Exception {
        if (supply.ItemOrder > newItemOrder)
        {
            for (int i = 0;i < ListSupplyAll.Count;i++)
            {
                //moving item up, itterate down through list
                if (ListSupplyAll[i].Category != supply.Category)
                {
                    continue;
                }
                 
                //wrong category
                if (ListSupplyAll[i].SupplyNum == supply.SupplyNum)
                {
                    ListSupplyAll[i].ItemOrder = newItemOrder;
                    continue;
                }
                 
                //set item order of this supply.
                if (ListSupplyAll[i].ItemOrder >= newItemOrder && ListSupplyAll[i].ItemOrder < supply.ItemOrder)
                {
                    //all items between newItemOrder and oldItemOrder
                    ListSupplyAll[i].ItemOrder++;
                }
                 
            }
        }
        else
        {
            for (int i = ListSupplyAll.Count - 1;i >= 0;i--)
            {
                //moving item down, itterate up through list
                if (ListSupplyAll[i].Category != supply.Category)
                {
                    continue;
                }
                 
                //wrong category
                if (ListSupplyAll[i].SupplyNum == supply.SupplyNum)
                {
                    ListSupplyAll[i].ItemOrder = newItemOrder;
                    continue;
                }
                 
                //set item order of this supply.
                if (ListSupplyAll[i].ItemOrder <= newItemOrder && ListSupplyAll[i].ItemOrder > supply.ItemOrder)
                {
                    //all items between newItemOrder and oldItemOrder
                    ListSupplyAll[i].ItemOrder--;
                }
                 
            }
        } 
        for (int i = 0;i < ListSupply.Count;i++)
        {
            for (int j = 0;j < ListSupplyAll.Count;j++)
            {
                //ListSupplyAll has correct itemOrder values, which we need to copy to listSupply without changing the actual order of ListSupply.
                if (ListSupply[i].SupplyNum != ListSupplyAll[j].SupplyNum)
                {
                    continue;
                }
                 
                ListSupply[i] = ListSupplyAll[j];
            }
        }
    }

    //update order and category.
    private void textFind_TextChanged(Object sender, EventArgs e) throws Exception {
        //TODO: standardize this validation. i.e. use validation provider?
        if (!Regex.IsMatch(textFind.Text, "^[0-9a-zA-Z]*$"))
        {
            textFind.BackColor = Color.LightPink;
            return ;
        }
         
        textFind.BackColor = SystemColors.Window;
        //if(textFind.Text!="" || IsSelectMode) {
        //	butUp.Enabled=false;
        //	butDown.Enabled=false;
        //}
        //else {
        //	butUp.Enabled=true;
        //	butDown.Enabled=true;
        //}
        SelectedGridItems.Clear();
        for (Object __dummyForeachVar5 : gridMain.getSelectedIndices())
        {
            int index = (Integer)__dummyForeachVar5;
            SelectedGridItems.Add(ListSupply[index].SupplyNum);
        }
        fillGrid();
    }

    /**
    * Empties listSupply and adds to it all elements that contain items in the search field. Matches on all columns simultaneously.
    */
    private void filterListSupply() throws Exception {
        ListSupply.Clear();
        ListSupplyAll.Sort(sortSupplyListByCategoryOrderThenItemOrder);
        long supplier = 0;
        if (SelectedSupplierNum != 0)
        {
            //Use supplier num if it is provided, usually when IsSelectMode is also true
            supplier = SelectedSupplierNum;
        }
        else if (comboSupplier.SelectedIndex < 1)
        {
            //this includes selecting All or not having anything selected.
            supplier = 0;
        }
        else
        {
            supplier = ListSupplier[comboSupplier.SelectedIndex - 1].SupplierNum;
        }  
        for (Object __dummyForeachVar6 : ListSupplyAll)
        {
            //SelectedIndex-1 because All is added before all the other items in the list.
            Supply supply = (Supply)__dummyForeachVar6;
            if (!checkShowHidden.Checked && supply.IsHidden)
            {
                continue;
            }
             
            //skip hidden supplies if show hidden is not checked
            if (supplier != 0 && supply.SupplierNum != supplier)
            {
                continue;
            }
             
            //skip supplies that do not match selected supplier
            if (StringSupport.equals(textFind.Text, ""))
            {
                //Start filtering based on findText
                ListSupply.Add(supply);
                continue;
            }
            else if (supply.CatalogNumber.ToString().ToUpper().Contains(textFind.Text.ToUpper()))
            {
                //Check each field to see if it matches the search text field. If it does then add it and move on.
                ListSupply.Add(supply);
                continue;
            }
            else if (DefC.getName(DefCat.SupplyCats,supply.Category).ToUpper().Contains(textFind.Text.ToUpper()))
            {
                ListSupply.Add(supply);
                continue;
            }
            else if (supply.Descript.ToString().ToUpper().Contains(textFind.Text.ToUpper()))
            {
                ListSupply.Add(supply);
                continue;
            }
            else //else if(supply.ItemOrder.ToString().Contains(textFind.Text)) {
            //  listSupply.Add(supply);
            //  continue;
            //}
            if (supply.LevelDesired.ToString().Contains(textFind.Text))
            {
                ListSupply.Add(supply);
                continue;
            }
            else if (supply.Price.ToString().ToUpper().Contains(textFind.Text.ToUpper()))
            {
                ListSupply.Add(supply);
                continue;
            }
            else if (supply.SupplierNum.ToString().Contains(textFind.Text))
            {
                ListSupply.Add(supply);
                continue;
            }
                   
        }
        return ;
    }

    //else if(supply.SupplyNum.ToString().Contains(textFind.Text)) {
    //  listSupply.Add(supply);
    //  continue;
    //}
    //end of filter, item not added, move to next supply item.
    /**
    * Used to sort supply list. Returns -1 if sup1 should come before sup2, 0 is they are the same, and 1 is sup2 should come before sup1.
    */
    private int sortSupplyListByCategoryOrderThenItemOrder(Supply sup1, Supply sup2) throws Exception {
        int sup1Cat = DefC.getOrder(DefCat.SupplyCats,sup1.Category);
        int sup2Cat = DefC.getOrder(DefCat.SupplyCats,sup2.Category);
        if (sup1Cat == sup2Cat)
        {
            //Items in same category
            if (sup1.ItemOrder == sup2.ItemOrder)
            {
                return 0;
            }
            else //same item
            if (sup1.ItemOrder < sup2.ItemOrder)
            {
                return -1;
            }
            else
            {
                return 1;
            }  
        }
        else if (sup1Cat < sup2Cat)
        {
            return -1;
        }
        else
        {
            return 1;
        }  
    }

    private void butPrint_Click(Object sender, EventArgs e) throws Exception {
        if (ListSupply.Count < 1)
        {
            MsgBox.show(this,"Supply list is Empty.");
            return ;
        }
         
        pagesPrinted = 0;
        headingPrinted = false;
        pd2 = new PrintDocument();
        pd2.DefaultPageSettings.Margins = new Margins(50, 50, 40, 30);
        pd2.PrintPage += new PrintPageEventHandler(pd2_PrintPage);
        if (pd2.DefaultPageSettings.PrintableArea.Height == 0)
        {
            pd2.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
        }
         
        if (PrinterL.SetPrinter(pd2, PrintSituation.Default, 0, "Supplies list printed"))
        {
            try
            {
                pd2.Print();
            }
            catch (Exception __dummyCatchVar0)
            {
                MsgBox.show(this,"Printer not available");
            }
        
        }
         
    }

    private void pd2_PrintPage(Object sender, System.Drawing.Printing.PrintPageEventArgs e) throws Exception {
        Rectangle bounds = e.MarginBounds;
        Graphics g = e.Graphics;
        String text = new String();
        Font headingFont = new Font("Arial", 13, FontStyle.Bold);
        Font subHeadingFont = new Font("Arial", 10, FontStyle.Bold);
        Font mainFont = new Font("Arial", 9);
        int yPos = bounds.Top;
        //TODO: Decide what information goes in the heading.
        if (!headingPrinted)
        {
            text = Lan.g(this,"Supply List");
            g.DrawString(text, headingFont, Brushes.Black, 425 - g.MeasureString(text, headingFont).Width / 2, yPos);
            yPos += (int)g.MeasureString(text, headingFont).Height;
            if (checkShowHidden.Checked)
            {
                text = Lan.g(this,"Showing Hidden Items");
                g.DrawString(text, subHeadingFont, Brushes.Black, 425 - g.MeasureString(text, subHeadingFont).Width / 2, yPos);
                yPos += (int)g.MeasureString(text, subHeadingFont).Height;
            }
            else
            {
                text = Lan.g(this,"Not Showing Hidden Items");
                g.DrawString(text, subHeadingFont, Brushes.Black, 425 - g.MeasureString(text, subHeadingFont).Width / 2, yPos);
                yPos += (int)g.MeasureString(text, subHeadingFont).Height;
            } 
            if (!StringSupport.equals(textFind.Text, ""))
            {
                text = Lan.g(this,"Search Filter") + ": " + textFind.Text;
                g.DrawString(text, subHeadingFont, Brushes.Black, 425 - g.MeasureString(text, subHeadingFont).Width / 2, yPos);
                yPos += (int)g.MeasureString(text, subHeadingFont).Height;
            }
            else
            {
                text = Lan.g(this,"No Search Filter");
                g.DrawString(text, subHeadingFont, Brushes.Black, 425 - g.MeasureString(text, subHeadingFont).Width / 2, yPos);
                yPos += (int)g.MeasureString(text, subHeadingFont).Height;
            } 
            if (comboSupplier.SelectedIndex < 1)
            {
                text = Lan.g(this,"All Suppliers");
                g.DrawString(text, subHeadingFont, Brushes.Black, 425 - g.MeasureString(text, subHeadingFont).Width / 2, yPos);
                yPos += (int)g.MeasureString(text, subHeadingFont).Height;
            }
            else
            {
                text = Lan.g(this,"Supplier") + ": " + ListSupplier[comboSupplier.SelectedIndex - 1].Name;
                g.DrawString(text, subHeadingFont, Brushes.Black, 425 - g.MeasureString(text, subHeadingFont).Width / 2, yPos);
                yPos += (int)g.MeasureString(text, subHeadingFont).Height;
                if (!StringSupport.equals(ListSupplier[comboSupplier.SelectedIndex - 1].Phone, ""))
                {
                    text = Lan.g(this,"Phone") + ": " + ListSupplier[comboSupplier.SelectedIndex - 1].Phone;
                    g.DrawString(text, subHeadingFont, Brushes.Black, 425 - g.MeasureString(text, subHeadingFont).Width / 2, yPos);
                    yPos += (int)g.MeasureString(text, subHeadingFont).Height;
                }
                 
                if (!StringSupport.equals(ListSupplier[comboSupplier.SelectedIndex - 1].Name, ""))
                {
                    text = Lan.g(this,"Note") + ": " + ListSupplier[comboSupplier.SelectedIndex - 1].Name;
                    g.DrawString(text, subHeadingFont, Brushes.Black, 425 - g.MeasureString(text, subHeadingFont).Width / 2, yPos);
                    yPos += (int)g.MeasureString(text, subHeadingFont).Height;
                }
                 
            } 
            yPos += 15;
            headingPrinted = true;
            headingPrintH = yPos;
        }
         
        yPos = gridMain.printPage(g,pagesPrinted,bounds,headingPrintH);
        pagesPrinted++;
        if (yPos == -1)
        {
            e.HasMorePages = true;
        }
        else
        {
            e.HasMorePages = false;
        } 
        g.Dispose();
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (IsSelectMode)
        {
            if (gridMain.getSelectedIndices().Length < 1)
            {
                MsgBox.show(this,"Please select a supply from the list first.");
                return ;
            }
             
            ListSelectedSupplies.Clear();
            for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
            {
                //just in case
                ListSelectedSupplies.Add(ListSupply[gridMain.getSelectedIndices()[i]]);
            }
            SelectedSupply = Supplies.getSupply((long)gridMain.getRows()[gridMain.getSelectedIndices()[0]].Tag);
            DialogResult = DialogResult.OK;
        }
         
        //saveChangesToDBHelper();//All changes should already be saved to the database
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
        this.label3 = new System.Windows.Forms.Label();
        this.comboSupplier = new System.Windows.Forms.ComboBox();
        this.checkShowHidden = new System.Windows.Forms.CheckBox();
        this.label1 = new System.Windows.Forms.Label();
        this.textFind = new System.Windows.Forms.TextBox();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butUp = new OpenDental.UI.Button();
        this.butDown = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butPrint = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(521, 11);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(67, 18);
        this.label3.TabIndex = 14;
        this.label3.Text = "Supplier";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboSupplier
        //
        this.comboSupplier.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.comboSupplier.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboSupplier.FormattingEnabled = true;
        this.comboSupplier.Location = new System.Drawing.Point(589, 10);
        this.comboSupplier.Name = "comboSupplier";
        this.comboSupplier.Size = new System.Drawing.Size(170, 21);
        this.comboSupplier.TabIndex = 13;
        this.comboSupplier.SelectionChangeCommitted += new System.EventHandler(this.comboSupplier_SelectionChangeCommitted);
        //
        // checkShowHidden
        //
        this.checkShowHidden.Location = new System.Drawing.Point(209, 13);
        this.checkShowHidden.Name = "checkShowHidden";
        this.checkShowHidden.Size = new System.Drawing.Size(99, 18);
        this.checkShowHidden.TabIndex = 12;
        this.checkShowHidden.Text = "Show Hidden";
        this.checkShowHidden.UseVisualStyleBackColor = true;
        this.checkShowHidden.Click += new System.EventHandler(this.checkShowHidden_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(309, 11);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(49, 18);
        this.label1.TabIndex = 18;
        this.label1.Text = "Search";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textFind
        //
        this.textFind.Location = new System.Drawing.Point(359, 11);
        this.textFind.Name = "textFind";
        this.textFind.Size = new System.Drawing.Size(168, 20);
        this.textFind.TabIndex = 19;
        this.textFind.TextChanged += new System.EventHandler(this.textFind_TextChanged);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 38);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMain.Size = new System.Drawing.Size(747, 614);
        this.gridMain.TabIndex = 5;
        this.gridMain.setTitle("Supplies");
        this.gridMain.setTranslationName(null);
        this.gridMain.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butUp
        //
        this.butUp.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butUp.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butUp.setAutosize(true);
        this.butUp.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butUp.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butUp.setCornerRadius(4F);
        this.butUp.Image = Resources.getup();
        this.butUp.Location = new System.Drawing.Point(12, 661);
        this.butUp.Name = "butUp";
        this.butUp.Size = new System.Drawing.Size(24, 24);
        this.butUp.TabIndex = 28;
        this.butUp.Click += new System.EventHandler(this.butUp_Click);
        //
        // butDown
        //
        this.butDown.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDown.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDown.setAutosize(true);
        this.butDown.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDown.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDown.setCornerRadius(4F);
        this.butDown.Image = Resources.getdown();
        this.butDown.Location = new System.Drawing.Point(42, 661);
        this.butDown.Name = "butDown";
        this.butDown.Size = new System.Drawing.Size(24, 24);
        this.butDown.TabIndex = 29;
        this.butDown.Click += new System.EventHandler(this.butDown_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(603, 661);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 27;
        this.butOK.Text = "&OK";
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
        this.butCancel.Location = new System.Drawing.Point(684, 661);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 26;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butPrint
        //
        this.butPrint.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrint.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butPrint.setAutosize(true);
        this.butPrint.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrint.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrint.setCornerRadius(4F);
        this.butPrint.Image = Resources.getbutPrint();
        this.butPrint.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrint.Location = new System.Drawing.Point(452, 661);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(80, 24);
        this.butPrint.TabIndex = 25;
        this.butPrint.Text = "Print";
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(12, 7);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(69, 24);
        this.butAdd.TabIndex = 11;
        this.butAdd.Text = "Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // FormSupplies
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(771, 696);
        this.Controls.Add(this.textFind);
        this.Controls.Add(this.butUp);
        this.Controls.Add(this.butDown);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.comboSupplier);
        this.Controls.Add(this.checkShowHidden);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.gridMain);
        this.Name = "FormSupplies";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Supplies";
        this.Load += new System.EventHandler(this.FormSupplies_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboSupplier = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.CheckBox checkShowHidden = new System.Windows.Forms.CheckBox();
    private OpenDental.UI.Button butAdd;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textFind = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butPrint;
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butUp;
    private OpenDental.UI.Button butDown;
}


