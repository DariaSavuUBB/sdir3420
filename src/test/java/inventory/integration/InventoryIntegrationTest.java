package inventory.integration;

import inventory.model.*;
import inventory.repository.InventoryRepository;
import inventory.service.InventoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InventoryIntegrationTest {

    static String filename = "test_items.txt";
    static InventoryRepository repo;
    static InventoryService service;

    @BeforeAll
    static void setup() throws IOException {
        // Scriem fișier test cu 0 elemente
        Files.write(new File(filename).toPath(), List.of());
        Inventory inventory=new Inventory();
        repo = new InventoryRepository(inventory,filename);
        service = new InventoryService(filename);
    }

    @Test
    @Order(1)
    void testAddInhousePartAndRetrieve() throws Exception {
        service.addInhousePart("Wheel", 25.0, 10, 1, 20, 123);

        ObservableList<Part> parts = service.getAllParts();
        assertEquals(1, parts.size());
        assertEquals("Wheel", parts.get(0).getName());
    }

    @Test
    @Order(2)
    void testAddProductWithPart() {
        ObservableList<Part> associatedParts = FXCollections.observableArrayList(service.getAllParts().get(0));
        service.addProduct("Bicycle", 100.0, 5, 1, 10, associatedParts);

        ObservableList<Product> products = service.getAllProducts();
        assertEquals(1, products.size());
        assertEquals("Bicycle", products.get(0).getName());
        assertEquals(1, products.get(0).getAssociatedParts().size());
    }

    @AfterAll
    static void cleanup() throws IOException {
        Files.deleteIfExists(new File(filename).toPath());
    }
}
