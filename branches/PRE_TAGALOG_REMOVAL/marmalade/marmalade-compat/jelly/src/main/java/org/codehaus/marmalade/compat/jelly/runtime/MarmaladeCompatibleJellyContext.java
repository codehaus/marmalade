/* Created on Jun 28, 2004 */
package org.codehaus.marmalade.compat.jelly.runtime;

import org.apache.commons.jelly.JellyContext;
import org.codehaus.marmalade.compat.jelly.JellyCompatUncheckedException;
import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;

import java.net.URL;

import java.util.Iterator;
import java.util.Map;

/**
 * @author jdcasey
 */
public class MarmaladeCompatibleJellyContext extends JellyContext
{
    private MarmaladeExecutionContext embeddedContext;
    private ExpressionEvaluator expressionEvaluator;

    public MarmaladeCompatibleJellyContext( MarmaladeExecutionContext context,
        ExpressionEvaluator el )
    {
        this.embeddedContext = context;
        this.expressionEvaluator = el;
    }

    protected JellyContext createChildContext(  )
    {
        return this;
    }

    public Object findVariable( String name )
    {
        return _getVar( name, null );
    }

    private Object _getVar( String name, String scopeName )
    {
        try
        {
            return embeddedContext.getVariable( name, expressionEvaluator );
        }
        catch ( ExpressionEvaluationException e )
        {
            throw new JellyCompatUncheckedException( 
                "Error retrieving variable: " + name, e );
        }
    }

    public Object getVariable( String name, String scopeName )
    {
        return _getVar( name, scopeName );
    }

    public Object getVariable( String name )
    {
        return _getVar( name, null );
    }

    public Iterator getVariableNames(  )
    {
        return embeddedContext.unmodifiableVariableMap(  ).keySet(  ).iterator(  );
    }

    public Map getVariables(  )
    {
        return embeddedContext.unmodifiableVariableMap(  );
    }

    public JellyContext newJellyContext(  )
    {
        return this;
    }

    public JellyContext newJellyContext( Map newVariables )
    {
        // TODO Auto-generated method stub
        return super.newJellyContext( newVariables );
    }

    public void removeVariable( String name, String scopeName )
    {
        rmVar( name, scopeName );
    }

    public void removeVariable( String name )
    {
        rmVar( name, null );
    }

    private void rmVar( String name, String scopeName )
    {
        embeddedContext.removeVariable( name );
    }

    protected void setParent( JellyContext context )
    {
        throw new JellyCompatUncheckedException( "Only "
            + getClass(  ).getName(  )
            + " implementations of JellyContext are allowed in this system." );
    }

    public void setVariable( String name, Object value )
    {
        setVar( name, null, value );
    }

    public void setVariable( String name, String scopeName, Object value )
    {
        setVar( name, scopeName, value );
    }

    public void setVariables( Map variables )
    {
        embeddedContext.setVariables( variables );
    }

    private void setVar( String name, String scopeName, Object value )
    {
        embeddedContext.setVariable( name, value );
    }
}
