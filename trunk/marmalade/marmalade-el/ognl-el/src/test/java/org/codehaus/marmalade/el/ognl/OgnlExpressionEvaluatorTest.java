/* Created on Apr 13, 2004 */
package org.codehaus.marmalade.el.ognl;

import java.util.Map;
import java.util.TreeMap;

import junit.framework.TestCase;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluatorTest;


/**
 * @author jdcasey
 */
public class OgnlExpressionEvaluatorTest extends TestCase implements ExpressionEvaluatorTest{

  public void testEvaluate() throws ExpressionEvaluationException{
    String id = "testId";
    TestSubject subject = new TestSubject(id);
    
    Map context = new TreeMap();
    context.put("subject", subject);
    
    OgnlExpressionEvaluator el = new OgnlExpressionEvaluator();
    Object result = el.evaluate("${#subject.id}", context, String.class);
    assertEquals("Id should come through unchanged.", id, result);
  }
  
  public void testAssign() throws ExpressionEvaluationException{
    String id = "testId";
    TestSubject subject = new TestSubject("otherId");
    
    Map context = new TreeMap();
    context.put("subject", subject);
    
    OgnlExpressionEvaluator el = new OgnlExpressionEvaluator();
    Object result = el.assign(subject, "id", id);
    
    assertEquals("Id should have been changed to \'testId\'", id, subject.getId());
  }

  public void testEmbeddedExpression() throws ExpressionEvaluationException{
    String id = "testId";
    TestSubject subject = new TestSubject(id);
    
    Map context = new TreeMap();
    context.put("subject", subject);
    
    OgnlExpressionEvaluator el = new OgnlExpressionEvaluator();
    Object result = el.evaluate("This is a test for id:${#subject.id}", context, String.class);
    assertEquals("Id value should be embedded in larger literal expression.", "This is a test for id:" + id, result);
  }

}
