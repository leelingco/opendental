//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:27 PM
//

package OpenDental.DivvyConnect;


public interface IPostcardService   
{
    OpenDental.DivvyConnect.PostcardReturnMessage sendPostcards(System.Guid apiKey, String username, String password, OpenDental.DivvyConnect.Postcard[] postcards, OpenDental.DivvyConnect.Practice practice) throws Exception ;

}


