/* Created on Apr 11, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class UrlTag extends AbstractMarmaladeTag {
  
  public static final String VAR_ATTRIBUTE = "var";
  public static final String URL_ATTRIBUTE = "url";
  
  private Map params = new TreeMap();

  public UrlTag(MarmaladeTagInfo tagInfo) {
    super(tagInfo);
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException {
    String url = (String)requireTagAttribute(URL_ATTRIBUTE, String.class, context);
    String var = (String)requireTagAttribute(VAR_ATTRIBUTE, String.class, context);
    
    processChildren(context);
    
    StringBuffer finishedUrl = new StringBuffer(url);
    if(finishedUrl.indexOf("?") < 0){finishedUrl.append("?");}
    else {finishedUrl.append("&");}
    
    for (Iterator it = params.keySet().iterator(); it.hasNext();) {
      String key = (String)it.next();
      String value = (String)params.get(key);
      try {
        key = URLEncoder.encode(key, "UTF-8");
        value = URLEncoder.encode(value, "UTF-8");
      }
      catch( UnsupportedEncodingException e )
      {
        throw new MarmaladeExecutionException(
          "Error encoding URL parameter using UTF-8.", e
        );
      }
      
      finishedUrl.append(key).append("=").append(value);
      if(it.hasNext()){
        finishedUrl.append("&");
      }
    }
    
    String result = finishedUrl.toString();
    if(result.endsWith("?")){result = result.substring(0, result.length()-1);}
    else if(result.endsWith("&")) {result = result.substring(0, result.length()-1);}
    
    try{
      context.setVariable(var, new URL(result));
    }
    catch(MalformedURLException e){
      throw new MarmaladeExecutionException(
        "Error forming URL instance from resultant url: " + result, e
      );
    }
  }

  protected void doReset() {
    params.clear();
  }
  
  public void addParam(String key, String value){
    params.put(key, value);
  }

}
