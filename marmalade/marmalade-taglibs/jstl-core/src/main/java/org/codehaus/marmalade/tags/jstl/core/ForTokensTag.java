/* Created on Apr 10, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import java.util.LinkedList;
import java.util.List;

import org.codehaus.marmalade.MarmaladeAttributes;
import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.generics.AbstractLoopingTag;

/**
 * @author jdcasey
 */
public class ForTokensTag extends ForEachTag {

  public static final String DELIMS_ATTRIBUTE = "delims";
  
  public ForTokensTag() {
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException {
    String delims = (String)requireTagAttribute(DELIMS_ATTRIBUTE, String.class, context);
    context.setVariable(ForEachTag.DELIMS_CONTEXT_KEY, delims);
    
    super.doExecute(context);
  }

}
