/* Created on Jun 22, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class RedirectTagTest extends TestCase {
    
    public void testShouldFailBecauseOfMarmaladeIncompatibility() {
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        
        RedirectTag tag = new RedirectTag(mti);
        
        try {
            tag.execute(new DefaultContext());
            fail("Should fail because of marmalade inability to redirect.");
        }
        catch(MarmaladeExecutionException e) {
            // should fail because redirect tag doesn't have a real impl.
        }
    }

}
