/* Created on Apr 29, 2004 */
package org.codehaus.marmalade.tags.httpunit.structure;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.codehaus.marmalade.IllegalAncestorException;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.MissingAttributeException;
import org.codehaus.marmalade.defaults.DefaultContext;
import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.testing.AbstractTagCGLibTestCase;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.Tag;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.cglib.Mock;

import com.meterware.httpunit.WebRequest;


/**
 * @author jdcasey
 */
public class HeaderTagTest extends AbstractTagCGLibTestCase{
  
  public void testSuccess_ValueAttribute() throws TagException, TagalogParseException, MarmaladeExecutionException{
    Mock wrMock = new Mock(TestingHeaderParent.class);
    wrMock.expects(once())
          .method("getExpressionEvaluator")
          .withNoArguments()
          .will(returnValue(null));
    
    wrMock.expects(once())
          .method("setHeader")
          .with(eq("testkey"), eq("testval"))
          .isVoid();
    
    Map attributes = new TreeMap();
    attributes.put(HeaderTag.NAME_ATTRIBUTE, "testkey");
    attributes.put(HeaderTag.VALUE_ATTRIBUTE, "testval");
    
    Mock attrMock = attributesFromMap(attributes);
    
    HeaderTag tag = new HeaderTag();
    tag.setParent((Tag)wrMock.proxy());
    tag.begin("header", (Attributes)attrMock.proxy());
    tag.end("header");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
  }

  public void testSuccess_ValueBody() throws TagException, TagalogParseException, MarmaladeExecutionException{
    Mock wrMock = new Mock(TestingHeaderParent.class);
    wrMock.expects(once())
          .method("getExpressionEvaluator")
          .withNoArguments()
          .will(returnValue(null));

    wrMock.expects(once())
          .method("setHeader")
          .with(eq("testkey"), eq("testval"))
          .isVoid();
    
    Map attributes = new TreeMap();
    attributes.put(HeaderTag.NAME_ATTRIBUTE, "testkey");
    
    Mock attrMock = attributesFromMap(attributes);
    
    HeaderTag tag = new HeaderTag();
    tag.setParent((Tag)wrMock.proxy());
    tag.begin("header", (Attributes)attrMock.proxy());
    
    String body = "testval";
    tag.text(body.toCharArray(), 0, body.length());
    
    tag.end("header");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
  }

  public void testFailure_NameMissing() throws TagException, TagalogParseException, MarmaladeExecutionException{
    Mock wrMock = new Mock(TestingHeaderParent.class);
    wrMock.expects(once())
          .method("getExpressionEvaluator")
          .withNoArguments()
          .will(returnValue(null));

    wrMock.expects(once())
          .method("setHeader")
          .with(eq("testkey"), eq("testval"))
          .isVoid();
    
    Map attributes = new TreeMap();
    
    Mock attrMock = attributesFromMap(attributes);
    
    HeaderTag tag = new HeaderTag();
    tag.setParent((Tag)wrMock.proxy());
    tag.begin("header", (Attributes)attrMock.proxy());
    
    String body = "testval";
    tag.text(body.toCharArray(), 0, body.length());
    
    tag.end("header");
    
    DefaultContext ctx = new DefaultContext();
    try{
      tag.execute(ctx);
      fail("Should have thrown MissingAttributeException for missing name attribute.");
    }
    catch(MissingAttributeException e){
    }
  }

}
