/* Created on Apr 11, 2004 */
package org.codehaus.marmalade.tags.core;

import org.codehaus.marmalade.AbstractMarmaladeTag;
import org.codehaus.marmalade.MarmaladeAttributes;
import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.el.ExpressionEvaluator;

/**
 * @author jdcasey
 */
public class SetTag extends AbstractMarmaladeTag {
  
  public static final String VAR_ATTRIBUTE = "var";
  public static final String VALUE_ATTRIBUTE = "value";
  public static final String PROPERTY_ATTRIBUTE = "property";
  public static final String TARGET_ATTRIBUTE = "target";

  public SetTag() {
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException {
    Object value = requireTagAttribute(VALUE_ATTRIBUTE, context);
    
    MarmaladeAttributes attributes = getAttributes();
    String var = (String)attributes.getValue(VAR_ATTRIBUTE, String.class, context);
    if(var != null && var.length() > 0){
      context.setVariable(var, value);
    }
    else{
      setObjectProperty(value, attributes, context);
    }
  }

  private void setObjectProperty(Object value, MarmaladeAttributes attributes, 
                                  MarmaladeExecutionContext context)
  throws MarmaladeExecutionException
  {
    Object target = requireTagAttribute(TARGET_ATTRIBUTE, context);
    String property = (String)requireTagAttribute(PROPERTY_ATTRIBUTE, String.class, context);
    
    ExpressionEvaluator el = getExpressionEvaluator();
    el.assign(target, property, value);
  }

}
