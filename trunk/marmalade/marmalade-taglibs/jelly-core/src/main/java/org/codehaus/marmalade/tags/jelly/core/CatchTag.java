/* Created on Apr 10, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class CatchTag extends AbstractMarmaladeTag {
  
  public static final String VAR_ATTRIBUTE = "var";
  public static final String CLASS_ATTRIBUTE = "class";

  public CatchTag(MarmaladeTagInfo tagInfo) {
      super(tagInfo);
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException {
    Object binding = (String)requireTagAttribute(VAR_ATTRIBUTE, context);
    try{
      processChildren(context);
    }
    catch(Throwable e) {
      String className = (String)getAttributes().getValue(CLASS_ATTRIBUTE, String.class, context);
      if(className != null && className.length() > 0) {
        if(e.getClass().getName().equals(className)) {
          context.setVariable(binding, e);
        }
        else {
          throw new MarmaladeExecutionException("Uncaught execution-phase exception.", e);
        }
      }
      else {
        context.setVariable(binding, e);
      }
    }
  }

}
