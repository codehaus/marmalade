package org.codehaus.marmalade.tags.lang;

import org.codehaus.marmalade.metamodel.AbstractMarmaladeTagLibrary;

/**
 * @author jdcasey
 *
 * Created on Feb 7, 2005
 */
public class LangTagLibrary
    extends AbstractMarmaladeTagLibrary
{

    /** Added: Feb 7, 2005 by jdcasey
     */
    public LangTagLibrary()
    {
        registerTag("ref", RefTag.class);
        registerTag("value", ValueTag.class);
        registerTag("name", NameTag.class);
        registerTag("key", KeyTag.class);
    }
}
