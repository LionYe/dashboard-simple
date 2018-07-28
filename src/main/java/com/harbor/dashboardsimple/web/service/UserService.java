package com.harbor.dashboardsimple.web.service;

import java.util.List;

import com.harbor.dashboardsimple.web.entity.SysUserRoleInfo;
import com.harbor.dashboardsimple.web.entity.UserInfo;


public interface UserService {
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	public UserInfo login(String username,String password);
	/**
	 * 添加用户
	 * @param userInfo
	 * @param userRoles
	 * @return
	 */
	public UserInfo addUserInfo(UserInfo userInfo, String userRoles) throws Exception;
	/**
	 * 添加用户角色
	 * @param sysUserRoleInfo
	 */
	public void addSysUserRole(SysUserRoleInfo sysUserRoleInfo) throws Exception;
	/**
	 * 删除用户角色
	 * @param uid
	 */
	public void delUserInfo(String uid) throws Exception;
	/**
	 * 更新用户信息
	 * @param userInfo
	 * @param roleList
	 */
    public void updateUserInfo(UserInfo userInfo, List<String> roleList) throws Exception;
	/**
	 * 通过用户名查询用户
	 * @param username
	 * @return
	 */
	public UserInfo  queryUserByUsername(String username) throws Exception;
//	/**
//	 * 获取用户列表
//	 * @param request
//	 * @param startRow
//	 * @param maxRow
//	 * @return
//	 */
//	public QueryResult<UserInfo> getUserInfosByPage(QueryRequest request, int startRow,int maxRow) throws Exception;

	/**
	 * 查找拥有对应角色的用户
	 * @param role
	 * @return
	 */
	public List<UserInfo> queryUserInfosByRole(String role) throws Exception;	
	
	/**
	 * 修改用户信息
	 * @param userInfo
	 * @throws Exception
	 */
	public void updateUserInfo(UserInfo userInfo) throws Exception;
}
