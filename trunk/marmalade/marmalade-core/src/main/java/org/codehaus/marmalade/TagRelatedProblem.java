// TODO Attach license header here.
package org.codehaus.marmalade;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;

/**
 * @author jdcasey
 *
 * Created on Dec 2, 2004
 */
public interface TagRelatedProblem
{
    
    public MarmaladeTagInfo getOffendingTagInfo();
    
    public String getSourceFile();
    
    public int getSourceLine();

}
