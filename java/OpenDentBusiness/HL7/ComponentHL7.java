//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:57 PM
//

package OpenDentBusiness.HL7;


/**
* A component in HL7 is a subportion of a field.  For example, a name field might have LName and FName components.  Components are 0-based.
*/
public class ComponentHL7   
{
    public String ComponentVal = new String();
    public ComponentHL7(String componentVal) throws Exception {
        ComponentVal = componentVal;
    }

    public String toString() {
        try
        {
            return ComponentVal;
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

}


