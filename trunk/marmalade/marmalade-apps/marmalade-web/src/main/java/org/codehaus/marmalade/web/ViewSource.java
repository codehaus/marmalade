/* Created on Aug 22, 2004 */
package org.codehaus.marmalade.web;

import org.codehaus.marmalade.parsing.MarmaladeParsingContext;

import java.io.IOException;

/**
 * @author jdcasey
 */
public interface ViewSource
{
    public MarmaladeParsingContext openContext() throws IOException;
}