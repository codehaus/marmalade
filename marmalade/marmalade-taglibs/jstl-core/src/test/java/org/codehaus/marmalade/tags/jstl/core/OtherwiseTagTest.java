/* Created on Apr 13, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.generics.DefaultContext;
import org.codehaus.marmalade.tags.jstl.core.ChooseTag;
import org.codehaus.marmalade.tags.jstl.core.WhenTag;
import org.codehaus.marmalade.testing.AbstractTagTestCase;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.Mock;


/**
 * @author jdcasey
 */
public class OtherwiseTagTest extends AbstractTagTestCase{

  public void testDoExecute_NoParent() throws TagException, TagalogParseException{
    Mock attrMock = attributesEmpty();
    WhenTag tag = new WhenTag();
    tag.begin("othewise", (Attributes)attrMock.proxy());
    
    Mock attrMock2 = attributesEmpty();
    
    FlagChildTestTag flag = new FlagChildTestTag();
    flag.begin("flag", (Attributes)attrMock2.proxy());
    
    tag.child(flag);
    
    DefaultContext context = new DefaultContext();
    try {
      tag.execute(context);
      fail("Tag should fail due to missing ChooseTag parent.");
    }
    catch(MarmaladeExecutionException e) {
    }
    attrMock.verify();
    attrMock2.verify();
  }

  public void testDoExecute_WithParent() throws TagException, TagalogParseException, MarmaladeExecutionException{
    Mock zAttrMock = attributesEmpty();
    ChooseTag parent = new ChooseTag();
    parent.begin("choose", (Attributes)zAttrMock.proxy());
    
    Mock attrMock = attributesEmpty();
    WhenTag tag = new WhenTag();
    tag.begin("otherwise", (Attributes)attrMock.proxy());
    
    parent.child(tag);
    tag.setParent(parent);
    
    Mock attrMock2 = attributesEmpty();
    FlagChildTestTag flag = new FlagChildTestTag();
    flag.begin("flag", (Attributes)attrMock2.proxy());
    
    tag.child(flag);
    flag.setParent(tag);
    
    DefaultContext context = new DefaultContext();
    tag.execute(context);
    assertTrue("Child tag should have fired.", flag.fired());
    
    zAttrMock.verify();
    attrMock.verify();
    attrMock2.verify();
  }

}
