package shiro.dubbo.user.backup.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import shiro.dubbo.bean.model.URolePermission;

@Mapper
public interface URolePermissionMapper {
	int insert(URolePermission record);

	int insertSelective(URolePermission record);

	List<URolePermission> findRolePermissionByPid(Long id);

	List<URolePermission> findRolePermissionByRid(Long id);

	List<URolePermission> find(URolePermission entity);

	int deleteByPid(Long id);

	int deleteByRid(Long id);

	int delete(URolePermission entity);

	int deleteByRids(Map<String, Object> resultMap);
}