/* Created on Jul 2, 2004 */
package org.codehaus.marmalade.util;

import junit.framework.TestCase;

import java.io.IOException;
import java.io.StringReader;

/**
 * @author jdcasey
 */
public class RecordingReaderTest
    extends TestCase
{
    public void testShouldReadCorrectlyWhenNotRecording() throws IOException
    {
        String test = "This is a test";
        StringReader sReader = new StringReader( test );
        RecordingReader reader = new RecordingReader( sReader );

        char[] cbuf = new char[test.length()];
        int numRead = reader.read( cbuf );

        assertEquals( "Number of characters read should be equal to test length.", test.length(), numRead );
        assertEquals( "Result of read should be equal to test input.", test, String.valueOf( cbuf ) );
    }

    public void testShouldStoreAndReadCorrectlyWhenRecording() throws IOException
    {
        String unRec = "This ";
        String rec = "is a test";

        String test = unRec + rec;
        StringReader sReader = new StringReader( test );
        RecordingReader reader = new RecordingReader( sReader );

        char[] cbuf1 = new char[unRec.length()];
        int numRead1 = reader.read( cbuf1 );

        reader.startRecording();

        char[] cbuf2 = new char[rec.length()];
        int numRead2 = reader.read( cbuf2 );

        assertEquals( "Number of characters read but unrecorded should be equal to unrecorded test input.", unRec
            .length(), numRead1 );
        assertEquals( "Number of characters read and recorded should be equal to recorded test input.", rec.length(),
            numRead2 );
        assertEquals( "Result of read before recording should be equal to unrecorded test input.", unRec, String
            .valueOf( cbuf1 ) );
        assertEquals( "Result of read after recording should be equal to recorded test input.", rec, String
            .valueOf( cbuf2 ) );
        assertEquals( "Recorded text should be equal to recorded test input.", rec, reader.getRecordedInput() );
    }
}