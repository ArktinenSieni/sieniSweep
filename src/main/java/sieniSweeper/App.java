package sieniSweeper;

import javafx.application.Application;
import javafx.stage.Stage;
import sieniSweeper.models.MineField;

import static javafx.application.Application.launch;


public class App {
    public static void main(String[] args) {
        //SweepLogic game = new SweepLogic(15, 15, 30);
        //game.printMineField();
        //game.sweep(7, 7);
        //game.printMineField();
        //game.sweep(0,0);
        //game.printMineField();
        //game.sweep(14, 14);
        //game.printMineField();

        launch(SieniUI.class);
    }
}
