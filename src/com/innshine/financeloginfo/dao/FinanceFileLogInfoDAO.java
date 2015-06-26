/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.financeloginfo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.innshine.financeloginfo.entity.FinanceFileLogInfo;

public interface FinanceFileLogInfoDAO extends JpaRepository<FinanceFileLogInfo, Long>,
		JpaSpecificationExecutor<FinanceFileLogInfo>
{
	List<FinanceFileLogInfo> findBySessionIdAndUserNameAndUserAddress(String sessionId, String userName,
			String userAddress);
	
	List<FinanceFileLogInfo> findBySessionIdAndUserName(String sessionId, String userName);
}
