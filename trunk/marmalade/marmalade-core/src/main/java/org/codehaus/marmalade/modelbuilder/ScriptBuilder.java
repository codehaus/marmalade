/* Created on May 18, 2004 */
package org.codehaus.marmalade.modelbuilder;

import org.codehaus.marmalade.model.MarmaladeScript;


/**
 * @author jdcasey
 */
public class ScriptBuilder{

  private String filename;
  private MarmaladeTagBuilder root;

  public ScriptBuilder(String filename, MarmaladeTagBuilder root){
    this.filename = filename;
    this.root = root;
  }
  
  public MarmaladeScript build() throws MarmaladeModelBuilderException{
    return new MarmaladeScript(filename, root.build());
  }
  
}
