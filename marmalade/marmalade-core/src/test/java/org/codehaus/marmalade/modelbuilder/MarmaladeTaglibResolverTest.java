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
/* Created on Apr 12, 2004 */
package org.codehaus.marmalade.modelbuilder;

import junit.framework.TestCase;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.metamodel.MarmaladeTaglibResolver;
import org.codehaus.marmalade.metamodel.strategy.TaglibDefinitionStrategy;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeTagLibrary;

/**
 * @author jdcasey
 */
public class MarmaladeTaglibResolverTest extends TestCase
{
    public void testShouldSucceedWithSingleSuccessfulResolver(  )
    {
        SuccessfulStrategy strategy = new SuccessfulStrategy(  );
        MarmaladeTaglibResolver resolver = new MarmaladeTaglibResolver( new TaglibDefinitionStrategy[]
                {
                    strategy
                } );

        MarmaladeTagLibrary taglib = resolver.resolve( "marmalade", "test" );

        assertNotNull( taglib );
    }

    public void testShouldSucceedWithDoubleResolverAndSuccessAtEnd(  )
    {
        UnsuccessfulStrategy strategy = new UnsuccessfulStrategy(  );
        SuccessfulStrategy strategy2 = new SuccessfulStrategy(  );
        MarmaladeTaglibResolver resolver = new MarmaladeTaglibResolver( new TaglibDefinitionStrategy[]
                {
                    strategy, strategy2
                } );

        MarmaladeTagLibrary taglib = resolver.resolve( "marmalade", "test" );

        assertNotNull( taglib );
    }

    public void testShouldSucceedWithDoubleResolverAndSuccessAtBeginning(  )
    {
        UnsuccessfulStrategy strategy2 = new UnsuccessfulStrategy(  );
        SuccessfulStrategy strategy = new SuccessfulStrategy(  );
        MarmaladeTaglibResolver resolver = new MarmaladeTaglibResolver( new TaglibDefinitionStrategy[]
                {
                    strategy, strategy2
                } );

        MarmaladeTagLibrary taglib = resolver.resolve( "marmalade", "test" );

        assertNotNull( taglib );
    }

    public void testShouldFailWithSingleUnsuccessfulResolver(  )
    {
        UnsuccessfulStrategy strategy = new UnsuccessfulStrategy(  );
        MarmaladeTaglibResolver resolver = new MarmaladeTaglibResolver( new TaglibDefinitionStrategy[]
                {
                    strategy
                } );

        MarmaladeTagLibrary taglib = resolver.resolve( "marmalade", "test" );

        assertNull( taglib );
    }

    public void testShouldFailWithDoubleUnsuccessfulResolver(  )
    {
        UnsuccessfulStrategy strategy = new UnsuccessfulStrategy(  );
        UnsuccessfulStrategy strategy2 = new UnsuccessfulStrategy(  );
        MarmaladeTaglibResolver resolver = new MarmaladeTaglibResolver( new TaglibDefinitionStrategy[]
                {
                    strategy, strategy2
                } );

        MarmaladeTagLibrary taglib = resolver.resolve( "marmalade", "test" );

        assertNull( taglib );
    }

    public static class Taglib implements MarmaladeTagLibrary
    {
        public Taglib(  )
        {
        }

        public MarmaladeTag createTag( MarmaladeTagInfo tagInfo )
        {
            return null;
        }
    }

    public static class UnsuccessfulStrategy implements TaglibDefinitionStrategy
    {
        UnsuccessfulStrategy(  )
        {
        }

        public MarmaladeTagLibrary resolve( String prefix, String taglib )
        {
            return null;
        }
    }

    public static class SuccessfulStrategy implements TaglibDefinitionStrategy
    {
        SuccessfulStrategy(  )
        {
        }

        public MarmaladeTagLibrary resolve( String prefix, String taglib )
        {
            return new Taglib(  );
        }
    }
}
