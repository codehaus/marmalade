/* Created on Aug 22, 2004 */
package org.codehaus.marmalade.web;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jdcasey
 */
public interface ViewFinder
{
    public ViewSource find( HttpServletRequest request );
}