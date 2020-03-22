package shiro.dubbo.user.backup.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;

import shiro.dubbo.bean.model.URole;

@Mapper
public interface URoleMapper {
	int deleteByPrimaryKey(Long id);

	int insert(URole record);

	int insertSelective(URole record);

	URole selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(URole record);

	int updateByPrimaryKey(URole record);

	Set<String> findRoleByUserId(Long id);

	List<URole> findNowAllPermission(Map<String, Object> map);

	void initData();
}