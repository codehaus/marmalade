
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

/* Created on Apr 23, 2004 */
package org.codehaus.marmalade.tags.httpunit;

import com.meterware.httpunit.WebResponse;

/**
 * @author jdcasey
 */
public class TestingWebResponse extends WebResponse
{
    public TestingWebResponse(  )
    {
        super( null, null, null );
    }

    public int getResponseCode(  )
    {
        return 0;
    }

    public String getResponseMessage(  )
    {
        return null;
    }

    public String[] getHeaderFieldNames(  )
    {
        return null;
    }

    public String getHeaderField( String arg0 )
    {
        return null;
    }

    public String toString(  )
    {
        return null;
    }

    public String[] getHeaderFields( String arg0 )
    {
        return null;
    }
}
