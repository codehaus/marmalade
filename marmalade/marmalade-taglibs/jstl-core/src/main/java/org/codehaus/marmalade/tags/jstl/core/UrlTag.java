/* Created on Apr 11, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;

import org.codehaus.marmalade.AbstractMarmaladeTag;
import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class UrlTag extends AbstractMarmaladeTag {
  
  public static final String VAR_ATTRIBUTE = "var";
  public static final String URL_ATTRIBUTE = "url";
  
  private Properties params = new Properties();

  public UrlTag() {
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException {
    String url = (String)requireTagAttribute(URL_ATTRIBUTE, String.class, context);
    String var = (String)requireTagAttribute(VAR_ATTRIBUTE, String.class, context);
    
    StringBuffer finishedUrl = new StringBuffer(url);
    if(finishedUrl.indexOf("?") < 0){finishedUrl.append("?");}
    for (Iterator it = params.keySet().iterator(); it.hasNext();) {
      String key = (String)it.next();
      String value = params.getProperty(key);
      
      finishedUrl.append(key).append("=").append(value);
      if(it.hasNext()){
        finishedUrl.append("&");
      }
    }
    
    String result = finishedUrl.toString();
    if(result.endsWith("?")){result = result.substring(0, result.length()-1);}
    
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
