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
/* Created on Apr 15, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import junit.framework.TestCase;

import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.DefaultAttributes;
import org.codehaus.marmalade.model.MarmaladeScript;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author jdcasey
 */
public class ImportTagTest extends TestCase
{
    private static final String SCRIPT = "<?xml version=\"1.0\"?>\n\n"
        + "<set xmlns=\"marmalade:jelly-core\" var=\"testKey\" value=\"testValue\"/>\n\n";
    private File scriptFile;

    public void testDoExecute_StringUrl_ParseOnly(  )
        throws MarmaladeExecutionException, MalformedURLException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", "", ImportTag.URL_ATTRIBUTE, "${scriptLocation}" );
        attrs.addAttribute( "", "", ImportTag.VAR_ATTRIBUTE, "script" );
        attrs.addAttribute( "", "", ImportTag.PARSE_ONLY_ATTRIBUTE, "true" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attrs );

        ImportTag tag = new ImportTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "scriptLocation",
            scriptFile.toURL(  ).toExternalForm(  ) );

        tag.execute( ctx );

        assertNotNull( "Parsed script is not present in context.",
            ctx.getVariable( "script", null ) );
        assertTrue( "Script variable is not of type MarmaladeScript.",
            ctx.getVariable( "script", null ) instanceof MarmaladeScript );
    }

    public void testDoExecute_StringUrl_Execute(  )
        throws MarmaladeExecutionException, MalformedURLException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", "", ImportTag.URL_ATTRIBUTE, "${scriptLocation}" );
        attrs.addAttribute( "", "", ImportTag.VAR_ATTRIBUTE, "script" );
        attrs.addAttribute( "", "", ImportTag.PARSE_ONLY_ATTRIBUTE, "false" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attrs );

        ImportTag tag = new ImportTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "scriptLocation",
            scriptFile.toURL(  ).toExternalForm(  ) );

        tag.execute( ctx );

        assertNotNull( "Parsed script is not present in context.",
            ctx.getVariable( "script", null ) );
        assertTrue( "Script variable is not of type MarmaladeScript.",
            ctx.getVariable( "script", null ) instanceof MarmaladeScript );
        assertNotNull( "Parsed script add-ins are not present in context.",
            ctx.getVariable( "testKey", null ) );
    }

    public void testDoExecute_URLUrl_ParseOnly(  )
        throws MarmaladeExecutionException, MalformedURLException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", "", ImportTag.URL_ATTRIBUTE, "${scriptLocation}" );
        attrs.addAttribute( "", "", ImportTag.VAR_ATTRIBUTE, "script" );
        attrs.addAttribute( "", "", ImportTag.PARSE_ONLY_ATTRIBUTE, "true" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attrs );

        ImportTag tag = new ImportTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "scriptLocation", scriptFile.toURL(  ) );

        tag.execute( ctx );

        assertNotNull( "Parsed script is not present in context.",
            ctx.getVariable( "script", null ) );
        assertTrue( "Script variable is not of type MarmaladeScript.",
            ctx.getVariable( "script", null ) instanceof MarmaladeScript );
    }

    public void testDoExecute_URLUrl_Execute(  )
        throws MarmaladeExecutionException, MalformedURLException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", "", ImportTag.URL_ATTRIBUTE, "${scriptLocation}" );
        attrs.addAttribute( "", "", ImportTag.VAR_ATTRIBUTE, "script" );
        attrs.addAttribute( "", "", ImportTag.PARSE_ONLY_ATTRIBUTE, "false" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attrs );

        ImportTag tag = new ImportTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "scriptLocation", scriptFile.toURL(  ) );

        tag.execute( ctx );

        assertNotNull( "Parsed script is not present in context.",
            ctx.getVariable( "script", null ) );
        assertTrue( "Script variable is not of type MarmaladeScript.",
            ctx.getVariable( "script", null ) instanceof MarmaladeScript );
        assertNotNull( "Parsed script add-ins are not present in context.",
            ctx.getVariable( "testKey", null ) );
    }

    public void testDoExecute_URLUrl_DefaultIsExecute(  )
        throws MarmaladeExecutionException, MalformedURLException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", "", ImportTag.URL_ATTRIBUTE, "${scriptLocation}" );
        attrs.addAttribute( "", "", ImportTag.VAR_ATTRIBUTE, "script" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        DefaultAttributes tagAttrs = new DefaultAttributes( attrs );

        ImportTag tag = new ImportTag(  );

        tag.setTagInfo( mti );
        tag.setAttributes( tagAttrs );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "scriptLocation", scriptFile.toURL(  ) );

        tag.execute( ctx );

        assertNotNull( "Parsed script is not present in context.",
            ctx.getVariable( "script", null ) );
        assertTrue( "Script variable is not of type MarmaladeScript.",
            ctx.getVariable( "script", null ) instanceof MarmaladeScript );
        assertNotNull( "Parsed script add-ins are not present in context.",
            ctx.getVariable( "testKey", null ) );
    }

    protected void setUp(  ) throws Exception
    {
        this.scriptFile = File.createTempFile( "import-test", ".mmld" );

        BufferedWriter out = new BufferedWriter( new FileWriter( scriptFile ) );

        out.write( SCRIPT );
        out.flush(  );
        out.close(  );
    }

    protected void tearDown(  )
        throws Exception
    {
        if ( scriptFile != null )
        {
            scriptFile.delete(  );
        }
    }
}
