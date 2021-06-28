package academy.pocu.comp3500.lab7;

import java.util.ArrayList;
import java.util.HashMap;

public class Decryptor {
    private HashMap<String, ArrayList<String>> wordMaps = new HashMap<>();

    public Decryptor(final String[] codeWords) {
        for (String codeWord : codeWords) {
            char[] chars = codeWord.toCharArray();
            QuickSort.sort(chars);

            String sorted = new String(chars);
            wordMaps.putIfAbsent(sorted, new ArrayList<>());
            wordMaps.get(sorted).add(codeWord);
        }
    }

    public String[] findCandidates(final String word) {
        char[] chars = word.toCharArray();
        QuickSort.sort(chars);
        String sorted = new String(chars);

        if (!wordMaps.containsKey(sorted)) {
            return new String[0];
        }
        ArrayList<String> words = wordMaps.get(sorted);
        String[] result = new String[words.size()];
        for (int i = 0; i < words.size(); i++) {
            result[i] = words.get(i);
        }
        return result;
    }
}   
