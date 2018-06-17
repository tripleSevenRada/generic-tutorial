package cz.etn.etnshop.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import cz.etn.etnshop.controllers.utils.RequestParseResult;

//TODO prozkoumat tento mechanismus. Mam pouzivat explicitne throws HibernateExceptions?

//https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Repository.html
//One advantage of using this annotation is that it has
//automatic persistence exception translation enabled. When using a persistence framework
//such as Hibernate, native exceptions thrown within classes annotated with
//@Repository will be automatically translated into subclasses of Spring’s DataAccessExeption.

@Repository("productDao")
public class ProductDaoImpl extends AbstractDao implements ProductDao {

	public static final String LOG_TAG = "--------------------- ProductDaoImpl: ";
	//https://docs.jboss.org/hibernate/core/3.2/api/org/hibernate/Session.html

	private Session getHibernateSession() throws HibernateException {
		Session session = getSession();
		if(session == null) throw new HibernateException("session = null");//?
		return session;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getProducts() throws HibernateException {
		// Declarative transactions separates transaction management code from the business logic.
		// Spring supports declarative transactions using transaction advice (using AOP)
		// via XML configuration in the spring context or with @Transactional annotation.
		
		Criteria criteria = getSession().createCriteria(Product.class);
		return (List<Product>) criteria.list();
	}

	/*
	 * Returns the persistent instance of the given entity class with the given identifier,
	 * or null if there is no such persistent instance.
	 */
	@Override
	public Product getProductById(int id) throws HibernateException{
		Session session = getHibernateSession();
		return (Product)session.get(Product.class, id);
	}

	@Override
	public void addProduct(Product p) throws HibernateException{
		Session session = getHibernateSession();
		if(p != null) {
			session.persist(p);
		} else {
			System.err.println(LOG_TAG + "Product = null --- addProduct");
		}
	}
	
	
	//nekonsistentni signatury metod, vim to, tutorial...
	
	
	@Override
	public void updateProduct(Product p, RequestParseResult rpr) throws HibernateException{
	Session session = getHibernateSession();
		if(p != null && rpr != null) {
			p.setName(rpr.getName());
			p.setSerial1(rpr.getSerial1());
			p.setSerial2(rpr.getSerial2());
			session.update(p);
		} else {
			System.err.println(LOG_TAG + "Product = null or RequestParseResult = null --- updateProduct");
		}
	}
	
	@Override
	public void updateProduct(int id, RequestParseResult rpr) throws HibernateException{
		Session session = getHibernateSession();
		Product p = (Product) session.get(Product.class,id);
		if(p != null && rpr != null){
			p.setName(rpr.getName());
			p.setSerial1(rpr.getSerial1());
			p.setSerial2(rpr.getSerial2());
			session.update(p);
		}
	}

	@Override
	public void removeProduct(Product p) throws HibernateException {
		Session session = getHibernateSession();
		if(p != null) {
			session.delete(p);
		} else {
			System.out.println(LOG_TAG + "non-existing product query --- removeProduct");
		}
	}
}
