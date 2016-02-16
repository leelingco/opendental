//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;


public enum ToolBarsAvail
{
    //<summary>CommItemType of 0 is reserved for later use with user defined types.</summary>
    /*public enum CommItemType{
    		///<Summary>Used temporarily while we get rid of StatementSent.</Summary>
    		None=0,
    		///<summary>1- auto. </summary>
    		StatementSentOld=1,
    		///<summary>2- Any activity related to appointment scheduling.</summary>
    		ApptRelated,
    		///<summary>3- </summary>
    		Insurance,
    		///<summary>4- </summary>
    		Financial,
    		///<summary>5- </summary>
    		Recall,
    		///<summary>6- </summary>
    		Misc//LetterSent used to be 6
    		//clinical not implemented yet.
    	}*/
    /**
    * 0
    */
    AccountModule,
    /**
    * 1
    */
    ApptModule,
    /**
    * 2
    */
    ChartModule,
    /**
    * 3
    */
    ImagesModule,
    /**
    * 4
    */
    FamilyModule,
    /**
    * 5
    */
    TreatmentPlanModule,
    /**
    * 6
    */
    ClaimsSend,
    /**
    * 7 Shows in the toolbar at the top that is common to all modules.
    */
    AllModules
}

