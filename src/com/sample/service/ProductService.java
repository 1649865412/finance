/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package	com.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.util.dwz.Page;
import com.base.util.dwz.PageUtils;
import com.sample.entity.Product;
import com.sample.dao.ProductDAO; 

@Service
@Transactional
public class ProductService  {
	
	@Autowired
	private ProductDAO productDAO;

	/*
	 * (non-Javadoc)
	 * @see com.sample.service.ProductService#get(java.lang.Long)  
	 */ 
	 
	public Product get(Long id) {
		return productDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.sample.service.ProductService#saveOrUpdate(com.sample.entity.Product)  
	 */
 
	public void saveOrUpdate(Product product) {
		productDAO.save(product);
	}

	/*
	 * (non-Javadoc)
	 * @see com.sample.service.ProductService#delete(java.lang.Long)  
	 */
 
	public void delete(Long id) {
		productDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sample.service.ProductService#findAll(com.base.util.dwz.Page)  
	 */
	 
	public List<Product> findAll(Page page) {
		org.springframework.data.domain.Page<Product> springDataPage = productDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	  
	 * (non-Javadoc)
	 * @see com.sample.service.ProductService#findByExample(org.springframework.data.jpa.domain.Specification, com.base.util.dwz.Page)	
	 */ 
	public List<Product> findByExample(
			Specification<Product> specification, Page page) {
		org.springframework.data.domain.Page<Product> springDataPage = productDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
