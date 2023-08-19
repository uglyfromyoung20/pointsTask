import org.example.Log;
import org.example.MainFrame;
import org.example.PairPoints;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculateDistanceThreeTest {
    MainFrame mainFrame = new MainFrame();
    private PairPoints pairPoint = new PairPoints();
    private Log log = new Log();

    @Test
    public void testCalculateDistanceTwo() {
        pairPoint.setFirstX(4);
        pairPoint.setFirstY(1);
        pairPoint.setSecondX(9);
        pairPoint.setSecondY(5);
        pairPoint.setFirstZ(8);
        pairPoint.setSecondZ(2);

        double expectedDistance = 8.77496438739212;
        pairPoint.setDistance(expectedDistance);
        log.setMethod("method for calculating in two dimensions");

       mainFrame.calculateDistanceThree(pairPoint);

        assertEquals(expectedDistance, pairPoint.getDistance(), 0.0001);
        assertEquals("method for calculating in two dimensions", log.getMethod());
    }
}

