package cz.etn.etnshop.validators;

import cz.etn.etnshop.dao.Product;

// http://www.beanvalidation.org
// http://www.hibernate.org/validator

public interface ProductValidator {

	boolean isValid(Product product);
	
}
