/* Created on Apr 12, 2004 */
package org.codehaus.marmalade;

import java.util.HashMap;
import java.util.Map;

import ognl.Evaluation;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.tagalog.Attributes;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;


/**
 * @author jdcasey
 */
public class DefaultAttributesTest extends MockObjectTestCase{

  public void testGetExpressionEvaluator(){
  }

  /*
   * Class to test for Object getValue(String, MarmaladeExecutionContext)
   */
  public void testGetValue_String_Context() throws ExpressionEvaluationException{
    Mock elMock = mock(ExpressionEvaluator.class);
    elMock.expects(once())
          .method("evaluate")
          .with(eq("value"), isA(Map.class), same(Object.class))
          .will(returnValue("value"));
    
    Mock attrMock = mock(Attributes.class);
    attrMock.expects(once())
            .method("getValue")
            .with(eq("testKey"))
            .will(returnValue("value"));
    
    DefaultAttributes attrs = new DefaultAttributes(
      (ExpressionEvaluator)elMock.proxy(), (Attributes)attrMock.proxy()
    );
    
    Mock ctxMock = mock(MarmaladeExecutionContext.class);
    ctxMock.expects(once())
           .method("unmodifiableVariableMap")
           .withNoArguments()
           .will(returnValue(new HashMap()));
    
    Object result = attrs.getValue("testKey", (MarmaladeExecutionContext)ctxMock.proxy());
    assertEquals("Wrong attribute value.", "value", result);
    
    elMock.verify();
    attrMock.verify();
    ctxMock.verify();
  }

  /*
   * Class to test for Object getValue(String, MarmaladeExecutionContext, Object)
   */
  public void testGetValue_String_Context_Object() throws ExpressionEvaluationException{
    Mock elMock = mock(ExpressionEvaluator.class);
    elMock.expects(once())
          .method("evaluate")
          .with(eq("value"), isA(Map.class), same(Object.class))
          .will(returnValue(null));
    
    Mock attrMock = mock(Attributes.class);
    attrMock.expects(once())
            .method("getValue")
            .with(eq("testKey"))
            .will(returnValue("value"));
    
    DefaultAttributes attrs = new DefaultAttributes(
      (ExpressionEvaluator)elMock.proxy(), (Attributes)attrMock.proxy()
    );
    
    Mock ctxMock = mock(MarmaladeExecutionContext.class);
    ctxMock.expects(once())
           .method("unmodifiableVariableMap")
           .withNoArguments()
           .will(returnValue(new HashMap()));
    
    Object result = attrs.getValue("testKey", (MarmaladeExecutionContext)ctxMock.proxy(), "value2");
    assertEquals("Wrong attribute value.", "value2", result);
    
    elMock.verify();
    attrMock.verify();
    ctxMock.verify();
  }

  /*
   * Class to test for Object getValue(String, Class, MarmaladeExecutionContext)
   */
  public void testGetValue_String_Class_MarmaladeExecutionContext() throws ExpressionEvaluationException{
    Integer testVal = new Integer(10);
    
    Mock elMock = mock(ExpressionEvaluator.class);
    elMock.expects(atLeastOnce())
          .method("evaluate")
          .with(eq("value"), isA(Map.class), same(Object.class))
          .will(returnValue(testVal));
    
    Mock attrMock = mock(Attributes.class);
    attrMock.expects(atLeastOnce())
            .method("getValue")
            .with(eq("testKey"))
            .will(returnValue("value"));
    
    DefaultAttributes attrs = new DefaultAttributes(
      (ExpressionEvaluator)elMock.proxy(), (Attributes)attrMock.proxy()
    );
    
    Mock ctxMock = mock(MarmaladeExecutionContext.class);
    ctxMock.expects(atLeastOnce())
           .method("unmodifiableVariableMap")
           .withNoArguments()
           .will(returnValue(new HashMap()));
    
    try {
      Object result = attrs.getValue("testKey", String.class, (MarmaladeExecutionContext)ctxMock.proxy());
      fail("Should not allow specification of String.class and return type of Integer.class.");
    }
    catch(ExpressionEvaluationException e ) {
      e.printStackTrace();
    }
    
    Object result = attrs.getValue("testKey", Integer.class, (MarmaladeExecutionContext)ctxMock.proxy());
    assertEquals("Wrong attribute value.", testVal, result);
    
    elMock.verify();
    attrMock.verify();
    ctxMock.verify();
  }

  /*
   * Class to test for Object getValue(String, Class, MarmaladeExecutionContext, Object)
   */
  public void testGetValue_String_Class_MarmaladeExecutionContext_Object() throws ExpressionEvaluationException{
    Integer testVal = new Integer(10);
    
    Mock elMock = mock(ExpressionEvaluator.class);
    elMock.expects(once())
          .method("evaluate")
          .with(eq("value"), isA(Map.class), same(Object.class))
          .will(returnValue(null));
    
    Mock attrMock = mock(Attributes.class);
    attrMock.expects(once())
            .method("getValue")
            .with(eq("testKey"))
            .will(returnValue("value"));
    
    DefaultAttributes attrs = new DefaultAttributes(
      (ExpressionEvaluator)elMock.proxy(), (Attributes)attrMock.proxy()
    );
    
    Mock ctxMock = mock(MarmaladeExecutionContext.class);
    ctxMock.expects(once())
           .method("unmodifiableVariableMap")
           .withNoArguments()
           .will(returnValue(new HashMap()));
    
    Object result = attrs.getValue("testKey", Integer.class, (MarmaladeExecutionContext)ctxMock.proxy(), new Integer(2));
    assertFalse("Wrong attribute value.", testVal.equals(result));
    assertEquals("Should equal default value", 2, ((Integer)result).intValue());
    
    elMock.verify();
    attrMock.verify();
    ctxMock.verify();
  }

}
