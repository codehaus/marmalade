// TODO Attach license header here.
package org.codehaus.marmalade.msp.finder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.marmalade.msp.InvalidHandlerStateException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

/**
 * @author jdcasey
 *
 * Created on Nov 26, 2004
 */
public class FileBasedScriptFinder
    implements ScriptFinder, ApplicationContextAware
{
    
    private String basePath;
    private PathInterpreter pathInterpreter;
    private ApplicationContext appContext;

    public void setBasePath(String basePath)
    {
        if(basePath.endsWith("/"))
        {
            this.basePath = basePath.substring(0, basePath.length() - 2);
        }
        else
        {
            this.basePath = basePath;
        }
    }
    
    public void setPathInterpreter(PathInterpreter pathInterpreter)
    {
        this.pathInterpreter = pathInterpreter;
    }

    public Reader getScript( HttpServletRequest request) 
    throws IOException
    {
        validateState();
        
        String path = request.getRequestURI();
        
        System.out.println("Request path: " + path);
        
        if(pathInterpreter != null)
        {
            path = pathInterpreter.interpret(path);
        }
        
        if(!path.startsWith("/"))
        {
            path = "/" + path;
        }
        
        System.out.println("Interpreted path: " + path);
        
        path = basePath + path;
        
        System.out.println("Full path: " + path);
        
        Reader result = null;
        if(appContext != null)
        {
            Resource scriptResource = appContext.getResource(path);
            result = new BufferedReader(new InputStreamReader(scriptResource.getInputStream()));
        }
        else
        {
            result = new BufferedReader(new FileReader(path));
        }
        
        System.out.println("Returning reader for file: " + path);
        
        return result;
    }

    private void validateState()
    {
        List stateViolations = new ArrayList();
        
        if(basePath == null)
        {
            stateViolations.add(new IllegalStateException("basePath must be specified on this scriptFinder instance."));
        }
        
        if(!stateViolations.isEmpty())
        {
            throw new InvalidHandlerStateException(stateViolations);
        }
    }

    public void setApplicationContext( ApplicationContext appContext ) throws BeansException
    {
        this.appContext = appContext;
    }

}
