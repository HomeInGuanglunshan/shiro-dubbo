package shiro.dubbo.api.cache;

public interface CustomSessionManager {

	/**
	 * 查询要禁用的用户是否在线。
	 *
	 * @param id
	 *            用户ID
	 * @param status
	 *            用户状态
	 */
	void forbidUserById(Long id, Long status);

}
