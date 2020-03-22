package shiro.dubbo.api.cache;

import java.io.Serializable;
import java.util.List;

public interface TokenManager {

	/**
	 * 根据UserIds 清空权限信息。
	 *
	 * @param id
	 *            用户ID
	 */
	void clearUserAuthByUserId(List<Long> userIds);

	/**
	 * 根据UserIds 清空权限信息。
	 *
	 * @param id
	 *            用户ID
	 */
	void clearUserAuthByUserId(Long... userIds);

	/**
	 * 获取当前用户ID
	 *
	 * @param sessionId
	 * @return
	 */
	Long getUserId(Serializable sessionId);
}
