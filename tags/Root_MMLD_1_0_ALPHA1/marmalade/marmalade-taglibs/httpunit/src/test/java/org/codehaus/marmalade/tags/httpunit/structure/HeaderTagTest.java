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



* Created on Apr 29, 2004 */
package org.codehaus.marmalade.tags.httpunit.structure;

import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.MissingAttributeException;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.Tag;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author jdcasey
 */
public class HeaderTagTest extends MockObjectTestCase
{
    public void testSuccess_ValueAttribute(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        Mock wrMock = mock( HeaderParent.class );

        wrMock.expects( once(  ) ).method( "setHeader" )
              .with( eq( "testkey" ), eq( "testval" ) ).isVoid(  );

        DefaultRawAttributes attributes = new DefaultRawAttributes();

        attributes.addAttribute( "", HeaderTag.NAME_ATTRIBUTE, "testkey" );
        attributes.addAttribute( "", HeaderTag.VALUE_ATTRIBUTE, "testval" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);

        HeaderTag tag = new HeaderTag( mti );

        tag.setParent( ( MarmaladeTag ) wrMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );
    }

    public void testSuccess_ValueBody(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        String body = "testval";

        Mock wrMock = new Mock( HeaderParent.class );

        wrMock.expects( once(  ) ).method( "getExpressionEvaluator" )
              .withNoArguments(  ).will( returnValue( null ) );

        wrMock.expects( once(  ) ).method( "setHeader" )
              .with( eq( "testkey" ), eq( "testval" ) ).isVoid(  );

        DefaultRawAttributes attributes = new DefaultRawAttributes();

        attributes.addAttribute( "", HeaderTag.NAME_ATTRIBUTE, "testkey" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.appendText( body.toCharArray(  ), 0, body.length(  ) );

        HeaderTag tag = new HeaderTag( mti );

        tag.setParent( ( MarmaladeTag ) wrMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );
    }

    public void testFailure_NameMissing(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        String body = "testval";

        Mock wrMock = new Mock( HeaderParent.class );

        wrMock.expects( once(  ) ).method( "getExpressionEvaluator" )
              .withNoArguments(  ).will( returnValue( null ) );

        wrMock.expects( once(  ) ).method( "setHeader" )
              .with( eq( "testkey" ), eq( "testval" ) ).isVoid(  );
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.appendText( body.toCharArray(  ), 0, body.length(  ) );

        HeaderTag tag = new HeaderTag( mti );

        tag.setParent( ( MarmaladeTag ) wrMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        try
        {
            tag.execute( ctx );
            fail( 
                "Should have thrown MissingAttributeException for missing name attribute." );
        }
        catch ( MissingAttributeException e )
        {
        }
    }
}