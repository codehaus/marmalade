/* Created on Jun 23, 2004 */
package org.codehaus.marmalade.model;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;

/**
 * @author jdcasey
 */
public interface MarmaladeAttribute {

    public String getNamespace();
    
    public String getName();
    
    public Object getValue(MarmaladeExecutionContext context) throws ExpressionEvaluationException;
    
    public Object getValue(Class expectedReturnType, MarmaladeExecutionContext context) throws ExpressionEvaluationException;
    
}
