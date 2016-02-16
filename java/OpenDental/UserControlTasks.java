//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.DataValid;
import OpenDental.DateTimeOD;
import OpenDental.FormTaskEdit;
import OpenDental.FormTaskListEdit;
import OpenDental.FormTaskSetup;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.Appointments;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.Security;
import OpenDentBusiness.Task;
import OpenDentBusiness.TaskAncestors;
import OpenDentBusiness.TaskDateType;
import OpenDentBusiness.TaskList;
import OpenDentBusiness.TaskLists;
import OpenDentBusiness.TaskNote;
import OpenDentBusiness.TaskNotes;
import OpenDentBusiness.TaskObjectType;
import OpenDentBusiness.Tasks;
import OpenDentBusiness.TaskStatusEnum;
import OpenDentBusiness.TaskSubscriptions;
import OpenDentBusiness.TaskUnreads;
import OpenDentBusiness.Userods;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.__MultiODToolBarButtonClickEventHandler;

public class UserControlTasks  extends UserControl 
{

    /**
    * List of all TastLists that are to be displayed in the main window. Combine with TasksList.
    */
    private List<TaskList> TaskListsList = new List<TaskList>();
    /**
    * List of all Tasks that are to be displayed in the main window.  Combine with TaskListsList.
    */
    private List<Task> TasksList = new List<Task>();
    //<Summary>Only used if viewing user tab.  This is a list of all task lists in the general tab.  It is used to generate full path heirarchy info for each task list in the user tab.</Summary>
    //private List<TaskList> TaskListsAllGeneral;
    /**
    * An arraylist of TaskLists beginning from the trunk and adding on branches.  If the count is 0, then we are in the trunk of one of the five categories.  The last TaskList in the TreeHistory is the one that is open in the main window.
    */
    private List<TaskList> TreeHistory = new List<TaskList>();
    /**
    * A TaskList that is on the 'clipboard' waiting to be pasted.  Will be null if nothing has been copied yet.
    */
    private TaskList ClipTaskList;
    /**
    * A Task that is on the 'clipboard' waiting to be pasted.  Will be null if nothing has been copied yet.
    */
    private Task ClipTask;
    /**
    * If there is an item on our 'clipboard', this tracks whether it was cut.
    */
    private boolean WasCut = new boolean();
    /**
    * The index of the last clicked item in the main list.
    */
    private int clickedI = new int();
    /**
    * After closing, if this is not zero, then it will jump to the object specified in GotoKeyNum.
    */
    public TaskObjectType GotoType = TaskObjectType.None;
    /**
    * After closing, if this is not zero, then it will jump to the specified patient.
    */
    public long GotoKeyNum = new long();
    /**
    * 
    */
    public EventHandler GoToChanged = null;
    /**
    * All notes for the showing tasks, ordered by date time.
    */
    private List<TaskNote> TaskNoteList = new List<TaskNote>();
    private static final int _TriageListNum = 1697;
    public UserControlTasks() throws Exception {
        initializeComponent();
        for (int i = 0;i < menuEdit.MenuItems.Count;i++)
        {
            //this.listMain.ContextMenu = this.menuEdit;
            //Lan.F(this);
            Lan.C(this, menuEdit.MenuItems[i]);
        }
        this.gridMain.ContextMenu = this.menuEdit;
    }

    /**
    * The parent might call this if it gets a signal that a relevant task was added from another workstation.  The parent should only call this if it has been verified that there is a change to tasks.
    */
    public void refreshTasks() throws Exception {
        fillGrid();
    }

    protected void onGoToChanged() throws Exception {
        if (GoToChanged != null)
        {
            GoToChanged(this, new EventArgs());
        }
         
    }

    /**
    * And resets the tabs if the user changes.
    */
    public void initializeOnStartup() throws Exception {
        if (Security.getCurUser() == null)
        {
            return ;
        }
         
        tabUser.Text = Lan.g(this,"for ") + Security.getCurUser().UserName;
        tabNew.Text = Lan.g(this,"New for ") + Security.getCurUser().UserName;
        if (PrefC.getBool(PrefName.TasksShowOpenTickets))
        {
            if (!tabContr.TabPages.Contains(tabOpenTickets))
            {
                tabContr.TabPages.Insert(2, tabOpenTickets);
            }
             
        }
        else
        {
            if (tabContr.TabPages.Contains(tabOpenTickets))
            {
                tabContr.TabPages.Remove(tabOpenTickets);
            }
             
        } 
        layoutToolBar();
        if (Tasks.LastOpenList == null)
        {
            //first time openning
            TreeHistory = new List<TaskList>();
            cal.SelectionStart = DateTimeOD.getToday();
        }
        else
        {
            //reopening
            tabContr.SelectedIndex = Tasks.LastOpenGroup;
            TreeHistory = new List<TaskList>();
            //for(int i=0;i<Tasks.LastOpenList.Count;i++) {
            //	TreeHistory.Add(((TaskList)Tasks.LastOpenList[i]).Copy());
            //}
            cal.SelectionStart = Tasks.LastOpenDate;
        } 
        fillTree();
        fillGrid();
        if (tabContr.SelectedTab != tabOpenTickets)
        {
            //because it will have alread been set
            setOpenTicketTab(-1);
        }
         
        setMenusEnabled();
    }

    /**
    * Called whenever OpenTicket tab is refreshed to set the count at the top.  Also called from InitializeOnStartup.  In that case, we don't know what the count should be, so we pass in a -1.
    */
    private void setOpenTicketTab(int countSet) throws Exception {
        if (!tabContr.TabPages.Contains(tabOpenTickets))
        {
            return ;
        }
         
        if (countSet == -1)
        {
            countSet = Tasks.getCountOpenTickets(Security.getCurUser().UserNum);
        }
         
        tabOpenTickets.Text = Lan.g(this,"Open Tickets") + " (" + countSet.ToString() + ")";
    }

    public void clearLogOff() throws Exception {
        tabUser.Text = "for";
        tabNew.Text = "New for";
        TreeHistory = new List<TaskList>();
        fillTree();
        gridMain.getRows().Clear();
        gridMain.Invalidate();
    }

    /**
    * Used by OD HQ.
    */
    public void fillGridWithTriageList() throws Exception {
        TaskList tlOne = TaskLists.GetOne(_TriageListNum);
        tabContr.SelectedTab = tabMain;
        if (TreeHistory == null)
        {
            TreeHistory = new List<TaskList>();
        }
         
        TreeHistory.Clear();
        TreeHistory.Add(tlOne);
        if (TaskListsList == null)
        {
            TaskListsList = new List<TaskList>();
        }
         
        TaskListsList.Clear();
        if (TasksList == null)
        {
            TasksList = new List<Task>();
        }
         
        TasksList.Clear();
        TasksList = Tasks.RefreshChildren(_TriageListNum, false, cal.SelectionStart, Security.getCurUser().UserNum, 0);
        fillTree();
        fillGrid();
    }

    private void userControlTasks_Load(Object sender, System.EventArgs e) throws Exception {
        if (this.DesignMode)
        {
            return ;
        }
         
        if (!PrefC.getBool(PrefName.TaskAncestorsAllSetInVersion55))
        {
            if (!MsgBox.show(this,true,"A one-time routine needs to be run.  It will take a few minutes.  Do you have time right now?"))
            {
                return ;
            }
             
            Cursor = Cursors.WaitCursor;
            TaskAncestors.synchAll();
            Prefs.updateBool(PrefName.TaskAncestorsAllSetInVersion55,true);
            DataValid.setInvalid(InvalidType.Prefs);
            Cursor = Cursors.Default;
        }
         
    }

    /**
    * 
    */
    public void layoutToolBar() throws Exception {
        ToolBarMain.getButtons().Clear();
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Setup"), -1, "", "Setup"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Add TaskList"), 0, "", "AddList"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Add Task"), 1, "", "AddTask"));
        OpenDental.UI.ODToolBarButton button = new OpenDental.UI.ODToolBarButton();
        button.setStyle(OpenDental.UI.ODToolBarButtonStyle.ToggleButton);
        button.setText(Lan.g(this,"BlockPopups"));
        button.setToolTipText(Lan.g(this,"Sounds will still play, but popups will be blocked."));
        button.setTag("Block");
        button.setPushed(Security.getCurUser().DefaultHidePopups);
        ToolBarMain.getButtons().add(button);
        ToolBarMain.Invalidate();
    }

    private void fillTree() throws Exception {
        tree.Nodes.Clear();
        TreeNode node = new TreeNode();
        //TreeNode lastNode=null;
        String nodedesc = new String();
        for (int i = 0;i < TreeHistory.Count;i++)
        {
            nodedesc = TreeHistory[i].Descript;
            if (tabContr.SelectedTab == tabUser)
            {
                nodedesc = TreeHistory[i].ParentDesc + nodedesc;
            }
             
            node = new TreeNode(nodedesc);
            node.Tag = TreeHistory[i].TaskListNum;
            if (tree.SelectedNode == null)
            {
                tree.Nodes.Add(node);
            }
            else
            {
                tree.SelectedNode.Nodes.Add(node);
            } 
            tree.SelectedNode = node;
        }
        //remember this position for the next time we open tasks
        Tasks.LastOpenList = new ArrayList();
        for (int i = 0;i < TreeHistory.Count;i++)
        {
            Tasks.LastOpenList.Add(TreeHistory[i].Copy());
        }
        Tasks.LastOpenGroup = tabContr.SelectedIndex;
        Tasks.LastOpenDate = cal.SelectionStart;
        //layout
        if (tabContr.SelectedTab == tabUser)
        {
            tree.Top = tabContr.Bottom;
        }
        else if (tabContr.SelectedTab == tabNew)
        {
            tree.Top = tabContr.Bottom;
        }
        else if (tabContr.SelectedTab == tabMain)
        {
            tree.Top = tabContr.Bottom;
        }
        else if (tabContr.SelectedTab == tabRepeating)
        {
            tree.Top = tabContr.Bottom;
        }
        else if (tabContr.SelectedTab == tabDate || tabContr.SelectedTab == tabWeek || tabContr.SelectedTab == tabMonth)
        {
            tree.Top = cal.Bottom + 1;
        }
             
        tree.Height = TreeHistory.Count * tree.ItemHeight + 8;
        tree.Refresh();
        gridMain.Top = tree.Bottom;
        checkShowFinished.Top = gridMain.Top + 1;
        textStartDate.Top = gridMain.Top;
        labelStartDate.Top = gridMain.Top + 1;
        gridMain.Height = this.ClientSize.Height - gridMain.Top - 3;
    }

    private void textStartDate_KeyPress(Object sender, KeyPressEventArgs e) throws Exception {
    }

    //FillGrid();
    private void textStartDate_TextChanged(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        if (Security.getCurUser() == null)
        {
            gridMain.beginUpdate();
            gridMain.getRows().Clear();
            gridMain.endUpdate();
            return ;
        }
         
        long parent = new long();
        DateTime date = new DateTime();
        if (TreeHistory == null)
        {
            return ;
        }
         
        if (TreeHistory.Count > 0)
        {
            //not on main trunk
            parent = TreeHistory[TreeHistory.Count - 1].TaskListNum;
            date = DateTime.MinValue;
        }
        else
        {
            //one of the main trunks
            parent = 0;
            date = cal.SelectionStart;
        } 
        refreshMainLists(parent,date);
        //dated trunk automation-----------------------------------------------------------------------------
        //main trunk
        if (TreeHistory.Count == 0 && (tabContr.SelectedTab == tabDate || tabContr.SelectedTab == tabWeek || tabContr.SelectedTab == tabMonth))
        {
            //clear any lists which are derived from a repeating list and which do not have any itmes checked off
            boolean changeMade = false;
            for (int i = 0;i < TaskListsList.Count;i++)
            {
                if (TaskListsList[i].FromNum == 0)
                {
                    continue;
                }
                 
                //ignore because not derived from a repeating list
                if (!AnyAreMarkedComplete(TaskListsList[i]))
                {
                    DeleteEntireList(TaskListsList[i]);
                    changeMade = true;
                }
                 
            }
            for (int i = 0;i < TasksList.Count;i++)
            {
                //clear any tasks which are derived from a repeating task
                //and which are still new (not marked viewed or done)
                if (TasksList[i].FromNum == 0)
                {
                    continue;
                }
                 
                if (TasksList[i].TaskStatus == TaskStatusEnum.New)
                {
                    Tasks.Delete(TasksList[i].TaskNum);
                    changeMade = true;
                }
                 
            }
            if (changeMade)
            {
                refreshMainLists(parent,date);
            }
             
            //now add back any repeating lists and tasks that meet the criteria
            //Get lists of all repeating lists and tasks of one type.  We will pick items from these two lists.
            List<TaskList> repeatingLists = new List<TaskList>();
            List<Task> repeatingTasks = new List<Task>();
            if (tabContr.SelectedTab == tabDate)
            {
                repeatingLists = TaskLists.refreshRepeating(TaskDateType.Day);
                repeatingTasks = Tasks.refreshRepeating(TaskDateType.Day,Security.getCurUser().UserNum);
            }
             
            if (tabContr.SelectedTab == tabWeek)
            {
                repeatingLists = TaskLists.refreshRepeating(TaskDateType.Week);
                repeatingTasks = Tasks.refreshRepeating(TaskDateType.Week,Security.getCurUser().UserNum);
            }
             
            if (tabContr.SelectedTab == tabMonth)
            {
                repeatingLists = TaskLists.refreshRepeating(TaskDateType.Month);
                repeatingTasks = Tasks.refreshRepeating(TaskDateType.Month,Security.getCurUser().UserNum);
            }
             
            //loop through list and add back any that meet criteria.
            changeMade = false;
            boolean alreadyExists = new boolean();
            for (int i = 0;i < repeatingLists.Count;i++)
            {
                //if already exists, skip
                alreadyExists = false;
                for (int j = 0;j < TaskListsList.Count;j++)
                {
                    //loop through Main list
                    if (TaskListsList[j].FromNum == repeatingLists[i].TaskListNum)
                    {
                        alreadyExists = true;
                        break;
                    }
                     
                }
                if (alreadyExists)
                {
                    continue;
                }
                 
                //otherwise, duplicate the list
                repeatingLists[i].DateTL = date;
                repeatingLists[i].FromNum = repeatingLists[i].TaskListNum;
                repeatingLists[i].IsRepeating = false;
                repeatingLists[i].Parent = 0;
                repeatingLists[i].ObjectType = 0;
                //user will have to set explicitly
                DuplicateExistingList(repeatingLists[i], null, true);
                //repeating lists cannot be subscribed to, so send null in as old list, will not attempt to move subscriptions
                changeMade = true;
            }
            for (int i = 0;i < repeatingTasks.Count;i++)
            {
                //if already exists, skip
                alreadyExists = false;
                for (int j = 0;j < TasksList.Count;j++)
                {
                    //loop through Main list
                    if (TasksList[j].FromNum == repeatingTasks[i].TaskNum)
                    {
                        alreadyExists = true;
                        break;
                    }
                     
                }
                if (alreadyExists)
                {
                    continue;
                }
                 
                //otherwise, duplicate the task
                repeatingTasks[i].DateTask = date;
                repeatingTasks[i].FromNum = repeatingTasks[i].TaskNum;
                repeatingTasks[i].IsRepeating = false;
                repeatingTasks[i].TaskListNum = 0;
                //repeatingTasks[i].UserNum//repeating tasks shouldn't get a usernum
                Tasks.Insert(repeatingTasks[i]);
                changeMade = true;
            }
            if (changeMade)
            {
                refreshMainLists(parent,date);
            }
             
        }
         
        //End of dated trunk automation--------------------------------------------------------------------------
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        OpenDental.UI.ODGridColumn col = new OpenDental.UI.ODGridColumn("",17);
        col.setImageList(imageListTree);
        gridMain.getColumns().add(col);
        if (tabContr.SelectedTab == tabNew && !PrefC.getBool(PrefName.TasksNewTrackedByUser))
        {
            //The old way
            col = new OpenDental.UI.ODGridColumn(Lan.g("TableTasks","Read"), 35, HorizontalAlignment.Center);
            //col.ImageList=imageListTree;
            gridMain.getColumns().add(col);
        }
         
        if (tabContr.SelectedTab == tabNew || tabContr.SelectedTab == tabOpenTickets)
        {
            col = new OpenDental.UI.ODGridColumn(Lan.g("TableTasks","Task List"),90);
            gridMain.getColumns().add(col);
        }
         
        col = new OpenDental.UI.ODGridColumn(Lan.g("TableTasks","Description"),200);
        //any width
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        String dateStr = "";
        String objDesc = "";
        String tasklistdescript = "";
        String notes = "";
        int imageindex = new int();
        for (int i = 0;i < TaskListsList.Count;i++)
        {
            dateStr = "";
            if (TaskListsList[i].DateTL.Year > 1880 && (tabContr.SelectedTab == tabUser || tabContr.SelectedTab == tabMain))
            {
                if (TaskListsList[i].DateType == TaskDateType.Day)
                {
                    dateStr = TaskListsList[i].DateTL.ToShortDateString() + " - ";
                }
                else if (TaskListsList[i].DateType == TaskDateType.Week)
                {
                    dateStr = Lan.g(this,"Week of") + " " + TaskListsList[i].DateTL.ToShortDateString() + " - ";
                }
                else if (TaskListsList[i].DateType == TaskDateType.Month)
                {
                    dateStr = TaskListsList[i].DateTL.ToString("MMMM") + " - ";
                }
                   
            }
             
            objDesc = "";
            if (tabContr.SelectedTab == tabUser)
            {
                objDesc = TaskListsList[i].ParentDesc;
            }
             
            tasklistdescript = TaskListsList[i].Descript;
            imageindex = 0;
            if (TaskListsList[i].NewTaskCount > 0)
            {
                imageindex = 3;
                //orange
                tasklistdescript = tasklistdescript + "(" + TaskListsList[i].NewTaskCount.ToString() + ")";
            }
             
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(imageindex.ToString());
            row.getCells().add(dateStr + objDesc + tasklistdescript);
            gridMain.getRows().add(row);
        }
        for (int i = 0;i < TasksList.Count;i++)
        {
            dateStr = "";
            if (tabContr.SelectedTab == tabUser || tabContr.SelectedTab == tabNew || tabContr.SelectedTab == tabOpenTickets || tabContr.SelectedTab == tabMain)
            {
                if (TasksList[i].DateTask.Year > 1880)
                {
                    if (TasksList[i].DateType == TaskDateType.Day)
                    {
                        dateStr += TasksList[i].DateTask.ToShortDateString() + " - ";
                    }
                    else if (TasksList[i].DateType == TaskDateType.Week)
                    {
                        dateStr += Lan.g(this,"Week of") + " " + TasksList[i].DateTask.ToShortDateString() + " - ";
                    }
                    else if (TasksList[i].DateType == TaskDateType.Month)
                    {
                        dateStr += TasksList[i].DateTask.ToString("MMMM") + " - ";
                    }
                       
                }
                else if (TasksList[i].DateTimeEntry.Year > 1880)
                {
                    dateStr += TasksList[i].DateTimeEntry.ToShortDateString() + " " + TasksList[i].DateTimeEntry.ToShortTimeString() + " - ";
                }
                  
            }
             
            objDesc = "";
            if (TasksList[i].TaskStatus == TaskStatusEnum.Done)
            {
                objDesc = Lan.g(this,"Done:") + TasksList[i].DateTimeFinished.ToShortDateString() + " - ";
            }
             
            if (TasksList[i].ObjectType == TaskObjectType.Patient)
            {
                if (TasksList[i].KeyNum != 0)
                {
                    objDesc += TasksList[i].PatientName + " - ";
                }
                 
            }
            else if (TasksList[i].ObjectType == TaskObjectType.Appointment)
            {
                if (TasksList[i].KeyNum != 0)
                {
                    Appointment AptCur = Appointments.GetOneApt(TasksList[i].KeyNum);
                    if (AptCur != null)
                    {
                        //this is going to stay. Still not optimized, but here at HQ, we don't use it.
                        objDesc = Patients.getPat(AptCur.PatNum).getNameLF() + "  " + AptCur.AptDateTime.ToString() + "  " + AptCur.ProcDescript + "  " + AptCur.Note + " - ";
                    }
                     
                }
                 
            }
              
            if (!TasksList[i].Descript.StartsWith("==") && TasksList[i].UserNum != 0)
            {
                objDesc += Userods.GetName(TasksList[i].UserNum) + " - ";
            }
             
            notes = "";
            boolean hasNotes = false;
            for (int n = 0;n < TaskNoteList.Count;n++)
            {
                if (TaskNoteList[n].TaskNum != TasksList[i].TaskNum)
                {
                    continue;
                }
                 
                //even on the first loop
                notes += "\r\n" + "==" + Userods.GetName(TaskNoteList[n].UserNum) + " - " + TaskNoteList[n].DateTimeNote.ToShortDateString() + " " + TaskNoteList[n].DateTimeNote.ToShortTimeString() + " - " + TaskNoteList[n].Note;
                hasNotes = true;
            }
            row = new OpenDental.UI.ODGridRow();
            if (PrefC.getBool(PrefName.TasksNewTrackedByUser))
            {
                //The new way
                if (TasksList[i].TaskStatus == TaskStatusEnum.Done)
                {
                    row.getCells().add("1");
                }
                else
                {
                    if (TasksList[i].IsUnread)
                    {
                        row.getCells().add("4");
                    }
                    else
                    {
                        row.getCells().add("2");
                    } 
                } 
            }
            else
            {
                TaskStatus __dummyScrutVar0 = TasksList[i].TaskStatus;
                if (__dummyScrutVar0.equals(TaskStatusEnum.New))
                {
                    row.getCells().add("4");
                }
                else if (__dummyScrutVar0.equals(TaskStatusEnum.Viewed))
                {
                    row.getCells().add("2");
                }
                else if (__dummyScrutVar0.equals(TaskStatusEnum.Done))
                {
                    row.getCells().add("1");
                }
                   
                if (tabContr.SelectedTab == tabNew)
                {
                    //In this mode, there's a extra column in this tab
                    row.getCells().add("read");
                }
                 
            } 
            if (tabContr.SelectedTab == tabNew || tabContr.SelectedTab == tabOpenTickets)
            {
                row.getCells().Add(TasksList[i].ParentDesc);
            }
             
            row.getCells().add(dateStr + objDesc + TasksList[i].Descript + notes);
            if (TasksList[i].TaskListNum == _TriageListNum)
            {
                if (!hasNotes || TasksList[i].Descript.Contains("@@"))
                {
                    row.setColorBackG(Color.LightBlue);
                }
                 
                if (TasksList[i].Descript.Contains("CUSTOMER") || TasksList[i].Descript.Contains("DOWN") || TasksList[i].Descript.Contains("URGENT") || TasksList[i].Descript.Contains("CONFERENCE") || TasksList[i].Descript.Contains("!!"))
                {
                    row.setColorBackG(Color.Salmon);
                }
                 
            }
             
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        gridMain.setScrollValue(gridMain.getScrollValue());
        //this forces scroll value to reset if it's > allowed max.
        if (tabContr.SelectedTab == tabOpenTickets)
        {
            SetOpenTicketTab(gridMain.getRows().Count);
        }
         
    }

    private void checkShowFinished_Click(Object sender, EventArgs e) throws Exception {
        if (checkShowFinished.Checked)
        {
            labelStartDate.Visible = true;
            textStartDate.Visible = true;
            textStartDate.Text = DateTime.Now.AddDays(-7).ToShortDateString();
        }
        else
        {
            labelStartDate.Visible = false;
            textStartDate.Visible = false;
        } 
        fillGrid();
    }

    /**
    * A recursive function that checks every child in a list IsFromRepeating.  If any are marked complete, then it returns true, signifying that this list should be immune from being deleted since it's already in use.
    */
    private boolean anyAreMarkedComplete(TaskList list) throws Exception {
        //get all children:
        List<TaskList> childLists = TaskLists.RefreshChildren(list.TaskListNum, Security.getCurUser().UserNum, 0);
        List<Task> childTasks = Tasks.RefreshChildren(list.TaskListNum, true, DateTime.MinValue, Security.getCurUser().UserNum, 0);
        for (int i = 0;i < childLists.Count;i++)
        {
            if (AnyAreMarkedComplete(childLists[i]))
            {
                return true;
            }
             
        }
        for (int i = 0;i < childTasks.Count;i++)
        {
            if (childTasks[i].TaskStatus == TaskStatusEnum.Done)
            {
                return true;
            }
             
        }
        return false;
    }

    /**
    * If parent=0, then this is a trunk.
    */
    private void refreshMainLists(long parent, DateTime date) throws Exception {
        DateTime startDate = DateTime.MinValue;
        if (textStartDate.Visible && !StringSupport.equals(textStartDate.Text, ""))
        {
            if (StringSupport.equals(textStartDate.errorProvider1.GetError(textStartDate), ""))
            {
                startDate = PIn.Date(textStartDate.Text);
            }
            else
            {
                //invalid date
                startDate = DateTimeOD.getToday().AddDays(-7);
            } 
        }
         
        if (this.DesignMode)
        {
            TaskListsList = new List<TaskList>();
            TasksList = new List<Task>();
            TaskNoteList = new List<TaskNote>();
            return ;
        }
         
        if (parent != 0)
        {
            //not a trunk
            //if(TreeHistory.Count>0//we already know this is true
            long userNumInbox = TaskLists.GetMailboxUserNum(TreeHistory[0].TaskListNum);
            TaskListsList = TaskLists.refreshChildren(parent,Security.getCurUser().UserNum,userNumInbox);
            TasksList = Tasks.RefreshChildren(parent, checkShowFinished.Checked, startDate, Security.getCurUser().UserNum, userNumInbox);
        }
        else if (tabContr.SelectedTab == tabUser)
        {
            TaskListsList = TaskLists.refreshUserTrunk(Security.getCurUser().UserNum);
            TasksList = new List<Task>();
        }
        else //no tasks in the user trunk
        if (tabContr.SelectedTab == tabNew)
        {
            TaskListsList = new List<TaskList>();
            //no task lists in new tab
            TasksList = Tasks.refreshUserNew(Security.getCurUser().UserNum);
        }
        else if (tabContr.SelectedTab == tabOpenTickets)
        {
            TaskListsList = new List<TaskList>();
            //no task lists in new tab
            TasksList = Tasks.refreshOpenTickets(Security.getCurUser().UserNum);
        }
        else if (tabContr.SelectedTab == tabMain)
        {
            TaskListsList = TaskLists.refreshMainTrunk(Security.getCurUser().UserNum);
            TasksList = Tasks.RefreshMainTrunk(checkShowFinished.Checked, startDate, Security.getCurUser().UserNum);
        }
        else if (tabContr.SelectedTab == tabRepeating)
        {
            TaskListsList = TaskLists.refreshRepeatingTrunk();
            TasksList = Tasks.refreshRepeatingTrunk();
        }
        else if (tabContr.SelectedTab == tabDate)
        {
            TaskListsList = TaskLists.refreshDatedTrunk(date,TaskDateType.Day);
            TasksList = Tasks.RefreshDatedTrunk(date, TaskDateType.Day, checkShowFinished.Checked, startDate, Security.getCurUser().UserNum);
        }
        else if (tabContr.SelectedTab == tabWeek)
        {
            TaskListsList = TaskLists.refreshDatedTrunk(date,TaskDateType.Week);
            TasksList = Tasks.RefreshDatedTrunk(date, TaskDateType.Week, checkShowFinished.Checked, startDate, Security.getCurUser().UserNum);
        }
        else if (tabContr.SelectedTab == tabMonth)
        {
            TaskListsList = TaskLists.refreshDatedTrunk(date,TaskDateType.Month);
            TasksList = Tasks.RefreshDatedTrunk(date, TaskDateType.Month, checkShowFinished.Checked, startDate, Security.getCurUser().UserNum);
        }
                 
        //notes
        List<long> taskNums = new List<long>();
        for (int i = 0;i < TasksList.Count;i++)
        {
            taskNums.Add(TasksList[i].TaskNum);
        }
        TaskNoteList = TaskNotes.RefreshForTasks(taskNums);
    }

    private void tabContr_Click(Object sender, System.EventArgs e) throws Exception {
        TreeHistory = new List<TaskList>();
        //clear the tree no matter which tab clicked.
        fillTree();
        fillGrid();
    }

    private void cal_DateSelected(Object sender, System.Windows.Forms.DateRangeEventArgs e) throws Exception {
        TreeHistory = new List<TaskList>();
        //clear the tree
        fillTree();
        fillGrid();
    }

    private void toolBarMain_ButtonClick(Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
        //if(e.Button.Tag.GetType()==typeof(string)){
        //standard predefined button
        Object.APPLY __dummyScrutVar1 = e.getButton().getTag().ToString();
        if (__dummyScrutVar1.equals("Setup"))
        {
            setup_Clicked();
        }
        else if (__dummyScrutVar1.equals("AddList"))
        {
            addList_Clicked();
        }
        else if (__dummyScrutVar1.equals("AddTask"))
        {
            addTask_Clicked();
        }
        else if (__dummyScrutVar1.equals("Block"))
        {
            block_Clicked();
        }
            
    }

    private void setup_Clicked() throws Exception {
        if (!Security.isAuthorized(OpenDentBusiness.Permissions.Setup))
        {
            return ;
        }
         
        FormTaskSetup FormT = new FormTaskSetup();
        FormT.ShowDialog();
    }

    private void addList_Clicked() throws Exception {
        if (tabContr.SelectedTab == tabUser && TreeHistory.Count == 0)
        {
            //trunk of user tab
            MsgBox.show(this,"Not allowed to add a task list to the trunk of the user tab.  Either use the subscription feature, or add it to a child list.");
            return ;
        }
         
        if (tabContr.SelectedTab == tabNew)
        {
            //new tab
            MsgBox.show(this,"Not allowed to add items to the 'New' tab.");
            return ;
        }
         
        TaskList cur = new TaskList();
        //if this is a child of any other taskList
        if (TreeHistory.Count > 0)
        {
            cur.Parent = TreeHistory[TreeHistory.Count - 1].TaskListNum;
        }
        else
        {
            cur.Parent = 0;
            if (tabContr.SelectedTab == tabDate)
            {
                cur.DateTL = cal.SelectionStart;
                cur.DateType = TaskDateType.Day;
            }
            else if (tabContr.SelectedTab == tabWeek)
            {
                cur.DateTL = cal.SelectionStart;
                cur.DateType = TaskDateType.Week;
            }
            else if (tabContr.SelectedTab == tabMonth)
            {
                cur.DateTL = cal.SelectionStart;
                cur.DateType = TaskDateType.Month;
            }
               
        } 
        if (tabContr.SelectedTab == tabRepeating)
        {
            cur.IsRepeating = true;
        }
         
        FormTaskListEdit FormT = new FormTaskListEdit(cur);
        FormT.IsNew = true;
        FormT.ShowDialog();
        fillGrid();
    }

    private void addTask_Clicked() throws Exception {
        //if(tabContr.SelectedTab==tabUser && TreeHistory.Count==0) {//trunk of user tab
        //	MsgBox.Show(this,"Not allowed to add a task to the trunk of the user tab.  Add it to a child list instead.");
        //	return;
        //}
        //if(tabContr.SelectedTab==tabNew) {//new tab
        //	MsgBox.Show(this,"Not allowed to add items to the 'New' tab.");
        //	return;
        //}
        Task task = new Task();
        task.TaskListNum = -1;
        //don't show it in any list yet.
        Tasks.insert(task);
        Task taskOld = task.copy();
        //if this is a child of any taskList
        if (TreeHistory.Count > 0)
        {
            task.TaskListNum = TreeHistory[TreeHistory.Count - 1].TaskListNum;
        }
        else if (tabContr.SelectedTab == tabNew)
        {
            //new tab
            task.TaskListNum = -1;
        }
        else if (tabContr.SelectedTab == tabUser && TreeHistory.Count == 0)
        {
            //trunk of user tab
            task.TaskListNum = -1;
        }
        else
        {
            task.TaskListNum = 0;
            if (tabContr.SelectedTab == tabDate)
            {
                task.DateTask = cal.SelectionStart;
                task.DateType = TaskDateType.Day;
            }
            else if (tabContr.SelectedTab == tabWeek)
            {
                task.DateTask = cal.SelectionStart;
                task.DateType = TaskDateType.Week;
            }
            else if (tabContr.SelectedTab == tabMonth)
            {
                task.DateTask = cal.SelectionStart;
                task.DateType = TaskDateType.Month;
            }
               
        }   
        if (tabContr.SelectedTab == tabRepeating)
        {
            task.IsRepeating = true;
        }
         
        task.UserNum = Security.getCurUser().UserNum;
        FormTaskEdit FormT = new FormTaskEdit(task,taskOld);
        FormT.IsNew = true;
        FormT.Closing += new CancelEventHandler(TaskGoToEvent);
        FormT.Show();
    }

    //non-modal
    public void taskGoToEvent(Object sender, CancelEventArgs e) throws Exception {
        FormTaskEdit FormT = (FormTaskEdit)sender;
        if (FormT.GotoType != TaskObjectType.None)
        {
            GotoType = FormT.GotoType;
            GotoKeyNum = FormT.GotoKeyNum;
            onGoToChanged();
        }
         
        if (!this.IsDisposed)
        {
            fillGrid();
        }
         
    }

    private void block_Clicked() throws Exception {
        if (ToolBarMain.getButtons().get___idx("Block").getPushed())
        {
            Security.getCurUser().DefaultHidePopups = true;
        }
        else
        {
            Security.getCurUser().DefaultHidePopups = false;
        } 
        try
        {
            Userods.update(Security.getCurUser());
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        DataValid.setInvalid(InvalidType.Security);
    }

    private void done_Clicked() throws Exception {
        //already blocked if list
        Task task = TasksList[clickedI - TaskListsList.Count];
        Task oldTask = task.copy();
        task.TaskStatus = TaskStatusEnum.Done;
        if (task.DateTimeFinished.Year < 1880)
        {
            task.DateTimeFinished = DateTime.Now;
        }
         
        try
        {
            Tasks.update(task,oldTask);
            TaskUnreads.deleteForTask(task.TaskNum);
            DataValid.setInvalidTask(task.TaskNum,false);
        }
        catch (Exception ex)
        {
            //this causes an immediate local refresh of the grid
            MessageBox.Show(ex.Message);
            return ;
        }
    
    }

    private void edit_Clicked() throws Exception {
        if (clickedI < TaskListsList.Count)
        {
            //is list
            FormTaskListEdit FormT = new FormTaskListEdit(TaskListsList[clickedI]);
            FormT.ShowDialog();
            fillGrid();
        }
        else
        {
            //task
            FormTaskEdit FormT = new FormTaskEdit(TasksList[clickedI - TaskListsList.Count], TasksList[clickedI - TaskListsList.Count].Copy());
            FormT.Closing += new CancelEventHandler(TaskGoToEvent);
            FormT.Show();
        } 
    }

    //non-modal
    private void cut_Clicked() throws Exception {
        if (clickedI < TaskListsList.Count)
        {
            //is list
            ClipTaskList = TaskListsList[clickedI].Copy();
            ClipTask = null;
        }
        else
        {
            //task
            ClipTaskList = null;
            ClipTask = TasksList[clickedI - TaskListsList.Count].Copy();
        } 
        WasCut = true;
    }

    private void copy_Clicked() throws Exception {
        if (clickedI < TaskListsList.Count)
        {
            //is list
            ClipTaskList = TaskListsList[clickedI].Copy();
            ClipTask = null;
        }
        else
        {
            //task
            ClipTaskList = null;
            ClipTask = TasksList[clickedI - TaskListsList.Count].Copy();
        } 
        WasCut = false;
    }

    private void paste_Clicked() throws Exception {
        if (ClipTaskList != null)
        {
            //a taskList is on the clipboard
            TaskList newTL = ClipTaskList.copy();
            if (TreeHistory.Count > 0)
            {
                //not on main trunk
                newTL.Parent = TreeHistory[TreeHistory.Count - 1].TaskListNum;
                if (tabContr.SelectedTab == tabUser)
                {
                }
                else //treat pasting just like it's the main tab, because not on the trunk.
                if (tabContr.SelectedTab == tabMain)
                {
                }
                else //even though usually only trunks are dated, we will leave the date alone in main
                //category since user may wish to preserve it. All other children get date cleared.
                if (tabContr.SelectedTab == tabRepeating)
                {
                    newTL.DateTL = DateTime.MinValue;
                }
                else //never a date
                //leave dateType alone, since that affects how it repeats
                if (tabContr.SelectedTab == tabDate || tabContr.SelectedTab == tabWeek || tabContr.SelectedTab == tabMonth)
                {
                    newTL.DateTL = DateTime.MinValue;
                    //children do not get dated
                    newTL.DateType = TaskDateType.None;
                }
                    
            }
            else
            {
                //this doesn't matter either for children
                //one of the main trunks
                newTL.Parent = 0;
                if (tabContr.SelectedTab == tabUser)
                {
                    //maybe we should treat this like a subscription rather than a paste.  Implement later.  For now:
                    MsgBox.show(this,"Not allowed to paste directly to the trunk of this tab.  Try using the subscription feature instead.");
                    return ;
                }
                else if (tabContr.SelectedTab == tabMain)
                {
                    newTL.DateTL = DateTime.MinValue;
                    newTL.DateType = TaskDateType.None;
                }
                else if (tabContr.SelectedTab == tabRepeating)
                {
                    newTL.DateTL = DateTime.MinValue;
                }
                else //never a date
                //newTL.DateType=TaskDateType.None;//leave alone
                if (tabContr.SelectedTab == tabDate)
                {
                    newTL.DateTL = cal.SelectionStart;
                    newTL.DateType = TaskDateType.Day;
                }
                else if (tabContr.SelectedTab == tabWeek)
                {
                    newTL.DateTL = cal.SelectionStart;
                    newTL.DateType = TaskDateType.Week;
                }
                else if (tabContr.SelectedTab == tabMonth)
                {
                    newTL.DateTL = cal.SelectionStart;
                    newTL.DateType = TaskDateType.Month;
                }
                      
            } 
            if (tabContr.SelectedTab == tabRepeating)
            {
                newTL.IsRepeating = true;
            }
            else
            {
                newTL.IsRepeating = false;
            } 
            newTL.FromNum = 0;
            //always
            //oldList used to move subscriptions to task list if Cut/Paste. If null, subscriptions will be deleted.
            TaskList oldList = null;
            if (WasCut)
            {
                oldList = ClipTaskList.copy();
            }
             
            if (tabContr.SelectedTab == tabUser || tabContr.SelectedTab == tabMain)
            {
                duplicateExistingList(newTL,oldList,true);
            }
            else
            {
                duplicateExistingList(newTL,oldList,false);
            } 
            DataValid.setInvalid(InvalidType.Task);
        }
         
        if (ClipTask != null)
        {
            //a task is on the clipboard
            Task newT = ClipTask.copy();
            if (TreeHistory.Count > 0)
            {
                //not on main trunk
                newT.TaskListNum = TreeHistory[TreeHistory.Count - 1].TaskListNum;
                if (tabContr.SelectedTab == tabUser)
                {
                }
                else //treat pasting just like it's the main tab, because not on the trunk.
                if (tabContr.SelectedTab == tabMain)
                {
                }
                else //even though usually only trunks are dated, we will leave the date alone in main
                //category since user may wish to preserve it. All other children get date cleared.
                if (tabContr.SelectedTab == tabRepeating)
                {
                    newT.DateTask = DateTime.MinValue;
                }
                else //never a date
                //leave dateType alone, since that affects how it repeats
                if (tabContr.SelectedTab == tabDate || tabContr.SelectedTab == tabWeek || tabContr.SelectedTab == tabMonth)
                {
                    newT.DateTask = DateTime.MinValue;
                    //children do not get dated
                    newT.DateType = TaskDateType.None;
                }
                    
            }
            else
            {
                //this doesn't matter either for children
                //one of the main trunks
                newT.TaskListNum = 0;
                if (tabContr.SelectedTab == tabUser)
                {
                    //never allowed to have a task on the user trunk.
                    MsgBox.show(this,"Tasks may not be pasted directly to the trunk of this tab.  Try pasting within a list instead.");
                    return ;
                }
                else if (tabContr.SelectedTab == tabMain)
                {
                    newT.DateTask = DateTime.MinValue;
                    newT.DateType = TaskDateType.None;
                }
                else if (tabContr.SelectedTab == tabRepeating)
                {
                    newT.DateTask = DateTime.MinValue;
                }
                else //never a date
                //newTL.DateType=TaskDateType.None;//leave alone
                if (tabContr.SelectedTab == tabDate)
                {
                    newT.DateTask = cal.SelectionStart;
                    newT.DateType = TaskDateType.Day;
                }
                else if (tabContr.SelectedTab == tabWeek)
                {
                    newT.DateTask = cal.SelectionStart;
                    newT.DateType = TaskDateType.Week;
                }
                else if (tabContr.SelectedTab == tabMonth)
                {
                    newT.DateTask = cal.SelectionStart;
                    newT.DateType = TaskDateType.Month;
                }
                      
            } 
            if (tabContr.SelectedTab == tabRepeating)
            {
                newT.IsRepeating = true;
            }
            else
            {
                newT.IsRepeating = false;
            } 
            newT.FromNum = 0;
            //always
            if (WasCut && Tasks.wasTaskAltered(ClipTask))
            {
                MsgBox.show("Tasks","Not allowed to move because the task has been altered by someone else.");
                fillGrid();
                return ;
            }
             
            List<TaskNote> noteList = TaskNotes.getForTask(newT.TaskNum);
            long newTaskNum = Tasks.insert(newT);
            for (int t = 0;t < noteList.Count;t++)
            {
                noteList[t].TaskNum = newTaskNum;
                TaskNotes.Insert(noteList[t]);
                //Creates the new note with the current datetime stamp.
                TaskNotes.Update(noteList[t]);
            }
            //Restores the historical datetime for the note.
            DataValid.setInvalidTask(newT.TaskNum,true);
        }
         
        if (WasCut)
        {
            if (ClipTaskList != null)
            {
                deleteEntireList(ClipTaskList);
            }
            else if (ClipTask != null)
            {
                Tasks.delete(ClipTask.TaskNum);
                DataValid.setInvalidTask(ClipTask.TaskNum,false);
            }
              
        }
         
    }

    //this causes an immediate local refresh of the grid
    private void sendToMe_Clicked() throws Exception {
        if (Security.getCurUser().TaskListInBox == 0)
        {
            MsgBox.show(this,"You do not have an inbox.");
            return ;
        }
         
        Task oldTask = TasksList[clickedI - TaskListsList.Count];
        Task task = oldTask.copy();
        task.TaskListNum = Security.getCurUser().TaskListInBox;
        try
        {
            Tasks.update(task,oldTask);
            DataValid.setInvalidTask(task.TaskNum,true);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }
    
    }

    private void goto_Clicked() throws Exception {
        //not even allowed to get to this point unless a valid task
        Task task = TasksList[clickedI - TaskListsList.Count];
        GotoType = task.ObjectType;
        GotoKeyNum = task.KeyNum;
        onGoToChanged();
    }

    /**
    * A recursive function that duplicates an entire existing TaskList.  For the initial loop, make changes to the original taskList before passing it in.  That way, Date and type are only set in initial loop.  All children preserve original dates and types.  The isRepeating value will be applied in all loops.  Also, make sure to change the parent num to the new one before calling this function.  The taskListNum will always change, because we are inserting new record into database.  Sending null in for oldList will cause all existing subscriptions to the old list to be deleted.  In order for the new list to have the same subscriptions as the old list (as in a Cut/Paste), send in the old task list.
    */
    private void duplicateExistingList(TaskList newList, TaskList oldList, boolean isInMainOrUser) throws Exception {
        //get all children:
        List<TaskList> childLists = TaskLists.RefreshChildren(newList.TaskListNum, Security.getCurUser().UserNum, 0);
        List<Task> childTasks = Tasks.RefreshChildren(newList.TaskListNum, true, DateTime.MinValue, Security.getCurUser().UserNum, 0);
        TaskLists.insert(newList);
        if (oldList != null)
        {
            TaskSubscriptions.updateAllSubs(oldList.TaskListNum,newList.TaskListNum);
        }
         
        for (int i = 0;i < childLists.Count;i++)
        {
            //now we have a new taskListNum to work with
            childLists[i].Parent = newList.TaskListNum;
            if (newList.IsRepeating)
            {
                childLists[i].IsRepeating = true;
                childLists[i].DateTL = DateTime.MinValue;
            }
            else
            {
                //never a date
                childLists[i].IsRepeating = false;
            } 
            childLists[i].FromNum = 0;
            if (!isInMainOrUser)
            {
                childLists[i].DateTL = DateTime.MinValue;
                childLists[i].DateType = TaskDateType.None;
            }
             
            if (oldList == null)
            {
                DuplicateExistingList(childLists[i], null, isInMainOrUser);
            }
            else
            {
                //delete any existing subscriptions
                DuplicateExistingList(childLists[i], childLists[i], isInMainOrUser);
            } 
        }
        for (int i = 0;i < childTasks.Count;i++)
        {
            childTasks[i].TaskListNum = newList.TaskListNum;
            if (newList.IsRepeating)
            {
                childTasks[i].IsRepeating = true;
                childTasks[i].DateTask = DateTime.MinValue;
            }
            else
            {
                //never a date
                childTasks[i].IsRepeating = false;
            } 
            childTasks[i].FromNum = 0;
            if (!isInMainOrUser)
            {
                childTasks[i].DateTask = DateTime.MinValue;
                childTasks[i].DateType = TaskDateType.None;
            }
             
            List<TaskNote> noteList = TaskNotes.GetForTask(childTasks[i].TaskNum);
            long newTaskNum = Tasks.Insert(childTasks[i]);
            for (int t = 0;t < noteList.Count;t++)
            {
                noteList[t].TaskNum = newTaskNum;
                TaskNotes.Insert(noteList[t]);
                //Creates the new note with the current datetime stamp.
                TaskNotes.Update(noteList[t]);
            }
        }
    }

    //Restores the historical datetime for the note.
    private void delete_Clicked() throws Exception {
        if (clickedI < TaskListsList.Count)
        {
            //is list
            //check to make sure the list is empty.
            List<Task> tsks = Tasks.RefreshChildren(TaskListsList[clickedI].TaskListNum, true, DateTime.MinValue, Security.getCurUser().UserNum, 0);
            List<TaskList> tsklsts = TaskLists.RefreshChildren(TaskListsList[clickedI].TaskListNum, Security.getCurUser().UserNum, 0);
            if (tsks.Count > 0 || tsklsts.Count > 0)
            {
                MsgBox.show(this,"Not allowed to delete a list unless it's empty.");
                return ;
            }
             
            if (TaskLists.GetMailboxUserNum(TaskListsList[clickedI].TaskListNum) != 0)
            {
                MsgBox.show(this,"Not allowed to delete task list because it is attached to a user inbox.");
                return ;
            }
             
            if (!MsgBox.show(this,true,"Delete this empty list?"))
            {
                return ;
            }
             
            TaskSubscriptions.UpdateAllSubs(TaskListsList[clickedI].TaskListNum, 0);
            TaskLists.Delete(TaskListsList[clickedI]);
            //DeleteEntireList(TaskListsList[clickedI]);
            DataValid.setInvalid(InvalidType.Task);
        }
        else
        {
            //Is task
            if (!MsgBox.show(this,true,"Delete?"))
            {
                return ;
            }
             
            Tasks.Delete(TasksList[clickedI - TaskListsList.Count].TaskNum);
            DataValid.SetInvalidTask(TasksList[clickedI - TaskListsList.Count].TaskNum, false);
        } 
    }

    //FillGrid();
    /**
    * A recursive function that deletes the specified list and all children.
    */
    private void deleteEntireList(TaskList list) throws Exception {
        //get all children:
        List<TaskList> childLists = TaskLists.RefreshChildren(list.TaskListNum, Security.getCurUser().UserNum, 0);
        List<Task> childTasks = Tasks.RefreshChildren(list.TaskListNum, true, DateTime.MinValue, Security.getCurUser().UserNum, 0);
        for (int i = 0;i < childLists.Count;i++)
        {
            DeleteEntireList(childLists[i]);
        }
        for (int i = 0;i < childTasks.Count;i++)
        {
            Tasks.Delete(childTasks[i].TaskNum);
        }
        try
        {
            TaskLists.delete(list);
        }
        catch (Exception e)
        {
            MessageBox.Show(e.Message);
        }
    
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (e.getCol() == 0)
        {
            return ;
        }
         
        //no longer allow double click on checkbox, because it's annoying.
        if (e.getRow() >= TaskListsList.Count)
        {
            //is task
            //It's important to grab the task directly from the db because the status in this list is fake, being the "unread" status instead.
            Task task = Tasks.GetOne(TasksList[e.getRow() - TaskListsList.Count].TaskNum);
            FormTaskEdit FormT = new FormTaskEdit(task,task.copy());
            FormT.Closing += new CancelEventHandler(TaskGoToEvent);
            FormT.Show();
        }
         
    }

    //non-modal
    private void gridMain_MouseDown(Object sender, MouseEventArgs e) throws Exception {
        clickedI = gridMain.PointToRow(e.Y);
        //e.Row;
        int clickedCol = gridMain.PointToCol(e.X);
        if (clickedI == -1)
        {
            return ;
        }
         
        gridMain.setSelected(clickedI,true);
        //if right click.
        if (e.Button != MouseButtons.Left)
        {
            return ;
        }
         
        if (clickedI < TaskListsList.Count)
        {
            //is list
            //If the list is someone else's inbox, block
            //long mailboxUserNum=TaskLists.GetMailboxUserNum(TaskListsList[clickedI].TaskListNum);
            //This is too restrictive.  Need to work into security permissions:
            //if(mailboxUserNum != 0 && mailboxUserNum != Security.CurUser.UserNum) {
            //	MsgBox.Show(this,"Inboxes are private.");
            //	return;
            //}
            TreeHistory.Add(TaskListsList[clickedI]);
            fillTree();
            fillGrid();
            return ;
        }
         
        if (tabContr.SelectedTab == tabNew && !PrefC.getBool(PrefName.TasksNewTrackedByUser))
        {
            //There's an extra column
            if (clickedCol == 1)
            {
                TaskUnreads.SetRead(Security.getCurUser().UserNum, TasksList[clickedI - TaskListsList.Count].TaskNum);
                fillGrid();
            }
             
            return ;
        }
         
        //but ignore column 0 for now.  We would need to add that as a new feature.
        if (clickedCol == 0)
        {
            //check tasks off
            if (PrefC.getBool(PrefName.TasksNewTrackedByUser))
            {
                if (tabContr.SelectedTab == tabNew)
                {
                }
                else
                {
                    //these are never in someone else's inbox, so don't block.
                    long userNumInbox = 0;
                    if (tabContr.SelectedTab == tabOpenTickets)
                    {
                        userNumInbox = TaskLists.GetMailboxUserNumByAncestor(TasksList[clickedI - TaskListsList.Count].TaskNum);
                    }
                    else
                    {
                        userNumInbox = TaskLists.GetMailboxUserNum(TreeHistory[0].TaskListNum);
                    } 
                    if (userNumInbox != 0 && userNumInbox != Security.getCurUser().UserNum)
                    {
                        MsgBox.show(this,"Not allowed to mark off tasks in someone else's inbox.");
                        return ;
                    }
                     
                } 
                //might not need to go to db to get this info
                //might be able to check this:
                //if(task.IsUnread) {
                //But seems safer to go to db.
                if (TaskUnreads.IsUnread(Security.getCurUser().UserNum, TasksList[clickedI - TaskListsList.Count].TaskNum))
                {
                    TaskUnreads.SetRead(Security.getCurUser().UserNum, TasksList[clickedI - TaskListsList.Count].TaskNum);
                }
                 
                DataValid.SetInvalidTask(TasksList[clickedI - TaskListsList.Count].TaskNum, false);
            }
            else
            {
                //if already read, nothing else to do.  If done, nothing to do
                if (TasksList[clickedI - TaskListsList.Count].TaskStatus == TaskStatusEnum.New)
                {
                    Task task = TasksList[clickedI - TaskListsList.Count].Copy();
                    Task taskOld = task.copy();
                    task.TaskStatus = TaskStatusEnum.Viewed;
                    try
                    {
                        Tasks.update(task,taskOld);
                        DataValid.setInvalidTask(task.TaskNum,false);
                    }
                    catch (Exception ex)
                    {
                        MessageBox.Show(ex.Message);
                        return ;
                    }
                
                }
                 
            } 
        }
         
    }

    //no longer allowed to mark done from here
    //FillGrid();
    private void menuEdit_Popup(Object sender, System.EventArgs e) throws Exception {
        setMenusEnabled();
    }

    private void setMenusEnabled() throws Exception {
        //Done----------------------------------
        if (gridMain.getSelectedIndices().Length == 0 || clickedI < TaskListsList.Count)
        {
            //or a tasklist selected
            menuItemDone.Enabled = false;
        }
        else
        {
            menuItemDone.Enabled = true;
        } 
        //Edit,Cut,Copy,Delete-------------------------
        if (gridMain.getSelectedIndices().Length == 0)
        {
            menuItemEdit.Enabled = false;
            menuItemCut.Enabled = false;
            menuItemCopy.Enabled = false;
            menuItemDelete.Enabled = false;
        }
        else
        {
            menuItemEdit.Enabled = true;
            menuItemCut.Enabled = true;
            menuItemCopy.Enabled = true;
            menuItemDelete.Enabled = true;
        } 
        //Paste----------------------------------------
        if (tabContr.SelectedTab == tabUser && TreeHistory.Count == 0)
        {
            //not allowed to paste into the trunk of a user tab
            menuItemPaste.Enabled = false;
        }
        else if (ClipTaskList == null && ClipTask == null)
        {
            menuItemPaste.Enabled = false;
        }
        else
        {
            //there is an item on our clipboard
            menuItemPaste.Enabled = true;
        }  
        //(overrides)
        if (tabContr.SelectedTab == tabNew || tabContr.SelectedTab == tabOpenTickets)
        {
            menuItemCut.Enabled = false;
            menuItemDelete.Enabled = false;
            menuItemPaste.Enabled = false;
        }
         
        //Subscriptions----------------------------------------------------------
        if (gridMain.getSelectedIndices().Length == 0)
        {
            menuItemSubscribe.Enabled = false;
            menuItemUnsubscribe.Enabled = false;
        }
        else if (tabContr.SelectedTab == tabUser)
        {
            //user
            menuItemSubscribe.Enabled = false;
            menuItemUnsubscribe.Enabled = true;
        }
        else if (tabContr.SelectedTab == tabMain && clickedI < TaskListsList.Count)
        {
            //main and tasklist
            menuItemSubscribe.Enabled = true;
            menuItemUnsubscribe.Enabled = false;
        }
        else
        {
            //either any other tab, or a task on the main list
            menuItemSubscribe.Enabled = false;
            menuItemUnsubscribe.Enabled = false;
        }   
        //SendToMe/GoTo---------------------------------------------------------------
        if (gridMain.getSelectedIndices().Length > 0 && clickedI >= TaskListsList.Count)
        {
            //is task
            Task task = TasksList[clickedI - TaskListsList.Count];
            if (task.ObjectType == TaskObjectType.None)
            {
                menuItemGoto.Enabled = false;
            }
            else
            {
                menuItemGoto.Enabled = true;
            } 
            menuItemSendToMe.Enabled = true;
        }
        else
        {
            menuItemGoto.Enabled = false;
            //not a task
            menuItemSendToMe.Enabled = false;
        } 
    }

    private void onSubscribe_Click() throws Exception {
        try
        {
            //won't even get to this point unless it is a list
            TaskSubscriptions.SubscList(TaskListsList[clickedI].TaskListNum, Security.getCurUser().UserNum);
        }
        catch (ApplicationException ex)
        {
            //for example, if already subscribed.
            MessageBox.Show(ex.Message);
            return ;
        }

        MsgBox.show(this,"Done");
    }

    private void onUnsubscribe_Click() throws Exception {
        TaskSubscriptions.UnsubscList(TaskListsList[clickedI].TaskListNum, Security.getCurUser().UserNum);
        //FillMain();
        fillGrid();
    }

    private void menuItemDone_Click(Object sender, EventArgs e) throws Exception {
        done_Clicked();
    }

    private void menuItemEdit_Click(Object sender, System.EventArgs e) throws Exception {
        edit_Clicked();
    }

    private void menuItemCut_Click(Object sender, System.EventArgs e) throws Exception {
        cut_Clicked();
    }

    private void menuItemCopy_Click(Object sender, System.EventArgs e) throws Exception {
        copy_Clicked();
    }

    private void menuItemPaste_Click(Object sender, System.EventArgs e) throws Exception {
        paste_Clicked();
    }

    private void menuItemDelete_Click(Object sender, System.EventArgs e) throws Exception {
        delete_Clicked();
    }

    private void menuItemSubscribe_Click(Object sender, EventArgs e) throws Exception {
        onSubscribe_Click();
    }

    private void menuItemUnsubscribe_Click(Object sender, EventArgs e) throws Exception {
        onUnsubscribe_Click();
    }

    private void menuItemSendToMe_Click(Object sender, EventArgs e) throws Exception {
        sendToMe_Clicked();
    }

    private void menuItemGoto_Click(Object sender, System.EventArgs e) throws Exception {
        goto_Clicked();
    }

    //private void listMain_SelectedIndexChanged(object sender,System.EventArgs e) {
    //	SetMenusEnabled();
    //}
    private void tree_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        for (int i = TreeHistory.Count - 1;i > 0;i--)
        {
            if (TreeHistory[i].TaskListNum == (long)tree.GetNodeAt(e.X, e.Y).Tag)
            {
                break;
            }
             
            //don't remove the node click on or any higher node
            TreeHistory.RemoveAt(i);
        }
        fillTree();
        //FillMain();
        fillGrid();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(OpenDental.UserControlTasks.class);
        this.tabContr = new System.Windows.Forms.TabControl();
        this.tabUser = new System.Windows.Forms.TabPage();
        this.tabNew = new System.Windows.Forms.TabPage();
        this.tabOpenTickets = new System.Windows.Forms.TabPage();
        this.tabMain = new System.Windows.Forms.TabPage();
        this.tabRepeating = new System.Windows.Forms.TabPage();
        this.tabDate = new System.Windows.Forms.TabPage();
        this.tabWeek = new System.Windows.Forms.TabPage();
        this.tabMonth = new System.Windows.Forms.TabPage();
        this.cal = new System.Windows.Forms.MonthCalendar();
        this.tree = new System.Windows.Forms.TreeView();
        this.imageListTree = new System.Windows.Forms.ImageList(this.components);
        this.imageListMain = new System.Windows.Forms.ImageList(this.components);
        this.menuEdit = new System.Windows.Forms.ContextMenu();
        this.menuItemDone = new System.Windows.Forms.MenuItem();
        this.menuItem4 = new System.Windows.Forms.MenuItem();
        this.menuItemEdit = new System.Windows.Forms.MenuItem();
        this.menuItem6 = new System.Windows.Forms.MenuItem();
        this.menuItemCut = new System.Windows.Forms.MenuItem();
        this.menuItemCopy = new System.Windows.Forms.MenuItem();
        this.menuItemPaste = new System.Windows.Forms.MenuItem();
        this.menuItemDelete = new System.Windows.Forms.MenuItem();
        this.menuItem2 = new System.Windows.Forms.MenuItem();
        this.menuItemSubscribe = new System.Windows.Forms.MenuItem();
        this.menuItemUnsubscribe = new System.Windows.Forms.MenuItem();
        this.menuItem3 = new System.Windows.Forms.MenuItem();
        this.menuItemSendToMe = new System.Windows.Forms.MenuItem();
        this.menuItemGoto = new System.Windows.Forms.MenuItem();
        this.checkShowFinished = new System.Windows.Forms.CheckBox();
        this.labelStartDate = new System.Windows.Forms.Label();
        this.textStartDate = new OpenDental.ValidDate();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.ToolBarMain = new OpenDental.UI.ODToolBar();
        this.tabContr.SuspendLayout();
        this.SuspendLayout();
        //
        // tabContr
        //
        this.tabContr.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.tabContr.Controls.Add(this.tabUser);
        this.tabContr.Controls.Add(this.tabNew);
        this.tabContr.Controls.Add(this.tabOpenTickets);
        this.tabContr.Controls.Add(this.tabMain);
        this.tabContr.Controls.Add(this.tabRepeating);
        this.tabContr.Controls.Add(this.tabDate);
        this.tabContr.Controls.Add(this.tabWeek);
        this.tabContr.Controls.Add(this.tabMonth);
        this.tabContr.Location = new System.Drawing.Point(0, 29);
        this.tabContr.Name = "tabContr";
        this.tabContr.SelectedIndex = 0;
        this.tabContr.Size = new System.Drawing.Size(941, 23);
        this.tabContr.TabIndex = 5;
        this.tabContr.Click += new System.EventHandler(this.tabContr_Click);
        //
        // tabUser
        //
        this.tabUser.Location = new System.Drawing.Point(4, 22);
        this.tabUser.Name = "tabUser";
        this.tabUser.Size = new System.Drawing.Size(933, 0);
        this.tabUser.TabIndex = 5;
        this.tabUser.Text = "for User";
        this.tabUser.UseVisualStyleBackColor = true;
        //
        // tabNew
        //
        this.tabNew.Location = new System.Drawing.Point(4, 22);
        this.tabNew.Name = "tabNew";
        this.tabNew.Size = new System.Drawing.Size(933, 0);
        this.tabNew.TabIndex = 6;
        this.tabNew.Text = "New for User";
        this.tabNew.UseVisualStyleBackColor = true;
        //
        // tabOpenTickets
        //
        this.tabOpenTickets.Location = new System.Drawing.Point(4, 22);
        this.tabOpenTickets.Name = "tabOpenTickets";
        this.tabOpenTickets.Size = new System.Drawing.Size(933, 0);
        this.tabOpenTickets.TabIndex = 7;
        this.tabOpenTickets.Text = "Open Tickets";
        this.tabOpenTickets.UseVisualStyleBackColor = true;
        //
        // tabMain
        //
        this.tabMain.Location = new System.Drawing.Point(4, 22);
        this.tabMain.Name = "tabMain";
        this.tabMain.Size = new System.Drawing.Size(933, 0);
        this.tabMain.TabIndex = 0;
        this.tabMain.Text = "Main";
        this.tabMain.UseVisualStyleBackColor = true;
        //
        // tabRepeating
        //
        this.tabRepeating.Location = new System.Drawing.Point(4, 22);
        this.tabRepeating.Name = "tabRepeating";
        this.tabRepeating.Size = new System.Drawing.Size(933, 0);
        this.tabRepeating.TabIndex = 2;
        this.tabRepeating.Text = "Repeating (setup)";
        this.tabRepeating.UseVisualStyleBackColor = true;
        //
        // tabDate
        //
        this.tabDate.Location = new System.Drawing.Point(4, 22);
        this.tabDate.Name = "tabDate";
        this.tabDate.Size = new System.Drawing.Size(933, 0);
        this.tabDate.TabIndex = 1;
        this.tabDate.Text = "By Date";
        this.tabDate.UseVisualStyleBackColor = true;
        //
        // tabWeek
        //
        this.tabWeek.Location = new System.Drawing.Point(4, 22);
        this.tabWeek.Name = "tabWeek";
        this.tabWeek.Size = new System.Drawing.Size(933, 0);
        this.tabWeek.TabIndex = 3;
        this.tabWeek.Text = "By Week";
        this.tabWeek.UseVisualStyleBackColor = true;
        //
        // tabMonth
        //
        this.tabMonth.Location = new System.Drawing.Point(4, 22);
        this.tabMonth.Name = "tabMonth";
        this.tabMonth.Size = new System.Drawing.Size(933, 0);
        this.tabMonth.TabIndex = 4;
        this.tabMonth.Text = "By Month";
        this.tabMonth.UseVisualStyleBackColor = true;
        //
        // cal
        //
        this.cal.Location = new System.Drawing.Point(2, 53);
        this.cal.MaxSelectionCount = 1;
        this.cal.Name = "cal";
        this.cal.TabIndex = 6;
        this.cal.DateSelected += new System.Windows.Forms.DateRangeEventHandler(this.cal_DateSelected);
        //
        // tree
        //
        this.tree.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.tree.HideSelection = false;
        this.tree.ImageIndex = 0;
        this.tree.ImageList = this.imageListTree;
        this.tree.ItemHeight = 18;
        this.tree.Location = new System.Drawing.Point(0, 206);
        this.tree.Name = "tree";
        this.tree.Scrollable = false;
        this.tree.SelectedImageIndex = 0;
        this.tree.ShowPlusMinus = false;
        this.tree.Size = new System.Drawing.Size(941, 98);
        this.tree.TabIndex = 7;
        this.tree.MouseDown += new System.Windows.Forms.MouseEventHandler(this.tree_MouseDown);
        //
        // imageListTree
        //
        this.imageListTree.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageListTree.ImageStream")));
        this.imageListTree.TransparentColor = System.Drawing.Color.Transparent;
        this.imageListTree.Images.SetKeyName(0, "TaskList.gif");
        this.imageListTree.Images.SetKeyName(1, "checkBoxChecked.gif");
        this.imageListTree.Images.SetKeyName(2, "checkBoxUnchecked.gif");
        this.imageListTree.Images.SetKeyName(3, "TaskListHighlight.gif");
        this.imageListTree.Images.SetKeyName(4, "checkBoxNew.gif");
        //
        // imageListMain
        //
        this.imageListMain.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageListMain.ImageStream")));
        this.imageListMain.TransparentColor = System.Drawing.Color.Transparent;
        this.imageListMain.Images.SetKeyName(0, "TaskListAdd.gif");
        this.imageListMain.Images.SetKeyName(1, "Add.gif");
        //
        // menuEdit
        //
        this.menuEdit.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemDone, this.menuItem4, this.menuItemEdit, this.menuItem6, this.menuItemCut, this.menuItemCopy, this.menuItemPaste, this.menuItemDelete, this.menuItem2, this.menuItemSubscribe, this.menuItemUnsubscribe, this.menuItem3, this.menuItemSendToMe, this.menuItemGoto });
        this.menuEdit.Popup += new System.EventHandler(this.menuEdit_Popup);
        //
        // menuItemDone
        //
        this.menuItemDone.Index = 0;
        this.menuItemDone.Text = "Done (affects all users)";
        this.menuItemDone.Click += new System.EventHandler(this.menuItemDone_Click);
        //
        // menuItem4
        //
        this.menuItem4.Index = 1;
        this.menuItem4.Text = "-";
        //
        // menuItemEdit
        //
        this.menuItemEdit.Index = 2;
        this.menuItemEdit.Text = "Edit Properties";
        this.menuItemEdit.Click += new System.EventHandler(this.menuItemEdit_Click);
        //
        // menuItem6
        //
        this.menuItem6.Index = 3;
        this.menuItem6.Text = "-";
        //
        // menuItemCut
        //
        this.menuItemCut.Index = 4;
        this.menuItemCut.Text = "Cut";
        this.menuItemCut.Click += new System.EventHandler(this.menuItemCut_Click);
        //
        // menuItemCopy
        //
        this.menuItemCopy.Index = 5;
        this.menuItemCopy.Text = "Copy";
        this.menuItemCopy.Click += new System.EventHandler(this.menuItemCopy_Click);
        //
        // menuItemPaste
        //
        this.menuItemPaste.Index = 6;
        this.menuItemPaste.Text = "Paste";
        this.menuItemPaste.Click += new System.EventHandler(this.menuItemPaste_Click);
        //
        // menuItemDelete
        //
        this.menuItemDelete.Index = 7;
        this.menuItemDelete.Text = "Delete";
        this.menuItemDelete.Click += new System.EventHandler(this.menuItemDelete_Click);
        //
        // menuItem2
        //
        this.menuItem2.Index = 8;
        this.menuItem2.Text = "-";
        //
        // menuItemSubscribe
        //
        this.menuItemSubscribe.Index = 9;
        this.menuItemSubscribe.Text = "Subscribe";
        this.menuItemSubscribe.Click += new System.EventHandler(this.menuItemSubscribe_Click);
        //
        // menuItemUnsubscribe
        //
        this.menuItemUnsubscribe.Index = 10;
        this.menuItemUnsubscribe.Text = "Unsubscribe";
        this.menuItemUnsubscribe.Click += new System.EventHandler(this.menuItemUnsubscribe_Click);
        //
        // menuItem3
        //
        this.menuItem3.Index = 11;
        this.menuItem3.Text = "-";
        //
        // menuItemSendToMe
        //
        this.menuItemSendToMe.Index = 12;
        this.menuItemSendToMe.Text = "Send to Me";
        this.menuItemSendToMe.Click += new System.EventHandler(this.menuItemSendToMe_Click);
        //
        // menuItemGoto
        //
        this.menuItemGoto.Index = 13;
        this.menuItemGoto.Text = "Go To";
        this.menuItemGoto.Click += new System.EventHandler(this.menuItemGoto_Click);
        //
        // checkShowFinished
        //
        this.checkShowFinished.CheckAlign = System.Drawing.ContentAlignment.TopLeft;
        this.checkShowFinished.Location = new System.Drawing.Point(4, 311);
        this.checkShowFinished.Name = "checkShowFinished";
        this.checkShowFinished.Size = new System.Drawing.Size(151, 15);
        this.checkShowFinished.TabIndex = 10;
        this.checkShowFinished.Text = "Show Finished Tasks";
        this.checkShowFinished.UseVisualStyleBackColor = true;
        this.checkShowFinished.Click += new System.EventHandler(this.checkShowFinished_Click);
        //
        // labelStartDate
        //
        this.labelStartDate.Location = new System.Drawing.Point(155, 311);
        this.labelStartDate.Name = "labelStartDate";
        this.labelStartDate.Size = new System.Drawing.Size(83, 17);
        this.labelStartDate.TabIndex = 12;
        this.labelStartDate.Text = "Start Date";
        this.labelStartDate.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.labelStartDate.Visible = false;
        //
        // textStartDate
        //
        this.textStartDate.Location = new System.Drawing.Point(238, 310);
        this.textStartDate.Name = "textStartDate";
        this.textStartDate.Size = new System.Drawing.Size(87, 20);
        this.textStartDate.TabIndex = 11;
        this.textStartDate.Visible = false;
        this.textStartDate.TextChanged += new System.EventHandler(this.textStartDate_TextChanged);
        this.textStartDate.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.textStartDate_KeyPress);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(0, 310);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(941, 200);
        this.gridMain.TabIndex = 9;
        this.gridMain.setTitle("Tasks");
        this.gridMain.setTranslationName("TableTasks");
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
        this.gridMain.MouseDown += new System.Windows.Forms.MouseEventHandler(this.gridMain_MouseDown);
        //
        // ToolBarMain
        //
        this.ToolBarMain.Dock = System.Windows.Forms.DockStyle.Top;
        this.ToolBarMain.setImageList(this.imageListMain);
        this.ToolBarMain.Location = new System.Drawing.Point(0, 0);
        this.ToolBarMain.Name = "ToolBarMain";
        this.ToolBarMain.Size = new System.Drawing.Size(941, 25);
        this.ToolBarMain.TabIndex = 2;
        this.ToolBarMain.ButtonClick = __MultiODToolBarButtonClickEventHandler.combine(this.ToolBarMain.ButtonClick,new OpenDental.UI.ODToolBarButtonClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
                this.ToolBarMain_ButtonClick(sender, e);
            }

            public List<OpenDental.UI.ODToolBarButtonClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODToolBarButtonClickEventHandler> ret = new ArrayList<OpenDental.UI.ODToolBarButtonClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // UserControlTasks
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.Controls.Add(this.textStartDate);
        this.Controls.Add(this.labelStartDate);
        this.Controls.Add(this.checkShowFinished);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.tree);
        this.Controls.Add(this.cal);
        this.Controls.Add(this.tabContr);
        this.Controls.Add(this.ToolBarMain);
        this.Name = "UserControlTasks";
        this.Size = new System.Drawing.Size(941, 510);
        this.Load += new System.EventHandler(this.UserControlTasks_Load);
        this.tabContr.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.ODToolBar ToolBarMain;
    private System.Windows.Forms.TabControl tabContr = new System.Windows.Forms.TabControl();
    private System.Windows.Forms.TabPage tabMain = new System.Windows.Forms.TabPage();
    private System.Windows.Forms.TabPage tabRepeating = new System.Windows.Forms.TabPage();
    private System.Windows.Forms.TabPage tabDate = new System.Windows.Forms.TabPage();
    private System.Windows.Forms.TabPage tabWeek = new System.Windows.Forms.TabPage();
    private System.Windows.Forms.TabPage tabMonth = new System.Windows.Forms.TabPage();
    private System.Windows.Forms.MonthCalendar cal = new System.Windows.Forms.MonthCalendar();
    private System.Windows.Forms.TreeView tree = new System.Windows.Forms.TreeView();
    private System.Windows.Forms.ImageList imageListMain = new System.Windows.Forms.ImageList();
    private System.Windows.Forms.ContextMenu menuEdit = new System.Windows.Forms.ContextMenu();
    private System.Windows.Forms.MenuItem menuItemEdit = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItem6 = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemCut = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemCopy = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemPaste = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemDelete = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItem2 = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemGoto = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.ImageList imageListTree = new System.Windows.Forms.ImageList();
    private System.Windows.Forms.TabPage tabUser = new System.Windows.Forms.TabPage();
    private System.Windows.Forms.MenuItem menuItemSubscribe = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemUnsubscribe = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItem3 = new System.Windows.Forms.MenuItem();
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.CheckBox checkShowFinished = new System.Windows.Forms.CheckBox();
    private OpenDental.ValidDate textStartDate;
    private System.Windows.Forms.Label labelStartDate = new System.Windows.Forms.Label();
    private System.Windows.Forms.TabPage tabNew = new System.Windows.Forms.TabPage();
    private System.Windows.Forms.TabPage tabOpenTickets = new System.Windows.Forms.TabPage();
    private System.Windows.Forms.MenuItem menuItemDone = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItem4 = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemSendToMe = new System.Windows.Forms.MenuItem();
}


