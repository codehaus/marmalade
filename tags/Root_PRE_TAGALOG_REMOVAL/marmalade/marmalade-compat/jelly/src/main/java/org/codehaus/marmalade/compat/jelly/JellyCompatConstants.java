/* Created on Jun 28, 2004 */
package org.codehaus.marmalade.compat.jelly;

import org.codehaus.marmalade.compat.jelly.metamodel.strategy.JellyCompatTaglibDefinitionStrategy;
import org.codehaus.marmalade.metamodel.strategy.LiteralClassDefinitionStrategy;
import org.codehaus.marmalade.metamodel.strategy.PrefixedDefFileDefinitionStrategy;
import org.codehaus.marmalade.metamodel.strategy.PrefixedTldDefinitionStrategy;
import org.codehaus.marmalade.metamodel.strategy.TaglibDefinitionStrategy;

/**
 * @author jdcasey
 */
public final class JellyCompatConstants
{
    public static final TaglibDefinitionStrategy[] JELLY_INCLUSIVE_TAGLIB_DEF_STRATEGY =
        {
            new JellyCompatTaglibDefinitionStrategy(  ),
            new LiteralClassDefinitionStrategy(  ),
            new PrefixedTldDefinitionStrategy(  ),
            new PrefixedDefFileDefinitionStrategy(  )
        /* JDC 6/27/2004:
         *
         * Disabled until PT can be fixed...this may require help from
         * MHW/taglog.
         *
         * ,new PassThroughTaglibDefinitionStrategy(  )
         */
        };

    /** Constants class...don't instantiate.
     */
    private JellyCompatConstants(  )
    {
    }
}
