/* Created on Jun 29, 2004 */
package org.codehaus.marmalade.parsetime;

import java.io.IOException;
import java.io.Reader;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.metamodel.MarmaladeTaglibResolver;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * @author jdcasey
 */
public class ScriptReader {

    public ScriptReader() {
    }
    
    public ScriptBuilder parse(MarmaladeTaglibResolver resolver, Reader reader) throws XmlPullParserException, IOException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        
        ScriptBuilder builder = null;
        
        parser.setInput(reader);
        
        MarmaladeTagInfo currentTagInfo = null;
        
        int eventType = parser.getEventType();
        while(eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_TAG) {
                MarmaladeTagInfo parent = null;
                if(currentTagInfo != null) {
                    parent = currentTagInfo;
                }
                
                currentTagInfo = new MarmaladeTagInfo();
                if(parent != null) {
                    currentTagInfo.setParent(parent);
                    parent.addChild(currentTagInfo);
                }
                
                String ns = parser.getNamespace();
                int colonIdx = ns.indexOf(":");
                String scheme = (colonIdx > 0)?(ns.substring(0, colonIdx)):(null);
                String taglib = (colonIdx > 0)?(ns.substring(colonIdx+1)):(ns);
                
                currentTagInfo.setElement(parser.getName());
                currentTagInfo.setScheme(scheme);
                currentTagInfo.setTaglib(taglib);
                currentTagInfo.setPrefix(parser.getPrefix());
            }
            
            eventType = parser.next();
        }
        
        return builder;
    }
}
