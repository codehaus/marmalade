/* Created on Aug 20, 2004 */
package org.codehaus.marmalade.lb.taglib;

import org.codehaus.marmalade.lb.discovery.LateBindingTagLibraryResolver;
import org.codehaus.marmalade.lb.metamodel.LateBoundTagLibrary;
import org.codehaus.marmalade.lb.model.LateBoundTagFactory;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.parsing.ParserHint;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class LateBoundTagLibraryTag
    extends AbstractMarmaladeTag
    implements LateBoundTagLibraryOwner
{
    public static final String SCHEME_ATTRIBUTE = "scheme";

    public static final String TAGLIB_ATTRIBUTE = "name";

    private LateBindingTagLibraryResolver resolver;

    private LateBoundTagLibrary library;

    public LateBoundTagLibraryTag( LateBindingTagLibraryResolver resolver )
    {
        this.resolver = resolver;
    }

    protected void doExecute( MarmaladeExecutionContext context ) throws MarmaladeExecutionException
    {
        String scheme = (String) requireTagAttribute( SCHEME_ATTRIBUTE, String.class, context );
        String taglib = (String) requireTagAttribute( TAGLIB_ATTRIBUTE, String.class, context );

        library = new LateBoundTagLibrary();

        processChildren( context );

        resolver.registerTagLibrary( scheme, taglib, library );
    }

    public void registerTag( String tagName, ParserHint hint, LateBoundTagFactory factory )
    {
        library.registerTag( tagName, hint, factory );
    }
}