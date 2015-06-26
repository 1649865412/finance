/**
 *  code generation
 */
package com.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.base.entity.main.DataControl;

public interface DataControlDAO extends JpaRepository<DataControl, Long>, JpaSpecificationExecutor<DataControl> {

}