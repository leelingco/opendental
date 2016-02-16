//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:25 PM
//

package OpenDental.SmartCards;

import java.util.ArrayList;
import java.util.List;
import OpenDental.SmartCards.Belgium.BelgianIdentityCard;
import OpenDental.SmartCards.ISmartCardManager;
import OpenDental.SmartCards.PatientCardInsertedEventArgs;
import OpenDental.SmartCards.PatientCardInsertedEventHandler;
import OpenDental.SmartCards.SmartCardManager;
import OpenDental.SmartCards.SmartCardService;
import OpenDental.SmartCards.SmartCardState;
import OpenDental.SmartCards.SmartCardStateChangedEventArgs;
import OpenDental.SmartCards.SmartCardStateChangedEventHandler;
import OpenDentBusiness.Patient;

public class SmartCardWatcher  extends Component 
{
    public SmartCardWatcher() throws Exception {
        manager = SmartCardManager.load();
        // This returns null on operation systems which are not supported. If the current OS is not supported,
        // we don't hook up the event handlers.
        if (manager == null)
            return ;
         
        manager.SmartCardChanged += new SmartCardStateChangedEventHandler() 
          { 
            public System.Void invoke(System.Object sender, SmartCardStateChangedEventArgs e) throws Exception {
                onSmartCardChanged(sender, e);
            }

            public List<SmartCardStateChangedEventHandler> getInvocationList() throws Exception {
                List<SmartCardStateChangedEventHandler> ret = new ArrayList<SmartCardStateChangedEventHandler>();
                ret.add(this);
                return ret;
            }
        
          };
        // Register all known Smart Cards
        smartCardServices = new Collection<SmartCardService>();
        smartCardServices.Add(new BelgianIdentityCard(manager));
    }

    private ISmartCardManager manager;
    private Collection<SmartCardService> smartCardServices = new Collection<SmartCardService>();
    void onSmartCardChanged(Object sender, SmartCardStateChangedEventArgs e) throws Exception {
        // If nobody is listening to us, we don't need to do any effort to talk.
        if (PatientCardInserted == null)
            return ;
         
        if (e.getState() == SmartCardState.Inserted)
        {
            for (Object __dummyForeachVar0 : smartCardServices)
            {
                SmartCardService service = (SmartCardService)__dummyForeachVar0;
                if (service.isSupported(e.getAtr()))
                {
                    Patient patient = service.getPatientInfo(e.getReader());
                    // The event may be unhooked from
                    if (PatientCardInserted != null)
                    {
                        PatientCardInserted.invoke(this,new PatientCardInsertedEventArgs(patient));
                    }
                     
                }
                 
            }
        }
         
    }

    protected void dispose(boolean disposing) throws Exception {
        manager.Dispose();
        super.Dispose(disposing);
    }

    public PatientCardInsertedEventHandler PatientCardInserted;
}


