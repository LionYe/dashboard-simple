package com.harbor.dashboardsimple.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SysUserRoleRepository extends BaseRepositorys<SysUserRole, Integer> {

	@Modifying
	@Query("update SysUserRole set roleId=:roleId   where uid=:uid")
	public void update(@Param("uid")Integer uid,@Param("roleId")Integer roleId);
	
	@Modifying
	@Query("delete from SysUserRole where uid=:uid")
	public void delete(@Param("uid")Integer uid);
}