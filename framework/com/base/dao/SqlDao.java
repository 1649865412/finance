package com.base.dao;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.base.dao.sql.ResultToBean;
import com.base.dao.sql.SQLHelper;
import com.base.dao.sql.SQLProperties;
import com.base.service.component.BusinessCache;
import com.base.util.dwz.Page;


/*******
 * SQL 查询DAO 在用的地方直接注入就可以了
 * 
 * @author ruijin
 * 
 */
@Repository
public class SqlDao {
	private static final Logger log = LoggerFactory.getLogger(SqlDao.class);

	private EntityManagerFactory defaultEm;


	

	private EntityManager em;

	@Resource(name = "entityManagerFactory")
	public void setDefaultEm(EntityManagerFactory defaultEm) {
		this.defaultEm = defaultEm;
		em = defaultEm.createEntityManager();
		if (em == null) {
			log.error("could not init em");
		}
	}




	public <T> List<T> queryByPageObject(Map<String, Object> param,
			String sqlCfgName, Page page, Class clazz) throws Exception {
		String executeSql = "";
		if (StringUtils.isNotEmpty(sqlCfgName)) {
			List<Object[]> listresult = new ArrayList<Object[]>();
			String sql = SQLProperties.getInstance().getSql(sqlCfgName);
			SQLHelper sqlHelper = new SQLHelper(sql);
			executeSql = sqlHelper.parepareSQLtext(param);
		}
		int perPage = page.getNumPerPage();
		int pageNum = page.getPageNum();
		String pageSql = "select * from (" + executeSql
				+ ") as page_list_tmp limit " + (pageNum - 1) * perPage + ", "
				+ pageNum * perPage;

		if (page.getTotalCount() == 0) {
			String countSql = " select count(1)  from (" + executeSql
					+ ") as page_tabel_tmp";
			BigInteger totalCount = (BigInteger) em.createNativeQuery(countSql)
					.getSingleResult();
			page.setTotalCount(totalCount.longValue());
		}

		Query query = em.createNativeQuery(pageSql);
		query.unwrap(SQLQuery.class).setResultTransformer(
				new ResultToBean().as(clazz));
		List<T> list = query.getResultList();

		return list;
	}

	/***********
	 * 查询数据，返回列表
	 * 
	 * @param <T>
	 * @param object
	 *            参数对象
	 * @param sqlCfgName
	 *            sql 配置文件中的key 值，具体看resources/sql 目录下的文件
	 * @param class1
	 *            类的class
	 * @return 查询数据的列表
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> queryListBySql(Map<String, Object> param,
			String sqlCfgName, Class clazz) throws Exception {
		String sql = SQLProperties.getInstance().getSql(sqlCfgName);
		SQLHelper sqlHelper = new SQLHelper(sql);
		String executeSql = sqlHelper.parepareSQLtext(param);
		log.debug(executeSql);
		Query query = em.createNativeQuery(executeSql);
		query.unwrap(SQLQuery.class).setResultTransformer(
				new ResultToBean().as(clazz));
		List<T> list = query.getResultList();
		return list;

	}

	/***********
	 * 查询数据，返回列表
	 * 
	 * @param <T>
	 * @param object
	 *            参数对象
	 * @param sqlCfgName
	 *            sql 配置文件中的key 值，具体看resources/sql 目录下的文件
	 * @param clazz
	 *            类的class
	 * @return 查询数据的列表
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> queryListBySql(String object, String sqlCfgName,
			Class<T> clazz) throws Exception {
		String sql = SQLProperties.getInstance().getSql(sqlCfgName);

		SQLHelper sqlHelper = new SQLHelper(sql);

		String executeSql = sqlHelper.parepareSQLObject(object);
	//	System.out.println("=========="+executeSql);

		Query query = em.createNativeQuery(executeSql);
		query.unwrap(SQLQuery.class).setResultTransformer(
				new ResultToBean().as(clazz));
		List<T> list = query.getResultList();
		return list;
	}

	/***********
	 * 查询数据，返回列表
	 * 
	 * @param <T>
	 * @param executeSql
	 *            执行SQL
	 * @param clazz
	 *            实体类的class
	 * @return 查询数据的列表
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> queryListBySql(String executeSql, Class<T> clazz)
			throws Exception {

		log.debug(executeSql);
		Query query = em.createNativeQuery(executeSql);
		query.unwrap(SQLQuery.class).setResultTransformer(
				new ResultToBean().as(clazz));
		List<T> list = query.getResultList();
		return list;
	}

	public <T> T querySingleObj(Map<String, Object> object, String sqlCfgName,
			Class<T> clazz) throws Exception {
		String sql = SQLProperties.getInstance().getSql(sqlCfgName);

		SQLHelper sqlHelper = new SQLHelper(sql);

		String executeSql = sqlHelper.parepareSQLObject(object);
		// log.debug(executeSql);

		Query query = em.createNativeQuery(executeSql);
		query.unwrap(SQLQuery.class).setResultTransformer(
				new ResultToBean().as(clazz));
		List<T> list = query.getResultList();
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**********
	 * 执行数据库更新操作，包括增加、删除、修改
	 * 
	 * @param object
	 *            参数对象
	 * @param sqlCfgName
	 *            sqlCfgName sql 配置文件中的key 值，具体看resources/sql 目录下的文件
	 * @return 执行更新的结果
	 * @throws Exception
	 */
	public int updateBy(Map<String,Object> object, String sqlCfgName) throws Exception {
		String sql = SQLProperties.getInstance().getSql(sqlCfgName);
		SQLHelper sqlHelper = new SQLHelper(sql);
		String executeSql = sqlHelper.parepareSQLObject(object);
		log.debug(executeSql);
		beginTransaction();
		int i = 0;
		try
		{
			 i = em.createNativeQuery(executeSql).executeUpdate();
			commitTransaction();  
		}
		catch(Exception e)
		{
			e.printStackTrace();
			rollbackTransacrion();
		}
		/*
		 * Map<String, Object> param=new HashMap(); String like="14,13,1";
		 * param.put("name", "管理人员"); param.put("id", 12); param.put("array",
		 * like); List<Role> list = sqlDao.queryListBySqltext(param,
		 * "builder.page", Role.class); System.out.println(list.size());
		 */
		return i;
	}
	
	/**********
	 * 执行数据库更新操作，包括增加、删除、修改
	 * 
	 * @param object
	 *            参数对象
	 * @param sqlCfgName
	 *            sqlCfgName sql 配置文件中的key 值，具体看resources/sql 目录下的文件
	 * @return 执行更新的结果
	 * @throws Exception
	 */
	public int deleteBySql(Map<String,Object> object, String sqlCfgName) throws Exception {
		String sql = SQLProperties.getInstance().getSql(sqlCfgName);
		SQLHelper sqlHelper = new SQLHelper(sql);
		String executeSql = sqlHelper.parepareSQLtext(object);
		log.debug(executeSql);
		int i = 0;
		beginTransaction();
		try
		{
			i = em.createNativeQuery(executeSql).executeUpdate();
			commitTransaction();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			rollbackTransacrion();
		}
		/*
		 * Map<String, Object> param=new HashMap(); String like="14,13,1";
		 * param.put("name", "管理人员"); param.put("id", 12); param.put("array",
		 * like); List<Role> list = sqlDao.queryListBySqltext(param,
		 * "builder.page", Role.class); System.out.println(list.size());
		 */
		return i;
	}

	public <T> List<T> queryByPage(Object params, String appendSql,
			String sqlCfgName, Page page, Class<T> clazz) throws Exception {
		String executeSql = "";
		if (StringUtils.isNotEmpty(sqlCfgName)) {
			String sql = SQLProperties.getInstance().getSql(sqlCfgName);
			SQLHelper sqlHelper = new SQLHelper(sql);
			executeSql = params instanceof Map ? sqlHelper.parepareSQLtext((Map)params):  sqlHelper.parepareSQLObject(params);
			if (StringUtils.isNotEmpty(appendSql)) {
				executeSql = executeSql + appendSql;
			}
		} else {
			executeSql += appendSql;
		}

		int perPage = page.getNumPerPage();
		int pageNum = page.getPageNum();
		String pageSql = "select * from (" + executeSql
				+ ") as page_list_tmp limit " + (pageNum - 1) * perPage + ", "
				+ pageNum * perPage;
		if (page.getTotalCount() == 0) {
			String countSql = " select count(1)  from (" + executeSql
					+ ") as page_tabel_tmp";
			BigInteger totalCount = (BigInteger) em.createNativeQuery(countSql)
					.getSingleResult();
			log.debug(countSql);
			page.setTotalCount(totalCount.longValue());
		}
		Query query = em.createNativeQuery(pageSql);
		query.unwrap(SQLQuery.class).setResultTransformer(
				new ResultToBean().as(clazz));
		log.debug(pageSql);
		List<T> list = query.getResultList();

		return list;
	}

	public <T> List<T> queryByPage(String executeSql, Page page, Class<T> clazz)
			throws Exception {
		return queryByPage(null, executeSql, null, page, clazz);
	}

	public <T> List<T> queryByPage(Map<String, Object> params,
			String sqlCfgName, Page page, Class<T> clazz) throws Exception {
		return queryByPage(params, null, sqlCfgName, page, clazz);
	}

	public <T> List<T> queryByPage(T t, String sqlCfgName, Page page,
			Class<T> clazz) throws Exception {
		return queryByPage(t, null, sqlCfgName, page, clazz);
	}
	
	public void closeEntityManager() {
		em.close();
	}

	public void rollbackTransacrion() throws SQLException {
		if (em != null) {
			em.getTransaction().rollback();
		}
	}

	public void commitTransaction() throws SQLException {
		if(null != em)
		{
			em.getTransaction().commit();
		}
	}

	public void beginTransaction() throws SQLException {
	
		if(null != em)
		{
			em.getTransaction().begin();
		}
	}
}
