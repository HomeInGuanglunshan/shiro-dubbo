package shiro.dubbo.permission;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

@EnableDubbo
@SpringBootApplication(scanBasePackages = { "shiro.dubbo" })
@MapperScan("shiro.dubbo.mybatis.dao")
public class PermissionApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(PermissionApplication.class).web(WebApplicationType.NONE).run(args);
	}

}
