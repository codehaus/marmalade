/* Created on Jul 13, 2004 */
package org.apache.commons.jelly.test;

import org.apache.commons.jelly.JellyTagException;
import org.apache.commons.jelly.MissingAttributeException;
import org.apache.commons.jelly.Tag;
import org.apache.commons.jelly.TagSupport;
import org.apache.commons.jelly.XMLOutput;

/**
 * @author jdcasey
 */
public class MarmaladeTagWrapperTestTag extends TagSupport {
    
    private boolean attributeWasSet = false;
    
    public void setAttribute(String attribute) {
        attributeWasSet = true;
    }

    public void doTag(XMLOutput output) throws MissingAttributeException, JellyTagException {
        getContext().setVariable("doTagRan", Boolean.TRUE);
        getContext().setVariable("attributeWasSet", Boolean.valueOf(attributeWasSet));
        getContext().setVariable("thisTag", this);
        
        if(getParent() != null) {
            getContext().setVariable("parentExists", Boolean.TRUE);
        }
        
        invokeBody(output);
    }

}
