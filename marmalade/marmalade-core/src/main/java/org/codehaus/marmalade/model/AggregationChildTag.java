package org.codehaus.marmalade.model;

import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;


public class AggregationChildTag
    extends AbstractMarmaladeTag
{

    private final AggregatorMarmaladeTag parentTag;

    public AggregationChildTag( AggregatorMarmaladeTag parentTag )
    {
        this.parentTag = parentTag;
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        parentTag.addChild( getTagInfo(), getAttributes() );
        
        processChildren( context );
    }

}
