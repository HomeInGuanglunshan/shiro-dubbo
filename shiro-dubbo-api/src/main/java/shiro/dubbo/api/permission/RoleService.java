package shiro.dubbo.api.permission;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import shiro.dubbo.bean.bo.RolePermissionAllocationBo;
import shiro.dubbo.bean.model.URole;
import shiro.dubbo.common.page.Pagination;

public interface RoleService {

	int deleteByPrimaryKey(Long id);

	int insert(URole record);

	int insertSelective(URole record);

	URole selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(URole record);

	int updateByPrimaryKey(URole record);

	Pagination<URole> findPage(Map<String, Object> resultMap, Integer pageNo, Integer pageSize);

	Map<String, Object> deleteRoleById(String ids);

	Pagination<RolePermissionAllocationBo> findRoleAndPermissionPage(Map<String, Object> resultMap, Integer pageNo,
			Integer pageSize);

	//根据用户ID查询角色（role），放入到Authorization里。
	Set<String> findRoleByUserId(Long userId);

	List<URole> findNowAllPermission(Serializable sessionId);

	//初始化数据
	void initData();
}
