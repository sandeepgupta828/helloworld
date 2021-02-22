package com.programs.searchword;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/* Given two sentences, each represented as array of words (words1 and words2) and list of similar word pairs (pairs),
 determine if two sentences are similar.

Example:
   Input:
    words1 = ["great", "acting", "skills"]
    words2 = ["fine", "drama", "talent"]
    pairs = [["great", "good"], ["fine", "good"], ["acting","drama"], ["skills","talent"]]
   Output
     True
 */
public class CheckSimilarity {

  public static void main(String[] args) {
    new CheckSimilarity().isSimilar(new String[]{"great", "acting", "skills"},
        new String[]{"fine", "drama", "talent"},
        new String[][]{{"great", "good"}, {"fine", "nice"}, {"great", "nice"}, {"acting","drama"}, {"skills","talent"}});
  }

  public boolean isSimilar(String[] words1, String[] words2, String[][] pairs) {
    // create sets of pairs
    Map<UUID, Set<String>> sets = new HashMap<>();

    // create lookup from word to set
    Map<String, UUID> wordToSetId = new HashMap<>();

    for (String[] pair: pairs) {
      // case1:
      // no word is associated with any set id : create a new set
      if (!wordToSetId.containsKey(pair[0]) && !wordToSetId.containsKey(pair[1])) {
        UUID setId = createSet(sets);
        mapWordToSet(sets, wordToSetId, pair[0], setId);
        mapWordToSet(sets, wordToSetId, pair[1], setId);
      }
      // case2:
      // one word is associated with a setId and other is not: merge other word to found set
      if ((wordToSetId.containsKey(pair[0]) && !wordToSetId.containsKey(pair[1])) ||
          (!wordToSetId.containsKey(pair[0]) && wordToSetId.containsKey(pair[1]))
      ) {
        UUID setId = wordToSetId.get(pair[0]) != null ? wordToSetId.get(pair[0]) : wordToSetId.get(pair[1]);
        if (wordToSetId.get(pair[0]) == null) {
          mapWordToSet(sets, wordToSetId, pair[0], setId);
        }
        if (wordToSetId.get(pair[1]) == null) {
          mapWordToSet(sets, wordToSetId, pair[1], setId);
        }
      }
      // case3:
      // both words are associated with different setIds : merge sets to a new setId, update mapping from word to setId
      if (wordToSetId.containsKey(pair[0])
          && wordToSetId.containsKey(pair[1])
          && !wordToSetId.get(pair[0]).equals(wordToSetId.containsKey(pair[1]))) {
        UUID setId1 = wordToSetId.get(pair[0]);
        UUID setId2 = wordToSetId.get(pair[1]);
        // create a merged set
        UUID mergedSetId = createSet(sets);
        for (String word: sets.get(setId1)) {
          mapWordToSet(sets, wordToSetId, word, mergedSetId);
        }
        for (String word: sets.get(setId2)) {
          mapWordToSet(sets, wordToSetId, word, mergedSetId);
        }
        sets.remove(setId1);
        sets.remove(setId2);
      }

      // case4:
      // both words are associated with same setIds
    }

    boolean isSimilar = true;
    for (int i=0; i<words1.length;i++) {
      if (wordToSetId.get(words1[i]).equals(wordToSetId.get(words2[i]))) {
        System.out.println(words1[i] + " and "+ words2[i] + " are similar");
      } else {
        isSimilar = false;
        System.out.println(words1[i] + " and "+ words2[i] + " are different");
      }
    }
    return isSimilar;
  }

  private UUID createSet(Map<UUID, Set<String>> sets) {
    // create setId
    UUID id = UUID.randomUUID();
    // create set
    Set<String> set = new HashSet<>();
    // add set
    sets.put(id,set);
    return id;
  }

  private void mapWordToSet(Map<UUID, Set<String>> sets, Map<String, UUID> wordToSetId, String word, UUID setId) {
    if (sets.containsKey(setId)) {
      sets.get(setId).add(word);
      wordToSetId.put(word, setId);
    }
  }
}
