/* Created on Jun 28, 2004 */
package org.codehaus.marmalade.compat.jelly.util;

import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.xml.sax.Attributes;

/** Gelded SAX Attributes implementation for use in the marmalade-jelly bridge.
 * This class will have EXACTLY what jelly requires for tag creation and nothing more.
 *
 * @author jdcasey
 */
public class SAXAttributesWrapper implements Attributes
{
    private MarmaladeAttributes attributes;

    public SAXAttributesWrapper( MarmaladeAttributes attributes )
    {
        this.attributes = attributes;
    }

    public int getLength(  )
    {
        return 0;
    }

    public String getLocalName( int index )
    {
        return null;
    }

    public String getQName( int index )
    {
        return null;
    }

    public String getType( int index )
    {
        return null;
    }

    public String getURI( int index )
    {
        return null;
    }

    public String getValue( int index )
    {
        return null;
    }

    public int getIndex( String qName )
    {
        return 0;
    }

    public String getType( String qName )
    {
        return null;
    }

    public String getValue( String qName )
    {
        return null;
    }

    public int getIndex( String uri, String localName )
    {
        return 0;
    }

    public String getType( String uri, String localName )
    {
        return null;
    }

    public String getValue( String uri, String localName )
    {
        return null;
    }
}
