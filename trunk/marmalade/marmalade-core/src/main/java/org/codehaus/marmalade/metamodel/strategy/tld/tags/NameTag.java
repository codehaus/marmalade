/* Created on Apr 12, 2004 */
package org.codehaus.marmalade.metamodel.strategy.tld.tags;

import org.codehaus.tagalog.AbstractTag;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;


/**
 * @author jdcasey
 */
public class NameTag extends AbstractTag{

  private StringBuffer name = new StringBuffer();

  public NameTag(){
  }

  public Object end(String elementName) throws TagException, TagalogParseException{
    TagTag parent = (TagTag)getParent();
    parent.setName(name.toString());
    
    return null;
  }

  public void text(char[] characters, int start, int length)
  throws TagException, TagalogParseException
  {
    String add = String.valueOf(characters, start, length);
    name.append(add.trim());
  }

  public boolean recycle(){
    name.setLength(0);
    return true;
  }
}
