/* Created on Apr 10, 2004 */
package org.codehaus.marmalade.model;

import java.util.Map;
import java.util.TreeMap;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.tagalog.Attributes;

/**
 * @author jdcasey
 */
public class DefaultAttributes implements MarmaladeAttributes {
  
  private ExpressionEvaluator el;
  private Map parsedAttributes = new TreeMap();

  public DefaultAttributes(ExpressionEvaluator el, Attributes attributes) {
    this.el = el;
    
    int attrCount = attributes.getAttributeCount();
    for(int i=0; i < attrCount; i++) {
      String name = attributes.getName(i);
      String ns = attributes.getNamespaceUri(i);
      String value = attributes.getValue(i);
      
      DefaultAttribute attr = new DefaultAttribute(
        ns, name, value
      );
      parsedAttributes.put(name, attr);
    }
  }

  public ExpressionEvaluator getExpressionEvaluator() {
    return el;
  }

  public Object getValue(String name, MarmaladeExecutionContext context)
  throws ExpressionEvaluationException
  {
    return _getValue(name, Object.class, context.unmodifiableVariableMap(), null);
  }
  
  public Object getValue(String name, MarmaladeExecutionContext context, Object defaultVal) 
  throws ExpressionEvaluationException
  {
    return _getValue(name, Object.class, context.unmodifiableVariableMap(), defaultVal);
  }

  public Object getValue(String name, Class type, MarmaladeExecutionContext context)
  throws ExpressionEvaluationException
  {
    return _getValue(name, type, context.unmodifiableVariableMap(), null);
  }
  
  public Object getValue(String name, Class type, MarmaladeExecutionContext context, 
                          Object defaultVal) 
  throws ExpressionEvaluationException
  {
    return _getValue(name, type, context.unmodifiableVariableMap(), defaultVal);
  }

  private Object _getValue(String name, Class type, Map context, Object defaultVal)
  throws ExpressionEvaluationException
  {
    DefaultAttribute attr = (DefaultAttribute)parsedAttributes.get(name);
    if(attr == null) {return null;}
    
    String expression = attr.getValue();
    Object result = null;
    if(expression != null && expression.length() > 0){
      if(el != null){
        result = el.evaluate(expression, context, type);
      }
      else{
        result = expression;
      }
    }
/*    
    if(result != null && !type.isAssignableFrom(result.getClass())) {
      throw new ExpressionEvaluationException(
        "Expression: \'" + 
        expression + 
        "\' for attribute: " + 
        name + 
        " returned result of type: " + 
        result.getClass().getName() + 
        "; expected: " + 
        type.getName()
      );
    }
*/    
    if(result == null) {
      result = defaultVal;
    }
    
    return result;
  }
  
  private static final class DefaultAttribute{
    private String namespace;
    private String name;
    private String value;
    
    DefaultAttribute(String namespace, String name, String value){
      this.namespace = namespace;
      this.name = name;
      this.value = value;
    }
    /**
     * @return Returns the name.
     */
    public String getName(){
      return name;
    }
    /**
     * @return Returns the namespace.
     */
    public String getNamespace(){
      return namespace;
    }
    /**
     * @return Returns the value.
     */
    public String getValue(){
      return value;
    }
  }

}
