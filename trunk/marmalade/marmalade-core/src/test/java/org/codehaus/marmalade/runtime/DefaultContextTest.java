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
public class DefaultContextTest
    extends MockObjectTestCase
{
    public void testSetGetOutWriter()
    {
        StringWriter out = new StringWriter();
        DefaultContext ctx = new DefaultContext();

        ctx.setOutWriter( new PrintWriter( out ) );

        String message = "This is a test message";

        ctx.getOutWriter().print( message );

        assertEquals( "Message should pass through unchanged.", message, out.getBuffer().toString() );
    }

    public void testSetGetErrWriter()
    {
        StringWriter out = new StringWriter();
        DefaultContext ctx = new DefaultContext();

        ctx.setErrWriter( new PrintWriter( out ) );

        String message = "This is a test message";

        ctx.getErrWriter().print( message );

        assertEquals( "Message should pass through unchanged.", message, out.getBuffer().toString() );
    }

    public void testSetGetInReader() throws IOException
    {
        String message = "This is a test message";
        StringReader in = new StringReader( message );

        DefaultContext context = new DefaultContext();

        context.setInReader( in );

        String line = new BufferedReader( context.getInReader() ).readLine();

        assertEquals( "Message should pass through unchanged.", message, line );
    }

    public void testGetVariable() throws ExpressionEvaluationException
    {
        Map seed = new HashMap();

        seed.put( "testkey", "testval" );

        DefaultContext ctx = new DefaultContext( seed );
        Object result = ctx.getVariable( "testkey", null );

        assertEquals( "Value returned should match that in the seed map.", "testval", result );
    }

    public void testSetVariable() throws ExpressionEvaluationException
    {
        Map seed = new HashMap();

        seed.put( "testkey", "testval" );

        Mock elMock = mock( ExpressionEvaluator.class );

        elMock.expects( once() ).method( "evaluate" ).with( eq( "testval2" ), isA( Map.class ), isA( Class.class ) )
            .will( returnValue( "testval2" ) );

        DefaultContext ctx = new DefaultContext( seed );

        ctx.setVariable( "testkey", "testval2" );

        Object result = ctx.getVariable( "testkey", (ExpressionEvaluator) elMock.proxy() );

        assertFalse( "Value returned should NOT match that in the seed map.", "testval".equals( result ) );
        assertEquals( "Value returned should match explicitly set value.", "testval2", result );

        elMock.verify();
    }

    public void testRemoveVariable() throws ExpressionEvaluationException
    {
        Map seed = new HashMap();

        seed.put( "testkey", "testval" );

        DefaultContext ctx = new DefaultContext( seed );

        ctx.removeVariable( "testkey" );

        Object result = ctx.getVariable( "testkey", null );

        assertFalse( "Value returned should NOT match that in the seed map.", "testval".equals( result ) );
        assertNull( "Value returned should null after being removed.", result );
    }

    public void testUnmodifiableVariableMap()
    {
        Map seed = new HashMap();

        seed.put( "testkey", "testval" );

        DefaultContext ctx = new DefaultContext( seed );
        Map unmod = ctx.unmodifiableVariableMap();

        try
        {
            unmod.remove( "testkey" );
            fail( "Result of unmodifiableVariableMap() must be unmodifiable." );
        }
        catch ( UnsupportedOperationException e )
        {
        }
    }

    public void testNewScope_LastScope() throws ExpressionEvaluationException
    {
        Map seed = new HashMap();

        seed.put( "testkey", "testval" );

        DefaultContext ctx = new DefaultContext( seed );

        Object result1 = ctx.getVariable( "testkey", null );

        assertEquals( "Value returned should match that in the seed map.", "testval", result1 );

        ctx.newScope();
        ctx.setVariable( "testkey", "testval2" );

        Object result2 = ctx.getVariable( "testkey", null );

        assertEquals( "Value returned should match explicitly set value.", "testval2", result2 );

        ctx.lastScope();

        Object result3 = ctx.getVariable( "testkey", null );

        assertEquals( "Value returned should match that in the original seed map.", "testval", result3 );
    }
}