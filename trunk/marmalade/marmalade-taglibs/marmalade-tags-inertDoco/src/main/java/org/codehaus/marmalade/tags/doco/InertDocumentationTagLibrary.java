package org.codehaus.marmalade.tags.doco;

import org.codehaus.marmalade.metamodel.AbstractMarmaladeTagLibrary;

/**
 * @author jdcasey
 *
 * Created on Feb 8, 2005
 */
public class InertDocumentationTagLibrary
    extends AbstractMarmaladeTagLibrary
{
    
    public InertDocumentationTagLibrary()
    {
        registerTag("content", InertDocumentationTag.class);
        registerTag("changelog", InertDocumentationTag.class);
        registerTag("version", InertDocumentationTag.class);
        registerTag("name", InertDocumentationTag.class);
        registerTag("date", InertDocumentationTag.class);
        registerTag("todo", InertDocumentationTag.class);
        registerTag("fixme", InertDocumentationTag.class);
        registerTag("warning", InertDocumentationTag.class);
    }

}
