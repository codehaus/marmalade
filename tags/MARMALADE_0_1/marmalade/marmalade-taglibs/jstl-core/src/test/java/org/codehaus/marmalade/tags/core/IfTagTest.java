/* Created on Apr 13, 2004 */
package org.codehaus.marmalade.tags.core;

import org.codehaus.marmalade.DefaultContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.testing.AbstractTagTestCase;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.Mock;


/**
 * @author jdcasey
 */
public class IfTagTest extends AbstractTagTestCase{

  public void testDoExecute() throws TagException, TagalogParseException, MarmaladeExecutionException{
    Mock attrMock = attributesWithSingleAttribute("test", "true");
    
    IfTag tag = new IfTag();
    tag.begin("if", (Attributes)attrMock.proxy());
    
    Mock attrMock2 = attributesEmpty();
    
    FlagChildTestTag flag = new FlagChildTestTag();
    flag.begin("flag", (Attributes)attrMock2.proxy());
    
    tag.child(flag);
    
    DefaultContext context = new DefaultContext();
    tag.execute(context);
    
    assertTrue("Child tag should have fired.", flag.fired());
    
    attrMock.verify();
    attrMock2.verify();
  }

}
