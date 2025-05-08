package inventory.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class PartServiceTest {
    private InventoryService service;

    @BeforeAll
    static void setupAll() {
        System.out.println("Start test suite");
    }

    @BeforeEach
    void setup() {
        service = new InventoryService("test.txt"); // Se face un service separat pentru testare
    }

//    @ParameterizedTest
//    @CsvSource({
//            "Part 1, 5, 10, 5, 20, 10",
//            "Part 2, 5, 15, 5, 20, 12",
//            "Part 3, 5, 17, 5, 20, 25"
//    })
//    @DisplayName("Should add part successfully when data is valid")
//    @Tag("ValidCasePrice")
//    void addValidPartStock(String name, double price, int inStock, int min, int max, int partDynamicValue) {
//        int length=service.getAllParts().size();
//        assertDoesNotThrow(() -> service.addInhousePart(name, price, inStock, min, max, partDynamicValue));
//        assertTrue(length+1==service.getAllParts().size());
//    }

    @Test
    void addValidPartStock() {
        assertDoesNotThrow(() -> service.addInhousePart("Part 1", 5.0, 10, 5, 20, 10));
    }


    @ParameterizedTest
    @CsvSource({
            "Part 1, 5.25, 10, 1, 20, 10",
            "Part 2, 7.5, 10, 1, 20, 12",
            "Part 3, 2.15, 10, 1, 20, 25",
            "Part 4, 3.35, 10, 1, 20, 25"
    })
    @DisplayName("Should add part successfully when data is valid")
    @Tag("ValidCasePrice")
    void addValidPartPrice(String name, double price, int inStock, int min, int max, int partDynamicValue) {
        assertDoesNotThrow(() -> service.addInhousePart(name, price, inStock, min, max, partDynamicValue));
    }

//    @ParameterizedTest
//    @ValueSource(doubles = {-5, -10})
//    @DisplayName("Should throw exception when price is negative")
//    @Tag("InvalidCaseNegativePrice")
//    void addPartWithNegativePrice(double invalidPrice) {
//        Exception exception = assertThrows(Exception.class, () ->
//                service.addInhousePart("Part 1", invalidPrice, 10, 1, 20, 10));
//        assertEquals("Prețul trebuie să fie mai mare decât 0.\n", exception.getMessage());
//    }
    @Test
//    @DisplayName("Should throw exception when price is -5")
//    @Tag("InvalidCaseNegativePrice")
    void addPartWithNegativePrice() {
        double invalidPrice = -5;
        Exception exception = assertThrows(Exception.class, () ->
                service.addInhousePart("Part 1", invalidPrice, 10, 1, 20, 10));
        assertEquals("Prețul trebuie să fie mai mare decât 0.\n", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when min is greater than max")
    @Tag("InvalidCaseMinMax")
    void addPartWithMinGreaterThanMax() {
        Exception exception = assertThrows(Exception.class, () ->
                service.addInhousePart("Part 1", 5, 10, 15, 4, 10));
        assertTrue(exception.getMessage().contains("Minimul nu poate fi mai mare decât maximul."));
    }

    @Test
//    @DisplayName("Should add part when price is at boundary (0.1)")
//    @Tag("BVACasePrice")
    void addPartWithBoundaryPrice() {
        assertDoesNotThrow(() -> service.addInhousePart("Part 1", 0.01, 10, 4, 10, 10));
    }

    @Test
//    @DisplayName("Should throw exception when price is zero")
//    @Tag("InvalidCaseBVAPrice")
    void addPartWithZeroPrice() {
        Exception exception = assertThrows(Exception.class, () ->
                service.addInhousePart("Part 1", 0, 10, 4, 10, 10));
        assertTrue(exception.getMessage().contains("Prețul trebuie să fie mai mare decât 0."));
    }

    @Test
    @DisplayName("Should add part when min equals max")
    @Tag("BVAMinMax")
    void addPartWithMinEqualToMax()  {
        assertDoesNotThrow(() -> service.addInhousePart("Part 1", 10, 10, 10, 10, 10));
    }

    @Test
    @DisplayName("Should throw exception when min is greater than max (Boundary)")
    @Tag("InvalidCaseBVAMinMax")
    void addPartWithMinGreaterThanMaxBoundary() {
        Exception exception = assertThrows(Exception.class, () ->
                service.addInhousePart("Part 1", 11, 10, 11, 10, 10));
        assertTrue(exception.getMessage().contains("Minimul nu poate fi mai mare decât maximul."));
    }

    @Test
    @DisplayName("Disabled test example")
    @Disabled("Pending implementation")
    void disabledTest() {
        fail("This test should not run");
    }

    @AfterEach
    void teardown() {
        System.out.println("Test case executed");
    }

    @AfterAll
    static void teardownAll() {
        System.out.println("End test suite");
    }
}
