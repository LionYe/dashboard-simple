package com.harbor.dashboardsimple.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.harbor.dashboardsimple.exception.AppBizException;
import com.harbor.dashboardsimple.exception.AppExcCode;
import com.harbor.dashboardsimple.web.entity.SysPermissionInfo;
import com.harbor.dashboardsimple.web.entity.SysRoleInfo;
import com.harbor.dashboardsimple.web.entity.UserInfo;
import com.harbor.dashboardsimple.web.form.LoginForm;

/**
 * 登录控制器
 * @author harbor
 *
 */
@Controller
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping("/goLogin")
	public ModelAndView goLogin(Locale locale, Model model) {
		if (SecurityUtils.getSubject().isAuthenticated()) {
			return new ModelAndView("redirect:/");
		}
		return new ModelAndView("sign-in");
	}

	@RequestMapping("/doLogin")
	@ResponseBody
	public String doLogin(ModelAndView model, @Valid LoginForm form, BindingResult result) throws AppBizException {

		//后台对表单进行二次验证
		if (result.hasErrors()) {
			List<ObjectError> list =  result.getAllErrors();
			for (ObjectError objectError : list) {
				FieldError error = (FieldError) objectError;
				logger.error(error.getField() + ":" + error.getDefaultMessage());
			}
			//throw new AppBizException(AppExcCode.FORM_ERROR, form.getUsername());
			return AppExcCode.FORM_ERROR_DESC;
		}else{
			try {
				UsernamePasswordToken token = new UsernamePasswordToken(form.getUsername(), form.getPassword());
				SecurityUtils.getSubject().login(token);
			} catch (Exception e) {
				logger.error("[LoginController.doLogin] Error Exception:"+e);
				return AppExcCode.LOGIN_ERROR_DESC;
			}	
			return "success";
		}

	}

	@RequestMapping(value = "/logout")
	public ModelAndView logout(ServletRequest request, ServletResponse response) {
		Subject subjects = SecurityUtils.getSubject();
		if (subjects != null) {
			subjects.logout();
		}
		logger.info(" Wellcome back ! Member has logout !");
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/getShiroRoleInfo", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getShiroRoleInfo() {
		UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		List<String> roleList = new ArrayList<>();
		List<String> permissionList = new ArrayList<>();
		for (SysRoleInfo role : userInfo.getRoleList()) {
			roleList.add(role.getRole());
			for (SysPermissionInfo p : role.getPermissions()) {
				permissionList.add(p.getName());
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("roleList", roleList);
		jsonObject.put("permissionList", permissionList);
		return jsonObject;
	}
}