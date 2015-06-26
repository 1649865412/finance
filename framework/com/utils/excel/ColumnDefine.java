package com.utils.excel;

public class ColumnDefine {
	// 属性名称
	private String propName;
	// 列标题
	private String title;
	// 列宽度
	private int width;
	// 列位置，在导入时，用于匹配列与属性
	private int index;
	
	private int rangeCol;
	
	public int getRangeCol() {
		return rangeCol;
	}

	public void setRangeCol(int rangeCol) {
		this.rangeCol = rangeCol;
	}

	public int getRangeRow() {
		return rangeRow;
	}

	public void setRangeRow(int rangeRow) {
		this.rangeRow = rangeRow;
	}

	private int rangeRow;
	
	// 导出单元格事件
	private IExportCellEvent exportCellEvent;
	// 导入单元格事件
	private IImportCellEvent importCellEvent;

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public IExportCellEvent getExportCellEvent() {
		return exportCellEvent;
	}

	public void setExportCellEvent(IExportCellEvent exportCellEvent) {
		this.exportCellEvent = exportCellEvent;
	}

	public IImportCellEvent getImportCellEvent() {
		return importCellEvent;
	}

	public void setImportCellEvent(IImportCellEvent importCellEvent) {
		this.importCellEvent = importCellEvent;
	}
}
