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
public class KeyTag
    extends AbstractMarmaladeTag
{
    
    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        Object key = getBody(context);
        
        if(key == null)
        {
            throw new TagExecutionException(getTagInfo(), "Key-tag body must not be null.");
        }
        
        KeyTagParent parent = (KeyTagParent) requireParent(KeyTagParent.class);
        parent.setKey(key);
    }
}
