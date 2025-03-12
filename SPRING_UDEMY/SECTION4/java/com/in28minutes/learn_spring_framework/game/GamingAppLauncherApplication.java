package com.in28minutes.learn_spring_framework.game;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


// Bean자동생성 방법 -> Configuration파일에 이는 내용을 갖고오기
@Configuration
@ComponentScan("package com.in28minutes.learn_spring_framework.game")
public class GamingAppLauncherApplication {
    // Bean을 옮김
//    @Bean
//    public GamingConsole game(){
//        var game = new PacmanGame();
//        return game;
//    }
    // PacmanGame, MarioGame, SuperContraGame에 @Component를 줘서
    // public GamingConsole game(){를 안해줄 수 있다. 단 이때 누가 디폴튼지 모르니
    // 셋 중 하나에 Primary해줘야함 그리고 패키지가 달라서 어딜 찾아야하는지 모르므로.. Scan해야함

//    @Bean
//    public GameRunner gameRunner(GamingConsole game){
//        System.out.println("Parameter : "+game);
//        var gameRunner = new GameRunner(game);
//        return gameRunner;
//    }
    // 근데 스프링이 위의  public GameRunner gameRunner 코드도 대신해줄수있다.

    public static void main(String[] args){
        var  context = new AnnotationConfigApplicationContext(GamingAppLauncherApplication.class);
        // GamingConfiguration -> App03GamingSpringBeans로 변경
        context.getBean(GamingConsole.class).up();
        context.getBean(GameRunner.class).run();
    }
}
