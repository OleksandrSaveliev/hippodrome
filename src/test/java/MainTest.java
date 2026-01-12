import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

@Timeout(value = 22, unit = TimeUnit.SECONDS)
@Disabled
public class MainTest {
    @Test
    public void main() throws Exception {
        Main.main(new String[]{});
    }
}
