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

import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.model.DefaultAttributes;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class SetPropertiesTagTest extends TestCase
{
    public void testShouldSetPropertiesInObjectFromAttribute(  )
        throws MarmaladeExecutionException
    {
        String personName = "Joe";
        String personAge = "11";
        Boolean isDemocrat = Boolean.TRUE;

        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( "", "", SetPropertiesTag.OBJECT_ATTRIBUTE,
            "${object}" );
        attributes.addAttribute( "", "", "name", personName );
        attributes.addAttribute( "", "", "age", "${age}" );
        attributes.addAttribute( "", "", "isDemocrat", "${democrat}" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attributes );

        SetPropertiesTag tag = new SetPropertiesTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );

        DefaultContext context = new DefaultContext(  );
        Person person = new Person(  );

        context.setVariable( "object", person );
        context.setVariable( "age", personAge );
        context.setVariable( "democrat", isDemocrat );

        tag.execute( context );

        assertEquals( personName, person.getName(  ) );
        assertEquals( personAge, person.getAge(  ) );
        assertEquals( isDemocrat, person.isDemocrat(  ) );
    }

    public void testShouldSetPropertiesInObjectFromTargetObjectOwnerAncestor(  )
        throws MarmaladeExecutionException
    {
        String personName = "Joe";
        String personAge = "11";
        Boolean isDemocrat = Boolean.TRUE;

        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( "", "", "name", personName );
        attributes.addAttribute( "", "", "age", "${age}" );
        attributes.addAttribute( "", "", "isDemocrat", "${democrat}" );

        Person person = new Person(  );
        TestTargetObjectOwnerTag parent = new TestTargetObjectOwnerTag( person );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attributes );

        SetPropertiesTag tag = new SetPropertiesTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );

        tag.setParent( parent );
        parent.addChild( tag );

        DefaultContext context = new DefaultContext(  );

        context.setVariable( "age", personAge );
        context.setVariable( "democrat", isDemocrat );

        tag.execute( context );

        assertNotNull( person );
        assertEquals( personName, person.getName(  ) );
        assertEquals( personAge, person.getAge(  ) );
        assertEquals( isDemocrat, person.isDemocrat(  ) );
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

    public static final class TestTargetObjectOwnerTag
        extends AbstractMarmaladeTag implements TargetObjectOwner
    {
        private Object target;

        TestTargetObjectOwnerTag( Object target )
        {
            this.target = target;
        }

        public Object getTarget(  )
        {
            return target;
        }
    }
}
