package org.codehaus.marmalade.tags.lang;

import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.TagExecutionException;

/**
 * @author jdcasey
 *
 * Created on Feb 4, 2005
 */
public class NameTag
    extends AbstractMarmaladeTag
{
    
    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        String name = (String) getBody(context, String.class);
        
        if(name == null)
        {
            throw new TagExecutionException(getTagInfo(), "Name-tag body must not be null.");
        }
        
        NameTagParent parent = (NameTagParent) requireParent(NameTagParent.class);
        parent.setName(name);
    }
}
