package inventory.service;

import inventory.model.InhousePart;
import inventory.model.OutsourcedPart;
import inventory.model.Part;
import inventory.model.Product;
import inventory.repository.InventoryRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class InventoryServiceTest {

    private InventoryRepository mockRepo;
    private InventoryService service;

    @BeforeEach
    void setup() {
        mockRepo = mock(InventoryRepository.class);
        service = new InventoryService(mockRepo);
    }

    @Test
    void testAddOutsourcePartCallsRepo() {
        service.addOutsourcePart("Screw", 0.5, 100, 10, 500, "Acme Corp");

        verify(mockRepo, times(1)).addPart(any(OutsourcedPart.class));
    }

    @Test
    void testAddProductStoresCorrectly() {
        ObservableList<Part> parts = FXCollections.observableArrayList(
                new InhousePart(1, "Wheel", 12.0, 10, 2, 20, 55)
        );

        service.addProduct("Bike", 99.99, 5, 1, 10, parts);
        verify(mockRepo).addProduct(any(Product.class));
    }

    @Test
    void testIsPartAssociatedWithAnyProductTrue() {
        Part part = new InhousePart(1, "Bolt", 0.2, 500, 100, 1000, 111);

        Product product = new Product(1, "Machine", 150.0, 2, 1, 10, FXCollections.observableArrayList(part));
        when(mockRepo.getAllProducts()).thenReturn(FXCollections.observableArrayList(product));

        assertTrue(service.isPartAssociatedWithAnyProduct(part));
    }

    @Test
    void testIsPartAssociatedWithAnyProductFalse() {
        Part part = new InhousePart(1, "Spring", 1.0, 100, 5, 200, 222);
        when(mockRepo.getAllProducts()).thenReturn(FXCollections.observableArrayList());

        assertFalse(service.isPartAssociatedWithAnyProduct(part));
    }
}
