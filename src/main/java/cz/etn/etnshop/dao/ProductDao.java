package cz.etn.etnshop.dao;

import java.util.List;

public interface ProductDao {

	List<Product> getProducts();
	
	Product getProductById(int id);

	void addProduct(Product product);
	
	void deleteProduct(int productId);
	
}
