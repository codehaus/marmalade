
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
/* Created on Apr 10, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import java.util.Iterator;

/**
 * @author jdcasey
 */
public class SwitchTag extends AbstractMarmaladeTag
{
    public static final String ON_ATTRIBUTE = "on";
    private DefaultTag def;
    private Object testObject;

    public SwitchTag( MarmaladeTagInfo tagInfo )
    {
        super( tagInfo );
    }

    public void processChildren( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        boolean blockDefaultRun = false;

        for ( Iterator it = children(  ).iterator(  ); it.hasNext(  ); )
        {
            MarmaladeTag child = ( MarmaladeTag ) it.next(  );

            if ( child instanceof CaseTag )
            {
                CaseTag tag = ( CaseTag ) child;

                tag.setTestTarget( testObject );

                boolean run = tag.conditionMatches( context );

                if ( run )
                {
                    child.execute( context );

                    if ( !tag.fallThrough(  ) )
                    {
                        blockDefaultRun = true;

                        break;
                    }
                }
            }
            else
            {
                child.execute( context );
            }
        }

        if ( !blockDefaultRun && ( def != null ) )
        {
            def.execute( context );
        }
    }

    protected boolean shouldAddChild( MarmaladeTag child )
    {
        if ( child instanceof DefaultTag )
        {
            this.def = ( DefaultTag ) child;

            return false;
        }
        else
        {
            return true;
        }
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        this.testObject = requireTagAttribute( ON_ATTRIBUTE, context );
    }

    protected boolean alwaysProcessChildren(  )
    {
        return false;
    }
}
