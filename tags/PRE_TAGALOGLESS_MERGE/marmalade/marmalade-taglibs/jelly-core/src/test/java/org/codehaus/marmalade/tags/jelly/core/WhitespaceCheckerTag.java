/* Created on Jun 28, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.jelly.AbstractJellyMarmaladeTag;

/**
 * @author jdcasey
 */
public class WhitespaceCheckerTag extends AbstractJellyMarmaladeTag {
    
    private String body;

    public WhitespaceCheckerTag(MarmaladeTagInfo tagInfo) {
        super(tagInfo);
    }

    protected void doExecute(MarmaladeExecutionContext context)
    throws MarmaladeExecutionException 
    {
        this.body = getRawBody(context);
    }

    public String getMyBody() {
        return body;
    }
    
}
