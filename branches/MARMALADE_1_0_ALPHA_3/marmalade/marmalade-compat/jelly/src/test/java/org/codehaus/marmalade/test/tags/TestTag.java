/* Created on Jul 13, 2004 */
package org.codehaus.marmalade.test.tags;

import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class TestTag extends AbstractMarmaladeTag {

    private boolean fired;
    
    public boolean tagFired() {
        return fired;
    }

    protected void doExecute(MarmaladeExecutionContext context)
    throws MarmaladeExecutionException 
    {
        fired = true;
    }
}
