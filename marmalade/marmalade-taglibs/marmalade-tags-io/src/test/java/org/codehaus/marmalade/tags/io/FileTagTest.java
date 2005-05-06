package org.codehaus.marmalade.tags.io;

import org.codehaus.marmalade.discovery.PrefixedDefFileResolutionStrategy;
import org.codehaus.marmalade.launch.MarmaladeLaunchException;
import org.codehaus.marmalade.launch.MarmaladeLauncher;
import org.codehaus.marmalade.model.MarmaladeScript;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class FileTagTest
    extends TestCase
{

    public void testShouldWriteTestFileWithoutHavingToCreateDir()
        throws MarmaladeLaunchException, IOException
    {
        String script = "<file xmlns=\"marmalade:io\" path=\"test1.txt\">This is a test</file>";

        MarmaladeLauncher launcher = new MarmaladeLauncher()
            .withAdditionalTaglibDefinitionStrategy( new PrefixedDefFileResolutionStrategy() )
            .withInputScriptString( script, "test script 1" );

        launcher.run();
        
        MarmaladeScript scriptInstance = launcher.getMarmaladeScript();
        FileTag tag = (FileTag)scriptInstance.getRoot();
        
        File testFile = tag.getFile();
        
        String result = readFileContents(testFile);
        
        testFile.delete();
        
        assertEquals("This is a test", result);
    }

    private String readFileContents( File testFile ) throws IOException
    {
        FileReader reader = new FileReader(testFile);
        
        StringBuffer content = new StringBuffer();
        
        char[] buf = new char[16];
        int read = -1;
        while((read = reader.read(buf)) > -1)
        {
            content.append(buf, 0, read);
        }
        
        reader.close();
        
        return content.toString();
    }

}