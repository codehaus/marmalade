
/*
 * Copyright 2001-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Copyright 2001-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
