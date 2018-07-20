package com.harbor.dashboardsimple.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SysPermissionRepository extends BaseRepositorys<SysPermission,Integer>{
	@Modifying
	@Query("delete from SysPermission where id=:id")
	public void delete(@Param("id")Integer id);
	
	@Modifying
	@Query("update SysPermission set pid=:pid, name=:name,type=:type, sort=:sort, description=:description, target=:target, iconCode=:iconCode, url=:url where id=:id")
	public void update(@Param("id")Integer id, @Param("pid")Integer pid, @Param("name")String name, @Param("type")char type, @Param("sort")Integer sort, @Param("description")String description, @Param("target")String target, @Param("iconCode")String iconCode, @Param("url")String url);
	
}