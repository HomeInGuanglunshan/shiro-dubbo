package shiro.dubbo.consumer.shiro.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.ValidatingSessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.Ordered;
import org.springframework.web.filter.DelegatingFilterProxy;

import shiro.dubbo.consumer.shiro.CustomShiroSessionDAO;
import shiro.dubbo.consumer.shiro.cache.JedisShiroSessionRepository;
import shiro.dubbo.consumer.shiro.cache.impl.CustomShiroCacheManager;
import shiro.dubbo.consumer.shiro.filter.KickoutSessionFilter;
import shiro.dubbo.consumer.shiro.filter.LoginFilter;
import shiro.dubbo.consumer.shiro.filter.PermissionFilter;
import shiro.dubbo.consumer.shiro.filter.RoleFilter;
import shiro.dubbo.consumer.shiro.filter.SimpleAuthFilter;
import shiro.dubbo.consumer.shiro.listenter.CustomSessionListener;
import shiro.dubbo.consumer.shiro.token.SampleRealm;

@Configuration
public class ShiroConfig {

	public SimpleCookie sessionIdCookie() {
		SimpleCookie cookie = new SimpleCookie("v_v-s-baidu");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(-1);
		return cookie;
	}

	public SimpleCookie rememberMeCookie() {
		SimpleCookie cookie = new SimpleCookie("v_v-re-baidu");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(2592000);
		return cookie;
	}

	public CookieRememberMeManager rememberMeManager() {
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		//rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位) //3AvVhmFLUs0KTA3Kprsdag==
		cookieRememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
		return cookieRememberMeManager;
	}

	@Bean
	public CustomShiroSessionDAO getCustomShiroSessionDAO() {
		return new CustomShiroSessionDAO();
	}

	@Bean
	public SessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		Collection<SessionListener> listeners = new ArrayList<SessionListener>();
		listeners.add(new CustomSessionListener());
		sessionManager.setSessionListeners(listeners);
		sessionManager.setGlobalSessionTimeout(1800000); //全局会话超时时间（单位毫秒），默认30分钟
		sessionManager.setSessionDAO(getCustomShiroSessionDAO());
		sessionManager.setDeleteInvalidSessions(true);
		sessionManager.setSessionValidationSchedulerEnabled(true);
		//定时清理失效会话, 清理用户直接关闭浏览器造成的孤立会话
		sessionManager.setSessionValidationInterval(1800000);
		// executorServiceSessionValidationScheduler()和sessionManager()互相依赖，导致项目无法启动
//		sessionManager.setSessionValidationScheduler(executorServiceSessionValidationScheduler());
		sessionManager.setSessionIdCookieEnabled(true);
		sessionManager.setSessionIdCookie(sessionIdCookie());
		return sessionManager;
	}

	@Bean
	public ExecutorServiceSessionValidationScheduler executorServiceSessionValidationScheduler() {
		ExecutorServiceSessionValidationScheduler sessionValidationScheduler = new ExecutorServiceSessionValidationScheduler();
		//会话验证调度器，每30分钟执行一次验证
//		sessionValidationScheduler.setInterval(1800000);
		sessionValidationScheduler.setInterval(18000000);
		sessionValidationScheduler.setSessionManager((ValidatingSessionManager) sessionManager());
		return sessionValidationScheduler;
	}

	@Bean("sampleRealm")
	public SampleRealm sampleRealm() {
		return new SampleRealm();
	}

	@Bean
	public CustomShiroCacheManager getCustomShiroCacheManager() {
		return new CustomShiroCacheManager();
	}

	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置realm.
		securityManager.setRealm(sampleRealm());
		//注入记住我管理器;
		securityManager.setRememberMeManager(rememberMeManager());
		securityManager.setCacheManager(getCustomShiroCacheManager()); //缓存管理器
		securityManager.setSessionManager(sessionManager());
		return securityManager;
	}

	/**
	 * 相当于调用SecurityUtils.setSecurityManager(securityManager)
	 *
	 * @param securityManager
	 * @return
	 */
	@Bean
	public MethodInvokingFactoryBean getMethodInvokingFactoryBean() {
		MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
		methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
		methodInvokingFactoryBean.setArguments(securityManager());
		return methodInvokingFactoryBean;
	}

	// Shiro生命周期处理器
	@Bean("lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * 相当于调用SecurityUtils.setSecurityManager(securityManager)
	 *
	 * @param securityManager
	 * @return
	 */
	@Bean
	public MethodInvokingFactoryBean getMethodInvokingFactoryBean(
			@Qualifier("securityManager") SecurityManager securityManager) {
		MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
		methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
		methodInvokingFactoryBean.setArguments(securityManager);
		return methodInvokingFactoryBean;
	}

	@Bean
	public MethodInvokingFactoryBean injectJedisShiroSessionRepositoryIntoKickoutSessionFilter(
			@Qualifier("jedisShiroSessionRepository") JedisShiroSessionRepository jedisShiroSessionRepository) {
		MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
		methodInvokingFactoryBean
				.setStaticMethod("shiro.dubbo.consumer.shiro.filter.KickoutSessionFilter.setShiroSessionRepository");
		methodInvokingFactoryBean.setArguments(jedisShiroSessionRepository);
		return methodInvokingFactoryBean;
	}

	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilterFactoryBean() {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		shiroFilterFactoryBean.setSecurityManager(securityManager());

		shiroFilterFactoryBean.setLoginUrl("/u/login.shtml");
		shiroFilterFactoryBean.setSuccessUrl("/");
		shiroFilterFactoryBean.setUnauthorizedUrl("/?login");

		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

		filterChainDefinitionMap.put("/u/**", "anon");
		filterChainDefinitionMap.put("/user/**", "kickout,simple,login");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/open/**", "anon");

		filterChainDefinitionMap.put("/permission/selectPermissionById.shtml", "kickout,simple,login");
		filterChainDefinitionMap.put("/member/onlineDetails/**", "kickout,simple,login");
		filterChainDefinitionMap.put("/role/mypermission.shtml", "kickout,simple,login");
		filterChainDefinitionMap.put("/role/getPermissionTree.shtml", "kickout,simple,login");
		filterChainDefinitionMap.put("/role/selectRoleByUserId.shtml", "kickout,simple,login");

		filterChainDefinitionMap.put("/permission/**", "kickout,simple,login,permission");
		filterChainDefinitionMap.put("/role/**", "kickout,simple,login,permission");
		filterChainDefinitionMap.put("/member/**", "kickout,simple,login,permission");

		filterChainDefinitionMap.put("/**", "simple,login");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		Map<String, Filter> filters = new HashMap<>();

		filters.put("login", new LoginFilter());
		filters.put("role", new RoleFilter());
		filters.put("simple", new SimpleAuthFilter());
		filters.put("permission", new PermissionFilter());
		filters.put("kickout", new KickoutSessionFilter());

		shiroFilterFactoryBean.setFilters(filters);

		return shiroFilterFactoryBean;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean
	@DependsOn({ "shiroFilter" })
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new DelegatingFilterProxy());
		registrationBean.addUrlPatterns("*.shtml");
		//被代理filter
		registrationBean.addInitParameter("targetBeanName", "shiroFilter");
		//指明作用于filter的所有生命周期
		registrationBean.addInitParameter("targetFilterLifecycle", "true");
		registrationBean.setName("shiroFilter");
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		registrationBean.setEnabled(true);
		return registrationBean;
	}
}
