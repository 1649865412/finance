package com.innshine.chart.imgtoppt.ppt;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hslf.model.Picture;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextBox;
import org.apache.poi.hslf.usermodel.RichTextRun;
import org.apache.poi.hslf.usermodel.SlideShow;

public class PPTExport {
	
	public static void main(String[] args){
		PPTExport ppt = new PPTExport();
		try {
			ppt.createPPT();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createPPT() throws Exception{
		SlideShow ppt = getPPT();//new SlideShow();
		//Slide[] slides = ppt.getSlides();
		
		savePPTFile(ppt);
		 
		
	}
	
	private SlideShow getPPT() throws IOException{
		SlideShow ppt = new SlideShow();
		
		 
/*		Slide newSlide = ppt.createSlide();
       //添加幻灯片标题
		TextBox title = newSlide.addTitle();
		RichTextRun titleRun = title.getTextRun().getRichTextRuns()[0];
		titleRun.setFontColor(Color.RED);
		title.setText("ppt测试");
		 
		//添加文本框
		TextBox txt = new TextBox();
		RichTextRun richTextRun = txt.getTextRun().getRichTextRuns()[0];
		richTextRun.setFontColor(Color.BLUE);  
		//setText参数字符串可以包含回车、换行符,但是最后一行不能以\r\n结尾,否则设置的格式没有效果(v3.5)
		richTextRun.setText("这里可以换行\r\n第二行文本");  		
		txt.setAnchor(new java.awt.Rectangle(50,150,400,400));
		newSlide.addShape(txt);*/
		
		Slide pictureSlide = ppt.createSlide();
		String fileName = "D:" + File.separator + "temp" + File.separator + "book.jpg";
		int picIndex = ppt.addPicture(new File(fileName), Picture.JPEG);
		Picture jpg = new Picture(picIndex);
		 
		//set image position in the slide，x， y, width,heigth
		jpg.setAnchor(new java.awt.Rectangle(5, 5, 700, 500));
		 
		pictureSlide.addShape(jpg);

        return ppt;
	}
	
	private void savePPTFile(SlideShow ppt) throws Exception{
		 String fileName = "D:" + File.separator + "temp";
		 File dir = new File(fileName);
		 dir.mkdirs();
		 String fileNamebook = fileName + File.separator + "data.ppt";
		 File f = new File(fileNamebook);
		 f.createNewFile();
         FileOutputStream out = new FileOutputStream(f);
	     ppt.write(out);
	     out.close();
    }
}
