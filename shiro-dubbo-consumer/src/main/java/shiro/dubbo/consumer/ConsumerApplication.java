package shiro.dubbo.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

@EnableDubbo
// 如果不指定scanBasePackages，貌似依赖模块中的bean会扫描不到
@SpringBootApplication(scanBasePackages = "shiro.dubbo")
public class ConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}

}
