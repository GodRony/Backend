package com.naver.shopping.prototype;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Provider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class SingletonWithPrototypeTest1 {
    // PrototypeBean(프로토타입)

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;
        public void addCount() {
            count++;
        }
        public int getCount() {
            return count;
        }
        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }
        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }


//    static class ClientBean {
//        private final PrototypeBean prototypeBean;
//        @Autowired
//        public ClientBean(PrototypeBean prototypeBean) {
//            this.prototypeBean = prototypeBean;
//        }
//        public int logic() {
//            prototypeBean.addCount();
//            int count = prototypeBean.getCount();
//            return count;
//        }
//    }
    // ########### ClientBean(싱글톤) #############
    // 싱글톤이 먼저 만들어지긴 하지만, 의존성을 주입받을때 까진 생성자가 실행되지 않음
    // 싱글톤 생성자 안에 다른 빈 클래스가 매개변수 또는 변수가 있는 경우
    // 싱글톤이 먼저 만들어지지만, 다른 빈 클래스 생성자를 만든 후에 싱글톤 생성자가 만들어짐(저번시간에 배움)
    // 싱글톤 클래스에 (생성자 밖) 다른 빈 클래스가 변수로 있는 경우
    // 이것도 마찬가지임.
    // ########### 코드가 문제있었던 이유 #############
    // 다른 유저가 프로토타입을 만들었을떄 2가 되었던 이유는
    // 클라이언트의 프로토타입이 1껄로 고정이 되어서 유저2가 프로토타입을 만들어도 1껄로 되어있었기 때문

    static class ClientBean {
        private final Provider<PrototypeBean> prototypeBeanProvider;

        @Autowired
        public ClientBean(Provider<PrototypeBean> prototypeBeanProvider) {
            this.prototypeBeanProvider = prototypeBeanProvider;
        }

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeanProvider.get(); // 얘가 getObject가 아닌 get
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    // 해결방법으로
    // Provider<PrototypeBean> , prototypeBeanProvider.get() 사용

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        Assertions.assertThat(count1).isEqualTo(1);
        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        Assertions.assertThat(count2).isEqualTo(1);
    }

}
