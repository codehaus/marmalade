package org.codehaus.marmalade.metamodel;

import org.codehaus.marmalade.MarmaladeException;

/**
 * @author jdcasey
 */
public class TaglibResolutionException
    extends MarmaladeException
{

    public TaglibResolutionException( String prefix, String taglib, String message )
    {
        super( "[prefix: " + prefix + ", taglib: " + taglib + "] " + message );
    }

    public TaglibResolutionException( String prefix, String taglib, String message, Throwable cause )
    {
        super( "[prefix: " + prefix + ", taglib: " + taglib + "] " + message, cause );
    }

}
