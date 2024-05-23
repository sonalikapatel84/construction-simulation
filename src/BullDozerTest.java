import models.BullDozer;
import models.Direction;
import models.Position;
import models.ReportManager;
import models.Site;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BullDozerTest {
    private BullDozer bulldozer;
    private Site site;
    private ReportManager reportManager;

    @BeforeEach
    public void setUp() throws IOException {
        bulldozer = new BullDozer(new Position(0, 0), Direction.EAST);
        site = new Site("src/test/site-map.txt");
        reportManager = new ReportManager();
    }

    @Test
    public void testAdvance() {
        bulldozer.advance(3, site, reportManager);
        Position expectedPosition = new Position(0, 3);
        assertEquals(expectedPosition.getX(), bulldozer.getCurrentPosition().getX());
        assertEquals(expectedPosition.getY(), bulldozer.getCurrentPosition().getY());
    }

    @Test
    public void testTurnAndAdvance() {
        bulldozer.turnRight();
        bulldozer.advance(2, site, reportManager);
        Position expectedPosition = new Position(2, 0);
        assertEquals(expectedPosition.getX(), bulldozer.getCurrentPosition().getX());
        assertEquals(expectedPosition.getY(), bulldozer.getCurrentPosition().getY());
    }
}