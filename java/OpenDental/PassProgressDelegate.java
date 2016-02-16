//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:35 PM
//

package OpenDental;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.PassProgressDelegate;

public interface PassProgressDelegate   
{
    void invoke(double newCurVal, String newDisplayText, double newMaxVal, String errorMessage) throws Exception ;

    System.Collections.Generic.IList<PassProgressDelegate> getInvocationList() throws Exception ;

}


