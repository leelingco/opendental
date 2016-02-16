//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:25 PM
//

package OpenDental.SmartCards;


public enum ScopeOptions
{
    User,
    // The context is a user context, and any
    // database operations are performed within the
    // domain of the user.
    Terminal,
    // The context is that of the current terminal,
    // and any database operations are performed
    // within the domain of that terminal.  (The
    // calling application must have appropriate
    // access permissions for any database actions.)
    System
}

// The context is the system context, and any
// database operations are performed within the
// domain of the system.  (The calling
// application must have appropriate access
// permissions for any database actions.)