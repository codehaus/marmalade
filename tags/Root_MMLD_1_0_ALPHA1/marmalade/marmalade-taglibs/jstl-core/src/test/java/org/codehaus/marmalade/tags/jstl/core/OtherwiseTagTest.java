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

import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.DefaultAttributes;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.IllegalParentException;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class OtherwiseTagTest extends TestCase
{
    public void testShouldFailWithMissingChooseParent(  )
        throws MarmaladeExecutionException
    {
        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        OtherwiseTag tag = new OtherwiseTag(  );
        tag.setTagInfo(ti);

        MarmaladeTagInfo flagTI = new MarmaladeTagInfo(  );

        FlagChildTestTag flag = new FlagChildTestTag(  );
        flag.setTagInfo(flagTI);

        tag.addChild( flag );
        flag.setParent( tag );

        try
        {
            tag.execute( new DefaultContext(  ) );
            fail( "Tag should fail due to missing ChooseTag parent." );
        }
        catch ( IllegalParentException e )
        {
        }
    }

    public void testShouldExecuteWhenLoneChildInChooseParent(  )
        throws MarmaladeExecutionException
    {
        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        OtherwiseTag tag = new OtherwiseTag(  );
        tag.setTagInfo(ti);

        MarmaladeTagInfo parentTI = new MarmaladeTagInfo(  );

        ChooseTag parent = new ChooseTag(  );
        parent.setTagInfo(parentTI);

        tag.setParent( parent );
        parent.addChild( tag );

        MarmaladeTagInfo flagTI = new MarmaladeTagInfo(  );

        FlagChildTestTag flag = new FlagChildTestTag(  );
        flag.setTagInfo(flagTI);

        tag.addChild( flag );
        flag.setParent( tag );

        tag.execute( new DefaultContext(  ) );
        assertTrue( "Child tag should have fired.", flag.fired(  ) );
    }
}
