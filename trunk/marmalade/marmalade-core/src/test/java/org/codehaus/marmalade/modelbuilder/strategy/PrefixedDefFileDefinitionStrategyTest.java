
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

import org.codehaus.marmalade.metamodel.strategy.PrefixedDefFileDefinitionStrategy;
import org.codehaus.marmalade.model.MarmaladeTagLibrary;
import org.codehaus.marmalade.modelbuilder.strategy.test.TestTaglibWithCorrectConstructor;

/**
 * @author jdcasey
 */
public class PrefixedDefFileDefinitionStrategyTest extends TestCase
    implements TaglibDefinitionStrategyTestTemplate
{
    public void testShouldResolveTestTagLibrary(  )
    {
        PrefixedDefFileDefinitionStrategy strat = new PrefixedDefFileDefinitionStrategy(  );
        MarmaladeTagLibrary taglib = strat.resolve( "marmalade", "testlib" );

        assertNotNull( taglib );
        assertEquals( TestTaglibWithCorrectConstructor.class,
            taglib.getClass(  ) );
    }

    public void testShouldReturnNullWithNonExistentTagLibrary(  )
    {
        PrefixedDefFileDefinitionStrategy strat = new PrefixedDefFileDefinitionStrategy(  );
        MarmaladeTagLibrary taglib = strat.resolve( "marmalade",
                "testlib-with-nonexistent-class" );

        assertNull( taglib );
    }

    public void testShouldReturnNullWithNonEmptyConstructorTagLibrary(  )
    {
        PrefixedDefFileDefinitionStrategy strat = new PrefixedDefFileDefinitionStrategy(  );
        MarmaladeTagLibrary taglib = strat.resolve( "marmalade",
                "testlib-with-nonempty-constructor" );

        assertNull( taglib );
    }

    public void testShouldReturnNullWithNonTagLibraryClass(  )
    {
        PrefixedDefFileDefinitionStrategy strat = new PrefixedDefFileDefinitionStrategy(  );
        MarmaladeTagLibrary taglib = strat.resolve( "marmalade",
                "testlib-with-nontaglib" );

        assertNull( taglib );
    }
}
