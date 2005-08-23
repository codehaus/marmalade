package org.codehaus.marmalade.compat.ant.util;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.UnknownElement;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author jdcasey
 */
public final class AntUtils
{
    private static final String MARMALADE_EXEC_TARGET_NAME = "marmalade-exec-target";
    
    private static Map PROJECT_REFS = new WeakHashMap();
    
    private AntUtils()
    {
    }

    public static Project getContextProject( MarmaladeExecutionContext context )
    {
        Project project = null;
        
        synchronized(context)
        {
            Reference ref = (Reference)PROJECT_REFS.get(context);
            if(ref != null)
            {
                project = (Project) ref.get();
            }
            
            if(project == null)
            {
                project = new Project();
                project.init();
                
                Target executionTarget = new Target();
                executionTarget.setName(MARMALADE_EXEC_TARGET_NAME);
                
                project.addTarget(executionTarget);
                
                PROJECT_REFS.put(context, new WeakReference(project));
            }
        }
        
        return project;
    }

    public static Target getExecutionTarget( Project project )
    {
        return (Target) project.getTargets().get(MARMALADE_EXEC_TARGET_NAME);
    }
    
    public static void assignChild( Object parent, Object child )
    {
        // Nested element
        System.out.println( "Parent is of type: " + parent.getClass().getName() );
        System.out.println( "Child is of type: " + child.getClass().getName() );
        
        if ( ( parent instanceof UnknownElement ) && ( child instanceof UnknownElement ) )
        {
            UnknownElement parentUnknown = (UnknownElement) parent;
            
            parentUnknown.addChild( (UnknownElement) child );
        }
        else
        {
            throw new IllegalArgumentException( "Either parent or child is not of type UnknownElement. This is not currently supported." );
        }
    }

}
