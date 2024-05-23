import models.ReportManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReportManagerTest {
    private ReportManager reportManager;

    @BeforeEach
    public void setUp() {
        reportManager = new ReportManager();
    }

    @Test
    public void testAddFuelUsage() {
        reportManager.addFuelUsage(5);
        assertEquals(5, reportManager.getFuelUsage());
    }

    @Test
    public void testIncrementPaintDamage() {
        reportManager.incrementPaintDamage();
        assertEquals(2, reportManager.getPaintDamageCount());
    }

    @Test
    public void testIncrementProtectedTreeDestruction() {
        reportManager.incrementProtectedTreeDestruction();
        assertEquals(10, reportManager.getProtectedTreeDestructionCount());
    }
}