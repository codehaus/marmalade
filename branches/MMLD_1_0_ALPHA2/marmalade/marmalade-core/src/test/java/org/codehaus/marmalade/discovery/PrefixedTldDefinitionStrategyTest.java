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
package org.codehaus.marmalade.discovery;

import junit.framework.TestCase;

import org.codehaus.marmalade.discovery.PrefixedTldResolutionStrategy;
import org.codehaus.marmalade.discovery.tld.TldDefinedTagLibrary;
import org.codehaus.marmalade.metamodel.MarmaladeTagLibrary;

/**
 * @author jdcasey
 */
public class PrefixedTldDefinitionStrategyTest
    extends TestCase
{
    public void testShouldResolveTestTagLibrary()
    {
        PrefixedTldResolutionStrategy strat = new PrefixedTldResolutionStrategy();
        MarmaladeTagLibrary taglib = strat.resolve( "marmalade", "testlib" );

        assertNotNull( "taglib is null", taglib );
        assertEquals( TldDefinedTagLibrary.class, taglib.getClass() );
    }

    public void testShouldReturnNullWithNonExistentTLD()
    {
        PrefixedTldResolutionStrategy strat = new PrefixedTldResolutionStrategy();
        MarmaladeTagLibrary taglib = strat.resolve( "marmalade", "testlib-missing" );

        assertNull( taglib );
    }
}