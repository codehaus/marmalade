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
package org.codehaus.marmalade.metamodel;

import org.codehaus.marmalade.model.MarmaladeTagLibrary;
import org.codehaus.tagalog.Tag;
import org.codehaus.tagalog.TagLibrary;

/**
 * @author jdcasey
 */
public class BuilderTagLibrary implements TagLibrary
{
    private String scheme;
    private String taglib;
    private MarmaladeTaglibResolver taglibResolver;

    public BuilderTagLibrary( String scheme, String taglib,
        MarmaladeTaglibResolver taglibResolver )
    {
        this.scheme = scheme;
        this.taglib = taglib;
        this.taglibResolver = taglibResolver;
    }

    public Tag getTag( String element )
    {
        MarmaladeTagLibrary tlib = taglibResolver.resolve( scheme, taglib );

        return new BuilderTag( scheme, taglib, tlib );
    }

    public void releaseTag( String element, Tag tag )
    {
    }
}
