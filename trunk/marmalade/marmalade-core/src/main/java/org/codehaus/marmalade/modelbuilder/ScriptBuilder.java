/* Created on May 18, 2004 */
package org.codehaus.marmalade.modelbuilder;

import org.codehaus.marmalade.model.MarmaladeScript;


/**
 * @author jdcasey
 */
public class ScriptBuilder{

  private String filename;
  private BuilderTag root;

  public ScriptBuilder(String filename, BuilderTag root){
    this.filename = filename;
    this.root = root;
  }
  
  public MarmaladeScript build() {
    return new MarmaladeScript(filename, root.build());
  }
  
}
