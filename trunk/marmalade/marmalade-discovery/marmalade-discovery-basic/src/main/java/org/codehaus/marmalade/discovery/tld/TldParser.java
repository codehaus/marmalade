/* Created on Jun 29, 2004 */
package org.codehaus.marmalade.discovery.tld;

import org.codehaus.marmalade.metamodel.MarmaladeTagLibrary;
import org.codehaus.plexus.util.xml.pull.MXParser;
import org.codehaus.plexus.util.xml.pull.XmlPullParser;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.IOException;
import java.io.Reader;

/**
 * @author jdcasey
 */
public class TldParser
{
    public static final String TAGLIB_TAG = "taglib";

    public static final String TAG_TAG = "tag";

    public static final String NAME_TAG = "name";

    public static final String TAGCLASS_TAG = "tagclass";

    public TldParser()
    {
    }

    public MarmaladeTagLibrary parse( Reader reader )
        throws XmlPullParserException, IOException, ClassNotFoundException
    {
        TldDefinedTagLibrary taglib = null;

        XmlPullParser parser = new MXParser();

        parser.setFeature( XmlPullParser.FEATURE_PROCESS_NAMESPACES, false );

        parser.setInput( reader );

        ClassLoader cloader = Thread.currentThread().getContextClassLoader();

        int eventType = parser.getEventType();

        while ( eventType != XmlPullParser.END_DOCUMENT )
        {
            if ( eventType == XmlPullParser.START_TAG )
            {
                if ( TAGLIB_TAG.equals( parser.getName() ) )
                {
                    taglib = new TldDefinedTagLibrary();
                }
                else if ( TAG_TAG.equals( parser.getName() ) )
                {
                    String tagName = null;
                    Class tagClass = null;

                    while ( parser.nextTag() == XmlPullParser.START_TAG )
                    {
                        if ( NAME_TAG.equals( parser.getName() ) )
                        {
                            tagName = parser.nextText();
                        }
                        else if ( TAGCLASS_TAG.equals( parser.getName() ) )
                        {
                            String className = parser.nextText();

                            tagClass = cloader.loadClass( className );
                        }
                    }

                    taglib.registerTag( tagName, tagClass );
                }
            }

            eventType = parser.next();
        }

        return taglib;
    }
}