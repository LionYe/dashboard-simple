package com.harbor.dashboardsimple.web.entity;

import java.io.Serializable;


public class SysUserRoleInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Integer id; // 编号
    private Integer roleId;//角色编号
    private Integer uid;//用户ID

    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
   
}