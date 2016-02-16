//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Expression;

public class CustomProperty   
{
    Expression _Name;
    // name of the property
    Expression _Value;
    // value of the property
    public CustomProperty(Expression name, Expression val) throws Exception {
        _Name = name;
        _Value = val;
    }

    public Expression getName() throws Exception {
        return _Name;
    }

    public Expression getValue() throws Exception {
        return _Value;
    }

}


