
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
/* Created on Mar 24, 2004 */
package org.codehaus.marmalade.el;

import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/** Exception signalling that an error has occurred while evaluating an expression.
 *
 * @author John Casey
 */
public class ExpressionEvaluationException extends MarmaladeExecutionException
{
    /** Construct a new exception to convey failure in evaluating an expression.
     * @param message The message giving detailed info about this exception
     */
    public ExpressionEvaluationException( String message )
    {
        super( message );
    }

    /** Construct a new exception to convey failure in evaluating an expression.
     * @param expression The expression that could not be evaluated.
     * @param cause The root cause of this exception
     */
    public ExpressionEvaluationException( String expression, Throwable cause )
    {
        super( "Error evaluating expression: " + expression, cause );
    }
}
