/* Created on Apr 10, 2004 */
package org.codehaus.marmalade;

import java.util.List;
import java.util.StringTokenizer;


/**
 * @author jdcasey
 */
public abstract class AbstractLoopingTag extends AbstractMarmaladeTag {

  protected AbstractLoopingTag() {
  }
  
  protected final void executeLoop(LoopStep[] steps, String itemBinding, String statusBinding, 
                                    MarmaladeExecutionContext context)
  throws MarmaladeExecutionException
  {
    context.newScope();
    for (int i = 0; i < steps.length; i++) {
      LoopStep step = steps[i];
      Object item = step.getItem();
      
      context.setVariable(itemBinding, item);
      if(statusBinding != null && statusBinding.length() > 0){
        context.setVariable(statusBinding, step);
      }
      
      processChildren(context);
    }
    context.lastScope();
  }
  
  public static final class LoopStep implements LoopStatus{
    private Object item;
    private int index;
    private int begin;
    private int end;
    private int step;
    
    public LoopStep(Object item, int begin, int end, int step, int index){
      this.item = item;
      this.index = index;
      this.begin = begin;
      this.end = end;
      this.step = step;
    }
    
    protected Object getItem(){
      return item;
    }
    
    public int getIndex() {
      return index;
    }

    public int getBegin() {
      return begin;
    }

    public int getEnd() {
      return end;
    }

    public int getStep() {
      return step;
    }
  }

  protected void getItemsFromString(String items, String delims, List itemList, int begin, int end, int step) {
    StringTokenizer st = new StringTokenizer(items, delims);
    int i=0;
    
    while(st.hasMoreTokens()){
      String item = st.nextToken().trim();
      
      if(i < begin || i > end || end < 0 || (i % step != 0)){
        continue;
      }
      else{
        itemList.add(new LoopStep(item, begin, end, step, i));
      }
      
      i++;
    }
  }
}
