using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using OpenDental.UI;
using OpenDentBusiness;

namespace OpenDental {
	public partial class FormRecallsPat:Form {
		public long PatNum;
		///<summary>This is just the list for the current patient.</summary>
		private List<Recall> RecallList;
		private bool IsPerio;

		public FormRecallsPat() {
			InitializeComponent();
			Lan.F(this);
		}

		private void FormRecallsPat_Load(object sender,EventArgs e) {
			/*
			//patient may or may not have existing recalls.
			Recall recallCur=null;
			for(int i=0;i<RecallList.Count;i++){
				if(RecallList[i].PatNum==PatCur.PatNum){
					recallCur=RecallList[i];
				}
			}*/
			//for testing purposes and because synchronization might have bugs, always synch here:
			//This might add a recall.
			//Recalls.Synch(PatNum);			
			FillGrid();
		}

		private void FillGrid(){
			Recalls.Synch(PatNum);
			RecallList=Recalls.GetList(PatNum);
			gridMain.BeginUpdate();
			gridMain.Columns.Clear();
			ODGridColumn col=new ODGridColumn(Lan.g("TableRecallsPat","Type"),90);
			gridMain.Columns.Add(col);
			col=new ODGridColumn(Lan.g("TableRecallsPat","Disabled"),60,HorizontalAlignment.Center);
			gridMain.Columns.Add(col);
			col=new ODGridColumn(Lan.g("TableRecallsPat","PreviousDate"),80);
			gridMain.Columns.Add(col);
			col=new ODGridColumn(Lan.g("TableRecallsPat","Due Date"),80);
			gridMain.Columns.Add(col);
			//col=new ODGridColumn(Lan.g("TableRecallsPat","Sched Date"),80);
			//gridMain.Columns.Add(col);
			col=new ODGridColumn(Lan.g("TableRecallsPat","Interval"),70);
			gridMain.Columns.Add(col);
			col=new ODGridColumn(Lan.g("TableRecallsPat","Status"),80);
			gridMain.Columns.Add(col);
			col=new ODGridColumn(Lan.g("TableRecallsPat","Note"),80);
			gridMain.Columns.Add(col);
			gridMain.Rows.Clear();
			ODGridRow row;
			ODGridCell cell;
			IsPerio=false;
			butPerio.Text=Lan.g(this,"Set Perio");
			for(int i=0;i<RecallList.Count;i++){
				if(PrefC.GetInt("RecallTypeSpecialPerio")==RecallList[i].RecallTypeNum){
					IsPerio=true;
					butPerio.Text=Lan.g(this,"Set Prophy");
				}
				row=new ODGridRow();
				row.Cells.Add(RecallTypes.GetDescription(RecallList[i].RecallTypeNum));
				if(RecallList[i].IsDisabled){
					row.Cells.Add("X");
				}
				else{
					row.Cells.Add("");
				}
				if(RecallList[i].DatePrevious.Year<1880){
					row.Cells.Add("");
				}
				else{
					row.Cells.Add(RecallList[i].DatePrevious.ToShortDateString());
				}
				if(RecallList[i].DateDue.Year<1880){
					row.Cells.Add("");
				}
				else{
					cell=new ODGridCell(RecallList[i].DateDue.ToShortDateString());
					if(RecallList[i].DateDue<DateTime.Today){
						cell.Bold=YN.Yes;
						cell.ColorText=Color.Firebrick;
					}
					row.Cells.Add(cell);
				}
				//row.Cells.Add("");//sched
				row.Cells.Add(RecallList[i].RecallInterval.ToString());
				row.Cells.Add(DefC.GetValue(DefCat.RecallUnschedStatus,RecallList[i].RecallStatus));
				row.Cells.Add(RecallList[i].Note);
				gridMain.Rows.Add(row);
			}
			gridMain.EndUpdate();
		}

		private void gridMain_CellDoubleClick(object sender,ODGridClickEventArgs e) {
			FormRecallEdit FormR=new FormRecallEdit();
			FormR.RecallCur=RecallList[e.Row].Copy();
			FormR.ShowDialog();
			FillGrid();
		}

		private void butPerio_Click(object sender,EventArgs e) {
			//make sure we have both special types properly setup.
			if(!RecallTypes.PerioAndProphyBothHaveTriggers()){
				MsgBox.Show(this,"Prophy and Perio special recall types are not setup properly.  They must both exist, and they must both have a trigger.");
				return;
			}
			if(IsPerio){
				//change the perio types to prophy
				for(int i=0;i<RecallList.Count;i++){
					if(PrefC.GetInt("RecallTypeSpecialPerio")==RecallList[i].RecallTypeNum){
						RecallList[i].RecallTypeNum=PrefC.GetInt("RecallTypeSpecialProphy");
						RecallList[i].RecallInterval=RecallTypes.GetInterval(PrefC.GetInt("RecallTypeSpecialProphy"));
						//previous date will be reset below in synch, but probably won't change since similar triggers.
						Recalls.Update(RecallList[i]);
						break;
					}
				}
			}
			else{
				bool found=false;
				//change any prophy types to perio
				for(int i=0;i<RecallList.Count;i++){
					if(PrefC.GetInt("RecallTypeSpecialProphy")==RecallList[i].RecallTypeNum){
						RecallList[i].RecallTypeNum=PrefC.GetInt("RecallTypeSpecialPerio");
						RecallList[i].RecallInterval=RecallTypes.GetInterval(PrefC.GetInt("RecallTypeSpecialPerio"));
						//previous date will be reset below in synch, but probably won't change since similar triggers.
						Recalls.Update(RecallList[i]);
						found=true;
						break;
					}
				}
				//if none found, then add a perio
				if(!found){
					Recall recall=new Recall();
					recall.PatNum=PatNum;
					recall.RecallInterval=RecallTypes.GetInterval(PrefC.GetInt("RecallTypeSpecialPerio"));
					recall.RecallTypeNum=PrefC.GetInt("RecallTypeSpecialPerio");
					Recalls.Insert(recall);
				}
			}
			FillGrid();
		}

		private void butAdd_Click(object sender,EventArgs e) {
			Recall recall=new Recall();
			recall.RecallTypeNum=0;//user will have to pick
			recall.PatNum=PatNum;
			recall.RecallInterval=new Interval(0,0,6,0);
			FormRecallEdit FormRE=new FormRecallEdit();
			FormRE.IsNew=true;
			FormRE.RecallCur=recall;
			FormRE.ShowDialog();
			FillGrid();
		}

		private void butClose_Click(object sender,EventArgs e) {	
			Close();
		}

		private void FormRecallsPat_FormClosing(object sender,FormClosingEventArgs e) {
			//check for duplicates that might cause a malfunction.
			int prophyCount=0;
			for(int i=0;i<RecallList.Count;i++) {
				if(RecallTypes.ProphyType==RecallList[i].RecallTypeNum) {
					prophyCount++;
				}
				if(RecallTypes.PerioType==RecallList[i].RecallTypeNum) {
					prophyCount++;
				}
			}
			if(prophyCount>1) {
				if(!MsgBox.Show(this,MsgBoxButtons.OKCancel,"Multiple prophy and/or perio recalls detected.  A patient should have only one prophy or perio recall, and the calculations will not work correctly otherwise.  Continue anyway?")){
					e.Cancel=true;
				}
			}
		}

		

		

		

		
		

		
	}
}