//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:25 PM
//

package OpenDental;

import OpenDental.GraphicsHelper;
import OpenDental.SheetFiller;
import OpenDentBusiness.GrowthBehaviorEnum;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.Sheet;
import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetField;
import OpenDentBusiness.SheetFieldDef;
import OpenDentBusiness.SheetFieldType;
import OpenDentBusiness.SheetParameter;

public class SheetUtil   
{
    /**
    * Supply a template sheet as well as a list of primary keys.  This method creates a new collection of sheets which each have a parameter of int.  It also fills the sheets with data from the database, so no need to run that separately.
    */
    public static List<Sheet> createBatch(SheetDef sheetDef, List<long> priKeys) throws Exception {
        //we'll assume for now that a batch sheet has only one parameter, so no need to check for values.
        //foreach(SheetParameter param in sheet.Parameters){
        //	if(param.IsRequired && param.ParamValue==null){
        //		throw new ApplicationException(Lan.g("Sheet","Parameter not specified for sheet: ")+param.ParamName);
        //	}
        //}
        List<Sheet> retVal = new List<Sheet>();
        //List<int> paramVals=(List<int>)sheet.Parameters[0].ParamValue;
        Sheet newSheet;
        SheetParameter paramNew;
        for (int i = 0;i < priKeys.Count;i++)
        {
            newSheet = createSheet(sheetDef);
            newSheet.Parameters = new List<SheetParameter>();
            paramNew = new SheetParameter(sheetDef.Parameters[0].IsRequired, sheetDef.Parameters[0].ParamName);
            paramNew.ParamValue = priKeys[i];
            newSheet.Parameters.Add(paramNew);
            SheetFiller.fillFields(newSheet);
            retVal.Add(newSheet);
        }
        return retVal;
    }

    /**
    * Just before printing or displaying the final sheet output, the heights and y positions of various fields are adjusted according to their growth behavior.  This also now gets run every time a user changes the value of a textbox while filling out a sheet.
    */
    public static void calculateHeights(Sheet sheet, Graphics g) throws Exception {
        //Sheet sheetCopy=sheet.Clone();
        int calcH = new int();
        Font font = new Font();
        FontStyle fontstyle = new FontStyle();
        for (Object __dummyForeachVar0 : sheet.SheetFields)
        {
            SheetField field = (SheetField)__dummyForeachVar0;
            if (field.GrowthBehavior == GrowthBehaviorEnum.None)
            {
                continue;
            }
             
            fontstyle = FontStyle.Regular;
            if (field.FontIsBold)
            {
                fontstyle = FontStyle.Bold;
            }
             
            font = new Font(field.FontName, field.FontSize, fontstyle);
            //calcH=(int)g.MeasureString(field.FieldValue,font).Height;//this was too short
            calcH = GraphicsHelper.measureStringH(g,field.FieldValue,font,field.Width);
            if (calcH <= field.Height)
            {
                continue;
            }
             
            int amountOfGrowth = calcH - field.Height;
            field.Height = calcH;
            if (field.GrowthBehavior == GrowthBehaviorEnum.DownLocal)
            {
                moveAllDownWhichIntersect(sheet,field,amountOfGrowth);
            }
            else if (field.GrowthBehavior == GrowthBehaviorEnum.DownGlobal)
            {
                moveAllDownBelowThis(sheet,field,amountOfGrowth);
            }
              
        }
    }

    //g.Dispose();
    //return sheetCopy;
    public static void moveAllDownBelowThis(Sheet sheet, SheetField field, int amountOfGrowth) throws Exception {
        for (Object __dummyForeachVar1 : sheet.SheetFields)
        {
            SheetField field2 = (SheetField)__dummyForeachVar1;
            if (field2.YPos > field.YPos)
            {
                //for all fields that are below this one
                field2.YPos += amountOfGrowth;
            }
             
        }
    }

    //bump down by amount that this one grew
    /**
    * Supply the field that we are testing.  All other fields which intersect with it will be moved down.  Each time one (or maybe some) is moved down, this method is called recursively.  The end result should be no intersections among fields near the original field that grew.
    */
    public static void moveAllDownWhichIntersect(Sheet sheet, SheetField field, int amountOfGrowth) throws Exception {
        //Phase 1 is to move everything that intersects with the field down. Phase 2 is to call this method on everything that was moved.
        //Phase 1: Move
        List<SheetField> affectedFields = new List<SheetField>();
        for (Object __dummyForeachVar2 : sheet.SheetFields)
        {
            SheetField field2 = (SheetField)__dummyForeachVar2;
            if (field2 == field)
            {
                continue;
            }
             
            if (field2.YPos < field.YPos)
            {
                continue;
            }
             
            //only fields which are below this one
            if (field2.FieldType == SheetFieldType.Drawing)
            {
                continue;
            }
             
            //drawings do not get moved down.
            if (field.getBounds().IntersectsWith(field2.getBounds()))
            {
                field2.YPos += amountOfGrowth;
                affectedFields.Add(field2);
            }
             
        }
        for (Object __dummyForeachVar3 : affectedFields)
        {
            //Phase 2: Recursion
            SheetField field2 = (SheetField)__dummyForeachVar3;
            //reuse the same amountOfGrowth again.
            moveAllDownWhichIntersect(sheet,field2,amountOfGrowth);
        }
    }

    /**
    * Creates a Sheet object from a sheetDef, complete with fields and parameters.  This overload is only to be used when the sheet will not be saved to the database, such as for labels
    */
    public static Sheet createSheet(SheetDef sheetDef) throws Exception {
        return CreateSheet(sheetDef, 0);
    }

    /**
    * Creates a Sheet object from a sheetDef, complete with fields and parameters.  Sets date to today.
    */
    public static Sheet createSheet(SheetDef sheetDef, long patNum) throws Exception {
        Sheet sheet = new Sheet();
        sheet.setIsNew(true);
        sheet.DateTimeSheet = DateTime.Now;
        sheet.FontName = sheetDef.FontName;
        sheet.FontSize = sheetDef.FontSize;
        sheet.Height = sheetDef.Height;
        sheet.SheetType = sheetDef.SheetType;
        sheet.Width = sheetDef.Width;
        sheet.PatNum = patNum;
        sheet.Description = sheetDef.Description;
        sheet.IsLandscape = sheetDef.IsLandscape;
        sheet.SheetFields = createFieldList(sheetDef.SheetFieldDefs);
        sheet.Parameters = sheetDef.Parameters;
        return sheet;
    }

    /*
    		///<summary>After pulling a list of SheetFieldData objects from the database, we use this to convert it to a list of SheetFields as we create the Sheet.</summary>
    		public static List<SheetField> CreateSheetFields(List<SheetFieldData> sheetFieldDataList){
    			List<SheetField> retVal=new List<SheetField>();
    			SheetField field;
    			FontStyle style;
    			for(int i=0;i<sheetFieldDataList.Count;i++){
    				style=FontStyle.Regular;
    				if(sheetFieldDataList[i].FontIsBold){
    					style=FontStyle.Bold;
    				}
    				field=new SheetField(sheetFieldDataList[i].FieldType,sheetFieldDataList[i].FieldName,sheetFieldDataList[i].FieldValue,
    					sheetFieldDataList[i].XPos,sheetFieldDataList[i].YPos,sheetFieldDataList[i].Width,sheetFieldDataList[i].Height,
    					new Font(sheetFieldDataList[i].FontName,sheetFieldDataList[i].FontSize,style),sheetFieldDataList[i].GrowthBehavior);
    				retVal.Add(field);
    			}
    			return retVal;
    		}*/
    /**
    * Creates the initial fields from the sheetDef.FieldDefs.
    */
    private static List<SheetField> createFieldList(List<SheetFieldDef> sheetFieldDefList) throws Exception {
        List<SheetField> retVal = new List<SheetField>();
        SheetField field;
        for (int i = 0;i < sheetFieldDefList.Count;i++)
        {
            field = new SheetField();
            field.setIsNew(true);
            field.FieldName = sheetFieldDefList[i].FieldName;
            field.FieldType = sheetFieldDefList[i].FieldType;
            field.FieldValue = sheetFieldDefList[i].FieldValue;
            field.FontIsBold = sheetFieldDefList[i].FontIsBold;
            field.FontName = sheetFieldDefList[i].FontName;
            field.FontSize = sheetFieldDefList[i].FontSize;
            field.GrowthBehavior = sheetFieldDefList[i].GrowthBehavior;
            field.Height = sheetFieldDefList[i].Height;
            field.RadioButtonValue = sheetFieldDefList[i].RadioButtonValue;
            //field.SheetNum=sheetFieldList[i];//set later
            field.Width = sheetFieldDefList[i].Width;
            field.XPos = sheetFieldDefList[i].XPos;
            field.YPos = sheetFieldDefList[i].YPos;
            field.RadioButtonGroup = sheetFieldDefList[i].RadioButtonGroup;
            field.IsRequired = sheetFieldDefList[i].IsRequired;
            field.TabOrder = sheetFieldDefList[i].TabOrder;
            field.ReportableName = sheetFieldDefList[i].ReportableName;
            retVal.Add(field);
        }
        return retVal;
    }

    /**
    * Typically returns something similar to \\SERVER\OpenDentImages\SheetImages
    */
    public static String getImagePath() throws Exception {
        String imagePath = new String();
        if (!PrefC.getAtoZfolderUsed())
        {
            throw new ApplicationException("Must be using AtoZ folders.");
        }
         
        imagePath = CodeBase.ODFileUtils.combinePaths(ImageStore.getPreferredAtoZpath(),"SheetImages");
        if (!Directory.Exists(imagePath))
        {
            Directory.CreateDirectory(imagePath);
        }
         
        return imagePath;
    }

}


