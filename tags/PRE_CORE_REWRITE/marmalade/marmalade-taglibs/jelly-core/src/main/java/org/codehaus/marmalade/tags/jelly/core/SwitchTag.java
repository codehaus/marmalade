/* Created on Apr 10, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import java.util.Iterator;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.MarmaladeTag;
import org.codehaus.marmalade.abstractions.AbstractConditionalTag;
import org.codehaus.marmalade.abstractions.AbstractMarmaladeTag;

/**
 * @author jdcasey
 */
public class SwitchTag extends AbstractMarmaladeTag {
  
  public static final String ON_ATTRIBUTE = "on";
  
  private DefaultTag def;

  private Object testObject;

  public SwitchTag() {
  }

  public void processChildren(MarmaladeExecutionContext context)
  throws MarmaladeExecutionException
  {
    boolean blockDefaultRun = false;
    for (Iterator it = children().iterator(); it.hasNext();) {
      MarmaladeTag child = (MarmaladeTag)it.next();
      if(child instanceof CaseTag){
        CaseTag tag = (CaseTag)child;
        tag.setTestTarget(testObject);
        
        boolean run = tag.conditionMatches(context);
        if(run){
          child.execute(context);
          
          if(!tag.fallThrough()) {
            blockDefaultRun = true;
            break;
          }
          
        }
      }
      else{
        child.execute(context);
      }
    }
    
    if(!blockDefaultRun && def != null){
      def.execute(context);
    }
  }

  protected boolean shouldAddChild(MarmaladeTag child) {
    if(child instanceof DefaultTag){
      this.def = (DefaultTag)child;
      return false;
    }
    else{
      return true;
    }
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    this.testObject = requireTagAttribute(ON_ATTRIBUTE, context);
  }

  protected boolean alwaysProcessChildren(){
    return false;
  }
}
