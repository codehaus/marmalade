/* Created on Jun 28, 2004 */
package org.codehaus.marmalade.compat.jelly;

import org.codehaus.marmalade.compat.jelly.metamodel.strategy.JellyCompatTaglibDefinitionStrategy;
import org.codehaus.marmalade.metamodel.strategy.LiteralClassDefinitionStrategy;
import org.codehaus.marmalade.metamodel.strategy.PassThroughTaglibDefinitionStrategy;
import org.codehaus.marmalade.metamodel.strategy.PrefixedDefFileDefinitionStrategy;
import org.codehaus.marmalade.metamodel.strategy.PrefixedTldDefinitionStrategy;
import org.codehaus.marmalade.metamodel.strategy.TaglibDefinitionStrategy;

/**
 * @author jdcasey
 */
public final class JellyCompatConstants
{
    public static final String JELLY_TAGLIB_PREFIX = "jelly";
    
    public static final String JELLY_XML_OUTPUT_CONTEXT_VARIABLE = "$$jelly-output$$";
    
    public static final TaglibDefinitionStrategy[] JELLY_INCLUSIVE_TAGLIB_DEF_STRATEGY =
        {
            new JellyCompatTaglibDefinitionStrategy(  ),
            new LiteralClassDefinitionStrategy(  ),
            new PrefixedTldDefinitionStrategy(  ),
            new PrefixedDefFileDefinitionStrategy(  ),
            new PassThroughTaglibDefinitionStrategy(  )
        };

    /** Constants class...don't instantiate.
     */
    private JellyCompatConstants(  )
    {
    }
}
