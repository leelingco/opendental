//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental.DivvyConnect;

import OpenDental.DivvyConnect.IPostcardService;

public class PostcardServiceClient  extends System.ServiceModel.ClientBase<IPostcardService> implements IPostcardService
{

    public PostcardServiceClient() throws Exception {
    }

    public PostcardServiceClient(String endpointConfigurationName) throws Exception {
        super(endpointConfigurationName);
    }

    public PostcardServiceClient(String endpointConfigurationName, String remoteAddress) throws Exception {
        super(endpointConfigurationName, remoteAddress);
    }

    public PostcardServiceClient(String endpointConfigurationName, System.ServiceModel.EndpointAddress remoteAddress) throws Exception {
        super(endpointConfigurationName, remoteAddress);
    }

    public PostcardServiceClient(System.ServiceModel.Channels.Binding binding, System.ServiceModel.EndpointAddress remoteAddress) throws Exception {
        super(binding, remoteAddress);
    }

    public OpenDental.DivvyConnect.PostcardReturnMessage sendPostcards(System.Guid apiKey, String username, String password, OpenDental.DivvyConnect.Postcard[] postcards, OpenDental.DivvyConnect.Practice practice) throws Exception {
        return super.Channel.SendPostcards(apiKey, username, password, postcards, practice);
    }

}


