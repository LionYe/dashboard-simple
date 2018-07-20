package com.harbor.dashboardsimple.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 角色与用户关联表
 * @author harbor
 *
 */
@Entity
public class SysUserRole implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id@GeneratedValue
    private Integer id; // 编号
    private Integer roleId;
    private Integer uid;
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