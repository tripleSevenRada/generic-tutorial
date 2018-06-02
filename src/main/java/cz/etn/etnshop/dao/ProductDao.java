package cz.etn.etnshop.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cz.etn.etnshop.controller.utils.RequestParseResult;

public interface ProductDao {

	@Transactional(readOnly = true)
	List<Product> getProducts();
	
	@Transactional(readOnly = true)
	Product getProductById(Integer id);

	@Transactional
	void addProduct(Product product);
	
	@Transactional
	void updateProduct(Product product, RequestParseResult rpr);
	
	@Transactional
	void removeProduct(Product product);
}
