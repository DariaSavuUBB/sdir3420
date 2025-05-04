module inventory {
    // JavaFX
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;

    // Logging
    requires java.logging;

    // === Pachete exportate ===
    exports inventory;
    exports inventory.controller;
    exports inventory.model;
    exports inventory.service;

    // === Pachete deschise pentru JavaFX (reflectie) ===
    opens inventory to javafx.fxml;
    opens inventory.controller to javafx.fxml;

    // === Pachete deschise pentru frameworkuri de testare (JUnit, Mockito etc.) ===
    opens inventory.model;
    opens inventory.repository;
}
