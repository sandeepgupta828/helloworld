package com.programs.matrixgraph;

//https://leetcode.com/problems/word-ladder-ii/
//https://leetcode.com/explore/interview/card/google/61/trees-and-graphs/3068/

/*
Input:
beginWord = "hit",
endWord = "cog",
wordList = ["hot","dot","dog","lot","log","cog"]

Output: 5

Explanation: As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
return its length 5.
 */

import java.util.*;
import java.util.stream.Collectors;

/**
 * algo: given a word, it's possible parent words can be computed that have 1 letter deleted
 *       hit -> {it, ht, hi}
 *       create a map of parent word --> list of child words
 *
 *       it -> [hit, its]
 *       ht --> [hit, hot]
 *       ...
 *
 *       next create a graph between words for a given word list
 *       where words neighbours are that share same parent word
 *
 *       hit<-(ht)->hot<-(ot)->cot and so on
 *
 *       next given a source word, not in the word list, find possible parent words
 *       using parent word map get list of word nodes in the graph
 *       across each of these word nodes, find overall shortest possible path to target word node.
 *       BFS gives shortest possible path
 */
public class WordLadder {
    public static void main(String[] args) {
        System.out.println(findAllPaths("hit", "cog", Arrays.asList("hot","dot","dog","lot","log","cog", "pit", "pot", "cot")));
    }

    public static class Node {
        String word;
        List<Node> neighbourNodes;
    }

    public static class PathNode {
        public PathNode(Node node, PathNode prevNode) {
            this.node = node;
            this.prevPathNode = prevNode;
        }

        Node node;
        PathNode prevPathNode;
    }

    public static List<List<String>> findAllPaths(String sourceWord, String targetWord, List<String> words) {
          if (!words.contains(targetWord)) return Collections.emptyList();
          Map<String, List<String>> parentToChildList = new HashMap<>();
          Map<String, List<String>> childToParentList = new HashMap<>();

          // precompute: build the map
          for (String word: words) {
              List<String> parentWords = getParentWords(word);
              childToParentList.put(word, parentWords);
              for (String parentWord: parentWords) {
                  List<String> list = parentToChildList.getOrDefault(parentWord, new ArrayList<>());
                  list.add(word);
                  parentToChildList.putIfAbsent(parentWord, list);
              }
          }
          // precompute: build the graph

          // build all nodes without any edges
          Map<String, Node> wordToNode = new HashMap<>();
          for (String word: words) {
              Node wordNode = new Node();
              wordNode.word = word;
              wordNode.neighbourNodes = new ArrayList<>();
              wordToNode.put(word, wordNode);
          }

          // add the edges
        for (String word: words) {
            // for each parent word
            for (String parentWord: childToParentList.get(word)) {
                wordToNode.get(word).neighbourNodes.addAll(parentToChildList.get(parentWord).stream().filter(childWord -> !childWord.equals(word)).map(childWord -> wordToNode.get(childWord)).collect(Collectors.toList()));
            }
        }

        // traverse the graph to find shortest path from source to target word
        List<String> parentWords = getParentWords(sourceWord);

        List<List<String>> paths = new ArrayList<>();
        for (String parentWord: parentWords) {
            if (parentToChildList.containsKey(parentWord)) {
                // we have this common parent word and it can lead to entry in the graph
                for (String child: parentToChildList.get(parentWord)) {
                    // go over paths to see if this child has been in one of the paths, if so no need to traverse again
                    if (!paths.stream().anyMatch(path -> path.contains(child))) {
                        List<String> newPath = traverseGraph(wordToNode.get(child), targetWord);
                        if (newPath.size() > 0) {
                            paths.add(traverseGraph(wordToNode.get(child), targetWord));
                        }
                    }
                }
            } else {
                // ignore as we don't have this common parent with any word
            }
        }
        return paths;
    }

    /**
     * Do BFS on graph
     * To figure path to a matching node use PathNode wrapper that preserves reference "prevPathNode" to Node that added given node to the queue
     * When a node is matched, walking up the prev links helps us create the path to/from root to matching node.
     * @param root
     * @param targetWord
     * @return
     */
    private static List<String> traverseGraph(Node root, String targetWord) {
        List<String> pathToTarget = new ArrayList<>();
        Set<String> visitedWords = new HashSet<>();
        Deque<PathNode> pathNodeQueue = new ArrayDeque<>();
        pathNodeQueue.add(new PathNode(root, null));
        visitedWords.add(root.word);

        while(pathNodeQueue.size() !=0) {
            PathNode pathNode = pathNodeQueue.removeFirst();
            Node nextNode = pathNode.node;
            if (nextNode.word.equals(targetWord)) {
                pathToTarget.add(nextNode.word);
                PathNode prevPathNode = pathNode.prevPathNode;
                while (prevPathNode != null) {
                    pathToTarget.add(prevPathNode.node.word);
                    prevPathNode = prevPathNode.prevPathNode;
                }
                break;
            }
            pathNodeQueue.addAll(nextNode.neighbourNodes.stream().filter(node -> !visitedWords.contains(node.word)).map(node -> {visitedWords.add(node.word); return new PathNode(node, pathNode);}).collect(Collectors.toList()));
        }
        Collections.reverse(pathToTarget);
        return pathToTarget;
    }

    private static List<String> getParentWords(String word) {
        List<String> parentWords = new ArrayList<>();
        for (int i=0;i<word.length();i++) {
            if (i==0) {
                parentWords.add(word.substring(i+1));
                continue;
            }
            parentWords.add(word.substring(0,i)+word.substring(i+1));
        }
        return parentWords;
    }

}
