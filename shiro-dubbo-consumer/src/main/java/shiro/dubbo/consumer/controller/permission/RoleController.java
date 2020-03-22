package shiro.dubbo.consumer.controller.permission;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;

import shiro.dubbo.api.permission.RoleService;
import shiro.dubbo.bean.model.URole;
import shiro.dubbo.common.controller.BaseController;
import shiro.dubbo.common.page.Pagination;
import shiro.dubbo.common.utils.LoggerUtils;
import shiro.dubbo.consumer.controller.user.manager.UserManager;

/**
 *
 * 开发公司：itboy.net<br/>
 * 版权：itboy.net<br/>
 * <p>
 *
 * 用户角色管理
 *
 * <p>
 *
 * 区分 责任人 日期 说明<br/>
 * 创建 周柏成 2016年5月26日 <br/>
 * <p>
 * *******
 * <p>
 *
 * @author zhou-baicheng
 * @email i@itboy.net
 * @version 1.0,2016年5月26日 <br/>
 *
 */
@Controller
//如果为prototype，项目在启动时没有向zookeeper注册的行为
//@Scope(value = "prototype")
@RequestMapping("role")
public class RoleController extends BaseController {

	@Reference
	RoleService roleService;

	/**
	 * 角色列表
	 *
	 * @return
	 */
	@RequestMapping(value = "index")
	public ModelAndView index(String findContent, ModelMap modelMap) {
		modelMap.put("findContent", findContent);
		Pagination<URole> role = roleService.findPage(modelMap, pageNo, pageSize);
		return new ModelAndView("role/index", "page", role);
	}

	/**
	 * 角色添加
	 *
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "addRole", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addRole(URole role) {
		try {
			int count = roleService.insertSelective(role);
			resultMap.put("status", 200);
			resultMap.put("successCount", count);
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "添加失败，请刷新后再试！");
			LoggerUtils.fmtError(getClass(), e, "添加角色报错。source[%s]", role.toString());
		}
		return resultMap;
	}

	/**
	 * 删除角色，根据ID，但是删除角色的时候，需要查询是否有赋予给用户，如果有用户在使用，那么就不能删除。
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "deleteRoleById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteRoleById(String ids) {
		return roleService.deleteRoleById(ids);
	}

	/**
	 * 我的权限页面
	 *
	 * @return
	 */
	@RequestMapping(value = "mypermission", method = RequestMethod.GET)
	public ModelAndView mypermission() {
		return new ModelAndView("permission/mypermission");
	}

	/**
	 * 我的权限 bootstrap tree data
	 *
	 * @return
	 */
	@RequestMapping(value = "getPermissionTree", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getPermissionTree() {
		Serializable sessionId = SecurityUtils.getSubject().getSession().getId();
		//查询我所有的角色 ---> 权限
		List<URole> roles = roleService.findNowAllPermission(sessionId);
		//把查询出来的roles 转换成bootstarp 的 tree数据
		List<Map<String, Object>> data = UserManager.toTreeData(roles);
		return data;
	}
}
