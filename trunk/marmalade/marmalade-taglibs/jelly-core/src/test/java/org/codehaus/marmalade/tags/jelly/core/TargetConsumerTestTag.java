/* Created on Jun 24, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class TargetConsumerTestTag extends AbstractMarmaladeTag {
    
    private Object target;

    public TargetConsumerTestTag(MarmaladeTagInfo tagInfo) {
        super(tagInfo);
    }
    
    public Object getTarget() {
        return target;
    }

    protected void doExecute(MarmaladeExecutionContext context)
    throws MarmaladeExecutionException 
    {
        TargetObjectOwner owner = (TargetObjectOwner) requireAncestor(TargetObjectOwner.class);
        this.target = owner.getTarget();
    }
}
