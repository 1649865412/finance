package com.innshine.reportExport.reportInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;

import com.innshine.chart.entity.ImgPathPine;
import com.innshine.reportExport.Constants;
import com.innshine.reportExport.entity.CellMonthSummaryInfo;
import com.innshine.reportExport.entity.ConditionEntity;
import com.innshine.reportExport.entity.RowMonthSummaryInfo;
import com.innshine.reportExport.reportService.ChartServiceImpl;
import com.innshine.reportExport.util.Constant;
import com.innshine.reportExport.util.ImgTool;
import com.innshine.reportExport.util.ParamTool;
import com.innshine.reportExport.util.ReportTool;

/**
 * <code>ProportionOneItmeManyDimension.java 占比：单项目多维度</code>
 * <p>
 * <p>
 * Copyright 2015 All right reserved.
 * 
 * @author 杨荣忠 时间 2015-1-19 下午02:33:17
 * @version 1.0 </br>最后修改人 无
 * 
 *          占比： 1）单项目多维度： 饼块：多维度 标题：时间+维度+图表+占比 图例：多维分类 支持: 饼图
 *          特点：只能一个项目，至少一个维度，占那个时间段的营总收入
 */
public class ProportionOneItmeManyDimension extends Abstrategy
{

	public static String ALL_COST = "主营业务收入";
	public static String OTHER_ALL_COST = "其它业务收入";
	public static String TITLE = "(各维度占营总收入)";
	public static int DO_TYPE_BUSSINESS=1;   // 占比的营总收入
	public static int DO_TYPE_NOT_BUSSINESS=2;  //非占比的营总收入

	@Override
	public String reportImg(InterfaceExportImpl interfaceExportImpl)
	{
		System.out.println("占比：单项目多维度策略");

		ConditionEntity conditionEntity = interfaceExportImpl
				.getConditionEntity();
		
		int checkProportionAll = conditionEntity.getCheckProportionAll();
		
		HttpServletRequest request = interfaceExportImpl.getRequest();
		
		ChartServiceImpl chartServiceImpl = new ChartServiceImpl();

		String[] keys = null; // 饼状图图例
		double[] data = null; // 饼状图数据
		String name = "";

		// 判断是不是求占比的营总收入，1为是，其它不是
		if (checkProportionAll == Constants.BUSINESS_INCOME_DEFAULT_TYPE)
		{
			keys = ImgTool.getZhabiDetach(conditionEntity); // 获取除去占比经营数据总收的其它图例
			try
			{  
				data =  getData(conditionEntity,DO_TYPE_BUSSINESS);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			 //图表名字
			 name = ImgTool.getReportName(conditionEntity, conditionEntity
					.getItem())+TITLE;
		} else
		{
			keys = ImgTool.getDimensionAll(conditionEntity);
			try
			{
				data = getData(conditionEntity,DO_TYPE_NOT_BUSSINESS);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			 //图表名字
			 name = ImgTool.getReportName(conditionEntity, conditionEntity
					.getItem());
		}
		ImgPathPine imgPathPine = new ImgPathPine(data, keys, name);
		String imgPath = chartServiceImpl.getImgPathPie(imgPathPine);
		return chartServiceImpl.getImageHtml(imgPath, request);
	}

	
	
	
	/**
	 * 功能:获取数据集(占营收入的一到四级，与非占营收入的一到四级)
	 * <p>作者 杨荣忠 2015-1-26 下午03:15:20
	 * @return
	 * @throws Exception 
	 */
	public double[] getData(ConditionEntity conditionEntity,int doType) throws Exception
	{
		
		double[]data =null;
		double[]dataOnetoTwo =null;
		double[]dataThridtoFour =null;
		
		if(doType==DO_TYPE_BUSSINESS)
		{
			Map<String ,Object> OnetoTwoData=getProportionDataBusinessOnetoTwo(conditionEntity);
			double other =0;
			if(OnetoTwoData!=null){
				dataOnetoTwo=(double[]) OnetoTwoData.get("data");
				other=Double.parseDouble(OnetoTwoData.get("otherValue").toString());
			}else
			{
				dataOnetoTwo=null;
				other=0;
			}
			
			dataThridtoFour=getProportionDataBusinessThridtoFour(conditionEntity, other);
			
			data= ParamTool.getData(dataOnetoTwo,dataThridtoFour);
		}
		else if(doType==DO_TYPE_NOT_BUSSINESS)
		{
			dataOnetoTwo=getProportionDataOnetoTwo( conditionEntity);
			
			dataThridtoFour=getProportionDataThridtoFour( conditionEntity);
		}
		data= ParamTool.getData(dataOnetoTwo,dataThridtoFour);
		return data;
	}
		
	
	/**
	 * 功能:占营总收入的（一二级）
	 * <p>
	 * 作者 杨荣忠 2015-1-21 下午05:52:40
	 * 
	 * @param conditionEntity
	 * @param rowKeysId
	 * @param changOneOrTwo
	 * @return
	 * @throws Exception
	 */
	public Map<String ,Object> getProportionDataBusinessOnetoTwo(ConditionEntity conditionEntity) throws Exception
	{ 
		Map<String ,Object>map =new HashMap();
		int type=Constant.PARAM_ONE_OR_TWO;
		String[] rowKeysId = ImgTool.getDimensionAllID(conditionEntity,type);
		
		if(rowKeysId!=null)
		{
			// 判断什么时候由一级分类匹配到二级分类切换,减一为已去掉一个主营维度
			int changOneOrTwo = ReportTool.getChangIndex(conditionEntity,type);
			double[] data = new double[rowKeysId.length];
			double other = 0; // 前端所选其它维度的和
			double value = 0; // 各维度对应的和
			
			List<CellMonthSummaryInfo>cellMonthSummaryInfoList = ReportTool
					.getCellMonthSummaryInfoResult(conditionEntity,type);

			for (int i = 0; i < rowKeysId.length; i++)
			{
				List<CellMonthSummaryInfo> result = new ArrayList();
				for (int j = 0; j < cellMonthSummaryInfoList.size(); j++)
				{
					CellMonthSummaryInfo cellMonthSummaryInfo = cellMonthSummaryInfoList.get(j);
					
					String classTypeId = ReportTool.getchangOneOrTwoValue(
							changOneOrTwo, i, cellMonthSummaryInfo);
					if (classTypeId.equals(rowKeysId[i]))
					{
						// 判断是不是属于该分类
						result.add(cellMonthSummaryInfo);
					}
				}
				value = ParamTool.getSumOneToTwo(result);
				data[i] = value;
				other += value;
			}
			map.put("otherValue", other);
			map.put("data", data);
			return map;
		}
		return null;
	}
	
	
	
	/**
	 * 功能:占营总收入的（三四级）
	 * <p>
	 * 作者 杨荣忠 2015-1-21 下午05:52:40
	 * 
	 * @param conditionEntity
	 * @param rowKeysId
	 * @param changOneOrTwo
	 * @return
	 * @throws Exception
	 */
	public double[] getProportionDataBusinessThridtoFour(ConditionEntity conditionEntity,double other) throws Exception
	{ 
		
		int type=Constant.PARAM_THIRD_OR_FOUR;
		String[] rowKeysId = ImgTool.getZhabiDetachId(conditionEntity,type);
		
		if(rowKeysId!=null)
		{
			int changOneOrTwo = ReportTool.getChangIndex(conditionEntity,type) - 1;
			double[] data = new double[rowKeysId.length];
			double sumAll = 0; // 总营收入的和
			double value = 0; // 各维度对应的和
			
			List<RowMonthSummaryInfo> rowMonthSummaryInfoList = ReportTool
					.getRowMonthSummaryInfoResult(conditionEntity,Constant.PARAM_THIRD_OR_FOUR);

			for (int i = 0; i < rowKeysId.length; i++)
			{
				List<RowMonthSummaryInfo> result = new ArrayList();
				for (int j = 0; j < rowMonthSummaryInfoList.size(); j++)
				{
					RowMonthSummaryInfo rowMonthSummaryInfo = rowMonthSummaryInfoList
							.get(j);
					
					String business_income_type = rowMonthSummaryInfo
							.getClassifyName(); // 求该条记录的
					
					String classTypeId = ReportTool.getchangThridOrFourValue(
							changOneOrTwo, i, rowMonthSummaryInfo);
					
					if (classTypeId.equals(rowKeysId[i]))
					{// 判断是不是属于该分类
						result.add(rowMonthSummaryInfo);
					}
					if (business_income_type.equals(ALL_COST)
							|| business_income_type.equals(OTHER_ALL_COST))
					{// 判断是不是属于总营收入
						try
						{
							sumAll += rowMonthSummaryInfo.getMonthSummaryAmount()
									.doubleValue();
						} catch (Exception e)
						{
							sumAll += 0;
						}
					}
				}
				value = ParamTool.getSumThirdToFour(result);
				data[i] = value;
				other += value;
			}
			if(sumAll>=other)
			   data = (double[]) ArrayUtils.add(data, (sumAll - other)); //结果求的是占总营收入的比，不是占全部和的比
			else
				data = (double[]) ArrayUtils.add(data, 0);
			return data;
		}
		return null;
	}
	
	
	
	/**
	 * 功能:非占营总收入 一级与二级
	 * <p>
	 * 作者 杨荣忠 2015-1-21 下午05:52:10
	 * 
	 * @param conditionEntity
	 * @param rowKeysId
	 * @param changOneOrTwo
	 * @return
	 * @throws Exception
	 */
	public double[] getProportionDataOnetoTwo(ConditionEntity conditionEntity) throws Exception
	{   
		int type=Constant.PARAM_ONE_OR_TWO;
		
		int changOneOrTwo = ReportTool.getChangIndex(conditionEntity,type);
		
		String[] rowKeysId=ImgTool.getDimensionAllID( conditionEntity,type);
		if(rowKeysId!=null){
			double[] data = new double[rowKeysId.length];
			
			List<CellMonthSummaryInfo> cellMonthSummaryInfoList = ReportTool.
			      getCellMonthSummaryInfoResult(conditionEntity,type);
	        
			// [维度AID，维度BID]
			for (int j = 0; j < rowKeysId.length; j++)
			{
				List<CellMonthSummaryInfo> result = new ArrayList();
				
				for (int i = 0; i < cellMonthSummaryInfoList.size(); i++)
				{
					CellMonthSummaryInfo cellMonthSummaryInfo = cellMonthSummaryInfoList
							.get(i);
					
					String classTypeId = ReportTool.getchangOneOrTwoValue(changOneOrTwo, j, cellMonthSummaryInfo);
					
					if (classTypeId.equals(rowKeysId[j]))
					{// 判断是不是属于该分类
						result.add(cellMonthSummaryInfo);
					}
				}
				data[j] = ParamTool.getSumOneToTwo(result);
			}
			return data;
		}
		return null;
	}
	
	
	/**
	 * 功能:非占营总收入  三级与四级
	 * <p>
	 * 作者 杨荣忠 2015-1-21 下午05:52:10
	 * 
	 * @param conditionEntity
	 * @param rowKeysId
	 * @param changOneOrTwo
	 * @return
	 * @throws Exception
	 */
	public double[] getProportionDataThridtoFour(ConditionEntity conditionEntity) throws Exception
	{   
		int type=Constant.PARAM_THIRD_OR_FOUR;
		
		int changOneOrTwo = ReportTool.getChangIndex(conditionEntity,type);
		
		String[] rowKeysId=ImgTool.getDimensionAllID( conditionEntity,type);
		
		if(rowKeysId!=null){
			double[] data = new double[rowKeysId.length];
			
			List<RowMonthSummaryInfo> rowMonthSummaryInfoList = ReportTool
					.getRowMonthSummaryInfoResult(conditionEntity,type);
	        
			// [维度AID，维度BID]
			for (int j = 0; j < rowKeysId.length; j++)
			{
				List<RowMonthSummaryInfo> result = new ArrayList();
				for (int i = 0; i < rowMonthSummaryInfoList.size(); i++)
				{
					RowMonthSummaryInfo rowMonthSummaryInfo = rowMonthSummaryInfoList
							.get(i);
					String classTypeId = ReportTool.getchangThridOrFourValue(
							changOneOrTwo, j, rowMonthSummaryInfo);
					if (classTypeId.equals(rowKeysId[j]))
					{
						// 判断是不是属于该分类
						result.add(rowMonthSummaryInfo);
					}
				}
				data[j] = ParamTool.getSumThirdToFour(result);
			}
			return data;
		}
		return null;
	}
	
}
