package com.utils.excel.template;

public class  CellDefine {  
	private Integer colRow = 1;
	private Integer colCell = 1;
	 
	private String property;
	private String value;
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public CellDefine(String property,String value){ 
		this.property = property;
		this.value = value;
		//System.out.println(""+property+","+value);
	}
	 
	public CellDefine(String property,String value,Integer colRow){ 
		this.property = property;
		this.value = value;
		this.colRow = colRow;
	}
	
	public CellDefine(String property,String value,Integer colRow,Integer colCell){ 
		this.property = property;
		this.value = value;
		this.colRow = colRow;
		this.colCell = colCell;
	}
	
	
	public Integer getColRow() {
		return colRow;
	}

	public void setColRow(Integer colRow) {
		this.colRow = colRow;
	}

	public Integer getColCell() {
		return colCell;
	}

	public void setColCell(Integer colCell) {
		this.colCell = colCell;
	}

	 
	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
}
