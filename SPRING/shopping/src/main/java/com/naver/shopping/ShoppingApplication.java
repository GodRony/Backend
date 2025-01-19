package com.naver.shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @SpringBootApplication**은 Spring Boot 애플리케이션의 진입점을 나타내는 어노테이션입니다.
// 이는 단지 애플리케이션의 설정을 나타내는 것이 아니라, 여러 중요한 설정을 포함하고 있습니다.
// 이 어노테이션은 @Configuration, @EnableAutoConfiguration, **@ComponentScan**을 조합한것.
public class ShoppingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingApplication.class, args);
	}

}
