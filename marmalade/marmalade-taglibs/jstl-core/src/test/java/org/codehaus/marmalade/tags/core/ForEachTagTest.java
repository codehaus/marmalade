/* Created on Apr 14, 2004 */
package org.codehaus.marmalade.tags.core;

import java.util.Map;
import java.util.TreeMap;

import org.codehaus.marmalade.DefaultContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.testing.AbstractTagTestCase;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.Mock;


/**
 * @author jdcasey
 */
public class ForEachTagTest extends AbstractTagTestCase{

  public void testDoExecute_Simple() throws TagException, TagalogParseException, MarmaladeExecutionException{
    Map attrs = new TreeMap();
    attrs.put("items", "#items");
    attrs.put("var", "item");
    
    Mock attrsMock = attributesFromMap(attrs);
    ForEachTag tag = new ForEachTag();
    tag.begin("forEach", (Attributes)attrsMock.proxy());
    
    CounterTestTag counter = new CounterTestTag();
    Mock counterAttrs = attributesEmpty();
    counter.begin("counter", (Attributes)counterAttrs.proxy());
    
    counter.setParent(tag);
    tag.child(counter);
    
    DefaultContext ctx = new DefaultContext();
    ctx.setVariable("items", new String[] {"one", "two", "three"});
    
    tag.execute(ctx);
    assertEquals("Counter should read three", 3, counter.counter());
    
    attrsMock.verify();
    counterAttrs.verify();
  }

}
