/* Created on Aug 22, 2004 */
package org.codehaus.marmalade.web;

import javax.servlet.http.HttpServletRequest;

import java.io.File;

/**
 * @author jdcasey
 */
public class PathBasedViewFinder
    implements ViewFinder
{
    private final String viewDirectory;

    private String pathReplace;

    private String pathSearch;

    public PathBasedViewFinder( String viewDirectory )
    {
        String dir = viewDirectory.replace( '\\', '/' );

        if ( !dir.endsWith( "/" ) )
        {
            dir += "/";
        }

        this.viewDirectory = dir;
    }

    public void setPathReplace( String pathReplace )
    {
        this.pathReplace = pathReplace;
    }

    public void setPathSearch( String pathSearch )
    {
        this.pathSearch = pathSearch;
    }

    public ViewSource find( HttpServletRequest request )
    {
        String path = request.getRequestURI();

        String pathFormatted = path;

        if ( (pathSearch != null) && (pathReplace != null) )
        {
            pathFormatted = pathFormatted.replaceAll( pathSearch, pathReplace );
        }

        String scriptLocation = viewDirectory + pathFormatted;

        return new FileBasedViewSource( path, new File( scriptLocation ) );
    }
}