package com.harbor.dashboardsimple;

/**
 * cglib代理测试
 * @author szy
 *
 */
public class CglibProxyTest {
	
	public static void main(String[] args) {
        //目标对象
        UserTest target = new UserTest();

        //代理对象
        UserTest proxy = (UserTest)new ProxyFactoryCglib(target).getProxyInstance();

        //执行代理对象的方法
        proxy.save();
	}
}
