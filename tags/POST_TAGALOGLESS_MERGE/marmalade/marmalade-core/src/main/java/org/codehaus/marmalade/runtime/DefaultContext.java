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

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.util.ScopedMap;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author John Casey
 */
public class DefaultContext implements MarmaladeExecutionContext
{
    private static final PrintWriter sysout = new PrintWriter( new OutputStreamWriter( 
                System.out ) );
    private static final PrintWriter syserr = new PrintWriter( new OutputStreamWriter( 
                System.err ) );
    private static final Reader sysin = new BufferedReader( new InputStreamReader( 
                System.in ) );
    public static final String PRESERVE_WS_OVERRIDE_VARIABLE = "marmalade:preserve-whitespace-override";
    private Map context;
    private PrintWriter out = sysout;
    private PrintWriter err = syserr;
    private Reader in = sysin;
    private XmlSerializer xmlSerializer;

    public DefaultContext(  )
    {
        this.context = new HashMap(  );
    }

    public DefaultContext( Map context )
    {
        this.context = context;
    }

    public void setOutWriter( PrintWriter out )
    {
        this.out = out;
    }

    public void setErrWriter( PrintWriter err )
    {
        this.err = err;
    }

    public void setInReader( Reader in )
    {
        this.in = in;
    }

    public Object getVariable( Object key, ExpressionEvaluator el )
        throws ExpressionEvaluationException
    {
        Object result = context.get( key );

        if ( ( el != null ) && ( result != null ) && ( result instanceof String ) )
        {
            result = el.evaluate( ( String ) result, context, Object.class );
        }

        return result;
    }

    public Object setVariable( Object key, Object value )
    {
        return context.put( key, value );
    }

    public Object removeVariable( Object key )
    {
        return context.remove( key );
    }

    public Map unmodifiableVariableMap(  )
    {
        return Collections.unmodifiableMap( context );
    }

    public void newScope(  )
    {
        this.context = new ScopedMap( context );
    }

    public Map lastScope(  )
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
            Map parent = ( ( ScopedMap ) context ).getSuperMap(  );
            Map local = ( ( ScopedMap ) context ).getLocalMap(  );

            if ( parent != null )
            {
                context = parent;
                replaced = local;
            }
        }

        if ( ( replaced != null ) && export )
        {
            context.putAll( replaced );
        }

        return replaced;
    }

    public PrintWriter getErrWriter(  )
    {
        return err;
    }

    public PrintWriter getOutWriter(  )
    {
        return out;
    }

    public Reader getInReader(  )
    {
        return in;
    }

    public Boolean preserveWhitespaceOverride(  )
    {
        return ( Boolean ) context.get( PRESERVE_WS_OVERRIDE_VARIABLE );
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
        Map vars = otherContext.unmodifiableVariableMap(  );

        context.putAll( vars );
    }

    public void setVariables(Map vars) {
        context.putAll(vars);
    }

    public XmlSerializer getXmlSerializer() throws XmlPullParserException, IOException {
        if(xmlSerializer == null) {
            XmlPullParserFactory xpp3Factory = XmlPullParserFactory.newInstance();
            xmlSerializer = xpp3Factory.newSerializer();
            xmlSerializer.setOutput(getOutWriter());
        }
        return xmlSerializer;
    }

}
