package shiro.dubbo.permission.backup;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

@EnableDubbo
@SpringBootApplication(scanBasePackages = { "shiro.dubbo" })
@MapperScan("shiro.dubbo.permission.backup.dao")
public class PermissionBackupApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(PermissionBackupApplication.class).web(WebApplicationType.NONE).run(args);
	}

}
