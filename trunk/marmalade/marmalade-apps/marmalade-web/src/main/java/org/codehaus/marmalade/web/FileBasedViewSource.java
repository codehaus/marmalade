/* Created on Aug 22, 2004 */
package org.codehaus.marmalade.web;

import org.codehaus.marmalade.parsing.DefaultParsingContext;
import org.codehaus.marmalade.parsing.MarmaladeParsingContext;
import org.codehaus.marmalade.util.RecordingReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author jdcasey
 */
public class FileBasedViewSource
    implements ViewSource
{
    private String viewPath;

    private final File sourceFile;

    public FileBasedViewSource( String sourcePath, File sourceFile )
    {
        this.viewPath = sourcePath;
        this.sourceFile = sourceFile;
    }

    public MarmaladeParsingContext openContext() throws IOException
    {
        MarmaladeParsingContext ctx = new DefaultParsingContext();

        ctx.setInputLocation( viewPath );

        ctx.setInput( new RecordingReader( new BufferedReader( new FileReader( sourceFile ) ) ) );

        return ctx;
    }
}