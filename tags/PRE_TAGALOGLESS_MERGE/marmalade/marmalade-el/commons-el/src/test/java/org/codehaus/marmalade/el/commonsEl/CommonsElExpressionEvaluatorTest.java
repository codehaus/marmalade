/* 
 * 
 * Copyright (c) 2004 John Dennis Casey
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to 
 * deal in the Software without restriction, including without limitation the 
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or 
 * sell copies of the Software, and to permit persons to whom the Software is 
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING 
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER 
 * DEALINGS IN THE SOFTWARE.
 * 
 */


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