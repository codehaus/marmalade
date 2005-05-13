package org.codehaus.marmalade.compat.ant.model;

import org.codehaus.marmalade.compat.ant.discovery.AntBasedDiscoveryStrategy;
import org.codehaus.marmalade.discovery.PrefixedDefFileResolutionStrategy;
import org.codehaus.marmalade.launch.MarmaladeLaunchException;
import org.codehaus.marmalade.launch.MarmaladeLauncher;

import java.io.PrintWriter;
import java.io.StringWriter;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class AntAdapterTagTest
    extends TestCase
{

    public void testShouldRunSimpleAntTag() throws MarmaladeLaunchException
    {
        String script = "<core:script xmlns:core=\"marmalade:core\"><ant:echo xmlns:ant=\"ant:ant\">This is a test</ant:echo></core:script>";

        StringWriter output = new StringWriter();
        PrintWriter pWriter = new PrintWriter(output);
        
        new MarmaladeLauncher().withAdditionalTaglibDefinitionStrategy( new AntBasedDiscoveryStrategy() )
                               .withAdditionalTaglibDefinitionStrategy(new PrefixedDefFileResolutionStrategy())
                               .withInputScriptString( script, "Test script" )
                               .withOutWriter(pWriter)
                               .run();
        
        assertEquals("This is a test", output.toString());
    }

}