/* Created on Jun 26, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.tags.AbstractConditionalTag;

/**
 * @author jdcasey
 */
public abstract class AbstractJellyConditionalTag extends AbstractConditionalTag {

    public static final String TRIM_ATTRIBUTE = "trim";

    protected AbstractJellyConditionalTag(MarmaladeTagInfo tagInfo) {
        super(tagInfo);
    }

    protected boolean preserveBodyWhitespace(MarmaladeExecutionContext context) throws ExpressionEvaluationException {
        Boolean preserveWS = (Boolean)getAttributes().getValue(TRIM_ATTRIBUTE, Boolean.class, context, Boolean.FALSE);
        return preserveWS.booleanValue();
    }

}
