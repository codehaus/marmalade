/* Created on May 25, 2004 */
package org.codehaus.marmalade.modelbuilder;


/**
 * @author jdcasey
 */
public class MarmaladeTagLibraryNotFoundException extends MarmaladeModelBuilderException{

  private String taglib;
  private String scheme;

  public MarmaladeTagLibraryNotFoundException(String scheme, String taglib){
    super("marmalade taglib not found");
    
    this.scheme = scheme;
    this.taglib = taglib;
  }

  public String getScheme(){
    return scheme;
  }

  public String getTaglib(){
    return taglib;
  }
}
