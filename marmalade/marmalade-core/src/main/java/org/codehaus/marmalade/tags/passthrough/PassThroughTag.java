/* Created on May 25, 2004 */
package org.codehaus.marmalade.tags.passthrough;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;
import org.codehaus.marmalade.modelbuilder.ModelBuilderAttribute;
import org.codehaus.marmalade.modelbuilder.ModelBuilderAttributes;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;


/**
 * @author jdcasey
 */
public class PassThroughTag extends AbstractMarmaladeTag{

  public PassThroughTag(MarmaladeTagInfo tagInfo){
    super(tagInfo);
  }

  protected void doExecute(MarmaladeExecutionContext context) 
  throws MarmaladeExecutionException
  {
    PrintWriter out = context.getOutWriter();
    MarmaladeTagInfo tagInfo = getTagInfo();
    
    out.print("<" + tagInfo.getElement());
    out.print(" xmlns=\"" + tagInfo.getScheme() + ":" + tagInfo.getTaglib() + "\"");
    
    ModelBuilderAttributes attributes = tagInfo.getAttributes();
    for(Iterator it = attributes.iterator(); it.hasNext();){
      ModelBuilderAttribute attribute = (ModelBuilderAttribute)it.next();
      
      out.print(" ");
      
      String ns = attribute.getNamespace();
      if(ns != null && ns.length() > 0) {
        out.print(ns + ":");
      }
      out.print(attribute.getName());
      out.print("=\"" + attribute.getValue() + "\"");
    }
    
    List children = children();
    if(children.isEmpty()) {
      out.println("/>");
    }
    else {
      out.println(">");
      processChildren(context);
      out.println("\n</" + tagInfo.getElement() + ">");
    }
  }

}
