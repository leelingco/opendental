//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:59 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Lans;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Tooth;
import OpenDentBusiness.ToothComparer;
import OpenDentBusiness.ToothNumberingNomenclature;

/**
* 
*/
public class Tooth   
{
    /**
    * 
    */
    public Tooth() throws Exception {
    }

    public static String[] labelsUniversal = new String[]{ "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "32", "31", "30", "29", "28", "27", "26", "25", "24", "23", "22", "21", "20", "19", "18", "17", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "T", "S", "R", "Q", "P", "O", "N", "M", "L", "K" };
    private static String[] labelsFDI = new String[]{ "18", "17", "16", "15", "14", "13", "12", "11", "21", "22", "23", "24", "25", "26", "27", "28", "48", "47", "46", "45", "44", "43", "42", "41", "31", "32", "33", "34", "35", "36", "37", "38", "55", "54", "53", "52", "51", "61", "62", "63", "64", "65", "85", "84", "83", "82", "81", "71", "72", "73", "74", "75" };
    private static String[] labelsHaderup = new String[]{ "8+", "7+", "6+", "5+", "4+", "3+", "2+", "1+", "+1", "+2", "+3", "+4", "+5", "+6", "+7", "+8", "8-", "7-", "6-", "5-", "4-", "3-", "2-", "1-", "-1", "-2", "-3", "-4", "-5", "-6", "-7", "-8", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "T", "S", "R", "Q", "P", "O", "N", "M", "L", "K" };
    private static String[] labelsPalmer = new String[]{ "UR8", "UR7", "UR6", "UR5", "UR4", "UR3", "UR2", "UR1", "UL1", "UL2", "UL3", "UL4", "UL5", "UL6", "UL7", "UL8", "LR8", "LR7", "LR6", "LR5", "LR4", "LR3", "LR2", "LR1", "LL1", "LL2", "LL3", "LL4", "LL5", "LL6", "LL7", "LL8", "URE", "URD", "URC", "URB", "URA", "ULA", "ULB", "ULC", "ULD", "ULE", "LRE", "LRD", "LRC", "LRB", "LRA", "LLA", "LLB", "LLC", "LLD", "LLE" };
    public static String[] labelsPalmerSimple = new String[]{ "8", "7", "6", "5", "4", "3", "2", "1", "1", "2", "3", "4", "5", "6", "7", "8", "8", "7", "6", "5", "4", "3", "2", "1", "1", "2", "3", "4", "5", "6", "7", "8", "E", "D", "C", "B", "A", "A", "B", "C", "D", "E", "E", "D", "C", "B", "A", "A", "B", "C", "D", "E" };
    /**
    * 
    */
    public static boolean isAnterior(String tooth_id) throws Exception {
        if (!isValidDB(tooth_id))
        {
            return false;
        }
         
        int intTooth = toInt(tooth_id);
        if (intTooth >= 6 && intTooth <= 11)
        {
            return true;
        }
         
        if (intTooth >= 22 && intTooth <= 27)
        {
            return true;
        }
         
        return false;
    }

    /**
    * 
    */
    public static boolean isAnterior(int intTooth) throws Exception {
        String toothNum = fromInt(intTooth);
        return isAnterior(toothNum);
    }

    /**
    * 
    */
    public static boolean isPosterior(String toothNum) throws Exception {
        if (!isValidDB(toothNum))
            return false;
         
        int intTooth = toInt(toothNum);
        if (intTooth >= 1 && intTooth <= 5)
            return true;
         
        if (intTooth >= 12 && intTooth <= 21)
            return true;
         
        if (intTooth >= 28 && intTooth <= 32)
            return true;
         
        return false;
    }

    /**
    * 
    */
    public static boolean isPosterior(int intTooth) throws Exception {
        String toothNum = fromInt(intTooth);
        return isPosterior(toothNum);
    }

    /**
    * toothNum gets validated here.
    */
    public static boolean isMolar(String toothNum) throws Exception {
        if (!isValidDB(toothNum))
        {
            return false;
        }
         
        int intTooth = toInt(toothNum);
        if (isPrimary(toothNum))
        {
            if (intTooth <= 5 || intTooth >= 28)
            {
                return true;
            }
             
            //AB, ST
            if (intTooth >= 12 && intTooth <= 21)
            {
                return true;
            }
             
            return false;
        }
         
        //IJ, KL
        if (intTooth >= 1 && intTooth <= 3)
        {
            return true;
        }
         
        if (intTooth >= 14 && intTooth <= 19)
        {
            return true;
        }
         
        if (intTooth >= 30 && intTooth <= 32)
        {
            return true;
        }
         
        return false;
    }

    /**
    * 
    */
    public static boolean isMolar(int intTooth) throws Exception {
        String toothNum = fromInt(intTooth);
        return isMolar(toothNum);
    }

    /**
    * toothNum gets validated here. Used for FGC insurance substitutions.
    */
    public static boolean isSecondMolar(String toothNum) throws Exception {
        if (!isValidDB(toothNum))
            return false;
         
        int intTooth = toInt(toothNum);
        if (intTooth == 2 || intTooth == 15 || intTooth == 18 || intTooth == 31)
        {
            return true;
        }
         
        return false;
    }

    /**
    * 
    */
    public static boolean isPreMolar(String toothNum) throws Exception {
        if (!isValidDB(toothNum))
            return false;
         
        int intTooth = toInt(toothNum);
        if (intTooth == 4 || intTooth == 5 || intTooth == 12 || intTooth == 13 || intTooth == 20 || intTooth == 21 || intTooth == 28 || intTooth == 29)
            return true;
         
        return false;
    }

    /**
    * 
    */
    public static boolean isPreMolar(int intTooth) throws Exception {
        String toothNum = fromInt(intTooth);
        return isPreMolar(toothNum);
    }

    /**
    * Sometimes validated by IsValidDB before coming here, otherwise an invalid toothnum .  This should be run on all displayed tooth numbers. It will handle checking for whether user is using international tooth numbers.  All tooth numbers are passed in american values until the very last moment.  Just before display, the string is converted using this method.
    */
    public static String getToothLabel(String tooth_id, ToothNumberingNomenclature nomenclature) throws Exception {
        if (tooth_id == null || StringSupport.equals(tooth_id, ""))
        {
            return "";
        }
         
        // CWI: We should fix the source of these
        //int nomenclature = PrefC.GetInt(PrefName.UseInternationalToothNumbers);
        if (nomenclature == ToothNumberingNomenclature.Universal)
        {
            return tooth_id;
        }
         
        int index = Array.IndexOf(labelsUniversal, tooth_id);
        if (index == -1)
        {
            return "-";
        }
         
        if (nomenclature == ToothNumberingNomenclature.FDI)
        {
            return labelsFDI[index];
        }
        else if (nomenclature == ToothNumberingNomenclature.Haderup)
        {
            return labelsHaderup[index];
        }
        else if (nomenclature == ToothNumberingNomenclature.Palmer)
        {
            return labelsPalmer[index];
        }
           
        return "-";
    }

    // Should never happen
    public static String getToothLabel(String tooth_id) throws Exception {
        return getToothLabel(tooth_id,ToothNumberingNomenclature.values()[PrefC.getInt(PrefName.UseInternationalToothNumbers)]);
    }

    /**
    * Identical to GetToothLabel, but just used in the 3D tooth chart because with Palmer, we don't want the UR, UL, etc.
    */
    public static String getToothLabelGraphic(String tooth_id, ToothNumberingNomenclature nomenclature) throws Exception {
        if (tooth_id == null || StringSupport.equals(tooth_id, ""))
        {
            return "";
        }
         
        // CWI: We should fix the source of these
        //int nomenclature = PrefC.GetInt(PrefName.UseInternationalToothNumbers);
        if (nomenclature == ToothNumberingNomenclature.Universal)
        {
            return tooth_id;
        }
         
        int index = Array.IndexOf(labelsUniversal, tooth_id);
        if (index == -1)
        {
            return "-";
        }
         
        if (nomenclature == ToothNumberingNomenclature.FDI)
        {
            return labelsFDI[index];
        }
        else if (nomenclature == ToothNumberingNomenclature.Haderup)
        {
            return labelsHaderup[index];
        }
        else if (nomenclature == ToothNumberingNomenclature.Palmer)
        {
            return labelsPalmerSimple[index];
        }
           
        return "-";
    }

    // Should never happen
    public static String getToothLabelGraphic(String tooth_id) throws Exception {
        return getToothLabelGraphic(tooth_id,ToothNumberingNomenclature.values()[PrefC.getInt(PrefName.UseInternationalToothNumbers)]);
    }

    /**
    * MUST be validated by IsValidEntry before coming here.  All user entered toothnumbers are run through this method which automatically checks to see if using international toothnumbers.  So the procedurelog class will always contain the american toothnum.
    */
    public static String getToothId(String tooth_label) throws Exception {
        ToothNumberingNomenclature nomenclature = ToothNumberingNomenclature.values()[PrefC.getInt(PrefName.UseInternationalToothNumbers)];
        if (nomenclature == ToothNumberingNomenclature.Universal)
        {
            return tooth_label;
        }
         
        int index = 0;
        if (nomenclature == ToothNumberingNomenclature.FDI)
        {
            index = Array.IndexOf(labelsFDI, tooth_label);
        }
        else if (nomenclature == ToothNumberingNomenclature.Haderup)
        {
            index = Array.IndexOf(labelsHaderup, tooth_label);
        }
        else if (nomenclature == ToothNumberingNomenclature.Palmer)
        {
            index = Array.IndexOf(labelsPalmer, tooth_label);
        }
           
        return labelsUniversal[index];
    }

    /**
    * Get quadrant returns "UR" for teeth 1-8, "LR" for 25-32, "UL" for 9-16, and "LL" for 17-24.
    */
    public static String getQuadrant(String toothNum) throws Exception {
        if (!isValidDB(toothNum))
        {
            return "";
        }
         
        int intTooth = toInt(toothNum);
        if (intTooth >= 1 && intTooth <= 8)
            return "UR";
         
        if (intTooth >= 9 && intTooth <= 16)
            return "UL";
         
        if (intTooth >= 17 && intTooth <= 24)
            return "LL";
         
        if (intTooth >= 25 && intTooth <= 32)
            return "LR";
         
        return "";
    }

    /**
    * Sometimes validated by IsValidDB before coming here, otherwise an invalid toothnum .  This should be run on all displayed tooth numbers. It will handle checking for whether user is using international tooth numbers.  All tooth numbers are passed in american values until the very last moment.  Just before display, the string is converted using this method.
    */
    public static String toInternat(String toothNum) throws Exception {
        return getToothLabel(toothNum,ToothNumberingNomenclature.values()[PrefC.getInt(PrefName.UseInternationalToothNumbers)]);
    }

    // CWI: Left for compatibility
    /**
    * MUST be validated by IsValidEntry before coming here.  All user entered toothnumbers are run through this method which automatically checks to see if using international toothnumbers.  So the procedurelog class will always contain the american toothnum.
    */
    public static String fromInternat(String toothNum) throws Exception {
        return getToothId(toothNum);
    }

    // CWI: Left for compatibility
    /**
    * The supplied toothNumbers will be a series of tooth numbers separated by commas.  They will be in american format..  For display purposes, ranges will use dashes, and international numbers will be used.
    */
    public static String formatRangeForDisplay(String toothNumbers) throws Exception {
        if (toothNumbers == null)
        {
            return "";
        }
         
        toothNumbers = toothNumbers.Replace(" ", "");
        //remove all spaces
        if (StringSupport.equals(toothNumbers, ""))
        {
            return "";
        }
         
        String[] toothArray = toothNumbers.Split(',');
        if (toothArray.Length == 1)
        {
            return Tooth.GetToothLabel(toothArray[0]);
        }
        else if (toothArray.Length == 2)
        {
            return Tooth.GetToothLabel(toothArray[0]) + "," + Tooth.GetToothLabel(toothArray[1]);
        }
          
        //just two numbers separated by comma
        Array.<String>Sort(toothArray, new ToothComparer());
        StringBuilder strbuild = new StringBuilder();
        //List<string> toothList=new List<string>();
        //strbuild.Append(Tooth.ToInternat(toothArray[0]));//always show the first number
        int currentNum = new int();
        int nextNum = new int();
        int numberInaRow = 1;
        for (int i = 0;i < toothArray.Length - 1;i++)
        {
            //must have 3 in a row to trigger dash
            //in each loop, we are comparing the current number with the next number
            currentNum = Tooth.ToOrdinal(toothArray[i]);
            nextNum = Tooth.ToOrdinal(toothArray[i + 1]);
            if (nextNum - currentNum == 1 && currentNum != 16 && currentNum != 32)
            {
                //if sequential (sequences always break at end of arch)
                numberInaRow++;
            }
            else
            {
                numberInaRow = 1;
            } 
            if (numberInaRow < 3)
            {
                //the next number is not sequential,or if it was a sequence, and it's now broken
                if (strbuild.Length > 0 && strbuild[strbuild.Length - 1] != '-')
                {
                    strbuild.Append(",");
                }
                 
                strbuild.Append(Tooth.GetToothLabel(toothArray[i]));
            }
            else if (numberInaRow == 3)
            {
                //this way, the dash only gets added exactly once
                strbuild.Append("-");
            }
              
        }
        //else do nothing
        if (strbuild.Length > 0 && strbuild[strbuild.Length - 1] != '-')
        {
            strbuild.Append(",");
        }
         
        strbuild.Append(Tooth.GetToothLabel(toothArray[toothArray.Length - 1]));
        return strbuild.ToString();
    }

    //always show the last number
    /**
    * Takes a user entered string and validates/formats it for the database.  Throws an ApplicationException if any formatting errors.  User string can contain spaces, dashes, and commas, too.
    */
    public static String formatRangeForDb(String toothNumbers) throws Exception {
        if (toothNumbers == null)
        {
            return "";
        }
         
        toothNumbers = toothNumbers.Replace(" ", "");
        //remove all spaces
        if (StringSupport.equals(toothNumbers, ""))
        {
            return "";
        }
         
        String[] toothArray = toothNumbers.Split(',');
        //some items will contain dashes
        List<String> toothList = new List<String>();
        String rangebegin = new String();
        String rangeend = new String();
        int beginint = new int();
        int endint = new int();
        for (int i = 0;i < toothArray.Length;i++)
        {
            //not sure how to handle supernumerary.  Probably just not acceptable.
            if (toothArray[i].Contains("-"))
            {
                rangebegin = toothArray[i].Split('-')[0];
                rangeend = toothArray[i].Split('-')[1];
                if (!isValidEntry(rangebegin))
                {
                    throw new ApplicationException(rangebegin + " " + Lans.g("Tooth","is not a valid tooth number."));
                }
                 
                if (!isValidEntry(rangeend))
                {
                    throw new ApplicationException(rangeend + " " + Lans.g("Tooth","is not a valid tooth number."));
                }
                 
                beginint = Tooth.toOrdinal(getToothId(rangebegin));
                endint = Tooth.toInt(getToothId(rangeend));
                if (endint < beginint)
                {
                    throw new ApplicationException("Range specified is impossible.");
                }
                 
                while (beginint <= endint)
                {
                    toothList.Add(Tooth.fromOrdinal(beginint));
                    beginint++;
                }
            }
            else
            {
                if (!IsValidEntry(toothArray[i]))
                {
                    throw new ApplicationException(toothArray[i] + " " + Lans.g("Tooth","is not a valid tooth number."));
                }
                 
                toothList.Add(Tooth.GetToothId(toothArray[i]));
            } 
        }
        toothList.Sort(new ToothComparer());
        String retVal = "";
        for (int i = 0;i < toothList.Count;i++)
        {
            if (i > 0)
            {
                retVal += ",";
            }
             
            retVal += toothList[i];
        }
        return retVal;
    }

    /**
    * Used every time user enters toothNum in procedure box. Must be followed with FromInternat. These are the *ONLY* methods that are designed to accept user input.  Can also handle international toothnum
    */
    public static boolean isValidEntry(String toothNum) throws Exception {
        int nomenclature = PrefC.getInt(PrefName.UseInternationalToothNumbers);
        if (nomenclature == 0)
        {
            return isValidDB(toothNum);
        }
        else //Universal,american
        //tooth numbers validated the same as they are in db.
        if (nomenclature == 1)
        {
            // FDI
            if (toothNum == null || StringSupport.equals(toothNum, ""))
            {
                return false;
            }
             
            if (Regex.IsMatch(toothNum, "^[1-4][1-8]$"))
            {
                return true;
            }
             
            //perm teeth: matches firt digit 1-4 and second digit 1-8,9 would be supernumerary?
            if (Regex.IsMatch(toothNum, "^[5-8][1-5]$"))
            {
                return true;
            }
             
            return false;
        }
        else //pri teeth: matches firt digit 5-8 and second digit 1-5
        if (nomenclature == 2)
        {
            //Haderup
            if (toothNum == null || StringSupport.equals(toothNum, ""))
            {
                return false;
            }
             
            for (int i = 0;i < labelsHaderup.Length;i++)
            {
                if (StringSupport.equals(labelsHaderup[i], toothNum))
                {
                    return true;
                }
                 
            }
            return false;
        }
        else
        {
            // if(nomenclature==3) {// Palmer
            if (toothNum == null || StringSupport.equals(toothNum, ""))
            {
                return false;
            }
             
            for (int i = 0;i < labelsPalmer.Length;i++)
            {
                if (StringSupport.equals(labelsPalmer[i], toothNum))
                {
                    return true;
                }
                 
            }
            return false;
        }   
    }

    /**
    * Intended to validate toothNum coming in from database. Will not handle any international tooth nums since all database teeth are in US format.
    */
    public static boolean isValidDB(String toothNum) throws Exception {
        if (toothNum == null || StringSupport.equals(toothNum, ""))
            return false;
         
        if (Regex.IsMatch(toothNum, "^[A-T]$"))
            return true;
         
        if (Regex.IsMatch(toothNum, "^[A-T]S$"))
            return true;
         
        //supernumerary
        if (!Regex.IsMatch(toothNum, "^[1-9]\\d?$"))
        {
            return false;
        }
         
        //matches 1 or 2 digits, leading 0 not allowed
        int intTooth = Convert.ToInt32(toothNum);
        if (intTooth <= 32)
        {
            return true;
        }
         
        if (intTooth >= 51 && intTooth <= 82)
        {
            return true;
        }
         
        return false;
    }

    //supernumerary
    /**
    * 
    */
    public static boolean isSuperNum(String toothNum) throws Exception {
        if (toothNum == null || StringSupport.equals(toothNum, ""))
            return false;
         
        if (Regex.IsMatch(toothNum, "^[A-T]$"))
            return false;
         
        if (Regex.IsMatch(toothNum, "^[A-T]S$"))
            return true;
         
        //supernumerary
        if (!Regex.IsMatch(toothNum, "^[1-9]\\d?$"))
        {
            return false;
        }
         
        //matches 1 or 2 digits, leading 0 not allowed
        int intTooth = Convert.ToInt32(toothNum);
        if (intTooth <= 32)
            return false;
         
        if (intTooth >= 51 && intTooth <= 82)
            return true;
         
        return false;
    }

    //supernumerary
    /**
    * Returns 1-32, or -1.  The toothNum should be validated before coming here, but it won't crash if invalid.  Primary or perm are ok.  Empty and null are also ok.  Supernumerary are also ok.
    */
    public static int toInt(String tooth_id) throws Exception {
        if (tooth_id == null || StringSupport.equals(tooth_id, ""))
            return -1;
         
        try
        {
            if (isPrimary(tooth_id))
            {
                return Convert.ToInt32(priToPerm(tooth_id));
            }
            else if (isSuperNum(tooth_id))
            {
                return Convert.ToInt32(supToPerm(tooth_id));
            }
            else
            {
                return Convert.ToInt32(tooth_id);
            }  
        }
        catch (Exception __dummyCatchVar0)
        {
            return -1;
        }
    
    }

    /**
    * 
    */
    public static String fromInt(int intTooth) throws Exception {
        //don't need much error checking.
        String retStr = "";
        retStr = intTooth.ToString();
        return retStr;
    }

    /**
    * Returns true if A-T or AS-TS.  Otherwise, returns false.
    */
    public static boolean isPrimary(String tooth_id) throws Exception {
        if (Regex.IsMatch(tooth_id, "^[A-T]$"))
        {
            return true;
        }
         
        if (Regex.IsMatch(tooth_id, "^[A-T]S$"))
        {
            return true;
        }
         
        return false;
    }

    /**
    * 
    */
    public static String permToPri(String tooth_id) throws Exception {
        System.String __dummyScrutVar0 = tooth_id;
        if (__dummyScrutVar0.equals("4"))
        {
            return "A";
        }
        else if (__dummyScrutVar0.equals("5"))
        {
            return "B";
        }
        else if (__dummyScrutVar0.equals("6"))
        {
            return "C";
        }
        else if (__dummyScrutVar0.equals("7"))
        {
            return "D";
        }
        else if (__dummyScrutVar0.equals("8"))
        {
            return "E";
        }
        else if (__dummyScrutVar0.equals("9"))
        {
            return "F";
        }
        else if (__dummyScrutVar0.equals("10"))
        {
            return "G";
        }
        else if (__dummyScrutVar0.equals("11"))
        {
            return "H";
        }
        else if (__dummyScrutVar0.equals("12"))
        {
            return "I";
        }
        else if (__dummyScrutVar0.equals("13"))
        {
            return "J";
        }
        else if (__dummyScrutVar0.equals("20"))
        {
            return "K";
        }
        else if (__dummyScrutVar0.equals("21"))
        {
            return "L";
        }
        else if (__dummyScrutVar0.equals("22"))
        {
            return "M";
        }
        else if (__dummyScrutVar0.equals("23"))
        {
            return "N";
        }
        else if (__dummyScrutVar0.equals("24"))
        {
            return "O";
        }
        else if (__dummyScrutVar0.equals("25"))
        {
            return "P";
        }
        else if (__dummyScrutVar0.equals("26"))
        {
            return "Q";
        }
        else if (__dummyScrutVar0.equals("27"))
        {
            return "R";
        }
        else if (__dummyScrutVar0.equals("28"))
        {
            return "S";
        }
        else if (__dummyScrutVar0.equals("29"))
        {
            return "T";
        }
        else if (__dummyScrutVar0.equals("54"))
        {
            return "AS";
        }
        else if (__dummyScrutVar0.equals("55"))
        {
            return "BS";
        }
        else if (__dummyScrutVar0.equals("56"))
        {
            return "CS";
        }
        else if (__dummyScrutVar0.equals("57"))
        {
            return "DS";
        }
        else if (__dummyScrutVar0.equals("58"))
        {
            return "ES";
        }
        else if (__dummyScrutVar0.equals("59"))
        {
            return "FS";
        }
        else if (__dummyScrutVar0.equals("60"))
        {
            return "GS";
        }
        else if (__dummyScrutVar0.equals("61"))
        {
            return "HS";
        }
        else if (__dummyScrutVar0.equals("62"))
        {
            return "IS";
        }
        else if (__dummyScrutVar0.equals("63"))
        {
            return "JS";
        }
        else if (__dummyScrutVar0.equals("70"))
        {
            return "KS";
        }
        else if (__dummyScrutVar0.equals("71"))
        {
            return "LS";
        }
        else if (__dummyScrutVar0.equals("72"))
        {
            return "MS";
        }
        else if (__dummyScrutVar0.equals("73"))
        {
            return "NS";
        }
        else if (__dummyScrutVar0.equals("74"))
        {
            return "OS";
        }
        else if (__dummyScrutVar0.equals("75"))
        {
            return "PS";
        }
        else if (__dummyScrutVar0.equals("76"))
        {
            return "QS";
        }
        else if (__dummyScrutVar0.equals("77"))
        {
            return "RS";
        }
        else if (__dummyScrutVar0.equals("78"))
        {
            return "SS";
        }
        else if (__dummyScrutVar0.equals("79"))
        {
            return "TS";
        }
        else
        {
            return "";
        }                                        
    }

    /**
    * 
    */
    public static String permToPri(int intTooth) throws Exception {
        String tooth_id = fromInt(intTooth);
        return permToPri(tooth_id);
    }

    /**
    * 
    */
    public static String priToPerm(String tooth_id) throws Exception {
        System.String __dummyScrutVar1 = tooth_id;
        if (__dummyScrutVar1.equals("A"))
        {
            return "4";
        }
        else if (__dummyScrutVar1.equals("B"))
        {
            return "5";
        }
        else if (__dummyScrutVar1.equals("C"))
        {
            return "6";
        }
        else if (__dummyScrutVar1.equals("D"))
        {
            return "7";
        }
        else if (__dummyScrutVar1.equals("E"))
        {
            return "8";
        }
        else if (__dummyScrutVar1.equals("F"))
        {
            return "9";
        }
        else if (__dummyScrutVar1.equals("G"))
        {
            return "10";
        }
        else if (__dummyScrutVar1.equals("H"))
        {
            return "11";
        }
        else if (__dummyScrutVar1.equals("I"))
        {
            return "12";
        }
        else if (__dummyScrutVar1.equals("J"))
        {
            return "13";
        }
        else if (__dummyScrutVar1.equals("K"))
        {
            return "20";
        }
        else if (__dummyScrutVar1.equals("L"))
        {
            return "21";
        }
        else if (__dummyScrutVar1.equals("M"))
        {
            return "22";
        }
        else if (__dummyScrutVar1.equals("N"))
        {
            return "23";
        }
        else if (__dummyScrutVar1.equals("O"))
        {
            return "24";
        }
        else if (__dummyScrutVar1.equals("P"))
        {
            return "25";
        }
        else if (__dummyScrutVar1.equals("Q"))
        {
            return "26";
        }
        else if (__dummyScrutVar1.equals("R"))
        {
            return "27";
        }
        else if (__dummyScrutVar1.equals("S"))
        {
            return "28";
        }
        else if (__dummyScrutVar1.equals("T"))
        {
            return "29";
        }
        else if (__dummyScrutVar1.equals("AS"))
        {
            return "4";
        }
        else if (__dummyScrutVar1.equals("BS"))
        {
            return "5";
        }
        else if (__dummyScrutVar1.equals("CS"))
        {
            return "6";
        }
        else if (__dummyScrutVar1.equals("DS"))
        {
            return "7";
        }
        else if (__dummyScrutVar1.equals("ES"))
        {
            return "8";
        }
        else if (__dummyScrutVar1.equals("FS"))
        {
            return "9";
        }
        else if (__dummyScrutVar1.equals("GS"))
        {
            return "10";
        }
        else if (__dummyScrutVar1.equals("HS"))
        {
            return "11";
        }
        else if (__dummyScrutVar1.equals("IS"))
        {
            return "12";
        }
        else if (__dummyScrutVar1.equals("JS"))
        {
            return "13";
        }
        else if (__dummyScrutVar1.equals("KS"))
        {
            return "20";
        }
        else if (__dummyScrutVar1.equals("LS"))
        {
            return "21";
        }
        else if (__dummyScrutVar1.equals("MS"))
        {
            return "22";
        }
        else if (__dummyScrutVar1.equals("NS"))
        {
            return "23";
        }
        else if (__dummyScrutVar1.equals("OS"))
        {
            return "24";
        }
        else if (__dummyScrutVar1.equals("PS"))
        {
            return "25";
        }
        else if (__dummyScrutVar1.equals("QS"))
        {
            return "26";
        }
        else if (__dummyScrutVar1.equals("RS"))
        {
            return "27";
        }
        else if (__dummyScrutVar1.equals("SS"))
        {
            return "28";
        }
        else if (__dummyScrutVar1.equals("TS"))
        {
            return "29";
        }
        else
        {
            return "";
        }                                        
    }

    /**
    * Converts supernumerary teeth to permanent.
    */
    public static String supToPerm(String tooth_id) throws Exception {
        System.String __dummyScrutVar2 = tooth_id;
        if (__dummyScrutVar2.equals("51"))
        {
            return "1";
        }
        else if (__dummyScrutVar2.equals("52"))
        {
            return "2";
        }
        else if (__dummyScrutVar2.equals("53"))
        {
            return "3";
        }
        else if (__dummyScrutVar2.equals("54"))
        {
            return "4";
        }
        else if (__dummyScrutVar2.equals("55"))
        {
            return "5";
        }
        else if (__dummyScrutVar2.equals("56"))
        {
            return "6";
        }
        else if (__dummyScrutVar2.equals("57"))
        {
            return "7";
        }
        else if (__dummyScrutVar2.equals("58"))
        {
            return "8";
        }
        else if (__dummyScrutVar2.equals("59"))
        {
            return "9";
        }
        else if (__dummyScrutVar2.equals("60"))
        {
            return "10";
        }
        else if (__dummyScrutVar2.equals("61"))
        {
            return "11";
        }
        else if (__dummyScrutVar2.equals("62"))
        {
            return "12";
        }
        else if (__dummyScrutVar2.equals("63"))
        {
            return "13";
        }
        else if (__dummyScrutVar2.equals("64"))
        {
            return "14";
        }
        else if (__dummyScrutVar2.equals("65"))
        {
            return "15";
        }
        else if (__dummyScrutVar2.equals("66"))
        {
            return "16";
        }
        else if (__dummyScrutVar2.equals("67"))
        {
            return "17";
        }
        else if (__dummyScrutVar2.equals("68"))
        {
            return "18";
        }
        else if (__dummyScrutVar2.equals("69"))
        {
            return "19";
        }
        else if (__dummyScrutVar2.equals("70"))
        {
            return "20";
        }
        else if (__dummyScrutVar2.equals("71"))
        {
            return "21";
        }
        else if (__dummyScrutVar2.equals("72"))
        {
            return "22";
        }
        else if (__dummyScrutVar2.equals("73"))
        {
            return "23";
        }
        else if (__dummyScrutVar2.equals("74"))
        {
            return "24";
        }
        else if (__dummyScrutVar2.equals("75"))
        {
            return "25";
        }
        else if (__dummyScrutVar2.equals("76"))
        {
            return "26";
        }
        else if (__dummyScrutVar2.equals("77"))
        {
            return "27";
        }
        else if (__dummyScrutVar2.equals("78"))
        {
            return "28";
        }
        else if (__dummyScrutVar2.equals("79"))
        {
            return "29";
        }
        else if (__dummyScrutVar2.equals("80"))
        {
            return "30";
        }
        else if (__dummyScrutVar2.equals("81"))
        {
            return "31";
        }
        else if (__dummyScrutVar2.equals("82"))
        {
            return "32";
        }
        else if (__dummyScrutVar2.equals("AS"))
        {
            return "4";
        }
        else if (__dummyScrutVar2.equals("BS"))
        {
            return "5";
        }
        else if (__dummyScrutVar2.equals("CS"))
        {
            return "6";
        }
        else if (__dummyScrutVar2.equals("DS"))
        {
            return "7";
        }
        else if (__dummyScrutVar2.equals("ES"))
        {
            return "8";
        }
        else if (__dummyScrutVar2.equals("FS"))
        {
            return "9";
        }
        else if (__dummyScrutVar2.equals("GS"))
        {
            return "10";
        }
        else if (__dummyScrutVar2.equals("HS"))
        {
            return "11";
        }
        else if (__dummyScrutVar2.equals("IS"))
        {
            return "12";
        }
        else if (__dummyScrutVar2.equals("JS"))
        {
            return "13";
        }
        else if (__dummyScrutVar2.equals("KS"))
        {
            return "20";
        }
        else if (__dummyScrutVar2.equals("LS"))
        {
            return "21";
        }
        else if (__dummyScrutVar2.equals("MS"))
        {
            return "22";
        }
        else if (__dummyScrutVar2.equals("NS"))
        {
            return "23";
        }
        else if (__dummyScrutVar2.equals("OS"))
        {
            return "24";
        }
        else if (__dummyScrutVar2.equals("PS"))
        {
            return "25";
        }
        else if (__dummyScrutVar2.equals("QS"))
        {
            return "26";
        }
        else if (__dummyScrutVar2.equals("RS"))
        {
            return "27";
        }
        else if (__dummyScrutVar2.equals("SS"))
        {
            return "28";
        }
        else if (__dummyScrutVar2.equals("TS"))
        {
            return "29";
        }
        else
        {
            return "";
        }                                                    
    }

    /**
    * Used to put perm and pri into a single array.  1-32 is perm.  33-52 is pri.
    */
    public static int toOrdinal(String toothNum) throws Exception {
        //
        if (isPrimary(toothNum))
        {
            System.String __dummyScrutVar3 = toothNum;
            if (__dummyScrutVar3.equals("A"))
            {
                return 33;
            }
            else if (__dummyScrutVar3.equals("B"))
            {
                return 34;
            }
            else if (__dummyScrutVar3.equals("C"))
            {
                return 35;
            }
            else if (__dummyScrutVar3.equals("D"))
            {
                return 36;
            }
            else if (__dummyScrutVar3.equals("E"))
            {
                return 37;
            }
            else if (__dummyScrutVar3.equals("F"))
            {
                return 38;
            }
            else if (__dummyScrutVar3.equals("G"))
            {
                return 39;
            }
            else if (__dummyScrutVar3.equals("H"))
            {
                return 40;
            }
            else if (__dummyScrutVar3.equals("I"))
            {
                return 41;
            }
            else if (__dummyScrutVar3.equals("J"))
            {
                return 42;
            }
            else if (__dummyScrutVar3.equals("K"))
            {
                return 43;
            }
            else if (__dummyScrutVar3.equals("L"))
            {
                return 44;
            }
            else if (__dummyScrutVar3.equals("M"))
            {
                return 45;
            }
            else if (__dummyScrutVar3.equals("N"))
            {
                return 46;
            }
            else if (__dummyScrutVar3.equals("O"))
            {
                return 47;
            }
            else if (__dummyScrutVar3.equals("P"))
            {
                return 48;
            }
            else if (__dummyScrutVar3.equals("Q"))
            {
                return 49;
            }
            else if (__dummyScrutVar3.equals("R"))
            {
                return 50;
            }
            else if (__dummyScrutVar3.equals("S"))
            {
                return 51;
            }
            else if (__dummyScrutVar3.equals("T"))
            {
                return 52;
            }
            else
            {
                return -1;
            }                    
        }
        else
        {
            return toInt(toothNum);
        } 
    }

    //perm
    /**
    * Assumes ordinal is valid.
    */
    public static String fromOrdinal(int ordinal) throws Exception {
        if (ordinal < 1 || ordinal > 52)
        {
            return "1";
        }
         
        //just so it won't crash.
        if (ordinal < 33)
        {
            return ordinal.ToString();
        }
         
        if (ordinal < 43)
        {
            return Tooth.permToPri(ordinal - 29);
        }
         
        return Tooth.permToPri(ordinal - 23);
    }

    /**
    * 
    */
    public static boolean isMaxillary(int intTooth) throws Exception {
        String toothNum = fromInt(intTooth);
        return isMaxillary(toothNum);
    }

    /**
    * 
    */
    public static boolean isMaxillary(String tooth_id) throws Exception {
        if (!isValidDB(tooth_id))
        {
            return false;
        }
         
        int intTooth = toInt(tooth_id);
        if (intTooth >= 1 && intTooth <= 16)
        {
            return true;
        }
         
        return false;
    }

    /**
    * Handles direct user input and tidies according to rules.  ToothNum might be empty, and a tidy should still be attempted.  Otherwise, toothNum must be valid.
    */
    public static String surfTidyForDisplay(String surf, String toothNum) throws Exception {
        boolean isCanadian = CultureInfo.CurrentCulture.Name.EndsWith("CA");
        //Canadian. en-CA or fr-CA
        //Canadian valid=MOIDBLV
        if (surf == null)
        {
            surf = "";
        }
         
        String surfTidy = "";
        ArrayList al = new ArrayList();
        for (int i = 0;i < surf.Length;i++)
        {
            al.Add(surf.Substring(i, 1).ToUpper());
        }
        //M----------------------------------------
        if (al.Contains("M"))
        {
            surfTidy += "M";
        }
         
        //O-------------------------------------------
        if (StringSupport.equals(toothNum, "") || isPosterior(toothNum))
        {
            if (al.Contains("O"))
            {
                surfTidy += "O";
            }
             
        }
         
        //I---------------------------------
        if (StringSupport.equals(toothNum, "") || isAnterior(toothNum))
        {
            if (al.Contains("I"))
            {
                surfTidy += "I";
            }
             
        }
         
        //D---------------------------------------
        if (al.Contains((String)"D"))
        {
            surfTidy += "D";
        }
         
        //B------------------------------------------------
        if (StringSupport.equals(toothNum, "") || isPosterior(toothNum))
        {
            if (al.Contains("B"))
            {
                surfTidy += "B";
            }
             
        }
         
        //F-----------------------------------------
        if (isCanadian)
        {
            if (StringSupport.equals(toothNum, "") || isAnterior(toothNum))
            {
                if (al.Contains("V"))
                {
                    //Canadian equivalent of F
                    surfTidy += "V";
                }
                 
            }
             
        }
        else
        {
            if (StringSupport.equals(toothNum, "") || isAnterior(toothNum))
            {
                if (al.Contains("F"))
                {
                    surfTidy += "F";
                }
                 
            }
             
        } 
        //V-----------------------------------------
        if (isCanadian)
        {
            if (al.Contains("5"))
            {
                //Canadian equivalent of V
                surfTidy += "5";
            }
             
        }
        else
        {
            if (al.Contains("V"))
            {
                surfTidy += "V";
            }
             
        } 
        //L-----------------------------------------
        if (al.Contains((String)"L"))
        {
            surfTidy += "L";
        }
         
        return surfTidy;
    }

    /**
    * Converts the database value to a claim value.  Special handling for V surfaces.  ToothNum must be valid.
    */
    public static String surfTidyForClaims(String surf, String toothNum) throws Exception {
        boolean isCanadian = CultureInfo.CurrentCulture.Name.EndsWith("CA");
        //Canadian. en-CA or fr-CA
        //Canadian valid=MOIDBLV
        if (surf == null)
        {
            surf = "";
        }
         
        String surfTidy = "";
        ArrayList al = new ArrayList();
        for (int i = 0;i < surf.Length;i++)
        {
            al.Add(surf.Substring(i, 1).ToUpper());
        }
        //M----------------------------------------
        if (al.Contains("M"))
        {
            surfTidy += "M";
        }
         
        //O-------------------------------------------
        if (isPosterior(toothNum))
        {
            if (al.Contains("O"))
            {
                surfTidy += "O";
            }
             
        }
         
        //I---------------------------------
        if (isAnterior(toothNum))
        {
            if (al.Contains("I"))
            {
                surfTidy += "I";
            }
             
        }
         
        //D---------------------------------------
        if (al.Contains((String)"D"))
        {
            surfTidy += "D";
        }
         
        //B------------------------------------------------
        //if(isCanadian) {//not needed because db to claim behavior is identical.  It's only in the UI where the V would show as 5
        if (isPosterior(toothNum))
        {
            if (al.Contains("B") || al.Contains("V"))
            {
                surfTidy += "B";
            }
             
        }
         
        //F-----------------------------------------
        if (isAnterior(toothNum))
        {
            if (al.Contains("F") || al.Contains("V"))
            {
                if (isCanadian)
                {
                    surfTidy += "V";
                }
                else
                {
                    //Vestibular
                    surfTidy += "F";
                } 
            }
             
        }
         
        //L-----------------------------------------
        if (al.Contains((String)"L"))
        {
            surfTidy += "L";
        }
         
        return surfTidy;
    }

    /**
    * Takes display string and converts it into Db string.  ToothNum does not need to be valid.
    */
    public static String surfTidyFromDisplayToDb(String surf, String toothNum) throws Exception {
        boolean isCanadian = CultureInfo.CurrentCulture.Name.EndsWith("CA");
        //Canadian. en-CA or fr-CA
        //Canadian valid=MOIDBLV
        if (surf == null)
        {
            surf = "";
        }
         
        String surfTidy = "";
        ArrayList al = new ArrayList();
        for (int i = 0;i < surf.Length;i++)
        {
            al.Add(surf.Substring(i, 1).ToUpper());
        }
        //M----------------------------------------
        if (al.Contains("M"))
        {
            surfTidy += "M";
        }
         
        //O-------------------------------------------
        if (StringSupport.equals(toothNum, "") || isPosterior(toothNum))
        {
            if (al.Contains("O"))
            {
                surfTidy += "O";
            }
             
        }
         
        //I---------------------------------
        if (StringSupport.equals(toothNum, "") || isAnterior(toothNum))
        {
            if (al.Contains("I"))
            {
                surfTidy += "I";
            }
             
        }
         
        //D---------------------------------------
        if (al.Contains((String)"D"))
        {
            surfTidy += "D";
        }
         
        //B------------------------------------------------
        if (StringSupport.equals(toothNum, "") || isPosterior(toothNum))
        {
            if (al.Contains("B"))
            {
                surfTidy += "B";
            }
             
        }
         
        //F-----------------------------------------
        if (isCanadian)
        {
            if (StringSupport.equals(toothNum, "") || isAnterior(toothNum))
            {
                if (al.Contains("V"))
                {
                    //Canadian equivalent of F
                    surfTidy += "F";
                }
                 
            }
             
        }
        else
        {
            //for db
            if (StringSupport.equals(toothNum, "") || isAnterior(toothNum))
            {
                if (al.Contains("F"))
                {
                    surfTidy += "F";
                }
                 
            }
             
        } 
        //V-----------------------------------------
        if (isCanadian)
        {
            if (al.Contains("5"))
            {
                //Canadian equivalent of V
                surfTidy += "V";
            }
             
        }
        else
        {
            //for db
            if (al.Contains("V"))
            {
                surfTidy += "V";
            }
             
        } 
        //L-----------------------------------------
        if (al.Contains((String)"L"))
        {
            surfTidy += "L";
        }
         
        return surfTidy;
    }

    /**
    * Takes surfaces from Db and converts them to appropriate culture for display.  Only Canada supported so far.  ToothNum does not need to be valid since minimal manipulation here.
    */
    public static String surfTidyFromDbToDisplay(String surf, String toothNum) throws Exception {
        boolean isCanadian = CultureInfo.CurrentCulture.Name.EndsWith("CA");
        //Canadian. en-CA or fr-CA
        //Canadian valid=MOIDBLV
        if (!isCanadian)
        {
            return surf;
        }
         
        //Canadian:
        if (surf == null)
        {
            return "";
        }
         
        String surfTidy = surf.Replace("V", "5");
        //USA classV becomes 5 for Canadian display
        surfTidy = surfTidy.Replace("F", "V");
        return surfTidy;
    }

    //USA Facial becomes Vestibular for Canadian display
    /**
    * This will be deleted as soon as it's no longer in use by DirectX chart.
    */
    public static float perioShiftMm(String tooth_id) throws Exception {
        return 0;
    }

}


