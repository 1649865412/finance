/**
 *  code generation
 */
package com.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.base.entity.main.LogInfo;

public interface LogInfoDAO extends JpaRepository<LogInfo, Long>, JpaSpecificationExecutor<LogInfo> {

}