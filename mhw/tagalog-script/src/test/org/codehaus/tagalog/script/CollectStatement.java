/*
 * $Id$
 */

package org.codehaus.tagalog.script;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public class CollectStatement implements Statement {
    public String content;

    public void setContent(String content) {
        this.content = content;
    }

    public void execute(Map context) throws Exception {
        List collection = (List) context.get("collection");
        collection.add(content);
    }
}
