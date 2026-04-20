    import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import round_1.question_1.StringPermutations;

public class StringPermutationsTest {
    
    @Test
    public void test1() {
        StringPermutations sut = new StringPermutations();

        List<String> expectedResult = Arrays.asList(
            "3Ba","3aB","B3a","Ba3","a3B","aB3"
        );

        List<String> actualResult = sut.permutations("aB3");

        assert actualResult.equals(expectedResult);
    }

    @Test
    public void test2() {
        StringPermutations sut = new StringPermutations();

        List<String> expectedResult = Arrays.asList(
            "4dddd","d4ddd","dd4dd","ddd4d","dddd4"
        );

        List<String> actualResult = sut.permutations("dd4dd");

        assert actualResult.equals(expectedResult);
    }
}
