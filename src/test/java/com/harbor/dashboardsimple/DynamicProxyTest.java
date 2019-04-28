package com.harbor.dashboardsimple;

/**
 * 动态代理测试类
 * @author szy
 *
 */
public class DynamicProxyTest {

	public static void main(String[] args) {
		UserDao target = new UserDaoImpl();
		
		System.out.println(target.getClass());
		
		UserDao proxy = (UserDao) new ProxyFactory(target).getProxyInstance();
		
		System.out.println(proxy.getClass());
		
		proxy.save();
	}
}
