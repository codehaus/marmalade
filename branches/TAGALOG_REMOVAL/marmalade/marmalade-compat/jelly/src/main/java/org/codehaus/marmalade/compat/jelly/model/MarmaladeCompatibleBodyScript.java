/* Created on Jun 28, 2004 */
package org.codehaus.marmalade.compat.jelly.model;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.jelly.JellyContext;
import org.apache.commons.jelly.JellyException;
import org.apache.commons.jelly.JellyTagException;
import org.apache.commons.jelly.Script;
import org.apache.commons.jelly.XMLOutput;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class MarmaladeCompatibleBodyScript implements Script {

    private final List bodyItems;
    private final MarmaladeExecutionContext context;
    private final JellyCompatMarmaladeTag owner;

    public MarmaladeCompatibleBodyScript(JellyCompatMarmaladeTag owner, MarmaladeExecutionContext context, List bodyItems) {
        this.owner = owner;
        this.context = context;
        this.bodyItems = bodyItems;
    }

    public Script compile() throws JellyException {
        return this;
    }

    public void run(JellyContext jellyContext, XMLOutput output) throws JellyTagException {
        try {
            for (Iterator it = bodyItems.iterator(); it.hasNext();) {
                Object item = it.next();
                if (item instanceof MarmaladeTag) {
                    MarmaladeTag tag = (MarmaladeTag) item;
                    tag.execute(context);
                }
                else {
                    String content = String.valueOf(item);
                    content = owner.formatWhitespace(content, context);
                    context.getOutWriter().print(content);
                }
            }
        }
        catch (MarmaladeExecutionException e) {
            throw new JellyTagException("Error running jelly tag body.", e);
        }
    }

}
