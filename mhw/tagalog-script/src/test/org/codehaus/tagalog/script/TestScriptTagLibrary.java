/*
 * $Id$
 */

package org.codehaus.tagalog.script;

import org.codehaus.tagalog.script.tags.ScriptTagLibrary;

/**
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public class TestScriptTagLibrary extends ScriptTagLibrary {
    public static final String NS_URI
        = "tagalog:test-script";

    public TestScriptTagLibrary() {
        super();
        registerTag("collect", CollectTag.class);
    }
}
