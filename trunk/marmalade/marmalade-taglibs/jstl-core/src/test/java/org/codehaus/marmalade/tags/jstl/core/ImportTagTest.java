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
package org.codehaus.marmalade.tags.jstl.core;

import junit.framework.TestCase;

import org.codehaus.marmalade.el.ognl.OgnlExpressionEvaluator;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.MarmaladeScript;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import java.net.MalformedURLException;

/**
 * @author jdcasey
 */
public class ImportTagTest extends TestCase
{
    private static final String SCRIPT = "<?xml version=\"1.0\"?>\n\n"
        + "<set xmlns=\"marmalade:jstl-core\" var=\"testKey\" value=\"testValue\"/>\n\n";
    private File scriptFile;

    public void testShouldParseButNotExecuteScriptFromStringUrlWhenParseOnlyAttributeTrue(  )
        throws MarmaladeExecutionException, MalformedURLException
    {
        _testScriptImport( scriptFile.toURL(  ).toExternalForm(  ), true, true );
    }

    public void testShouldParseAndExecuteScriptFromStringUrlWhenParseOnlyAttributeFalse(  )
        throws MarmaladeExecutionException, MalformedURLException
    {
        _testScriptImport( scriptFile.toURL(  ).toExternalForm(  ), true, false );
    }

    public void testShouldParseButNotExecuteScriptFromUrlObjectWhenParseOnlyAttributeTrue(  )
        throws MarmaladeExecutionException, MalformedURLException
    {
        _testScriptImport( scriptFile.toURL(  ), true, true );
    }

    public void testShouldParseAndExecuteScriptFromUrlObjectWhenParseOnlyAttributeFalse(  )
        throws MarmaladeExecutionException, MalformedURLException
    {
        _testScriptImport( scriptFile.toURL(  ), true, false );
    }

    public void testShouldParseAndExecuteScriptFromUrlObjectWhenParseOnlyAttributeUnspecified(  )
        throws MarmaladeExecutionException, MalformedURLException
    {
        _testScriptImport( scriptFile.toURL(  ), false, false );
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

    private void _testScriptImport( Object urlObject,
        boolean parseOnlySpecified, boolean parseOnly )
        throws MarmaladeExecutionException, MalformedURLException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", "url", "#scriptLocation" );
        attrs.addAttribute( "", "var", "script" );

        if ( parseOnlySpecified )
        {
            attrs.addAttribute( "", "parse-only", Boolean.toString( parseOnly ) );
        }

        OgnlExpressionEvaluator el = new OgnlExpressionEvaluator(  );

        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( attrs );
        ti.setExpressionEvaluator( el );

        ImportTag tag = new ImportTag( ti );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "scriptLocation", urlObject );

        tag.execute( ctx );

        if ( parseOnly )
        {
            assertNull( ctx.getVariable( "testKey", el ) );
        }
        else
        {
            assertNotNull( ctx.getVariable( "testKey", el ) );
        }

        assertNotNull( ctx.getVariable( "script", el ) );
        assertTrue( ctx.getVariable( "script", el ) instanceof MarmaladeScript );
    }
}
