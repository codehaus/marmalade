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



* Created on Apr 21, 2004 */
package org.codehaus.marmalade.tags.httpunit.structure;

import com.meterware.httpunit.ClientProperties;
import com.meterware.httpunit.DNSListener;
import com.meterware.httpunit.WebConversation;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class ClientPropertiesTag extends AbstractWebConversationSubTag
{
    public static final String ACCEPT_COOKIES_ATTIBUTE = "acceptCookies";
    public static final String ACCEPT_GZIP_ATTRIBUTE = "acceptGzip";
    public static final String APPLICATION_CODE_NAME_ATTRIBUTE = "appCodeName";
    public static final String APPLICATION_NAME_ATTRIBUTE = "appName";
    public static final String APPLICATION_VERSION_ATTRIBUTE = "appVersion";
    public static final String AUTO_REDIRECT_ATTRIBUTE = "autoRedirect";
    public static final String SCREEN_WIDTH_ATTRIBUTE = "screenWidth";
    public static final String SCREEN_HEIGHT_ATTRIBUTE = "screenHeight";
    public static final String DNS_LISTENER_ATTRIBUTE = "dnsListener";
    public static final String IFRAME_SUPPORTED_ATTRIBUTE = "iframeSupported";
    public static final String PLATFORM_ATTRIBUTE = "platform";
    public static final String USER_AGENT_ATTRIBUTE = "userAgent";
    private static final Boolean DEFAULT_ACCEPT_COOKIES = Boolean.TRUE;
    private static final Boolean DEFAULT_ACCEPT_GZIP = Boolean.TRUE;
    private static final Boolean DEFAULT_AUTO_REDIRECT = Boolean.TRUE;
    private static final Boolean DEFAULT_IFRAME_SUPPORTED = Boolean.TRUE;

    public ClientPropertiesTag( MarmaladeTagInfo tagInfo )
    {
        super( tagInfo );
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        MarmaladeAttributes attrs = getAttributes(  );

        WebConversation conversation = getConversation( context );
        ClientProperties props = conversation.getClientProperties(  );

        Boolean acceptCookies = ( Boolean ) attrs.getValue( ACCEPT_COOKIES_ATTIBUTE,
                Boolean.class, context, DEFAULT_ACCEPT_COOKIES );

        if ( acceptCookies != null )
        {
            props.setAcceptCookies( acceptCookies.booleanValue(  ) );
        }

        Boolean acceptGzip = ( Boolean ) attrs.getValue( ACCEPT_GZIP_ATTRIBUTE,
                Boolean.class, context, DEFAULT_ACCEPT_GZIP );

        if ( acceptGzip != null )
        {
            props.setAcceptGzip( acceptGzip.booleanValue(  ) );
        }

        String appCodeName = ( String ) attrs.getValue( APPLICATION_CODE_NAME_ATTRIBUTE,
                String.class, context );

        if ( ( appCodeName != null ) && ( appCodeName.length(  ) > 0 ) )
        {
            props.setApplicationCodeName( appCodeName );
        }

        String appName = ( String ) attrs.getValue( APPLICATION_NAME_ATTRIBUTE,
                String.class, context );

        if ( ( appName != null ) && ( appName.length(  ) > 0 ) )
        {
            props.setApplicationName( appName );
        }

        String appVersion = ( String ) attrs.getValue( APPLICATION_VERSION_ATTRIBUTE,
                String.class, context );

        if ( ( appVersion != null ) && ( appVersion.length(  ) > 0 ) )
        {
            props.setApplicationVersion( appVersion );
        }

        Boolean autoRedir = ( Boolean ) attrs.getValue( AUTO_REDIRECT_ATTRIBUTE,
                Boolean.class, context, DEFAULT_AUTO_REDIRECT );

        if ( autoRedir != null )
        {
            props.setAutoRedirect( autoRedir.booleanValue(  ) );
        }

        Integer width = ( Integer ) attrs.getValue( SCREEN_WIDTH_ATTRIBUTE,
                Integer.class, context );

        if ( width != null )
        {
            props.setAvailableScreenWidth( width.intValue(  ) );
        }

        Integer height = ( Integer ) attrs.getValue( SCREEN_HEIGHT_ATTRIBUTE,
                Integer.class, context );

        if ( height != null )
        {
            props.setAvailHeight( height.intValue(  ) );
        }

        DNSListener dnsListener = ( DNSListener ) attrs.getValue( DNS_LISTENER_ATTRIBUTE,
                DNSListener.class, context );

        if ( dnsListener != null )
        {
            props.setDnsListener( dnsListener );
        }

        Boolean iframeSupported = ( Boolean ) attrs.getValue( IFRAME_SUPPORTED_ATTRIBUTE,
                Boolean.class, context, DEFAULT_IFRAME_SUPPORTED );

        if ( iframeSupported != null )
        {
            props.setIframeSupported( iframeSupported.booleanValue(  ) );
        }

        String platform = ( String ) attrs.getValue( PLATFORM_ATTRIBUTE,
                String.class, context );

        if ( ( platform != null ) && ( platform.length(  ) > 0 ) )
        {
            props.setPlatform( platform );
        }

        String userAgent = ( String ) attrs.getValue( USER_AGENT_ATTRIBUTE,
                String.class, context );

        if ( ( userAgent != null ) && ( userAgent.length(  ) > 0 ) )
        {
            props.setUserAgent( userAgent );
        }
    }
}