/* Created on Jun 29, 2004 */
package org.codehaus.marmalade.metamodel;

import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.parsetime.MarmaladeParsetimeException;

/**
 * @author jdcasey
 */
public interface TagFactory {
    
    MarmaladeTag createTag(MarmaladeParsingContext context) throws MarmaladeParsetimeException;

}
