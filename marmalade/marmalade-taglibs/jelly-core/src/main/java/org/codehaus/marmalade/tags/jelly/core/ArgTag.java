/* Created on Apr 18, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;



/**
 * @author jdcasey
 */
public class ArgTag extends AbstractMarmaladeTag{
  
  public static final String VALUE_ATTRIBUTE = "value";

  public ArgTag(MarmaladeTagInfo tagInfo){
      super(tagInfo);
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    requireParent(ArgParent.class);
    
    MarmaladeAttributes attributes = getAttributes();
    
    Object arg = attributes.getValue(VALUE_ATTRIBUTE, context);
    if(arg == null) {
      arg = getBody(context);
    }
    
    if(arg == null) {
      throw new MarmaladeExecutionException(
        "You must specify either the \'" + 
        VALUE_ATTRIBUTE +  
        "\' attribute or body content for the arg tag."
      );
    }
    else {
      ArgParent parent = (ArgParent)getParent();
      parent.addArgument(arg);
    }
  }
}
