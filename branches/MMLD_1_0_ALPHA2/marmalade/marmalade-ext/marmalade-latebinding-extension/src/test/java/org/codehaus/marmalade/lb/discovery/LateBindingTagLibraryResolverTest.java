/* Created on Aug 24, 2004 */
package org.codehaus.marmalade.lb.discovery;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.metamodel.MarmaladeTagLibrary;
import org.codehaus.marmalade.metamodel.TagInstantiationException;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

/**
 * @author jdcasey
 */
public class LateBindingTagLibraryResolverTest
    extends MockObjectTestCase
{

    public void testShouldConstructWithNoParams() {
        LateBindingTagLibraryResolver resolver = new LateBindingTagLibraryResolver();
    }
    
    public void testShouldHandleLBBindTaglibWithoutRegistering() {
        LateBindingTagLibraryResolver resolver = new LateBindingTagLibraryResolver();
        assertTrue("should handle lb:bind", resolver.handles("lb", "bind"));
    }
    
    public void testShouldRegisterAndHandleTaglib() {
        LateBindingTagLibraryResolver resolver = new LateBindingTagLibraryResolver();
        Mock tlibMock = mock(MarmaladeTagLibrary.class);
        MarmaladeTagLibrary tlib = (MarmaladeTagLibrary)tlibMock.proxy();
        resolver.registerTagLibrary("lb", "test", tlib);
        
        assertTrue("should handle registered lb:test taglib", resolver.handles("lb", "test"));
    }
    
    public void testShouldReisterTagLibAndCreateTag() throws TagInstantiationException {
        LateBindingTagLibraryResolver resolver = new LateBindingTagLibraryResolver();
        Mock tlibMock = mock(MarmaladeTagLibrary.class);
        
        Mock tagMock = mock(MarmaladeTag.class);
        
        tlibMock.expects(atLeastOnce()).method("createTag").with(isA(MarmaladeTagInfo.class)).will(returnValue(tagMock.proxy()));
        
        MarmaladeTagLibrary tlib = (MarmaladeTagLibrary)tlibMock.proxy();
        
        resolver.registerTagLibrary("lb", "test", tlib);
        
        MarmaladeTagInfo ti = new MarmaladeTagInfo();
        ti.setScheme("lb");
        ti.setTaglib("test");
        ti.setElement("test");
        
        MarmaladeTag tag = resolver.createTag(ti);
        assertNotNull("created tag should not be null", tag);
        assertEquals("created tag should be the mock's proxy", tagMock.proxy(), tag);
    }
    
}
