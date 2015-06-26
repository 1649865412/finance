package com.utils.excel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 将导入文件; 支持Excel 和txt 导入的txt 文件是由Excel 另存而得来的
 * 
 * @author 188563 sample 通过方法，传入文件，前台提交上来的，每一行数据将保存到Bean 对应的属性中。 新建一个List
 *         属性；注意，要使用Bean 范式,暂不支持Map 型；下面是新建 一个Test 类范式List , 用来存储处理后的数据；
 *         List<Test> dataList = new ArrayList<Test>(); Vector<String> set = new
 *         Vector<String>();//装载上传的属性 //注意放的顺序，和对应的Excel 文件列一致；
 *         set.add("employeeId"); set.add("employeeName"); set.add("wareId");
 *         .... 初始化上传对象，同样注意Bean 的范式；注意第二个参数，告诉处理类，当前上传的是Excel 文件；
 *         InFileBean<Test> fileBean = InFileBean.parseData(set,
 *         InFileBean.excelType, Test.class, file); 处理完后，使用上面新建的List 来接收数据；
 *         dataList = fileBean.fileDataList();
 * 
 * @param <T>
 *            Bean 类的基型，如Test Bean 类，写时应该是InFileBean<Test>
 */

public class InFileBean<T> {
	// 文件类型
	private String fileType;
	// 字段数组
	private Vector<String> set;
	private InputStream inputStream;
	// 文件
	private File file;
	// 主类型
	private Class<?> cls;
	// 文件格式，暂仅支持txt 和excel 文件
	public static String textType = "txt";
	public static String excelType = "excel";
	public static String excelType2007 = "excel2007";

	public InFileBean(Vector<String> set, String fileType, Class<?> cls,
			File file) {
		this.fileType = fileType;
		this.set = set;
		this.file = file;
		this.cls = cls;
	}

	public InFileBean(Vector<String> set, String fileType, Class<?> cls,
			InputStream inputStream) {
		this.fileType = fileType;
		this.set = set;
		this.inputStream = inputStream;
		this.cls = cls;
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
	public static InFileBean parseData(Vector<String> set, String fileType,
			Class<?> cls, File file) {
		return new InFileBean(set, fileType, cls, file);
	}

	public static InFileBean parseData(Vector<String> set, String fileType,
			Class<?> cls, InputStream inputStream) {
		return new InFileBean(set, fileType, cls, inputStream);
	}

	/**
	 * 返回解析文档的数据列表
	 * 
	 * @return List 数据集
	 */
	public List<T> fileDataList(int startRow) {
		if (textType.equals(fileType)) {
			return parseTextFile();
		} else if (excelType.equals(fileType) || excelType2007.equals(fileType)) {
			return parseExcelFile(startRow);
		} else {
			return new ArrayList();
		}

	}

	/**
	 * 处理处理Txt文件上传
	 */
	private List<T> parseTextFile() {
		BufferedReader filebuff = null;
		try {
			List<T> datalist = new ArrayList<T>();
			FileReader fileRead = new FileReader(file);
			filebuff = new BufferedReader(fileRead);
			String line;
			int countNum = 0;
			while ((line = filebuff.readLine()) != null) {
				if (countNum > 0) {// 跳过第一行
					if (line.trim().length() > 0) {// 读取的行长度为空0
						String[] perlines = line.split("\t");
						if (set.size() <= perlines.length) {
							Object objClazz = cls.newInstance();
							for (int i = 0; i < set.size(); i++) {
								String fieldName = set.get(i);
								Field field = FieldUtil.getDeclaredField(cls,
										fieldName);
								field.setAccessible(true);// 至为可访问
								field.set(objClazz, FieldUtil.findFieldValue(
										perlines[i], field.getType()));
							}
							datalist.add((T) objClazz);
						}

					}
				}
				countNum++;
			}

			return datalist;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (filebuff != null) {
				try {
					filebuff.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 处理Excel 上传
	 */
	private List<T> parseExcelFile(int startRow) {
		List<T> datalist = new ArrayList<T>();
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
			 
			Map<String,Field> fieldsMap = FieldUtil.getFields(cls);
			//Iterator itro = sheet1.rowIterator(); 
			for (int i = startRow; i < rowNum; i++) { // 过滤 掉首行
				Row pRow = sheet1.getRow(i);
				if (pRow != null) {
					long cellNum = pRow.getLastCellNum();
					if (set.size() <= cellNum) {
						Object objClazz = cls.newInstance();
						for (int j = 0; j < set.size(); j++) {// 根据属性值的顺序进行遍历
							String fieldName = set.get(j);
							Cell pCell = pRow.getCell((short) j);
							Field field = fieldsMap.get(fieldName);
							field.setAccessible(true);// 至为可访问
							// 取得Excel 单元格值
							Object value = getCellValue(pCell);
							field.set(objClazz, FieldUtil.findFieldValue(value,
									field.getType()));
						}
						datalist.add((T) objClazz);
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
