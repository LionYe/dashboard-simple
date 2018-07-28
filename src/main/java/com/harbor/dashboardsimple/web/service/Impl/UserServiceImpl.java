package com.harbor.dashboardsimple.web.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.harbor.dashboardsimple.domain.SysPermission;
import com.harbor.dashboardsimple.domain.SysRole;
import com.harbor.dashboardsimple.domain.SysRoleRepository;
import com.harbor.dashboardsimple.domain.SysUserRole;
import com.harbor.dashboardsimple.domain.SysUserRoleRepository;
import com.harbor.dashboardsimple.domain.User;
import com.harbor.dashboardsimple.domain.UserRepository;
import com.harbor.dashboardsimple.domain.criteria.Criteria;
import com.harbor.dashboardsimple.domain.criteria.Restrictions;
import com.harbor.dashboardsimple.domain.criteria.SimpleExpression;
import com.harbor.dashboardsimple.util.CollectionUtil;
import com.harbor.dashboardsimple.util.StringUtil;
import com.harbor.dashboardsimple.web.entity.SysPermissionInfo;
import com.harbor.dashboardsimple.web.entity.SysRoleInfo;
import com.harbor.dashboardsimple.web.entity.SysUserRoleInfo;
import com.harbor.dashboardsimple.web.entity.UserInfo;
import com.harbor.dashboardsimple.web.service.UserService;



@Service("userService")
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SysUserRoleRepository sysUserRoleRepository;
	@Autowired
	private SysRoleRepository sysRoleRepository;


	@Override
	public UserInfo login(String username, String password) {
		Criteria<User> c = new Criteria<User>();
		c.add(Restrictions.eq("username", username, false));
		c.add(Restrictions.eq("password", password, false));
		User user = this.userRepository.findOne(c);

		UserInfo userInfo = null;
		if (user != null) {
			userInfo = new UserInfo();
			BeanUtils.copyProperties(user, userInfo);
			// 数据库对象转化数据传输对象
			if (CollectionUtil.isNotEmpty(user.getRoleList())) {
				List<SysRoleInfo> sysRoleInfos = new ArrayList<SysRoleInfo>();
				for (SysRole sysRole : user.getRoleList()) {
					SysRoleInfo sysRoleInfo = new SysRoleInfo();
					BeanUtils.copyProperties(sysRole, sysRoleInfo);
					if (CollectionUtil.isNotEmpty(sysRole.getPermissions())) {
						List<SysPermissionInfo> sysPermissionInfos = new ArrayList<SysPermissionInfo>();
						for (SysPermission sysPermission : sysRole.getPermissions()) {
							SysPermissionInfo sysPermissionInfo = new SysPermissionInfo();
							BeanUtils.copyProperties(sysPermission, sysPermissionInfo);
							sysPermissionInfos.add(sysPermissionInfo);
						}
						sysRoleInfo.setPermissions(sysPermissionInfos);
					}
					sysRoleInfos.add(sysRoleInfo);
				}
				userInfo.setRoleList(sysRoleInfos);
			}
		}
		return userInfo;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserInfo addUserInfo(UserInfo userInfo, String userRoles) throws Exception {
		User user = new User();
		if (userInfo != null) {
			BeanUtils.copyProperties(userInfo, user);
		}

		User userAdd = this.userRepository.save(user);

		String[] roleArgs;
		roleArgs = userRoles.split(",");
		for (int i = 0; i < roleArgs.length; i++) {

			SysUserRoleInfo sysUserRoleInfo = new SysUserRoleInfo();
			sysUserRoleInfo.setUid(userAdd.getUid());
			sysUserRoleInfo.setRoleId(Integer.valueOf(roleArgs[i]));

			this.addSysUserRole(sysUserRoleInfo);
		}
		return userInfo;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addSysUserRole(SysUserRoleInfo sysUserRoleInfo) throws Exception {
		SysUserRole sysUserRole = new SysUserRole();
		BeanUtils.copyProperties(sysUserRoleInfo, sysUserRole);

		this.sysUserRoleRepository.save(sysUserRole);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delUserInfo(String uid) throws Exception {

		Integer userId = Integer.valueOf(uid);

		this.userRepository.delete(userId);
		this.sysUserRoleRepository.delete(userId);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateUserInfo(UserInfo userInfo, List<String> roleList) throws Exception {
		Integer uid = userInfo.getUid();
		String name = userInfo.getName();
		String username = userInfo.getUsername();
		String phoneNumber = userInfo.getPhoneNumber();
		String email = userInfo.getEmail();
		String department = userInfo.getDepartment();
		String password = userInfo.getPassword();

		this.userRepository.update(uid, name, username, phoneNumber, email, department,password);
		if(roleList != null){
			this.sysUserRoleRepository.delete(uid);
			for (String rid : roleList) {
				Integer roleId = Integer.valueOf(rid);
				SysUserRole sysUserRole = new SysUserRole();
				sysUserRole.setRoleId(roleId);
				sysUserRole.setUid(uid);
				this.sysUserRoleRepository.save(sysUserRole);
			}

		}
		
	}

	@Override
	public UserInfo queryUserByUsername(String username) throws Exception {
		Criteria<User> criteria = new Criteria<User>();
		criteria.add(Restrictions.eq("username", username, false));

		User user = new User();
		user = this.userRepository.findOne(criteria);

		List<SysRoleInfo> roleList = new ArrayList<SysRoleInfo>();

		for (SysUserRole sysUserRole : this.sysUserRoleRepository.findAll()) {
			if (sysUserRole.getUid() == user.getUid()) {
				Criteria<SysRole> roleCriteria = new Criteria<SysRole>();
				roleCriteria.add(Restrictions.eq("id", sysUserRole.getRoleId(), false));

				SysRole sysRole = new SysRole();
				sysRole = this.sysRoleRepository.findOne(roleCriteria);
				SysRoleInfo sysRoleInfo = new SysRoleInfo();
				BeanUtils.copyProperties(sysRole, sysRoleInfo);

				roleList.add(sysRoleInfo);
			}
		}

		UserInfo userInfo = new UserInfo();
		BeanUtils.copyProperties(user, userInfo);
		userInfo.setRoleList(roleList);

		return userInfo;
	}

//	@Override
//	public QueryResult<UserInfo> getUserInfosByPage(QueryRequest request, int startRow, int maxRow) throws Exception {
//		QueryResult<UserInfo> queryResult = null;
//		Criteria<User> criteria = new Criteria<User>();
//		if (StringUtil.isNotEmpty(request.getRequest())) {
//			SimpleExpression criterion1 = Restrictions.like("username", request.getRequest(), false);
//			SimpleExpression criterion2 = Restrictions.like("name", request.getRequest(), false);
//			criteria.add(Restrictions.or(criterion1, criterion2));
//		}
//		if (StringUtil.isNotEmpty(request.getDepartment())) {
//			criteria.add(Restrictions.eq("department", request.getDepartment(), false));
//		}
//
//		Pageable pageable = new PageRequest(startRow, maxRow, Sort.Direction.DESC, "uid");
//		Page<User> users = this.userRepository.findAll(criteria, pageable);
//		if (users != null) {
//			queryResult = new QueryResult<UserInfo>();
//			List<UserInfo> userInfos = new ArrayList<UserInfo>();
//			UserInfo userInfo = null;
//			for (User user : users) {
//				userInfo = new UserInfo();
//				List<SysRoleInfo> sysInfoList = new ArrayList<SysRoleInfo>();
//				if (user.getRoleList() != null) {
//					for (SysRole sysRole : user.getRoleList()) {
//						SysRoleInfo sysRoleInfo = new SysRoleInfo();
//						BeanUtils.copyProperties(sysRole, sysRoleInfo);
//						sysInfoList.add(sysRoleInfo);
//					}
//				}
//				BeanUtils.copyProperties(user, userInfo); // bootgrid
//				userInfo.setRoleList(sysInfoList); // ajax默认读取rolelist对象
//				// 有序列化警告
//				userInfos.add(userInfo);
//			}
//			queryResult.setElements(userInfos);
//			queryResult.setCount(users.getTotalElements());
//		}
//		return queryResult;
//	}

	@Override
	public List<UserInfo> queryUserInfosByRole(String role) throws Exception {
		List<User> userListByRole = new ArrayList<>();
		List<User> userList = (List<User>) this.userRepository.findAll();
		for (User user : userList) {
			Boolean isRolePerson = false;
			List<SysRole> sysRoleList = user.getRoleList();
			for (SysRole sysRole : sysRoleList) {
				if (sysRole.getRole().equals(role)) {
					isRolePerson = true;
					break; // 角色集合中找到相应集合即终止查找
				}
			}
			if (isRolePerson) {
				userListByRole.add(user);
			}
		}
		List<UserInfo> userInfoListByRole = new ArrayList<>();
		if (userListByRole != null) {
			UserInfo userInfo = null;
			for (User user : userListByRole) {
				userInfo = new UserInfo();
				userInfo.setName(user.getName());
				userInfo.setUid(user.getUid());
				userInfo.setUsername(user.getUsername());
				userInfo.setPhoneNumber(user.getPhoneNumber());
				userInfo.setEmail(user.getEmail());
				userInfo.setDepartment(user.getDepartment());
				// BeanUtils.copyProperties(user, userInfo); bootgrid
				// ajax默认读取rolelist对象 有序列化警告
				userInfoListByRole.add(userInfo);
			}

		}
		return userInfoListByRole;
	}

	@Override
	public void updateUserInfo(UserInfo userInfo) throws Exception {
		
		User user = new User();
		if(userInfo != null){
			BeanUtils.copyProperties(userInfo, user);
		}
		this.userRepository.save(user);
		
	}

}
