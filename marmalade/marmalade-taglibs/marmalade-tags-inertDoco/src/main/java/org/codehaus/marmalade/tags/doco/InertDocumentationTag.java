package org.codehaus.marmalade.tags.doco;

import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 *
 * Created on Feb 8, 2005
 */
public class InertDocumentationTag
    extends AbstractMarmaladeTag
{

    protected boolean alwaysProcessChildren()
    {
        return false;
    }
    
    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        // do nothing. This element is for documentation only, and provides
        // a hook for alternate overriding implementations to extract the
        // doco.
    }
}
