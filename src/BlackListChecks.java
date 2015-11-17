import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sagupta on 11/16/15.
 */
// This is the text editor interface.
// Anything you type or change here will be seen by the other person in real time.

//blacklist.txt: "123-456-7890", "123-456-7891", "123-456-7892", ...

public class BlackListChecks {

    Map<String, Boolean> mapBlackList = new ConcurrentHashMap();
    Map<String, Integer> mapBlackListPrefix = new ConcurrentHashMap();


    synchronized void addEntry(String newNumber) {
        mapBlackList.put(newNumber, true);
        // update prefix hash map..

        // realize that concurrent hashmap doesn't help much
    }

    void process(String blackListData) {
        String[] blackListAr = blackListData.split(",");
        for (String ar : blackListAr) {
            mapBlackList.put(ar, true);
            // for prefix of length 3
            if (ar.length() >=3) {
                String prefix = ar.substring(0,3);
                Integer existingCount = mapBlackListPrefix.get(prefix);
                if (existingCount != null) {
                    mapBlackListPrefix.put(prefix,existingCount+1);
                } else {
                    mapBlackListPrefix.put(prefix,1);
                }
            }
            // generalize to handle prefix of any length
        }
    }

    boolean isBlacklist(String blacklistNumber) {
        // find if blacklisted
        Boolean isPresent = mapBlackList.get(blacklistNumber);
        if (isPresent != null && isPresent) {
            return true;
        }
        return false;
    }

    int countNumberOfBlackListWithPrefix(String prefix) {
        int count = 0;
        for(String blacklistNumber : mapBlackListPrefix.keySet()) {
            if (blacklistNumber.startsWith(prefix)) {
                count++;
            }
        }
        return count;
    }

}
