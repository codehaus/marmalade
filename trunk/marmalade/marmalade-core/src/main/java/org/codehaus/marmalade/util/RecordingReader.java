/* Created on Jul 2, 2004 */
package org.codehaus.marmalade.util;

import java.io.IOException;
import java.io.Reader;

/**
 * @author jdcasey
 */
public final class RecordingReader
    extends Reader
{
    private final Reader reader;

    private StringBuffer recordedInput;

    public RecordingReader( Reader reader )
    {
        this.reader = reader;
    }

    public RecordingReader( Object lock, Reader reader )
    {
        super( lock );
        this.reader = reader;
    }

    public void close() throws IOException
    {
        reader.close();
    }

    public int read( char[] cbuf, int off, int len ) throws IOException
    {
        // do the read
        int result = reader.read( cbuf, off, len );

        // if we're recording, put a copy in the recorded input
        if ( (recordedInput != null) && (result > 0) )
        {
            recordedInput.append( cbuf, off, result );
        }

        // return the result of the read.
        return result;
    }

    public void startRecording()
    {
        recordedInput = new StringBuffer();
    }

    public String getRecordedInput()
    {
        return (recordedInput == null) ? (null) : (recordedInput.toString());
    }
}