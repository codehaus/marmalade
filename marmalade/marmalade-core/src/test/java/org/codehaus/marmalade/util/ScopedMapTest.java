/* Created on Mar 26, 2004 */
package org.codehaus.marmalade.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.codehaus.marmalade.util.ScopedMap;

import junit.framework.TestCase;

public class ScopedMapTest extends TestCase {

  public ScopedMapTest(String arg0) {
    super(arg0);
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(ScopedMapTest.class);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  public void testConstruct() {
    System.out.println("testConstruct");
    ScopedMap map = new ScopedMap();
    assertTrue("Empty constructor should result in empty map.", map.isEmpty());
  }

  public void testConstructWithMap() {
    System.out.println("testConstructWithMap");
    Map base = new HashMap();
    base.put("test", "testVal");
    base.put("test2", "testVal2");
    
    ScopedMap map = new ScopedMap(base);
    assertFalse("Constructor with map should result in non-empty map.", map.isEmpty());
    assertEquals("Base map contains value for \'test\', scoped map should also.", "testVal", map.get("test"));
    
    base.remove("test");
    assertNotNull("Removal from map used as constructor's parameter should NOT affect map.", map.get("test"));
  }

  public void testSize() {
    System.out.println("testSize");
    Map base = new HashMap();
    base.put("test", "testVal");
    base.put("test2", "testVal2");
    
    ScopedMap map = new ScopedMap(base);
    assertEquals("Scoped map size should be 2.", 2, map.size());
    
    map.put("test3", "testVal3");
    assertEquals("After addition scoped map size should be 3.", 3, map.size());
  }

  public void testClear() {
    System.out.println("testClear");
    Map base = new HashMap();
    base.put("test", "testVal");
    base.put("test2", "testVal2");
    
    ScopedMap map = new ScopedMap(base);
    map.clear();
    assertEquals("After clear, scoped map size should still be 2.", 2, map.size());
    
    map.put("test3", "testVal3");
    assertEquals("After addition scoped map size should be 3.", 3, map.size());
    map.clear();
    assertEquals("After addition and clear, scoped map size should be 2.", 2, map.size());
  }

  public void testIsEmpty() {
    System.out.println("testIsEmpty");
    Map base = new HashMap();
    base.put("test", "testVal");
    base.put("test2", "testVal2");
    
    ScopedMap map = new ScopedMap(base);
    assertFalse("Scoped map with non-empty base should not be empty.", map.isEmpty());
    map.clear();
    assertFalse("After clear, scoped map with non-empty base should not be empty.", map.isEmpty());
    
    ScopedMap map2 = new ScopedMap();
    assertTrue("Scoped map with empty base should be empty.", map2.isEmpty());
  }

  public void testContainsKey() {
    System.out.println("testContainsKey");
    Map base = new HashMap();
    base.put("test", "testVal");
    
    ScopedMap map = new ScopedMap(base);
    assertTrue("Scoped map with non-empty base should contain key \'test\'.", map.containsKey("test"));
    
    ScopedMap map2 = new ScopedMap();
    assertFalse("Scoped map with empty base should NOT contain key \'test\'.", map2.containsKey("test"));
    map.put("test", "testVal");
    assertFalse("After put, scoped map with empty base should contain key \'test\'.", map2.containsKey("test"));
  }

  public void testContainsValue() {
    System.out.println("testContainsValue");
    Map base = new HashMap();
    base.put("test", "testVal");
    
    ScopedMap map = new ScopedMap(base);
    assertTrue("Scoped map with non-empty base should contain value \'testVal\'.", map.containsValue("testVal"));
    
    ScopedMap map2 = new ScopedMap();
    assertFalse("Scoped map with empty base should NOT contain value \'testVal\'.", map2.containsValue("testVal"));
    map2.put("test", "testVal");
    assertTrue("After put, scoped map with empty base should contain value \'testVal\'.", map2.containsValue("testVal"));
  }

  public void testValues() {
    System.out.println("testValues");
    Map base = new HashMap();
    base.put("test", "testVal");
    
    ScopedMap map = new ScopedMap(base);
    Collection coll = map.values();
    assertTrue("Values from scoped map with non-empty base should contain \'testVal\'.", coll.contains("testVal"));
    coll.remove("testVal");
    assertTrue("After collection remove, map should still contain value \'testVal\'.", map.containsValue("testVal"));
    
    ScopedMap map2 = new ScopedMap();
    Collection coll2 = map2.values();
    assertFalse("Values from scoped map with empty base should NOT contain \'testVal\'.", coll2.contains("testVal"));
    map2.put("test", "testVal");
    assertTrue("After put, values from scoped map with empty base should contain \'testVal\'.", coll2.contains("testVal"));
    coll2.remove("testVal");
    assertFalse("After collection remove, map should NOT contain value \'testVal\'.", map2.containsValue("testVal"));
    
    try{
      coll2.add("testVal3");
      fail("Add to values from scoped map should result in UnsupportedOperationException.");
    }
    catch(UnsupportedOperationException e){
    }
  }

  public void testPutAll() {
    System.out.println("testEntrySet");
    Map base = new HashMap();
    base.put("test", "testVal");
    
    ScopedMap map = new ScopedMap(base);
    assertEquals("ScopedMap with base should be of size 1", 1, map.size());
    
    Map adds = new HashMap();
    adds.put("test", "testVal4");
    adds.put("test2", "testVal2");
    adds.put("test3", "testVal3");
    
    map.putAll(adds);
    assertEquals("ScopedMap with base should be of size 3 after putAll.", 3, map.size());
    assertEquals("ScopedMap value mapped to \'test\' should be testVal.", "testVal", map.get("test"));
    
    ScopedMap map2 = new ScopedMap();
    assertEquals("ScopedMap without base should be of size 0", 0, map2.size());
    
    map2.put("test", "testVal");
    assertEquals("After put, ScopedMap without Base should have mapped value for \'test\' of \'testVal\'", "testVal", map2.get("test"));
    
    Map adds2 = new HashMap();
    adds2.put("test", "testVal4");
    adds2.put("test2", "testVal2");
    adds2.put("test3", "testVal3");
    
    map2.putAll(adds2);
    assertEquals("ScopedMap without base should be of size 3 after putAll.", 3, map2.size());
    assertEquals("ScopedMap value mapped to \'test\' should be testVal4.", "testVal4", map2.get("test"));
  }

  public void testEntrySet() {
    System.out.println("testEntrySet");
    Map base = new HashMap();
    base.put("test", "testVal");
    
    ScopedMap map = new ScopedMap(base);
    Set coll = map.entrySet();
    assertEquals("EntrySet from scoped map with non-empty base should be of size 1.", 1, coll.size());
    
    Iterator it = coll.iterator();
    Map.Entry entry = (Map.Entry)it.next();
    assertEquals("Entry key should equal \'test\'", "test", entry.getKey());
    it.remove();
    assertEquals("After iterator remove, map should still be of size 1.", 1, map.size());
    
    ScopedMap map2 = new ScopedMap();
    Set coll2 = map2.entrySet();
    assertEquals("EntrySet from scoped map with empty base should be of size 0.", 0, coll2.size());
    map2.put("test", "testVal");
    assertEquals("After put, entrySet from scoped map with empty base should be of size 1.", 1, coll2.size());

    Iterator it2 = coll2.iterator();
    Map.Entry entry2 = (Map.Entry)it2.next();
    assertEquals("Entry key should equal \'test\'", "test", entry2.getKey());
    it2.remove();
    assertEquals("After iterator remove, map should be of size 0.", 0, map2.size());
    
    try{
      coll2.add(new DummyMapEntry());
      fail("Add to entrySet from scoped map should result in UnsupportedOperationException.");
    }
    catch(UnsupportedOperationException e){
    }
  }

  public void testKeySet() {
    System.out.println("testKeySet");
    Map base = new HashMap();
    base.put("test", "testVal");
    
    ScopedMap map = new ScopedMap(base);
    Set coll = map.keySet();
    assertTrue("KeySet from scoped map with non-empty base should contain \'test\'.", coll.contains("test"));
    coll.remove("test");
    assertTrue("After set remove, map should still contain value \'test\'.", map.containsKey("test"));
    
    ScopedMap map2 = new ScopedMap();
    Set coll2 = map2.keySet();
    assertFalse("KeySet from scoped map with empty base should NOT contain \'test\'.", coll2.contains("test"));
    map2.put("test", "testVal");
    assertTrue("After put, keySet from scoped map with empty base should contain \'test\'.", coll2.contains("test"));
    coll2.remove("test");
    assertFalse("After set remove, map should NOT contain value \'test\'.", map2.containsValue("test"));
    
    try{
      coll2.add("test3");
      fail("Add to keySet from scoped map should result in UnsupportedOperationException.");
    }
    catch(UnsupportedOperationException e){
    }
  }

  public void testGet() {
    System.out.println("testGet");
    Map base = new HashMap();
    base.put("test", "testVal");
    
    ScopedMap map = new ScopedMap(base);
    assertEquals("Get with ScopedMap having base for key \'test\' should be \'testVal\'", "testVal", map.get("test"));
    map.put("test", "testVal4");
    assertEquals("Get with ScopedMap having base for key \'test\' should be \'testVal4\'", "testVal4", map.get("test"));
    
    ScopedMap map2 = new ScopedMap();
    assertNull("Get with ScopedMap for key \'test\' should be null", map2.get("test"));
    map2.put("test", "testVal");
    assertEquals("Get with ScopedMap for key \'test\' should be \'testVal\'", "testVal", map2.get("test"));
  }

  public void testRemove() {
    System.out.println("testRemove");
    Map base = new HashMap();
    base.put("test", "testVal");
    
    ScopedMap map = new ScopedMap(base);
    assertEquals("Get with ScopedMap having base for key \'test\' should be \'testVal\'", "testVal", map.get("test"));
    map.remove("test");
    assertEquals("Get with ScopedMap having base for key \'test\' should be \'testVal\'", "testVal", map.get("test"));
    
    ScopedMap map2 = new ScopedMap();
    map2.put("test", "testVal");
    assertEquals("Get with ScopedMap for key \'test\' should be \'testVal\'", "testVal", map2.get("test"));
    map2.remove("test");
    assertNull("Get with ScopedMap for key \'test\' should be null", map2.get("test"));
  }

  public void testPut() {
    System.out.println("testPut");
    Map base = new HashMap();
    base.put("test", "testVal");
    
    ScopedMap map = new ScopedMap(base);
    assertEquals("Get with ScopedMap having base for key \'test\' should be \'testVal\'", "testVal", map.get("test"));
    map.put("test", "testVal4");
    assertEquals("Get with ScopedMap having base for key \'test\' should be \'testVal4\'", "testVal4", map.get("test"));
    
    ScopedMap map2 = new ScopedMap();
    assertNull("Get with ScopedMap for key \'test\' should be null", map2.get("test"));
    map2.put("test", "testVal");
    assertEquals("Get with ScopedMap for key \'test\' should be \'testVal\'", "testVal", map2.get("test"));
  }

  private static class DummyMapEntry implements Map.Entry{
    
    DummyMapEntry(){}

    public Object getKey() {
      return null;
    }

    public Object getValue() {
      return null;
    }

    public Object setValue(Object value) {
      return null;
    }
  }
}
