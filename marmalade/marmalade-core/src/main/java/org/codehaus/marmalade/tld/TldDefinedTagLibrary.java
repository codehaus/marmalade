/* Created on Apr 12, 2004 */
package org.codehaus.marmalade.tld;

import org.codehaus.marmalade.generics.AbstractMarmaladeTagLibrary;


/**
 * @author jdcasey
 */
public class TldDefinedTagLibrary extends AbstractMarmaladeTagLibrary{
  
  private String shortName;
  private String tldVersion;

  public TldDefinedTagLibrary(){
  }

  public void setShortname(String shortName){
    this.shortName = shortName;
  }
  
  public void setTlibversion(String tlibversion) {
    this.tldVersion = tlibversion;
  }

  public void registerTag(String name, Class tagClass){
    super.registerTag(name, tagClass);
  }

  public String getShortName(){
    return shortName;
  }

  public String getTldVersion(){
    return tldVersion;
  }
}
