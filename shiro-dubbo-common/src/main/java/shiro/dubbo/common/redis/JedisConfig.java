package shiro.dubbo.common.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class JedisConfig {

	@Bean
	public JedisPoolConfig getJedisPoolConfig() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(100);
		poolConfig.setMinIdle(10);
		poolConfig.setTestOnBorrow(true);
		return poolConfig;
	}

	@Bean
	public JedisPool getJedisPool() {
		return new JedisPool(getJedisPoolConfig(), "127.0.0.1", 6379, 5000);
	}
}
