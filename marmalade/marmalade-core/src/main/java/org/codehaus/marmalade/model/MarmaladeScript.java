/* Created on Apr 12, 2004 */
package org.codehaus.marmalade.model;

import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;



/**
 * @author jdcasey
 */
public class MarmaladeScript{
  
  private String location;
  private MarmaladeTag root;

  public MarmaladeScript(String scriptLocation, MarmaladeTag root){
    this.location = scriptLocation;
    this.root = root;
  }

  public String getLocation(){
    return location;
  }
  
  public void execute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    root.execute(context);
  }
}
