
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
/* Created on Apr 15, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

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
import java.net.URL;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author jdcasey
 */
public class ImportTagTest extends TestCase
{
    private static final String SCRIPT = "<?xml version=\"1.0\"?>\n\n"
        + "<set xmlns=\"marmalade:jstl-core\" var=\"testKey\" value=\"testValue\"/>\n\n";
    private File scriptFile;

    public void testDoExecute_StringUrl_ParseOnly(  )
        throws MarmaladeExecutionException, MalformedURLException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", ImportTag.URL_ATTRIBUTE, "#scriptLocation" );
        attrs.addAttribute( "", ImportTag.VAR_ATTRIBUTE, "script" );
        attrs.addAttribute( "", ImportTag.PARSE_ONLY_ATTRIBUTE, "true" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        ImportTag tag = new ImportTag( mti );

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

        attrs.addAttribute( "", ImportTag.URL_ATTRIBUTE, "#scriptLocation" );
        attrs.addAttribute( "", ImportTag.VAR_ATTRIBUTE, "script" );
        attrs.addAttribute( "", ImportTag.PARSE_ONLY_ATTRIBUTE, "false" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        ImportTag tag = new ImportTag( mti );

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

        attrs.addAttribute( "", ImportTag.URL_ATTRIBUTE, "#scriptLocation" );
        attrs.addAttribute( "", ImportTag.VAR_ATTRIBUTE, "script" );
        attrs.addAttribute( "", ImportTag.PARSE_ONLY_ATTRIBUTE, "true" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        ImportTag tag = new ImportTag( mti );

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

        attrs.addAttribute( "", ImportTag.URL_ATTRIBUTE, "#scriptLocation" );
        attrs.addAttribute( "", ImportTag.VAR_ATTRIBUTE, "script" );
        attrs.addAttribute( "", ImportTag.PARSE_ONLY_ATTRIBUTE, "false" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        ImportTag tag = new ImportTag( mti );

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

        attrs.addAttribute( "", ImportTag.URL_ATTRIBUTE, "#scriptLocation" );
        attrs.addAttribute( "", ImportTag.VAR_ATTRIBUTE, "script" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        ImportTag tag = new ImportTag( mti );

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
