/* Created on Jun 23, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.el.ognl.OgnlExpressionEvaluator;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.MissingAttributeException;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class SetPropertiesTagTest extends TestCase {
    
    public void testShouldSetPropertiesInObjectFromAttribute() throws MarmaladeExecutionException {
        String personName = "Joe";
        Integer personAge = new Integer(11);
        boolean isDemocrat = true;
        
        DefaultRawAttributes attributes = new DefaultRawAttributes();
        attributes.addAttribute("", SetPropertiesTag.OBJECT_ATTRIBUTE, "#object");
        attributes.addAttribute("", "name", personName);
        attributes.addAttribute("", "age", "#age");
        attributes.addAttribute("", "isDemocrat", "#democrat");
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());
        
        SetPropertiesTag tag = new SetPropertiesTag(mti);
        
        DefaultContext context = new DefaultContext();
        Person person = new Person();
        
        context.setVariable("object", person);
        context.setVariable("age", personAge);
        context.setVariable("democrat", Boolean.valueOf(isDemocrat));
        
        tag.execute(context);
        
        assertEquals(personName, person.getName());
        assertEquals(personAge, person.getAge());
        assertEquals(isDemocrat, person.isDemocrat());
    }

    public void testShouldSetPropertiesInObjectFromTargetObjectOwnerAncestor() throws MarmaladeExecutionException {
        String personName = "Joe";
        Integer personAge = new Integer(11);
        boolean isDemocrat = true;
        
        DefaultRawAttributes attributes = new DefaultRawAttributes();
        attributes.addAttribute("", "name", personName);
        attributes.addAttribute("", "age", "#age");
        attributes.addAttribute("", "isDemocrat", "#democrat");
        
        Person person = new Person();
        TestTargetObjectOwnerTag parent = new TestTargetObjectOwnerTag(new MarmaladeTagInfo(), person);
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());
        
        SetPropertiesTag tag = new SetPropertiesTag(mti);
        
        tag.setParent(parent);
        parent.addChild(tag);
        
        DefaultContext context = new DefaultContext();
        context.setVariable("age", personAge);
        context.setVariable("democrat", Boolean.valueOf(isDemocrat));
        
        tag.execute(context);
        
        assertNotNull(person);
        assertEquals(personName, person.getName());
        assertEquals(personAge, person.getAge());
        assertEquals(isDemocrat, person.isDemocrat());
    }

    public static final class Person
    {
        private String name;
        private Integer age;
        private boolean isDemocrat;

        public Person() {
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getName() {
            return name;
        }
        
        public void setAge(Integer age) {
            this.age = age;
        }
        
        public Integer getAge() {
            return age;
        }
        
        public void setIsDemocrat(boolean isDemocrat) {
            this.isDemocrat = isDemocrat;
        }
        
        public boolean isDemocrat() {
            return isDemocrat;
        }
    }
    
    public static final class TestTargetObjectOwnerTag extends AbstractMarmaladeTag implements TargetObjectOwner{
        
        private Object target;

        TestTargetObjectOwnerTag(MarmaladeTagInfo tagInfo, Object target) {
            super(tagInfo);
            this.target = target;
        }

        public Object getTarget() {
            return target;
        }
        
    }
}
