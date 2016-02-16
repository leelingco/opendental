//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:35 PM
//

package SparksToothChart;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
* 
*/
public class ToothChartDrawEventArgs   
{
    private String drawingSegment = new String();
    //private bool isInsert;
    /**
    * 
    */
    public ToothChartDrawEventArgs(String drawingSeg) throws Exception {
        //,bool isInsert){
        this.drawingSegment = drawingSeg;
    }

    //this.isInsert=isInsert;
    /**
    * 
    */
    public String getDrawingSegement() throws Exception {
        return drawingSegment;
    }

}


/*//<summary></summary>
		public bool IsInsert{
			get{ 
				return isInsert;
			}
		}*/