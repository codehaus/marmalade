
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

/* Created on Apr 28, 2004 */
package org.codehaus.marmalade.tags.httpunit.structure;

import com.meterware.httpunit.DialogResponder;
import com.meterware.httpunit.WebConversation;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ognl.OgnlExpressionEvaluator;
import org.codehaus.marmalade.metamodel.DefaultRawAttribute;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.MockObjectTestCase;
import org.jmock.cglib.Mock;

/**
 * @author jdcasey
 */
public class WebConversationTagTest extends MockObjectTestCase
{
    public void testEmbeddedSuccess_NoAttributes(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attributes );
        mti.setElement( "conversation" );
        mti.setParent( null );

        DefaultWebConversationTag tag = new DefaultWebConversationTag( mti );

        MarmaladeTagInfo mtiChild = new MarmaladeTagInfo(  );

        mtiChild.setAttributes( attributes );
        mtiChild.setElement( "child" );

        TestConversationSubTag child = new TestConversationSubTag( mtiChild );

        child.setParent(tag);
        tag.addChild( child );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        assertNotNull( "conversation should be bound to context.",
            ( WebConversation ) ctx.getVariable( "testresult", null ) );
    }

    public void testEmbeddedSuccess_UserPassword(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( new DefaultRawAttribute( "",
                DefaultWebConversationTag.USER_ATTRIBUTE, "testEmbeddedusr" ) );
        attributes.addAttribute( new DefaultRawAttribute( "",
                DefaultWebConversationTag.PASSWORD_ATTRIBUTE, "testEmbeddedpass" ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attributes );
        mti.setElement( "conversation" );

        DefaultWebConversationTag tag = new DefaultWebConversationTag( mti );

        MarmaladeTagInfo mtiChild = new MarmaladeTagInfo(  );

        mtiChild.setAttributes( attributes );
        mtiChild.setElement( "child" );

        TestConversationSubTag child = new TestConversationSubTag( mtiChild );

        child.setParent(tag);
        tag.addChild( child );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        WebConversation conv = ( WebConversation ) ctx.getVariable( "testresult",
                null );

        assertNotNull( "conversation should be bound to context.", conv );
    }

    public void testEmbeddedSuccess_UserNoPassword(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( new DefaultRawAttribute( "",
                DefaultWebConversationTag.USER_ATTRIBUTE, "testEmbeddedusr" ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attributes );
        mti.setElement( "conversation" );

        DefaultWebConversationTag tag = new DefaultWebConversationTag( mti );

        MarmaladeTagInfo mtiChild = new MarmaladeTagInfo(  );

        mtiChild.setAttributes( attributes );
        mtiChild.setElement( "child" );

        TestConversationSubTag child = new TestConversationSubTag( mtiChild );

        child.setParent(tag);
        tag.addChild( child );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        WebConversation conv = ( WebConversation ) ctx.getVariable( "testresult",
                null );

        assertNotNull( "conversation should be bound to context.", conv );
    }

    public void testEmbeddedSuccess_NoUserPassword(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( new DefaultRawAttribute( "",
                DefaultWebConversationTag.PASSWORD_ATTRIBUTE, "testEmbeddedpass" ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attributes );
        mti.setElement( "conversation" );

        DefaultWebConversationTag tag = new DefaultWebConversationTag( mti );

        MarmaladeTagInfo mtiChild = new MarmaladeTagInfo(  );

        mtiChild.setAttributes( attributes );
        mtiChild.setElement( "child" );

        TestConversationSubTag child = new TestConversationSubTag( mtiChild );

        child.setParent(tag);
        tag.addChild( child );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        WebConversation conv = ( WebConversation ) ctx.getVariable( "testresult",
                null );

        assertNotNull( "conversation should be bound to context.", conv );
    }

    public void testEmbeddedSuccess_FailOnError(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( new DefaultRawAttribute( "",
                DefaultWebConversationTag.FAIL_ON_ERROR_ATTRIBUTE, "true" ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attributes );
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());
        mti.setElement( "conversation" );

        DefaultWebConversationTag tag = new DefaultWebConversationTag( mti );

        MarmaladeTagInfo mtiChild = new MarmaladeTagInfo(  );

        mtiChild.setAttributes( attributes );
        mtiChild.setElement( "child" );

        TestConversationSubTag child = new TestConversationSubTag( mtiChild );

        child.setParent(tag);
        tag.addChild( child );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        WebConversation conv = ( WebConversation ) ctx.getVariable( "testresult",
                null );

        assertNotNull( "conversation should be bound to context.", conv );
        assertTrue( "Conversation should be set to fail on error",
            conv.getExceptionsThrownOnErrorStatus(  ) );
    }

    public void testEmbeddedFail_BadFailOnError(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( new DefaultRawAttribute( "",
                DefaultWebConversationTag.FAIL_ON_ERROR_ATTRIBUTE, "nothing" ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attributes );
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());
        mti.setElement( "conversation" );

        DefaultWebConversationTag tag = new DefaultWebConversationTag( mti );

        DefaultContext ctx = new DefaultContext(  );

        try
        {
            tag.execute( ctx );
            fail( 
                "Should have throw an expression error for the bad failOnError attribute value" );
        }
        catch ( ExpressionEvaluationException e )
        {
        }
    }

    public void testEmbeddedSuccess_DialogResponder(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( new DefaultRawAttribute( "",
                DefaultWebConversationTag.DIALOG_RESPONDER_ATTRIBUTE,
                "#reponder" ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attributes );
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());
        mti.setElement( "conversation" );

        DefaultWebConversationTag tag = new DefaultWebConversationTag( mti );

        MarmaladeTagInfo mtiChild = new MarmaladeTagInfo(  );

        mtiChild.setAttributes( attributes );
        mtiChild.setElement( "child" );

        TestConversationSubTag child = new TestConversationSubTag( mtiChild );

        child.setParent(tag);
        tag.addChild( child );

        Mock dlgRespMock = new Mock( DialogResponder.class );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "responder", ( DialogResponder ) dlgRespMock.proxy(  ) );

        tag.execute( ctx );

        WebConversation conv = ( WebConversation ) ctx.getVariable( "testresult",
                null );

        assertNotNull( "conversation should be bound to context.", conv );

        System.out.println( 
            "Warning!!! Cannot properly verify that the dialog responder was set. "
            + "API does not provide the means." );
    }

    /* 4/29/2004: Commenting out, because HTTPUnit's proxy settings are currently global to the VM.
     * Therefore, we should supply another tag to create a WebClient and use that to config a global
     * proxy setting if that's what we need to do. This will make the global nature of the proxy setting
     * more intuitive.
      public void testEmbeddedSuccess_ProxyHostPort() throws TagException, TagalogParseException, MarmaladeExecutionException {
        Map attributes = new TreeMap();
        attributes.put(DefaultWebConversationTag.PROXY_HOST_ATTRIBUTE, "proxy.net");
        attributes.put(DefaultWebConversationTag.PROXY_PORT_ATTRIBUTE, "80");

        Mock attrMock = attributesFromMap(attributes);

        Mock childAttrMock = attributesEmpty();
        DefaultWebConversationTag tag = new DefaultWebConversationTag();
        tag.begin("conversation", (Attributes)attrMock.proxy());
        TestConversationSubTag child = new TestConversationSubTag();

        child.setParent(tag);
        child.begin("child", (Attributes)childAttrMock.proxy());
        child.end("child");
        tag.child(child);

        tag.end("conversation");

        DefaultContext ctx = new DefaultContext();
        tag.execute(ctx);

        WebConversation conv = (WebConversation)ctx.getVariable("testresult", null);
        assertNotNull("conversation should be bound to context.", conv);
        assertEquals("Conversation proxy host is wrong.", "proxy.net", conv.getProxyHost());
        assertEquals("Conversation proxy port is wrong.", 80, conv.getProxyPort());

        attrMock.verify();
        childAttrMock.verify();
      }

      public void testEmbeddedSuccess_ProxyHostNoPort() throws TagException, TagalogParseException, MarmaladeExecutionException {
        Map attributes = new TreeMap();
        attributes.put(DefaultWebConversationTag.PROXY_HOST_ATTRIBUTE, "proxy.net");

        Mock attrMock = attributesFromMap(attributes);

        Mock childAttrMock = attributesEmpty();
        DefaultWebConversationTag tag = new DefaultWebConversationTag();
        tag.begin("conversation", (Attributes)attrMock.proxy());
        TestConversationSubTag child = new TestConversationSubTag();

        child.setParent(tag);
        child.begin("child", (Attributes)childAttrMock.proxy());
        child.end("child");
        tag.child(child);

        tag.end("conversation");

        DefaultContext ctx = new DefaultContext();
        tag.execute(ctx);

        WebConversation conv = (WebConversation)ctx.getVariable("testresult", null);
        assertNotNull("conversation should be bound to context.", conv);
        assertEquals("Conversation proxy host is wrong.", "proxy.net", conv.getProxyHost());
        assertEquals("Conversation proxy port is wrong.", 80, conv.getProxyPort());

        attrMock.verify();
        childAttrMock.verify();
      }

      public void testEmbeddedSuccess_ProxyNoHostPort() throws TagException, TagalogParseException, MarmaladeExecutionException {
        System.out.println("testEmbeddedSuccess_ProxyNoHostPort");
        Map attributes = new TreeMap();
        attributes.put(DefaultWebConversationTag.PROXY_PORT_ATTRIBUTE, "80");

        Mock attrMock = attributesFromMap(attributes);

        Mock childAttrMock = attributesEmpty();
        DefaultWebConversationTag tag = new DefaultWebConversationTag();
        tag.begin("conversation", (Attributes)attrMock.proxy());
        TestConversationSubTag child = new TestConversationSubTag();

        child.setParent(tag);
        child.begin("child", (Attributes)childAttrMock.proxy());
        child.end("child");
        tag.child(child);

        tag.end("conversation");

        DefaultContext ctx = new DefaultContext();
        tag.execute(ctx);

        WebConversation conv = (WebConversation)ctx.getVariable("testresult", null);
        assertNotNull("conversation should be bound to context.", conv);

        System.out.println("Proxy host: " + conv.getProxyHost());
        assertNull("Conversation proxy host should be null.", conv.getProxyHost());
    //    assertEquals("Conversation proxy port is wrong.", 80, conv.getProxyPort());

        attrMock.verify();
        childAttrMock.verify();
      }
    */
    public void testSerialSuccess_NoAttributes(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        String var = "conversation";

        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( new DefaultRawAttribute( "",
                DefaultWebConversationTag.VAR_ATTRIBUTE, var ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attributes );
        mti.setElement( "conversation" );

        DefaultWebConversationTag tag = new DefaultWebConversationTag( mti );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        assertNotNull( "conversation should be bound to context.",
            ( WebConversation ) ctx.getVariable( var, null ) );
    }

    public void testSerialSuccess_UserPassword(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        String var = "conversation";

        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( new DefaultRawAttribute( "",
                DefaultWebConversationTag.VAR_ATTRIBUTE, var ) );
        attributes.addAttribute( new DefaultRawAttribute( "",
                DefaultWebConversationTag.USER_ATTRIBUTE, "testSerialusr" ) );
        attributes.addAttribute( new DefaultRawAttribute( "",
                DefaultWebConversationTag.PASSWORD_ATTRIBUTE, "testSerialpass" ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attributes );
        mti.setElement( "conversation" );

        DefaultWebConversationTag tag = new DefaultWebConversationTag( mti );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        WebConversation conv = ( WebConversation ) ctx.getVariable( var, null );

        assertNotNull( "conversation should be bound to context.", conv );
    }

    public void testSerialSuccess_UserNoPassword(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        String var = "conversation";
        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( new DefaultRawAttribute( "",
                DefaultWebConversationTag.VAR_ATTRIBUTE, var ) );
        attributes.addAttribute( new DefaultRawAttribute( "",
                DefaultWebConversationTag.USER_ATTRIBUTE, "testSerialusr" ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attributes );
        mti.setElement( "conversation" );

        DefaultWebConversationTag tag = new DefaultWebConversationTag( mti );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        WebConversation conv = ( WebConversation ) ctx.getVariable( var, null );

        assertNotNull( "conversation should be bound to context.", conv );
    }

    public void testSerialSuccess_NoUserPassword(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        String var = "conversation";
        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( new DefaultRawAttribute( "",
                DefaultWebConversationTag.VAR_ATTRIBUTE, var ) );
        attributes.addAttribute( new DefaultRawAttribute( "",
                DefaultWebConversationTag.PASSWORD_ATTRIBUTE, "testSerialpass" ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attributes );
        mti.setElement( "conversation" );

        DefaultWebConversationTag tag = new DefaultWebConversationTag( mti );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        WebConversation conv = ( WebConversation ) ctx.getVariable( var, null );

        assertNotNull( "conversation should be bound to context.", conv );
    }

    public void testSerialSuccess_FailOnError(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        String var = "conversation";
        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( new DefaultRawAttribute( "",
                DefaultWebConversationTag.VAR_ATTRIBUTE, var ) );
        attributes.addAttribute( new DefaultRawAttribute( "",
                DefaultWebConversationTag.FAIL_ON_ERROR_ATTRIBUTE, "true" ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attributes );
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());
        mti.setElement( "conversation" );

        DefaultWebConversationTag tag = new DefaultWebConversationTag( mti );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        WebConversation conv = ( WebConversation ) ctx.getVariable( var, null );

        assertNotNull( "conversation should be bound to context.", conv );
        assertTrue( "Conversation should be set to fail on error",
            conv.getExceptionsThrownOnErrorStatus(  ) );
    }

    public void testSerialFail_BadFailOnError(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        String var = "conversation";
        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( new DefaultRawAttribute( "",
                DefaultWebConversationTag.VAR_ATTRIBUTE, var ) );
        attributes.addAttribute( new DefaultRawAttribute( "",
                DefaultWebConversationTag.FAIL_ON_ERROR_ATTRIBUTE, "nothing" ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attributes );
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());
        mti.setElement( "conversation" );

        DefaultWebConversationTag tag = new DefaultWebConversationTag( mti );

        DefaultContext ctx = new DefaultContext(  );

        try
        {
            tag.execute( ctx );
            fail( 
                "Should have throw an expression error for the bad failOnError attribute value" );
        }
        catch ( ExpressionEvaluationException e )
        {
        }
    }

    public void testSerialSuccess_DialogResponder(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        String var = "conversation";
        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( new DefaultRawAttribute( "",
                DefaultWebConversationTag.VAR_ATTRIBUTE, var ) );
        attributes.addAttribute( new DefaultRawAttribute( "",
                DefaultWebConversationTag.DIALOG_RESPONDER_ATTRIBUTE,
                "#responder" ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attributes );
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());
        mti.setElement( "conversation" );

        DefaultWebConversationTag tag = new DefaultWebConversationTag( mti );

        Mock dlgRespMock = new Mock( DialogResponder.class );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "responder", ( DialogResponder ) dlgRespMock.proxy(  ) );

        tag.execute( ctx );

        WebConversation conv = ( WebConversation ) ctx.getVariable( var, null );

        assertNotNull( "conversation should be bound to context.", conv );

        System.out.println( 
            "Warning!!! Cannot properly verify that the dialog responder was set. "
            + "API does not provide the means." );
    }

    /* 4/29/2004: Commenting out, because HTTPUnit's proxy settings are currently global to the VM.
     * Therefore, we should supply another tag to create a WebClient and use that to config a global
     * proxy setting if that's what we need to do. This will make the global nature of the proxy setting
     * more intuitive.
      public void testSerialSuccess_ProxyHostPort() throws TagException, TagalogParseException, MarmaladeExecutionException {
        String var = "conversation";
        Map attributes = new TreeMap();
        attributes.put(DefaultWebConversationTag.VAR_ATTRIBUTE, var);
        attributes.put(DefaultWebConversationTag.PROXY_HOST_ATTRIBUTE, "proxy.net");
        attributes.put(DefaultWebConversationTag.PROXY_PORT_ATTRIBUTE, "80");

        Mock attrMock = attributesFromMap(attributes);

        DefaultWebConversationTag tag = new DefaultWebConversationTag();
        tag.begin("conversation", (Attributes)attrMock.proxy());
        tag.end("conversation");

        DefaultContext ctx = new DefaultContext();
        tag.execute(ctx);

        WebConversation conv = (WebConversation)ctx.getVariable(var, null);
        assertNotNull("conversation should be bound to context.", conv);
        assertEquals("Conversation proxy host is wrong.", "proxy.net", conv.getProxyHost());
        assertEquals("Conversation proxy port is wrong.", 80, conv.getProxyPort());

        attrMock.verify();
      }

      public void testSerialSuccess_ProxyHostNoPort() throws TagException, TagalogParseException, MarmaladeExecutionException {
        String var = "conversation";
        Map attributes = new TreeMap();
        attributes.put(DefaultWebConversationTag.VAR_ATTRIBUTE, var);
        attributes.put(DefaultWebConversationTag.PROXY_HOST_ATTRIBUTE, "proxy.net");

        Mock attrMock = attributesFromMap(attributes);

        DefaultWebConversationTag tag = new DefaultWebConversationTag();
        tag.begin("conversation", (Attributes)attrMock.proxy());
        tag.end("conversation");

        DefaultContext ctx = new DefaultContext();
        tag.execute(ctx);

        WebConversation conv = (WebConversation)ctx.getVariable(var, null);
        assertNotNull("conversation should be bound to context.", conv);
        assertEquals("Conversation proxy host is wrong.", "proxy.net", conv.getProxyHost());
        assertEquals("Conversation proxy port is wrong.", 80, conv.getProxyPort());

        attrMock.verify();
      }

      public void testSerialSuccess_ProxyNoHostPort() throws TagException, TagalogParseException, MarmaladeExecutionException {
        String var = "conversation";
        Map attributes = new TreeMap();
        attributes.put(DefaultWebConversationTag.VAR_ATTRIBUTE, var);
        attributes.put(DefaultWebConversationTag.PROXY_PORT_ATTRIBUTE, "80");

        Mock attrMock = attributesFromMap(attributes);

        DefaultWebConversationTag tag = new DefaultWebConversationTag();
        tag.begin("conversation", (Attributes)attrMock.proxy());
        tag.end("conversation");

        DefaultContext ctx = new DefaultContext();
        tag.execute(ctx);

        WebConversation conv = (WebConversation)ctx.getVariable(var, null);
        assertNotNull("conversation should be bound to context.", conv);

        System.out.println("Proxy host: " + conv.getProxyHost());
        assertNull("Conversation proxy host should be null.", conv.getProxyHost());
    //    assertEquals("Conversation proxy port is wrong.", 80, conv.getProxyPort());

        attrMock.verify();
      }
    */
}
