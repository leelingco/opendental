//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:25 PM
//

package OpenDental.SmartCards.Belgium;


public enum ErrorCodeOptions
{
    /**
    * Function succeeded
    */
    OK,
    /**
    * Unknown system error (see system error code)
    */
    E_SYSTEM,
    /**
    * Unknown PC/SC error (see PC/SC error code)
    */
    E_PCSC,
    /**
    * Unknown card error (see card status word)
    */
    E_CARD,
    /**
    * Invalid parameter (NULL pointer, out of bound, etc.)
    */
    E_BAD_PARAM,
    /**
    * An internal consistency check failed
    */
    E_INTERNAL,
    /**
    * The supplied handle was invalid
    */
    E_INVALID_HANDLE,
    /**
    * The data buffer to receive returned data is too small for the
    * returned data
    */
    E_INSUFFICIENT_BUFFER,
    /**
    * An internal communications error has been detected
    */
    E_COMM_ERROR,
    /**
    * A specified timeout value has expired
    */
    E_TIMEOUT,
    /**
    * Unknown card detected
    */
    E_UNKNOWN_CARD,
    /**
    * Input on pinpad cancelled
    */
    E_KEYPAD_CANCELLED,
    /**
    * Timout returned from pinpad
    */
    E_KEYPAD_TIMEOUT,
    /**
    * The two PINs did not match
    */
    E_KEYPAD_PIN_MISMATCH,
    /**
    * Message too long on pinpad
    */
    E_KEYPAD_MSG_TOO_LONG,
    /**
    * Invalid PIN length
    */
    E_INVALID_PIN_LENGTH,
    /**
    * Error in a signature verification or a certificate validation
    */
    E_VERIFICATION,
    /**
    * Library not initialized
    */
    E_NOT_INITIALIZED,
    /**
    * An internal error has been detected, but the source is unknown
    */
    E_UNKNOWN,
    /**
    * Function is not supported
    */
    E_UNSUPPORTED_FUNCTION,
    /**
    * Incorrect library version
    */
    E_INCORRECT_VERSION
}

