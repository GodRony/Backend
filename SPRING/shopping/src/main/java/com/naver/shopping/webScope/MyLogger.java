package com.naver.shopping.webScope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.ScopedProxyMode;


import java.util.UUID;
//  UUID : 전 세계적으로 고유한 식별자를 의미
//  고유 식별자: 각 MyLogger 객체가 고유하게 식별될 수 있도록 합니다.
//  이를 통해 로그 출력 시 어느 요청에서 온 로그인지 쉽게 구분할 수 있습니다.
//  요청 추적: uuid는 요청마다 고유하므로, 특정 요청에 대한 로그를 추적할 때 유용
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
// @Component: 일반적인 빈을 정의할 때 사용.
// @Scope: 빈의 생명 주기와 범위를 설정하는 어노테이션 (singleton, prototype, request, session 등).
// 기본은 싱글톤임.
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "] [" + requestURL + "] " +message);
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create:" + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close:" + this);
    }
}
// ########## 주요 Scope값 #########
//  request:
//  웹 애플리케이션에서 HTTP 요청이 있을 때마다 새로운 인스턴스가 생성됩니다.
//  각 HTTP 요청마다 별도의 빈이 생성되며, 요청 처리 완료 후 소멸됩니다.
//
//  session:
//  웹 애플리케이션에서 HTTP 세션마다 하나의 빈 인스턴스가 생성됩니다.
//  하나의 세션 동안만 빈이 유지되고, 세션이 종료되면 빈도 소멸됩니다.
//
//  application:
//  Spring 애플리케이션 범위로, 애플리케이션이 실행되는 동안 하나의 인스턴스가 생성됩니다.
//  보통 싱글톤과 유사하게 작동하지만, 전체 애플리케이션 범위에서 관리되는 빈입니다.
//
//  WebSocket:
//  WebSocket 세션 당 빈이 생성되며, WebSocket 연결이 유지되는 동안만 빈이 존재합니다.