/* Created on Apr 14, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import junit.framework.TestCase;

import org.codehaus.marmalade.el.ognl.OgnlExpressionEvaluator;
import org.codehaus.marmalade.modelbuilder.DefaultRawAttribute;
import org.codehaus.marmalade.modelbuilder.DefaultRawAttributes;
import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.MissingAttributeException;
import org.codehaus.marmalade.tags.jstl.core.RemoveTag;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.Mock;


/**
 * @author jdcasey
 */
public class RemoveTagTest extends TestCase{

  public void testShouldRequireVarAttribute() throws TagException, TagalogParseException, MarmaladeExecutionException{
    MarmaladeTagInfo ti = new MarmaladeTagInfo();
    ti.setAttributes(new DefaultRawAttributes());
    ti.setExpressionEvaluator(new OgnlExpressionEvaluator());
    
    RemoveTag tag = new RemoveTag(ti);
    
    try
    {
      tag.execute( new DefaultContext() );
      fail("should fail because of missing var attribute");
    }
    catch( MissingAttributeException e )
    {
      // should snag on missing var attribute
    }
  }

  public void testShouldRemoveExistingVariableFromContext() throws TagException, TagalogParseException, MarmaladeExecutionException{
    DefaultRawAttributes attrs = new DefaultRawAttributes();
    attrs.addAttribute(new DefaultRawAttribute("", "var", "testKey"));
    
    MarmaladeTagInfo ti = new MarmaladeTagInfo();
    ti.setAttributes(attrs);
    ti.setExpressionEvaluator(new OgnlExpressionEvaluator());
    
    RemoveTag tag = new RemoveTag(ti);
    
    DefaultContext ctx = new DefaultContext();
    ctx.setVariable("testKey", "value");
    
    assertEquals("Can't verify successful setVariable() operation.", "value", ctx.getVariable("testKey", null));
    tag.execute(ctx);
    assertNull("Variable \'testKey\' should no longer be in context.", ctx.getVariable("testKey", null));
  }

}
