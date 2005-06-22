/* Created on Aug 20, 2004 */
package org.codehaus.marmalade.lb.taglib;

import org.codehaus.marmalade.lb.LateBindingConstants;
import org.codehaus.marmalade.lb.discovery.LateBindingTagLibraryResolver;
import org.codehaus.marmalade.metamodel.AbstractMarmaladeTagLibrary;

/**
 * @author jdcasey
 */
public class LateBindingDefinitionTagLibrary
    extends AbstractMarmaladeTagLibrary
{
    public static final String LB_DEFINITION_TAGLIB_URI = LateBindingConstants.LATE_BINDING_PREFIX + ":bind";

    private final LateBindingTagLibraryResolver resolver;

    public LateBindingDefinitionTagLibrary( LateBindingTagLibraryResolver resolver )
    {
        this.resolver = resolver;

        registerTag( "library", LateBoundTagLibraryTag.class );
        registerTag( "tag", LooseMarmaladeTagTag.class );
        registerTag( "bean", BeanTag.class );
        registerTag( "constructorArg", ConstructorArgTag.class );
        registerTag( "methodParam", MethodParamTag.class );
        registerTag( "property", PropertyTag.class );
    }
}