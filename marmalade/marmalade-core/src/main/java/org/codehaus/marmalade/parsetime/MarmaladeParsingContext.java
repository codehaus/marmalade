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
package org.codehaus.marmalade.parsetime;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;

import java.io.PrintWriter;
import java.io.Reader;

import java.util.Map;

/**
 * @author jdcasey
 */
public interface MarmaladeParsingContext
{
    public Object getVariable( Object key, ExpressionEvaluator el )
        throws ExpressionEvaluationException;

    public Object setVariable( Object key, Object value );
    
    public void setVariables( Map vars );

    public Object removeVariable( Object key );

    public Map unmodifiableVariableMap(  );

    public void newScope(  );

    public Map lastScope(  );

    public Map lastScope( boolean export );

    public void setOutWriter( PrintWriter out );

    public void setErrWriter( PrintWriter err );

    public void setInReader( Reader in );

    public PrintWriter getErrWriter(  );

    public PrintWriter getOutWriter(  );

    public Reader getInReader(  );

    public Boolean preserveWhitespaceOverride(  );

    public void preserveWhitespaceOverride( Boolean shouldOverride );

    public void importContext( MarmaladeParsingContext context );
}
