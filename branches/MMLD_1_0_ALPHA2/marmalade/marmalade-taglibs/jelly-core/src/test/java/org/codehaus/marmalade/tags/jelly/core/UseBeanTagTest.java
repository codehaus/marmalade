/*
 *
 * Copyright (c) 2004 John Dennis Casey
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 */
/* Created on Jun 23, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import junit.framework.TestCase;

import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.DefaultAttributes;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.MissingAttributeException;

/**
 * @author jdcasey
 */
public class UseBeanTagTest extends TestCase
{
    public void testShouldRequireClassAttribute(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( "", "", UseBeanTag.VAR_ATTRIBUTE, "var" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attributes );

        UseBeanTag tag = new UseBeanTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );

        try
        {
            tag.execute( new DefaultContext(  ) );
            fail( "should fail because of missing class attribute" );
        }
        catch ( MissingAttributeException e )
        {
            // should fail b/c of missing class attribute.
        }
    }

    public void testShouldRequireVarAttribute(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( "", "", UseBeanTag.CLASS_ATTRIBUTE,
            "java.util.List" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attributes );

        UseBeanTag tag = new UseBeanTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );

        try
        {
            tag.execute( new DefaultContext(  ) );
            fail( "should fail because of missing var attribute" );
        }
        catch ( MissingAttributeException e )
        {
            // should fail b/c of missing var attribute.
        }
    }

    public void testShouldRequireExistentBeanClass(  )
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( "", "", UseBeanTag.VAR_ATTRIBUTE, "var" );
        attributes.addAttribute( "", "", UseBeanTag.CLASS_ATTRIBUTE,
            "my.nonexistent.Class" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attributes );

        UseBeanTag tag = new UseBeanTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );

        try
        {
            tag.execute( new DefaultContext(  ) );
            fail( "should fail because of non-existent bean class" );
        }
        catch ( MarmaladeExecutionException e )
        {
        }
    }

    public void testShouldRequireEmptyConstructorInBeanClass(  )
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( "", "", UseBeanTag.VAR_ATTRIBUTE, "var" );
        attributes.addAttribute( "", "", UseBeanTag.CLASS_ATTRIBUTE,
            NonEmptyConstructorBean.class.getName(  ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attributes );

        UseBeanTag tag = new UseBeanTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );

        try
        {
            tag.execute( new DefaultContext(  ) );
            fail( "should fail because of non-empty bean constructor" );
        }
        catch ( MarmaladeExecutionException e )
        {
        }
    }

    public void testShouldSetPropertiesInBeanWithClassBeanSpec(  )
        throws MarmaladeExecutionException
    {
        String personName = "Joe";
        String personAge = "11";
        Boolean isDemocrat = Boolean.TRUE;

        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( "", "", UseBeanTag.VAR_ATTRIBUTE, "var" );
        attributes.addAttribute( "", "", UseBeanTag.CLASS_ATTRIBUTE, "${class}" );
        attributes.addAttribute( "", "", "name", personName );
        attributes.addAttribute( "", "", "age", "${age}" );
        attributes.addAttribute( "", "", "isDemocrat", "${democrat}" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attributes );

        UseBeanTag tag = new UseBeanTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );

        DefaultContext context = new DefaultContext(  );

        context.setVariable( "class", Person.class );
        context.setVariable( "age", personAge );
        context.setVariable( "democrat", isDemocrat );

        tag.execute( context );

        Person person = ( Person ) context.getVariable( "var", null );

        assertNotNull( person );
        assertEquals( personName, person.getName(  ) );
        assertEquals( personAge, person.getAge(  ) );
        assertEquals( isDemocrat, person.isDemocrat(  ) );
    }

    public void testShouldMakeTargetObjectAvailableToChildren(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( "", "", UseBeanTag.VAR_ATTRIBUTE, "var" );
        attributes.addAttribute( "", "", UseBeanTag.CLASS_ATTRIBUTE,
            Person.class.getName(  ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attributes );

        UseBeanTag tag = new UseBeanTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );

        TargetConsumerTestTag child = new TargetConsumerTestTag(  );

        child.setParent( tag );
        tag.addChild( child );

        DefaultContext context = new DefaultContext(  );

        tag.execute( context );

        Person person = ( Person ) context.getVariable( "var", null );

        assertNotNull( person );
        assertEquals( person, child.getTarget(  ) );
    }

    public static final class NonEmptyConstructorBean
    {
        public NonEmptyConstructorBean( String arg )
        {
        }
    }

    public static final class Person
    {
        private String name;
        private String age;
        private Boolean isDemocrat;

        public Person(  )
        {
        }

        public void setName( String name )
        {
            this.name = name;
        }

        public String getName(  )
        {
            return name;
        }

        public void setAge( String age )
        {
            this.age = age;
        }

        public String getAge(  )
        {
            return age;
        }

        public void setIsDemocrat( Boolean isDemocrat )
        {
            this.isDemocrat = isDemocrat;
        }

        public Boolean isDemocrat(  )
        {
            return isDemocrat;
        }
    }
}
