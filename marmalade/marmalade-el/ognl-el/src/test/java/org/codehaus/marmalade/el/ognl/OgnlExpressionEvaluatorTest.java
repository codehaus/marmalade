/* Created on Apr 13, 2004 */
package org.codehaus.marmalade.el.ognl;

import java.util.Map;
import java.util.TreeMap;

import org.codehaus.marmalade.el.ExpressionEvaluationException;

import junit.framework.TestCase;


/**
 * @author jdcasey
 */
public class OgnlExpressionEvaluatorTest extends TestCase{

  public void testEvaluate() throws ExpressionEvaluationException{
    String id = "testId";
    TestSubject subject = new TestSubject(id);
    
    Map context = new TreeMap();
    context.put("subject", subject);
    
    OgnlExpressionEvaluator el = new OgnlExpressionEvaluator();
    Object result = el.evaluate("#subject.id", context, String.class);
    assertEquals("Id should come through unchanged.", id, result);
  }
  
  public void testIsExpression() {
    OgnlExpressionEvaluator el = new OgnlExpressionEvaluator();
    assertFalse("\'somestring\' should not be a valid expression.", el.isExpression("somestring"));
    assertTrue("\'true\' should be a valid expression.", el.isExpression("true"));
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

}
