/* Created on Apr 11, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.model.MarmaladeScript;
import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;
import org.codehaus.marmalade.modelbuilder.MarmaladeTaglibResolver;
import org.codehaus.marmalade.parsetime.MarmaladeParsetimeException;
import org.codehaus.marmalade.parsetime.ScriptParser;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class ImportTag extends AbstractMarmaladeTag {
  
  public static final String URL_ATTRIBUTE = "url";
  public static final String VAR_ATTRIBUTE = "var";
  public static final String PARSE_ONLY_ATTRIBUTE = "parse-only";
  public static final String RESOLVER_ATTRIBUTE = "resolver";

  public ImportTag(MarmaladeTagInfo tagInfo) {
    super(tagInfo);
  }
  
  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException {
    Object location = requireTagAttribute(URL_ATTRIBUTE, Object.class, context);
    URL resource = null;
    if(location instanceof URL) {
      resource = (URL)location;
    }
    else if(location instanceof String) {
      try{
        resource = new URL((String)location);
      }
      catch(MalformedURLException e){
        throw new MarmaladeExecutionException("Error parsing url into java.net.URL.", e);
      }
    }
    else if(location instanceof File) {
      try{
        resource = ((File)location).toURL();
      }
      catch(MalformedURLException e){
        throw new MarmaladeExecutionException("Error parsing url into java.net.URL.", e);
      }
    }
    else {
      throw new MarmaladeExecutionException(
        "url attribute must be String, java.net.URL, or java.io.File type."
      );
    }
    
    MarmaladeAttributes attributes = getAttributes();
    
    MarmaladeTaglibResolver resolver = (MarmaladeTaglibResolver)attributes.getValue(
      RESOLVER_ATTRIBUTE, MarmaladeTaglibResolver.class, context
    );
    
    if(resolver == null) {
      resolver = new MarmaladeTaglibResolver(MarmaladeTaglibResolver.DEFAULT_STRATEGY_CHAIN);
    }
    
    try{
      ScriptParser parser = new ScriptParser(resolver);
      MarmaladeScript script = parser.parse(resource);
      Boolean parseOnly = (Boolean)attributes.getValue(PARSE_ONLY_ATTRIBUTE, Boolean.class, context, "false");
      boolean shouldExec = (parseOnly == null)?(true):(!parseOnly.booleanValue());
      
      if(shouldExec) {
        script.execute(context);
        String var = (String)attributes.getValue(VAR_ATTRIBUTE, String.class, context);
        if(var != null && var.length() > 0) {
          context.setVariable(var, script);
        }
      }
      else {
        String var = (String)requireTagAttribute(VAR_ATTRIBUTE, String.class, context);
        if(var != null && var.length() > 0) {
          context.setVariable(var, script);
        }
      }
    }
    catch(MarmaladeParsetimeException e){
      throw new MarmaladeExecutionException(
        "Error parsing script from: " + resource.toExternalForm(), e
      );
    }
  }
}
