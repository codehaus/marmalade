
/*
 * Copyright 2001-2004 The Apache Software Foundation.
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

/*
 * Copyright 2001-2004 The Apache Software Foundation.
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
/* Created on Apr 14, 2004 */
package org.codehaus.marmalade.el;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author jdcasey
 */
public class PassThroughExpressionEvaluator implements ExpressionEvaluator
{
    public PassThroughExpressionEvaluator(  )
    {
    }

    public Object evaluate( String expression, Map context,
        Class expectedReturnType )
        throws ExpressionEvaluationException
    {
        return expression;
    }

    public Object assign( Object target, String property, Object value )
        throws ExpressionEvaluationException
    {
        throw new ExpressionEvaluationException( 
            "PassThrough evaluator not equipped for this. "
            + "Please ensure that the ognl evaluator is on the classpath." );
    }

    public Pattern getExpressionPattern(  )
    {
        return Pattern.compile( ".*" );
    }
}