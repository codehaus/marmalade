/* Created on Apr 13, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.defaults.DefaultContext;
import org.codehaus.marmalade.tags.jstl.core.ChooseTag;
import org.codehaus.marmalade.tags.jstl.core.OtherwiseTag;
import org.codehaus.marmalade.tags.jstl.core.WhenTag;
import org.codehaus.marmalade.testing.AbstractTagTestCase;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.Mock;


/**
 * @author jdcasey
 */
public class ChooseTagTest extends AbstractTagTestCase{
  
  public void testExecute_C1Fires() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Mock attrMock = attributesEmpty();
    Mock attrMockC1 = attributesWithSingleAttribute("test", "true");
    Mock attrMockC1F = attributesEmpty();
    Mock attrMockC2 = attributesWithSingleAttribute("test", "true");
    Mock attrMockC2F = attributesEmpty();
    Mock attrMockO = attributesEmpty();
    Mock attrMockOF = attributesEmpty();
    
    ChooseTag tag = new ChooseTag();
    tag.begin("choose", (Attributes)attrMock.proxy());
    
    WhenTag c1 = new WhenTag();
    c1.begin("when", (Attributes)attrMockC1.proxy());
    FlagChildTestTag c1f = new FlagChildTestTag();
    c1f.begin("flag", (Attributes)attrMockC1F.proxy());
    c1.child(c1f);
    c1f.setParent(c1);
    tag.child(c1);
    c1.setParent(tag);
    
    WhenTag c2 = new WhenTag();
    c2.begin("when", (Attributes)attrMockC2.proxy());
    FlagChildTestTag c2f = new FlagChildTestTag();
    c2f.begin("flag", (Attributes)attrMockC2F.proxy());
    c2.child(c2f);
    c2f.setParent(c2);
    tag.child(c2);
    c2.setParent(tag);
    
    OtherwiseTag o = new OtherwiseTag();
    o.begin("otherwise", (Attributes)attrMockO.proxy());
    FlagChildTestTag of = new FlagChildTestTag();
    of.begin("flag", (Attributes)attrMockOF.proxy());
    o.child(of);
    of.setParent(o);
    tag.child(o);
    o.setParent(tag);
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    assertTrue("Condition 1 should have fired.", c1f.fired());
    assertFalse("Condition 2 should NOT have fired.", c2f.fired());
    assertFalse("Otherwise should NOT have fired.", of.fired());
    
    attrMock.verify();
    attrMockC1.verify();
    attrMockC1F.verify();
    attrMockC2.verify();
    attrMockC2F.verify();
    attrMockO.verify();
    attrMockOF.verify();
  }

  public void testExecute_C2Fires() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Mock attrMock = attributesEmpty();
    Mock attrMockC1 = attributesWithSingleAttribute("test", "false");
    Mock attrMockC1F = attributesEmpty();
    Mock attrMockC2 = attributesWithSingleAttribute("test", "true");
    Mock attrMockC2F = attributesEmpty();
    Mock attrMockO = attributesEmpty();
    Mock attrMockOF = attributesEmpty();
    
    ChooseTag tag = new ChooseTag();
    tag.begin("choose", (Attributes)attrMock.proxy());
    
    WhenTag c1 = new WhenTag();
    c1.begin("when", (Attributes)attrMockC1.proxy());
    FlagChildTestTag c1f = new FlagChildTestTag();
    c1f.begin("flag", (Attributes)attrMockC1F.proxy());
    c1.child(c1f);
    c1f.setParent(c1);
    tag.child(c1);
    c1.setParent(tag);
    
    WhenTag c2 = new WhenTag();
    c2.begin("when", (Attributes)attrMockC2.proxy());
    FlagChildTestTag c2f = new FlagChildTestTag();
    c2f.begin("flag", (Attributes)attrMockC2F.proxy());
    c2.child(c2f);
    c2f.setParent(c2);
    tag.child(c2);
    c2.setParent(tag);
    
    OtherwiseTag o = new OtherwiseTag();
    o.begin("otherwise", (Attributes)attrMockO.proxy());
    FlagChildTestTag of = new FlagChildTestTag();
    of.begin("flag", (Attributes)attrMockOF.proxy());
    o.child(of);
    of.setParent(o);
    tag.child(o);
    o.setParent(tag);
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    assertFalse("Condition 1 should NOT have fired.", c1f.fired());
    assertTrue("Condition 2 should have fired.", c2f.fired());
    assertFalse("Otherwise should NOT have fired.", of.fired());
    
    attrMock.verify();
    attrMockC1.verify();
    attrMockC1F.verify();
    attrMockC2.verify();
    attrMockC2F.verify();
    attrMockO.verify();
    attrMockOF.verify();
  }

  public void testExecute_OFires() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Mock attrMock = attributesEmpty();
    Mock attrMockC1 = attributesWithSingleAttribute("test", "false");
    Mock attrMockC1F = attributesEmpty();
    Mock attrMockC2 = attributesWithSingleAttribute("test", "false");
    Mock attrMockC2F = attributesEmpty();
    Mock attrMockO = attributesEmpty();
    Mock attrMockOF = attributesEmpty();
    
    ChooseTag tag = new ChooseTag();
    tag.begin("choose", (Attributes)attrMock.proxy());
    
    WhenTag c1 = new WhenTag();
    c1.begin("when", (Attributes)attrMockC1.proxy());
    FlagChildTestTag c1f = new FlagChildTestTag();
    c1f.begin("flag", (Attributes)attrMockC1F.proxy());
    c1.child(c1f);
    c1f.setParent(c1);
    tag.child(c1);
    c1.setParent(tag);
    
    WhenTag c2 = new WhenTag();
    c2.begin("when", (Attributes)attrMockC2.proxy());
    FlagChildTestTag c2f = new FlagChildTestTag();
    c2f.begin("flag", (Attributes)attrMockC2F.proxy());
    c2.child(c2f);
    c2f.setParent(c2);
    tag.child(c2);
    c2.setParent(tag);
    
    OtherwiseTag o = new OtherwiseTag();
    o.begin("otherwise", (Attributes)attrMockO.proxy());
    FlagChildTestTag of = new FlagChildTestTag();
    of.begin("flag", (Attributes)attrMockOF.proxy());
    o.child(of);
    of.setParent(o);
    tag.child(o);
    o.setParent(tag);
    
    DefaultContext ctx = new DefaultContext();
    tag.execute(ctx);
    
    assertFalse("Condition 1 should NOT have fired.", c1f.fired());
    assertFalse("Condition 2 should NOT have fired.", c2f.fired());
    assertTrue("Otherwise should have fired.", of.fired());
    
    attrMock.verify();
    attrMockC1.verify();
    attrMockC1F.verify();
    attrMockC2.verify();
    attrMockC2F.verify();
    attrMockO.verify();
    attrMockOF.verify();
  }

}
