package shiro.dubbo.user.backup;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

@EnableDubbo
@SpringBootApplication
public class UserBackupApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(UserBackupApplication.class).web(WebApplicationType.NONE).run(args);
	}

}
