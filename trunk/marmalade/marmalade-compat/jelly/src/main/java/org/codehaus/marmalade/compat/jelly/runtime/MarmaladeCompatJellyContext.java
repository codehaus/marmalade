/* Created on Jun 28, 2004 */
package org.codehaus.marmalade.compat.jelly.runtime;

import org.apache.commons.jelly.JellyContext;
import org.apache.commons.jelly.TagLibrary;
import org.codehaus.marmalade.compat.jelly.JellyCompatUncheckedException;
import org.codehaus.marmalade.compat.jelly.metamodel.JellyCompatMarmaladeTaglib;
import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;

import java.util.Iterator;
import java.util.Map;

/**
 * @author jdcasey
 */
public class MarmaladeCompatJellyContext extends JellyContext
{
    public static final String JELLY_CONTEXT_SPECIAL_VARIABLE = "context";
    private MarmaladeExecutionContext embeddedContext;
    private ExpressionEvaluator expressionEvaluator;
    private final JellyCompatMarmaladeTaglib marmaladeTaglib;

    public MarmaladeCompatJellyContext( MarmaladeExecutionContext context,
        ExpressionEvaluator el, JellyCompatMarmaladeTaglib marmaladeTaglib )
    {
        this.embeddedContext = context;
        this.expressionEvaluator = el;
        this.marmaladeTaglib = marmaladeTaglib;
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
        if ( JELLY_CONTEXT_SPECIAL_VARIABLE.equals( name ) )
        {
            return this;
        }
        else
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
        this.embeddedContext.setVariables( newVariables );

        return this;
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

    public void registerTagLibrary( String uri, TagLibrary taglib )
    {
        marmaladeTaglib.registerTagLibrary( uri, taglib );
        super.registerTagLibrary( uri, taglib );
    }

    public TagLibrary getTagLibrary( String name )
    {
        return marmaladeTaglib.getTagLibrary( name );
    }

    public boolean isTagLibraryRegistered( String name )
    {
        return marmaladeTaglib.getTagLibrary( name ) != null;
    }

    public void registerTagLibrary( String uri, String taglib )
    {
        marmaladeTaglib.registerTagLibrary( uri, taglib );
        super.registerTagLibrary( uri, taglib );
    }
}
