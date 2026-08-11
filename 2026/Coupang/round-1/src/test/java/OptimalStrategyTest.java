import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OptimalStrategyTest {
    private OptimalStrategy sut;
    
    @BeforeEach
    public void setUp() {
        this.sut = new OptimalStrategy();
    }

    @Test
    public void test1() {
        int[] input = new int[] {5, 25, 10, 1};
        assertEquals(26, this.sut.getMaxAmount(input));
    }

    @Test
    public void test2() {
        int[] input = new int[] {5, 3, 7, 10};
        assertEquals(15, this.sut.getMaxAmount(input));
    }

    @Test
    public void test3() {
        int[] input = new int[] {8, 15, 3, 7};
        assertEquals(22, this.sut.getMaxAmount(input));
    }

    @Test
    public void test4() {
        int[] input = new int[] {101, 102, 3, 0};
        assertEquals(104, this.sut.getMaxAmount(input));
    }

    @Test
    public void test5() {
        int[] input = new int[] {20, 30, 2, 10};
        assertEquals(40, this.sut.getMaxAmount(input));
    }

    @Test
    public void test6() {
        int[] input = new int[] {1, 2, 3, 4, 5, 6};
        assertEquals(12, this.sut.getMaxAmount(input));
    }

    @Test
    public void test7() {
        int[] input = new int[] {5, 100, 5, 5};
        assertEquals(105, this.sut.getMaxAmount(input));
    }

    @Test
    public void test8() {
        int[] input = new int[] {10, 10, 10, 10, 10, 10};
        assertEquals(30, this.sut.getMaxAmount(input));
    }

    @Test
    public void test9() {
        int[] input = new int[] {4, 1, 9, 2, 15, 3};
        assertEquals(28, this.sut.getMaxAmount(input));
    }
}
