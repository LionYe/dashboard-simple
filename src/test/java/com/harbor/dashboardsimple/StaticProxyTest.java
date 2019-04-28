package com.harbor.dashboardsimple;


/**
 * 静态代理测试类
 * @author szy
 *
 */
public class StaticProxyTest {
	
	public static void main(String[] args) {
	    //目标对象
	    UserDaoImpl target = new UserDaoImpl();

	    //代理对象,把目标对象传给代理对象,建立代理关系
	    UserDaoProxyImpl proxy = new UserDaoProxyImpl(target);

	    proxy.save();
	}  
}
