
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
/* Created on Apr 12, 2004 */
package org.codehaus.marmalade.parsetime;

import junit.framework.TestCase;

import org.codehaus.marmalade.metamodel.MarmaladeModelBuilderException;
import org.codehaus.marmalade.metamodel.MarmaladeTaglibResolver;
import org.codehaus.marmalade.model.MarmaladeScript;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author jdcasey
 */
public class ScriptParserTest extends TestCase
{
    private static final String MESSAGE = "This is a test";
    private static final String TEST_PARSE_CONTENT =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n"
        + "<testTag xmlns=\"marmalade:org.codehaus.marmalade.tags.TestParseTaglib\" value=\""
        + MESSAGE + "\"/>";

    public void testParse(  )
        throws IOException, MarmaladeExecutionException, 
            MarmaladeParsetimeException, MarmaladeModelBuilderException
    {
        File f = File.createTempFile( "parse-test-", ".mmld" );
        PrintWriter fout = new PrintWriter( new FileWriter( f ) );

        fout.println( TEST_PARSE_CONTENT );
        fout.flush(  );
        fout.close(  );

        MarmaladeTaglibResolver resolver = new MarmaladeTaglibResolver( MarmaladeTaglibResolver.DEFAULT_STRATEGY_CHAIN );

        MarmaladeScript script = new ScriptParser( resolver ).parse( f );

        f.delete(  );

        assertNotNull( "Parsed script should not be null", script );

        DefaultContext ctx = new DefaultContext(  );
        StringWriter out = new StringWriter(  );

        ctx.setOutWriter( new PrintWriter( out ) );

        script.execute( ctx );

        assertEquals( "Messages should equal.", MESSAGE,
            out.getBuffer(  ).toString(  ) );
    }
}
