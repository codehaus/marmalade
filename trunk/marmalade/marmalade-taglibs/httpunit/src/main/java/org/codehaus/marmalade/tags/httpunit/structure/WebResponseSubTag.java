/* Created on Jun 14, 2004 */

package org.codehaus.marmalade.tags.httpunit.structure;

import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import com.meterware.httpunit.WebResponse;

/**
 * @author jdcasey
 */
public interface WebResponseSubTag
{
  WebResponse getResponse(MarmaladeExecutionContext context) throws MarmaladeExecutionException;
}