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

import org.codehaus.marmalade.discovery.LiteralResolutionStrategy;
import org.codehaus.marmalade.discovery.PassThroughResolutionStrategy;
import org.codehaus.marmalade.discovery.PrefixedDefFileResolutionStrategy;
import org.codehaus.marmalade.discovery.PrefixedTldResolutionStrategy;
import org.codehaus.marmalade.discovery.TaglibResolutionStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author jdcasey
 */
public class MarmaladeTaglibResolver
{
    public static final List DEFAULT_STRATEGY_CHAIN = Arrays.asList( new TaglibResolutionStrategy[] {
        new LiteralResolutionStrategy(), new PrefixedTldResolutionStrategy(), new PrefixedDefFileResolutionStrategy(),
        new PassThroughResolutionStrategy() } );

    private List strategies;

    private String defaultPrefix;

    private MarmaladeTagLibrary defaultTagLibrary;

    public MarmaladeTaglibResolver()
    {
        this.strategies = new LinkedList();
    }

    public boolean hasStrategies()
    {
        return !strategies.isEmpty();
    }

    public void addTaglibDefinitionStrategy( TaglibResolutionStrategy strategy )
    {
        if ( !strategies.contains( strategy ) )
        {
            strategies.add( strategy );
        }
    }

    public void addTaglibDefinitionStrategies( Collection strategies )
    {
        for ( Iterator it = strategies.iterator(); it.hasNext(); )
        {
            TaglibResolutionStrategy strategy = (TaglibResolutionStrategy) it.next();

            if ( !strategies.contains( strategy ) )
            {
                strategies.add( strategy );
            }
        }
    }
    
    public void setTaglibDefinitionStrategies(Collection strategies)
    {
        this.strategies = new ArrayList(strategies);
    }

    public void setDefaultTagLibrary( MarmaladeTagLibrary taglib )
    {
        this.defaultTagLibrary = taglib;
    }

    public MarmaladeTagLibrary getDefaultTagLibrary()
    {
        return defaultTagLibrary;
    }

    public MarmaladeTagLibrary resolve( String prefix, String taglib )
    {
        MarmaladeTagLibrary tlib = null;

        // if both the prefix and the taglib are empty, use the default if it's set.
        if ( defaultTagLibrary != null && (prefix == null || prefix.trim().length() < 1)
            && (taglib == null || taglib.trim().length() < 1) )
        {
            // use the default taglib.
            tlib = defaultTagLibrary;
        }
        else
        {
            for ( Iterator it = strategies.iterator(); it.hasNext(); )
            {
                TaglibResolutionStrategy strategy = (TaglibResolutionStrategy) it.next();

                tlib = strategy.resolve( prefix, taglib );

                if ( tlib != null )
                {
                    break;
                }
            }
        }

        return tlib;
    }
}