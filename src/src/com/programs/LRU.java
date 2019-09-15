package com.programs;

// This is the text editor interface.
// Anything you type or change here will be seen by the other person in real time.

// LRU class with one public function: isDuplucate(String) return True | False; max=3

import java.util.*;

// Reference: https://docs.oracle.com/javase/8/docs/api/java/util/LinkedHashMap.html#removeEldestEntry-java.util.Map.Entry-

public class LRU {

    private final Integer MAX_ENTRIES = 3;
    private CustomLinkedHashMap cache = new CustomLinkedHashMap(MAX_ENTRIES, 0.75F, true); // turn on access order by last param being true


    public static void main(String[] args) {
        // invoke the method
        LRU cache = new LRU();
        // test -ve (string is absent)
        System.out.println("Result="+ Boolean.toString(cache.isDuplicate("someentity")));
        cache.add("someentity1");
        // test +ve
        System.out.println("Result="+ Boolean.toString(cache.isDuplicate("someentity1")));
        // test deletion of eldest
        cache.add("someentity2");
        cache.add("someentity3");
        cache.add("someentity4");
        // now expected "someentity1" to be gone
        System.out.println("Result="+ Boolean.toString(cache.isDuplicate("someentity1")));
    }

    public boolean isDuplicate(String str) {
        return cache.containsKey(str);
    }

    public void add(String str) {
        cache.put(str, ""); // invoking "put" generates access to this entry
    }

    // subclass  LinkedHashMap so that we can override removeEldestEntry(Map.Entry<K,V> eldest)

    public class CustomLinkedHashMap extends LinkedHashMap<String, String> {
        // c-tor
        public CustomLinkedHashMap(int initialCapacity, float loadFactor, boolean accessOrder) {
            super(initialCapacity, loadFactor, accessOrder);
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<String,String> eldest) {
            return size() > MAX_ENTRIES;
        }
    }

}