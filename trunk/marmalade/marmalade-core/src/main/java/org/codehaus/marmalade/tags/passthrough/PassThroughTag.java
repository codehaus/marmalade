/* Created on Apr 20, 2004 */
package org.codehaus.marmalade.tags.passthrough;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.MarmaladeTag;
import org.codehaus.marmalade.abstractions.AbstractMarmaladeTag;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;


/**
 * @author jdcasey
 */
public final class PassThroughTag extends AbstractMarmaladeTag{
  
  private String elementHeader;
  private String elementFooter;
  private StringBuffer currentBuffer = new StringBuffer();
  private List childrenWithPrecedingText = new ArrayList();
  
  private boolean hasBody = false;

  public PassThroughTag(){
  }

  // ----------------------------------- EXECUTE PHASE METHODS ----------------------------------//
  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    context.getOutWriter().print(elementHeader);
    processChildren(context);
    
    if(currentBuffer.length() > 0) {
      context.getOutWriter().print(currentBuffer.toString());
    }
    
    context.getOutWriter().print(elementFooter);
  }

  protected boolean alwaysProcessChildren(){
    return false;
  }
  
  public void processChildren(MarmaladeExecutionContext context) throws MarmaladeExecutionException
  {
    for(Iterator it = childrenWithPrecedingText.iterator(); it.hasNext();){
      TextAndChild textChild = (TextAndChild)it.next();
      context.getOutWriter().print(textChild.getText());
      textChild.getChild().execute(context);
    }
  }

  // ----------------------------------- PARSE PHASE METHODS -----------------------------------//
  public void begin(String element, Attributes parseAttrs)
  throws TagException, TagalogParseException
  {
    StringBuffer headerBuffer = new StringBuffer();
    headerBuffer.append('<').append(element);
    
    int attrLen = parseAttrs.getAttributeCount();
    for(int i=0; i<attrLen; i++) {
      headerBuffer.append(' ');
      
      String namespace = parseAttrs.getNamespaceUri(i);
      if(namespace != null && namespace.length() > 0) {headerBuffer.append(namespace).append(':');}
      
      String name = parseAttrs.getName(i);
      headerBuffer.append(name).append("=\"");
      
      String value = parseAttrs.getValue(i);
      if(value == null || value.length() < 1) {headerBuffer.append("true");}
      else {headerBuffer.append(value);}
      headerBuffer.append('\"');
    }
    elementHeader = headerBuffer.toString();
    
    super.begin(element, parseAttrs);
  }

  public void child(Object child) throws TagException, TagalogParseException{
    if(!hasBody) {
      hasBody = true;
      elementHeader += ">";
    }
    
    childrenWithPrecedingText.add(new TextAndChild(currentBuffer.toString(), (MarmaladeTag)child));
    currentBuffer.setLength(0);
  }

  public Object end(String element) throws TagException, TagalogParseException{
    if(hasBody) {
      elementFooter = "</" + element + ">";
    }
    else {
      elementFooter = "/>";
    }
    
    return super.end(element);
  }

  public void text(char[] ch, int start, int length) throws TagException, TagalogParseException{
    if(!hasBody) {
      hasBody = true;
      elementHeader += ">";
    }
    
    currentBuffer.append(ch, start, length);
  }
  
  private static final class TextAndChild{
    private String text;
    private MarmaladeTag child;
    
    TextAndChild(String text, MarmaladeTag child){
      this.text = text;
      this.child = child;
    }
    
    String getText() {return text;}
    
    MarmaladeTag getChild() {return child;}
  }

}
