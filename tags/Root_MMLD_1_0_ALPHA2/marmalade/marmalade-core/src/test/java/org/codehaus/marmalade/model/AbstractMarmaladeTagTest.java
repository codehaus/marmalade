/* Created on Aug 21, 2004 */
package org.codehaus.marmalade.model;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.util.Collections;

/**
 * @author jdcasey
 */
public class AbstractMarmaladeTagTest
    extends MockObjectTestCase
{
    public void testShouldPrintBodyToContextOutWhenNotProcessedByDefault() throws MarmaladeExecutionException
    {
        MarmaladeTag tag = new AbstractMarmaladeTag() {
        };

        String testBody = "test text";

        runTest( tag, true, true, testBody, testBody );
    }

    public void testShouldPrintBodyToContextOutWhenNotProcessedAndAlwaysProcessIsTrue()
        throws MarmaladeExecutionException
    {
        MarmaladeTag tag = new AbstractMarmaladeTag() {
            protected boolean alwaysProcessBody()
            {
                return true;
            }
        };

        String testBody = "test text";

        runTest( tag, true, true, testBody, testBody );
    }

    public void testShouldNotPrintBodyToContextOutWhenNotProcessedAndAlwaysProcessIsFalse()
        throws MarmaladeExecutionException
    {
        MarmaladeTag tag = new AbstractMarmaladeTag() {
            protected boolean alwaysProcessBody()
            {
                return false;
            }
        };

        runTest( tag, false, false, "test text", "" );
    }

    public void testShouldNotPrintBodyToContextOutWhenProcessedAndAlwaysProcessIsTrue()
        throws MarmaladeExecutionException
    {
        MarmaladeTag tag = new AbstractMarmaladeTag() {
            protected boolean alwaysProcessBody()
            {
                return true;
            }

            protected void doExecute( MarmaladeExecutionContext ctx ) throws MarmaladeExecutionException
            {
                getBody( ctx );
            }
        };

        String testBody = "test text";

        runTest( tag, false, true, "test text", "" );
    }

    private void runTest( MarmaladeTag tag, boolean expectOutputAccess, boolean expectBodyProcess, String testInput,
        String expectedOutput ) throws MarmaladeExecutionException
    {
        Mock attrsMock = mock( MarmaladeAttributes.class );
        MarmaladeAttributes attrs = (MarmaladeAttributes) attrsMock.proxy();

        tag.setAttributes( attrs );

        MarmaladeTagInfo info = new MarmaladeTagInfo();

        info.setElement( "test" );
        info.setPrefix( "t" );
        info.setScheme( "marmalade" );
        info.setTaglib( "test" );
        info.setSourceFile( "test.mmld" );
        info.setSourceLine( 1 );

        tag.setTagInfo( info );

        tag.appendBodyText( testInput );

        Mock ctxMock = mock( MarmaladeExecutionContext.class );

        StringWriter strWriter = new StringWriter();
        PrintWriter pWriter = new PrintWriter( strWriter );

        if ( expectOutputAccess )
        {
            ctxMock.expects( atLeastOnce() ).method( "getOutWriter" ).withNoArguments().will( returnValue( pWriter ) );
        }

        if ( expectBodyProcess )
        {
            ctxMock.expects( atLeastOnce() ).method( "preserveWhitespaceOverride" ).withNoArguments().will(
                returnValue( true ) );
            ctxMock.expects( atLeastOnce() ).method( "unmodifiableVariableMap" ).withNoArguments().will(
                returnValue( Collections.EMPTY_MAP ) );
        }

        MarmaladeExecutionContext ctx = (MarmaladeExecutionContext) ctxMock.proxy();

        tag.execute( ctx );

        String output = strWriter.toString();

        assertEquals( expectedOutput, output );
    }
}