/* Created on Aug 20, 2004 */
package org.codehaus.marmalade.tags.core;

import org.codehaus.marmalade.discovery.TaglibResolutionStrategy;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.TaglibResolutionStrategyOwner;

/**
 * @author jdcasey
 */
public class TaglibResolutionStrategyTag
    extends AbstractMarmaladeTag
{
    public static final String CLASS_ATTRIBUTE = "class";

    protected boolean alwaysProcessChildren()
    {
        return false;
    }

    protected void doExecute( MarmaladeExecutionContext context ) throws MarmaladeExecutionException
    {
        String strategyClassName = (String) requireTagAttribute( CLASS_ATTRIBUTE, String.class, context );
        ClassLoader cloader = Thread.currentThread().getContextClassLoader();

        try
        {
            Class stratClass = cloader.loadClass( strategyClassName );

            TaglibResolutionStrategy strat = (TaglibResolutionStrategy) stratClass.newInstance();

            TaglibResolutionStrategyOwner parent = (TaglibResolutionStrategyOwner) requireParent( TaglibResolutionStrategyOwner.class );

            parent.addTaglibResolutionStrategy( strat );
        }
        catch ( ClassNotFoundException e )
        {
            throw new MarmaladeExecutionException( "cannot find strategy class", e );
        }
        catch ( InstantiationException e )
        {
            throw new MarmaladeExecutionException( "cannot instantiate strategy", e );
        }
        catch ( IllegalAccessException e )
        {
            throw new MarmaladeExecutionException( "cannot access strategy constructor", e );
        }
    }
}