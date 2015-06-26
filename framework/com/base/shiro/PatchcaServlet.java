package com.base.shiro;

import java.awt.image.BufferedImageOp;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.patchca.filter.ConfigurableFilterFactory;
import org.patchca.filter.library.AbstractImageOp;
import org.patchca.filter.library.WobbleImageOp;
import org.patchca.filter.predefined.DoubleRippleFilterFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.utils.encoder.EncoderHelper;
import org.patchca.word.RandomWordFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.utils.Exceptions;

/**
 * @author Vigor
 * @since 2014年1月7日 下午3:11:46
 */
public class PatchcaServlet extends HttpServlet
{
	
	private static final long serialVersionUID = -656795564528376490L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PatchcaServlet.class);
	
	private static int WIDTH = 110;
	private static int HEIGHT = 50;
	private static int MAX_LENGTH = 4;
	private static int MIN_LENGTH = 2;
	
	private static String CACHE_NAME = "_pcatcha_";
	private static String CACHE_FILE = "ehcache/ehcache-pcaptcha.xml";
	
	private static Cache cache;
	private static ConfigurableCaptchaService cs;
	
	// private static BestFitTextRenderer textRenderer;
	//
	// private static RandomFontFactory fontFactory;
	
	private static ConfigurableFilterFactory filterFactory = null;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException
	{
		cs = new ConfigurableCaptchaService();
		
		int width = NumberUtils.toInt(this.getInitParameter("width"), WIDTH);
		int height = NumberUtils.toInt(this.getInitParameter("height"), HEIGHT);
		cs.setWidth(width);
		cs.setHeight(height);
		
		int maxLength = NumberUtils.toInt(this.getInitParameter("maxLength"), MAX_LENGTH);
		int minLength = NumberUtils.toInt(this.getInitParameter("minLength"), MIN_LENGTH);
		RandomWordFactory wf = new RandomWordFactory();
		wf.setMaxLength(maxLength);
		wf.setMinLength(minLength);
		cs.setWordFactory(wf);
		
		// 文字渲染器设置
		
		// textRenderer = new BestFitTextRenderer();
		//
		// textRenderer.setBottomMargin(3);
		//
		// textRenderer.setTopMargin(3);
		//
		// cs.setTextRenderer(textRenderer);
		
		// 随机字体生成器
		// fontFactory = new RandomFontFactory();
		//
		// fontFactory.setMaxSize(32);
		//
		// fontFactory.setMinSize(28);
		//
		// cs.setFontFactory(fontFactory);
		//
		DoubleRippleFilterFactory drff = new DoubleRippleFilterFactory();
	 
		filterFactory = new ConfigurableFilterFactory();
		
		List<BufferedImageOp> filters = new ArrayList<BufferedImageOp>();
		
		WobbleImageOp wobbleImageOp = new WobbleImageOp();
		
		wobbleImageOp.setEdgeMode(AbstractImageOp.EDGE_MIRROR);
		
		wobbleImageOp.setxAmplitude(3.0);
		wobbleImageOp.setyWavelength(3.0);
		
		wobbleImageOp.setyAmplitude(1.0);
		
		filters.add(wobbleImageOp);
		
		filterFactory.setFilters(filters);
		
		cs.setFilterFactory(filterFactory);
		
		ResourcePatternResolver rpr = new PathMatchingResourcePatternResolver();
		Resource resource = rpr.getResource("classpath:" + CACHE_FILE);
		
		try
		{
			CacheManager cacheManager = CacheManager.create(resource.getInputStream());
			
			cache = cacheManager.getCache(CACHE_NAME);
			if (cache == null)
			{
				Configuration configuration = ConfigurationFactory.parseConfiguration(resource.getInputStream());
				
				cache = new Cache(configuration.getCacheConfigurations().get(CACHE_NAME));
				if (cache == null)
				{
					cache = cacheManager.getCache(Cache.DEFAULT_CACHE_NAME);
				}
				else
				{
					cacheManager.addCache(cache);
				}
			}
		}
		catch (CacheException e)
		{
			LOGGER.error("创建缓存[" + CACHE_NAME + "]出错，请检查配置参数:" + Exceptions.getStackTraceAsString(e));
		}
		catch (IOException e)
		{
			LOGGER.error("缓存配置文件[" + CACHE_FILE + "]读取出错:" + Exceptions.getStackTraceAsString(e));
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// 清除缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0L);
		
		// 显示类型
		response.setContentType("image/png");
		
		OutputStream os = response.getOutputStream();
		String patchca = EncoderHelper.getChallangeAndWriteImage(cs, "png", os);
		
		// 放入缓存
		Element element = new Element(request.getSession().getId(), patchca);
		cache.put(element);
		
		os.flush();
		os.close();
	}
	
	/**
	 * 检查验证码是否正确
	 * 
	 * @param req
	 * @return
	 */
	public static boolean validate(String sessionId, String code)
	{
		Element element = cache.get(sessionId);
		
		if (element != null)
		{
			String sourceCode = (String) element.getObjectValue();
			
			cache.remove(sessionId);
			return StringUtils.equalsIgnoreCase(code, sourceCode);
		}
		
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#destroy()
	 */
	@Override
	public void destroy()
	{
		filterFactory = null;
		cs = null;
		CacheManager.getInstance().removeCache(CACHE_NAME);
	}
}
