/* Created on Apr 12, 2004 */
package org.codehaus.marmalade.metamodel.strategy.tld.tags;

import org.codehaus.tagalog.AbstractTagLibrary;

/**
 * @author jdcasey
 */
public class TldTagLibrary extends AbstractTagLibrary {
  
  public static final String NS_URL = "http://java.sun.com/j2ee/dtds/web-jsptaglibrary_1_1.dtd";
  public static final String TAGLIB_TAG = "taglib";
  public static final String TLIBVERSION_TAG = "tlibversion";
  public static final String SHORTNAME_TAG = "shortname";
  public static final String TAG_TAG = "tag";
  public static final String NAME_TAG = "name";
  public static final String TAGCLASS_TAG = "tagclass";

  public TldTagLibrary() {
    registerTag(TAGLIB_TAG, TaglibTag.class);
    registerTag(TLIBVERSION_TAG, TlibversionTag.class);
    registerTag(SHORTNAME_TAG, ShortnameTag.class);
    registerTag(TAG_TAG, TagTag.class);
    registerTag(NAME_TAG, NameTag.class);
    registerTag(TAGCLASS_TAG, TagclassTag.class);
  }
  
}
