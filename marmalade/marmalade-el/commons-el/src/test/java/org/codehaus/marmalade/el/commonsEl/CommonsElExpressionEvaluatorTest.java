/* Created on Apr 13, 2004 */
package org.codehaus.marmalade.el.commonsEl;

import java.util.Map;
import java.util.TreeMap;

import junit.framework.TestCase;

import org.codehaus.marmalade.el.ExpressionEvaluationException;


/**
 * @author jdcasey
 */
public class CommonsElExpressionEvaluatorTest extends TestCase {

  public void testEvaluate() throws ExpressionEvaluationException{
    String id = "testId";
    TestSubject subject = new TestSubject(id);
    
    Map context = new TreeMap();
    context.put("subject", subject);
    
    CommonsElExpressionEvaluator el = new CommonsElExpressionEvaluator();
    Object result = el.evaluate("${subject.id}", context, String.class);
    assertEquals("Id should come through unchanged.", id, result);
  }
  
  public void testAssign() throws ExpressionEvaluationException{
    String id = "testId";
    TestSubject subject = new TestSubject("otherId");
    
    Map context = new TreeMap();
    context.put("subject", subject);
    
    CommonsElExpressionEvaluator el = new CommonsElExpressionEvaluator();
    Object result = el.assign(subject, "id", id);
    result = el.assign(subject, "name.firstName", "John");
    result = el.assign(subject, "name.lastName", "Doe");
    
    assertEquals("Id should have been changed to \'testId\'", id, subject.getId());
    assertEquals("First name should be \'John\'", "John", subject.getName().getFirstName());
    assertEquals("Last name should be \'Doe\'", "Doe", subject.getName().lastName);
  }

  public void testEmbeddedExpression() throws ExpressionEvaluationException{
    String id = "testId";
    TestSubject subject = new TestSubject(id);
    
    Map context = new TreeMap();
    context.put("subject", subject);
    
    CommonsElExpressionEvaluator el = new CommonsElExpressionEvaluator();
    Object result = el.evaluate("This is a test for id:${subject.id}", context, String.class);
    assertEquals("Id value should be embedded in larger literal expression.", "This is a test for id:" + id, result);
  }

}
