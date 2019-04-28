package com.harbor.dashboardsimple;

/**
 * 代理对象,静态代理
 */
public class UserDaoProxyImpl implements UserDao{

	private UserDao target;
	
	public UserDaoProxyImpl(UserDao target) {
		this.target = target;
	}

	@Override
	public void save() {
		System.out.println("开始事务");
		target.save();
		System.out.println("提交事务");
	}

}
