/* Created on Apr 13, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import java.util.Map;
import java.util.TreeMap;

import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.defaults.DefaultContext;
import org.codehaus.marmalade.tags.jstl.core.CatchTag;
import org.codehaus.marmalade.testing.AbstractTagTestCase;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.Mock;


/**
 * @author jdcasey
 */
public class CatchTagTest extends AbstractTagTestCase{

  public void testDoExecuteWithException_NoClass() throws TagException, TagalogParseException, MarmaladeExecutionException{
    Mock attrMock = attributesWithSingleAttribute("var", "exception");
    
    CatchTag tag = new CatchTag();
    tag.begin("catch", (Attributes)attrMock.proxy());
    
    Mock attrMock2 = attributesEmpty();
    
    ErrorGeneratingTestTag errTag = new ErrorGeneratingTestTag();
    errTag.begin("err", (Attributes)attrMock2.proxy());
    tag.child(errTag);
    
    DefaultContext ctx = new DefaultContext();
    try {
      tag.execute(ctx);
    }
    catch(Throwable e) {
      e.printStackTrace();
      fail("Exception should have been caught in tag.");
    }
    
    Throwable t = (Throwable)ctx.getVariable("exception", null);
    assertNotNull("Should have caught an exception", t);
    assertTrue(
      "Exception should be UnsupportedOperationException", 
      t instanceof UnsupportedOperationException
    );
    
    attrMock.verify();
    attrMock2.verify();
  }

  public void testDoExecuteWithException_WithClass_Match() throws TagException, TagalogParseException, MarmaladeExecutionException{
    Map attrs = new TreeMap();
    attrs.put("var", "exception");
    attrs.put("class", "java.lang.UnsupportedOperationException");
    
    Mock attrMock = attributesFromMap(attrs);
    
    CatchTag tag = new CatchTag();
    tag.begin("catch", (Attributes)attrMock.proxy());
    
    Mock attrMock2 = attributesEmpty();
    
    ErrorGeneratingTestTag errTag = new ErrorGeneratingTestTag();
    errTag.begin("err", (Attributes)attrMock2.proxy());
    tag.child(errTag);
    
    DefaultContext ctx = new DefaultContext();
    try {
      tag.execute(ctx);
    }
    catch(Throwable e) {
      e.printStackTrace();
      fail("Exception should have been caught in tag.");
    }
    
    Throwable t = (Throwable)ctx.getVariable("exception", null);
    assertNotNull("Should have caught an exception", t);
    assertTrue(
      "Exception should be UnsupportedOperationException", 
      t instanceof UnsupportedOperationException
    );
    
    attrMock.verify();
    attrMock2.verify();
  }

  public void testDoExecuteWithException_WithClass_NoMatch() throws TagException, TagalogParseException, MarmaladeExecutionException{
    Map attrs = new TreeMap();
    attrs.put("var", "exception");
    attrs.put("class", "java.lang.IllegalArgumentException");
    
    Mock attrMock = attributesFromMap(attrs);
    
    CatchTag tag = new CatchTag();
    tag.begin("catch", (Attributes)attrMock.proxy());
    
    Mock attrMock2 = attributesEmpty();
    
    ErrorGeneratingTestTag errTag = new ErrorGeneratingTestTag();
    errTag.begin("err", (Attributes)attrMock2.proxy());
    tag.child(errTag);
    
    DefaultContext ctx = new DefaultContext();
    try {
      tag.execute(ctx);
      fail("Exception should NOT have been caught in tag.");
    }
    catch(Throwable e) {
    }
    
    Throwable t = (Throwable)ctx.getVariable("exception", null);
    assertNull("Should have caught an exception", t);
    
    attrMock.verify();
    attrMock2.verify();
  }

}