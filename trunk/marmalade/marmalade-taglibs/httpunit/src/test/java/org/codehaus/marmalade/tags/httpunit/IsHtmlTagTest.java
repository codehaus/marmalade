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



* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit;

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
public class IsHtmlTagTest extends MockObjectTestCase
{
    public void testSucceed(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        Mock responseMock = new Mock( TestingWebResponse.class );

        responseMock.expects( once(  ) ).method( "isHTML" ).withNoArguments(  )
                    .will( returnValue( true ) );

        Mock parentMock = new Mock( WebResponseTag.class );

        parentMock.expects( once(  ) ).method( "getParent" ).withNoArguments(  )
                  .will( returnValue( null ) );

        parentMock.expects( once(  ) ).method( "getResponse" ).withNoArguments(  )
                  .will( returnValue( responseMock.proxy(  ) ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( new DefaultRawAttributes(  ) );
        mti.setElement( "isHtml" );

        IsHtmlTag tag = new IsHtmlTag( mti );

        tag.setParent( ( MarmaladeTag ) parentMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );
    }

    public void testFail(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        Mock responseMock = new Mock( TestingWebResponse.class );

        responseMock.expects( once(  ) ).method( "isHTML" ).withNoArguments(  )
                    .will( returnValue( false ) );

        Mock parentMock = new Mock( WebResponseTag.class );

        parentMock.expects( once(  ) ).method( "getParent" ).withNoArguments(  )
                  .will( returnValue( null ) );

        parentMock.expects( once(  ) ).method( "getResponse" ).withNoArguments(  )
                  .will( returnValue( responseMock.proxy(  ) ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( new DefaultRawAttributes(  ) );
        mti.setElement( "isHtml" );

        IsHtmlTag tag = new IsHtmlTag( mti );

        tag.setParent( ( MarmaladeTag ) parentMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        try
        {
            tag.execute( ctx );
            fail( "Should have caught an assertion exception." );
        }
        catch ( HttpAssertionFailedException e )
        {
        }
    }
}