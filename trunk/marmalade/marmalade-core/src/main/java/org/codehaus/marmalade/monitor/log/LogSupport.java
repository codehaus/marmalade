package org.codehaus.marmalade.monitor.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

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

public final class LogSupport
{
    
    private LogSupport()
    {
    }
    
    public static CharSequence formatEntryList(List entryList)
    {
        StringBuffer messageBuffer = new StringBuffer();

        for ( Iterator it = entryList.iterator(); it.hasNext(); )
        {
            Object element = (Object) it.next();

            if ( element instanceof CharSequence )
            {
                messageBuffer.append( ((CharSequence) element).toString() );
            }
            else if ( element instanceof LogRenderable )
            {
                messageBuffer.append( ((LogRenderable) element).render() );
            }
            else if ( element instanceof Throwable )
            {
                messageBuffer.append( '\n' ).append( formatThrowable( (Throwable) element ) );
            }
            else
            {
                messageBuffer.append( String.valueOf( element ) );
            }
        }
        
        return messageBuffer;
    }

    public static CharSequence formatThrowable( Throwable error )
    {
        StringWriter sWriter = new StringWriter();
        PrintWriter pWriter = new PrintWriter( sWriter );

        error.printStackTrace( pWriter );

        return sWriter.getBuffer();
    }
}
