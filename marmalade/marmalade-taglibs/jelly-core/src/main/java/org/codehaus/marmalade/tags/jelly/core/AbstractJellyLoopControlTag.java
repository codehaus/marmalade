/* Created on Jun 26, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.tags.AbstractLoopControlTag;
import org.codehaus.marmalade.tags.AbstractLoopingTag;

/**
 * @author jdcasey
 */
public abstract class AbstractJellyLoopControlTag extends AbstractLoopControlTag {

    public static final String TRIM_ATTRIBUTE = "trim";

    protected AbstractJellyLoopControlTag(MarmaladeTagInfo tagInfo) {
        super(tagInfo);
    }

    protected boolean preserveBodyWhitespace(MarmaladeExecutionContext context) throws ExpressionEvaluationException {
        Boolean preserveWS = (Boolean)getAttributes().getValue(TRIM_ATTRIBUTE, Boolean.class, context, Boolean.FALSE);
        return preserveWS.booleanValue();
    }

}
