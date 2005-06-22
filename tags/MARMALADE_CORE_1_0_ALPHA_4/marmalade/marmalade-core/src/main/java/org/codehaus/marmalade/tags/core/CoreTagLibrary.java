/* Created on Aug 20, 2004 */
package org.codehaus.marmalade.tags.core;

import org.codehaus.marmalade.metamodel.AbstractMarmaladeTagLibrary;

/**
 * @author jdcasey
 */
public class CoreTagLibrary
    extends AbstractMarmaladeTagLibrary
{
    public CoreTagLibrary()
    {
        registerTag( "catch", CatchTag.class );
        registerTag( "choose", ChooseTag.class );
        registerTag( "err", ErrTag.class );
        registerTag( "forEach", ForEachTag.class );
        registerTag( "if", IfTag.class );
        registerTag( "import", ImportTag.class );
        registerTag( "out", OutTag.class );
        registerTag( "remove", RemoveTag.class );
        registerTag( "script", ScriptTag.class );
        registerTag( "set", SetTag.class );
        registerTag( "taglibStrategy", TaglibResolutionStrategyTag.class );
        registerTag( "when", WhenTag.class );
    }
}