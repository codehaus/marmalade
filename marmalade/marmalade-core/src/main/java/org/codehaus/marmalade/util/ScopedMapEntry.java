
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
/* Created on Mar 25, 2004 */
package org.codehaus.marmalade.util;

import java.util.Map;
import java.util.Map.Entry;

/**
 * @author jdcasey
 */
public class ScopedMapEntry implements Entry
{
    private Map.Entry entry;
    private boolean mutable = true;

    public ScopedMapEntry( Map.Entry entry, boolean mutable )
    {
        this.entry = entry;
        this.mutable = mutable;
    }

    public boolean isMutable(  )
    {
        return mutable;
    }

    public Object getKey(  )
    {
        return entry.getKey(  );
    }

    public Object getValue(  )
    {
        return entry.getValue(  );
    }

    public Object setValue( Object value )
    {
        if ( !mutable )
        {
            throw new UnsupportedOperationException( 
                "Specified map entry is immutable." );
        }
        else
        {
            return entry.setValue( value );
        }
    }
}
