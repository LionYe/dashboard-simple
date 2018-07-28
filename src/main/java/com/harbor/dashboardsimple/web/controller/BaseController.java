package com.harbor.dashboardsimple.web.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.util.Assert;

import com.harbor.dashboardsimple.util.CollectionUtil;
import com.harbor.dashboardsimple.util.Page;
import com.harbor.dashboardsimple.util.StringUtil;

/**
 *  基础控制器 
 * @author harbor
 *
 */
public class BaseController {

	
	/**
	 * 获取page对象
	 * 
	 * @param request
	 * @return page对象
	 */
	public <T> Page<T> getPage(HttpServletRequest request) {
		int pageNo = 1; // 当前页码
		int pageSize = 10; // 每页显示栏目
		if (StringUtil.isNotEmpty(request.getParameter("pageNo"))) {
			pageNo = Integer.valueOf(request.getParameter("pageNo"));
		}
		//jquery bootgrid获取当前页码
		if (StringUtil.isNotEmpty(request.getParameter("current"))) {
			pageNo = Integer.valueOf(request.getParameter("current"));
		}		
		//jquery bootgrid获取每页显示栏目		
		if (StringUtil.isNotEmpty(request.getParameter("rowCount"))) {
			pageSize = Integer.valueOf(request.getParameter("rowCount"));
		}
		return new Page<T>(pageNo, pageSize, getPageUrl(request));
	}

	/**
	 * 获取分页请求相对路径
	 * 
	 * @param request
	 * @return
	 */
	private String getPageUrl(HttpServletRequest request) {
		String path = request.getRequestURI();
		String context = request.getContextPath();
		return path.replaceAll(context, "");
	}

	/**
	 * 获取分页数据
	 * 
	 * @param page
	 * @return map对象
	 */
	public <T> Map<String, Object> getUIData(Page<T> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (CollectionUtil.isEmpty(page.getResult())) {
			map.put("pageResult", new ArrayList<T>());
		} else {
			map.put("pageResult", page.getResult());
		}
		map.put("totalPage", page.getTotalCount());
		return map;
	}
	
	/**
	 * REQUEST转化FORM
	 * 
	 * @param request
	 * @param prefix
	 * @param clazz
	 * @return FORM
	 */
	@SuppressWarnings("unchecked")
	public <T> T getForm(ServletRequest request, String prefix, Class<T> clazz) {
		Assert.notNull(request, "Request must not be null");
		Enumeration paramNames = request.getParameterNames();
		T t = null;
		try {
			FastClass fastClass = FastClass.create(clazz);
			t = (T) fastClass.newInstance();
			if (prefix == null) {
				prefix = "";
			}
			while (paramNames != null && paramNames.hasMoreElements()) {
				String paramName = (String) paramNames.nextElement();
				if ("".equals(prefix) || paramName.startsWith(prefix)) {
					String unprefixed = paramName.substring(prefix.length());
					String[] values = request.getParameterValues(paramName);
					if (values == null || values.length == 0) {
						// Do nothing, no values found at all.
					} else if (values.length > 1) {
						PropertyUtils.setProperty(t, unprefixed, values);
					} else {
						PropertyUtils.setProperty(t, unprefixed, values[0]);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return t;
	}
}
