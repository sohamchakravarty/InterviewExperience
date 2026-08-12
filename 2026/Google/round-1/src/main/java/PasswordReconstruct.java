import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PasswordReconstruct {

    private Map<Character, Set<Character>> nextCharacterMap;
    private Set<Character> visitedCharacters;
    private List<Character> resultList;

    public PasswordReconstruct() { 
        this.nextCharacterMap = new HashMap<>();
        this.visitedCharacters = new HashSet<>();
        this.resultList = new ArrayList<>();
    }

    public String getPassword(List<Character[]> tuples) {
        // build the nextCharacterMap
        for (Character[] tuple : tuples) {
            Character c1 = tuple[0];
            Character c2 = tuple[1];
            Character c3 = tuple[2];
            
            updateNextCharacterMap(c1, c2);
            updateNextCharacterMap(c2, c3);
        }

        // identify the order of characters
        for(Character c : this.nextCharacterMap.keySet()) {
            dfs(c);    
        }

        // construct the password
        StringBuffer result = new StringBuffer();
        for(int i=resultList.size() - 1; i>=0; i--) {
            result.append(this.resultList.get(i));
        }

        return result.toString();
    }

    private void dfs(Character currentChar) {
        if(this.visitedCharacters.contains(currentChar)) {
            return;
        }

        Set<Character> nexCharacters = this.nextCharacterMap.get(currentChar);
        if (nexCharacters != null) {
            for (Character nextChar : nexCharacters) {
                dfs(nextChar);
            }
        }

        this.visitedCharacters.add(currentChar);
        this.resultList.add(currentChar);
    }

    private void updateNextCharacterMap(Character c1, Character c2) {
        Set<Character> nextCharacters = this.nextCharacterMap.computeIfAbsent(c1, s -> new HashSet<>());
        nextCharacters.add(c2);
    }
}