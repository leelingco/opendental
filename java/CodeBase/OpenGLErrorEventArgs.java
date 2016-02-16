//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:25 PM
//

package CodeBase;


public class OpenGLErrorEventArgs  extends EventArgs 
{
    private int errorCode = Gl.GL_NO_ERROR;
    private String description = "";
    /**
    * A brief description of the error.
    */
    public String getDescription() throws Exception {
        return (description);
    }

    /**
    * The OpenGL error code.
    */
    public int getErrorCode() throws Exception {
        return (errorCode);
    }

    public OpenGLErrorEventArgs(int errorCode) throws Exception {
        this.errorCode = errorCode;
        switch(errorCode)
        {
            case Gl.GL_INVALID_ENUM: 
                description = "GL_INVALID_ENUM - An unacceptable value has been specified for an enumerated argument.  The offending function has been ignored.";
                break;
            case Gl.GL_INVALID_VALUE: 
                description = "GL_INVALID_VALUE - A numeric argument is out of range.  The offending function has been ignored.";
                break;
            case Gl.GL_INVALID_OPERATION: 
                description = "GL_INVALID_OPERATION - The specified operation is not allowed in the current state.  The offending function has been ignored.";
                break;
            case Gl.GL_STACK_OVERFLOW: 
                description = "GL_STACK_OVERFLOW - This function would cause a stack overflow.  The offending function has been ignored.";
                break;
            case Gl.GL_STACK_UNDERFLOW: 
                description = "GL_STACK_UNDERFLOW - This function would cause a stack underflow.  The offending function has been ignored.";
                break;
            case Gl.GL_OUT_OF_MEMORY: 
                description = "GL_OUT_OF_MEMORY - There is not enough memory left to execute the function.  The state of OpenGL has been left undefined.";
                break;
            default: 
                description = "Unknown OpenGL Error.";
                break;
        
        }
    }

}


