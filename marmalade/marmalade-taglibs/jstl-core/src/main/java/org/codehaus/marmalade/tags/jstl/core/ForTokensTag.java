/* Created on Apr 10, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import java.util.LinkedList;
import java.util.List;

import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class ForTokensTag extends ForEachTag {

  public static final String DELIMS_ATTRIBUTE = "delims";
  
  public ForTokensTag(MarmaladeTagInfo tagInfo) {
    super(tagInfo);
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException {
    String delims = (String)requireTagAttribute(DELIMS_ATTRIBUTE, String.class, context);
    context.setVariable(ForEachTag.DELIMS_CONTEXT_KEY, delims);
    
    super.doExecute(context);
  }

}
