/* Created on Jun 28, 2004 */
package org.codehaus.marmalade.compat.ant.runtime;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.codehaus.marmalade.compat.ant.AntCompatUncheckedException;
import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;

import java.util.Hashtable;

/**
 * @author jdcasey
 */
public class MarmaladeCompatibleProject extends Project
{
    private final ExpressionEvaluator expressionEvaluator;
    private final MarmaladeExecutionContext context;

    public MarmaladeCompatibleProject( MarmaladeExecutionContext context,
        ExpressionEvaluator expressionEvaluator )
    {
        this.context = context;
        this.expressionEvaluator = expressionEvaluator;
    }

    public ClassLoader getCoreLoader(  )
    {
        return getClass().getClassLoader();
    }

    public Hashtable getProperties(  )
    {
        return getVars();
    }

    public String getProperty( String name )
    {
        return getVar( name );
    }

    public Hashtable getUserProperties(  )
    {
        return getVars();
    }

    public String getUserProperty( String name )
    {
        return getVar(name);
    }

    public String replaceProperties( String expression )
        throws BuildException
    {
        try
        {
            String result = ( String ) expressionEvaluator.evaluate( expression,
                    context.unmodifiableVariableMap(  ), String.class );

            return result;
        }
        catch ( ExpressionEvaluationException e )
        {
            throw new BuildException( "Cannot resolve: " + expression, e );
        }
    }

    public void setNewProperty( String name, String value )
    {
        String storedValue = getVar(name);

        if ( storedValue == null )
        {
            setVar(name, value);
        }
    }

    public void setProperty( String name, String value )
    {
        setVar(name, value);
    }

    public void setSystemProperties(  )
    {
        //sysprops are already part of the scopedMap.
    }

    public void setUserProperty( String name, String value )
    {
        setVar(name, value);
    }
    
    private void setVar(String name, String value) {
        context.setVariable( name, value );
    }

    private String getVar(String name) {
        try {
            return (String)context.getVariable(name, expressionEvaluator);
        }
        catch ( ExpressionEvaluationException e )
        {
            throw new AntCompatUncheckedException( e );
        }
    }

    private Hashtable getVars() {
        return new Hashtable(context.unmodifiableVariableMap());
    }

}
