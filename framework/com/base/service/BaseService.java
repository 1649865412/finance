package com.base.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class BaseService {
	@PersistenceContext
	private EntityManager em;
	
	 
}
