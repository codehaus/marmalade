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
import org.codehaus.marmalade.tags.httpunit.TestingWebRequest;
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
public class ParameterTagTest extends AbstractTagCGLibTestCase{
  
  public void testEmbedded_Success_ValueAttribute() throws TagException, TagalogParseException, MarmaladeExecutionException{
    Mock wrMock = new Mock(TestingWebRequest.class);
    wrMock.expects(once())
          .method("setParameter")
          .with(eq("testkey"), eq("testval"))
          .isVoid();
    
    Mock wrtMock = new Mock(AbstractWebRequestTag.class);
    wrtMock.expects(once())
           .method("getRequest")
           .withNoArguments()
           .will(returnValue(wrMock.proxy()));
    
    Map attributes = new TreeMap();
    attributes.put(ParameterTag.NAME_ATTRIBUTE, "testkey");
    attributes.put(ParameterTag.VALUE_ATTRIBUTE, "testval");
    
    Mock attrMock = attributesFromMap(attributes);
    
    ParameterTag tag = new ParameterTag();
    tag.setParent((Tag)wrtMock.proxy());
    tag.begin("parameter", (Attributes)attrMock.proxy());
    tag.end("parameter");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
  }

  public void testEmbedded_Success_ValueBody() throws TagException, TagalogParseException, MarmaladeExecutionException{
    Mock wrMock = new Mock(TestingWebRequest.class);
    wrMock.expects(once())
          .method("setParameter")
          .with(eq("testkey"), eq("testval"))
          .isVoid();
    
    Mock wrtMock = new Mock(AbstractWebRequestTag.class);
    wrtMock.expects(once())
           .method("getRequest")
           .withNoArguments()
           .will(returnValue(wrMock.proxy()));
    
    Map attributes = new TreeMap();
    attributes.put(ParameterTag.NAME_ATTRIBUTE, "testkey");
    
    Mock attrMock = attributesFromMap(attributes);
    
    ParameterTag tag = new ParameterTag();
    tag.setParent((Tag)wrtMock.proxy());
    tag.begin("parameter", (Attributes)attrMock.proxy());
    
    String body = "testval";
    tag.text(body.toCharArray(), 0, body.length());
    
    tag.end("parameter");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
  }

  public void testEmbedded_Failure_NameMissing() throws TagException, TagalogParseException, MarmaladeExecutionException{
    Mock wrMock = new Mock(TestingWebRequest.class);
    wrMock.expects(once())
          .method("setParameter")
          .with(eq("testkey"), eq("testval"))
          .isVoid();
    
    Mock wrtMock = new Mock(AbstractWebRequestTag.class);
    wrtMock.expects(once())
           .method("getRequest")
           .withNoArguments()
           .will(returnValue(wrMock.proxy()));
    
    Map attributes = new TreeMap();
    
    Mock attrMock = attributesFromMap(attributes);
    
    ParameterTag tag = new ParameterTag();
    tag.setParent((Tag)wrtMock.proxy());
    tag.begin("parameter", (Attributes)attrMock.proxy());
    
    String body = "testval";
    tag.text(body.toCharArray(), 0, body.length());
    
    tag.end("parameter");
    
    DefaultContext ctx = new DefaultContext();
    try{
      tag.execute(ctx);
      fail("Should have thrown MissingAttributeException for missing name attribute.");
    }
    catch(MissingAttributeException e){
    }
  }

  public void testSerial_Success_ValueAttribute() throws TagException, TagalogParseException, MarmaladeExecutionException{
    Mock wrMock = new Mock(TestingWebRequest.class);
    wrMock.expects(once())
          .method("setParameter")
          .with(eq("testkey"), eq("testval"))
          .isVoid();
    
    Map attributes = new TreeMap();
    attributes.put(ParameterTag.NAME_ATTRIBUTE, "testkey");
    attributes.put(ParameterTag.VALUE_ATTRIBUTE, "testval");
    attributes.put(ParameterTag.REQUEST_ATTRIBUTE, "#request");
    
    Mock attrMock = attributesFromMap(attributes);
    
    ParameterTag tag = new ParameterTag();
    tag.begin("parameter", (Attributes)attrMock.proxy());
    tag.end("parameter");
    
    DefaultContext ctx = new DefaultContext();
    ctx.setVariable("request", wrMock.proxy());
    tag.execute(ctx);
  }

  public void testSerial_Failure_NameMissing() throws TagException, TagalogParseException, MarmaladeExecutionException{
    Mock wrMock = new Mock(TestingWebRequest.class);
    wrMock.expects(once())
          .method("setParameter")
          .with(eq("testkey"), eq("testval"))
          .isVoid();
    
    Map attributes = new TreeMap();
    attributes.put(ParameterTag.VALUE_ATTRIBUTE, "testval");
    attributes.put(ParameterTag.REQUEST_ATTRIBUTE, "#request");
    
    Mock attrMock = attributesFromMap(attributes);
    
    ParameterTag tag = new ParameterTag();
    tag.begin("parameter", (Attributes)attrMock.proxy());
    tag.end("parameter");
    
    DefaultContext ctx = new DefaultContext();
    ctx.setVariable("request", wrMock.proxy());
    try{
      tag.execute(ctx);
      fail("Should have thrown MissingAttributeException for missing name attribute.");
    }
    catch(MissingAttributeException e){
    }
  }

  public void testSerial_Failure_RequestParamMissing() throws TagException, TagalogParseException, MarmaladeExecutionException{
    Map attributes = new TreeMap();
    attributes.put(ParameterTag.NAME_ATTRIBUTE, "testkey");
    attributes.put(ParameterTag.VALUE_ATTRIBUTE, "testval");
    
    Mock attrMock = attributesFromMap(attributes);
    
    ParameterTag tag = new ParameterTag();
    tag.begin("parameter", (Attributes)attrMock.proxy());
    tag.end("parameter");
    
    DefaultContext ctx = new DefaultContext();
    try{
      tag.execute(ctx);
      fail("Should have thrown IllegalAncestorException for missing request attribute.");
    }
    catch(IllegalAncestorException e){
      
    }
  }

  public void testSerial_Failure_RequestObjectMissing() throws TagException, TagalogParseException, MarmaladeExecutionException{
    Map attributes = new TreeMap();
    attributes.put(ParameterTag.NAME_ATTRIBUTE, "testkey");
    attributes.put(ParameterTag.VALUE_ATTRIBUTE, "testval");
    attributes.put(ParameterTag.REQUEST_ATTRIBUTE, "#request");
    
    Mock attrMock = attributesFromMap(attributes);
    
    ParameterTag tag = new ParameterTag();
    tag.begin("parameter", (Attributes)attrMock.proxy());
    tag.end("parameter");
    
    DefaultContext ctx = new DefaultContext();
    try{
      tag.execute(ctx);
      fail("Should have thrown IllegalAncestorException for missing request object/parent.");
    }
    catch(IllegalAncestorException e){
    }
  }

  public void testSerial_Success_ValueBody() throws TagException, TagalogParseException, MarmaladeExecutionException{
    Mock wrMock = new Mock(TestingWebRequest.class);
    wrMock.expects(once())
          .method("setParameter")
          .with(eq("testkey"), eq("testval"))
          .isVoid();
    
    Map attributes = new TreeMap();
    attributes.put(ParameterTag.NAME_ATTRIBUTE, "testkey");
    attributes.put(ParameterTag.REQUEST_ATTRIBUTE, "#request");
    
    Mock attrMock = attributesFromMap(attributes);
    
    ParameterTag tag = new ParameterTag();
    tag.begin("parameter", (Attributes)attrMock.proxy());
    
    String body = "testval";
    tag.text(body.toCharArray(), 0, body.length());
    
    tag.end("parameter");
    
    DefaultContext ctx = new DefaultContext();
    ctx.setVariable("request", wrMock.proxy());
    tag.execute(ctx);
  }

}
