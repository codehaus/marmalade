/* Created on Aug 22, 2004 */
package org.codehaus.marmalade.web.config;

import org.codehaus.marmalade.web.ViewFinder;

import java.util.LinkedList;
import java.util.List;

/**
 * @author jdcasey
 */
public class ViewServletConfig
{
    private ViewFinder viewFinder;

    private List tagStrategies = new LinkedList();

    public List getTagStrategies()
    {
        return tagStrategies;
    }

    public void setTagStrategies( List tagStrategies )
    {
        this.tagStrategies = tagStrategies;
    }

    public ViewFinder getViewFinder()
    {
        return viewFinder;
    }

    public void setViewFinder( ViewFinder viewFinder )
    {
        this.viewFinder = viewFinder;
    }
}