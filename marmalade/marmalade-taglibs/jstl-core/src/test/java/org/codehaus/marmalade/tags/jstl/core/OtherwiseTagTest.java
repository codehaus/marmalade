
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

/* Created on Apr 13, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import junit.framework.TestCase;

import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.IllegalParentException;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class OtherwiseTagTest extends TestCase
{
    public void testShouldFailWithMissingChooseParent(  )
        throws MarmaladeExecutionException
    {
        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( new DefaultRawAttributes(  ) );

        OtherwiseTag tag = new OtherwiseTag( ti );

        MarmaladeTagInfo flagTI = new MarmaladeTagInfo(  );

        flagTI.setAttributes( new DefaultRawAttributes(  ) );

        FlagChildTestTag flag = new FlagChildTestTag( flagTI );

        tag.addChild( flag );
        flag.setParent( tag );

        try
        {
            tag.execute( new DefaultContext(  ) );
            fail( "Tag should fail due to missing ChooseTag parent." );
        }
        catch ( IllegalParentException e )
        {
        }
    }

    public void testShouldExecuteWhenLoneChildInChooseParent(  )
        throws MarmaladeExecutionException
    {
        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( new DefaultRawAttributes(  ) );

        OtherwiseTag tag = new OtherwiseTag( ti );

        MarmaladeTagInfo parentTI = new MarmaladeTagInfo(  );

        parentTI.setAttributes( new DefaultRawAttributes(  ) );

        ChooseTag parent = new ChooseTag( parentTI );

        tag.setParent( parent );
        parent.addChild( tag );

        MarmaladeTagInfo flagTI = new MarmaladeTagInfo(  );

        flagTI.setAttributes( new DefaultRawAttributes(  ) );

        FlagChildTestTag flag = new FlagChildTestTag( flagTI );

        tag.addChild( flag );
        flag.setParent( tag );

        tag.execute( new DefaultContext(  ) );
        assertTrue( "Child tag should have fired.", flag.fired(  ) );
    }
}
