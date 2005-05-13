/* Created on Aug 24, 2004 */
package org.codehaus.marmalade.lb.metamodel;

import org.codehaus.marmalade.lb.model.LateBoundTagFactory;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.metamodel.TagInstantiationException;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.parsing.ParserHint;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

/**
 * @author jdcasey
 */
public class LateBoundTagLibraryTest
    extends MockObjectTestCase
{

    public void testShouldConstructWithNoParams() {
        LateBoundTagLibrary lib = new LateBoundTagLibrary();
    }
    
    public void testShouldRegisterTagFactoryAndCreateTag() throws TagInstantiationException {
        LateBoundTagLibrary lib = new LateBoundTagLibrary();
        
        Mock factoryMock = mock(LateBoundTagFactory.class);
        Mock tagMock = mock(MarmaladeTag.class);
        
        factoryMock.expects(atLeastOnce()).method("newTag").withNoArguments().will(returnValue(tagMock.proxy()));
        
        LateBoundTagFactory factory = (LateBoundTagFactory)factoryMock.proxy();
        lib.registerTag("test", new ParserHint().parseChildren(true), factory);
        
        MarmaladeTagInfo ti = new MarmaladeTagInfo();
        ti.setElement("test");
        
        MarmaladeTag tag = lib.createTag(ti);
        assertEquals("created tag should be the mock", tagMock.proxy(), tag);
        assertTrue("parser hint should signal parsing of children", lib.getParserHint("test").parseChildren());
    }
    
}
