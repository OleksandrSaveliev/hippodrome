import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class HorseTest {
    private Horse horse;

    @BeforeEach
    public void setUp() {
        horse = new Horse("John Doe", 123.01, 123.01);
    }

    @Test
    public void constructor_shouldThrowIllegalArgumentException_whenPassNullAsName() {
        assertThrows(IllegalArgumentException.class,
                () -> new Horse(null, 1.1, 1d));
    }

    @Test
    public void constructor_shouldThrowExceptionWithExpectedMessage_whenPassNullAsName() {
        String message = assertThrows(IllegalArgumentException.class,
                () -> new Horse(null, 1.1, 1d)).getMessage();
        assertEquals("Name cannot be null.", message);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n"})
    public void constructor_shouldThrowIllegalArgumentException_whenPassInvalidName(String string) {
        assertThrows(IllegalArgumentException.class,
                () -> new Horse(string, 1.1, 1d));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n"})
    public void constructor_shouldThrowExceptionWithExpectedMessage_whenPassInvalidName(String string) {
        String message = assertThrows(IllegalArgumentException.class,
                () -> new Horse(string, 1.1, 1d)).getMessage();

        assertEquals("Name cannot be blank.", message);
    }

    @Test
    public void constructor_shouldThrowIllegalArgumentException_whenPassNegativeSpeed() {
        assertThrows(IllegalArgumentException.class,
                () -> new Horse("name", -1.1, 1d));
    }

    @Test
    public void constructor_shouldThrowExceptionWithExpectedMessage_whenPassNegativeSpeed() {
        String message = assertThrows(IllegalArgumentException.class,
                () -> new Horse("name", -1.1, 1d)).getMessage();
        assertEquals("Speed cannot be negative.", message);
    }

    @Test
    public void constructor_shouldThrowIllegalArgumentException_whenPassNegativeDistance() {
        assertThrows(IllegalArgumentException.class,
                () -> new Horse("name", 1.1, -1d));
    }

    @Test
    public void constructor_shouldThrowExceptionWithExpectedMessage_whenPassNegativeDistance() {
        String message = assertThrows(IllegalArgumentException.class,
                () -> new Horse("name", 1.1, -1d)).getMessage();
        assertEquals("Distance cannot be negative.", message);
    }

    @Test
    public void getName_shouldReturnValidName_whenValidConstructorArguments() {
        assertEquals("John Doe", horse.getName());
    }

    @Test
    public void getSpeed_shouldReturnValidSpeed_whenValidConstructorArguments() {
        assertEquals(123.01, horse.getSpeed());
    }

    @Test
    public void getDistance_shouldReturnValidDistance_whenValidConstructorArguments() {
        assertEquals(123.01, horse.getDistance());
    }

    @Test
    public void move_shouldInvokeGetRandomDouble_withProvidedArguments() {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horse.move();

            horseMockedStatic.verify(
                    () -> Horse.getRandomDouble(0.2, 0.9),
                    times(1)
            );
        }
    }

    @Test
    public void move_shouldSetCorrectDistanceValue_withProvidedArguments() {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9))
                    .thenReturn(0.5);
            double initialDistance = horse.getDistance();

            horse.move();
            double expectedDistance = initialDistance + (horse.getSpeed() * 0.5);

            assertEquals(expectedDistance, horse.getDistance(), 0.001);
        }
    }
}
