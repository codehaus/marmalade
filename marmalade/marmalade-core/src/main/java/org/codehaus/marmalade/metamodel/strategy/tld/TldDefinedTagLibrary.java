
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
/* Created on Apr 12, 2004 */
package org.codehaus.marmalade.metamodel.strategy.tld;

import org.codehaus.marmalade.model.AbstractMarmaladeTagLibrary;

/**
 * @author jdcasey
 */
public class TldDefinedTagLibrary extends AbstractMarmaladeTagLibrary
{
    private String shortName;
    private String tldVersion;

    public TldDefinedTagLibrary(  )
    {
    }

    public void setShortname( String shortName )
    {
        this.shortName = shortName;
    }

    public void setTlibversion( String tlibversion )
    {
        this.tldVersion = tlibversion;
    }

    public void registerTag( String name, Class tagClass )
    {
        super.registerTag( name, tagClass );
    }

    public String getShortName(  )
    {
        return shortName;
    }

    public String getTldVersion(  )
    {
        return tldVersion;
    }
}
