
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

/* Created on Jun 23, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import java.lang.reflect.Field;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.commonjava.reflection.Reflector;
import org.commonjava.reflection.ReflectorException;

/**
 * @author jdcasey
 */
public class GetStaticTag extends AbstractMarmaladeTag
{
    public static final String CLASS_NAME_ATTRIBUTE = "className";
    public static final String FIELD_ATTRIBUTE = "field";
    public static final String VAR_ATTRIBUTE = "var";
    
    public GetStaticTag( MarmaladeTagInfo tagInfo )
    {
        super( tagInfo );
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        String className = (String)requireTagAttribute(CLASS_NAME_ATTRIBUTE, String.class, context);
        String fieldName = (String)requireTagAttribute(FIELD_ATTRIBUTE, String.class, context);
        String var = (String)requireTagAttribute(VAR_ATTRIBUTE, String.class, context);
        
        Class targetClass;

        try
        {
            targetClass = Class.forName( className );
            Object result = Reflector.getStaticField(targetClass, fieldName);
            
            if(result != null) {
                context.setVariable(var, result);
            }
        }
        catch ( ClassNotFoundException e )
        {
            throw new MarmaladeExecutionException( "Error loading class: "
                + className, e );
        }
        catch (ReflectorException e) {
            throw new MarmaladeExecutionException( "error accessing field: " + fieldName + " in class: " + className);
        }
    }
}