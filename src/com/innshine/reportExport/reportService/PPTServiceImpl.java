package com.innshine.reportExport.reportService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hslf.model.Picture;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innshine.reportExport.util.Constant;
import com.innshine.reportExport.util.ExcelConstant;

/**
 * 
 *  <code>PPTServiceImpl.java  ,图片转ppt文件下载业务类</code>
 *  <p>
 *  <p>Copyright  2015 All right reserved.
 *  @author 杨荣忠 时间 2015-1-16 下午04:18:27	
 *  @version 1.0 
 *  </br>最后修改人 无
 */
@Service
@Transactional
public class PPTServiceImpl {
	
	public void createPPT(String imgPath,String pptSavePath) throws Exception{
		SlideShow ppt = getPPT(imgPath);
		savePPTFile(ppt,pptSavePath);		 
	}
	
	
	private SlideShow getPPT(String imgPath) throws IOException{
		SlideShow ppt = new SlideShow();	
		Slide pictureSlide = ppt.createSlide();
		String fileName = imgPath;
		int picIndex = ppt.addPicture(new File(fileName), Picture.JPEG);
		Picture jpg = new Picture(picIndex);
		//x,y,width,height
		jpg.setAnchor(new java.awt.Rectangle(36,108, 633, 300));		 
		pictureSlide.addShape(jpg);
        return ppt;
	}

	
	public  void exportPPTFile(String imgName,HttpServletResponse response,HttpServletRequest request) throws Exception{
		//统一路径
		String url = this.getClass().getResource("").getPath().replaceAll("%20", " ");
		String tempPath = url.substring(0, url.indexOf("WEB-INF")) + "excelImg"+File.separator;
		
		//图片路径与ppt路径
		String imgPath=tempPath+imgName;
		String pptPath=tempPath+getPPTname(imgName)+".ppt";
		
		//
		SlideShow ppt = getPPT(imgPath);
		savePPTFile(ppt,pptPath);
		
		//FileInputStream  pptImg = new FileInputStream(new File(pptPath));
		download(response, request,pptPath, Constant.FILE_FORMAT);
		
   }
	
	
	public  String getPPTname(String name){
		String str=name.substring(0,name.lastIndexOf("."));
		return str;
		
	}
	private void savePPTFile(SlideShow ppt,String pptSavePath) throws Exception{
		 File f = new File(pptSavePath);
		 f.createNewFile();
         FileOutputStream out = new FileOutputStream(f);
	     ppt.write(out);
	     out.close();
    }
	
	  /** 
     * 文件下载 
     * @param response 
     * @param filePath 文件路径 
     * @param fileName 文件名称 
     */  
    public void download(HttpServletResponse response, HttpServletRequest request,String filePath, String fileName)throws IOException {
    	
        FileInputStream fis = null;  
        OutputStream os = null;  
        try {  
            fis = new FileInputStream(filePath);  
            os = response.getOutputStream();// 取得输出流  
            response.reset();// 清空输出流  
       //     response.setHeader("Content-disposition", "attachment; filename=" + fileName);// 设定输出文件头  
     //       response.setContentType("application/x-download");  
            setResponses( request, response,  fileName);
            byte[] mybyte = new byte[8192];  
            int len = 0;  
            while ((len = fis.read(mybyte)) != -1) {  
                os.write(mybyte, 0, len);  
            }  
            os.close();  
        }catch (IOException e) {  
            throw e;  
        }  
    }  

	public static void setResponses(HttpServletRequest request,
			HttpServletResponse response, String fileName)
			throws UnsupportedEncodingException {
		response.reset();
		response.setContentType(ExcelConstant.APPLICATION_X_MSDOWNLOAD);
		response.setCharacterEncoding(ExcelConstant.DEAULT_ENCODE_UTF_8);
		if (request.getHeader(ExcelConstant.USER_AGENT).toLowerCase().indexOf(
				ExcelConstant.FIREFOX) > 0) {
			fileName = new String(fileName
					.getBytes(ExcelConstant.DEAULT_ENCODE_UTF_8),
					ExcelConstant.ENCODE_ISO8859_1);// firefox浏览器
		} else {
			fileName = URLEncoder.encode(fileName,
					ExcelConstant.DEAULT_ENCODE_UTF_8)
					.replace(ExcelConstant.BLANK_SPACE_20,
							ExcelConstant.BLANK_CHARACTER);
		}
		response.addHeader("Content-Disposition", "attachment; filename=\""
				+ fileName + "\"");
	}
}
