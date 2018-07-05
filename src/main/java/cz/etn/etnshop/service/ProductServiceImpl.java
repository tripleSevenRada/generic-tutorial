package cz.etn.etnshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.etn.etnshop.dao.Product;
import cz.etn.etnshop.dao.ProductDao;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	
	//https://softwareengineering.stackexchange.com/questions/220909/service-layer-vs-dao-why-both
	//https://stackoverflow.com/questions/13785634/responsibilities-and-use-of-service-and-dao-layers
	
	//It is a good idea to have those two layers (DAO vs SERVICE) when your business logic is more complex than your data logic.
	//The service layer implements the business logic. In most cases, this layer has to perform more operations than just
	// calling a method from a DAO object.
	//And if you're thinking of making your application bigger, this is probably the best solution.
	
	
	// vim o tom, ze jsem preskocil service layer v tomto tutorialovem priklade a mam service pouze pro "serving"
	
	@Autowired
	private ProductDao productDao;

	@Override
	@Transactional
	public List<Product> getProducts() {
		return productDao.getProducts();
	}

	@Override
	@Transactional
	public Product getProductById(int id) {
		return productDao.getProductById(id);
	}

}
