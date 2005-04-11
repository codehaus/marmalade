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
/* Created on Apr 10, 2004 */
package org.codehaus.marmalade.runtime;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.monitor.log.MarmaladeLog;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.codehaus.plexus.util.xml.pull.XmlSerializer;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

import java.util.Map;

/**
 * @author jdcasey
 */
public interface MarmaladeExecutionContext
{
    Object getVariable( Object key, ExpressionEvaluator el ) throws ExpressionEvaluationException;
    
    Map getExternalizedVariables(ExpressionEvaluator el) throws ExpressionEvaluationException;
    
    Map getExternalizedVariables();

    Object setVariable( Object key, Object value );

    Object setVariable( Object key, Object value, boolean externalize );

    void setVariables( Map vars );

    void setVariables( Map vars, boolean externalize );

    Object removeVariable( Object key );

    Map unmodifiableVariableMap();

    void newScope();

    Map lastScope();

    Map lastScope( boolean export );

    void setOutWriter( PrintWriter out );

    void setErrWriter( PrintWriter err );

    void setInReader( Reader in );

    PrintWriter getErrWriter();

    PrintWriter getOutWriter();

    Reader getInReader();

    Boolean preserveWhitespaceOverride();

    void preserveWhitespaceOverride( Boolean shouldOverride );

    void importContext( MarmaladeExecutionContext context );

    XmlSerializer getXmlSerializer() throws XmlPullParserException, IOException;
    
    MarmaladeLog getLog();
    
}