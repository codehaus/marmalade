/*
 * $Id$
 */

package org.codehaus.tagalog.xpp;

import java.io.InputStream;
import java.io.Reader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * Package of information needed to set the input source for an
 * <code>XmlPullParser</code>.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
final class XmlPullSource {
    private final InputStream byteStream;

    private final String encoding;

    private final Reader characterStream;

    public XmlPullSource(InputStream byteStream, String encoding) {
        this.byteStream = byteStream;
        this.encoding = encoding;
        this.characterStream = null;
    }

    public XmlPullSource(Reader characterStream) {
        this.byteStream = null;
        this.encoding = null;
        this.characterStream = characterStream;
    }

    public void setInputFor(XmlPullParser parser)
        throws XmlPullParserException
    {
        if (characterStream != null)
            parser.setInput(characterStream);
        else
            parser.setInput(byteStream, encoding);
    }
}
