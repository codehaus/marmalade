/* Created on Jun 23, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

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
public class UseBeanTagTest extends TestCase {
    
    public void testShouldRequireClassAttribute() throws MarmaladeExecutionException {
        DefaultRawAttributes attributes = new DefaultRawAttributes();
        attributes.addAttribute("", UseBeanTag.VAR_ATTRIBUTE, "var");
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        
        UseBeanTag tag = new UseBeanTag(mti);
        
        try {
            tag.execute(new DefaultContext());
            fail("should fail because of missing class attribute");
        }
        catch(MissingAttributeException e) {
            // should fail b/c of missing class attribute.
        }
    }

    public void testShouldRequireVarAttribute() throws MarmaladeExecutionException {
        DefaultRawAttributes attributes = new DefaultRawAttributes();
        attributes.addAttribute("", UseBeanTag.CLASS_ATTRIBUTE, "java.util.List");
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        
        UseBeanTag tag = new UseBeanTag(mti);
        
        try {
            tag.execute(new DefaultContext());
            fail("should fail because of missing var attribute");
        }
        catch(MissingAttributeException e) {
            // should fail b/c of missing var attribute.
        }
    }

    public void testShouldRequireExistentBeanClass() {
        DefaultRawAttributes attributes = new DefaultRawAttributes();
        attributes.addAttribute("", UseBeanTag.VAR_ATTRIBUTE, "var");
        attributes.addAttribute("", UseBeanTag.CLASS_ATTRIBUTE, "my.nonexistent.Class");
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        
        UseBeanTag tag = new UseBeanTag(mti);
        
        try {
            tag.execute(new DefaultContext());
            fail("should fail because of non-existent bean class");
        }
        catch(MarmaladeExecutionException e) {
        }
    }

    public void testShouldRequireEmptyConstructorInBeanClass() {
        DefaultRawAttributes attributes = new DefaultRawAttributes();
        attributes.addAttribute("", UseBeanTag.VAR_ATTRIBUTE, "var");
        attributes.addAttribute("", UseBeanTag.CLASS_ATTRIBUTE, NonEmptyConstructorBean.class.getName());
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        
        UseBeanTag tag = new UseBeanTag(mti);
        
        try {
            tag.execute(new DefaultContext());
            fail("should fail because of non-empty bean constructor");
        }
        catch(MarmaladeExecutionException e) {
        }
    }

    public void testShouldSetPropertiesInBeanWithClassBeanSpec() throws MarmaladeExecutionException {
        String personName = "Joe";
        Integer personAge = new Integer(11);
        boolean isDemocrat = true;
        
        DefaultRawAttributes attributes = new DefaultRawAttributes();
        attributes.addAttribute("", UseBeanTag.VAR_ATTRIBUTE, "var");
        attributes.addAttribute("", UseBeanTag.CLASS_ATTRIBUTE, "#class");
        attributes.addAttribute("", "name", personName);
        attributes.addAttribute("", "age", "#age");
        attributes.addAttribute("", "isDemocrat", "#democrat");
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());
        
        UseBeanTag tag = new UseBeanTag(mti);
        
        DefaultContext context = new DefaultContext();
        context.setVariable("class", Person.class);
        context.setVariable("age", personAge);
        context.setVariable("democrat", Boolean.valueOf(isDemocrat));
        
        tag.execute(context);
        
        Person person = (Person)context.getVariable("var", null);
        assertNotNull(person);
        assertEquals(personName, person.getName());
        assertEquals(personAge, person.getAge());
        assertEquals(isDemocrat, person.isDemocrat());
    }

    public void testShouldSetPropertiesInBeanWithStringBeanSpec() throws MarmaladeExecutionException {
        String personName = "Joe";
        Integer personAge = new Integer(11);
        boolean isDemocrat = true;
        
        DefaultRawAttributes attributes = new DefaultRawAttributes();
        attributes.addAttribute("", UseBeanTag.VAR_ATTRIBUTE, "var");
        attributes.addAttribute("", UseBeanTag.CLASS_ATTRIBUTE, Person.class.getName());
        attributes.addAttribute("", "name", personName);
        attributes.addAttribute("", "age", "#age");
        attributes.addAttribute("", "isDemocrat", "#democrat");
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());
        
        UseBeanTag tag = new UseBeanTag(mti);
        
        DefaultContext context = new DefaultContext();
        context.setVariable("age", personAge);
        context.setVariable("democrat", Boolean.valueOf(isDemocrat));
        
        tag.execute(context);
        
        Person person = (Person)context.getVariable("var", null);
        assertNotNull(person);
        assertEquals(personName, person.getName());
        assertEquals(personAge, person.getAge());
        assertEquals(isDemocrat, person.isDemocrat());
    }

    public static final class NonEmptyConstructorBean
    {
        public NonEmptyConstructorBean(String arg) {
        }
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
}
