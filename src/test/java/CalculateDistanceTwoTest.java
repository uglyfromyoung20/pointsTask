import org.example.Log;
import org.example.MainFrame;
import org.example.PairPoints;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculateDistanceTwoTest {
    MainFrame mainFrame = new MainFrame();
    private PairPoints pairPoint = new PairPoints();
    private Log log = new Log();

    @Test
    public void testCalculateDistanceTwo() {
        pairPoint.setFirstX(4);
        pairPoint.setFirstY(4);
        pairPoint.setSecondX(3);
        pairPoint.setSecondY(2);

        double expectedDistance = 2.23606797749979;
        pairPoint.setDistance(expectedDistance);
        log.setMethod("method for calculating in two dimensions");

        mainFrame.calculateDistanceTwo(pairPoint);

        assertEquals(expectedDistance, pairPoint.getDistance(), 0.0001);
        assertEquals("method for calculating in two dimensions", log.getMethod());
    }
}
