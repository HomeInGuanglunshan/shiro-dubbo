package shiro.dubbo.user.backup.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import shiro.dubbo.bean.bo.URoleBo;
import shiro.dubbo.bean.model.UUser;

@Mapper
public interface UUserMapper {

	int deleteByPrimaryKey(Long id);

	int insert(UUser record);

	int insertSelective(UUser record);

	UUser selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(UUser record);

	int updateByPrimaryKey(UUser record);

	UUser login(Map<String, Object> map);

	UUser findUserByEmail(String email);

	List<URoleBo> selectRoleByUserId(Long id);

}