package cz.etn.etnshop.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cz.etn.etnshop.controllers.utils.RequestParseResult;

public interface ProductDao {

	@Transactional(readOnly = true)
	List<Product> getProducts();
	
	@Transactional(readOnly = true)
	Product getProductById(int id);

	@Transactional
	void addProduct(Product product);
	
	@Transactional
	void updateProduct(Product product, RequestParseResult rpr);
	
	@Transactional
	void updateProduct(int id, RequestParseResult rpr);
	
	@Transactional
	void removeProduct(Product product);
}
