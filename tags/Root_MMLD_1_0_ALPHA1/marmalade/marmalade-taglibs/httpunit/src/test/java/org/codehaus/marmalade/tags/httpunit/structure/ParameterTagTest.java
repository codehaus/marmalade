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

import org.codehaus.marmalade.el.ognl.OgnlExpressionEvaluator;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.IllegalAncestorException;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.MissingAttributeException;
import org.codehaus.marmalade.tags.httpunit.TestingWebRequest;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.Tag;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.cglib.Mock;
import org.jmock.cglib.MockObjectTestCase;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author jdcasey
 */
public class ParameterTagTest extends MockObjectTestCase
{
    public void testEmbedded_Success_ValueAttribute(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        Mock wrMock = new Mock( TestingWebRequest.class );

        wrMock.expects( once(  ) ).method( "setParameter" )
              .with( eq( "testkey" ), eq( "testval" ) ).isVoid(  );

        Mock wrtMock = new Mock( WebRequestTag.class );

        wrtMock.expects( once(  ) ).method( "getRequest" ).withNoArguments(  )
               .will( returnValue( wrMock.proxy(  ) ) );

        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( "", ParameterTag.NAME_ATTRIBUTE, "testkey" );
        attributes.addAttribute( "", ParameterTag.VALUE_ATTRIBUTE, "testval" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attributes );

        ParameterTag tag = new ParameterTag( mti );

        tag.setParent( ( MarmaladeTag ) wrtMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );
    }

    public void testEmbedded_Success_ValueBody(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        String body = "testval";

        Mock wrMock = new Mock( TestingWebRequest.class );

        wrMock.expects( once(  ) ).method( "setParameter" )
              .with( eq( "testkey" ), eq( "testval" ) ).isVoid(  );

        Mock wrtMock = new Mock( WebRequestTag.class );

        wrtMock.expects( once(  ) ).method( "getRequest" ).withNoArguments(  )
               .will( returnValue( wrMock.proxy(  ) ) );

        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( "", ParameterTag.NAME_ATTRIBUTE, "testkey" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attributes );
        mti.appendText( body.toCharArray(  ), 0, body.length(  ) );

        ParameterTag tag = new ParameterTag( mti );

        tag.setParent( ( MarmaladeTag ) wrtMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );
    }

    public void testEmbedded_Failure_NameMissing(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        String body = "testval";
        
        Mock wrMock = new Mock( TestingWebRequest.class );

        wrMock.expects( once(  ) ).method( "setParameter" )
              .with( eq( "testkey" ), eq( "testval" ) ).isVoid(  );

        Mock wrtMock = new Mock( WebRequestTag.class );

        wrtMock.expects( once(  ) ).method( "getRequest" ).withNoArguments(  )
               .will( returnValue( wrMock.proxy(  ) ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.appendText( body.toCharArray(  ), 0, body.length(  ) );
        ParameterTag tag = new ParameterTag( mti );

        tag.setParent( ( MarmaladeTag ) wrtMock.proxy(  ) );

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

    public void testSerial_Success_ValueAttribute(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        Mock wrMock = new Mock( TestingWebRequest.class );

        wrMock.expects( once(  ) ).method( "setParameter" )
              .with( eq( "testkey" ), eq( "testval" ) ).isVoid(  );

        DefaultRawAttributes attributes = new DefaultRawAttributes();
        attributes.addAttribute( "", ParameterTag.NAME_ATTRIBUTE, "testkey" );
        attributes.addAttribute( "", ParameterTag.VALUE_ATTRIBUTE, "testval" );
        attributes.addAttribute( "", ParameterTag.REQUEST_ATTRIBUTE, "#request" );
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());

        ParameterTag tag = new ParameterTag( mti );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "request", wrMock.proxy(  ) );
        tag.execute( ctx );
    }

    public void testSerial_Failure_NameMissing(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        Mock wrMock = new Mock( TestingWebRequest.class );

        wrMock.expects( once(  ) ).method( "setParameter" )
              .with( eq( "testkey" ), eq( "testval" ) ).isVoid(  );

        DefaultRawAttributes attributes = new DefaultRawAttributes();
        
        attributes.addAttribute( "", ParameterTag.VALUE_ATTRIBUTE, "testval" );
        attributes.addAttribute( "", ParameterTag.REQUEST_ATTRIBUTE, "#request" );
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);

        ParameterTag tag = new ParameterTag( mti );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "request", wrMock.proxy(  ) );

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

    public void testSerial_Failure_RequestParamMissing(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes();

        attributes.addAttribute( "", ParameterTag.NAME_ATTRIBUTE, "testkey" );
        attributes.addAttribute( "", ParameterTag.VALUE_ATTRIBUTE, "testval" );
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);

        ParameterTag tag = new ParameterTag( mti );

        DefaultContext ctx = new DefaultContext(  );

        try
        {
            tag.execute( ctx );
            fail( 
                "Should have thrown IllegalAncestorException for missing request attribute." );
        }
        catch ( IllegalAncestorException e )
        {
        }
    }

    public void testSerial_Failure_RequestObjectMissing(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes();

        attributes.addAttribute( "", ParameterTag.NAME_ATTRIBUTE, "testkey" );
        attributes.addAttribute( "", ParameterTag.VALUE_ATTRIBUTE, "testval" );
        attributes.addAttribute( "", ParameterTag.REQUEST_ATTRIBUTE, "#request" );
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());

        ParameterTag tag = new ParameterTag( mti );

        DefaultContext ctx = new DefaultContext(  );

        try
        {
            tag.execute( ctx );
            fail( 
                "Should have thrown IllegalAncestorException for missing request object/parent." );
        }
        catch ( IllegalAncestorException e )
        {
        }
    }

    public void testSerial_Success_ValueBody(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        String body = "testval";

        Mock wrMock = new Mock( TestingWebRequest.class );

        wrMock.expects( once(  ) ).method( "setParameter" )
              .with( eq( "testkey" ), eq( "testval" ) ).isVoid(  );

        DefaultRawAttributes attributes = new DefaultRawAttributes();

        attributes.addAttribute( "", ParameterTag.NAME_ATTRIBUTE, "testkey" );
        attributes.addAttribute( "", ParameterTag.REQUEST_ATTRIBUTE, "#request" );
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());
        mti.appendText( body.toCharArray(  ), 0, body.length(  ) );

        ParameterTag tag = new ParameterTag( mti );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "request", wrMock.proxy(  ) );
        tag.execute( ctx );
    }
}