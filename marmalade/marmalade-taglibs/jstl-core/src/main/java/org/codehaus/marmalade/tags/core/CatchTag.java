/* Created on Apr 10, 2004 */
package org.codehaus.marmalade.tags.core;

import org.codehaus.marmalade.AbstractMarmaladeTag;
import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class CatchTag extends AbstractMarmaladeTag {
  
  public static final String VAR_ATTRIBUTE = "var";

  public CatchTag() {
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException {
    Object binding = (String)requireTagAttribute(VAR_ATTRIBUTE, context);
    System.out.println("Binding to: " + binding);
    try{
      processChildren(context);
    }
    catch(Throwable e) {
      context.setVariable(binding, e);
    }
  }

}
