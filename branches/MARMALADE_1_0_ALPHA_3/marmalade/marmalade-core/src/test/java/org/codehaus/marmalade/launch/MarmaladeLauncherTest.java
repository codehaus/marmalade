package org.codehaus.marmalade.launch;

import org.codehaus.marmalade.discovery.PrefixedDefFileResolutionStrategy;
import org.codehaus.marmalade.metamodel.ScriptBuilder;
import org.codehaus.marmalade.model.MarmaladeScript;

import java.io.StringReader;
import java.util.Map;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class MarmaladeLauncherTest
    extends TestCase
{
    
    public void testShouldFindParsedBuilderWithoutRunning()
    throws MarmaladeLaunchException
    {
        String script = "<set var=\"var\" value=\"${valkey}\" extern=\"true\" xmlns=\"marmalade:core\"/>";

        MarmaladeLauncher launcher = new MarmaladeLauncher().withInputLocation( "<embedded test>" )
                                          .withInput( new StringReader( script ) );
        
        ScriptBuilder builder = launcher.getScriptBuilder();

        assertNotNull( builder );
    }

    public void testShouldFindBuiltScriptInstanceWithoutRunning()
    throws MarmaladeLaunchException
    {
        String scriptSource = "<set var=\"var\" value=\"${valkey}\" extern=\"true\" xmlns=\"marmalade:core\"/>";

        MarmaladeLauncher launcher = new MarmaladeLauncher().withInputLocation( "<embedded test>" )
                                          .withInput( new StringReader( scriptSource ) );
        
        MarmaladeScript script = launcher.getMarmaladeScript();

        assertNotNull( script );
    }

    public void testShouldExecuteSimpleScriptWithNoBellsOrWhistles() throws MarmaladeLaunchException
    {
        String script = "<set var=\"var\" value=\"${valkey}\" extern=\"true\" xmlns=\"marmalade:core\"/>";

        Map vars = new MarmaladeLauncher().withInputLocation( "<embedded test>" )
                                          .withInput( new StringReader( script ) )
                                          .withVariable( "valkey", "value" )
                                          .run();

        assertEquals( "value", vars.get( "var" ) );
    }

    public void testShouldExecuteSimpleScriptWithRestrictedTaglibDiscovery() throws MarmaladeLaunchException
    {
        String script = "<set var=\"var\" value=\"${valkey}\" extern=\"true\" xmlns=\"marmalade:core\"/>";

        Map vars = new MarmaladeLauncher().withInputLocation( "<embedded test>" )
                                          .withInput( new StringReader( script ) )
                                          .withVariable( "valkey", "value" )
                                          .withAdditionalTaglibDefinitionStrategy( new PrefixedDefFileResolutionStrategy() )
                                          .run();

        assertEquals( "value", vars.get( "var" ) );
    }

}