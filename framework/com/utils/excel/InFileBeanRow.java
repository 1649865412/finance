package com.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 将跨行文件导入解析，导出Map，中间存放父子数据列表 
 * 两个是父子关系 ，所以在父属性中必须有details 这个列表来存放子对象。
 * 
 * @author 188563 sample 通过方法，传入文件，前台提交上来的，每一行数据将保存到Bean 对应的属性中。 新建一个List
 *         属性； 
 *         List<ParentObj> list = new ArrayList<ParentObj>(); 
 *         Vector<String> set = new
 *         Vector<String>();//装载上传的属性 //注意放的顺序，和对应的Excel 文件列一致；
 *         set.add("employeeId#");表示是父类的 
 *         set.add("employeeName#"); 表示是父类的
 *         set.add("wareId");表示是子类
 *         set.add("wareId2");表示是子类
 *         .... 
 *         初始化上传对象，同样注意Bean 的范式；注意第二个参数，告诉处理类，当前上传的是Excel 文件；
 *         InFileBeanRow fileBean = InFileBeanRow.parseData(set,
 *         InFileBeanRow.excelType,parentCls, childCls, file); 处理完后，使用上面新建的List 来接收数据；
 *         list = fileBean.fileDataMap();
 *         
 *         
  
 */

public class InFileBeanRow {
	// 文件类型
	private String fileType;
	// 字段数组
	private Vector<String> set;
	private InputStream inputStream;
	// 文件
	private File file;
	
	private Class<?> parentCls;
	private Class<?> childCls;
	
	 
	public static String excelType = "excel";
	public static String excelType2007 = "excel2007";

	public InFileBeanRow(Vector<String> set, String fileType, Class parentCls,Class<?> childCls,
			File file) {
		this.fileType = fileType;
		this.set = set;
		this.parentCls = parentCls;
		this.childCls = childCls; 
		this.file = file;
	}

	public InFileBeanRow(Vector<String> set, String fileType, Class parentCls,Class<?> childCls,
			InputStream inputStream) {
		this.fileType = fileType;
		this.set = set;
		this.parentCls = parentCls;
		this.childCls = childCls; 
		this.inputStream = inputStream;
	}

	/**
	 * 解析Excel 文档或txt 文档
	 * 
	 * @param set
	 *            列头字段名称，小写
	 * @param fileType
	 *            文件类型，就InFileBean.textType 和InFileBean.excelType两个类型
	 * @param cls
	 *            将解析的bean类
	 * @param file
	 *            要解析的文件
	 * @return 解析对象
	 */
	public static InFileBeanRow parseData(Vector<String> set, String fileType,
			Class<?> parentCls,Class<?> childCls, File file) {
		return new InFileBeanRow(set, fileType, parentCls,childCls, file);
	}

	public static InFileBeanRow parseData(Vector<String> set, String fileType,
			Class<?> parentCls,Class<?> childCls, InputStream inputStream) {
		return new InFileBeanRow(set, fileType, parentCls,childCls, inputStream);
	}

	/**
	 * 返回解析文档的数据列表
	 * 
	 * @return List 数据集
	 */
	public List<Map<String,String>> fileDataMap(int startRow) {
	    if (excelType.equals(fileType) || excelType2007.equals(fileType)) {
			return parseExcelFile(startRow);
		} else {
			return new ArrayList();
		}

	}
	
	/**
	 * 返回解析文档的数据列表
	 * 
	 * @return List 数据集
	 */
	public  List fileDataMap(int startRow,int startCell) {
		 if (excelType.equals(fileType) || excelType2007.equals(fileType)) {
			return parseExcelFile(startRow,startCell);
		} else {
			return null;
		}

	}

  
	/**
	 * 处理Excel 上传,返回指定行数据
	 */
	public  List parseExcelFile(int startRow,int startCell) {
		List  parentList = new LinkedList();  
		try {
			if (inputStream == null) {
				inputStream = new FileInputStream(file);
			}
			Workbook workbook; 
			if (excelType2007.equals(fileType)) {
				workbook = new XSSFWorkbook(inputStream);
			}else{
				workbook = new HSSFWorkbook(inputStream);
			}
			Sheet sheet1 = workbook.getSheetAt(0); // 仅处理第一张表格，
			int rowNum = sheet1.getLastRowNum() - sheet1.getFirstRowNum() + 1; 
			Map<String,Field> parentMap = FieldUtil.getFields(parentCls);
			Set<String> parentKeys = parentMap.keySet();
			Map<String,Field> childMap = FieldUtil.getFields(childCls);
			
			for (int i = startRow; i < rowNum; i++) {  
				Row pRow = sheet1.getRow(i);
				if (pRow != null) {
					long cellNum = pRow.getLastCellNum();
					if (set.size() <= cellNum) {
						Object parentObj = parentCls.newInstance();
						Object childObj = childCls.newInstance();
						for (int j = 0; j < set.size(); j++) {// 根据属性值的顺序进行遍历
							String fieldName = set.get(j);
							boolean isParent = fieldName.endsWith("#");
							if(isParent){
								//是父类属性
								fieldName = fieldName.replace("#", "");
							} 
							Cell pCell = pRow.getCell((short) j);
							Field field = childMap.get(fieldName);
							if(isParent){
								field = parentMap.get(fieldName);
							}
							field.setAccessible(true);// 至为可访问
							// 取得Excel 单元格值
							Object value = getCellValue(pCell);
							if(isParent){
								field.set(parentObj, FieldUtil.findFieldValue(value,
										field.getType()));
							}else{
								field.set(childObj, FieldUtil.findFieldValue(value,
										field.getType()));
							}
							 
						}
						//判断是否全部为空值，全部空的不加入到列表
						boolean found = false;
						for (String key  : parentKeys) {
							Field field = parentMap.get(key);
							field.setAccessible(true);
							Object value = field.get(parentObj);
							if(value != null && StringUtils.isNotEmpty(String.valueOf(value))){
								found = true;
								break;
							}
							
						}
						if(found){
							parentList.add(parentObj);
						}else{
							parentObj = parentList.get(parentList.size()-1);
						}
						Field field = parentMap.get("details");
						if(field.getType() == List.class){
							Object value = field.get(parentObj);
							List details = (List)value;
							details.add(childObj);
						} 
					} 
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parentList;
	}
	/**
	 * 处理Excel 上传
	 */
	private List<Map<String,String>> parseExcelFile(int maxRow) {
		List<Map<String,String>> datalist = new ArrayList<Map<String,String>>();
		try {
			if (inputStream == null) {
				inputStream = new FileInputStream(file);
			}
			Workbook workbook; 
			if (excelType2007.equals(fileType)) {
				workbook = new XSSFWorkbook(inputStream);
			}else{
				workbook = new HSSFWorkbook(inputStream);
			}
			Sheet sheet1 = workbook.getSheetAt(0); // 仅处理第一张表格，
			int rowNum = sheet1.getLastRowNum() - sheet1.getFirstRowNum() + 1;
			if(maxRow > 0){
				rowNum = maxRow;
			} 
			 
			//Iterator itro = sheet1.rowIterator(); 
			for (int i = 1; i < rowNum; i++) { // 过滤 掉首行
				Row pRow = sheet1.getRow(i);
				if (pRow != null) {
					long cellNum = pRow.getLastCellNum();
					if (set.size() <= cellNum) {
						Map<String,String> map = new HashMap<String, String>();
						 
						for (int j = 0; j < set.size(); j++) {// 根据属性值的顺序进行遍历
							String fieldName = set.get(j);
							Cell pCell = pRow.getCell((short) j);
							 
							// 取得Excel 单元格值
							Object value = getCellValue(pCell);
							if(value == null){
								value = "";
							}
							map.put(fieldName, String.valueOf(value));
						}
						datalist.add(map);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datalist;
	}

	/**
	 * 处理Excel 单元格字段值
	 */
	private Object getCellValue(Cell cell) {
		if (cell != null) {
			int cellType = cell.getCellType();
			if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
				return cell.getNumericCellValue();
			} else if (cellType == HSSFCell.CELL_TYPE_BOOLEAN) {
				return cell.getBooleanCellValue();
			} else if (cellType == HSSFCell.CELL_TYPE_STRING) {
				RichTextString rt = cell.getRichStringCellValue();
				return rt.toString();
			} else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
				return "";
			} else if (cellType == HSSFCell.CELL_TYPE_ERROR) {
				return cell.getErrorCellValue();
			} else if (cellType == HSSFCell.CELL_TYPE_FORMULA) {
				return cell.getCellFormula();
			} else {
				return null;
			}

		} else {
			return null;
		}
	}

}
 