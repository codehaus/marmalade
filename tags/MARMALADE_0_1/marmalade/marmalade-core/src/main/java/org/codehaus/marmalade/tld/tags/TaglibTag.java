/* Created on Apr 12, 2004 */
package org.codehaus.marmalade.tld.tags;

import org.codehaus.marmalade.tld.TldDefinedTagLibrary;
import org.codehaus.tagalog.AbstractTag;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;


/**
 * @author jdcasey
 */
public class TaglibTag extends AbstractTag{
  
  private TldDefinedTagLibrary taglib = new TldDefinedTagLibrary();

  public TaglibTag(){
  }
  
  void setShortname(String shortName) {
    taglib.setShortname(shortName);
  }
  
  void setTlibversion(String tlibversion) {
    taglib.setTlibversion(tlibversion);
  }
  
  void addTag(String name, Class tagClass) {
    taglib.registerTag(name, tagClass);
  }
  
  public Object end(String elementName) throws TagException, TagalogParseException{
    return taglib;
  }

  public boolean recycle(){
    taglib = new TldDefinedTagLibrary();
    return true;
  }
}
