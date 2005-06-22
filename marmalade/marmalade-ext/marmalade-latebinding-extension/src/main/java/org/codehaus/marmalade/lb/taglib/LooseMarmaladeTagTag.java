/* Created on Aug 20, 2004 */
package org.codehaus.marmalade.lb.taglib;

import org.codehaus.marmalade.lb.model.LooseMarmaladeTagFactory;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.parsing.ParserHint;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.TagExecutionException;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author jdcasey
 */
public class LooseMarmaladeTagTag
    extends AbstractMarmaladeTag
    implements ConstructorArgOwner, PropertyOwner
{
    public static final String TAG_CLASS_ATTRIBUTE = "class";

    private List constructorArgs = new LinkedList();

    private Map properties = new TreeMap();

    protected void doExecute( MarmaladeExecutionContext context ) throws MarmaladeExecutionException
    {
        String name = (String) requireTagAttribute( DefinitionTagConstants.TAG_NAME_ATTRIBUTE, String.class, context );

        String tagClassName = (String) requireTagAttribute( TAG_CLASS_ATTRIBUTE, String.class, context );
        ClassLoader cloader = Thread.currentThread().getContextClassLoader();

        Class tagClass = null;

        try
        {
            tagClass = cloader.loadClass( tagClassName );
        }
        catch ( ClassNotFoundException e )
        {
            throw new TagExecutionException( getTagInfo(), "Cannot find tag class.", e );
        }

        LooseMarmaladeTagFactory factory = new LooseMarmaladeTagFactory( getTagInfo(), tagClass, constructorArgs, properties,
            getExpressionEvaluator() );

        LateBoundTagLibraryOwner parent = (LateBoundTagLibraryOwner) requireParent( LateBoundTagLibraryOwner.class );

        parent.registerTag( name, new ParserHint(), factory );
    }

    public void addConstructorArg( Object arg )
    {
        constructorArgs.add( arg );
    }

    public void addProperty( String propertyName, Object value )
    {
        properties.put( propertyName, value );
    }
}