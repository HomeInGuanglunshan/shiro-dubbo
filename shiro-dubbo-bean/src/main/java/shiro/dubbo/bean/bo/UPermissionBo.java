package shiro.dubbo.bean.bo;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import shiro.dubbo.bean.model.UPermission;

/**
 * 权限选择
 *
 * @author zhou-baicheng
 *
 */
public class UPermissionBo extends UPermission implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 是否勾选
	 */
	private String marker;
	/**
	 * role Id
	 */
	private String roleId;

	public boolean isCheck() {
		return StringUtils.equals(roleId, marker);
	}

	public String getMarker() {
		return marker;
	}

	public void setMarker(String marker) {
		this.marker = marker;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
