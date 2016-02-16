//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.FormSupplies;
import OpenDental.FormSupplyOrderEdit;
import OpenDental.FormSupplyOrderItemEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.PrinterL;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.Supplier;
import OpenDentBusiness.Suppliers;
import OpenDentBusiness.Supply;
import OpenDentBusiness.SupplyOrder;
import OpenDentBusiness.SupplyOrderItem;
import OpenDentBusiness.SupplyOrderItems;
import OpenDentBusiness.SupplyOrders;
import java.util.ArrayList;
import java.util.List;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormSupplyOrders  extends Form 
{

    private List<Supplier> ListSuppliers = new List<Supplier>();
    private List<SupplyOrder> ListOrdersAll = new List<SupplyOrder>();
    private List<SupplyOrder> ListOrders = new List<SupplyOrder>();
    private DataTable tableOrderItems = new DataTable();
    //Variables used for printing are copied and pasted here
    PrintDocument pd2 = new PrintDocument();
    private int pagesPrinted = new int();
    private boolean headingPrinted = new boolean();
    private int headingPrintH = new int();
    public FormSupplyOrders() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formSupplyOrders_Load(Object sender, EventArgs e) throws Exception {
        Height = SystemInformation.WorkingArea.Height;
        //max height
        Location = new Point(Location.X, 0);
        //move to top of screen
        ListSuppliers = Suppliers.createObjects();
        ListOrdersAll = SupplyOrders.getAll();
        ListOrders = new List<SupplyOrder>();
        fillComboSupplier();
        fillGridOrders();
        gridOrders.scrollToEnd();
    }

    private void fillComboSupplier() throws Exception {
        ListSuppliers = Suppliers.createObjects();
        comboSupplier.Items.Clear();
        comboSupplier.Items.Add(Lan.g(this,"All"));
        //add all to begining of list for composite listings.
        comboSupplier.SelectedIndex = 0;
        for (int i = 0;i < ListSuppliers.Count;i++)
        {
            comboSupplier.Items.Add(ListSuppliers[i].Name);
        }
    }

    private void comboSupplier_SelectedIndexChanged(Object sender, EventArgs e) throws Exception {
        fillGridOrders();
        gridOrders.scrollToEnd();
        fillGridOrderItem();
    }

    private void gridOrder_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        fillGridOrderItem();
    }

    private void gridOrder_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormSupplyOrderEdit FormSOE = new FormSupplyOrderEdit();
        FormSOE.ListSupplier = ListSuppliers;
        FormSOE.Order = ListOrders[e.getRow()];
        FormSOE.ShowDialog();
        if (FormSOE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        ListOrdersAll = SupplyOrders.getAll();
        fillGridOrders();
        fillGridOrderItem();
    }

    private void butAddSupply_Click(Object sender, EventArgs e) throws Exception {
        if (gridOrders.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select a supply order to add items to first.");
            return ;
        }
         
        FormSupplies FormSup = new FormSupplies();
        FormSup.IsSelectMode = true;
        FormSup.SelectedSupplierNum = ListOrders[gridOrders.getSelectedIndex()].SupplierNum;
        FormSup.ShowDialog();
        if (FormSup.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        for (int i = 0;i < FormSup.ListSelectedSupplies.Count;i++)
        {
            //check for existing----
            if (itemExistsHelper(FormSup.ListSelectedSupplies[i]))
            {
                continue;
            }
             
            //MsgBox.Show(this,"Selected item already exists in currently selected order. Please edit quantity instead.");
            SupplyOrderItem orderitem = new SupplyOrderItem();
            orderitem.SupplyNum = FormSup.ListSelectedSupplies[i].SupplyNum;
            orderitem.Qty = 1;
            orderitem.Price = FormSup.ListSelectedSupplies[i].Price;
            orderitem.SupplyOrderNum = ListOrders[gridOrders.getSelectedIndex()].SupplyOrderNum;
            //soi.SupplyOrderItemNum
            SupplyOrderItems.insert(orderitem);
        }
        fillGridOrderItem();
    }

    /**
    * Returns true if item exists in supply order.
    */
    private boolean itemExistsHelper(Supply supply) throws Exception {
        for (int i = 0;i < tableOrderItems.Rows.Count;i++)
        {
            if ((long)tableOrderItems.Rows[i]["SupplyNum"] == supply.SupplyNum)
            {
                return true;
            }
             
        }
        return false;
    }

    private void fillGridOrders() throws Exception {
        filterListOrder();
        gridOrders.beginUpdate();
        gridOrders.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"Date Placed"),80);
        gridOrders.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Amount"), 70, HorizontalAlignment.Right);
        gridOrders.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Supplier"),120);
        gridOrders.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Note"),200);
        gridOrders.getColumns().add(col);
        gridOrders.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ListOrders.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            if (ListOrders[i].DatePlaced.Year > 2200)
            {
                row.getCells().add(Lan.g(this,"pending"));
            }
            else
            {
                row.getCells().Add(ListOrders[i].DatePlaced.ToShortDateString());
            } 
            row.getCells().Add(ListOrders[i].AmountTotal.ToString("c"));
            row.getCells().Add(Suppliers.GetName(ListSuppliers, ListOrders[i].SupplierNum));
            row.getCells().Add(ListOrders[i].Note);
            gridOrders.getRows().add(row);
        }
        gridOrders.endUpdate();
    }

    private void filterListOrder() throws Exception {
        ListOrders.Clear();
        long supplier = 0;
        if (comboSupplier.SelectedIndex < 1)
        {
            //this includes selecting All or not having anything selected.
            supplier = 0;
        }
        else
        {
            supplier = ListSuppliers[comboSupplier.SelectedIndex - 1].SupplierNum;
        } 
        for (Object __dummyForeachVar0 : ListOrdersAll)
        {
            //SelectedIndex-1 because All is added before all the other items in the list.
            SupplyOrder order = (SupplyOrder)__dummyForeachVar0;
            if (supplier == 0)
            {
                //Either the ALL supplier is selected or no supplier is selected.
                ListOrders.Add(order);
            }
            else if (order.SupplierNum == supplier)
            {
                ListOrders.Add(order);
                continue;
            }
              
        }
    }

    private void fillGridOrderItem() throws Exception {
        long orderNum = 0;
        if (gridOrders.getSelectedIndex() != -1)
        {
            //an order is selected
            orderNum = ListOrders[gridOrders.getSelectedIndex()].SupplyOrderNum;
        }
         
        tableOrderItems = SupplyOrderItems.getItemsForOrder(orderNum);
        gridItems.beginUpdate();
        gridItems.getColumns().Clear();
        //ODGridColumn col=new ODGridColumn(Lan.g(this,"Supplier"),120);
        //gridItems.Columns.Add(col);
        ODGridColumn col = new ODGridColumn(Lan.g(this,"Catalog #"),80);
        gridItems.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Description"),320);
        gridItems.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Qty"), 60, HorizontalAlignment.Center);
        col.setIsEditable(true);
        gridItems.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Price/Unit"), 70, HorizontalAlignment.Right);
        col.setIsEditable(true);
        gridItems.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Subtotal"), 70, HorizontalAlignment.Right);
        gridItems.getColumns().add(col);
        gridItems.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        double price = new double();
        int qty = new int();
        double subtotal = new double();
        double total = 0;
        boolean autocalcTotal = true;
        for (int i = 0;i < tableOrderItems.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            //if(gridOrders.GetSelectedIndex()==-1){
            //	row.Cells.Add("");
            //}
            //else{
            //	row.Cells.Add(Suppliers.GetName(ListSuppliers,ListOrders[gridOrders.GetSelectedIndex()].SupplierNum));
            //}
            row.getCells().Add(tableOrderItems.Rows[i]["CatalogNumber"].ToString());
            row.getCells().Add(tableOrderItems.Rows[i]["Descript"].ToString());
            qty = PIn.Int(tableOrderItems.Rows[i]["Qty"].ToString());
            row.getCells().Add(qty.ToString());
            price = PIn.Double(tableOrderItems.Rows[i]["Price"].ToString());
            row.getCells().Add(price.ToString("n"));
            subtotal = ((double)qty) * price;
            row.getCells().Add(subtotal.ToString("n"));
            gridItems.getRows().add(row);
            if (subtotal == 0)
            {
                autocalcTotal = false;
            }
             
            total += subtotal;
        }
        gridItems.endUpdate();
        if (gridOrders.getSelectedIndex() != -1 && autocalcTotal && total != ListOrders[gridOrders.getSelectedIndex()].AmountTotal)
        {
            SupplyOrder order = ListOrders[gridOrders.getSelectedIndex()].Copy();
            order.AmountTotal = total;
            SupplyOrders.update(order);
            fillGridOrders();
            for (int i = 0;i < ListOrders.Count;i++)
            {
                if (ListOrders[i].SupplyOrderNum == order.SupplyOrderNum)
                {
                    gridOrders.setSelected(i,true);
                }
                 
            }
        }
         
    }

    private void gridOrderItem_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormSupplyOrderItemEdit FormSOIE = new FormSupplyOrderItemEdit();
        FormSOIE.ItemCur = SupplyOrderItems.createObject((long)tableOrderItems.Rows[e.getRow()]["SupplyOrderItemNum"]);
        FormSOIE.ListSupplier = Suppliers.createObjects();
        FormSOIE.ShowDialog();
        if (FormSOIE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        SupplyOrderItems.update(FormSOIE.ItemCur);
        ListOrdersAll = SupplyOrders.getAll();
        //force refresh because total might have changed.
        int gridSelect = gridOrders.getSelectedIndices()[0];
        fillGridOrders();
        gridOrders.setSelected(gridSelect,true);
        fillGridOrderItem();
    }

    /**
    * Used to update subtotal when qty or price are edited.
    */
    private void calculateSubtotalHelper() throws Exception {
        try
        {
            gridItems.getRows()[gridItems.getSelectedCell().Y].ColorBackG = Color.White;
            if (gridItems.getSelectedCell().X == 2)
            {
                //Qty
                int qty = Int32.Parse(gridItems.getRows()[gridItems.getSelectedCell().Y].Cells[gridItems.getSelectedCell().X].Text);
                gridItems.getRows()[gridItems.getSelectedCell().Y].Cells[4].Text = (qty * PIn.Double(gridItems.getRows()[gridItems.getSelectedCell().Y].Cells[3].Text)).ToString("n");
            }
             
            if (gridItems.getSelectedCell().X == 3)
            {
                //Price
                double price = Double.Parse(gridItems.getRows()[gridItems.getSelectedCell().Y].Cells[gridItems.getSelectedCell().X].Text);
                gridItems.getRows()[gridItems.getSelectedCell().Y].Cells[4].Text = (price * PIn.Int(gridItems.getRows()[gridItems.getSelectedCell().Y].Cells[2].Text)).ToString("n");
            }
             
            Application.DoEvents();
        }
        catch (Exception ex)
        {
            //save changes to order item on cell leave
            //problem calculating or parsing amount.
            gridItems.getRows()[gridItems.getSelectedCell().Y].ColorBackG = Color.LightPink;
            gridItems.getRows()[gridItems.getSelectedCell().Y].Cells[4].Text = 0.ToString("n");
        }
    
    }

    private void butNewOrder_Click(Object sender, EventArgs e) throws Exception {
        if (comboSupplier.SelectedIndex < 1)
        {
            //Includes no items or the ALL supplier being selected.
            MsgBox.show(this,"Please select a supplier first.");
            return ;
        }
         
        for (int i = 0;i < ListOrders.Count;i++)
        {
            if (ListOrders[i].DatePlaced.Year > 2200)
            {
                MsgBox.show(this,"Not allowed to add a new order when there is already one pending.  Please finish the other order instead.");
                return ;
            }
             
        }
        SupplyOrder order = new SupplyOrder();
        if (comboSupplier.SelectedIndex == 0)
        {
            //Supplier "All".
            order.SupplierNum = 0;
        }
        else
        {
            //Specific supplier selected.
            order.SupplierNum = ListSuppliers[comboSupplier.SelectedIndex - 1].SupplierNum;
        } 
        //SelectedIndex-1 because "All" is first option.
        order.setIsNew(true);
        order.DatePlaced = new DateTime(2500, 1, 1);
        order.Note = "";
        SupplyOrders.insert(order);
        ListOrdersAll = SupplyOrders.getAll();
        //Refresh the list all.
        fillGridOrders();
        gridOrders.SetSelected(ListOrders.Count - 1, true);
        gridOrders.scrollToEnd();
        fillGridOrderItem();
    }

    private void butPrint_Click(Object sender, EventArgs e) throws Exception {
        if (tableOrderItems.Rows.Count < 1)
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
         
        if (PrinterL.SetPrinter(pd2, PrintSituation.Default, 0, "Supplies order from " + ListOrders[gridOrders.getSelectedIndex()].DatePlaced.ToShortDateString() + " printed"))
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
            text = Lan.g(this,"Order Number") + ": " + ListOrders[gridOrders.getSelectedIndices()[0]].SupplyOrderNum;
            g.DrawString(text, subHeadingFont, Brushes.Black, 425 - g.MeasureString(text, subHeadingFont).Width / 2, yPos);
            yPos += (int)g.MeasureString(text, subHeadingFont).Height;
            text = Lan.g(this,"Date") + ": " + ListOrders[gridOrders.getSelectedIndices()[0]].DatePlaced.ToShortDateString();
            g.DrawString(text, subHeadingFont, Brushes.Black, 425 - g.MeasureString(text, subHeadingFont).Width / 2, yPos);
            yPos += (int)g.MeasureString(text, subHeadingFont).Height;
            Supplier supCur = Suppliers.GetOne(ListOrders[gridOrders.getSelectedIndices()[0]].SupplierNum);
            text = supCur.Name;
            g.DrawString(text, subHeadingFont, Brushes.Black, 425 - g.MeasureString(text, subHeadingFont).Width / 2, yPos);
            yPos += (int)g.MeasureString(text, subHeadingFont).Height;
            text = supCur.Phone;
            g.DrawString(text, subHeadingFont, Brushes.Black, 425 - g.MeasureString(text, subHeadingFont).Width / 2, yPos);
            yPos += (int)g.MeasureString(text, subHeadingFont).Height;
            text = supCur.Note;
            g.DrawString(text, subHeadingFont, Brushes.Black, 425 - g.MeasureString(text, subHeadingFont).Width / 2, yPos);
            yPos += (int)g.MeasureString(text, subHeadingFont).Height;
            yPos += 15;
            headingPrinted = true;
            headingPrintH = yPos;
        }
         
        yPos = gridItems.printPage(g,pagesPrinted,bounds,headingPrintH);
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

    /**
    * Save changes to orderItems based on input in grid.
    */
    //private bool saveChangesHelper() {
    //	if(gridItems.Rows.Count==0) {
    //		return true;
    //	}
    //	//validate ------------------------------------------------------------------------
    //	for(int i=0;i<gridItems.Rows.Count;i++) {
    //		int qtyThisRow=0;
    //		double priceThisRow=0;
    //		if(gridItems.Rows[i].Cells[2].Text!=""){
    //			try{
    //					qtyThisRow=Int32.Parse(gridItems.Rows[i].Cells[2].Text);
    //			}
    //			catch{
    //				MsgBox.Show(this,"Please fix errors in Qty column first.");
    //				return false;
    //			}
    //		}
    //		if(gridItems.Rows[i].Cells[3].Text!=""){
    //			try{
    //					priceThisRow=double.Parse(gridItems.Rows[i].Cells[3].Text);
    //			}
    //			catch{
    //				MsgBox.Show(this,"Please fix errors in Price column first.");
    //				return false;
    //			}
    //		}
    //	}
    //	//Save changes---------------------------------------------------------------------------
    //	//List<SupplyOrderItem> listOrderItems=OpenDentBusiness.Crud.SupplyOrderItemCrud.TableToList(tableOrderItems);//turn table into list of supplyOrderItem objects
    //	for(int i=0;i<gridItems.Rows.Count;i++) {
    //		int qtyThisRow=PIn.Int(gridItems.Rows[i].Cells[2].Text);//already validated
    //		double priceThisRow=PIn.Double(gridItems.Rows[i].Cells[3].Text);//already validated
    //		if(qtyThisRow==PIn.Int(tableOrderItems.Rows[i]["Qty"].ToString())
    //			&& priceThisRow==PIn.Double(tableOrderItems.Rows[i]["Price"].ToString()))
    //		{
    //			continue;//no changes to order item.
    //		}
    //		SupplyOrderItem soi=new SupplyOrderItem();
    //		soi.SupplyNum=PIn.Long(tableOrderItems.Rows[i]["SupplyNum"].ToString());
    //		soi.SupplyOrderItemNum=PIn.Long(tableOrderItems.Rows[i]["SupplyOrderItemNum"].ToString());
    //		soi.SupplyOrderNum=ListOrders[gridOrders.GetSelectedIndex()].SupplyOrderNum;
    //		soi.Qty=qtyThisRow;
    //		soi.Price=priceThisRow;
    //		SupplyOrderItems.Update(soi);
    //	}//end gridItems
    //	SupplyOrders.UpdateOrderPrice(ListOrders[gridOrders.GetSelectedIndex()].SupplyOrderNum);
    //	int selectedIndex=gridOrders.GetSelectedIndex();
    //	ListOrdersAll = SupplyOrders.GetAll();//update new totals
    //	FillGridOrders();
    //	if(selectedIndex!=-1) {
    //		gridOrders.SetSelected(selectedIndex,true);
    //	}
    //	return true;
    //}
    private void gridItems_CellLeave(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        //no need to check which cell was edited, just reprocess both cells
        int qtyNew = 0;
        try
        {
            //default value.
            qtyNew = PIn.Int(gridItems.getRows().get___idx(e.getRow()).getCells()[2].Text);
        }
        catch (Exception __dummyCatchVar1)
        {
        }

        //0 if not valid input
        double priceNew = PIn.Double(gridItems.getRows().get___idx(e.getRow()).getCells()[3].Text);
        //0 if not valid input
        SupplyOrderItem suppOI = SupplyOrderItems.CreateObject(PIn.Long(tableOrderItems.Rows[e.getRow()]["SupplyOrderItemNum"].ToString()));
        suppOI.Qty = qtyNew;
        suppOI.Price = priceNew;
        SupplyOrderItems.update(suppOI);
        SupplyOrders.updateOrderPrice(suppOI.SupplyOrderNum);
        gridItems.getRows().get___idx(e.getRow()).getCells()[2].Text = qtyNew.ToString();
        //to standardize formatting.  They probably didn't type .00
        gridItems.getRows().get___idx(e.getRow()).getCells()[3].Text = priceNew.ToString("n");
        //to standardize formatting.  They probably didn't type .00
        gridItems.getRows().get___idx(e.getRow()).getCells()[4].Text = (qtyNew * priceNew).ToString("n");
        //to standardize formatting.  They probably didn't type .00
        gridItems.Invalidate();
        int si = gridOrders.getSelectedIndex();
        ListOrdersAll = SupplyOrders.getAll();
        fillGridOrders();
        gridOrders.setSelected(si,true);
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        //maybe rename to close, since most saving happens automatically.
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
        this.comboSupplier = new System.Windows.Forms.ComboBox();
        this.label3 = new System.Windows.Forms.Label();
        this.gridItems = new OpenDental.UI.ODGrid();
        this.gridOrders = new OpenDental.UI.ODGrid();
        this.butPrint = new OpenDental.UI.Button();
        this.butAddSupply = new OpenDental.UI.Button();
        this.butNewOrder = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // comboSupplier
        //
        this.comboSupplier.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboSupplier.FormattingEnabled = true;
        this.comboSupplier.Location = new System.Drawing.Point(295, 10);
        this.comboSupplier.Name = "comboSupplier";
        this.comboSupplier.Size = new System.Drawing.Size(144, 21);
        this.comboSupplier.TabIndex = 13;
        this.comboSupplier.SelectedIndexChanged += new System.EventHandler(this.comboSupplier_SelectedIndexChanged);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(218, 10);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(75, 18);
        this.label3.TabIndex = 14;
        this.label3.Text = "Supplier";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // gridItems
        //
        this.gridItems.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridItems.setHScrollVisible(false);
        this.gridItems.Location = new System.Drawing.Point(12, 227);
        this.gridItems.Name = "gridItems";
        this.gridItems.setScrollValue(0);
        this.gridItems.setSelectionMode(OpenDental.UI.GridSelectionMode.OneCell);
        this.gridItems.Size = new System.Drawing.Size(705, 432);
        this.gridItems.TabIndex = 17;
        this.gridItems.setTitle("Supplies on one Order");
        this.gridItems.setTranslationName(null);
        this.gridItems.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridItems.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridOrderItem_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridItems.CellLeave = __MultiODGridClickEventHandler.combine(this.gridItems.CellLeave,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridItems_CellLeave(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // gridOrders
        //
        this.gridOrders.setHScrollVisible(false);
        this.gridOrders.Location = new System.Drawing.Point(12, 37);
        this.gridOrders.Name = "gridOrders";
        this.gridOrders.setScrollValue(0);
        this.gridOrders.Size = new System.Drawing.Size(427, 184);
        this.gridOrders.TabIndex = 15;
        this.gridOrders.setTitle("Order History");
        this.gridOrders.setTranslationName(null);
        this.gridOrders.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridOrders.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridOrder_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridOrders.CellClick = __MultiODGridClickEventHandler.combine(this.gridOrders.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridOrder_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butPrint
        //
        this.butPrint.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrint.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butPrint.setAutosize(true);
        this.butPrint.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrint.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrint.setCornerRadius(4F);
        this.butPrint.Image = Resources.getbutPrint();
        this.butPrint.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrint.Location = new System.Drawing.Point(322, 664);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(80, 26);
        this.butPrint.TabIndex = 26;
        this.butPrint.Text = "Print";
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // butAddSupply
        //
        this.butAddSupply.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddSupply.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butAddSupply.setAutosize(true);
        this.butAddSupply.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddSupply.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddSupply.setCornerRadius(4F);
        this.butAddSupply.Image = Resources.getAdd();
        this.butAddSupply.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddSupply.Location = new System.Drawing.Point(648, 197);
        this.butAddSupply.Name = "butAddSupply";
        this.butAddSupply.Size = new System.Drawing.Size(69, 24);
        this.butAddSupply.TabIndex = 25;
        this.butAddSupply.Text = "Add";
        this.butAddSupply.Click += new System.EventHandler(this.butAddSupply_Click);
        //
        // butNewOrder
        //
        this.butNewOrder.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNewOrder.setAutosize(true);
        this.butNewOrder.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNewOrder.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNewOrder.setCornerRadius(4F);
        this.butNewOrder.Image = Resources.getAdd();
        this.butNewOrder.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butNewOrder.Location = new System.Drawing.Point(12, 7);
        this.butNewOrder.Name = "butNewOrder";
        this.butNewOrder.Size = new System.Drawing.Size(95, 24);
        this.butNewOrder.TabIndex = 16;
        this.butNewOrder.Text = "New Order";
        this.butNewOrder.Click += new System.EventHandler(this.butNewOrder_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(561, 665);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
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
        this.butCancel.Location = new System.Drawing.Point(642, 665);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormSupplyOrders
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(725, 696);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.butAddSupply);
        this.Controls.Add(this.gridItems);
        this.Controls.Add(this.butNewOrder);
        this.Controls.Add(this.gridOrders);
        this.Controls.Add(this.comboSupplier);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormSupplyOrders";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Supply Orders";
        this.Load += new System.EventHandler(this.FormSupplyOrders_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butNewOrder;
    private OpenDental.UI.ODGrid gridOrders;
    private System.Windows.Forms.ComboBox comboSupplier = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private OpenDental.UI.ODGrid gridItems;
    private OpenDental.UI.Button butAddSupply;
    private OpenDental.UI.Button butPrint;
}


