package com.utils.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelTemplateExport {
	private List<TableDefine> list;
	
	public ExcelTemplateExport(List<TableDefine> list){
		this.list = list;
	}
	
	public InputStream export(){
		HSSFWorkbook workbook = new HSSFWorkbook();
		for(TableDefine tableDefine : list){  
			HSSFSheet sheet = workbook.createSheet(tableDefine.getSheetName());
			HSSFRow row = sheet.createRow(0);
			for (ColumnDefine column : tableDefine.getColumns()) {
				int columnIndex =   column.getIndex();
				HSSFCell cell = row.createCell(columnIndex);
				if (column.getWidth() > 0) {
					sheet.setColumnWidth(columnIndex, (short) column.getWidth());
				}
				Object value = column.getTitle();
				if (column.getExportCellEvent() != null) {
					value = column.getExportCellEvent().onExportCell(column, 0, cell, value);
				}
				if(value == null){
					value = "";
				} 
				cell.setCellValue(value.toString());
			}
		}
		return save(workbook);
	}
	
	/**
	 * 保存Excel到InputStream
	 * 
	 * @param workbook
	 * @return
	 */
	private InputStream save(HSSFWorkbook workbook) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			workbook.write(bos);
			InputStream bis = new ByteArrayInputStream(bos.toByteArray());
			return bis;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
