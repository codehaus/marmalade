/* Created on Jun 29, 2004 */
package org.codehaus.marmalade.metamodel.strategy.tld;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.codehaus.marmalade.model.MarmaladeTagLibrary;
import org.codehaus.marmalade.parsetime.MarmaladeParsetimeException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.sun.corba.se.connection.GetEndPointInfoAgainException;

/**
 * @author jdcasey
 */
public class TldParser {
    
    public static final String TAGLIB_TAG = "taglib";
    public static final String TAG_TAG = "tag";
    public static final String NAME_TAG = "name";
    public static final String TAGCLASS_TAG = "tagclass";

    public TldParser() {
    }
    
    public MarmaladeTagLibrary parse(Reader reader) throws XmlPullParserException, IOException, ClassNotFoundException {
        TldDefinedTagLibrary taglib = null;
        
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(false);
        factory.setValidating(false);
        
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(reader);
        
        ClassLoader cloader = getClass().getClassLoader();
        
        int eventType = parser.getEventType();
        while(eventType != XmlPullParser.END_DOCUMENT) {
            
            if(eventType == XmlPullParser.START_TAG) {
                if(TAGLIB_TAG.equals(parser.getName())) {
                    taglib = new TldDefinedTagLibrary();
                }
                else if(TAG_TAG.equals(parser.getName())){
                    
                    String tagName = null;
                    Class tagClass = null;
                    
                    while(parser.nextTag() == XmlPullParser.START_TAG) {
                        if(NAME_TAG.equals(parser.getName())) {
                            tagName = parser.nextText();
                        }
                        else if(TAGCLASS_TAG.equals(parser.getName())) {
                            String className = parser.nextText();
                            tagClass = cloader.loadClass(className);
                        }
                    }
                    
                    taglib.registerTag(tagName, tagClass);
                }
            }
            
            eventType = parser.next();
        }
        
        return taglib;
    }
}
