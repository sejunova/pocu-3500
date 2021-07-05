package academy.pocu.comp3500.lab7;

import java.util.ArrayList;
import java.util.HashMap;

public class Decryptor {
    private Trie trie = new Trie();

    public Decryptor(final String[] codeWords) {
        for (String codeWord : codeWords) {
            codeWord = codeWord.toLowerCase();
            trie.insert(codeWord);
        }
    }

    public String[] findCandidates(final String word) {
        String wordLowercase = word.toLowerCase();
        ArrayList<String> candidates = trie.search(wordLowercase);
        String[] result = new String[candidates.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = candidates.get(i);
        }
        return result;
    }
}
