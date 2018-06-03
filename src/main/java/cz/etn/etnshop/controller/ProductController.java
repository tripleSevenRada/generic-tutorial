package cz.etn.etnshop.controller;

import java.util.List;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.IntSupplier;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cz.etn.etnshop.controller.utils.RequestParseResult;
import cz.etn.etnshop.controller.utils.RequestParser;
import cz.etn.etnshop.dao.Product;
import cz.etn.etnshop.dao.ProductDao;
import cz.etn.etnshop.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

	private static final String LOG_TAG = "--------------------- ProductController: ";
	// https://www.tutorialspoint.com/hibernate/hibernate_examples.htm
	// http://www.beanvalidation.org/
	// http://www.hibernate.org/validator
	
	private static Validator validator;

	@Autowired
	private ProductService productService;
	@Autowired
	private ProductDao productDao;

	@RequestMapping("/list")
	public ModelAndView list() {
		return getProductListModelAndView();
	}

	@RequestMapping("/add_product")
	public ModelAndView add(HttpServletRequest request) {
		// http://www.beanvalidation.org
		// http://www.hibernate.org/validator
		RequestParseResult rpr = null;
		try {
			rpr = RequestParser.parseRequest(request);
		} catch (Exception e) {
			e.printStackTrace();
			return getProductListModelAndView();
		}
		if (rpr.getName() == null || rpr.getSerial1() == -1 || rpr.getSerial2() == -1) {
			System.err.println(LOG_TAG + "parse error1: " + rpr.toString());
			return getProductListModelAndView();
		}

		Product p = new Product(rpr.getName(), rpr.getSerial1(), rpr.getSerial2());
		
		//TODO get rid of JS validation
		Set<ConstraintViolation<Product>> constraintViolations = validator.validate(p);
		if(constraintViolations.size() != 0) {
			System.err.println("validation error: /add_product");
			return getProductListModelAndView();
		}
		
		try {
			productDao.addProduct(p);
		} catch (HibernateException he) {
			// TODO
			he.printStackTrace();
		}
		return getProductListModelAndView();
	}

	@RequestMapping("/edit_product")
	public ModelAndView edit(HttpServletRequest request) {
		// http://www.beanvalidation.org
		// http://www.hibernate.org/validator
		RequestParseResult rpr = null;
		try {
			rpr = RequestParser.parseRequest(request);
		} catch (Exception e) {
			e.printStackTrace();
			return getProductListModelAndView();
		}
		if (rpr.getId() == null || rpr.getName() == null || rpr.getSerial1() == -1 || rpr.getSerial2() == -1) {
			System.err.println(LOG_TAG + "parse error2: " + rpr.toString());
			return getProductListModelAndView();
		}

		Product p = null;
		try {
			p = productDao.getProductById(rpr.getId());
		} catch (HibernateException he) {
			// TODO
			he.printStackTrace();
		}
		if (p != null) {
			//TODO get rid of JS validation
			Set<ConstraintViolation<Product>> constraintViolations = validator.validate(p);
			if(constraintViolations.size() != 0) {
				System.err.println("validation error: /edit_product");
				return getProductListModelAndView();
			}
			try {
				productDao.updateProduct(p, rpr);
			} catch (HibernateException he) {
				// TODO
				he.printStackTrace();
			}
		}
		return getProductListModelAndView();
	}

	/*
	 * Tutorial practice, inconsistent with other methods signatures, I know
	 */
	@RequestMapping("/remove_product")
	public ModelAndView remove(@RequestParam("idRemove") String idRequest) {
		// necessary?, safe?, good?
		Integer id = -1;
		try {
			id = Integer.parseInt(idRequest);
		} catch (Exception e) {
			e.printStackTrace();
			return getProductListModelAndView();
		}
		System.out.println(LOG_TAG + "remove, id: " + id);
		try {
			productDao.removeProduct(productDao.getProductById(id));
		} catch (HibernateException he) {
			// TODO
			he.printStackTrace();
		}
		return getProductListModelAndView();
	}

	@RequestMapping("/stats")
	public ModelAndView stats() {
		ModelAndView modelAndView = new ModelAndView("product/stats");
		List<Product> products = null;
		try {
			products = productService.getProducts();
		} catch (HibernateException he) {
			// TODO
			he.printStackTrace();
		}
		modelAndView.addObject("products_stats_size", products.size());

		// crazy... practice purposes only
		OptionalInt maxNameLength = products.stream().mapToInt(IntSupplier::getAsInt).max();
		OptionalInt minNameLength = products.stream().mapToInt(IntSupplier::getAsInt).min();

		if (maxNameLength.isPresent()) {
			modelAndView.addObject("products_stats_name_max_length", maxNameLength.getAsInt());
		} else {
			System.err.println(LOG_TAG + "maxDescriptionLength NOT PRESENT");
			modelAndView.addObject("products_stats_name_max_length", 0);
		}

		if (minNameLength.isPresent()) {
			modelAndView.addObject("products_stats_name_min_length", minNameLength.getAsInt());
		} else {
			System.err.println(LOG_TAG + "minDescriptionLength NOT PRESENT");
			modelAndView.addObject("products_stats_name_min_length", 0);
		}
		//

		return modelAndView;
	}

	// corner case: http://localhost:8080/etnshop/product/list//product/list
	@RequestMapping(value = { "*", "*/*", "/*", "*/" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String fallbackJSPPage() {
		return "fallback";
	}

	private ModelAndView getProductListModelAndView() {
		ModelAndView modelAndViewProductList = new ModelAndView("product/list");
		try {
			modelAndViewProductList.addObject("products", productService.getProducts());
		} catch (HibernateException he) {
			// TODO
			he.printStackTrace();
		}
		return modelAndViewProductList;
	}

}
