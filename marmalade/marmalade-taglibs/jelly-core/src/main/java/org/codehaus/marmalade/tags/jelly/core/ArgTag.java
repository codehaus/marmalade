/* Created on Apr 18, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.MarmaladeAttributes;
import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.abstractions.AbstractMarmaladeTag;


/**
 * @author jdcasey
 */
public class ArgTag extends AbstractMarmaladeTag{
  
  public static final String VALUE_ATTRIBUTE = "value";

  public ArgTag(){
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
