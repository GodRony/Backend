package com.naver.shopping.lifecycle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LifeCycleConfig {

    // 방법 1    @Bean(initMethod = "init", destroyMethod = "close") 로 설정해주기.
//    @Bean(initMethod = "init", destroyMethod = "close") // 초기화 및 종료 메서드 지정
//    public NetworkClient networkClient() {
//        NetworkClient networkClient = new NetworkClient();
//        networkClient.setUrl("https://www.github.com");
//        // setUrl은 의존성이 있으니 이거를 설정하고나서 init메서드가 실행됨.
//        return networkClient;
//    }
    // Spring에서 @Bean의 initMethod로 지정된 메서드는 빈 생성 후 의존성 주입이 완료된 다음에 실행됩니다.
    // init()은 @Bean(initMethod = "init")로 지정했기 때문에
    // networkClient() 메서드의 마지막에 반환되기 전에 실행됩니다.


    // 방법 2
    // 그냥 Bean으로 해주고
    // NetworkClient에 init에 @PostConstruct를 close에는 @PreDestroy를 붙임.
    @Bean
    public NetworkClient networkClient() {
        NetworkClient networkClient = new NetworkClient();
        networkClient.setUrl("https://www.github.com");
        return networkClient;
    }

    // ######## 둘 중 어느 방법을 써야할까? #########
    // 1. 외부에서 받은 클래스를 쓰는 경우
    // 외부 클래스는 저희가 수정을 못하죠. 이 코드안에 @PostConstruct같은 어노테이션을 추가하지 못하니
    // 저희가 설정할 설정 클래스에서 @Bean에 initMethod, destroyMethod를 넣어주는 방법이 수월할것입니다.
    // 2. 내가 만든 클래스를 쓰는 경우
    // 내가 만든 클래스는 중간에 어노테이션 자유롭게 넣을 수 있겠죠? @Bean에 메서드 이름을 string으로 넣어주면
    // 컴파일이 잡지 못하는 에러가 나올 수도 있기 때문에 @PostConstruct, @PreDestroy를 사용해줍시다.
}
