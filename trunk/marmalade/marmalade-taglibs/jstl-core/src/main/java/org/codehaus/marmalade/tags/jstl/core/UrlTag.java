
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

    public UrlTag( MarmaladeTagInfo tagInfo )
    {
        super( tagInfo );
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
