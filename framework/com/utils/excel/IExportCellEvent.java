package com.utils.excel;

 
import org.apache.poi.hssf.usermodel.HSSFCell; 

public interface IExportCellEvent {
	public Object onExportCell(ColumnDefine column, int row, HSSFCell cell, Object value);
}
