/*
 * $Id$
 */

package org.codehaus.tagalog;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * Simple implementation of the <code>TagLibrary</code> contract.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public abstract class AbstractTagLibrary implements TagLibrary {
    private Map tags = new java.util.TreeMap();

    protected void registerTag(String tagName, Class tagClass) {
        if (tagName.length() == 0)
            throw new IllegalArgumentException("tag name is empty");
        tags.put(tagName, new TagInfo(tagClass));
    }

    public Tag getTag(String element) {
        TagInfo info;

        if (element.length() == 0)
            throw new IllegalArgumentException("tag name is empty");
        info = (TagInfo) tags.get(element);
        if (info == null)
            return null;
        return info.getTag();
    }

    public void releaseTag(String element, Tag tag) {
        TagInfo info = (TagInfo) tags.get(element);
        if (info == null)
            throw new IllegalStateException("could not find tag " + tag
                                          + " for element '" + element + "'");
        info.releaseTag(tag);
    }

    public String listUnreleasedTags() {
        StringBuffer buf = new StringBuffer();
        Iterator iter = tags.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            TagInfo info = (TagInfo) entry.getValue();
            if (info.unreleasedTagCount() > 0) {
                if (buf.length() > 0)
                    buf.append(", ");
                buf.append((String) entry.getKey());
                buf.append(": ");
                buf.append(info.unreleasedTagCount());
            }
        }
        return buf.toString();
    }

    private static class TagInfo {
        private final Class tagClass;

        // Using a linked-list to recycle tag instances is probably a bad
        // idea as the management of the linked-list requires creation of
        // a new node for each add operation; this could be made more
        // efficient by managing our own singly-linked-list.

        /**
         * Tag instances that are in use.
         */
        private LinkedList tagInstances = new LinkedList();

        /**
         * Unused tag instances for recycling.
         */
        private LinkedList unusedTagInstances = new LinkedList();

        TagInfo(Class tagClass) {
            if (!Tag.class.isAssignableFrom(tagClass))
                throw new IllegalArgumentException("class does not implement Tag");
            this.tagClass = tagClass;

            // Check that we can create instances of the tag.
            Tag tag;
            try {
                tag = (Tag) tagClass.newInstance();
            } catch (Exception e) {
                throw new IllegalArgumentException("could not instantiate "
                                                   + tagClass.getName()
                                                   + ": " + e);
            }
            unusedTagInstances.addFirst(tag);
        }

        synchronized Tag getTag() {
            Tag tag;

            if (unusedTagInstances.isEmpty()) {
                try {
                    tag = (Tag) tagClass.newInstance();
                } catch (Exception e) {
                    throw new Error("exception instantiating tag", e);
                }
            } else {
                tag = (Tag) unusedTagInstances.removeFirst();
            }
            tagInstances.addFirst(tag);
            return tag;
        }

        synchronized void releaseTag(Tag tag) {
            Iterator iter = tagInstances.iterator();
            boolean found = false;

            while (iter.hasNext()) {
                Tag listElement = (Tag) iter.next();
                if (listElement == tag) {
                    iter.remove();
                    if (tag.recycle())
                        unusedTagInstances.addFirst(tag);
                    found = true;
                    break;
                }
            }
            if (!found)
                throw new IllegalArgumentException("could not find tag " + tag);
        }

        int unreleasedTagCount() {
            return tagInstances.size();
        }
    }
}
