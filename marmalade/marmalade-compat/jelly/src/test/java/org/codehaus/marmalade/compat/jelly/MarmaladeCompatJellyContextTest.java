/*
 * Copyright 2002,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codehaus.marmalade.compat.jelly;

import java.util.HashMap;

import org.codehaus.marmalade.compat.jelly.runtime.MarmaladeCompatJellyContext;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.el.commonsEl.CommonsElExpressionEvaluator;
import org.codehaus.marmalade.runtime.DefaultContext;

import junit.framework.TestCase;

/**
 * Adapted from jelly's own tests for JellyContext.
 * 
 * @author jdcasey
 * @author <a href="proyal@apache.org">peter royal</a>
 */
public class MarmaladeCompatJellyContextTest extends TestCase
{
    public void testSetVariablesAndRetainContextEntry()
    {
        DefaultContext ctx = new DefaultContext();
        ExpressionEvaluator el = new CommonsElExpressionEvaluator();
        
        MarmaladeCompatJellyContext jc = new MarmaladeCompatJellyContext(ctx, el);

        assertNotNull( "Initial variable of context is incorrect", jc.getVariable( "context" ) );

        jc.setVariables( new HashMap() );

        assertNotNull( "Value after setVariables() is incorrect", jc.getVariable( "context" ) );
    }
}
