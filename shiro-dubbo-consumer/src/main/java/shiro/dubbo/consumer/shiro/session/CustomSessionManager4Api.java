package shiro.dubbo.consumer.shiro.session;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;

@Service
public class CustomSessionManager4Api implements shiro.dubbo.api.cache.CustomSessionManager {

	@Autowired
	CustomSessionManager customSessionManager;

	@Override
	public void forbidUserById(Long id, Long status) {
		customSessionManager.forbidUserById(id, status);
	}

}
