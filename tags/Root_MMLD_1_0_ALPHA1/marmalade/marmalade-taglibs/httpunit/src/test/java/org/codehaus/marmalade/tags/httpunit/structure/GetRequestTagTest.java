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



* Created on Apr 26, 2004 */
package org.codehaus.marmalade.tags.httpunit.structure;

import com.meterware.httpunit.WebConversation;

import org.codehaus.marmalade.el.ognl.OgnlExpressionEvaluator;
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
import org.jmock.cglib.Mock;
import org.jmock.cglib.MockObjectTestCase;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Map;
import java.util.TreeMap;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class GetRequestTagTest extends MockObjectTestCase
{
    public void testEmbeddedSuccess(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        Mock convTagMock = new Mock( WebConversationTag.class );

        DefaultRawAttributes attributes = new DefaultRawAttributes();

        attributes.addAttribute( "", GetRequestTag.URL_ATTRIBUTE, "http://localhost" );
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);

        GetRequestTag tag = new GetRequestTag( mti );

        tag.setParent( ( MarmaladeTag ) convTagMock.proxy(  ) );

        MarmaladeTagInfo mtiChild = new MarmaladeTagInfo();
        
        TestRequestSubTag child = new TestRequestSubTag( mtiChild );

        child.setParent( tag );
        tag.addChild(child);

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        assertTrue( "Child should have found request.", child.foundRequest(  ) );
    }

    public void testEmbeddedMissingConversation(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes();

        attributes.addAttribute( "", GetRequestTag.URL_ATTRIBUTE, "http://localhost" );
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);

        GetRequestTag tag = new GetRequestTag( mti );

        MarmaladeTagInfo mtiChild = new MarmaladeTagInfo();
        
        TestRequestSubTag child = new TestRequestSubTag( mtiChild );

        child.setParent( tag );
        tag.addChild( child );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        assertTrue( "Child should have found request.", child.foundRequest(  ) );
    }

    public void testSerialSuccess(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        Mock convMock = new Mock( WebConversation.class );

        DefaultRawAttributes attributes = new DefaultRawAttributes();

        attributes.addAttribute( "", GetRequestTag.VAR_ATTRIBUTE, "request" );
        attributes.addAttribute( "", GetRequestTag.URL_ATTRIBUTE, "http://localhost" );
        attributes.addAttribute( "", GetRequestTag.CONVERSATION_ATTRIBUTE, "#conversation" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);

        GetRequestTag tag = new GetRequestTag( mti );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "conversation", ( WebConversation ) convMock.proxy(  ) );
        tag.execute( ctx );

        assertNotNull( "Request variable should NOT be null.",
            ctx.getVariable( "request", null ) );
    }

    public void testSerialMissingConversation(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes();

        attributes.addAttribute( "", GetRequestTag.VAR_ATTRIBUTE, "request" );
        attributes.addAttribute( "", GetRequestTag.URL_ATTRIBUTE, "http://localhost" );
        attributes.addAttribute( "", GetRequestTag.CONVERSATION_ATTRIBUTE, "#conversation" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);

        GetRequestTag tag = new GetRequestTag( mti );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        assertNotNull( "Request variable should NOT be null.",
            ctx.getVariable( "request", null ) );
    }

    public void testAllAttributes(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes();

        attributes.addAttribute( "", GetRequestTag.VAR_ATTRIBUTE, "request" );
        attributes.addAttribute( "", GetRequestTag.BASE_URL_ATTRIBUTE, "http://localhost" );
        attributes.addAttribute( "", GetRequestTag.URL_ATTRIBUTE, "/index.html" );
        attributes.addAttribute( "", GetRequestTag.TARGET_ATTRIBUTE, "_new" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);

        GetRequestTag tag = new GetRequestTag( mti );

        MarmaladeTagInfo mtiChild = new MarmaladeTagInfo();
        
        TestRequestSubTag child = new TestRequestSubTag( mtiChild );

        child.setParent( tag );
        tag.addChild( child );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        assertTrue( "Tag child should have found request.",
            child.foundRequest(  ) );
    }

    public void testAllAttributes_BaseUrlIsObject(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException, 
            MalformedURLException
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes();

        attributes.addAttribute( "", GetRequestTag.VAR_ATTRIBUTE, "request" );
        attributes.addAttribute( "", GetRequestTag.BASE_URL_ATTRIBUTE, "#baseUrl" );
        attributes.addAttribute( "", GetRequestTag.URL_ATTRIBUTE, "/index.html" );
        attributes.addAttribute( "", GetRequestTag.TARGET_ATTRIBUTE, "_new" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());

        GetRequestTag tag = new GetRequestTag( mti );

        MarmaladeTagInfo mtiChild = new MarmaladeTagInfo();
        
        TestRequestSubTag child = new TestRequestSubTag( mtiChild );

        child.setParent( tag );
        tag.addChild( child );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "baseUrl", new URL( "http://localhost" ) );
        tag.execute( ctx );

        assertTrue( "Tag child should have found request.",
            child.foundRequest(  ) );
    }

    public void testMissingUrl(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes();

        attributes.addAttribute( "", GetRequestTag.VAR_ATTRIBUTE, "request" );
        attributes.addAttribute( "", GetRequestTag.BASE_URL_ATTRIBUTE, "http://localhost" );
        attributes.addAttribute( "", GetRequestTag.TARGET_ATTRIBUTE, "_new" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);

        GetRequestTag tag = new GetRequestTag( mti );

        DefaultContext ctx = new DefaultContext(  );

        try
        {
            tag.execute( ctx );
            fail( "Should throw exception for missing url attribute" );
        }
        catch ( MissingAttributeException e )
        {
        }
    }

    public void testMissingBaseUrl(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes();

        attributes.addAttribute( "", GetRequestTag.VAR_ATTRIBUTE, "request" );
        attributes.addAttribute( "", GetRequestTag.URL_ATTRIBUTE, "/index.html" );
        attributes.addAttribute( "", GetRequestTag.TARGET_ATTRIBUTE, "_new" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);

        GetRequestTag tag = new GetRequestTag( mti );

        MarmaladeTagInfo mtiChild = new MarmaladeTagInfo();
        
        TestRequestSubTag child = new TestRequestSubTag( mtiChild );

        child.setParent( tag );
        tag.addChild( child );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        assertTrue( "Tag child should have found request.",
            child.foundRequest(  ) );
    }

    public void testMissingTarget(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes();

        attributes.addAttribute( "", GetRequestTag.VAR_ATTRIBUTE, "request" );
        attributes.addAttribute( "", GetRequestTag.BASE_URL_ATTRIBUTE, "http://localhost" );
        attributes.addAttribute( "", GetRequestTag.URL_ATTRIBUTE, "/index.html" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);

        GetRequestTag tag = new GetRequestTag( mti );

        MarmaladeTagInfo mtiChild = new MarmaladeTagInfo();
        
        TestRequestSubTag child = new TestRequestSubTag( mtiChild );

        child.setParent( tag );
        tag.addChild( child );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        assertTrue( "Tag child should have found request.",
            child.foundRequest(  ) );
    }
}