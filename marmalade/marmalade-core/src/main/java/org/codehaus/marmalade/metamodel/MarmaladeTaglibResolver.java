
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

/* Created on May 18, 2004 */
package org.codehaus.marmalade.metamodel;

import org.codehaus.marmalade.metamodel.strategy.LiteralClassDefinitionStrategy;
import org.codehaus.marmalade.metamodel.strategy.PassThroughTaglibDefinitionStrategy;
import org.codehaus.marmalade.metamodel.strategy.PrefixedDefFileDefinitionStrategy;
import org.codehaus.marmalade.metamodel.strategy.PrefixedTldDefinitionStrategy;
import org.codehaus.marmalade.metamodel.strategy.TaglibDefinitionStrategy;
import org.codehaus.marmalade.model.MarmaladeTagLibrary;

/**
 * @author jdcasey
 */
public class MarmaladeTaglibResolver
{
    public static final TaglibDefinitionStrategy[] DEFAULT_STRATEGY_CHAIN = 
        {
            new LiteralClassDefinitionStrategy(  ),
            new PrefixedTldDefinitionStrategy(  ),
            new PrefixedDefFileDefinitionStrategy(  ),
            new PassThroughTaglibDefinitionStrategy(  )
        };
    private TaglibDefinitionStrategy[] strategies;

    public MarmaladeTaglibResolver( TaglibDefinitionStrategy[] strategies )
    {
        this.strategies = strategies;
    }

    public MarmaladeTagLibrary resolve( String prefix, String taglib )
    {
        MarmaladeTagLibrary tlib = null;

        for ( int i = 0; i < strategies.length; i++ )
        {
            TaglibDefinitionStrategy strategy = strategies[i];

            tlib = strategy.resolve( prefix, taglib );

            if ( tlib != null )
            {
                break;
            }
        }

        return tlib;
    }
}
