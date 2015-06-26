package com.utils.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;   
import org.apache.poi.ss.util.CellRangeAddress;
/**
 * 通过查询出来的列表数据，导出Excel 表单上，返回给方法是一个InputStream; 
 * Sample; 
 * Test 即导出的Bean 封装类；queryObj 传入的参数对象；
 * 先将从数据库的查询出来的数据放到一个集合 data 内；
 * Collection<Test> data = findCollectionPageBy(queryObj);
 * 定义处理行，注意getMsg 仅在biz 层使用，意思是取出资源文件中的相应国际化资源；
 * 下在一行的意思，是新建一excel 单元表；表头为getMsg("testtitle"); 
 * 
	TableDefine table = new TableDefine(getMsg("testtitle"));
	 
	table.addColumn("id", getMsg("test.id"), 0);
	table.addColumn("wareName", getMsg("test.wareName"), 0);
	table.addColumn("code", getMsg("test.code"), 0);
	....
	//导出excel InputStream ; 
	return new ExcelExport(table).export(data);
 * @author 188563
 *
 *Biz 层方法定义
 *public InputStream exportCurrentCol(QueryObj queryObj){
		try{
		   ....
			return new ExcelExport(table).export(data);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
 */
 

public class ExcelExport {
	// Excel导出表格式定义
	private TableDefine tableDefine;

	public ExcelExport(TableDefine tableDefine) {
		this.tableDefine = tableDefine;
	}

	/**
	 * 导出数据到InputStream
	 * 
	 * @param data
	 * @return
	 */
	public InputStream export(Collection<?> data) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(tableDefine.getSheetName());
		writeSheet(sheet, data);
		return save(workbook);
	}
	
	/**
	 * 导出数据到InputStream,数据为Map类型
	 * 
	 * @param data
	 * @return
	 */
	public InputStream exportWithMap(List<Map<String,Object>> data){
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(tableDefine.getSheetName());
		writeSheetWithMap(sheet, data);
		return save(workbook);
	}

	/**
	 * 导出Excel
	 * 
	 * @param sheet
	 * @param data
	 */
	private void writeSheet(HSSFSheet sheet, Collection<?> data) {
		try {
			writeSheetHeader(sheet);
			writeSheetContent(sheet, data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 导出Excel,数据为Map类型
	 * 
	 * @param sheet
	 * @param data
	 */
	private void writeSheetWithMap(HSSFSheet sheet, List<Map<String,Object>> data) {
		try {
			writeSheetHeader(sheet);
			writeSheetContentWithMap(sheet, data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 生成Excel内容,数据为Map类型
	 * 
	 * @param sheet
	 * @param data
	 * @throws Exception
	 */
	private void writeSheetContentWithMap(HSSFSheet sheet, List<Map<String,Object>> datas) throws Exception {
		if (datas.size() > 0) {
			int rowIndex = 0;
			int rIndex = 0;
			
			for(Map<String,Object> data : datas){
				int cIndex = 0;
				int cellIndex = 0;
				HSSFRow row = sheet.createRow(++rowIndex);
				for(ColumnDefine column : tableDefine.getColumns()){
					  cellIndex ++;
					 int columnIndex =  column.getIndex();
						HSSFCell cell = row.createCell(columnIndex);
						Object value = data.get(column.getPropName());
						if (column.getExportCellEvent() != null) {
							value = column.getExportCellEvent().onExportCell(column, rowIndex, cell, value);
						}
						if(column.getRangeCol() >0){
							int changecIndex = cIndex+column.getRangeCol();
							sheet.addMergedRegion(new  CellRangeAddress(rIndex, rIndex, cIndex, changecIndex));
							cIndex = changecIndex;
						}else{
							cIndex += 1;
						}
						if(column.getRangeRow() >0){
							int changerIndex = rIndex + column.getRangeRow();
							sheet.addMergedRegion(new  CellRangeAddress(rIndex, changerIndex, cIndex, cIndex));
							rIndex = changerIndex;
						}else{
							rIndex += 1;
						}
						
						 
						if(value == null){
							value = "";
						}else if(value instanceof Date){
							value = dateValue(value);
						} 
						 
						cell.setCellValue(value.toString());
				}
			}
		}
	}

	/**
	 * 生成表头
	 * 
	 * @param sheet
	 */
	private void writeSheetHeader(HSSFSheet sheet) {
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

	/**
	 * 生成Excel内容
	 * 
	 * @param sheet
	 * @param data
	 * @throws Exception
	 */
	private void writeSheetContent(HSSFSheet sheet, Collection<?> data) throws Exception {
		if (data.size() > 0) {
			Map<String, Field> fields = getClazzFields(data.iterator().next().getClass());
			int rowIndex = 0;
			for (Object obj : data) {
				HSSFRow row = sheet.createRow(++rowIndex);
				for (ColumnDefine column : tableDefine.getColumns()) {
					 int columnIndex =  column.getIndex();
					HSSFCell cell = row.createCell(columnIndex);
					Field mfield = fields.get(column.getPropName());
					Object value = mfield.get(obj);
					if (column.getExportCellEvent() != null) {
						value = column.getExportCellEvent().onExportCell(column, rowIndex, cell, value);
					}
					 
					if(value == null){
						value = "";
					}else if(mfield.getType() == Date.class){
						value = dateValue(value);
					} 
					 
					cell.setCellValue(value.toString());
				}
			}
		}
	}

	/**
	 * 获取对象的全部属性
	 * 
	 * @param clazz
	 * @return
	 */
	private Map<String, Field> getClazzFields(Class<?> clazz) {
		Map<String, Field> fieldMap = new HashMap<String, Field>();
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				fieldMap.put(field.getName(), field);
			}
		}
		return fieldMap;
	}
	//格式化日期处理
	private  String dateValue(Object value){
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
		"yyyy-MM-dd HH:mm:ss");
		 SimpleDateFormat DATE_FORMAT_SHORT = new SimpleDateFormat(
			"yyyy-MM-dd");
		 String datestr = String.valueOf(value);
		 Date date = (Date)value;
		 try{
			 if (datestr == null || datestr.equals("null") || datestr.trim().equals("")){
					return "";
				}else if(datestr.length()<=10){ 
					return  DATE_FORMAT_SHORT.format(date);
				}else{
					return DATE_FORMAT.format(date);
			 }
		 
		 }catch(Exception e){
			 e.printStackTrace();
			 return "";
		 }
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
