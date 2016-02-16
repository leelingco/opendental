//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:26 PM
//

package OpenDental;

import OpenDental.ContrChart;
import OpenDentBusiness.ProgramC;
import OpenDentBusiness.Programs;

public class ChartLayoutHelper   
{
    /**
    * This reduces the number of places where Programs.UsingEcwTight() is called.  This helps with organization.  All calls within this class must pass through here.
    */
    private static boolean usingEcwTightOrFull() throws Exception {
        return Programs.usingEcwTightOrFullMode();
    }

    public static void resize(OpenDental.UI.ODGrid gridProg, Panel panelImages, Panel panelEcw, TabControl tabControlImages, Size ClientSize, OpenDental.UI.ODGrid gridPtInfo, SparksToothChart.ToothChartWrapper toothChart, RichTextBox textTreatmentNotes) throws Exception {
        if (ProgramC.hListIsNull())
        {
            return ;
        }
         
        if (usingEcwTightOrFull())
        {
            //gridProg.Width=524;
            if (gridProg.getColumns() != null && gridProg.getColumns().Count > 0)
            {
                int gridW = 0;
                for (int i = 0;i < gridProg.getColumns().Count;i++)
                {
                    gridW += gridProg.getColumns().get___idx(i).getColWidth();
                }
                if (gridW < 524)
                {
                    //for example, if not very many columns
                    gridW = 524;
                }
                 
                if (gridW + 20 + toothChart.Width < ClientSize.Width)
                {
                    //if space is big enough to allow full width
                    gridProg.Width = gridW + 20;
                }
                else
                {
                    if (ClientSize.Width > 0)
                    {
                        //prevents an error
                        if (ClientSize.Width - toothChart.Width - 1 < 524)
                        {
                            gridProg.Width = 524;
                        }
                        else
                        {
                            gridProg.Width = ClientSize.Width - toothChart.Width - 1;
                        } 
                    }
                     
                } 
                //now, bump the other controls over
                toothChart.Location = new Point(gridProg.Width + 2, 26);
                textTreatmentNotes.Location = new Point(gridProg.Width + 2, toothChart.Bottom + 1);
                panelEcw.Location = new Point(gridProg.Width + 2, textTreatmentNotes.Bottom + 1);
            }
             
            if (panelImages.Visible)
            {
                panelEcw.Height = tabControlImages.Top - panelEcw.Top + 1 - (panelImages.Height + 2);
            }
            else
            {
                panelEcw.Height = tabControlImages.Top - panelEcw.Top + 1;
            } 
            return ;
        }
         
        if (Programs.getUsingOrion())
        {
            //full width
            gridProg.Width = ClientSize.Width - gridProg.Location.X - 1;
        }
        else if (gridProg.getColumns() != null && gridProg.getColumns().Count > 0)
        {
            int gridW = 0;
            for (int i = 0;i < gridProg.getColumns().Count;i++)
            {
                gridW += gridProg.getColumns().get___idx(i).getColWidth();
            }
            if (gridProg.Location.X + gridW + 20 < ClientSize.Width)
            {
                //if space is big enough to allow full width
                gridProg.Width = gridW + 20;
            }
            else
            {
                if (ClientSize.Width > gridProg.Location.X)
                {
                    //prevents an error
                    gridProg.Width = ClientSize.Width - gridProg.Location.X - 1;
                }
                 
            } 
        }
          
        if (Programs.getUsingOrion())
        {
        }
        else
        {
            //gridPtInfo is up in the tabs and does not need to be resized.
            gridPtInfo.Height = tabControlImages.Top - gridPtInfo.Top;
        } 
    }

    public static void initializeOnStartup(ContrChart contrChart, TabControl tabProc, OpenDental.UI.ODGrid gridProg, Panel panelEcw, TabControl tabControlImages, Size ClientSize, OpenDental.UI.ODGrid gridPtInfo, SparksToothChart.ToothChartWrapper toothChart, RichTextBox textTreatmentNotes, OpenDental.UI.Button butECWup, OpenDental.UI.Button butECWdown, TabPage tabPatInfo) throws Exception {
        tabProc.SelectedIndex = 0;
        tabProc.Height = 259;
        if (usingEcwTightOrFull())
        {
            toothChart.Location = new Point(524 + 2, 26);
            textTreatmentNotes.Location = new Point(524 + 2, toothChart.Bottom + 1);
            textTreatmentNotes.Size = new Size(411, 40);
            //make it a bit smaller than usual
            gridPtInfo.Visible = false;
            panelEcw.Visible = true;
            panelEcw.Location = new Point(524 + 2, textTreatmentNotes.Bottom + 1);
            panelEcw.Size = new Size(411, tabControlImages.Top - panelEcw.Top + 1);
            butECWdown.Location = butECWup.Location;
            //they will be at the same location, but just hide one or the other.
            butECWdown.Visible = false;
            tabProc.Location = new Point(0, 28);
            gridProg.Location = new Point(0, tabProc.Bottom + 2);
            gridProg.Height = ClientSize.Height - gridProg.Location.Y - 2;
        }
        else
        {
            //normal:
            toothChart.Location = new Point(0, 26);
            textTreatmentNotes.Location = new Point(0, toothChart.Bottom + 1);
            textTreatmentNotes.Size = new Size(411, 71);
            gridPtInfo.Visible = true;
            gridPtInfo.Location = new Point(0, textTreatmentNotes.Bottom + 1);
            panelEcw.Visible = false;
            tabProc.Location = new Point(415, 28);
            gridProg.Location = new Point(415, tabProc.Bottom + 2);
            gridProg.Height = ClientSize.Height - gridProg.Location.Y - 2;
        } 
        if (Programs.getUsingOrion())
        {
            textTreatmentNotes.Visible = false;
            contrChart.Controls.Remove(gridPtInfo);
            gridPtInfo.Visible = true;
            gridPtInfo.Location = new Point(0, 0);
            gridPtInfo.Size = new Size(tabPatInfo.ClientSize.Width, tabPatInfo.ClientSize.Height);
            gridPtInfo.Anchor = AnchorStyles.Left | AnchorStyles.Right | AnchorStyles.Top | AnchorStyles.Bottom;
            tabPatInfo.Controls.Add(gridPtInfo);
            tabProc.SelectedTab = tabPatInfo;
            tabProc.Height = toothChart.Height - 1;
            gridProg.Location = new Point(0, toothChart.Bottom + 2);
            gridProg.setHScrollVisible(true);
            gridProg.Height = ClientSize.Height - gridProg.Location.Y - 2;
            gridProg.Width = ClientSize.Width - gridProg.Location.X - 1;
        }
        else
        {
            //full width
            tabProc.TabPages.Remove(tabPatInfo);
        } 
    }

    public static void setGridProgWidth(OpenDental.UI.ODGrid gridProg, Size ClientSize, Panel panelEcw, RichTextBox textTreatmentNotes, SparksToothChart.ToothChartWrapper toothChart) throws Exception {
        if (usingEcwTightOrFull())
        {
            //gridProg.Width=524;
            if (gridProg.getColumns() != null && gridProg.getColumns().Count > 0)
            {
                int gridW = 0;
                for (int i = 0;i < gridProg.getColumns().Count;i++)
                {
                    gridW += gridProg.getColumns().get___idx(i).getColWidth();
                }
                if (gridW < 524)
                {
                    //for example, if not very many columns
                    gridW = 524;
                }
                 
                if (gridW + 20 + toothChart.Width < ClientSize.Width)
                {
                    //if space is big enough to allow full width
                    gridProg.Width = gridW + 20;
                }
                else
                {
                    if (ClientSize.Width > 0)
                    {
                        //prevents an error
                        if (ClientSize.Width - toothChart.Width - 1 < 524)
                        {
                            gridProg.Width = 524;
                        }
                        else
                        {
                            gridProg.Width = ClientSize.Width - toothChart.Width - 1;
                        } 
                    }
                     
                } 
                //now, bump the other controls over
                toothChart.Location = new Point(gridProg.Width + 2, 26);
                textTreatmentNotes.Location = new Point(gridProg.Width + 2, toothChart.Bottom + 1);
                panelEcw.Location = new Point(gridProg.Width + 2, textTreatmentNotes.Bottom + 1);
            }
            else
            {
                gridProg.Width = 524;
            } 
            return ;
        }
         
        if (Programs.getUsingOrion())
        {
            //full width
            gridProg.Width = ClientSize.Width - gridProg.Location.X - 1;
        }
        else if (gridProg.getColumns() != null && gridProg.getColumns().Count > 0)
        {
            int gridW = 0;
            for (int i = 0;i < gridProg.getColumns().Count;i++)
            {
                gridW += gridProg.getColumns().get___idx(i).getColWidth();
            }
            if (gridProg.Location.X + gridW + 20 < ClientSize.Width)
            {
                //if space is big enough to allow full width
                gridProg.Width = gridW + 20;
            }
            else
            {
                gridProg.Width = ClientSize.Width - gridProg.Location.X - 1;
            } 
        }
          
    }

    public static void tabProc_MouseDown(int SelectedProcTab, OpenDental.UI.ODGrid gridProg, TabControl tabProc, Size ClientSize, MouseEventArgs e) throws Exception {
        if (Programs.getUsingOrion())
        {
            return ;
        }
         
        //tabs never minimize
        //selected tab will have changed, so we need to test the original selected tab:
        Rectangle rect = tabProc.GetTabRect(SelectedProcTab);
        if (rect.Contains(e.X, e.Y) && tabProc.Height > 27)
        {
            //clicked on the already selected tab which was maximized
            tabProc.Height = 27;
            tabProc.Refresh();
            gridProg.Location = new Point(tabProc.Left, tabProc.Bottom + 1);
            gridProg.Height = ClientSize.Height - gridProg.Location.Y - 2;
        }
        else if (tabProc.Height == 27)
        {
            //clicked on a minimized tab
            tabProc.Height = 259;
            tabProc.Refresh();
            gridProg.Location = new Point(tabProc.Left, tabProc.Bottom + 1);
            gridProg.Height = ClientSize.Height - gridProg.Location.Y - 2;
        }
        else
        {
        }  
        //clicked on a new tab
        //height will have already been set, so do nothing
        SelectedProcTab = tabProc.SelectedIndex;
    }

    public static void gridPtInfoSetSize(OpenDental.UI.ODGrid gridPtInfo, TabControl tabControlImages) throws Exception {
        if (!Programs.getUsingOrion())
        {
            gridPtInfo.Height = tabControlImages.Top - gridPtInfo.Top;
        }
         
    }

}


//public enum ChartLayoutMode