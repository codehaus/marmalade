/* Created on Apr 15, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

import junit.framework.TestCase;

import org.codehaus.marmalade.el.ognl.OgnlExpressionEvaluator;
import org.codehaus.marmalade.model.MarmaladeScript;
import org.codehaus.marmalade.modelbuilder.DefaultRawAttributes;
import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.jstl.core.ImportTag;
import org.jmock.Mock;


/**
 * @author jdcasey
 */
public class ImportTagTest extends TestCase{
  
  private static final String SCRIPT = 
    "<?xml version=\"1.0\"?>\n\n" +
    "<set xmlns=\"marmalade:jstl-core\" var=\"testKey\" value=\"testValue\"/>\n\n";
  
  private File scriptFile;

  public void testShouldParseButNotExecuteScriptFromStringUrlWhenParseOnlyAttributeTrue() throws MarmaladeExecutionException, MalformedURLException{
    _testScriptImport(scriptFile.toURL().toExternalForm(), true, true);
  }
  
  public void testShouldParseAndExecuteScriptFromStringUrlWhenParseOnlyAttributeFalse() throws MarmaladeExecutionException, MalformedURLException{
    _testScriptImport(scriptFile.toURL().toExternalForm(), true, false);
  }

  public void testShouldParseButNotExecuteScriptFromUrlObjectWhenParseOnlyAttributeTrue() throws MarmaladeExecutionException, MalformedURLException{
    _testScriptImport(scriptFile.toURL(), true, true);
  }
  
  public void testShouldParseAndExecuteScriptFromUrlObjectWhenParseOnlyAttributeFalse() throws MarmaladeExecutionException, MalformedURLException{
    _testScriptImport(scriptFile.toURL(), true, false);
  }

  public void testShouldParseAndExecuteScriptFromUrlObjectWhenParseOnlyAttributeUnspecified() throws MarmaladeExecutionException, MalformedURLException{
    _testScriptImport(scriptFile.toURL(), false, false);
  }

  protected void setUp() throws Exception
  {
    this.scriptFile = File.createTempFile("import-test", ".mmld");
    
    BufferedWriter out = new BufferedWriter(new FileWriter(scriptFile));
    out.write(SCRIPT);
    out.flush();
    out.close();
  }

  protected void tearDown() throws Exception
  {
    if(scriptFile != null) {
      scriptFile.delete();
    }
  }
  
  private void _testScriptImport(Object urlObject, boolean parseOnlySpecified, boolean parseOnly) throws MarmaladeExecutionException, MalformedURLException{
    DefaultRawAttributes attrs = new DefaultRawAttributes();
    attrs.addAttribute("", "url", "#scriptLocation");
    attrs.addAttribute("", "var", "script");
    if(parseOnlySpecified) {
      attrs.addAttribute("", "parse-only", Boolean.toString(parseOnly));
    }
    
    OgnlExpressionEvaluator el = new OgnlExpressionEvaluator();
    
    MarmaladeTagInfo ti = new MarmaladeTagInfo();
    ti.setAttributes(attrs);
    ti.setExpressionEvaluator(el);
    
    ImportTag tag = new ImportTag(ti);
    
    DefaultContext ctx = new DefaultContext();
    ctx.setVariable("scriptLocation", urlObject);
    
    tag.execute(ctx);
    
    if(parseOnly) {
      assertNull(ctx.getVariable("testKey", el));
    }
    else {
      assertNotNull(ctx.getVariable("testKey", el));
    }
    
    assertNotNull(ctx.getVariable("script", el));
    assertTrue(
      ctx.getVariable("script", el) instanceof MarmaladeScript
    );
  }
  
}
