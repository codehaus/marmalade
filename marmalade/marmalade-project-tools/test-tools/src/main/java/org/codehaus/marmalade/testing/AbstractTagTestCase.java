/* Created on Apr 13, 2004 */
package org.codehaus.marmalade.testing;

import java.util.Iterator;
import java.util.Map;

import org.codehaus.marmalade.generics.AbstractMarmaladeTag;
import org.codehaus.tagalog.Attributes;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;


/**
 * @author jdcasey
 */
public abstract class AbstractTagTestCase extends MockObjectTestCase{
  
  protected Mock attributesEmpty() {
    Mock attrMock = mock(Attributes.class);
    attrMock.expects(once())
            .method("getValue")
            .with(eq(AbstractMarmaladeTag.MARMALADE_EL_ATTRIBUTE))
            .will(returnValue(null));
    
    attrMock.expects(once())
            .method("getAttributeCount")
            .withNoArguments()
            .will(returnValue(0));
    
    return attrMock;
  }

  protected Mock attributesEmptyWithEl(String el) {
    Mock attrMock = mock(Attributes.class);
    attrMock.expects(once())
            .method("getValue")
            .with(eq(AbstractMarmaladeTag.MARMALADE_EL_ATTRIBUTE))
            .will(returnValue(el));
    
    attrMock.expects(once())
            .method("getAttributeCount")
            .withNoArguments()
            .will(returnValue(0));
    
    return attrMock;
  }

  protected Mock attributesWithSingleAttribute(String name, String value) {
    Mock attrMock = mock(Attributes.class);
    attrMock.expects(once())
            .method("getValue")
            .with(eq(AbstractMarmaladeTag.MARMALADE_EL_ATTRIBUTE))
            .will(returnValue(null));
    
    attrMock.expects(once())
            .method("getAttributeCount")
            .withNoArguments()
            .will(returnValue(1));
    
    attrMock.expects(once())
            .method("getValue")
            .with(eq(0))
            .will(returnValue(value));
    attrMock.expects(once())
            .method("getNamespaceUri")
            .with(eq(0))
            .will(returnValue(null));
    attrMock.expects(once())
            .method("getName")
            .with(eq(0))
            .will(returnValue(name));
    
    return attrMock;
  }

  protected Mock attributesWithSingleAttributeWithEl(String el, String name, String value) {
    Mock attrMock = mock(Attributes.class);
    attrMock.expects(once())
            .method("getValue")
            .with(eq(AbstractMarmaladeTag.MARMALADE_EL_ATTRIBUTE))
            .will(returnValue(el));
    
    attrMock.expects(once())
            .method("getAttributeCount")
            .withNoArguments()
            .will(returnValue(1));
    
    attrMock.expects(once())
            .method("getValue")
            .with(eq(0))
            .will(returnValue(value));
    attrMock.expects(once())
            .method("getNamespaceUri")
            .with(eq(0))
            .will(returnValue(null));
    attrMock.expects(once())
            .method("getName")
            .with(eq(0))
            .will(returnValue(name));
    
    return attrMock;
  }

  protected Mock attributesFromMap(Map attrs) {
    Mock attrMock = mock(Attributes.class);
    attrMock.expects(once())
            .method("getValue")
            .with(eq(AbstractMarmaladeTag.MARMALADE_EL_ATTRIBUTE))
            .will(returnValue(null));
    
    attrMock.expects(once())
            .method("getAttributeCount")
            .withNoArguments()
            .will(returnValue(attrs.size()));
    
    int i=0;
    for(Iterator it = attrs.keySet().iterator(); it.hasNext();){
      String key = (String)it.next();
      String value = (String)attrs.get(key);
      attrMock.expects(once())
              .method("getValue")
              .with(eq(i))
              .will(returnValue(value));
      attrMock.expects(once())
              .method("getName")
              .with(eq(i))
              .will(returnValue(key));
      i++;
    }
    attrMock.expects(atLeastOnce())
            .method("getNamespaceUri")
            .withAnyArguments()
            .will(returnValue(null));
    
    return attrMock;
  }

  protected Mock attributesFromMapWithEl(String el, Map attrs) {
    Mock attrMock = mock(Attributes.class);
    attrMock.expects(once())
            .method("getValue")
            .with(eq(AbstractMarmaladeTag.MARMALADE_EL_ATTRIBUTE))
            .will(returnValue(el));
    
    attrMock.expects(once())
            .method("getAttributeCount")
            .withNoArguments()
            .will(returnValue(attrs.size()));
    
    int i=0;
    for(Iterator it = attrs.keySet().iterator(); it.hasNext();){
      String key = (String)it.next();
      String value = (String)attrs.get(key);
      attrMock.expects(once())
              .method("getValue")
              .with(eq(i))
              .will(returnValue(value));
      attrMock.expects(once())
              .method("getName")
              .with(eq(i))
              .will(returnValue(key));
      i++;
    }
    attrMock.expects(atLeastOnce())
            .method("getNamespaceUri")
            .withAnyArguments()
            .will(returnValue(null));
    
    return attrMock;
  }

}
