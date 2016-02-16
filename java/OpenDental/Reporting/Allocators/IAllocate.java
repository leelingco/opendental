//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:23 PM
//

package OpenDental.Reporting.Allocators;


/**
* This is the interface that is to describe how diffenrent allocators are supposed
* to work when allocating payments.
* 
* Here is the model.
* Payments arrive.  When they arrive an allocation event is to occur. The payement
* can then be allocated by the set of rules given by the allocator which a programer
* will create.  The Allocator created must conform to the method rules called by the
* allocator interface.
*/
public interface IAllocate   
{
    boolean allocate(int iGuarantor) throws Exception ;

    boolean deAllocate(int iGuarantor) throws Exception ;

}


//	void SetDbaseTable_and_Columns(string tableName, string[] Columns);
//	bool AllocationRequired(); // to check to see if an allocation needs to be done.
//	public event AllocationEvent(AllocationEventArgs arg); // future implementation