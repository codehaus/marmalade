/* Created on Apr 13, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.defaults.DefaultContext;
import org.codehaus.marmalade.tags.jstl.core.ErrTag;
import org.codehaus.marmalade.testing.AbstractTagTestCase;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.Mock;


/**
 * @author jdcasey
 */
public class ErrTagTest extends AbstractTagTestCase{

  public void testExecute() throws TagException, TagalogParseException, MarmaladeExecutionException{
    String message = "test message";
    
    Mock attrMock = attributesWithSingleAttribute("value", message);
    
    StringWriter out = new StringWriter();
    ErrTag tag = new ErrTag();
    tag.begin("err", (Attributes)attrMock.proxy());
    
    DefaultContext context = new DefaultContext();
    context.setErrWriter(new PrintWriter(out));
    tag.execute(context);
    
    assertEquals("Error writer should pass the message through.", message, out.getBuffer().toString());
    
    attrMock.verify();
  }

}
