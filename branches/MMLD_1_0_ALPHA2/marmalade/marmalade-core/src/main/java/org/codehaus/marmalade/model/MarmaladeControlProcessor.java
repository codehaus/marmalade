package org.codehaus.marmalade.model;

import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.el.ExpressionEvaluatorFactory;
import org.codehaus.marmalade.el.PassThroughExpressionEvaluator;
import org.codehaus.marmalade.metamodel.MetaAttributes;

/**
 * @author jdcasey
 *
 * Created on Feb 8, 2005
 */
public final class MarmaladeControlProcessor
{
    
    private static ExpressionEvaluator PASS_THROUGH_EL = new PassThroughExpressionEvaluator();
    
    private MarmaladeControlProcessor() {}

    public static MarmaladeTag activateTagControls( MetaAttributes metaAttributes, MarmaladeTag tag )
    {
        String elName = metaAttributes.getValue(MarmaladeControlDefinitions.MARMALADE_RESERVED_NS, MarmaladeControlDefinitions.EXPRESSION_EVALUATOR_ATTRIBUTE);
        
        if(elName != null && elName.trim().length() > 0)
        {
            ExpressionEvaluator el = null;
            if("none".equalsIgnoreCase(elName))
            {
                el = PASS_THROUGH_EL;
            }
            else
            {
                el = new ExpressionEvaluatorFactory().getExpressionEvaluator(elName);
            }
            
            tag.setExpressionEvaluator(el);
        }
        
        String preserveWS = metaAttributes.getValue(MarmaladeControlDefinitions.MARMALADE_RESERVED_NS, MarmaladeControlDefinitions.PRESERVE_BODY_WHITESPACE_ATTRIBUTE);
        
        if(preserveWS != null && preserveWS.trim().length() > 0)
        {
            Boolean preserve = Boolean.valueOf(preserveWS);
            if(preserve != null)
            {
                tag.setPreserveBodyWhitespace(preserve.booleanValue());
            }
        }
        
        return tag;
    }

}
