package com.innshine.chart.service.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.base.service.component.BusinessCache;
import com.base.util.dwz.Page;
import com.base.util.persistence.SearchFilter;
import com.utils.ServletUtils;

public class SqlChangetool
{

	
	/**
	 * 功能:销售时间纵向分析求图例与横轴
	 * <p>
	 * 作者 杨荣忠 2014-9-25 上午10:28:07
	 * 
	 * @param suitTime
	 * @param timemapNow
	 * @return
	 * @throws ParseException
	 */
	public static Map<String, String[]> getxValue_typeValue(ShopTime shopTime,
			Map<String, String> timemapNow) throws ParseException
	{
		Map<String, String[]> map = new HashMap();
		String[] typeValue = null;// 图例
		String[] xValue = null;// 横轴
		String firstTime = timemapNow.get("beginTime");
		String endTime = timemapNow.get("endTime");
		String SuitselectTimeType = shopTime.getSelectTimeType();
		if (SuitselectTimeType.equals("M") || SuitselectTimeType.equals("R"))
		{
			if (DateUtil.RMDayCheck(firstTime, endTime))
			{
				endTime = DateUtil.getNewDay(45, firstTime);
			}
			xValue = DateUtil.xValueRM(firstTime, endTime);
			typeValue = DateUtil.getTypeValueRM(firstTime, endTime, shopTime
					.getCircle());
		} else if (SuitselectTimeType.equals("Z"))
		{
			xValue = DateUtil.xValueZ(shopTime.getQuarterfirstTime(), shopTime
					.getQuarterendTime());
			typeValue = DateUtil.getTypeValueZ(shopTime.getQuarterfirstTime(),
					shopTime.getQuarterendTime(), shopTime.getYear(), shopTime
							.getCircle());
		}

		else if (SuitselectTimeType.equals("Y"))
		{
			int Quarterfirst = 1;
			int Quarterendend = 4;
			xValue = DateUtil.xValueY(Quarterfirst, Quarterendend);
			typeValue = DateUtil.getTypeValueY(shopTime.getYear(), shopTime
					.getCircle());
		}

		map.put("xValue", xValue);
		map.put("typeValue", typeValue);
		return map;
		
		
	}

	
	/**
	 * 功能:增加时间过滤条件
	 * <p>作者 杨荣忠 2014-9-23 下午04:44:50
	 * @param param
	 * @param filterSet
	 * @return
	 * @throws ParseException
	 */
	public static Map<String, Object> addSQLTime(Map<String, Object> param,
			Set<SearchFilter> filterSet) throws ParseException
	{
		Map<String, String> mapTime = checkTime(filterSet);
		param = getParamTime(param, mapTime);
		return param;
	}

	/**
	 * 功能:增加平台ID
	 * <p>作者 杨荣忠 2014-9-23 下午04:21:21
	 * @param param
	 * @param platid
	 * @return
	 */
	public static Map<String, Object> paramPlateId(Map<String, Object> param,
			String platid)
	{
		if (!platid.equals("0"))
		{
			param.put("platid", platid);
		}
		return param;
	}

	/**
	 * 功能:增加组织id
	 * <p>作者 杨荣忠 2014-9-23 下午04:21:36
	 * @param param
	 * @param organizationId
	 * @return
	 */
	public static Map<String, Object> paramOrganizationId(
			Map<String, Object> param, Long organizationId)
	{
		if (organizationId != null)
		{
			param.put("organizationId", organizationId);
		}
		return param;
	}

	
	/**
	 * 设置查询参数列表，返回给前台页面
	 * <p>
	 * 
	 * @param request
	 */
	public static void setQueryParmas(HttpServletRequest request)
	{
		Map<String, Object> map = ServletUtils.getParametersStartingWith(
				request, null);

		if (null != map && map.size() > 0)
		{
			Iterator<Entry<String, Object>> it = map.entrySet().iterator();
			while (it.hasNext())
			{
				Entry<String, Object> entry = it.next();
				if (null != entry)
				{
					String key = entry.getKey();
					if (StringUtils.isNotBlank(key))
					{
						try
						{
							String tmpKey = key
									.lastIndexOf(SalesAnalyseConstants.POINT) != -1 ? key
									.replace(
											key
													.substring(
															key
																	.lastIndexOf(SalesAnalyseConstants.UNDER_LINE) + 1,
															key
																	.lastIndexOf(SalesAnalyseConstants.POINT) + 1),
											SalesAnalyseConstants.BLANK_CHARACTER)
									: key;
							request.setAttribute(tmpKey, entry.getValue());
						} catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	
	public static ShopTime check(ShopTime shopTime) throws ParseException
	{
		String begin = shopTime.getFirstTime();
		String end = shopTime.getEndTime();
		if (begin.trim().equals("") || end.trim().equals(""))
		{
			shopTime.setFirstTime(DateUtil.getNewDay(-30, DateUtil
					.nowtimeString()));
			shopTime.setEndTime(DateUtil.nowtimeString());
		}
		return shopTime;
	}



	@SuppressWarnings("unchecked")
	public static List getResult(BusinessCache businessCache, String cacheKey,
			String indexKey)
	{
		List list = (List) businessCache.get(cacheKey, indexKey);
		return list;
	}

	public static <T> List<T> getPageList(List<T> result, Page page)
	{
		List<T> list = null;
		int perPage = page.getNumPerPage();
		int pageNum = page.getPageNum();
		int begin = (pageNum - 1) * perPage;
		int end = begin + pageNum * perPage;

		for (int i = begin; i < result.size(); i++)
		{
			list.add(result.get(i));
		}

		return list;
	}

	public static Map<String, Object> paramTime_Plate_Org(
			Map<String, Object> param, String platid, Long organizationId,
			Set<SearchFilter> filterSet) throws ParseException
	{
		param = paramPlateId(param, platid);
		param = paramOrganizationId(param, organizationId);
		param = addSQLTime(param, filterSet);
		return param;
	}


	
	
	// 品类，系列，sku过滤
	public static Map<String, Object> paramSuitProduct(
			Map<String, Object> param, ShopTime shopTime)
	{
		String sku = shopTime.getSku();
		String category = shopTime.getCategory();
		String series = shopTime.getSeries();
		if (!sku.trim().equals(""))
		{
			param.put("sku", sku);
		}
		if (!category.trim().equals(""))
		{
			param.put("category", category);
		}
		if (!series.trim().equals(""))
		{
			param.put("series", series);
		}
		return param;
	}

	

	public static List<String> getMonthShanlyse(List timeList)
	{
		List<String> list = new ArrayList();
		for (int i = 0; i < timeList.size(); i++)
		{
			String month = timeList.get(i).toString().substring(5, 7);
			list.add(month);
		}
		return list;
	}

	

	
	public static int checkMarked(List<Object[]> result, int index)
	{
		int flag = 0;
		try
		{
			if (result.size() > 0)
			{
				for (int i = 0; i < result.size(); i++)
				{
					Object[] resultLast = result.get(i);
					if (Integer.parseInt(resultLast[index] + "") >= 1)
					{
						flag = 1;
						break;
					}
				}
			}
		} catch (Exception e)
		{
			flag = 1;
		}
		return flag;
	}

	public static double doNum(Object object[], int index)
	{
		double num = 0;
		try
		{
			num = object[index] != null ? Double
					.parseDouble(object[index] + "") : 0;
		} catch (Exception e)
		{
			num = 0;
		}
		return num;

	}

	public static double doNum(String value)
	{
		double num = 0;
		try
		{
			num = value != null ? Double.parseDouble(value + "") : 0;
		} catch (Exception e)
		{
			num = 0;
		}
		return num;

	}

	public static Object[] preSum(Object[] object, String[] preSumResult)
	{
		// 同比汇总：销售数量，金额，折扣
		String prenumSum = disDiscount(object[0], preSumResult[0]);
		String presalesMoney = disDiscount(object[1], preSumResult[1]);
		String predisCount = disDiscount(object[3], preSumResult[2]);
		object = concat(object, new Object[] { prenumSum, presalesMoney,
				predisCount });
		return object;
	}

	public static String[] preSumResult(List<Object[]> result)
	{
		// 同比汇总：销售数量，金额，折扣
		int size = result.size() - 1;
		String prenumSumAll = result.get(size)[0] != null ? result.get(size)[0]
				+ "" : null;
		String presalesMoneyAll = result.get(size)[1] != null ? result
				.get(size)[1]
				+ "" : null;
		String predisCountAll = result.get(size)[3] != null ? result.get(size)[3]
				+ ""
				: null;
		String[] objectResult = new String[] { prenumSumAll, presalesMoneyAll,
				predisCountAll };
		return objectResult;
	}

	public static int doNumInt(String value)
	{
		int num = 0;
		try
		{
			num = value != null ? Integer.parseInt(value + "") : 0;
		} catch (Exception e)
		{
			num = 0;
		}
		return num;

	}

	public static Object[] shopTimeAnalyseObject(Object[] object,
			Object[] preobject)
	{
		// 同比：销售数量，金额，折扣
		String prenumSum = disDiscount(object[0], preobject[0]);
		String presalesMoney = disDiscount(object[1], preobject[1]);
		String predisCount = disDiscount(object[3], preobject[3]);
		object = concat(object, new Object[] { prenumSum, presalesMoney,
				predisCount });
		return object;
	}

	public static String disDiscount(Object a, Object b)
	{
		String result = "";
		try
		{
			Double preResult = Double.parseDouble(b.toString());
			Double nowResult = Double.parseDouble(a.toString());
			if (preResult != 0)
			{
				double num = ((nowResult / preResult) - 1) * 100;
				result = DateUtil.changeMoneyType(num);
			} else
			{
				result = null;
			}
		} catch (Exception e)
		{
			result = null;
		}
		return result;
	}

	// 匹配销售套装分析分类所选列
	public static String[] getGroupZAll(String[] groupSelect)
	{
		String GroupName[] = Constant.GROUP_Z_NAME;
		List<String> listGroup = new ArrayList();
		for (int j = 0; j < GroupName.length; j++)
		{
			if (checkGroupOne(groupSelect, GroupName[j]))
				listGroup.add(GroupName[j]);
		}
		return concat(groupSelect, (String[]) listGroup
				.toArray(new String[listGroup.size()]));
	}

	// 匹配分类所选列
	public static String[] getGroupAll(String[] groupSelect, int shopType)
	{
		String GroupName[] = null;
		if (shopType == 1)
		{
			GroupName = Constant.GROUP_NAME;
		} else if (shopType == 2)
		{
			GroupName = Constant.GROUP_Z_NAME;
		}
		List<String> listGroup = new ArrayList();
		for (int j = 0; j < GroupName.length; j++)
		{
			if (checkGroupOne(groupSelect, GroupName[j]))
				listGroup.add(GroupName[j]);
		}
		return concat(groupSelect, (String[]) listGroup
				.toArray(new String[listGroup.size()]));
	}

	// shopType=1:销售分析，shopType=2,套装销售分析(根据所选顺序拼凑)
	public static String getGroupLastOneField(String[] groupSelectAll,
			int shopType)
	{
		String str = "";
		String GroupName[] = null;
		String GroupNameSQL[] = null;
		if (shopType == 1)
		{
			GroupNameSQL = Constant.GROUP_NAME_SQL;
			GroupName = Constant.GROUP_NAME;
		} else if (shopType == 2)
		{
			GroupNameSQL = Constant.GROUP_Z_NAME_SQL;
			GroupName = Constant.GROUP_Z_NAME;
		}
		for (int j = 0; j < groupSelectAll.length; j++)
		{
			int index = checkGroupOneIndex(GroupName, groupSelectAll[j]);
			if (index != -1)
			{
				if (j != groupSelectAll.length - 1)
					str += GroupNameSQL[index] + ", ";
				else
				{
					str += GroupNameSQL[index];
				}
			}
		}
		return str;
	}

	// 判断有无包含对应值，返回false
	public static boolean checkGroupOne(String groupSelect[], String str)
	{
		boolean flag = true;
		for (int j = 0; j < groupSelect.length; j++)
		{
			if (groupSelect[j].trim().equals(str.trim()))
			{
				flag = false;
				break;
			}
		}
		return flag;
	}

	// 套装SQL转条件语句
	public static String groupListOneZValueStr(Object[] resultList,
			String[] groupSelectAll) throws Exception
	{
		String str = "";
		String GroupName = " and ";
		for (int i = 0; i < groupSelectAll.length; i++)
		{
			String value = "";
			if (resultList[i] != null)
			{
				value = "='" + resultList[i] + "'";
			} else
			{
				value = " IS NULL";
			}
			GroupName = groupSelectAll[i];
			if (GroupName.trim().equals("平台"))
			{
				str += "s.platform_id" + value;
			} else if (GroupName.trim().equals("品类"))
			{
				str += "p.suit_category" + value;
			} else if (GroupName.trim().equals("系列"))
			{
				str += "p.suit_series" + value;
			} else if (GroupName.trim().equals("功效"))
			{
				str += "p.suit_efficacy" + value;
			}
			if (i != groupSelectAll.length - 1)
			{
				str += " and ";
			}
		}
		return str;
	}

	public static List<Integer> getGroupConditionIndex(List<String> condition,
			List<String> list, List<Integer> IndexList)
	{
		int numIndex = -1;
		for (int m = 0; m < list.size(); m++)
		{
			numIndex = SqlChangetool.checkGroupOne(condition, list.get(m));
			if (numIndex != -1 && SqlChangetool.checkList(IndexList, numIndex))
			{
				IndexList.add(numIndex);
			}
		}

		return IndexList;
	}

	// 判断有无包含对应值
	public static int checkGroupOne(List<String> condition, String str)
	{
		int index = -1;
		for (int j = 0; j < condition.size(); j++)
		{
			if (condition.get(j).equals(str))
			{
				index = j;
				break;
			}
		}
		return index;
	}

	public static String getShopOptionsName(String name)
	{
		name = name.replace("gplate", "平台");
		name = name.replace("gcategory", "品类");
		name = name.replace("gseries", "系列");
		name = name.replace("gefficacy", "功效");
		name = name.replace("plate", "平台");
		name = name.replace("category", "品类");
		name = name.replace("efficacy", "系列");
		name = name.replace("distriType", "分销类型");
		return name;
	}

	// 判断有无包含对应值，返回索引
	public static int checkGroupOneIndex(String groupSelect[], String str)
	{
		int index = -1;
		for (int j = 0; j < groupSelect.length; j++)
		{
			if (groupSelect[j].trim().equals(str.trim()))
			{
				index = j;
				break;
			}
		}
		return index;
	}

	// 获取销售分析(包括套装)动态列(全)（从大到小）
	public static String[] getShopAnlyseSelectGroupList(String ShopOptions,
			String OrderList, String ShopOptionsName, int shopType)
			throws Exception
	{
		String[] groupList = null;

		if (SqlChangetool.checkHaveSelect(ShopOptions))
		{
			// 获取所选排序的对应大小号
			int[] numList = OrderList(ChangeOrder(OrderList.split(",")),
					ChangeGroup(ShopOptions.split(","), shopType));
			int[] numListName = Arrays.copyOf(numList, numList.length);

			groupList = groupOrderList(ShopOptionsName.split(","), numListName);
		}
		return groupList;
	}

	/**
	 *销售分析(销售表字段+销售时间范围+界面所有查询条件)（默认）
	 */
	public static Map<String, Object> getMOShopAnlyseResultParam(
			Map<String, Object> param, Set<SearchFilter> filterSet,
			String platid) throws Exception
	{
		Map<String, String> mapTime = checkTime(filterSet);
		param = paramPlateId(param, platid);
		param = getParamTime(param, mapTime);
		param.put("condition", getValueSql(filterSet, "b"));
		return param;
	}

	public static Map<String, Object> getParamTime(Map<String, Object> param,
			Map<String, String> mapTime) throws ParseException
	{
		// 如果只有一个时间范围，则
		String beginTime = mapTime.get("beginTime");
		String endTime = mapTime.get("endTime");
		if (!(beginTime == null && endTime == null))
		{
			if (beginTime != null && endTime == null)
			{
				endTime = DateUtil.nextMonth(beginTime);
			} else if (beginTime == null && endTime != null)
			{
				beginTime = DateUtil.lastMonth(endTime);
			}
		} else
		{
			// 默认查询范围当前月一號至目前时间
			beginTime = DateUtil.nowmonthfirst();
			endTime = DateUtil.nowtimeString();
		}
		try
		{
			param.put("allday", DateUtil.getXcDay(beginTime, endTime));
		} catch (Exception e)
		{
			param.put("allday", 30);
		}
		param.put("beginTime", beginTime);
		param.put("endTime", endTime);
		param.put("allday", DateUtil.getDateLate(beginTime, endTime));
		return param;
	}

	public static Map<String, Object> TimeTypeSql(Map<String, Object> param,
			String timeType)
	{
		if (timeType.equals("Y"))
		{
			param.put("condition",
					"FLOOR((DATE_FORMAT(a.sales_time, '%m')+2)/3)");
		} else if (timeType.equals("Z"))
		{
			param.put("condition", "DATE_FORMAT(a.sales_time,'%m')");
		} else
		{
			param.put("condition", "DATE_FORMAT(a.sales_time,'%Y%m%d')");
		}
		return param;
	}

	// 求一个数组的索引范围内的行总数(以最后一列为主)
	public static int GetListRowSum(int begin, int end,
			List<String[]> groupArray)
	{
		int max = Integer.parseInt(groupArray.get(groupArray.size() - 1)[2]);// 求记录最后一条的行索引
		if (end > max)
		{
			end = max;
		}
		int num = 0;
		List<String[]> Array = getSum(begin, end, groupArray);
		if (Array.size() > 0)
		{
			for (int i = 0; i < Array.size(); i++)
			{
				String arrayOne[] = Array.get(i);
				num += Integer.parseInt(arrayOne[1]);
			}
		}
		return num;
	}

	public static String groupListOneValueStr(Object[] resultList,
			String[] groupSelectAll) throws Exception
	{
		String str = "";
		String GroupName = " and ";
		for (int i = 0; i < groupSelectAll.length; i++)
		{
			String value = "";
			if (resultList[i] != null)
			{
				value = "='" + resultList[i] + "'";
			} else
			{
				value = " IS NULL";
			}
			GroupName = groupSelectAll[i];
			if (GroupName.trim().equals("平台"))
			{
				str += "t.platform_id" + value;
			} else if (GroupName.trim().equals("品类"))
			{
				str += "j.category" + value;
			} else if (GroupName.trim().equals("系列"))
			{
				str += "j.series" + value;
			} else if (GroupName.trim().equals("分销类型"))
			{
				str += "j.distri_type" + value;
			}
			if (i != groupSelectAll.length - 1)
			{
				str += " and ";
			}
		}
		return str;
	}

	

	public static boolean checkGroupOne(List<String[]> groupArrayLast, int num)
	{
		boolean flag = false;
		for (int j = 0; j < groupArrayLast.size(); j++)
		{
			String arrayOne[] = groupArrayLast.get(j);
			if (num == Integer.parseInt(arrayOne[2]))
			{
				flag = true;
				break;
			}
		}
		return flag;
	}

	public static List<String[]> getSum(int begin, int end,
			List<String[]> groupArray)
	{
		List<String[]> Array = new ArrayList();
		for (int i = 0; i < groupArray.size(); i++)
		{
			int num = Integer.parseInt(groupArray.get(i)[2]);
			if (begin <= num && num <= end)
				Array.add(groupArray.get(i));
		}
		return Array;
	}

	// 求group查询条件集拼接
	public static List<String> getGroupCondition(List<List> condition)
			throws Exception
	{
		List<String> con = condition.get(0);
		for (int i = 0; i < condition.size(); i++)
		{
			if (i + 1 < condition.size())
			{
				con = foreachTool(con, condition.get(i + 1), new ArrayList());
			}
		}
		return con;
	}

	public static String groupListOneZName(String GroupName) throws Exception
	{
		String str = "";
		if (GroupName.trim().equals("平台"))
		{
			str = "s.platform_id";
		} else if (GroupName.trim().equals("品类"))
		{
			str = "p.suit_category";
		} else if (GroupName.trim().equals("系列"))
		{
			str = "p.suit_series";
		} else if (GroupName.trim().equals("功效"))
		{
			str = "p.suit_efficacy";
		}
		return str;
	}

	public static List<String> foreachTool(List first, List second,
			List<String> con)
	{
		for (int i = 0; i < first.size(); i++)
		{
			for (int j = 0; j < second.size(); j++)
			{
				con.add(first.get(i) + " and " + second.get(j));
			}
		}
		return con;
	}

	public static List chagneList(List list, String str, boolean flag)
	{
		List array = new ArrayList();
		for (int i = 0; i < list.size(); i++)
		{
			if (list.get(i) != null)
			{
				array.add(str + "='" + list.get(i) + "'");
			} else
			{
				if (flag == true)
					array.add(str + " IS NULL");
				else
				{
					continue;
				}
			}
		}
		return array;
	}

	public static String groupListOneName(String GroupName) throws Exception
	{
		String str = "";
		if (GroupName.trim().equals("平台"))
		{
			str = "t.platform_id";
		} else if (GroupName.trim().equals("品类"))
		{
			str = "j.category";
		} else if (GroupName.trim().equals("系列"))
		{
			str = "j.series";
		} else if (GroupName.trim().equals("分销类型"))
		{
			str = "j.distri_type";
		}
		return str;
	}

	public static boolean checkHaveSelect(String ShopOptions)
	{
		boolean flag = false;
		if (!ShopOptions.split(",")[0].equals(0 + ""))
		{
			flag = true;
		}
		return flag;
	}

	// 图表时间查询范围 //R:日 Z:季 M:月 Y：年
	public static Map<String, String> getMapTime(ShopTime shopTime, int circle)
			throws Exception
	{

		Map<String, String> timemap = Collections
				.synchronizedMap(new LinkedHashMap());

		String timeType = shopTime.getSelectTimeType();

		int year = shopTime.getYear() - circle;

		if (timeType.equals("M"))
		{
			timemap = DateUtil.getfulletimeMonth(shopTime.getMonthBegin(),
					shopTime.getMonthEnd(), year);
		}
		if (timeType.equals("Z"))
		{
			timemap = DateUtil.getfulletimeJidu(shopTime.getQuarterfirstTime(),
					shopTime.getQuarterendTime(), year);
		}
		if (timeType.equals("R"))
		{
			timemap = DateUtil.getfulletimeDay(shopTime.getFirstTime(),
					shopTime.getEndTime(), circle);
		}
		if (timeType.equals("Y"))
		{
			timemap = DateUtil.getfulletimeYear(year);
		}
		return timemap;
	}

	// 求对比查询的时间总范围 //R:日 Z:季 M:月 Y：年
	public static Map<String, Object> getMapTimeAll(Map<String, Object> param,
			ShopTime shopTime) throws Exception
	{

		Map<String, String> timemapEnd = getMapTime(shopTime, 0);
		Map<String, String> timemapbegin = getMapTime(shopTime, shopTime
				.getCircle());

		param.put("beginTime", timemapbegin.get("beginTime"));
		param.put("endTime", timemapEnd.get("endTime"));

		return param;
	}

	public static String[] concat(String[] a, String[] b)
	{
		String[] c = new String[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}

	public static Object[] concat(Object[] a, Object[] b)
	{
		Object[] c = new Object[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}

	// 处理过来的顺序集，非数字顺序转为0
	public static List ChangeOrder(String[] orderList)
	{
		List list = new ArrayList();
		for (int i = 0; i < orderList.length; i++)
		{
			if (!isNumeric(orderList[i]))
			{
				orderList[i] = 0 + "";
			}
			list.add(orderList[i]);
		}
		return list;
	}

	// 获取所选排序的索引号type=1:销售分析，type=2,套装销售分析
	public static List ChangeGroup(String[] group, int type)
	{
		List list = new ArrayList();
		String[] ArrayList = null;
		if (type == 1)
		{
			ArrayList = Arrays.copyOf(Constant.GROUP_NAME_CHECK,
					Constant.GROUP_NAME_CHECK.length);
		}
		if (type == 2)
		{
			ArrayList = Arrays.copyOf(Constant.GROUP_Z_NAME_CHECK,
					Constant.GROUP_Z_NAME_CHECK.length);
		}
		for (int i = 0; i < group.length; i++)
		{
			for (int j = 0; j < ArrayList.length; j++)
			{
				if (ArrayList[j].equals(group[i]))
				{
					list.add(j);
				}
			}
		}
		return list;
	}

	public static List<Integer> getNum(int begin, int end)
	{
		List<Integer> list = new ArrayList();
		for (int i = begin; i <= end; i++)
		{
			list.add(i);
		}
		return list;
	}

	// 获取所选排序的对应索引号
	public static int[] OrderList(List ChangeOrder, List ChangeGroup)
	{
		int[] num = new int[ChangeGroup.size()];
		for (int i = 0; i < ChangeGroup.size(); i++)
		{
			int a = Integer.parseInt(ChangeGroup.get(i).toString());
			int b = Integer.parseInt(ChangeOrder.get(a).toString());
			num[i] = b;
		}
		return num;
	}

	// 给动态列按从大到小排的优先顺序排序
	public static String[] groupOrderList(String[] order, int[] numList)
	{
		int[] numListArray = Arrays.copyOf(numList, numList.length);
		String[] orderArray = Arrays.copyOf(order, order.length);
		List<Integer> haveIndexList = new ArrayList();// 建一个list专门存储已标志的索引，防止相同索引取同一个地方对应值
		int indexArray[] = sortdesc(numList);// 从大到小排
		if (indexArray.length != 0)
		{
			for (int i = 0; i < indexArray.length; i++)
			{
				int index = getIndex(indexArray[i], numListArray, haveIndexList);
				order[i] = orderArray[index];
				haveIndexList.add(index);
			}
		}
		return order;
	}

	public static int getIndex(int num, int value[], List haveList)
	{
		int index = 0;
		for (int j = 0; j < value.length; j++)
		{
			if (num == value[j] && checkList(haveList, j))
			{
				index = j;
				break;
			}
		}
		return index;
	}

	public static boolean checkList(List<Integer> haveList, int num)
	{
		boolean flag = true;
		if (haveList.size() > 0)
		{
			for (int i = 0; i < haveList.size(); i++)
			{
				if (haveList.get(i) == num)
				{
					flag = false;
				}
			}
		}
		return flag;
	}

	public static boolean checkListSpace(List<String> list, int num)
	{
		boolean flag = true;
		if (list.size() != 0)
		{
			Collections.sort(list, new Comparator()
			{
				public int compare(Object _o1, Object _o2)
				{
					return chineseCompare(_o1, _o2);
				}
			});
			int max = Integer.parseInt(list.get(list.size() - 1));
			if (num <= max)
				flag = false;
		}
		return flag;
	}

	public static int chineseCompare(Object arg0, Object arg1)
	{
		return Integer.parseInt(arg0 + "") - Integer.parseInt(arg1 + "");
		// return 0;
	}

	// 对数组从大到小排序
	private static int[] sortdesc(int[] a)
	{
		for (int i = 0; i < a.length - 1; i++)
		{
			for (int j = i + 1; j < a.length; j++)
			{
				if (a[j] > a[i])
				{
					int t = a[j];
					a[j] = a[i];
					a[i] = t;
				}
			}
		}
		return a;
	}

	// 对数组从小到大排序
	public static int[] sort(int value[])
	{
		int[] a = value;
		int i, j, k = 0;
		int temp;
		int len = value.length;
		for (i = 0; i < len - 1; i++)
		{
			k = i;
			for (j = i + 1; j < len; j++)
				if (a[j] < a[k])
					k = j;
			if (k > i)
			{
				temp = a[i];
				a[i] = a[k];
				a[k] = temp;
			}
		}
		return a;
	}

	// 判断是不是数字
	public static boolean isNumeric(String str)
	{
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();

	}

	// sql:针对基础表前端过来的参数 productinfo a
	public static String getValueSql(Set<SearchFilter> filterSet,
			String tableAsName)
	{
		String hql = "";
		if (filterSet.size() > 0)
		{
			hql = " and ";
			int i = 0;
			for (SearchFilter filter : filterSet)
			{
				i = i + 1;
				if (!filter.getFieldName().equals("marketTime"))
				{
					String FieldName = changeWord(filter.getFieldName());
					String Operator = filter.getOperator().toString();
					String Value = filter.getValue().toString();
					hql = hql
							+ tableAsName
							+ "."
							+ FieldName
							+ " "
							+ conditionChange(Operator)
							+ " "
							+ (conditionChange(Operator).equals("like") ? "'%"
									: "");

					if (isNumeric(Value)
							|| conditionChange(Operator).equals("like"))
					{
						hql += "" + Value + "";
					} else
					{
						hql += "'" + Value + "'";
					}

					hql += (conditionChange(Operator).equals("like") ? "%'"
							: "")
							+ " and ";
				}
			}
		}
		if (hql.length() > 5)
			hql = hql.substring(0, hql.length() - 4);
		else
			hql = "";
		return hql;
	}

	// 处理前端过来的参数
	public static String conditionChange(String condition)
	{
		if (condition.equals("EQ"))
			return "=";
		else if (condition.equals("LIKE"))
			return "like";
		else if (condition.equals("GT"))
			return ">";
		else if (condition.equals("LT"))
			return "<";
		else if (condition.equals("GTE"))
			return ">=";
		else if (condition.equals("LTE"))
			return "<=";
		else if (condition.equals("IN"))
			return "in";
		else
			return "=";
	}

	// top20,2top2Z，3月报，4销售分析Z（默认） 5销售分析（默认） 6销售分析Z（分类） 7销售分析（分类）
	public static String getExcelName(int functionId)
	{
		String str = "";
		if (functionId == 1)
			str = "top20报表";
		else if (functionId == 2)
			str = "top2套装报表";
		else if (functionId == 3)
			str = "月报报表";
		else if (functionId == 4)
			str = "套装销售分析报表";
		else if (functionId == 5)
			str = "销售分析报表";
		else if (functionId == 6)
			str = "销售分析套装分类报表";
		else if (functionId == 7)
			str = "销售分析分类报表";
		return str;
	}

	// 获取前端参数时间范围
	public static Map<String, String> checkTime(Set<SearchFilter> filterSet)
	{
		Map<String, String> map = Collections
				.synchronizedMap(new LinkedHashMap());
		for (SearchFilter filter : filterSet)
		{
			String FieldName = filter.getFieldName();
			String Operator = filter.getOperator().toString();
			if (FieldName.equals("marketTime") && Operator.equals("LTE"))
			{
				map.put("endTime", filter.getValue().toString());
			}
			if (FieldName.equals("marketTime") && Operator.equals("GTE"))
			{
				map.put("beginTime", filter.getValue().toString());
			}
		}
		return map;
	}

	public static List<String[]> DeleteNull(List<String[]> list)
	{
		List<String[]> arraylist = new ArrayList();
		for (int i = 0; i < list.size(); i++)
		{
			if (list.get(i) != null)
			{
				list.add(list.get(i));
			}
		}
		return list;
	}

	// 转成大数据库字段
	public static String changeWord(String name)
	{
		List<Character> indexlist = isAcronym(name);
		String text = name;
		for (int i = 0; i < indexlist.size(); i++)
		{
			String a = indexlist.get(i) + "";
			String b = String.valueOf(Character.toLowerCase(indexlist.get(i)));
			text = text.replace(a, "_" + b);
		}
		return text;
	}

	// 求大写字母
	public static List<Character> isAcronym(String word)
	{
		List indexlist = new ArrayList();
		int index = 0;
		for (int i = 0; i < word.length(); i++)
		{
			char c = word.charAt(i);
			if (!Character.isLowerCase(c))
			{
				indexlist.add(c);
			}
		}
		return indexlist;
	}

	public static Map<String, Object> setMapParam(Map<String, Object> map,
			ShopTime shopTime)
	{
		map.put("selectTimeType", shopTime.getSelectTimeType());
		map.put("year", shopTime.getYear());
		map.put("firstTime", shopTime.getFirstTime());
		map.put("endTime", shopTime.getEndTime());
		map.put("MonthBegin", shopTime.getMonthBegin());
		map.put("MonthEnd", shopTime.getMonthEnd());
		map.put("QuarterfirstTime", shopTime.getQuarterfirstTime());
		map.put("QuarterendTime", shopTime.getQuarterendTime());
		map.put("circle", shopTime.getCircle());
		map.put("platid", shopTime.getShopPlatid());
		map.put("numList", getNum(1, 12));
		map.put("sku", shopTime.getSku());
		map.put("category", shopTime.getCategory());
		map.put("series", shopTime.getSeries());
		return map;
	}

	// 测试main
	public static void main(String[] args) throws Exception
	{
		// System.out.println(SqlChangetool.isNumeric("555"));
		System.out.println(new Date());
	}

}
