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
    private String defaultPrefix;

    public MarmaladeTaglibResolver( TaglibDefinitionStrategy[] strategies )
    {
        this.strategies = strategies;
    }
    
    public void setDefaultPrefix(String defaultPrefix) {
        this.defaultPrefix = defaultPrefix;
    }
    
    public String getDefaultPrefix() {
        return defaultPrefix;
    }

    public MarmaladeTagLibrary resolve( String prefix, String taglib )
    {
        MarmaladeTagLibrary tlib = null;

        String realPrefix = prefix;
        if(realPrefix == null || realPrefix.trim().length() < 1) {
            realPrefix = defaultPrefix;
        }
        
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
