//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:25 PM
//

package OpenDental.SmartCards;

import CS2JNet.JavaSupport.language.IEventCollection;
import OpenDental.SmartCards.SmartCardStateChangedEventHandler;

public interface ISmartCardManager   extends IDisposable
{
    ReadOnlyCollection<String> getReaders() throws Exception ;

    IEventCollection<SmartCardStateChangedEventHandler> SmartCardChanged() throws Exception ;

}


