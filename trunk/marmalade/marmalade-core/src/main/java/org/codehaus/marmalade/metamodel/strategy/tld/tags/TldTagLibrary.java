
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

/* Created on Apr 12, 2004 */
package org.codehaus.marmalade.metamodel.strategy.tld.tags;

import org.codehaus.tagalog.AbstractTagLibrary;

/**
 * @author jdcasey
 */
public class TldTagLibrary extends AbstractTagLibrary
{
    public static final String NS_URL = "http://java.sun.com/j2ee/dtds/web-jsptaglibrary_1_1.dtd";
    public static final String TAGLIB_TAG = "taglib";
    public static final String TLIBVERSION_TAG = "tlibversion";
    public static final String SHORTNAME_TAG = "shortname";
    public static final String TAG_TAG = "tag";
    public static final String NAME_TAG = "name";
    public static final String TAGCLASS_TAG = "tagclass";

    public TldTagLibrary(  )
    {
        registerTag( TAGLIB_TAG, TaglibTag.class );
        registerTag( TLIBVERSION_TAG, TlibversionTag.class );
        registerTag( SHORTNAME_TAG, ShortnameTag.class );
        registerTag( TAG_TAG, TagTag.class );
        registerTag( NAME_TAG, NameTag.class );
        registerTag( TAGCLASS_TAG, TagclassTag.class );
    }
}
