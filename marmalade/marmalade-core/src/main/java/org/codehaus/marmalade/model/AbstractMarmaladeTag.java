/* Created on Mar 24, 2004 */
package org.codehaus.marmalade.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.marmalade.*;
import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.el.ExpressionEvaluatorFactory;
import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.*;
import org.codehaus.tagalog.AbstractTag;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.Tag;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;

/** Base class implementing common marmalade tag features.
 * 
 * @author John Casey
 */
public abstract class AbstractMarmaladeTag implements MarmaladeTag {
  
  public static final String MARMALADE_EL_PI_NAMESPACE = "marmalade-el";
  public static final String MARMALADE_EL_ATTRIBUTE = "marmalade:el";
  
  private String element;
  
  private ExpressionEvaluator el;
  private MarmaladeAttributes attributes;
  private MarmaladeTagInfo tagInfo;
  
  private boolean childrenProcessed = false;
  private List children = new ArrayList();
  private MarmaladeTag parent;

  protected AbstractMarmaladeTag(MarmaladeTagInfo tagInfo) {
    this.tagInfo = tagInfo;
  }
  
  public final void setParent(MarmaladeTag parent) {
    this.parent = parent;
  }
  
  public final void addChild(MarmaladeTag child) {
    children.add(child);
  }
  
  public final MarmaladeTag getParent() {
    return parent;
  }
  
// --------------------- CONCRETE IMPLEMENTATIONS CAN OVERRIDE THESE --------------------- //
  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException {
  }
  
  protected boolean alwaysProcessChildren() {
    return true;
  }
  
  protected void doReset() {
  }
  
  protected boolean shouldAddChild(MarmaladeTag child){
    return true;
  }
  
// ------------------ MARMALADE TAG IMPLEMENTATION DETAILS ------------------ //
  public final void execute(MarmaladeExecutionContext context) throws MarmaladeExecutionException {
    doExecute(context);
    if(!childrenProcessed && alwaysProcessChildren()){
      processChildren(context);
    }
    reset();
  }
  
  protected final void resetChildrenProcessedFlag(){
    this.childrenProcessed = false;
  }

  public void processChildren(MarmaladeExecutionContext context)
  throws MarmaladeExecutionException
  {
    try {
      for (Iterator it = children.iterator(); it.hasNext();) {
        MarmaladeTag child = (MarmaladeTag)it.next();
        child.execute(context);
      }
    }
    finally {
      childrenProcessed = true;
    }
  }
  
  protected final List children(){
    return Collections.unmodifiableList(children);
  }

  protected final void reset(){
    doReset();
    childrenProcessed = false;
  }
  
  public final MarmaladeAttributes getAttributes() {
    return attributes;
  }
  
  public final ExpressionEvaluator getExpressionEvaluator() 
  throws ConfigurationException
  {
    return el;
  }
  
  public final Object getBody(MarmaladeExecutionContext context)
  throws ExpressionEvaluationException
  {
    return _getBody(context, Object.class);
  }

  public final Object getBody(MarmaladeExecutionContext context, Class targetType)
  throws ExpressionEvaluationException
  {
    return _getBody(context, targetType);
  }

  private Object _getBody(MarmaladeExecutionContext context, Class targetType)
  throws ExpressionEvaluationException
  {
    String expression = tagInfo.getText().toString();
    Object result = null;
    if(el == null) {
      if(targetType.isAssignableFrom(String.class)) {
        result = expression;
      }
      else {
        throw new ExpressionEvaluationException(
          "Expression cannot be evaluated and is not an instance of " + targetType.getName()
        );
      }
    }
    result = el.evaluate(expression, context.unmodifiableVariableMap(), targetType);
    
    return result;
  }

  private void loadExpressionEvaluator(Attributes parseAttrs) 
  throws ConfigurationException
  {
    if(el == null){
      //First, try to pull an attribute with the el.
      String elType = parseAttrs.getValue(MARMALADE_EL_ATTRIBUTE);
    
      //Then, start searching...try to get the parent's evaluator.
      if(elType == null || elType.length() < 1){
        MarmaladeTag parent = getParent();
        el = parent.getExpressionEvaluator();
      
        //If we cannot find an evaluator for the parent, check the processing instructions.
/*        
        if(el == null){
          Map processingInstructions = (Map)context.get(TagalogParser.PROCESSING_INSTRUCTIONS);
          if(processingInstructions != null){
            List piSet = (List)processingInstructions.get(MARMALADE_EL_PI_NAMESPACE);
            if(piSet != null && !piSet.isEmpty()){
              elType = (String)piSet.get(0);
            }
          }
        }
*/        
      }
    
      if(el == null){
        if(elType == null || elType.length() < 1){
          el = ExpressionEvaluatorFactory.getDefaultExpressionEvaluator();
        }
        else{
          el = ExpressionEvaluatorFactory.getExpressionEvaluator(elType);
        }
      }
    }
  }



  protected Object requireTagAttribute(String name, Class type, MarmaladeExecutionContext context)
  throws MissingAttributeException, ExpressionEvaluationException
  {
    return _requireTagAttribute(name, type, context);
  }
  
  protected Object requireTagAttribute(String name, MarmaladeExecutionContext context)
  throws MissingAttributeException, ExpressionEvaluationException
  {
    return _requireTagAttribute(name, Object.class, context);
  }
  
  private Object _requireTagAttribute(String name, Class type, MarmaladeExecutionContext context)
  throws MissingAttributeException, ExpressionEvaluationException
  {
    Object result = attributes.getValue(name, type, context);
    if(result == null){
      throw new MissingAttributeException(element, name);
    }
    else{
      return result;
    }
  }
  
  protected MarmaladeTag requireParent(Class cls) throws IllegalParentException
  {
    MarmaladeTag parent = getParent();
    if(parent != null) {
      if(!cls.isAssignableFrom(parent.getClass())){
        throw new IllegalParentException(element, cls);
      }
    }
    else {
      throw new IllegalParentException(element, cls);
    }
    
    return parent;
  }

  protected MarmaladeTag requireAncestor(Class cls) throws IllegalAncestorException
  {
    MarmaladeTag parent = this;
    while((parent = parent.getParent()) != null) {
      if(cls.isAssignableFrom(parent.getClass())){
        break;
      }
    }
    
    if(parent == null) {
      throw new IllegalAncestorException(element, cls);
    }
    else {
      return parent;
    }
  }

}
