/* Created on May 8, 2004 */
package org.codehaus.marmalade.tags.httpunit.structure;

import java.util.Map;
import java.util.TreeMap;

import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.defaults.DefaultContext;
import org.codehaus.marmalade.testing.AbstractTagCGLibTestCase;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.Tag;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.cglib.Mock;

import com.meterware.httpunit.ClientProperties;
import com.meterware.httpunit.DNSListener;
import com.meterware.httpunit.WebConversation;


/**
 * @author jdcasey
 */
public class ClientPropertiesTagTest extends AbstractTagCGLibTestCase{
  
  public void testShouldConstructEmbeddedInConversationWithNoAttributes() throws TagException, TagalogParseException, MarmaladeExecutionException{
    ClientProperties props = ClientProperties.getDefaultProperties();
    Mock wcMock = new Mock(WebConversation.class);
    wcMock.expects(once())
          .method("getClientProperties")
          .withNoArguments()
          .will(returnValue(props));
    
    Mock wctMock = new Mock(WebConversationTag.class);
    wctMock.expects(once())
           .method("getConversation")
           .withNoArguments()
           .will(returnValue(wcMock.proxy()));
    
    Map attributes = new TreeMap();
    
    Mock attrMock = attributesFromMap(attributes);
    ClientPropertiesTag tag = new ClientPropertiesTag();
    tag.setParent((Tag)wctMock.proxy());
    tag.begin("clientProperties", (Attributes)attrMock.proxy());
    tag.end("clientProperties");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
  }

  public void testShouldConstructEmbeddedInConversationWithAcceptCookiesAttribute() throws TagException, TagalogParseException, MarmaladeExecutionException{
    ClientProperties props = ClientProperties.getDefaultProperties();
    Mock wcMock = new Mock(WebConversation.class);
    wcMock.expects(once())
          .method("getClientProperties")
          .withNoArguments()
          .will(returnValue(props));
    
    Mock wctMock = new Mock(WebConversationTag.class);
    wctMock.expects(once())
           .method("getConversation")
           .withNoArguments()
           .will(returnValue(wcMock.proxy()));
    
    Map attributes = new TreeMap();
    attributes.put(ClientPropertiesTag.ACCEPT_COOKIES_ATTIBUTE, "true");
    
    Mock attrMock = attributesFromMap(attributes);
    ClientPropertiesTag tag = new ClientPropertiesTag();
    tag.setParent((Tag)wctMock.proxy());
    tag.begin("clientProperties", (Attributes)attrMock.proxy());
    tag.end("clientProperties");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    assertTrue(props.isAcceptCookies());
  }

  public void testShouldConstructEmbeddedInConversationWithAcceptGzipAttribute() throws TagException, TagalogParseException, MarmaladeExecutionException{
    ClientProperties props = ClientProperties.getDefaultProperties();
    Mock wcMock = new Mock(WebConversation.class);
    wcMock.expects(once())
          .method("getClientProperties")
          .withNoArguments()
          .will(returnValue(props));
    
    Mock wctMock = new Mock(WebConversationTag.class);
    wctMock.expects(once())
           .method("getConversation")
           .withNoArguments()
           .will(returnValue(wcMock.proxy()));
    
    Map attributes = new TreeMap();
    attributes.put(ClientPropertiesTag.ACCEPT_GZIP_ATTRIBUTE, "true");
    
    Mock attrMock = attributesFromMap(attributes);
    ClientPropertiesTag tag = new ClientPropertiesTag();
    tag.setParent((Tag)wctMock.proxy());
    tag.begin("clientProperties", (Attributes)attrMock.proxy());
    tag.end("clientProperties");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    assertTrue(props.isAcceptGzip());
  }

  public void testShouldConstructEmbeddedInConversationWithAppCodeNameAttribute() throws TagException, TagalogParseException, MarmaladeExecutionException{
    ClientProperties props = ClientProperties.getDefaultProperties();
    Mock wcMock = new Mock(WebConversation.class);
    wcMock.expects(once())
          .method("getClientProperties")
          .withNoArguments()
          .will(returnValue(props));
    
    Mock wctMock = new Mock(WebConversationTag.class);
    wctMock.expects(once())
           .method("getConversation")
           .withNoArguments()
           .will(returnValue(wcMock.proxy()));
    
    Map attributes = new TreeMap();
    attributes.put(ClientPropertiesTag.APPLICATION_CODE_NAME_ATTRIBUTE, "TestCodeName");
    
    Mock attrMock = attributesFromMap(attributes);
    ClientPropertiesTag tag = new ClientPropertiesTag();
    tag.setParent((Tag)wctMock.proxy());
    tag.begin("clientProperties", (Attributes)attrMock.proxy());
    tag.end("clientProperties");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    assertEquals("TestCodeName", props.getApplicationCodeName());
  }

  public void testShouldConstructEmbeddedInConversationWithAppNameAttribute() throws TagException, TagalogParseException, MarmaladeExecutionException{
    ClientProperties props = ClientProperties.getDefaultProperties();
    Mock wcMock = new Mock(WebConversation.class);
    wcMock.expects(once())
          .method("getClientProperties")
          .withNoArguments()
          .will(returnValue(props));
    
    Mock wctMock = new Mock(WebConversationTag.class);
    wctMock.expects(once())
           .method("getConversation")
           .withNoArguments()
           .will(returnValue(wcMock.proxy()));
    
    Map attributes = new TreeMap();
    attributes.put(ClientPropertiesTag.APPLICATION_NAME_ATTRIBUTE, "TestAppName");
    
    Mock attrMock = attributesFromMap(attributes);
    ClientPropertiesTag tag = new ClientPropertiesTag();
    tag.setParent((Tag)wctMock.proxy());
    tag.begin("clientProperties", (Attributes)attrMock.proxy());
    tag.end("clientProperties");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    assertEquals("TestAppName", props.getApplicationName());
  }

  public void testShouldConstructEmbeddedInConversationWithAppVersionAttribute() throws TagException, TagalogParseException, MarmaladeExecutionException{
    ClientProperties props = ClientProperties.getDefaultProperties();
    Mock wcMock = new Mock(WebConversation.class);
    wcMock.expects(once())
          .method("getClientProperties")
          .withNoArguments()
          .will(returnValue(props));
    
    Mock wctMock = new Mock(WebConversationTag.class);
    wctMock.expects(once())
           .method("getConversation")
           .withNoArguments()
           .will(returnValue(wcMock.proxy()));
    
    Map attributes = new TreeMap();
    attributes.put(ClientPropertiesTag.APPLICATION_VERSION_ATTRIBUTE, "1.0");
    
    Mock attrMock = attributesFromMap(attributes);
    ClientPropertiesTag tag = new ClientPropertiesTag();
    tag.setParent((Tag)wctMock.proxy());
    tag.begin("clientProperties", (Attributes)attrMock.proxy());
    tag.end("clientProperties");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    assertEquals("1.0", props.getApplicationVersion());
  }

  public void testShouldConstructEmbeddedInConversationWithAutoRedirectAttribute() throws TagException, TagalogParseException, MarmaladeExecutionException{
    ClientProperties props = ClientProperties.getDefaultProperties();
    Mock wcMock = new Mock(WebConversation.class);
    wcMock.expects(once())
          .method("getClientProperties")
          .withNoArguments()
          .will(returnValue(props));
    
    Mock wctMock = new Mock(WebConversationTag.class);
    wctMock.expects(once())
           .method("getConversation")
           .withNoArguments()
           .will(returnValue(wcMock.proxy()));
    
    Map attributes = new TreeMap();
    attributes.put(ClientPropertiesTag.AUTO_REDIRECT_ATTRIBUTE, "true");
    
    Mock attrMock = attributesFromMap(attributes);
    ClientPropertiesTag tag = new ClientPropertiesTag();
    tag.setParent((Tag)wctMock.proxy());
    tag.begin("clientProperties", (Attributes)attrMock.proxy());
    tag.end("clientProperties");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    assertTrue(props.isAutoRedirect());
  }

  public void testShouldConstructEmbeddedInConversationWithScreenWidthAttribute() throws TagException, TagalogParseException, MarmaladeExecutionException{
    ClientProperties props = ClientProperties.getDefaultProperties();
    Mock wcMock = new Mock(WebConversation.class);
    wcMock.expects(once())
          .method("getClientProperties")
          .withNoArguments()
          .will(returnValue(props));
    
    Mock wctMock = new Mock(WebConversationTag.class);
    wctMock.expects(once())
           .method("getConversation")
           .withNoArguments()
           .will(returnValue(wcMock.proxy()));
    
    Map attributes = new TreeMap();
    attributes.put(ClientPropertiesTag.SCREEN_WIDTH_ATTRIBUTE, "400");
    
    Mock attrMock = attributesFromMap(attributes);
    ClientPropertiesTag tag = new ClientPropertiesTag();
    tag.setParent((Tag)wctMock.proxy());
    tag.begin("clientProperties", (Attributes)attrMock.proxy());
    tag.end("clientProperties");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    assertEquals(400, props.getAvailableScreenWidth());
  }

  public void testShouldConstructEmbeddedInConversationWithScreenHeightAttribute() throws TagException, TagalogParseException, MarmaladeExecutionException{
    ClientProperties props = ClientProperties.getDefaultProperties();
    Mock wcMock = new Mock(WebConversation.class);
    wcMock.expects(once())
          .method("getClientProperties")
          .withNoArguments()
          .will(returnValue(props));
    
    Mock wctMock = new Mock(WebConversationTag.class);
    wctMock.expects(once())
           .method("getConversation")
           .withNoArguments()
           .will(returnValue(wcMock.proxy()));
    
    Map attributes = new TreeMap();
    attributes.put(ClientPropertiesTag.SCREEN_HEIGHT_ATTRIBUTE, "400");
    
    Mock attrMock = attributesFromMap(attributes);
    ClientPropertiesTag tag = new ClientPropertiesTag();
    tag.setParent((Tag)wctMock.proxy());
    tag.begin("clientProperties", (Attributes)attrMock.proxy());
    tag.end("clientProperties");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    assertEquals(400, props.getAvailHeight());
  }

  public void testShouldConstructEmbeddedInConversationWithIFrameSupportedAttribute() throws TagException, TagalogParseException, MarmaladeExecutionException{
    ClientProperties props = ClientProperties.getDefaultProperties();
    Mock wcMock = new Mock(WebConversation.class);
    wcMock.expects(once())
          .method("getClientProperties")
          .withNoArguments()
          .will(returnValue(props));
    
    Mock wctMock = new Mock(WebConversationTag.class);
    wctMock.expects(once())
           .method("getConversation")
           .withNoArguments()
           .will(returnValue(wcMock.proxy()));
    
    Map attributes = new TreeMap();
    attributes.put(ClientPropertiesTag.IFRAME_SUPPORTED_ATTRIBUTE, "true");
    
    Mock attrMock = attributesFromMap(attributes);
    ClientPropertiesTag tag = new ClientPropertiesTag();
    tag.setParent((Tag)wctMock.proxy());
    tag.begin("clientProperties", (Attributes)attrMock.proxy());
    tag.end("clientProperties");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    assertTrue(props.isIframeSupported());
  }

  public void testShouldConstructEmbeddedInConversationWithPlatformAttribute() throws TagException, TagalogParseException, MarmaladeExecutionException{
    ClientProperties props = ClientProperties.getDefaultProperties();
    Mock wcMock = new Mock(WebConversation.class);
    wcMock.expects(once())
          .method("getClientProperties")
          .withNoArguments()
          .will(returnValue(props));
    
    Mock wctMock = new Mock(WebConversationTag.class);
    wctMock.expects(once())
           .method("getConversation")
           .withNoArguments()
           .will(returnValue(wcMock.proxy()));
    
    Map attributes = new TreeMap();
    attributes.put(ClientPropertiesTag.PLATFORM_ATTRIBUTE, "TestPlatform");
    
    Mock attrMock = attributesFromMap(attributes);
    ClientPropertiesTag tag = new ClientPropertiesTag();
    tag.setParent((Tag)wctMock.proxy());
    tag.begin("clientProperties", (Attributes)attrMock.proxy());
    tag.end("clientProperties");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    assertEquals("TestPlatform", props.getPlatform());
  }

  public void testShouldConstructEmbeddedInConversationWithUserAgentAttribute() throws TagException, TagalogParseException, MarmaladeExecutionException{
    ClientProperties props = ClientProperties.getDefaultProperties();
    Mock wcMock = new Mock(WebConversation.class);
    wcMock.expects(once())
          .method("getClientProperties")
          .withNoArguments()
          .will(returnValue(props));
    
    Mock wctMock = new Mock(WebConversationTag.class);
    wctMock.expects(once())
           .method("getConversation")
           .withNoArguments()
           .will(returnValue(wcMock.proxy()));
    
    Map attributes = new TreeMap();
    attributes.put(ClientPropertiesTag.USER_AGENT_ATTRIBUTE, "TestUA");
    
    Mock attrMock = attributesFromMap(attributes);
    ClientPropertiesTag tag = new ClientPropertiesTag();
    tag.setParent((Tag)wctMock.proxy());
    tag.begin("clientProperties", (Attributes)attrMock.proxy());
    tag.end("clientProperties");
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    assertEquals("TestUA", props.getUserAgent());
  }

  public void testShouldConstructEmbeddedInConversationWithDnsListenerAttribute() throws TagException, TagalogParseException, MarmaladeExecutionException{
    ClientProperties props = ClientProperties.getDefaultProperties();
    Mock wcMock = new Mock(WebConversation.class);
    wcMock.expects(once())
          .method("getClientProperties")
          .withNoArguments()
          .will(returnValue(props));
    
    Mock wctMock = new Mock(WebConversationTag.class);
    wctMock.expects(once())
           .method("getConversation")
           .withNoArguments()
           .will(returnValue(wcMock.proxy()));
    
    Map attributes = new TreeMap();
    attributes.put(ClientPropertiesTag.DNS_LISTENER_ATTRIBUTE, "#dns_l");
    
    Mock attrMock = attributesFromMap(attributes);
    ClientPropertiesTag tag = new ClientPropertiesTag();
    tag.setParent((Tag)wctMock.proxy());
    tag.begin("clientProperties", (Attributes)attrMock.proxy());
    tag.end("clientProperties");
    
    Mock dlMock = new Mock(DNSListener.class);
    
    DefaultContext ctx = new DefaultContext();
    ctx.setVariable("dns_l", dlMock.proxy());
    
    tag.execute(ctx);
  }

}
