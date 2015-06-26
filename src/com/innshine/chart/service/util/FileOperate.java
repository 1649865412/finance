package com.innshine.chart.service.util;

//import static com.google.common.collect.ImmutableMap.of;

import java.io.File;
import java.io.IOException;

public class FileOperate
{

	// 测试main
	public static void main(String[] args) throws Exception
	{
		// del("D:\\a");
	}

	public void del(String filepath) throws IOException
	{
		String url = this.getClass().getResource("").getPath().replaceAll(
				"%20", " ");
		String tempPath = url.substring(0, url.indexOf("WEB-INF")) + "excelImg";
		File f = new File(filepath);// 定义文件路径
		if (f.exists() && f.isDirectory())
		{// 判断是文件还是目录
			if (f.listFiles().length == 0)
			{// 若目录下没有文件则直接删除
				f.delete();
			} else
			{// 若有则把文件放进数组，并判断是否有下级目录
				File delFile[] = f.listFiles();
				int i = f.listFiles().length;
				for (int j = 0; j < i; j++)
				{
					if (delFile[j].isDirectory())
					{
						del(delFile[j].getAbsolutePath());// 递归调用del方法并取得子目录路径
					}
					delFile[j].delete();// 删除文件
				}
			}
		}
		System.out.println("删除成功：" + filepath);
	}
}
