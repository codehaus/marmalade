package org.codehaus.marmalade.compat.ant.metamodel;

import org.codehaus.marmalade.compat.ant.model.AntAdapterTag;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.metamodel.MarmaladeTagLibrary;
import org.codehaus.marmalade.metamodel.TagInstantiationException;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.parsing.ParserHint;

/**
 * @author jdcasey
 */
public class AntAdapterTagLibrary
    implements MarmaladeTagLibrary
{

    public MarmaladeTag createTag( MarmaladeTagInfo tagInfo ) throws TagInstantiationException
    {
        return new AntAdapterTag(tagInfo);
    }

    public ParserHint getParserHint( String name )
    {
        return new ParserHint().parseChildren(true);
    }

}
