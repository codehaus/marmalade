/* Created on Jun 28, 2004 */
package org.codehaus.marmalade.compat.jelly.model;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.jelly.JellyContext;
import org.apache.commons.jelly.JellyException;
import org.apache.commons.jelly.JellyTagException;
import org.apache.commons.jelly.Script;
import org.apache.commons.jelly.XMLOutput;
import org.codehaus.marmalade.compat.jelly.JellyCompatConstants;
import org.codehaus.marmalade.compat.jelly.JellyCompatUncheckedException;
import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.xml.sax.SAXException;

/**
 * @author jdcasey
 */
public class MarmaladeCompatBodyScript implements Script {

    private final List bodyItems;
    private final MarmaladeExecutionContext context;
    private final JellyCompatMarmaladeTag owner;
    private final ExpressionEvaluator el;

    public MarmaladeCompatBodyScript(JellyCompatMarmaladeTag owner, MarmaladeExecutionContext context, ExpressionEvaluator el, List bodyItems) {
        this.owner = owner;
        this.context = context;
        this.el = el;
        this.bodyItems = bodyItems;
    }

    public Script compile() throws JellyException {
        return this;
    }

    public void run(JellyContext jellyContext, XMLOutput output) throws JellyTagException {
        XMLOutput oldOut = null;
        try {
            oldOut = (XMLOutput)context.getVariable(JellyCompatConstants.JELLY_XML_OUTPUT_CONTEXT_VARIABLE, null);
            context.setVariable(JellyCompatConstants.JELLY_XML_OUTPUT_CONTEXT_VARIABLE, output);
            
            for (Iterator it = bodyItems.iterator(); it.hasNext();) {
                Object item = it.next();
                if (item instanceof MarmaladeTag) {
                    MarmaladeTag tag = (MarmaladeTag) item;
                    tag.execute(context);
                }
                else {
                    String expression = String.valueOf(item);
                    expression = owner.formatWhitespace(expression, context);
                    
                    String content = String.valueOf(el.evaluate(expression, context.unmodifiableVariableMap(), Object.class));
                    
                    try {
                        output.write(content);
                    }
                    catch (SAXException e) {
                        throw new JellyCompatUncheckedException("Error writing to XMLOutput.", e);
                    }
                }
            }
        }
        catch (MarmaladeExecutionException e) {
            throw new JellyTagException("Error running jelly tag body.", e);
        }
        finally {
            if(oldOut != null) {
                context.setVariable(JellyCompatConstants.JELLY_XML_OUTPUT_CONTEXT_VARIABLE, oldOut);
            }
            else {
                context.removeVariable(JellyCompatConstants.JELLY_XML_OUTPUT_CONTEXT_VARIABLE);
            }
        }
    }

}
