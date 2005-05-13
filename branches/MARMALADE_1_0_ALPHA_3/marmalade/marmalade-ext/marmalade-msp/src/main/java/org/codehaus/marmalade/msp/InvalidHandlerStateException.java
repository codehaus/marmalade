// TODO Attach license header here.
package org.codehaus.marmalade.msp;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author jdcasey
 *
 * Created on Nov 26, 2004
 */
public class InvalidHandlerStateException
    extends RuntimeException
{

    private final List stateViolations;

    public InvalidHandlerStateException(List stateViolations)
    {
        super("The handler is not correctly configured.");
        
        this.stateViolations = stateViolations;
    }
    
    public List getStateViolations()
    {
        return Collections.unmodifiableList(stateViolations);
    }

    public void printStackTrace()
    {
        printStackTrace(System.out);
    }
    
    public void printStackTrace( PrintStream s )
    {
        s.println(getLocalizedMessage());
        
        s.println("There were " + stateViolations.size() + " problems with your configuration:");
        
        int idx = 1;
        for ( Iterator it = stateViolations.iterator(); it.hasNext(); )
        {
            Throwable error = (Throwable) it.next();
            s.print("[[" + (idx++) + "]]  ");
            error.printStackTrace(s);
        }
    }
    
    public void printStackTrace( PrintWriter s )
    {
        s.println(getLocalizedMessage());
        
        s.println("There were " + stateViolations.size() + " problems with your configuration:");
        
        int idx = 1;
        for ( Iterator it = stateViolations.iterator(); it.hasNext(); )
        {
            Throwable error = (Throwable) it.next();
            s.print("[[" + (idx++) + "]]  ");
            error.printStackTrace(s);
        }
    }
    
}
