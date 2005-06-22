/*
 *
 * Copyright (c) 2004 John Dennis Casey
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 */
/* Created on Apr 11, 2004 */
package org.codehaus.marmalade.runtime;

import org.codehaus.marmalade.el.BareBonesExpressionEvaluator;
import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.monitor.event.ComposableDispatcherManager;
import org.codehaus.marmalade.monitor.event.EventDispatcherManager;
import org.codehaus.marmalade.monitor.event.context.ContextEventDispatcher;
import org.codehaus.marmalade.monitor.event.context.DefaultContextEventDispatcher;
import org.codehaus.marmalade.monitor.log.DefaultLog;
import org.codehaus.marmalade.monitor.log.MarmaladeLog;
import org.codehaus.marmalade.util.ScopedMap;
import org.codehaus.plexus.util.xml.pull.MXSerializer;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.codehaus.plexus.util.xml.pull.XmlSerializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author John Casey
 */
public class DefaultContext
    implements MarmaladeExecutionContext
{
    private static final PrintWriter SYSOUT = new PrintWriter( new OutputStreamWriter( System.out ) );

    private static final PrintWriter SYSERR = new PrintWriter( new OutputStreamWriter( System.err ) );

    private static final Reader SYSIN = new BufferedReader( new InputStreamReader( System.in ) );

    public static final String PRESERVE_WS_OVERRIDE_VARIABLE = "marmalade:preserve-whitespace-override";

    private Map context;

    private Map systemContext;

    private PrintWriter out = SYSOUT;

    private PrintWriter err = SYSERR;

    private Reader in = SYSIN;

    private XmlSerializer xmlSerializer;

    private Set externalized = new HashSet();

    private EventDispatcherManager eventDispatcherManager;

    private ContextEventDispatcher contextDispatcher;

    private MarmaladeLog log;

    public DefaultContext()
    {
        this.systemContext = Collections.unmodifiableMap( new TreeMap( System.getProperties() ) );

        this.context = new ScopedMap( systemContext );

        buildDefaultEventDispatcher();
    }

    public DefaultContext( Map context )
    {
        this.systemContext = new HashMap();
        this.systemContext.putAll( System.getProperties() );

        this.systemContext = Collections.unmodifiableMap( systemContext );

        this.context = new ScopedMap( systemContext );
        this.context.putAll( context );

        buildDefaultEventDispatcher();
    }
    
    public DefaultContext(EventDispatcherManager eventDispatcherManager)
    {
        this.systemContext = Collections.unmodifiableMap( new TreeMap( System.getProperties() ) );

        this.context = new ScopedMap( systemContext );

        this.eventDispatcherManager = eventDispatcherManager;
        this.contextDispatcher = eventDispatcherManager.getContextDispatcher();
    }

    public DefaultContext( Map context, EventDispatcherManager eventDispatcherManager )
    {
        this.systemContext = new HashMap();
        this.systemContext.putAll( System.getProperties() );

        this.systemContext = Collections.unmodifiableMap( systemContext );

        this.context = new ScopedMap( systemContext );
        this.context.putAll( context );
        
        this.eventDispatcherManager = eventDispatcherManager;
        this.contextDispatcher = eventDispatcherManager.getContextDispatcher();
    }
    
    public void setLog(MarmaladeLog log)
    {
        if(log == null)
        {
            this.log = log;
        }
    }
    
    public synchronized MarmaladeLog getLog()
    {
        if(log == null)
        {
            log = new DefaultLog();
        }
        
        return log;
    }
    
    private void buildDefaultEventDispatcher()
    {
        ComposableDispatcherManager mgr = new ComposableDispatcherManager();
        
        ContextEventDispatcher ctxDispatcher = new DefaultContextEventDispatcher();
        
        mgr.initContextDispatcher( ctxDispatcher );
        
        this.eventDispatcherManager = mgr;
        
        this.contextDispatcher = ctxDispatcher;
    }

    public void setOutWriter( PrintWriter out )
    {
        PrintWriter old = this.out;
        
        this.out = out;
        
        // Dispatch event.
        contextDispatcher.outWriterChanged(old, out);
    }

    public void setErrWriter( PrintWriter err )
    {
        PrintWriter old = this.err;
        
        this.err = err;
        
        // Dispatch event.
        contextDispatcher.errWriterChanged(old, err);
    }

    public void setInReader( Reader in )
    {
        Reader old = this.in;
        
        this.in = in;
        
        // Dispatch event.
        contextDispatcher.inReaderChanged(old, in);
    }

    public Object getVariable( Object key, ExpressionEvaluator el ) throws ExpressionEvaluationException
    {
        Object result = context.get( key );

        if ( el == null )
        {
            el = new BareBonesExpressionEvaluator();
        }

        if ( (result != null) && (result instanceof String) )
        {
            result = el.evaluate( (String) result, context, Object.class );
        }

        return result;
    }

    public Object setVariable( Object key, Object value )
    {
        // Dispatch event.
        contextDispatcher.variableSet(key, value, false);
        
        return context.put( key, value );
    }

    public Object setVariable( Object key, Object value, boolean externalize )
    {
        if ( externalize )
        {
            externalized.add( key );
        }
        
        // Dispatch event.
        contextDispatcher.variableSet(key, value, externalize);
        
        return context.put( key, value );
    }

    public Object removeVariable( Object key )
    {
        Object result = context.remove( key );

        externalized.remove( key );

        // Dispatch event.
        contextDispatcher.variableRemoved(key);
        
        return result;
    }

    public Map unmodifiableVariableMap()
    {
        return Collections.unmodifiableMap( context );
    }

    public void newScope()
    {
        this.context = new ScopedMap( context );
        
        // Dispatch event.
        contextDispatcher.scopeCreated();
    }

    public Map lastScope()
    {
        return _lastScope( false );
    }

    public Map lastScope( boolean export )
    {
        return _lastScope( export );
    }

    private Map _lastScope( boolean export )
    {
        Map replaced = null;

        if ( context instanceof ScopedMap )
        {
            Map parent = ((ScopedMap) context).getSuperMap();
            Map local = ((ScopedMap) context).getLocalMap();

            // If parent isn't an instance of ScopedMap,
            // we're already at the root.
            if ( (parent != null) && (parent instanceof ScopedMap) )
            {
                context = parent;
                replaced = local;
            }
        }

        if ( (replaced != null) && export )
        {
            context.putAll( replaced );
        }
        
        // Dispatch event.
        contextDispatcher.scopeRestored();

        return replaced;
    }

    public PrintWriter getErrWriter()
    {
        return err;
    }

    public PrintWriter getOutWriter()
    {
        return out;
    }

    public Reader getInReader()
    {
        return in;
    }

    public Boolean preserveWhitespaceOverride()
    {
        return (Boolean) context.get( PRESERVE_WS_OVERRIDE_VARIABLE );
    }

    public void preserveWhitespaceOverride( Boolean shouldOverride )
    {
        if ( shouldOverride == null )
        {
            context.remove( PRESERVE_WS_OVERRIDE_VARIABLE );
        }
        else
        {
            context.put( PRESERVE_WS_OVERRIDE_VARIABLE, shouldOverride );
        }
    }

    public void importContext( MarmaladeExecutionContext otherContext )
    {
        Map vars = otherContext.unmodifiableVariableMap();

        context.putAll( vars );
    }

    public void setVariables( Map vars )
    {
        context.putAll( vars );
    }

    public void setVariables( Map vars, boolean externalize )
    {
        if ( externalize )
        {
            for ( Iterator it = vars.keySet().iterator(); it.hasNext(); )
            {
                Object key = (Object) it.next();
                externalized.add( key );
            }
        }

        context.putAll( vars );
    }

    public XmlSerializer getXmlSerializer() throws XmlPullParserException, IOException
    {
        if ( xmlSerializer == null )
        {
            xmlSerializer = new MXSerializer();
            xmlSerializer.setOutput( getOutWriter() );
        }

        return xmlSerializer;
    }

    public Map getExternalizedVariables( ExpressionEvaluator el ) throws ExpressionEvaluationException
    {
        return getVariablesAsResolved(externalized, el);
    }

    public Map getExternalizedVariables()
    {
        try
        {
            return getVariablesAsResolved(externalized, null);
        }
        catch ( ExpressionEvaluationException e )
        {
            StringWriter sWriter = new StringWriter();
            PrintWriter pWriter = new PrintWriter( sWriter );
            e.printStackTrace( pWriter );

            throw new RuntimeException( "This should never, ever happen! Error was:\n" + sWriter.toString() );
        }
    }
    
    public Map getVariablesAsResolved( ExpressionEvaluator el ) throws ExpressionEvaluationException
    {
        return getVariablesAsResolved(context.keySet(), el);
    }

    public Map getVariablesAsResolved()
    {
        try
        {
            return getVariablesAsResolved(context.keySet(), null);
        }
        catch ( ExpressionEvaluationException e )
        {
            StringWriter sWriter = new StringWriter();
            PrintWriter pWriter = new PrintWriter( sWriter );
            e.printStackTrace( pWriter );

            throw new RuntimeException( "This should never, ever happen! Error was:\n" + sWriter.toString() );
        }
    }
    
    private Map getVariablesAsResolved( Set variableKeys, ExpressionEvaluator el ) throws ExpressionEvaluationException
    {
        Map result = new HashMap();

        for ( Iterator it = variableKeys.iterator(); it.hasNext(); )
        {
            Object key = (Object) it.next();
            result.put( key, getVariable( key, el ) );
        }

        return result;
    }

}