/* Created on Apr 12, 2004 */
package org.codehaus.marmalade.metamodel.strategy.tld.tags;

import org.codehaus.tagalog.AbstractTag;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;


/**
 * @author jdcasey
 */
public class ShortnameTag extends AbstractTag{

  private StringBuffer shortname = new StringBuffer();

  public ShortnameTag(){
  }

  public Object end(String elementName) throws TagException, TagalogParseException{
    TaglibTag parent = (TaglibTag)getParent();
    parent.setShortname(shortname.toString());
    
    return null;
  }

  public void text(char[] characters, int start, int length)
  throws TagException, TagalogParseException
  {
    String add = String.valueOf(characters, start, length);
    shortname.append(add.trim());
  }

  public boolean recycle(){
    shortname.setLength(0);
    return true;
  }
}
