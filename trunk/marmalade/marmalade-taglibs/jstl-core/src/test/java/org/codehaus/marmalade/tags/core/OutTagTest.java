/* Created on Apr 13, 2004 */
package org.codehaus.marmalade.tags.core;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.codehaus.marmalade.DefaultContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.testing.AbstractTagTestCase;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import junit.framework.TestCase;


/**
 * @author jdcasey
 */
public class OutTagTest extends AbstractTagTestCase{

  public void testExecute() throws TagException, TagalogParseException, MarmaladeExecutionException{
    String message = "test message";
    
    Mock attrMock = attributesWithSingleAttribute("value", message);
    
    StringWriter out = new StringWriter();
    OutTag tag = new OutTag();
    tag.begin("out", (Attributes)attrMock.proxy());
    
    DefaultContext context = new DefaultContext();
    context.setOutWriter(new PrintWriter(out));
    tag.execute(context);
    
    assertEquals("Out writer should pass the message through.", message, out.getBuffer().toString());
    
    attrMock.verify();
  }

}
