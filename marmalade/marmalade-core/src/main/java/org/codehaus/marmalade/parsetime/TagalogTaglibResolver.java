
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

    public TagalogTaglibResolver( String prefix,
        MarmaladeTaglibResolver marmaladeResolver )
    {
        this.prefix = prefix;
        this.marmaladeResolver = marmaladeResolver;
    }

    public String uriPrefix(  )
    {
        return prefix;
    }

    public TagLibrary resolve( String tlib )
    {
        TagLibrary taglib = ( TagLibrary ) mappedTaglibs.get( tlib );

        if ( taglib == null )
        {
            taglib = new BuilderTagLibrary( uriPrefix(  ), tlib,
                    marmaladeResolver );
        }

        return taglib;
    }

    public void addTagLibrary( String uri, TagLibrary tagLibrary )
    {
        mappedTaglibs.put( uri, tagLibrary );
    }
}
