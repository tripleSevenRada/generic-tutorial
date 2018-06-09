package cz.etn.etnshop.controllers;

import java.util.List;
import java.util.OptionalInt;
import java.util.function.IntSupplier;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import cz.etn.etnshop.controllers.utils.RequestParseResult;
import cz.etn.etnshop.controllers.utils.RequestParser;
import cz.etn.etnshop.custom_editors.BaseCustomEditor;
import cz.etn.etnshop.custom_editors.DirtyWordsEditor;
import cz.etn.etnshop.dao.Product;
import cz.etn.etnshop.dao.ProductDao;
import cz.etn.etnshop.service.ProductService;
import cz.etn.etnshop.validators.ProductValidator;

@Controller
@RequestMapping("/product")
public class ProductController {

	private static final String LOG_TAG = "--------------------- ProductController: ";
	
	// https://www.tutorialspoint.com/hibernate/hibernate_examples.htm
	// http://www.beanvalidation.org/
	// http://www.hibernate.org/validator
	
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductValidator productValidator;

	@RequestMapping("/list")
	public ModelAndView list() {
		return getProductListModelAndViewFresh();
	}

	//nesourode signatury metod, vim, chci si vyzkouset vic veci...
	
	@RequestMapping("/add_product")
	public ModelAndView add(
			@Valid
			@ModelAttribute ("intoFormProduct") Product outOfFormProduct,
			BindingResult theBindingResult
			) {
		if(theBindingResult.hasErrors()) {
			return getProductListModelAndViewStale();
		}
		
		try {
			productDao.addProduct(outOfFormProduct);
		} catch (HibernateException he) {
			// TODO
			he.printStackTrace();
		}
		return getProductListModelAndViewFresh();
	}

	@RequestMapping("/edit_product")
	public ModelAndView edit(HttpServletRequest request) {
		RequestParseResult rpr = null;
		try {
			rpr = RequestParser.parseRequest(request);
		} catch (Exception e) {
			e.printStackTrace();
			return getProductListModelAndViewFresh();
		}
		if (rpr.getId() == null || rpr.getName() == null || rpr.getSerial1() == -1 || rpr.getSerial2() == -1) {
			System.err.println(LOG_TAG + "parse error2: " + rpr.toString());
			return getProductListModelAndViewFresh();
		}

		Product p = null;
		try {
			p = productDao.getProductById(rpr.getId());
		} catch (HibernateException he) {
			// TODO
			he.printStackTrace();
		}
		if (p != null) {
			//TODO get rid of explicit validation
			if(! productValidator.isValid(p)) {
				System.err.println(LOG_TAG + "validation error: /edit_product");
				System.err.println(LOG_TAG + p.toString());
				return getProductListModelAndViewFresh();
			}
			try {
				productDao.updateProduct(p, rpr);
			} catch (HibernateException he) {
				// TODO
				he.printStackTrace();
			}
		} else {
			System.err.println(LOG_TAG + "/edit_product: query retrieved null");
		}
		return getProductListModelAndViewFresh();
	}

	/*
	 *
	 */
	@RequestMapping("/remove_product")
	public ModelAndView remove(@RequestParam("idRemove") String idRequest) {
		// TODO prozkoumat
		// muzu mit @RequestParam("idRemove") int idRequest
		// redundantni parseInt, ale jak to funguje behind the scenes?
		
		// TODO https://www.udemy.com/spring-hibernate-tutorial/learn/v4/t/lecture/6846298?start=0
		
		int id = -1;
		try {
			id = Integer.parseInt(idRequest);
		} catch (Exception e) {
			System.err.println(LOG_TAG + "failed:>" + idRequest + "<");
			return getProductListModelAndViewFresh();
		}
		System.out.println(LOG_TAG + "remove, id: " + id);
		try {
			productDao.removeProduct(productDao.getProductById(id));
		} catch (HibernateException he) {
			// TODO
			he.printStackTrace();
		}
		return getProductListModelAndViewFresh();
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

	// edge case: http://localhost:8080/etnshop/product/list//product/list
	@RequestMapping(value = { "*", "*/*", "/*", "*/" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String fallbackJSPPage() {
		return "fallback";
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder){
		// https://github.com/spring-projects/spring-framework/blob/master/spring-beans/src/main/java/org/springframework/beans/propertyeditors/StringTrimmerEditor.java
		// var editor = new StringTrimmerEditor(true); // true - trim whitespace only Strings to NULL

		// TODO is this too heavy?
		var context = new AnnotationConfigApplicationContext(BaseCustomEditor.class);
		var dirty = context.getBean("dirtyWordsEditor", DirtyWordsEditor.class);
		dataBinder.registerCustomEditor(String.class, dirty);
		context.close();
		
		// custom editor impl: https://howtodoinjava.com/spring/spring-boot/custom-property-editor-example/
		// !
		// multiple editors bound to one class: https://stackoverflow.com/questions/39853350/spring-initbinder-register-multiple-custom-editor-string-class
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
	
	private ModelAndView getProductListModelAndViewFresh() {
		ModelAndView maw = getProductListModelAndView();
		maw.addObject("intoFormProduct", new Product());
		return maw;
	}
	//TODO jak tohle funguje behind the scenes...  vs (maw.addObject("intoFormProduct", new Product());)
	private ModelAndView getProductListModelAndViewStale() {
		ModelAndView maw = getProductListModelAndView();
		return maw;
	}

}
