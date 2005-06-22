/* Created on Jul 13, 2004 */
package org.apache.commons.jelly.test;

import org.apache.commons.jelly.JellyTagException;
import org.apache.commons.jelly.MissingAttributeException;
import org.apache.commons.jelly.TagSupport;
import org.apache.commons.jelly.XMLOutput;

/**
 * @author jdcasey
 */
public class ParentTestTag extends TagSupport {
    
    public void doTag(XMLOutput output) throws MissingAttributeException, JellyTagException {
        invokeBody(output);
    }

}
