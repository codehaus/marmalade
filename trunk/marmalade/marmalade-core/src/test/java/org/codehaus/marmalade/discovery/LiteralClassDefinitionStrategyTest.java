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

import org.codehaus.marmalade.discovery.test.TestTaglibWithCorrectConstructor;
import org.codehaus.marmalade.metamodel.MarmaladeTagLibrary;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class LiteralClassDefinitionStrategyTest
    extends TestCase
    implements TaglibDefinitionStrategyTestTemplate
{
    public void testShouldResolveTestTagLibrary()
    {
        LiteralResolutionStrategy strat = new LiteralResolutionStrategy();
        MarmaladeTagLibrary taglib = strat
            .resolve( "marmalade", "org.codehaus.marmalade.discovery.test.TestTaglibWithCorrectConstructor", Thread
                .currentThread().getContextClassLoader() );

        assertNotNull( taglib );
        assertEquals( TestTaglibWithCorrectConstructor.class, taglib.getClass() );
    }

    public void testShouldReturnNullWithNonExistentTagLibrary()
    {
        LiteralResolutionStrategy strat = new LiteralResolutionStrategy();
        MarmaladeTagLibrary taglib = strat
            .resolve( "marmalade", "org.codehaus.marmalade.lb.discovery.tags.mytags.NonExistentTaglib", Thread
                .currentThread().getContextClassLoader() );

        assertNull( taglib );
    }

    public void testShouldReturnNullWithNonEmptyConstructorTagLibrary()
    {
        LiteralResolutionStrategy strat = new LiteralResolutionStrategy();
        MarmaladeTagLibrary taglib = strat
            .resolve( "marmalade", "org.codehaus.marmalade.lb.discovery.test.NonEmptyConstructorTaglib", Thread
                .currentThread().getContextClassLoader() );

        assertNull( taglib );
    }

    public void testShouldReturnNullWithNonTagLibraryClass()
    {
        LiteralResolutionStrategy strat = new LiteralResolutionStrategy();
        MarmaladeTagLibrary taglib = strat.resolve( "marmalade",
                                                    "org.codehaus.marmalade.lb.discovery.test.NonTaglibClass", Thread
                                                        .currentThread().getContextClassLoader() );

        assertNull( taglib );
    }
}