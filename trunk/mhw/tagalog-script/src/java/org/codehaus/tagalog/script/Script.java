/*
 * $Id$
 */

package org.codehaus.tagalog.script;

import java.util.Map;

/**
 * Representation of a script, which attaches a name to a
 * {@link CompoundStatement}.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class Script {
    private String name;

    private CompoundStatement body;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBody(CompoundStatement body) {
        this.body = body;
    }

    public void execute(Map context) throws Exception {
        body.execute(context);
    }
}
