package com.innshine.reportExport.test;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.innshine.reportExport.service.FinanceExcelParseService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext*.xml" })
@TestExecutionListeners( { TransactionalTestExecutionListener.class })
public class FinanceExcelParseServiceTest extends AbstractJUnit4SpringContextTests
{
	@Autowired
	private FinanceExcelParseService financeExcelParseService;
	
	@Test
	public void testParseFile()
	{
		File file = new File("C:\\Documents and Settings\\Administrator\\桌面\\2014.xls");
		
		financeExcelParseService.parseFile(file, null,null);
	}
}
