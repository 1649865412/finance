/**
 *  code generation
 */
package	com.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.util.dwz.Page;
import com.base.util.dwz.PageUtils;
import com.sample.entity.Task;
import com.sample.dao.TaskDAO;

@Service
@Transactional
public class TaskService {
	
	@Autowired
	private TaskDAO taskDAO;

	/*
	 * (non-Javadoc)
	 * @see com.sample.service.TaskService#get(java.lang.Long)  
	 */ 
	 
	public Task get(Long id) {
		return taskDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.sample.service.TaskService#saveOrUpdate(com.sample.entity.Task)  
	 */
 
	public void saveOrUpdate(Task task) {
		taskDAO.save(task);
	}

	/*
	 * (non-Javadoc)
	 * @see com.sample.service.TaskService#delete(java.lang.Long)  
	 */
	 
	public void delete(Long id) {
		taskDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sample.service.TaskService#findAll(com.base.util.dwz.Page)  
	 */
	 
	public List<Task> findAll(Page page) {
		org.springframework.data.domain.Page<Task> springDataPage = taskDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sample.service.TaskService#findByExample(org.springframework.data.jpa.domain.Specification, com.base.util.dwz.Page)	
	 */
	 
	public List<Task> findByExample(
			Specification<Task> specification, Page page) {
		org.springframework.data.domain.Page<Task> springDataPage = taskDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
