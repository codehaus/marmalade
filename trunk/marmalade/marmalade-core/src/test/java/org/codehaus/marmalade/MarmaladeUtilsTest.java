/* Created on Apr 12, 2004 */
package org.codehaus.marmalade;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.codehaus.marmalade.generics.*;

import junit.framework.TestCase;


/**
 * @author jdcasey
 */
public class MarmaladeUtilsTest extends TestCase{

  public void testParse() throws IOException, MarmaladeExecutionException, MarmaladeParseException{
    File f = File.createTempFile("parse-test-", ".mmld");
    PrintWriter fout = new PrintWriter(new FileWriter(f));
    fout.println(TEST_PARSE_CONTENT);
    fout.flush();
    fout.close();
    
    MarmaladeScript script = MarmaladeUtils.parse(f);
    
    f.delete();
    
    assertNotNull("Parsed script should not be null", script);
    assertEquals(
      "Parsed script's root tag should be of type TestParseTag", 
      script.getRoot().getClass(), 
      TestParseTag.class
    );
    
    DefaultContext ctx = new DefaultContext();
    StringWriter out = new StringWriter();
    ctx.setOutWriter(new PrintWriter(out));
    
    script.execute(ctx);
    
    assertEquals("Messages should equal.", MESSAGE, out.getBuffer().toString());
  }

  private static final String MESSAGE = "This is a test";
  private static final String TEST_PARSE_CONTENT = 
    "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n" + 
    "<testTag xmlns=\"marmalade:org.codehaus.marmalade.TestParseTaglib\" output=\"" + MESSAGE + "\"/>";
  
}
