/* Created on Jun 23, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.codehaus.marmalade.el.ognl.OgnlExpressionEvaluator;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.MissingAttributeException;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class UseListTagTest extends TestCase {
    
    public void testShouldRequireVarAttribute() throws MarmaladeExecutionException {
        DefaultRawAttributes attributes = new DefaultRawAttributes();
        attributes.addAttribute("", UseListTag.ITEMS_ATTRIBUTE, "#items");
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        
        UseListTag tag = new UseListTag(mti);
        
        try {
            tag.execute(new DefaultContext());
            fail("Should require var attribute.");
        }
        catch(MissingAttributeException e) {
            // should snag on missing var attribute.
        }
    }

    public void testShouldRequireItemsAttribute() throws MarmaladeExecutionException {
        DefaultRawAttributes attributes = new DefaultRawAttributes();
        attributes.addAttribute("", UseListTag.VAR_ATTRIBUTE, "var");
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        
        UseListTag tag = new UseListTag(mti);
        
        try {
            tag.execute(new DefaultContext());
            fail("Should require items attribute.");
        }
        catch(MissingAttributeException e) {
            // should snag on missing items attribute.
        }
    }

    public void testShouldCreateListWithStringItemBoundTo_varKey() throws MarmaladeExecutionException {
        DefaultRawAttributes attributes = new DefaultRawAttributes();
        attributes.addAttribute("", UseListTag.ITEMS_ATTRIBUTE, "testItem");
        attributes.addAttribute("", UseListTag.VAR_ATTRIBUTE, "varKey");
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        
        UseListTag tag = new UseListTag(mti);
        
        DefaultContext context = new DefaultContext();
        tag.execute(context);
        
        List list = (List)context.getVariable("varKey", null);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("testItem", list.get(0));
    }

    public void testShouldCreateLinkedListWithStringItemBoundTo_varKey() throws MarmaladeExecutionException {
        DefaultRawAttributes attributes = new DefaultRawAttributes();
        attributes.addAttribute("", UseListTag.ITEMS_ATTRIBUTE, "testItem");
        attributes.addAttribute("", UseListTag.VAR_ATTRIBUTE, "varKey");
        attributes.addAttribute("", UseListTag.CLASS_ATTRIBUTE, "java.util.LinkedList");
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        
        UseListTag tag = new UseListTag(mti);
        
        DefaultContext context = new DefaultContext();
        tag.execute(context);
        
        List list = (List)context.getVariable("varKey", null);
        assertNotNull(list);
        assertEquals(LinkedList.class, list.getClass());
        assertEquals(1, list.size());
        assertEquals("testItem", list.get(0));
    }

    public void testShouldCreateListWithItemsListContentsBoundTo_varKey() throws MarmaladeExecutionException {
        List items = new ArrayList();
        items.add("item1");
        items.add("item2");
        items.add("item3");
        
        DefaultRawAttributes attributes = new DefaultRawAttributes();
        attributes.addAttribute("", UseListTag.ITEMS_ATTRIBUTE, "#items");
        attributes.addAttribute("", UseListTag.VAR_ATTRIBUTE, "varKey");
        
        OgnlExpressionEvaluator ognlEl = new OgnlExpressionEvaluator();
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.setExpressionEvaluator(ognlEl);
        
        UseListTag tag = new UseListTag(mti);
        
        DefaultContext context = new DefaultContext();
        context.setVariable("items", items);
        
        tag.execute(context);
        
        List list = (List)context.getVariable("varKey", null);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("item1", list.get(0));
        assertEquals("item2", list.get(1));
        assertEquals("item3", list.get(2));
    }

    public void testShouldCreateListWithItemsSetContentsBoundTo_varKey() throws MarmaladeExecutionException {
        Set items = new HashSet();
        items.add("item1");
        items.add("item2");
        items.add("item3");
        
        DefaultRawAttributes attributes = new DefaultRawAttributes();
        attributes.addAttribute("", UseListTag.ITEMS_ATTRIBUTE, "#items");
        attributes.addAttribute("", UseListTag.VAR_ATTRIBUTE, "varKey");
        
        OgnlExpressionEvaluator ognlEl = new OgnlExpressionEvaluator();
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.setExpressionEvaluator(ognlEl);
        
        UseListTag tag = new UseListTag(mti);
        
        DefaultContext context = new DefaultContext();
        context.setVariable("items", items);
        
        tag.execute(context);
        
        List list = (List)context.getVariable("varKey", null);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertTrue(list.contains("item1"));
        assertTrue(list.contains("item2"));
        assertTrue(list.contains("item3"));
    }

}
