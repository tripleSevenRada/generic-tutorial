package cz.etn.etnshop.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface ProductDao {

	@Transactional(readOnly = true)
	List<Product> getProducts();
	
	@Transactional(readOnly = true)
	Product getProductById(int id);

	@Transactional
	void addProduct(Product product);
	
	@Transactional
	void deleteProduct(int productId);
	
}
