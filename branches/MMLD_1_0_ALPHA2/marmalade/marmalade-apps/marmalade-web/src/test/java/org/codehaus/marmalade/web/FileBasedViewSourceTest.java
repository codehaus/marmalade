/* Created on Aug 25, 2004 */
package org.codehaus.marmalade.web;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.codehaus.marmalade.parsing.MarmaladeParsingContext;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class FileBasedViewSourceTest
    extends TestCase
{
    private File tempFile;
    
    protected void setUp() throws IOException {
        tempFile = File.createTempFile("FileBaseViewSource", "test.mmld");
        
        FileWriter writer = new FileWriter(tempFile);
        writer.write("<c:set var=\"test\" value=\"value\" xmlns:c=\"marmalade:core\"/>");
        writer.flush();
        writer.close();
        
        tempFile.deleteOnExit();
    }

    public void testShouldConstructWithPathAndFile() {
        FileBasedViewSource src = new FileBasedViewSource("/path/to/temp/file", tempFile);
    }
    
    public void testShouldOpenContext() throws IOException {
        FileBasedViewSource src = new FileBasedViewSource("/path/to/temp/file", tempFile);
        MarmaladeParsingContext ctx = src.openContext();
        
        assertEquals("input location is wrong", "/path/to/temp/file", ctx.getInputLocation());
        assertNotNull("input reader should not be null", ctx.getInput());
    }
    
}
