 /* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit.link;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.httpunit.link.*;
import org.codehaus.marmalade.tags.httpunit.structure.*;


/**
 * @author jdcasey
 */
public class LinkHasUrlPatternTag extends AbstractLinkSubAssertionTag{
  
  public static final String WITH_PATTERN_ATTRIBUTE = "withPattern";

  public LinkHasUrlPatternTag(){
  }

  protected boolean test(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    String value = (String)requireTagAttribute(WITH_PATTERN_ATTRIBUTE, String.class, context);
    return getLink(context).getURLString().matches(value);
  }

}
