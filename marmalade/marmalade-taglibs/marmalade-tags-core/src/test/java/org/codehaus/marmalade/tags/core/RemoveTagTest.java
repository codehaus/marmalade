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
/* Created on Apr 14, 2004 */
package org.codehaus.marmalade.tags.core;

import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.DefaultAttributes;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.MissingAttributeException;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class RemoveTagTest
    extends TestCase
{
    public void testShouldRequireVarAttribute() throws MarmaladeExecutionException
    {
        MarmaladeTagInfo ti = new MarmaladeTagInfo();

        RemoveTag tag = new RemoveTag();

        tag.setTagInfo( ti );
        tag.setAttributes( new DefaultAttributes() );

        try
        {
            tag.execute( new DefaultContext() );
            fail( "should fail because of missing var attribute" );
        }
        catch ( MissingAttributeException e )
        {
            // should snag on missing var attribute
        }
    }

    public void testShouldRemoveExistingVariableFromContext() throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes();

        attrs.addAttribute( "", "", "var", "testKey" );

        DefaultAttributes tagAttrs = new DefaultAttributes( attrs );

        MarmaladeTagInfo ti = new MarmaladeTagInfo();

        RemoveTag tag = new RemoveTag();

        tag.setTagInfo( ti );
        tag.setAttributes( tagAttrs );

        DefaultContext ctx = new DefaultContext();

        ctx.setVariable( "testKey", "value" );

        assertEquals( "Can't verify successful setVariable() operation.", "value", ctx.getVariable( "testKey", null ) );
        tag.execute( ctx );
        assertNull( "Variable \'testKey\' should no longer be in context.", ctx.getVariable( "testKey", null ) );
    }
}