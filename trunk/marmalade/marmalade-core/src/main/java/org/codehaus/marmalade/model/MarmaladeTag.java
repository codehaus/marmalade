
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
package org.codehaus.marmalade.model;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.runtime.ConfigurationException;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/** Represents a marmalade tag. Simply used as a common place to specify behavior in the future.
 *
 * @author John Casey
 */
public interface MarmaladeTag
{
    Object getBody( MarmaladeExecutionContext context )
        throws ExpressionEvaluationException;

    Object getBody( MarmaladeExecutionContext context, Class targetType )
        throws ExpressionEvaluationException;

    MarmaladeAttributes getAttributes(  );

    ExpressionEvaluator getExpressionEvaluator(  )
        throws ConfigurationException;

    void execute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException;

    void processChildren( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException;

    void setParent( MarmaladeTag parent );

    void addChild( MarmaladeTag child );

    MarmaladeTag getParent(  );
}
