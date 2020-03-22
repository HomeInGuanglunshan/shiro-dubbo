package shiro.dubbo.consumer.shiro.token.manager;

import java.io.Serializable;
import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;

@Service
public class TokenManager4Api implements shiro.dubbo.api.cache.TokenManager {

	@Override
	public void clearUserAuthByUserId(List<Long> userIds) {
		TokenManager.clearUserAuthByUserId(userIds);
	}

	@Override
	public void clearUserAuthByUserId(Long... userIds) {
		TokenManager.clearUserAuthByUserId(userIds);
	}

	@Override
	public Long getUserId(Serializable sessionId) {
		return TokenManager.getUserId(sessionId);
	}

}
