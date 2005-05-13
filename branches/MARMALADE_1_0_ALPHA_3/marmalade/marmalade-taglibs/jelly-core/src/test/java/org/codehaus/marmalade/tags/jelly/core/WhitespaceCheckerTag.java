/* Created on Jun 28, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.jelly.AbstractJellyMarmaladeTag;

/**
 * @author jdcasey
 */
public class WhitespaceCheckerTag extends AbstractJellyMarmaladeTag
{
    private String body;
    private final boolean trim;

    public WhitespaceCheckerTag( boolean trim )
    {
        this.trim = trim;
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        this.body = getRawBody( context );
    }

    public String getMyBody(  )
    {
        return body;
    }

    protected boolean preserveBodyWhitespace( MarmaladeExecutionContext context )
        throws ExpressionEvaluationException
    {
        return trim;
    }
}
