/* Created on Jul 13, 2004 */
package org.apache.commons.jelly.test;

import org.apache.commons.jelly.TagLibrary;

/**
 * @author jdcasey
 */
public class MarmaladeTaglibWrapperCompatTestTagLibrary extends TagLibrary{

    public MarmaladeTaglibWrapperCompatTestTagLibrary() {
        registerTag("test", MarmaladeTagWrapperTestTag.class);
        registerTag("parentTest", ParentTestTag.class);
        registerTag("childTest", ChildTestTag.class);
    }

}
