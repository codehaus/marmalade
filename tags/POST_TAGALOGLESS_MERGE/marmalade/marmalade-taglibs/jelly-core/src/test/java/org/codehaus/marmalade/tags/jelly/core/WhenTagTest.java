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
package org.codehaus.marmalade.tags.jelly.core;

import junit.framework.TestCase;

import org.codehaus.marmalade.el.ognl.OgnlExpressionEvaluator;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.DefaultAttributes;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class WhenTagTest extends TestCase
{
    public void testDoExecute_NoParent(  )
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( "", "", WhenTag.TEST_ATTRIBUTE, "true" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        OgnlExpressionEvaluator el = new OgnlExpressionEvaluator();
        
        DefaultAttributes tagAttrs = new DefaultAttributes(attributes);
        tagAttrs.setExpressionEvaluator(el);
        
        WhenTag tag = new WhenTag(  );
        tag.setTagInfo(mti);
        tag.setAttributes(tagAttrs);
        tag.setExpressionEvaluator(el);

        FlagChildTestTag flag = new FlagChildTestTag(  );

        flag.setParent( tag );
        tag.addChild( flag );

        DefaultContext context = new DefaultContext(  );

        try
        {
            tag.execute( context );
            fail( "Tag should fail due to missing ChooseTag parent." );
        }
        catch ( MarmaladeExecutionException e )
        {
        }
    }

    public void testDoExecute_WithParent(  )
        throws MarmaladeExecutionException
    {
        ChooseTag parent = new ChooseTag(  );

        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( "", "", WhenTag.TEST_ATTRIBUTE, "true" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        OgnlExpressionEvaluator el = new OgnlExpressionEvaluator();
        
        DefaultAttributes tagAttrs = new DefaultAttributes(attributes);
        tagAttrs.setExpressionEvaluator(el);
        
        WhenTag tag = new WhenTag(  );
        tag.setTagInfo(mti);
        tag.setAttributes(tagAttrs);
        tag.setExpressionEvaluator(el);

        parent.addChild( tag );
        tag.setParent( parent );

        FlagChildTestTag flag = new FlagChildTestTag(  );

        tag.addChild( flag );
        flag.setParent( tag );

        DefaultContext context = new DefaultContext(  );

        tag.execute( context );
        assertTrue( "Child tag should have fired.", flag.fired(  ) );
    }
}
