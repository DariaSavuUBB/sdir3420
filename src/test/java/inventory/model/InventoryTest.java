package inventory.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
        ObservableList<Part> allParts = FXCollections.observableArrayList(
                new InhousePart(1, "Cog", 3.75, 14, 4, 40, 12),
                new OutsourcedPart(2, "Spring", 2.3, 7, 5, 20, "C1"),
                new OutsourcedPart(3, "Screw", 0.11, 280, 100, 1000, "CompanyLtd"),
                new OutsourcedPart(4, "part1", 1.34, 15, 2, 15, "me"),
                new InhousePart(5, "part2", 1.23, 11, 1, 11, 1),
                new OutsourcedPart(6, "p3", 1.0, 11, 1, 13, "ebgef"),
                new InhousePart(7, "p111", 1.23, 11, 2, 12, 23),
                new OutsourcedPart(8, "part10", 1.51, 5, 2, 19, "none"),
                new InhousePart(10, "part11", 1.23, 1, 1, 9, 123),
                new InhousePart(11, "part12", 1.89, 3, 1, 8, 124),
                new OutsourcedPart(12, "Part100", 2.34, 10, 4, 10, "NewCompany"),
                new OutsourcedPart(13, "Part200", 10.4, 10, 10, 12, "MyCompany")
        );
        inventory.setAllParts(allParts);
    }

    @Test
    void testLookupPartEmptyList_ReturnsNull() {
        inventory.setAllParts(FXCollections.observableArrayList());
        Part result = inventory.lookupPart("Cog");
        assertNull(result);
    }
    @Test
    void testLookupPartByName_NotFound_ReturnsNull() {
        Part result = inventory.lookupPart("abc");
        assertNull(result);
    }

    @Test
    void testLookupPartByName_Found_ReturnsCorrectPart() {
        Part result = inventory.lookupPart("Cog");
        assertNotNull(result);
        assertEquals("Cog", result.getName());
    }

    @Test
    void testLookupPartByIdAsString_Found_ReturnsCorrectPart() {
        Part result = inventory.lookupPart("1");
        assertNotNull(result);
        assertEquals(1, result.getPartId());
    }

    @Test
    void testLookupPartByName_LastItemInList_ReturnsCorrectMatch() {
        Part result = inventory.lookupPart("Part200");
        assertNotNull(result);
        assertEquals("Part200", result.getName());
    }
}
