/* Created on Apr 14, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

import junit.framework.TestCase;

import org.codehaus.marmalade.el.ognl.OgnlExpressionEvaluator;
import org.codehaus.marmalade.modelbuilder.DefaultRawAttributes;
import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.jstl.core.ParamTag;
import org.codehaus.marmalade.tags.jstl.core.UrlTag;


/**
 * @author jdcasey
 */
public class UrlTagParamTagTest extends TestCase{
  
  private void _testUrlTag(String url, ParamTag[] params, String checkUrl) throws MarmaladeExecutionException {
    DefaultRawAttributes attrs = new DefaultRawAttributes();
    attrs.addAttribute("", "var", "url");
    attrs.addAttribute("", "url", url);
    
    OgnlExpressionEvaluator el = new OgnlExpressionEvaluator();
    
    MarmaladeTagInfo ti = new MarmaladeTagInfo();
    ti.setAttributes(attrs);
    ti.setExpressionEvaluator(el);
    
    UrlTag tag = new UrlTag(ti);
    
    if(params != null) {
      for( int i = 0; i < params.length; i++ )
      {
        ParamTag paramTag = params[i];
        tag.addChild(paramTag);
        paramTag.setParent(tag);
      }
    }
    
    DefaultContext ctx = new DefaultContext();
    assertNull("url variable should be null", ctx.getVariable("url", el));
    tag.execute(ctx);
    URL test = (URL)ctx.getVariable("url", el);    
    
    System.out.println("Original URL: " + url + "\tResulting URL: " + test.toExternalForm());
    
    assertEquals(checkUrl, test.toExternalForm());
  }

  public void testShouldConstructURLObjectFromStringUrlWithNoParamsWithoutParamChildren() throws MarmaladeExecutionException{
    String url = "http://www.google.com";
    _testUrlTag(url, null, url);
  }

  public void testShouldConstructURLObjectFromStringUrlWithNoParamsWithOneParamChild() throws MarmaladeExecutionException{
    String url = "http://www.google.com";
    String check = url + "?test=value";
    
    DefaultRawAttributes attrs = new DefaultRawAttributes();
    attrs.addAttribute("", "name", "test");
    attrs.addAttribute("", "value", "value");
    
    MarmaladeTagInfo ti = new MarmaladeTagInfo();
    ti.setAttributes(attrs);
    ti.setExpressionEvaluator(new OgnlExpressionEvaluator());
    
    ParamTag param = new ParamTag(ti);
    
    _testUrlTag(url, new ParamTag[] {param}, check);
  }

  public void testShouldConstructURLObjectFromStringUrlWithNoParamsWithOneParamChildContainingSpaces() throws MarmaladeExecutionException{
    String url = "http://www.google.com";
    String check = url + "?test=test+value";
    
    DefaultRawAttributes attrs = new DefaultRawAttributes();
    attrs.addAttribute("", "name", "test");
    attrs.addAttribute("", "value", "test value");
    
    MarmaladeTagInfo ti = new MarmaladeTagInfo();
    ti.setAttributes(attrs);
    ti.setExpressionEvaluator(new OgnlExpressionEvaluator());
    
    ParamTag param = new ParamTag(ti);
    
    _testUrlTag(url, new ParamTag[] {param}, check);
  }

  public void testShouldConstructURLObjectFromStringUrlWithOneParamAndOneParamChild() throws MarmaladeExecutionException{
    String url = "http://www.google.com?param=pValue";
    String check = url + "&test=value";
    
    DefaultRawAttributes attrs = new DefaultRawAttributes();
    attrs.addAttribute("", "name", "test");
    attrs.addAttribute("", "value", "value");
    
    MarmaladeTagInfo ti = new MarmaladeTagInfo();
    ti.setAttributes(attrs);
    ti.setExpressionEvaluator(new OgnlExpressionEvaluator());
    
    ParamTag param = new ParamTag(ti);
    
    _testUrlTag(url, new ParamTag[] {param}, check);
  }

}
