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
/* Created on Apr 11, 2004 */
package org.codehaus.marmalade.parsetime;

import org.codehaus.marmalade.metamodel.BuilderTagLibrary;
import org.codehaus.marmalade.metamodel.MarmaladeTaglibResolver;
import org.codehaus.tagalog.FallbackTagLibraryResolver;
import org.codehaus.tagalog.PrefixTagLibraryResolver;
import org.codehaus.tagalog.TagLibrary;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author jdcasey
 */
public class TagalogTaglibResolver implements PrefixTagLibraryResolver,
    FallbackTagLibraryResolver
{
    public static final String DEFAULT_MARMALADE_TAGLIB_PREFIX = "marmalade";
    private String prefix = DEFAULT_MARMALADE_TAGLIB_PREFIX;
    private Map mappedTaglibs = new TreeMap(  );
    private MarmaladeTaglibResolver marmaladeResolver;
    private boolean separatePrefixBeforeResolve = false;

    public TagalogTaglibResolver( String prefix,
        MarmaladeTaglibResolver marmaladeResolver,
        boolean separatePrefixBeforeResolve )
    {
        this.prefix = prefix;
        this.marmaladeResolver = marmaladeResolver;
        this.separatePrefixBeforeResolve = separatePrefixBeforeResolve;
    }

    public String uriPrefix(  )
    {
        return prefix;
    }

    public TagLibrary resolve( String tlib )
    {
        TagLibrary taglib = ( TagLibrary ) mappedTaglibs.get( tlib );

        String prefix = null;
        String tlibName = null;

        if ( separatePrefixBeforeResolve )
        {
            int colonIdx = tlib.indexOf( ":" );

            if ( colonIdx > 0 )
            {
                prefix = tlib.substring( 0, colonIdx );
                tlibName = tlib.substring( colonIdx + 1 );
            }
        }

        if ( prefix == null )
        {
            prefix = uriPrefix(  );
            tlibName = tlib;
        }

        if ( taglib == null )
        {
            taglib = new BuilderTagLibrary( prefix, tlibName, marmaladeResolver );
        }

        return taglib;
    }

    public void addTagLibrary( String uri, TagLibrary tagLibrary )
    {
        mappedTaglibs.put( uri, tagLibrary );
    }
}
