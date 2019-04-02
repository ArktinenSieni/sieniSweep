package sieniSweeper;

import sieniSweeper.models.MineField;
import sieniSweeper.models.Tile;

public class SweepLogic {
    MineField mineField;
    Boolean gameWon;
    Boolean gameLost;

    public SweepLogic(int width, int height, int mineAmount) {
        this.mineField = new MineField(width, height, mineAmount);
        this.gameWon = false;
        this.gameLost = false;
    }

    public void printMineField() {
        System.out.println(this.mineField);
    }

    public void sweep(int x, int y) {
        this.mineField.sweep(x, y);

        updateGameStatus();

        if (!gameRunning()) {
            this.mineField.revealAll();
        }
    }

    private void updateGameStatus() {
        Tile[][] field = mineField.getMineField();
        Boolean mineSweeped = false;

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                mineSweeped = mineSweeped || field[i][j].hasMineSweeped();
            }
        }

        this.gameLost = mineSweeped;
    }

    public Boolean gameRunning() {
        return !(gameLost || gameWon);
    }

    public String getTileSymbol(Tile tile) {
        if (tile.hasMine()) {
            return "X";
        } else if (tile.getContent() == 0){
            return " ";
        }

        return tile.getContent() + "";
    }
}
