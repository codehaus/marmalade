/* Created on Apr 14, 2004 */
package org.codehaus.marmalade.tags.core;

import java.util.Map;
import java.util.TreeMap;

import org.codehaus.marmalade.DefaultContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.testing.AbstractTagTestCase;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.Mock;


/**
 * @author jdcasey
 */
public class SetTagTest extends AbstractTagTestCase{

  public void testDoExecute_VarValue() throws TagException, TagalogParseException, MarmaladeExecutionException{
    Map attrs = new TreeMap();
    attrs.put("var", "testKey");
    attrs.put("value", "value");
    Mock attrMock = attributesFromMap(attrs);
    
    SetTag tag = new SetTag();
    tag.begin("set", (Attributes)attrMock.proxy());
    
    DefaultContext ctx = new DefaultContext();
    assertNull("Variable \'testKey\' should not be in context.", ctx.getVariable("testKey", null));
    tag.execute(ctx);
    assertEquals("Can't verify successful setVariable() operation.", "value", ctx.getVariable("testKey", null));
    attrMock.verify();
  }

  public void testDoExecute_PropertyTargetValue() throws TagException, TagalogParseException, MarmaladeExecutionException{
    Map attrs = new TreeMap();
    attrs.put("property", "attribute.value");
    attrs.put("target", "#subject");
    attrs.put("value", "testResult");
    Mock attrMock = attributesFromMap(attrs);
    
    SetTag tag = new SetTag();
    tag.begin("set", (Attributes)attrMock.proxy());
    
    DefaultContext ctx = new DefaultContext();
    
    TestSubject subject = new TestSubject(new TestAttribute("testSource"));
    ctx.setVariable("subject", subject);
    
    assertEquals("Pre-Test: subject's attribute's value should be \'testSource\'", "testSource", subject.getAttribute().getValue());
    tag.execute(ctx);
    assertEquals("Post-Test: subject's attribute's value should be \'testResult\'", "testResult", subject.getAttribute().getValue());
    attrMock.verify();
  }

}
