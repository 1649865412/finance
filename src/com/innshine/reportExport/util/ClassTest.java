package com.innshine.reportExport.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.innshine.areainfo.service.AreaInfoService;
import com.innshine.reportExport.entity.ConditionEntity;
import com.innshine.reportExport.reportInterface.Abstrategy;
import com.innshine.reportExport.reportInterface.InterfaceExportImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext*.xml" })
@TestExecutionListeners( { TransactionalTestExecutionListener.class })
public class ClassTest extends AbstractJUnit4SpringContextTests
{
	@Autowired
	private AreaInfoService areaInfoService;
		
	@Test
	public void testDoTask() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException
	{		

	}
}
