/* Created on Jun 26, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;

/**
 * @author jdcasey
 */
public abstract class AbstractJellyMarmaladeTag extends AbstractMarmaladeTag {

    public static final String TRIM_ATTRIBUTE = "trim";

    protected AbstractJellyMarmaladeTag(MarmaladeTagInfo tagInfo) {
        super(tagInfo);
    }

    protected boolean preserveBodyWhitespace(MarmaladeExecutionContext context) throws ExpressionEvaluationException {
        Boolean preserveWS = (Boolean)getAttributes().getValue(TRIM_ATTRIBUTE, Boolean.class, context, Boolean.FALSE);
        return preserveWS.booleanValue();
    }

}
