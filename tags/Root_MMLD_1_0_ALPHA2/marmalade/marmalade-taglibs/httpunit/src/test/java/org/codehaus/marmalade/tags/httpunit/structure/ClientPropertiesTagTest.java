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



* Created on May 8, 2004 */
package org.codehaus.marmalade.tags.httpunit.structure;

import com.meterware.httpunit.ClientProperties;
import com.meterware.httpunit.DNSListener;
import com.meterware.httpunit.WebConversation;

import org.codehaus.marmalade.el.ognl.OgnlExpressionEvaluator;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
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
public class ClientPropertiesTagTest extends MockObjectTestCase
{
    public void testShouldConstructEmbeddedInConversationWithNoAttributes(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        ClientProperties props = ClientProperties.getDefaultProperties(  );
        Mock wcMock = new Mock( WebConversation.class );

        wcMock.expects( once(  ) ).method( "getClientProperties" )
              .withNoArguments(  ).will( returnValue( props ) );

        Mock wctMock = new Mock( WebConversationTag.class );

        wctMock.expects( once(  ) ).method( "getConversation" ).withNoArguments(  )
               .will( returnValue( wcMock.proxy(  ) ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        
        ClientPropertiesTag tag = new ClientPropertiesTag( mti );

        tag.setParent( ( MarmaladeTag ) wctMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );
    }

    public void testShouldConstructEmbeddedInConversationWithAcceptCookiesAttribute(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        ClientProperties props = ClientProperties.getDefaultProperties(  );
        Mock wcMock = new Mock( WebConversation.class );

        wcMock.expects( once(  ) ).method( "getClientProperties" )
              .withNoArguments(  ).will( returnValue( props ) );

        Mock wctMock = new Mock( WebConversationTag.class );

        wctMock.expects( once(  ) ).method( "getConversation" ).withNoArguments(  )
               .will( returnValue( wcMock.proxy(  ) ) );

        DefaultRawAttributes attributes = new DefaultRawAttributes();

        attributes.addAttribute( "", ClientPropertiesTag.ACCEPT_COOKIES_ATTIBUTE, "true" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());
        
        ClientPropertiesTag tag = new ClientPropertiesTag( mti );

        tag.setParent( ( MarmaladeTag ) wctMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        assertTrue( props.isAcceptCookies(  ) );
    }

    public void testShouldConstructEmbeddedInConversationWithAcceptGzipAttribute(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        ClientProperties props = ClientProperties.getDefaultProperties(  );
        Mock wcMock = new Mock( WebConversation.class );

        wcMock.expects( once(  ) ).method( "getClientProperties" )
              .withNoArguments(  ).will( returnValue( props ) );

        Mock wctMock = new Mock( WebConversationTag.class );

        wctMock.expects( once(  ) ).method( "getConversation" ).withNoArguments(  )
               .will( returnValue( wcMock.proxy(  ) ) );

        DefaultRawAttributes attributes = new DefaultRawAttributes();

        attributes.addAttribute( "", ClientPropertiesTag.ACCEPT_GZIP_ATTRIBUTE, "true" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());
        
        ClientPropertiesTag tag = new ClientPropertiesTag( mti );

        tag.setParent( ( MarmaladeTag ) wctMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        assertTrue( props.isAcceptGzip(  ) );
    }

    public void testShouldConstructEmbeddedInConversationWithAppCodeNameAttribute(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        ClientProperties props = ClientProperties.getDefaultProperties(  );
        Mock wcMock = new Mock( WebConversation.class );

        wcMock.expects( once(  ) ).method( "getClientProperties" )
              .withNoArguments(  ).will( returnValue( props ) );

        Mock wctMock = new Mock( WebConversationTag.class );

        wctMock.expects( once(  ) ).method( "getConversation" ).withNoArguments(  )
               .will( returnValue( wcMock.proxy(  ) ) );

        DefaultRawAttributes attributes = new DefaultRawAttributes();

        attributes.addAttribute( "", ClientPropertiesTag.APPLICATION_CODE_NAME_ATTRIBUTE,
            "TestCodeName" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        
        ClientPropertiesTag tag = new ClientPropertiesTag( mti );

        tag.setParent( ( MarmaladeTag ) wctMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        assertEquals( "TestCodeName", props.getApplicationCodeName(  ) );
    }

    public void testShouldConstructEmbeddedInConversationWithAppNameAttribute(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        ClientProperties props = ClientProperties.getDefaultProperties(  );
        Mock wcMock = new Mock( WebConversation.class );

        wcMock.expects( once(  ) ).method( "getClientProperties" )
              .withNoArguments(  ).will( returnValue( props ) );

        Mock wctMock = new Mock( WebConversationTag.class );

        wctMock.expects( once(  ) ).method( "getConversation" ).withNoArguments(  )
               .will( returnValue( wcMock.proxy(  ) ) );

        DefaultRawAttributes attributes = new DefaultRawAttributes();

        attributes.addAttribute( "", ClientPropertiesTag.APPLICATION_NAME_ATTRIBUTE,
            "TestAppName" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        
        ClientPropertiesTag tag = new ClientPropertiesTag( mti );

        tag.setParent( ( MarmaladeTag ) wctMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        assertEquals( "TestAppName", props.getApplicationName(  ) );
    }

    public void testShouldConstructEmbeddedInConversationWithAppVersionAttribute(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        ClientProperties props = ClientProperties.getDefaultProperties(  );
        Mock wcMock = new Mock( WebConversation.class );

        wcMock.expects( once(  ) ).method( "getClientProperties" )
              .withNoArguments(  ).will( returnValue( props ) );

        Mock wctMock = new Mock( WebConversationTag.class );

        wctMock.expects( once(  ) ).method( "getConversation" ).withNoArguments(  )
               .will( returnValue( wcMock.proxy(  ) ) );

        DefaultRawAttributes attributes = new DefaultRawAttributes();

        attributes.addAttribute( "", ClientPropertiesTag.APPLICATION_VERSION_ATTRIBUTE, "1.0" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        
        ClientPropertiesTag tag = new ClientPropertiesTag( mti );

        tag.setParent( ( MarmaladeTag ) wctMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        assertEquals( "1.0", props.getApplicationVersion(  ) );
    }

    public void testShouldConstructEmbeddedInConversationWithAutoRedirectAttribute(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        ClientProperties props = ClientProperties.getDefaultProperties(  );
        Mock wcMock = new Mock( WebConversation.class );

        wcMock.expects( once(  ) ).method( "getClientProperties" )
              .withNoArguments(  ).will( returnValue( props ) );

        Mock wctMock = new Mock( WebConversationTag.class );

        wctMock.expects( once(  ) ).method( "getConversation" ).withNoArguments(  )
               .will( returnValue( wcMock.proxy(  ) ) );

        DefaultRawAttributes attributes = new DefaultRawAttributes();

        attributes.addAttribute( "", ClientPropertiesTag.AUTO_REDIRECT_ATTRIBUTE, "true" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());
        
        ClientPropertiesTag tag = new ClientPropertiesTag( mti );

        tag.setParent( ( MarmaladeTag ) wctMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        assertTrue( props.isAutoRedirect(  ) );
    }

    public void testShouldConstructEmbeddedInConversationWithScreenWidthAttribute(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        ClientProperties props = ClientProperties.getDefaultProperties(  );
        Mock wcMock = new Mock( WebConversation.class );

        wcMock.expects( once(  ) ).method( "getClientProperties" )
              .withNoArguments(  ).will( returnValue( props ) );

        Mock wctMock = new Mock( WebConversationTag.class );

        wctMock.expects( once(  ) ).method( "getConversation" ).withNoArguments(  )
               .will( returnValue( wcMock.proxy(  ) ) );

        DefaultRawAttributes attributes = new DefaultRawAttributes();

        attributes.addAttribute( "", ClientPropertiesTag.SCREEN_WIDTH_ATTRIBUTE, "400" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());
        
        ClientPropertiesTag tag = new ClientPropertiesTag( mti );

        tag.setParent( ( MarmaladeTag ) wctMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        assertEquals( 400, props.getAvailableScreenWidth(  ) );
    }

    public void testShouldConstructEmbeddedInConversationWithScreenHeightAttribute(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        ClientProperties props = ClientProperties.getDefaultProperties(  );
        Mock wcMock = new Mock( WebConversation.class );

        wcMock.expects( once(  ) ).method( "getClientProperties" )
              .withNoArguments(  ).will( returnValue( props ) );

        Mock wctMock = new Mock( WebConversationTag.class );

        wctMock.expects( once(  ) ).method( "getConversation" ).withNoArguments(  )
               .will( returnValue( wcMock.proxy(  ) ) );

        DefaultRawAttributes attributes = new DefaultRawAttributes();

        attributes.addAttribute( "", ClientPropertiesTag.SCREEN_HEIGHT_ATTRIBUTE, "400" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());
        
        ClientPropertiesTag tag = new ClientPropertiesTag( mti );

        tag.setParent( ( MarmaladeTag ) wctMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        assertEquals( 400, props.getAvailHeight(  ) );
    }

    public void testShouldConstructEmbeddedInConversationWithIFrameSupportedAttribute(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        ClientProperties props = ClientProperties.getDefaultProperties(  );
        Mock wcMock = new Mock( WebConversation.class );

        wcMock.expects( once(  ) ).method( "getClientProperties" )
              .withNoArguments(  ).will( returnValue( props ) );

        Mock wctMock = new Mock( WebConversationTag.class );

        wctMock.expects( once(  ) ).method( "getConversation" ).withNoArguments(  )
               .will( returnValue( wcMock.proxy(  ) ) );

        DefaultRawAttributes attributes = new DefaultRawAttributes();

        attributes.addAttribute( "", ClientPropertiesTag.IFRAME_SUPPORTED_ATTRIBUTE, "true" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());
        
        ClientPropertiesTag tag = new ClientPropertiesTag( mti );

        tag.setParent( ( MarmaladeTag ) wctMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        assertTrue( props.isIframeSupported(  ) );
    }

    public void testShouldConstructEmbeddedInConversationWithPlatformAttribute(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        ClientProperties props = ClientProperties.getDefaultProperties(  );
        Mock wcMock = new Mock( WebConversation.class );

        wcMock.expects( once(  ) ).method( "getClientProperties" )
              .withNoArguments(  ).will( returnValue( props ) );

        Mock wctMock = new Mock( WebConversationTag.class );

        wctMock.expects( once(  ) ).method( "getConversation" ).withNoArguments(  )
               .will( returnValue( wcMock.proxy(  ) ) );

        DefaultRawAttributes attributes = new DefaultRawAttributes();

        attributes.addAttribute( "", ClientPropertiesTag.PLATFORM_ATTRIBUTE, "TestPlatform" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        
        ClientPropertiesTag tag = new ClientPropertiesTag( mti );

        tag.setParent( ( MarmaladeTag ) wctMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        assertEquals( "TestPlatform", props.getPlatform(  ) );
    }

    public void testShouldConstructEmbeddedInConversationWithUserAgentAttribute(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        ClientProperties props = ClientProperties.getDefaultProperties(  );
        Mock wcMock = new Mock( WebConversation.class );

        wcMock.expects( once(  ) ).method( "getClientProperties" )
              .withNoArguments(  ).will( returnValue( props ) );

        Mock wctMock = new Mock( WebConversationTag.class );

        wctMock.expects( once(  ) ).method( "getConversation" ).withNoArguments(  )
               .will( returnValue( wcMock.proxy(  ) ) );

        DefaultRawAttributes attributes = new DefaultRawAttributes();

        attributes.addAttribute( "", ClientPropertiesTag.USER_AGENT_ATTRIBUTE, "TestUA" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        
        ClientPropertiesTag tag = new ClientPropertiesTag( mti );

        tag.setParent( ( MarmaladeTag ) wctMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        assertEquals( "TestUA", props.getUserAgent(  ) );
    }

    public void testShouldConstructEmbeddedInConversationWithDnsListenerAttribute(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        ClientProperties props = ClientProperties.getDefaultProperties(  );
        Mock wcMock = new Mock( WebConversation.class );

        wcMock.expects( once(  ) ).method( "getClientProperties" )
              .withNoArguments(  ).will( returnValue( props ) );

        Mock wctMock = new Mock( WebConversationTag.class );

        wctMock.expects( once(  ) ).method( "getConversation" ).withNoArguments(  )
               .will( returnValue( wcMock.proxy(  ) ) );

        DefaultRawAttributes attributes = new DefaultRawAttributes();

        attributes.addAttribute( "", ClientPropertiesTag.DNS_LISTENER_ATTRIBUTE, "#dns_l" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());
        
        ClientPropertiesTag tag = new ClientPropertiesTag( mti );

        tag.setParent( ( MarmaladeTag ) wctMock.proxy(  ) );

        Mock dlMock = new Mock( DNSListener.class );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "dns_l", dlMock.proxy(  ) );

        tag.execute( ctx );
    }
}