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
/* Created on May 20, 2004 */
package org.codehaus.marmalade.modelbuilder;

import junit.framework.TestCase;

import org.codehaus.marmalade.metamodel.DefaultRawAttribute;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MetaAttribute;

import java.util.Iterator;

/**
 * @author jdcasey
 */
public class DefaultRawAttributesTest extends TestCase
{
    public void testShouldIterateOverTwoAttributes(  )
    {
        DefaultRawAttributes ra = new DefaultRawAttributes(  );

        ra.addAttribute( new DefaultRawAttribute( "testNS", "testAttr",
                "testVal" ) );
        ra.addAttribute( new DefaultRawAttribute( "testNS2", "testAttr2",
                "testVal2" ) );

        int counter = 0;

        for ( Iterator it = ra.iterator(  ); it.hasNext(  ); )
        {
            MetaAttribute attribute = ( MetaAttribute ) it.next(  );

            counter++;
        }

        assertEquals( 2, counter );
    }

    public void testShouldStoreRetrieveAttributeNamespace(  )
    {
        DefaultRawAttributes ra = new DefaultRawAttributes(  );

        ra.addAttribute( new DefaultRawAttribute( "testNS", "testAttr",
                "testVal" ) );

        assertEquals( "testNS", ra.getNamespace( "testAttr" ) );
    }

    public void testShouldStoreRetrieveAttributeValue(  )
    {
        DefaultRawAttributes ra = new DefaultRawAttributes(  );

        ra.addAttribute( new DefaultRawAttribute( "testNS", "testAttr",
                "testVal" ) );

        assertEquals( "testVal", ra.getValue( "testAttr" ) );
    }
}
