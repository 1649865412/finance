package com.utils.excel;

 

public interface IImportCellEvent {
	public Object onImportCell(ColumnDefine column, String value);
}
