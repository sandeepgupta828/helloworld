package com.programs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
/*
sample input:
Given an arbitrary text document written in English, write a program that will generate a
concordance, i.e. an alphabetical list of all word occurrences, labeled with word frequencies.
Bonus: label each word with the sentence numbers in which each occurrence appeared.

output:
a {2:[1, 1]}
all {1:[1]}
alphabetical {1:[1]}
an {2:[1, 1]}
appeared {1:[2]}
arbitrary {1:[1]}
bonus {1:[2]}
concordance {1:[1]}
document {1:[1]}
each {2:[2, 2]}
english {1:[1]}
frequencies {1:[1]}
generate {1:[1]}
given {1:[1]}
i.e. {1:[1]}
in {2:[1, 2]}
label {1:[2]}
labeled {1:[1]}
list {1:[1]}
numbers {1:[2]}
occurrence {1:[2]}
occurrences {1:[1]}
of {1:[1]}
program {1:[1]}
sentence {1:[2]}
text {1:[1]}
that {1:[1]}
the {1:[2]}
which {1:[2]}
will {1:[1]}
with {2:[1, 2]}
word {3:[1, 1, 2]}
write {1:[1]}
written {1:[1]}
 */

public class ComputeWordFrequency {
    private Map<String, WordStats> frequencyMap = new TreeMap<>();
    private List<Character> sentenceEndChars = new ArrayList<>();
    private String splitRegEx = "";
    {
        sentenceEndChars.add('.');
        sentenceEndChars.add('?');
        sentenceEndChars.add('!');
        // build regex string for splitting line along above sentence delimiter chars
        for (int i = 0; i<sentenceEndChars.size();i++) {
            if (!splitRegEx.isEmpty()) {
                splitRegEx += '|';
            }
            // handle special regex characters
            if (sentenceEndChars.get(i) == '.' || sentenceEndChars.get(i) == '?') {
                splitRegEx += "\\";
            }
            splitRegEx += sentenceEndChars.get(i);
        }
    }

    public static void main(String[] args) throws IOException {
        ComputeWordFrequency computeWordFrequency = new ComputeWordFrequency();
        Map<String, ComputeWordFrequency.WordStats> result = computeWordFrequency.compute("/Users/sagupta/g/helloworld/src/com/programs/somefile.txt");
        File outputFile = new File("/Users/sagupta/g/helloworld/src/com/programs/somefile.txt.freq.txt");
        // delete any existing output file
        outputFile.delete();
        if (!outputFile.createNewFile()) {
            System.out.println("Couldn't create file");
        } else {
            FileWriter writer = new FileWriter(outputFile);
            for (String word : result.keySet()) {
                String output = word + " {" + result.get(word).getCount() + ":" + result.get(word).getLineList().toString() + "}";
                System.out.println(output);
                writer.write(output + "\n");
            }
            writer.close();
        }
    }

    /*
    Highlevel:
    We scan through the lines and do some pre-processing such as:
    (a) remove empty lines
    (b) replace some special words that contain sentence delimiters such as "i.e."
    (c) merge sentences that split across more than 1 line into 1 line
    (d) normalize: have 1 sentence per line
    Then we use a tree map (which keeps its data sorted) to compute frequency per word and a list of sentence indexes on which it occurred.
    At last we replace back the special words
     */
    public Map<String, WordStats> compute(String fileName) {
        List<String> lines = readFile(fileName);
        Deque<String> mergedLines = new ArrayDeque<>();
        lines.forEach( line -> {
                    // words such as "i.e." that contain sentence delimiters are mapped to a version without any delimiters, and later mapped back
                    String processedLine = line.replace("i.e.", "ie").trim();
                    if (processedLine.isEmpty()) return; // skip this line
                    String prevLine = null;
                    if (mergedLines.size() > 0) {
                        prevLine = mergedLines.getLast();
                    }
                    // coalesce this line with previous line if there is a previous line that didn't end yet
                    if (prevLine != null && !sentenceEndChars.contains(prevLine.charAt(prevLine.length() - 1))) {
                        mergedLines.removeLast();
                        mergedLines.addLast(prevLine + " " + processedLine);
                    } else {
                        mergedLines.addLast(processedLine);
                    }
                }
            );
        List<String> flattenedSentences = mergedLines.stream().flatMap(lineWithPossibleMultipleSetences -> Stream.of(lineWithPossibleMultipleSetences.split(splitRegEx))).map(line -> line.trim()).collect(Collectors.toList());
        IntStream.range(1, flattenedSentences.size()+1).forEach(sentenceIndex -> computeFreq(frequencyMap, flattenedSentences.get(sentenceIndex-1), sentenceIndex));
        // put "i.e." back if there was an "ie" and ie is not an english word itself
        if (frequencyMap.containsKey("ie")) {
            frequencyMap.put("i.e.", frequencyMap.get("ie"));
            frequencyMap.remove("ie");
        }
        return frequencyMap;
    }


    private void computeFreq(Map<String, WordStats> frequencyMap, String line, Integer lineIndex) {
        Stream.of(line.split(" ")).map(word -> word.replaceAll("[^a-zA-Z]", "").toLowerCase()).forEach(word -> {
            WordStats wordStats = new WordStats();
            WordStats currentWordStats = frequencyMap.putIfAbsent(word, wordStats);
            WordStats wordStatsToUpdate = currentWordStats != null ? currentWordStats : wordStats;
            wordStatsToUpdate.incrementCount();
            wordStatsToUpdate.addLineIndex(lineIndex);
        });
    }

    private List<String> readFile(String fileName) {
        String line;
        List<String> lines = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'" + ex.toString());
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'" + ex.toString());
        }
        return lines;
    }

    private class WordStats {
        Integer count = 0;
        List<Integer> lineList = new ArrayList<>();

        public Integer getCount() {
            return count;
        }

        public void incrementCount() {
            count += 1;
        }

        public void addLineIndex(Integer lineIndex) {
            lineList.add(lineIndex);
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public List<Integer> getLineList() {
            return lineList;
        }

        public void setLineList(List<Integer> lineList) {
            this.lineList = lineList;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("WordStats{");
            sb.append("count=").append(count);
            sb.append(", lineList=").append(lineList);
            sb.append('}');
            return sb.toString();
        }
    }
}
