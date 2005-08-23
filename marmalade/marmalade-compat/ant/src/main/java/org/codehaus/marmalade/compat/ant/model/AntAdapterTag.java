package org.codehaus.marmalade.compat.ant.model;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Location;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.apache.tools.ant.PropertyHelper;
import org.apache.tools.ant.RuntimeConfigurable;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.UnknownElement;
import org.codehaus.marmalade.compat.ant.util.AntUtils;
import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeAttribute;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.monitor.event.context.ContextEventMonitor;
import org.codehaus.marmalade.monitor.log.CommonLogLevels;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import java.io.PrintWriter;
import java.io.Reader;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;

/**
 * @author jdcasey
 */
public class AntAdapterTag
    extends AbstractMarmaladeTag
{

    /** For internal use ONLY. This may break with subsequent versions of Ant. */
    private static final String ANT_TYPE = "ant-type";

    /** For internal use ONLY. This may break with subsequent versions of Ant. */
    private static final String PROPERTY_HELPER_REF_ID = "ant.PropertyHelper";

    /** For internal use ONLY. This may break with subsequent versions of Ant. */
    private static final String ANT_TAGLIB_NS = "ant";

    private RuntimeConfigurable runtimeConfigWrapper;

    public AntAdapterTag( MarmaladeTagInfo tagInfo )
    {
        setTagInfo( tagInfo );
    }

    protected void doExecute( MarmaladeExecutionContext context ) throws MarmaladeExecutionException
    {
        Project project = AntUtils.getContextProject( context );

        // Try to isolate the copied code from Ant's ProjectHelper2...
        buildAntObject( context );

        AntAdapterTag parentTag = (AntAdapterTag) getAncestor( AntAdapterTag.class );

        Target target = AntUtils.getExecutionTarget( project );

        // if we're the top-level ant-adapted tag, then we'll just execute the
        // target.
        if ( parentTag == null )
        {
            project.addReference( PROPERTY_HELPER_REF_ID, new ContextPropertyHelper( context, getExpressionEvaluator() ) );

            target.execute();
        }
    }

    // TODO: We need to be dealing in terms of realObject() from the RuntimeConfigurable...but this
    // means putting in place some more advanced reflective algorithms for setting subelements on parent
    // tags.
    private void buildAntObject( MarmaladeExecutionContext context ) throws ExpressionEvaluationException
    {
        Project project = AntUtils.getContextProject( context );
        Target target = AntUtils.getExecutionTarget( project );

        AntAdapterTag parentTag = (AntAdapterTag) getAncestor( AntAdapterTag.class );

        String tag = getTagInfo().getElement();
        String qname = getTagInfo().getTaglib() + ":" + tag;
        
        RuntimeConfigurable parentWrapper = null;
        if ( parentTag != null )
        {
            parentWrapper = parentTag.getRuntimeConfigWrapper();
        }

        // ********************************************************************
        // From here down to next marker, this code is adapted directly from
        // org.apache.tools.ant.helper.ProjectHelper2.
        //
        // For strategy, see google "Underpants Gnomes"...what follows is
        // Phase Two
        //
        // vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv

        Object parent = null;

        if ( parentWrapper != null )
        {
            parent = parentWrapper.getProxy();
        }

        /*
         * UnknownElement is used for tasks and data types - with delayed eval
         */
        UnknownElement task = new UnknownElement( tag );
        task.setProject( project );
        
        String taglib = getTagInfo().getTaglib();
        if(!ANT_TAGLIB_NS.equals(taglib))
        {
            task.setNamespace(taglib);
        }
        
        task.setQName( qname );
        task.setTaskType( ProjectHelper.genComponentName( task.getNamespace(), tag ) );
        task.setTaskName( qname );

        Location location = new TagInfoLocation( getTagInfo() );

        task.setLocation( location );
        task.setOwningTarget( target );

        MarmaladeAttributes attributes = getAttributes();

        String id = (String) attributes.getValue( "id", String.class, context );

        if ( id != null )
        {
            project.addReference( id, task );
        }

        if ( parent != null )
        {
            AntUtils.assignChild(parent, task);
        }
        else
        {
            // Task included in a target ( including the default one ).
            target.addTask( task );
        }

        // container.addTask(task);
        // This is a nop in UE: task.init();

        this.runtimeConfigWrapper = new RuntimeConfigurable( task, task.getTaskName() );

        String rawBody = getRawBody( context );
        if ( rawBody != null && rawBody.length() > 0 )
        {
            runtimeConfigWrapper.addText( rawBody );
        }

        for ( Iterator it = attributes.iterator(); it.hasNext(); )
        {
            MarmaladeAttribute attribute = (MarmaladeAttribute) it.next();

            String name = attribute.getName();
            String attrUri = attribute.getNamespace();

            //            if ( attrUri != null && !attrUri.equals( "" ) && !attrUri.equals(
            // getTagInfo().getTaglib() ) )
            //            {
            //                name = attrUri + ":" + attrs.getQName( i );
            //            }

            String value = (String) attribute.getValue( context );

            // PR: Hack for ant-type value
            //  an ant-type is a component name which can
            // be namespaced, need to extract the name
            // and convert from qualified name to uri/name
            if ( ANT_TYPE.equals( name ) || (ProjectHelper.ANT_CORE_URI.equals( attrUri ) && ANT_TYPE.equals( name )) )
            {
                name = ANT_TYPE;
                int index = value.indexOf( ":" );
                if ( index != -1 )
                {
                    String prefix = value.substring( 0, index );
                    String mappedUri = attribute.getNamespace();
                    if ( mappedUri == null )
                    {
                        throw new BuildException( "Unable to find XML NS prefix " + prefix );
                    }
                    value = ProjectHelper.genComponentName( mappedUri, value.substring( index + 1 ) );
                }
            }
            runtimeConfigWrapper.setAttribute( name, value );
        }

        if ( parentWrapper != null )
        {
            parentWrapper.addChild( runtimeConfigWrapper );
        }

        // ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        //
        // From here UP to next marker, this code is adapted directly from
        // org.apache.tools.ant.helper.ProjectHelper2.
        // ********************************************************************
    }

    private RuntimeConfigurable getRuntimeConfigWrapper()
    {
        return runtimeConfigWrapper;
    }

    public class TagInfoLocation
        extends Location
    {

        TagInfoLocation( MarmaladeTagInfo tagInfo )
        {
            super( tagInfo.getSourceFile(), tagInfo.getSourceLine(), tagInfo.getSourceColumn() );
        }

    }

    public class ContextPropertyHelper
        extends PropertyHelper
        implements ContextEventMonitor
    {

        private final MarmaladeExecutionContext context;

        private final ExpressionEvaluator el;

        private transient Properties calculatedProperties;

        public ContextPropertyHelper( MarmaladeExecutionContext context, ExpressionEvaluator el )
        {
            this.context = context;
            this.el = el;
        }

        public Hashtable getProperties()
        {
            synchronized ( context )
            {
                this.calculatedProperties = calculateProperties();
            }

            return calculatedProperties;
        }

        private Properties calculateProperties()
        {
            Properties result = new Properties();

            for ( Iterator it = context.unmodifiableVariableMap().keySet().iterator(); it.hasNext(); )
            {
                Object rawKey = it.next();

                String key = String.valueOf( rawKey );

                try
                {
                    String value = (String) context.getVariable( rawKey, el );
                    if ( value != null )
                    {
                        result.setProperty( key, value );
                    }
                }
                catch ( ExpressionEvaluationException e )
                {
                    context.getLog().log( CommonLogLevels.ERROR,
                                          "Cannot calculate ant-property for context variable: \'" + rawKey + "\'",
                                          e );
                }
            }

            return result;
        }

        public synchronized Object getProperty( String ns, String name )
        {
            return super.getProperty( ns, name );
        }

        public String replaceProperties( String ns, String value, Hashtable keys ) throws BuildException
        {
            return super.replaceProperties( ns, value, keys );
        }

        public synchronized void setInheritedProperty( String ns, String name, Object value )
        {
            super.setInheritedProperty( ns, name, value );
        }

        public synchronized void setNewProperty( String ns, String name, Object value )
        {
            super.setNewProperty( ns, name, value );
        }

        public synchronized boolean setProperty( String ns, String name, Object value, boolean verbose )
        {
            return super.setProperty( ns, name, value, verbose );
        }

        public void variableSet( Object varKey, Object varValue, boolean externalizable )
        {
            this.calculatedProperties = null;
        }

        public void variableRemoved( Object varKey )
        {
            this.calculatedProperties = null;
        }

        public void scopeCreated()
        {
        }

        public void scopeRestored()
        {
        }

        public void outWriterChanged( PrintWriter old, PrintWriter replacement )
        {
        }

        public void errWriterChanged( PrintWriter old, PrintWriter replacement )
        {
        }

        public void inReaderChanged( Reader old, Reader replacement )
        {
        }
    }
}