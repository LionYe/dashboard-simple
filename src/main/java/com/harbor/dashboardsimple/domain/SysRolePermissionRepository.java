package com.harbor.dashboardsimple.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SysRolePermissionRepository extends BaseRepositorys<SysRolePermission, Integer> {

	@Modifying
	@Query("delete from SysRolePermission where roleId=:roleId")
	public void deleteByRoleId(@Param("roleId")Integer roleId);
	
	@Modifying
	@Query("delete from SysRolePermission where permissionId=:permissionId")
	public void deleteByPermissionId(@Param("permissionId")Integer permissionId);
	
}