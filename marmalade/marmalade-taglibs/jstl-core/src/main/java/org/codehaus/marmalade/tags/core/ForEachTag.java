/* Created on Apr 10, 2004 */
package org.codehaus.marmalade.tags.core;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.marmalade.AbstractLoopingTag;
import org.codehaus.marmalade.MarmaladeAttributes;
import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class ForEachTag extends AbstractLoopingTag {

  public static final String ITEMS_ATTRIBUTE = "items";
  public static final String VAR_ATTRIBUTE = "var";
  public static final String VAR_STATUS_ATTRIBUTE = "varStatus";
  public static final String BEGIN_ATTRIBUTE = "begin";
  public static final String END_ATTRIBUTE = "end";
  public static final String STEP_ATTRIBUTE = "step";
  
  private static final Integer DEFAULT_BEGIN = new Integer(0);
  private static final Integer DEFAULT_END = new Integer(-1);
  private static final Integer DEFAULT_STEP = new Integer(1);

  public ForEachTag() {
  }

  protected boolean alwaysProcessChildren() {
    return false;
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException {
    Object items = requireTagAttribute(ITEMS_ATTRIBUTE, context);
    
    // Don't need this until later, but we want to fail early for efficiency...
    String var = (String)requireTagAttribute(VAR_ATTRIBUTE, String.class, context);
    
    MarmaladeAttributes attributes = getAttributes();
    
    Integer beginI = (Integer)attributes.getValue(
      BEGIN_ATTRIBUTE, Integer.class, context, DEFAULT_BEGIN
    );
    
    int begin = 0;
    if(beginI != null) {begin = beginI.intValue();}
    if(begin < 0){begin = 0;}
    
    Integer endI = (Integer)attributes.getValue(
      END_ATTRIBUTE, Integer.class, context, DEFAULT_END
    );
    
    int end = -1;
    if(endI != null) {end = endI.intValue();}
    if(end < -1){end = -1;}
    
    Integer stepI = (Integer)attributes.getValue(
      STEP_ATTRIBUTE, Integer.class, context, DEFAULT_STEP
    );
    
    int step = 1;
    if(stepI != null) {step = stepI.intValue();}
    if(step < 1){step = 1;}
    
    List itemList = new LinkedList();
    if(items instanceof Collection){
      getItemsFromCollection(((Collection)items), itemList, begin, end, step);
    }
    else if(items.getClass().isArray()) {
      Object[] itemArray = (Object[])items;
      for(int i = 0; i < itemArray.length; i++){
        Object item = itemArray[i];
        if(i < begin || (i > end && end > 0) || (i % step != 0)){
          continue;
        }
        else{
          itemList.add(new LoopStep(item, begin, end, step, i));
        }
      }
    }
    else if(items instanceof String){
      getItemsFromString((String)items, ",", itemList, begin, end, step);
    }
    else{
      if(begin == 0){
        itemList.add(new LoopStep(items, 0, 0, 1, 0));
      }
    }
    
    if(!itemList.isEmpty()){
      String varStatus = (String)attributes.getValue(VAR_STATUS_ATTRIBUTE, String.class, context);
      LoopStep[] steps = (LoopStep[])itemList.toArray(new LoopStep[itemList.size()]);
      executeLoop(steps, var, varStatus, context);
    }
  }

  private void getItemsFromCollection(Collection items, List itemList, int begin, int end, 
                                        int step)
  {
    int i=0;
    for (Iterator it = items.iterator(); it.hasNext();) {
      Object item = it.next();
      
      if(i < begin || (i > end && end > 0) || (i % step != 0)){
        continue;
      }
      else{
        itemList.add(new LoopStep(item, begin, end, step, i));
      }
      
      i++;
    }
  }

}