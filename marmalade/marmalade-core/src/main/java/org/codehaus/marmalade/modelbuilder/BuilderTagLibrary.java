/* Created on May 18, 2004 */
package org.codehaus.marmalade.modelbuilder;

import org.codehaus.marmalade.model.MarmaladeTagLibrary;
import org.codehaus.tagalog.Tag;
import org.codehaus.tagalog.TagLibrary;


/**
 * @author jdcasey
 */
public class BuilderTagLibrary implements TagLibrary{

  private String scheme;
  private String taglib;
  private MarmaladeTaglibResolver taglibResolver;

  public BuilderTagLibrary(String scheme, String taglib, 
                           MarmaladeTaglibResolver taglibResolver)
  {
    this.scheme = scheme;
    this.taglib = taglib;
    this.taglibResolver = taglibResolver;
  }

  public Tag getTag(String element){
    MarmaladeTagLibrary tlib = taglibResolver.resolve(scheme, taglib);
    return new BuilderTag(scheme, taglib, tlib);
  }

  public void releaseTag(String element, Tag tag){
  }

}
