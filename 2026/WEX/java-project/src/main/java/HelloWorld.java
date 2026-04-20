import lombok.Builder;
import lombok.Data;
import round_1.question_2.StackStringProcessor;

/**
 * Sample class to verify Lombok and project setup.
 */
@Data
@Builder
public class HelloWorld {
    private String message;

    public static void main(String[] args) {
        // HelloWorld hello = HelloWorld.builder()
        //         .message("Hello from Java 25!")
        //         .build();
        // System.out.println(hello);

        StackStringProcessor sp = new StackStringProcessor();
        System.out.println("Testing StackStringProcessor...");
        sp.processInput("O O U 88 U -9 O U -9 O O X");
    }
}
