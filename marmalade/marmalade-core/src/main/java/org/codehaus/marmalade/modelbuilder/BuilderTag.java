/* Created on May 18, 2004 */
package org.codehaus.marmalade.modelbuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeTagLibrary;
import org.codehaus.tagalog.AbstractTag;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.Tag;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.codehaus.tagalog.TagalogParser;


public class BuilderTag extends AbstractTag implements MarmaladeTagBuilder{
  
  private MarmaladeTagInfo tagInfo = new MarmaladeTagInfo();
  private MarmaladeTagLibrary tagLibrary;

  public BuilderTag(String scheme, String taglib, MarmaladeTagLibrary tagLibrary){
    tagInfo.setScheme(scheme);
    tagInfo.setTaglib(taglib);
    this.tagLibrary = tagLibrary;
  }
  
  public MarmaladeTagLibrary getTagLibrary() {
    return tagLibrary;
  }
  
  public MarmaladeTagInfo getTagInfo() {
    return tagInfo;
  }
  
  public MarmaladeTag build() {
    return tagLibrary.createTag(tagInfo);
  }

  public void setParent(Tag parent){
    if(parent instanceof MarmaladeTagBuilder) {
      tagInfo.setParent((MarmaladeTagBuilder)parent);
    }
    
    super.setParent(parent);
  }

  public void begin(String elementName, Attributes attributes)
  throws TagException, TagalogParseException
  {
    DefaultRawAttributes attrs = new DefaultRawAttributes();
    for(int i=0; i<attributes.getAttributeCount(); i++) {
      attrs.addAttribute(new DefaultRawAttribute(
        attributes.getNamespaceUri(i), 
        attributes.getName(i), 
        attributes.getValue(i)
      ));
    }
    
    tagInfo.setElement(elementName);
    tagInfo.setAttributes(attrs);
  }

  public synchronized void text(char[] characters, int start, int length)
  throws TagException, TagalogParseException
  {
    tagInfo.appendText(characters, start, length);
  }

  public void child(Object child) throws TagException, TagalogParseException{
    if(!(child instanceof MarmaladeTagBuilder)) {
      throw new TagalogParseException(
        "Marmamalde Tag Builder implementations can only contain MarmaladeTagBuilder children."
      );
    }
    else {
      tagInfo.addChild((MarmaladeTagBuilder)child);
    }
  }

  public Object end(String elementName) throws TagException, TagalogParseException{
    return this;
  }

  public boolean recycle(){
    return false;
  }

}
