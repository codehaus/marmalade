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

import com.meterware.httpunit.DialogResponder;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebWindow;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class DefaultWebConversationTag extends AbstractMarmaladeTag
    implements HeaderParent, WebConversationTag
{
    public static final String VAR_ATTRIBUTE = "var";
    public static final String USER_ATTRIBUTE = "user";
    public static final String PASSWORD_ATTRIBUTE = "password";
    public static final String FAIL_ON_ERROR_ATTRIBUTE = "failOnError";
    public static final String MAIN_WINDOW_ATTRIBUTE = "mainWindow";
    public static final String DIALOG_RESPONDER_ATTRIBUTE = "dialogResponder";
    private static final Integer DEFAULT_WEB_PORT = new Integer( 80 );
    private static final Boolean DEFAULT_FAIL_ON_ERROR = Boolean.TRUE;
    private WebConversation conversation;

    public DefaultWebConversationTag( MarmaladeTagInfo tagInfo )
    {
        super( tagInfo );
    }

    public WebConversation getConversation(  )
    {
        return conversation;
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        conversation = new WebConversation(  );

        MarmaladeAttributes attrs = getAttributes(  );
        String user = ( String ) attrs.getValue( USER_ATTRIBUTE, String.class,
                context );
        String password = ( String ) attrs.getValue( PASSWORD_ATTRIBUTE,
                String.class, context, "" );

        if ( ( user != null ) && ( user.length(  ) > 0 ) )
        {
            conversation.setAuthorization( user, password );
        }

        Boolean failOnError = ( Boolean ) attrs.getValue( FAIL_ON_ERROR_ATTRIBUTE,
                Boolean.class, context, DEFAULT_FAIL_ON_ERROR );

        if ( failOnError != null )
        {
            conversation.setExceptionsThrownOnErrorStatus( failOnError
                .booleanValue(  ) );
        }

        WebWindow window = ( WebWindow ) attrs.getValue( MAIN_WINDOW_ATTRIBUTE,
                WebWindow.class, context );

        if ( window != null )
        {
            conversation.setMainWindow( window );
        }

        DialogResponder dlgResp = ( DialogResponder ) attrs.getValue( DIALOG_RESPONDER_ATTRIBUTE,
                DialogResponder.class, context );

        if ( dlgResp != null )
        {
            conversation.setDialogResponder( dlgResp );
        }

        String var = ( String ) attrs.getValue( VAR_ATTRIBUTE, String.class,
                context );

        if ( ( var != null ) && ( var.length(  ) > 0 ) )
        {
            context.setVariable( var, conversation );
        }
    }

    protected void doReset(  )
    {
        this.conversation = null;
        super.doReset(  );
    }

    public void setHeader( String name, String value )
    {
        this.conversation.setHeaderField( name, value );
    }
}