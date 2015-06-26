package com.utils.excel.template;

import java.util.ArrayList;
import java.util.List;

public class Step {
	private String property;

	// 起始行号，终止行号， 起始列号，终止列号
	private Integer start;
	private Integer end;
	private Integer colStart;
	private Integer colEnd;

	public Step(String property, Integer start, Integer end, Integer colStart,
			Integer colEnd) {
		this.property = property;
		this.start = start;
		this.end = end;
		this.colStart = colStart;
		this.colEnd = colEnd;
	}

	public static Step foundStep(String property, List<Step> steps) {
		for (Step s : steps) {
			if (property.equals(s.getProperty())) {
				return s;
			}
		}
		return null;
	}
	
	//判断获取是不是大于同一字段下的最新索引
	public static boolean getSameNew(String property, List<Step> steps,int rowIndex){
		boolean flag=false;
	     Step StepLast=getSameNewStep( property, steps);
		if(StepLast!=null){
			if(rowIndex>StepLast.getEnd()){
				flag=true;
			}
		}
	
		 return flag;
	}
	
	
	
	//用于获取同一字段下的最新索引，从顺序开始后，取最后一条
	public static Step getSameNewStep(String property, List<Step> steps){
		Step StepLast=null;
		if(steps!=null){
			if(steps.size()>0){
				 List<Step> stepsList=new ArrayList();
					for (Step s : steps) {
						if (property.equals(s.getProperty())) {
							stepsList.add(s);
						}
					}
					if(stepsList.size()>0){
						 StepLast=	stepsList.get(stepsList.size()-1);
					}
			}
		}
		 return StepLast;
	}
	
	
	//判断是否进入合并
	public static boolean foundStepNewCheck(String property, List<Step> steps,
			int rowIndex) {
		boolean flag = true;
		boolean tagOne = true;
		boolean tagTwo = false;
		
		for (Step s : steps) {
			// 如果合并的记录里没有，就可以进入合并单元格方法
			if (property.equals(s.getProperty())) {
				tagOne = false;
				break;
			}
		}
		if (tagOne) {
			flag = true;
		}/* else {
			// 如果已经存在该字段记录，则判断 是不是大于同一类别下的最新的索引
		//	Step step = getSameNewStep( property,  steps);
			if (getSameNew(property,  steps, rowIndex)) {
				flag = true;
			}
		}*/
		return flag;
	}

	public Integer getColStart() {
		return colStart;
	}

	public void setColStart(Integer colStart) {
		this.colStart = colStart;
	}

	public Integer getColEnd() {
		return colEnd;
	}

	public void setColEnd(Integer colEnd) {
		this.colEnd = colEnd;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}
}
