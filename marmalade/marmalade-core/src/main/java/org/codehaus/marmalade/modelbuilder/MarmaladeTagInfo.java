/* Created on May 18, 2004 */
package org.codehaus.marmalade.modelbuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.codehaus.marmalade.el.ExpressionEvaluator;



/**
 * @author jdcasey
 */
public class MarmaladeTagInfo{
  
  private String scheme;
  private String taglib;
  private String element;
  private ModelBuilderAttributes attributes;
  private StringBuffer text;
  private MarmaladeTagBuilder parent;
  private List children = new ArrayList();
  private ExpressionEvaluator el;

  public MarmaladeTagInfo(){
  }

  public ModelBuilderAttributes getAttributes(){
    return attributes;
  }

  public void setAttributes(ModelBuilderAttributes attributes){
    this.attributes = attributes;
  }

  public List getChildren(){
    return Collections.unmodifiableList(children);
  }
  
  public void addChild(MarmaladeTagBuilder child) {
    children.add(child);
  }
  
  public String getElement(){
    return element;
  }

  public void setElement(String element){
    this.element = element;
  }

  public MarmaladeTagBuilder getParent(){
    return parent;
  }

  public void setParent(MarmaladeTagBuilder parent){
    this.parent = parent;
  }

  public String getScheme(){
    return scheme;
  }

  public void setScheme(String scheme){
    this.scheme = scheme;
  }

  public String getTaglib(){
    return taglib;
  }

  public void setTaglib(String taglib){
    this.taglib = taglib;
  }

  public String getText(){
    if(text == null) {return null;}
    else {return text.toString();}
  }
  
  public synchronized void appendText(char[] c, int start, int length) {
    if(text == null) {text = new StringBuffer();}
    
    text.append(c, start, length);
  }
  
  public void setExpressionEvaluator(ExpressionEvaluator el) {
    this.el = el;
  }

  public ExpressionEvaluator getExpressionEvaluator(){
    return el;
  }
}
