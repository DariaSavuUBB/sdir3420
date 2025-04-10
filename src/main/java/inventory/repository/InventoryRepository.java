package inventory.repository;

import inventory.model.*;
import inventory.service.InventoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Files;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import java.util.logging.Level;

public class InventoryRepository {

	public Logger logger = Logger.getLogger(getClass().getName());
	public String filename = "data/items.txt";
	public Inventory inventory;
	public InventoryRepository(Inventory inventory,String filename)
	{
		this.filename=filename;
		this.inventory=inventory;
	}
	public InventoryRepository(Inventory inventory){
		this.inventory=inventory;
		try {
			readParts();
			readProducts();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void readParts() throws IOException{
		File file = new File(filename);
		ObservableList<Part> listP = FXCollections.observableArrayList();
		try (BufferedReader br = Files.newBufferedReader(file.toPath())) {
			String line = null;
			while((line=br.readLine())!=null){
				Part part=getPartFromString(line);
				if (part!=null)
					listP.add(part);
			}
        }
		inventory.setAllParts(listP);
	}

	public Part getPartFromString(String line){
		Part item=null;
		if (line==null|| line.equals("")) return null;
		StringTokenizer st=new StringTokenizer(line, ",");
		String type=st.nextToken();
		if (type.equals("I")) {
			int id= Integer.parseInt(st.nextToken());
			inventory.setAutoPartId(id);
			String name= st.nextToken();
			double price = Double.parseDouble(st.nextToken());
			int inStock = Integer.parseInt(st.nextToken());
			int minStock = Integer.parseInt(st.nextToken());
			int maxStock = Integer.parseInt(st.nextToken());
			int idMachine= Integer.parseInt(st.nextToken());
			item = new InhousePart(id, name, price, inStock, minStock, maxStock, idMachine);
		}
		if (type.equals("O")) {
			int id= Integer.parseInt(st.nextToken());
			inventory.setAutoPartId(id);
			String name= st.nextToken();
			double price = Double.parseDouble(st.nextToken());
			int inStock = Integer.parseInt(st.nextToken());
			int minStock = Integer.parseInt(st.nextToken());
			int maxStock = Integer.parseInt(st.nextToken());
			String company=st.nextToken();
			item = new OutsourcedPart(id, name, price, inStock, minStock, maxStock, company);
		}
		return item;
	}

	public void readProducts() throws IOException{
		File file = new File(filename);

		ObservableList<Product> listP = FXCollections.observableArrayList();
		try (BufferedReader br = Files.newBufferedReader(file.toPath())) {
			String line = null;
			while ((line = br.readLine()) != null) {
				Product product = getProductFromString(line);
				if (product != null)
					listP.add(product);
			}
		}
        inventory.setProducts(listP);
	}

	public Product getProductFromString(String line){
		Product product=null;
		if (line==null|| line.equals("")) return null;
		StringTokenizer st=new StringTokenizer(line, ",");
		String type=st.nextToken();
		if (type.equals("P")) {
			int id= Integer.parseInt(st.nextToken());
			inventory.setAutoProductId(id);
			String name= st.nextToken();
			double price = Double.parseDouble(st.nextToken());
			int inStock = Integer.parseInt(st.nextToken());
			int minStock = Integer.parseInt(st.nextToken());
			int maxStock = Integer.parseInt(st.nextToken());
			String partIDs=st.nextToken();

			StringTokenizer ids= new StringTokenizer(partIDs,":");
			ObservableList<Part> list= FXCollections.observableArrayList();
			while (ids.hasMoreTokens()) {
				String idP = ids.nextToken();
				Part part = inventory.lookupPart(idP);
				if (part != null)
					list.add(part);
			}
			product = new Product(id, name, price, inStock, minStock, maxStock, list);
			product.setAssociatedParts(list);
		}
		return product;
	}

	public void writeAll() throws IOException {
		File file = new File(filename);

		ObservableList<Part> parts=inventory.getAllParts();
		ObservableList<Product> products=inventory.getProducts();

		try (BufferedWriter bw = Files.newBufferedWriter(file.toPath())) {
			for (Part p:parts) {
				logger.log(Level.INFO,p::toString);
				bw.write(p.toString());
				bw.newLine();
			}

			for (Product pr:products) {
				ObservableList<Part> list= pr.getAssociatedParts();
				int index=0;
				StringBuilder bld = new StringBuilder();
				while(index<list.size()-1){
					bld.append(list.get(index).getPartId()).append(":");
					index++;
				}
				if (index==list.size()-1)
					bld.append(list.get(index).getPartId());
				bw.write(bld.toString());
				bw.newLine();
			}
		}
	}

	public void addPart(Part part){
		try {
			inventory.addPart(part);
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		try {
			writeAll();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void addProduct(Product product){
		try {
			inventory.addProduct(product);
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		try {
			writeAll();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public int getAutoPartId(){
		return inventory.getAutoPartId();
	}

	public int getAutoProductId(){
		return inventory.getAutoProductId();
	}

	public ObservableList<Part> getAllParts(){
		return inventory.getAllParts();
	}

	public ObservableList<Product> getAllProducts(){
		return inventory.getProducts();
	}

	public Part lookupPart (String search){
		return inventory.lookupPart(search);
	}

	public Product lookupProduct (String search){
		return inventory.lookupProduct(search);
	}

	public void updatePart(int partIndex, Part part){
		inventory.updatePart(partIndex, part);
		try {
			writeAll();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void updateProduct(int productIndex, Product product){
		inventory.updateProduct(productIndex, product);
		try {
			writeAll();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void deletePart(Part part){
		inventory.deletePart(part);
		try {
			writeAll();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public void deleteProduct(Product product){
		inventory.removeProduct(product);
		try {
			writeAll();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public Inventory getInventory(){
		return inventory;
	}

	public void setInventory(Inventory inventory){
		this.inventory=inventory;
	}
}
