/*
 * $Id$
 */

package org.codehaus.typle;

import java.util.Map;

/**
 * A <code>MimeType</code> is a data type given to items that have no
 * further internal structure as far as the type system is concerned,
 * but which may be transformed into structured types. For example, an
 * XML document has a <code>MimeType</code>, but can be converted into
 * a structured type by being parsed.
 *
 * @author Mark Wilkinson
 * @version $Revision$
 */
public final class MimeType extends TypeHelper implements Type {
    /**
     * Constant representing application/octet-stream MIME type.
     */
    public static final MimeType APPLICATION_OCTET_STREAM
        = new MimeType("application/octet-stream");

    /**
     * Constant representing text/plain MIME type.
     */
    public static final MimeType TEXT_PLAIN = new MimeType("text/plain");

    /**
     * Constant representing text/xml MIME type.
     */
    public static final MimeType TEXT_XML = new MimeType("text/xml");

    /**
     * Constant representing text/html MIME type.
     */
    public static final MimeType TEXT_HTML = new MimeType("text/html");

    private static Map mimeTypeMap = new java.util.HashMap();

    static {
        mimeTypeMap.put(MimeType.APPLICATION_OCTET_STREAM.getName(),
                        MimeType.APPLICATION_OCTET_STREAM);
        mimeTypeMap.put(MimeType.TEXT_PLAIN.getName(),
                        MimeType.TEXT_PLAIN);
        mimeTypeMap.put(MimeType.TEXT_XML.getName(),
                        MimeType.TEXT_XML);
        mimeTypeMap.put(MimeType.TEXT_HTML.getName(),
                        MimeType.TEXT_HTML);
    }

    public static synchronized MimeType getMimeType(String mimeTypeString) {
        MimeType type;

        if (mimeTypeString == null) {
            return MimeType.APPLICATION_OCTET_STREAM;
        }
        type = (MimeType) mimeTypeMap.get(mimeTypeString);
        if (type == null) {
            type = new MimeType(mimeTypeString);
            mimeTypeMap.put(mimeTypeString, type);
        }
        return type;
    }

    /**
     * Constructor for <code>MimeType</code>s.
     *
     * @param name Type's name.
     */
    MimeType(String name) {
        super(name);
    }
}
