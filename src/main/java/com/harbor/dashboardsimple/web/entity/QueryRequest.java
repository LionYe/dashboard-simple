package com.harbor.dashboardsimple.web.entity;

import java.io.Serializable;

/**
 * 查询参数类
 * 
 * @author szy
 *
 */
public class QueryRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String request;// 模糊查询内容

	private String menu;

	private String search;
	
	private char permissionType;

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public char getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(char permissionType) {
		this.permissionType = permissionType;
	}
	
	
}
