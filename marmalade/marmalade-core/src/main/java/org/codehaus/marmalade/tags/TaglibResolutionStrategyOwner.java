/* Created on Aug 20, 2004 */
package org.codehaus.marmalade.tags;

import org.codehaus.marmalade.discovery.TaglibResolutionStrategy;

/**
 * @author jdcasey
 */
public interface TaglibResolutionStrategyOwner
{
    public void addTaglibResolutionStrategy( TaglibResolutionStrategy strategy );
}