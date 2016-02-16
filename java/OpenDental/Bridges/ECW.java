//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:27 PM
//

package OpenDental.Bridges;

import OpenDentBusiness.HL7MessageStatus;
import OpenDentBusiness.HL7Msg;
import OpenDentBusiness.HL7Msgs;
import OpenDentBusiness.Patient;

public class ECW   
{
    /**
    * AptNum is always passed in by eCW.  It is used in the logic for setting procedures complete within apt edit window.
    */
    public static long AptNum = new long();
    public static String EcwConfigPath = new String();
    public static long UserId = new long();
    public static String JSessionId = new String();
    public static String JSessionIdSSO = new String();
    public static String LBSessionId = new String();
    //OD accepts commandline arguments from eCW.  That's handled in FormOpenDental.
    //public static void SendHL7(Appointment apt,Patient pat) {
    //  OpenDentBusiness.HL7.DFT dft=new OpenDentBusiness.HL7.DFT(apt,pat);
    //  HL7Msg msg=new HL7Msg();
    //  msg.AptNum=apt.AptNum;
    //  msg.HL7Status=HL7MessageStatus.OutPending;//it will be marked outSent by the HL7 service.
    //  msg.MsgText=dft.GenerateMessage();
    //  HL7Msgs.Insert(msg);
    //}
    public static void sendHL7(long aptNum, long provNum, Patient pat, String pdfDataBase64, String pdfDescription, boolean justPDF) throws Exception {
        OpenDentBusiness.HL7.EcwDFT dft = new OpenDentBusiness.HL7.EcwDFT();
        dft.initializeEcw(aptNum,provNum,pat,pdfDataBase64,pdfDescription,justPDF);
        HL7Msg msg = new HL7Msg();
        if (justPDF)
        {
            msg.AptNum = 0;
        }
        else
        {
            //Prevents the appt complete button from changing to the "Revise" button prematurely.
            msg.AptNum = aptNum;
        } 
        msg.HL7Status = HL7MessageStatus.OutPending;
        //it will be marked outSent by the HL7 service.
        msg.MsgText = dft.generateMessage();
        msg.PatNum = pat.PatNum;
        HL7Msgs.insert(msg);
    }

}


/**
* //Creates a new .pdf file containing all of the procedures attached to this appointment and
* //returns the contents of the .pdf file as a base64 encoded string.
*/
//public static string GenerateProceduresIntoPdf() {
//  MigraDoc.DocumentObjectModel.Document doc=new MigraDoc.DocumentObjectModel.Document();
//  doc.DefaultPageSetup.PageWidth=Unit.FromInch(8.5);
//  doc.DefaultPageSetup.PageHeight=Unit.FromInch(11);
//  doc.DefaultPageSetup.TopMargin=Unit.FromInch(.5);
//  doc.DefaultPageSetup.LeftMargin=Unit.FromInch(.5);
//  doc.DefaultPageSetup.RightMargin=Unit.FromInch(.5);
//  MigraDoc.DocumentObjectModel.Section section=doc.AddSection();
//  MigraDoc.DocumentObjectModel.Font headingFont=MigraDocHelper.CreateFont(13,true);
//  MigraDoc.DocumentObjectModel.Font bodyFontx=MigraDocHelper.CreateFont(9,false);
//  string text;
//  //Heading---------------------------------------------------------------------------------------------------------------
//  #region printHeading
//  Paragraph par=section.AddParagraph();
//  ParagraphFormat parformat=new ParagraphFormat();
//  parformat.Alignment=ParagraphAlignment.Center;
//  parformat.Font=MigraDocHelper.CreateFont(10,true);
//  par.Format=parformat;
//  text=Lan.g("ECW","procedures").ToUpper();
//  par.AddFormattedText(text,headingFont);
//  par.AddLineBreak();
//  text=pat.GetNameFLFormal();
//  par.AddFormattedText(text,headingFont);
//  par.AddLineBreak();
//  text=DateTime.Now.ToShortDateString();
//  par.AddFormattedText(text,headingFont);
//  par.AddLineBreak();
//  par.AddLineBreak();
//  #endregion
//  //Procedure List--------------------------------------------------------------------------------------------------------
//  #region Procedure List
//  ODGrid gridProg=new ODGrid();
//  //this.Controls.Add(gridProg);//Only added temporarily so that printing will work. Removed at end with Dispose().
//  gridProg.BeginUpdate();
//  gridProg.Columns.Clear();
//  ODGridColumn col;
//  List<DisplayField> fields=DisplayFields.GetForCategory(DisplayFieldCategory.None);
//  for(int i=0;i<fields.Count;i++) {
//    if(fields[i].InternalName=="User" || fields[i].InternalName=="Signed") {
//      continue;
//    }
//    if(fields[i].Description=="") {
//      col=new ODGridColumn(fields[i].InternalName,fields[i].ColumnWidth);
//    }
//    else {
//      col=new ODGridColumn(fields[i].Description,fields[i].ColumnWidth);
//    }
//    if(fields[i].InternalName=="Amount") {
//      col.TextAlign=HorizontalAlignment.Right;
//    }
//    if(fields[i].InternalName=="ADA Code") {
//      col.TextAlign=HorizontalAlignment.Center;
//    }
//    gridProg.Columns.Add(col);
//  }
//  gridProg.NoteSpanStart=2;
//  gridProg.NoteSpanStop=7;
//  gridProg.Rows.Clear();
//  List<Procedure> procsForDay=Procedures.GetProcsForPatByDate(AptCur.PatNum,AptCur.AptDateTime);
//  for(int i=0;i<procsForDay.Count;i++) {
//    Procedure proc=procsForDay[i];
//    ProcedureCode procCode=ProcedureCodes.GetProcCodeFromDb(proc.CodeNum);
//    Provider prov=Providers.GetProv(proc.ProvNum);
//    Userod usr=Userods.GetUser(proc.UserNum);
//    ODGridRow row=new ODGridRow();
//    row.ColorLborder=System.Drawing.Color.Black;
//    for(int f=0;f<fields.Count;f++) {
//      switch(fields[f].InternalName) {
//        case "Date":
//          row.Cells.Add(proc.ProcDate.Date.ToShortDateString());
//          break;
//        case "Time":
//          row.Cells.Add(proc.ProcDate.ToString("h:mm")+proc.ProcDate.ToString("%t").ToLower());
//          break;
//        case "Th":
//          row.Cells.Add(proc.ToothNum);
//          break;
//        case "Surf":
//          row.Cells.Add(proc.Surf);
//          break;
//        case "Dx":
//          row.Cells.Add(proc.Dx.ToString());
//          break;
//        case "Description":
//          row.Cells.Add((procCode.LaymanTerm!="")?procCode.LaymanTerm:procCode.Descript);
//          break;
//        case "Stat":
//          row.Cells.Add(Lans.g("enumProcStat",proc.ProcStatus.ToString()));
//          break;
//        case "Prov":
//          if(prov.Abbr.Length>5) {
//            row.Cells.Add(prov.Abbr.Substring(0,5));
//          }
//          else {
//            row.Cells.Add(prov.Abbr);
//          }
//          break;
//        case "Amount":
//          row.Cells.Add(proc.ProcFee.ToString("F"));
//          break;
//        case "ADA Code":
//          if(procCode.ProcCode.Length>5 && procCode.ProcCode.StartsWith("D")) {
//            row.Cells.Add(procCode.ProcCode.Substring(0,5));//Remove suffix from all D codes.
//          }
//          else {
//            row.Cells.Add(procCode.ProcCode);
//          }
//          break;
//        case "User":
//          row.Cells.Add(usr!=null?usr.UserName:"");
//          break;
//      }
//    }
//    row.Note=proc.Note;
//    //Row text color.
//    switch(proc.ProcStatus) {
//      case ProcStat.TP:
//        row.ColorText=DefC.Long[(int)DefCat.ProgNoteColors][0].ItemColor;
//        break;
//      case ProcStat.C:
//        row.ColorText=DefC.Long[(int)DefCat.ProgNoteColors][1].ItemColor;
//        break;
//      case ProcStat.EC:
//        row.ColorText=DefC.Long[(int)DefCat.ProgNoteColors][2].ItemColor;
//        break;
//      case ProcStat.EO:
//        row.ColorText=DefC.Long[(int)DefCat.ProgNoteColors][3].ItemColor;
//        break;
//      case ProcStat.R:
//        row.ColorText=DefC.Long[(int)DefCat.ProgNoteColors][4].ItemColor;
//        break;
//      case ProcStat.D:
//        row.ColorText=System.Drawing.Color.Black;
//        break;
//      case ProcStat.Cn:
//        row.ColorText=DefC.Long[(int)DefCat.ProgNoteColors][22].ItemColor;
//        break;
//    }
//    row.ColorBackG=System.Drawing.Color.White;
//    if(proc.ProcDate.Date==DateTime.Today) {
//      row.ColorBackG=DefC.Long[(int)DefCat.MiscColors][6].ItemColor;
//    }
//    gridProg.Rows.Add(row);
//  }
//  MigraDocHelper.DrawGrid(section,gridProg);
//  #endregion
//  MigraDoc.Rendering.PdfDocumentRenderer pdfRenderer=new MigraDoc.Rendering.PdfDocumentRenderer(true,PdfFontEmbedding.Always);
//  pdfRenderer.Document=doc;
//  pdfRenderer.RenderDocument();
//  MemoryStream ms=new MemoryStream();
//  pdfRenderer.PdfDocument.Save(ms);
//  byte[] pdfBytes=ms.GetBuffer();
//  //#region Remove when testing is complete.
//  //string tempFilePath=Path.GetTempFileName();
//  //File.WriteAllBytes(tempFilePath,pdfBytes);
//  //#endregion
//  string pdfDataStr=Convert.ToBase64String(pdfBytes);
//  ms.Dispose();
//  return pdfDataStr;
//}