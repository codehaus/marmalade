
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

import com.meterware.httpunit.WebResponse;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class AbstractWebResponseSubTag extends AbstractWebRequestSubTag 
    implements WebResponseSubTag
{
    public static final String RESPONSE_ATTRIBUTE = "response";

    protected AbstractWebResponseSubTag( MarmaladeTagInfo tagInfo )
    {
        super( tagInfo );
    }

    public WebResponse getResponse( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        MarmaladeAttributes attrs = getAttributes(  );

        WebResponse response = ( WebResponse ) attrs.getValue( RESPONSE_ATTRIBUTE,
                WebResponse.class, context );

        if ( response == null )
        {
            WebResponseTag ancestor = ( WebResponseTag ) requireAncestor( WebResponseTag.class );

            response = ancestor.getResponse(  );
        }

        return response;
    }
}
