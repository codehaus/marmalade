/* Created on Apr 13, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import junit.framework.TestCase;

import org.codehaus.marmalade.modelbuilder.DefaultRawAttributes;
import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.IllegalParentException;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.jstl.core.ChooseTag;
import org.codehaus.marmalade.tags.jstl.core.WhenTag;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.Mock;


/**
 * @author jdcasey
 */
public class OtherwiseTagTest extends TestCase{

  public void testShouldFailWithMissingChooseParent() throws MarmaladeExecutionException {
    MarmaladeTagInfo ti = new MarmaladeTagInfo();
    ti.setAttributes(new DefaultRawAttributes());
    
    OtherwiseTag tag = new OtherwiseTag(ti);
    
    MarmaladeTagInfo flagTI = new MarmaladeTagInfo();
    flagTI.setAttributes(new DefaultRawAttributes());
    
    FlagChildTestTag flag = new FlagChildTestTag(flagTI);
    
    tag.addChild(flag);
    flag.setParent(tag);
    
    try {
      tag.execute(new DefaultContext());
      fail("Tag should fail due to missing ChooseTag parent.");
    }
    catch(IllegalParentException e) {
    }
  }

  public void testShouldExecuteWhenLoneChildInChooseParent() throws MarmaladeExecutionException{
    MarmaladeTagInfo ti = new MarmaladeTagInfo();
    ti.setAttributes(new DefaultRawAttributes());
    
    OtherwiseTag tag = new OtherwiseTag(ti);
    
    MarmaladeTagInfo parentTI = new MarmaladeTagInfo();
    parentTI.setAttributes(new DefaultRawAttributes());
    
    ChooseTag parent = new ChooseTag(parentTI);
    
    tag.setParent(parent);
    parent.addChild(tag);
    
    MarmaladeTagInfo flagTI = new MarmaladeTagInfo();
    flagTI.setAttributes(new DefaultRawAttributes());
    
    FlagChildTestTag flag = new FlagChildTestTag(flagTI);
    
    tag.addChild(flag);
    flag.setParent(tag);
    
    tag.execute(new DefaultContext());
    assertTrue("Child tag should have fired.", flag.fired());
  }

}
