package com.innshine.reportExport.entity;

/**
 * <code>ConditionEntity.java,财务报表导出前端条件实体类</code>
 * <p>
 * <p>
 * Copyright 2015 All right reserved.
 * 
 * @author 杨荣忠 时间 2015-1-19 上午11:39:38
 * @version 1.0 </br>最后修改人 无
 */
public class ConditionEntity
{
	private String item;
	private String itemId;
	
	private String itemClassOne;//一级维度名字
	private String itemClassTwo;//二级维度名字
	private String itemClassThird;  //三级维度名字
	private String itemClassFour;  //四级名字
	
	private String itemClassOneId;//一级维度名字ID
	private String itemClassTwoId;//二级维度ID
	private String itemClassThirdId;  //三级维度ID
	private String itemClassFourId;  //四级名字ID
	
	
	private int type;      //单选按钮类型
	private int typeImg;   //导出类型
	private int circle;    //同比周期
	private String beginTime;
	private String endTime; 
	private int strategyType; //前端判断采取的导出图策略类型
	private int checkProportionAll ;//1：为占比求营收入的比，0：为其它的占比,不用营总收入做分母

	
	
	public String getItemClassOne()
	{
		return itemClassOne;
	}
	public void setItemClassOne(String itemClassOne)
	{
		this.itemClassOne = itemClassOne;
	}
	public String getItemClassThird()
	{
		return itemClassThird;
	}
	public void setItemClassThird(String itemClassThird)
	{
		this.itemClassThird = itemClassThird;
	}
	public String getItemClassFour()
	{
		return itemClassFour;
	}
	public void setItemClassFour(String itemClassFour)
	{
		this.itemClassFour = itemClassFour;
	}
	public String getItemClassOneId()
	{
		return itemClassOneId;
	}
	public void setItemClassOneId(String itemClassOneId)
	{
		this.itemClassOneId = itemClassOneId;
	}
	public String getItemClassThirdId()
	{
		return itemClassThirdId;
	}
	public void setItemClassThirdId(String itemClassThirdId)
	{
		this.itemClassThirdId = itemClassThirdId;
	}
	public String getItemClassFourId()
	{
		return itemClassFourId;
	}
	public void setItemClassFourId(String itemClassFourId)
	{
		this.itemClassFourId = itemClassFourId;
	}
	public String getItemId()
	{
		return itemId;
	}
	public void setItemId(String itemId)
	{
		this.itemId = itemId;
	}

	public String getItemClassTwoId()
	{
		return itemClassTwoId;
	}
	public void setItemClassTwoId(String itemClassTwoId)
	{
		this.itemClassTwoId = itemClassTwoId;
	}
	public ConditionEntity(){
		
	}
	public String getItem()
	{
		return item;
	}

	public void setItem(String item)
	{
		this.item = item;
	}


	public String getItemClassTwo()
	{
		return itemClassTwo;
	}

	public void setItemClassTwo(String itemClassTwo)
	{
		this.itemClassTwo = itemClassTwo;
	}


	public int getType()
	{
		return type;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	public int getTypeImg()
	{
		return typeImg;
	}
	public void setTypeImg(int typeImg)
	{
		this.typeImg = typeImg;
	}
	public String getBeginTime()
	{
		return beginTime;
	}

	public void setBeginTime(String beginTime)
	{
		this.beginTime = beginTime;
	}

	public String getEndTime()
	{
		return endTime;
	}

	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}


	public int getCircle()
	{
		return circle;
	}
	public void setCircle(int circle)
	{
		this.circle = circle;
	}
	public int getStrategyType()
	{
		return strategyType;
	}

	public void setStrategyType(int strategyType)
	{
		this.strategyType = strategyType;
	}
	public int getCheckProportionAll()
	{
		return checkProportionAll;
	}
	public void setCheckProportionAll(int checkProportionAll)
	{
		this.checkProportionAll = checkProportionAll;
	}
	
	

}
