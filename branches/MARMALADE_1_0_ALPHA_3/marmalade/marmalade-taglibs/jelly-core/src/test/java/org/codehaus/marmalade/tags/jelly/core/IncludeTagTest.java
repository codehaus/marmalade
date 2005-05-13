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
/* Created on Jun 25, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.DefaultAttributes;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class IncludeTagTest extends TestCase
{
    private static final String SCRIPT_WITHOUT_XML_DECL = "<set xmlns=\"jelly:core\" var=\"testKey\" value=\"testVal\"/>";
    private static final String SCRIPT_WITH_XML_DECL = "<?xml version=\"1.0\"?>\n"
        + SCRIPT_WITHOUT_XML_DECL;
    private static final String SCRIPT_WITHOUT_XML_WITH_EXPECTATION_DECL =
        "<?xml version=\"1.0\"?>\n"
        + "<set xmlns=\"jelly:core\" xmlns:marmalade=\"marmalade\" marmalade:el=\"ognl\" var=\"testKey\" value=\"${testVal}\"/>";

    public void testShouldSkipIfTestAttributeEvaluatesToFalse(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", "", IncludeTag.TEST_ATTRIBUTE, "false" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attrs );

        IncludeTag tag = new IncludeTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );

        tag.execute( new DefaultContext(  ) );
    }

    public void testShouldFailIfNoScriptSourceFromFileUriOrBody(  )
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", "", IncludeTag.TEST_ATTRIBUTE, "true" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attrs );

        IncludeTag tag = new IncludeTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );

        try
        {
            tag.execute( new DefaultContext(  ) );
            fail( 
                "Should fail if no script source (file, uri, body()) provided." );
        }
        catch ( MarmaladeExecutionException e )
        {
            // should snag on missing inclusion script.
        }
    }

    public void testShouldExportVariableWithValidXMLFromBodyWhenExportAttributeSpecified(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", "", IncludeTag.TEST_ATTRIBUTE, "true" );
        attrs.addAttribute( "", "", IncludeTag.EXPORT_ATTRIBUTE, "true" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attrs );

        IncludeTag tag = new IncludeTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );
        tag.appendBodyText( SCRIPT_WITH_XML_DECL );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        String value = ( String ) ctx.getVariable( "testKey", null );

        assertEquals( "testVal", value );
    }

    public void testShouldExportVariableWithValidXMLFromBodyWhenInheritingValuesAndExportAttributeSpecified(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", "", IncludeTag.TEST_ATTRIBUTE, "true" );
        attrs.addAttribute( "", "", IncludeTag.EXPORT_ATTRIBUTE, "true" );
        attrs.addAttribute( "", "", IncludeTag.INHERIT_ATTRIBUTE, "true" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attrs );

        IncludeTag tag = new IncludeTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );
        tag.appendBodyText( SCRIPT_WITHOUT_XML_WITH_EXPECTATION_DECL );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "testVal", "myVal" );
        tag.execute( ctx );

        String value = ( String ) ctx.getVariable( "testKey", null );

        assertEquals( "myVal", value );
    }

    public void testShouldNotExportVariableWithValidXMLFromBodyWhenExportAttributeUnspecified(  )
        throws MarmaladeExecutionException
    {
        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", "", IncludeTag.TEST_ATTRIBUTE, "true" );

        DefaultAttributes tagAttrs = new DefaultAttributes( attrs );

        IncludeTag tag = new IncludeTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );
        tag.appendBodyText( SCRIPT_WITH_XML_DECL );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );

        String value = ( String ) ctx.getVariable( "testKey", null );

        assertEquals( "testVal", value );
    }
}
