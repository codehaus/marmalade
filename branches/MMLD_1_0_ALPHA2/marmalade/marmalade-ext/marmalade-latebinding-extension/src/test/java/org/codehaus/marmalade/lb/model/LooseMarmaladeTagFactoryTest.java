/* Created on Aug 24, 2004 */
package org.codehaus.marmalade.lb.model;

import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import org.codehaus.marmalade.el.BareBonesExpressionEvaluator;
import org.codehaus.marmalade.metamodel.TagInstantiationException;
import org.codehaus.marmalade.model.MarmaladeTag;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class LooseMarmaladeTagFactoryTest
    extends TestCase
{
    
    public void testShouldConstructWithTagClassConstructorArgsPropertiesAndEL() throws TagInstantiationException {
        LooseMarmaladeTagFactory factory = new LooseMarmaladeTagFactory(TestTag.class, new LinkedList(), new HashMap(), new BareBonesExpressionEvaluator());
        MarmaladeTag tag = factory.newTag();
        
        assertTrue("should be a TestTag", (tag instanceof TestTag));
        
        TestTag testTag = (TestTag)tag;
        assertNull("str arg should be null", testTag.getStringArg());
        assertNull("int arg should be null", testTag.getIntegerArg());
        assertNull("str prop should be null", testTag.getStringProperty());
        assertNull("int prop should be null", testTag.getIntegerProperty());
    }

    public void testShouldConstructWithTagClassConstructorArgsPropertiesAndNullEL() throws TagInstantiationException {
        LooseMarmaladeTagFactory factory = new LooseMarmaladeTagFactory(TestTag.class, new LinkedList(), new HashMap(), null);
        MarmaladeTag tag = factory.newTag();
        
        assertTrue("should be a TestTag", (tag instanceof TestTag));
        
        TestTag testTag = (TestTag)tag;
        assertNull("str arg should be null", testTag.getStringArg());
        assertNull("int arg should be null", testTag.getIntegerArg());
        assertNull("str prop should be null", testTag.getStringProperty());
        assertNull("int prop should be null", testTag.getIntegerProperty());
    }

    public void testShouldConstructAndRetrieveNonEmptyArgs() throws TagInstantiationException {
        List constructorArgs = new LinkedList();
        constructorArgs.add("test");
        constructorArgs.add(new Integer(1));
        
        LooseMarmaladeTagFactory factory = new LooseMarmaladeTagFactory(TestTag.class, constructorArgs, new HashMap(), null);
        MarmaladeTag tag = factory.newTag();
        
        assertTrue("should be a TestTag", (tag instanceof TestTag));
        
        TestTag testTag = (TestTag)tag;
        assertEquals("str arg should NOT be null", "test", testTag.getStringArg());
        assertEquals("int arg should NOT be null", new Integer(1), testTag.getIntegerArg());
        assertNull("str prop should be null", testTag.getStringProperty());
        assertNull("int prop should be null", testTag.getIntegerProperty());
    }

    public void testShouldFailToConstructWhenArgsListDoesNotMatchConstructors() throws TagInstantiationException {
        List constructorArgs = new LinkedList();
        constructorArgs.add("test");
        
        LooseMarmaladeTagFactory factory = new LooseMarmaladeTagFactory(TestTag.class, constructorArgs, new HashMap(), null);
        try
        {
            MarmaladeTag tag = factory.newTag();
            fail("should fail with no matching constructor");
        }
        catch ( TagInstantiationException e )
        {
            // should fail with no matching constructor
        }
    }

    public void testShouldFailWithPropertiesAndNullEL() {
        Map properties = new TreeMap();
        properties.put("stringProperty", "test");
        properties.put("integerProperty", new Integer(1));
        
        LooseMarmaladeTagFactory factory = new LooseMarmaladeTagFactory(TestTag.class, Collections.EMPTY_LIST, properties, null);
        try
        {
            MarmaladeTag tag = factory.newTag();
            fail("Should snag on null EL");
        }
        catch ( TagInstantiationException e )
        {
            // should snag on missing EL
        }
        
    }

    public void testShouldConstructAndRetrieveNonEmptyProperties() throws TagInstantiationException {
        Map properties = new TreeMap();
        properties.put("stringProperty", "test");
        properties.put("integerProperty", new Integer(1));
        
        LooseMarmaladeTagFactory factory = new LooseMarmaladeTagFactory(TestTag.class, Collections.EMPTY_LIST, properties, new BareBonesExpressionEvaluator());
        MarmaladeTag tag = factory.newTag();
        
        assertTrue("should be a TestTag", (tag instanceof TestTag));
        
        TestTag testTag = (TestTag)tag;
        assertNull("str arg should be null", testTag.getStringArg());
        assertNull("int arg should be null", testTag.getIntegerArg());
        assertEquals("str prop should NOT be null", "test", testTag.getStringProperty());
        assertEquals("int prop should NOT be null", new Integer(1), testTag.getIntegerProperty());
    }

}
