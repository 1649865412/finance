/**
 *  code generation
 */
package com.sample.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sample.entity.Task;

public interface TaskDAO extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

}