package com.utils.excel.template;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.collections.CollectionUtils;

import com.utils.excel.FieldUtil;

public class GroupList
{
	private static String splitChar = "_rel_";

	public static <T> List<RowDefine> group(List<T> data,
			Vector<String> displayProperties, Vector<String> groupProperties,
			Class<T> cls) throws Exception
	{
		String firstGroup = "";
		
		if (groupProperties != null && groupProperties.size()>1)
		{
			firstGroup = groupProperties.get(0);
		}
		List<RowDefine> rowList = new LinkedList<RowDefine>();
		Map<String, Field> fieldsMap = FieldUtil.getFields(cls);
		boolean notNeedGroup = (groupProperties == null);
		int rowIndex = 0;// 记录的位置
		List<Step> rowRecord = new ArrayList<Step>();

		for (T t : data)
		{
			RowDefine rowDefine = new RowDefine();
			// 记录字段的位置
			int cellIndex = 1;

			int lastGroupCount = 1;
			for (String display : displayProperties)
			{
				
				// 反转
				Field mfield = fieldsMap.get(display);
				mfield.setAccessible(true);
				Object objv = mfield.get(t);

				String value = objectToString(objv, mfield);
				boolean foundGroup = !notNeedGroup;

				if (!notNeedGroup)
				{
					foundGroup = group(display, groupProperties);
				}

				CellDefine cellDefine = new CellDefine(display, value);
				int colRow = 1;
				cellDefine.setColRow(colRow);

				/******
				 * 查找对应的列，并统计数量
				 */
			/*	 System.out.println("========="+display);
				 System.out.println("========="+value);*/

				// 判断是不是合并列
				if (foundGroup)
				{
					// 用于获取同一字段下的最新索引
					Step perStep = Step.getSameNewStep(display, rowRecord);
					int index = perStep != null ? perStep.getEnd() : 0;

					String rel = askRel(display, groupProperties);

					if (rel == null)
					{
						// 求出该次合并的列数
						colRow = rowSize(value, data, mfield, index,
								lastGroupCount, firstGroup,display);// 值，数据list,字段，索引，上一级的跨行数,一级分类的名字，当前分类的名字
					} else
					{
						Field zfield = fieldsMap.get(rel);
						zfield.setAccessible(true);
						Object ztmp = zfield.get(t);
						String zV = objectToString(ztmp, zfield);
						colRow = rowSize(zV, data, zfield, index,
								lastGroupCount, firstGroup,display);
					}

					// 此处可能后面覆盖前面的问题
					// 起始行号，终止行号， 起始列号，终止列号
					rowRecord.add(new Step(display, rowIndex - 1 < 0 ? 0
							: rowIndex - 1, (index + colRow), cellIndex,
							cellIndex));
					cellDefine.setColRow(colRow);
					lastGroupCount = colRow;
				}
				
				rowDefine.addCell(cellDefine);

				cellIndex++;
			}
			rowList.add(rowDefine);

			rowIndex++;
		}
		return rowList;
	}

	private static String objectToString(Object value, Field mfield)
	{
		if (value == null)
		{
			value = "";
		} else if (mfield.getType() == Date.class)
		{
			value = dateValue(value);
		}
		return value.toString();
	}

	// 格式化日期处理
	private static String dateValue(Object value)
	{
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat DATE_FORMAT_SHORT = new SimpleDateFormat("yyyy-MM-dd");
		String datestr = String.valueOf(value);
		Date date = (Date) value;
		try
		{
			if (datestr == null || datestr.equals("null")
					|| datestr.trim().equals(""))
			{
				return "";
			} else if (datestr.length() <= 10)
			{
				return DATE_FORMAT_SHORT.format(date);
			} else
			{
				return DATE_FORMAT.format(date);
			}

		} catch (Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}

	private static boolean group(String property, Vector<String> groupProperties)
	{
		boolean foundGroup = false;
		for (String groups : groupProperties)
		{
			String perGroup = filterProperty(groups, true);
			if (property.equals(perGroup))
			{
				foundGroup = true;
				break;
			}
		}
		return foundGroup;
	}

	private static String askRel(String property, Vector<String> groupProperties)
	{
		String rel = null;
		for (String groups : groupProperties)
		{
			if (groups.contains(splitChar))
			{
				String relGroup = filterProperty(groups, false);
				String realGroup = filterProperty(groups, true);
				if (property.equals(realGroup))
				{
					rel = relGroup;
					break;
				}
			}

		}
		return rel;
	}

	private static String filterProperty(String groups, boolean first)
	{
		String perGroup = groups;
		String[] groupArray = groups.split(splitChar);
		if (groupArray.length == 2)
		{
			if (first)
			{
				perGroup = groupArray[0];
			} else
			{
				perGroup = groupArray[1];
			}
		}
		return perGroup;
	}

	/*****
	 * 根据已排序好的列表进行统计，从取得当前值 开始，向下找，没有相同的说明没有跨度
	 * 
	 * @param value
	 *            原值
	 * @param data
	 *            已排序好的列表
	 * @param mfield
	 *            字段
	 * @param start
	 *            上一次合并的最后位置， 也是这一次合并的开始位置，数值对应查询出来的LIST值
	 * @return 大小
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private static <T> int rowSize(String value, List<T> data, Field mfield,
			int start, int lastGroupCount, String firstGroup,String display)
			throws IllegalArgumentException, IllegalAccessException
	{

		// 上一次合并的最后位置， 也是这一次合并的开始位置，数值必须对应查询出来的LIST值的对应索引值
		int size = 0;
		int index = 0;
		for (T t : data)
		{
			Object v = mfield.get(t);
			if (index >= start)
			{
				if (v != null)
				{
					String tmpV = v.toString();
					if (value.equals(tmpV))
					{
						size++;
					} else
					{
						break;
					}
				}
			}
			index++;
		}
		
		
    if(firstGroup!=display){
    	// 对比上一级的行数
		if (size >= lastGroupCount)
		{
			size = lastGroupCount;
		} else if (lastGroupCount == 1)
		{
			size = 1;
		}
    }
		return size;
	}
}
