/*
 * $Id$
 */
package org.codehaus.typle.src;

import java.io.IOException;

/**
 * Common interface for writers of the source
 * @author Kristopher Brown
 * @version $Revision$ $Date$
 */
public interface SourceFileWriter {

    void print(String str) throws IOException;

    void println() throws IOException;

    void println(String str) throws IOException;
    
}
