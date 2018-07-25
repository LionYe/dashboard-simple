package com.harbor.dashboardsimple.web.entity;

import java.io.Serializable;

public class SysPermissionInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Integer id;//主键.
    private Integer pid;//承接一级菜单ID否则为null.
    private String name;//菜单名
    private char type; //F:菜单 O:按钮
    private Integer sort; //菜单显示顺序
    private String url; //菜单跳转路径
    private String description; //菜单描述
    private char state; //状态
    private String target; //bootstrap一二级菜单关联的data-target值
    private String iconCode; //图标code码

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public char getType() {
		return type;
	}
	public void setType(char type) {
		this.type = type;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public char getState() {
		return state;
	}
	public void setState(char state) {
		this.state = state;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getIconCode() {
		return iconCode;
	}
	public void setIconCode(String iconCode) {
		this.iconCode = iconCode;
	}
	
	
}