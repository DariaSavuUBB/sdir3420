package inventory.repository;

import inventory.model.*;
import inventory.repository.InventoryRepository;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryRepositoryUnitar {

    private Inventory inventory;
    private InventoryRepository repo;
    private File tempFile;

    @BeforeEach
    public void setUp() throws IOException {
        inventory = new Inventory();
        tempFile = File.createTempFile("test_items", ".txt");
        repo = new InventoryRepository(inventory, tempFile.getAbsolutePath());
    }

    @Test
    public void testAddPartAndWriteToFile() {
        Part part = new InhousePart(1, "TestPart", 5.0, 10, 1, 20, 100);
        repo.addPart(part);

        ObservableList<Part> parts = repo.getAllParts();
        assertEquals(1, parts.size());
        assertEquals("TestPart", parts.get(0).getName());
    }

    @Test
    public void testGetAutoPartId() {
        int id1 = repo.getAutoPartId();
        int id2 = repo.getAutoPartId();
        assertEquals(id1 + 1, id2);
    }

    @AfterEach
    public void tearDown() {
        tempFile.delete();
    }
}
