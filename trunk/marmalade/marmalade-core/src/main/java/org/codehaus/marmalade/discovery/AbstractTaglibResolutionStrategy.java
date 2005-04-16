package org.codehaus.marmalade.discovery;

import org.codehaus.marmalade.monitor.log.DefaultLog;
import org.codehaus.marmalade.monitor.log.MarmaladeLog;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
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

public abstract class AbstractTaglibResolutionStrategy
    implements TaglibResolutionStrategy
{
    
    private MarmaladeLog log;

    protected AbstractTaglibResolutionStrategy()
    {
    }

    public void setLog( MarmaladeLog log )
    {
        this.log = log;
    }
    
    protected MarmaladeLog getLog()
    {
        synchronized ( this )
        {
            if(log == null)
            {
                log = new DefaultLog();
            }
        }
        
        return log;
    }

}
