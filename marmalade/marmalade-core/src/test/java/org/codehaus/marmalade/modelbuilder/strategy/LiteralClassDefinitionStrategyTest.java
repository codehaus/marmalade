
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

/* Created on May 20, 2004 */
package org.codehaus.marmalade.modelbuilder.strategy;

import junit.framework.TestCase;

import org.codehaus.marmalade.metamodel.strategy.LiteralClassDefinitionStrategy;
import org.codehaus.marmalade.model.MarmaladeTagLibrary;
import org.codehaus.marmalade.modelbuilder.strategy.test.TestTaglibWithCorrectConstructor;

/**
 * @author jdcasey
 */
public class LiteralClassDefinitionStrategyTest extends TestCase
    implements TaglibDefinitionStrategyTestTemplate
{
    public void testShouldResolveTestTagLibrary(  )
    {
        LiteralClassDefinitionStrategy strat = new LiteralClassDefinitionStrategy(  );
        MarmaladeTagLibrary taglib = strat.resolve( "marmalade",
                "org.codehaus.marmalade.modelbuilder.strategy.test.TestTaglibWithCorrectConstructor" );

        assertNotNull( taglib );
        assertEquals( TestTaglibWithCorrectConstructor.class,
            taglib.getClass(  ) );
    }

    public void testShouldReturnNullWithNonExistentTagLibrary(  )
    {
        LiteralClassDefinitionStrategy strat = new LiteralClassDefinitionStrategy(  );
        MarmaladeTagLibrary taglib = strat.resolve( "marmalade",
                "org.codehaus.marmalade.tags.mytags.NonExistentTaglib" );

        assertNull( taglib );
    }

    public void testShouldReturnNullWithNonEmptyConstructorTagLibrary(  )
    {
        LiteralClassDefinitionStrategy strat = new LiteralClassDefinitionStrategy(  );
        MarmaladeTagLibrary taglib = strat.resolve( "marmalade",
                "org.codehaus.marmalade.modelbuilder.strategy.test.NonEmptyConstructorTaglib" );

        assertNull( taglib );
    }

    public void testShouldReturnNullWithNonTagLibraryClass(  )
    {
        LiteralClassDefinitionStrategy strat = new LiteralClassDefinitionStrategy(  );
        MarmaladeTagLibrary taglib = strat.resolve( "marmalade",
                "org.codehaus.marmalade.modelbuilder.strategy.test.NonTaglibClass" );

        assertNull( taglib );
    }
}
