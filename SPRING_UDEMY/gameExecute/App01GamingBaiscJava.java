package gameExecute;

import com.in28minutes.learn_spring_framework.game.GameRunner;
import com.in28minutes.learn_spring_framework.game.PacmanGame;

public class App01GamingBaiscJava {
    public static void main(String[] args){
      //  var game = new SuperContraGame();
        //var game = new MarioGame();
        var game = new PacmanGame(); // 1: Object Creation
        var gameRunner = new GameRunner(game);
        // 2: Object Creation + Wiring of Dependencies
        // Game is a Dependency
        gameRunner.run();
    }
}
