/* Created on Apr 12, 2004 */
package org.codehaus.marmalade.tld.tags;

import org.codehaus.tagalog.AbstractTag;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;


/**
 * @author jdcasey
 */
public class TagTag extends AbstractTag{
  
  private String name;
  private Class tagClass;

  public TagTag(){
  }
  
  void setName(String name) {
    this.name = name;
  }
  
  void setTagClass(Class tagClass) {
    this.tagClass = tagClass;
  }

  public Object end(String elementName) throws TagException, TagalogParseException{
    TaglibTag parent = (TaglibTag)getParent();
    parent.addTag(name, tagClass);
    
    return null;
  }

  public boolean recycle(){
    this.name = null;
    this.tagClass = null;
    return true;
  }
}
