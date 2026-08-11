import java.util.Arrays;

public class HelloWorld {
    public static void main(String[] args) {
        String[][] input = {
            {"ADD", "1"},
            {"ADD", "2"},
            {"ADD", "5"},
            {"ADD", "2"},
            {"EXISTS", "2"},
            {"EXISTS", "5"},
            {"EXISTS", "1"},
            {"EXISTS", "4"}
        };

        String[] result = solution(input);

        System.out.print("[");
        for (String s : result) {
            System.out.print(s + ",");
        }
        System.out.print("]");
    }

    private static String[] valArr;

    private static String[] solution(String[][] queries) {
        String[] result = new String[queries.length];
        valArr = new String[queries.length];
        
        int i = 0;
        for (String[] query : queries) {
            if (query[0].equals("ADD")) {
                valArr[i] = query[1];
                result[i] = "";
            } else if (Arrays.stream(valArr).anyMatch(query[1]::equals)) {
                    result[i] = "true";
            } else {
                result[i] = "false";
            }
            
            i++;
        }
        
        return result;
    }


}
