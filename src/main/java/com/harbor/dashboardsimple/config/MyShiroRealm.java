package com.harbor.dashboardsimple.config;


import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.harbor.dashboardsimple.exception.AppExcCode;
import com.harbor.dashboardsimple.util.MD5Util;
import com.harbor.dashboardsimple.web.entity.SysPermissionInfo;
import com.harbor.dashboardsimple.web.entity.SysRoleInfo;
import com.harbor.dashboardsimple.web.entity.UserInfo;
import com.harbor.dashboardsimple.web.service.UserService;



public class MyShiroRealm extends AuthorizingRealm {
	private static final Logger logger =LoggerFactory.getLogger(MyShiroRealm.class);
	@Autowired
	private UserService userServiceImpl;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        UserInfo userInfo  = (UserInfo)principals.getPrimaryPrincipal();
        for(SysRoleInfo role:userInfo.getRoleList()){
        	logger.info("================>role:"+role.getRole());
            authorizationInfo.addRole(role.getRole());
            for(SysPermissionInfo p:role.getPermissions()){
            	logger.info("================>permission:"+p.getName());
                authorizationInfo.addStringPermission(p.getName());
            }
        }
        return authorizationInfo;
    }

    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
            throws AuthenticationException {
        	UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        	String username=token.getUsername();
        	String password=new String(token.getPassword());
        	
        	UserInfo userInfo = this.userServiceImpl.login(token.getUsername(), MD5Util.toMd5Hex32(password));
        	if(userInfo==null){
        		throw new AuthenticationException(AppExcCode.LOGIN_ERROR);
        	}
        	SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
        				userInfo,
        				userInfo.getPassword(),
        				username
        			);
        return authenticationInfo;
    }
    
	/**
	 * 清除认证缓存
	 */
	@Override
	protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		// 清除授权缓存
		super.clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清除授权缓存
	 */
	@Override
	protected void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		// 清除认证缓存
		SimplePrincipalCollection spc = new SimplePrincipalCollection(((UserInfo) principals.getPrimaryPrincipal()).getUsername(),getName());
		super.clearCachedAuthenticationInfo(spc);
	}
}