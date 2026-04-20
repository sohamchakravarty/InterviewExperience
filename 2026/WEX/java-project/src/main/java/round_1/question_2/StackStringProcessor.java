package round_1.question_2;

import java.util.Stack;

public class StackStringProcessor {
    public void processInput(String input) {
        String[] inputArr = input.split(" ");
        Stack<String> resultStk = new Stack<>();

        for (int i = 0; i<inputArr.length; i++) {
            if (inputArr[i].equals("U")) {
                resultStk.push(inputArr[++i]);
            } else if (inputArr[i].equals("O") && !resultStk.isEmpty()) {
                System.out.print(resultStk.pop() + " ");
            } else if (inputArr[i].equals("X")) {
                break;
            }
        }
    }

}
