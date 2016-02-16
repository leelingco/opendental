//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.LanguageForeign;
import OpenDentBusiness.TableBase;

/**
* Will usually only contain translations for a single foreign language, although more are allowed.  The primary key is a combination of the ClassType and the English phrase and the culture.
*/
public class LanguageForeign  extends TableBase 
{
    /**
    * Primary key.
    */
    public long LanguageForeignNum = new long();
    /**
    * A string representing the class where the translation is used.
    */
    public String ClassType = new String();
    /**
    * The English version of the phrase.  Case sensitive.
    */
    public String English = new String();
    /**
    * The specific culture name.  Almost always in 5 digit format like this: en-US.
    */
    public String Culture = new String();
    /**
    * The foreign translation.  Remember we use Unicode-8, so this translation can be in any language, including Russian, Hebrew, and Chinese.
    */
    public String Translation = new String();
    /**
    * Comments for other translators for the foreign language.
    */
    public String Comments = new String();
    /**
    * 
    */
    public LanguageForeign copy() throws Exception {
        return (LanguageForeign)this.MemberwiseClone();
    }

}


