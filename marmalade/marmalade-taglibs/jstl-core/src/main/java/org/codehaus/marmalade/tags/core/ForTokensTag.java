/* Created on Apr 10, 2004 */
package org.codehaus.marmalade.tags.core;

import java.util.LinkedList;
import java.util.List;

import org.codehaus.marmalade.AbstractLoopingTag;
import org.codehaus.marmalade.MarmaladeAttributes;
import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class ForTokensTag extends AbstractLoopingTag {

  public static final String ITEMS_ATTRIBUTE = "items";
  public static final String VAR_ATTRIBUTE = "var";
  public static final String VAR_STATUS_ATTRIBUTE = "varStatus";
  public static final String BEGIN_ATTRIBUTE = "begin";
  public static final String END_ATTRIBUTE = "end";
  public static final String STEP_ATTRIBUTE = "step";
  public static final String DELIMS_ATTRIBUTE = "delims";
  
  private static final Integer DEFAULT_BEGIN = new Integer(0);
  private static final Integer DEFAULT_END = new Integer(-1);
  private static final Integer DEFAULT_STEP = new Integer(1);
  private static final String DEFAULT_DELIMS = ",";

  public ForTokensTag() {
  }

  protected boolean alwaysProcessChildren() {
    return false;
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException {
    String items = (String)requireTagAttribute(ITEMS_ATTRIBUTE, String.class, context);
    String delims = (String)requireTagAttribute(DELIMS_ATTRIBUTE, String.class, context);
    
    // Don't need this until later, but we want to fail early for efficiency...
    String var = (String)requireTagAttribute(VAR_ATTRIBUTE, String.class, context);
    
    MarmaladeAttributes attributes = getAttributes();
    
    int begin = ((Integer)attributes.getValue(
      BEGIN_ATTRIBUTE, Integer.class, context, DEFAULT_BEGIN
    )).intValue();
    if(begin < 0){begin = 0;}
    
    int end = ((Integer)attributes.getValue(
      END_ATTRIBUTE, Integer.class, context, DEFAULT_END
    )).intValue();
    if(end < -1){end = -1;}
    
    int step = ((Integer)attributes.getValue(
      STEP_ATTRIBUTE, Integer.class, context, DEFAULT_STEP
    )).intValue();
    if(step < 1){step = 1;}
    
    List itemList = new LinkedList();
    getItemsFromString(items, delims, itemList, begin, end, step);
    
    if(!itemList.isEmpty()){
      String varStatus = (String)attributes.getValue(VAR_STATUS_ATTRIBUTE, String.class, context);
      LoopStep[] steps = (LoopStep[])itemList.toArray(new LoopStep[itemList.size()]);
      executeLoop(steps, var, varStatus, context);
    }
  }

}
