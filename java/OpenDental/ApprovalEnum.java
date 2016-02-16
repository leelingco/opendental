//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:43 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;

public enum ApprovalEnum
{
    /*
    	///<summary>This object is used to organize all the datafields in FormRequestEdit.  It is used both for admin mode and for regular mode.</summary>
    	public class Request{
    		///<summary>Once approval is changed to Approved, this cannot be edited by submitter.</summary>
    		public string Description;
    		///<summary>Once approval is changed to Approved, this cannot be edited by submitter.</summary>
    		public string Detail;
    		///<summary></summary>
    		public DateTime DateTSubmitted;
    		///<summary>On the server, this is a PatNum.  Here on the client, it's true false.  This value is never sent to the server.  It's inferred on the server end.</summary>
    		public bool IsMine;
    		///<summary>Only set by Jordan.</summary>
    		public int Difficulty;
    		///<summary>Only set by admins.  In non-admin mode, this converts to a wordy description.</summary>
    		public ApprovalEnum Approval;
    		///<summary>Ignored when in admin mode.</summary>
    		public int MyPoints;
    		///<summary>Ignored when in admin mode.</summary>
    		public bool IsCritical;
    		///<summary>Ignored when in admin mode.</summary>
    		public double MyPledge;
    		///<summary>This is the points remaining with the assumption that zero points are consumed by this request.  Then, the points for the current request are subtracted from the amount before display.</summary>
    		public int MyPointsRemain;
    		///<summary>Just informational.  Nobody can edit.</summary>
    		public string TotalPoints;
    		///<summary>Just informational.  Nobody can edit.</summary>
    		public string TotalCritical;
    		///<summary>Just informational.  Nobody can edit.</summary>
    		public string TotalPledged;
    	}*/
    New,
    NeedsClarification,
    Redundant,
    TooBroad,
    NotARequest,
    AlreadyDone,
    Obsolete,
    Approved,
    InProgress,
    Complete
}

