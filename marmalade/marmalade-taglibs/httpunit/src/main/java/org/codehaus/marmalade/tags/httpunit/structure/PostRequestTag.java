
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

/* Created on Apr 21, 2004 */
package org.codehaus.marmalade.tags.httpunit.structure;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import java.net.URL;

/**
 * @author jdcasey
 */
public class PostRequestTag extends AbstractWebRequestTag
{
    public PostRequestTag( MarmaladeTagInfo tagInfo )
    {
        super( tagInfo );
    }

    protected WebRequest createRequest( URL baseUrl, String url, String target )
        throws MarmaladeExecutionException
    {
        PostMethodWebRequest request = null;

        if ( baseUrl != null )
        {
            if ( ( target == null ) || ( target.length(  ) < 1 ) )
            {
                request = new PostMethodWebRequest( baseUrl.toExternalForm(  )
                        + url );
            }
            else
            {
                request = new PostMethodWebRequest( baseUrl, url, target );
            }
        }
        else
        {
            request = new PostMethodWebRequest( url );
        }

        return request;
    }
}
