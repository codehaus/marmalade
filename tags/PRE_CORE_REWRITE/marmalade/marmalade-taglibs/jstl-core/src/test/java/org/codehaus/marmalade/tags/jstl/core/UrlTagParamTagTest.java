/* Created on Apr 14, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.defaults.DefaultContext;
import org.codehaus.marmalade.tags.jstl.core.ParamTag;
import org.codehaus.marmalade.tags.jstl.core.UrlTag;
import org.codehaus.marmalade.testing.AbstractTagTestCase;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.Mock;


/**
 * @author jdcasey
 */
public class UrlTagParamTagTest extends AbstractTagTestCase{

  public void testDoExecute_NoParams() throws TagException, TagalogParseException, MarmaladeExecutionException{
    String url = "http://www.google.com";
    Map attrs = new TreeMap();
    attrs.put("var", "url");
    attrs.put("url", url);
    
    Mock attrsMock = attributesFromMap(attrs);
    UrlTag tag = new UrlTag();
    tag.begin("url", (Attributes)attrsMock.proxy());
    
    DefaultContext ctx = new DefaultContext();
    assertNull("Nothing up this sleeve...", ctx.getVariable("url", null));
    tag.execute(ctx);
    assertEquals(
      "URL's external form should match the input url.", 
      url, 
      ((URL)ctx.getVariable("url", null)).toExternalForm()
    );
    
    attrsMock.verify();
  }

  public void testDoExecute_NoParamsNewParam() throws TagException, TagalogParseException, MarmaladeExecutionException{
    String url = "http://www.google.com";
    Map attrs = new TreeMap();
    attrs.put("var", "url");
    attrs.put("url", url);
    
    Mock attrsMock = attributesFromMap(attrs);
    UrlTag tag = new UrlTag();
    tag.begin("url", (Attributes)attrsMock.proxy());
    
    Map pAttrs = new TreeMap();
    pAttrs.put("name", "test");
    pAttrs.put("value", "value");
    Mock pAttrsMock = attributesFromMap(pAttrs);
    ParamTag param = new ParamTag();
    param.begin("param", (Attributes)pAttrsMock.proxy());
    
    tag.child(param);
    param.setParent(tag);
    
    DefaultContext ctx = new DefaultContext();
    assertNull("Nothing up this sleeve...", ctx.getVariable("url", null));
    tag.execute(ctx);
    assertEquals(
      "URL's external form should match the input url, plus the added param.", 
      url + "?name=value", 
      ((URL)ctx.getVariable("url", null)).toExternalForm() + "?name=value"
    );
    
    attrsMock.verify();
    pAttrsMock.verify();
  }

  public void testDoExecute_NoParamsNewParamWithSpace() throws TagException, TagalogParseException, MarmaladeExecutionException{
    String url = "http://www.google.com";
    Map attrs = new TreeMap();
    attrs.put("var", "url");
    attrs.put("url", url);
    
    Mock attrsMock = attributesFromMap(attrs);
    UrlTag tag = new UrlTag();
    tag.begin("url", (Attributes)attrsMock.proxy());
    
    Map pAttrs = new TreeMap();
    pAttrs.put("name", "test");
    pAttrs.put("value", "value one");
    Mock pAttrsMock = attributesFromMap(pAttrs);
    ParamTag param = new ParamTag();
    param.begin("param", (Attributes)pAttrsMock.proxy());
    
    tag.child(param);
    param.setParent(tag);
    
    DefaultContext ctx = new DefaultContext();
    assertNull("Nothing up this sleeve...", ctx.getVariable("url", null));
    tag.execute(ctx);
    assertEquals(
      "URL's external form should match the input url, plus the added param.", 
      url + "?name=value+one", 
      ((URL)ctx.getVariable("url", null)).toExternalForm() + "?name=value+one"
    );
    
    attrsMock.verify();
    pAttrsMock.verify();
  }

  public void testDoExecute_HadParamNewParam() throws TagException, TagalogParseException, MarmaladeExecutionException{
    String url = "http://www.google.com?s=one+two+three";
    Map attrs = new TreeMap();
    attrs.put("var", "url");
    attrs.put("url", url);
    
    Mock attrsMock = attributesFromMap(attrs);
    UrlTag tag = new UrlTag();
    tag.begin("url", (Attributes)attrsMock.proxy());
    
    Map pAttrs = new TreeMap();
    pAttrs.put("name", "test");
    pAttrs.put("value", "value");
    Mock pAttrsMock = attributesFromMap(pAttrs);
    ParamTag param = new ParamTag();
    param.begin("param", (Attributes)pAttrsMock.proxy());
    
    tag.child(param);
    param.setParent(tag);
    
    DefaultContext ctx = new DefaultContext();
    assertNull("Nothing up this sleeve...", ctx.getVariable("url", null));
    tag.execute(ctx);
    assertEquals(
      "URL's external form should match the input url, plus the added param.", 
      url + "&name=value", 
      ((URL)ctx.getVariable("url", null)).toExternalForm() + "&name=value"
    );
    
    attrsMock.verify();
    pAttrsMock.verify();
  }

}
