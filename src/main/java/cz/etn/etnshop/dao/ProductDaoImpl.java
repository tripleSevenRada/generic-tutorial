package cz.etn.etnshop.dao;

import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//TODO prozkoumat tento mechanismus. Mam pouzivat explicitne throws HibernateExceptions?

//https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Repository.html
//One advantage of using this annotation is that it has
//automatic persistence exception translation enabled. When using a persistence framework
//such as Hibernate, native exceptions thrown within classes annotated with
//@Repository will be automatically translated into subclasses of Spring’s DataAccessExeption./unchecked/

@Repository("productDao")
public class ProductDaoImpl extends AbstractDao implements ProductDao {

	public static final String LOG_TAG = "--------------------- ProductDaoImpl: ";
	//https://docs.jboss.org/hibernate/core/3.2/api/org/hibernate/Session.html

	private Session getHibernateSession(){
		Session session = getSession();
		return session;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Product> getProducts(){
		
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
	@Transactional
	public Product getProductById(int productId){
		Session session = getHibernateSession();
		return (Product)session.get(Product.class, productId);
	}

	@Override
	@Transactional
	public void addProduct(Product product){
		Session session = getHibernateSession();
		if(product != null) {
			session.saveOrUpdate(product);
		} else {
			System.err.println(LOG_TAG + "Product = null --- addProduct");
		}
	}

	@Override
	@Transactional
	public void deleteProduct(int productId) {
		Session session = getHibernateSession();
		Query query = session.createQuery("delete from Product where id=:productId");
		query.setParameter("productId", productId);
		query.executeUpdate();
	}
	
}
