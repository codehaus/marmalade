/* Created on Apr 12, 2004 */
package org.codehaus.marmalade.parsetime;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import junit.framework.TestCase;

import org.codehaus.marmalade.model.MarmaladeScript;
import org.codehaus.marmalade.modelbuilder.MarmaladeModelBuilderException;
import org.codehaus.marmalade.modelbuilder.MarmaladeTaglibResolver;
import org.codehaus.marmalade.parsetime.MarmaladeParsetimeException;
import org.codehaus.marmalade.parsetime.ScriptParser;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;


/**
 * @author jdcasey
 */
public class ScriptParserTest extends TestCase{

  public void testParse() 
  throws IOException, MarmaladeExecutionException, MarmaladeParsetimeException, 
          MarmaladeModelBuilderException
  {
    File f = File.createTempFile("parse-test-", ".mmld");
    PrintWriter fout = new PrintWriter(new FileWriter(f));
    fout.println(TEST_PARSE_CONTENT);
    fout.flush();
    fout.close();
    
    MarmaladeTaglibResolver resolver = new MarmaladeTaglibResolver(
      MarmaladeTaglibResolver.DEFAULT_STRATEGY_CHAIN
    );
    
    MarmaladeScript script = new ScriptParser(resolver).parse(f);
    
    f.delete();
    
    assertNotNull("Parsed script should not be null", script);
    
    DefaultContext ctx = new DefaultContext();
    StringWriter out = new StringWriter();
    ctx.setOutWriter(new PrintWriter(out));
    
    script.execute(ctx);
    
    assertEquals("Messages should equal.", MESSAGE, out.getBuffer().toString());
  }

  private static final String MESSAGE = "This is a test";
  private static final String TEST_PARSE_CONTENT = 
    "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n" + 
    "<testTag xmlns=\"marmalade:org.codehaus.marmalade.tags.TestParseTaglib\" value=\"" + MESSAGE + "\"/>";
  
}
