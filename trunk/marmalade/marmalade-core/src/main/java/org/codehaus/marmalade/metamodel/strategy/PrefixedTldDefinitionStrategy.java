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
