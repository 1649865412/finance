package com.utils.excel;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 将导入文件; 支持Excel 和txt 导入的txt 文件是由Excel 另存而得来的
 * 
 * @author 通过方法，传入文件，前台提交上来的，每一行数据将保存到Bean 对应的属性中。 新建一个List 属性；注意，要使用Bean
 *         范式,暂不支持Map 型；下面是新建 一个Test 类范式List , 用来存储处理后的数据； List<Test> dataList =
 *         new ArrayList<Test>(); Vector<String> set = new
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

public class FileExcelUtils<T>
{
	private static final String PNG = "PNG";
	
	/**
	 * 默认编码 System.getProperty("file.encoding") 获取系统默认编码;
	 */
	private static final String DEFAULT_ENCODING = "UTF-8";
	
	/**
	 * txt、CVS 文件内容分割符
	 */
	private static final String REGEX_SPLIT = ",";
	
	/**
	 * 文件格式，暂仅支持txt 和excel 文件
	 */
	public static final String TEXT_TYPE = "txt";
	
	/**
	 * 文件格式，暂仅支持txt 和excel 文件
	 */
	public static final String EXCEL_TYPE = "excel";
	
	/**
	 * 文件类型
	 */
	private String fileType;
	/**
	 * 字段数组
	 */
	private Vector<String> set;
	/**
	 * 文件
	 */
	private File file;
	/**
	 * 文件
	 */
	private Class<?> cls;
	
	public FileExcelUtils(Vector<String> set, String fileType, Class<?> cls, File file)
	{
		this.fileType = fileType;
		this.set = set;
		this.file = file;
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
	@SuppressWarnings("unchecked")
	public static FileExcelUtils parseData(Vector<String> set, String fileType, Class<?> cls, File file)
	{
		return new FileExcelUtils(set, fileType, cls, file);
	}
	
	/**
	 * 返回解析文档的数据列表
	 * 
	 * @return List 数据集
	 */
	public List<T> fileDataList()
	{
		if (TEXT_TYPE.equals(fileType))
		{
			return parseTextFile();
		}
		else if (EXCEL_TYPE.equals(fileType))
		{
			return parseExcelFile(1);
		}
		else
		{
			return new ArrayList<T>();
		}
		
	}
	
	/***************
	 * 返回解析文档的数据列表
	 * 
	 * @param row
	 *            跳过多少行，对excel 有效，索引从0开始计算
	 * @return
	 */
	public List<T> fileDataList(int row)
	{
		if (TEXT_TYPE.equals(fileType))
		{
			return parseTextFile();
		}
		else if (EXCEL_TYPE.equals(fileType))
		{
			return parseExcelFile(row);
		}
		else
		{
			return new ArrayList<T>();
		}
		
	}
	
	/**
	 * 处理处理Txt文件上传
	 */
	private List<T> parseTextFile()
	{
		InputStreamReader fileRead = null;
		BufferedReader filebuff = null;
		InputStream is = null;
		try
		{
			List<T> datalist = new ArrayList<T>();
			is = new FileInputStream(file);
			fileRead = new InputStreamReader(is, DEFAULT_ENCODING);
			
			filebuff = new BufferedReader(fileRead);
			String line;
			int countNum = 0;
			while ((line = filebuff.readLine()) != null)
			{
				if (countNum > 0)
				{// 跳过第一行
					if (line.trim().length() > 0)
					{// 读取的行长度为空0
						String[] perlines = line.split(REGEX_SPLIT);
						if (set.size() <= perlines.length)
						{
							Object objClazz = cls.newInstance();
							for (int i = 0; i < set.size(); i++)
							{
								String fieldName = set.get(i);
								Field field = FieldUtils.getDeclaredField(cls, fieldName);
								field.setAccessible(true);// 至为可访问
								field.set(objClazz, FieldUtils.findFieldValue(perlines[i], field.getType()));
							}
							datalist.add((T) objClazz);
						}
						
					}
				}
				countNum++;
			}
			
			return datalist;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		finally
		{
			
			if (is != null)
			{
				try
				{
					is.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			
			if (null != fileRead)
			{
				try
				{
					fileRead.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			
			if (null != filebuff)
			{
				try
				{
					filebuff.close();
				}
				catch (IOException e)
				{
					
					e.printStackTrace();
				}
			}
		}
		
	}
	
	/**
	 * 功能:处理Excel文件解析，该方法目前只支持2003、2007、2010 excel. <br/>
	 * <p>
	 * 作者 null 2014-5-19 下午01:53:48
	 * 
	 * @param row
	 *            忽略的行数，默认忽略第1行标题头
	 * @param workbook
	 *            解析的execl对象
	 * @exception IllegalArgumentException
	 *                如果Workbook参数为null，则会返回 IllegalArgumentException
	 * @return 返回解析结果列表，列表可能为空
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	private List<T> parseExcelFile(int row, Workbook workbook)
	{
		
		List<T> datalist = new LinkedList<T>();
		
		if (null == workbook)
		{
			throw new IllegalArgumentException("parameter error！ workbook =  " + workbook);
		}
		try
		{
			
			Sheet sheet1 = workbook.getSheetAt(0); // 仅处理第一张表格，
			int rowNum = sheet1.getLastRowNum() - sheet1.getFirstRowNum() + 1;
			Iterator<Row> itro = sheet1.rowIterator();
			for (int i = row; i < rowNum; i++)
			{ // 过滤 掉首行
				Row pRow = sheet1.getRow(i);
				if (pRow != null)
				{
					long cellNum = pRow.getLastCellNum();
					Object objClazz = cls.newInstance();
					for (int j = 0; j < set.size(); j++)
					{// 根据属性值的顺序进行遍历
						String fieldName = set.get(j);
						Field field = FieldUtils.getDeclaredField(cls, fieldName);
						field.setAccessible(true);// 至为可访问
						// 取得Excel 单元格值
						Object value = null;
						if (j < cellNum)
						{
							Cell pCell = pRow.getCell((short) j);
							
							value = getCellValue(pCell, workbook);
						}
						else
						{
							value = "";
						}
						
						// 处理有跨行情况下，会出现多空行的情况
						if (value instanceof String && StringUtils.isBlank((String) value))
						{
							if ((field.getType() != List.class))
							{
								continue;
							}
						}
						else if (value == null)
						{
							if ((field.getType() != List.class))
							{
								continue;
							}
						}
						
						setFieldByType(objClazz, field, value, j, cellNum, pRow, workbook);
					}
					datalist.add((T) objClazz);
				}
			}
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return datalist;
	}
	
	/**
	 * 处理末尾为List ，默认则认为后面还有N个字段列需要读取，读取完成后存入List中，
	 * 
	 * @param objClazz
	 *            数据对象Class
	 * @param field
	 *            自定义片段
	 * @param value
	 *            第1次值
	 * @param j
	 *            当前原始对象中的字段数量，默认为最后一个
	 * @param cellNum
	 *            有效的最后一列
	 * @param workbook
	 *            工作薄对象
	 * @param pRow
	 *            行对象
	 * @throws IllegalAccessException
	 */
	
	public void setFieldByType(Object objClazz, Field field, Object value, int j, long cellNum, Row pRow,
			Workbook workbook) throws IllegalAccessException
	{
		if (field.getType() == List.class)
		{
			List<Object> fieldValues = new LinkedList<Object>();
			try
			{
				
				for (int i = j; i < cellNum; i++)
				{
					Cell pCell = pRow.getCell((short) i);
					
					value = getCellValue(pCell, workbook);
					
					if (null == value)
					{
						value = "";
					}
					
					fieldValues.add(value);
					
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			field.set(objClazz, fieldValues);
		}
		else
		{
			try
			{
				field.set(objClazz, FieldUtils.findFieldValue(value, field.getType()));
			}
			catch (Exception e)
			{
				field.set(objClazz, null);
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 功能:处理Excel文件解析，该方法目前兼容2003~2007
	 * <p>
	 * 
	 * @param row
	 *            忽略的行数，默认忽略第1行标题头
	 * @return 返回解析结果列表
	 */
	private List<T> parseExcelFile(int row)
	{
		return parseExcelFile(row, createCommonWorkbook());
		
	}
	
	/**
	 * 功能: 创建操作excel对应版本对象 ，支持2003、2007、2010
	 * <p>
	 * 作者 admin 2014-5-19 下午03:06:19
	 * 
	 * @return Workbook 有可能返回null ,需要对null进行处理
	 */
	private Workbook createCommonWorkbook()
	{
		if (null == file)
		{
			return null;
		}
		
		try
		{
			InputStream is = new FileInputStream(file);
			
			if (file.getPath().indexOf(".xlsx") > -1)
			{
				return new XSSFWorkbook(is);
			}
			else
			{
				return new HSSFWorkbook(is);
			}
			
		}
		catch (FileNotFoundException e)
		{
			
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 处理Excel 单元格字段值
	 */
	private Object getCellValue(Cell cell, Workbook workbook)
	{
		if (cell != null)
		{
			
			int cellType = cell.getCellType();
			if (cellType == HSSFCell.CELL_TYPE_NUMERIC)
			{
				String dataStr = getDateValue(cell);
				if (StringUtils.isNotBlank(dataStr))
				{
					return dataStr;
				}
				
				// 格式化double数据，以防止长整型数据变成科学计数法形式，对Double本身或int类型不影响。
				// 如：694E+537059828
				DecimalFormat df = new DecimalFormat("#.##");
				try
				{
					dataStr = df.format(cell.getNumericCellValue());
				}
				catch (ArithmeticException e)
				{
					e.printStackTrace();
				}
				
				return dataStr;
			}
			else if (cellType == HSSFCell.CELL_TYPE_BOOLEAN)
			{
				return cell.getBooleanCellValue();
			}
			else if (cellType == HSSFCell.CELL_TYPE_STRING)
			{
				RichTextString hr = cell.getRichStringCellValue();
				return hr.toString();
			}
			else if (cellType == HSSFCell.CELL_TYPE_BLANK)
			{
				return "";
			}
			else if (cellType == HSSFCell.CELL_TYPE_ERROR)
			{
				return cell.getErrorCellValue();
			}
			else if (cellType == HSSFCell.CELL_TYPE_FORMULA)
			{
				return formulaGetValue(cell, workbook);
				// return cell.getCellFormula();
			}
			else
			{
				return null;
			}
			
		}
		else
		{
			return null;
		}
	}
	
	private String getDateValue(Cell cell)
	{
		if (HSSFDateUtil.isCellDateFormatted(cell))
		{
			SimpleDateFormat sdf = null;
			short format = cell.getCellStyle().getDataFormat();
			if (format == 14 || format == 31 || format == 57 || format == 58)
			{
				// 日期 yyyy-MM-dd HH:mm:ss
				sdf = new SimpleDateFormat("yyyy-MM-dd");
			}
			else if (format == 20 || format == 32)
			{
				// 日期格式
				sdf = new SimpleDateFormat("HH:mm");
			}
			
			Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
			
			return null == sdf ? null : sdf.format(date);
			
		}
		
		return null;
	}
	
	/**
	 * 插入图片到EXCEL
	 * 
	 * @param writableSheet
	 *            sheet
	 * @param pictureFile
	 *            图片file对象
	 * @param cellRow
	 *            行数
	 * @param cellCol
	 *            列数
	 * @throws Exception
	 * 
	 */
	public static void addPictureToExcel(Sheet writableSheet, Workbook workbook, File pictureFile, int startRow,
			int endRow, int startColmum, int endColmun) throws Exception
	{
		// // 开始位置
		double picBeginCol = startColmum - 1;
		double picBeginRow = startRow - 1;
		
		// 图片时间的高度，宽度
		double picCellWidth = 0.0;
		double picCellHeight = 0.0;
		// // 读入图片
		BufferedImage picImage = ImageIO.read(pictureFile);
		// 取得图片的像素高度，宽度
		int picWidth = picImage.getWidth();
		int picHeight = picImage.getHeight();
		
		// 计算图片的实际宽度
		int picWidth_t = picWidth * 32;
		for (int x = 0; x < 1234; x++)
		{
			int bc = (int) Math.floor(picBeginCol + x);
			// 得到单元格的宽度
			int v = writableSheet.getColumnWidth(bc);
			double offset0_t = 0.0;
			if (0 == x)
			{
				offset0_t = (picBeginCol - bc) * v;
			}
			
			if (0.0 + offset0_t + picWidth_t > v)
			{
				// 剩余宽度超过一个单元格的宽度
				double ratio_t = 1.0;
				if (0 == x)
				{
					ratio_t = (0.0 + v - offset0_t) / v;
				}
				picCellWidth += ratio_t;
				picWidth_t -= (int) (0.0 + v - offset0_t);
			}
			else
			{ // 剩余宽度不足一个单元格的宽度
				double ratio_r = 0.0;
				if (v != 0)
					ratio_r = (0.0 + picWidth_t) / v;
				picCellWidth += ratio_r;
				break;
			}
		}
		// 计算图片的实际高度
		int picHeight_t = picHeight * 15;
		for (int x = 0; x < 1234; x++)
		{
			int bc = (int) Math.floor(picBeginRow + x);
			// 得到单元格的高度
			int v = 25;// writableSheet.getRow(bc).getHeight();
			double offset0_r = 0.0;
			if (0 == x)
				offset0_r = (picBeginRow - bc) * v;
			if (0.0 + offset0_r + picHeight_t > v)
			{
				// 剩余高度超过一个单元格的高度
				double ratio_q = 1.0;
				if (0 == x)
					ratio_q = (0.0 + v - offset0_r) / v;
				picCellHeight += ratio_q;
				picHeight_t -= (int) (0.0 + v - offset0_r);
			}
			else
			{// 剩余高度不足一个单元格的高度
				double ratio_m = 0.0;
				if (v != 0)
					ratio_m = (0.0 + picHeight_t) / v;
				picCellHeight += ratio_m;
				break;
			}
		}
		
		// 把图片插入到sheet
		ClientAnchor anchor = getClientAnchor(picBeginCol, picBeginRow, picCellWidth, picCellHeight, startRow, endRow,
				startColmum, endColmun, writableSheet);
		anchor.setAnchorType(ClientAnchor.DONT_MOVE_AND_RESIZE);
		
		// 创建画布
		Drawing drawing = writableSheet.createDrawingPatriarch();
		
		drawing.createPicture(anchor, workbook.addPicture(getImageByte(picImage), XSSFWorkbook.PICTURE_TYPE_PNG));
	}
	
	/**
	 * 获取anchor对象，主要用于设置图片的属性
	 * 
	 * @param picBeginCol
	 *            图片高度
	 * @param picBeginRow
	 * @param picCellWidth
	 * @param picCellHeight
	 * @param startRow
	 * @param endRow
	 * @param startColmum
	 * @param endColmun
	 * @param writableSheet
	 * @return
	 */
	public static ClientAnchor getClientAnchor(double picBeginCol, double picBeginRow, double picCellWidth,
			double picCellHeight, int startRow, int endRow, int startColmum, int endColmun, Sheet writableSheet)
	{
		if (writableSheet instanceof HSSFSheet)
		{
			return new HSSFClientAnchor((int) picBeginCol, (int) picBeginRow, (int) picCellWidth, (int) picCellHeight,
					(short) startRow, endRow, (short) startColmum, endColmun);
		}
		else if (writableSheet instanceof XSSFSheet)
		{
			return new XSSFClientAnchor((int) picBeginCol, (int) picBeginRow, (int) picCellWidth, (int) picCellHeight,
					startRow, endRow, startColmum, endColmun);
		}
		
		return null;
	}
	
	/**
	 * 获取图片字节数
	 * <p>
	 * 
	 * @param picImage
	 *            计算读取对象
	 * @return byte[]
	 */
	private static byte[] getImageByte(BufferedImage picImage)
	{
		try
		{
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ImageIO.write(picImage, PNG, bout);
			return bout.toByteArray();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
		}
		return null;
	}
	
	/**
	 * 执行单元格公式
	 * 
	 * @param cell
	 *            单元格
	 * @param workbook
	 *            工作薄对象
	 * @return Object 单元格值
	 */
	private Object formulaGetValue(Cell cell, Workbook workbook)
	{
		FormulaEvaluator eval = null;
		
		if (workbook instanceof HSSFWorkbook)
		{
			eval = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
		}
		
		else if (workbook instanceof XSSFWorkbook)
		{
			eval = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
		}
		
		Object objValue = null;
		if (null != eval)
		{
			// evaluateInCell(Cell cell)
			// 方法是计算公式，并将原公式替换为计算结果，
			// 也就是说该单元格的类型不在是Cell.CELL_TYPE_FORMULA而是Cell.CELL_TYPE_NUMBERIC
			objValue = eval.evaluateInCell(cell).getNumericCellValue();
			
			// evaluateFormulaCell(Cell cell)方法，计算公式保存结果，但不改变公式
			// objValue = eval.evaluateFormulaCell(cell);
		}
		
		return objValue == null ? cell.getCellFormula() : objValue;
	}
	
	/*
	 * public static void main(String[] args) { File file = new
	 * File("F:\\export.xls");
	 * 
	 * List<ProductModel> productList = new ArrayList<ProductModel>();
	 * 
	 * Vector<String> productsVector = new Vector<String>();
	 * 
	 * Field[] fields = ProductModel.class.getDeclaredFields();
	 * 
	 * if (null != fields && fields.length > 0) { for (int i = 0; i <
	 * fields.length; i++) { productsVector.add(fields[i].getName()); } }
	 * 
	 * FileExcelUtils<ProductModel> fileBean =
	 * FileExcelUtils.parseData(productsVector, FileExcelUtils.EXCEL_TYPE,
	 * ProductModel.class, file); productList = fileBean.fileDataList();
	 * 
	 * System.err.println(productList); }
	 */
	
	// public static void main(String[] args) throws IOException
	// {
	// List datalist = new ArrayList();
	//
	// InputStreamReader isr = new InputStreamReader(new FileInputStream(new
	// File(
	// "F:\\ExportOrderList201405151819.csv")), "utf-8");
	// // BufferedReader read = new BufferedReader(isr);
	// BufferedReader filebuff = new BufferedReader(isr);
	// String line;
	// int countNum = 0;
	// while ((line = filebuff.readLine()) != null)
	// {
	// if (countNum > 0)
	// {// 跳过第一行
	// if (line.trim().length() > 0)
	// {// 读取的行长度为空0
	// String[] perlines = line.split(REGEX_SPLIT);
	// // if (set.size() <= perlines.length)
	// // {
	// // Object objClazz = cls.newInstance();
	// // for (int i = 0; i < set.size(); i++)
	// // {
	// // String fieldName = set.get(i);
	// // Field field = FieldUtils.getDeclaredField(cls,
	// // fieldName);
	// // field.setAccessible(true);// 至为可访问
	// // field.set(objClazz,
	// // FieldUtils.findFieldValue(perlines[i], field.getType()));
	// // }
	// // //datalist.add((T) objClazz);
	// // }
	//
	// System.err.println(ArrayUtils.toString(perlines));
	//
	// }
	// }
	// countNum++;
	// }
	// isr.close();
	// filebuff.close();
	//
	// // return datalist;
	//
	// }
}
