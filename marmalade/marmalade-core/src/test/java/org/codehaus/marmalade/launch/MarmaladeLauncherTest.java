package org.codehaus.marmalade.launch;

import org.codehaus.marmalade.discovery.PrefixedDefFileResolutionStrategy;

import java.io.StringReader;
import java.util.Map;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class MarmaladeLauncherTest
    extends TestCase
{

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