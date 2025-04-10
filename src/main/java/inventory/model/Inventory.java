
package inventory.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    // Declare fields
    public ObservableList<Product> products;
    public ObservableList<Part> allParts;
    public int autoPartId;
    public int autoProductId;
    public Inventory(){
        this.products = FXCollections.observableArrayList();
        this.allParts= FXCollections.observableArrayList();
        this.autoProductId=0;
        this.autoPartId=0;
    }

    // Declare methods
    /**
     * Add new product to observable list products
     * @param product 
     */
    public void addProduct(Product product) {
        products.add(product);
    }
    
    /**
     * Remove product from observable list products
     * @param product 
     */
    public void removeProduct(Product product) {
        products.remove(product);
    }
    
    /**
     * Accepts search parameter and if an ID or name matches input, that product is returned
     * @param searchItem
     * @return 
     */
    public Product lookupProduct(String searchItem) {
        for(Product p: products) {
            if(p.getName().contains(searchItem) || String.valueOf(p.getProductId()).equals(searchItem)) return p;

        }
       return new Product(0, "", 0.0, 0, 0, 0,FXCollections.observableArrayList());
    }
    
    /**
     * Update product at given index
     * @param index
     * @param product 
     */
    public void updateProduct(int index, Product product) {
        if (index >= 0 && index < products.size()) {
            products.set(index, product);
        } else {
            System.out.println("Eroare: Index invalid pentru updateProduct!");
        }
    }
    /**
     * Getter for Product Observable List
     * @return 
     */
    public ObservableList<Product> getProducts() {
        return products;
    }

    public void setProducts(ObservableList<Product> list) {
        if(list!=null )products=list;
    }
    
    /**
     * Add new part to observable list allParts
     * @param part 
     */
    public void addPart(Part part) {
        allParts.add(part);
    }
    
    /**
     * Removes part passed as parameter from allParts
     * @param part 
     */
    public void deletePart(Part part) {
        allParts.remove(part);
    }
    
    /**
     * Accepts search parameter and if an ID or name matches input, that part is returned
     * @param searchItem
     * @return 
     */
    public Part lookupPart(String searchItem) {
        if(allParts.isEmpty())
            return null;
        for(Part p:allParts) {
            if(p.getName().contains(searchItem) ) return p;
            if(String.valueOf(p.getPartId()).equals(searchItem)) return p;
        }
        return null;
    }
    
    /**
     * Update part at given index
     * @param index
     * @param part 
     */
    public void updatePart(int index, Part part) {
        if (index >= 0 && index < allParts.size()) {
            allParts.set(index, part);
        } else {
            System.out.println("Eroare: Index invalid pentru updatePart!");
        }
    }
    
    /**
     * Getter for allParts Observable List
     * @return 
     */
    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     *
     * @param list
     */
    public void setAllParts(ObservableList<Part> list) {
        if(list!=null )allParts=list;
    }
    
    /**
     * Method for incrementing part ID to be used to automatically
     * assign ID numbers to parts
     * @return 
     */
    public int getAutoPartId() {
        return autoPartId++;
    }
    
    /**
     * Method for incrementing product ID to be used to automatically
     * assign ID numbers to products
     * @return 
     */
    public int getAutoProductId() {
        return autoProductId++;
    }


    public void setAutoPartId(int id){
        autoPartId=id;
    }

    public void setAutoProductId(int id){
        autoProductId=id;
    }
    
}
