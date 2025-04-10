package inventory.service;

import inventory.model.*;
import inventory.repository.InventoryRepository;
import inventory.validation.PartValidator;
import javafx.collections.ObservableList;

import java.util.Objects;

public class InventoryService {

    private static InventoryService instance;
    private final InventoryRepository repo;

    public InventoryService(InventoryRepository inventoryRepo) {
        this.repo = inventoryRepo;
    }

    public InventoryService(String filename)
    {

        this.repo=new InventoryRepository(new Inventory(),filename);
    }

    public void addInhousePart(String name, double price, int inStock, int min, int  max, int partDynamicValue) throws Exception {

            String error=PartValidator.validate(name,price,inStock,min,max);
            if(Objects.equals(error, ""))
            {
                InhousePart inhousePart = new InhousePart(repo.getAutoPartId(), name, price, inStock, min, max, partDynamicValue);
                repo.addPart(inhousePart);
             }
            else throw new Exception(error);
    }
    public void addOutsourcePart(String name, double price, int inStock, int min, int  max, String partDynamicValue){
        OutsourcedPart outsourcedPart = new OutsourcedPart(repo.getAutoPartId(), name, price, inStock, min, max, partDynamicValue);
        repo.addPart(outsourcedPart);
    }

    public void addProduct(String name, double price, int inStock, int min, int  max, ObservableList<Part> addParts){
        Product product = new Product(repo.getAutoProductId(), name, price, inStock, min, max, addParts);
        repo.addProduct(product);
    }

    public ObservableList<Part> getAllParts() {
        return repo.getAllParts();
    }

    public ObservableList<Product> getAllProducts() {
        return repo.getAllProducts();
    }

    public Part lookupPart(String search) {
        return repo.lookupPart(search);
    }

    public Product lookupProduct(String search) {
        return repo.lookupProduct(search);
    }

    public void updateInhousePart(int partIndex, int partId, String name, double price, int inStock, int min, int max, int machineId){
        InhousePart inhousePart = new InhousePart(partId, name, price, inStock, min, max, machineId);
        repo.updatePart(partIndex, inhousePart);
    }

    public void updateOutsourcedPart(int partIndex, int partId, String name, double price, int inStock, int min, int max, String companyName){
        OutsourcedPart outsourcedPart = new OutsourcedPart(partId, name, price, inStock, min, max, companyName);
        repo.updatePart(partIndex, outsourcedPart);
    }

    public void updateProduct(int productIndex, int productId, String name, double price, int inStock, int min, int max, ObservableList<Part> addParts){
        Product product = new Product(productId, name, price, inStock, min, max, addParts);
        repo.updateProduct(productIndex, product);
    }

    public boolean isPartAssociatedWithAnyProduct(Part part) {
        for (Product product : this.getAllProducts()) {
            if (product.getAssociatedParts().contains(part)) {
                return true;
            }
        }
        return false;
    }

    public void deletePart(Part part){
        repo.deletePart(part);
    }

    public void deleteProduct(Product product){
        repo.deleteProduct(product);
    }

}