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
/* Created on May 18, 2004 */
package org.codehaus.marmalade.metamodel.strategy;

import org.codehaus.marmalade.metamodel.strategy.tld.tags.TldTagLibrary;
import org.codehaus.marmalade.model.MarmaladeTagLibrary;
import org.codehaus.tagalog.ParserConfiguration;
import org.codehaus.tagalog.TagalogParseException;
import org.codehaus.tagalog.TagalogParser;
import org.codehaus.tagalog.sax.TagalogSAXParserFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author jdcasey
 */
public class PrefixedTldDefinitionStrategy implements TaglibDefinitionStrategy
{
    private TagalogSAXParserFactory factory;

    public PrefixedTldDefinitionStrategy(  )
    {
    }

    public MarmaladeTagLibrary resolve( String prefix, String taglib )
    {
        ClassLoader cloader = getClass(  ).getClassLoader(  );
        MarmaladeTagLibrary tlib = null;

        InputStream tldStream = null;

        try
        {
            String tldFile = "META-INF/" + prefix + "/" + taglib + ".tld";

            tldStream = cloader.getResourceAsStream( tldFile );

            if ( tldStream != null )
            {
                try
                {
                    ensureTldParserInited(  );

                    TagalogParser parser = factory.createParser( tldStream );

                    tlib = ( MarmaladeTagLibrary ) parser.parse(  );
                }

                // Ignore these and return null.
                catch ( ParserConfigurationException e )
                {
                }
                catch ( SAXException e )
                {
                }
                catch ( TagalogParseException e )
                {
                }
            }
        }
        finally
        {
            if ( tldStream != null )
            {
                try
                {
                    tldStream.close(  );
                }
                catch ( IOException e )
                {
                }
            }
        }

        return tlib;
    }

    private void ensureTldParserInited(  )
    {
        ParserConfiguration config = new ParserConfiguration(  );

        config.setDefaultNamespace( TldTagLibrary.NS_URL );
        config.addTagLibrary( TldTagLibrary.NS_URL, new TldTagLibrary(  ) );

        this.factory = new TagalogSAXParserFactory( config );
    }
}
