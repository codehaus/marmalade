package org.codehaus.marmalade.compat.ant.model;

import org.codehaus.marmalade.compat.ant.discovery.AntBasedDiscoveryStrategy;
import org.codehaus.marmalade.discovery.PrefixedDefFileResolutionStrategy;
import org.codehaus.marmalade.launch.MarmaladeLaunchException;
import org.codehaus.marmalade.launch.MarmaladeLauncher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class AntAdapterTagTest
    extends TestCase
{
    
    private static final String SCRIPTS_RESOURCE = "scripts";
    private static final String UNIT_TESTS_RESOURCE = "unitTests";
    
    private File resourcesDirectory;
    private Properties resourceSubdirs;

    public void testShouldRunSimpleAntTag() throws MarmaladeLaunchException
    {
        String script = "<core:script xmlns:core=\"marmalade:core\">" +
                "<ant:echo xmlns:ant=\"ant:ant\">This is a test</ant:echo>" +
                "</core:script>";

        StringWriter output = new StringWriter();
        PrintWriter pWriter = new PrintWriter(output);
        
        new MarmaladeLauncher().withAdditionalTaglibDefinitionStrategy( new AntBasedDiscoveryStrategy() )
                               .withAdditionalTaglibDefinitionStrategy(new PrefixedDefFileResolutionStrategy())
                               .withInputScriptString( script, "Test script" )
                               .withOutWriter(pWriter)
                               .run();
        
        assertEquals("This is a test", output.toString());
    }
    
    public void testShouldMkdirAndDeleteDir() throws MarmaladeLaunchException
    {
        String script = "<core:script xmlns:core=\"marmalade:core\" xmlns:ant=\"ant:ant\">" +
                "<ant:mkdir dir=\"target/test-mkdir-and-delete-dir\" />" +
                "<ant:delete dir=\"target/test-mkdir-and-delete-dir\" />" +
                "</core:script>";

        StringWriter output = new StringWriter();
        PrintWriter pWriter = new PrintWriter(output);
        
        new MarmaladeLauncher().withAdditionalTaglibDefinitionStrategy( new AntBasedDiscoveryStrategy() )
                               .withAdditionalTaglibDefinitionStrategy(new PrefixedDefFileResolutionStrategy())
                               .withInputScriptString( script, "Test script" )
                               .withOutWriter(pWriter)
                               .run();
        
        File testDir = new File( "target/test-mkdir-and-delete-dir" );
        
        assertFalse( testDir.exists() );
    }

    public void testShouldMkdirAndCopyTestResources() throws MarmaladeLaunchException
    {
        String script = "<core:script xmlns:core=\"marmalade:core\" xmlns:ant=\"ant:ant\">" +
                "<ant:mkdir dir=\"target/test-mkdir-and-copy\" />" +
                "<ant:delete dir=\"target/test-mkdir-and-delete-dir\" />" +
                "</core:script>";

        StringWriter output = new StringWriter();
        PrintWriter pWriter = new PrintWriter(output);
        
        new MarmaladeLauncher().withAdditionalTaglibDefinitionStrategy( new AntBasedDiscoveryStrategy() )
                               .withAdditionalTaglibDefinitionStrategy(new PrefixedDefFileResolutionStrategy())
                               .withInputScriptString( script, "Test script" )
                               .withOutWriter(pWriter)
                               .run();
        
        File testDir = new File( "target/test-mkdir-and-delete-dir" );
        
        assertFalse( testDir.exists() );
    }
    
    public void testShouldExecuteJUnitTestsFromScript() throws Exception
    {
        File scriptDir = findResourceDirectory( SCRIPTS_RESOURCE );
        
        File script = new File( scriptDir, "testShouldExecuteJUnitTestsFromScript.mmld" );
        
        File testsDir = findResourceDirectory( UNIT_TESTS_RESOURCE );
        
        String junitlibPath = findJUnitLibPath();
        
        Map context = new HashMap();
        context.put("basedir", new File(".").getAbsolutePath());
        context.put("junit-lib", junitlibPath);
        context.put("junit-sources", testsDir.getAbsolutePath());
        
        StringWriter output = new StringWriter();
        PrintWriter pWriter = new PrintWriter(output);
        
        try
        {
            new MarmaladeLauncher().withAdditionalTaglibDefinitionStrategy( new AntBasedDiscoveryStrategy() )
                .withAdditionalTaglibDefinitionStrategy( new PrefixedDefFileResolutionStrategy() )
                .withVariables( context ).withInputFile( script ).withOutWriter( pWriter ).run();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "Should have succeeded." );
        }
    }
    
    private String findJUnitLibPath()
    {
        String jUnitClassName = TestCase.class.getName().replace('.', '/') + ".class";
        
        ClassLoader cloader = getClass().getClassLoader();
        
        URL resource = cloader.getResource( jUnitClassName );
        
        String result = null;
        
        if ( resource != null )
        {
            String path = resource.getPath();
            
            result = path.substring( 0, path.length() - jUnitClassName.length() );
        }
        
        return result;
    }

    private File findResourceDirectory( String resourceName ) throws Exception
    {
        if ( resourceSubdirs == null )
        {
            ClassLoader cloader = getClass().getClassLoader();
            
            URL resource = cloader.getResource( "ant-compat.resources.properties" );
            
            if ( resource != null )
            {
                File propsfile = new File( resource.getPath() );
                
                this.resourcesDirectory = propsfile.getParentFile();
                
                this.resourceSubdirs = new Properties();
                
                FileInputStream inStream = new FileInputStream( propsfile );
                
                resourceSubdirs.load( inStream );
            }
        }
        
        String resourceSubdir = resourceSubdirs.getProperty( resourceName );
        
        return new File( resourcesDirectory, resourceSubdir );
    }
}