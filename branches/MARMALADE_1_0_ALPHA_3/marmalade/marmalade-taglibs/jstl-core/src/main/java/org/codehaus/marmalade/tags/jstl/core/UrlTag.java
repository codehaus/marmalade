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
package org.codehaus.marmalade.tags.jstl.core;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import java.io.UnsupportedEncodingException;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author jdcasey
 */
public class UrlTag extends AbstractMarmaladeTag
{
    public static final String VAR_ATTRIBUTE = "var";
    public static final String URL_ATTRIBUTE = "url";
    private Map params = new TreeMap(  );

    public UrlTag(  )
    {
        super(  );
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        String url = ( String ) requireTagAttribute( URL_ATTRIBUTE,
                String.class, context );
        String var = ( String ) requireTagAttribute( VAR_ATTRIBUTE,
                String.class, context );

        processChildren( context );

        StringBuffer finishedUrl = new StringBuffer( url );

        if ( finishedUrl.indexOf( "?" ) < 0 )
        {
            finishedUrl.append( "?" );
        }
        else
        {
            finishedUrl.append( "&" );
        }

        for ( Iterator it = params.keySet(  ).iterator(  ); it.hasNext(  ); )
        {
            String key = ( String ) it.next(  );
            String value = ( String ) params.get( key );

            try
            {
                key = URLEncoder.encode( key, "UTF-8" );
                value = URLEncoder.encode( value, "UTF-8" );
            }
            catch ( UnsupportedEncodingException e )
            {
                throw new MarmaladeExecutionException( "Error encoding URL parameter using UTF-8.",
                    e );
            }

            finishedUrl.append( key ).append( "=" ).append( value );

            if ( it.hasNext(  ) )
            {
                finishedUrl.append( "&" );
            }
        }

        String result = finishedUrl.toString(  );

        if ( result.endsWith( "?" ) )
        {
            result = result.substring( 0, result.length(  ) - 1 );
        }
        else if ( result.endsWith( "&" ) )
        {
            result = result.substring( 0, result.length(  ) - 1 );
        }

        try
        {
            context.setVariable( var, new URL( result ) );
        }
        catch ( MalformedURLException e )
        {
            throw new MarmaladeExecutionException( 
                "Error forming URL instance from resultant url: " + result, e );
        }
    }

    protected void doReset(  )
    {
        params.clear(  );
    }

    public void addParam( String key, String value )
    {
        params.put( key, value );
    }
}
