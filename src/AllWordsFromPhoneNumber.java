import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sagupta on 11/5/15.
 */
public class AllWordsFromPhoneNumber {

    Map<Integer, String> letterMap = new HashMap<>();

    public class LetterBlock {
        LetterBlock prevBlock;
        Integer blockIndex = -1;
        public LetterBlock(String block) {
            this.block = block;
        }
        String block;
        Integer currentIndex = 0;

        boolean isMaxIndex() {
            return currentIndex.equals(block.length());
        }

        void reset() {
            currentIndex = 0;
        }

        char getNextChar() {
            return block.charAt(currentIndex++);
        }
    }
    // init block...
    {
        letterMap.put(2,"abc");
        letterMap.put(3,"def");
        letterMap.put(4,"ghi");
        letterMap.put(5,"jkl");
        letterMap.put(6,"mno");
        letterMap.put(7,"pqrs");
        letterMap.put(8,"tuv");
        letterMap.put(9,"wxyz");

    }


    public void solution(int[] phoneDigits) {
        LetterBlock prevBlock = null;
        LetterBlock block = null;
        LetterBlock firstBlock = null;
        LetterBlock lastBlock = null;

        StringBuffer sBuffer = new StringBuffer();
        for (int i=0;i<phoneDigits.length;i++) {
            block = new LetterBlock(letterMap.get(phoneDigits[i]));
            if (i == 0) {
                firstBlock = block;
            }
            block.blockIndex = i;
            sBuffer.append(block.getNextChar());
            block.prevBlock = prevBlock;
            prevBlock = block;
        }

        lastBlock = block;


        while(true) {

            if (block == lastBlock) {
                System.out.println(sBuffer);
                while (!block.isMaxIndex()) {
                    sBuffer.setCharAt(block.blockIndex, block.getNextChar());
                    System.out.println(sBuffer);
                }
                block.reset();
                sBuffer.setCharAt(block.blockIndex, block.getNextChar());
                block = block.prevBlock;
            }

            while (block != lastBlock && block != firstBlock) {
                if (!block.isMaxIndex()) {
                    sBuffer.setCharAt(block.blockIndex, block.getNextChar());
                    break;
                } else {
                    block.reset();
                    sBuffer.setCharAt(block.blockIndex, block.getNextChar());
                    block = block.prevBlock;
                }
            }

            if (block == firstBlock) {
                if (block.isMaxIndex()) {
                    break;
                } else {
                    sBuffer.setCharAt(block.blockIndex, block.getNextChar());
                }
            }
            block = lastBlock;
        }


    }
}