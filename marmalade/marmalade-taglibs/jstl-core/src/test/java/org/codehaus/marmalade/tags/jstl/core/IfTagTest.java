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
/* Created on Apr 13, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import junit.framework.TestCase;

import org.codehaus.marmalade.metamodel.DefaultRawAttribute;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.DefaultAttributes;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.MissingAttributeException;

/**
 * @author jdcasey
 */
public class IfTagTest extends TestCase
{
    public void testShouldRequireTestAttribute(  )
        throws MarmaladeExecutionException
    {
        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        IfTag tag = new IfTag(  );
        tag.setTagInfo(ti);
        tag.setAttributes(new DefaultAttributes());

        try
        {
            tag.execute( new DefaultContext(  ) );
            fail( "should fail because of missing test attribute" );
        }
        catch ( MissingAttributeException e )
        {
            // should snag on missing test attribute
        }
    }

    public void testShouldExecuteChildrenWhenTestResultIsTrue(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", "", "test", "true" );
        
        DefaultAttributes tagAttrs = new DefaultAttributes(attrs);

        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        IfTag tag = new IfTag(  );
        tag.setTagInfo(ti);
        tag.setAttributes(tagAttrs);

        MarmaladeTagInfo flagTI = new MarmaladeTagInfo(  );

        FlagChildTestTag flag = new FlagChildTestTag(  );
        flag.setTagInfo(flagTI);

        tag.addChild( flag );
        flag.setParent( tag );

        tag.execute( new DefaultContext(  ) );

        assertTrue( "Child tag should have fired.", flag.fired(  ) );
    }

    public void testShouldNotExecuteChildrenWhenTestResultIsFalse(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", "", "test", "false" );

        DefaultAttributes tagAttrs = new DefaultAttributes(attrs);

        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        IfTag tag = new IfTag(  );
        tag.setTagInfo(ti);
        tag.setAttributes(tagAttrs);

        MarmaladeTagInfo flagTI = new MarmaladeTagInfo(  );

        FlagChildTestTag flag = new FlagChildTestTag(  );
        flag.setTagInfo(flagTI);

        tag.addChild( flag );
        flag.setParent( tag );

        tag.execute( new DefaultContext(  ) );

        assertFalse( "Child tag should NOT have fired.", flag.fired(  ) );
    }
}
