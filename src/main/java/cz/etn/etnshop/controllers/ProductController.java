package cz.etn.etnshop.controllers;

import java.util.List;
import java.util.OptionalInt;
import java.util.function.IntSupplier;

import javax.validation.Valid;

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
	@SuppressWarnings("unused")
	@Autowired
	private ProductValidator productValidator;

	@RequestMapping("/list")
	public ModelAndView list() {
		return getProductListModelAndView();
	}
	
	// vim o tom, ze jsem preskocil service layer v tomto tutorialovem priklade a mam
	// service pouze pro "serving"
	
	@RequestMapping(value = "/add_product", method = RequestMethod.POST)
	public ModelAndView add(
			@Valid
			@ModelAttribute ("intoFormProduct") Product outOfFormProduct,
			BindingResult theBindingResult
			) {
		if(theBindingResult.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView("product/add-product");
			return modelAndView;
		}
		productDao.addProduct(outOfFormProduct);
		return getProductListModelAndView();
	}

	@RequestMapping("/delete_product")
	public ModelAndView delete(@RequestParam("productId") int id) {
		productDao.deleteProduct(id);
		return getProductListModelAndView();
	}
	
	@RequestMapping("/add_form")
	public ModelAndView addForm() {
		ModelAndView modelAndView = new ModelAndView("product/add-product");
		modelAndView.addObject("intoFormProduct", new Product());
		return modelAndView;
	}
	
	@RequestMapping("/update_form")
	public ModelAndView update(@RequestParam("productId") int id) {
		ModelAndView modelAndView = new ModelAndView("product/add-product");
		modelAndView.addObject("intoFormProduct", productService.getProductById(id));
		return modelAndView;
	}
	
	@RequestMapping("/stats")
	public ModelAndView stats() {
		ModelAndView modelAndView = new ModelAndView("product/stats");
		List<Product> products = null;
		products = productService.getProducts();
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
		// multiple editors bound to one class: https://stackoverflow.com/questions/39853350/spring-initbinder-register-multiple-custom-editor-string-class
	}

	private ModelAndView getProductListModelAndView() {
		ModelAndView modelAndViewProductList = new ModelAndView("product/list");
		modelAndViewProductList.addObject("products", productService.getProducts());
		return modelAndViewProductList;
	}
}
