package com.harbor.dashboardsimple.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends  BaseRepositorys<User, Integer>{
	@Modifying
	@Query("delete from User where uid=:uid")
	public void delete(@Param("uid")Integer uid);
	
	@Modifying
	@Query("update User set name=:name,username=:username, phoneNumber=:phoneNumber, email=:email, department=:department, password=:password where uid=:uid")
	public void update(@Param("uid")Integer uid,@Param("name")String name, @Param("username")String username, @Param("phoneNumber")String phoneNumber,
			@Param("email")String email,@Param("department")String department,@Param("password")String password);
	   
}