package com.harbor.dashboardsimple.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 角色与菜单关联表
 * @author harbor
 *
 */
@Entity
public class SysRolePermission implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id@GeneratedValue
    private Integer id; // 编号
	private Integer permissionId;
	private Integer roleId;

	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}