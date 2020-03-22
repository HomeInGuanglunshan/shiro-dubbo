package shiro.dubbo.permission.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations = { "dubbo-api.xml" })
public class DubboConfig {
//public class DubboConfig implements EnvironmentAware {

//	@Autowired
//	private RegistryConfig registryConfig; //保存配置对象避免重复创建
//
//	@Autowired
//	private ApplicationConfig applicationConfig; //保存配置对象避免重复创建
//
//	@Override
//	public void setEnvironment(Environment environment) {
//		applicationConfig.setName("dubbo-config");
//		registryConfig = new RegistryConfig(environment.getProperty("dubbo.registry.address"));
//	}
//
//	private <T> T getReference(Class<T> c) { //通用获取代理对象方法
//		ReferenceConfig<T> reference = new ReferenceConfig<>();
//		reference.setApplication(applicationConfig);
//		reference.setRegistry(registryConfig);
//		reference.setInterface(c);
//		return reference.get();
//	}
//
//	@Bean
//	public AnnotationBean annotationBean() {
//		AnnotationBean annotationBean = new AnnotationBean();
//		annotationBean.setPackage("shiro.dubbo.api.*");
//		return annotationBean;
//	}
//
//	@Bean
//	public UUserService userService() {
//		return getReference(UUserService.class);
//	}
//
//	@Bean
//	public RoleService roleService() {
//		return getReference(RoleService.class);
//	}
//
//	@Bean
//	public PermissionService permissionService() {
//		return getReference(PermissionService.class);
//	}
}
