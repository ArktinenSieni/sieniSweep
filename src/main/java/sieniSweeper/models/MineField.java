package sieniSweeper.models;

import java.util.Random;

public class MineField {
    /**
     * Value 10 in a field means it contains a mine
     */
    private Tile field[][];
    private int mineAmount;
    private int width;
    private int height;

    public MineField(int width, int height, int mineAmount) {
        this.width = width;
        this.height = height;
        this.mineAmount = mineAmount;

        initMinefield();
    }

    private void initMinefield() {
        this.field = initTiles();

        Random r = new Random();

        for (int i = 0; i < mineAmount; i++) {
            int targetX = r.nextInt(this.width);
            int targetY = r.nextInt(this.height);

            while(this.field[targetX][targetY].getContent() == 10) {
                // To make sure the mine is deployed to empty tile
                targetX = r.nextInt(this.width);
                targetY = r.nextInt(this.height);
            }

            this.field[targetX][targetY].setMine();

            setNumbersAroundMine(targetX, targetY);
        }
    }

    private Tile[][] initTiles() {
        Tile[][] result = new Tile[this.width][this.height];

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                result[i][j] = new Tile();
            }
        }

        return result;
    }

    private void setNumbersAroundMine(int targetX, int targetY) {
        int[] limits = selectAround(targetX, targetY);

        int xMin = limits[0];
        int xMax = limits[1];
        int yMin = limits[2];
        int yMax = limits[3];

        for (int i = xMin; i <= xMax ; i++) {
            for (int j = yMin; j <= yMax ; j++) {
                if (this.field[i][j].getContent() != 10) {
                    this.field[i][j].incrementContent();
                }
            }
        }
    }

    @Override
    public String toString() {
        String result = "";

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                Tile tile = this.field[i][j];

                result += "|";

                if (tile.toString().length() < 2) {
                    result += " ";
                }

                result += tile;
            }
            result += "|\n";
        }
        return result;
    }

    private int[] selectAround(int x, int y) {
        int xMin = Math.max(0, x - 1);
        int xMax = Math.min(this.width - 1, x + 1);

        int yMin = Math.max(0, y - 1);
        int yMax = Math.min(this.height - 1, y + 1);

        return new int[]{xMin, xMax, yMin, yMax};
    }

    public void sweep(int x, int y) {
        this.field[x][y].setVisible();

        int[] limits = selectAround(x, y);
        int xMin = limits[0];
        int xMax = limits[1];
        int yMin = limits[2];
        int yMax = limits[3];

        for (int i = xMin; i <= xMax; i++) {
            for (int j = yMin; j <= yMax; j++) {

                if (!this.field[i][j].hasMine())
                this.field[i][j].setVisible();
            }
        }
    }

    public Tile[][] getMineField() {
        return this.field;
    }

    public void revealAll() {
        for (int i = 0; i < this.field.length; i++) {
            for (int j = 0; j < this.field[0].length; j++) {
                this.field[i][j].setVisible();
            }
        }
    }
}
