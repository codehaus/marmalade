/*
 * $Id$
 */
package org.codehaus.typle.src;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Implementation of {@link SourceFileWriter} that utilises 
 * {@link java.io.PrintWriter}.
 * @author Kristopher Brown
 * @version $Revision$ $Date$
 */
public class SourceFilePrintWriter implements SourceFileWriter {
    
    /**
     * Underlying PrintWriter to utilise
     */
    private PrintWriter w;
    
    public SourceFilePrintWriter(PrintWriter writer) {
        this.w = writer;
    }
    
    public void print(String str) throws IOException {
        w.print(str);
    }

    public void println(String str) throws IOException {
        w.println(str);
    }

    public void println() throws IOException {
        w.println();
    }

}
