
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
package org.codehaus.marmalade.runtime;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jdcasey
 */
public class DefaultContextTest extends MockObjectTestCase
{
    public void testSetGetOutWriter(  )
    {
        StringWriter out = new StringWriter(  );
        DefaultContext ctx = new DefaultContext(  );

        ctx.setOutWriter( new PrintWriter( out ) );

        String message = "This is a test message";

        ctx.getOutWriter(  ).print( message );

        assertEquals( "Message should pass through unchanged.", message,
            out.getBuffer(  ).toString(  ) );
    }

    public void testSetGetErrWriter(  )
    {
        StringWriter out = new StringWriter(  );
        DefaultContext ctx = new DefaultContext(  );

        ctx.setErrWriter( new PrintWriter( out ) );

        String message = "This is a test message";

        ctx.getErrWriter(  ).print( message );

        assertEquals( "Message should pass through unchanged.", message,
            out.getBuffer(  ).toString(  ) );
    }

    public void testSetGetInReader(  )
        throws IOException
    {
        String message = "This is a test message";
        StringReader in = new StringReader( message );

        DefaultContext context = new DefaultContext(  );

        context.setInReader( in );

        String line = new BufferedReader( context.getInReader(  ) ).readLine(  );

        assertEquals( "Message should pass through unchanged.", message, line );
    }

    public void testGetVariable(  )
        throws ExpressionEvaluationException
    {
        Map seed = new HashMap(  );

        seed.put( "testkey", "testval" );

        Mock elMock = mock( ExpressionEvaluator.class );

        elMock.expects( once(  ) ).method( "evaluate" )
              .with( eq( "testval" ), isA( Map.class ), isA( Class.class ) )
              .will( returnValue( "testval" ) );

        DefaultContext ctx = new DefaultContext( seed );
        Object result = ctx.getVariable( "testkey",
                ( ExpressionEvaluator ) elMock.proxy(  ) );

        assertEquals( "Value returned should match that in the seed map.",
            "testval", result );

        elMock.verify(  );
    }

    public void testSetVariable(  )
        throws ExpressionEvaluationException
    {
        Map seed = new HashMap(  );

        seed.put( "testkey", "testval" );

        Mock elMock = mock( ExpressionEvaluator.class );

        elMock.expects( once(  ) ).method( "evaluate" )
              .with( eq( "testval2" ), isA( Map.class ), isA( Class.class ) )
              .will( returnValue( "testval2" ) );

        DefaultContext ctx = new DefaultContext( seed );

        ctx.setVariable( "testkey", "testval2" );

        Object result = ctx.getVariable( "testkey",
                ( ExpressionEvaluator ) elMock.proxy(  ) );

        assertFalse( "Value returned should NOT match that in the seed map.",
            "testval".equals( result ) );
        assertEquals( "Value returned should match explicitly set value.",
            "testval2", result );

        elMock.verify(  );
    }

    public void testRemoveVariable(  )
        throws ExpressionEvaluationException
    {
        Map seed = new HashMap(  );

        seed.put( "testkey", "testval" );

        Mock elMock = mock( ExpressionEvaluator.class );

        elMock.expects( never(  ) );

        DefaultContext ctx = new DefaultContext( seed );

        ctx.removeVariable( "testkey" );

        Object result = ctx.getVariable( "testkey",
                ( ExpressionEvaluator ) elMock.proxy(  ) );

        assertFalse( "Value returned should NOT match that in the seed map.",
            "testval".equals( result ) );
        assertNull( "Value returned should null after being removed.", result );

        elMock.verify(  );
    }

    public void testUnmodifiableVariableMap(  )
    {
        Map seed = new HashMap(  );

        seed.put( "testkey", "testval" );

        DefaultContext ctx = new DefaultContext( seed );
        Map unmod = ctx.unmodifiableVariableMap(  );

        try
        {
            unmod.remove( "testkey" );
            fail( "Result of unmodifiableVariableMap() must be unmodifiable." );
        }
        catch ( UnsupportedOperationException e )
        {
        }
    }

    public void testNewScope_LastScope(  )
        throws ExpressionEvaluationException
    {
        Map seed = new HashMap(  );

        seed.put( "testkey", "testval" );

        Mock elMock = mock( ExpressionEvaluator.class );

        elMock.expects( once(  ) ).method( "evaluate" )
              .with( eq( "testval" ), isA( Map.class ), isA( Class.class ) )
              .will( returnValue( "testval" ) );

        Mock elMock2 = mock( ExpressionEvaluator.class );

        elMock2.expects( once(  ) ).method( "evaluate" )
               .with( eq( "testval2" ), isA( Map.class ), isA( Class.class ) )
               .will( returnValue( "testval2" ) );

        Mock elMock3 = mock( ExpressionEvaluator.class );

        elMock3.expects( once(  ) ).method( "evaluate" )
               .with( eq( "testval" ), isA( Map.class ), isA( Class.class ) )
               .will( returnValue( "testval" ) );

        DefaultContext ctx = new DefaultContext( seed );

        Object result1 = ctx.getVariable( "testkey",
                ( ExpressionEvaluator ) elMock.proxy(  ) );

        assertEquals( "Value returned should match that in the seed map.",
            "testval", result1 );

        ctx.newScope(  );
        ctx.setVariable( "testkey", "testval2" );

        Object result2 = ctx.getVariable( "testkey",
                ( ExpressionEvaluator ) elMock2.proxy(  ) );

        assertEquals( "Value returned should match explicitly set value.",
            "testval2", result2 );

        ctx.lastScope(  );

        Object result3 = ctx.getVariable( "testkey",
                ( ExpressionEvaluator ) elMock3.proxy(  ) );

        assertEquals( "Value returned should match that in the original seed map.",
            "testval", result3 );

        elMock.verify(  );
        elMock2.verify(  );
        elMock3.verify(  );
    }
}
