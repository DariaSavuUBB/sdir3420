package inventory.repository;

import inventory.model.InhousePart;
import inventory.model.Inventory;
import inventory.model.Part;
import inventory.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class InventoryRepositoryTest {

    private InventoryRepository repo;
    private Inventory mockInventory;

    @BeforeEach
    void setup() {
        mockInventory = mock(Inventory.class);
        ObservableList<Part> mockPartsList = FXCollections.observableArrayList();
        ObservableList<Product> mockProductList = FXCollections.observableArrayList();
        when(mockInventory.getAllParts()).thenReturn(mockPartsList);
        when(mockInventory.getProducts()).thenReturn(mockProductList);
        repo = new InventoryRepository(mockInventory,"test_items.txt");
    }

    @Test
    void testAddPartCallsInventory() {
        Part part = new InhousePart(1, "Axle", 25.0, 20, 5, 100, 456);

        repo.addPart(part);
        verify(mockInventory).addPart(part);
    }

    @Test
    void testGetAutoPartIdDelegatesToInventory() {
        when(mockInventory.getAutoPartId()).thenReturn(123);
        assertEquals(123, repo.getAutoPartId());
    }

    @Test
    void testLookupPartDelegates() {
        Part mockPart = new InhousePart(2, "Wheel", 50, 30, 5, 100, 789);
        when(mockInventory.lookupPart("2")).thenReturn(mockPart);

        Part result = repo.lookupPart("2");
        assertEquals("Wheel", result.getName());
    }
}
