//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:16 PM
//

package fyiReporting.RdlDesign;


public class StaticLists   
{
    /**
    * Names of colors to put into lists
    */
    static public final String[] ColorList = new String[]{ "Aliceblue", "Antiquewhite", "Aqua", "Aquamarine", "Azure", "Beige", "Bisque", "Black", "Blanchedalmond", "Blue", "Blueviolet", "Brown", "Burlywood", "Cadetblue", "Chartreuse", "Chocolate", "Coral", "Cornflowerblue", "Cornsilk", "Crimson", "Cyan", "Darkblue", "Darkcyan", "Darkgoldenrod", "Darkgray", "Darkgreen", "Darkkhaki", "Darkmagenta", "Darkolivegreen", "Darkorange", "Darkorchid", "Darkred", "Darksalmon", "Darkseagreen", "Darkslateblue", "Darkslategray", "Darkturquoise", "Darkviolet", "Deeppink", "Deepskyblue", "Dimgray", "Dodgerblue", "Firebrick", "Floralwhite", "Forestgreen", "Fuchsia", "Gainsboro", "Ghostwhite", "Gold", "Goldenrod", "Gray", "Green", "Greenyellow", "Honeydew", "Hotpink", "Indianred", "Indigo", "Ivory", "Khaki", "Lavender", "Lavenderblush", "Lawngreen", "Lemonchiffon", "Lightblue", "Lightcoral", "Lightcyan", "Lightgoldenrodyellow", "Lightgreen", "Lightgrey", "Lightpink", "Lightsalmon", "Lightseagreen", "Lightskyblue", "Lightslategrey", "Lightsteelblue", "Lightyellow", "Lime", "Limegreen", "Linen", "Magenta", "Maroon", "Mediumaquamarine", "Mediumblue", "Mediumorchid", "Mediumpurple", "Mediumseagreen", "Mediumslateblue", "Mediumspringgreen", "Mediumturquoise", "Mediumvioletred", "Midnightblue", "Mintcream", "Mistyrose", "Moccasin", "Navajowhite", "Navy", "Oldlace", "Olive", "Olivedrab", "Orange", "Orangered", "Orchid", "Palegoldenrod", "Palegreen", "Paleturquoise", "Palevioletred", "Papayawhip", "Peachpuff", "Peru", "Pink", "Plum", "Powderblue", "Purple", "Red", "Rosybrown", "Royalblue", "Saddlebrown", "Salmon", "Sandybrown", "Seagreen", "Seashell", "Sienna", "Silver", "Skyblue", "Slateblue", "Slategray", "Snow", "Springgreen", "Steelblue", "Tan", "Teal", "Thistle", "Tomato", "Turquoise", "Violet", "Wheat", "White", "Whitesmoke", "Yellow", "Yellowgreen" };
    /**
    * Names of globals to put into expressions
    */
    static public final String[] GlobalList = new String[]{ "=Globals!PageNumber.Value", "=Globals!TotalPages.Value", "=Globals!ExecutionTime.Value", "=Globals!ReportFolder.Value", "=Globals!ReportName.Value" };
    static public final String[] OperatorList = new String[]{ " & ", " + ", " - ", " * ", " / ", " mod ", " and ", " or ", " = ", " != ", " > ", " >= ", " < ", " <= " };
    /**
    * Names of functions with pseudo arguments
    */
    static public final String[] FunctionList = new String[]{ "Iif(boolean, trueExpr, falseExpr)", "Choose(number, choice1 [,choice2]...)", "Switch(boolean1, value1[, boolean2, value2]...[elseValue])", "Format(expr, formatExpr)" };
    static public final String[] AggrFunctionList = new String[]{ "Sum(number [, scope])", "Avg(number [, scope])", "Min(expr [, scope])", "Max(expr [, scope])", "First(expr [, scope])", "Last(expr [, scope])", "Next(expr [, scope])", "Previous(expr [, scope])", "Level([scope])", "Count(expr [, scope])", "Countrows(expr [, scope])", "Countdistinct(expr [, scope])", "RowNumber()", "Runningvalue(expr, sum [, scope])", "Runningvalue(expr, avg [, scope])", "Runningvalue(expr, count [, scope])", "Runningvalue(expr, max [, scope])", "Runningvalue(expr, min [, scope])", "Runningvalue(expr, stdev [, scope])", "Runningvalue(expr, stdevp [, scope])", "Runningvalue(expr, var [, scope])", "Runningvalue(expr, varp [, scope])", "Stdev(expr [, scope])", "Stdevp(expr [, scope])", "Var(expr [, scope])", "Varp(expr [, scope])" };
    /**
    * Zoom values
    */
    static public final String[] ZoomList = new String[]{ "Actual Size", "Fit Page", "Fit Width", "800%", "400%", "200%", "150%", "125%", "100%", "75%", "50%", "25%" };
}


