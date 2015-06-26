package com.innshine.reportExport.reportInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innshine.areainfo.service.AreaInfoService;

/**
 *  <code>InterfaceExport.java  报表导出报表图标准接口</code>
 *  <p>
 *  <p>Copyright  2015 All right reserved.
 *  @author 杨荣忠 时间 2015-1-16 下午04:01:41	
 *  @version 1.0 
 *  </br>最后修改人 无
 */

public abstract class  Abstrategy

{     
	  public abstract String reportImg( InterfaceExportImpl interfaceExportImpl);
}
