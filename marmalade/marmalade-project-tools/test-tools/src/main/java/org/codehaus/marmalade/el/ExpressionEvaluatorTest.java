/* Created on May 9, 2004 */
package org.codehaus.marmalade.el;

import java.util.TreeMap;

import junit.framework.TestCase;


/**
 * @author jdcasey
 */
public interface ExpressionEvaluatorTest{
  
  void testEvaluate() throws ExpressionEvaluationException;
  void testAssign() throws ExpressionEvaluationException;
  void testEmbeddedExpression() throws ExpressionEvaluationException;
}
