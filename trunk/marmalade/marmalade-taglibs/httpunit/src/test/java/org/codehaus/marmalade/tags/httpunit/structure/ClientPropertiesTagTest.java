
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

/* Created on May 8, 2004 */
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
