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
package org.codehaus.marmalade.tags.httpunit.link;

import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.httpunit.AbstractAssertionTag;
import org.xml.sax.SAXException;

/**
 * @author jdcasey
 */
public class DefaultHasLinkTag extends AbstractAssertionTag
{
    public static final String WITH_ID_ATTRIBUTE = "withId";
    public static final String WITH_NAME_ATTRIBUTE = "withName";
    public static final String WITH_TEXT_ATTRIBUTE = "withText";
    public static final String WITH_IMAGE_TEXT_ATTRIBUTE = "withImageText";
    private WebLink link;

    public DefaultHasLinkTag( MarmaladeTagInfo tagInfo )
    {
        super( tagInfo );
    }

    protected void setUp( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        WebResponse response = getResponse( context );

        MarmaladeAttributes attributes = getAttributes(  );

        String id = ( String ) attributes.getValue( WITH_ID_ATTRIBUTE,
                String.class, context );

        if ( ( id != null ) && ( id.length(  ) > 0 ) )
        {
            try
            {
                link = response.getLinkWithID( id );
            }
            catch ( SAXException e )
            {
                throw new MarmaladeExecutionException( "Error parsing web response.",
                    e );
            }
        }

        if ( link == null )
        {
            String name = ( String ) attributes.getValue( WITH_NAME_ATTRIBUTE,
                    String.class, context );

            if ( ( name != null ) && ( name.length(  ) > 0 ) )
            {
                try
                {
                    link = response.getLinkWithName( name );
                }
                catch ( SAXException e )
                {
                    throw new MarmaladeExecutionException( "Error parsing web response.",
                        e );
                }
            }
        }

        if ( link == null )
        {
            String text = ( String ) attributes.getValue( WITH_TEXT_ATTRIBUTE,
                    String.class, context );

            if ( ( text != null ) && ( text.length(  ) > 0 ) )
            {
                try
                {
                    link = response.getLinkWith( text );
                }
                catch ( SAXException e )
                {
                    throw new MarmaladeExecutionException( "Error parsing web response.",
                        e );
                }
            }
        }

        if ( link == null )
        {
            String imgText = ( String ) attributes.getValue( WITH_IMAGE_TEXT_ATTRIBUTE,
                    String.class, context );

            if ( ( imgText != null ) && ( imgText.length(  ) > 0 ) )
            {
                try
                {
                    link = response.getLinkWithImageText( imgText );
                }
                catch ( SAXException e )
                {
                    throw new MarmaladeExecutionException( "Error parsing web response.",
                        e );
                }
            }
        }
    }

    public WebLink getLink(  )
    {
        return link;
    }

    protected boolean test( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        return link != null;
    }
}