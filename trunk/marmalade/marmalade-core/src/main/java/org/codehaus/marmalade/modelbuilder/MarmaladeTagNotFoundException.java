/* Created on May 25, 2004 */
package org.codehaus.marmalade.modelbuilder;


/**
 * @author jdcasey
 */
public class MarmaladeTagNotFoundException extends MarmaladeModelBuilderException{

  private String scheme;
  private String taglib;
  private String element;

  public MarmaladeTagNotFoundException(String scheme, String taglib, String element){
    super("Tag \'" + element + "\' in XMLNS \'" + scheme + ":" + taglib + "\' cannot be found.");
    
    this.scheme = scheme;
    this.taglib = taglib;
    this.element = element;
  }

  public String getElement(){
    return element;
  }

  public String getScheme(){
    return scheme;
  }

  public String getTaglib(){
    return taglib;
  }
}
