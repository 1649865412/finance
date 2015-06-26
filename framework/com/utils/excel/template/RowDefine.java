package com.utils.excel.template;

import java.util.LinkedList;
import java.util.List;

public class RowDefine {
	private List<CellDefine> cells = new LinkedList<CellDefine>();

	public void addCell(CellDefine cell){
		cells.add(cell);
	}
	
	public List<CellDefine> getCells() {
		return cells;
	}

	public void setCells(List<CellDefine> cells) {
		this.cells = cells;
	}
 
}
