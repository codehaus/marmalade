/* Created on Apr 14, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.defaults.DefaultContext;
import org.codehaus.marmalade.tags.jstl.core.RemoveTag;
import org.codehaus.marmalade.testing.AbstractTagTestCase;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.Mock;


/**
 * @author jdcasey
 */
public class RemoveTagTest extends AbstractTagTestCase{

  public void testDoExecute() throws TagException, TagalogParseException, MarmaladeExecutionException{
    Mock attrMock = attributesWithSingleAttribute("var", "testKey");
    
    RemoveTag tag = new RemoveTag();
    tag.begin("remove", (Attributes)attrMock.proxy());
    
    DefaultContext ctx = new DefaultContext();
    ctx.setVariable("testKey", "value");
    
    assertEquals("Can't verify successful setVariable() operation.", "value", ctx.getVariable("testKey", null));
    tag.execute(ctx);
    assertNull("Variable \'testKey\' should no longer be in context.", ctx.getVariable("testKey", null));
    
    attrMock.verify();
  }

}
