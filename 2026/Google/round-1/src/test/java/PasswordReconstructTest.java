import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PasswordReconstructTest {
    private PasswordReconstruct sut;
    
    @BeforeEach
    public void setUp() {
        this.sut = new PasswordReconstruct();
    }

    @Test
    public void test1() {
        List<Character[]> tuples = new ArrayList<>();
        tuples.add(new Character[] {'b', 'a', 'c'});
        tuples.add(new Character[] {'b', 'a', 'd'});
        tuples.add(new Character[] {'c', 'd', 'e'});

        assertEquals("bacde", this.sut.getPassword(tuples));
    }

    @Test
    public void test2() {
        List<Character[]> tuples = new ArrayList<>();
        tuples.add(new Character[] {'d', 'a', 'c'});
        tuples.add(new Character[] {'b', 'a', 'c'});
        tuples.add(new Character[] {'d', 'e', 'f'});
        tuples.add(new Character[] {'b', 'd', 'e'});
        tuples.add(new Character[] {'d', 'c', 'f'});
        tuples.add(new Character[] {'a', 'e', 'c'});

        assertEquals("bdaecf", this.sut.getPassword(tuples));
    }
}
