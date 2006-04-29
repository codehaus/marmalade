package org.codehaus.marmalade.launch;

import java.io.StringReader;
import java.util.Collections;
import java.util.Map;

import junit.framework.TestCase;

import org.codehaus.marmalade.metamodel.ScriptBuilder;
import org.codehaus.marmalade.model.MarmaladeScript;
import org.codehaus.marmalade.tags.test.TestTagLibrary;

/**
 * @author jdcasey
 */
public class MarmaladeLauncherTest extends TestCase {

	public void testShouldFindParsedBuilderWithoutRunning()
			throws MarmaladeLaunchException {
		String script = "<set key=\"var\" value=\"${valkey}\" extern=\"true\"/>";

		MarmaladeLauncher launcher = new MarmaladeLauncher()
				.withTaglibDefinitionStrategies(Collections.EMPTY_LIST)
				.withDefaultTagLibrary(new TestTagLibrary()).withInputLocation(
						"<embedded test>").withInput(new StringReader(script));

		ScriptBuilder builder = launcher.getScriptBuilder();

		assertNotNull(builder);
	}

	public void testShouldFindBuiltScriptInstanceWithoutRunning()
			throws MarmaladeLaunchException {
		String scriptSource = "<set key=\"var\" value=\"${valkey}\" extern=\"true\"/>";

		MarmaladeLauncher launcher = new MarmaladeLauncher()
				.withTaglibDefinitionStrategies(Collections.EMPTY_LIST)
				.withDefaultTagLibrary(new TestTagLibrary()).withInputLocation(
						"<embedded test>").withInput(
						new StringReader(scriptSource));

		MarmaladeScript script = launcher.getMarmaladeScript();

		assertNotNull(script);
	}

	public void testShouldExecuteSimpleScriptWithNoBellsOrWhistles()
			throws MarmaladeLaunchException {
		String script = "<set key=\"var\" value=\"${valkey}\" extern=\"true\"/>";

		MarmaladeLauncher launcher = new MarmaladeLauncher()
				.withTaglibDefinitionStrategies(Collections.EMPTY_LIST)
				.withDefaultTagLibrary(new TestTagLibrary()).withInputLocation(
						"<embedded test>").withInput(new StringReader(script));

		Map vars = launcher.withVariable("valkey", "value").run();

		assertEquals("value", vars.get("var"));
	}

}