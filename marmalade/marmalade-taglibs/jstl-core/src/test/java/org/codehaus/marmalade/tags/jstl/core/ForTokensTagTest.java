/* Created on Apr 15, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import java.util.Map;
import java.util.TreeMap;

import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.generics.DefaultContext;
import org.codehaus.marmalade.tags.jstl.core.ForTokensTag;
import org.codehaus.marmalade.testing.AbstractTagTestCase;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.Mock;


/**
 * @author jdcasey
 */
public class ForTokensTagTest extends AbstractTagTestCase{

  public void testDoExecute_SingleDelim_SingleValue()
  throws TagException, TagalogParseException, MarmaladeExecutionException
  {
    Map attrs = new TreeMap();
    attrs.put("items", "#items");
    attrs.put("delims", ",");
    attrs.put("var", "item");
    
    Mock attrsMock = attributesFromMap(attrs);
    ForTokensTag tag = new ForTokensTag();
    tag.begin("forEach", (Attributes)attrsMock.proxy());
    
    CounterTestTag counter = new CounterTestTag();
    Mock counterAttrs = attributesEmpty();
    counter.begin("counter", (Attributes)counterAttrs.proxy());
    
    counter.setParent(tag);
    tag.child(counter);
    
    DefaultContext ctx = new DefaultContext();
    ctx.setVariable("items", "one");
    
    tag.execute(ctx);
    assertEquals("Counter should read one", 1, counter.counter());
    
    attrsMock.verify();
    counterAttrs.verify();
  }

  public void testDoExecute_SingleDelim_MultiValue()
  throws TagException, TagalogParseException, MarmaladeExecutionException
  {
    Map attrs = new TreeMap();
    attrs.put("items", "#items");
    attrs.put("delims", ",");
    attrs.put("var", "item");
    
    Mock attrsMock = attributesFromMap(attrs);
    ForTokensTag tag = new ForTokensTag();
    tag.begin("forEach", (Attributes)attrsMock.proxy());
    
    CounterTestTag counter = new CounterTestTag();
    Mock counterAttrs = attributesEmpty();
    counter.begin("counter", (Attributes)counterAttrs.proxy());
    
    counter.setParent(tag);
    tag.child(counter);
    
    DefaultContext ctx = new DefaultContext();
    ctx.setVariable("items", "one,two");
    
    tag.execute(ctx);
    assertEquals("Counter should read two", 2, counter.counter());
    
    attrsMock.verify();
    counterAttrs.verify();
  }

  public void testDoExecute_MultiDelim_SingleValue()
  throws TagException, TagalogParseException, MarmaladeExecutionException
  {
    Map attrs = new TreeMap();
    attrs.put("items", "#items");
    attrs.put("delims", ",:");
    attrs.put("var", "item");
    
    Mock attrsMock = attributesFromMap(attrs);
    ForTokensTag tag = new ForTokensTag();
    tag.begin("forEach", (Attributes)attrsMock.proxy());
    
    CounterTestTag counter = new CounterTestTag();
    Mock counterAttrs = attributesEmpty();
    counter.begin("counter", (Attributes)counterAttrs.proxy());
    
    counter.setParent(tag);
    tag.child(counter);
    
    DefaultContext ctx = new DefaultContext();
    ctx.setVariable("items", "one");
    
    tag.execute(ctx);
    assertEquals("Counter should read one", 1, counter.counter());
    
    attrsMock.verify();
    counterAttrs.verify();
  }

  public void testDoExecute_MultiDelim_MultiValue()
  throws TagException, TagalogParseException, MarmaladeExecutionException
  {
    Map attrs = new TreeMap();
    attrs.put("items", "#items");
    attrs.put("delims", ",.");
    attrs.put("var", "item");
    
    Mock attrsMock = attributesFromMap(attrs);
    ForTokensTag tag = new ForTokensTag();
    tag.begin("forEach", (Attributes)attrsMock.proxy());
    
    CounterTestTag counter = new CounterTestTag();
    Mock counterAttrs = attributesEmpty();
    counter.begin("counter", (Attributes)counterAttrs.proxy());
    
    counter.setParent(tag);
    tag.child(counter);
    
    DefaultContext ctx = new DefaultContext();
    ctx.setVariable("items", "one.a,two");
    
    tag.execute(ctx);
    assertEquals("Counter should read three", 3, counter.counter());
    
    attrsMock.verify();
    counterAttrs.verify();
  }

}
