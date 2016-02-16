//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;

/**
* This is a list of phrases that need to be translated.  The primary key is a combination of the ClassType and the English phrase.  This table is currently filled dynmically at run time, but the plan is to fill it using a tool that parses the code.
*/
public class Language  extends TableBase 
{
    /**
    * Primary key.
    */
    public long LanguageNum = new long();
    /**
    * No longer used.
    */
    public String EnglishComments = new String();
    /**
    * A string representing the class where the translation is used. Maximum length is 25 characters.
    */
    public String ClassType = new String();
    /**
    * The English version of the phrase, case sensitive.
    */
    public String English = new String();
    /**
    * As this gets more sophisticated, we will use this field to mark some phrases obsolete instead of just deleting them outright.  That way, translators will still have access to them.  For now, this is not used at all.
    */
    public boolean IsObsolete = new boolean();
}


