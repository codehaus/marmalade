
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

/* Created on Apr 25, 2004 */
package org.codehaus.marmalade.tags.httpunit;

import org.codehaus.marmalade.metamodel.DefaultRawAttribute;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.httpunit.structure.WebResponseTag;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.MockObjectTestCase;
import org.jmock.cglib.Mock;

/**
 * @author jdcasey
 */
public class HasContentTypeTagTest extends MockObjectTestCase
{
    public void testSucceed(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        String contentType = "text/html";

        Mock responseMock = new Mock( TestingWebResponse.class );

        responseMock.expects( once(  ) ).method( "getContentType" )
                    .withNoArguments(  ).will( returnValue( contentType ) );

        Mock parentMock = new Mock( WebResponseTag.class );

        parentMock.expects( once(  ) ).method( "getResponse" ).withNoArguments(  )
                  .will( returnValue( responseMock.proxy(  ) ) );

        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "withValue",
                contentType ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setElement( "hasContentType" );

        HasContentTypeTag tag = new HasContentTypeTag( mti );

        tag.setParent( ( MarmaladeTag ) parentMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );
    }

    public void testFail(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        String contentType = "text/html";
        String contentType2 = "text/plain";

        Mock responseMock = new Mock( TestingWebResponse.class );

        responseMock.expects( once(  ) ).method( "getContentType" )
                    .withNoArguments(  ).will( returnValue( contentType ) );

        Mock parentMock = new Mock( WebResponseTag.class );

        parentMock.expects( once(  ) ).method( "getResponse" ).withNoArguments(  )
                  .will( returnValue( responseMock.proxy(  ) ) );

        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "withValue",
                contentType2 ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setElement( "hasContentType" );

        HasContentTypeTag tag = new HasContentTypeTag( mti );

        tag.setParent( ( MarmaladeTag ) parentMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        try
        {
            tag.execute( ctx );
            fail( "An exception should have been thrown." );
        }
        catch ( HttpAssertionFailedException e )
        {
        }
    }
}