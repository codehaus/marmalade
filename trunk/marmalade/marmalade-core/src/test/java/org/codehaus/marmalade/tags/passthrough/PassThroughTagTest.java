/* Created on Apr 20, 2004 */
package org.codehaus.marmalade.tags.passthrough;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.defaults.DefaultContext;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import junit.framework.TestCase;


/**
 * @author jdcasey
 */
public class PassThroughTagTest extends MockObjectTestCase{
  
  public void testPassThroughTag_Empty() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Mock attrMock = passThroughTagAttributes();
    
    PassThroughTag tag = new PassThroughTag();
    tag.begin("tag", (Attributes)attrMock.proxy());
    tag.end("tag");
    
    DefaultContext ctx = new DefaultContext();
    StringWriter str = new StringWriter();
    PrintWriter out = new PrintWriter(str);
    ctx.setOutWriter(out);
    
    tag.execute(ctx);
    
    String expected = "<tag marmalade:el=\"ognl\" test=\"#myval.idIsTrue\"/>";
    
    System.out.println("Expected: " + expected);
    System.out.println("Got: " + str.getBuffer().toString());
    
    assertEquals(
      "PassThroughTag output for empth element didn't match expected.", 
      expected, 
      str.getBuffer().toString()
    );
    
    attrMock.verify();
  }
  
  public void testPassThroughTag_Body_NoChildren() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Mock attrMock = passThroughTagAttributes();
    
    String bodyText = "\n\n            This is a test.\n\n            ";
    
    PassThroughTag tag = new PassThroughTag();
    tag.begin("tag", (Attributes)attrMock.proxy());
    tag.text(bodyText.toCharArray(), 0, bodyText.length());
    tag.end("tag");
    
    DefaultContext ctx = new DefaultContext();
    StringWriter str = new StringWriter();
    PrintWriter out = new PrintWriter(str);
    ctx.setOutWriter(out);
    
    tag.execute(ctx);
    
    String expected = "<tag marmalade:el=\"ognl\" test=\"#myval.idIsTrue\">" + 
      bodyText + "</tag>";
    
    System.out.println("Expected: " + expected);
    System.out.println("Got: " + str.getBuffer().toString());
    
    assertEquals(
      "PassThroughTag output for empth element didn't match expected.", 
      expected, 
      str.getBuffer().toString()
    );
    
    attrMock.verify();
  }
  
  public void testPassThroughTag_NoBody_Children() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Mock attrMock = passThroughTagAttributes();
    
    PassThroughTag tag = new PassThroughTag();
    tag.begin("tag", (Attributes)attrMock.proxy());
    
    PassThroughTag child1 = new PassThroughTag();
    Mock c1AttrMock = passThroughTagAttributes();
    child1.begin("child", (Attributes)c1AttrMock.proxy());
    child1.setParent(tag);
    child1.end("child");
    tag.child(child1);
    
    PassThroughTag child2 = new PassThroughTag();
    Mock c2AttrMock = passThroughTagAttributes();
    child2.begin("child", (Attributes)c2AttrMock.proxy());
    child2.setParent(tag);
    child2.end("child");
    tag.child(child2);
    
    tag.end("tag");
    
    DefaultContext ctx = new DefaultContext();
    StringWriter str = new StringWriter();
    PrintWriter out = new PrintWriter(str);
    ctx.setOutWriter(out);
    
    tag.execute(ctx);
    
    String expected = 
      "<tag marmalade:el=\"ognl\" test=\"#myval.idIsTrue\">" + 
        "<child marmalade:el=\"ognl\" test=\"#myval.idIsTrue\"/>" + 
        "<child marmalade:el=\"ognl\" test=\"#myval.idIsTrue\"/>" + 
      "</tag>";
    
    System.out.println("Expected: " + expected);
    System.out.println("Got: " + str.getBuffer().toString());
    
    assertEquals(
      "PassThroughTag output for empth element didn't match expected.", 
      expected, 
      str.getBuffer().toString()
    );
    
    attrMock.verify();
    c1AttrMock.verify();
    c2AttrMock.verify();
  }
  
  public void testPassThroughTag_Body_Children() throws TagException, TagalogParseException, MarmaladeExecutionException {
    Mock attrMock = passThroughTagAttributes();
    
    PassThroughTag tag = new PassThroughTag();
    tag.begin("tag", (Attributes)attrMock.proxy());
    
    String body1 = "\n    This is a test.\n    ";
    tag.text(body1.toCharArray(), 0, body1.length());
    
    PassThroughTag child1 = new PassThroughTag();
    Mock c1AttrMock = passThroughTagAttributes();
    child1.begin("child", (Attributes)c1AttrMock.proxy());
    child1.setParent(tag);
    child1.end("child");
    tag.child(child1);
    
    String body2 = "\n    This is also a test.\n    ";
    tag.text(body2.toCharArray(), 0, body2.length());
    
    PassThroughTag child2 = new PassThroughTag();
    Mock c2AttrMock = passThroughTagAttributes();
    child2.begin("child", (Attributes)c2AttrMock.proxy());
    child2.setParent(tag);
    child2.end("child");
    tag.child(child2);
    
    String body3 = "\n    This is a test, too.\n";
    tag.text(body3.toCharArray(), 0, body3.length());
    
    tag.end("tag");
    
    DefaultContext ctx = new DefaultContext();
    StringWriter str = new StringWriter();
    PrintWriter out = new PrintWriter(str);
    ctx.setOutWriter(out);
    
    tag.execute(ctx);
    
    String expected = 
      "<tag marmalade:el=\"ognl\" test=\"#myval.idIsTrue\">" + 
        body1 + 
        "<child marmalade:el=\"ognl\" test=\"#myval.idIsTrue\"/>" + 
        body2 + 
        "<child marmalade:el=\"ognl\" test=\"#myval.idIsTrue\"/>" + 
        body3 + 
      "</tag>";
    
    System.out.println("Expected: " + expected);
    System.out.println("Got: " + str.getBuffer().toString());
    
    assertEquals(
      "PassThroughTag output for empth element didn't match expected.", 
      expected, 
      str.getBuffer().toString()
    );
    
    attrMock.verify();
    c1AttrMock.verify();
    c2AttrMock.verify();
  }
  
  private Mock passThroughTagAttributes() {
    Mock attrMock = mock(Attributes.class, "PassThroughTag attributes");
    attrMock.expects(once())
        .method("getValue")
        .with(eq("marmalade:el"))
        .will(returnValue("ognl"));
    attrMock.expects(once())
        .method("getAttributeCount")
        .withNoArguments()
        .will(returnValue(2));
    attrMock.expects(once())
        .method("getNamespaceUri")
        .with(eq(0))
        .will(returnValue(null));
    attrMock.expects(once())
        .method("getName")
        .with(eq(0))
        .will(returnValue("marmalade:el"));
    attrMock.expects(once())
        .method("getValue")
        .with(eq(0))
        .will(returnValue("ognl"));
    attrMock.expects(once())
        .method("getNamespaceUri")
        .with(eq(1))
        .will(returnValue(null));
    attrMock.expects(once())
        .method("getName")
        .with(eq(1))
        .will(returnValue("test"));
    attrMock.expects(once())
        .method("getValue")
        .with(eq(1))
        .will(returnValue("#myval.idIsTrue"));
    attrMock.expects(once())
        .method("getAttributeCount")
        .withNoArguments()
        .will(returnValue(2));
    attrMock.expects(once())
        .method("getNamespaceUri")
        .with(eq(0))
        .will(returnValue(null));
    attrMock.expects(once())
        .method("getName")
        .with(eq(0))
        .will(returnValue("marmalade:el"));
    attrMock.expects(once())
        .method("getValue")
        .with(eq(0))
        .will(returnValue("ognl"));
    attrMock.expects(once())
        .method("getNamespaceUri")
        .with(eq(1))
        .will(returnValue(null));
    attrMock.expects(once())
        .method("getName")
        .with(eq(1))
        .will(returnValue("test"));
    attrMock.expects(once())
        .method("getValue")
        .with(eq(1))
        .will(returnValue("#myval.idIsTrue"));
    
    return attrMock;
  }

}
