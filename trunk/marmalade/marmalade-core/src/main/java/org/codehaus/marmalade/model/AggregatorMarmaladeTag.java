package org.codehaus.marmalade.model;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;

public interface AggregatorMarmaladeTag
    extends MarmaladeTag
{

    void addChild( MarmaladeTagInfo tagInfo, MarmaladeAttributes attributes );
    
}
