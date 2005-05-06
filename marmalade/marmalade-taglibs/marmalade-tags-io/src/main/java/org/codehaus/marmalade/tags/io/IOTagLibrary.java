package org.codehaus.marmalade.tags.io;

import org.codehaus.marmalade.metamodel.AbstractMarmaladeTagLibrary;

/**
 * @author jdcasey
 */
public class IOTagLibrary
    extends AbstractMarmaladeTagLibrary
{

    public IOTagLibrary()
    {
        registerTag("file", FileTag.class);
    }
    
}
