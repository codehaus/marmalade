package org.codehaus.marmalade.tags.io;

import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.TagExecutionException;
import org.codehaus.plexus.util.IOUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author jdcasey
 */
public class FileTag
    extends AbstractMarmaladeTag
{
    
    public static final String PATH_ATTRIBUTE = "path";
    public static final String MKDIRS_ATTRIBUTE = "mkdirs";
    
    private File file;
    
    public File getFile()
    {
        return file;
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        processChildren(context);
        
        this.file = requireTagAttributeAsFile(PATH_ATTRIBUTE, context, false).getAbsoluteFile();
        
        Boolean mkdirs = (Boolean) getAttributes().getValue(MKDIRS_ATTRIBUTE, Boolean.class, context);
        
        String content = (String) getBody(context, String.class);
        
        File dir = file.getParentFile();
        if(!dir.exists())
        {
            if(Boolean.TRUE == mkdirs)
            {
                dir.mkdirs();
            }
            else
            {
                throw new TagExecutionException(getTagInfo(), "Cannot write file: \'" + file + "\' because parent directory does not exist. Try specifying \'mkdirs=\"true\"\'.");
            }
        }
        
        FileWriter writer = null;
        
        try
        {
            writer = new FileWriter(file);
            writer.write(content);
        }
        catch ( IOException e )
        {
            throw new TagExecutionException(getTagInfo(), "Error writing file.", e);
        }
        finally
        {
            IOUtil.close(writer);
        }
    }
}
