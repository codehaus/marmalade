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
public class ChooseTag extends AbstractMarmaladeTag {
  
  private OtherwiseTag otherwise;

  public ChooseTag() {
  }

  public void processChildren(MarmaladeExecutionContext context)
  throws MarmaladeExecutionException
  {
    boolean conditionHit = false;
    for (Iterator it = children().iterator(); it.hasNext();) {
      MarmaladeTag child = (MarmaladeTag)it.next();
      if(child instanceof AbstractConditionalTag){
        conditionHit = ((AbstractConditionalTag)child).conditionMatches(context);
        if(conditionHit){
          child.execute(context);
          break;
        }
      }
      else{
        child.execute(context);
      }
    }
    
    if(!conditionHit && otherwise != null){
      otherwise.execute(context);
    }
  }

  protected boolean shouldAddChild(MarmaladeTag child) {
    if(child instanceof OtherwiseTag){
      this.otherwise = (OtherwiseTag)child;
      return false;
    }
    else{
      return true;
    }
  }

}
