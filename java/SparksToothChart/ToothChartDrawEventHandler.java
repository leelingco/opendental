//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:35 PM
//

package SparksToothChart;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import SparksToothChart.ToothChartDrawEventArgs;

public interface ToothChartDrawEventHandler   
{
    void invoke(Object sender, ToothChartDrawEventArgs e) throws Exception ;

    System.Collections.Generic.IList<SparksToothChart.ToothChartDrawEventHandler> getInvocationList() throws Exception ;

}


