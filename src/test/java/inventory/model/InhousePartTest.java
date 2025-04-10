package inventory.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InhousePartTest {

    @Test
    void testConstructorAndGetters() {
        InhousePart part = new InhousePart(1, "Gear", 10.5, 100, 10, 200, 1234);

        assertEquals(1, part.getPartId());
        assertEquals("Gear", part.getName());
        assertEquals(10.5, part.getPrice());
        assertEquals(100, part.getInStock());
        assertEquals(10, part.getMin());
        assertEquals(200, part.getMax());
        assertEquals(1234, part.getMachineId());
    }

    @Test
    void testSetters() {
        InhousePart part = new InhousePart(0, "", 0.0, 0, 0, 0, 0);

        part.setName("Bolt");
        part.setPrice(5.5);
        part.setInStock(50);

        assertEquals("Bolt", part.getName());
        assertEquals(5.5, part.getPrice());
        assertEquals(50, part.getInStock());
    }
}
