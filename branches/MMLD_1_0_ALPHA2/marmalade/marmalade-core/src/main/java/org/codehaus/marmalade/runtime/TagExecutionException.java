// TODO Attach license header here.
package org.codehaus.marmalade.runtime;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;

/** This is a general-purpose exceptions provided as a means for 
 * tag implementations to display errors that don't really warrant a
 * new exception type.
 * 
 * @author jdcasey
 *
 * Created on Dec 2, 2004
 */
public class TagExecutionException
    extends AbstractTagRelatedExecutionException
{

    public TagExecutionException( MarmaladeTagInfo tagInfo, String message )
    {
        super( tagInfo, message );
    }

    public TagExecutionException( MarmaladeTagInfo tagInfo, String message, Throwable cause )
    {
        super( tagInfo, message, cause );
    }

}
