package com.utils.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class ValidatorsData
{
	
	/**
	 * 是否是整数
	 * 
	 * @param value
	 */
	public static boolean isIntege(String value)
	{
		return isPattern(value, "^-?[1-9]\\d*$");
	}
	
	/**
	 * 根据传入的规则，效验数据
	 * <p>
	 * 
	 * @param value
	 *            值
	 * @param pattern
	 *            规则
	 * @return
	 */
	public static boolean isPattern(String value, String pattern)
	{
		if (StringUtils.isBlank(pattern) || StringUtils.isBlank(value))
		{
			return false;
		}
		
		Matcher m = null;
		try
		{
			Pattern p = Pattern.compile(pattern);
			
			m = p.matcher(value);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
		return m.matches();
	}
	
	/**
	 * 是否是正整数
	 * 
	 * @param value
	 */
	public static boolean isIntege1(String value)
	{
		
		return isPattern(value, "^[1-9]\\d*$");
	}
	
	/**
	 * 是否是负整数
	 * 
	 * @param value
	 */
	public static boolean isIntege2(String value)
	{
		return isPattern(value, "^-[1-9]\\d*$");
	}
	
	/**
	 * 是否是数字
	 * 
	 * @param value
	 */
	public static boolean isNum(String value)
	{
		return isPattern(value, "^([+-]?)\\d*\\.?\\d+$");
	}
	
	/**
	 * 是否是正数（正整数 + 0）
	 * 
	 * @param value
	 */
	public static boolean isNum1(String value)
	{
		return isPattern(value, "^[1-9]\\d*|0$");
	}
	
	/**
	 * 是否是负数（负整数 + 0）
	 * 
	 * @param value
	 */
	public static boolean isNum2(String value)
	{
		return isPattern(value, "^-[1-9]\\d*|0$");
	}
	
	/**
	 * 是否是浮点数
	 * 
	 * @param value
	 */
	public static boolean isDecmal(String value)
	{
		return isPattern(value, "^([+-]?)\\d*\\.\\d+$");
	}
	
	/**
	 * 是否是正浮点数
	 * 
	 * @param value
	 */
	public static boolean isDecmal1(String value)
	{
		return isPattern(value, "^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*$");
	}
	
	/**
	 * 是否是负浮点数
	 * 
	 * @param value
	 */
	public static boolean isDecmal2(String value)
	{
		
		return isPattern(value, "^-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*)$");
	}
	
	/**
	 * 是否是浮点数
	 * 
	 * @param value
	 */
	public static boolean isDecmal3(String value)
	{
		return isPattern(value, "^-?([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0)$");
	}
	
	/**
	 * 是否是非负浮点数（正浮点数 + 0）
	 * 
	 * @param value
	 */
	public static boolean isDecmal4(String value)
	{
		return isPattern(value, "^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0$");
	}
	
	/**
	 * 是否是非正浮点数（负浮点数 + 0）
	 * 
	 * @param value
	 */
	public static boolean isDecmal5(String value)
	{
		return isPattern(value, "^(-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*))|0?.0+|0$");
	}
	
	/**
	 * 是否是邮件
	 * 
	 * @param value
	 */
	public static boolean isEmail(String value)
	{
		return isPattern(value, "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$");
	}
	
	/**
	 * 是否是颜色
	 * 
	 * @param value
	 */
	public static boolean isColor(String value)
	{
		return isPattern(value, "^[a-fA-F0-9]{6}$");
	}
	
	/**
	 * 是否是url
	 * 
	 * @param value
	 */
	public static boolean isUrl(String value)
	{
		return isPattern(value, "^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$");
	}
	
	/**
	 * 是否是中文
	 * 
	 * @param value
	 */
	public static boolean isChinese(String value)
	{
		return isPattern(value, "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$");
	}
	
	/**
	 * 是否是ACSII字符
	 * 
	 * @param value
	 */
	public static boolean isAscii(String value)
	{
		return isPattern(value, "^[\\x00-\\xFF]+$");
	}
	
	/**
	 * 是否是邮编
	 * 
	 * @param value
	 */
	public static boolean isZipcode(String value)
	{
		return isPattern(value, "^\\d{6}$");
	}
	
	/**
	 * 是否是手机
	 * 
	 * @param value
	 */
	public static boolean isMobile(String value)
	{
		return isPattern(value, "^(13|15)[0-9]{9}$");
	}
	
	/**
	 * 是否是ip地址
	 * 
	 * @param value
	 */
	public static boolean isIp(String value)
	{
		return isPattern(
				value,
				"^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$");
	}
	
	/**
	 * 是否是非空
	 * 
	 * @param value
	 */
	public static boolean isNotempty(String value)
	{
		return isPattern(value, "^\\S+$");
	}
	
	/**
	 * 是否是图片
	 * 
	 * @param value
	 */
	public static boolean isPicture(String value)
	{
		return isPattern(value, "(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$");
	}
	
	/**
	 * 是否是压缩文件
	 * 
	 * @param value
	 */
	public static boolean isRar(String value)
	{
		return isPattern(value, "(.*)\\.(rar|zip|7zip|tgz)$");
	}
	
	/**
	 * 是否是日期
	 * 
	 * @param value
	 */
	public static boolean isDate(String value)
	{
		return isPattern(
				value,
				"^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
	}
	
	/**
	 * 是否是QQ号码
	 * 
	 * @param value
	 */
	public static boolean isQq(String value)
	{
		return isPattern(value, "^[1-9]*[1-9][0-9]*$");
	}
	
	/**
	 * 是否是电话号码的函数(包括验证国内区号,国际区号,分机号)
	 * 
	 * @param value
	 */
	public static boolean isTel(String value)
	{
		return isPattern(value, "^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{3,}))?$");
	}
	
	/**
	 * 用来用户注册。匹配由数字、26个英文字母或者下划线组成的字符串
	 * 
	 * @param value
	 */
	public static boolean isUsername(String value)
	{
		return isPattern(value, "^\\w+$");
	}
	
	/**
	 * 是否是字母
	 * 
	 * @param value
	 */
	public static boolean isLetter(String value)
	{
		return isPattern(value, "^[A-Za-z]+$");
	}
	
	/**
	 * 是否是大写字母
	 * 
	 * @param value
	 */
	public static boolean isLetter_u(String value)
	{
		
		return isPattern(value, "^[A-Z]+$");
	}
	
	/**
	 * 是否是大写字母
	 * 
	 * @param value
	 */
	public static boolean isLetter_l(String value)
	{
		
		return isPattern(value, "^[a-z]+$");
	}
	
	/**
	 * 是否是价格
	 * 
	 * @param value
	 */
	public static boolean isPrice(String value)
	{
		return isPattern(value, "^([1-9]{1}[0-9]{0,}(\\.[0-9]{0,2})?|0(\\.[0-9]{0,2})?|\\.[0-9]{1,2})$");
	}
	
}
