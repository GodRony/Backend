package com.codingrecipe.board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private String resourcePath = "/upload/**"; // view 에서 접근할 경로
    private String savePath = "file:///C:/springboot_img/"; // 실제 파일 저장 경로(win)
//    private String savePath = "file:///Users/사용자이름/springboot_img/"; // 실제 파일 저장 경로(mac)

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(resourcePath)
                .addResourceLocations(savePath);
    }
}
// addResourceHandler(resourcePath):
// 웹에서 접근할 경로를 설정하는 부분. 예를 들어, /upload/**로 요청이 오면 이 경로에 대응되는 리소스를 찾겠다는 뜻
// addResourceLocations(savePath) :
// 실제로 서버의 어떤 경로에 저장되는지 설정합니다. 즉, /upload/**로 요청이 오면 savePath에서 해당 파일을 찾음.
