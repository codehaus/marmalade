/* Created on Apr 13, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import junit.framework.TestCase;

import org.codehaus.marmalade.el.ognl.OgnlExpressionEvaluator;
import org.codehaus.marmalade.modelbuilder.DefaultRawAttribute;
import org.codehaus.marmalade.modelbuilder.DefaultRawAttributes;
import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.MissingAttributeException;
import org.codehaus.marmalade.tags.jstl.core.IfTag;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.Mock;


/**
 * @author jdcasey
 */
public class IfTagTest extends TestCase{
  
  public void testShouldRequireTestAttribute() throws MarmaladeExecutionException {
    MarmaladeTagInfo ti = new MarmaladeTagInfo();
    ti.setAttributes(new DefaultRawAttributes());
    
    IfTag tag = new IfTag(ti);
    try {
      tag.execute(new DefaultContext());
      fail("should fail because of missing test attribute");
    }
    catch(MissingAttributeException e) {
      // should snag on missing test attribute
    }
  }

  public void testShouldExecuteChildrenWhenTestResultIsTrue() throws MarmaladeExecutionException{
    DefaultRawAttributes attrs = new DefaultRawAttributes();
    attrs.addAttribute(new DefaultRawAttribute("", "test", "true"));
    
    MarmaladeTagInfo ti = new MarmaladeTagInfo();
    ti.setAttributes(attrs);
    ti.setExpressionEvaluator(new OgnlExpressionEvaluator());
    
    IfTag tag = new IfTag(ti);
    
    MarmaladeTagInfo flagTI = new MarmaladeTagInfo();
    flagTI.setAttributes(new DefaultRawAttributes());
    
    FlagChildTestTag flag = new FlagChildTestTag(ti);
    
    tag.addChild(flag);
    flag.setParent(tag);
    
    tag.execute(new DefaultContext());
    
    assertTrue("Child tag should have fired.", flag.fired());
  }

  public void testShouldNotExecuteChildrenWhenTestResultIsFalse() throws MarmaladeExecutionException{
    DefaultRawAttributes attrs = new DefaultRawAttributes();
    attrs.addAttribute(new DefaultRawAttribute("", "test", "false"));
    
    MarmaladeTagInfo ti = new MarmaladeTagInfo();
    ti.setAttributes(attrs);
    ti.setExpressionEvaluator(new OgnlExpressionEvaluator());
    
    IfTag tag = new IfTag(ti);
    
    MarmaladeTagInfo flagTI = new MarmaladeTagInfo();
    flagTI.setAttributes(new DefaultRawAttributes());
    
    FlagChildTestTag flag = new FlagChildTestTag(ti);
    
    tag.addChild(flag);
    flag.setParent(tag);
    
    tag.execute(new DefaultContext());
    
    assertFalse("Child tag should NOT have fired.", flag.fired());
  }

}
