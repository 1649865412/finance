package com.innshine.common.utils;

public class BrandChangLocalThread
{
	private static final ThreadLocal<Object> BRAND_LOCAL_THREAD = new ThreadLocal<Object>();
	
	public static void setOrganizationId(Object OrganizationId)
	{
		BRAND_LOCAL_THREAD.set(OrganizationId);
	}
	
	public static Object getOrganizationId()
	{
		return BRAND_LOCAL_THREAD.get();
	}
	
	public static void removeOrganizationId()
	{
		BRAND_LOCAL_THREAD.remove();
	}
}
