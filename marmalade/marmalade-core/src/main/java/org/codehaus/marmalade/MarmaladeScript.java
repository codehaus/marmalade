/* Created on Apr 12, 2004 */
package org.codehaus.marmalade;

import java.io.File;


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
  
  public MarmaladeTag getRoot() {
    return root;
  }
  
  public void execute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    root.execute(context);
  }
}
