/* Created on Apr 15, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.MarmaladeScript;
import org.codehaus.marmalade.generics.DefaultContext;
import org.codehaus.marmalade.tags.jstl.core.ImportTag;
import org.codehaus.marmalade.testing.AbstractTagTestCase;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.Mock;


/**
 * @author jdcasey
 */
public class ImportTagTest extends AbstractTagTestCase{

  public void testDoExecute_StringUrl_ParseOnly() throws TagException, TagalogParseException, MarmaladeExecutionException{
    Map attrs = new TreeMap();
    attrs.put("url", "#scriptLocation");
    attrs.put("var", "script");
    attrs.put("parse-only", "true");
    
    Mock attrsMock = attributesFromMap(attrs);
    ImportTag tag = new ImportTag();
    tag.begin("import", (Attributes)attrsMock.proxy());
    
    DefaultContext ctx = new DefaultContext();
    ctx.setVariable("scriptLocation", "http://marmalade.codehaus.org/unit-tests/importTest.mmld");
    
    tag.execute(ctx);
    
    assertNotNull("Parsed script is not present in context.", ctx.getVariable("script", null));
    assertTrue(
      "Script variable is not of type MarmaladeScript.", 
      ctx.getVariable("script", null) instanceof MarmaladeScript
    );
  }

  public void testDoExecute_StringUrl_Execute() throws TagException, TagalogParseException, MarmaladeExecutionException{
    Map attrs = new TreeMap();
    attrs.put("url", "#scriptLocation");
    attrs.put("var", "script");
    attrs.put("parse-only", "false");
    
    Mock attrsMock = attributesFromMap(attrs);
    ImportTag tag = new ImportTag();
    tag.begin("import", (Attributes)attrsMock.proxy());
    
    DefaultContext ctx = new DefaultContext();
    ctx.setVariable("scriptLocation", "http://marmalade.codehaus.org/unit-tests/importTest.mmld");
    
    tag.execute(ctx);
    
    assertNotNull("Parsed script is not present in context.", ctx.getVariable("script", null));
    assertTrue(
      "Script variable is not of type MarmaladeScript.", 
      ctx.getVariable("script", null) instanceof MarmaladeScript
    );
    assertNotNull("Parsed script add-ins are not present in context.", ctx.getVariable("import-result", null));
  }

  public void testDoExecute_URLUrl_ParseOnly() throws TagException, TagalogParseException, MarmaladeExecutionException, MalformedURLException{
    Map attrs = new TreeMap();
    attrs.put("url", "#scriptLocation");
    attrs.put("var", "script");
    attrs.put("parse-only", "true");
    
    Mock attrsMock = attributesFromMap(attrs);
    ImportTag tag = new ImportTag();
    tag.begin("import", (Attributes)attrsMock.proxy());
    
    DefaultContext ctx = new DefaultContext();
    ctx.setVariable("scriptLocation", new URL("http://marmalade.codehaus.org/unit-tests/importTest.mmld"));
    
    tag.execute(ctx);
    
    assertNotNull("Parsed script is not present in context.", ctx.getVariable("script", null));
    assertTrue(
      "Script variable is not of type MarmaladeScript.", 
      ctx.getVariable("script", null) instanceof MarmaladeScript
    );
  }

  public void testDoExecute_URLUrl_Execute() throws TagException, TagalogParseException, MarmaladeExecutionException, MalformedURLException{
    Map attrs = new TreeMap();
    attrs.put("url", "#scriptLocation");
    attrs.put("var", "script");
    attrs.put("parse-only", "false");
    
    Mock attrsMock = attributesFromMap(attrs);
    ImportTag tag = new ImportTag();
    tag.begin("import", (Attributes)attrsMock.proxy());
    
    DefaultContext ctx = new DefaultContext();
    ctx.setVariable("scriptLocation", new URL("http://marmalade.codehaus.org/unit-tests/importTest.mmld"));
    
    tag.execute(ctx);
    
    assertNotNull("Parsed script is not present in context.", ctx.getVariable("script", null));
    assertTrue(
      "Script variable is not of type MarmaladeScript.", 
      ctx.getVariable("script", null) instanceof MarmaladeScript
    );
    assertNotNull("Parsed script add-ins are not present in context.", ctx.getVariable("import-result", null));
  }

  public void testDoExecute_URLUrl_DefaultIsExecute() throws TagException, TagalogParseException, MarmaladeExecutionException, MalformedURLException{
    Map attrs = new TreeMap();
    attrs.put("url", "#scriptLocation");
    attrs.put("var", "script");
    
    Mock attrsMock = attributesFromMap(attrs);
    ImportTag tag = new ImportTag();
    tag.begin("import", (Attributes)attrsMock.proxy());
    
    DefaultContext ctx = new DefaultContext();
    ctx.setVariable("scriptLocation", new URL("http://marmalade.codehaus.org/unit-tests/importTest.mmld"));
    
    tag.execute(ctx);
    
    assertNotNull("Parsed script is not present in context.", ctx.getVariable("script", null));
    assertTrue(
      "Script variable is not of type MarmaladeScript.", 
      ctx.getVariable("script", null) instanceof MarmaladeScript
    );
    assertNotNull("Parsed script add-ins are not present in context.", ctx.getVariable("import-result", null));
  }

}
