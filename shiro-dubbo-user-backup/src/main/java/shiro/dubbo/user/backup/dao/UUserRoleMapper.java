package shiro.dubbo.user.backup.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import shiro.dubbo.bean.model.UUserRole;

@Mapper
public interface UUserRoleMapper {
	int insert(UUserRole record);

	int insertSelective(UUserRole record);

	int deleteByUserId(Long id);

	int deleteRoleByUserIds(Map<String, Object> resultMap);

	List<Long> findUserIdByRoleId(Long id);
}