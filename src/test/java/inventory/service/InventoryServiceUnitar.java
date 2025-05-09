package inventory.service;

import inventory.model.InhousePart;
import inventory.model.Inventory;
import inventory.repository.InventoryRepository;
import inventory.service.InventoryService;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryServiceUnitar {

    private InventoryService service;
    private Inventory inventory;
    private InventoryRepository repo;

    @BeforeEach
    public void setup() throws IOException {
        inventory = new Inventory();
        File tempFile = File.createTempFile("test_items", ".txt");
        repo = new InventoryRepository(inventory, tempFile.getAbsolutePath());
        service = new InventoryService(repo);
    }

    @Test
    public void testAddValidInhousePart() throws Exception {
        service.addInhousePart("Motor", 99.99, 5, 1, 10, 123);
        ObservableList<InhousePart> parts = (ObservableList<InhousePart>) (ObservableList<?>) service.getAllParts();
        assertEquals(1, parts.size());
        assertEquals("Motor", parts.get(0).getName());
    }

    @Test
    public void testAddInvalidInhousePartThrowsException() {
        Exception e = assertThrows(Exception.class, () ->
                service.addInhousePart("Part", -10.0, 3, 2, 5, 123));
        assertEquals("Prețul trebuie să fie mai mare decât 0.\n", e.getMessage());
    }
}
