package com.harbor.dashboardsimple;

/**
 * 接口实现
 * 目标对象
 */
public class UserDaoImpl implements UserDao{

	@Override
	public void save() {
		System.out.println("测试存储一条用户数据");
	}

}
