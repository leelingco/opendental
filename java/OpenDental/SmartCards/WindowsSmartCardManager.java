//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:25 PM
//

package OpenDental.SmartCards;

import CS2JNet.JavaSupport.language.RefSupport;
import OpenDental.SmartCards.CardState;
import OpenDental.SmartCards.ISmartCardManager;
import OpenDental.SmartCards.ReaderState;
import OpenDental.SmartCards.ScopeOptions;
import OpenDental.SmartCards.SmartCardErrors;
import OpenDental.SmartCards.SmartCardState;
import OpenDental.SmartCards.SmartCardStateChangedEventArgs;
import OpenDental.SmartCards.SmartCardStateChangedEventHandler;

public class WindowsSmartCardManager   implements ISmartCardManager
{
    public static /* [UNSUPPORTED] 'extern' modifier not supported */ SmartCardErrors establishContext(ScopeOptions scope, IntPtr reserved1, IntPtr reserved2, RefSupport<IntPtr> context) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ SmartCardErrors releaseContext(IntPtr context) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ SmartCardErrors listReaders(IntPtr context, String groups, String readers, RefSupport<int> size) throws Exception ;

    public static /* [UNSUPPORTED] 'extern' modifier not supported */ SmartCardErrors getStatusChange(IntPtr handle, int timeout, ReaderState[] states, int count) throws Exception ;

    // A pointer to the unmanaged context;
    private IntPtr context = IntPtr.Zero;
    // The name of all readers found on the computer;
    private Collection<String> readers = new Collection<String>();
    ReaderState[] states = new ReaderState[]();
    // A thread that watches for new smart cards
    private BackgroundWorker worker = new BackgroundWorker();
    public ReadOnlyCollection<String> getReaders() throws Exception {
        return new ReadOnlyCollection<String>(readers);
    }

    public WindowsSmartCardManager() throws Exception {
        // Establish the Windows Smart Card context
        RefSupport refVar___0 = new RefSupport(context);
        SmartCardErrors result = EstablishContext(ScopeOptions.User, IntPtr.Zero, IntPtr.Zero, refVar___0);
        context = refVar___0.getValue();
        checkResult(result);
        // List all readers
        int size = 2048;
        readers = new Collection<String>();
        // Ask for the size of the buffer first.
        RefSupport<int> refVar___1 = new RefSupport<int>(size);
        boolean boolVar___0 = listReaders(context,null,null,refVar___1) == SmartCardErrors.SCARD_S_SUCCESS;
        size = refVar___1.getValue();
        if (boolVar___0)
        {
            // Allocate a string of the proper size.
            String names = new String(' ', size);
            RefSupport<int> refVar___2 = new RefSupport<int>(size);
            boolean boolVar___1 = listReaders(context,null,names,refVar___2) == SmartCardErrors.SCARD_S_SUCCESS;
            size = refVar___2.getValue();
            if (boolVar___1)
            {
                // The 'names' string will contain a multi-string of the,
                // reader names i.e. they are separated by 0x00 characters.
                String name = String.Empty;
                for (int i = 0;i < names.Length;i++)
                {
                    if (names[i] == 0x00)
                    {
                        if (name.Length > 0)
                        {
                            //
                            // We have the reader name.
                            //
                            readers.Add(name);
                            name = String.Empty;
                        }
                         
                    }
                    else
                    {
                        // Append found character.
                        name = name + new String(names[i], 1);
                    } 
                }
            }
             
        }
         
        states = new ReaderState[readers.Count];
        for (int i = 0;i < readers.Count;i++)
            states[i].Reader = readers[i];
        if (readers.Count > 0)
        {
            worker = new BackgroundWorker();
            worker.WorkerSupportsCancellation = true;
            worker.DoWork += new DoWorkEventHandler(WaitChangeStatus);
            worker.RunWorkerAsync();
        }
         
    }

    public SmartCardStateChangedEventHandler SmartCardChanged;
    public void dispose() throws Exception {
        try
        {
            worker.CancelAsync();
            synchronized (this)
            {
                {
                    // Obtaina lock when we use the context pointer, which may be used (is used every 1s!) by
                    // WaitChangeStatus.
                    if (context != IntPtr.Zero)
                    {
                        SmartCardErrors result = releaseContext(context);
                        checkResult(result);
                        context = IntPtr.Zero;
                    }
                     
                }
            }
        }
        catch (Exception __dummyCatchVar0)
        {
        }
    
    }

    //This is to catch an error message that many users were getting on shutdown of the program
    private void waitChangeStatus(Object sender, DoWorkEventArgs e) throws Exception {
        while (!e.Cancel)
        {
            SmartCardErrors result = SmartCardErrors.SCARD_S_SUCCESS;
            synchronized (this)
            {
                {
                    // Obtain a lock when we use the context pointer, which may be modified in the Dispose() method.
                    if (context == IntPtr.Zero)
                        return ;
                     
                    // This thread will be executed every 1000ms. The thread also blocks for 1000ms, meaning that the application
                    // may keep on running for one extra second after the user has closed the Main Form.
                    result = GetStatusChange(context, 1000, states, readers.Count);
                }
            }
            if (result == SmartCardErrors.SCARD_E_TIMEOUT)
            {
                continue;
            }
             
            // Time out has passed, but there is no new info. Just go on with the loop
            checkResult(result);
            for (int i = 0;i < states.Length;i++)
            {
                // Check if the state changed from the last time
                if ((states[i].EventState & CardState.Changed) == CardState.Changed)
                {
                    // Check what changed.
                    SmartCardState state = SmartCardState.None;
                    if ((states[i].EventState & CardState.Present) == CardState.Present && (states[i].CurrentState & CardState.Present) != CardState.Present)
                    {
                        // The card was inserted.
                        state = SmartCardState.Inserted;
                    }
                    else if ((states[i].EventState & CardState.Empty) == CardState.Empty && (states[i].CurrentState & CardState.Empty) != CardState.Empty)
                    {
                        // The card was ejected.
                        state = SmartCardState.Ejected;
                    }
                      
                    if (state != SmartCardState.None && states[i].CurrentState != CardState.Unaware)
                    {
                        // Raise an event to indicate the change, if we weren't previously unaware of the state
                        // This prevents from the event being raised the first time.
                        this.onStateChanged(new SmartCardStateChangedEventArgs(states[i].Reader, state, states[i].rgbAtr));
                    }
                     
                    // Update the current state
                    // for the next time they are checked.
                    states[i].CurrentState = states[i].EventState;
                }
                 
            }
        }
    }

    protected void onStateChanged(SmartCardStateChangedEventArgs e) throws Exception {
        if (SmartCardChanged != null)
            SmartCardChanged.invoke(this,e);
         
    }

    private void checkResult(SmartCardErrors result) throws Exception {
    }

    //if(result != SmartCardErrors.SCARD_S_SUCCESS);
    //throw new Win32Exception((int)result);
    protected void finalize() throws Throwable {
        try
        {
            dispose();
        }
        finally
        {
            super.finalize();
        }
    }

}


