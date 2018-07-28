package com.harbor.dashboardsimple.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.harbor.dashboardsimple.util.Page;
import com.harbor.dashboardsimple.util.StringUtil;
import com.harbor.dashboardsimple.web.entity.QueryRequest;
import com.harbor.dashboardsimple.web.entity.QueryResult;
import com.harbor.dashboardsimple.web.entity.SysPermissionInfo;
import com.harbor.dashboardsimple.web.entity.SysRoleInfo;
import com.harbor.dashboardsimple.web.entity.UserInfo;
import com.harbor.dashboardsimple.web.form.SysPermissionForm;
import com.harbor.dashboardsimple.web.service.SysPermissionService;

/**
 * 菜单管理控制器
 * 
 * @author harbor
 *
 */
@Controller
@RequestMapping("/sysPermission")
public class SysPermissionController extends BaseController{
	private static final Logger logger =LoggerFactory.getLogger(SysPermissionController.class);

	@Autowired
	private SysPermissionService sysPermissionService;

	/**
	 * 按用户角色查找其下可查看菜单
	 * 
	 * @return 菜单集合
	 */
	@RequestMapping(value = "/queryPermissionInfo", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject queryPermissionInfo() {
		UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();

		List<SysPermissionInfo> list = new ArrayList<>();
		List<SysPermissionInfo> listSort = new ArrayList<>();

		for (SysRoleInfo role : userInfo.getRoleList()) {
			for (SysPermissionInfo p : role.getPermissions()) {
				if ('F' == p.getType()) {
					list.add(p);
				}
			}
		}

		// 按sort排序
		Collections.sort(list, new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				SysPermissionInfo per1 = (SysPermissionInfo) o1;
				SysPermissionInfo per2 = (SysPermissionInfo) o2;
				return per1.getSort().compareTo(per2.getSort());
			}
		});

		// 二级菜单跟随一级菜单
		for (SysPermissionInfo sysPermissionInfoFirst : list) {
			if (sysPermissionInfoFirst.getPid() == null) {
				listSort.add(sysPermissionInfoFirst);
				for (SysPermissionInfo sysPermissionInfoSecond : list) {
					if (sysPermissionInfoSecond.getPid() == sysPermissionInfoFirst.getId())
						listSort.add(sysPermissionInfoSecond);
				}
			}
		}

		JSONObject permissionInfoJson = new JSONObject();
		permissionInfoJson.put("permissionInfo", listSort);
		return permissionInfoJson;
	}
	
	/**
	 * 查找所有一级菜单
	 * @return
	 */
	@RequestMapping(value = "/findAllFirstPermission", method = RequestMethod.POST)
	@ResponseBody
	public List<SysPermissionInfo> queryFirstPermission() {

		List<SysPermissionInfo> firstSysPermissionInfoList = new ArrayList<>();
		try {
			List<SysPermissionInfo> sysPermissionInfoList = this.sysPermissionService.queryAllInfo();
			for (SysPermissionInfo sysPermissionInfo : sysPermissionInfoList) {
				if (sysPermissionInfo.getPid() == null) {
					firstSysPermissionInfoList.add(sysPermissionInfo);
				}
			}
		} catch (Exception e) {
			logger.error("[SysPermissionController.findAllFirstPermission] Error Exception:" + e);
		}
		return firstSysPermissionInfoList;
	}

	/**
	 * 查找所有二级菜单
	 * @return
	 */
	@RequestMapping(value = "/querySecondPermission", method = RequestMethod.POST)
	@ResponseBody
	public List<SysPermissionInfo> querySecondPermission() {

		List<SysPermissionInfo> SecondSysPermissionInfoList = new ArrayList<>();
		try {
			List<SysPermissionInfo> sysPermissionInfoList = this.sysPermissionService.queryAllInfo();
			for (SysPermissionInfo sysPermissionInfo : sysPermissionInfoList) {
				if (sysPermissionInfo.getPid() != null && sysPermissionInfo.getType() == 'F') {
					SecondSysPermissionInfoList.add(sysPermissionInfo);
				}
			}
		} catch (Exception e) {
			logger.error("[SysPermissionController.findAllFirstPermission] Error Exception:" + e);
		}
		return SecondSysPermissionInfoList;
	}
	
	/**
	 * 通过ID查询菜单/权限具体信息
	 * @param menuId
	 * @return
	 */
	@RequestMapping(value = "/findPermission", method = RequestMethod.POST)
	@ResponseBody
	public SysPermissionInfo queryPermissionById(String menuId) {
		SysPermissionInfo sysPermissionInfo = new SysPermissionInfo();
		try {
			sysPermissionInfo = this.sysPermissionService.querySysPermissionById(menuId);
		} catch (Exception e) {
			logger.error("[SysPermissionController.queryPermissionById] Error Exception:" + e);
		}
		return sysPermissionInfo;
	}
	
	/**
	 * 增加菜单/权限信息
	 * @param sysPermissionForm
	 * @return
	 */
	@RequestMapping(value = "/addPermission", method = RequestMethod.POST)
	@ResponseBody
	public String addPermission(SysPermissionForm sysPermissionForm) {
		try {
			SysPermissionInfo sysPermissionInfo = new SysPermissionInfo();
			if (StringUtil.isNotEmpty(sysPermissionForm.getPid())) {
				sysPermissionInfo.setPid(Integer.valueOf(sysPermissionForm.getPid()));
			}
			sysPermissionInfo.setName(sysPermissionForm.getName());
			sysPermissionInfo.setSort(Integer.valueOf(sysPermissionForm.getSort()));

			sysPermissionInfo.setState('1');
			if (StringUtil.isNotEmpty(sysPermissionForm.getType())) {
				sysPermissionInfo.setType(sysPermissionForm.getType().toCharArray()[0]);				
			}
			if (StringUtil.isNotEmpty(sysPermissionForm.getUrl())) {
				sysPermissionInfo.setUrl(sysPermissionForm.getUrl());				
			}
			if (StringUtil.isNotEmpty(sysPermissionForm.getTarget())) {
				sysPermissionInfo.setTarget(sysPermissionForm.getTarget());				
			}
			if (StringUtil.isNotEmpty(sysPermissionForm.getIconCode())) {
				sysPermissionInfo.setIconCode(sysPermissionForm.getIconCode());				
			}
			sysPermissionInfo.setDescription(sysPermissionForm.getDescription());
			this.sysPermissionService.addSysPermissionInfo(sysPermissionInfo);
			return "success";
		} catch (NumberFormatException e) {
			logger.error("[SysPermissionController.addPermission] Error Exception:" + e);
			return "error";
		} catch (Exception e) {
			logger.error("[SysPermissionController.addPermission] Error Exception:" + e);
			return "error";
		}
	}

	/**
	 * 删除菜单
	 * @param sysPermissionId
	 * @return
	 */
	@RequestMapping(value = "/deletePermission", method = RequestMethod.POST)
	@ResponseBody
	public String delPermission(String sysPermissionId) {
		try {
			this.sysPermissionService.delSysPermissionInfo(sysPermissionId);
			return "success";
		} catch (Exception e) {
			logger.error("[SysPermissionController.delPermission] Error Exception:" + e);
			return "error";
		}
	}
	
	
	/**
	 * 更新菜单信息
	 * @param sysPermissionInfo
	 * @return
	 */
	@RequestMapping(value = "/updatePermission", method = RequestMethod.POST)
	@ResponseBody
	public String updatePermission(SysPermissionInfo sysPermissionInfo) {
		try {
			sysPermissionInfo.setState('1');
			this.sysPermissionService.updateSysPermisssionInfo(sysPermissionInfo);
			return "success";
		} catch (Exception e) {
			logger.error("[SysPermissionController.updatePermission] Error Exception:" + e);
			return "error";
		}
	}

	/**
	 * 查找菜单列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/findPermissionList", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject findPermissionList(HttpServletRequest request) {

		JSONObject jsonObject = new JSONObject();

		try {
			QueryRequest queryRequest = new QueryRequest();
			Page<SysPermissionInfo> queryPage = getPage(request);
			queryRequest.setRequest(request.getParameter("search"));
			queryRequest.setMenu(request.getParameter("menuId"));
			queryRequest.setPermissionType('F');

			QueryResult<SysPermissionInfo> queryResult = this.sysPermissionService.getSysPermissionInfosByPage(queryRequest, queryPage.getPageNo() - 1, queryPage.getPageSize());
			queryPage.setResult(queryResult.getElements());
			queryPage.setTotalCount(queryResult.getCount());

			// 当前页
			jsonObject.put("current", queryPage.getPageNo());
			// 每页多少条数据
			jsonObject.put("rowCount", queryPage.getPageSize());
			// 总数
			jsonObject.put("total", queryPage.getTotalCount());
			// 详细信息
			jsonObject.put("rows", queryPage.getResult());
		} catch (Exception e) {
			logger.error("[SysPermissionController.findPermissionList] Error Exception:" + e);
		}
		return jsonObject;
	}

	/**
	 * 查询所有菜单&&按钮权限集合
	 * @return
	 */
	@RequestMapping(value = "/queryAllSysPermissionInfo", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject queryAllSysPermissionInfo() {

		JSONObject permissionInfoJson = new JSONObject();
		try {
			List<SysPermissionInfo> sysPermissionInfoList = this.sysPermissionService.queryAllInfo();
			List<SysPermissionInfo> firstSysPermissionInfoList = new ArrayList<SysPermissionInfo>();
			for (SysPermissionInfo sysPermissionInfo : sysPermissionInfoList) {
				firstSysPermissionInfoList.add(sysPermissionInfo);
			}

			List<SysPermissionInfo> listSort = new ArrayList<>();
			
			// 按sort排序
			Collections.sort(firstSysPermissionInfoList, new Comparator<Object>() {
				@Override
				public int compare(Object o1, Object o2) {
					SysPermissionInfo per1 = (SysPermissionInfo) o1;
					SysPermissionInfo per2 = (SysPermissionInfo) o2;
					return per1.getSort().compareTo(per2.getSort());
				}
			});

			// 二级菜单跟随一级菜单
			for (SysPermissionInfo sysPermissionInfoFirst : firstSysPermissionInfoList) {
				if (sysPermissionInfoFirst.getPid() == null) {
					listSort.add(sysPermissionInfoFirst);
					for (SysPermissionInfo sysPermissionInfoSecond : firstSysPermissionInfoList) {
						if (sysPermissionInfoSecond.getPid() == sysPermissionInfoFirst.getId())
							listSort.add(sysPermissionInfoSecond);
					}
				}
			}
			
			//将排序完的按钮添加至集合
			for (SysPermissionInfo sysPermissionInfoButton : firstSysPermissionInfoList) {
				if(sysPermissionInfoButton.getType() == 'O'){
					listSort.add(sysPermissionInfoButton);
				}	
			}
			permissionInfoJson.put("permissionInfo", listSort);
		} catch (Exception e) {
			logger.error("[SysPermissionController.queryAllSysPermissionInfo] Error Exception:" + e);
		}
		return permissionInfoJson;
	}
	
	/**
	 * 加载所有权限资源
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/querySysList", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject querySysList(HttpServletRequest request){
		
		JSONObject jsonObject = new JSONObject();
		try {
			QueryRequest queryRequest = new QueryRequest();
			Page<SysPermissionInfo> queryPage = getPage(request);
			queryRequest.setRequest(request.getParameter("search"));
			queryRequest.setMenu(request.getParameter("menuId"));
			
			QueryResult<SysPermissionInfo> queryResult = this.sysPermissionService.getSysInfosByPage(queryRequest, queryPage.getPageNo() - 1, queryPage.getPageSize());

			queryPage.setResult(queryResult.getElements());
			queryPage.setTotalCount(queryResult.getCount());
			
			// 当前页
			jsonObject.put("current", queryPage.getPageNo());
			// 每页多少条数据
			jsonObject.put("rowCount", queryPage.getPageSize());
			// 总数
			jsonObject.put("total", queryPage.getTotalCount());
			// 详细信息
			jsonObject.put("rows", queryPage.getResult());
		} catch (Exception e) {
			logger.error("[SysPermissionController.querySysList] Error Exception:" + e);
		}	     
        return jsonObject;
	}
	
	
}
