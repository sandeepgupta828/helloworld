package setassociativecache;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AssociativeCache {
    public static void main(String[] args) throws IOException {
        // SetAssociativeCacheRunner.parseInput(System.in);

        SetAssociativeCache cache = new SetAssociativeCache(1, 2, new ReplacementAlgoFactory().createReplacementAlgo("LRUReplacementAlgo"));


        Thread t1 = new Thread(() -> AssociativeCache.setRandomValues(cache));
        Thread t2 = new Thread(() -> AssociativeCache.setRandomValues(cache));
        Thread t3 = new Thread(() -> AssociativeCache.setRandomValues(cache));
        Thread t4 = new Thread(() -> AssociativeCache.setRandomValues(cache));

        t1.start();
        t2.start();
        t3.start();
        t4.start();


        cache.set(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        cache.set(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        cache.set(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        cache.set(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        cache.set(UUID.randomUUID().toString(), UUID.randomUUID().toString());
    }

    public static void setRandomValues(SetAssociativeCache cache) {
        Integer count = 0;
        while (true) {
            cache.set(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        }
    }

    /**
     * Parses Test Case input to instantiate and invoke a SetAssociativeCache
     * <p>
     * NOTE: You can typically ignore anything in here. Feel free to collapse...
     */
    static class SetAssociativeCacheRunner {
        public static void parseInput(InputStream inputStream) throws IOException {
            InputStreamReader inputReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputReader);

            String line;
            int lineCount = 0;
            SetAssociativeCache cache = null;

            while (!isNullOrEmpty(line = reader.readLine())) {
                lineCount++;
                OutParam<String> replacementAlgoName = new OutParam<>();

                if (lineCount == 1) {

                    cache = createCache(line, replacementAlgoName);

                } else {

                    // All remaining lines invoke instance methods on the SetAssociativeCache
                    Object retValue = SetAssociativeCacheFactory.InvokeCacheMethod(line, cache);

                    // Write the method's return value (if any) to stdout
                    if (retValue != null) {
                        System.out.println(retValue);
                    }
                }
            }
        }
    }

    private static SetAssociativeCache createCache(String inputLine, OutParam<String> replacementAlgoName) {
        String[] cacheParams = Arrays.stream(inputLine.split(",")).map(s -> s.trim()).toArray(n -> new String[n]);
        int setCount = Integer.parseInt(cacheParams[0]);
        int setSize = Integer.parseInt(cacheParams[1]);
        replacementAlgoName.value = cacheParams[2];
        return SetAssociativeCacheFactory.CreateStringCache(setCount, setSize, replacementAlgoName.value);
    }


    // ############################ BEGIN Solution Classes ############################

    /**
     * NOTE: You are free to modify anything below, except for class names and generic interface.
     * Other public interface changes may require updating one or more of the helper classes above
     * for test cases to run and pass.
     * <p>
     * A Set-Associative Cache data structure with fixed capacity.
     * <p>
     * - Data is structured into setCount # of setSize-sized sets.
     * - Every possible key is associated with exactly one set via a hashing algorithm
     * - If more items are added to a set than it has capacity for (i.e. > setSize items),
     * a replacement victim is chosen from that set using an LRU algorithm.
     * <p>
     * NOTE: Part of the exercise is to allow for different kinds of replacement algorithms...
     */
    public static class SetAssociativeCache<TKey, TValue> {
        int SetSize;
        int SetCount;
        CacheSet<TKey, TValue>[] Sets;
        Lock[] SetLocks;


        public SetAssociativeCache(int setCount, int setSize, IReplacementAlgo replacementAlgo) {
            this.SetCount = setCount;
            this.SetSize = setSize;

            // Initialize the sets
            this.Sets = new CacheSet[this.SetCount];
            this.SetLocks = new Lock[this.SetCount];
            for (int i = 0; i < this.SetCount; i++) {
                Sets[i] = new CacheSet<>(setSize, replacementAlgo);
                SetLocks[i] = new ReentrantLock(true);
            }
        }

        /**
         * Gets the value associated with `key`. Throws if key not found.
         */
        public TValue get(TKey key) {
            int setIndex = Math.abs(key.toString().hashCode()) % SetCount;
            CacheSet<TKey, TValue> set = this.Sets[setIndex];
            this.SetLocks[setIndex].lock();
            try {
                return set.get(key);
            } finally {
                this.SetLocks[setIndex].unlock();
            }
        }

        /**
         * Adds the `key` to the cache with the associated value, or overwrites the existing key.
         * If adding would exceed capacity, an existing key is chosen to replace using an LRU algorithm
         * (NOTE: It is part of this exercise to allow for more replacement algos)
         */
        public void set(TKey key, TValue value) {
            int setIndex = Math.abs(key.toString().hashCode()) % SetCount;
            CacheSet<TKey, TValue> set = this.Sets[setIndex];
            this.SetLocks[setIndex].lock();
            try {
                set.set(key, value);
            } finally {
                this.SetLocks[setIndex].unlock();
            }
        }

        /**
         * Returns the count of items in the cache
         */
        public int getCount() {
            int count = 0;
            for (int i = 0; i < this.Sets.length; i++) {
                this.SetLocks[i].lock();
                try {
                    count += this.Sets[i].Count;
                } finally {
                    this.SetLocks[i].unlock();
                }
            }
            return count;
        }

        /**
         * Returns `true` if the given `key` is present in the set; otherwise, `false`.
         */
        public boolean containsKey(TKey key) {
            return this.isInAnySet(key);
        }

        /**
         * checks if key is any of the sets
         *
         * @param key
         * @return
         */
        private boolean isInAnySet(TKey key) {
            for (int i = 0; i < this.Sets.length; i++) {
                this.SetLocks[i].lock();
                try {
                    if (this.Sets[i].Count > 0 && this.Sets[i].containsKey(key)) {
                        return true;
                    }
                } finally {
                    this.SetLocks[i].unlock();
                }
            }
            return false;
        }
    }

    /**
     * An internal data structure representing one set in a N-Way Set-Associative Cache
     */
    static class CacheSet<TKey, TValue> {
        int Capacity;
        CacheItem<TKey, TValue>[] Store;
        IReplacementAlgo<TKey> replacementAlgo;
        public int Count = 0;


        public CacheSet(int capacity, IReplacementAlgo replacementAlgo) {
            this.Capacity = capacity;
            this.Store = new CacheItem[capacity];
            this.replacementAlgo = replacementAlgo;
        }

        /**
         * Gets the value associated with `key`. Throws if key not found.
         */
        public TValue get(TKey key) {
            // If the key is present, update the usage tracker
            int indexOfKey = this.findIndexOfKey(key);
            if (indexOfKey >= 0) {
                this.recordUsage(key);
            } else {
                throw new RuntimeException(String.format("The key '%s' was not found", key));
            }

            return this.Store[indexOfKey].value;
        }

        /**
         * Adds the `key` to the cache with the associated value, or overwrites the existing key.
         * If adding would exceed capacity, an existing key is chosen to replace using an LRU algorithm
         * (NOTE: It is part of this exercise to allow for more replacement algos)
         */
        synchronized public void set(TKey key, TValue value) {
            int indexOfKey = this.findIndexOfKey(key);

            if (indexOfKey >= 0) {

                this.Store[indexOfKey].value = value;

            } else {
                int indexToSet;
                // If the set is at it's capacity
                if (this.Count == this.Capacity) {
                    TKey keyToReplace = replacementAlgo.getNextKeyToReplace();
                    indexToSet = this.findIndexOfKey(keyToReplace);
                    if (indexToSet < 0) {
                        // this is interesting case when we cannot find the key in cache that is tracked by replacement algo
                        // this is only possible when removal from cache did not correspondingly remove the key in the replacement algo
                        // this would be a "bug" in this implementation (to be figured)
                        // but if it happens we pick a last index to be freed
                        indexToSet = Capacity - 1;
                        this.removeKey(this.Store[indexToSet].key);
                    }
                    // Remove the key provided by algo
                    this.removeKey(keyToReplace);
                } else {
                    indexToSet = this.Count;
                }

                this.Store[indexToSet] = new CacheItem<>(key, value);
                this.Count++;

            }

            this.recordUsage(key);
        }

        /**
         * Returns `true` if the given `key` is present in the set; otherwise, `false`.
         */
        public boolean containsKey(TKey key) {
            return this.findIndexOfKey(key) >= 0;
        }

        private void removeKey(TKey key) {
            int indexOfKey = this.findIndexOfKey(key);
            replacementAlgo.recordKeyRemoval(key);
            if (indexOfKey >= 0) {
                this.Store[indexOfKey] = null;
                this.Count--;
            }
        }

        private int findIndexOfKey(TKey key) {
            for (int i = 0; i < this.Count; i++) {
                if (this.Store[i] != null && this.Store[i].key.equals(key)) return i;
            }
            return -1;
        }

        private void recordUsage(TKey key) {
            this.replacementAlgo.recordKeyUsage(key);
        }
    }

    /**
     * An internal data structure representing a single item in an N-Way Set-Associative Cache
     */
    static class CacheItem<TKey, TValue> {
        public TKey key;
        public TValue value;

        public CacheItem(TKey key, TValue value) {
            this.key = key;
            this.value = value;
        }
    }

    public final static String LruAlgorithm = "LRUReplacementAlgo";
    public final static String MruAlgorithm = "MRUReplacementAlgo";

    /**
     * A common interface for replacement algos, which decide which item in a CacheSet to evict
     */
    interface IReplacementAlgo<TKey> {
        TKey getNextKeyToReplace();

        void recordKeyUsage(TKey key);

        void recordKeyRemoval(TKey key);
    }

    static class LRUReplacementAlgo<TKey> implements IReplacementAlgo<TKey> {
        List<TKey> keyList;

        public LRUReplacementAlgo() {
            this.keyList = new ArrayList<>();
        }

        @Override
        synchronized public TKey getNextKeyToReplace() {
            return keyList.get(keyList.size() - 1);
        }

        @Override
        synchronized public void recordKeyUsage(TKey key) {
            keyList.remove(key);
            //System.out.println("KeyRemoved:"+key);
            keyList.add(0, key);
            //System.out.println("KeyAdded:"+key);
        }

        @Override
        synchronized public void recordKeyRemoval(TKey key) {
            keyList.remove(key);
            //System.out.println("KeyRemoved:"+key);
        }
    }

    static class MRUReplacementAlgo<TKey> implements IReplacementAlgo<TKey> {
        Deque<TKey> keyDeque;

        public MRUReplacementAlgo() {
            this.keyDeque = new LinkedList<>();
        }

        @Override
        public TKey getNextKeyToReplace() {
            return keyDeque.getLast();
        }

        @Override
        public void recordKeyUsage(TKey key) {
            keyDeque.remove(key);
            keyDeque.addLast(key);
        }

        @Override
        public void recordKeyRemoval(TKey key) {
            keyDeque.remove(key);
        }
    }

    // ############################ BEGIN Helper Classes ############################
    // NOTE: Your code in the classes below will not be evaluated as part of the exericse.
    // They are just used by the stub code in the header to help run HackerRank test cases.
    // You may need to make small modifications to these classes, depending on your interface design,
    // for tests to run and pass, but it is not a core part of the exercise
    //
    static class OutParam<T> {
        public T value;
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static class SetAssociativeCacheFactory {
        /// NOTE: replacementAlgoName is provided in case you need it here. Whether you do will depend on your interface design.
        public static SetAssociativeCache CreateStringCache(int setCount, int setSize, String replacementAlgoName) {
            IReplacementAlgo<String> replacementAlgo = new ReplacementAlgoFactory().createReplacementAlgo(replacementAlgoName);

            return new SetAssociativeCache(setCount, setSize, replacementAlgo);
        }

        /// NOTE: Modify only if you change the main interface of SetAssociativeCache
        public static Object InvokeCacheMethod(String inputLine, SetAssociativeCache cacheInstance) {
            String[] callArgs = Arrays.stream(inputLine.split(",", -1)).map(a -> a.trim()).toArray(n -> new String[n]);

            String methodName = callArgs[0].toLowerCase();
            //String[] callParams = Arrays.copyOfRange(callArgs, 1, callArgs.length - 1); // TODO: This is unused

            switch (methodName) {
                case "get":
                    return cacheInstance.get(callArgs[1]);
                case "set":
                    cacheInstance.set(callArgs[1], callArgs[2]);
                    return null;
                case "containskey":
                    return cacheInstance.containsKey(callArgs[1]);
                case "getcount":
                    return cacheInstance.getCount();

                // TODO: If you want to add and test other public methods to SetAssociativeCache,
                //  add them to the switch statement here... (this is not common)

                default:
                    throw new RuntimeException(String.format("Unknown method name '{%s}'", methodName));
            }
        }
    }

    public static class ReplacementAlgoFactory {
        IReplacementAlgo createReplacementAlgo(String replacementAlgoName) {
            switch (replacementAlgoName) {
                case LruAlgorithm:
                    return new LRUReplacementAlgo();
                case MruAlgorithm:
                    return new MRUReplacementAlgo();
                default:
                    // TODO: If you want to test other replacement algos, add them to the switch statement here...
                    throw new RuntimeException(String.format("Unknown replacement algo '%s'", replacementAlgoName));
            }
        }
    }


// ^^ ######################### END Helper Classes ######################### ^^

}

