/*
 *
 * Copyright (c) 2004 John Dennis Casey
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 */
/* Created on Apr 20, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.TagExecutionException;
import org.codehaus.marmalade.tags.jelly.AbstractJellyMarmaladeTag;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author jdcasey
 */
public class ThreadTag extends AbstractJellyMarmaladeTag
{
    public static final String FILE_ATTRIBUTE = "file";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String ERR_FILE_ATTRIBUTE = "errFile";
    public static final String IN_FILE_ATTRIBUTE = "inFile";

    public ThreadTag(  )
    {
    }
    
    protected boolean alwaysProcessChildren() {
        return false;
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        MarmaladeAttributes attrs = getAttributes(  );

        DefaultContext ctx = new DefaultContext(  );

        Object outFile = attrs.getValue( FILE_ATTRIBUTE, ctx );

        if ( outFile != null )
        {
            PrintWriter out = null;

            if ( outFile instanceof File )
            {
                try
                {
                    out = new PrintWriter( new BufferedWriter( 
                                new FileWriter( ( File ) outFile ) ) );
                }
                catch ( IOException e )
                {
                    throw new TagExecutionException( getTagInfo(), "Error opening thread\'s output file for writing.",
                        e );
                }
            }
            else
            {
                try
                {
                    out = new PrintWriter( new BufferedWriter( 
                                new FileWriter( ( String ) outFile ) ) );
                }
                catch ( IOException e )
                {
                    throw new TagExecutionException( getTagInfo(), "Error opening thread\'s output file for writing.",
                        e );
                }
            }

            ctx.setOutWriter( out );
        }

        Object errFile = attrs.getValue( ERR_FILE_ATTRIBUTE, ctx );

        if ( errFile != null )
        {
            PrintWriter err = null;

            if ( errFile instanceof File )
            {
                try
                {
                    err = new PrintWriter( new BufferedWriter( 
                                new FileWriter( ( File ) errFile ) ) );
                }
                catch ( IOException e )
                {
                    throw new TagExecutionException( getTagInfo(), "Error opening thread\'s error output file for writing.",
                        e );
                }
            }
            else
            {
                try
                {
                    err = new PrintWriter( new BufferedWriter( 
                                new FileWriter( ( String ) errFile ) ) );
                }
                catch ( IOException e )
                {
                    throw new TagExecutionException( getTagInfo(), "Error opening thread\'s error output file for writing.",
                        e );
                }
            }

            ctx.setErrWriter( err );
        }

        Object inFile = attrs.getValue( IN_FILE_ATTRIBUTE, ctx );

        if ( inFile != null )
        {
            BufferedReader in = null;

            if ( inFile instanceof File )
            {
                try
                {
                    in = new BufferedReader( new FileReader( ( File ) inFile ) );
                }
                catch ( FileNotFoundException e )
                {
                    throw new TagExecutionException( getTagInfo(), "Error opening thread\'s input file for reading.",
                        e );
                }
            }
            else
            {
                try
                {
                    in = new BufferedReader( new FileReader( ( String ) inFile ) );
                }
                catch ( FileNotFoundException e )
                {
                    throw new TagExecutionException( getTagInfo(), "Error opening thread\'s input file for reading.",
                        e );
                }
            }

            ctx.setInReader( in );
        }

        String threadName = ( String ) attrs.getValue( NAME_ATTRIBUTE,
                String.class, context );
        Thread thread = null;

        if ( ( threadName == null ) && ( threadName.length(  ) > 0 ) )
        {
            thread = new Thread( new ChildRunner( this, ctx ) );
        }
        else
        {
            thread = new Thread( new ChildRunner( this, ctx ), threadName );
        }

        thread.setPriority( Thread.currentThread(  ).getPriority(  ) - 1 );
        thread.start();
    }
    
    public void run(MarmaladeExecutionContext context) throws MarmaladeExecutionException {
        processChildren(context);
    }

    private static class ChildRunner implements Runnable
    {
        private MarmaladeExecutionContext ctx;
        private final ThreadTag tag;

        ChildRunner( ThreadTag tag, MarmaladeExecutionContext ctx )
        {
            this.tag = tag;
            this.ctx = ctx;
        }

        public void run(  )
        {
            try
            {
                tag.run( ctx );
            }
            catch ( MarmaladeExecutionException e )
            {
                e.printStackTrace( ctx.getErrWriter(  ) );
            }
        }
    }
}
