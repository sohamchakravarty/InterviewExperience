import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HelloWorld Tests")
class HelloWorldTest {

    @Test
    @DisplayName("Should create HelloWorld with builder")
    void testHelloWorldBuilder() {
        HelloWorld hello = HelloWorld.builder()
                .message("Test message")
                .build();

        assertNotNull(hello);
        assertEquals("Test message", hello.getMessage());
    }
}
