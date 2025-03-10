package com.in28minutes.learn_spring_framework.game;

public class GameRunner {
    //MarioGame game;
    private GamingConsole game; //SuperContraGame -> GamingConsole로 변경
    // game변수는 MarioGame, SuperControlGame 둘 다 받을 줄 알아야함
    public GameRunner(GamingConsole game) {
        // 매개변수를 SuperContraGame -> GamingConsole로 변경
        this.game = game;
    }
    public void run(){
        System.out.println("Running game : " + game);
        game.up();
        game.down();
        game.left();
        game.right();
    }
}
