import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class HippodromeTest {

    @Test
    public void constructor_shouldThrowIllegalArgumentException_whenPassNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(null));
    }

    @Test
    public void constructor_shouldThrowExceptionWithExpectedMessage_whenPassNull() {
        String message = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(null)).getMessage();

        assertEquals("Horses cannot be null.", message);
    }

    @Test
    public void constructor_shouldThrowIllegalArgumentException_whenPassEmptyList() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(List.of()));
    }

    @Test
    public void constructor_shouldThrowExceptionWithExpectedMessage_whenPassEmptyList() {
        String message = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(List.of())).getMessage();

        assertEquals("Horses cannot be empty.", message);
    }


    @Test
    public void getHorses_shouldReturnCorrectList_whenPassListToConstructor() {
        List<Horse> horses = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            horses.add(mock(Horse.class));
        }

        Hippodrome hippodrome = new Hippodrome(horses);

        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    void move_shouldInvokeMoveOnEachHorse_whenPassListToConstructor() {
        List<Horse> horses = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            horses.add(mock(Horse.class));
        }

        Hippodrome hippodrome = new Hippodrome(horses);

        hippodrome.move();

        for (Horse horse : horses) {
            verify(horse, times(1)).move();
        }
    }

    @Test
    public void getWinner_shouldReturnHorseWithGreatestDistance() {
        Horse horse1 = new Horse("1", 1, 1);
        Horse horse2 = new Horse("2", 1, 1);
        Horse horse3 = new Horse("3", 1, 3);
        Horse horse4 = new Horse("4", 1, 2);

        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3, horse4));

        Horse winner = hippodrome.getWinner();

        assertEquals(horse3, winner);
    }
}
