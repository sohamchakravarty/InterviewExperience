package round_1.question_1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringPermutations {
    private List<String> result = new ArrayList<>();

    public List<String> permutations(String str) {
        if (str == null || str.length() == 0) {
            return result;
        }

        char[] strCharArray = str.toCharArray();
        Arrays.sort(strCharArray);
        boolean[] used = new boolean[strCharArray.length];
        
        backtrack(strCharArray, used, new ArrayList<>());
        return result;
    }

    private void backtrack(char[] input, boolean[] used, List<Character> temp) {
        if (temp.size() == input.length) {
            String tempStr = convertCharacterListToString(temp);
            result.add(tempStr);
            return;
        }

        for (int i = 0; i < input.length; i++) {
            if (used[i]) 
                continue;
            
            // If the current character is the same as the previous character 
            // and the previous character has not been used in this recursion path, 
            // skip it to avoid generating duplicate permutations.
            if (i > 0 && input[i] == input[i - 1] && !used[i - 1]) 
                continue;

            used[i] = true;
            temp.add(input[i]);
            backtrack(input, used, temp);
            temp.remove(temp.size() - 1);
            used[i] = false;
        }
    }

    private String convertCharacterListToString(List<Character> charList) {
        StringBuilder sb = new StringBuilder(charList.size());
        for (Character c : charList) {
            sb.append(c);
        }
        return sb.toString();
    }
}
