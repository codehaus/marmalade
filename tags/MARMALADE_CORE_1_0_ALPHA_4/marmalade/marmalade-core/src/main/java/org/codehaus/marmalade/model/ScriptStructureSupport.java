// TODO Attach license header here.
package org.codehaus.marmalade.model;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.IllegalScriptStructureException;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MissingAttributeException;

/**
 * @author jdcasey Created on Dec 2, 2004
 */
public final class ScriptStructureSupport
{

    // static utility class...don't instantiate.
    private ScriptStructureSupport()
    {
    }

    public static Object requireTagAttribute( MarmaladeTag tag, String name, Class type, MarmaladeExecutionContext context )
        throws MissingAttributeException, ExpressionEvaluationException
    {
        return _requireTagAttribute( tag, name, type, context );
    }

    public static Object requireTagAttribute( MarmaladeTag tag, String name, MarmaladeExecutionContext context )
        throws MissingAttributeException, ExpressionEvaluationException
    {
        return _requireTagAttribute( tag, name, Object.class, context );
    }

    private static Object _requireTagAttribute( MarmaladeTag tag, String name, Class type, MarmaladeExecutionContext context )
        throws MissingAttributeException, ExpressionEvaluationException
    {
        Object result = tag.getAttributes().getValue( name, type, context );

        if ( result == null )
        {
            throw new MissingAttributeException( tag.getTagInfo(), name );
        }
        else
        {
            return result;
        }
    }

    public static void deprecateTagAttribute( MarmaladeTag tag, String name, MarmaladeExecutionContext context )
        throws MissingAttributeException, ExpressionEvaluationException
    {
        Object result = tag.getAttributes().getValue( name, context );

        if ( result != null )
        {
            MarmaladeTagInfo ti = tag.getTagInfo();

            context.getErrWriter().println(
                "Attribute " + name + " of element " + ti.getElement()
                    + " has been deprecated and will be ignored (file: " + ti.getSourceFile() + ", line: "
                    + ti.getSourceLine() + ")." );
        }
    }

    public static MarmaladeTag requireParent( MarmaladeTag tag, Class cls ) throws IllegalScriptStructureException
    {
        MarmaladeTag parent = tag.getParent();

        if ( parent != null )
        {
            if ( !cls.isAssignableFrom( parent.getClass() ) )
            {
                throw new IllegalScriptStructureException( tag.getTagInfo(), cls );
            }
        }
        else
        {
            throw new IllegalScriptStructureException( tag.getTagInfo(), cls );
        }

        return parent;
    }

    public static MarmaladeTag requireAncestor( MarmaladeTag tag, Class cls ) throws IllegalScriptStructureException
    {
        MarmaladeTag parent = tag;
        
        while ( (parent = parent.getParent()) != null )
        {
            if ( cls.isAssignableFrom( parent.getClass() ) )
            {
                break;
            }
        }

        if ( parent == null )
        {
            throw new IllegalScriptStructureException( tag.getTagInfo(), cls );
        }
        else
        {
            return parent;
        }
    }
    
    public static MarmaladeTag getAncestor( MarmaladeTag tag, Class cls )
    {
        MarmaladeTag parent = null;
        
        MarmaladeTag check = tag;
        
        while ( (check = check.getParent()) != null )
        {
            if ( cls.isAssignableFrom( check.getClass() ) )
            {
                parent = check;
                break;
            }
        }

        return parent;
    }
}