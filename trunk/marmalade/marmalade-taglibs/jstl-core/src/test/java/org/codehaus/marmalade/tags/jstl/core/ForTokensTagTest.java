/* Created on Apr 15, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import java.util.Map;
import java.util.TreeMap;

import junit.framework.TestCase;

import org.codehaus.marmalade.el.PassThroughExpressionEvaluator;
import org.codehaus.marmalade.modelbuilder.DefaultRawAttributes;
import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.jstl.core.ForTokensTag;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.Mock;


/**
 * @author jdcasey
 */
public class ForTokensTagTest extends TestCase{
  
  private void _testTokenIteration(String delim, String value, int expectedResult) throws MarmaladeExecutionException {
    DefaultRawAttributes attrs = new DefaultRawAttributes();
    attrs.addAttribute("", "items", value);
    attrs.addAttribute("", "delims", delim);
    attrs.addAttribute("", "var", "item");
    
    MarmaladeTagInfo ti = new MarmaladeTagInfo();
    ti.setAttributes(attrs);
    ti.setExpressionEvaluator(new PassThroughExpressionEvaluator());
    
    ForTokensTag tag = new ForTokensTag(ti);
    
    MarmaladeTagInfo counterTI = new MarmaladeTagInfo();
    counterTI.setAttributes(new DefaultRawAttributes());
    
    CounterTestTag counter = new CounterTestTag(counterTI);
    
    counter.setParent(tag);
    tag.addChild(counter);
    
    tag.execute(new DefaultContext());
    
    assertEquals(expectedResult, counter.counter());
  }

  public void testDoExecute_SingleDelim_SingleValue()
  throws TagException, TagalogParseException, MarmaladeExecutionException
  {
    _testTokenIteration(",", "one", 1);
  }

  public void testDoExecute_SingleDelim_MultiValue()
  throws TagException, TagalogParseException, MarmaladeExecutionException
  {
    _testTokenIteration(",", "one,two", 2);
  }

  public void testDoExecute_MultiDelim_SingleValue()
  throws TagException, TagalogParseException, MarmaladeExecutionException
  {
    _testTokenIteration(",;", "one", 1);
  }

  public void testDoExecute_MultiDelim_MultiValue()
  throws TagException, TagalogParseException, MarmaladeExecutionException
  {
    _testTokenIteration(",;", "one,two;three", 3);
  }

}
