/* Created on Apr 12, 2004 */
package org.codehaus.marmalade.tld.tags;

import org.codehaus.tagalog.AbstractTag;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;


/**
 * @author jdcasey
 */
public class TlibversionTag extends AbstractTag{
  
  private StringBuffer version = new StringBuffer();

  public TlibversionTag(){
  }

  public Object end(String elementName) throws TagException, TagalogParseException{
    TaglibTag parent = (TaglibTag)getParent();
    parent.setTlibversion(version.toString());
    
    return null;
  }

  public void text(char[] characters, int start, int length)
  throws TagException, TagalogParseException
  {
    String add = String.valueOf(characters, start, length);
    version.append(add.trim());
  }
  
  public boolean recycle(){
    version.setLength(0);
    return true;
  }
}
