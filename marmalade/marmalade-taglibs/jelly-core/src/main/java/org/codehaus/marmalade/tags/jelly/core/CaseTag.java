/* Created on Apr 11, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.AbstractConditionalTag;

/**
 * @author jdcasey
 */
public class CaseTag extends AbstractConditionalTag {
  
  public static final String VALUE_ATTRIBUTE = "value";
  public static final String FALL_THROUGH_ATTRIBUTE = "fallThru";

  private Object testTarget;
  private boolean fallThrough = false;

  public CaseTag(MarmaladeTagInfo tagInfo) {
      super(tagInfo);
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException {
    requireParent(SwitchTag.class);
    
    if(conditionMatches(context)) {
      processChildren(context);
      Boolean fallThrough = (Boolean)getAttributes().getValue(
        FALL_THROUGH_ATTRIBUTE, Boolean.class, context, Boolean.FALSE
      );
      this.fallThrough = fallThrough.booleanValue();
    }
  }

  public void setTestTarget(Object testObject){
    this.testTarget = testObject;
  }

  public boolean fallThrough(){
    return fallThrough;
  }

  public boolean conditionMatches(MarmaladeExecutionContext context)
  throws MarmaladeExecutionException
  {
    return requireTagAttribute(VALUE_ATTRIBUTE, context).equals(testTarget);
  }

  protected void doReset(){
    testTarget = null;
    fallThrough = false;
    super.doReset();
  }
}
