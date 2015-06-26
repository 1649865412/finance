package com.utils.excel;

import java.util.ArrayList;
import java.util.Collection;
/**
 * 导出时处理行函数；
 * @author 188563
 *
 */
public class TableDefine {

	// 表单名称
	private String sheetName;
	// 导入数据的开始行
	private int startRow;
	// 列定义
	private Collection<ColumnDefine> columns;

	public TableDefine(String sheetName) {
		this.sheetName = sheetName;
		this.columns = new ArrayList<ColumnDefine>();
	}

	public String getSheetName() {
		return sheetName;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public Collection<ColumnDefine> getColumns() {
		return columns;
	}

	public void addColumn(ColumnDefine column) {
		this.columns.add(column);
	}

	public ColumnDefine addColumn(String propName, String title, int width, IExportCellEvent exportCellEvent) {
		return addColumn(propName, title, width, columns.size(), exportCellEvent, null);
	}

	public ColumnDefine addColumn(String propName, String title, int width) {
		return addColumn(propName, title, width, null);
	}
	
	public ColumnDefine addRangeColColumn(String propName, String title, int width,int rangeCol) {
		ColumnDefine column = new ColumnDefine();
		column.setPropName(propName);
		column.setTitle(title);
		column.setWidth(width); 
		column.setRangeCol(rangeCol); 
		addColumn(column);
		return column;
	}
	
	public ColumnDefine addRangeRowColumn(String propName, String title, int width,int rangeRow) {
		ColumnDefine column = new ColumnDefine();
		column.setPropName(propName);
		column.setTitle(title);
		column.setWidth(width); 
		column.setRangeRow(rangeRow);
		addColumn(column);
		return column;
	}

	public ColumnDefine addColumn(String propName, int index, IImportCellEvent importCellEvent) {
		return addColumn(propName, null, 0, index, null, importCellEvent);
	}

	public ColumnDefine addColumn(String propName, int index) {
		return addColumn(propName, index, null);
	}

	private ColumnDefine addColumn(String propName, String title, int width, int index,
			IExportCellEvent exportCellEvent, IImportCellEvent importCellEvent) {
		ColumnDefine column = new ColumnDefine();
		column.setPropName(propName);
		column.setTitle(title);
		column.setWidth(width);
		column.setIndex(index);
		column.setExportCellEvent(exportCellEvent);
		column.setImportCellEvent(importCellEvent);
		addColumn(column);
		return column;
	}
}
