package cz.etn.etnshop.validators;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cz.etn.etnshop.dao.Product;

@Component
@Scope("singleton") //default anyway
public class RudimentaryProductValidator implements ProductValidator {

	private Validator validator;
	
	public RudimentaryProductValidator() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Override
	public boolean isValid(Product product) {
		Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
		return (constraintViolations.size() == 0);
	}

}
