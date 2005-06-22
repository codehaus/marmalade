/* Created on Aug 24, 2004 */
package org.codehaus.marmalade.lb.discovery;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class LateBindingResolutionStrategyTest
    extends TestCase
{
    
    public void testShouldConstructWithNoParams() {
        LateBindingResolutionStrategy strategy = new LateBindingResolutionStrategy();
    }

    public void testShouldResolveLBBindTagLibWithoutRegistration() {
        LateBindingResolutionStrategy strategy = new LateBindingResolutionStrategy();
        assertNotNull("lb:bind library should be resolved", strategy.resolve("lb", "bind", getClass().getClassLoader()));
    }
    
    public void testShouldNotResolveOtherTagLibWithoutRegistration() {
        LateBindingResolutionStrategy strategy = new LateBindingResolutionStrategy();
        assertNull("lb:test library should not be resolved", strategy.resolve("lb", "test", getClass().getClassLoader()));
    }
    
}
